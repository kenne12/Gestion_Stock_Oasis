/*    */ package controllers.service;
/*    */ 
/*    */ import entities.Service;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.ejb.EJB;
/*    */ import sessions.MouchardFacadeLocal;
/*    */ import sessions.ServiceFacadeLocal;
/*    */ import utils.Routine;
/*    */ 
/*    */ public class AbstractServiceController
/*    */ {
/*    */ 
/*    */   @EJB
/*    */   protected ServiceFacadeLocal serviceFacadeLocal;
/*    */   protected Service service;
/* 25 */   protected List<Service> services = new ArrayList();
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
/*    */   public Service getService() {
/* 81 */     return this.service;
/*    */   }
/*    */ 
/*    */   public void setService(Service service) {
/* 85 */     this.modifier = (this.supprimer = this.detail = Boolean.valueOf(service == null));
/* 86 */     this.service = service;
/*    */   }
/*    */ 
/*    */   public List<Service> getServices() {
/* 90 */     this.services = this.serviceFacadeLocal.findAllRange();
/* 91 */     return this.services;
/*    */   }
/*    */ 
/*    */   public void setServices(List<Service> services) {
/* 95 */     this.services = services;
/*    */   }
/*    */ 
/*    */   public Routine getRoutine() {
/* 99 */     return this.routine;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.service.AbstractServiceController
 * JD-Core Version:    0.6.2
 */