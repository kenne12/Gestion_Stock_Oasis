/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.structure;

import entities.Magasin;
import entities.Parametrage;
import entities.Personnel;
import entities.Utilisateur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MagasinFacadeLocal;
import sessions.ParametrageFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.UtilisateurFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.Routine;

/**
 *
 * @author USER
 */
public class AbstractStructureController {

    @EJB
    protected ParametrageFacadeLocal parametrageFacadeLocal;
    protected Parametrage parametrage = new Parametrage();
    protected List<Parametrage> parametrages = new ArrayList<>();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();

    @EJB
    protected PersonnelFacadeLocal personnelFacadeLocal;
    protected Personnel personnel = new Personnel();

    @EJB
    protected UtilisateurFacadeLocal utilisateurFacadeLocal;
    protected Utilisateur utilisateur;

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;
    protected List<String> templates = new ArrayList();

    protected Boolean modifier = true;
    protected Boolean supprimer = true;

    protected int windowHeight;
    protected int column;

    protected Routine routine = new Routine();

    protected String mode = "";

    public Boolean getModifier() {
        return this.modifier;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Utilisateur getUtilisateur() {
        return this.utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {     
        this.utilisateur = utilisateur;
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

    public String getMode() {
        return this.mode;
    }

    public Parametrage getParametrage() {
        return parametrage;
    }

    public void setParametrage(Parametrage parametrage) {
        this.modifier = this.supprimer = parametrage == null;
        this.parametrage = parametrage;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public List<Parametrage> getParametrages() {
        parametrages = parametrageFacadeLocal.findAllRange();
        return parametrages;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

}
