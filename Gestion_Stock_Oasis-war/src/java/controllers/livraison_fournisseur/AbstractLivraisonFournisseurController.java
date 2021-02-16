 package controllers.livraison_fournisseur;
 
 import entities.Article;
 import entities.Commandefournisseur;
 import entities.Fournisseur;
 import entities.Lignecommandefournisseur;
 import entities.Lignelivraisonfournisseur;
 import entities.Lignemvtstock;
 import entities.Livraisonfournisseur;
 import entities.Lot;
 import entities.Mvtstock;
 import entities.Unite;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.ejb.EJB;
 import javax.transaction.UserTransaction;
 import sessions.ArticleFacadeLocal;
 import sessions.CommandefournisseurFacadeLocal;
 import sessions.FournisseurFacadeLocal;
 import sessions.LignecommandefournisseurFacadeLocal;
 import sessions.LignelivraisonfournisseurFacadeLocal;
 import sessions.LignemvtstockFacadeLocal;
 import sessions.LivraisonfournisseurFacadeLocal;
 import sessions.LotFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.MvtstockFacadeLocal;
 import sessions.UniteFacadeLocal;
 import utils.Routine;
 
 public class AbstractLivraisonFournisseurController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected LivraisonfournisseurFacadeLocal livraisonfournisseurFacadeLocal;
/*  49 */   protected Livraisonfournisseur livraisonfournisseur = new Livraisonfournisseur();
/*  50 */   protected List<Livraisonfournisseur> livraisonfournisseurs = new ArrayList();
 
   @EJB
   protected LignelivraisonfournisseurFacadeLocal lignelivraisonfournisseurFacadeLocal;
/*  54 */   protected Lignelivraisonfournisseur lignelivraisonfournisseur = new Lignelivraisonfournisseur();
/*  55 */   protected List<Lignelivraisonfournisseur> lignelivraisonfournisseurs = new ArrayList();
 
   @EJB
   protected CommandefournisseurFacadeLocal commandefournisseurFacadeLocal;
/*  59 */   protected Commandefournisseur commandefournisseur = new Commandefournisseur();
/*  60 */   protected List<Commandefournisseur> commandefournisseurs = new ArrayList();
 
   @EJB
   protected LignecommandefournisseurFacadeLocal lignecommandefournisseurFacadeLocal;
/*  64 */   protected Lignecommandefournisseur lignecommandefournisseur = new Lignecommandefournisseur();
/*  65 */   protected List<Lignecommandefournisseur> lignecommandefournisseurs = new ArrayList();
 
   @EJB
   protected ArticleFacadeLocal articleFacadeLocal;
/*  69 */   protected Article article = new Article();
/*  70 */   protected List<Article> articles = new ArrayList();
 
   @EJB
   protected FournisseurFacadeLocal fournisseurFacadeLocal;
/*  74 */   protected Fournisseur fournisseur = new Fournisseur();
 
   @EJB
   protected LotFacadeLocal lotFacadeLocal;
/*  78 */   protected Lot lot = new Lot();
/*  79 */   protected List<Lot> lots = new ArrayList();
 
   @EJB
   protected MvtstockFacadeLocal mvtstockFacadeLocal;
/*  83 */   protected Mvtstock mvtstock = new Mvtstock();
 
   @EJB
   protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
/*  87 */   protected List<Lignemvtstock> lignemvtstocks = new ArrayList();
 
   @EJB
   protected UniteFacadeLocal uniteFacadeLocal;
/*  91 */   protected List<Unite> unites = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/*  96 */   protected Routine routine = new Routine();
 
/*  98 */   protected Double cout_quantite = Double.valueOf(0.0D);
/*  99 */   protected Double total = Double.valueOf(0.0D);
 
/* 101 */   protected Boolean showSelectorCommand = Boolean.valueOf(true);
 
/* 103 */   protected Boolean detail = Boolean.valueOf(true);
/* 104 */   protected Boolean modifier = Boolean.valueOf(true);
/* 105 */   protected Boolean consulter = Boolean.valueOf(true);
/* 106 */   protected Boolean imprimer = Boolean.valueOf(true);
/* 107 */   protected Boolean supprimer = Boolean.valueOf(true);
 
/* 109 */   protected String fileName = "";
 
/* 111 */   protected String mode = "";
 
   public Article getArticle() {
/* 114 */     return this.article;
   }
 
   public void setArticle(Article article) {
/* 118 */     this.article = article;
   }
 
   public List<Article> getArticles() {
/* 122 */     return this.articles;
   }
 
   public Boolean getDetail() {
/* 126 */     return this.detail;
   }
 
   public Boolean getModifier() {
/* 130 */     return this.modifier;
   }
 
   public Boolean getConsulter() {
/* 134 */     return this.consulter;
   }
 
   public Boolean getImprimer() {
/* 138 */     return this.imprimer;
   }
 
   public Boolean getSupprimer() {
/* 142 */     return this.supprimer;
   }
 
   public Double getCout_quantite() {
/* 146 */     return this.cout_quantite;
   }
 
   public void setCout_quantite(Double cout_quantite) {
/* 150 */     this.cout_quantite = cout_quantite;
   }
 
   public Double getTotal() {
/* 154 */     return this.total;
   }
 
   public void setTotal(Double total) {
/* 158 */     this.total = total;
   }
 
   public String getFileName() {
/* 162 */     return this.fileName;
   }
 
   public Boolean getShowSelectorCommand() {
/* 166 */     return this.showSelectorCommand;
   }
 
   public Routine getRoutine() {
/* 170 */     return this.routine;
   }
 
   public Lot getLot() {
/* 174 */     return this.lot;
   }
 
   public void setLot(Lot lot) {
/* 178 */     this.lot = lot;
   }
 
   public List<Lot> getLots() {
/* 182 */     return this.lots;
   }
 
   public Mvtstock getMvtstock() {
/* 186 */     return this.mvtstock;
   }
 
   public void setMvtstock(Mvtstock mvtstock) {
/* 190 */     this.mvtstock = mvtstock;
   }
 
   public List<Lignemvtstock> getLignemvtstocks() {
/* 194 */     return this.lignemvtstocks;
   }
 
   public Commandefournisseur getCommandefournisseur() {
/* 198 */     return this.commandefournisseur;
   }
 
   public void setCommandefournisseur(Commandefournisseur commandefournisseur) {
/* 202 */     this.commandefournisseur = commandefournisseur;
   }
 
   public List<Commandefournisseur> getCommandefournisseurs() {
/* 206 */     return this.commandefournisseurs;
   }
 
   public void setCommandefournisseurs(List<Commandefournisseur> commandefournisseurs) {
/* 210 */     this.commandefournisseurs = commandefournisseurs;
   }
 
   public Lignecommandefournisseur getLignecommandefournisseur() {
/* 214 */     return this.lignecommandefournisseur;
   }
 
   public void setLignecommandefournisseur(Lignecommandefournisseur lignecommandefournisseur) {
/* 218 */     this.lignecommandefournisseur = lignecommandefournisseur;
   }
 
   public List<Lignecommandefournisseur> getLignecommandefournisseurs() {
/* 222 */     return this.lignecommandefournisseurs;
   }
 
   public void setLignecommandefournisseurs(List<Lignecommandefournisseur> lignecommandefournisseurs) {
/* 226 */     this.lignecommandefournisseurs = lignecommandefournisseurs;
   }
 
   public Livraisonfournisseur getLivraisonfournisseur() {
/* 230 */     return this.livraisonfournisseur;
   }
 
   public void setLivraisonfournisseur(Livraisonfournisseur livraisonfournisseur) {
/* 234 */     this.livraisonfournisseur = livraisonfournisseur;
/* 235 */     this.modifier = (this.supprimer = this.detail = this.imprimer = Boolean.valueOf(livraisonfournisseur == null));
   }
 
   public List<Livraisonfournisseur> getLivraisonfournisseurs() {
     try {
/* 240 */       this.livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(false);
     } catch (Exception e) {
/* 242 */       e.printStackTrace();
     }
/* 244 */     return this.livraisonfournisseurs;
   }
 
   public Lignelivraisonfournisseur getLignelivraisonfournisseur() {
/* 248 */     return this.lignelivraisonfournisseur;
   }
 
   public List<Lignelivraisonfournisseur> getLignelivraisonfournisseurs() {
/* 252 */     return this.lignelivraisonfournisseurs;
   }
 
   public Fournisseur getFournisseur() {
/* 256 */     return this.fournisseur;
   }
 
   public void setFournisseur(Fournisseur fournisseur) {
/* 260 */     this.fournisseur = fournisseur;
   }
 
   public List<Unite> getUnites() {
/* 264 */     this.unites = this.uniteFacadeLocal.findAllRange();
/* 265 */     return this.unites;
   }
 
   public void setUnites(List<Unite> unites) {
/* 269 */     this.unites = unites;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.livraison_fournisseur.AbstractLivraisonFournisseurController
 * JD-Core Version:    0.6.2
 */