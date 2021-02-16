 package controllers.transert_mc_ms;
 
import com.sun.javafx.scene.control.skin.VirtualFlow;
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
 
 public class AbstractTransertMagCentralController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected RepartitionarticleFacadeLocal repartitionarticleFacadeLocal;
/*  49 */   protected Repartitionarticle repartitionarticle = new Repartitionarticle();
/*  50 */   protected List<Repartitionarticle> repartitionarticles = new ArrayList();
 
   @EJB
   protected LignerepartitionarticleFacadeLocal lignerepartitionarticleFacadeLocal;
/*  54 */   protected List<Lignerepartitionarticle> lignerepartitionarticles = new ArrayList<>();
/*  55 */   protected List<Lignerepartitionarticle> lignerepartitionarticles_1 = new ArrayList<>();
 
   @EJB
   protected ArticleFacadeLocal articleFacadeLocal;
/*  59 */   protected Article article = new Article();
/*  60 */   protected List<Article> articles = new ArrayList();
 
   @EJB
   protected FamilleFacadeLocal familleFacadeLocal;
/*  64 */   protected Famille famille = new Famille();
/*  65 */   protected List<Famille> familles = new ArrayList();
 
   @EJB
   protected LotFacadeLocal lotFacadeLocal;
/*  69 */   protected Lot lot = new Lot();
/*  70 */   protected List<Lot> lots = new ArrayList();
 
   @EJB
   protected MagasinlotFacadeLocal magasinlotFacadeLocal;
/*  74 */   protected List<Magasinlot> magasinlots = new ArrayList();
/*  75 */   protected List<Magasinlot> selectedMagasinlots = new ArrayList();
 
   @EJB
   protected UniteFacadeLocal uniteFacadeLocal;
/*  79 */   protected Unite unite = new Unite();
/*  80 */   protected List<Unite> unites = new ArrayList();
 
   @EJB
   protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
/*  85 */   protected List<Magasinarticle> magasinarticles = new ArrayList();
 
   @EJB
   protected MvtstockFacadeLocal mvtstockFacadeLocal;
/*  89 */   protected Mvtstock mvtstock = new Mvtstock();
 
   @EJB
   protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
/*  93 */   protected List<Lignemvtstock> lignemvtstocks = new ArrayList();
 
   @EJB
   protected LignerepartitiontempFacadeLocal lignerepartitiontempFacadeLocal;
   protected List<Lignerepartitiontemp> lignerepartitiontemps = new ArrayList<>();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
  protected Routine routine = new Routine();
 
   protected Double cout_quantite = Double.valueOf(0.0D);
  protected Double total = Double.valueOf(0.0D);
 
/* 107 */   protected int state_zero = 0;
 
/* 109 */   protected Boolean showSelectorCommand = Boolean.valueOf(true);
/* 110 */   protected boolean payement_credit = false;
 
/* 112 */   protected Boolean detail = Boolean.valueOf(true);
/* 113 */   protected Boolean modifier = Boolean.valueOf(true);
/* 114 */   protected Boolean consulter = Boolean.valueOf(true);
/* 115 */   protected Boolean imprimer = Boolean.valueOf(true);
/* 116 */   protected Boolean supprimer = Boolean.valueOf(true);
 
/* 118 */   protected String fileName = "";
/* 119 */   protected String printDialogTitle = "";
 
/* 121 */   protected String mode = "";
 
   public Article getArticle() {
/* 124 */     return this.article;
   }
 
   public void setArticle(Article article) {
/* 128 */     this.article = article;
   }
 
   public List<Article> getArticles() {
/* 132 */     return this.articles;
   }
 
   public Boolean getDetail() {
/* 136 */     return this.detail;
   }
 
   public Boolean getModifier() {
/* 140 */     return this.modifier;
   }
 
   public Boolean getConsulter() {
/* 144 */     return this.consulter;
   }
 
   public Boolean getImprimer() {
/* 148 */     return this.imprimer;
   }
 
   public Boolean getSupprimer() {
/* 152 */     return this.supprimer;
   }
 
   public Double getCout_quantite() {
/* 156 */     return this.cout_quantite;
   }
 
   public void setCout_quantite(Double cout_quantite) {
/* 160 */     this.cout_quantite = cout_quantite;
   }
 
   public Double getTotal() {
/* 164 */     return this.total;
   }
 
   public void setTotal(Double total) {
/* 168 */     this.total = total;
   }
 
   public String getFileName() {
/* 172 */     return this.fileName;
   }
 
   public Boolean getShowSelectorCommand() {
/* 176 */     return this.showSelectorCommand;
   }
 
   public Routine getRoutine() {
/* 180 */     return this.routine;
   }
 
   public Lot getLot() {
/* 184 */     return this.lot;
   }
 
   public void setLot(Lot lot) {
/* 188 */     this.lot = lot;
   }
 
   public List<Lot> getLots() {
/* 192 */     return this.lots;
   }
 
   public boolean isPayement_credit() {
/* 196 */     return this.payement_credit;
   }
 
   public Mvtstock getMvtstock() {
/* 200 */     return this.mvtstock;
   }
 
   public void setMvtstock(Mvtstock mvtstock) {
/* 204 */     this.mvtstock = mvtstock;
   }
 
   public List<Lignemvtstock> getLignemvtstocks() {
/* 208 */     return this.lignemvtstocks;
   }
 
   public String getPrintDialogTitle() {
/* 212 */     return this.printDialogTitle;
   }
 
   public void setPrintDialogTitle(String printDialogTitle) {
/* 216 */     this.printDialogTitle = printDialogTitle;
   }
 
   public Repartitionarticle getRepartitionarticle() {
/* 220 */     return this.repartitionarticle;
   }
 
   public void setRepartitionarticle(Repartitionarticle repartitionarticle) {
/* 224 */     this.repartitionarticle = repartitionarticle;
/* 225 */     this.modifier = (this.supprimer = this.detail = this.imprimer = Boolean.valueOf(repartitionarticle == null));
   }
 
   public List<Repartitionarticle> getRepartitionarticles() {
/* 229 */     this.repartitionarticles = this.repartitionarticleFacadeLocal.findAllRange();
/* 230 */     return this.repartitionarticles;
   }
 
   public void setRepartitionarticles(List<Repartitionarticle> repartitionarticles) {
/* 234 */     this.repartitionarticles = repartitionarticles;
   }
 
   public List<Lignerepartitionarticle> getLignerepartitionarticles() {
/* 238 */     return this.lignerepartitionarticles;
   }
 
   public void setLignerepartitionarticles(List<Lignerepartitionarticle> lignerepartitionarticles) {
/* 242 */     this.lignerepartitionarticles = lignerepartitionarticles;
   }
 
   public List<Magasinarticle> getMagasinarticles() {
/* 246 */     return this.magasinarticles;
   }
 
   public void setMagasinarticles(List<Magasinarticle> magasinarticles) {
/* 250 */     this.magasinarticles = magasinarticles;
   }
 
   public List<Magasinlot> getMagasinlots() {
/* 254 */     return this.magasinlots;
   }
 
   public void setMagasinlots(List<Magasinlot> magasinlots) {
/* 258 */     this.magasinlots = magasinlots;
   }
 
   public Famille getFamille() {
/* 262 */     return this.famille;
   }
 
   public void setFamille(Famille famille) {
/* 266 */     this.famille = famille;
   }
 
   public List<Famille> getFamilles() {
/* 270 */     this.familles = this.familleFacadeLocal.findAllRange();
/* 271 */     return this.familles;
   }
 
   public void setFamilles(List<Famille> familles) {
/* 275 */     this.familles = familles;
   }
 
   public List<Lignerepartitionarticle> getLignerepartitionarticles_1() {
/* 279 */     return this.lignerepartitionarticles_1;
   }
 
   public void setLignerepartitionarticles_1(List<Lignerepartitionarticle> lignerepartitionarticles_1) {
/* 283 */     this.lignerepartitionarticles_1 = lignerepartitionarticles_1;
   }
 
   public List<Lignerepartitiontemp> getLignerepartitiontemps() {
/* 287 */     return this.lignerepartitiontemps;
   }
 
   public void setLignerepartitiontemps(List<Lignerepartitiontemp> lignerepartitiontemps) {
/* 291 */     this.lignerepartitiontemps = lignerepartitiontemps;
   }
 
   public List<Magasinlot> getSelectedMagasinlots() {
/* 295 */     return this.selectedMagasinlots;
   }
 
   public void setSelectedMagasinlots(List<Magasinlot> selectedMagasinlots) {
/* 299 */     this.selectedMagasinlots = selectedMagasinlots;
   }
 
   public Unite getUnite() {
/* 303 */     return this.unite;
   }
 
   public void setUnite(Unite unite) {
/* 307 */     this.unite = unite;
   }
 
   public List<Unite> getUnites() {
/* 311 */     this.unites = this.uniteFacadeLocal.findAllRange();
/* 312 */     return this.unites;
   }
 
   public void setUnites(List<Unite> unites) {
/* 316 */     this.unites = unites;
   }
 
   public int getState_zero() {
/* 320 */     return this.state_zero;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.transert_mc_ms.AbstractTransertMagCentralController
 * JD-Core Version:    0.6.2
 */