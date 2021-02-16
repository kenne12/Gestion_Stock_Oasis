/*    */ package controllers.analyse.demande_par_personnel;
/*    */ 
/*    */ import entities.Demande;
/*    */ import entities.Personnel;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.ejb.EJB;
/*    */ import sessions.DemandeFacadeLocal;
/*    */ import sessions.PersonnelFacadeLocal;
/*    */ 
/*    */ public class AbstratDemandeParPersonnelReportController
/*    */ {
/* 23 */   protected Date dateDebut = new Date();
/* 24 */   protected Date dateFin = new Date();
/*    */ 
/*    */   @EJB
/*    */   protected PersonnelFacadeLocal personnelFacadeLocal;
/* 28 */   protected Personnel personnel = new Personnel();
/* 29 */   protected List<Personnel> personnels = new ArrayList();
/*    */ 
/*    */   @EJB
/*    */   protected DemandeFacadeLocal demandeFacadeLocal;
/* 33 */   protected List<Demande> demandes = new ArrayList();
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
/*    */   public Personnel getPersonnel() {
/* 52 */     return this.personnel;
/*    */   }
/*    */ 
/*    */   public void setPersonnel(Personnel personnel) {
/* 56 */     this.personnel = personnel;
/*    */   }
/*    */ 
/*    */   public List<Personnel> getPersonnels() {
/* 60 */     this.personnels = this.personnelFacadeLocal.findAllRange();
/* 61 */     return this.personnels;
/*    */   }
/*    */ 
/*    */   public void setPersonnels(List<Personnel> personnels) {
/* 65 */     this.personnels = personnels;
/*    */   }
/*    */ 
/*    */   public List<Demande> getDemandes() {
/* 69 */     return this.demandes;
/*    */   }
/*    */ 
/*    */   public void setDemandes(List<Demande> demandes) {
/* 73 */     this.demandes = demandes;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.demande_par_personnel.AbstratDemandeParPersonnelReportController
 * JD-Core Version:    0.6.2
 */