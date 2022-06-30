package controllers.analyse.mvt_par_produit;

import entities.Article;
import entities.Lignemvtstock;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.ArticleFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import utils.SessionMBean;

public class AbstratMvtPrReportController {

    protected Date dateDebut = new Date();
    protected Date dateFin = new Date();

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
    protected List<Lignemvtstock> lignemvtstocks = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();
    protected List<Article> articles = new ArrayList();

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public List<Article> getArticles() {
        this.articles = this.articleFacadeLocal.findAllRange(SessionMBean.getMagasin().getParametrage().getId(), true);
        return this.articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Lignemvtstock> getLignemvtstocks() {
        return this.lignemvtstocks;
    }

    public void setLignemvtstocks(List<Lignemvtstock> lignemvtstocks) {
        this.lignemvtstocks = lignemvtstocks;
    }
}
