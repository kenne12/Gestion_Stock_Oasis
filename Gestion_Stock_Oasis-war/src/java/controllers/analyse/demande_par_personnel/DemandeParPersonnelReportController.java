/*    */ package controllers.analyse.demande_par_personnel;
/*    */ 
/*    */ import entities.Personnel;
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.ViewScoped;
/*    */ import sessions.DemandeFacadeLocal;
/*    */ import utils.Printer;
/*    */ 
/*    */ @ManagedBean
/*    */ @ViewScoped
/*    */ public class DemandeParPersonnelReportController extends AbstratDemandeParPersonnelReportController
/*    */   implements Serializable
/*    */ {
/*    */   @PostConstruct
/*    */   private void init()
/*    */   {
/*    */   }
/*    */ 
/*    */   public void search()
/*    */   {
/*    */     try
/*    */     {
/* 31 */       if (this.personnel.getIdpersonnel() != null)
/* 32 */         this.demandes = this.demandeFacadeLocal.findByIdpersonnelIntervalDate(this.personnel.getIdpersonnel().longValue(), this.dateDebut, this.dateFin);
/*    */     }
/*    */     catch (Exception e) {
/* 35 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void printStock() {
/*    */     try {
/* 41 */       Map map = new HashMap();
/* 42 */       map.put("date_debut", this.dateDebut);
/* 43 */       map.put("date_fin", this.dateFin);
/* 44 */       map.put("idpersonnel", this.personnel.getIdpersonnel());
/* 45 */       Printer.print("/reports/ireport/demande_par_personnel.jasper", map);
/*    */     } catch (Exception e) {
/* 47 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.demande_par_personnel.DemandeParPersonnelReportController
 * JD-Core Version:    0.6.2
 */