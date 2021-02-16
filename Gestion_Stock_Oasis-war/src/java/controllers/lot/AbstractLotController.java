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

    protected Boolean detail = Boolean.valueOf(true);
    protected Boolean modifier = Boolean.valueOf(true);
    protected Boolean consulter = Boolean.valueOf(true);
    protected Boolean imprimer = Boolean.valueOf(true);
    protected Boolean supprimer = Boolean.valueOf(true);

    protected boolean disableProduct = false;
    protected boolean showUser = SessionMBean.getParametrage().getEtatuser().booleanValue();
    protected boolean showBailleur = SessionMBean.getParametrage().getEtatbailleur().booleanValue();

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
        /*  77 */ return this.imprimer;
    }

    public Boolean getSupprimer() {
        /*  81 */ return this.supprimer;
    }

    public String getFileName() {
        /*  85 */ return this.fileName;
    }

    public void setFileName(String fileName) {
        /*  89 */ this.fileName = fileName;
    }

    public Routine getRoutine() {
        /*  93 */ return this.routine;
    }

    public boolean isDisableProduct() {
        /*  97 */ return this.disableProduct;
    }

    public Lot getLot() {
        /* 101 */ return this.lot;
    }

    public void setLot(Lot lot) {
        /* 105 */ this.lot = lot;
        /* 106 */ this.modifier = (this.supprimer = this.detail = Boolean.valueOf(lot == null));
    }

    public List<Lot> getLots() {
        /* 110 */ this.lots = this.lotFacadeLocal.findAllRange(true);
        /* 111 */ return this.lots;
    }

    public Article getArticle() {
        /* 115 */ return this.article;
    }

    public void setArticle(Article article) {
        /* 119 */ this.article = article;
    }

    public List<Article> getArticles() {
        /* 123 */ return this.articles;
    }

    public boolean isDateRequired() {
        /* 127 */ return this.dateRequired;
    }

    public boolean isShowUser() {
        /* 131 */ return this.showUser;
    }

    public boolean isShowBailleur() {
        /* 135 */ return this.showBailleur;
    }

    public String getMode() {
        /* 139 */ return this.mode;
    }
}


