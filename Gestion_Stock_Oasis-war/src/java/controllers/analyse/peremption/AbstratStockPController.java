/*    */ package controllers.analyse.peremption;
/*    */ 
/*    */ import entities.Magasin;
/*    */ import entities.Magasinlot;
/*    */ import entities.Utilisateur;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.ejb.EJB;
/*    */ import sessions.MagasinFacadeLocal;
/*    */ import sessions.MagasinlotFacadeLocal;
/*    */ import sessions.UtilisateurmagasinFacadeLocal;
/*    */ import utils.SessionMBean;
/*    */ import utils.Utilitaires;
/*    */ 
/*    */ public class AbstratStockPController
/*    */ {
/*    */ 
/*    */   @EJB
/*    */   protected MagasinFacadeLocal magasinFacadeLocal;
/* 27 */   protected Magasin magasin = new Magasin();
/* 28 */   protected List<Magasin> magasins = new ArrayList();
/*    */ 
/*    */   @EJB
/*    */   protected MagasinlotFacadeLocal magasinlotFacadeLocal;
/* 32 */   protected List<Magasinlot> magasinlots = new ArrayList();
/*    */ 
/*    */   @EJB
/*    */   protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;
/*    */ 
/* 38 */   public List<Magasin> getMagasins() { this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
/* 39 */     return this.magasins; }
/*    */ 
/*    */   public Magasin getMagasin()
/*    */   {
/* 43 */     return this.magasin;
/*    */   }
/*    */ 
/*    */   public void setMagasin(Magasin magasin) {
/* 47 */     this.magasin = magasin;
/*    */   }
/*    */ 
/*    */   public List<Magasinlot> getMagasinlots() {
/* 51 */     return this.magasinlots;
/*    */   }
/*    */ 
/*    */   public void setMagasinlots(List<Magasinlot> magasinlots) {
/* 55 */     this.magasinlots = magasinlots;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.peremption.AbstratStockPController
 * JD-Core Version:    0.6.2
 */