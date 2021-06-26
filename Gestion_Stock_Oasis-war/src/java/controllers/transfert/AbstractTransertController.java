package controllers.transfert;

import entities.Famille;
import entities.Lignemvtstock;
import entities.Lignetransfert;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import entities.Transfert;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.FamilleFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LignetransfertFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.MvtstockFacadeLocal;
import sessions.TransfertFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractTransertController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected TransfertFacadeLocal transfertFacadeLocal;
    protected Transfert transfert = new Transfert();
    protected List<Transfert> transferts = new ArrayList();

    @EJB
    protected LignetransfertFacadeLocal lignetransfertFacadeLocal;
    protected Lignetransfert lignetransfert = new Lignetransfert();
    protected List<Lignetransfert> lignetransferts = new ArrayList();
    protected List<Lignetransfert> lignetransferts_1 = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected Magasin magasinCible = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    protected Famille famille = new Famille();
    protected List<Famille> familles = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected Magasinlot magasinlot = new Magasinlot();
    protected List<Magasinlot> magasinlots = new ArrayList();
    protected List<Magasinlot> selectedMagasinlots = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    protected Magasinarticle magasinarticle = new Magasinarticle();
    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
    protected List<Lignemvtstock> lignemvtstocks = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected String libelle_article = "-";

    protected Double total = 0d;

    protected Boolean showSelectorCommand = true;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected String fileName = "";
    protected String printDialogTitle = "";

    protected String mode = "";

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

    public Double getTotal() {
        return this.total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Boolean getShowSelectorCommand() {
        return this.showSelectorCommand;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public Mvtstock getMvtstock() {
        return this.mvtstock;
    }

    public void setMvtstock(Mvtstock mvtstock) {
        this.mvtstock = mvtstock;
    }

    public List<Lignemvtstock> getLignemvtstocks() {
        return this.lignemvtstocks;
    }

    public String getPrintDialogTitle() {
        return this.printDialogTitle;
    }

    public void setPrintDialogTitle(String printDialogTitle) {
        this.printDialogTitle = printDialogTitle;
    }

    public List<Magasinarticle> getMagasinarticles() {
        return this.magasinarticles;
    }

    public void setMagasinarticles(List<Magasinarticle> magasinarticles) {
        this.magasinarticles = magasinarticles;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public void setMagasinlots(List<Magasinlot> magasinlots) {
        this.magasinlots = magasinlots;
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

    public void setFamilles(List<Famille> familles) {
        this.familles = familles;
    }

    public List<Magasinlot> getSelectedMagasinlots() {
        return this.selectedMagasinlots;
    }

    public void setSelectedMagasinlots(List<Magasinlot> selectedMagasinlots) {
        this.selectedMagasinlots = selectedMagasinlots;
    }

    public Transfert getTransfert() {
        return this.transfert;
    }

    public void setTransfert(Transfert transfert) {
        this.transfert = transfert;
        this.modifier = this.supprimer = this.detail = this.imprimer = transfert == null;
    }

    public List<Transfert> getTransferts() {
        transferts = transfertFacadeLocal.findByIdMagasinBidirection(SessionMBean.getMagasin().getIdmagasin());
        return transferts;
    }

    public Lignetransfert getLignetransfert() {
        return this.lignetransfert;
    }

    public void setLignetransfert(Lignetransfert lignetransfert) {
        this.lignetransfert = lignetransfert;
    }

    public List<Lignetransfert> getLignetransferts() {
        return this.lignetransferts;
    }

    public void setLignetransferts(List<Lignetransfert> lignetransferts) {
        this.lignetransferts = lignetransferts;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public Magasin getMagasinCible() {
        return this.magasinCible;
    }

    public void setMagasinCible(Magasin magasinCible) {
        this.magasinCible = magasinCible;
    }

    public List<Magasin> getMagasins() {
        this.magasins = this.magasinFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId());
        return this.magasins;
    }

    public Magasinarticle getMagasinarticle() {
        return this.magasinarticle;
    }

    public void setMagasinarticle(Magasinarticle magasinarticle) {
        this.magasinarticle = magasinarticle;
    }

    public Magasinlot getMagasinlot() {
        return this.magasinlot;
    }

    public void setMagasinlot(Magasinlot magasinlot) {
        this.magasinlot = magasinlot;
    }

    public List<Lignetransfert> getLignetransferts_1() {
        return this.lignetransferts_1;
    }

    public void setLignetransferts_1(List<Lignetransfert> lignetransferts_1) {
        this.lignetransferts_1 = lignetransferts_1;
    }

    public String getLibelle_article() {
        return this.libelle_article;
    }
}
