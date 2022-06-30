package controllers.service;

import entities.Service;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class ServiceController extends AbstractServiceController
        implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(17L)) {
                signalError("acces_refuse");
                return;
            }

            this.service = new Service();
            this.mode = "Create";
            RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(18L)) {
                signalError("acces_refuse");
                return;
            }
            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.service.setIdservice(this.serviceFacadeLocal.nextVal());
                this.serviceFacadeLocal.create(this.service);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du service : " + this.service.getNom(), SessionMBean.getUserAccount());
                this.service = null;
                this.modifier = (this.detail = this.supprimer = true);
                RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').hide()");
                signalSuccess();
            } else if (this.service != null) {
                this.serviceFacadeLocal.edit(this.service);
                this.service = null;
                this.modifier = (this.detail = this.supprimer = true);
                RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').hide()");
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun service selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.service != null) {
                if (!Utilitaires.isAccess(19L)) {
                    signalError("acces_refuse");
                    return;
                }
                this.serviceFacadeLocal.remove(this.service);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du service : " + this.service.getNom(), SessionMBean.getUserAccount());
                this.service = null;
                this.modifier = (this.detail = this.supprimer = true);
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucune Service selectionnée");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void signalError(String chaine) {
        this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalSuccess() {
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalException(Exception e) {
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
