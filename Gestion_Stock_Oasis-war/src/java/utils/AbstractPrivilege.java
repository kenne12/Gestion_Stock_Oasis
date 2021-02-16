/*    */ package utils;
/*    */ 
/*    */ public class AbstractPrivilege
/*    */ {
/* 16 */   protected boolean gestionPersonnel = false;
/* 17 */   protected boolean gestionNote = false;
/* 18 */   protected boolean gestionPrivilege = false;
/* 19 */   protected boolean gestionDiscipline = false;
/* 20 */   protected boolean gestionInscription = false;
/* 21 */   protected boolean gestionEtat = false;
/* 22 */   protected boolean parametrage = false;
/* 23 */   protected boolean bibliotheque = false;
/*    */ 
/*    */   public boolean isGestionPersonnel() {
/* 26 */     return this.gestionPersonnel;
/*    */   }
/*    */ 
/*    */   public void setGestionPersonnel(boolean gestionPersonnel) {
/* 30 */     this.gestionPersonnel = gestionPersonnel;
/*    */   }
/*    */ 
/*    */   public boolean isGestionNote() {
/* 34 */     return this.gestionNote;
/*    */   }
/*    */ 
/*    */   public void setGestionNote(boolean gestionNote) {
/* 38 */     this.gestionNote = gestionNote;
/*    */   }
/*    */ 
/*    */   public boolean isGestionPrivilege() {
/* 42 */     return this.gestionPrivilege;
/*    */   }
/*    */ 
/*    */   public void setGestionPrivilege(boolean gestionPrivilege) {
/* 46 */     this.gestionPrivilege = gestionPrivilege;
/*    */   }
/*    */ 
/*    */   public boolean isGestionDiscipline() {
/* 50 */     return this.gestionDiscipline;
/*    */   }
/*    */ 
/*    */   public void setGestonDiscipline(boolean gestonDiscipline) {
/* 54 */     this.gestionDiscipline = gestonDiscipline;
/*    */   }
/*    */ 
/*    */   public boolean isGestionInscription() {
/* 58 */     return this.gestionInscription;
/*    */   }
/*    */ 
/*    */   public void setGestionInscription(boolean gestionInscription) {
/* 62 */     this.gestionInscription = gestionInscription;
/*    */   }
/*    */ 
/*    */   public boolean isGestionEtat() {
/* 66 */     return this.gestionEtat;
/*    */   }
/*    */ 
/*    */   public void setGestionEtat(boolean gestionEtat) {
/* 70 */     this.gestionEtat = gestionEtat;
/*    */   }
/*    */ 
/*    */   public boolean isParametrage() {
/* 74 */     return this.parametrage;
/*    */   }
/*    */ 
/*    */   public void setParametrage(boolean parametrage) {
/* 78 */     this.parametrage = parametrage;
/*    */   }
/*    */ 
/*    */   public boolean isBibliotheque() {
/* 82 */     return this.bibliotheque;
/*    */   }
/*    */ 
/*    */   public void setBibliotheque(boolean bibliotheque) {
/* 86 */     this.bibliotheque = bibliotheque;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.AbstractPrivilege
 * JD-Core Version:    0.6.2
 */