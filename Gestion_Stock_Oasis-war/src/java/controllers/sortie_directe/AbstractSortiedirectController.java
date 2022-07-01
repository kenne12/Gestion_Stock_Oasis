package controllers.sortie_directe;

import entities.Annee;
import entities.AnneeMois;
import entities.Client;
import entities.Famille;
import entities.Lignelivraisonclient;
import entities.Livraisonclient;
import entities.Magasin;
import entities.Magasinlot;
import entities.Mvtstock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import service.StockOutputService;
import sessions.AnneeMoisFacadeLocal;
import sessions.ClientFacadeLocal;
import sessions.FamilleFacadeLocal;
import sessions.LignelivraisonclientFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LivraisonclientFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.MvtstockFacadeLocal;
import sessions.UniteFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractSortiedirectController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    protected Famille famille = new Famille();
    protected List<Famille> familles = new ArrayList();

    @EJB
    protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;
    protected Livraisonclient livraisonclient = new Livraisonclient();
    protected List<Livraisonclient> livraisonclients = new ArrayList();

    @EJB
    protected ClientFacadeLocal clientFacadeLocal;
    protected Client client = new Client();
    protected Client clientToSave = new Client();
    protected List<Client> clients = new ArrayList<>();

    @EJB
    protected LignelivraisonclientFacadeLocal lignelivraisonclientFacadeLocal;
    protected Lignelivraisonclient lignelivraisonclient = new Lignelivraisonclient();

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal ligneMvtstockFacadeLocal;

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected Magasinlot magasinlot = new Magasinlot();
    protected List<Magasinlot> magasinlots = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected final Magasin magasin = SessionMBean.getMagasin();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;

    protected Annee annee = SessionMBean.getAnnee();

    protected Date startDate = new Date();
    protected Date endDate = new Date();

    protected String searchMode;

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected Integer idMois;
    protected List<AnneeMois> listMois = new ArrayList<>();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected StockOutputService stockOutputService = new StockOutputService();

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected boolean isShowFiltreMois;
    protected boolean isShowStartDate;
    protected boolean isShowEndDate;

    protected String fileName = "";
    protected String mode = "";

    protected String directory = "";

    public Livraisonclient getLivraisonclient() {
        return this.livraisonclient;
    }

    public void setLivraisonclient(Livraisonclient livraisonclient) {
        this.modifier = this.supprimer = this.detail = livraisonclient == null;
        this.livraisonclient = livraisonclient;
    }

    public List<Livraisonclient> getLivraisonclients() {
        return this.livraisonclients;
    }

    public Lignelivraisonclient getLignelivraisonclient() {
        return this.lignelivraisonclient;
    }

    public void setLignelivraisonclient(Lignelivraisonclient lignelivraisonclient) {
        this.lignelivraisonclient = lignelivraisonclient;
    }

    public Boolean getDetail() {
        return this.detail;
    }

    public Boolean getModifier() {
        return this.modifier;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        return this.supprimer;
    }

    public Famille getFamille() {
        return this.famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }

    public List<Famille> getFamilles() {
        this.familles = this.familleFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.familles;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public Magasinlot getMagasinlot() {
        return this.magasinlot;
    }

    public void setMagasinlot(Magasinlot magasinlot) {
        this.magasinlot = magasinlot;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Client> getClients() {
        clients = clientFacadeLocal.findByIdstructure(SessionMBean.getMagasin().getParametrage().getId(), true);
        return clients;
    }

    public Client getClientToSave() {
        return clientToSave;
    }

    public void setClientToSave(Client clientToSave) {
        this.clientToSave = clientToSave;
    }

    public Annee getAnnee() {
        return annee;
    }

    public List<AnneeMois> getListMois() throws Exception {
        this.listMois = anneeMoisFacadeLocal.findByAnnee(SessionMBean.getAnnee().getIdannee());
        return listMois;
    }

    public void setListMois(List<AnneeMois> listMois) {
        this.listMois = listMois;
    }

    public Integer getIdMois() {
        return idMois;
    }

    public void setIdMois(Integer idMois) {
        this.idMois = idMois;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getSearchMode() {
        return searchMode;
    }

    public void setSearchMode(String searchMode) {
        this.searchMode = searchMode;
    }

    public boolean isIsShowFiltreMois() {
        return isShowFiltreMois;
    }

    public void setIsShowFiltreMois(boolean isShowFiltreMois) {
        this.isShowFiltreMois = isShowFiltreMois;
    }

    public boolean isIsShowStartDate() {
        return isShowStartDate;
    }

    public void setIsShowStartDate(boolean isShowStartDate) {
        this.isShowStartDate = isShowStartDate;
    }

    public boolean isIsShowEndDate() {
        return isShowEndDate;
    }

    public void setIsShowEndDate(boolean isShowEndDate) {
        this.isShowEndDate = isShowEndDate;
    }

    public String getDirectory() {
        return directory;
    }

}
