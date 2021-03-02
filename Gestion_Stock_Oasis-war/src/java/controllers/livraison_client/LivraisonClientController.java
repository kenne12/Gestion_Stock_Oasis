package controllers.livraison_client;

import entities.Client;
import entities.Demande;
import entities.Lignedemande;
import entities.Lignelivraisonclient;
import entities.Lignemvtstock;
import entities.Livraisonclient;
import entities.Lot;
import entities.Magasinarticle;
import entities.Magasinlot;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
public class LivraisonClientController extends AbstractLivraisonClientController implements Serializable {

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    @PostConstruct
    private void init() {
        this.unites = this.uniteFacadeLocal.findAllRange();
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(41L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').show()");
            this.mode = "Create";

            this.livraisonclient = new Livraisonclient();
            this.client = new Client();

            this.livraisonclient.setMontant(0.0);
            this.lignelivraisonclients.clear();

            this.demande = new Demande();
            this.lignedemandes.clear();
            this.showSelectorCommand = false;

            this.total = 0.0;
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        try {
            this.demande = new Demande();
            this.demandes = this.demandeFacadeLocal.findByValidee(false);
            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.livraisonclient == null) {
                notifyError("not_row_selected");
                return;
            }

            if (!this.livraisonclient.getLivraisondirecte()) {
                this.showSelectorCommand = true;
                this.demande = this.livraisonclient.getIddemande();

                if (this.demande.getValidee()) {
                    notifyError("demande_deja_livree");
                    return;
                }

                if (!Utilitaires.isAccess(41L)) {
                    notifyError("acces_refuse");
                    this.demande = null;
                    return;
                }

                this.mode = "Edit";

                this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());
                this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                this.client = this.livraisonclient.getIddemande().getClient();
                this.total = this.livraisonclient.getMontant();
                RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').show()");
            } else {
                notifyError("vente_directe");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void afficherDemande() {
        this.demandes_1 = this.demandeFacadeLocal.findAllRange();
    }

    public void prepareview() {
        try {
            if (this.livraisonclient != null) {
                this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                if (!this.lignelivraisonclients.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('LivraisonClientDetailDialog').show()");
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

    public void prepareviewD() {
        try {
            if (this.demande != null) {
                this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());
                if (!this.lignedemandes.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('DemandeDetailDialog').show()");
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

    public void selectCommande() {
        try {
            if (this.demande != null) {
                this.lignelivraisonclients.clear();

                this.client = this.demande.getClient();
                this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());

                this.demande.setDateeffectlivraison(new Date());
                livraisonclient.setTauxTva(demande.getTauxTva());
                livraisonclient.setTauxRemise(demande.getTauxRemise());
                int conteur = 0;
                Double pourcentage = 0d;
                for (Lignedemande lcc : this.lignedemandes) {
                    boolean suffisant = false;
                    Double qteDemandee = lcc.getQuantitemultiple();
                    Double qteReste = lcc.getQuantitemultiple();

                    double prixVente = lcc.getPrixVente();
                    double prixAchat = lcc.getPrixAchat();

                    List<Magasinlot> lotTemp = this.magasinlotFacadeLocal.findByArticleIsavailable(lcc.getIdmagasinarticle().getIdmagasin().getIdmagasin(), lcc.getIdmagasinarticle().getIdarticle().getIdarticle());

                    Double sommeQte = 0.0;
                    if (!lotTemp.isEmpty()) {
                        Magasinlot lSearch = lotTemp.get(0);
                        if (lSearch != null) {
                            if (lSearch.getQuantitemultiple() > 0d) {
                                Lignelivraisonclient c = new Lignelivraisonclient();
                                c.setIdlignelivraisonclient(0L);
                                c.setPrixUnitaire(lcc.getPrixUnitaire());
                                c.setPrixAchat(prixAchat);
                                c.setPrixVente(prixVente);

                                c.setIdmagasinlot(lSearch);
                                c.setIdlot(lSearch.getIdlot());
                                c.setIdunite(lcc.getIdunite());
                                c.setUnite(lcc.getUnite());
                                if (lSearch.getQuantitemultiple() >= lcc.getQuantitemultiple()) {
                                    c.setQuantite(lcc.getQuantite());
                                    c.setQuantitemultiple(c.getQuantite() * lcc.getUnite());
                                    c.setQuantitereduite((c.getQuantitemultiple() / c.getIdmagasinlot().getIdlot().getIdarticle().getUnite()));
                                    c.setMontant(lcc.getMontant());
                                    this.lignelivraisonclients.add(c);
                                    qteReste = 0.0;
                                    sommeQte = (sommeQte + lcc.getQuantitemultiple());
                                    suffisant = true;
                                } else {
                                    c.setQuantitemultiple(lSearch.getQuantitemultiple());
                                    c.setQuantite((c.getQuantitemultiple() / lSearch.getUnite()));
                                    c.setQuantitereduite((c.getQuantitemultiple() / c.getIdmagasinlot().getIdlot().getIdarticle().getUnite()));
                                    c.setMontant(c.getPrixUnitaire() * c.getQuantitemultiple());
                                    qteReste = (qteDemandee - lSearch.getQuantitemultiple());
                                    this.lignelivraisonclients.add(c);
                                    sommeQte = (sommeQte + lSearch.getQuantitemultiple());
                                    suffisant = false;
                                }
                                c.setMarge((c.getPrixUnitaire() - c.getPrixAchat()) * c.getQuantitemultiple());
                            } else {
                                suffisant = false;
                            }
                        }

                        if (!suffisant) {
                            for (Magasinlot l : lotTemp) {
                                if (!l.getIdlot().equals(lotTemp)) {
                                    if (l.getQuantitemultiple() > 0.0) {
                                        Lignelivraisonclient c = new Lignelivraisonclient();
                                        c.setIdlignelivraisonclient(0L);
                                        c.setPrixUnitaire(lcc.getPrixUnitaire());
                                        c.setPrixAchat(prixAchat);
                                        c.setPrixVente(prixVente);
                                        c.setIdmagasinlot(l);
                                        c.setIdlot(l.getIdlot());
                                        c.setIdunite(lcc.getIdunite());
                                        c.setUnite(lcc.getUnite());
                                        if (l.getQuantitemultiple() >= qteReste) {
                                            c.setQuantitemultiple(qteReste);
                                            c.setMontant(c.getPrixUnitaire() * c.getQuantitemultiple());
                                            c.setQuantite((qteReste / l.getUnite()));
                                            c.setQuantitereduite((l.getQuantitemultiple() / l.getIdlot().getIdarticle().getUnite()));
                                            this.lignelivraisonclients.add(c);
                                            sommeQte += qteReste;
                                            suffisant = true;
                                            break;
                                        }
                                        qteReste -= l.getQuantitemultiple();
                                        c.setQuantitemultiple(l.getQuantitemultiple());
                                        c.setQuantite((l.getQuantitemultiple() / l.getUnite()));
                                        c.setQuantitereduite((l.getQuantitemultiple() / l.getIdlot().getIdarticle().getUnite()));
                                        this.lignelivraisonclients.add(c);
                                        sommeQte += l.getQuantitemultiple();
                                        suffisant = false;
                                        c.setMarge((c.getPrixUnitaire() - c.getPrixAchat()) * c.getQuantitemultiple());
                                    } else {
                                        suffisant = false;
                                    }
                                }
                            }
                        }
                    }
                    Utilitaires.arrondiNDecimales(sommeQte / qteDemandee * 100.0, 2);
                    this.lignedemandes.get(conteur).setTauxsatisfaction(Utilitaires.arrondiNDecimales(sommeQte / qteDemandee * 100.0, 2));
                    pourcentage += pourcentage + Utilitaires.arrondiNDecimales(sommeQte / qteDemandee * 100.0, 2);
                    conteur++;
                }
                this.demande.setTauxsatisfaction(pourcentage);
                updateTotal();
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Lignedemande searchByMagasinLotInLigneDemande(Magasinlot ml) {
        for (Lignedemande ld : this.lignedemandes) {
            if (ld.getIdmagasinarticle().equals(ml.getIdmagasinarticle())) {
                return ld;
            }
        }
        return null;
    }

    public void create() {
        try {
            if ("Create".equals(this.mode)) {
                if (!this.lignelivraisonclients.isEmpty()) {
                    updateTotal();

                    for (Lignedemande ld : this.lignedemandes) {
                        Double somme = 0.0D;
                        for (Lignelivraisonclient llc : this.lignelivraisonclients) {
                            if ((ld.getIdmagasinarticle().getIdarticle().equals(llc.getIdlot().getIdarticle())) && (ld.getIdmagasinarticle().getIdmagasin().equals(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasin()))) {
                                somme = (somme + llc.getQuantitemultiple());
                            }
                        }

                        if (somme > ld.getQuantitemultiple()) {
                            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                            JsfUtil.addErrorMessage("Les quantités accordées sont superieurs à celles demandées");
                            return;
                        }

                        if (somme > ld.getIdmagasinarticle().getQuantitemultiple()) {
                            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                            JsfUtil.addErrorMessage("Les quantités accordées sont superieurs à celles disponible en stock");
                            return;
                        }
                    }

                    String message = "";
                    updateTotal();
                    this.ut.begin();

                    this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    String codeMvt = "MVT";
                    codeMvt = Utilitaires.genererCodeStock(codeMvt, this.mvtstock.getIdmvtstock());
                    this.mvtstock.setCode(codeMvt);

                    this.mvtstock.setDatemvt(this.demande.getDateeffectlivraison());
                    this.mvtstock.setClient(this.demande.getClient().getNom() + " " + this.demande.getClient().getPrenom());
                    this.mvtstock.setFournisseur(" ");
                    this.mvtstock.setMagasin(" ");
                    this.mvtstock.setType(" ");
                    this.mvtstockFacadeLocal.create(this.mvtstock);

                    String code = "LIV";
                    this.livraisonclient.setIdlivraisonclient(this.livraisonclientFacadeLocal.nextVal());
                    code = Utilitaires.genererCodeDemande(code, this.livraisonclient.getIdlivraisonclient());
                    this.livraisonclient.setCode(code);
                    this.livraisonclient.setMontant(this.total);
                    this.livraisonclient.setLivraisondirecte(false);
                    this.livraisonclient.setDatelivraison(this.demande.getDateeffectlivraison());
                    this.livraisonclient.setIddemande(this.demande);
                    this.livraisonclient.setClient(this.demande.getClient());
                    this.livraisonclient.setIdmvtstock(this.mvtstock);
                    this.livraisonclientFacadeLocal.create(this.livraisonclient);

                    for (Lignelivraisonclient llc : this.lignelivraisonclients) {
                        llc.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());
                        llc.setIdlivraisonclient(this.livraisonclient);
                        this.lignelivraisonclientFacadeLocal.create(llc);

                        Lignedemande ldTemp = searchByMagasinLotInLigneDemande(llc.getIdmagasinlot());

                        Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        maTemp.setQuantite((maTemp.getQuantite() - llc.getQuantite()));
                        maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - llc.getQuantitemultiple()));
                        maTemp.setQuantitereduite((maTemp.getQuantitereduite() - llc.getQuantitereduite()));
                        maTemp.setQuantitevirtuelle((maTemp.getQuantitevirtuelle() - ldTemp.getQuantitemultiple()));
                        this.magasinarticleFacadeLocal.edit(maTemp);

                        Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot());
                        double qteAvant = mlTemp.getQuantitemultiple();
                        mlTemp.setQuantite((mlTemp.getQuantite() - llc.getQuantite()));
                        mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - llc.getQuantitemultiple()));
                        mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - llc.getQuantitereduite()));
                        this.magasinlotFacadeLocal.edit(mlTemp);

                        Lignemvtstock lmvts = new Lignemvtstock();
                        lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        lmvts.setIdmvtstock(this.mvtstock);
                        lmvts.setIdlot(llc.getIdmagasinlot().getIdlot());
                        lmvts.setQtesortie(llc.getQuantitemultiple());
                        lmvts.setQteentree(0.0);
                        lmvts.setQteAvant(qteAvant);
                        lmvts.setReste(mlTemp.getQuantitemultiple());
                        lmvts.setIdmagasinlot(llc.getIdmagasinlot());
                        lmvts.setUnite(llc.getUnite());
                        lmvts.setMagasin(" ");
                        lmvts.setClient(this.demande.getClient().getNom() + " " + this.demande.getClient().getPrenom());
                        lmvts.setFournisseur(" ");
                        lmvts.setType("SORTIE");
                        this.lignemvtstockFacadeLocal.create(lmvts);
                    }
                    this.demande.setValidee(true);
                    this.demandeFacadeLocal.edit(this.demande);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Validation de la commande  N° : " + this.livraisonclient.getIddemande().getCode(), SessionMBean.getUserAccount());

                    this.ut.commit();
                    this.demande = new Demande();
                    this.lignedemandes.clear();
                    this.lignelivraisonclients.clear();
                    this.detail = this.supprimer = this.modifier = this.imprimer = true;
                    JsfUtil.addSuccessMessage(message);

                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').hide()");
                } else {
                    notifyError("liste_article_vide");
                }
            } else if (this.livraisonclient != null) {
                this.ut.commit();
                this.lignedemandes.clear();
                this.demandes.clear();
                this.demande = new Demande();
                this.livraisonclient = null;
                this.supprimer = this.modifier = this.imprimer = detail = true;

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('LignelivraisonclientCreateDialog').hide()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.livraisonclient != null) {
                if (!this.livraisonclient.getLivraisondirecte()) {
                    if (!Utilitaires.isAccess(41L)) {
                        notifyError("acces_refuse");
                        this.detail = this.supprimer = this.modifier = this.imprimer = true;
                        this.livraisonclient = null;
                        return;
                    }

                    this.ut.begin();

                    List<Lignelivraisonclient> temp = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());

                    if (!temp.isEmpty()) {
                        for (Lignelivraisonclient llc : temp) {

                            llc.setIdmagasinlot(this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot()));

                            llc.getIdmagasinlot().setQuantite(llc.getIdmagasinlot().getQuantite() + llc.getQuantite());
                            llc.getIdmagasinlot().setQuantitemultiple(llc.getIdmagasinlot().getQuantitemultiple() + llc.getQuantitemultiple());
                            llc.getIdmagasinlot().setQuantitereduite(llc.getIdmagasinlot().getQuantitereduite() + llc.getQuantitereduite());
                            llc.getIdmagasinlot().setQuantitevirtuelle(llc.getIdmagasinlot().getQuantitevirtuelle() + llc.getQuantitemultiple());

                            llc.getIdmagasinlot().getIdmagasinarticle().setQuantite(llc.getIdmagasinlot().getIdmagasinarticle().getQuantite() + llc.getQuantite());
                            llc.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple(llc.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple() + llc.getQuantitemultiple());
                            llc.getIdmagasinlot().getIdmagasinarticle().setQuantitereduite(llc.getIdmagasinlot().getIdmagasinarticle().getQuantitereduite() + llc.getQuantitereduite());

                            this.magasinlotFacadeLocal.edit(llc.getIdmagasinlot());
                            this.magasinarticleFacadeLocal.edit(llc.getIdmagasinlot().getIdmagasinarticle());

                            this.lignelivraisonclientFacadeLocal.remove(llc);
                        }
                    }
                    Lignelivraisonclient llc;
                    this.livraisonclientFacadeLocal.remove(this.livraisonclient);

                    this.demande = this.livraisonclient.getIddemande();

                    this.demande.setValidee(false);
                    this.demande.setTauxsatisfaction(0.0);
                    this.demandeFacadeLocal.edit(this.demande);

                    List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.livraisonclient.getIdmvtstock().getIdmvtstock());
                    for (Lignemvtstock l : lmvt) {
                        this.lignemvtstockFacadeLocal.remove(l);
                    }

                    this.mvtstockFacadeLocal.remove(this.livraisonclient.getIdmvtstock());

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de validation de la demande : " + this.livraisonclient.getCode(), SessionMBean.getUserAccount());
                    this.ut.commit();

                    this.livraisonclient = new Livraisonclient();
                    this.demande = new Demande();
                    this.supprimer = this.modifier = this.imprimer = this.detail = true;
                    notifySuccess();
                } else {
                    notifyError("vente_directe");
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

    public void initPrinterBon(Livraisonclient l) {
        this.livraisonclient = l;
        printBonLivraison();
    }

    public void initEdit(Livraisonclient l) {
        this.livraisonclient = l;
        prepareEdit();
    }

    public void initViewV(Demande d) {
        Livraisonclient lc = this.livraisonclientFacadeLocal.findByIddemande(d.getIddemande());
        this.livraisonclient = lc;
        prepareview();
    }

    public void initView(Livraisonclient l) {
        this.livraisonclient = l;
        prepareview();
    }

    public void initViewD(Demande d) {
        this.demande = d;
        prepareviewD();
    }

    public void initDelete(Livraisonclient l) {
        this.livraisonclient = l;
        delete();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(42L)) {
                notifyError("acces_refuse");
                this.livraisonclient = null;
                return;
            }

            if (this.livraisonclient != null) {
                this.printDialogTitle = this.routine.localizeMessage("livraisonclient");
                List list = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient().longValue());
                this.livraisonclient.setLignelivraisonclientList(list);
                fileName = PrintUtils.printFacture(livraisonclient, SessionMBean.getParametrage());
                RequestContext.getCurrentInstance().execute("PF('LivraisonClientImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printBonLivraison() {
        try {
            if (!Utilitaires.isAccess((54L))) {
                notifyError("acces_refuse");
                this.livraisonclient = null;
                return;
            }

            if (this.livraisonclient != null) {
                this.printDialogTitle = this.routine.localizeMessage("bon_de_livraison");
                RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void addArticle() {
        try {
            Lignedemande l = this.lignedemande;

            boolean drapeau = false;
            int i = 0;
            for (Lignedemande lcc : this.lignedemandes) {
                i++;
            }

            if (!drapeau) {
                this.lignedemandes.add(l);
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
                this.lignedemande = new Lignedemande();

                return;
            }
            JsfUtil.addErrorMessage("Article existant dans la lignelivraisonclient");
            updateTotal();
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

            Lignedemande lcc = (Lignedemande) this.lignedemandes.get(index);

            this.lignedemandes.remove(index);

            updateTotal();
            if (trouve) {
                this.demandeFacadeLocal.edit(this.demande);
            }
            this.ut.commit();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal(List<Lignelivraisonclient> lignelivraisonclients) {
        Double resultatTotal = 0.0;
        double marge = 0;
        int i = 0;
        for (Lignelivraisonclient llc : lignelivraisonclients) {
            lignelivraisonclients.get(i).setQuantitemultiple((llc.getQuantite() * llc.getIdlot().getIdarticle().getUnite()));
            Double somme = (llc.getPrixUnitaire() * lignelivraisonclients.get(i).getQuantite());
            resultatTotal += somme;
            lignelivraisonclients.get(i).setMontant(somme);
            lignelivraisonclients.get(i).setMarge((llc.getPrixUnitaire() - llc.getPrixAchat()) * lignelivraisonclients.get(i).getQuantitemultiple());
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
            this.total = calculTotal(this.lignelivraisonclients);
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

    public void updatedataLot() {
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
