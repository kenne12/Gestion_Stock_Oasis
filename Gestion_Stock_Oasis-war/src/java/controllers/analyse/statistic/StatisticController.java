package controllers.analyse.statistic;

import entities.AnneeMois;
import entities.Lignelivraisonclient;
import entities.Livraisonclient;
import entities.Livraisonfournisseur;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.BarChartSeries;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class StatisticController extends AbstractStatisticController implements Serializable {

    @PostConstruct
    private void init() {
        try {
            this.critere = 1;
            if (SessionMBean.getAnnee() != null) {
                this.annee = SessionMBean.getAnnee();
                this.anneeMoises = this.anneeMoisFacadeLocal.findByAnnee(annee.getIdannee());
                createLineModels();
                createBarModel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        try {
            if (!this.annee.getIdannee().equals(0)) {
                this.annee = this.anneeFacadeLocal.find(this.annee.getIdannee());
                this.anneeMoises = this.anneeMoisFacadeLocal.findByAnnee(this.annee.getIdannee());
                createLineModels();
                createBarModel();
            } else {
                this.lineModel1 = new LineChartModel();
                this.lineModel2 = new LineChartModel();
                this.barModel = new BarChartModel();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createLineModels() {
        this.lineModel1 = initLinearModel();
        this.lineModel1.setTitle("Courbe évolutive des ventes et approvisionnement");
        this.lineModel1.setLegendPosition("e");
        this.lineModel1.getAxes().put(AxisType.X, new CategoryAxis("Mois"));
        this.lineModel1.getAxes().put(AxisType.Y, new CategoryAxis("Montant"));
        Axis yAxis = this.lineModel1.getAxis(AxisType.Y);
        yAxis.setMin(0);

        this.lineModel2 = initCategoryModel();
        this.lineModel2.setTitle("Courbe évolutive des ventes et approvisionnements aucours de l'année " + this.annee.getNom());
        this.lineModel2.setLegendPosition("e");
        this.lineModel2.setShowPointLabels(true);
        this.lineModel2.getAxes().put(AxisType.X, new CategoryAxis("Mois"));
        yAxis = this.lineModel2.getAxis(AxisType.Y);
        yAxis.setLabel("Montant");
        yAxis.setMin(0);
    }

    private LineChartModel initLinearModel() {
        try {
            LineChartModel model = new LineChartModel();

            LineChartSeries series1 = new LineChartSeries();
            series1.setLabel("Ventes");

            LineChartSeries series2 = new LineChartSeries();
            series2.setLabel("Approvisionnement");

            for (AnneeMois a : this.anneeMoises) {

                Integer somme = 0;
                List<Livraisonclient> livraisonclients = livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), a.getDateDebut(), a.getDateFin());
                for (Livraisonclient l : livraisonclients) {
                    somme += (int) l.getMontantTtc();
                }

                series1.set(a.getIdmois().getNom(), somme);

                int somme1 = 0;
                List<Livraisonfournisseur> livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), a.getDateDebut(), a.getDateFin());
                for (Livraisonfournisseur s : livraisonfournisseurs) {
                    somme1 += s.getMontant().intValue();
                }
                series2.set(a.getIdmois().getNom(), somme1);
            }

            model.addSeries((ChartSeries) series1);
            model.addSeries((ChartSeries) series2);

            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new LineChartModel();
        }
    }

    private LineChartModel initCategoryModel() {
        try {
            LineChartModel model = new LineChartModel();

            LineChartSeries series1 = new LineChartSeries();
            series1.setLabel("Ventes");

            LineChartSeries series2 = new LineChartSeries();
            series2.setLabel("Approvisionnement");

            for (AnneeMois a : this.anneeMoises) {

                Integer somme = 0;
                List<Livraisonclient> livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), a.getDateDebut(), a.getDateFin());
                for (Livraisonclient l : livraisonclients) {
                    somme += (int) l.getMontantTtc();
                }

                series1.set(a.getIdmois().getNom(), somme);

                int somme1 = 0;
                List<Livraisonfournisseur> livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), a.getDateDebut(), a.getDateFin());
                for (Livraisonfournisseur s : livraisonfournisseurs) {
                    somme1 += s.getMontant().intValue();
                }
                series2.set(a.getIdmois().getNom(), somme1);
            }

            model.addSeries((ChartSeries) series1);
            model.addSeries((ChartSeries) series2);

            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new LineChartModel();
        }
    }

    private BarChartModel initBarModel() {
        try {
            BarChartModel model = new BarChartModel();

            BarChartSeries series1 = new BarChartSeries();
            series1.setLabel("Ventes");

            BarChartSeries series2 = new BarChartSeries();
            series2.setLabel("Approvisionnement");

            for (AnneeMois a : this.anneeMoises) {

                int somme = 0;
                List<Livraisonclient> livraisonclients = livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), a.getDateDebut(), a.getDateFin());
                for (Livraisonclient f : livraisonclients) {
                    somme += f.getMontantTtc();
                }

                series1.set(a.getIdmois().getNom(), somme);

                int somme1 = 0;
                List<Livraisonfournisseur> livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), a.getDateDebut(), a.getDateFin());
                for (Livraisonfournisseur s : livraisonfournisseurs) {
                    somme1 += s.getMontant().intValue();
                }
                series2.set(a.getIdmois().getNom(), somme1);
            }

            model.addSeries((ChartSeries) series1);
            model.addSeries((ChartSeries) series2);

            return model;
        } catch (Exception e) {
            e.printStackTrace();
            return new BarChartModel();
        }
    }

    private void createBarModel() {
        this.barModel = initBarModel();

        this.barModel.setTitle("Histogramme des ventes et approvisonnements de l'année " + this.annee.getNom());
        this.barModel.setLegendPosition("ne");

        Axis xAxis = this.barModel.getAxis(AxisType.X);
        xAxis.setLabel("Mois");

        Axis yAxis = this.barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Montant");
        yAxis.setMin(0);
    }

    public void updateClient() {
        this.livraisonclients.clear();
        this.imprimer = true;
    }

    public void updateDate() {
        updateClient();
    }

    public void rechercher() {
        try {
            this.imprimer = true;
            if (this.critere == 1) {
                this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange();
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = false;
                    notifySuccess();
                } else {
                    notifyFail();
                }
                return;
            }
            if (this.critere == 2) {
                this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), anneeMois.getDateDebut(), anneeMois.getDateFin());
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = false;
                    notifySuccess();
                } else {
                    notifyFail();
                }
                return;
            }
            if (this.critere == 3) {
                if (this.date_interval == 2) {
                    this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinAndIdclientAndDate(SessionMBean.getMagasin().getIdmagasin(), this.client.getIdclient(), this.date);
                } else {
                    this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinAndIdclientTwoDates(SessionMBean.getMagasin().getIdmagasin(), this.client.getIdclient(), this.dateDebut, this.dateFin);
                }
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = false;
                    notifySuccess();
                } else {
                    notifyFail();
                }
                return;
            }
            if (this.critere == 4) {
                this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinAndDate(SessionMBean.getMagasin().getIdmagasin(), date);
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = Boolean.valueOf(false);
                    notifySuccess();
                } else {
                    notifyFail();
                }
                return;
            }
            this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), this.dateDebut, this.dateFin);
            ajaxHide();
            if (!this.livraisonclients.isEmpty()) {
                this.imprimer = false;
                notifySuccess();
            } else {
                notifyFail();
            }

            return;
        } catch (Exception e) {
            ajaxHide();
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            return;
        }
    }

    public void ajaxHide() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
    }

    public void notifySuccess() {
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail() {
        this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("aucune_ligne_trouvee"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void prepareview() {
        try {
            if (this.livraisonclient != null) {
                List<Lignelivraisonclient> c = lignelivraisonclientFacadeLocal.findByIdlivraisonclient(livraisonclient.getIdlivraisonclient());
                if (!c.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('LivraisonclientDetailDialog').show()");
                    return;
                }
                this.livraisonclient.setLignelivraisonclientList(c);
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("liste_article_vide"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {

                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("not_row_selected"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void initPrinter(Livraisonclient f) {
        this.livraisonclient = f;
        print();
    }

    public void initView(Livraisonclient f) {
        this.livraisonclient = f;
        prepareview();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(Long.valueOf(26L))) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                this.livraisonclient = null;
                return;
            }
            if (this.livraisonclient != null) {
                this.printDialogTitle = this.routine.localizeMessage("facture");

                List<Lignelivraisonclient> c = lignelivraisonclientFacadeLocal.findByIdlivraisonclient(livraisonclient.getIdlivraisonclient());
                this.livraisonclient.setLignelivraisonclientList(c);
                //this.fileName = PrintUtils.printLivraisonclient(this.facture, SessionMBean.getParametrage());
                RequestContext.getCurrentInstance().execute("PF('LivraisonclientImprimerDialog').show()");
            } else {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("not_row_selected"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void printHistoric() {
        try {
            this.imprimer = true;
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            if (this.critere == 1) {
                this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasin(SessionMBean.getMagasin().getIdmagasin());
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = (false);

                    this.printDialogTitle = "Historique des ventes";
                    String titre = "HISTORIQUE DE TOUTES LES VENTES";

                    this.fileName = "Historic_total_vente_" + sdf.format(new Date(System.currentTimeMillis())) + ".pdf";
                    //PrintUtils.printHistoric(this.livraisonclients, SessionMBean.getParametrage(), titre, this.fileName);
                    RequestContext.getCurrentInstance().execute("PF('LivraisonclientImprimerDialog').show()");
                } else {
                    notifyFail();
                }
                return;
            }
            if (this.critere == 2) {
                this.anneeMois = this.anneeMoisFacadeLocal.find(this.anneeMois.getIdAnneeMois());
                this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), this.anneeMois.getDateDebut(), anneeMois.getDateFin());
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = false;

                    this.printDialogTitle = "Historique des ventes de " + this.anneeMois.getIdmois().getNom() + "/" + this.anneeMois.getIdannee().getNom();
                    String titre = "HISTORIQUE DES VENTES : " + this.anneeMois.getIdmois().getNom() + "/" + this.anneeMois.getIdannee().getNom();

                    this.fileName = "Historic_mensuel_vente_" + this.anneeMois.getIdmois().getNom() + "_" + this.anneeMois.getIdannee().getNom() + ".pdf";
                    //PrintUtils.printHistoric(this.livraisonclients, SessionMBean.getParametrage(), titre, this.fileName);
                    RequestContext.getCurrentInstance().execute("PF('LivraisonclientImprimerDialog').show()");
                } else {
                    notifyFail();
                }
                return;
            }
            if (this.critere == 3) {
                if (this.date_interval == 2) {
                    this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinAndIdclientAndDate(SessionMBean.getMagasin().getIdmagasin(), this.client.getIdclient(), this.date);
                } else {
                    this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinAndIdclientTwoDates(SessionMBean.getMagasin().getIdmagasin(), this.client.getIdclient(), this.dateDebut, this.dateFin);
                }
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = false;

                    if (this.date_interval == 2) {
                        this.printDialogTitle = "Historique des achats du client " + ((Livraisonclient) this.livraisonclients.get(0)).getClient().getNom() + " à la date du " + sdf.format(this.date);
                        String str = "HISTORIQUE DES OPERATIONS DU : " + sdf.format(this.date);

                        this.fileName = "Historic_des_achats_" + ((Livraisonclient) this.livraisonclients.get(0)).getClient().getNom() + "_" + sdf.format(this.date) + ".pdf";
                        //PrintUtils.printHistoric(this.livraisonclients, SessionMBean.getParametrage(), str, this.fileName, ((Livraisonclient) this.livraisonclients.get(0)).getClient(), "" + sdf.format(this.date));
                        RequestContext.getCurrentInstance().execute("PF('LivraisonclientImprimerDialog').show()");
                        return;
                    }
                    this.printDialogTitle = "Historique des achats du client " + ((Livraisonclient) this.livraisonclients.get(0)).getClient().getNom() + " du " + sdf.format(this.dateDebut) + " Au " + sdf.format(this.dateFin);
                    String titre = "HISTORIQUE DES VENTES DU : " + sdf.format(this.dateDebut) + " AU " + sdf.format(this.dateFin);

                    this.fileName = "Historic_des_achats_" + ((Livraisonclient) this.livraisonclients.get(0)).getClient().getNom() + "_" + sdf.format(this.dateDebut) + "_Au_" + sdf.format(this.dateFin) + ".pdf";
                    //PrintUtils.printHistoric(this.livraisonclients, SessionMBean.getParametrage(), titre, this.fileName, ((Livraisonclient) this.livraisonclients.get(0)).getClient(), "" + sdf.format(this.dateDebut) + " Au " + sdf.format(this.dateFin));
                    RequestContext.getCurrentInstance().execute("PF('LivraisonclientImprimerDialog').show()");
                } else {
                    notifyFail();
                }
                return;
            }
            if (this.critere == 4) {
                this.livraisonclients = this.livraisonclientFacadeLocal.findByIdmagasinAndDate(SessionMBean.getMagasin().getIdmagasin(), this.date);
                ajaxHide();
                if (!this.livraisonclients.isEmpty()) {
                    this.imprimer = false;

                    this.printDialogTitle = "Historique des ventes du " + sdf.format(this.date);
                    String titre = "HISTORIQUE DE TOUTES LES VENTES : " + sdf.format(this.date);

                    this.fileName = "Historic_des_vente_" + sdf.format(this.date) + ".pdf";
                    //PrintUtils.printHistoric(this.livraisonclients, SessionMBean.getParametrage(), titre, this.fileName);
                    RequestContext.getCurrentInstance().execute("PF('LivraisonclientImprimerDialog').show()");
                } else {
                    notifyFail();
                }
                return;
            }
            this.livraisonclients = this.livraisonclientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), this.dateDebut, this.dateFin);
            ajaxHide();
            if (!this.livraisonclients.isEmpty()) {
                this.imprimer = false;

                this.printDialogTitle = "Historique des ventes du " + sdf.format(this.dateDebut) + " Au " + sdf.format(this.dateFin);
                String titre = "HISTORIQUE DES VENTES DU " + sdf.format(this.dateDebut) + " AU " + sdf.format(this.dateFin);

                this.fileName = "Historic_des_vente_" + sdf.format(this.dateDebut) + "_au_" + sdf.format(this.dateFin) + ".pdf";
                //PrintUtils.printHistoric(this.livraisonclients, SessionMBean.getParametrage(), titre, this.fileName);
                RequestContext.getCurrentInstance().execute("PF('LivraisonclientImprimerDialog').show()");
            } else {
                notifyFail();
            }

            return;
        } catch (Exception e) {
            ajaxHide();
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            return;
        }
    }
}
