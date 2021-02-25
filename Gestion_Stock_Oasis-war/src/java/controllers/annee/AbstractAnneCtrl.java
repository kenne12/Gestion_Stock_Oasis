package controllers.annee;

import entities.Annee;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;

public class AbstractAnneCtrl {

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = new Annee();
    protected Annee selectedAnnee = new Annee();
    protected List<Annee> annees = new ArrayList<>();
    protected List<Annee> anneeCourantes = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Routine routine = new Routine();

    protected boolean detail = true;
    protected boolean modifier = true;
    protected boolean supprimer = true;
    protected boolean imprimer = true;

    public List<Annee> getAnnees() {
        this.annees = this.anneeFacadeLocal.findByRange();
        return this.annees;
    }

    public Annee getSelectedAnnee() {
        return this.selectedAnnee;
    }

    public void setSelectedAnnee(Annee selectedAnnee) {
        this.selectedAnnee = selectedAnnee;
        this.modifier = this.supprimer = this.detail = (selectedAnnee == null);
    }

    public boolean isDetail() {
        return this.detail;
    }

    public boolean isModifier() {
        return this.modifier;
    }

    public boolean isSupprimer() {
        return this.supprimer;
    }

    public boolean isImprimer() {
        this.imprimer = this.anneeFacadeLocal.findAll().isEmpty();
        return this.imprimer;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public void setAnneeCourantes(List<Annee> anneeCourantes) {
        this.anneeCourantes = anneeCourantes;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public void setRoutine(Routine routine) {
        this.routine = routine;
    }
}
