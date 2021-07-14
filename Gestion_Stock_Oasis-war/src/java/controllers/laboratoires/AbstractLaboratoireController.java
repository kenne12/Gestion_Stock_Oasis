package controllers.laboratoires;

import entities.Laboratoire;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.LaboratoireFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;

public class AbstractLaboratoireController {

    @EJB
    protected LaboratoireFacadeLocal laboratoireFacadeLocal;
    protected Laboratoire laboratoire;
    protected List<Laboratoire> laboratoires = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected Routine routine = new Routine();

    protected String mode = "";

    public Boolean getDetail() {
        /* 41 */ return this.detail;
    }

    public void setDetail(Boolean detail) {
        /* 45 */ this.detail = detail;
    }

    public Boolean getModifier() {
        /* 49 */ return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        /* 53 */ this.modifier = modifier;
    }

    public Boolean getConsulter() {
        /* 57 */ return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        /* 61 */ this.consulter = consulter;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Laboratoire getLaboratoire() {
        return this.laboratoire;
    }

    public void setLaboratoire(Laboratoire laboratoire) {
        this.modifier = (this.supprimer = this.detail = (laboratoire == null));
        this.laboratoire = laboratoire;
    }

    public List<Laboratoire> getLaboratoires() {
        this.laboratoires = this.laboratoireFacadeLocal.findAllRange();
        return this.laboratoires;
    }

    public void setLaboratoires(List<Laboratoire> laboratoires) {
        this.laboratoires = laboratoires;
    }

    public Routine getRoutine() {
        return this.routine;
    }
}
