package controllers.analyse.mvt_par_projet;

import entities.Lignelivraisonclient;
import entities.Projet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import sessions.LignelivraisonclientFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.ProjetFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.SessionMBean;
import utils.Utilitaires;

public class AbstratMvtPReportController {

    @EJB
    protected ProjetFacadeLocal projetFacadeLocal;
    protected Projet projet = new Projet();
    protected List<Projet> projets = new ArrayList();

    @EJB
    protected LignelivraisonclientFacadeLocal lignelivraisonclientFacadeLocal;
    protected List<Lignelivraisonclient> lignelivraisonclients = new ArrayList();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;
    protected Date dateDebut = new Date();
    protected Date dateFin = new Date();

    public List<Lignelivraisonclient> getLignelivraisonclients() {
        return this.lignelivraisonclients;
    }

    public void setLignelivraisonclients(List<Lignelivraisonclient> lignelivraisonclients) {
        this.lignelivraisonclients = lignelivraisonclients;
    }

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public Projet getProjet() {
        return this.projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public List<Projet> getProjets() {
        List list = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
        this.projets = Utilitaires.searchProjetctByMagasin(this.projetFacadeLocal, list);
        return this.projets;
    }

    public void setProjets(List<Projet> projets) {
        this.projets = projets;
    }
}
