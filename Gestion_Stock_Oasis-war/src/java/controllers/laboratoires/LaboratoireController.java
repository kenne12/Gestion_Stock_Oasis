package controllers.laboratoires;

import entities.Laboratoire;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class LaboratoireController extends AbstractLaboratoireController implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(10L)) {
                signalError("acces_refuse");
                return;
            }

            this.laboratoire = new Laboratoire();
            this.mode = "Create";
            RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(11L)) {
                signalError("acces_refuse");
                return;
            }
            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.laboratoire.setIdlaboratoire(this.laboratoireFacadeLocal.nextVal());
                this.laboratoireFacadeLocal.create(this.laboratoire);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du laboratoire : " + this.laboratoire.getNom(), SessionMBean.getUserAccount());
                this.laboratoire = null;
                RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').hide()");
                signalSuccess();
            } else if (this.laboratoire != null) {
                this.laboratoireFacadeLocal.edit(this.laboratoire);
                this.laboratoire = null;
                RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').hide()");
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun laboratoire selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.laboratoire != null) {
                if (!Utilitaires.isAccess(12L)) {
                    signalError("acces_refuse");
                    return;
                }

                this.laboratoireFacadeLocal.remove(this.laboratoire);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du laboratoire : " + this.laboratoire.getNom(), SessionMBean.getUserAccount());
                this.laboratoire = null;
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucune Laboratoire selectionnée");
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
