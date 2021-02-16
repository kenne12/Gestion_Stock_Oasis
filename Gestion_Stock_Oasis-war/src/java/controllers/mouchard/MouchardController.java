/*    */ package controllers.mouchard;
/*    */ 
/*    */ import entities.Mouchard;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.ViewScoped;
/*    */ 
/*    */ @ManagedBean
/*    */ @ViewScoped
/*    */ public class MouchardController extends AbstractMouchardController
/*    */ {
/*    */   @PostConstruct
/*    */   private void init()
/*    */   {
/* 29 */     this.mouchard = new Mouchard();
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.mouchard.MouchardController
 * JD-Core Version:    0.6.2
 */