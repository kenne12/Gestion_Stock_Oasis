/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers.analyse.analyse_depense;

import entities.AnneeMois;
import entities.Lignelivraisonfournisseur;
import entities.Lot;
import entities.Magasinlot;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.AnalyseRD;
import utils.JsfUtil;
import utils.Printer;
import utils.Utilitaires;

/**
 *
 * @author USER
 */
@ManagedBean
@ViewScoped
public class AnalyseDepenseController extends AbstractAnalyseDepenseController implements Serializable {

    /**
     * Creates a new instance of AnalyseDepenseController
     */
    public AnalyseDepenseController() {
    }

    @PostConstruct
    private void init() {
        this.magasin = this.getMagasins().get(0);
        annee = anneeFacadeLocal.findOneActive();
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    private double getLigneLivraison(List<Lignelivraisonfournisseur> list) {
        Double somme = 0.0;
        for (Lignelivraisonfournisseur llf : list) {
            somme += llf.getPrixachat() * llf.getQuantitemultiple();
        }
        return somme;
    }

    private void initAmount() {
        valeurJanv = 0;
        valeurFev = 0;
        valeurMars = 0;
        valeurAvr = 0;
        valeurMai = 0;
        valeurJuin = 0;
        valeurJuil = 0;
        valeurAout = 0;
        valeurSept = 0;
        valeurOct = 0;
        valeurNov = 0;
        valeurDec = 0;
        montantTotal = 0;
        pourcentage = 0;
    }

    public void search() {
        try {
            this.initAmount();
            analyseRDs.clear();
            if (this.magasin.getIdmagasin() != null && magasin.getIdmagasin() != -1) {
                if (annee.getIdannee() != null && annee.getIdannee() != -1) {
                    annee = anneeFacadeLocal.find(annee.getIdannee());
                    magasin = magasinFacadeLocal.find(magasin.getIdmagasin());

                    lignelivraisonfournisseurs = lignelivraisonfournisseurFacadeLocal.findByIdMagasin(magasin.getIdmagasin(), annee.getDateDebut(), annee.getDateFin());
                    List<AnneeMois> listMois = anneeMoisFacadeLocal.findByAnnee(annee.getIdannee());
                    if (!lignelivraisonfournisseurs.isEmpty()) {
                        this.montantTotal = this.getLigneLivraison(lignelivraisonfournisseurs);
                        magasinlots = magasinlotFacadeLocal.findByIdMagasin(magasin.getIdmagasin());

                        for (Magasinlot ml : magasinlots) {
                            AnalyseRD a = new AnalyseRD();
                            a.setMagasinlot(ml);
                            a.setMontantTotal(0);
                            a.setPourcentage(0);

                            List<Lignelivraisonfournisseur> llcLots = lignelivraisonfournisseurFacadeLocal.findByIdLot(ml.getIdmagasinlot(), annee.getDateDebut(), annee.getDateFin());
                            if (!llcLots.isEmpty()) {
                                Double totalLot = this.getLigneLivraison(llcLots);
                                a.setMontantTotal(totalLot);
                                a.setPourcentage((totalLot / montantTotal) * 100);
                                this.pourcentage += a.getPourcentage();
                            }

                            for (AnneeMois mois : listMois) {
                                List<Lignelivraisonfournisseur> llcLotsMois = lignelivraisonfournisseurFacadeLocal.findByIdLot(ml.getIdmagasinlot(), mois.getDateDebut(), mois.getDateFin());
                                if (!listMois.isEmpty()) {
                                    Double totalMois = this.getLigneLivraison(llcLotsMois);
                                    switch (mois.getIdmois().getIdmois()) {
                                        case 1: {
                                            a.setValeurJanv(totalMois);
                                            this.valeurJanv += totalMois;
                                            break;
                                        }
                                        case 2: {
                                            a.setValeurFev(totalMois);
                                            this.valeurFev += totalMois;
                                            break;
                                        }
                                        case 3: {
                                            a.setValeurMars(totalMois);
                                            this.valeurMars += totalMois;
                                            break;
                                        }
                                        case 4: {
                                            a.setValeurAvr(totalMois);
                                            this.valeurAvr += totalMois;
                                            break;
                                        }
                                        case 5: {
                                            a.setValeurMai(totalMois);
                                            this.valeurMai += totalMois;
                                            break;
                                        }
                                        case 6: {
                                            a.setValeurJuin(totalMois);
                                            this.valeurJuin += totalMois;
                                            break;
                                        }

                                        case 7: {
                                            a.setValeurJuil(totalMois);
                                            this.valeurJuil += totalMois;
                                            break;
                                        }

                                        case 8: {
                                            a.setValeurAout(totalMois);
                                            this.valeurAout += totalMois;
                                            break;
                                        }

                                        case 9: {
                                            a.setValeurSept(totalMois);
                                            this.valeurSept += totalMois;
                                            break;
                                        }

                                        case 10: {
                                            a.setValeurOct(totalMois);
                                            this.valeurOct += totalMois;
                                            break;
                                        }

                                        case 11: {
                                            a.setValeurNov(totalMois);
                                            this.valeurNov += totalMois;
                                            break;
                                        }

                                        case 12: {
                                            a.setValeurJanv(totalMois);
                                            this.valeurDec += totalMois;
                                            break;
                                        }
                                    }
                                }
                            }
                            analyseRDs.add(a);
                        }
                        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                        return;
                    }
                }
                JsfUtil.addErrorMessage("Veuillez selectionner un exercice");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                return;
            }
            JsfUtil.addErrorMessage("Veuillez selectionner un magasin");
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        } catch (Exception e) {
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            e.printStackTrace();
            JsfUtil.addErrorMessage("Erreur survenue");
        }
    }

    public String formatMoney(double montant) {
        return JsfUtil.formaterStringMoney(montant);
    }

    public void printStock() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                Map map = new HashMap();
                map.put("idmagasin", this.magasin.getIdmagasin());
                Printer.print("/reports/ireport/stock_par_magasin.jasper", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}