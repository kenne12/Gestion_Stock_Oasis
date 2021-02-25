package controllers.mois;

import entities.Annee;
import entities.AnneeMois;
import entities.Mois;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.MoisFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;

public class AbstractMoisCtrl {

    protected String fileName = "";

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = new Annee();
    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected MoisFacadeLocal moisFacadeLocal;
    protected Mois mois = new Mois();
    protected List<Mois> moises = new ArrayList<>();
    protected List<Mois> selectedMois = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = new AnneeMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Routine routine = new Routine();

    protected String mode = "";

    protected boolean detail = true;
    protected boolean modifier = true;
    protected boolean supprimer = true;
    protected boolean imprimer = true;

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
        return this.imprimer;
    }

    public List<Mois> getMoises() {
        return this.moises;
    }

    public void setMoises(List<Mois> moises) {
        this.moises = moises;
    }

    public List<Mois> getSelectedMois() {
        return this.selectedMois;
    }

    public void setSelectedMois(List<Mois> selectedMois) {
        this.selectedMois = selectedMois;
    }

    public AnneeMois getAnneeMois() {
        return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.detail = this.modifier = this.supprimer = (anneeMois == null);
        this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        try {
            this.anneeMoises = this.anneeMoisFacadeLocal.findByRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.anneeMoises;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        this.annees = this.anneeFacadeLocal.findByRange();
        return this.annees;
    }

    public Routine getRoutine() {
        return this.routine;
    }
}
