/*    */ package controllers.unite;
/*    */ 
/*    */ import entities.Unite;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.ejb.EJB;
/*    */ import sessions.MouchardFacadeLocal;
/*    */ import sessions.UniteFacadeLocal;
/*    */ import utils.Routine;
/*    */ 
/*    */ public class AbstractUniteController
/*    */ {
/*    */ 
/*    */   @EJB
/*    */   protected UniteFacadeLocal uniteFacadeLocal;
/* 24 */   protected Unite unite = new Unite();
/* 25 */   protected List<Unite> unites = new ArrayList();
/*    */ 
/*    */   @EJB
/*    */   protected MouchardFacadeLocal mouchardFacadeLocal;
/* 30 */   protected Boolean detail = Boolean.valueOf(true);
/* 31 */   protected Boolean modifier = Boolean.valueOf(true);
/* 32 */   protected Boolean consulter = Boolean.valueOf(true);
/* 33 */   protected Boolean imprimer = Boolean.valueOf(true);
/* 34 */   protected Boolean supprimer = Boolean.valueOf(true);
/*    */ 
/* 36 */   protected Routine routine = new Routine();
/*    */ 
/* 38 */   protected String mode = "";
/*    */ 
/*    */   public Boolean getDetail() {
/* 41 */     return this.detail;
/*    */   }
/*    */ 
/*    */   public void setDetail(Boolean detail) {
/* 45 */     this.detail = detail;
/*    */   }
/*    */ 
/*    */   public Boolean getModifier() {
/* 49 */     return this.modifier;
/*    */   }
/*    */ 
/*    */   public void setModifier(Boolean modifier) {
/* 53 */     this.modifier = modifier;
/*    */   }
/*    */ 
/*    */   public Boolean getConsulter() {
/* 57 */     return this.consulter;
/*    */   }
/*    */ 
/*    */   public void setConsulter(Boolean consulter) {
/* 61 */     this.consulter = consulter;
/*    */   }
/*    */ 
/*    */   public Boolean getImprimer() {
/* 65 */     return this.imprimer;
/*    */   }
/*    */ 
/*    */   public void setImprimer(Boolean imprimer) {
/* 69 */     this.imprimer = imprimer;
/*    */   }
/*    */ 
/*    */   public Boolean getSupprimer() {
/* 73 */     return this.supprimer;
/*    */   }
/*    */ 
/*    */   public void setSupprimer(Boolean supprimer) {
/* 77 */     this.supprimer = supprimer;
/*    */   }
/*    */ 
/*    */   public Unite getUnite() {
/* 81 */     return this.unite;
/*    */   }
/*    */ 
/*    */   public void setUnite(Unite unite) {
/* 85 */     this.modifier = (this.supprimer = this.detail = Boolean.valueOf(unite == null));
/* 86 */     this.unite = unite;
/*    */   }
/*    */ 
/*    */   public List<Unite> getUnites() {
/* 90 */     this.unites = this.uniteFacadeLocal.findAllRange();
/* 91 */     return this.unites;
/*    */   }
/*    */ 
/*    */   public void setUnites(List<Unite> unites) {
/* 95 */     this.unites = unites;
/*    */   }
/*    */ 
/*    */   public Routine getRoutine() {
/* 99 */     return this.routine;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.unite.AbstractUniteController
 * JD-Core Version:    0.6.2
 */