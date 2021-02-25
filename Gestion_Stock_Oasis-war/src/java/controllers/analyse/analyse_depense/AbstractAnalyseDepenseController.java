/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.analyse_depense;

import entities.Annee;
import entities.Lignelivraisonclient;
import entities.Lignelivraisonfournisseur;
import entities.Magasin;
import entities.Magasinlot;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.AnneeFacadeLocal;
import sessions.AnneeMoisFacadeLocal;
import sessions.LignelivraisonclientFacadeLocal;
import sessions.LignelivraisonfournisseurFacadeLocal;
import sessions.MagasinFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.AnalyseRD;
import utils.SessionMBean;
import utils.Utilitaires;

/**
 *
 * @author USER
 */
public class AbstractAnalyseDepenseController {

    public AbstractAnalyseDepenseController() {

    }

    @EJB
    protected LignelivraisonfournisseurFacadeLocal lignelivraisonfournisseurFacadeLocal;
    protected List<Lignelivraisonfournisseur> lignelivraisonfournisseurs = new ArrayList<>();

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected AnneeFacadeLocal anneeFacadeLocal;
    protected Annee annee = new Annee();
    protected List<Annee> annees = new ArrayList<>();

    @EJB
    protected AnneeMoisFacadeLocal anneeMoisFacadeLocal;

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected List<Magasinlot> magasinlots = new ArrayList();

    public List<AnalyseRD> analyseRDs = new ArrayList<>();

    protected String fileName = "";

    protected double valeurJanv;
    protected double valeurFev;
    protected double valeurMars;
    protected double valeurAvr;
    protected double valeurMai;
    protected double valeurJuin;
    protected double valeurJuil;
    protected double valeurAout;
    protected double valeurSept;
    protected double valeurOct;
    protected double valeurNov;
    protected double valeurDec;
    protected double montantTotal;
    protected double pourcentage;

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

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

    public Annee getAnnee() {
        return annee;
    }

    public void setAnnee(Annee annee) {
        this.annee = annee;
    }

    public List<Annee> getAnnees() {
        try {
            annees = anneeFacadeLocal.findByEtat(true);
        } catch (Exception e) {
        }
        return annees;
    }

    public String getFileName() {
        return fileName;
    }

    public List<AnalyseRD> getAnalyseRDs() {
        return analyseRDs;
    }

    public double getValeurJanv() {
        return valeurJanv;
    }

    public double getValeurFev() {
        return valeurFev;
    }

    public double getValeurMars() {
        return valeurMars;
    }

    public double getValeurAvr() {
        return valeurAvr;
    }

    public double getValeurMai() {
        return valeurMai;
    }

    public double getValeurJuin() {
        return valeurJuin;
    }

    public double getValeurJuil() {
        return valeurJuil;
    }

    public double getValeurAout() {
        return valeurAout;
    }

    public double getValeurSept() {
        return valeurSept;
    }

    public double getValeurOct() {
        return valeurOct;
    }

    public double getValeurNov() {
        return valeurNov;
    }

    public double getValeurDec() {
        return valeurDec;
    }

    public void setValeurDec(double valeurDec) {
        this.valeurDec = valeurDec;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public double getPourcentage() {
        return pourcentage;
    }

}
