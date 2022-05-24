package controllers.versement;

import entities.Livraisonclient;
import entities.Versement;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class VersementController extends AbstractVersementController implements Serializable {

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(28L)) {
                notifyError("acces_refuse");
                return;
            }
            this.mode = "Create";
            this.livraisonclient = new Livraisonclient();
            this.versement = new Versement();
            this.versement.setDateOperation(SessionMBean.getDateOuverture());
            this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinNonRegle(SessionMBean.getMagasin().getIdmagasin());
            this.showClient = false;
            RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.versement == null) {
                notifyError("not_row_selected");

                return;
            }
            if (!Utilitaires.isAccess(28L)) {
                notifyError("acces_refuse");
                return;
            }
            this.livraisonclient = this.versement.getLivraisonclient();
            if (this.livraisonclient.isPaye() || (this.livraisonclient.getReste() == 0d)) {
                this.livraisonclients.clear();
                this.livraisonclients.add(this.livraisonclient);
            }
            this.mode = "Edit";
            this.showClient = true;
            RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void updateSolde() {
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {

                this.versement.setHeure(Date.from(Instant.now()));
                this.versement.setLivraisonclient(livraisonclient);
                this.versement.setIdUtilisateur(SessionMBean.getUserAccount().getIdutilisateur());

                String code = "V-" + SessionMBean.getMois().getIdannee().getNom() + "-" + SessionMBean.getMois().getIdmois().getNom().toUpperCase().substring(0, 3);
                Long nextPayement = versementFacadeLocal.nextVal(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin());
                code = Utilitaires.genererCodeStock(code, nextPayement);

                this.ut.begin();

                this.livraisonclient.setMontantPaye(this.livraisonclient.getMontantPaye() + this.versement.getMontant());
                this.livraisonclient.setReste(this.livraisonclient.getMontantTtc() - this.livraisonclient.getMontantPaye());
                if (this.livraisonclient.getReste() == 0d) {
                    this.livraisonclient.setPaye(true);
                }

                this.versement.setReste(this.livraisonclient.getReste());
                this.versement.setCode(code);
                this.versement.setIdversement(this.versementFacadeLocal.nextVal());
                this.versementFacadeLocal.create(this.versement);

                this.livraisonclientFacadeLocal.edit(this.livraisonclient);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du versement -> client : " + this.versement.getLivraisonclient().getClient().getNom() + " ; Montant : " + this.versement.getMontant() + " ; Code : " + this.versement.getCode(), SessionMBean.getUserAccount());
                this.ut.commit();

                this.versement = new Versement();
                this.livraisonclient = new Livraisonclient();
                RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').hide()");
                notifySuccess();
                this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinNonRegle(SessionMBean.getMagasin().getIdmagasin());
            } else if (this.versement != null) {

                this.ut.begin();
                Versement v = this.versementFacadeLocal.find(this.versement.getIdversement());
                if (v.getMontant() != this.versement.getMontant()) {
                    this.livraisonclient = v.getLivraisonclient();
                    this.livraisonclient.setMontantPaye((this.livraisonclient.getMontantPaye() - v.getMontant() + this.versement.getMontant()));
                    this.livraisonclient.setReste((this.livraisonclient.getReste() + v.getMontant() - this.versement.getMontant()));
                    if (this.livraisonclient.getReste() == 0d) {
                        this.livraisonclient.setPaye(true);
                    }
                    this.livraisonclientFacadeLocal.edit(this.livraisonclient);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification du versement -> du client : " + this.versement.getLivraisonclient().getClient().getNom() + " Ancien montant : " + this.versement.getMontant() + " ; Nouveau Montant : " + v.getMontant() + " ; Code : " + this.versement.getCode(), SessionMBean.getUserAccount());
                }
                this.versement.setReste(this.livraisonclient.getReste());
                this.versementFacadeLocal.edit(this.versement);
                this.ut.commit();
                this.versement = null;
                this.livraisonclient = new Livraisonclient();
                RequestContext.getCurrentInstance().execute("PF('VersementCreerDialog').hide()");
                notifySuccess();
                this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinNonRegle(SessionMBean.getMagasin().getIdmagasin());
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.versement != null) {
                this.ut.begin();

                this.versementFacadeLocal.remove(this.versement);

                this.livraisonclient = this.versement.getLivraisonclient();
                this.livraisonclient.setMontantPaye(this.livraisonclient.getMontantPaye() - this.versement.getMontant());
                this.livraisonclient.setReste(this.livraisonclient.getReste() + this.versement.getMontant());
                this.livraisonclient.setPaye(false);
                if (this.livraisonclient.getReste() == 0d) {
                    this.livraisonclient.setPaye(true);
                }
                this.livraisonclientFacadeLocal.edit(this.livraisonclient);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation du versement -> client : " + this.versement.getLivraisonclient().getClient().getNom() + " N° livraisonclient : " + this.versement.getLivraisonclient().getCode() + " ; Montant : " + this.versement.getMontant() + " ; Code : " + this.versement.getCode(), SessionMBean.getUserAccount());
                this.ut.commit();
                notifySuccess();
                this.versement = null;
                this.livraisonclient = new Livraisonclient();
                this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinNonRegle(SessionMBean.getMagasin().getIdmagasin());
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Versement versement) {
        this.versement = versement;
        print();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess((26L))) {
                notifyError("acces_refuse");
                this.livraisonclient = null;
                return;
            }
            if (this.versement != null) {
                this.fileName = PrintUtils.printRecuVersement(versement, SessionMBean.getParametrage());
                RequestContext.getCurrentInstance().execute("PF('VersementImprimerDialog').show()");
                return;
            }
            notifyError("not_row_selected");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void notifyError(String message) {
        this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
