package controllers.annee;

import entities.Annee;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean(name = "anneeController")
@ViewScoped
public class AnneeController extends AbstractAnneCtrl implements AnneeInterfaceCtrl, Serializable {

    @PostConstruct
    private void initAnnee() {
        this.selectedAnnee = new Annee();
        this.annee = new Annee();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(32L)) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {
                this.annee = new Annee();
                this.annee.setEtat(true);
                RequestContext.getCurrentInstance().execute("PF('AnneeCreerDialog').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(32L)) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {
                RequestContext.getCurrentInstance().execute("PF('AnneeModifierDialog').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    @Override
    public void enregistrerAnnee() {
        try {
            Annee an = this.anneeFacadeLocal.findByCode(this.annee.getNom());
            if (an != null) {
                JsfUtil.addErrorMessage("Un exercice ayant ces paramètres existe déjà");
            } else {
                this.annee.setIdannee(this.anneeFacadeLocal.nextVal());
                this.anneeFacadeLocal.create(this.annee);

                this.modifier = this.detail = this.supprimer = true;

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Création  de l'exercice : " + this.annee.getNom(), SessionMBean.getUserAccount());
                this.annee = null;

                RequestContext.getCurrentInstance().execute("PF('AnneeCreerDialog').hide()");
                this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    @Override
    public void modifier() {
        try {
            if (this.selectedAnnee == null) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("not_row_selected"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {
                this.anneeFacadeLocal.edit(this.selectedAnnee);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification de l'exercice -> " + this.selectedAnnee.getNom(), SessionMBean.getUserAccount());
                this.modifier = this.detail = this.supprimer = true;
                this.selectedAnnee = null;

                RequestContext.getCurrentInstance().execute("PF('AnneeModifierDialog').hide()");
                this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    @Override
    public void supprimer() {
        try {
            if (!Utilitaires.isAccess(32L)) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }
            if (this.selectedAnnee == null || this.selectedAnnee.getIdannee() == null) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("not_row_selected"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }
            this.anneeFacadeLocal.remove(this.selectedAnnee);
            this.modifier = this.detail = this.supprimer = true;
            Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'exercice -> " + this.selectedAnnee.getNom(), SessionMBean.getUserAccount());
            this.selectedAnnee = null;
            this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("suppression_impossible"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }
}
