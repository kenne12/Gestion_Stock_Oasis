package controllers.produit;

import entities.Article;
import entities.Famille;
import entities.Fournisseur;
import entities.Lignedemande;
import entities.Lignelivraisonfournisseur;
import entities.Lignetransfert;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Unite;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.Printer;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class ArticleController extends AbstractArticleController implements Serializable {

    @PostConstruct
    private void init() {
        this.fournisseur = new Fournisseur();
        this.famille = new Famille();
        this.password.add("momo1234");
        this.password.add("kenne1234");
    }

    public void prepareCreate() {
        if (!Utilitaires.isAccess(13L)) {
            notifyError("acces_refuse");
            return;
        }

        /*  63 */ this.mode = "Create";
        /*  64 */ this.fournisseur = new Fournisseur();
        /*  65 */ this.unite = new Unite();
        /*  66 */ this.famille = new Famille();
        /*  67 */ this.article = new Article();
        /*  68 */ this.lot = new Lot();
        /*  69 */ this.article.setQuantitestock(Double.valueOf(0.0D));
        /*  70 */ this.article.setQuantitealerte(Double.valueOf(0.0D));
        /*  71 */ this.article.setPerissable(Boolean.valueOf(true));
        /*  72 */ this.article.setEtat(Boolean.valueOf(true));
        /*  73 */ this.article.setUnite(Double.valueOf(0.0D));
        /*  74 */ this.article.setPhoto("article.jpeg");
        /*  75 */ this.article.setPhotoRelatif("article.jpeg");
        /*  76 */ this.article.setDateEnregistre(new Date());
        /*  77 */ this.article.setDerniereModif(new Date());
        /*  78 */ this.article.setDescription("-");
        /*  79 */ this.article.setUnite(Double.valueOf(1.0D));
        /*  80 */ this.article.setQuantitemultiple(Double.valueOf(0.0D));
        /*  81 */ this.article.setNbjralerte(Integer.valueOf(30));
        /*  82 */ this.article.setPhotoRelatif("article.jpeg");
        /*  83 */ this.article.setFabricant("-");
        /*  84 */ this.article.setPoids(Double.valueOf(0.0D));
        /*  85 */ this.article.setQuantitemin(Integer.valueOf(0));
        /*  86 */ this.article.setQuantiteavarie(Integer.valueOf(0));
        /*  87 */ this.article.setQuantiteminpv(Integer.valueOf(0));
        /*  88 */ this.article.setQuantitepv(Integer.valueOf(0));
        /*  89 */ this.article.setQuantitesecurite(Double.valueOf(0.0D));
        /*  90 */ this.article.setNbjralerte(Integer.valueOf(30));
        /*  91 */ this.article.setUnitesortie(Double.valueOf(1.0D));
        /*  92 */ this.article.setCoutachat(Double.valueOf(0.0D));
        /*  93 */ this.article.setPrixunit(Double.valueOf(0.0D));
        /*  94 */ this.article.setQuantitereduite(Double.valueOf(0.0D));
        /*  95 */ this.article.setQuantitemultiple(Double.valueOf(0.0D));

        /*  97 */ List listMag = this.magasins;
        /*  98 */ this.selectedMagasins = listMag;
        /*  99 */ this.showLot = true;
        /* 100 */ this.showUser = SessionMBean.getParametrage().getEtatuser().booleanValue();
        /* 101 */ this.showBailleur = SessionMBean.getParametrage().getEtatbailleur().booleanValue();
        /* 102 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreerDialog').show()");
    }

    public void prepareEdit() {
        this.mode = "Edit";
        try {
            if (!Utilitaires.isAccess(Long.valueOf(14L))) {
                notifyError("acces_refuse");
                return;
            }
            if (this.article != null) {
                this.showLot = false;
                this.famille = this.article.getIdfamille();
                this.unite = this.article.getIdunite();

                this.selectedMagasins.clear();
                List<Magasinarticle> listMa = this.magasinarticleFacadeLocal.findByIdarticle(this.article.getIdarticle().longValue());
                if (!listMa.isEmpty()) {
                    for (Magasinarticle ma : listMa) {
                        this.selectedMagasins.add(ma.getIdmagasin());
                    }
                }

                RequestContext.getCurrentInstance().execute("PF('ArticleCreerDialog').show()");
                return;
            }

            notifyError("not_row_select");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void updateLot() {
        if ("Create".equals(this.mode)) {
            this.showLot = this.article.getPerissable();
            if (!this.showLot) {
                this.showBailleur = this.showUser = false;
            } else {
                if (SessionMBean.getParametrage().getEtatbailleur()) {
                    this.showBailleur = true;
                }
                if (SessionMBean.getParametrage().getEtatuser()) {
                    this.showUser = true;
                }
            }
            this.lot = new Lot();
            return;
        }
        this.showLot = false;
    }

    public void create() {
        try {

            if (this.mode.equals("Create")) {
                if (this.articleFacadeLocal.findByCode(this.article.getCode()) != null) {
                    notifyError("code_article_existant");
                    return;
                }

                this.article.setIdarticle(this.articleFacadeLocal.nextVal());

                if (this.famille.getIdfamille() != null) {
                    this.article.setIdfamille(this.famille);
                }

                this.article.setIdunite(this.unite);
                this.article.setUnitesortie(1.0D);

                this.ut.begin();

                this.articleFacadeLocal.create(this.article);

                this.lot.setIdlot(this.lotFacadeLocal.nextVal());
                this.lot.setIdarticle(this.article);
                this.lot.setPrixunitaire(this.article.getPrixunit());
                this.lot.setPrixachat(this.article.getCoutachat());
                this.lot.setUnitesortie(Double.valueOf(0.0D));
                this.lot.setQuantite(Double.valueOf(0.0D));
                this.lot.setQuantitemultiple(Double.valueOf(0.0D));
                this.lot.setQuantite(Double.valueOf(0.0D));
                this.lot.setQuantitereduite(Double.valueOf(0.0D));
                this.lot.setDateenregistrement(new Date());
                this.lot.setUniteentree(Double.valueOf(1.0D));
                this.lot.setQuantitesecurite(this.article.getQuantitesecurite());
                this.lot.setEtat(Boolean.valueOf(true));

                if (!this.showLot) {
                    this.lot.setDatefabrication(null);
                    this.lot.setDateperemption(null);
                    this.lot.setNumero(this.article.getCode());
                    this.lotFacadeLocal.create(this.lot);
                }

                for (Magasin m : selectedMagasins) {

                    Magasinarticle obj = new Magasinarticle();
                    obj.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                    obj.setIdarticle(this.article);
                    obj.setIdmagasin(m);
                    obj.setEtat(true);
                    obj.setUnite(this.article.getUnite());
                    obj.setQuantite(0.0D);
                    obj.setQuantitemultiple(0.0D);
                    obj.setQuantitereduite(0.0);
                    obj.setQuantitevirtuelle(0.0D);
                    obj.setQuantitesecurite(this.article.getQuantitesecurite());
                    this.magasinarticleFacadeLocal.create(obj);

                    if (!this.showLot) {
                        Magasinlot obj1 = new Magasinlot();
                        obj1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                        obj1.setIdmagasinarticle(obj);
                        obj1.setIdlot(this.lot);
                        obj1.setQuantite(0.0D);
                        obj1.setUnite(this.article.getUnite());
                        obj1.setQuantitemultiple(0.0D);
                        obj1.setQuantitereduite(0.0D);
                        obj1.setQuantitevirtuelle(0.0D);
                        obj1.setEtat(true);
                        obj1.setQuantitesecurite(this.article.getQuantitesecurite());
                        this.magasinlotFacadeLocal.create(obj1);
                    }
                }

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'article : " + this.article.getLibelle(), SessionMBean.getUserAccount());

                this.ut.commit();
                this.fournisseur = null;
                this.famille = new Famille();
                this.article = new Article();

                this.modifier = (this.supprimer = this.detail = Boolean.valueOf(true));

                RequestContext.getCurrentInstance().execute("PF('ArticleCreerDialog').hide()");
                notifySuccess();
            } else if (this.article != null) {
                Article p = this.articleFacadeLocal.find(this.article.getIdarticle());

                if ((!p.getCode().equals(this.article.getCode()))
                        && (this.articleFacadeLocal.findByCode(this.article.getCode()) != null)) {
                    notifyError("code_article_existant");
                    return;
                }

                if (this.famille.getIdfamille() != p.getIdfamille().getIdfamille()) {
                    this.article.setIdfamille(this.familleFacadeLocal.find(this.famille.getIdfamille()));
                }
                this.article.setIdunite(this.uniteFacadeLocal.find(this.unite.getIdunite()));

                this.ut.begin();
                this.articleFacadeLocal.edit(this.article);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification de l'article : " + this.article.getLibelle() + " Ancienne quantité : " + p.getQuantitestock() + " ; Nouvelle quantité : " + this.article.getQuantitestock(), SessionMBean.getUserAccount());
                this.ut.commit();

                for (Magasin m : selectedMagasins) {
                    Magasinarticle ma = this.magasinarticleFacadeLocal.findByIdmagasinIdarticle(m.getIdmagasin().intValue(), this.article.getIdarticle().longValue());
                    if (ma == null) {
                        ma = new Magasinarticle();
                        ma.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                        ma.setIdmagasin(m);
                        ma.setIdarticle(this.article);
                        ma.setEtat(Boolean.valueOf(true));
                        ma.setQuantite(Double.valueOf(0.0D));
                        ma.setQuantitemultiple(Double.valueOf(0.0D));
                        ma.setQuantitereduite(Double.valueOf(0.0D));
                        ma.setQuantitesecurite(Double.valueOf(0.0D));
                        ma.setQuantitevirtuelle(Double.valueOf(0.0D));
                        ma.setUnite(this.article.getUnite());
                        this.magasinarticleFacadeLocal.create(ma);
                    }
                }

                this.modifier = (this.supprimer = this.detail = Boolean.valueOf(true));
                this.article = new Article();
                RequestContext.getCurrentInstance().execute("PF('ArticleCreerDialog').hide()");
                notifySuccess();
            } else {
                notifyError("not_row_select");
            }
        } catch (Exception e) {
            notifyFail(e);
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

    public void checkSession() {
        try {
            if (!"".equals(this.sessionPassword)) {
                if (this.password.contains(this.sessionPassword)) {
                    HttpSession session1 = SessionMBean.getSession();
                    session1.setAttribute("session", true);
                    this.session = false;
                } else {
                    JsfUtil.addErrorMessage("Mot de passe incorrect");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        try {
            if (this.article != null) {
                if (!Utilitaires.isAccess(15L)) {
                    notifyError("acces_refuse");
                    return;
                }

                List<Lignedemande> listLd = this.lignedemandeFacadeLocal.findByIdarticle(this.article.getIdarticle());
                List<Lignelivraisonfournisseur> listLlf = this.lignelivraisonfournisseurFacadeLocal.findByIdarticle(this.article.getIdarticle());
                List<Lignetransfert> llt = lignetransfertFacadeLocal.findByIdarticle(article.getIdarticle());
                if (listLd.isEmpty() && listLlf.isEmpty() && llt.isEmpty()) {
                    this.ut.begin();
                    this.magasinlotFacadeLocal.removeAllByIdarticle(this.article.getIdarticle());
                    this.magasinarticleFacadeLocal.removeAllByIdarticle(this.article.getIdarticle());

                    this.articleFacadeLocal.remove(this.article);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'article : " + this.article.getLibelle(), SessionMBean.getUserAccount());
                    this.ut.commit();
                    this.article = null;
                    notifySuccess();
                } else {
                    notifyError("cet_article_associe_a_plusieurs_demande");
                }
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(29L)) {
                notifyError("acces_refuse");
                return;
            }

            Map map = new HashMap();
            Printer.print("/reports/ireport/liste_produit.jasper", map);
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printStockGeneral() {
        try {
            if (!Utilitaires.isAccess(32L)) {
                notifyError("acces_refuse");
                return;
            }
            this.fileName1 = PrintUtils.printGeneralStockReport(this.articles);
            RequestContext.getCurrentInstance().execute("PF('StockImprimerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printInventory() {
        try {
            if (!Utilitaires.isAccess(Long.valueOf(31L))) {
                notifyError("acces_refuse");
                return;
            }

            this.fileName1 = PrintUtils.printInventoryReport(this.articles);
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('InventaireImprimerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printSousStock() {
        try {
            if (!Utilitaires.isAccess(30L)) {
                notifyError("acces_refuse");
                return;
            }

            this.fileName2 = PrintUtils.printCritickStockReport(this.articles1);
            RequestContext.getCurrentInstance().execute("PF('StockImprimerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }
}
