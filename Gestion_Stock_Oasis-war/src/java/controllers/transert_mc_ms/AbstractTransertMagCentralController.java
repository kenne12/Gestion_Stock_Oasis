package controllers.transert_mc_ms;

import entities.Article;
import entities.Famille;
import entities.Lignemvtstock;
import entities.Lignerepartitionarticle;
import entities.Lignerepartitiontemp;
import entities.Lot;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import entities.Repartitionarticle;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.FamilleFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LignerepartitionarticleFacadeLocal;
import sessions.LignerepartitiontempFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.MvtstockFacadeLocal;
import sessions.RepartitionarticleFacadeLocal;
import sessions.UniteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractTransertMagCentralController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected RepartitionarticleFacadeLocal repartitionarticleFacadeLocal;
    protected Repartitionarticle repartitionarticle = new Repartitionarticle();
    protected List<Repartitionarticle> repartitionarticles = new ArrayList();

    @EJB
    protected LignerepartitionarticleFacadeLocal lignerepartitionarticleFacadeLocal;
    protected List<Lignerepartitionarticle> lignerepartitionarticles = new ArrayList<>();
    protected List<Lignerepartitionarticle> lignerepartitionarticles_1 = new ArrayList<>();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList();

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    protected Famille famille = new Famille();
    protected List<Famille> familles = new ArrayList();

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected List<Magasinlot> magasinlots = new ArrayList();
    protected List<Magasinlot> selectedMagasinlots = new ArrayList();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected Unite unite = new Unite();
    protected List<Unite> unites = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
    protected List<Lignemvtstock> lignemvtstocks = new ArrayList();

    @EJB
    protected LignerepartitiontempFacadeLocal lignerepartitiontempFacadeLocal;
    protected List<Lignerepartitiontemp> lignerepartitiontemps = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected Double cout_quantite = 0d;
    protected Double total = 0d;

    protected int state_zero = 0;

    protected Boolean showSelectorCommand = true;
    protected boolean payement_credit = false;

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

    public boolean isPayement_credit() {
        return this.payement_credit;
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

    public Repartitionarticle getRepartitionarticle() {
        return this.repartitionarticle;
    }

    public void setRepartitionarticle(Repartitionarticle repartitionarticle) {
        this.repartitionarticle = repartitionarticle;
        this.modifier = (this.supprimer = this.detail = this.imprimer = (repartitionarticle == null));
    }

    public List<Repartitionarticle> getRepartitionarticles() {
        this.repartitionarticles = this.repartitionarticleFacadeLocal.findAllRange();
        return this.repartitionarticles;
    }

    public void setRepartitionarticles(List<Repartitionarticle> repartitionarticles) {
        this.repartitionarticles = repartitionarticles;
    }

    public List<Lignerepartitionarticle> getLignerepartitionarticles() {
        return this.lignerepartitionarticles;
    }

    public void setLignerepartitionarticles(List<Lignerepartitionarticle> lignerepartitionarticles) {
        this.lignerepartitionarticles = lignerepartitionarticles;
    }

    public List<Magasinarticle> getMagasinarticles() {
        return this.magasinarticles;
    }

    public void setMagasinarticles(List<Magasinarticle> magasinarticles) {
        this.magasinarticles = magasinarticles;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public void setMagasinlots(List<Magasinlot> magasinlots) {
        this.magasinlots = magasinlots;
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

    public void setFamilles(List<Famille> familles) {
        this.familles = familles;
    }

    public List<Lignerepartitionarticle> getLignerepartitionarticles_1() {
        return this.lignerepartitionarticles_1;
    }

    public void setLignerepartitionarticles_1(List<Lignerepartitionarticle> lignerepartitionarticles_1) {
        this.lignerepartitionarticles_1 = lignerepartitionarticles_1;
    }

    public List<Lignerepartitiontemp> getLignerepartitiontemps() {
        return this.lignerepartitiontemps;
    }

    public void setLignerepartitiontemps(List<Lignerepartitiontemp> lignerepartitiontemps) {
        this.lignerepartitiontemps = lignerepartitiontemps;
    }

    public List<Magasinlot> getSelectedMagasinlots() {
        return this.selectedMagasinlots;
    }

    public void setSelectedMagasinlots(List<Magasinlot> selectedMagasinlots) {
        this.selectedMagasinlots = selectedMagasinlots;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public List<Unite> getUnites() {
        this.unites = this.uniteFacadeLocal.findByStructure(SessionMBean.getParametrage().getId());
        return this.unites;
    }

    public int getState_zero() {
        return this.state_zero;
    }
}
