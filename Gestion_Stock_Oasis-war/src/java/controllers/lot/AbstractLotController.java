package controllers.lot;

import entities.Article;
import entities.Lot;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.ArticleFacadeLocal;
import sessions.LotFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractLotController {

    @EJB
    protected LotFacadeLocal lotFacadeLocal;
    protected Lot lot = new Lot();
    protected List<Lot> lots = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = null;
    protected List<Article> articles = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected String fileName = "";
    protected String mode = "";
    protected boolean dateRequired = true;

    protected Boolean detail = true;
    protected Boolean modifier = true;
    protected Boolean consulter = true;
    protected Boolean imprimer = true;
    protected Boolean supprimer = true;

    protected boolean disableProduct = false;
    protected boolean showUser = SessionMBean.getParametrage().isEtatuser();
    protected boolean showBailleur = SessionMBean.getParametrage().isEtatbailleur();

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

    public void setLot(Lot lot) {
        this.modifier = this.supprimer = this.detail = lot == null;
        this.lot = lot;    
    }

    public List<Lot> getLots() {
        this.lots = this.lotFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId(), true);
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

    public boolean isDateRequired() {
        return this.dateRequired;
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
}
