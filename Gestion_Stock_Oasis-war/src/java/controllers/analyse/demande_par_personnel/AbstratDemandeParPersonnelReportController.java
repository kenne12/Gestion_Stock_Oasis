package controllers.analyse.demande_par_personnel;

import entities.Demande;
import entities.Personnel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.DemandeFacadeLocal;
import sessions.PersonnelFacadeLocal;
import utils.SessionMBean;

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
        this.dateFin = dateFin;
    }

    public Personnel getPersonnel() {
        return this.personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public List<Personnel> getPersonnels() {
        this.personnels = this.personnelFacadeLocal.findByIdStructure(SessionMBean.getParametrage().getId());
        return this.personnels;
    }

    public List<Demande> getDemandes() {
        return this.demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }
}
