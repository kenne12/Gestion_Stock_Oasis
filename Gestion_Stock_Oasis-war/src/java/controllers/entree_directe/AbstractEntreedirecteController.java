package controllers.entree_directe;

import entities.Article;
import entities.Famille;
import entities.Fournisseur;
import entities.Lignelivraisonfournisseur;
import entities.Livraisonfournisseur;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.FamilleFacadeLocal;
import sessions.FournisseurFacadeLocal;
import sessions.LignelivraisonfournisseurFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LivraisonfournisseurFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.MvtstockFacadeLocal;
import sessions.UniteFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractEntreedirecteController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    protected Famille famille = new Famille();
    protected List<Famille> familles = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList();

    @EJB
    protected LivraisonfournisseurFacadeLocal livraisonfournisseurFacadeLocal;
    protected Livraisonfournisseur livraisonfournisseur = new Livraisonfournisseur();
    protected List<Livraisonfournisseur> livraisonfournisseurs = new ArrayList();

    @EJB
    protected LignelivraisonfournisseurFacadeLocal lignelivraisonfournisseurFacadeLocal;
    protected Lignelivraisonfournisseur lignelivraisonfournisseur = new Lignelivraisonfournisseur();
    protected List<Lignelivraisonfournisseur> lignelivraisonfournisseurs = new ArrayList();

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected FournisseurFacadeLocal fournisseurFacadeLocal;
    protected Fournisseur fournisseur = new Fournisseur();
    protected Fournisseur fournisseurToSave = new Fournisseur();
    protected List<Fournisseur> fournisseurs = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = SessionMBean.getMagasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    protected Magasinarticle magasinarticle = new Magasinarticle();
    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected List<Unite> unites = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected Magasinlot magasinlot = new Magasinlot();
    protected List<Magasinlot> magasinlots = new ArrayList<>();

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected String libelle_article = "-";

    protected Routine routine = new Routine();

    protected Double total = 0d;

    //protected double qty = 0;
    protected boolean perempted = false;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected String fileName = "";

    protected String mode = "";

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<Fournisseur> getFournisseurs() {
        this.fournisseurs = this.fournisseurFacadeLocal
                .findByIdstructure(SessionMBean.getParametrage().getId());
        return this.fournisseurs;
    }

    public Livraisonfournisseur getLivraisonfournisseur() {
        return this.livraisonfournisseur;
    }

    public void setLivraisonfournisseur(Livraisonfournisseur livraisonfournisseur) {
        this.livraisonfournisseur = livraisonfournisseur;
        this.modifier = (this.supprimer = this.detail = this.imprimer = livraisonfournisseur == null);
    }

    public List<Livraisonfournisseur> getLivraisonfournisseurs() {
        return this.livraisonfournisseurs;
    }

    public Lignelivraisonfournisseur getLignelivraisonfournisseur() {
        return this.lignelivraisonfournisseur;
    }

    public void setLignelivraisonfournisseur(Lignelivraisonfournisseur lignelivraisonfournisseur) {
        this.lignelivraisonfournisseur = lignelivraisonfournisseur;
    }

    public List<Lignelivraisonfournisseur> getLignelivraisonfournisseurs() {
        return this.lignelivraisonfournisseurs;
    }

    public void setLignelivraisonfournisseurs(List<Lignelivraisonfournisseur> lignelivraisonfournisseurs) {
        this.lignelivraisonfournisseurs = lignelivraisonfournisseurs;
    }

    public Boolean getDetail() {
        return this.detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Famille getFamille() {
        return this.famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }

    public List<Famille> getFamilles() {
        this.familles = this.familleFacadeLocal
                .findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.familles;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Lot getLot() {
        return this.lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public List<Lot> getLots() {
        return this.lots;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public List<Unite> getUnites() {
        this.unites = this.uniteFacadeLocal.findByStructure(SessionMBean.getParametrage().getId());
        return this.unites;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public List<Magasin> getMagasins() {
        return this.magasins;
    }

    public Magasinarticle getMagasinarticle() {
        return this.magasinarticle;
    }

    public void setMagasinarticle(Magasinarticle magasinarticle) {
        this.magasinarticle = magasinarticle;
    }

    public List<Magasinarticle> getMagasinarticles() {
        return this.magasinarticles;
    }

    public String getLibelle_article() {
        return this.libelle_article;
    }

    public void setLibelle_article(String libelle_article) {
        this.libelle_article = libelle_article;
    }

    public Fournisseur getFournisseurToSave() {
        return fournisseurToSave;
    }

    public void setFournisseurToSave(Fournisseur fournisseurToSave) {
        this.fournisseurToSave = fournisseurToSave;
    }

    public Magasinlot getMagasinlot() {
        return magasinlot;
    }

    public void setMagasinlot(Magasinlot magasinlot) {
        this.magasinlot = magasinlot;
    }

    public List<Magasinlot> getMagasinlots() {
        return magasinlots;
    }

    public boolean isPerempted() {
        return perempted;
    }

}
