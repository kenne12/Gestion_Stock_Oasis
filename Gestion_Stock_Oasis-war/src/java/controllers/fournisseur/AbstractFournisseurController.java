/*    */ package controllers.fournisseur;
/*    */ 
/*    */ import entities.Fournisseur;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.ejb.EJB;
/*    */ import sessions.FournisseurFacadeLocal;
/*    */ import sessions.MouchardFacadeLocal;
/*    */ import utils.Routine;
/*    */ 
/*    */ public class AbstractFournisseurController
/*    */ {
/*    */ 
/*    */   @EJB
/*    */   protected FournisseurFacadeLocal fournisseurFacadeLocal;
/*    */   protected Fournisseur fournisseur;
/* 25 */   protected List<Fournisseur> fournisseurs = new ArrayList();
/*    */ 
/* 27 */   protected Routine routine = new Routine();
/*    */ 
/*    */   @EJB
/*    */   protected MouchardFacadeLocal mouchardFacadeLocal;
/* 32 */   protected Boolean detail = Boolean.valueOf(true);
/* 33 */   protected Boolean modifier = Boolean.valueOf(true);
/* 34 */   protected Boolean consulter = Boolean.valueOf(true);
/* 35 */   protected Boolean imprimer = Boolean.valueOf(true);
/* 36 */   protected Boolean supprimer = Boolean.valueOf(true);
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
/*    */   public Fournisseur getFournisseur() {
/* 81 */     return this.fournisseur;
/*    */   }
/*    */ 
/*    */   public void setFournisseur(Fournisseur fournisseur) {
/* 85 */     this.modifier = (this.supprimer = this.detail = Boolean.valueOf(fournisseur == null));
/* 86 */     this.fournisseur = fournisseur;
/*    */   }
/*    */ 
/*    */   public List<Fournisseur> getFournisseurs() {
/* 90 */     this.fournisseurs = this.fournisseurFacadeLocal.findAllRange();
/* 91 */     return this.fournisseurs;
/*    */   }
/*    */ 
/*    */   public void setFournisseurs(List<Fournisseur> fournisseurs) {
/* 95 */     this.fournisseurs = fournisseurs;
/*    */   }
/*    */ 
/*    */   public Routine getRoutine() {
/* 99 */     return this.routine;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.fournisseur.AbstractFournisseurController
 * JD-Core Version:    0.6.2
 */