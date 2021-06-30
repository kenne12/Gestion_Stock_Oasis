package controllers.control_peremption;

import entities.Inventaire;
import entities.Ligneinventaire;
import entities.Lignemvtstock;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.Printer;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class ControlPeremptionController extends AbstractControlPeremptionController implements Serializable {

    @PostConstruct
    private void init() {
        try {
            if (SessionMBean.getMagasin() != null) {
                this.magasinlots = this.magasinlotFacadeLocal.findAllPeremptedEtatIsTrue(SessionMBean.getMagasin().getIdmagasin(), new Date(System.currentTimeMillis()));
                this.magasins.clear();
                this.showSessionPanel = false;
                if (!this.magasinlots.isEmpty()) {
                    for (Magasinlot ml : this.magasinlots) {
                        if (!this.magasins.contains(ml.getIdmagasinarticle().getIdmagasin())) {
                            this.magasins.add(ml.getIdmagasinarticle().getIdmagasin());
                        }
                    }
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Magasinlot> filterLotByMagasin(Magasin m) {
        List list = new ArrayList();
        for (Magasinlot ml : this.magasinlots) {
            if (ml.getIdmagasinarticle().getIdmagasin().equals(m)) {
                list.add(ml);
            }
        }
        return list;
    }

    public void openDialog() {
        this.showSessionPanel = true;
        RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').show()");
    }

    public void close() {
        this.showSessionPanel = false;
        RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').hide()");
    }

    public void closePeremptionDialog() {
        try {
            if (!Utilitaires.isAccess((1L))) {
                notifyError("acces_refuse");
                return;
            }

            for (Magasin m : this.magasins) {
                this.ut.begin();

                Mvtstock mvtstock = new Mvtstock();

                mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                mvtstock.setClient(" ");
                mvtstock.setFournisseur(" ");
                mvtstock.setMagasin(" ");
                mvtstock.setType(" ");
                mvtstock.setCode(Utilitaires.genererCodeStock("MVT", mvtstock.getIdmvtstock()));
                mvtstock.setDatemvt(new Date());
                this.mvtstockFacadeLocal.create(mvtstock);

                Inventaire inventaire = new Inventaire();
                inventaire.setIdinventaire(this.inventaireFacadeLocal.nextVal());
                inventaire.setEtat(true);
                inventaire.setDateinventaire(new Date(System.currentTimeMillis()));
                inventaire.setAllarticle((false));
                inventaire.setCode(Utilitaires.genererCodeInventaire("INV-", inventaire.getIdinventaire()));
                inventaire.setLibelle("Inventaire de retrait des peremptions / " + m.getNom());
                inventaire.setIdmagasin(m);
                inventaire.setIdmvtstock(mvtstock);
                this.inventaireFacadeLocal.create(inventaire);

                List<Magasinlot> mlList = filterLotByMagasin(m);

                for (Magasinlot ml : mlList) {
                    Ligneinventaire li = new Ligneinventaire();
                    Lignemvtstock lms = new Lignemvtstock();
                    lms.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                    lms.setIdmvtstock(inventaire.getIdmvtstock());
                    lms.setIdlot(ml.getIdlot());
                    lms.setIdmagasinlot(ml);
                    lms.setClient("RETRAIT PEREMPTION");
                    lms.setFournisseur(" ");
                    lms.setQteentree((0.0D));
                    lms.setQtesortie(ml.getQuantitemultiple());
                    lms.setReste((0.0D));
                    lms.setType("SORTIE");
                    this.lignemvtstockFacadeLocal.create(lms);

                    li.setIdligneinventaire(this.ligneinventaireFacadeLocal.nextVal());
                    li.setIdinventaire(inventaire);
                    li.setQtetheorique(ml.getQuantitemultiple());
                    li.setQtephysique(0d);
                    li.setEcart(ml.getQuantitemultiple());
                    li.setIdlot(ml.getIdlot());
                    li.setIdmagasinlot(ml);
                    li.setObservation("Retrait Des Péremptions");
                    this.ligneinventaireFacadeLocal.create(li);

                    updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle(), ml.getQuantitemultiple(), "-");
                    updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot(), ml.getQuantitemultiple(), "-");
                }
                this.ut.commit();
            }

            this.showSessionPanel = false;
            notifySuccess();
            RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').hide()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    private void updateMagasinArticle(long idmagasinArticle, double ecart, String signe) {
        Magasinarticle magasinarticle = this.magasinarticleFacadeLocal.find(Long.valueOf(idmagasinArticle));
        if (signe.equals("-")) {
            double vabs = Math.abs(ecart);
            magasinarticle.setQuantitemultiple((magasinarticle.getQuantitemultiple() - vabs));
            magasinarticle.setQuantitereduite((magasinarticle.getQuantitereduite() - vabs));
            magasinarticle.setQuantite((magasinarticle.getQuantite() - vabs));
        } else {
            magasinarticle.setQuantitemultiple((magasinarticle.getQuantitemultiple() + ecart));
            magasinarticle.setQuantitereduite((magasinarticle.getQuantitereduite() + ecart));
            magasinarticle.setQuantite((magasinarticle.getQuantite() + ecart));
        }
        this.magasinarticleFacadeLocal.edit(magasinarticle);
    }

    public void updateMagasinLot(long idmagasinLot, double ecart, String signe) {
        Magasinlot magasinlot = this.magasinlotFacadeLocal.find(Long.valueOf(idmagasinLot));
        magasinlot.setEtat((false));
        if (signe.equals("+")) {
            magasinlot.setQuantitemultiple((magasinlot.getQuantitemultiple() + ecart));
            magasinlot.setQuantitereduite((magasinlot.getQuantitereduite() + ecart));
            magasinlot.setQuantite((magasinlot.getQuantite() + ecart));
        } else {
            double vabs = Math.abs(ecart);
            magasinlot.setQuantitemultiple((magasinlot.getQuantitemultiple() - vabs));
            magasinlot.setQuantitereduite((magasinlot.getQuantitereduite() - vabs));
            magasinlot.setQuantite((magasinlot.getQuantite() - vabs));
        }
        this.magasinlotFacadeLocal.edit(magasinlot);
    }

    public void print() {
        try {
            Map param = new HashMap();
            param.put("date_jour", new Date());
            Printer.print("/reports/ireport/produits_peremption_all.jasper", param);
            RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').hide()");
        } catch (Exception e) {
            notifyFail(e);
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
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').show()");
        this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
