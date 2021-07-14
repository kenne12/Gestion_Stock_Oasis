package controllers.personnel;

import entities.Magasin;
import entities.Personnel;
import entities.Qualite;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import sessions.MagasinFacadeLocal;
import sessions.PersonnelFacadeLocal;
import sessions.QualiteFacadeLocal;
import utils.Printer;
import utils.Routine;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class PersonnelController extends AbstractPersonnelController
        implements Serializable {

    @PostConstruct
    private void init() {
        this.personnel = new Personnel();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess((27L))) {
                notifyError("acces_refuse");
                return;
            }

            /*  48 */ this.mode = "Create";
            /*  49 */ this.personnel = new Personnel();
            /*  50 */ this.magasin = new Magasin();
            /*  51 */ this.qualite = new Qualite();

            /*  53 */ this.personnel.setPrenom("-");
            /*  54 */ this.personnel.setAddresse("");
            /*  55 */ this.personnel.setContact("-");
            /*  56 */ this.personnel.setMatricule("-");
            /*  57 */ this.personnel.setAddresse("-");

            RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess((27L))) {
                /*  68 */ notifyError("acces_refuse");
                /*  69 */ return;
            }

            if (this.personnel == null) {
                notifyError("not_row_selected");
                return;
            }

            /*  77 */ this.mode = "Edit";
            /*  78 */ this.qualite = this.personnel.getIdqualite();
            /*  79 */ this.magasin = this.personnel.getIdmagasin();

            RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                /*  91 */ this.personnel.setIdpersonnel(this.personnelFacadeLocal.nextVal());
                /*  92 */ this.personnel.setIdqualite(this.qualite);
                /*  93 */ this.personnel.setIdmagasin(this.magasin);
                /*  94 */ this.personnelFacadeLocal.create(this.personnel);

                /*  96 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du personnel : " + this.personnel.getNom() + " " + this.personnel.getPrenom(), SessionMBean.getUserAccount());
                /*  97 */ this.personnel = null;
                /*  98 */ this.modifier = (this.supprimer = this.detail = Boolean.valueOf(true));
                /*  99 */ RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').hide()");
                /* 100 */ notifySuccess();
            } else if (this.personnel != null) {
                /* 103 */ this.personnel.setIdqualite(this.qualiteFacadeLocal.find(this.qualite.getIdqualite()));
                /* 104 */ this.personnel.setIdmagasin(this.magasinFacadeLocal.find(this.magasin.getIdmagasin()));
                /* 105 */ this.personnelFacadeLocal.edit(this.personnel);
                /* 106 */ RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').hide()");
                /* 107 */ this.modifier = (this.supprimer = this.detail = (true));
                /* 108 */ notifySuccess();
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.personnel != null) {
                if (!Utilitaires.isAccess((27L))) {
                    notifyError("acces_refuse");
                    return;
                }

                /* 127 */ this.personnelFacadeLocal.remove(this.personnel);
                /* 128 */ this.personnel = null;
                /* 129 */ this.modifier = (this.supprimer = this.detail = (true));
                /* 130 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du personnel : " + this.personnel.getNom() + " " + this.personnel.getPrenom(), SessionMBean.getUserAccount());
                /* 131 */ notifySuccess();
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(Long.valueOf(27L))) {
                /* 143 */ notifyError("acces_refuse");
                /* 144 */ return;
            }

            /* 147 */ Map map = new HashMap();
            /* 148 */ Printer.print("/reports/ireport/liste_produit.jasper", map);
        } catch (Exception e) {
            /* 150 */ notifyFail(e);
        }
    }

    public void notifyError(String message) {
        /* 155 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 156 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 160 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 161 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 165 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 166 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
