package controllers.utilisateur;

import entities.Magasin;
import entities.Personnel;
import entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.DemandeFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.UtilisateurFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.Routine;

public class AbstractUtilisateurController {

    @EJB
    protected UtilisateurFacadeLocal utilisateurFacadeLocal;
    protected Utilisateur utilisateur;
    protected List<Utilisateur> utilisateurs = new ArrayList();

    @EJB
    protected PersonnelFacadeLocal personnelFacadeLocal;
    protected Personnel personnel = new Personnel();
    protected List<Personnel> personnels = new ArrayList();

    protected List<Utilisateur> utilisateurActifs = new ArrayList();
    protected List<Utilisateur> utilisateurInactifs = new ArrayList();

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected List<Magasin> magasins = new ArrayList();
    protected List<Magasin> selectedMagasins = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected DemandeFacadeLocal demandeFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;
    protected List<String> templates = new ArrayList();

    protected Boolean detail = Boolean.valueOf(true);
    protected Boolean modifier = Boolean.valueOf(true);
    protected Boolean consulter = Boolean.valueOf(true);
    protected Boolean imprimer = Boolean.valueOf(true);
    protected Boolean supprimer = Boolean.valueOf(true);

    /*  63 */    protected Boolean showEditSolde = Boolean.valueOf(true);

    /*  65 */    protected Boolean showUserCreateDialog = Boolean.valueOf(false);
    /*  66 */    protected Boolean showUserDetailDialog = Boolean.valueOf(false);
    /*  67 */    protected Boolean showUserDeleteDialog = Boolean.valueOf(false);
    /*  68 */    protected Boolean showUserEditDialog = Boolean.valueOf(false);
    /*  69 */    protected Boolean showUserPrintDialog = Boolean.valueOf(false);

    /*  71 */    protected boolean buttonActif = false;
    /*  72 */    protected boolean buttonInactif = true;

    /*  74 */    protected Routine routine = new Routine();

    /*  76 */    protected String mode = "";

    public Boolean getDetail() {
        /*  79 */ return this.detail;
    }

    public void setDetail(Boolean detail) {
        /*  83 */ this.detail = detail;
    }

    public Boolean getModifier() {
        /*  87 */ return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        /*  91 */ this.modifier = modifier;
    }

    public Boolean getConsulter() {
        /*  95 */ return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        /*  99 */ this.consulter = consulter;
    }

    public Boolean getImprimer() {
        /* 103 */ this.imprimer = Boolean.valueOf(this.utilisateurFacadeLocal.findAll().isEmpty());
        /* 104 */ return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        /* 108 */ this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        /* 112 */ return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        /* 116 */ this.supprimer = supprimer;
    }

    public Boolean getShowEditSolde() {
        /* 120 */ return this.showEditSolde;
    }

    public void setShowEditSolde(Boolean showEditSolde) {
        /* 124 */ this.showEditSolde = showEditSolde;
    }

    public Utilisateur getUtilisateur() {
        /* 128 */ return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        /* 132 */ this.modifier = (this.supprimer = this.detail = Boolean.valueOf(utilisateur == null));
        /* 133 */ this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        /* 137 */ this.utilisateurs = this.utilisateurFacadeLocal.findAll();
        /* 138 */ return this.utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        /* 142 */ this.utilisateurs = utilisateurs;
    }

    public Boolean getShowUserCreateDialog() {
        /* 146 */ return this.showUserCreateDialog;
    }

    public void setShowUserCreateDialog(Boolean showUserCreateDialog) {
        /* 150 */ this.showUserCreateDialog = showUserCreateDialog;
    }

    public Boolean getShowUserDetailDialog() {
        /* 154 */ return this.showUserDetailDialog;
    }

    public void setShowUserDetailDialog(Boolean showUserDetailDialog) {
        /* 158 */ this.showUserDetailDialog = showUserDetailDialog;
    }

    public Boolean getShowUserDeleteDialog() {
        /* 162 */ return this.showUserDeleteDialog;
    }

    public void setShowUserDeleteDialog(Boolean showUserDeleteDialog) {
        /* 166 */ this.showUserDeleteDialog = showUserDeleteDialog;
    }

    public Boolean getShowUserEditDialog() {
        /* 170 */ return this.showUserEditDialog;
    }

    public void setShowUserEditDialog(Boolean showUserEditDialog) {
        /* 174 */ this.showUserEditDialog = showUserEditDialog;
    }

    public Boolean getShowUserPrintDialog() {
        /* 178 */ return this.showUserPrintDialog;
    }

    public void setShowUserPrintDialog(Boolean showUserPrintDialog) {
        /* 182 */ this.showUserPrintDialog = showUserPrintDialog;
    }

    public List<Utilisateur> getUtilisateurActifs() {
        /* 186 */ this.utilisateurActifs = this.utilisateurFacadeLocal.findByActif(Boolean.valueOf(true));
        /* 187 */ return this.utilisateurActifs;
    }

    public void setUtilisateurActifs(List<Utilisateur> utilisateurActifs) {
        /* 191 */ this.utilisateurActifs = utilisateurActifs;
    }

    public List<Utilisateur> getUtilisateurInactifs() {
        /* 195 */ this.utilisateurInactifs = this.utilisateurFacadeLocal.findByActif(Boolean.valueOf(false));
        /* 196 */ return this.utilisateurInactifs;
    }

    public void setUtilisateurInactifs(List<Utilisateur> utilisateurInactifs) {
        /* 200 */ this.utilisateurInactifs = utilisateurInactifs;
    }

    public boolean isButtonActif() {
        /* 204 */ return this.buttonActif;
    }

    public void setButtonActif(boolean buttonActif) {
        /* 208 */ this.buttonActif = buttonActif;
    }

    public boolean isButtonInactif() {
        /* 212 */ return this.buttonInactif;
    }

    public void setButtonInactif(boolean buttonInactif) {
        /* 216 */ this.buttonInactif = buttonInactif;
    }

    public List<String> getTemplates() {
        /* 220 */ return this.templates;
    }

    public Routine getRoutine() {
        /* 224 */ return this.routine;
    }

    public Personnel getPersonnel() {
        /* 228 */ return this.personnel;
    }

    public void setPersonnel(Personnel personnel) {
        /* 232 */ this.personnel = personnel;
    }

    public List<Personnel> getPersonnels() {
        /* 236 */ return this.personnels;
    }

    public List<Magasin> getMagasins() {
        /* 240 */ return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        /* 244 */ this.magasins = magasins;
    }

    public List<Magasin> getSelectedMagasins() {
        /* 248 */ return this.selectedMagasins;
    }

    public void setSelectedMagasins(List<Magasin> selectedMagasins) {
        /* 252 */ this.selectedMagasins = selectedMagasins;
    }

    public String getMode() {
        /* 256 */ return this.mode;
    }
}

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.utilisateur.AbstractUtilisateurController
 * JD-Core Version:    0.6.2
 */
