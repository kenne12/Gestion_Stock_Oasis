package controllers.control_peremption;

import entities.Article;
import entities.Lot;
import entities.Magasin;
import entities.Magasinlot;
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
import utils.Routine;
import utils.SessionMBean;

public class AbstractControlPeremptionController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = null;
    protected List<Article> articles = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected List<Magasinlot> magasinlots = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected boolean showSessionPanel = true;

    @EJB
    protected InventaireFacadeLocal inventaireFacadeLocal;

    @EJB
    protected LigneinventaireFacadeLocal ligneinventaireFacadeLocal;

    @EJB
    protected MvtstockFacadeLocal mvtstockFacadeLocal;

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
    protected String fileName = "";
    protected String mode = "";

    protected boolean disableProduct = false;
    protected boolean showUser = SessionMBean.getParametrage().getEtatuser();
    protected boolean showBailleur = SessionMBean.getParametrage().getEtatbailleur();

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public boolean isDisableProduct() {
        return this.disableProduct;
    }

    public Lot getLot() {
        return this.lot;
    }

    public List<Lot> getLots() {
        return this.lots;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Article> getArticles() {
        return this.articles;
    }

    public boolean isShowUser() {
        return this.showUser;
    }

    public boolean isShowBailleur() {
        return this.showBailleur;
    }

    public String getMode() {
        return this.mode;
    }

    public List<Magasin> getMagasins() {
        return this.magasins;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public boolean isShowSessionPanel() {
        return this.showSessionPanel;
    }
}
