package controllers.livraison_client;

import entities.Article;
import entities.Demande;
import entities.Lignedemande;
import entities.Lignelivraisonclient;
import entities.Lignemvtstock;
import entities.Livraisonclient;
import entities.Lot;
import entities.Mvtstock;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.DemandeFacadeLocal;
import sessions.LignedemandeFacadeLocal;
import sessions.LignelivraisonclientFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LivraisonclientFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.MvtstockFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.UniteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractLivraisonClientController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected DemandeFacadeLocal demandeFacadeLocal;
    protected Demande demande = new Demande();
    protected List<Demande> demandes = new ArrayList();
    protected List<Demande> demandes_1 = new ArrayList();

    @EJB
    protected LignedemandeFacadeLocal lignedemandeFacadeLocal;
    protected Lignedemande lignedemande = new Lignedemande();
    protected List<Lignedemande> lignedemandes = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList();

    @EJB
    protected PersonnelFacadeLocal personnelFacadeLocal;

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;

    @EJB
    protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;
    protected Livraisonclient livraisonclient = new Livraisonclient();
    protected List<Livraisonclient> livraisonclients = new ArrayList<>();

    @EJB
    protected LignelivraisonclientFacadeLocal lignelivraisonclientFacadeLocal;
    protected Lignelivraisonclient lignelivraisonclient = new Lignelivraisonclient();
    protected List<Lignelivraisonclient> lignelivraisonclients = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected Unite unite = new Unite();
    protected List<Unite> unites = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;

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
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected Double total = 0.0;

    protected Boolean showSelectorCommand = true;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected String fileName = "";
    protected String printDialogTitle = "";

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

    public Livraisonclient getLivraisonclient() {
        return this.livraisonclient;
    }

    public void setLivraisonclient(Livraisonclient livraisonclient) {
        this.livraisonclient = livraisonclient;
        this.modifier = this.supprimer = this.detail = this.imprimer = (livraisonclient == null);
    }

    public List<Livraisonclient> getLivraisonclients() {
        this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin());
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

    public Demande getDemande() {
        return this.demande;
    }

    public void setDemande(Demande demande) {
        this.demande = demande;
    }

    public List<Demande> getDemandes() {
        return this.demandes;
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

    public Mvtstock getMvtstock() {
        return this.mvtstock;
    }

    public void setMvtstock(Mvtstock mvtstock) {
        this.mvtstock = mvtstock;
    }

    public List<Lignemvtstock> getLignemvtstocks() {
        return this.lignemvtstocks;
    }

    public String getPrintDialogTitle() {
        return this.printDialogTitle;
    }

    public void setPrintDialogTitle(String printDialogTitle) {
        this.printDialogTitle = printDialogTitle;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public List<Unite> getUnites() {
        return this.unites;
    }

    public List<Demande> getDemandes_1() {
        return this.demandes_1;
    }

    public void setDemandes_1(List<Demande> demandes_1) {
        this.demandes_1 = demandes_1;
    }

}
