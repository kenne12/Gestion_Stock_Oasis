package controllers.fournisseur;

import entities.Fournisseur;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.FournisseurFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractFournisseurController {

    @EJB
    protected FournisseurFacadeLocal fournisseurFacadeLocal;
    protected Fournisseur fournisseur;
    protected List<Fournisseur> fournisseurs = new ArrayList();

    protected Routine routine = new Routine();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

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

    public Fournisseur getFournisseur() {
        return this.fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.modifier = (this.supprimer = this.detail = (fournisseur == null));
        this.fournisseur = fournisseur;
    }

    public List<Fournisseur> getFournisseurs() {
        this.fournisseurs = this.fournisseurFacadeLocal.findByIdstructure(SessionMBean.getParametrage().getId());
        return this.fournisseurs;
    }

    public Routine getRoutine() {
        return this.routine;
    }
}
