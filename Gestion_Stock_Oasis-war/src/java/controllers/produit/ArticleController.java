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
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

        this.mode = "Create";
        this.fournisseur = new Fournisseur();
        this.famille = new Famille();
        this.article = new Article();
        this.lot = new Lot();
        this.article.setQuantitestock(0d);
        this.article.setQuantitealerte(0d);
        this.article.setPerissable(false);
        this.article.setEtat(true);
        this.article.setUnite(0d);
        this.article.setPhoto("article.jpeg");
        this.article.setPhotoRelatif("article.jpeg");
        this.article.setDateEnregistre(Date.from(Instant.now()));
        this.article.setDerniereModif(Date.from(Instant.now()));
        this.article.setDescription("-");
        this.article.setUnite(1d);
        this.article.setQuantitemultiple(0d);
        this.article.setNbjralerte(30);
        this.article.setPhotoRelatif("article.jpeg");
        this.article.setFabricant("-");
        this.article.setPoids(0d);
        this.article.setQuantitemin(0);
        this.article.setQuantiteavarie(0);
        this.article.setQuantiteminpv(0);
        this.article.setQuantitepv(0);
        this.article.setQuantitesecurite(0d);
        this.article.setNbjralerte(30);
        this.article.setUnitesortie(1d);
        this.article.setCoutachat(0d);
        this.article.setPrixunit(0d);
        this.article.setQuantitereduite(0d);
        this.article.setQuantitemultiple(0d);
        article.setCode(Utilitaires.genererCodeArticle("ARTICLE_", articleFacadeLocal.nextValByIdstructure(SessionMBean.getParametrage().getId())));

        List listMag = this.magasins;
        this.selectedMagasins = listMag;
        this.showLot = false;
        this.showUser = SessionMBean.getParametrage().isEtatuser();
        this.showBailleur = SessionMBean.getParametrage().isEtatbailleur();
        RequestContext.getCurrentInstance().execute("PF('ArticleCreerDialog').show()");
    }

    public void prepareEdit() {
        this.mode = "Edit";
        try {
            if (!Utilitaires.isAccess((14L))) {
                notifyError("acces_refuse");
                return;
            }
            if (this.article != null) {
                this.showLot = false;
                this.famille = this.article.getIdfamille();
                this.selectedMagasins.clear();
                List<Magasinarticle> listMa = this.magasinarticleFacadeLocal.findByIdarticle(this.article.getIdarticle());
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
                if (SessionMBean.getParametrage().isEtatbailleur()) {
                    this.showBailleur = true;
                }
                if (SessionMBean.getParametrage().isEtatuser()) {
                    this.showUser = true;
                }
            }
            this.lot = new Lot();
            return;
        }
        this.showLot = false;
    }

    public void updatePrixDetail() {
        try {
            if (article.getUnite() != null && article.getUnite() != 1) {
                article.setPrixAchatDetail(article.getCoutachat() / article.getUnite());
                article.setPrixVenteDetail(article.getPrixunit() / article.getUnite());
            } else {
                article.setPrixVenteDetail(article.getPrixunit());
                article.setPrixAchatDetail(article.getCoutachat());
            }
        } catch (Exception e) {
            article.setPrixVenteDetail(article.getPrixunit());
            article.setPrixAchatDetail(article.getCoutachat());
            article.setUnite(1d);
        }
    }

    public void create() {
        try {

            if (this.mode.equals("Create")) {
                if (this.articleFacadeLocal.findByCode(SessionMBean.getMagasin().getParametrage().getId(), this.article.getCode()) != null) {
                    notifyError("code_article_existant");
                    return;
                }

                this.article.setIdarticle(this.articleFacadeLocal.nextVal());

                if (this.famille.getIdfamille() != null) {
                    this.article.setIdfamille(this.famille);
                }

                article.setUnitesortie(1.0);
                article.setParametrage(SessionMBean.getMagasin().getParametrage());
                article.setQuantitevirtuelle(0d);
                article.setUniteentree(0d);
                article.setIdunite(uniteFacadeLocal.find(article.getIdunite().getIdunite()));

                this.ut.begin();

                this.articleFacadeLocal.create(this.article);

                this.lot.setIdlot(this.lotFacadeLocal.nextVal());
                this.lot.setIdarticle(this.article);
                this.lot.setPrixunitaire(this.article.getPrixunit());
                this.lot.setPrixachat(this.article.getCoutachat());
                this.lot.setUnitesortie(0.0);
                this.lot.setQuantite(0.0);
                this.lot.setQuantitemultiple(0.0);
                this.lot.setQuantite(0.0);
                this.lot.setQuantitereduite(0.0);
                this.lot.setDateenregistrement(new Date());
                this.lot.setUniteentree(1.0);
                this.lot.setQuantitesecurite(this.article.getQuantitesecurite());
                this.lot.setEtat(true);
                lot.setQuantitevirtuelle(0d);

                if (!showLot) {
                    lot.setDatefabrication(null);
                    lot.setDateperemption(null);
                    lot.setNumero(this.article.getCode());
                    lotFacadeLocal.create(this.lot);
                }

                for (Magasin m : selectedMagasins) {

                    Magasinarticle obj = new Magasinarticle();
                    obj.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                    obj.setIdarticle(this.article);
                    obj.setIdmagasin(m);
                    obj.setEtat(true);
                    obj.setUnite(this.article.getUnite());
                    obj.setQuantite(0.0);
                    obj.setQuantitemultiple(0.0);
                    obj.setQuantitereduite(0.0);
                    obj.setQuantitevirtuelle(0.0);
                    obj.setQuantitesecurite(this.article.getQuantitesecurite());
                    this.magasinarticleFacadeLocal.create(obj);

                    if (!this.showLot) {
                        Magasinlot obj1 = new Magasinlot();
                        obj1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                        obj1.setIdmagasinarticle(obj);
                        obj1.setIdlot(this.lot);
                        obj1.setQuantite(0.0);
                        obj1.setUnite(this.article.getUnite());
                        obj1.setQuantitemultiple(0.0);
                        obj1.setQuantitereduite(0.0);
                        obj1.setQuantitevirtuelle(0.0);
                        obj1.setQuantitesecurite(0.0);
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
                this.modifier = this.supprimer = this.detail = true;

                RequestContext.getCurrentInstance().execute("PF('ArticleCreerDialog').hide()");
                notifySuccess();
            } else if (this.article != null) {
                Article p = this.articleFacadeLocal.find(this.article.getIdarticle());

                if ((!p.getCode().equals(this.article.getCode()))
                        && (this.articleFacadeLocal.findByCode(SessionMBean.getMagasin().getParametrage().getId(), this.article.getCode()) != null)) {
                    notifyError("code_article_existant");
                    return;
                }

                if (!Objects.equals(this.famille.getIdfamille(), p.getIdfamille().getIdfamille())) {
                    this.article.setIdfamille(this.familleFacadeLocal.find(this.famille.getIdfamille()));
                }
                article.setIdunite(uniteFacadeLocal.find(article.getIdunite().getIdunite()));

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
                        ma.setEtat(true);
                        ma.setQuantite(0.0);
                        ma.setQuantitemultiple(0.0);
                        ma.setQuantitereduite(0.0);
                        ma.setQuantitesecurite(0.0);
                        ma.setQuantitevirtuelle(0.0);
                        ma.setUnite(this.article.getUnite());
                        this.magasinarticleFacadeLocal.create(ma);
                    }
                }

                this.modifier = this.supprimer = this.detail = true;
                article = new Article();
                article.setIdunite(new Unite());

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
            if (!Utilitaires.isAccess(31L)) {
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
