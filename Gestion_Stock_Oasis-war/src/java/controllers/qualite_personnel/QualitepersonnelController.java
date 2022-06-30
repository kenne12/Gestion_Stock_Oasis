package controllers.qualite_personnel;

import entities.Laboratoire;
import entities.Qualite;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class QualitepersonnelController extends AbstractQualitepersonnelController implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(13L)) {
                signalError("acces_refuse");
                return;
            }
            this.mode = "Create";
            this.qualite = new Qualite();
            this.laboratoire = new Laboratoire();
            RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(13L)) {
                signalError("acces_refuse");
                return;
            }
            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.qualite.setIdqualite(this.qualiteFacadeLocal.nextVal());
                this.qualiteFacadeLocal.create(this.qualite);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la qualité du personnel : " + this.qualite.getNom(), SessionMBean.getUserAccount());
                this.qualite = null;
                RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').hide()");
                signalSuccess();
            } else if (this.qualite != null) {
                this.qualiteFacadeLocal.edit(this.qualite);
                this.qualite = null;
                this.modifier = (this.supprimer = true);
                RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').hide()");
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun qualite selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.qualite != null) {
                if (!Utilitaires.isAccess((13L))) {
                    signalError("acces_refuse");
                    return;
                }

                this.qualiteFacadeLocal.remove(this.qualite);
                this.modifier = (this.supprimer = true);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de la qualité du personnel : " + this.qualite.getNom(), SessionMBean.getUserAccount());
                this.qualite = null;
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucune Qualite selectionnée");
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
