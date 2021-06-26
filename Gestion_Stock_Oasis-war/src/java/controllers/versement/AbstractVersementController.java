package controllers.versement;

import entities.Livraisonclient;
import entities.Versement;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.LivraisonclientFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.VersementFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractVersementController {

    @Resource
    protected UserTransaction ut;
    @EJB
    protected VersementFacadeLocal versementFacadeLocal;
    protected Versement versement = new Versement();
    protected List<Versement> versements = new ArrayList<>();

    @EJB
    protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;
    protected Livraisonclient livraisonclient = new Livraisonclient();
    protected List<Livraisonclient> livraisonclients = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Routine routine = new Routine();

    protected Boolean modifier = true;
    protected Boolean supprimer = true;

    protected Boolean showClient = true;

    protected String mode = "";
    protected String fileName = "";

    public Boolean getModifier() {
        return this.modifier;
    }

    public void setModifier(Boolean modifier) {
        this.modifier = modifier;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Versement getVersement() {
        return this.versement;
    }

    public void setVersement(Versement versement) {
        this.versement = versement;
        this.supprimer = this.modifier = (versement == null);
    }

    public List<Versement> getVersements() {
        this.versements = this.versementFacadeLocal.findByIdmagasin(SessionMBean.getMagasin().getIdmagasin());
        return this.versements;
    }

    public Boolean getShowClient() {
        return this.showClient;
    }

    public Livraisonclient getLivraisonclient() {
        return this.livraisonclient;
    }

    public void setLivraisonclient(Livraisonclient livraisonclient) {
        this.livraisonclient = livraisonclient;
    }

    public List<Livraisonclient> getLivraisonclients() {
        return this.livraisonclients;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public String getFileName() {
        return this.fileName;
    }
}
