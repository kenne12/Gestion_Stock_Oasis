 package controllers.privilege;
 
 import entities.Menu;
 import entities.Privilege;
 import entities.Utilisateur;
 import java.util.ArrayList;
 import java.util.List;
 import javax.ejb.EJB;
 import org.primefaces.model.DualListModel;
 import sessions.MenuFacadeLocal;
 import sessions.MouchardFacadeLocal;
 import sessions.PrivilegeFacadeLocal;
 import sessions.UtilisateurFacadeLocal;
 
 public class AbstractPrivilegeController
 {
 
   @EJB
   protected PrivilegeFacadeLocal privilegeFacadeLocal;
/*  29 */   protected List<Privilege> privileges = new ArrayList();
 
   @EJB
   protected MenuFacadeLocal menuFacadeLocal;
/*  33 */   protected List<Menu> menus = new ArrayList();
/*  34 */   protected DualListModel<Menu> dualMenu = new DualListModel();
 
   @EJB
   protected UtilisateurFacadeLocal utilisateurFacadeLocal;
   protected Utilisateur utilisateur;
/*  39 */   protected List<Utilisateur> utilisateurs = new ArrayList();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
   protected String fileName;
/*  46 */   protected boolean detail = true;
/*  47 */   protected boolean supprimer = true;
/*  48 */   protected boolean imprimer = true;
 
/*  50 */   protected Boolean showPrivilegeCreateDialog = Boolean.valueOf(false);
/*  51 */   protected Boolean showPrivilegeDeleteDialog = Boolean.valueOf(false);
/*  52 */   protected Boolean showPrivilegePrintDialog = Boolean.valueOf(false);
 
   public boolean isDetail() {
/*  55 */     return this.detail;
   }
 
   public boolean isSupprimer() {
/*  59 */     return this.supprimer;
   }
 
   public boolean isImprimer() {
/*  63 */     this.imprimer = this.privilegeFacadeLocal.findAll().isEmpty();
/*  64 */     return this.imprimer;
   }
 
   public List<Menu> getMenus() {
/*  68 */     return this.menus;
   }
 
   public void setMenus(List<Menu> menus) {
/*  72 */     this.menus = menus;
   }
 
   public DualListModel<Menu> getDualMenu() {
/*  76 */     return this.dualMenu;
   }
 
   public void setDualMenu(DualListModel<Menu> dualMenu) {
/*  80 */     this.dualMenu = dualMenu;
   }
 
   public Utilisateur getUtilisateur() {
/*  84 */     return this.utilisateur;
   }
 
   public void setUtilisateur(Utilisateur utilisateur) {
/*  88 */     this.utilisateur = utilisateur;
   }
 
   public List<Utilisateur> getUtilisateurs() {
/*  92 */     this.utilisateurs = this.utilisateurFacadeLocal.findByActif(Boolean.valueOf(true));
/*  93 */     return this.utilisateurs;
   }
 
   public void setUtilisateurs(List<Utilisateur> utilisateurs) {
/*  97 */     this.utilisateurs = utilisateurs;
   }
 
   public List<Privilege> getPrivileges() {
/* 101 */     return this.privileges;
   }
 
   public void setPrivileges(List<Privilege> privileges) {
/* 105 */     this.privileges = privileges;
   }
 
   public Boolean getShowPrivilegeCreateDialog() {
/* 109 */     return this.showPrivilegeCreateDialog;
   }
 
   public void setShowPrivilegeCreateDialog(Boolean showPrivilegeCreateDialog) {
/* 113 */     this.showPrivilegeCreateDialog = showPrivilegeCreateDialog;
   }
 
   public Boolean getShowPrivilegeDeleteDialog() {
/* 117 */     return this.showPrivilegeDeleteDialog;
   }
 
   public void setShowPrivilegeDeleteDialog(Boolean showPrivilegeDeleteDialog) {
/* 121 */     this.showPrivilegeDeleteDialog = showPrivilegeDeleteDialog;
   }
 
   public Boolean getShowPrivilegePrintDialog() {
/* 125 */     return this.showPrivilegePrintDialog;
   }
 
   public void setShowPrivilegePrintDialog(Boolean showPrivilegePrintDialog) {
/* 129 */     this.showPrivilegePrintDialog = showPrivilegePrintDialog;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.privilege.AbstractPrivilegeController
 * JD-Core Version:    0.6.2
 */