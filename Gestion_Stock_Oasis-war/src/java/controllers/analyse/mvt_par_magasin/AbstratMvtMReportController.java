 package controllers.analyse.mvt_par_magasin;
 
 import entities.Lignemvtstock;
 import entities.Magasin;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.List;
 import javax.ejb.EJB;
 import sessions.LignemvtstockFacadeLocal;
 import sessions.MagasinFacadeLocal;
 import sessions.UtilisateurmagasinFacadeLocal;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 public class AbstratMvtMReportController
 {
/* 26 */   protected Date dateDebut = new Date();
/* 27 */   protected Date dateFin = new Date();
 
   @EJB
   protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
/* 31 */   protected List<Lignemvtstock> lignemvtstocks = new ArrayList();
 
   @EJB
   protected MagasinFacadeLocal magasinFacadeLocal;
/* 35 */   protected Magasin magasin = new Magasin();
/* 36 */   protected List<Magasin> magasins = new ArrayList();
 
   @EJB
   protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;
 
/* 42 */   public Date getDateDebut() { return this.dateDebut; }
 
   public Date getDateFin()
   {
/* 46 */     return this.dateFin;
   }
 
   public void setDateDebut(Date dateDebut) {
/* 50 */     this.dateDebut = dateDebut;
   }
 
   public void setDateFin(Date dateFin) {
/* 54 */     this.dateFin = dateFin;
   }
 
   public List<Lignemvtstock> getLignemvtstocks() {
/* 58 */     return this.lignemvtstocks;
   }
 
   public Magasin getMagasin() {
/* 62 */     return this.magasin;
   }
 
   public void setMagasin(Magasin magasin) {
/* 66 */     this.magasin = magasin;
   }
 
   public List<Magasin> getMagasins() {
/* 70 */     this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
/* 71 */     return this.magasins;
   }
 
   public void setMagasins(List<Magasin> magasins) {
/* 75 */     this.magasins = magasins;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.mvt_par_magasin.AbstratMvtMReportController
 * JD-Core Version:    0.6.2
 */