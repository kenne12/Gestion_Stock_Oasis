package controllers.commande_fournisseur;

import entities.Article;
import entities.Commandefournisseur;
import entities.Famille;
import entities.Fournisseur;
import entities.Lignecommandefournisseur;
import entities.Lot;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.CommandefournisseurFacadeLocal;
import sessions.FamilleFacadeLocal;
import sessions.FournisseurFacadeLocal;
import sessions.LignecommandefournisseurFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.UniteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractCommandefournisseurController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected CommandefournisseurFacadeLocal commandefournisseurFacadeLocal;
    protected Commandefournisseur commandefournisseur = new Commandefournisseur();
    protected List<Commandefournisseur> commandefournisseurs = new ArrayList();

    @EJB
    protected LignecommandefournisseurFacadeLocal lignecommandefournisseurFacadeLocal;
    protected Lignecommandefournisseur lignecommandefournisseur = new Lignecommandefournisseur();
    protected List<Lignecommandefournisseur> lignecommandefournisseurs = new ArrayList();

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    protected Famille famille = new Famille();
    protected List<Famille> familles = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList();

    @EJB
    protected FournisseurFacadeLocal fournisseurFacadeLocal;
    protected Fournisseur fournisseur = new Fournisseur();
    protected List<Fournisseur> fournisseurs = new ArrayList();

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected Unite unite = new Unite();
    protected List<Unite> unites = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected Double cout_quantite = 0d;
    protected Double total = 0d;

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
        this.familles = this.familleFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
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

    public Commandefournisseur getCommandefournisseur() {
        return this.commandefournisseur;
    }

    public void setCommandefournisseur(Commandefournisseur commandefournisseur) {
        this.commandefournisseur = commandefournisseur;
        this.modifier = (this.supprimer = this.detail = this.imprimer = (commandefournisseur == null));
    }

    public List<Commandefournisseur> getCommandefournisseurs() {
        try {
            this.commandefournisseurs = this.commandefournisseurFacadeLocal.findAllRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.commandefournisseurs;
    }

    public Lignecommandefournisseur getLignecommandefournisseur() {
        return this.lignecommandefournisseur;
    }

    public void setLignecommandefournisseur(Lignecommandefournisseur lignecommandefournisseur) {
        this.lignecommandefournisseur = lignecommandefournisseur;
    }

    public List<Lignecommandefournisseur> getLignecommandefournisseurs() {
        return this.lignecommandefournisseurs;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<Fournisseur> getFournisseurs() {
        this.fournisseurs = this.fournisseurFacadeLocal.findAllRange();
        return this.fournisseurs;
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
}
