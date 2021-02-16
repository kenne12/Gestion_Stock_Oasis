 package controllers.control_peremption;
 
 import entities.Article;
 import entities.Lot;
 import entities.Magasin;
 import entities.Magasinlot;
 import entities.Parametrage;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.ejb.EJB;
 import javax.transaction.UserTransaction;
 import sessions.ArticleFacadeLocal;
 import sessions.InventaireFacadeLocal;
 import sessions.LigneinventaireFacadeLocal;
 import sessions.LignemvtstockFacadeLocal;
 import sessions.LotFacadeLocal;
 import sessions.MagasinFacadeLocal;
 import sessions.MagasinarticleFacadeLocal;
 import sessions.MagasinlotFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.MvtstockFacadeLocal;
 import utils.Routine;
 import utils.SessionMBean;
 
 public class AbstractControlPeremptionController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected LotFacadeLocal lotFacadeLocal;
/*  42 */   protected Lot lot = new Lot();
/*  43 */   protected List<Lot> lots = new ArrayList();
 
   @EJB
   protected ArticleFacadeLocal articleFacadeLocal;
/*  47 */   protected Article article = null;
/*  48 */   protected List<Article> articles = new ArrayList();
 
   @EJB
   protected MagasinFacadeLocal magasinFacadeLocal;
/*  52 */   protected List<Magasin> magasins = new ArrayList();
 
   @EJB
   protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
 
   @EJB
   protected MagasinlotFacadeLocal magasinlotFacadeLocal;
/*  59 */   protected List<Magasinlot> magasinlots = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/*  63 */   protected Routine routine = new Routine();
 
/*  65 */   protected boolean showSessionPanel = true;
 
   @EJB
   protected InventaireFacadeLocal inventaireFacadeLocal;
 
   @EJB
   protected LigneinventaireFacadeLocal ligneinventaireFacadeLocal;
 
   @EJB
   protected MvtstockFacadeLocal mvtstockFacadeLocal;
 
   @EJB
   protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
/*  79 */   protected String fileName = "";
/*  80 */   protected String mode = "";
 
/*  82 */   protected boolean disableProduct = false;
/*  83 */   protected boolean showUser = SessionMBean.getParametrage().getEtatuser().booleanValue();
/*  84 */   protected boolean showBailleur = SessionMBean.getParametrage().getEtatbailleur().booleanValue();
 
   public String getFileName() {
/*  87 */     return this.fileName;
   }
 
   public void setFileName(String fileName) {
/*  91 */     this.fileName = fileName;
   }
 
   public Routine getRoutine() {
/*  95 */     return this.routine;
   }
 
   public boolean isDisableProduct() {
/*  99 */     return this.disableProduct;
   }
 
   public Lot getLot() {
/* 103 */     return this.lot;
   }
 
   public List<Lot> getLots() {
/* 107 */     return this.lots;
   }
 
   public Article getArticle() {
/* 111 */     return this.article;
   }
 
   public void setArticle(Article article) {
/* 115 */     this.article = article;
   }
 
   public List<Article> getArticles() {
/* 119 */     return this.articles;
   }
 
   public boolean isShowUser() {
/* 123 */     return this.showUser;
   }
 
   public boolean isShowBailleur() {
/* 127 */     return this.showBailleur;
   }
 
   public String getMode() {
/* 131 */     return this.mode;
   }
 
   public List<Magasin> getMagasins() {
/* 135 */     return this.magasins;
   }
 
   public List<Magasinlot> getMagasinlots() {
/* 139 */     return this.magasinlots;
   }
 
   public boolean isShowSessionPanel() {
/* 143 */     return this.showSessionPanel;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.control_peremption.AbstractControlPeremptionController
 * JD-Core Version:    0.6.2
 */