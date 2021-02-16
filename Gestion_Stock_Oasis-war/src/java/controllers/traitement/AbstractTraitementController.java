 package controllers.traitement;
 
 import entities.Article;
 import entities.Demande;
 import entities.Lignedemande;
 import entities.Lignelivraisonclient;
 import entities.Lignemvtstock;
 import entities.Livraisonclient;
 import entities.Lot;
 import entities.Mvtstock;
 import entities.Personnel;
 import entities.Unite;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.ejb.EJB;
 import javax.transaction.UserTransaction;
 import sessions.ArticleFacadeLocal;
 import sessions.DemandeFacadeLocal;
 import sessions.LignedemandeFacadeLocal;
 import sessions.LignelivraisonclientFacadeLocal;
 import sessions.LignemvtstockFacadeLocal;
 import sessions.LivraisonclientFacadeLocal;
 import sessions.LotFacadeLocal;
 import sessions.MagasinarticleFacadeLocal;
 import sessions.MagasinlotFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.MvtstockFacadeLocal;
 import sessions.PersonnelFacadeLocal;
 import sessions.UniteFacadeLocal;
 import utils.Routine;
 
 public class AbstractTraitementController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected DemandeFacadeLocal demandeFacadeLocal;
/*  49 */   protected Demande demande = new Demande();
/*  50 */   protected List<Demande> demandes = new ArrayList();
 
/*  52 */   protected List<Demande> demandes_1 = new ArrayList();
 
   @EJB
   protected LignedemandeFacadeLocal lignedemandeFacadeLocal;
/*  56 */   protected Lignedemande lignedemande = new Lignedemande();
/*  57 */   protected List<Lignedemande> lignedemandes = new ArrayList();
 
   @EJB
   protected ArticleFacadeLocal articleFacadeLocal;
/*  61 */   protected Article article = new Article();
/*  62 */   protected List<Article> articles = new ArrayList();
 
   @EJB
   protected PersonnelFacadeLocal personnelFacadeLocal;
/*  66 */   protected Personnel personnel = new Personnel();
/*  67 */   protected List<Personnel> personnels = new ArrayList();
 
   @EJB
   protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;
/*  71 */   protected Livraisonclient livraisonclient = new Livraisonclient();
/*  72 */   protected List<Livraisonclient> livraisonclients = new ArrayList();
 
   @EJB
   protected LignelivraisonclientFacadeLocal lignelivraisonclientFacadeLocal;
/*  76 */   protected Lignelivraisonclient lignelivraisonclient = new Lignelivraisonclient();
/*  77 */   protected List<Lignelivraisonclient> lignelivraisonclients = new ArrayList();
 
   @EJB
   protected UniteFacadeLocal uniteFacadeLocal;
/*  81 */   protected Unite unite = new Unite();
/*  82 */   protected List<Unite> unites = new ArrayList();
 
   @EJB
   protected MagasinlotFacadeLocal magasinlotFacadeLocal;
 
   @EJB
   protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
 
   @EJB
   protected LotFacadeLocal lotFacadeLocal;
/*  92 */   protected Lot lot = new Lot();
/*  93 */   protected List<Lot> lots = new ArrayList();
 
   @EJB
   protected MvtstockFacadeLocal mvtstockFacadeLocal;
/*  97 */   protected Mvtstock mvtstock = new Mvtstock();
 
   @EJB
   protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
/* 101 */   protected List<Lignemvtstock> lignemvtstocks = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/* 106 */   protected Routine routine = new Routine();
 
/* 108 */   protected Double cout_quantite = Double.valueOf(0.0D);
/* 109 */   protected Double total = Double.valueOf(0.0D);
 
/* 111 */   protected Boolean showSelectorCommand = Boolean.valueOf(true);
/* 112 */   protected boolean payement_credit = false;
 
/* 114 */   protected Boolean detail = Boolean.valueOf(true);
/* 115 */   protected Boolean modifier = Boolean.valueOf(true);
/* 116 */   protected Boolean consulter = Boolean.valueOf(true);
/* 117 */   protected Boolean imprimer = Boolean.valueOf(true);
/* 118 */   protected Boolean imprimer2 = Boolean.valueOf(true);
/* 119 */   protected Boolean supprimer = Boolean.valueOf(true);
 
/* 121 */   protected String fileName = "";
/* 122 */   protected String printDialogTitle = "";
 
/* 124 */   protected String mode = "";
 
   public Article getArticle() {
/* 127 */     return this.article;
   }
 
   public void setArticle(Article article) {
/* 131 */     this.article = article;
   }
 
   public List<Article> getArticles() {
/* 135 */     return this.articles;
   }
 
   public Personnel getPersonnel() {
/* 139 */     return this.personnel;
   }
 
   public void setPersonnel(Personnel personnel) {
/* 143 */     this.personnel = personnel;
   }
 
   public List<Personnel> getPersonnels() {
/* 147 */     return this.personnels;
   }
 
   public Livraisonclient getLivraisonclient() {
/* 151 */     return this.livraisonclient;
   }
 
   public void setLivraisonclient(Livraisonclient livraisonclient) {
/* 155 */     this.livraisonclient = livraisonclient;
   }
 
   public List<Livraisonclient> getLivraisonclients()
   {
     try {
/* 161 */       this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange();
     } catch (Exception e) {
/* 163 */       e.printStackTrace();
     }
/* 165 */     return this.livraisonclients;
   }
 
   public Lignelivraisonclient getLignelivraisonclient() {
/* 169 */     return this.lignelivraisonclient;
   }
 
   public void setLignelivraisonclient(Lignelivraisonclient lignelivraisonclient) {
/* 173 */     this.lignelivraisonclient = lignelivraisonclient;
   }
 
   public List<Lignelivraisonclient> getLignelivraisonclients() {
/* 177 */     return this.lignelivraisonclients;
   }
 
   public Boolean getDetail() {
/* 181 */     return this.detail;
   }
 
   public Boolean getModifier() {
/* 185 */     return this.modifier;
   }
 
   public Boolean getConsulter() {
/* 189 */     return this.consulter;
   }
 
   public Boolean getImprimer() {
/* 193 */     return this.imprimer;
   }
 
   public Boolean getImprimer2() {
/* 197 */     return this.imprimer2;
   }
 
   public void setImprimer2(Boolean imprimer2) {
/* 201 */     this.imprimer2 = imprimer2;
   }
 
   public Boolean getSupprimer() {
/* 205 */     return this.supprimer;
   }
 
   public Double getCout_quantite() {
/* 209 */     return this.cout_quantite;
   }
 
   public void setCout_quantite(Double cout_quantite) {
/* 213 */     this.cout_quantite = cout_quantite;
   }
 
   public Double getTotal() {
/* 217 */     return this.total;
   }
 
   public void setTotal(Double total) {
/* 221 */     this.total = total;
   }
 
   public String getFileName() {
/* 225 */     return this.fileName;
   }
 
   public Boolean getShowSelectorCommand() {
/* 229 */     return this.showSelectorCommand;
   }
 
   public Routine getRoutine() {
/* 233 */     return this.routine;
   }
 
   public Lot getLot() {
/* 237 */     return this.lot;
   }
 
   public void setLot(Lot lot) {
/* 241 */     this.lot = lot;
   }
 
   public List<Lot> getLots() {
/* 245 */     return this.lots;
   }
 
   public Demande getDemande() {
/* 249 */     return this.demande;
   }
 
   public void setDemande(Demande demande) {
/* 253 */     this.demande = demande;
/* 254 */     this.modifier = (this.supprimer = this.detail = this.imprimer = Boolean.valueOf(demande == null));
/* 255 */     if ((demande != null) && 
/* 256 */       (demande.getValidee().booleanValue())) {
/* 257 */       this.imprimer = Boolean.valueOf(false);
/* 258 */       this.imprimer2 = Boolean.valueOf(false);
     }
   }
 
   public List<Demande> getDemandes()
   {
/* 264 */     return this.demandes;
   }
 
   public Lignedemande getLignedemande() {
/* 268 */     return this.lignedemande;
   }
 
   public void setLignedemande(Lignedemande lignedemande) {
/* 272 */     this.lignedemande = lignedemande;
   }
 
   public List<Lignedemande> getLignedemandes() {
/* 276 */     return this.lignedemandes;
   }
 
   public boolean isPayement_credit() {
/* 280 */     return this.payement_credit;
   }
 
   public Mvtstock getMvtstock() {
/* 284 */     return this.mvtstock;
   }
 
   public void setMvtstock(Mvtstock mvtstock) {
/* 288 */     this.mvtstock = mvtstock;
   }
 
   public List<Lignemvtstock> getLignemvtstocks() {
/* 292 */     return this.lignemvtstocks;
   }
 
   public String getPrintDialogTitle() {
/* 296 */     return this.printDialogTitle;
   }
 
   public void setPrintDialogTitle(String printDialogTitle) {
/* 300 */     this.printDialogTitle = printDialogTitle;
   }
 
   public Unite getUnite() {
/* 304 */     return this.unite;
   }
 
   public void setUnite(Unite unite) {
/* 308 */     this.unite = unite;
   }
 
   public List<Unite> getUnites() {
/* 312 */     return this.unites;
   }
 
   public void setUnites(List<Unite> unites) {
/* 316 */     this.unites = unites;
   }
 
   public List<Demande> getDemandes_1() {
/* 320 */     return this.demandes_1;
   }
 
   public void setDemandes_1(List<Demande> demandes_1) {
/* 324 */     this.demandes_1 = demandes_1;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.traitement.AbstractTraitementController
 * JD-Core Version:    0.6.2
 */