package utils;

import entities.Annee;
import entities.AnneeMois;
import entities.Journee;
import entities.Livraisonclient;
import entities.Livraisonfournisseur;
import entities.Magasin;
import entities.Parametrage;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import org.primefaces.model.chart.LineChartModel;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.JourneeFacadeLocal;
import sessions.LivraisonclientFacadeLocal;
import sessions.LivraisonfournisseurFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MenuFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.ParametrageFacadeLocal;
import sessions.PrivilegeFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;

public class AbstractLoginBean {

    @EJB
    protected ParametrageFacadeLocal parametrageFacadeLocal;
    protected Parametrage param = new Parametrage();

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList<>();

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = new Annee();
    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = new AnneeMois();
    protected List<AnneeMois> anneeMoises = new ArrayList<>();

    @EJB
    protected MenuFacadeLocal menuFacadeLocal;

    @EJB
    protected PrivilegeFacadeLocal privilegeFacadeLocal;

    @EJB
    protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;
    protected List<Livraisonclient> livraisonclients = new ArrayList<>();

    @EJB
    protected LivraisonfournisseurFacadeLocal livraisonfournisseurFacadeLocal;
    protected List<Livraisonfournisseur> livraisonfournisseurs = new ArrayList<>();

    @EJB
    protected JourneeFacadeLocal journeeFacadeLocal;
    protected Journee journee = new Journee();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected LineChartModel lineModel;

    protected Date date = Date.from(Instant.now());

    protected String confirmPassword = "";

    protected boolean showHibernatePanel = false;
    protected String hibernatePassword = "";

    protected boolean showSessionPanel = true;

    protected boolean connected = false;

    protected String connectionVisible = "visible";

    public boolean isShowHibernatePanel() {
        return this.showHibernatePanel;
    }

    public void setShowHibernatePanel(boolean showHibernatePanel) {
        this.showHibernatePanel = showHibernatePanel;
    }

    public String getHibernatePassword() {
        return this.hibernatePassword;
    }

    public void setHibernatePassword(String hibernatePassword) {
        this.hibernatePassword = hibernatePassword;
    }

    public Parametrage getParam() {
        return this.param;
    }

    public boolean isShowSessionPanel() {
        return this.showSessionPanel;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public String getConfirmPassword() {
        return this.confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public List<Magasin> getMagasins() {
        return magasins;
    }

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        return annees;
    }

    public LineChartModel getLineModel() {
        return lineModel;
    }

    public void setLineModel(LineChartModel lineModel) {
        this.lineModel = lineModel;
    }

    public Journee getJournee() {
        return journee;
    }

    public void setJournee(Journee journee) {
        this.journee = journee;
    }

    public List<Livraisonclient> getLivraisonclients() {
        return livraisonclients;
    }

    public AnneeMois getAnneeMois() {
        return anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getAnneeMoises() {
        return anneeMoises;
    }

    public List<Livraisonfournisseur> getLivraisonfournisseurs() {
        return livraisonfournisseurs;
    }

}
