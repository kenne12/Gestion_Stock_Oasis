 package controllers.projet;
 
 import entities.Magasin;
 import entities.Projet;
 import java.util.ArrayList;
 import java.util.List;
 import javax.ejb.EJB;
 import sessions.MagasinFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.ProjetFacadeLocal;
 import utils.Routine;
 
 public class AbstractProjetController
 {
 
   @EJB
   protected ProjetFacadeLocal projetFacadeLocal;
   protected Projet projet;
/*  27 */   protected List<Projet> projets = new ArrayList();
 
   @EJB
   protected MagasinFacadeLocal magasinFacadeLocal;
/*  31 */   protected Magasin magasin = new Magasin();
/*  32 */   protected List<Magasin> magasins = new ArrayList();
 
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
 
   public Projet getProjet() {
/*  88 */     return this.projet;
   }
 
   public void setProjet(Projet projet) {
/*  92 */     this.modifier = (this.supprimer = this.detail = Boolean.valueOf(projet == null));
/*  93 */     this.projet = projet;
   }
 
   public List<Projet> getProjets() {
/*  97 */     this.projets = this.projetFacadeLocal.findAllRange();
/*  98 */     return this.projets;
   }
 
   public void setProjets(List<Projet> projets) {
/* 102 */     this.projets = projets;
   }
 
   public Routine getRoutine() {
/* 106 */     return this.routine;
   }
 
   public Magasin getMagasin() {
/* 110 */     return this.magasin;
   }
 
   public void setMagasin(Magasin magasin) {
/* 114 */     this.magasin = magasin;
   }
 
   public List<Magasin> getMagasins() {
/* 118 */     this.magasins = this.magasinFacadeLocal.findAllRange();
/* 119 */     return this.magasins;
   }
 
   public void setMagasins(List<Magasin> magasins) {
/* 123 */     this.magasins = magasins;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.projet.AbstractProjetController
 * JD-Core Version:    0.6.2
 */