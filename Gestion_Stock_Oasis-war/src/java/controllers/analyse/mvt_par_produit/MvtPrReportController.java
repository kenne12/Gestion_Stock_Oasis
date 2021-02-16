/*    */ package controllers.analyse.mvt_par_produit;
/*    */ 
/*    */ import entities.Article;
/*    */ import java.io.Serializable;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.ViewScoped;
/*    */ import sessions.LignemvtstockFacadeLocal;
/*    */ import utils.Printer;
/*    */ 
/*    */ @ManagedBean
/*    */ @ViewScoped
/*    */ public class MvtPrReportController extends AbstratMvtPrReportController
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
/* 31 */       if (this.article.getIdarticle() != null)
/* 32 */         this.lignemvtstocks = this.lignemvtstockFacadeLocal.findByIdarticleIntevalDate(this.article.getIdarticle().longValue(), this.dateDebut, this.dateFin);
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
/* 44 */       map.put("idarticle", this.article.getIdarticle());
/* 45 */       Printer.print("/reports/ireport/mouvement_par_produit.jasper", map);
/*    */     } catch (Exception e) {
/* 47 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.mvt_par_produit.MvtPrReportController
 * JD-Core Version:    0.6.2
 */