package controllers.demande_intrant;

import entities.Article;
import entities.Demande;
import entities.Famille;
import entities.Lignedemande;
import entities.Lignelivraisonclient;
import entities.Livraisonclient;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Personnel;
import entities.Projet;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.DemandeFacadeLocal;
import sessions.FamilleFacadeLocal;
import sessions.LignedemandeFacadeLocal;
import sessions.LignelivraisonclientFacadeLocal;
import sessions.LivraisonclientFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.ProjetFacadeLocal;
import sessions.UniteFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractCommandePersonnelController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected ProjetFacadeLocal projetFacadeLocal;
    protected Projet projet = new Projet();
    protected List<Projet> projets = new ArrayList<>();

    @EJB
    protected DemandeFacadeLocal demandeFacadeLocal;
    protected Demande demande = new Demande();
    protected List<Demande> demandes = new ArrayList<>();

    @EJB
    protected LignedemandeFacadeLocal lignedemandeFacadeLocal;
    protected Lignedemande lignedemande = new Lignedemande();
    protected List<Lignedemande> lignedemandes = new ArrayList();

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    protected Famille famille = new Famille();
    protected List<Famille> familles = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    protected Magasinarticle magasinarticle = new Magasinarticle();
    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected Magasinlot magasinlot = new Magasinlot();
    protected List<Magasinlot> magasinlots = new ArrayList();

    @EJB
    protected PersonnelFacadeLocal personnelFacadeLocal;
    protected Personnel personnel = new Personnel();
    protected List<Personnel> personnels = new ArrayList();

    @EJB
    protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;
    protected Livraisonclient livraisonclient = new Livraisonclient();
    protected List<Livraisonclient> livraisonclients = new ArrayList();

    @EJB
    protected LignelivraisonclientFacadeLocal lignelivraisonclientFacadeLocal;
    protected Lignelivraisonclient lignelivraisonclient = new Lignelivraisonclient();
    protected List<Lignelivraisonclient> lignelivraisonclients = new ArrayList();

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected Unite unite = new Unite();
    protected List<Unite> unites = new ArrayList();

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected String libelle_article = "-";

    protected Double cout_quantite = 0.0D;
    protected Double total = 0.0D;

    protected Boolean showSelector = true;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected String fileName = "";

    protected String mode = "";

    int conteur = 0;

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public Personnel getPersonnel() {
        return this.personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public List<Personnel> getPersonnels() {
        this.personnels = this.personnelFacadeLocal.findAllRange();
        return this.personnels;
    }

    public Livraisonclient getLivraisonclient() {
        return this.livraisonclient;
    }

    public void setLivraisonclient(Livraisonclient livraisonclient) {
        this.livraisonclient = livraisonclient;
    }

    public List<Livraisonclient> getLivraisonclients() {
        try {
            this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.livraisonclients;
    }

    public Lignelivraisonclient getLignelivraisonclient() {
        return this.lignelivraisonclient;
    }

    public void setLignelivraisonclient(Lignelivraisonclient lignelivraisonclient) {
        this.lignelivraisonclient = lignelivraisonclient;
    }

    public List<Lignelivraisonclient> getLignelivraisonclients() {
        return this.lignelivraisonclients;
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
        this.familles = this.familleFacadeLocal.findAllRange();
        return this.familles;
    }

    public Double getCout_quantite() {
        return this.cout_quantite;
    }

    public void setCout_quantite(Double cout_quantite) {
        this.cout_quantite = cout_quantite;
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

    public Boolean getShowSelector() {
        return this.showSelector;
    }

    public Routine getRoutine() {
        return this.routine;
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

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<Projet> getProjets() {
        return this.projets;
    }

    public Lignedemande getLignedemande() {
        return this.lignedemande;
    }

    public void setLignedemande(Lignedemande lignedemande) {
        this.lignedemande = lignedemande;
    }

    public List<Lignedemande> getLignedemandes() {
        return this.lignedemandes;
    }

    public Demande getDemande() {
        return this.demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
        this.modifier = (this.supprimer = this.detail = this.imprimer = (demande == null));
    }

    public List<Demande> getDemandes() {
        this.demandes = this.demandeFacadeLocal.findAllRange(SessionMBean.getUserAccount().getIdpersonnel().getIdpersonnel());
        return this.demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
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

    public void setMagasinarticles(List<Magasinarticle> magasinarticles) {
        this.magasinarticles = magasinarticles;
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

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public List<Unite> getUnites() {
        this.unites = this.uniteFacadeLocal.findAllRange();
        return this.unites;
    }

    public void setUnites(List<Unite> unites) {
        this.unites = unites;
    }

    public Magasinlot getMagasinlot() {
        return this.magasinlot;
    }

    public void setMagasinlot(Magasinlot magasinlot) {
        this.magasinlot = magasinlot;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public void setMagasinlots(List<Magasinlot> magasinlots) {
        this.magasinlots = magasinlots;
    }

    public String getLibelle_article() {
        return this.libelle_article;
    }
}
