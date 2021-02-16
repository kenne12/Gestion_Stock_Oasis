 package controllers.analyse.alerte;
 
 import entities.Commandefournisseur;
 import entities.Demande;
 import entities.Magasin;
 import entities.Magasinarticle;
 import entities.Magasinlot;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.ejb.EJB;
 import javax.transaction.UserTransaction;
 import sessions.CommandefournisseurFacadeLocal;
 import sessions.DemandeFacadeLocal;
 import sessions.MagasinFacadeLocal;
 import sessions.MagasinarticleFacadeLocal;
 import sessions.MagasinlotFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import utils.Routine;
 
 public class AbstractAlerteController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected CommandefournisseurFacadeLocal commandefournisseurFacadeLocal;
/*  37 */   protected List<Commandefournisseur> commandefournisseurs = new ArrayList();
 
   @EJB
   protected DemandeFacadeLocal demandeFacadeLocal;
/*  41 */   protected List<Demande> demandes = new ArrayList();
 
   @EJB
   protected MagasinlotFacadeLocal magasinlotFacadeLocal;
/*  45 */   protected List<Magasinlot> magasinlot_peremps = new ArrayList();
/*  46 */   protected List<Magasinlot> magasinlot_alert = new ArrayList();
 
   @EJB
   protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
/*  50 */   protected List<Magasinarticle> magasinarticle_alert = new ArrayList();
 
   @EJB
   protected MagasinFacadeLocal magasinFacadeLocal;
/*  54 */   protected List<Magasin> magasins = new ArrayList();
/*  55 */   protected List<Magasin> magasins_1 = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/*  60 */   protected Routine routine = new Routine();
 
/*  62 */   protected String fileName = "";
 
/*  64 */   protected String mode = "";
 
   public String getFileName() {
/*  67 */     return this.fileName;
   }
 
   public Routine getRoutine() {
/*  71 */     return this.routine;
   }
 
   public List<Demande> getDemandes() {
/*  75 */     this.demandes = this.demandeFacadeLocal.findByValidee(false);
/*  76 */     return this.demandes;
   }
 
   public void setDemandes(List<Demande> demandes) {
/*  80 */     this.demandes = demandes;
   }
 
   public List<Magasinlot> getMagasinlot_peremps() {
/*  84 */     return this.magasinlot_peremps;
   }
 
   public void setMagasinlot_peremps(List<Magasinlot> magasinlot_peremps) {
/*  88 */     this.magasinlot_peremps = magasinlot_peremps;
   }
 
   public List<Magasinlot> getMagasinlot_alert() {
/*  92 */     return this.magasinlot_alert;
   }
 
   public void setMagasinlot_alert(List<Magasinlot> magasinlot_alert) {
/*  96 */     this.magasinlot_alert = magasinlot_alert;
   }
 
   public List<Magasin> getMagasins() {
/* 100 */     return this.magasins;
   }
 
   public void setMagasins(List<Magasin> magasins) {
/* 104 */     this.magasins = magasins;
   }
 
   public List<Commandefournisseur> getCommandefournisseurs() {
/* 108 */     this.commandefournisseurs = this.commandefournisseurFacadeLocal.findByLivre(false);
/* 109 */     return this.commandefournisseurs;
   }
 
   public void setCommandefournisseurs(List<Commandefournisseur> commandefournisseurs) {
/* 113 */     this.commandefournisseurs = commandefournisseurs;
   }
 
   public List<Magasin> getMagasins_1() {
/* 117 */     return this.magasins_1;
   }
 
   public List<Magasinarticle> getMagasinarticle_alert() {
/* 121 */     return this.magasinarticle_alert;
   }
 
   public void setMagasinarticle_alert(List<Magasinarticle> magasinarticle_alert) {
/* 125 */     this.magasinarticle_alert = magasinarticle_alert;
   }
 
   public void setMagasins_1(List<Magasin> magasins_1) {
/* 129 */     this.magasins_1 = magasins_1;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.analyse.alerte.AbstractAlerteController
 * JD-Core Version:    0.6.2
 */