package controllers.qualite_personnel;

import entities.Laboratoire;
import entities.Qualite;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.LaboratoireFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.QualiteFacadeLocal;
import utils.Routine;

public class AbstractQualitepersonnelController {

    @EJB
    protected QualiteFacadeLocal qualiteFacadeLocal;
    protected Qualite qualite;
    protected List<Qualite> qualites = new ArrayList();

    @EJB
    protected LaboratoireFacadeLocal laboratoireFacadeLocal;
    protected Laboratoire laboratoire = new Laboratoire();
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
        return this.detail;
    }

    public void setDetail(Boolean detail) {
        this.detail = detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        this.consulter = consulter;
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

    public Qualite getQualite() {
        return this.qualite;
    }

    public void setQualite(Qualite qualite) {
        this.modifier = (this.supprimer = this.detail = qualite == null);
        this.qualite = qualite;
    }

    public List<Qualite> getQualites() {
        this.qualites = this.qualiteFacadeLocal.findAllRange();
        return this.qualites;
    }

    public void setQualites(List<Qualite> qualites) {
        this.qualites = qualites;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public Laboratoire getLaboratoire() {
        return this.laboratoire;
    }

    public void setLaboratoire(Laboratoire laboratoire) {
        this.laboratoire = laboratoire;
    }

    public List<Laboratoire> getLaboratoires() {
        this.laboratoires = this.laboratoireFacadeLocal.findAll();
        return this.laboratoires;
    }

    public void setLaboratoires(List<Laboratoire> laboratoires) {
        this.laboratoires = laboratoires;
    }
}
