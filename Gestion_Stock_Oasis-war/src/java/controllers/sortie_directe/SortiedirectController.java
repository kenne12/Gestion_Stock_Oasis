package controllers.sortie_directe;

import entities.AnneeMois;
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
import enumeration.ModeleFacture;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
public class SortiedirectController extends AbstractSortiedirectController implements Serializable {

    @PostConstruct
    private void init() {
        this.searchMode = "MOIS";
        this.filtre(this.searchMode);
        this.reload();
    }

    public void reload() {
        this.livraisonclients = this.livraisonclientFacadeLocal
                .findAllRange(SessionMBean.getMagasin().getIdmagasin(),
                        SessionMBean.getMois().getDateDebut(), SessionMBean.getDateOuverture());
    }

    public void updateCalculTva() {
        //this.livraisonclient.computeTotals();
        stockOutputService.computeTotals(livraisonclient);
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
            this.livraisonclient.setTauxRemise(SessionMBean.getParametrage().getTauxRemise());
            this.livraisonclient.setTauxTva(SessionMBean.getParametrage().getTauxTva());
            clientToSave = new Client();

            this.mvtstock = new Mvtstock();
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

                if (!Utilitaires.isAccess(39L)) {
                    notifyError("acces_refuse");
                    this.livraisonclient = new Livraisonclient();
                    return;
                }

                this.mode = "Edit";

                if (this.livraisonclient != null) {
                    livraisonclient.getLignelivraisonclientList();
                    this.livraisonclient.setLignelivraisonclientList(this.lignelivraisonclientFacadeLocal
                            .findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient()));

                    this.client = this.livraisonclient.getClient();
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
                this.livraisonclient.getLignelivraisonclientList();
                this.livraisonclient.setLignelivraisonclientList(lignelivraisonclientFacadeLocal
                        .findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient()));
                if (!this.livraisonclient.getLignelivraisonclientList().isEmpty()) {
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
                this.magasinlots = magasinlotFacadeLocal
                        .findByIdmagasinAndFamilleAndEtatGtzero(this.magasin.getIdmagasin(), this.famille.getIdfamille(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            String message;
            if ("Create".equals(this.mode)) {
                if (this.livraisonclient.getLignelivraisonclientList().isEmpty()) {
                    notifyError("liste_article_vide");
                    return;
                }
                message = "";

                //this.livraisonclient.computeTotals();
                stockOutputService.computeTotals(livraisonclient);

                if (livraisonclient.getModePayement().equals(ModePayement.PAYE_A_CREDIT)) {
                    livraisonclient.setPaye(false);
                    if (livraisonclient.getAvanceInitiale() > livraisonclient.getMontantTtc()) {
                        notifyError("montant_avance_incorrect");
                        return;
                    }
                }

                this.ut.begin();
                this.client = this.clientFacadeLocal.find(this.client.getIdclient());

                long nextMvt = this.mvtstockFacadeLocal.nextVal();

                String codeMvt = Utilitaires.genererCodeStock("MVT", nextMvt);

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
                this.livraisonclient.setLivraisondirecte(true);
                this.livraisonclient.setIdUtilisateur(SessionMBean.getUserAccount().getIdutilisateur());

                this.livraisonclient.setIdmvtstock(this.mvtstock);
                if (livraisonclient.getModePayement().equals(ModePayement.PAYE_COMPTANT)) {
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

                // compute benefice and marges
                //this.lignelivraisonclient.compute_benef_marge();
                List<Lignelivraisonclient> lignes = new ArrayList<>();
                lignes.addAll(this.livraisonclient.getLignelivraisonclientList());
                livraisonclient.getLignelivraisonclientList().clear();
                this.livraisonclientFacadeLocal.create(this.livraisonclient);

                for (Lignelivraisonclient llc : lignes) {

                    llc.setIdlivraisonclient(this.livraisonclient);
                    llc.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());

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

                    lmvts.setQteAvant(qteAvant);
                    lmvts.setQtesortie(llc.getQuantitereduite());
                    lmvts.setReste(ml.getQuantitereduite());

                    lmvts.setType("SORTIE");
                    lmvts.setClient(this.client.getNom());
                    lmvts.setFournisseur(this.magasin.getNom());
                    lmvts.setMagasin(this.magasin.getNom());
                    lmvts.setLignelivraisonclient(llc);
                    this.ligneMvtstockFacadeLocal.create(lmvts);
                }

                RequestContext.getCurrentInstance().execute("PF('SortieDirecteCreateDialog').hide()");
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la sortie : " + code, SessionMBean.getUserAccount());

                this.ut.commit();

                this.livraisonclients = this.livraisonclientFacadeLocal
                        .findByIdmagasinAndDate(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getDateOuverture());
                this.livraisonclient = new Livraisonclient();
                this.supprimer = this.modifier = this.detail = true;
                JsfUtil.addSuccessMessage(message);
                notifySuccess();
            } else if (this.livraisonclient != null) {

                //this.livraisonclient.computeTotals();
                this.stockOutputService.computeTotals(livraisonclient);
                this.ut.begin();

                this.client = this.clientFacadeLocal.find(this.client.getIdclient());
                this.livraisonclient.setClient(this.client);
                this.livraisonclientFacadeLocal.edit(this.livraisonclient);

                if (!this.livraisonclient.getLignelivraisonclientList().isEmpty()) {
                    for (Lignelivraisonclient llc : this.livraisonclient.getLignelivraisonclientList()) {
                        if (llc.getIdlignelivraisonclient() != 0L) {
                            Lignelivraisonclient llcOld = this.lignelivraisonclientFacadeLocal.find(llc.getIdlignelivraisonclient());
                            if (!Objects.equals(llc.getQuantite(), llcOld.getQuantite())) {

                                Lignemvtstock lmvts = null;
                                lmvts = ligneMvtstockFacadeLocal
                                        .findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(),
                                                llc.getIdlot().getIdlot(), llc.getIdlignelivraisonclient());
                                if (lmvts == null) {
                                    lmvts = ligneMvtstockFacadeLocal
                                            .findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(), llc.getIdlot().getIdlot());
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
                this.livraisonclients = this.livraisonclientFacadeLocal
                        .findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin());
                this.livraisonclient = new Livraisonclient();
                this.supprimer = this.modifier = this.detail = true;

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('SortieDirecteCreateDialog').hide()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
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
            magasinlot.setQuantite(magasinlot.getQuantite() + qteReduite);
        } else {
            magasinlot.setQuantitemultiple(magasinlot.getQuantitemultiple() - qteMultiple);
            magasinlot.setQuantitereduite((magasinlot.getQuantitereduite() - qteReduite));
            magasinlot.setQuantite(magasinlot.getQuantite() - qteReduite);
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

                    if (!Utilitaires.isAccess(40L)) {
                        notifyError("acces_refuse");
                        this.supprimer = this.modifier = this.imprimer = true;
                        this.livraisonclient = new Livraisonclient();
                        return;
                    }

                    this.ut.begin();

                    List<Lignelivraisonclient> temp = this.lignelivraisonclientFacadeLocal
                            .findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
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

                    this.livraisonclients.remove(livraisonclient);

                    //this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin());
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de la facture : " + this.livraisonclient.getCode() + " Montant : " + this.livraisonclient.getMontant() + " Client : " + this.livraisonclient.getClient().getNom() + " " + this.livraisonclient.getClient().getPrenom(), SessionMBean.getUserAccount());
                    this.livraisonclient = new Livraisonclient();
                    this.supprimer = this.modifier = this.detail = true;
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
        printBIll();
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

    public void printAll() {
        directory = "vente";
        fileName = PrintUtils.printPeriodicOutput(livraisonclients, "--");
        RequestContext.getCurrentInstance().execute("PF('SortieDirecteImprimerDialog').show()");
    }

    public void printBIll() {
        try {
            if (!Utilitaires.isAccess(26L)) {
                notifyError("acces_refuse");
                this.livraisonclient = null;
                return;
            }

            if (this.livraisonclient != null) {
                List list = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                this.livraisonclient.setLignelivraisonclientList(list);
                directory = "facture";

                switch (SessionMBean.getMagasin().getModeleFacture()) {
                    case MODELE_A4:
                        fileName = PrintUtils.printFacture(livraisonclient, SessionMBean.getParametrage());
                        break;
                    case MODELE_80_217_TRACE:
                        fileName = PrintUtils.printFacture_format_80_217(livraisonclient, SessionMBean.getParametrage());
                        break;
                    case MODELE_80_217:
                        fileName = PrintUtils.printFacture_format_80_217_(livraisonclient, SessionMBean.getParametrage());
                        break;
                }
                RequestContext.getCurrentInstance().execute("PF('SortieDirecteImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    private void updateItemQties(Lignelivraisonclient source) {
        if (source.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
            source.setQuantitemultiple(source.getQuantite() * source.getIdlot().getIdarticle().getUnite());
            source.setQuantitereduite(source.getQuantite());
            source.setUnite(source.getIdlot().getIdarticle().getUnite());
        } else {
            source.setQuantitemultiple(source.getQuantite());
            source.setQuantitereduite(source.getQuantite() / source.getIdlot().getIdarticle().getUnite());
            source.setUnite(1d);
        }
    }

    private double sumQtiesInItemByItem(Magasinlot ml) {
        double qtyByArticle = 0;
        for (Lignelivraisonclient item : livraisonclient.getLignelivraisonclientList()) {
            if (item.getIdmagasinlot().equals(ml)) {
                if (item.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                    qtyByArticle += item.getQuantite();
                } else {
                    qtyByArticle += (item.getQuantite() / item.getIdmagasinlot().getIdlot().getIdarticle().getUnite());
                }
            }
        }
        return qtyByArticle;
    }

    public void addArticle() {
        try {
            if (this.magasinlot == null) {
                JsfUtil.addErrorMessage(this.routine.localizeMessage("article_invalide"));
                return;
            }
            Magasinlot ml = magasinlotFacadeLocal.find(magasinlot.getIdmagasinlot());
            Lignelivraisonclient llcToAdd = this.lignelivraisonclient;
            llcToAdd.setIdlot(ml.getIdlot());
            llcToAdd.setIdmagasinlot(ml);
            llcToAdd.setIdlignelivraisonclient(0L);

            this.updateItemQties(llcToAdd);

            double qtyByArticle = this.sumQtiesInItemByItem(ml);

            if ((llcToAdd.getQuantitereduite() + qtyByArticle) > this.magasinlot.getQuantitereduite()) {
                JsfUtil.addErrorMessage(this.routine.localizeMessage("quantite_debordee"));
                return;
            }

            llcToAdd.setIdunite(ml.getIdlot().getIdarticle().getIdunite());
            if (llcToAdd.getModeVente().equals(ModeEntreSorti.VENTE_EN_DETAIL)) {
                llcToAdd.setIdunite(uniteFacadeLocal.find(ml.getIdlot().getIdarticle().getIdUniteDetail()));
            }

            //llcToAdd.setPrixAchat(this.magasinlot.getIdmagasinarticle().getIdarticle().getCoutachat());
            //llcToAdd.setPrixVente(this.magasinlot.getIdmagasinarticle().getIdarticle().getPrixunit());
            this.livraisonclient.addItem(llcToAdd);

            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));

            //updateTotal();
            //this.livraisonclient.computeTotals();
            this.stockOutputService.computeTotals(livraisonclient);

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

            Lignelivraisonclient llc = this.livraisonclient.getLignelivraisonclientList().get(index);
            if (llc.getIdlignelivraisonclient() != 0L) {
                trouve = true;

                llc = lignelivraisonclientFacadeLocal.find(llc.getIdlignelivraisonclient());

                this.updateMagasinArticle(llc.getIdmagasinlot().getIdmagasinarticle(), llc.getQuantitereduite(), llc.getQuantitemultiple(), "+");

                //Magasinarticle pro = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                this.updateMagasinLot(llc.getIdmagasinlot(), llc.getQuantitereduite(), llc.getQuantitemultiple(), "+");

                Lignemvtstock lmvts = null;
                lmvts = ligneMvtstockFacadeLocal
                        .findByIdmvtIdLot(livraisonclient.getIdmvtstock().getIdmvtstock(), llc.getIdlot().getIdlot(), llc.getIdlignelivraisonclient());
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
            this.livraisonclient.getLignelivraisonclientList().remove(index);

            //updateTotal();
            //this.livraisonclient.computeTotals();
            this.stockOutputService.computeTotals(livraisonclient);

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

    /*public Double calculTotal() {
        Double resultatTotal = 0d;
        //double marge = 0d;
        int i = 0;
        for (Lignelivraisonclient llc : this.livraisonclient.getLignelivraisonclientList()) {

            Double resultat = 0d;
            if (llc.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                this.livraisonclient.getLignelivraisonclientList().get(i).setQuantitemultiple((llc.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getUnite() * llc.getQuantite()));
                this.livraisonclient.getLignelivraisonclientList().get(i).setQuantitereduite(llc.getQuantite());
                resultat = llc.getPrixUnitaire() * this.livraisonclient.getLignelivraisonclientList().get(i).getQuantite();
                //livraisonclient.getLignelivraisonclientList().get(i).setMarge(((llc.getPrixUnitaire() - this.livraisonclient.getLignelivraisonclientList().get(i).getPrixAchat()) * livraisonclient.getLignelivraisonclientList().get(i).getQuantite()));
            } else {
                this.livraisonclient.getLignelivraisonclientList().get(i).setQuantitemultiple((llc.getQuantite()));
                this.livraisonclient.getLignelivraisonclientList().get(i).setQuantitereduite(this.livraisonclient.getLignelivraisonclientList().get(i).getQuantite() / llc.getIdmagasinlot().getIdlot().getIdarticle().getUnite());
                resultat = llc.getPrixUnitaire() * this.livraisonclient.getLignelivraisonclientList().get(i).getQuantitemultiple();
                //livraisonclient.getLignelivraisonclientList().get(i).setMarge(((llc.getPrixUnitaire() - this.livraisonclient.getLignelivraisonclientList().get(i).getPrixAchat()) * this.livraisonclient.getLignelivraisonclientList().get(i).getQuantitemultiple()));
            }

            resultatTotal += resultat;
            this.livraisonclient.getLignelivraisonclientList().get(i).setMontant(resultat);

            if (this.livraisonclient.getLignelivraisonclientList().get(i).getMarge() < 0) {
                this.livraisonclient.getLignelivraisonclientList().get(i).setMarge(0);
            }
            //marge += this.livraisonclient.getLignelivraisonclientList().get(i).getMarge();
            i++;
        }
        //livraisonclient.setMarge(marge);
        return resultatTotal;
    }*/
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
                this.factoriseModeGros(lignelivraisonclient, magasinlot);
                lignelivraisonclient.setIdunite(magasinlot.getIdlot().getIdarticle().getIdunite());
            } else {
                this.factoriseModeDetail(lignelivraisonclient, magasinlot);
            }
        }
    }

    private void factoriseModeDetail(Lignelivraisonclient llc, Magasinlot ml) {
        llc.setPrixUnitaire(ml.getIdlot().getIdarticle().getPrixVenteDetail());
        llc.setUnite(1);

        llc.setPrixAchat(ml.getIdlot().getIdarticle().getPrixAchatDetail());

        llc.setPrixVente(ml.getPrixVenteDetail());

        llc.setQuantite(1);
        llc.setQuantitemultiple(1d);

        llc.setMontant(ml.getPrixVenteDetail());

        llc.setIdunite(uniteFacadeLocal.find(ml.getIdlot().getIdarticle().getIdUniteDetail()));
    }

    private void factoriseModeGros(Lignelivraisonclient llc, Magasinlot ml) {
        llc.setPrixUnitaire(ml.getIdlot().getIdarticle().getPrixunit());
        llc.setUnite(ml.getIdlot().getIdarticle().getUnite());

        llc.setPrixAchat(ml.getIdlot().getIdarticle().getCoutachat());
        llc.setPrixVente(ml.getPrixVenteGros());
        llc.setQuantite(1);
        llc.setQuantitemultiple(ml.getIdlot().getIdarticle().getUnite());

        llc.setMontant(ml.getPrixVenteGros());
    }

    private void initModeDetail(Magasinlot ml, Lignelivraisonclient llc) {
        this.factoriseModeDetail(llc, ml);
        llc.setModeVente(ModeEntreSorti.VENTE_EN_DETAIL);
        llc.setIdmagasinlot(ml);
        this.famille = ml.getIdmagasinarticle().getIdarticle().getIdfamille();
    }

    private void initModeGros(Magasinlot ml, Lignelivraisonclient llc) {
        this.factoriseModeGros(llc, ml);
        llc.setIdunite(ml.getIdlot().getIdarticle().getIdunite());
        llc.setIdmagasinlot(ml);
        llc.setModeVente(ModeEntreSorti.VENTE_EN_GROS);
        this.famille = magasinlot.getIdmagasinarticle().getIdarticle().getIdfamille();
    }

    public void updatedataLot() {
        try {
            if (this.magasinlot.getIdmagasinlot() != null && this.magasinlot != null) {
                this.magasinlot = magasinlotFacadeLocal.find(magasinlot.getIdmagasinlot());
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

    public String getformatTotal(String option) {
        if (option.equals("total")) {
            double value = livraisonclients.stream().mapToDouble(Livraisonclient::getMontantTtc).sum();
            return JsfUtil.formaterStringMoney(value);
        } else if (option.equals("regle")) {
            double value = livraisonclients.stream().mapToDouble(Livraisonclient::getMontantPaye).sum();
            return JsfUtil.formaterStringMoney(value);
        } else if (option.equals("ht")) {
            double value = livraisonclients.stream().mapToDouble(Livraisonclient::getMontantHt).sum();
            return JsfUtil.formaterStringMoney(value);
        } else {
            double value = livraisonclients.stream().mapToDouble(Livraisonclient::getMontantRemise).sum();
            return JsfUtil.formaterStringMoney(value);
        }
    }

    public void updateDate() {
        startDate = null;
        endDate = null;
        if (this.idMois != null) {
            Optional<AnneeMois> value = this.listMois.stream()
                    .filter(item -> item.getIdAnneeMois().equals(idMois)).findAny();

            if (value.isPresent()) {
                startDate = value.get().getDateDebut();
                endDate = value.get().getDateFin();
            }
        }
    }

    public void onSearch() {
        this.livraisonclients = new ArrayList<>();
        switch (searchMode) {
            case "MOIS":
                livraisonclients = livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), startDate, endDate);
                break;
            case "INTERVAL":
                livraisonclients = livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), startDate, endDate);
                break;
            case "DATE":
                livraisonclients = livraisonclientFacadeLocal.findByIdmagasinAndDate(SessionMBean.getMagasin().getIdmagasin(), startDate);
                break;
        }

        if (livraisonclients == null || livraisonclients.isEmpty()) {
            JsfUtil.addWarningMessage("Aucune donnée retrouvée");
        }
    }

    public void updateFiltre() {
        this.filtre(this.searchMode);
    }

    private void filtre(String option) {
        this.isShowEndDate = false;
        this.isShowStartDate = false;
        this.isShowFiltreMois = false;
        switch (searchMode) {
            case "MOIS":
                this.isShowFiltreMois = true;
                break;
            case "INTERVAL":
                this.isShowStartDate = true;
                this.isShowEndDate = true;
                break;
            case "DATE":
                this.isShowStartDate = true;
                break;
        }
    }

}
