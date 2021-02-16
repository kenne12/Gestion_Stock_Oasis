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
    /*  54 */    protected Magasin magasin = new Magasin();
    /*  55 */    protected Magasin magasinCible = new Magasin();
    /*  56 */    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected FamilleFacadeLocal familleFacadeLocal;
    /*  60 */    protected Famille famille = new Famille();
    /*  61 */    protected List<Famille> familles = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    /*  65 */    protected Magasinlot magasinlot = new Magasinlot();
    /*  66 */    protected List<Magasinlot> magasinlots = new ArrayList();
    /*  67 */    protected List<Magasinlot> selectedMagasinlots = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    /*  71 */    protected Magasinarticle magasinarticle = new Magasinarticle();
    /*  72 */    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    /*  76 */    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
    /*  80 */    protected List<Lignemvtstock> lignemvtstocks = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    /*  85 */    protected Routine routine = new Routine();

    /*  87 */    protected String libelle_article = "-";

    /*  89 */    protected Double total = Double.valueOf(0.0D);

    /*  91 */    protected Boolean showSelectorCommand = Boolean.valueOf(true);

    /*  93 */    protected Boolean detail = Boolean.valueOf(true);
    /*  94 */    protected Boolean modifier = Boolean.valueOf(true);
    /*  95 */    protected Boolean consulter = Boolean.valueOf(true);
    /*  96 */    protected Boolean imprimer = Boolean.valueOf(true);
    /*  97 */    protected Boolean supprimer = Boolean.valueOf(true);

    /*  99 */    protected String fileName = "";
    /* 100 */    protected String printDialogTitle = "";

    /* 102 */    protected String mode = "";

    public Boolean getDetail() {
        /* 105 */ return this.detail;
    }

    public Boolean getModifier() {
        /* 109 */ return this.modifier;
    }

    public Boolean getConsulter() {
        /* 113 */ return this.consulter;
    }

    public Boolean getImprimer() {
        /* 117 */ return this.imprimer;
    }

    public Boolean getSupprimer() {
        /* 121 */ return this.supprimer;
    }

    public Double getTotal() {
        /* 125 */ return this.total;
    }

    public void setTotal(Double total) {
        /* 129 */ this.total = total;
    }

    public String getFileName() {
        /* 133 */ return this.fileName;
    }

    public Boolean getShowSelectorCommand() {
        /* 137 */ return this.showSelectorCommand;
    }

    public Routine getRoutine() {
        /* 141 */ return this.routine;
    }

    public Mvtstock getMvtstock() {
        /* 145 */ return this.mvtstock;
    }

    public void setMvtstock(Mvtstock mvtstock) {
        /* 149 */ this.mvtstock = mvtstock;
    }

    public List<Lignemvtstock> getLignemvtstocks() {
        /* 153 */ return this.lignemvtstocks;
    }

    public String getPrintDialogTitle() {
        /* 157 */ return this.printDialogTitle;
    }

    public void setPrintDialogTitle(String printDialogTitle) {
        /* 161 */ this.printDialogTitle = printDialogTitle;
    }

    public List<Magasinarticle> getMagasinarticles() {
        /* 165 */ return this.magasinarticles;
    }

    public void setMagasinarticles(List<Magasinarticle> magasinarticles) {
        /* 169 */ this.magasinarticles = magasinarticles;
    }

    public List<Magasinlot> getMagasinlots() {
        /* 173 */ return this.magasinlots;
    }

    public void setMagasinlots(List<Magasinlot> magasinlots) {
        /* 177 */ this.magasinlots = magasinlots;
    }

    public Famille getFamille() {
        /* 181 */ return this.famille;
    }

    public void setFamille(Famille famille) {
        /* 185 */ this.famille = famille;
    }

    public List<Famille> getFamilles() {
        /* 189 */ this.familles = this.familleFacadeLocal.findAllRange();
        /* 190 */ return this.familles;
    }

    public void setFamilles(List<Famille> familles) {
        /* 194 */ this.familles = familles;
    }

    public List<Magasinlot> getSelectedMagasinlots() {
        /* 198 */ return this.selectedMagasinlots;
    }

    public void setSelectedMagasinlots(List<Magasinlot> selectedMagasinlots) {
        /* 202 */ this.selectedMagasinlots = selectedMagasinlots;
    }

    public Transfert getTransfert() {
        /* 206 */ return this.transfert;
    }

    public void setTransfert(Transfert transfert) {
        /* 210 */ this.transfert = transfert;
        /* 211 */ this.modifier = (this.supprimer = this.detail = this.imprimer = Boolean.valueOf(transfert == null));
    }

    public List<Transfert> getTransferts() {
        /* 215 */ this.transferts = this.transfertFacadeLocal.findAllRange();
        /* 216 */ return this.transferts;
    }

    public void setTransferts(List<Transfert> transferts) {
        /* 220 */ this.transferts = transferts;
    }

    public Lignetransfert getLignetransfert() {
        /* 224 */ return this.lignetransfert;
    }

    public void setLignetransfert(Lignetransfert lignetransfert) {
        /* 228 */ this.lignetransfert = lignetransfert;
    }

    public List<Lignetransfert> getLignetransferts() {
        /* 232 */ return this.lignetransferts;
    }

    public void setLignetransferts(List<Lignetransfert> lignetransferts) {
        /* 236 */ this.lignetransferts = lignetransferts;
    }

    public Magasin getMagasin() {
        /* 240 */ return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        /* 244 */ this.magasin = magasin;
    }

    public Magasin getMagasinCible() {
        /* 248 */ return this.magasinCible;
    }

    public void setMagasinCible(Magasin magasinCible) {
        /* 252 */ this.magasinCible = magasinCible;
    }

    public List<Magasin> getMagasins() {
        /* 256 */ this.magasins = this.magasinFacadeLocal.findAllRange();
        /* 257 */ return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        /* 261 */ this.magasins = magasins;
    }

    public Magasinarticle getMagasinarticle() {
        /* 265 */ return this.magasinarticle;
    }

    public void setMagasinarticle(Magasinarticle magasinarticle) {
        /* 269 */ this.magasinarticle = magasinarticle;
    }

    public Magasinlot getMagasinlot() {
        /* 273 */ return this.magasinlot;
    }

    public void setMagasinlot(Magasinlot magasinlot) {
        /* 277 */ this.magasinlot = magasinlot;
    }

    public List<Lignetransfert> getLignetransferts_1() {
        /* 281 */ return this.lignetransferts_1;
    }

    public void setLignetransferts_1(List<Lignetransfert> lignetransferts_1) {
        /* 285 */ this.lignetransferts_1 = lignetransferts_1;
    }

    public String getLibelle_article() {
        /* 289 */ return this.libelle_article;
    }
}
