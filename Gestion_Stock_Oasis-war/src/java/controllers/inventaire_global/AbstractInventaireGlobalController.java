 package controllers.inventaire_global;
 
 import entities.Article;
 import entities.Inventaire;
 import entities.Ligneinventaire;
 import entities.Lot;
 import entities.Magasin;
 import entities.Magasinarticle;
 import entities.Magasinlot;
 import entities.Mvtstock;
 import entities.Utilisateur;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.ejb.EJB;
 import javax.transaction.UserTransaction;
 import sessions.ArticleFacadeLocal;
 import sessions.InventaireFacadeLocal;
 import sessions.LigneinventaireFacadeLocal;
 import sessions.LignemvtstockFacadeLocal;
 import sessions.LotFacadeLocal;
 import sessions.MagasinFacadeLocal;
 import sessions.MagasinarticleFacadeLocal;
 import sessions.MagasinlotFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.MvtstockFacadeLocal;
 import sessions.UtilisateurmagasinFacadeLocal;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 public class AbstractInventaireGlobalController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected InventaireFacadeLocal inventaireFacadeLocal;
/*  47 */   protected Inventaire inventaire = null;
/*  48 */   protected List<Inventaire> inventaires = new ArrayList();
 
   @EJB
   protected LigneinventaireFacadeLocal ligneinventaireFacadeLocal;
/*  52 */   protected List<Ligneinventaire> ligneinventaires = new ArrayList();
/*  53 */   protected List<Ligneinventaire> ligneinventaires_1 = new ArrayList();
 
   @EJB
   protected ArticleFacadeLocal articleFacadeLocal;
/*  57 */   protected List<Article> articles = new ArrayList();
 
   @EJB
   protected LotFacadeLocal lotFacadeLocal;
/*  61 */   protected List<Lot> lots = new ArrayList();
/*  62 */   protected List<Lot> selectedLots = new ArrayList();
 
   @EJB
   protected MagasinlotFacadeLocal magasinlotFacadeLocal;
/*  66 */   protected List<Magasinlot> magasinlots = new ArrayList();
/*  67 */   protected List<Magasinlot> selectedMagasinlots = new ArrayList();
 
/*  69 */   protected List<Magasinarticle> magasinarticles = new ArrayList();
 
   @EJB
   protected MvtstockFacadeLocal mvtstockFacadeLocal;
/*  73 */   protected Mvtstock mvtstock = new Mvtstock();
 
   @EJB
   protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
 
   @EJB
   protected MagasinFacadeLocal magasinFacadeLocal;
/*  80 */   protected Magasin magasin = new Magasin();
/*  81 */   protected List<Magasin> magasins = new ArrayList();
 
   @EJB
   protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
 
   @EJB
   protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;
/*  89 */   protected boolean editQuantite = false;
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/*  94 */   protected Routine routine = new Routine();
 
/*  96 */   protected Boolean detail = Boolean.valueOf(true);
/*  97 */   protected Boolean modifier = Boolean.valueOf(true);
/*  98 */   protected Boolean consulter = Boolean.valueOf(true);
/*  99 */   protected Boolean imprimer = Boolean.valueOf(true);
/* 100 */   protected Boolean supprimer = Boolean.valueOf(true);
/* 101 */   protected boolean valider = true;
/* 102 */   protected boolean cancel = true;
 
/* 104 */   protected String fileName = "";
/* 105 */   protected boolean showSelectArticle = false;
 
/* 107 */   protected String mode = "";
/* 108 */   protected String valideBtn = "";
 
   public Boolean getDetail() {
/* 111 */     return this.detail;
   }
 
   public Boolean getModifier() {
     try {
/* 116 */       if (this.inventaire != null) {
/* 117 */         if (this.inventaire.getEtat().booleanValue())
/* 118 */           this.modifier = Boolean.valueOf(true);
         else
/* 120 */           this.modifier = Boolean.valueOf(false);
       }
       else
/* 123 */         this.modifier = Boolean.valueOf(true);
     }
     catch (Exception e) {
/* 126 */       this.modifier = Boolean.valueOf(true);
     }
/* 128 */     return this.modifier;
   }
 
   public boolean isValider() {
     try {
/* 133 */       if (this.inventaire != null) {
/* 134 */         if (this.inventaire.getEtat().booleanValue())
/* 135 */           this.valider = true;
         else
/* 137 */           this.valider = false;
       }
       else
/* 140 */         this.valider = true;
     }
     catch (Exception e) {
/* 143 */       this.valider = true;
     }
/* 145 */     return this.valider;
   }
 
   public Boolean getConsulter() {
/* 149 */     return this.consulter;
   }
 
   public Boolean getImprimer() {
/* 153 */     return this.imprimer;
   }
 
   public Boolean getSupprimer() {
     try {
/* 158 */       if (this.inventaire != null) {
/* 159 */         if (this.inventaire.getEtat().booleanValue())
/* 160 */           this.supprimer = Boolean.valueOf(true);
         else
/* 162 */           this.supprimer = Boolean.valueOf(false);
       }
       else
/* 165 */         this.supprimer = Boolean.valueOf(true);
     }
     catch (Exception e) {
/* 168 */       this.supprimer = Boolean.valueOf(true);
     }
/* 170 */     return this.supprimer;
   }
 
   public String getFileName() {
/* 174 */     return this.fileName;
   }
 
   public Routine getRoutine() {
/* 178 */     return this.routine;
   }
 
   public List<Lot> getLots() {
/* 182 */     return this.lots;
   }
 
   public Inventaire getInventaire() {
/* 186 */     return this.inventaire;
   }
 
   public void setInventaire(Inventaire inventaire) {
/* 190 */     this.inventaire = inventaire;
/* 191 */     this.supprimer = (this.detail = this.imprimer = Boolean.valueOf(inventaire == null));
   }
 
   public List<Inventaire> getInventaires() {
     try {
/* 196 */       this.inventaires = this.inventaireFacadeLocal.findAllRange();
     } catch (Exception e) {
/* 198 */       e.printStackTrace();
     }
/* 200 */     return this.inventaires;
   }
 
   public void setInventaires(List<Inventaire> inventaires) {
/* 204 */     this.inventaires = inventaires;
   }
 
   public List<Ligneinventaire> getLigneinventaires() {
/* 208 */     return this.ligneinventaires;
   }
 
   public void setLigneinventaires(List<Ligneinventaire> ligneinventaires) {
/* 212 */     this.ligneinventaires = ligneinventaires;
   }
 
   public List<Ligneinventaire> getLigneinventaires_1() {
/* 216 */     return this.ligneinventaires_1;
   }
 
   public void setLigneinventaires_1(List<Ligneinventaire> ligneinventaires_1) {
/* 220 */     this.ligneinventaires_1 = ligneinventaires_1;
   }
 
   public String getValideBtn() {
/* 224 */     return this.valideBtn;
   }
 
   public void setValideBtn(String valideBtn) {
/* 228 */     this.valideBtn = valideBtn;
   }
 
   public List<Lot> getSelectedLots() {
/* 232 */     return this.selectedLots;
   }
 
   public void setSelectedLots(List<Lot> selectedLots) {
/* 236 */     this.selectedLots = selectedLots;
   }
 
   public boolean isShowSelectArticle() {
/* 240 */     return this.showSelectArticle;
   }
 
   public boolean isEditQuantite() {
/* 244 */     return this.editQuantite;
   }
 
   public boolean isCancel() {
     try {
/* 249 */       if (this.inventaire != null) {
/* 250 */         if (this.inventaire.getEtat().booleanValue())
/* 251 */           this.cancel = false;
         else
/* 253 */           this.cancel = true;
       }
       else
/* 256 */         this.cancel = true;
     }
     catch (Exception e) {
/* 259 */       this.cancel = true;
     }
/* 261 */     return this.cancel;
   }
 
   public Magasin getMagasin() {
/* 265 */     return this.magasin;
   }
 
   public void setMagasin(Magasin magasin) {
/* 269 */     this.magasin = magasin;
   }
 
   public List<Magasin> getMagasins() {
/* 273 */     this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
/* 274 */     return this.magasins;
   }
 
   public void setMagasins(List<Magasin> magasins) {
/* 278 */     this.magasins = magasins;
   }
 
   public String getMode() {
/* 282 */     return this.mode;
   }
 
   public List<Magasinlot> getMagasinlots() {
/* 286 */     return this.magasinlots;
   }
 
   public void setMagasinlots(List<Magasinlot> magasinlots) {
/* 290 */     this.magasinlots = magasinlots;
   }
 
   public List<Magasinlot> getSelectedMagasinlots() {
/* 294 */     return this.selectedMagasinlots;
   }
 
   public void setSelectedMagasinlots(List<Magasinlot> selectedMagasinlots) {
/* 298 */     this.selectedMagasinlots = selectedMagasinlots;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.inventaire_global.AbstractInventaireGlobalController
 * JD-Core Version:    0.6.2
 */