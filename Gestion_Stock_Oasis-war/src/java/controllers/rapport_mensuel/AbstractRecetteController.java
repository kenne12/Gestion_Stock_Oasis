package controllers.rapport_mensuel;

import entities.Annee;
import entities.AnneeMois;

import entities.Journee;
import entities.Magasin;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;

import sessions.JourneeFacadeLocal;
import sessions.LivraisonclientFacadeLocal;
import sessions.LivraisonfournisseurFacadeLocal;
import sessions.MouchardFacadeLocal;
import sessions.TransfertFacadeLocal;

import utils.Routine;
import utils.SessionMBean;

public class AbstractRecetteController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = new Annee();
    protected List<Annee> annees = SessionMBean.getAnnees();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;
    protected AnneeMois anneeMois = new AnneeMois();
    protected List<AnneeMois> listMois = new ArrayList<>();

    protected Magasin magasin = SessionMBean.getMagasin();
    protected List<Magasin> magasins = SessionMBean.getMagasins();

    @EJB
    protected JourneeFacadeLocal journeeFacadeLocal;
    protected List<Journee> journees = new ArrayList<>();

    @EJB
    protected LivraisonclientFacadeLocal livraisonclientFacadeLocal;

    @EJB
    protected LivraisonfournisseurFacadeLocal livraisonfournisseurFacadeLocal;

    @EJB
    protected TransfertFacadeLocal transfertFacadeLocal;

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;

    protected Routine routine = new Routine();

    protected boolean imprimer = true;

    protected String mode = "";

    protected String fileName = "";

    public boolean isImprimer() {
        return imprimer;
    }

    public String getFileName() {
        return this.fileName;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public String getMode() {
        return this.mode;
    }

    public List<Annee> getAnnees() {
        return this.annees;
    }

    public AnneeMois getAnneeMois() {
        return this.anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public List<AnneeMois> getListMois() {
        return this.listMois;
    }

    public List<Journee> getJournees() {
        return this.journees;
    }

    public Annee getAnnee() {
        return this.annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
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

}
