package controllers.produit;

import com.google.common.io.Files;
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
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
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
        this.article = new Article();
        this.article.setIdunite(new Unite(0l));
    }

    public void prepareCreate() {
        if (!Utilitaires.isAccess(25L)) {
            notifyError("acces_refuse");
            return;
        }

        this.mode = "Create";
        this.fournisseur = new Fournisseur();
        this.famille = new Famille();
        this.article = new Article();
        this.lot = new Lot();
        this.article.setPerissable(false);
        this.article.setEtat(true);
        this.article.setPhoto("article.jpeg");
        this.article.setPhotoRelatif("article.jpeg");
        this.article.setDateEnregistre(Date.from(Instant.now()));
        this.article.setDerniereModif(Date.from(Instant.now()));
        this.article.setDescription("-");
        this.article.setNbjralerte(30);
        this.article.setPhotoRelatif("article.jpeg");
        this.article.setFabricant("-");
        this.article.setNbjralerte(30);
        this.unite = new Unite(0l);
        article.setCode(Utilitaires.genererCodeArticle("ARTICLE_", articleFacadeLocal.nextValByIdstructure(SessionMBean.getParametrage().getId())));
        this.setIsSelectAll(false);
        this.selectedMagasins.clear();
        this.showLot = false;
        this.showUser = SessionMBean.getParametrage().isEtatuser();
        this.showBailleur = SessionMBean.getParametrage().isEtatbailleur();
        RequestContext.getCurrentInstance().execute("PF('ArticleCreerDialog').show()");
    }

    public void prepareEdit() {
        this.mode = "Edit";
        try {
            if (!Utilitaires.isAccess(25L)) {
                notifyError("acces_refuse");
                return;
            }
            if (this.article != null) {
                this.showLot = false;
                this.famille = this.article.getIdfamille();
                this.unite = this.article.getIdunite();
                this.selectedMagasins.clear();
                List<Magasinarticle> listArticles = this.magasinarticleFacadeLocal.findByIdarticle(this.article.getIdarticle());
                if (!listArticles.isEmpty()) {
                    listArticles.stream().forEach(item -> {
                        this.selectedMagasins.add(item.getIdmagasin());
                    });
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
            if (article.getUnite() != 1) {
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

    public void selectAllStore() {
        selectedMagasins.clear();
        if (isSelectAll) {
            selectedMagasins.addAll(magasins);
        }
    }

    public void create() {
        try {

            if (this.famille.getIdfamille() == 0 || unite.getIdunite() == 0l || article.getIdUniteDetail() == 0) {
                notifyError("veuillez_verifier_le_formulaire");
                return;
            }

            if (this.mode.equals("Create")) {
                if (this.articleFacadeLocal.findByCode(SessionMBean.getMagasin().getParametrage().getId(), this.article.getCode()) != null) {
                    notifyError("code_article_existant");
                    return;
                }

                this.article.setIdarticle(articleFacadeLocal.nextVal());

                this.article.setIdfamille(this.famille);

                article.setUnitesortie(1.0);
                article.setParametrage(SessionMBean.getMagasin().getParametrage());
                article.setQuantitevirtuelle(0d);
                article.setUniteentree(0d);
                article.setIdunite(uniteFacadeLocal.find(unite.getIdunite()));

                this.ut.begin();

                this.articleFacadeLocal.create(this.article);

                this.lot.setIdlot(this.lotFacadeLocal.nextVal());
                this.lot.setIdarticle(this.article);
                this.lot.setPrixunitaire(this.article.getPrixunit());
                this.lot.setPrixachat(this.article.getCoutachat());
                this.lot.setUnitesortie(article.getUnitesortie());
                this.lot.setDateenregistrement(new Date());
                this.lot.setUniteentree(1.0);
                this.lot.setQuantitesecurite(this.article.getQuantitesecurite());
                this.lot.setEtat(true);

                if (!showLot) {
                    lot.setDatefabrication(null);
                    lot.setDateperemption(null);
                    lot.setNumero(this.article.getCode());
                    lotFacadeLocal.create(this.lot);
                }

                //selectedMagasins.clear();
                //selectedMagasins.add(SessionMBean.getMagasin());
                for (Magasin m : selectedMagasins) {
                    Magasinarticle obj = new Magasinarticle();
                    obj.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                    obj.setIdarticle(this.article);
                    obj.setIdmagasin(m);
                    obj.setEtat(true);
                    obj.setUnite(this.article.getUnite());
                    obj.setPrixVenteGros(article.getPrixunit());
                    obj.setPrixVenteDetail(article.getPrixVenteDetail());
                    obj.setQuantitesecurite(this.article.getQuantitesecurite());
                    this.magasinarticleFacadeLocal.create(obj);

                    if (!this.showLot) {
                        Magasinlot obj1 = new Magasinlot();
                        obj1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                        obj1.setIdmagasinarticle(obj);
                        obj1.setIdlot(this.lot);
                        obj1.setUnite(this.article.getUnite());
                        obj1.setEtat(true);
                        obj1.setPrixVenteGros(article.getPrixunit());
                        obj1.setPrixVenteDetail(article.getPrixVenteDetail());
                        obj1.setQuantitesecurite(this.article.getQuantitesecurite());
                        this.magasinlotFacadeLocal.create(obj1);
                    }
                }

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'article : " + this.article.getLibelle(), SessionMBean.getUserAccount());

                this.ut.commit();
                this.fournisseur = new Fournisseur();
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
                article.setIdunite(uniteFacadeLocal.find(unite.getIdunite()));

                this.ut.begin();
                this.articleFacadeLocal.edit(this.article);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification de l'article : " + this.article.getLibelle() + " Ancienne quantité : " + p.getQuantitestock() + " ; Nouvelle quantité : " + this.article.getQuantitestock(), SessionMBean.getUserAccount());
                this.ut.commit();

                selectedMagasins.forEach((m) -> {
                    Magasinarticle ma = this.magasinarticleFacadeLocal.findByIdmagasinIdarticle(m.getIdmagasin(), this.article.getIdarticle());
                    if (ma == null) {
                        ma = new Magasinarticle();
                        ma.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                        ma.setIdmagasin(m);
                        ma.setIdarticle(this.article);
                        ma.setEtat(true);
                        ma.setUnite(this.article.getUnite());
                        ma.setPrixVenteDetail(article.getPrixVenteDetail());
                        ma.setPrixVenteGros(article.getPrixunit());
                        this.magasinarticleFacadeLocal.create(ma);
                    }
                });

                this.modifier = this.supprimer = this.detail = true;
                article = new Article();

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
                if (!Utilitaires.isAccess(25L)) {
                    notifyError("acces_refuse");
                    return;
                }

                List<Lignedemande> listLd = this.lignedemandeFacadeLocal.findByIdarticle(article.getIdarticle());
                List<Lignelivraisonfournisseur> listLlf = this.lignelivraisonfournisseurFacadeLocal.findByIdarticle(this.article.getIdarticle());
                List<Lignetransfert> llt = lignetransfertFacadeLocal.findByIdarticle(article.getIdarticle());
                if (listLd.isEmpty() && listLlf.isEmpty() && llt.isEmpty()) {
                    this.ut.begin();
                    this.magasinlotFacadeLocal.removeAllByIdarticle(article.getIdarticle());
                    this.magasinarticleFacadeLocal.removeAllByIdarticle(article.getIdarticle());
                    lotFacadeLocal.deleteByIdarticle(article.getIdarticle());
                    this.articleFacadeLocal.remove(this.article);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'article : " + this.article.getLibelle(), SessionMBean.getUserAccount());
                    this.ut.commit();
                    this.article = new Article();
                    this.article.setIdunite(new Unite(0L));
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

    public void delete2() {
        try {
            if (this.article != null) {
                if (!Utilitaires.isAccess(25L)) {
                    notifyError("acces_refuse");
                    return;
                }

                //this.ut.begin();
                lignemvtstockFacadeLocal.deleteByIdarticle(article.getIdarticle());
                lignetransfertFacadeLocal.deleteByIdarticle(article.getIdarticle());
                lignedemandeFacadeLocal.deleteByIdarticle(article.getIdarticle());
                lignelivraisonfournisseurFacadeLocal.deleteByIdarticle(article.getIdarticle());
                lignelivraisonclientFacadeLocal.deleteByIdarticle(article.getIdarticle());
                ligneinventaireFacadeLocal.deleteByIdarticle(article.getIdarticle());

                this.magasinlotFacadeLocal.removeAllByIdarticle(article.getIdarticle());
                this.magasinarticleFacadeLocal.removeAllByIdarticle(article.getIdarticle());
                lotFacadeLocal.deleteByIdarticle(article.getIdarticle());

                this.articleFacadeLocal.remove(this.article);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'article : " + this.article.getLibelle(), SessionMBean.getUserAccount());
                //this.ut.commit();
                this.article = new Article();
                this.article.setIdunite(new Unite(0L));
                notifySuccess();

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
            if (!Utilitaires.isAccess(25L)) {
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
            if (!Utilitaires.isAccess(25l)) {
                notifyError("acces_refuse");
                return;
            }

            this.fileName2 = PrintUtils.printCritickStockReport(this.articles1);
            RequestContext.getCurrentInstance().execute("PF('StockImprimerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initInitialLotPrice(Magasinlot item) {
        item.setPrixVenteDetail(item.getIdlot().getIdarticle().getPrixVenteDetail());
        item.setPrixVenteGros(item.getIdlot().getIdarticle().getPrixunit());
    }

    public void initInitialArticlePrice(Magasinarticle item) {
        item.setPrixVenteDetail(item.getIdarticle().getPrixVenteDetail());
        item.setPrixVenteGros(item.getIdarticle().getPrixunit());
    }

    public void initInitialPrice(String option) {

        if (option.equals("lot")) {
            magasinlots.forEach(item -> {

                if (item.getPrixVenteGros() == 0 || item.getPrixVenteDetail() == 0) {
                    item.setPrixVenteDetail(item.getIdmagasinarticle().getIdarticle().getPrixVenteDetail());
                    item.setPrixVenteGros(item.getIdmagasinarticle().getIdarticle().getPrixunit());
                }
            });

            System.err.println("lot size" + magasinlots.size());
        }

        if (option.equals("article")) {
            magasinarticles.forEach(item -> {
                if (item.getPrixVenteDetail() == 0 || item.getPrixVenteGros() == 0) {
                    item.setPrixVenteDetail(item.getIdarticle().getPrixVenteDetail());
                    item.setPrixVenteGros(item.getIdarticle().getPrixunit());
                }

            });
            System.err.println("article size" + magasinarticles.size());
        }
    }

    public void prepareEditPrice(Article item) {
        //List<Lot> lots = item.getLotList();
        magasinlots.clear();
        magasinlots.addAll(magasinlotFacadeLocal.findByIdArticle(item.getIdarticle()));

        magasinarticles.clear();
        magasinarticles.addAll(item.getMagasinarticleList());

        RequestContext.getCurrentInstance().execute("PF('EditPriceProduitDialog').show()");
    }

    private void editListLot() {
        System.err.println("magasin lots " + magasinlots.size());
        magasinlots.forEach(item -> {
            magasinlotFacadeLocal.edit(item);
        });
    }

    private void editListArticle() {
        magasinarticles.forEach(item -> {
            magasinarticleFacadeLocal.edit(item);
        });
    }

    public void editPrice(String option) {

        switch (option) {
            case "total": {
                editListLot();
                editListArticle();
                break;
            }

            case "lot": {
                editListLot();
                break;
            }

            case "article": {
                editListArticle();
                break;
            }
        }

        magasinlots.clear();
        magasinarticles.clear();
        article = new Article();
        article.setIdUniteDetail(0);
        //RequestContext.getCurrentInstance().execute("PF('EditPriceProduitDialog').hide()");
    }

    public void prepareUploadPhoto(Article item) {
        this.article = item;
        this.filename = item.getPhoto();
        File f1 = new File(Utilitaires.path + "" + this.imageDir + "/" + item.getPhoto());
        if (!f1.isFile()) {
            File f2 = new File(SessionMBean.getParametrage().getRepertoireImageProduct() + File.separatorChar + "" + item.getPhoto());
            if (f2.exists()) {
                try {
                    Files.copy(f2, f1);
                    filename = item.getPhoto();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        HttpSession session = SessionMBean.getSession();
        session.setAttribute("product_to_upload_photo", item);
        RequestContext.getCurrentInstance().execute("PF('PhotoProduitDialog').show()");
    }

    public void handleFileUpload(FileUploadEvent event) {
        try {
            if ((event.getFile() == null) || (event.getFile().getFileName() == null) || (event.getFile().getFileName().equals(""))) {
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("nom_image_incorrect"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            if (!Utilitaires.estExtensionImage(Utilitaires.getExtension(event.getFile().getFileName()))) {
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("fichier_non_pris_en_charge"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            article = SessionMBean.getProductToUpLoadPhoto();
            String nom = "image_product_" + article.getCode().toLowerCase() + "_" + article.getIdarticle() + "." + Utilitaires.getExtension(event.getFile().getFileName());

            FileOutputStream out = new FileOutputStream(Utilitaires.path + "" + this.imageDir + "/" + nom, true);

            byte[] bytes = event.getFile().getContents();
            out.write(bytes);

            this.filename = nom;
            this.article.setPhoto(nom);
            this.articleFacadeLocal.edit(this.article);

            this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));

            File f1 = new File(Utilitaires.path + "" + this.imageDir + File.separatorChar + nom);
            File f2 = new File(SessionMBean.getParametrage().getRepertoireImageProduct() + File.separatorChar + "" + nom);
            //File f3 = new File(System.getProperty("user.dir") + File.separatorChar + "gescom" + File.separatorChar + "photo_products" + File.separatorChar + nom);
            Files.copy(f1, f2);
            //Files.copy(f1, f3);
            RequestContext.getCurrentInstance().execute("PF('PhotoProduitDialog').hide()");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("product_to_upload_photo");

            article = new Article();
            article.setIdUniteDetail(0l);
        } catch (IOException ex) {
            this.routine.catchException(ex, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void closeUploadDialog() {

        article = new Article();
        article.setIdUniteDetail(0l);

        RequestContext.getCurrentInstance().execute("PF('PhotoProduitDialog').hide()");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("product_to_upload_photo");
    }

    public void closeEditPriceDialog() {
        article = new Article();
        article.setIdUniteDetail(0);
        magasinarticles.clear();
        magasinlots.clear();
        this.deactiveButton();
        RequestContext.getCurrentInstance().execute("PF('EditPriceProduitDialog').hide()");
    }

    private void deactiveButton() {
        modifier = supprimer = detail = true;
    }

    public void synchronisePicture() {
        for (Article a : articles) {
            if (!a.getPhoto().contains("article.jpeg")) {
                File f1 = new File(Utilitaires.path + "" + this.imageDir + "/" + a.getPhoto());
                if (!f1.isFile()) {
                    File f2 = new File(SessionMBean.getParametrage().getRepertoireImageProduct() + File.separatorChar + "" + a.getPhoto());
                    if (f2.exists()) {
                        try {
                            Files.copy(f2, f1);
                            filename = a.getPhoto();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.redirect("/parametre/produit/produit.html");
    }

    private void redirect(String link) {
        try {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath() + link);
        } catch (Exception e) {
        }
    }

    public void nothing() {
        System.err.println("void");
    }
}
