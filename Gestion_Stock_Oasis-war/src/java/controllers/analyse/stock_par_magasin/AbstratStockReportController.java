package controllers.analyse.stock_par_magasin;

import entities.Article;
import entities.Magasin;
import entities.Magasinlot;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.ArticleFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.Routine;
import utils.SessionMBean;
import utils.Utilitaires;

public class AbstratStockReportController {

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;
    protected Article article = new Article();

    protected String chemin_replicated_images = SessionMBean.getParametrage().getRepertoireLogo();
    protected String filename = "";
    protected String imageDir = "/photos/products";

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected List<Magasinlot> magasinlots = new ArrayList();

    protected Routine routine = new Routine();

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    protected boolean printBtnState = true;

    protected String fileName = "";

    public List<Magasin> getMagasins() {
        this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
        return this.magasins;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isPrintBtnState() {
        return printBtnState;
    }

    public void setPrintBtnState(boolean printBtnState) {
        this.printBtnState = printBtnState;
    }

    public Routine getRoutine() {
        return routine;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

}
