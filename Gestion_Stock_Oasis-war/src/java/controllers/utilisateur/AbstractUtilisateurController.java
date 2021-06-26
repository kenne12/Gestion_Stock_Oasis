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
import utils.SessionMBean;

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

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected boolean buttonActif = false;
    protected boolean buttonInactif = true;

    protected Routine routine = new Routine();

    protected String mode = "";

    public Boolean getDetail() {
        return this.detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public Boolean getImprimer() {
        //this.imprimer = (this.utilisateurFacadeLocal.findAll().isEmpty());
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.modifier = (this.supprimer = this.detail = utilisateur == null);
        this.utilisateur = utilisateur;
    }

    public List<Utilisateur> getUtilisateurs() {
        this.utilisateurs = this.utilisateurFacadeLocal.findByIdStructure(SessionMBean.getParametrage().getId());
        return this.utilisateurs;
    }

    public List<Utilisateur> getUtilisateurActifs() {
        this.utilisateurActifs = this.utilisateurFacadeLocal.findByIdStructureEtat(SessionMBean.getParametrage().getId(), true);
        return this.utilisateurActifs;
    }

    public List<Utilisateur> getUtilisateurInactifs() {
        this.utilisateurInactifs = this.utilisateurFacadeLocal.findByIdStructureEtat(SessionMBean.getParametrage().getId(), false);
        return this.utilisateurInactifs;
    }

    public boolean isButtonActif() {
        return this.buttonActif;
    }

    public boolean isButtonInactif() {
        return this.buttonInactif;
    }

    public List<String> getTemplates() {
        return this.templates;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public Personnel getPersonnel() {
        return this.personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public List<Personnel> getPersonnels() {
        return this.personnels;
    }

    public List<Magasin> getMagasins() {
        return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
    }

    public List<Magasin> getSelectedMagasins() {
        return this.selectedMagasins;
    }

    public void setSelectedMagasins(List<Magasin> selectedMagasins) {
        this.selectedMagasins = selectedMagasins;
    }

    public String getMode() {
        return this.mode;
    }
}
