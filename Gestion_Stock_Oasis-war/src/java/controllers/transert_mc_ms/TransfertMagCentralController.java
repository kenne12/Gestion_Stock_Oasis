package controllers.transert_mc_ms;

import entities.Article;
import entities.Famille;
import entities.Lignemvtstock;
import entities.Lignerepartitionarticle;
import entities.Lignerepartitiontemp;
import entities.Lot;
import entities.Magasinlot;
import entities.Repartitionarticle;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.GroupQuantite;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class TransfertMagCentralController extends AbstractTransertMagCentralController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(52L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
            this.mode = "Create";

            this.magasinlots.clear();
            this.selectedMagasinlots.clear();

            this.repartitionarticle = new Repartitionarticle();
            this.repartitionarticle.setDate(new Date());
            this.payement_credit = false;

            this.showSelectorCommand = false;
            this.lignerepartitiontemps.clear();
            this.lignerepartitionarticles.clear();
            this.lignerepartitionarticles_1.clear();

            this.total = 0d;
            this.state_zero = 0;
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareAddArticle() {
        try {
            this.lignerepartitionarticles_1.clear();
            this.selectedMagasinlots.clear();
            this.magasinlots.clear();

            this.famille = new Famille();
            this.lot = new Lot();
            this.article = new Article();
            this.lots.clear();
            this.articles = this.articleFacadeLocal.findByEtatQuantityPositif(SessionMBean.getMagasin().getParametrage().getId(), true);
            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.repartitionarticle == null) {
                notifyError("not_row_selected");
                return;
            }

            this.showSelectorCommand = true;

            if (!Utilitaires.isAccess(49L)) {
                notifyError("acces_refuse");
                this.repartitionarticle = null;
                return;
            }

            this.mode = "Edit";

            this.lignerepartitionarticles = this.lignerepartitionarticleFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle());
            this.lignerepartitiontemps = this.lignerepartitiontempFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle());

            this.total = this.repartitionarticle.getMontanttotal();
            RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.repartitionarticle != null) {
                this.lignerepartitionarticles = this.lignerepartitionarticleFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle());
                this.lignerepartitiontemps = this.lignerepartitiontempFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle());

                if (!this.lignerepartitionarticles.isEmpty()) {
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

    public void filterArticle() {
        try {
            this.articles.clear();
            this.lots.clear();
            this.article = new Article();
            this.lot = new Lot();
            if (this.famille.getIdfamille() != null) {
                this.articles = this.articleFacadeLocal.findByFamille(this.famille.getIdfamille());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduit() {
        try {
            if (!this.selectedMagasinlots.isEmpty()) {
                if (findLotLignerepartititionTmp(this.lot)) {
                    Lignerepartitiontemp lrt = new Lignerepartitiontemp();
                    lrt.setIdlignerepartitiontemp(0L);
                    lrt.setIdlot(this.lot);
                    lrt.setQuantite(0d);
                    lrt.setUnite(this.lot.getIdarticle().getUnite());
                    lrt.setQuantitemultiple(0d);
                    this.lignerepartitiontemps.add(lrt);
                }

                for (Magasinlot m : this.selectedMagasinlots) {
                    this.state_zero += 1;
                    if (findMagasinLotInLigneRepartition(m, this.lignerepartitionarticles_1)) {
                        Lignerepartitionarticle lra = new Lignerepartitionarticle();
                        lra.setIdlignerepartitionarticle(0L);
                        lra.setIdmagasinlot(m);
                        lra.setUnite(m.getIdlot().getIdarticle().getUnite());
                        lra.setQuantite(1d);
                        lra.setQuantitemultiple(lra.getUnite());
                        lra.setIdunite(m.getIdlot().getIdarticle().getIdunite());
                        this.lignerepartitionarticles_1.add(lra);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean findLotLignerepartititionTmp(Lot lot) {
        if (this.lignerepartitiontemps.isEmpty()) {
            return true;
        }
        boolean result = false;
        for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
            if (!lrt.getIdlot().equals(lot)) {
                result = true;
                break;
            }
        }
        return result;
    }

    private boolean findMagasinLotInLigneRepartition(Magasinlot m, List<Lignerepartitionarticle> lignerepartitionarticles) {
        if (lignerepartitionarticles.isEmpty()) {
            return true;
        }
        boolean result = false;

        for (Lignerepartitionarticle lra : lignerepartitionarticles) {
            if (!lra.getIdmagasinlot().equals(m)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void updateListMagasin() {
        try {
            this.magasinlots.clear();
            if (this.lot != null) {
                this.magasinlots = this.magasinlotFacadeLocal.findByIdlot(this.lot.getIdlot());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateData() {
        try {
            if (!this.selectedMagasinlots.isEmpty()) {
                double qte_test = 0.0D;
                for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
                    qte_test += lrt.getIdlot().getQuantite();
                }

                for (Lignerepartitionarticle lra : this.lignerepartitionarticles_1) {
                    if (findMagasinLotInLigneRepartition(lra.getIdmagasinlot(), this.lignerepartitionarticles)) {
                        this.lignerepartitionarticles.add(lra);
                    }
                }

                double qte_all = 0.0D;
                for (Lignerepartitionarticle lra : lignerepartitionarticles) {
                    qte_all += lra.getQuantite();
                }

                if (qte_test < qte_all) {
                    notifyError("quantite_inexate");
                    return;
                }

                int i = 0;
                for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
                    Double qte_1 = 0d;
                    for (Lignerepartitionarticle lra : this.lignerepartitionarticles) {
                        if (lrt.getIdlot().equals(lra.getIdmagasinlot().getIdlot())) {
                            qte_1 += lra.getQuantite();
                        }
                    }
                    this.lignerepartitiontemps.get(i).setQuantite(qte_1);
                    this.lignerepartitiontemps.get(i).setQuantitemultiple((qte_1 * (this.lignerepartitiontemps.get(i)).getUnite()));
                    i++;
                }

                updateTotal();
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void updatedata() {
        try {
            if (this.article != null) {
                this.famille = this.article.getIdfamille();
                this.lot = new Lot();
                this.lots = this.lotFacadeLocal.findByArticle(this.article.getIdarticle(), this.article.getPerissable(), new Date(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map groupQtyByLrt() {
        Map map = new HashMap();
        int i = 0;
        for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
            GroupQuantite gq = new GroupQuantite();
            for (Lignerepartitionarticle lra : this.lignerepartitionarticles) {
                if (lrt.getIdlot().equals(lra.getIdmagasinlot().getIdlot())) {
                    gq.setQuantite(gq.getQuantite() + lra.getQuantite());
                    gq.setQuantiteReduite(gq.getQuantiteReduite() + lra.getQuantitereduite());
                    gq.setQuantiteMultiple(gq.getQuantiteMultiple() + lra.getQuantitemultiple());
                    gq.setUnite(gq.getUnite() + lra.getUnite());
                }
            }
            this.lignerepartitiontemps.get(i).setQuantite(gq.getQuantite());
            this.lignerepartitiontemps.get(i).setQuantitemultiple(gq.getQuantiteMultiple());
            this.lignerepartitiontemps.get(i).setQuantitereduite(gq.getQuantiteReduite());
            this.lignerepartitiontemps.get(i).setUnite(gq.getUnite());
            map.put("" + lrt.getIdlot().getIdlot(), gq);
        }
        return map;
    }

    public void create() {
        try {
            if ("Create".equals(this.mode)) {
                if (!this.lignerepartitionarticles.isEmpty()) {
                    updateTotal();
                    Map mapGroupLrt = groupQtyByLrt();

                    String message = "";

                    String codeMvt = "MVT";
                    this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    Long nextMvt = this.mvtstock.getIdmvtstock();
                    codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);

                    this.ut.begin();

                    this.mvtstock.setCode(codeMvt);
                    this.mvtstock.setDatemvt(this.repartitionarticle.getDate());
                    this.mvtstock.setClient("MAGA SEC");
                    this.mvtstock.setFournisseur(" ");
                    this.mvtstock.setMagasin("MAGASIN CENTRAL");
                    this.mvtstock.setType("SORTIE");
                    this.mvtstockFacadeLocal.create(this.mvtstock);

                    String code = "MVT";
                    Integer nextVal = this.repartitionarticleFacadeLocal.nextVal();
                    code = Utilitaires.genererCodeDemande(code, (nextVal.longValue()));
                    this.repartitionarticle.setCode(code);
                    this.repartitionarticle.setIdrepartitionarticle(nextVal);
                    this.repartitionarticle.setIdmvtstock(this.mvtstock);
                    this.repartitionarticleFacadeLocal.create(this.repartitionarticle);

                    Map m = new HashMap();

                    for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
                        lrt.setIdlignerepartitiontemp(this.lignerepartitiontempFacadeLocal.nextVal());
                        lrt.setIdrepartitionarticle(this.repartitionarticle);

                        GroupQuantite gq = (GroupQuantite) mapGroupLrt.get("" + lrt.getIdlot().getIdlot());
                        lrt.setQuantitemultiple((gq.getQuantiteMultiple()));
                        lrt.setUnite((gq.getUnite()));
                        lrt.setQuantitereduite(gq.getQuantiteReduite());
                        this.lignerepartitiontempFacadeLocal.create(lrt);

                        m.put("" + lrt.getIdlot().getIdlot(), lrt);

                        lrt.setIdlot(this.lotFacadeLocal.find(lrt.getIdlot().getIdlot()));
                        lrt.getIdlot().setQuantite((lrt.getIdlot().getQuantite() - lrt.getQuantite()));
                        lrt.getIdlot().setQuantitemultiple((lrt.getIdlot().getQuantitemultiple() - lrt.getQuantitemultiple()));
                        lrt.getIdlot().setQuantitereduite((lrt.getIdlot().getQuantitereduite() - lrt.getQuantitereduite()));
                        this.lotFacadeLocal.edit(lrt.getIdlot());

                        lrt.getIdlot().getIdarticle().setQuantitestock((lrt.getIdlot().getIdarticle().getQuantitestock() - lrt.getQuantite()));
                        lrt.getIdlot().getIdarticle().setQuantitemultiple((lrt.getIdlot().getIdarticle().getQuantitemultiple() - lrt.getQuantitemultiple()));
                        lrt.getIdlot().getIdarticle().setQuantitereduite((lrt.getIdlot().getIdarticle().getQuantitereduite() - lrt.getQuantitereduite()));
                        this.articleFacadeLocal.edit(lrt.getIdlot().getIdarticle());

                        Lignemvtstock lmvts = new Lignemvtstock();
                        lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        lmvts.setIdmvtstock(this.mvtstock);

                        lmvts.setIdlot(lrt.getIdlot());
                        lmvts.setQtesortie((lrt.getQuantitemultiple() * lrt.getIdlot().getIdarticle().getUnite()));
                        lmvts.setQteentree(0d);
                        lmvts.setReste(lrt.getIdlot().getQuantitereduite());
                        lmvts.setClient("MAGASIN SECONDAIRE");
                        lmvts.setFournisseur("");
                        lmvts.setType("SORTIE");
                        this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    for (Lignerepartitionarticle lra : this.lignerepartitionarticles) {
                        lra.setIdlignerepartitionarticle(this.lignerepartitionarticleFacadeLocal.nextVal());
                        lra.setIdrepartitionarticle(this.repartitionarticle);
                        lra.setMontant((lra.getIdmagasinlot().getIdlot().getPrixunitaire() * lra.getQuantite()));
                        lra.setIdlignerepartitiontemp((Lignerepartitiontemp) m.get("" + lra.getIdmagasinlot().getIdlot().getIdlot()));
                        this.lignerepartitionarticleFacadeLocal.create(lra);

                        lra.getIdmagasinlot().setIdmagasinarticle(this.magasinarticleFacadeLocal.find(lra.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle()));
                        lra.getIdmagasinlot().getIdmagasinarticle().setQuantite((lra.getIdmagasinlot().getIdmagasinarticle().getQuantite() + lra.getQuantite()));
                        lra.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple((lra.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple() + lra.getQuantitemultiple()));
                        lra.getIdmagasinlot().getIdmagasinarticle().setQuantitereduite((lra.getIdmagasinlot().getIdmagasinarticle().getQuantitereduite() - lra.getQuantitereduite()));
                        this.magasinarticleFacadeLocal.edit(lra.getIdmagasinlot().getIdmagasinarticle());

                        lra.setIdmagasinlot(this.magasinlotFacadeLocal.find(lra.getIdmagasinlot().getIdmagasinlot()));
                        lra.getIdmagasinlot().setQuantite((lra.getIdmagasinlot().getQuantite() + lra.getQuantite()));
                        lra.getIdmagasinlot().setQuantitemultiple((lra.getIdmagasinlot().getQuantitemultiple() + lra.getQuantitemultiple()));
                        lra.getIdmagasinlot().setQuantitereduite((lra.getIdmagasinlot().getQuantitereduite() - lra.getQuantitereduite()));
                        this.magasinlotFacadeLocal.edit(lra.getIdmagasinlot());

                        Lignemvtstock lmvts = new Lignemvtstock();
                        lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        lmvts.setIdmvtstock(this.mvtstock);
                        lmvts.setIdlot(lra.getIdmagasinlot().getIdlot());
                        lmvts.setIdmagasinlot(lra.getIdmagasinlot());
                        lmvts.setQtesortie(0d);
                        lmvts.setQteentree(lra.getQuantitemultiple());
                        lmvts.setClient(lra.getIdmagasinlot().getIdmagasinarticle().getIdmagasin().getNom());
                        lmvts.setFournisseur("MAGASIN CENTRAL");
                        lmvts.setType("ENTREE");
                        lmvts.setReste(lra.getIdmagasinlot().getQuantite());
                        this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du transfert d'article ; N° : " + this.repartitionarticle.getCode() + "; Montant : " + this.repartitionarticle.getMontanttotal(), SessionMBean.getUserAccount());

                    this.ut.commit();

                    this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
                    JsfUtil.addSuccessMessage(message);

                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').hide()");
                } else {
                    notifyError("liste_article_vide");
                }
            } else if (this.repartitionarticle == null) {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.repartitionarticle != null) {
                if (!Utilitaires.isAccess(53L)) {
                    notifyError("acces_refuse");
                    this.detail = (this.supprimer = this.modifier = this.imprimer = true);
                    this.repartitionarticle = null;
                    return;
                }

                this.ut.begin();

                List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.repartitionarticle.getIdmvtstock().getIdmvtstock());
                for (Lignemvtstock l : lmvt) {
                    this.lignemvtstockFacadeLocal.remove(l);
                }

                List<Lignerepartitiontemp> listRepartitionTemp = this.lignerepartitiontempFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle());
                if (!listRepartitionTemp.isEmpty()) {
                    for (Lignerepartitiontemp lrt : listRepartitionTemp) {

                        this.lignerepartitiontempFacadeLocal.remove(lrt);

                        lrt.setIdlot(this.lotFacadeLocal.find(lrt.getIdlot().getIdlot()));
                        lrt.getIdlot().setQuantite((lrt.getIdlot().getQuantite() + lrt.getQuantite()));
                        lrt.getIdlot().setQuantitemultiple((lrt.getIdlot().getQuantitemultiple() + lrt.getQuantitemultiple()));
                        lrt.getIdlot().setQuantitereduite((lrt.getIdlot().getQuantitereduite() + lrt.getQuantitereduite()));
                        this.lotFacadeLocal.edit(lrt.getIdlot());

                        lrt.getIdlot().getIdarticle().setQuantitestock((lrt.getIdlot().getIdarticle().getQuantitestock() + lrt.getQuantite()));
                        lrt.getIdlot().getIdarticle().setQuantitemultiple((lrt.getIdlot().getIdarticle().getQuantitemultiple() + lrt.getQuantitemultiple()));
                        lrt.getIdlot().getIdarticle().setQuantitereduite((lrt.getIdlot().getIdarticle().getQuantitereduite() + lrt.getQuantitereduite()));
                        this.articleFacadeLocal.edit(lrt.getIdlot().getIdarticle());
                    }
                }

                List<Lignerepartitionarticle> listLigneRepartArts = this.lignerepartitionarticleFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle());
                for (Lignerepartitionarticle lra : listLigneRepartArts) {
                    this.lignerepartitionarticleFacadeLocal.remove(lra);

                    lra.setIdmagasinlot(this.magasinlotFacadeLocal.find(lra.getIdmagasinlot().getIdmagasinlot()));
                    lra.getIdmagasinlot().setQuantite((lra.getIdmagasinlot().getQuantite() - lra.getQuantite()));
                    lra.getIdmagasinlot().setQuantitemultiple((lra.getIdmagasinlot().getQuantitemultiple() - lra.getQuantitemultiple()));
                    lra.getIdmagasinlot().setQuantitereduite((lra.getIdmagasinlot().getQuantitereduite() - lra.getQuantitereduite()));

                    lra.getIdmagasinlot().getIdmagasinarticle().setQuantite((lra.getIdmagasinlot().getIdmagasinarticle().getQuantite() - lra.getQuantite()));
                    lra.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple((lra.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple() - lra.getQuantitemultiple()));
                    lra.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple((lra.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple() - lra.getQuantitemultiple()));

                    this.magasinlotFacadeLocal.edit(lra.getIdmagasinlot());
                    this.magasinarticleFacadeLocal.edit(lra.getIdmagasinlot().getIdmagasinarticle());
                }

                this.repartitionarticleFacadeLocal.remove(this.repartitionarticle);
                this.mvtstockFacadeLocal.remove(this.repartitionarticle.getIdmvtstock());

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation du transfert d'article : " + this.repartitionarticle.getCode() + " ; Montant : " + this.repartitionarticle.getMontanttotal(), SessionMBean.getUserAccount());
                this.ut.commit();

                this.repartitionarticle = null;
                this.supprimer = (this.modifier = this.imprimer = this.detail = true);
                notifySuccess();
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Repartitionarticle r) {
        this.repartitionarticle = r;
        print();
    }

    public void initPrinterBon(Repartitionarticle r) {
        this.repartitionarticle = r;
        printBonLivraison();
    }

    public void initEdit(Repartitionarticle r) {
        this.repartitionarticle = r;
        prepareEdit();
    }

    public void initView(Repartitionarticle r) {
        this.repartitionarticle = r;
        prepareview();
    }

    public void initDelete(Repartitionarticle r) {
        this.repartitionarticle = r;
        delete();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(54L)) {
                notifyError("acces_refuse");
                this.repartitionarticle = null;
                return;
            }

            if (this.repartitionarticle != null) {
                this.printDialogTitle = this.routine.localizeMessage("livraisonclient");

                RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printBonLivraison() {
        try {
            if (!Utilitaires.isAccess(54L)) {
                notifyError("acces_refuse");
                this.repartitionarticle = null;
                return;
            }

            if (this.repartitionarticle != null) {
                this.printDialogTitle = this.routine.localizeMessage("bon_de_livraison");

                RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
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

    public Double calculTotal(List<Lignerepartitionarticle> lignerepartitionarticles) {
        Double resultat = 0d;
        int i = 0;
        for (Lignerepartitionarticle lra : lignerepartitionarticles) {
            resultat += (lra.getIdmagasinlot().getIdlot().getPrixunitaire() * lra.getQuantite());
            lignerepartitionarticles.get(i).setQuantitemultiple((lra.getUnite() * lra.getQuantite()));
            lignerepartitionarticles.get(i).setQuantitereduite((lignerepartitionarticles.get(i).getQuantitemultiple() / lra.getIdmagasinlot().getIdlot().getIdarticle().getUnite()));
            i++;
        }
        return resultat;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal(this.lignerepartitionarticles);
            this.repartitionarticle.setMontanttotal(this.total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean findPositionZero(Lignerepartitionarticle l) {
        int i = this.lignerepartitionarticles_1.indexOf(l);

        if (this.state_zero == 1) {
            return true;
        }
        return false;
    }

    public void updateTotaux() {
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
