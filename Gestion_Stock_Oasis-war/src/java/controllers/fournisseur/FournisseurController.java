package controllers.fournisseur;

import entities.Fournisseur;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class FournisseurController extends AbstractFournisseurController implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(14L)) {
                signalError("acces_refuse");
                return;
            }

            this.mode = "Create";
            this.fournisseur = new Fournisseur();
            this.fournisseur.setContact("-");
            this.fournisseur.setAdresse("-");
            this.fournisseur.setEmail("-");
            RequestContext.getCurrentInstance().execute("PF('FournisseurCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(15L)) {
                signalError("acces_refuse");
                return;
            }
            this.mode = "Edit";
            RequestContext.getCurrentInstance().execute("PF('FournisseurCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.fournisseur.setIdfournisseur(this.fournisseurFacadeLocal.nextVal());
                this.fournisseur.setMagasin(SessionMBean.getMagasin());
                this.fournisseurFacadeLocal.create(this.fournisseur);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du fournisseur : " + this.fournisseur.getNom(), SessionMBean.getUserAccount());
                this.fournisseur = null;
                RequestContext.getCurrentInstance().execute("PF('FournisseurCreerDialog').hide()");
                signalSuccess();
            } else if (this.fournisseur != null) {
                this.fournisseurFacadeLocal.edit(this.fournisseur);
                this.fournisseur = null;
                RequestContext.getCurrentInstance().execute("PF('FournisseurCreerDialog').hide()");
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun fournisseur selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.fournisseur != null) {
                if (!Utilitaires.isAccess(16L)) {
                    signalError("acces_refuse");
                    return;
                }
                this.fournisseurFacadeLocal.remove(this.fournisseur);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du fournisseur : " + this.fournisseur.getNom(), SessionMBean.getUserAccount());
                this.fournisseur = null;
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun Fournisseur selectionnée");
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
