 package controllers.commande_fournisseur;
 
 import entities.Article;
 import entities.Commandefournisseur;
 import entities.Famille;
 import entities.Fournisseur;
 import entities.Lignecommandefournisseur;
 import entities.Lot;
 import entities.Unite;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.ejb.EJB;
 import javax.transaction.UserTransaction;
 import sessions.ArticleFacadeLocal;
 import sessions.CommandefournisseurFacadeLocal;
 import sessions.FamilleFacadeLocal;
 import sessions.FournisseurFacadeLocal;
 import sessions.LignecommandefournisseurFacadeLocal;
 import sessions.LotFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.UniteFacadeLocal;
 import utils.Routine;
 
 public class AbstractCommandefournisseurController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected CommandefournisseurFacadeLocal commandefournisseurFacadeLocal;
/*  41 */   protected Commandefournisseur commandefournisseur = new Commandefournisseur();
/*  42 */   protected List<Commandefournisseur> commandefournisseurs = new ArrayList();
 
   @EJB
   protected LignecommandefournisseurFacadeLocal lignecommandefournisseurFacadeLocal;
/*  46 */   protected Lignecommandefournisseur lignecommandefournisseur = new Lignecommandefournisseur();
/*  47 */   protected List<Lignecommandefournisseur> lignecommandefournisseurs = new ArrayList();
 
   @EJB
   protected FamilleFacadeLocal familleFacadeLocal;
/*  51 */   protected Famille famille = new Famille();
/*  52 */   protected List<Famille> familles = new ArrayList();
 
   @EJB
   protected ArticleFacadeLocal articleFacadeLocal;
/*  56 */   protected Article article = new Article();
/*  57 */   protected List<Article> articles = new ArrayList();
 
   @EJB
   protected FournisseurFacadeLocal fournisseurFacadeLocal;
/*  61 */   protected Fournisseur fournisseur = new Fournisseur();
/*  62 */   protected List<Fournisseur> fournisseurs = new ArrayList();
 
   @EJB
   protected LotFacadeLocal lotFacadeLocal;
/*  66 */   protected Lot lot = new Lot();
/*  67 */   protected List<Lot> lots = new ArrayList();
 
   @EJB
   protected UniteFacadeLocal uniteFacadeLocal;
/*  71 */   protected Unite unite = new Unite();
/*  72 */   protected List<Unite> unites = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
/*  77 */   protected Routine routine = new Routine();
 
/*  79 */   protected Double cout_quantite = Double.valueOf(0.0D);
/*  80 */   protected Double total = Double.valueOf(0.0D);
 
/*  82 */   protected Boolean detail = Boolean.valueOf(true);
/*  83 */   protected Boolean modifier = Boolean.valueOf(true);
/*  84 */   protected Boolean consulter = Boolean.valueOf(true);
/*  85 */   protected Boolean imprimer = Boolean.valueOf(true);
/*  86 */   protected Boolean supprimer = Boolean.valueOf(true);
 
/*  88 */   protected String fileName = "";
 
/*  90 */   protected String mode = "";
 
   public Article getArticle() {
/*  93 */     return this.article;
   }
 
   public void setArticle(Article article) {
/*  97 */     this.article = article;
   }
 
   public List<Article> getArticles() {
/* 101 */     return this.articles;
   }
 
   public Boolean getDetail() {
/* 105 */     return this.detail;
   }
 
   public Boolean getModifier() {
/* 109 */     return this.modifier;
   }
 
   public Boolean getConsulter() {
/* 113 */     return this.consulter;
   }
 
   public Boolean getImprimer() {
/* 117 */     return this.imprimer;
   }
 
   public Boolean getSupprimer() {
/* 121 */     return this.supprimer;
   }
 
   public Famille getFamille() {
/* 125 */     return this.famille;
   }
 
   public void setFamille(Famille famille) {
/* 129 */     this.famille = famille;
   }
 
   public List<Famille> getFamilles() {
/* 133 */     this.familles = this.familleFacadeLocal.findAllRange();
/* 134 */     return this.familles;
   }
 
   public Double getCout_quantite() {
/* 138 */     return this.cout_quantite;
   }
 
   public void setCout_quantite(Double cout_quantite) {
/* 142 */     this.cout_quantite = cout_quantite;
   }
 
   public Double getTotal() {
/* 146 */     return this.total;
   }
 
   public void setTotal(Double total) {
/* 150 */     this.total = total;
   }
 
   public String getFileName() {
/* 154 */     return this.fileName;
   }
 
   public Routine getRoutine() {
/* 158 */     return this.routine;
   }
 
   public Lot getLot() {
/* 162 */     return this.lot;
   }
 
   public void setLot(Lot lot) {
/* 166 */     this.lot = lot;
   }
 
   public List<Lot> getLots() {
/* 170 */     return this.lots;
   }
 
   public Commandefournisseur getCommandefournisseur() {
/* 174 */     return this.commandefournisseur;
   }
 
   public void setCommandefournisseur(Commandefournisseur commandefournisseur) {
/* 178 */     this.commandefournisseur = commandefournisseur;
/* 179 */     this.modifier = (this.supprimer = this.detail = this.imprimer = Boolean.valueOf(commandefournisseur == null));
   }
 
   public List<Commandefournisseur> getCommandefournisseurs() {
     try {
/* 184 */       this.commandefournisseurs = this.commandefournisseurFacadeLocal.findAllRange();
     } catch (Exception e) {
/* 186 */       e.printStackTrace();
     }
/* 188 */     return this.commandefournisseurs;
   }
 
   public Lignecommandefournisseur getLignecommandefournisseur() {
/* 192 */     return this.lignecommandefournisseur;
   }
 
   public void setLignecommandefournisseur(Lignecommandefournisseur lignecommandefournisseur) {
/* 196 */     this.lignecommandefournisseur = lignecommandefournisseur;
   }
 
   public List<Lignecommandefournisseur> getLignecommandefournisseurs() {
/* 200 */     return this.lignecommandefournisseurs;
   }
 
   public Fournisseur getFournisseur() {
/* 204 */     return this.fournisseur;
   }
 
   public void setFournisseur(Fournisseur fournisseur) {
/* 208 */     this.fournisseur = fournisseur;
   }
 
   public List<Fournisseur> getFournisseurs() {
/* 212 */     this.fournisseurs = this.fournisseurFacadeLocal.findAllRange();
/* 213 */     return this.fournisseurs;
   }
 
   public Unite getUnite() {
/* 217 */     return this.unite;
   }
 
   public void setUnite(Unite unite) {
/* 221 */     this.unite = unite;
   }
 
   public List<Unite> getUnites() {
/* 225 */     this.unites = this.uniteFacadeLocal.findAllRange();
/* 226 */     return this.unites;
   }
 
   public void setUnites(List<Unite> unites) {
/* 230 */     this.unites = unites;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.commande_fournisseur.AbstractCommandefournisseurController
 * JD-Core Version:    0.6.2
 */