package controllers.unite;

import entities.Unite;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class UniteController extends AbstractUniteController implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(15L)) {
                signalError("acces_refuse");
                return;
            }

            unite = new Unite();
            unite.setCode(Utilitaires.genererCodeArticle("U", uniteFacadeLocal.nextValByIdstructure(SessionMBean.getParametrage().getId())));
            this.mode = "Create";
            RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').show()");
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
            RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').show()");
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                this.unite.setIdunite(this.uniteFacadeLocal.nextVal());
                this.unite.setMagasin(SessionMBean.getMagasin());
                this.unite.setDateEnregistre(Date.from(Instant.now()));
                this.unite.setDerniereModif(Date.from(Instant.now()));
                this.uniteFacadeLocal.create(this.unite);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'unité : " + this.unite.getLibelle(), SessionMBean.getUserAccount());
                this.unite = new Unite();
                this.modifier = this.detail = this.supprimer = true;
                RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').hide()");
                signalSuccess();
            } else if (this.unite != null) {
                this.unite.setDerniereModif(Date.from(Instant.now()));
                this.uniteFacadeLocal.edit(this.unite);
                this.unite = new Unite();
                this.modifier = this.detail = this.supprimer = true;
                RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').hide()");
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucun unite selectionné");
            }
        } catch (Exception e) {
            signalException(e);
        }
    }

    public void delete() {
        try {
            if (this.unite != null) {
                if (!Utilitaires.isAccess((15L))) {
                    signalError("acces_refuse");
                    return;
                }

                this.uniteFacadeLocal.remove(this.unite);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'unité : " + this.unite.getLibelle(), SessionMBean.getUserAccount());
                this.unite = null;
                this.modifier = (this.detail = this.supprimer = (true));
                signalSuccess();
            } else {
                JsfUtil.addErrorMessage("Aucune Unite selectionnée");
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
