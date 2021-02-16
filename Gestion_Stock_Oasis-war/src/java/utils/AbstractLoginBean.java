 package utils;
 
 import entities.Mouchard;
 import entities.Parametrage;
 import java.util.Date;
 import javax.ejb.EJB;
 import sessions.MenuFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.ParametrageFacadeLocal;
 import sessions.PrivilegeFacadeLocal;
 
 public class AbstractLoginBean
 {
 
   @EJB
   protected ParametrageFacadeLocal parametrageFacadeLocal;
/*  25 */   protected Parametrage param = new Parametrage();
 
   @EJB
   protected MenuFacadeLocal menuFacadeLocal;
 
   @EJB
   protected PrivilegeFacadeLocal privilegeFacadeLocal;
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
   protected Mouchard traceur;
/*  37 */   protected Routine routine = new Routine();
 
/*  39 */   protected Date date = new Date();
 
/*  41 */   protected String confirmPassword = "";
   protected boolean gestionPersonnel;
   protected boolean gestionNote;
   protected boolean gestionPrivilege;
   protected boolean gestionDiscipline;
   protected boolean gestionInscription;
   protected boolean gestionEtat;
   protected boolean parametrage;
   protected boolean bibliotheque;
/*  53 */   protected String gestionPersonnelVisible = "hidden";
/*  54 */   protected String gestionNoteVisible = "hidden";
/*  55 */   protected String gestionPrivilegeVisible = "hidden";
/*  56 */   protected String gestionDisciplineVisible = "hidden";
/*  57 */   protected String gestionInscriptionVisible = "hidden";
/*  58 */   protected String gestionEtatVisible = "hidden";
/*  59 */   protected String parametrageVisible = "hidden";
/*  60 */   protected String bibliothequeVisible = "hidden";
 
/*  62 */   protected boolean showHibernatePanel = false;
/*  63 */   protected String hibernatePassword = "";
 
/*  66 */   protected boolean showSessionPanel = true;
 
/*  68 */   protected boolean connected = false;
 
/*  70 */   protected String connectionVisible = "visible";
 
   public boolean isGestionPersonnel() {
/*  73 */     return this.gestionPersonnel;
   }
 
   public void setGestionPersonnel(boolean gestionPersonnel) {
/*  77 */     this.gestionPersonnel = gestionPersonnel;
   }
 
   public boolean isGestionNote() {
/*  81 */     return this.gestionNote;
   }
 
   public void setGestionNote(boolean gestionNote) {
/*  85 */     this.gestionNote = gestionNote;
   }
 
   public boolean isGestionPrivilege() {
/*  89 */     return this.gestionPrivilege;
   }
 
   public void setGestionPrivilege(boolean gestionPrivilege) {
/*  93 */     this.gestionPrivilege = gestionPrivilege;
   }
 
   public boolean isGestionDiscipline() {
/*  97 */     return this.gestionDiscipline;
   }
 
   public void setGestionDiscipline(boolean gestionDiscipline) {
/* 101 */     this.gestionDiscipline = gestionDiscipline;
   }
 
   public boolean isGestionInscription() {
/* 105 */     return this.gestionInscription;
   }
 
   public void setGestionInscription(boolean gestionInscription) {
/* 109 */     this.gestionInscription = gestionInscription;
   }
 
   public boolean isGestionEtat() {
/* 113 */     return this.gestionEtat;
   }
 
   public void setGestionEtat(boolean gestionEtat) {
/* 117 */     this.gestionEtat = gestionEtat;
   }
 
   public boolean isParametrage() {
/* 121 */     return this.parametrage;
   }
 
   public void setParametrage(boolean parametrage) {
/* 125 */     this.parametrage = parametrage;
   }
 
   public boolean isBibliotheque() {
/* 129 */     return this.bibliotheque;
   }
 
   public void setBibliotheque(boolean bibliotheque) {
/* 133 */     this.bibliotheque = bibliotheque;
   }
 
   public String getGestionPersonnelVisible()
   {
/* 138 */     return this.gestionPersonnelVisible;
   }
 
   public void setGestionPersonnelVisible(String gestionPersonnelVisible) {
/* 142 */     this.gestionPersonnelVisible = gestionPersonnelVisible;
   }
 
   public String getGestionNoteVisible() {
/* 146 */     return this.gestionNoteVisible;
   }
 
   public void setGestionNoteVisible(String gestionNoteVisible) {
/* 150 */     this.gestionNoteVisible = gestionNoteVisible;
   }
 
   public String getGestionPrivilegeVisible() {
/* 154 */     return this.gestionPrivilegeVisible;
   }
 
   public void setGestionPrivilegeVisible(String gestionPrivilegeVisible) {
/* 158 */     this.gestionPrivilegeVisible = gestionPrivilegeVisible;
   }
 
   public String getGestionDisciplineVisible() {
/* 162 */     return this.gestionDisciplineVisible;
   }
 
   public void setGestionDisciplineVisible(String gestionDisciplineVisible) {
/* 166 */     this.gestionDisciplineVisible = gestionDisciplineVisible;
   }
 
   public String getGestionInscriptionVisible() {
/* 170 */     return this.gestionInscriptionVisible;
   }
 
   public void setGestionInscriptionVisible(String gestionInscriptionVisible) {
/* 174 */     this.gestionInscriptionVisible = gestionInscriptionVisible;
   }
 
   public String getGestionEtatVisible() {
/* 178 */     return this.gestionEtatVisible;
   }
 
   public void setGestionEtatVisible(String gestionEtatVisible) {
/* 182 */     this.gestionEtatVisible = gestionEtatVisible;
   }
 
   public String getParametrageVisible() {
/* 186 */     return this.parametrageVisible;
   }
 
   public void setParametrageVisible(String parametrageVisible) {
/* 190 */     this.parametrageVisible = parametrageVisible;
   }
 
   public String getBibliothequeVisible() {
/* 194 */     return this.bibliothequeVisible;
   }
 
   public void setBibliothequeVisible(String bibliothequeVisible) {
/* 198 */     this.bibliothequeVisible = bibliothequeVisible;
   }
 
   public boolean isShowHibernatePanel() {
/* 202 */     return this.showHibernatePanel;
   }
 
   public void setShowHibernatePanel(boolean showHibernatePanel) {
/* 206 */     this.showHibernatePanel = showHibernatePanel;
   }
 
   public String getHibernatePassword() {
/* 210 */     return this.hibernatePassword;
   }
 
   public void setHibernatePassword(String hibernatePassword) {
/* 214 */     this.hibernatePassword = hibernatePassword;
   }
 
   public Mouchard getTraceur() {
/* 218 */     return this.traceur;
   }
 
   public void setTraceur(Mouchard traceur) {
/* 222 */     this.traceur = traceur;
   }
 
   public Parametrage getParam() {
/* 226 */     return this.param;
   }
 
   public boolean isShowSessionPanel() {
/* 230 */     return this.showSessionPanel;
   }
 
   public Date getDate() {
/* 234 */     return this.date;
   }
 
   public void setDate(Date date) {
/* 238 */     this.date = date;
   }
 
   public Routine getRoutine() {
/* 242 */     return this.routine;
   }
 
   public String getConfirmPassword() {
/* 246 */     return this.confirmPassword;
   }
 
   public void setConfirmPassword(String confirmPassword) {
/* 250 */     this.confirmPassword = confirmPassword;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.AbstractLoginBean
 * JD-Core Version:    0.6.2
 */