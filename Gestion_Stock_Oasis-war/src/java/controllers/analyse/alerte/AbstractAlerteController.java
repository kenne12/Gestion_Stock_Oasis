package controllers.analyse.alerte;

import entities.Commandefournisseur;
import entities.Demande;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.transaction.UserTransaction;
import sessions.CommandefournisseurFacadeLocal;
import sessions.DemandeFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinarticleFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.MouchardFacadeLocal;
import utils.Routine;
import utils.SessionMBean;

public class AbstractAlerteController {

    @Resource
    protected UserTransaction ut;

    @EJB
    protected CommandefournisseurFacadeLocal commandefournisseurFacadeLocal;
    protected List<Commandefournisseur> commandefournisseurs = new ArrayList();

    @EJB
    protected DemandeFacadeLocal demandeFacadeLocal;
    protected List<Demande> demandes = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected List<Magasinlot> magasinlot_peremps = new ArrayList();
    protected List<Magasinlot> magasinlot_alert = new ArrayList();

    @EJB
    protected MagasinarticleFacadeLocal magasinarticleFacadeLocal;
    protected List<Magasinarticle> magasinarticle_alert = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected List<Magasin> magasins = new ArrayList();
    protected List<Magasin> magasins_1 = new ArrayList();

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Routine routine = new Routine();

    protected String fileName = "";

    protected String mode = "";

    public String getFileName() {
        return this.fileName;
    }

    public Routine getRoutine() {
        return this.routine;
    }

    public List<Demande> getDemandes() {
        demandes.clear();
        if (SessionMBean.getMagasin() != null) {
            this.demandes = this.demandeFacadeLocal.findByValidee(SessionMBean.getMagasin().getIdmagasin(), false);
        }
        return this.demandes;
    }

    public void setDemandes(List<Demande> demandes) {
        this.demandes = demandes;
    }

    public List<Magasinlot> getMagasinlot_peremps() {
        return this.magasinlot_peremps;
    }

    public void setMagasinlot_peremps(List<Magasinlot> magasinlot_peremps) {
        this.magasinlot_peremps = magasinlot_peremps;
    }

    public List<Magasinlot> getMagasinlot_alert() {
        return this.magasinlot_alert;
    }

    public void setMagasinlot_alert(List<Magasinlot> magasinlot_alert) {
        this.magasinlot_alert = magasinlot_alert;
    }

    public List<Magasin> getMagasins() {
        return this.magasins;
    }

    public void setMagasins(List<Magasin> magasins) {
        this.magasins = magasins;
    }

    public List<Commandefournisseur> getCommandefournisseurs() {
        commandefournisseurs.clear();
        if(SessionMBean.getMagasin()!=null){
            this.commandefournisseurs = this.commandefournisseurFacadeLocal.findByLivre(SessionMBean.getMagasin().getIdmagasin(), false);
        }
        return this.commandefournisseurs;
    }

    public List<Magasin> getMagasins_1() {
        return this.magasins_1;
    }

    public List<Magasinarticle> getMagasinarticle_alert() {
        return this.magasinarticle_alert;
    }

    public void setMagasinarticle_alert(List<Magasinarticle> magasinarticle_alert) {
        this.magasinarticle_alert = magasinarticle_alert;
    }

    public void setMagasins_1(List<Magasin> magasins_1) {
        this.magasins_1 = magasins_1;
    }
}
