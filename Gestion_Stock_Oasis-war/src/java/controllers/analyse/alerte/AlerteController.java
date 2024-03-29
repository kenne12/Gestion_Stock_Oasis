package controllers.analyse.alerte;

import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.Printer;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class AlerteController extends AbstractAlerteController implements Serializable {

    @PostConstruct
    private void init() {
        try {

            if (SessionMBean.getMagasin() != null) {

                List<Magasinlot> list = this.magasinlotFacadeLocal.findAllEtatIsTrue(SessionMBean.getMagasin().getIdmagasin());
                this.magasins.clear();
                this.magasinlot_peremps.clear();
                if (!list.isEmpty()) {
                    for (Magasinlot ml : list) {
                        int ecart = Utilitaires.duration(new Date(System.currentTimeMillis()), ml.getIdlot().getDateperemption());
                        if (ecart <= ml.getIdlot().getIdarticle().getNbjralerte()) {
                            this.magasinlot_peremps.add(ml);
                        }

                        if (!this.magasins.contains(ml.getIdmagasinarticle().getIdmagasin())) {
                            this.magasins.add(ml.getIdmagasinarticle().getIdmagasin());
                        }
                    }
                }
            }

            for (Magasinlot ml : this.magasinlot_peremps) {
                if (!this.magasins.contains(ml.getIdmagasinarticle().getIdmagasin())) {
                    this.magasins.add(ml.getIdmagasinarticle().getIdmagasin());
                }
            }

            this.magasinarticle_alert = this.magasinarticleFacadeLocal.findAllEtatIsTrueAlert();
            for (Magasinarticle ma : this.magasinarticle_alert) {
                if (!this.magasins_1.contains(ma.getIdmagasin())) {
                    this.magasins_1.add(ma.getIdmagasin());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Magasinlot> filterLotByMagasin(Magasin m) {
        List<Magasinlot> list = new ArrayList();
        list.addAll(magasinlot_alert);
        return list.stream()
                .filter(item -> item.getIdmagasinarticle().getIdmagasin().getIdmagasin().equals(m.getIdmagasin()))
                .collect(Collectors.toList());

    }

    public List<Magasinarticle> filterArticleByMagasin(Magasin m) {
        List<Magasinarticle> list = new ArrayList();
        list.addAll(magasinarticle_alert);
        return list.stream()
                .filter(item -> item.getIdmagasin().getIdmagasin().equals(m.getIdmagasin()))
                .collect(Collectors.toList());

    }

    public void openAlertPeremptionDialog() {
        RequestContext.getCurrentInstance().execute("PF('AlertePeremptionDialog').show()");
    }

    public void openDemandeDialog() {
        RequestContext.getCurrentInstance().execute("PF('AlerteCommandeClientDialog').show()");
    }

    public void printAlertePeremption() {
        try {
            Map param = new HashMap();
            param.put("date_jour", new Date());
            Printer.print("/reports/ireport/alerte_peremption_all.jasper", param);
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
