 package controllers.qualite_personnel;
 
 import entities.Laboratoire;
 import entities.Qualite;
 import java.util.ArrayList;
 import java.util.List;
 import javax.ejb.EJB;
 import sessions.LaboratoireFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.QualiteFacadeLocal;
 import utils.Routine;
 
 public class AbstractQualitepersonnelController
 {
 
   @EJB
   protected QualiteFacadeLocal qualiteFacadeLocal;
   protected Qualite qualite;
/*  27 */   protected List<Qualite> qualites = new ArrayList();
 
   @EJB
   protected LaboratoireFacadeLocal laboratoireFacadeLocal;
/*  31 */   protected Laboratoire laboratoire = new Laboratoire();
/*  32 */   protected List<Laboratoire> laboratoires = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/*  37 */   protected Boolean detail = Boolean.valueOf(true);
/*  38 */   protected Boolean modifier = Boolean.valueOf(true);
/*  39 */   protected Boolean consulter = Boolean.valueOf(true);
/*  40 */   protected Boolean imprimer = Boolean.valueOf(true);
/*  41 */   protected Boolean supprimer = Boolean.valueOf(true);
 
/*  43 */   protected Routine routine = new Routine();
 
/*  45 */   protected String mode = "";
 
   public Boolean getDetail() {
/*  48 */     return this.detail;
   }
 
   public void setDetail(Boolean detail) {
/*  52 */     this.detail = detail;
   }
 
   public Boolean getModifier() {
/*  56 */     return this.modifier;
   }
 
   public void setModifier(Boolean modifier) {
/*  60 */     this.modifier = modifier;
   }
 
   public Boolean getConsulter() {
/*  64 */     return this.consulter;
   }
 
   public void setConsulter(Boolean consulter) {
/*  68 */     this.consulter = consulter;
   }
 
   public Boolean getImprimer() {
/*  72 */     return this.imprimer;
   }
 
   public void setImprimer(Boolean imprimer) {
/*  76 */     this.imprimer = imprimer;
   }
 
   public Boolean getSupprimer() {
/*  80 */     return this.supprimer;
   }
 
   public void setSupprimer(Boolean supprimer) {
/*  84 */     this.supprimer = supprimer;
   }
 
   public Qualite getQualite() {
/*  88 */     return this.qualite;
   }
 
   public void setQualite(Qualite qualite) {
/*  92 */     this.modifier = (this.supprimer = this.detail = Boolean.valueOf(qualite == null));
/*  93 */     this.qualite = qualite;
   }
 
   public List<Qualite> getQualites() {
/*  97 */     this.qualites = this.qualiteFacadeLocal.findAllRange();
/*  98 */     return this.qualites;
   }
 
   public void setQualites(List<Qualite> qualites) {
/* 102 */     this.qualites = qualites;
   }
 
   public Routine getRoutine() {
/* 106 */     return this.routine;
   }
 
   public Laboratoire getLaboratoire() {
/* 110 */     return this.laboratoire;
   }
 
   public void setLaboratoire(Laboratoire laboratoire) {
/* 114 */     this.laboratoire = laboratoire;
   }
 
   public List<Laboratoire> getLaboratoires() {
/* 118 */     this.laboratoires = this.laboratoireFacadeLocal.findAll();
/* 119 */     return this.laboratoires;
   }
 
   public void setLaboratoires(List<Laboratoire> laboratoires) {
/* 123 */     this.laboratoires = laboratoires;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.qualite_personnel.AbstractQualitepersonnelController
 * JD-Core Version:    0.6.2
 */