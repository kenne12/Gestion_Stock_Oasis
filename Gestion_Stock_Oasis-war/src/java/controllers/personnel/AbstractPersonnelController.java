 package controllers.personnel;
 
 import entities.Magasin;
 import entities.Personnel;
 import entities.Qualite;
 import java.util.ArrayList;
 import java.util.List;
 import javax.ejb.EJB;
 import sessions.MagasinFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.PersonnelFacadeLocal;
 import sessions.QualiteFacadeLocal;
 import utils.Routine;
 
 public class AbstractPersonnelController
 {
 
   @EJB
   protected PersonnelFacadeLocal personnelFacadeLocal;
   protected Personnel personnel;
/*  29 */   protected List<Personnel> personnels = new ArrayList();
 
   @EJB
   protected QualiteFacadeLocal qualiteFacadeLocal;
/*  33 */   protected Qualite qualite = new Qualite();
/*  34 */   protected List<Qualite> qualites = new ArrayList();
 
   @EJB
   protected MagasinFacadeLocal magasinFacadeLocal;
/*  38 */   protected Magasin magasin = new Magasin();
/*  39 */   protected List<Magasin> Magasins = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/*  44 */   protected Routine routine = new Routine();
 
/*  46 */   protected String fileName = "";
 
/*  48 */   protected Boolean detail = Boolean.valueOf(true);
/*  49 */   protected Boolean modifier = Boolean.valueOf(true);
/*  50 */   protected Boolean consulter = Boolean.valueOf(true);
/*  51 */   protected Boolean imprimer = Boolean.valueOf(true);
/*  52 */   protected Boolean supprimer = Boolean.valueOf(true);
 
/*  54 */   protected String mode = "";
 
   public Personnel getPersonnel() {
/*  57 */     return this.personnel;
   }
 
   public void setPersonnel(Personnel personnel) {
/*  61 */     this.modifier = (this.supprimer = this.detail = Boolean.valueOf(personnel == null));
/*  62 */     this.personnel = personnel;
   }
 
   public List<Personnel> getPersonnels() {
/*  66 */     this.personnels = this.personnelFacadeLocal.findAllRange();
/*  67 */     return this.personnels;
   }
 
   public void setPersonnels(List<Personnel> personnels) {
/*  71 */     this.personnels = personnels;
   }
 
   public Boolean getDetail() {
/*  75 */     return this.detail;
   }
 
   public Boolean getModifier() {
/*  79 */     return this.modifier;
   }
 
   public Boolean getConsulter() {
/*  83 */     return this.consulter;
   }
 
   public Boolean getImprimer() {
/*  87 */     return this.imprimer;
   }
 
   public List<Qualite> getQualites() {
/*  91 */     this.qualites = this.qualiteFacadeLocal.findAllRange();
/*  92 */     return this.qualites;
   }
 
   public void setQualites(List<Qualite> qualites) {
/*  96 */     this.qualites = qualites;
   }
 
   public Boolean getSupprimer() {
/* 100 */     return this.supprimer;
   }
 
   public void setSupprimer(Boolean supprimer) {
/* 104 */     this.supprimer = supprimer;
   }
 
   public Qualite getQualite() {
/* 108 */     return this.qualite;
   }
 
   public void setQualite(Qualite qualite) {
/* 112 */     this.qualite = qualite;
   }
 
   public Routine getRoutine() {
/* 116 */     return this.routine;
   }
 
   public String getMode() {
/* 120 */     return this.mode;
   }
 
   public Magasin getMagasin() {
/* 124 */     return this.magasin;
   }
 
   public void setMagasin(Magasin Magasin) {
/* 128 */     this.magasin = Magasin;
   }
 
   public List<Magasin> getMagasins() {
/* 132 */     this.Magasins = this.magasinFacadeLocal.findAllRange();
/* 133 */     return this.Magasins;
   }
 
   public void setMagasins(List<Magasin> Magasins) {
/* 137 */     this.Magasins = Magasins;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.personnel.AbstractPersonnelController
 * JD-Core Version:    0.6.2
 */