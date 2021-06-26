package controllers.personnel;

import entities.Magasin;
import entities.Personnel;
import entities.Qualite;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MagasinFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.QualiteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractPersonnelController {

    @EJB
    protected PersonnelFacadeLocal personnelFacadeLocal;
    protected Personnel personnel;
    protected List<Personnel> personnels = new ArrayList();

    @EJB
    protected QualiteFacadeLocal qualiteFacadeLocal;
    protected Qualite qualite = new Qualite();
    protected List<Qualite> qualites = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> Magasins = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected String fileName = "";

    protected Boolean detail = (true);
    protected Boolean modifier = (true);
    protected Boolean consulter = (true);
    protected Boolean imprimer = (true);
    protected Boolean supprimer = (true);

    protected String mode = "";

    public Personnel getPersonnel() {
         return this.personnel;
    }

    public void setPersonnel(Personnel personnel) {
       this.modifier = (this.supprimer = this.detail = (personnel == null));
         this.personnel = personnel;
    }

    public List<Personnel> getPersonnels() {
        this.personnels = this.personnelFacadeLocal.findByIdStructure(SessionMBean.getParametrage().getId());
         return this.personnels;
    }

    public void setPersonnels(List<Personnel> personnels) {
         this.personnels = personnels;
    }

    public Boolean getDetail() {
        return this.detail;
    }

    public Boolean getModifier() {
        /*  79 */ return this.modifier;
    }

    public Boolean getConsulter() {
        /*  83 */ return this.consulter;
    }

    public Boolean getImprimer() {
        /*  87 */ return this.imprimer;
    }

    public List<Qualite> getQualites() {
        /*  91 */ this.qualites = this.qualiteFacadeLocal.findAllRange();
        /*  92 */ return this.qualites;
    }

    public void setQualites(List<Qualite> qualites) {
        /*  96 */ this.qualites = qualites;
    }

    public Boolean getSupprimer() {
        /* 100 */ return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        /* 104 */ this.supprimer = supprimer;
    }

    public Qualite getQualite() {
        /* 108 */ return this.qualite;
    }

    public void setQualite(Qualite qualite) {
        /* 112 */ this.qualite = qualite;
    }

    public Routine getRoutine() {
        /* 116 */ return this.routine;
    }

    public String getMode() {
        /* 120 */ return this.mode;
    }

    public Magasin getMagasin() {
        /* 124 */ return this.magasin;
    }

    public void setMagasin(Magasin Magasin) {
        /* 128 */ this.magasin = Magasin;
    }

    public List<Magasin> getMagasins() {
        this.Magasins = this.magasinFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.Magasins;
    }

    public void setMagasins(List<Magasin> Magasins) {
        this.Magasins = Magasins;
    }
}
