package controllers.analyse.demande_par_personnel;

import entities.Demande;
import entities.Personnel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.DemandeFacadeLocal;
import sessions.PersonnelFacadeLocal;

public class AbstratDemandeParPersonnelReportController {

    protected Date dateDebut = new Date();
    protected Date dateFin = new Date();

    @EJB
    protected PersonnelFacadeLocal personnelFacadeLocal;
    protected Personnel personnel = new Personnel();
    protected List<Personnel> personnels = new ArrayList();

    @EJB
    protected DemandeFacadeLocal demandeFacadeLocal;
    protected List<Demande> demandes = new ArrayList();

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public Date getDateFin() {
         return this.dateFin;
    }

    public void setDateDebut(Date dateDebut) {
         this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        /* 48 */ this.dateFin = dateFin;
    }

    public Personnel getPersonnel() {
        /* 52 */ return this.personnel;
    }

    public void setPersonnel(Personnel personnel) {
        /* 56 */ this.personnel = personnel;
    }

    public List<Personnel> getPersonnels() {
        /* 60 */ this.personnels = this.personnelFacadeLocal.findAllRange();
        /* 61 */ return this.personnels;
    }

    public void setPersonnels(List<Personnel> personnels) {
        /* 65 */ this.personnels = personnels;
    }

    public List<Demande> getDemandes() {
        /* 69 */ return this.demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        /* 73 */ this.demandes = demandes;
    }
}
