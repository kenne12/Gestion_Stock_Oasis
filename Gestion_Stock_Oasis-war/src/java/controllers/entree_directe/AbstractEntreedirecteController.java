package controllers.entree_directe;

import entities.Article;
import entities.Famille;
import entities.Fournisseur;
import entities.Lignelivraisonfournisseur;
import entities.Livraisonfournisseur;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Mvtstock;
import entities.Unite;
import entities.Utilisateur;
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
import utils.Utilitaires;

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
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    protected Magasinarticle magasinarticle = new Magasinarticle();
    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected Unite unite = new Unite();
    protected List<Unite> unites = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected String libelle_article = "-";

    protected Routine routine = new Routine();

    protected Double cout_quantite = 0.0D;
    protected Double total = 0.0D;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected String fileName = "";

    protected String mode = "";

    public Article getArticle() {
        /* 130 */ return this.article;
    }

    public void setArticle(Article article) {
        /* 134 */ this.article = article;
    }

    public List<Article> getArticles() {
        /* 138 */ return this.articles;
    }

    public void setArticles(List<Article> articles) {
        /* 142 */ this.articles = articles;
    }

    public Fournisseur getFournisseur() {
        /* 146 */ return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        /* 150 */ this.fournisseur = fournisseur;
    }

    public List<Fournisseur> getFournisseurs() {
        /* 154 */ this.fournisseurs = this.fournisseurFacadeLocal.findAllRange();
        /* 155 */ return this.fournisseurs;
    }

    public void setFournisseurs(List<Fournisseur> fournisseurs) {
        /* 159 */ this.fournisseurs = fournisseurs;
    }

    public Livraisonfournisseur getLivraisonfournisseur() {
        /* 163 */ return this.livraisonfournisseur;
    }

    public void setLivraisonfournisseur(Livraisonfournisseur livraisonfournisseur) {
        this.livraisonfournisseur = livraisonfournisseur;
        this.modifier = (this.supprimer = this.detail = this.imprimer = livraisonfournisseur == null);
    }

    public List<Livraisonfournisseur> getLivraisonfournisseurs() {
        /* 172 */ this.livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(true);
        /* 173 */ return this.livraisonfournisseurs;
    }

    public void setLivraisonfournisseurs(List<Livraisonfournisseur> livraisonfournisseurs) {
        /* 177 */ this.livraisonfournisseurs = livraisonfournisseurs;
    }

    public Lignelivraisonfournisseur getLignelivraisonfournisseur() {
        /* 181 */ return this.lignelivraisonfournisseur;
    }

    public void setLignelivraisonfournisseur(Lignelivraisonfournisseur lignelivraisonfournisseur) {
        /* 185 */ this.lignelivraisonfournisseur = lignelivraisonfournisseur;
    }

    public List<Lignelivraisonfournisseur> getLignelivraisonfournisseurs() {
        /* 189 */ return this.lignelivraisonfournisseurs;
    }

    public void setLignelivraisonfournisseurs(List<Lignelivraisonfournisseur> lignelivraisonfournisseurs) {
        /* 193 */ this.lignelivraisonfournisseurs = lignelivraisonfournisseurs;
    }

    public Boolean getDetail() {
        /* 197 */ return this.detail;
    }

    public void setDetail(Boolean detail) {
        /* 201 */ this.detail = detail;
    }

    public Boolean getModifier() {
        /* 205 */ return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        /* 209 */ this.modifier = modifier;
    }

    public Boolean getConsulter() {
        /* 213 */ return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        /* 217 */ this.consulter = consulter;
    }

    public Boolean getImprimer() {
        /* 221 */ return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        /* 225 */ this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        /* 229 */ return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        /* 233 */ this.supprimer = supprimer;
    }

    public Famille getFamille() {
        /* 237 */ return this.famille;
    }

    public void setFamille(Famille famille) {
        /* 241 */ this.famille = famille;
    }

    public List<Famille> getFamilles() {
        /* 245 */ this.familles = this.familleFacadeLocal.findAllRange();
        /* 246 */ return this.familles;
    }

    public void setFamilles(List<Famille> familles) {
        /* 250 */ this.familles = familles;
    }

    public Double getCout_quantite() {
        /* 254 */ return this.cout_quantite;
    }

    public void setCout_quantite(Double cout_quantite) {
        /* 258 */ this.cout_quantite = cout_quantite;
    }

    public Double getTotal() {
        /* 262 */ return this.total;
    }

    public void setTotal(Double total) {
        /* 266 */ this.total = total;
    }

    public String getFileName() {
        /* 270 */ return this.fileName;
    }

    public Lot getLot() {
        /* 274 */ return this.lot;
    }

    public void setLot(Lot lot) {
        /* 278 */ this.lot = lot;
    }

    public List<Lot> getLots() {
        /* 282 */ return this.lots;
    }

    public Routine getRoutine() {
        /* 286 */ return this.routine;
    }

    public Unite getUnite() {
        /* 290 */ return this.unite;
    }

    public void setUnite(Unite unite) {
        /* 294 */ this.unite = unite;
    }

    public List<Unite> getUnites() {
        /* 298 */ this.unites = this.uniteFacadeLocal.findAllRange();
        /* 299 */ return this.unites;
    }

    public void setUnites(List<Unite> unites) {
        /* 303 */ this.unites = unites;
    }

    public Magasin getMagasin() {
        /* 307 */ return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        /* 311 */ this.magasin = magasin;
    }

    public List<Magasin> getMagasins() {
        /* 315 */ this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
        /* 316 */ return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        /* 320 */ this.magasins = magasins;
    }

    public Magasinarticle getMagasinarticle() {
        /* 324 */ return this.magasinarticle;
    }

    public void setMagasinarticle(Magasinarticle magasinarticle) {
        /* 328 */ this.magasinarticle = magasinarticle;
    }

    public List<Magasinarticle> getMagasinarticles() {
        /* 332 */ return this.magasinarticles;
    }

    public void setMagasinarticles(List<Magasinarticle> magasinarticles) {
        /* 336 */ this.magasinarticles = magasinarticles;
    }

    public String getLibelle_article() {
        /* 340 */ return this.libelle_article;
    }

    public void setLibelle_article(String libelle_article) {
        /* 344 */ this.libelle_article = libelle_article;
    }

    public Fournisseur getFournisseurToSave() {
        return fournisseurToSave;
    }

    public void setFournisseurToSave(Fournisseur fournisseurToSave) {
        this.fournisseurToSave = fournisseurToSave;
    }

}
