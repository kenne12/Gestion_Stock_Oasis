package controllers.sortie_directe;

import entities.Client;
import entities.Famille;
import entities.Lignelivraisonclient;
import entities.Lignemvtstock;
import entities.Livraisonclient;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import entities.Unite;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.time.Instant;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class SortiedirectController extends AbstractSortiedirectController implements Serializable {

    @PostConstruct
    private void init() {
        this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getAnnee().getDateDebut(), SessionMBean.getAnnee().getDateFin());
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public void prepareCreate() {
        try {
            if (Utilitaires.isDayClosed()) {
                notifyError("journee_cloturee");
                return;
            }

            if (!Utilitaires.isAccess(23L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('SortieDirecteCreateDialog').show()");
            this.mode = "Create";
            this.client = new Client();
            this.magasinarticle = new Magasinarticle();
            this.livraisonclient = new Livraisonclient();
            this.livraisonclient.setDatelivraison(Date.from(Instant.now()));
            this.livraisonclient.setModePayement("PAYE_COMPTANT");
            this.livraisonclient.setMontant(0.0);
            this.livraisonclient.setTauxRemise(SessionMBean.getParametrage().getTauxRemise());
            this.livraisonclient.setTauxTva(SessionMBean.getParametrage().getTauxTva());
            clientToSave = new Client();

            this.lignelivraisonclients.clear();
            this.mvtstock = new Mvtstock();
            this.total = 0.0;
            this.magasinarticles = this.magasinarticleFacadeLocal.findByIdmagasin(this.magasin.getIdmagasin(), true);
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateClient() {
        clientToSave = new Client();
        clientToSave.setNom("-");
        clientToSave.setCode("" + clientFacadeLocal.nextVal());
        clientToSave.setContact("-");
        clientToSave.setEmail("-");
        clientToSave.setAdresse("-");
        RequestContext.getCurrentInstance().execute("PF('ClientCreerDialog').show()");
    }

    public void prepareAddArticle() {
        this.famille = new Famille();
        this.unite = new Unite();
        this.magasinarticle = new Magasinarticle();
        this.lignelivraisonclient = new Lignelivraisonclient();
        this.magasinlot = new Magasinlot();
        RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
    }

    public void prepareEdit() {
        try {
            if (this.livraisonclient.getLivraisondirecte()) {
                if (Utilitaires.isDayClosed()) {
                    notifyError("journee_cloturee");
                    return;
                }

                if (!Utilitaires.isAccess(24L)) {
                    notifyError("acces_refuse");
                    this.livraisonclient = null;
                    return;
                }

                this.mode = "Edit";

                if (this.livraisonclient != null) {
                    this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                    this.client = this.livraisonclient.getClient();
                    this.total = this.livraisonclient.getMontant();
                    this.mvtstock = this.livraisonclient.getIdmvtstock();
                }
                RequestContext.getCurrentInstance().execute("PF('SortieDirecteCreateDialog').show()");
            } else {
                notifyError("vente_effectuee_par_lignelivraisonclient");
            }
        } catch (Exception e) {
            notifyError("echec_operation");
        }
    }

    public void prepareview() {
        try {
            if (this.livraisonclient != null) {
                this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                if (!this.lignelivraisonclients.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('SortieDirecteDetailDialog').show()");
                    return;
                }
                notifyError("liste_article_vide");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyError("echec_operation");
        }
    }

    public void filterArticle() {
        try {
            this.magasinarticles.clear();
            this.magasinlots.clear();
            this.magasinarticle = new Magasinarticle();
            this.magasinlot = new Magasinlot();
            this.lignelivraisonclient = new Lignelivraisonclient();
            if (this.famille.getIdfamille() != null) {
                this.magasinarticles = this.magasinarticleFacadeLocal.findByIdmagasinIdfamille(this.magasin.getIdmagasin(), this.famille.getIdfamille(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            String message;
            if ("Create".equals(this.mode)) {
                if (this.lignelivraisonclients.isEmpty()) {
                    notifyError("liste_article_vide");
                    return;
                }
                message = "";
                updateTotal();

                if (livraisonclient.getModePayement().equalsIgnoreCase("PAYE_A_CREDIT")) {
                    if (livraisonclient.getAvanceInitiale() > livraisonclient.getMontantTtc()) {
                        notifyError("montant_avance_incorrect");
                        return;
                    }
                }

                this.ut.begin();
                this.client = this.clientFacadeLocal.find(this.client.getIdclient());

                Long nextMvt = this.mvtstockFacadeLocal.nextVal();
                String codeMvt = "MVT";
                codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);

                this.mvtstock.setCode(codeMvt);
                this.mvtstock.setIdmvtstock(nextMvt);
                this.mvtstock.setDatemvt(this.livraisonclient.getDatelivraison());
                this.mvtstock.setType("-");
                this.mvtstock.setClient("-");
                this.mvtstock.setFournisseur("-");
                this.mvtstock.setMagasin("-");
                this.mvtstockFacadeLocal.create(this.mvtstock);

                String code = "F";
                Long nextPayement = this.livraisonclientFacadeLocal.nextVal();
                code = Utilitaires.genererCodeStock(code, nextPayement);

                this.livraisonclient.setCode(code);
                this.livraisonclient.setIdlivraisonclient(nextPayement);
                this.livraisonclient.setClient(this.client);
                this.livraisonclient.setIdmagasin(this.magasin);
                this.livraisonclient.setMontant(this.total);
                this.livraisonclient.setLivraisondirecte(true);

                this.livraisonclient.setIdmvtstock(this.mvtstock);
                if (livraisonclient.getModePayement().equals("PAYE_COMPTANT")) {
                    livraisonclient.setAvanceInitiale(livraisonclient.getMontantTtc());
                    livraisonclient.setMontantPaye(livraisonclient.getMontantTtc());
                } else {
                    if (livraisonclient.getAvanceInitiale() < 0) {
                        livraisonclient.setAvanceInitiale(0);
                    }
                    livraisonclient.setMontantPaye(livraisonclient.getAvanceInitiale());
                }
                this.livraisonclient.setIdmagasin(SessionMBean.getMagasin());
                this.livraisonclientFacadeLocal.create(this.livraisonclient);

                for (Lignelivraisonclient c : this.lignelivraisonclients) {
                    c.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());
                    c.setIdlivraisonclient(this.livraisonclient);
                    c.setQuantitemultiple((c.getQuantite() * c.getUnite()));
                    c.setQuantitereduite(c.getQuantite());
                    c.setIdlot(c.getIdmagasinlot().getIdlot());
                    this.lignelivraisonclientFacadeLocal.create(c);

                    c.setIdmagasinlot(this.magasinlotFacadeLocal.find(c.getIdmagasinlot().getIdmagasinlot()));
                    double qteAvant = c.getIdmagasinlot().getQuantitemultiple();
                    c.getIdmagasinlot().setQuantite((c.getIdmagasinlot().getQuantite() - c.getQuantite()));
                    c.getIdmagasinlot().setQuantitemultiple((c.getIdmagasinlot().getQuantitemultiple() - c.getQuantitemultiple()));
                    c.getIdmagasinlot().setQuantitereduite((c.getIdmagasinlot().getQuantitereduite() - c.getQuantitereduite()));
                    this.magasinlotFacadeLocal.edit(c.getIdmagasinlot());

                    c.getIdmagasinlot().setIdmagasinarticle(this.magasinarticleFacadeLocal.find(c.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle()));
                    c.getIdmagasinlot().getIdmagasinarticle().setQuantite((c.getIdmagasinlot().getIdmagasinarticle().getQuantite() - c.getQuantite()));
                    c.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple((c.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple() - c.getQuantitemultiple()));
                    c.getIdmagasinlot().getIdmagasinarticle().setQuantitereduite((c.getIdmagasinlot().getIdmagasinarticle().getQuantitereduite() - c.getQuantitereduite()));
                    this.magasinarticleFacadeLocal.edit(c.getIdmagasinlot().getIdmagasinarticle());

                    Lignemvtstock lmvts = new Lignemvtstock();
                    lmvts.setIdlignemvtstock(this.ligneMvtstockFacadeLocal.nextVal());
                    lmvts.setIdmvtstock(this.mvtstock);
                    lmvts.setIdlot(c.getIdmagasinlot().getIdlot());
                    lmvts.setIdmagasinlot(c.getIdmagasinlot());

                    lmvts.setQteentree(0.0);
                    lmvts.setQteAvant(qteAvant);
                    lmvts.setQtesortie(c.getQuantitemultiple());
                    lmvts.setReste(c.getIdmagasinlot().getQuantitemultiple());

                    lmvts.setType("SORTIE");
                    lmvts.setClient(this.client.getNom() + " " + this.client.getPrenom());
                    lmvts.setFournisseur(this.magasin.getNom());
                    lmvts.setMagasin(this.magasin.getNom());
                    this.ligneMvtstockFacadeLocal.create(lmvts);
                }

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la sortie : " + code, SessionMBean.getUserAccount());

                this.ut.commit();
                this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getAnnee().getDateDebut(), SessionMBean.getAnnee().getDateFin());
                this.livraisonclient = new Livraisonclient();
                this.supprimer = this.modifier = this.imprimer = detail = true;
                JsfUtil.addSuccessMessage(message);

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('SortieDirecteCreateDialog').hide()");

            } else if (this.livraisonclient != null) {
                this.calculTotal();
                this.ut.begin();

                this.client = this.clientFacadeLocal.find(this.client.getIdclient());
                this.livraisonclient.setClient(this.client);
                this.livraisonclientFacadeLocal.edit(this.livraisonclient);

                if (!this.lignelivraisonclients.isEmpty()) {
                    for (Lignelivraisonclient llc : this.lignelivraisonclients) {
                        if (llc.getIdlignelivraisonclient() != 0L) {
                            Lignelivraisonclient cc = this.lignelivraisonclientFacadeLocal.find(llc.getIdlignelivraisonclient());
                            if (!Objects.equals(llc.getQuantite(), cc.getQuantite())) {
                                Magasinarticle pro = this.magasinarticleFacadeLocal.find(cc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                                pro.setQuantite((pro.getQuantite() + cc.getQuantite()) - llc.getQuantite());
                                pro.setQuantitemultiple((pro.getQuantitemultiple() + cc.getQuantitemultiple()) - llc.getQuantitemultiple());
                                pro.setQuantitereduite((pro.getQuantitereduite() + cc.getQuantitereduite()) - llc.getQuantitereduite());
                                this.magasinarticleFacadeLocal.edit(pro);

                                Magasinlot l = this.magasinlotFacadeLocal.find(cc.getIdmagasinlot().getIdmagasinlot());
                                l.setQuantite((l.getQuantite() + cc.getQuantite()) - llc.getQuantite());
                                l.setQuantitemultiple((l.getQuantitemultiple() + cc.getQuantitemultiple()) - llc.getQuantitemultiple());
                                l.setQuantitereduite((l.getQuantitereduite() + cc.getQuantitereduite()) - llc.getQuantitereduite());
                                this.magasinlotFacadeLocal.edit(l);

                                Lignemvtstock lmvts = ligneMvtstockFacadeLocal.findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(), llc.getIdlot().getIdlot());
                                lmvts.setQtesortie(llc.getQuantitemultiple());
                                if (llc.getQuantitemultiple() > cc.getQuantitemultiple()) {
                                    lmvts.setReste(lmvts.getReste() - (llc.getQuantitemultiple() - cc.getQuantitemultiple()));
                                } else {
                                    lmvts.setReste(lmvts.getReste() + (cc.getQuantitemultiple() - llc.getQuantitemultiple()));
                                }
                                this.ligneMvtstockFacadeLocal.edit(lmvts);
                            }
                            this.lignelivraisonclientFacadeLocal.edit(llc);
                        } else {
                            llc.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());
                            llc.setIdlivraisonclient(this.livraisonclient);
                            this.lignelivraisonclientFacadeLocal.create(llc);
                            llc.getIdmagasinlot().setIdmagasinarticle(this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle()));
                            llc.getIdmagasinlot().getIdmagasinarticle().setQuantite((llc.getIdmagasinlot().getIdmagasinarticle().getQuantite() - llc.getQuantite()));
                            this.magasinarticleFacadeLocal.edit(llc.getIdmagasinlot().getIdmagasinarticle());

                            Magasinlot l = this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot());
                            l.setQuantite((l.getQuantite() - llc.getQuantite()));
                            this.magasinlotFacadeLocal.edit(l);

                            Lignemvtstock lmvts = new Lignemvtstock();
                            lmvts.setIdlignemvtstock(this.ligneMvtstockFacadeLocal.nextVal());
                            lmvts.setIdmvtstock(this.mvtstock);
                            lmvts.setIdlot(llc.getIdmagasinlot().getIdlot());
                            lmvts.setIdmagasinlot(llc.getIdmagasinlot());

                            lmvts.setQteentree(0.0);
                            lmvts.setQtesortie(llc.getQuantitemultiple());
                            lmvts.setReste(llc.getIdmagasinlot().getQuantitemultiple());
                            lmvts.setQteAvant(llc.getIdmagasinlot().getQuantitemultiple() + llc.getQuantitemultiple());

                            lmvts.setClient(this.client.getNom() + " " + this.client.getPrenom());
                            lmvts.setFournisseur(this.magasin.getNom());
                            lmvts.setMagasin(this.magasin.getNom());

                            this.ligneMvtstockFacadeLocal.create(lmvts);
                        }
                    }
                }

                this.ut.commit();
                this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getAnnee().getDateDebut(), SessionMBean.getAnnee().getDateFin());
                this.livraisonclient = new Livraisonclient();
                this.supprimer = this.modifier = this.imprimer = this.detail = true;

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('SortieDirecteCreateDialog').hide()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void createClient() {
        this.clientToSave.setIdclient(this.clientFacadeLocal.nextVal());
        clientToSave.setEtat(true);
        clientToSave.setMagasin(SessionMBean.getMagasin());
        this.clientFacadeLocal.create(this.clientToSave);
        Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du client : " + this.clientToSave.getNom(), SessionMBean.getUserAccount());
        this.client = clientToSave;
        RequestContext.getCurrentInstance().execute("PF('ClientCreerDialog').hide()");
    }

    public void delete() {
        try {
            if (this.livraisonclient != null) {
                if (this.livraisonclient.getLivraisondirecte()) {
                    if (!Utilitaires.isAccess(25L)) {
                        notifyError("acces_refuse");
                        this.supprimer = this.modifier = this.imprimer = true;
                        this.livraisonclient = null;
                        return;
                    }

                    this.ut.begin();

                    List<Lignelivraisonclient> temp = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                    if (!temp.isEmpty()) {
                        for (Lignelivraisonclient c : temp) {
                            c.getIdmagasinlot().setIdmagasinarticle(this.magasinarticleFacadeLocal.find(c.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle()));
                            c.getIdmagasinlot().getIdmagasinarticle().setQuantite((c.getIdmagasinlot().getIdmagasinarticle().getQuantite() + c.getQuantite()));
                            c.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple((c.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple() + c.getQuantitemultiple()));
                            c.getIdmagasinlot().getIdmagasinarticle().setQuantitereduite((c.getIdmagasinlot().getIdmagasinarticle().getQuantitereduite() + c.getQuantitereduite()));
                            this.magasinarticleFacadeLocal.edit(c.getIdmagasinlot().getIdmagasinarticle());

                            c.setIdmagasinlot(this.magasinlotFacadeLocal.find(c.getIdmagasinlot().getIdmagasinlot()));
                            c.getIdmagasinlot().setQuantite((c.getIdmagasinlot().getQuantite() + c.getQuantite()));
                            c.getIdmagasinlot().setQuantitereduite((c.getIdmagasinlot().getQuantitereduite() + c.getQuantitereduite()));
                            c.getIdmagasinlot().setQuantitemultiple((c.getIdmagasinlot().getQuantitemultiple() + c.getQuantitemultiple()));
                            this.magasinlotFacadeLocal.edit(c.getIdmagasinlot());

                            this.lignelivraisonclientFacadeLocal.remove(c);
                        }
                    }
                    this.livraisonclientFacadeLocal.remove(this.livraisonclient);

                    Mvtstock mTemp = this.livraisonclient.getIdmvtstock();
                    ligneMvtstockFacadeLocal.deleteByIdmvt(mTemp.getIdmvtstock());
                    this.mvtstockFacadeLocal.remove(mTemp);

                    this.ut.commit();
                    this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getAnnee().getDateDebut(), SessionMBean.getAnnee().getDateFin());
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de la sortie : " + this.livraisonclient.getCode() + " Montant : " + this.livraisonclient.getMontant() + " Client : " + this.livraisonclient.getClient().getNom() + " " + this.livraisonclient.getClient().getPrenom(), SessionMBean.getUserAccount());
                    this.livraisonclient = new Livraisonclient();
                    this.supprimer = this.modifier = this.imprimer = true;
                    notifySuccess();
                } else {
                    notifyError("vente_effectuee_par_lignelivraisonclient");
                }
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Livraisonclient l) {
        this.livraisonclient = l;
        print();
    }

    public void initEdit(Livraisonclient l) {
        this.livraisonclient = l;
        prepareEdit();
    }

    public void initView(Livraisonclient l) {
        this.livraisonclient = l;
        prepareview();
    }

    public void initDelete(Livraisonclient l) {
        this.livraisonclient = l;
        delete();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(26L)) {
                notifyError("acces_refuse");
                this.livraisonclient = null;
                return;
            }

            if (this.livraisonclient != null) {
                List list = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient().longValue());
                this.livraisonclient.setLignelivraisonclientList(list);
                fileName = PrintUtils.printFacture(livraisonclient, SessionMBean.getParametrage());
                RequestContext.getCurrentInstance().execute("PF('SortieDirecteImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void addArticle() {
        try {
            if (this.magasinlot == null) {
                JsfUtil.addErrorMessage(this.routine.localizeMessage("article_invalide"));
                return;
            }
            Lignelivraisonclient llc = this.lignelivraisonclient;
            llc.setIdlignelivraisonclient(0L);

            this.magasinarticle = this.magasinarticleFacadeLocal.find(this.magasinarticle.getIdmagasinarticle());
            this.magasinlot = this.magasinlotFacadeLocal.find(this.magasinlot.getIdmagasinlot());

            double q = 0.0;
            for (Lignelivraisonclient c1 : this.lignelivraisonclients) {
                if (c1.getIdmagasinlot().equals(this.magasinlot)) {
                    q += c1.getQuantitemultiple();
                }
            }

            if (llc.getQuantitemultiple() + q > this.magasinlot.getQuantitemultiple()) {
                JsfUtil.addErrorMessage(this.routine.localizeMessage("quantite_debordee"));
                return;
            }

            Unite u = this.unite;
            llc.setIdunite(u);
            llc.setIdmagasinlot(this.magasinlot);

            this.lignelivraisonclients.add(llc);
            llc.setPrixAchat(this.magasinlot.getIdmagasinarticle().getIdarticle().getCoutachat());
            llc.setPrixVente(this.magasinlot.getIdmagasinarticle().getIdarticle().getPrixunit());
            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));

            updateTotal();
            this.lignelivraisonclient = new Lignelivraisonclient();
            this.magasinarticle = new Magasinarticle();
        } catch (Exception e) {
            e.printStackTrace();
            this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("formulaire_incomplet"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void removeArticle(int index) {
        try {
            boolean trouve = false;
            this.ut.begin();

            Lignelivraisonclient c1 = (Lignelivraisonclient) this.lignelivraisonclients.get(index);
            if (c1.getIdlignelivraisonclient() != 0L) {
                trouve = true;
                this.lignelivraisonclientFacadeLocal.remove(c1);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'article : " + c1.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getLibelle() + " quantité : " + c1.getQuantite() + " dans la sortie : " + this.livraisonclient.getCode(), SessionMBean.getUserAccount());

                Magasinarticle pro = this.magasinarticleFacadeLocal.find(c1.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                pro.setQuantite((pro.getQuantite() + c1.getQuantite()));
                this.magasinarticleFacadeLocal.edit(pro);
            }
            this.lignelivraisonclients.remove(index);

            updateTotal();
            if (trouve) {
                this.livraisonclientFacadeLocal.edit(this.livraisonclient);
            }
            this.ut.commit();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal() {
        Double resultatTotal = 0.0;
        double marge = 0.0;
        int i = 0;
        for (Lignelivraisonclient llc : this.lignelivraisonclients) {
            this.lignelivraisonclients.get(i).setQuantitemultiple((llc.getUnite() * llc.getQuantite()));
            Double resultat = llc.getPrixUnitaire() * this.lignelivraisonclients.get(i).getQuantitemultiple();
            resultatTotal += resultat;
            this.lignelivraisonclients.get(i).setQuantitereduite(this.lignelivraisonclients.get(i).getQuantitemultiple() / llc.getIdmagasinlot().getIdlot().getIdarticle().getUnite());
            this.lignelivraisonclients.get(i).setMontant(resultat);
            lignelivraisonclients.get(i).setMarge(((llc.getPrixUnitaire() - llc.getPrixAchat()) * lignelivraisonclients.get(i).getQuantitemultiple()));
            if (lignelivraisonclients.get(i).getMarge() < 0) {
                lignelivraisonclients.get(i).setMarge(0);
            }
            marge += lignelivraisonclients.get(i).getMarge();
            i++;
        }
        livraisonclient.setMarge(marge);
        return resultatTotal;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal();
            this.livraisonclient.setMontant(this.total);
            this.livraisonclient.setMontantRemise((total * livraisonclient.getTauxRemise()) / 100);
            this.livraisonclient.setMontantHt((total - livraisonclient.getMontantRemise()));
            this.livraisonclient.setMontantTva((livraisonclient.getMontantHt() * livraisonclient.getTauxTva()) / 100);
            this.livraisonclient.setMontantTtc(this.livraisonclient.getMontantTva() + this.livraisonclient.getMontantHt());
            this.livraisonclient.setMarge(livraisonclient.getMarge() - ((livraisonclient.getMarge() * livraisonclient.getTauxRemise()) / 100));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            this.lignelivraisonclient.setMontant(0.0);
            if ((this.lignelivraisonclient.getQuantite() != null) && (this.lignelivraisonclient.getMontant() != null)) {
                if (this.lignelivraisonclient.getUnite() != null) {
                    this.lignelivraisonclient.setQuantitemultiple((this.lignelivraisonclient.getUnite() * this.lignelivraisonclient.getQuantite()));
                    this.lignelivraisonclient.setQuantitereduite((this.lignelivraisonclient.getQuantitemultiple() / this.magasinarticle.getIdarticle().getUnite()));
                }
                this.lignelivraisonclient.setMontant(this.lignelivraisonclient.getPrixUnitaire() * this.lignelivraisonclient.getQuantite());
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.lignelivraisonclient.setMontant(0.0);
        }
    }

    public void updatedata() {
        try {
            if (this.magasinarticle != null) {
                this.magasinlot = new Magasinlot();
                this.famille = this.magasinarticle.getIdarticle().getIdfamille();

                this.lignelivraisonclient.setMontant(this.magasinarticle.getIdarticle().getPrixunit());
                this.lignelivraisonclient.setUnite(this.magasinarticle.getIdarticle().getUnite());
                this.unite = this.magasinarticle.getIdarticle().getIdunite();

                this.magasinlots = this.magasinlotFacadeLocal.findByArticleIsavailable(this.magasin.getIdmagasin(), this.magasinarticle.getIdarticle().getIdarticle(), this.magasinarticle.getIdarticle().getPerissable(), new Date());

                if (Objects.equals(this.magasinlots.size(), 0)) {
                    this.magasinlot = null;
                    return;
                }
                if (Objects.equals(this.magasinlots.size(), 1)) {
                    this.magasinlot = (this.magasinlots.get(0));
                    this.lignelivraisonclient.setQuantite(1.0);
                    this.lignelivraisonclient.setPrixUnitaire(this.magasinlot.getIdlot().getPrixunitaire());
                    this.lignelivraisonclient.setMontant(this.magasinlot.getIdlot().getPrixunitaire());
                    this.lignelivraisonclient.setUnite(this.magasinlot.getIdlot().getIdarticle().getUnite());
                    this.lignelivraisonclient.setQuantitemultiple(1.0);
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatedataLot() {
        try {
            if (this.magasinlot != null) {
                this.lignelivraisonclient.setPrixUnitaire(this.magasinlot.getIdlot().getPrixunitaire());
                this.lignelivraisonclient.setMontant(this.magasinlot.getIdlot().getPrixunitaire());
                this.lignelivraisonclient.setQuantite(1.0);
                this.lignelivraisonclient.setUnite(this.magasinlot.getIdlot().getIdarticle().getUnite());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
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
