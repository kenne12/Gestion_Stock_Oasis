package controllers.magasin;

import entities.Magasin;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class MagasinController extends AbstractMagasinController
        implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(23L)) {
                signalError("acces_refuse");
                return;
            }
            this.mode = "Create";
            this.magasin = new Magasin();
            this.magasin.setCentral(false);
            RequestContext.getCurrentInstance().execute("PF('MagasinCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(24L)) {
                signalError("acces_refuse");
                return;
            }

            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('MagasinCreerDialog').show()");
        } catch (Exception e) {
             signalException(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.magasin.setIdmagasin(this.magasinFacadeLocal.nextVal());
                this.magasinFacadeLocal.create(this.magasin);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du magasin : " + this.magasin.getNom(), SessionMBean.getUserAccount());
                this.magasin = null;
                RequestContext.getCurrentInstance().execute("PF('MagasinCreerDialog').hide()");
                signalSuccess();
            } else if (this.magasin != null) {
                this.magasinFacadeLocal.edit(this.magasin);
                this.magasin = null;
                this.modifier = this.supprimer = true;
                RequestContext.getCurrentInstance().execute("PF('MagasinCreerDialog').hide()");
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun magasin selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.magasin != null) {
                if (!Utilitaires.isAccess(25L)) {
                    signalError("acces_refuse");
                    return;
                }

                this.magasinFacadeLocal.remove(this.magasin);
                this.modifier = this.supprimer = true;
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du magasin : " + this.magasin.getNom(), SessionMBean.getUserAccount());
                this.magasin = null;
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucune Magasin selectionnée");
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
