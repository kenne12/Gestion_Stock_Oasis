/*    */ package controllers.analyse.peremption;
/*    */ 
/*    */ import entities.Lot;
/*    */ import entities.Magasin;
/*    */ import java.io.Serializable;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.ViewScoped;
/*    */ import sessions.MagasinlotFacadeLocal;
/*    */ import utils.Printer;
/*    */ import utils.Utilitaires;
/*    */ 
/*    */ @ManagedBean
/*    */ @ViewScoped
/*    */ public class StockPController extends AbstratStockPController
/*    */   implements Serializable
/*    */ {
/*    */   @PostConstruct
/*    */   private void init()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Boolean checkPeremption(Lot lot)
/*    */   {
/* 38 */     return Boolean.valueOf(Utilitaires.checkPeremption(lot));
/*    */   }
/*    */ 
/*    */   public void search() {
/*    */     try {
/* 43 */       if (this.magasin.getIdmagasin() != null)
/* 44 */         this.magasinlots = this.magasinlotFacadeLocal.findAllPeremptedEtatIsTrue(new Date());
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 48 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ 
/*    */   public void printStock() {
/*    */     try {
/* 54 */       if (this.magasin.getIdmagasin() != null) {
/* 55 */         Map map = new HashMap();
/* 56 */         map.put("idmagasin", this.magasin.getIdmagasin());
/* 57 */         map.put("date_jour", new Date());
/* 58 */         Printer.print("/reports/ireport/stock_par_magasin_peremption.jasper", map);
/*    */       }
/*    */     } catch (Exception e) {
/* 61 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.peremption.StockPController
 * JD-Core Version:    0.6.2
 */