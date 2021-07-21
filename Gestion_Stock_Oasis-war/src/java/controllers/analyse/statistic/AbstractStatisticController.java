package controllers.analyse.statistic;

import entities.Annee;
import entities.AnneeMois;
import entities.Client;
import entities.Livraisonclient;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.LineChartModel;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.LignelivraisonclientFacadeLocal;
import sessions.LivraisonclientFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.LivraisonfournisseurFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractStatisticController {

    @Resource
    protected UserTransaction ut;

    protected LineChartModel lineModel1;
    protected LineChartModel lineModel2;
    protected BarChartModel barModel;

    @EJB
    protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;
    protected Livraisonclient livraisonclient = new Livraisonclient();
    protected List<Livraisonclient> livraisonclients = new ArrayList<>();

    @EJB
    protected LivraisonfournisseurFacadeLocal livraisonfournisseurFacadeLocal;

    @EJB
    protected LignelivraisonclientFacadeLocal lignelivraisonclientFacadeLocal;

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    protected Client client = new Client();
    protected List<Client> clients = new ArrayList<>();

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = SessionMBean.getAnnee();
    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = SessionMBean.getMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    protected Routine routine = new Routine();

    protected Double cout_quantite = 0d;
    protected Double total = 0d;

    protected Boolean imprimer = true;

    protected int date_interval = 1;
    protected Date date = new Date(System.currentTimeMillis());
    protected Date dateDebut = new Date(System.currentTimeMillis());
    protected Date dateFin = new Date(System.currentTimeMillis());

    protected String fileName = "";
    protected String printDialogTitle = "";
    protected int critere = 1;

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
        this.clients = this.clientFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin());
        return this.clients;
    }

    public Livraisonclient getLivraisonclient() {
        return this.livraisonclient;
    }

    public void setLivraisonclient(Livraisonclient livraisonclient) {
        this.livraisonclient = livraisonclient;
    }

    public List<Livraisonclient> getLivraisonclients() {
        return this.livraisonclients;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public Double getCout_quantite() {
        return this.cout_quantite;
    }

    public void setCout_quantite(Double cout_quantite) {
        this.cout_quantite = cout_quantite;
    }

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        try {
            this.annees = this.anneeFacadeLocal.findByRange();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.annees;
    }

    public void setAnnees(List<Annee> annees) {
        this.annees = annees;
    }

    public AnneeMois getAnneeMois() {
        return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        return this.anneeMoises;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public int getCritere() {
        return this.critere;
    }

    public void setCritere(int critere) {
        this.critere = critere;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getDate_interval() {
        return this.date_interval;
    }

    public void setDate_interval(int date_interval) {
        this.date_interval = date_interval;
    }

    public String getPrintDialogTitle() {
        return this.printDialogTitle;
    }

    public LineChartModel getLineModel1() {
        return this.lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        this.lineModel1 = lineModel1;
    }

    public LineChartModel getLineModel2() {
        return this.lineModel2;
    }

    public void setLineModel2(LineChartModel lineModel2) {
        this.lineModel2 = lineModel2;
    }

    public BarChartModel getBarModel() {
        return this.barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }
}
