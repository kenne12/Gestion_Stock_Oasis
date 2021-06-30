package controllers.transfert;

import entities.Famille;
import entities.Lignemvtstock;
import entities.Lignetransfert;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Transfert;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class TransfertController extends AbstractTransertController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(43L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
            this.mode = "Create";

            this.magasinCible = new Magasin();
            this.magasin = new Magasin();

            this.lignetransferts.clear();
            this.lignetransferts_1.clear();
            this.transfert = new Transfert();
            this.transfert.setDatetransfert(new Date());
            this.magasinlots.clear();
            this.selectedMagasinlots.clear();

            this.showSelectorCommand = false;

            this.total = 0d;
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareAddArticle() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                if (this.magasinCible.getIdmagasin() != null) {
                    if (!Objects.equals(this.magasin.getIdmagasin(), this.magasinCible.getIdmagasin())) {
                        this.famille = new Famille();

                        this.lignetransfert = new Lignetransfert();
                        this.lignetransfert.setQuantite(1.0D);

                        this.magasinlots.clear();
                        this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinQtyNotNull(this.magasin.getIdmagasin());

                        this.magasinarticle = new Magasinarticle();
                        this.magasinlot = new Magasinlot();
                        this.lignetransferts_1.clear();
                        this.libelle_article = "-";
                        RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
                        return;
                    }
                    notifyError("veuillez_selectionner_les_magasin_differents");
                    return;
                }
                notifyError("veuillez_selectionner_le_magasin_cible");
                return;
            }
            notifyError("veuillez_selectionner_le_magasin_initial");
            return;
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.transfert == null) {
                notifyError("not_row_selected");
                return;
            }

            this.showSelectorCommand = true;

            if (!Utilitaires.isAccess(49L)) {
                notifyError("acces_refuse");
                this.transfert = null;
                return;
            }

            this.mode = "Edit";
            this.magasin = this.transfert.getIdmagasin();
            this.magasinCible = this.magasinFacadeLocal.find(this.transfert.getIdmagasincible());

            this.lignetransferts = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());
            this.lignetransferts_1 = this.lignetransferts;
            this.total = this.transfert.getMontanttotal();
            RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.transfert != null) {
                this.lignetransferts = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());

                if (!this.lignetransferts.isEmpty()) {
                    this.magasin = this.transfert.getIdmagasin();
                    this.magasinCible = this.magasinFacadeLocal.find(this.transfert.getIdmagasincible());
                    RequestContext.getCurrentInstance().execute("PF('TransfertDetailDialog').show()");
                    return;
                }
                notifyError("liste_article_vide");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public Magasin findMagasin(int idmagasin) {
        return this.magasinFacadeLocal.find(idmagasin);
    }

    public void filterArticle() {
        try {
            this.magasinarticles.clear();
            this.magasinlots.clear();
            this.selectedMagasinlots.clear();
            this.magasinarticle = new Magasinarticle();
            this.magasinlot = new Magasinlot();
            if (this.famille.getIdfamille() != null) {
                this.magasinarticleFacadeLocal.findByIdmagasinIdfamilleIsavailable(this.magasin.getIdmagasin(), this.famille.getIdfamille(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduit() {
        try {
            if (this.magasinlot != null) {
                Magasinlot ml = this.magasinlot;

                if (this.magasinlot.getQuantitemultiple() < 1.0D) {
                    notifyError("defaut_de_quantite");
                    return;
                }

                if (this.lignetransfert.getQuantite() > this.magasinlot.getQuantitemultiple() - this.magasinlot.getQuantitevirtuelle()) {
                    notifyError("quantite_inexate");
                    return;
                }

                if (findMagasinLotInLigneRepartition(ml, this.lignetransferts)) {
                    /* 205 */ Lignetransfert lt = this.lignetransfert;
                    /* 206 */ lt.setIdlignetransfert((0L));
                    /* 207 */ lt.setIdmagasinlot(ml);
                    /* 208 */ lt.setUnite(ml.getIdlot().getIdarticle().getUnite());
                    /* 209 */ lt.setQuantitemultiple(ml.getIdlot().getIdarticle().getUnite());
                    /* 210 */ this.lignetransferts.add(lt);
                }
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean findMagasinLotInLigneRepartition(Magasinlot m, List<Lignetransfert> lignetransferts) {
        if (lignetransferts.isEmpty()) {
            return true;
        }
        boolean result = false;

        for (Lignetransfert lt : lignetransferts) {
            if (!lt.getIdmagasinlot().equals(m)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void selectProduct() {
        try {
            if (this.magasinlot != null) {
                this.lignetransfert.setUnite(this.magasinlot.getIdmagasinarticle().getIdarticle().getUnite());
                this.libelle_article = this.magasinlot.getIdmagasinarticle().getIdarticle().getLibelle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateData() {
        try {
            if (!this.lignetransferts_1.isEmpty()) {
                double qte_physique = 0.0D;
                double qte_solicitee = 0.0D;
                for (Lignetransfert lt : this.lignetransferts_1) {
                    qte_physique += lt.getIdmagasinlot().getQuantite();
                    qte_solicitee += lt.getQuantite();
                }

                if (qte_solicitee > qte_physique) {
                    notifyError("quantite_inexate");
                    return;
                }

                for (Lignetransfert lt : this.lignetransferts_1) {
                    if (findMagasinLotInLigneRepartition(lt.getIdmagasinlot(), this.lignetransferts)) {
                        this.lignetransferts.add(lt);
                    }
                }

                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void updatedata() {
        try {
            this.magasinlot = new Magasinlot();
            this.magasinlots.clear();
            this.selectedMagasinlots.clear();
            if (this.magasinarticle != null) {
                this.famille = this.magasinarticle.getIdarticle().getIdfamille();
                this.magasinlots = this.magasinlotFacadeLocal.findByArticleIsavailable(this.magasin.getIdmagasin(), this.magasinarticle.getIdarticle().getIdarticle(), this.magasinarticle.getIdarticle().getPerissable(), new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if ("Create".equals(this.mode)) {
                if (!this.lignetransferts.isEmpty()) {
                    this.magasin = this.magasinFacadeLocal.find(this.magasin.getIdmagasin());
                    this.magasinCible = this.magasinFacadeLocal.find(this.magasinCible.getIdmagasin());

                    String message = "";

                    String codeMvt = "MVT";
                    this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    Long nextMvt = this.mvtstock.getIdmvtstock();
                    codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);

                    this.ut.begin();

                    this.mvtstock.setCode(codeMvt);
                    this.mvtstock.setDatemvt(this.transfert.getDatetransfert());
                    this.mvtstock.setClient(this.magasinCible.getNom());
                    this.mvtstock.setFournisseur(this.magasin.getNom());
                    this.mvtstock.setMagasin(" ");
                    this.mvtstock.setType("ENTRE SORTIE");
                    this.mvtstockFacadeLocal.create(this.mvtstock);

                    String code = "MVT";
                    Long nextVal = this.transfertFacadeLocal.nextVal();
                    code = Utilitaires.genererCodeDemande(code, nextVal);
                    this.transfert.setCode(code);
                    this.transfert.setIdtransfert(nextVal);
                    this.transfert.setIdmagasin(this.magasin);
                    this.transfert.setIdmagasincible(this.magasinCible.getIdmagasin());
                    this.transfert.setIdmvtstock(this.mvtstock);
                    this.transfert.setIdUtilisateur(SessionMBean.getUserAccount().getIdutilisateur());
                    this.transfertFacadeLocal.create(this.transfert);

                    updateTotal();

                    for (Lignetransfert ltt : this.lignetransferts) {
                        ltt.setIdlignetransfert(this.lignetransfertFacadeLocal.nextVal());
                        ltt.setIdtransfert(this.transfert);
                        ltt.setQuantitemultiple((ltt.getQuantite() * ltt.getUnite()));
                        ltt.setMontant(ltt.getIdmagasinlot().getIdlot().getPrixunitaire());
                        this.lignetransfertFacadeLocal.create(ltt);

                        Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ltt.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        maTemp.setQuantite(maTemp.getQuantite() - ltt.getQuantite());
                        maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - ltt.getQuantitemultiple()));
                        maTemp.setQuantitereduite((maTemp.getQuantitereduite() - ltt.getQuantitereduite()));
                        this.magasinarticleFacadeLocal.edit(maTemp);

                        Magasinlot mlTemp = this.magasinlotFacadeLocal.find(ltt.getIdmagasinlot().getIdmagasinlot());
                        double qteAvant = mlTemp.getQuantitemultiple();

                        mlTemp.setQuantite((mlTemp.getQuantite() - ltt.getQuantite()));
                        mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - ltt.getQuantitemultiple()));
                        mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - ltt.getQuantitereduite()));
                        this.magasinlotFacadeLocal.edit(mlTemp);

                        Lignemvtstock lmvts = new Lignemvtstock();
                        lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        lmvts.setIdmvtstock(this.mvtstock);
                        lmvts.setIdlot(ltt.getIdmagasinlot().getIdlot());
                        lmvts.setIdmagasinlot(ltt.getIdmagasinlot());
                        lmvts.setQtesortie(ltt.getQuantite());
                        lmvts.setQteentree(0d);
                        lmvts.setClient(this.magasinCible.getNom());
                        lmvts.setFournisseur(" ");
                        lmvts.setType("SORTIE");
                        lmvts.setReste(mlTemp.getQuantitemultiple());
                        lmvts.setQteAvant(qteAvant);
                        this.lignemvtstockFacadeLocal.create(lmvts);

                        Magasinlot ml = this.magasinlotFacadeLocal.findByIdmagasinIdlot(this.magasinCible.getIdmagasin().intValue(), ltt.getIdmagasinlot().getIdlot().getIdlot().longValue());
                        if (ml != null) {
                            qteAvant = ml.getQuantitemultiple();
                            ml.setIdmagasinarticle(this.magasinarticleFacadeLocal.find(ml.getIdmagasinarticle().getIdmagasinarticle()));
                            ml.getIdmagasinarticle().setQuantite((ml.getIdmagasinarticle().getQuantite() + ltt.getQuantite()));
                            ml.getIdmagasinarticle().setQuantitemultiple((ml.getIdmagasinarticle().getQuantitemultiple() + ltt.getQuantitemultiple()));
                            ml.getIdmagasinarticle().setQuantitereduite((ml.getIdmagasinarticle().getQuantitereduite() + ltt.getQuantitereduite()));
                            this.magasinarticleFacadeLocal.edit(ml.getIdmagasinarticle());

                            ml.setQuantite(ml.getQuantite() + ltt.getQuantite());
                            ml.setQuantitemultiple(ml.getQuantitemultiple() + ltt.getQuantitemultiple());
                            ml.setQuantitereduite(ml.getQuantitereduite() + ltt.getQuantitereduite());
                            this.magasinlotFacadeLocal.edit(ml);

                            Lignemvtstock lmvt = new Lignemvtstock();
                            lmvt.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            lmvt.setIdmvtstock(this.mvtstock);
                            lmvt.setIdlot(ml.getIdlot());
                            lmvt.setIdmagasinlot(ml);
                            lmvt.setQtesortie(0d);
                            lmvt.setQteentree(ltt.getQuantitemultiple());
                            lmvt.setClient(" ");
                            lmvt.setReste(ml.getQuantitemultiple());
                            lmvt.setFournisseur(this.magasin.getNom());
                            lmvt.setType("ENTREE");
                            lmvt.setQteAvant(qteAvant);
                            this.lignemvtstockFacadeLocal.create(lmvt);
                        } else {
                            Magasinarticle ma = this.magasinarticleFacadeLocal.findByIdmagasinIdarticle(this.magasinCible.getIdmagasin().intValue(), ltt.getIdmagasinlot().getIdlot().getIdarticle().getIdarticle().longValue());
                            if (ma == null) {
                                ma = new Magasinarticle();
                                ma.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                                ma.setIdarticle(ltt.getIdmagasinlot().getIdlot().getIdarticle());
                                ma.setIdmagasin(this.magasinCible);
                                ma.setQuantite(ltt.getQuantite());
                                ma.setQuantitemultiple(ltt.getQuantitemultiple());
                                ma.setUnite(ltt.getUnite());
                                ma.setEtat(true);
                                ma.setQuantitereduite(ltt.getQuantitereduite());
                                ma.setQuantitevirtuelle(0d);
                                ma.setQuantitesecurite(ltt.getIdmagasinlot().getIdlot().getIdarticle().getQuantitesecurite());
                                this.magasinarticleFacadeLocal.create(ma);
                            } else {
                                ma.setQuantite((ma.getQuantite() + ltt.getQuantite()));
                                ma.setQuantitemultiple((ma.getQuantitemultiple() + ltt.getQuantitemultiple()));
                                ma.setQuantitereduite((ma.getQuantitereduite() + ltt.getQuantitereduite()));
                                this.magasinarticleFacadeLocal.edit(ma);
                            }

                            Magasinlot mlTemp1 = new Magasinlot();
                            mlTemp1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                            mlTemp1.setIdmagasinarticle(ma);
                            mlTemp1.setIdlot(ltt.getIdmagasinlot().getIdlot());
                            mlTemp1.setEtat((true));
                            mlTemp1.setQuantite(ltt.getQuantite());
                            mlTemp1.setUnite(ltt.getUnite());
                            mlTemp1.setQuantitemultiple(ltt.getQuantitemultiple());
                            mlTemp1.setQuantitereduite(ltt.getQuantitemultiple());
                            mlTemp1.setQuantitevirtuelle(0d);

                            this.magasinlotFacadeLocal.create(mlTemp1);

                            Lignemvtstock lmvt = new Lignemvtstock();
                            lmvt.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            lmvt.setIdmvtstock(this.mvtstock);
                            lmvt.setIdlot(mlTemp1.getIdlot());
                            lmvt.setIdmagasinlot(mlTemp1);
                            lmvt.setQtesortie(0d);
                            lmvt.setQteentree(ltt.getQuantitemultiple());
                            lmvt.setClient(" ");
                            lmvt.setReste(ltt.getQuantitemultiple());
                            lmvt.setFournisseur(this.magasin.getNom());
                            lmvt.setType("ENTREE");
                            lmvt.setQteAvant(0d);
                            this.lignemvtstockFacadeLocal.create(lmvt);
                        }
                    }

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du transfert d'article ; N° : " + this.transfert.getCode() + "; Montant : " + this.transfert.getMontanttotal(), SessionMBean.getUserAccount());

                    this.ut.commit();
                    this.detail = this.supprimer = this.modifier = this.imprimer = true;
                    JsfUtil.addSuccessMessage(message);

                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').hide()");
                } else {
                    notifyError("liste_article_vide");
                }
            } else if (this.transfert == null) {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.transfert != null) {
                if (!Utilitaires.isAccess((43L))) {
                    notifyError("acces_refuse");
                    this.detail = (this.supprimer = this.modifier = this.imprimer = (true));
                    this.transfert = null;
                    return;
                }

                this.ut.begin();

                List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.transfert.getIdmvtstock().getIdmvtstock());
                for (Lignemvtstock l : lmvt) {
                    this.lignemvtstockFacadeLocal.remove(l);
                }
                Lignemvtstock l;
                List<Lignetransfert> listLigneTransfert = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());
                for (Lignetransfert lt : listLigneTransfert) {
                    this.lignetransfertFacadeLocal.remove(lt);

                    Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(lt.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                    maTemp.setQuantite((maTemp.getQuantite() + lt.getQuantite()));
                    maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() + lt.getQuantitemultiple()));
                    maTemp.setQuantitereduite((maTemp.getQuantitereduite() + lt.getQuantitereduite()));
                    this.magasinarticleFacadeLocal.edit(maTemp);

                    Magasinlot mlTemp = this.magasinlotFacadeLocal.find(lt.getIdmagasinlot().getIdmagasinlot());
                    mlTemp.setQuantite((mlTemp.getQuantite() + lt.getQuantite()));
                    mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() + lt.getQuantitemultiple()));
                    mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() + lt.getQuantitereduite()));
                    this.magasinlotFacadeLocal.edit(mlTemp);

                    Magasinlot ml = this.magasinlotFacadeLocal.findByIdmagasinIdlot(this.transfert.getIdmagasincible().intValue(), lt.getIdmagasinlot().getIdlot().getIdlot().longValue());
                    if (ml != null) {
                        Magasinarticle ma1 = ml.getIdmagasinarticle();

                        ma1.setQuantite((ma1.getQuantite() - lt.getQuantite()));
                        ma1.setQuantitemultiple((ma1.getQuantitemultiple() - lt.getQuantitemultiple()));
                        ma1.setQuantitereduite((ma1.getQuantitereduite() - lt.getQuantitereduite()));
                        this.magasinarticleFacadeLocal.edit(ma1);

                        ml.setQuantite((ml.getQuantite() - lt.getQuantite()));
                        ml.setQuantitemultiple((ml.getQuantitemultiple() - lt.getQuantitemultiple()));
                        ml.setQuantitereduite((ml.getQuantitereduite() - lt.getQuantitereduite()));
                        this.magasinlotFacadeLocal.edit(ml);
                    }
                }

                this.transfertFacadeLocal.remove(this.transfert);
                this.mvtstockFacadeLocal.remove(this.transfert.getIdmvtstock());

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation du transfert d'article : " + this.transfert.getCode() + " ; Montant : " + this.transfert.getMontanttotal(), SessionMBean.getUserAccount());
                this.ut.commit();

                this.transfert = null;
                this.supprimer = (this.modifier = this.imprimer = this.detail = Boolean.valueOf(true));
                notifySuccess();
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Transfert t) {
        this.transfert = t;
        print();
    }

    public void initEdit(Transfert t) {
        this.transfert = t;
        prepareEdit();
    }

    public void initView(Transfert t) {
        this.transfert = t;
        prepareview();
    }

    public void initDelete(Transfert t) {
        this.transfert = t;
        delete();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(43L)) {
                notifyError("acces_refuse");
                this.transfert = null;
                return;
            }

            if (this.transfert != null) {
                transfert.setLignetransfertList(lignetransfertFacadeLocal.findByIdTransfert(transfert.getIdtransfert()));

                fileName = PrintUtils.printTransfert(transfert, magasinFacadeLocal.find(transfert.getIdmagasincible()).getNom());

                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('TransfertImprimerDialog').show()");

                //Map map = new HashMap();
                //map.put("idtransfert", this.transfert.getIdtransfert());
                //Printer.print("/reports/ireport/transfert.jasper", map);
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void removeArticle(int index) {
        try {
            boolean trouve = false;

            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal(List<Lignetransfert> lignetransferts) {
        Double resultat = 0d;
        int i = 0;
        for (Lignetransfert lt : lignetransferts) {
            resultat += (lt.getIdmagasinlot().getIdlot().getPrixunitaire() * lt.getQuantite() * lt.getUnite());
            lignetransferts.get(i).setQuantitemultiple((lignetransferts.get(i).getQuantite() * lt.getUnite()));
            lignetransferts.get(i).setMontant(lt.getIdmagasinlot().getIdlot().getPrixunitaire());
            lignetransferts.get(i).setQuantitereduite((lignetransferts.get(i).getQuantitemultiple() / lt.getIdmagasinlot().getIdlot().getIdarticle().getUnite()));
            i++;
        }
        return resultat;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal(this.lignetransferts);
            this.transfert.setMontanttotal(this.total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
