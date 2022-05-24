package controllers.analyse.analys_recette;

import entities.AnneeMois;
import entities.Lignelivraisonclient;
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
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class AnalyseRecetteController extends AbstratAnalyseRecetteController implements Serializable {

    public AnalyseRecetteController() {

    }

    @PostConstruct
    private void init() {
        this.magasin = SessionMBean.getMagasin();
        annee = SessionMBean.getAnnee();
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    private double getLigneLivraison(List<Lignelivraisonclient> list) {
        return list.stream().mapToDouble(Lignelivraisonclient::getMontant).sum();
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
            if (this.magasin.getIdmagasin() == null) {
                JsfUtil.addErrorMessage("Veuillez selectionner un magasin");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                return;
            }

            if (annee.getIdannee() == null) {
                JsfUtil.addErrorMessage("Veuillez selectionner un exercice");
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                return;
            }
            annee = anneeFacadeLocal.find(annee.getIdannee());
            magasin = magasinFacadeLocal.find(magasin.getIdmagasin());
            lignelivraisonclients = lignelivraisonclientFacadeLocal
                    .findByIdMagasin(magasin.getIdmagasin(), annee.getDateDebut(), annee.getDateFin());
            List<AnneeMois> listMois = anneeMoisFacadeLocal.findByAnnee(annee.getIdannee());

            if (lignelivraisonclients == null || lignelivraisonclients.isEmpty()) {
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                JsfUtil.addErrorMessage("Aucune donnée trouvée");
                return;
            }

            this.montantTotal = this.getLigneLivraison(lignelivraisonclients);
            magasinlots = magasinlotFacadeLocal.findByIdMagasin(magasin.getIdmagasin());

            for (Magasinlot ml : magasinlots) {
                AnalyseRD a = new AnalyseRD();
                a.setMagasinlot(ml);

                List<Lignelivraisonclient> llcLots = lignelivraisonclientFacadeLocal.findByIdLot(ml.getIdmagasinlot(), annee.getDateDebut(), annee.getDateFin());
                if (!llcLots.isEmpty()) {
                    Double totalLot = this.getLigneLivraison(llcLots);
                    a.setMontantTotal(totalLot);
                    a.setPourcentage((totalLot / montantTotal) * 100);
                    this.pourcentage += a.getPourcentage();
                }

                for (AnneeMois mois : listMois) {
                    List<Lignelivraisonclient> llcLotsMois = lignelivraisonclientFacadeLocal.findByIdLot(ml.getIdmagasinlot(), mois.getDateDebut(), mois.getDateFin());
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
        } catch (Exception e) {
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            e.printStackTrace();
            JsfUtil.addErrorMessage("Erreur survenue");
        }
    }

    public String formatMoney(double montant) {
        return JsfUtil.formaterStringMoney(montant);
    }

    public String formatMoneyAmount(Double value) {
        return JsfUtil.formaterStringMoney(value.intValue());
    }

    public String roundAndFormat(double value) {
        return JsfUtil.formaterStringMoney(Math.ceil(value));
    }

    public void print() {
        try {
            Map m = new HashMap();
            m.put("perc", pourcentage);
            m.put("total", montantTotal);
            m.put("jan", valeurJanv);
            m.put("fev", valeurFev);
            m.put("mar", valeurMars);
            m.put("avr", valeurAvr);
            m.put("mai", valeurMai);
            m.put("jui", valeurJuin);
            m.put("juil", valeurJuil);
            m.put("aou", valeurAout);
            m.put("sep", valeurSept);
            m.put("oct", valeurOct);
            m.put("nov", valeurNov);
            m.put("dec", valeurDec);

            fileName = PrintUtils.printAnalyseRDAnnuel("R", annee, magasin, m, analyseRDs, SessionMBean.getParametrage());
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('AnalyseRecetteImprimerDialog').show()");
        } catch (Exception e) {
            e.printStackTrace();
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        }
    }
}
