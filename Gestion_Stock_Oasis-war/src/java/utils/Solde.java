/*    */ package utils;
/*    */ 
/*    */ import entities.Personnel;
/*    */ 
/*    */ public class Solde
/*    */ {
/*    */   private Personnel personnel;
/*    */   private Integer montantVerse;
/*    */   private Integer montantRetire;
/*    */   private Integer carnet;
/*    */   private Integer commission;
/*    */ 
/*    */   public Solde()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Solde(Personnel personnel, Integer montantVerse, Integer montantRetire)
/*    */   {
/* 27 */     this.personnel = personnel;
/* 28 */     this.montantVerse = montantVerse;
/* 29 */     this.montantRetire = montantRetire;
/*    */   }
/*    */ 
/*    */   public Personnel getPersonnel() {
/* 33 */     return this.personnel;
/*    */   }
/*    */ 
/*    */   public void setPersonnel(Personnel personnel) {
/* 37 */     this.personnel = personnel;
/*    */   }
/*    */ 
/*    */   public Integer getMontantVerse() {
/* 41 */     return this.montantVerse;
/*    */   }
/*    */ 
/*    */   public void setMontantVerse(Integer montantVerse) {
/* 45 */     this.montantVerse = montantVerse;
/*    */   }
/*    */ 
/*    */   public Integer getMontantRetire() {
/* 49 */     return this.montantRetire;
/*    */   }
/*    */ 
/*    */   public void setMontantRetire(Integer montantRetire) {
/* 53 */     this.montantRetire = montantRetire;
/*    */   }
/*    */ 
/*    */   public Integer getCarnet() {
/* 57 */     return this.carnet;
/*    */   }
/*    */ 
/*    */   public void setCarnet(Integer carnet) {
/* 61 */     this.carnet = carnet;
/*    */   }
/*    */ 
/*    */   public Integer getCommission() {
/* 65 */     return this.commission;
/*    */   }
/*    */ 
/*    */   public void setCommission(Integer commission) {
/* 69 */     this.commission = commission;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.Solde
 * JD-Core Version:    0.6.2
 */