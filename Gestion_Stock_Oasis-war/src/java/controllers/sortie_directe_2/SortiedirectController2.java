package controllers.sortie_directe_2;

import entities.Client;
import entities.Famille;
import entities.Lignelivraisonclient;
import entities.Lignemvtstock;
import entities.Livraisonclient;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import enumeration.ModeEntreSorti;
import enumeration.ModePayement;
import java.io.Serializable;
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
public class SortiedirectController2 extends AbstractSortiedirectController2 implements Serializable {

    @PostConstruct
    private void init() {
        this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin());
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

            if (!Utilitaires.isAccess(27L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('SortieDirecteCreateDialog').show()");
            this.mode = "Create";
            this.client = new Client();
            this.livraisonclient = new Livraisonclient();
            this.livraisonclient.setDatelivraison(SessionMBean.getJournee().getDateJour());
            this.livraisonclient.setModePayement(ModePayement.PAYE_COMPTANT);
            this.livraisonclient.setMontant(0d);
            this.livraisonclient.setTauxRemise(SessionMBean.getParametrage().getTauxRemise());
            this.livraisonclient.setTauxTva(SessionMBean.getParametrage().getTauxTva());
            clientToSave = new Client();

            this.lignelivraisonclients.clear();
            this.mvtstock = new Mvtstock();
            this.total = 0d;
            if (!this.clients.isEmpty()) {
                this.client = this.clients.get(0);
            }
            this.magasinlots = this.magasinlotFacadeLocal.findByIdMagasinAndEtatGtZero(this.magasin.getIdmagasin(), true);
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
        this.lignelivraisonclient = new Lignelivraisonclient();
        this.lignelivraisonclient.setModeVente(SessionMBean.getMagasin().getModeSortiDefault());
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

                if (!Utilitaires.isAccess(27L)) {
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
            this.magasinlots.clear();
            this.magasinlot = new Magasinlot();
            this.lignelivraisonclient = new Lignelivraisonclient();
            if (this.famille.getIdfamille() != null) {
                this.magasinlots = magasinlotFacadeLocal.findByIdmagasinAndFamilleAndEtatGtzero(this.magasin.getIdmagasin(), this.famille.getIdfamille(), true);
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

                if (livraisonclient.getModePayement().equals("PAYE_A_CREDIT")) {
                    livraisonclient.setPaye(false);
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

                String code = "F-" + SessionMBean.getAnnee().getNom() + "-" + SessionMBean.getMois().getIdmois().getNom().toUpperCase().substring(0, 3);
                Long nextFacture = this.livraisonclientFacadeLocal.nextVal(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois());
                code = Utilitaires.genererCodeFacture(code, nextFacture);

                this.livraisonclient.setCode(code);

                this.livraisonclient.setClient(this.client);
                this.livraisonclient.setIdmagasin(this.magasin);
                this.livraisonclient.setMontant(this.total);
                this.livraisonclient.setLivraisondirecte(true);
                this.livraisonclient.setIdUtilisateur(SessionMBean.getUserAccount().getIdutilisateur());

                this.livraisonclient.setIdmvtstock(this.mvtstock);
                if (livraisonclient.getModePayement().equals("PAYE_COMPTANT")) {
                    livraisonclient.setAvanceInitiale(livraisonclient.getMontantTtc());
                    livraisonclient.setMontantPaye(livraisonclient.getMontantTtc());
                    livraisonclient.setReste(0);
                    livraisonclient.setPaye(true);
                } else {
                    if (livraisonclient.getAvanceInitiale() < 0) {
                        livraisonclient.setAvanceInitiale(0);
                    }
                    livraisonclient.setMontantPaye(livraisonclient.getAvanceInitiale());
                    livraisonclient.setReste(livraisonclient.getMontantTtc() - livraisonclient.getAvanceInitiale());
                }
                this.livraisonclient.setIdmagasin(SessionMBean.getMagasin());
                this.livraisonclient.setIdlivraisonclient(livraisonclientFacadeLocal.nextValue());
                this.livraisonclientFacadeLocal.create(this.livraisonclient);

                for (Lignelivraisonclient llc : this.lignelivraisonclients) {

                    llc.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());
                    llc.setIdlivraisonclient(this.livraisonclient);
                    llc.setQuantitereduite(llc.getQuantitereduite());
                    llc.setIdlot(llc.getIdmagasinlot().getIdlot());
                    llc.setTauxsatisfaction(0d);
                    if (llc.getModeVente().equals("VENTE_EN_DETAIL")) {
                        llc.setPrixAchat(llc.getIdmagasinlot().getIdlot().getIdarticle().getPrixAchatDetail());
                        llc.setPrixVente(llc.getIdmagasinlot().getIdlot().getIdarticle().getPrixVenteDetail());
                    }
                    this.lignelivraisonclientFacadeLocal.create(llc);

                    Magasinlot ml = this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot());
                    double qteAvant = ml.getQuantitereduite();
                    this.updateMagasinLot(ml, llc.getQuantitereduite(), llc.getQuantitemultiple(), "-");

                    Magasinarticle ma = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                    this.updateMagasinArticle(ma, llc.getQuantitereduite(), llc.getQuantitemultiple(), "-");

                    Lignemvtstock lmvts = new Lignemvtstock();
                    lmvts.setIdlignemvtstock(this.ligneMvtstockFacadeLocal.nextVal());
                    lmvts.setIdmvtstock(this.mvtstock);
                    lmvts.setIdlot(llc.getIdmagasinlot().getIdlot());
                    lmvts.setIdmagasinlot(llc.getIdmagasinlot());

                    lmvts.setQteentree(0d);
                    lmvts.setQteAvant(qteAvant);
                    lmvts.setQtesortie(llc.getQuantitereduite());
                    lmvts.setReste(ml.getQuantitereduite());

                    lmvts.setType("SORTIE");
                    lmvts.setClient(this.client.getNom() + " " + this.client.getPrenom());
                    lmvts.setFournisseur(this.magasin.getNom());
                    lmvts.setMagasin(this.magasin.getNom());
                    lmvts.setLignelivraisonclient(llc);
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
                            Lignelivraisonclient llcOld = this.lignelivraisonclientFacadeLocal.find(llc.getIdlignelivraisonclient());
                            if (!Objects.equals(llc.getQuantite(), llcOld.getQuantite())) {

                                Lignemvtstock lmvts = null;
                                lmvts = ligneMvtstockFacadeLocal.findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(), llc.getIdlot().getIdlot(), llc.getIdlignelivraisonclient());
                                if (lmvts == null) {
                                    lmvts = ligneMvtstockFacadeLocal.findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(), llc.getIdlot().getIdlot());
                                }

                                double diffReduite = llc.getQuantitereduite() - llcOld.getQuantitereduite();
                                double diffMultiple = llc.getQuantitemultiple() - llcOld.getQuantitemultiple();

                                Magasinarticle ma = this.magasinarticleFacadeLocal.find(llcOld.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                                Magasinlot ml = this.magasinlotFacadeLocal.find(llcOld.getIdmagasinlot().getIdmagasinlot());
                                if (diffReduite > 0) {
                                    updateMagasinArticle(ma, diffReduite, diffMultiple, "-");
                                    this.updateMagasinLot(ml, diffReduite, diffMultiple, "-");
                                    lmvts.setReste(lmvts.getReste() - diffReduite);
                                } else {
                                    updateMagasinArticle(ma, Math.abs(diffReduite), Math.abs(diffMultiple), "+");
                                    this.updateMagasinLot(ml, Math.abs(diffReduite), Math.abs(diffMultiple), "+");
                                    lmvts.setReste(lmvts.getReste() + Math.abs(diffReduite));
                                }

                                lmvts.setQtesortie(llc.getQuantitereduite());
                                this.ligneMvtstockFacadeLocal.edit(lmvts);
                            }
                            this.lignelivraisonclientFacadeLocal.edit(llc);
                        } else {
                            llc.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());
                            llc.setIdlivraisonclient(this.livraisonclient);
                            this.lignelivraisonclientFacadeLocal.create(llc);

                            Magasinarticle ma = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                            this.updateMagasinArticle(ma, llc.getQuantitereduite(), llc.getQuantitemultiple(), "-");

                            Magasinlot ml = this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot());
                            ml = this.updateMagasinLot(ml, llc.getQuantitereduite(), llc.getQuantitemultiple(), "-");

                            Lignemvtstock lmvts = new Lignemvtstock();
                            lmvts.setIdlignemvtstock(this.ligneMvtstockFacadeLocal.nextVal());
                            lmvts.setIdmvtstock(this.mvtstock);
                            lmvts.setIdlot(llc.getIdmagasinlot().getIdlot());
                            lmvts.setIdmagasinlot(llc.getIdmagasinlot());

                            lmvts.setQteentree(0.0);
                            lmvts.setQtesortie(llc.getQuantitereduite());
                            lmvts.setReste(ml.getQuantitereduite());
                            lmvts.setQteAvant(ml.getQuantitereduite() + llc.getQuantitereduite());

                            lmvts.setClient(this.client.getNom() + " " + this.client.getPrenom());
                            lmvts.setFournisseur(this.magasin.getNom());
                            lmvts.setMagasin(this.magasin.getNom());
                            lmvts.setLignelivraisonclient(llc);
                            this.ligneMvtstockFacadeLocal.create(lmvts);
                        }
                    }
                }

                this.ut.commit();
                this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin());
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

    private Magasinarticle updateMagasinArticle(Magasinarticle magasinarticle, double qteReduite, double qteMultiple, String signe) {
        if (signe.equals("-")) {
            magasinarticle.setQuantitemultiple(magasinarticle.getQuantitemultiple() - qteMultiple);
            magasinarticle.setQuantitereduite((magasinarticle.getQuantitereduite() - qteReduite));
            magasinarticle.setQuantite((magasinarticle.getQuantite() - qteReduite));
        } else {
            magasinarticle.setQuantitemultiple(magasinarticle.getQuantitemultiple() + qteMultiple);
            magasinarticle.setQuantitereduite((magasinarticle.getQuantitereduite() + qteReduite));
            magasinarticle.setQuantite((magasinarticle.getQuantite() + qteReduite));
        }
        this.magasinarticleFacadeLocal.edit(magasinarticle);
        return magasinarticle;
    }

    public Magasinlot updateMagasinLot(Magasinlot magasinlot, double qteReduite, double qteMultiple, String signe) {
        if (signe.equals("+")) {
            magasinlot.setQuantitemultiple(magasinlot.getQuantitemultiple() + qteMultiple);
            magasinlot.setQuantitereduite((magasinlot.getQuantitereduite() + qteReduite));
            magasinlot.setQuantite((magasinlot.getQuantite() + qteReduite));
        } else {
            magasinlot.setQuantitemultiple(magasinlot.getQuantitemultiple() - qteMultiple);
            magasinlot.setQuantitereduite((magasinlot.getQuantitereduite() - qteReduite));
            magasinlot.setQuantite((magasinlot.getQuantite() - qteReduite));
        }
        this.magasinlotFacadeLocal.edit(magasinlot);
        return magasinlot;
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
            if (this.livraisonclient != null && this.livraisonclient.getIdlivraisonclient() != null) {
                if (this.livraisonclient.getLivraisondirecte()) {

                    if (Utilitaires.isDayClosed()) {
                        notifyError("journee_cloturee");
                        return;
                    }

                    if (!Utilitaires.isAccess(27L)) {
                        notifyError("acces_refuse");
                        this.supprimer = this.modifier = this.imprimer = true;
                        this.livraisonclient = new Livraisonclient();
                        return;
                    }

                    this.ut.begin();

                    List<Lignelivraisonclient> temp = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                    if (!temp.isEmpty()) {
                        for (Lignelivraisonclient llc : temp) {

                            Magasinarticle ma = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                            this.updateMagasinArticle(ma, llc.getQuantitereduite(), llc.getQuantitemultiple(), "+");

                            Magasinlot ml = this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot());
                            this.updateMagasinLot(ml, llc.getQuantitereduite(), llc.getQuantitemultiple(), "+");
                            this.lignelivraisonclientFacadeLocal.remove(llc);

                        }
                    }
                    this.livraisonclientFacadeLocal.deleteLivraison(livraisonclient.getIdlivraisonclient());

                    Mvtstock mTemp = this.livraisonclient.getIdmvtstock();
                    ligneMvtstockFacadeLocal.deleteByIdmvt(mTemp.getIdmvtstock());
                    this.mvtstockFacadeLocal.remove(mTemp);

                    this.ut.commit();
                    this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin());
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de la facture : " + this.livraisonclient.getCode() + " Montant : " + this.livraisonclient.getMontant() + " Client : " + this.livraisonclient.getClient().getNom() + " " + this.livraisonclient.getClient().getPrenom(), SessionMBean.getUserAccount());
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
            Magasinlot ml = magasinlotFacadeLocal.find(magasinlot.getIdmagasinlot());
            Lignelivraisonclient llcToAdd = this.lignelivraisonclient;
            llcToAdd.setIdmagasinlot(ml);
            llcToAdd.setIdlignelivraisonclient(0L);

            if (llcToAdd.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                llcToAdd.setQuantitemultiple(llcToAdd.getQuantite() * ml.getIdlot().getIdarticle().getUnite());
                llcToAdd.setQuantitereduite(llcToAdd.getQuantite());
                llcToAdd.setUnite(ml.getIdlot().getIdarticle().getUnite());
            } else {
                llcToAdd.setQuantitemultiple(llcToAdd.getQuantite());
                llcToAdd.setQuantitereduite(llcToAdd.getQuantite() / ml.getIdlot().getIdarticle().getUnite());
                llcToAdd.setUnite(1d);
            }

            double q = 0d;
            for (Lignelivraisonclient l1c : lignelivraisonclients) {
                if (l1c.getIdmagasinlot().equals(ml)) {
                    if (l1c.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                        q += l1c.getQuantite();
                    } else {
                        q += (l1c.getQuantite() / l1c.getIdmagasinlot().getIdlot().getIdarticle().getUnite());
                    }
                }
            }

            if ((llcToAdd.getQuantitereduite() + q) > this.magasinlot.getQuantitereduite()) {
                JsfUtil.addErrorMessage(this.routine.localizeMessage("quantite_debordee"));
                return;
            }

            llcToAdd.setIdunite(ml.getIdlot().getIdarticle().getIdunite());
            if (llcToAdd.getModeVente().equals("VENTE_EN_DETAIL")) {
                llcToAdd.setIdunite(uniteFacadeLocal.find(ml.getIdlot().getIdarticle().getIdUniteDetail()));
            }

            this.lignelivraisonclients.add(llcToAdd);
            llcToAdd.setPrixAchat(this.magasinlot.getIdmagasinarticle().getIdarticle().getCoutachat());
            llcToAdd.setPrixVente(this.magasinlot.getIdmagasinarticle().getIdarticle().getPrixunit());
            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));

            updateTotal();
            this.lignelivraisonclient = new Lignelivraisonclient();
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

            Lignelivraisonclient llc = (Lignelivraisonclient) this.lignelivraisonclients.get(index);
            if (llc.getIdlignelivraisonclient() != 0L) {
                trouve = true;

                llc = lignelivraisonclientFacadeLocal.find(llc.getIdlignelivraisonclient());

                this.updateMagasinArticle(llc.getIdmagasinlot().getIdmagasinarticle(), llc.getQuantitereduite(), llc.getQuantitemultiple(), "+");

                //Magasinarticle pro = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                this.updateMagasinLot(llc.getIdmagasinlot(), llc.getQuantitereduite(), llc.getQuantitemultiple(), "+");

                Lignemvtstock lmvts = null;
                lmvts = ligneMvtstockFacadeLocal.findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(), llc.getIdlot().getIdlot(), llc.getIdlignelivraisonclient());
                if (lmvts == null) {
                    lmvts = ligneMvtstockFacadeLocal.findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(), llc.getIdlot().getIdlot());
                }
                try {
                    ligneMvtstockFacadeLocal.remove(lmvts);
                } catch (Exception e) {
                }

                this.lignelivraisonclientFacadeLocal.remove(llc);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'article : " + llc.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getLibelle() + " quantité : " + llc.getQuantite() + " dans la sortie : " + this.livraisonclient.getCode(), SessionMBean.getUserAccount());
            }
            this.lignelivraisonclients.remove(index);

            updateTotal();
            if (trouve) {
                //this.livraisonclientFacadeLocal.edit(this.livraisonclient);
            }
            this.ut.commit();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal() {
        Double resultatTotal = 0d;
        double marge = 0d;
        int i = 0;
        for (Lignelivraisonclient llc : this.lignelivraisonclients) {

            Double resultat = 0d;
            if (llc.getModeVente().equals("VENTE_EN_GROS")) {
                this.lignelivraisonclients.get(i).setQuantitemultiple((llc.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getUnite() * llc.getQuantite()));
                this.lignelivraisonclients.get(i).setQuantitereduite(llc.getQuantite());
                this.lignelivraisonclients.get(i).setPrixAchat(llc.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getCoutachat());
                this.lignelivraisonclients.get(i).setPrixVente(llc.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getPrixunit());
                resultat = llc.getPrixUnitaire() * this.lignelivraisonclients.get(i).getQuantite();
                lignelivraisonclients.get(i).setMarge(((llc.getPrixUnitaire() - this.lignelivraisonclients.get(i).getPrixAchat()) * lignelivraisonclients.get(i).getQuantite()));
            } else {
                this.lignelivraisonclients.get(i).setQuantitemultiple((llc.getQuantite()));
                this.lignelivraisonclients.get(i).setQuantitereduite(this.lignelivraisonclients.get(i).getQuantite() / llc.getIdmagasinlot().getIdlot().getIdarticle().getUnite());
                this.lignelivraisonclients.get(i).setPrixAchat(llc.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getPrixAchatDetail());
                this.lignelivraisonclients.get(i).setPrixVente(llc.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getPrixVenteDetail());
                resultat = llc.getPrixUnitaire() * this.lignelivraisonclients.get(i).getQuantitemultiple();
                lignelivraisonclients.get(i).setMarge(((llc.getPrixUnitaire() - this.lignelivraisonclients.get(i).getPrixAchat()) * lignelivraisonclients.get(i).getQuantitemultiple()));
            }

            resultatTotal += resultat;
            this.lignelivraisonclients.get(i).setMontant(resultat);

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
            lignelivraisonclient.setMontant(0.0);
            if ((lignelivraisonclient.getQuantite() != 0)) {
                if (lignelivraisonclient.getUnite() != 0) {
                    if (lignelivraisonclient.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                        lignelivraisonclient.setUnite(lignelivraisonclient.getIdmagasinlot().getIdlot().getIdarticle().getUnite());
                        lignelivraisonclient.setQuantitemultiple(lignelivraisonclient.getUnite() * lignelivraisonclient.getQuantite());
                        lignelivraisonclient.setMontant(lignelivraisonclient.getPrixUnitaire() * lignelivraisonclient.getQuantite());
                    } else {
                        lignelivraisonclient.setUnite(1d);
                        lignelivraisonclient.setQuantitemultiple(lignelivraisonclient.getQuantite());
                        lignelivraisonclient.setMontant(lignelivraisonclient.getPrixUnitaire() * lignelivraisonclient.getQuantite());
                    }
                    lignelivraisonclient.setQuantitereduite((lignelivraisonclient.getQuantitemultiple() / magasinlot.getIdlot().getIdarticle().getUnite()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            lignelivraisonclient.setMontant(0d);
        }
    }

    public void updatePrixGrosAndDetail() {
        if (magasinlot != null) {
            lignelivraisonclient.setQuantite(1d);
            if (lignelivraisonclient.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                lignelivraisonclient.setPrixUnitaire(magasinlot.getIdlot().getIdarticle().getPrixunit());
                lignelivraisonclient.setUnite(magasinlot.getIdlot().getIdarticle().getUnite());
                lignelivraisonclient.setPrixAchat(magasinlot.getIdlot().getIdarticle().getCoutachat());
                lignelivraisonclient.setPrixVente(magasinlot.getIdlot().getIdarticle().getPrixunit());
                lignelivraisonclient.setIdunite(magasinlot.getIdlot().getIdarticle().getIdunite());
                lignelivraisonclient.setMontant(magasinlot.getIdlot().getPrixunitaire());
                lignelivraisonclient.setQuantitemultiple(magasinlot.getIdlot().getIdarticle().getUnite());
            } else {
                lignelivraisonclient.setPrixUnitaire(magasinlot.getIdlot().getIdarticle().getPrixVenteDetail());
                lignelivraisonclient.setUnite(1d);
                lignelivraisonclient.setQuantitemultiple(1d);
                lignelivraisonclient.setMontant(magasinlot.getIdlot().getIdarticle().getPrixVenteDetail());
                lignelivraisonclient.setPrixAchat(magasinlot.getIdlot().getIdarticle().getPrixAchatDetail());
                lignelivraisonclient.setPrixVente(magasinlot.getIdlot().getIdarticle().getPrixVenteDetail());
                lignelivraisonclient.setIdunite(uniteFacadeLocal.find(magasinlot.getIdlot().getIdarticle().getIdUniteDetail()));
            }
        }
    }

    private void initModeDetail(Magasinlot ml, Lignelivraisonclient llc) {
        llc.setModeVente(ModeEntreSorti.VENTE_EN_DETAIL);
        llc.setPrixUnitaire(ml.getIdlot().getIdarticle().getPrixVenteDetail());
        llc.setUnite(1d);
        llc.setPrixAchat(ml.getIdlot().getIdarticle().getPrixAchatDetail());
        llc.setPrixVente(ml.getIdlot().getIdarticle().getPrixVenteDetail());
        llc.setQuantite(1d);
        llc.setIdunite(uniteFacadeLocal.find(ml.getIdlot().getIdarticle().getIdUniteDetail()));
        llc.setIdmagasinlot(ml);
        llc.setQuantitemultiple(1d);
        llc.setMontant(ml.getIdlot().getIdarticle().getPrixVenteDetail());
        this.famille = ml.getIdmagasinarticle().getIdarticle().getIdfamille();
    }

    private void initModeGros(Magasinlot ml, Lignelivraisonclient llc) {
        llc.setQuantite(1.0);
        this.famille = magasinlot.getIdmagasinarticle().getIdarticle().getIdfamille();
        llc.setPrixUnitaire(ml.getIdlot().getPrixunitaire());
        llc.setMontant(ml.getIdlot().getPrixunitaire());
        llc.setUnite(ml.getIdlot().getIdarticle().getUnite());
        llc.setQuantitemultiple(ml.getIdlot().getIdarticle().getUnite());
        llc.setIdunite(ml.getIdlot().getIdarticle().getIdunite());
        llc.setIdmagasinlot(ml);
        llc.setModeVente(ModeEntreSorti.VENTE_EN_GROS);
    }

    public void updatedataLot() {
        try {
            if (this.magasinlot != null) {
                if (SessionMBean.getMagasin().getModeSortiDefault().equals(ModeEntreSorti.VENTE_EN_DETAIL)) {
                    this.initModeDetail(magasinlot, lignelivraisonclient);
                } else {
                    this.initModeGros(magasinlot, lignelivraisonclient);
                }
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
