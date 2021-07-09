package controllers.unite;

import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MouchardFacadeLocal;
import sessions.UniteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractUniteController {

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected Unite unite = new Unite();
    protected List<Unite> unites = new ArrayList();

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

    public Boolean getModifier() {
        return this.modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.modifier = this.supprimer = this.detail = unite == null;
        this.unite = unite;
    }

    public List<Unite> getUnites() {
        this.unites = this.uniteFacadeLocal.findByStructure(SessionMBean.getParametrage().getId());
        return this.unites;
    }

    public Routine getRoutine() {
        return this.routine;
    }
}
