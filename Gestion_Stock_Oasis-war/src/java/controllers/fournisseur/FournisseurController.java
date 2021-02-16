package controllers.fournisseur;

import entities.Fournisseur;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import sessions.FournisseurFacadeLocal;
import utils.JsfUtil;
import utils.Routine;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class FournisseurController extends AbstractFournisseurController
        implements Serializable {

    public void prepareCreate() {
        try {
            /*  34 */ if (!Utilitaires.isAccess(Long.valueOf(14L))) {
                /*  35 */ signalError("acces_refuse");
                /*  36 */ return;
            }

            /*  39 */ this.mode = "Create";
            /*  40 */ this.fournisseur = new Fournisseur();
            /*  41 */ this.fournisseur.setContact("-");
            /*  42 */ this.fournisseur.setAdresse("-");
            /*  43 */ this.fournisseur.setEmail("-");
            /*  44 */ RequestContext.getCurrentInstance().execute("PF('FournisseurCreerDialog').show()");
        } catch (Exception e) {
            /*  46 */ signalException(e);
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
            /*  89 */ if (this.fournisseur != null) {
                /*  91 */ if (!Utilitaires.isAccess(Long.valueOf(16L))) {
                    /*  92 */ signalError("acces_refuse");
                    /*  93 */ return;
                }
                /*  95 */ this.fournisseurFacadeLocal.remove(this.fournisseur);
                /*  96 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du fournisseur : " + this.fournisseur.getNom(), SessionMBean.getUserAccount());
                /*  97 */ this.fournisseur = null;
                /*  98 */ signalSuccess();
            } else {
                /* 100 */ JsfUtil.addErrorMessage("Aucun Fournisseur selectionnée");
            }
        } catch (Exception e) {
            /* 103 */ signalException(e);
        }
    }

    public void signalError(String chaine) {
        /* 108 */ this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
        /* 109 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalSuccess() {
        /* 113 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 114 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void signalException(Exception e) {
        /* 118 */ this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
        /* 119 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.fournisseur.FournisseurController
 * JD-Core Version:    0.6.2
 */
