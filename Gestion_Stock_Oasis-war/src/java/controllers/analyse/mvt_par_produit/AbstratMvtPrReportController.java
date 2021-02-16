/*    */ package controllers.analyse.mvt_par_produit;
/*    */ 
/*    */ import entities.Article;
/*    */ import entities.Lignemvtstock;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.ejb.EJB;
/*    */ import sessions.ArticleFacadeLocal;
/*    */ import sessions.LignemvtstockFacadeLocal;
/*    */ 
/*    */ public class AbstratMvtPrReportController
/*    */ {
/* 23 */   protected Date dateDebut = new Date();
/* 24 */   protected Date dateFin = new Date();
/*    */ 
/*    */   @EJB
/*    */   protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
/* 28 */   protected List<Lignemvtstock> lignemvtstocks = new ArrayList();
/*    */ 
/*    */   @EJB
/*    */   protected ArticleFacadeLocal articleFacadeLocal;
/* 32 */   protected Article article = new Article();
/* 33 */   protected List<Article> articles = new ArrayList();
/*    */ 
/*    */   public Date getDateDebut() {
/* 36 */     return this.dateDebut;
/*    */   }
/*    */ 
/*    */   public Date getDateFin() {
/* 40 */     return this.dateFin;
/*    */   }
/*    */ 
/*    */   public void setDateDebut(Date dateDebut) {
/* 44 */     this.dateDebut = dateDebut;
/*    */   }
/*    */ 
/*    */   public void setDateFin(Date dateFin) {
/* 48 */     this.dateFin = dateFin;
/*    */   }
/*    */ 
/*    */   public Article getArticle() {
/* 52 */     return this.article;
/*    */   }
/*    */ 
/*    */   public void setArticle(Article article) {
/* 56 */     this.article = article;
/*    */   }
/*    */ 
/*    */   public List<Article> getArticles() {
/* 60 */     this.articles = this.articleFacadeLocal.findAllRange(true);
/* 61 */     return this.articles;
/*    */   }
/*    */ 
/*    */   public void setArticles(List<Article> articles) {
/* 65 */     this.articles = articles;
/*    */   }
/*    */ 
/*    */   public List<Lignemvtstock> getLignemvtstocks() {
/* 69 */     return this.lignemvtstocks;
/*    */   }
/*    */ 
/*    */   public void setLignemvtstocks(List<Lignemvtstock> lignemvtstocks) {
/* 73 */     this.lignemvtstocks = lignemvtstocks;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.mvt_par_produit.AbstratMvtPrReportController
 * JD-Core Version:    0.6.2
 */