package controllers.livraison_fournisseur;

import entities.Article;
import entities.Commandefournisseur;
import entities.Fournisseur;
import entities.Lignecommandefournisseur;
import entities.Lignelivraisonfournisseur;
import entities.Lignemvtstock;
import entities.Livraisonfournisseur;
import entities.Lot;
import entities.Mvtstock;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.CommandefournisseurFacadeLocal;
import sessions.FournisseurFacadeLocal;
import sessions.LignecommandefournisseurFacadeLocal;
import sessions.LignelivraisonfournisseurFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LivraisonfournisseurFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.MvtstockFacadeLocal;
import sessions.UniteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractLivraisonFournisseurController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected LivraisonfournisseurFacadeLocal livraisonfournisseurFacadeLocal;
    protected Livraisonfournisseur livraisonfournisseur = new Livraisonfournisseur();
    protected List<Livraisonfournisseur> livraisonfournisseurs = new ArrayList();

    @EJB
    protected LignelivraisonfournisseurFacadeLocal lignelivraisonfournisseurFacadeLocal;
    protected Lignelivraisonfournisseur lignelivraisonfournisseur = new Lignelivraisonfournisseur();
    protected List<Lignelivraisonfournisseur> lignelivraisonfournisseurs = new ArrayList();

    @EJB
    protected CommandefournisseurFacadeLocal commandefournisseurFacadeLocal;
    protected Commandefournisseur commandefournisseur = new Commandefournisseur();
    protected List<Commandefournisseur> commandefournisseurs = new ArrayList();

    @EJB
    protected LignecommandefournisseurFacadeLocal lignecommandefournisseurFacadeLocal;
    protected Lignecommandefournisseur lignecommandefournisseur = new Lignecommandefournisseur();
    protected List<Lignecommandefournisseur> lignecommandefournisseurs = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList();

    @EJB
    protected FournisseurFacadeLocal fournisseurFacadeLocal;
    protected Fournisseur fournisseur = new Fournisseur();

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
    protected List<Lignemvtstock> lignemvtstocks = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected List<Unite> unites = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected Double cout_quantite = (0.0D);
    protected Double total = (0.0D);

    protected Boolean showSelectorCommand = (true);

    protected Boolean detail = (true);
    protected Boolean modifier = (true);
    protected Boolean consulter = (true);
    protected Boolean imprimer = (true);
    protected Boolean supprimer = (true);

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

    public Boolean getShowSelectorCommand() {
        return this.showSelectorCommand;
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

    public Mvtstock getMvtstock() {
        return this.mvtstock;
    }

    public void setMvtstock(Mvtstock mvtstock) {
        this.mvtstock = mvtstock;
    }

    public List<Lignemvtstock> getLignemvtstocks() {
        return this.lignemvtstocks;
    }

    public Commandefournisseur getCommandefournisseur() {
        return this.commandefournisseur;
    }

    public void setCommandefournisseur(Commandefournisseur commandefournisseur) {
        this.commandefournisseur = commandefournisseur;
    }

    public List<Commandefournisseur> getCommandefournisseurs() {
        return this.commandefournisseurs;
    }

    public void setCommandefournisseurs(List<Commandefournisseur> commandefournisseurs) {
        this.commandefournisseurs = commandefournisseurs;
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

    public void setLignecommandefournisseurs(List<Lignecommandefournisseur> lignecommandefournisseurs) {
        this.lignecommandefournisseurs = lignecommandefournisseurs;
    }

    public Livraisonfournisseur getLivraisonfournisseur() {
        return this.livraisonfournisseur;
    }

    public void setLivraisonfournisseur(Livraisonfournisseur livraisonfournisseur) {
        this.livraisonfournisseur = livraisonfournisseur;
        this.modifier = (this.supprimer = this.detail = this.imprimer = (livraisonfournisseur == null));
    }

    public List<Livraisonfournisseur> getLivraisonfournisseurs() {
        try {
            this.livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.livraisonfournisseurs;
    }

    public Lignelivraisonfournisseur getLignelivraisonfournisseur() {
        return this.lignelivraisonfournisseur;
    }

    public List<Lignelivraisonfournisseur> getLignelivraisonfournisseurs() {
        return this.lignelivraisonfournisseurs;
    }

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }

    public List<Unite> getUnites() {
        this.unites = this.uniteFacadeLocal.findByStructure(SessionMBean.getParametrage().getId());
        return this.unites;
    }

}
