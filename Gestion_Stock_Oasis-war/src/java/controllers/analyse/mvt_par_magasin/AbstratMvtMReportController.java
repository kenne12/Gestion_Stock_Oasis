package controllers.analyse.mvt_par_magasin;

import entities.Lignemvtstock;
import entities.Magasin;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.ArticleFacadeLocal;
import sessions.LignemvtstockFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.SessionMBean;
import utils.Utilitaires;

public class AbstratMvtMReportController {

    protected Date dateDebut = Date.from(Instant.now());
    protected Date dateFin = Date.from(Instant.now());

    @EJB
    protected LignemvtstockFacadeLocal lignemvtstockFacadeLocal;
    protected List<Lignemvtstock> lignemvtstocks = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected ArticleFacadeLocal articleFacadeLocal;

    protected String fileName = "";

    protected boolean printStateBtn = true;

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

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

    public List<Lignemvtstock> getLignemvtstocks() {
        return this.lignemvtstocks;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public List<Magasin> getMagasins() {
        this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal,
                this.utilisateurmagasinFacadeLocal,
                SessionMBean.getUserAccount().getIdpersonnel());
        return this.magasins;
    }

    public String getFileName() {
        return fileName;
    }

    public boolean isPrintStateBtn() {
        return printStateBtn;
    }

    public void setPrintStateBtn(boolean printStateBtn) {
        this.printStateBtn = printStateBtn;
    }

}
