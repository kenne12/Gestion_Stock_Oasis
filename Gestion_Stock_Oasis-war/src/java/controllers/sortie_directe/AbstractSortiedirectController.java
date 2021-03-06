package controllers.sortie_directe;

import entities.Annee;
import entities.Client;
import entities.Famille;
import entities.Lignelivraisonclient;
import entities.Livraisonclient;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import entities.Unite;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
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
    protected List<Lignelivraisonclient> lignelivraisonclients = new ArrayList();

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
    protected Magasinarticle magasinarticle = new Magasinarticle();
    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected final Magasin magasin = SessionMBean.getMagasin();

    @EJB
    protected UniteFacadeLocal uniteFacadeLocal;
    protected Unite unite = new Unite();
    protected List<Unite> unites = new ArrayList();

    protected Annee annee = SessionMBean.getAnnee();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected Double total = 0.0;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected String fileName = "";
    protected String mode = "";

    //int conteur = 0;
    public Livraisonclient getLivraisonclient() {
        return this.livraisonclient;
    }

    public void setLivraisonclient(Livraisonclient livraisonclient) {
        this.modifier = this.supprimer = this.detail = this.imprimer = livraisonclient == null;
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

    public List<Lignelivraisonclient> getLignelivraisonclients() {
        return this.lignelivraisonclients;
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

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
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

    public Magasinarticle getMagasinarticle() {
        return this.magasinarticle;
    }

    public void setMagasinarticle(Magasinarticle magasinarticle) {
        this.magasinarticle = magasinarticle;
    }

    public List<Magasinarticle> getMagasinarticles() {
        return this.magasinarticles;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public Unite getUnite() {
        return this.unite;
    }

    public void setUnite(Unite unite) {
        this.unite = unite;
    }

    public List<Unite> getUnites() {
        this.unites = this.uniteFacadeLocal.findByStructure(SessionMBean.getParametrage().getId());
        return this.unites;
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

}
