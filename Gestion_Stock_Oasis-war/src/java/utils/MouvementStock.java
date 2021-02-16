/*    */ package utils;
/*    */ 
/*    */ import entities.Article;
/*    */ import entities.Famille;
/*    */ import entities.Lot;
/*    */ 
/*    */ public class MouvementStock
/*    */ {
/*    */   private Famille famille;
/*    */   private Article article;
/*    */   private Lot lot;
/*    */   private int quantiteIn;
/*    */   private int quantiteOut;
/*    */ 
/*    */   public Famille getFamille()
/*    */   {
/* 25 */     return this.famille;
/*    */   }
/*    */ 
/*    */   public void setFamille(Famille famille) {
/* 29 */     this.famille = famille;
/*    */   }
/*    */ 
/*    */   public Article getArticle() {
/* 33 */     return this.article;
/*    */   }
/*    */ 
/*    */   public void setArticle(Article article) {
/* 37 */     this.article = article;
/*    */   }
/*    */ 
/*    */   public Lot getLot() {
/* 41 */     return this.lot;
/*    */   }
/*    */ 
/*    */   public void setLot(Lot lot) {
/* 45 */     this.lot = lot;
/*    */   }
/*    */ 
/*    */   public int getQuantiteIn() {
/* 49 */     return this.quantiteIn;
/*    */   }
/*    */ 
/*    */   public void setQuantiteIn(int quantiteIn) {
/* 53 */     this.quantiteIn = quantiteIn;
/*    */   }
/*    */ 
/*    */   public int getQuantiteOut() {
/* 57 */     return this.quantiteOut;
/*    */   }
/*    */ 
/*    */   public void setQuantiteOut(int quantiteOut) {
/* 61 */     this.quantiteOut = quantiteOut;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.MouvementStock
 * JD-Core Version:    0.6.2
 */