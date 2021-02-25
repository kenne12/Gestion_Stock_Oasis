package controllers.famille;

import entities.Famille;
import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class FamilleController extends AbstractFamilleController implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(10L)) {
                signalError("acces_refuse");
                return;
            }

            this.famille = new Famille();
            this.mode = "Create";
            RequestContext.getCurrentInstance().execute("PF('FamilleCreerDialog').show()");
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
            RequestContext.getCurrentInstance().execute("PF('FamilleCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.famille.setIdfamille(this.familleFacadeLocal.nextVal());
                this.famille.setEtat("True");
                this.famille.setDateEnregistre(new Date());
                this.famille.setDerniereModif(new Date());
                this.familleFacadeLocal.create(this.famille);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du la famille des produits : " + this.famille.getNom(), SessionMBean.getUserAccount());
                this.famille = null;
                signalSuccess();
            } else if (this.famille != null) {
                this.familleFacadeLocal.edit(this.famille);
                this.famille = null;
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun famille selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.famille != null) {
                if (!Utilitaires.isAccess(12L)) {
                    signalError("acces_refuse");
                    return;
                }

                this.familleFacadeLocal.remove(this.famille);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de la famille de produit : " + this.famille.getNom(), SessionMBean.getUserAccount());
                this.famille = null;
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucune Famille selectionnée");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void signalError(String chaine) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalSuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalException(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
