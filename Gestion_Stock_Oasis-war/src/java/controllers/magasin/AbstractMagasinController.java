package controllers.magasin;

import entities.Laboratoire;
import entities.Magasin;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.LaboratoireFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractMagasinController {

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin;
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected LaboratoireFacadeLocal laboratoireFacadeLocal;
    protected Laboratoire laboratoire = new Laboratoire();
    protected List<Laboratoire> laboratoires = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Boolean detail = (true);
    protected Boolean modifier = (true);
    protected Boolean consulter = (true);
    protected Boolean imprimer = (true);
    protected Boolean supprimer = (true);

    protected Routine routine = new Routine();

    protected String mode = "";

    public Boolean getDetail() {
        /*  48 */ return this.detail;
    }

    public void setDetail(Boolean detail) {
        /*  52 */ this.detail = detail;
    }

    public Boolean getModifier() {
        /*  56 */ return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        /*  60 */ this.modifier = modifier;
    }

    public Boolean getConsulter() {
        /*  64 */ return this.consulter;
    }

    public void setConsulter(Boolean consulter) {
        /*  68 */ this.consulter = consulter;
    }

    public Boolean getImprimer() {
        /*  72 */ return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        /*  76 */ this.imprimer = imprimer;
    }

    public Boolean getSupprimer() {
        /*  80 */ return this.supprimer;
    }

    public void setSupprimer(Boolean supprimer) {
        this.supprimer = supprimer;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.modifier = (this.supprimer = this.detail = (magasin == null));
        this.magasin = magasin;
    }

    public List<Magasin> getMagasins() {
        this.magasins = this.magasinFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
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
