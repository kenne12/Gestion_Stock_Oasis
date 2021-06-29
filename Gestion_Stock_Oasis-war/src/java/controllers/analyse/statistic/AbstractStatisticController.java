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
    //protected AnneeMois anneeMois = SessionMBean.getMois();
    protected AnneeMois anneeMois = new AnneeMois();
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

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public LivraisonclientFacadeLocal getLivraisonclientFacadeLocal() {
        return this.livraisonclientFacadeLocal;
    }

    public void setLivraisonclientFacadeLocal(LivraisonclientFacadeLocal livraisonclientFacadeLocal) {
        this.livraisonclientFacadeLocal = livraisonclientFacadeLocal;
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

    public void setLivraisonclients(List<Livraisonclient> livraisonclients) {
        this.livraisonclients = livraisonclients;
    }

    public MouchardFacadeLocal getMouchardFacadeLocal() {
        return this.mouchardFacadeLocal;
    }

    public void setMouchardFacadeLocal(MouchardFacadeLocal mouchardFacadeLocal) {
        this.mouchardFacadeLocal = mouchardFacadeLocal;
    }

    public PrivilegeFacadeLocal getPrivilegeFacadeLocal() {
        return this.privilegeFacadeLocal;
    }

    public void setPrivilegeFacadeLocal(PrivilegeFacadeLocal privilegeFacadeLocal) {
        this.privilegeFacadeLocal = privilegeFacadeLocal;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public void setImprimer(Boolean imprimer) {
        /* 154 */ this.imprimer = imprimer;
    }

    public Double getCout_quantite() {
        /* 158 */ return this.cout_quantite;
    }

    public void setCout_quantite(Double cout_quantite) {
        /* 162 */ this.cout_quantite = cout_quantite;
    }

    public Double getTotal() {
        /* 166 */ return this.total;
    }

    public void setTotal(Double total) {
        /* 170 */ this.total = total;
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

    public void setAnneeMoises(List<AnneeMois> anneeMoises) {
        this.anneeMoises = anneeMoises;
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
        /* 239 */ return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        /* 243 */ this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        /* 247 */ return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        /* 251 */ this.dateFin = dateFin;
    }

    public int getDate_interval() {
        /* 255 */ return this.date_interval;
    }

    public void setDate_interval(int date_interval) {
        /* 259 */ this.date_interval = date_interval;
    }

    public String getPrintDialogTitle() {
        /* 263 */ return this.printDialogTitle;
    }

    public LineChartModel getLineModel1() {
        /* 267 */ return this.lineModel1;
    }

    public void setLineModel1(LineChartModel lineModel1) {
        /* 271 */ this.lineModel1 = lineModel1;
    }

    public LineChartModel getLineModel2() {
        /* 275 */ return this.lineModel2;
    }

    public void setLineModel2(LineChartModel lineModel2) {
        /* 279 */ this.lineModel2 = lineModel2;
    }

    public BarChartModel getBarModel() {
        /* 283 */ return this.barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        /* 287 */ this.barModel = barModel;
    }
}
