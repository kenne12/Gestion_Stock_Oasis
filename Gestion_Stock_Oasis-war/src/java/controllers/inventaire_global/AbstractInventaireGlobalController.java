package controllers.inventaire_global;

import entities.Article;
import entities.Inventaire;
import entities.Ligneinventaire;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.ArticleFacadeLocal;
import sessions.InventaireFacadeLocal;
import sessions.LigneinventaireFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.MvtstockFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.Routine;
import utils.SessionMBean;
import utils.Utilitaires;

public class AbstractInventaireGlobalController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected InventaireFacadeLocal inventaireFacadeLocal;
    protected Inventaire inventaire = null;
    protected List<Inventaire> inventaires = new ArrayList();

    @EJB
    protected LigneinventaireFacadeLocal ligneinventaireFacadeLocal;
    protected List<Ligneinventaire> ligneinventaires = new ArrayList();
    protected List<Ligneinventaire> ligneinventaires_1 = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected List<Article> articles = new ArrayList();

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected List<Lot> lots = new ArrayList();
    protected List<Lot> selectedLots = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected List<Magasinlot> magasinlots = new ArrayList();
    protected List<Magasinlot> selectedMagasinlots = new ArrayList();

    protected List<Magasinarticle> magasinarticles = new ArrayList();

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;
    protected Mvtstock mvtstock = new Mvtstock();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;
    protected boolean editQuantite = false;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;
    protected boolean valider = true;
    protected boolean cancel = true;

    protected String fileName = "";
    protected boolean showSelectArticle = false;

    protected String mode = "";
    protected String valideBtn = "";

    public Boolean getDetail() {
        return this.detail;
    }

    public Boolean getModifier() {
        try {
            if (this.inventaire != null) {
                if (this.inventaire.getEtat()) {
                    this.modifier = true;
                } else {
                    this.modifier = false;
                }
            } else {
                this.modifier = true;
            }
        } catch (Exception e) {
            this.modifier = true;
        }
        return this.modifier;
    }

    public boolean isValider() {
        try {
            if (this.inventaire != null) {
                if (this.inventaire.getEtat()) {
                    this.valider = true;
                } else {
                    this.valider = false;
                }
            } else {
                this.valider = true;
            }
        } catch (Exception e) {
            this.valider = true;
        }
        return this.valider;
    }

    public Boolean getConsulter() {
        return this.consulter;
    }

    public Boolean getImprimer() {
        return this.imprimer;
    }

    public Boolean getSupprimer() {
        try {
            if (this.inventaire != null) {
                if (this.inventaire.getEtat()) {
                    this.supprimer = true;
                } else {
                    this.supprimer = false;
                }
            } else {
                this.supprimer = true;
            }
        } catch (Exception e) {
            this.supprimer = true;
        }
        return this.supprimer;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public List<Lot> getLots() {
        return this.lots;
    }

    public Inventaire getInventaire() {
        return this.inventaire;
    }

    public void setInventaire(Inventaire inventaire) {
        this.inventaire = inventaire;
        this.supprimer = (this.detail = this.imprimer = inventaire == null);
    }

    public List<Inventaire> getInventaires() {
        try {
            this.inventaires = this.inventaireFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return this.inventaires;
    }

    public List<Ligneinventaire> getLigneinventaires() {
        return this.ligneinventaires;
    }

    public void setLigneinventaires(List<Ligneinventaire> ligneinventaires) {
        this.ligneinventaires = ligneinventaires;
    }

    public List<Ligneinventaire> getLigneinventaires_1() {
        return this.ligneinventaires_1;
    }

    public void setLigneinventaires_1(List<Ligneinventaire> ligneinventaires_1) {
        this.ligneinventaires_1 = ligneinventaires_1;
    }

    public String getValideBtn() {
        return this.valideBtn;
    }

    public void setValideBtn(String valideBtn) {
        this.valideBtn = valideBtn;
    }

    public List<Lot> getSelectedLots() {
        return this.selectedLots;
    }

    public void setSelectedLots(List<Lot> selectedLots) {
        this.selectedLots = selectedLots;
    }

    public boolean isShowSelectArticle() {
        return this.showSelectArticle;
    }

    public boolean isEditQuantite() {
        return this.editQuantite;
    }

    public boolean isCancel() {
        try {
            if (this.inventaire != null) {
                if (this.inventaire.getEtat()) {
                    this.cancel = false;
                } else {
                    this.cancel = true;
                }
            } else {
                this.cancel = true;
            }
        } catch (Exception e) {
            this.cancel = true;
        }
        return this.cancel;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public List<Magasin> getMagasins() {
        this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
        return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
    }

    public String getMode() {
        return this.mode;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public void setMagasinlots(List<Magasinlot> magasinlots) {
        this.magasinlots = magasinlots;
    }

    public List<Magasinlot> getSelectedMagasinlots() {
        return this.selectedMagasinlots;
    }

    public void setSelectedMagasinlots(List<Magasinlot> selectedMagasinlots) {
        this.selectedMagasinlots = selectedMagasinlots;
    }
}
