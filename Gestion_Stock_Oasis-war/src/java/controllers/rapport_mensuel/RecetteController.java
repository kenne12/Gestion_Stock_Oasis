package controllers.rapport_mensuel;

import entities.Journee;
import entities.Livraisonclient;
import entities.Livraisonfournisseur;
import entities.Transfert;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class RecetteController extends AbstractRecetteController implements Serializable {

    @PostConstruct
    private void init() {

        if (SessionMBean.getAnneeDefault() != null) {
            annee = SessionMBean.getAnnee();
        }

        if (SessionMBean.getListDefaultMonths() != null) {
            listMois = SessionMBean.getListDefaultMonths();
        }

        if (SessionMBean.getMois() != null) {
            anneeMois = SessionMBean.getMois();
        }
        magasin = SessionMBean.getMagasin();
    }

    public void updateMois() {
        this.listMois.clear();
        try {
            if (this.annee.getIdannee() != null) {
                listMois = anneeMoisFacadeLocal.findByAnnee(annee.getIdannee());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
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

    public void afficher() {
        this.journees.clear();
        if (this.anneeMois.getIdAnneeMois() != null) {
            if (magasin.getIdmagasin() != null) {
                this.anneeMois = anneeMoisFacadeLocal.find(anneeMois.getIdAnneeMois());
                this.journees = journeeFacadeLocal.findByIdmagasinTwoDates(magasin.getIdmagasin(), anneeMois.getDateDebut(), anneeMois.getDateFin());
            }
        }
    }

    private double sommeRecette(List<Livraisonclient> livraisonclients, Journee journee) {
        double reste = 0;
        if (!livraisonclients.isEmpty()) {
            double total = 0;
            double marge = 0;
            for (Livraisonclient l : livraisonclients) {
                total += l.getMontantTtc();
                reste += l.getReste();
                marge += l.getMarge();
            }
            journee.setMontantVendu(total);
            journee.setBord(marge);
        }
        return reste;
    }

    private void sommeApprovisionnement(List<Livraisonfournisseur> livraisonfournisseurs, Journee journee) {
        journee.setMontantVendu(0);
        if (!livraisonfournisseurs.isEmpty()) {
            double total = 0;
            for (Livraisonfournisseur l : livraisonfournisseurs) {
                total += l.getMontant();
            }
            journee.setMontantEntre(total);
        }
    }

    private void sommeTransfert(String mode, List<Transfert> transferts, Journee journee) {
        double montant = 0;
        if (!transferts.isEmpty()) {
            for (Transfert t : transferts) {
                montant += t.getMontanttotal();
            }
        }
        if (mode.equals("sortant")) {
            journee.setTransfertSortant(montant);
        } else {
            journee.setTransfertEntrant(montant);
        }
    }

    public void conciliate() {
        try {
            if (this.anneeMois.getIdAnneeMois() != null) {

                this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
                if (magasin.getIdmagasin() != null) {

                    List<Journee> list = this.journeeFacadeLocal.findByIdmagasinTwoDates(magasin.getIdmagasin(), anneeMois.getDateDebut(), anneeMois.getDateFin());
                    for (Journee j : list) {

                        if (!j.isFermee()) {

                            List<Livraisonclient> livraisonclients = livraisonclientFacadeLocal.findByIdmagasinAndDate(magasin.getIdmagasin(), j.getDateJour());
                            List<Livraisonfournisseur> livraisonfournisseurs = livraisonfournisseurFacadeLocal.findAllRange(magasin.getIdmagasin(), j.getDateJour());
                            List<Transfert> transfertEntrant = transfertFacadeLocal.findByIdMagasinDestination(magasin.getIdmagasin(), j.getDateJour());
                            List<Transfert> transfertSource = transfertFacadeLocal.findByIdMagasinSource(magasin.getIdmagasin(), j.getDateJour());

                            this.sommeRecette(livraisonclients, j);
                            this.sommeApprovisionnement(livraisonfournisseurs, j);
                            this.sommeTransfert("sortant", transfertSource, j);
                            this.sommeTransfert("entrant", transfertEntrant, j);

                            this.journeeFacadeLocal.edit(j);
                        }
                    }
                    this.journees = this.journeeFacadeLocal.findByIdmagasinTwoDates(magasin.getIdmagasin(), anneeMois.getDateDebut(), anneeMois.getDateFin());
                }
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printRecette() {
        try {
            if (!Utilitaires.isAccess(36L)) {
                notifyError("acces_refuse");
                return;
            }
            this.fileName = PrintUtils.printRecette(anneeMois, this.journees);
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('RapportImprimerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }
}
