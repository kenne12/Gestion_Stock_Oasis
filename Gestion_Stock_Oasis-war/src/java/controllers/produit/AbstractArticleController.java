package controllers.produit;

import entities.Article;
import entities.Famille;
import entities.Fournisseur;
import entities.Lot;
import entities.Magasin;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.FamilleFacadeLocal;
import sessions.FournisseurFacadeLocal;
import sessions.LignedemandeFacadeLocal;
import sessions.LigneinventaireFacadeLocal;
import sessions.LignelivraisonclientFacadeLocal;
import sessions.LignelivraisonfournisseurFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LignetransfertFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.UniteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractArticleController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList<>();
    protected List<Article> articles1 = new ArrayList<>();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    protected List<Magasin> magasins = new ArrayList<>();
    protected List<Magasin> selectedMagasins = new ArrayList<>();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;

    @EJB
    protected FournisseurFacadeLocal fournisseurFacadeLocal;
    protected Fournisseur fournisseur;
    protected List<Fournisseur> fournisseurs = new ArrayList();

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    protected Famille famille = new Famille();
    protected List<Famille> familles = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected List<Unite> unites = new ArrayList();
    protected Unite unite = new Unite(0l);

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected LignedemandeFacadeLocal lignedemandeFacadeLocal;

    @EJB
    protected LignetransfertFacadeLocal lignetransfertFacadeLocal;

    @EJB
    protected LignelivraisonfournisseurFacadeLocal lignelivraisonfournisseurFacadeLocal;

    @EJB
    protected LignelivraisonclientFacadeLocal lignelivraisonclientFacadeLocal;

    @EJB
    protected LigneinventaireFacadeLocal ligneinventaireFacadeLocal;

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;

    protected String chemin_replicated_images = SessionMBean.getParametrage().getRepertoireLogo();
    protected String filename = "";
    protected String imageDir = "/photos/products";

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();
    protected List<String> password = new ArrayList();
    protected String sessionPassword = "";

    protected Boolean session = true;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected boolean showQuantiteDosage = SessionMBean.getParametrage().getEtatQuantiteDosage();
    protected boolean showFormeStockage = SessionMBean.getParametrage().getEtatFormeStockage();
    protected boolean showUser = SessionMBean.getParametrage().isEtatuser();
    protected boolean showBailleur = SessionMBean.getParametrage().isEtatbailleur();

    protected boolean showLot = true;

    protected String mode = "";

    protected String fileName = "";
    protected String fileName1 = "";
    protected String fileName2 = "";

    public Boolean getDetail() {
        return this.detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        this.consulter = consulter;
    }

    public Boolean getImprimer() {
        this.imprimer = this.articleFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId()).isEmpty();
        return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.modifier = this.supprimer = this.detail = fournisseur == null;
        this.fournisseur = fournisseur;
    }

    public List<Fournisseur> getFournisseurs() {
        this.fournisseurs = this.fournisseurFacadeLocal.findByIdstructure(SessionMBean.getMagasin().getParametrage().getId());
        return this.fournisseurs;
    }

    public void setFournisseurs(List<Fournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
    }

    public Famille getFamille() {
        return this.famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }

    public List<Famille> getFamilles() {
        this.familles = this.familleFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.familles;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.modifier = (this.supprimer = this.detail = (article == null));
        this.article = article;
    }

    public List<Article> getArticles() {
        this.articles = this.articleFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.articles;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName1() {
        return this.fileName1;
    }

    public void setFileName1(String fileName1) {
        this.fileName1 = fileName1;
    }

    public List<Article> getArticles1() {
        return this.articles1;
    }

    public void setArticles1(List<Article> articles1) {
        this.articles1 = articles1;
    }

    public String getFileName2() {
        return this.fileName2;
    }

    public void setFileName2(String fileName2) {
        this.fileName2 = fileName2;
    }

    public Boolean getSession() {
        try {
            if (SessionMBean.getSession1()) {
                this.session = false;
            } else {
                this.session = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.session;
    }

    public void setSession(Boolean session) {
        this.session = session;
    }

    public String getSessionPassword() {
        return this.sessionPassword;
    }

    public void setSessionPassword(String sessionPassword) {
        this.sessionPassword = sessionPassword;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public List<Unite> getUnites() {
        this.unites = this.uniteFacadeLocal.findByStructure(SessionMBean.getParametrage().getId());
        return this.unites;
    }

    public boolean isShowQuantiteDosage() {
        return this.showQuantiteDosage;
    }

    public boolean isShowFormeStockage() {
        return this.showFormeStockage;
    }

    public Lot getLot() {
        return this.lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public boolean isShowLot() {
        return this.showLot;
    }

    public boolean isShowUser() {
        return this.showUser;
    }

    public boolean isShowBailleur() {
        return this.showBailleur;
    }

    public String getMode() {
        return this.mode;
    }

    public List<Magasin> getMagasins() {
        this.magasins = this.magasinFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
    }

    public List<Magasin> getSelectedMagasins() {
        return this.selectedMagasins;
    }

    public void setSelectedMagasins(List<Magasin> selectedMagasins) {
        this.selectedMagasins = selectedMagasins;
    }

    public String getChemin_replicated_images() {
        return chemin_replicated_images;
    }

    public void setChemin_replicated_images(String chemin_replicated_images) {
        this.chemin_replicated_images = chemin_replicated_images;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Unite getUnite() {
        return unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

}
