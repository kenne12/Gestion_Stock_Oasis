package controllers.traitement;

import entities.Client;
import entities.Demande;
import entities.Lignedemande;
import entities.Lignelivraisonclient;
import entities.Lignemvtstock;
import entities.Livraisonclient;
import entities.Lot;
import entities.Magasinarticle;
import entities.Magasinlot;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.Printer;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class TraitementController extends AbstractTraitementController implements Serializable {

    @PostConstruct
    private void init() {
        this.unites = this.uniteFacadeLocal.findByStructure(SessionMBean.getParametrage().getId());
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(41L)) {
                notifyError("acces_refuse");
                return;
            }

            this.mode = "Create";

            this.livraisonclient = new Livraisonclient();
            this.client = new Client();

            this.livraisonclient.setMontant(0.0);

            this.lignelivraisonclients.clear();
            this.payement_credit = false;

            this.lignedemandes.clear();
            this.showSelectorCommand = false;

            this.total = 0.0D;
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        try {
            this.demande = new Demande();
            this.demandes = this.demandeFacadeLocal.findByValidee(false);
            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.livraisonclient == null) {
                notifyError("not_row_selected");
                return;
            }

            if (!this.livraisonclient.getLivraisondirecte()) {
                this.showSelectorCommand = true;
                this.demande = this.livraisonclient.getIddemande();

                if (this.demande.getValidee()) {
                    notifyError("demande_deja_livree");
                    return;
                }

                if (!Utilitaires.isAccess(41L)) {
                    notifyError("acces_refuse");
                    this.demande = null;
                    return;
                }

                this.mode = "Edit";

                this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());
                this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());
                this.client = this.livraisonclient.getIddemande().getClient();
                this.total = this.livraisonclient.getMontant();
                RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').show()");
            } else {
                notifyError("vente_directe");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void afficherDemande() {
        this.demandes_1 = this.demandeFacadeLocal.findAllRange();
    }

    public void prepareview() {
        try {
            if (this.livraisonclient != null) {
                this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient().longValue());
                if (!this.lignelivraisonclients.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('LivraisonClientDetailDialog').show()");
                    return;
                }
                notifyError("liste_article_vide");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareviewD() {
        try {
            if (this.demande != null) {
                this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());
                if (!this.lignedemandes.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('DemandeDetailDialog').show()");
                    return;
                }
                notifyError("liste_article_vide");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void selectCommande() {
        try {
            if (this.demande != null) {
                this.lignelivraisonclients.clear();

                client = this.demande.getClient();
                this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());

                this.demande.setDateeffectlivraison(new Date());
                int conteur = 0;
                Double pourcentage = 0.0D;
                for (Lignedemande lcc : this.lignedemandes) {
                    boolean suffisant = false;
                    Double qteDemandee = lcc.getQuantitemultiple();
                    Double qteReste = lcc.getQuantitemultiple();

                    List<Magasinlot> lotTemp = this.magasinlotFacadeLocal.findByArticleIsavailable(lcc.getIdmagasinarticle().getIdmagasin().getIdmagasin(), lcc.getIdmagasinarticle().getIdarticle().getIdarticle());

                    Double sommeQte = 0.0D;

                    //Lignelivraisonclient c;
                    if (!lotTemp.isEmpty()) {
                        Magasinlot lSearch = (Magasinlot) lotTemp.get(0);
                        if (lSearch != null) {
                            if (lSearch.getQuantitemultiple() > 0.0) {
                                Lignelivraisonclient c = new Lignelivraisonclient();
                                c.setIdlignelivraisonclient(0L);
                                c.setIdmagasinlot(lSearch);
                                c.setIdlot(lSearch.getIdlot());
                                c.setMontant(lcc.getMontant());
                                c.setIdunite(lcc.getIdunite());

                                if (lSearch.getQuantitemultiple() >= lcc.getQuantitemultiple()) {
                                    /* 213 */ c.setQuantite(lcc.getQuantite());
                                    /* 214 */ c.setQuantitemultiple(Double.valueOf(c.getQuantite().doubleValue() * lcc.getUnite().doubleValue()));
                                    /* 215 */ c.setQuantitereduite(Double.valueOf(c.getQuantitemultiple().doubleValue() / c.getIdmagasinlot().getIdlot().getIdarticle().getUnite().doubleValue()));
                                    /* 216 */ this.lignelivraisonclients.add(c);
                                    /* 217 */ qteReste = Double.valueOf(0.0D);
                                    /* 218 */ sommeQte = Double.valueOf(sommeQte.doubleValue() + lcc.getQuantitemultiple().doubleValue());
                                    /* 219 */ suffisant = true;
                                } else {
                                    /* 221 */ c.setQuantitemultiple(lSearch.getQuantitemultiple());
                                    /* 222 */ c.setQuantite(Double.valueOf(c.getQuantitemultiple().doubleValue() / lSearch.getUnite().doubleValue()));
                                    /* 223 */ c.setQuantitereduite(Double.valueOf(c.getQuantitemultiple().doubleValue() / c.getIdmagasinlot().getIdlot().getIdarticle().getUnite().doubleValue()));
                                    /* 224 */ qteReste = Double.valueOf(qteDemandee.doubleValue() - lSearch.getQuantitemultiple().doubleValue());
                                    /* 225 */ this.lignelivraisonclients.add(c);
                                    /* 226 */ sommeQte = Double.valueOf(sommeQte.doubleValue() + lSearch.getQuantitemultiple().doubleValue());
                                    /* 227 */ suffisant = false;
                                }
                            } else {
                                suffisant = false;
                            }
                        }

                        if (!suffisant) {
                            for (Magasinlot l : lotTemp) {
                                if (!l.getIdlot().equals(lotTemp)) {
                                    if (l.getQuantitemultiple() > 0.0D) {
                                        /* 241 */ Lignelivraisonclient c = new Lignelivraisonclient();
                                        /* 242 */ c.setIdlignelivraisonclient(0L);
                                        /* 243 */ c.setIdmagasinlot(l);
                                        /* 244 */ c.setIdlot(l.getIdlot());
                                        /* 245 */ c.setMontant(lcc.getMontant());
                                        /* 246 */ c.setIdunite(lcc.getIdunite());

                                        if (l.getQuantitemultiple() >= qteReste) {
                                            /* 249 */ c.setQuantitemultiple(qteReste);
                                            /* 250 */ c.setQuantite(Double.valueOf(qteReste.doubleValue() / l.getUnite().doubleValue()));
                                            /* 251 */ c.setQuantitereduite(Double.valueOf(l.getQuantitemultiple().doubleValue() / l.getIdlot().getIdarticle().getUnite().doubleValue()));
                                            /* 252 */ this.lignelivraisonclients.add(c);
                                            /* 253 */ sommeQte = Double.valueOf(sommeQte.doubleValue() + qteReste.doubleValue());
                                            /* 254 */ suffisant = true;
                                            /* 255 */ break;
                                        }
                                        qteReste = (qteReste - l.getQuantitemultiple());
                                        c.setQuantitemultiple(l.getQuantitemultiple());
                                        c.setQuantite(Double.valueOf(l.getQuantitemultiple().doubleValue() / l.getUnite().doubleValue()));
                                        c.setQuantitereduite(Double.valueOf(l.getQuantitemultiple().doubleValue() / l.getIdlot().getIdarticle().getUnite().doubleValue()));
                                        this.lignelivraisonclients.add(c);
                                        sommeQte = Double.valueOf(sommeQte.doubleValue() + l.getQuantitemultiple().doubleValue());
                                        suffisant = false;
                                    } else {
                                        suffisant = false;
                                    }
                                }
                            }
                        }
                    }
                    Utilitaires.arrondiNDecimales(sommeQte.doubleValue() / qteDemandee.doubleValue() * 100.0D, 2);
                    ((Lignedemande) this.lignedemandes.get(conteur)).setTauxsatisfaction(Utilitaires.arrondiNDecimales(sommeQte.doubleValue() / qteDemandee.doubleValue() * 100.0D, 2));
                    pourcentage = Double.valueOf(pourcentage.doubleValue() + Utilitaires.arrondiNDecimales(sommeQte.doubleValue() / qteDemandee.doubleValue() * 100.0D, 2).doubleValue());
                    conteur++;
                }
                this.demande.setTauxsatisfaction(pourcentage);
                updateTotal();
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Lignedemande searchByMagasinLotInLigneDemande(Magasinlot ml) {
        for (Lignedemande ld : this.lignedemandes) {
            if (ld.getIdmagasinarticle().equals(ml.getIdmagasinarticle())) {
                return ld;
            }
        }
        return null;
    }

    public void create() {
        try {
            if ("Create".equals(this.mode)) {
                if (!this.lignelivraisonclients.isEmpty()) {
                    updateTotal();

                    for (Lignedemande ld : this.lignedemandes) {
                        Double somme = 0.0;
                        for (Lignelivraisonclient llc : this.lignelivraisonclients) {
                            if ((ld.getIdmagasinarticle().getIdarticle().equals(llc.getIdlot().getIdarticle()))
                                    && (ld.getIdmagasinarticle().getIdmagasin().equals(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasin()))) {
                                somme += llc.getQuantitemultiple();
                            }

                        }

                        if (somme > ld.getQuantitemultiple()) {
                            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                            JsfUtil.addErrorMessage("Les quantités accordées sont superieurs à celles demandées");
                            return;
                        }

                        if (somme > ld.getIdmagasinarticle().getQuantitemultiple()) {
                            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                            JsfUtil.addErrorMessage("Les quantités accordées sont superieurs à celles disponible en stock");
                            return;
                        }
                    }

                    /* 327 */ String message = "";
                    /* 328 */ updateTotal();
                    /* 329 */ this.ut.begin();

                    /* 331 */ this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    /* 332 */ String codeMvt = "MVT";
                    /* 333 */ codeMvt = Utilitaires.genererCodeStock(codeMvt, this.mvtstock.getIdmvtstock());
                    /* 334 */ this.mvtstock.setCode(codeMvt);

                    /* 336 */ this.mvtstock.setDatemvt(this.demande.getDateeffectlivraison());
                    /* 337 */ this.mvtstock.setClient(this.demande.getClient().getNom() + " " + this.demande.getClient().getPrenom());
                    /* 338 */ this.mvtstock.setFournisseur(" ");
                    /* 339 */ this.mvtstock.setMagasin(" ");
                    /* 340 */ this.mvtstock.setType(" ");
                    /* 341 */ this.mvtstockFacadeLocal.create(this.mvtstock);

                    /* 343 */ String code = "LIV";
                    /* 344 */ this.livraisonclient.setIdlivraisonclient(this.livraisonclientFacadeLocal.nextValue());
                    /* 345 */ code = Utilitaires.genererCodeDemande(code, this.livraisonclient.getIdlivraisonclient());
                    /* 346 */ this.livraisonclient.setCode(code);
                    /* 347 */ this.livraisonclient.setMontant(this.total);
                    /* 348 */ this.livraisonclient.setLivraisondirecte(false);
                    /* 349 */ this.livraisonclient.setDatelivraison(this.demande.getDateeffectlivraison());
                    /* 350 */ this.livraisonclient.setIddemande(this.demande);
                    /* 351 */ this.livraisonclient.setClient(this.demande.getClient());
                    /* 352 */ this.livraisonclient.setIdmvtstock(this.mvtstock);
                    /* 353 */ this.livraisonclientFacadeLocal.create(this.livraisonclient);

                    for (Lignelivraisonclient llc : this.lignelivraisonclients) {
                        llc.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());
                        llc.setIdlivraisonclient(this.livraisonclient);
                        this.lignelivraisonclientFacadeLocal.create(llc);

                        /* 360 */ Lignedemande ldTemp = searchByMagasinLotInLigneDemande(llc.getIdmagasinlot());

                        /* 362 */ Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        /* 363 */ maTemp.setQuantite((maTemp.getQuantite() - llc.getQuantite()));
                        /* 364 */ maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - llc.getQuantitemultiple()));
                        /* 365 */ maTemp.setQuantitereduite((maTemp.getQuantitereduite() - llc.getQuantitereduite()));
                        /* 366 */ maTemp.setQuantitevirtuelle((maTemp.getQuantitevirtuelle() - ldTemp.getQuantitemultiple()));
                        /* 367 */ this.magasinarticleFacadeLocal.edit(maTemp);

                        /* 369 */ Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot());
                        /* 370 */ mlTemp.setQuantite((mlTemp.getQuantite() - llc.getQuantite()));
                        /* 371 */ mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - llc.getQuantitemultiple()));
                        /* 372 */ mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - llc.getQuantitereduite()));
                        /* 373 */ this.magasinlotFacadeLocal.edit(mlTemp);

                        /* 375 */ Lignemvtstock lmvts = new Lignemvtstock();
                        /* 376 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        /* 377 */ lmvts.setIdmvtstock(this.mvtstock);
                        /* 378 */ lmvts.setIdlot(llc.getIdmagasinlot().getIdlot());
                        /* 379 */ lmvts.setQtesortie(llc.getQuantitemultiple());
                        /* 380 */ lmvts.setQteentree(0.0);
                        /* 381 */ lmvts.setReste(mlTemp.getQuantitemultiple());
                        /* 382 */ lmvts.setIdmagasinlot(llc.getIdmagasinlot());
                        /* 383 */ lmvts.setUnite(llc.getUnite());
                        /* 384 */ lmvts.setMagasin(" ");
                        /* 385 */ lmvts.setClient(this.demande.getClient().getNom() + " " + this.demande.getClient().getPrenom());
                        /* 386 */ lmvts.setFournisseur(" ");
                        /* 387 */ lmvts.setType("SORTIE");
                        /* 388 */ this.lignemvtstockFacadeLocal.create(lmvts);
                    }
                    this.demande.setValidee(true);
                    /* 391 */ this.demandeFacadeLocal.edit(this.demande);
                    /* 392 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Validation de la demande  N° : " + this.livraisonclient.getIddemande().getCode(), SessionMBean.getUserAccount());

                    /* 394 */ this.ut.commit();
                    /* 395 */ this.demande = new Demande();
                    /* 396 */ this.lignedemandes.clear();
                    /* 397 */ this.lignelivraisonclients.clear();
                    /* 398 */ this.detail = (this.supprimer = this.modifier = this.imprimer = this.imprimer2 = true);
                    /* 399 */ JsfUtil.addSuccessMessage(message);

                    /* 401 */ notifySuccess();
                    /* 402 */ RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').hide()");
                    /* 403 */ this.demandes_1 = this.demandeFacadeLocal.findAllRange();
                } else {
                    notifyError("liste_article_vide");
                }
            } else if (this.livraisonclient != null) {
                this.ut.commit();
                this.lignedemandes.clear();
                this.demandes.clear();
                this.demande = new Demande();
                this.livraisonclient = null;
                this.supprimer = (this.modifier = this.imprimer = Boolean.valueOf(true));

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('LignelivraisonclientCreateDialog').hide()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            this.livraisonclient = this.livraisonclientFacadeLocal.findByIddemande(this.demande.getIddemande());

            if (this.livraisonclient != null) {
                if (!this.livraisonclient.getLivraisondirecte()) {
                    if (!Utilitaires.isAccess(41L)) {
                        notifyError("acces_refuse");
                        this.detail = this.supprimer = this.modifier = this.imprimer = true;
                        this.livraisonclient = null;
                        return;
                    }

                    this.ut.begin();

                    List<Lignelivraisonclient> temp = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient());

                    if (!temp.isEmpty()) {
                        for (Lignelivraisonclient llc : temp) {

                            /* 451 */ llc.setIdmagasinlot(this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot()));

                            /* 453 */ llc.getIdmagasinlot().setQuantite((llc.getIdmagasinlot().getQuantite() + llc.getQuantite()));
                            /* 454 */ llc.getIdmagasinlot().setQuantitemultiple((llc.getIdmagasinlot().getQuantitemultiple() + llc.getQuantitemultiple()));
                            /* 455 */ llc.getIdmagasinlot().setQuantitereduite((llc.getIdmagasinlot().getQuantitereduite() + llc.getQuantitereduite()));
                            /* 456 */ llc.getIdmagasinlot().setQuantitevirtuelle((llc.getIdmagasinlot().getQuantitevirtuelle() + llc.getQuantitemultiple()));

                            /* 458 */ llc.getIdmagasinlot().getIdmagasinarticle().setQuantite((llc.getIdmagasinlot().getIdmagasinarticle().getQuantite() + llc.getQuantite()));
                            /* 459 */ llc.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple((llc.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple() + llc.getQuantitemultiple()));
                            /* 460 */ llc.getIdmagasinlot().getIdmagasinarticle().setQuantitereduite((llc.getIdmagasinlot().getIdmagasinarticle().getQuantitereduite() + llc.getQuantitereduite()));

                            /* 463 */ this.magasinlotFacadeLocal.edit(llc.getIdmagasinlot());
                            /* 464 */ this.magasinarticleFacadeLocal.edit(llc.getIdmagasinlot().getIdmagasinarticle());

                            /* 466 */ this.lignelivraisonclientFacadeLocal.remove(llc);
                        }
                    }
                    Lignelivraisonclient llc;
                    /* 469 */ this.livraisonclientFacadeLocal.remove(this.livraisonclient);

                    /* 471 */ this.demande = this.livraisonclient.getIddemande();

                    /* 473 */ this.demande.setValidee(false);
                    /* 474 */ this.demande.setTauxsatisfaction(0.0);
                    /* 475 */ this.demandeFacadeLocal.edit(this.demande);

                    List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.livraisonclient.getIdmvtstock().getIdmvtstock());
                    for (Lignemvtstock l : lmvt) {
                        this.lignemvtstockFacadeLocal.remove(l);
                    }

                    /* 482 */ this.mvtstockFacadeLocal.remove(this.livraisonclient.getIdmvtstock());

                    /* 484 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de validation de la demande : " + this.livraisonclient.getCode(), SessionMBean.getUserAccount());
                    /* 485 */ this.ut.commit();

                    /* 487 */ this.livraisonclient = null;
                    /* 488 */ this.demande = null;
                    /* 489 */ this.supprimer = (this.modifier = this.imprimer = this.detail = this.imprimer2 = true);
                    /* 490 */ notifySuccess();
                    /* 491 */ this.demandes_1 = this.demandeFacadeLocal.findAllRange();
                } else {
                    notifyError("vente_directe");
                }
            } /* 496 */ else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 499 */ notifyFail(e);
        }
    }

    public void initPrinter(Livraisonclient l) {
        /* 504 */ this.livraisonclient = l;
        /* 505 */ print();
    }

    public void initPrinterBon(Livraisonclient l) {
        /* 509 */ this.livraisonclient = l;
        /* 510 */ printBonLivraison();
    }

    public void initEdit(Livraisonclient l) {
        /* 514 */ this.livraisonclient = l;
        /* 515 */ prepareEdit();
    }

    public void initViewV(Demande d) {
        /* 519 */ Livraisonclient lc = this.livraisonclientFacadeLocal.findByIddemande(d.getIddemande().longValue());
        /* 520 */ this.livraisonclient = lc;
        /* 521 */ prepareview();
    }

    public void initView(Livraisonclient l) {
        /* 525 */ this.livraisonclient = l;
        /* 526 */ prepareview();
    }

    public void initViewD(Demande d) {
        /* 530 */ this.demande = d;
        /* 531 */ prepareviewD();
    }

    public void initTreatD(Demande d) {
        /* 535 */ this.demande = d;
        /* 536 */ prepareCreate();
        /* 537 */ selectCommande();
        /* 538 */ RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').show()");
    }

    public void initDelete(Livraisonclient l) {
        /* 542 */ this.livraisonclient = l;
        /* 543 */ delete();
    }

    public void print() {
        try {
            this.livraisonclient = this.livraisonclientFacadeLocal.findByIddemande(this.demande.getIddemande());

            if (!Utilitaires.isAccess(42L)) {
                /* 552 */ notifyError("acces_refuse");
                /* 553 */ this.livraisonclient = null;
                /* 554 */ return;
            }

            if (this.livraisonclient != null) {
                /* 558 */ this.printDialogTitle = this.routine.localizeMessage("livraisonclient");
                /* 559 */ Map map = new HashMap();
                /* 560 */ map.put("idlivraisonclient", this.livraisonclient.getIdlivraisonclient());
                /* 561 */ Printer.print("/reports/ireport/fiche_sortie_materiel.jasper", map);
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printBonLivraison() {
        try {
            if (!Utilitaires.isAccess(54L)) {
                notifyError("acces_refuse");
                this.livraisonclient = null;
                return;
            }

            if (this.livraisonclient != null) {
                this.printDialogTitle = this.routine.localizeMessage("bon_de_livraison");

                RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void printDemande() {
        try {
            if (!Utilitaires.isAccess(33L)) {
                notifyError("acces_refuse");
                this.demande = null;
                return;
            }

            if (this.demande != null) {
                Map param = new HashMap();
                param.put("iddemande", this.demande.getIddemande());
                Printer.print("/reports/ireport/demande_report.jasper", param);
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void addArticle() {
        try {
            Lignedemande l = this.lignedemande;

            boolean drapeau = false;
            int i = 0;
            for (Lignedemande lcc : this.lignedemandes) {
                i++;
            }

            if (!drapeau) {
                this.lignedemandes.add(l);
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
                this.lignedemande = new Lignedemande();

                return;
            }
            JsfUtil.addErrorMessage("Article existant dans la lignelivraisonclient");
            updateTotal();
        } catch (Exception e) {
            e.printStackTrace();
            this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("formulaire_incomplet"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void removeArticle(int index) {
        try {
            boolean trouve = false;
            this.ut.begin();

            Lignedemande lcc = (Lignedemande) this.lignedemandes.get(index);

            this.lignedemandes.remove(index);

            updateTotal();
            if (trouve) {
                this.demandeFacadeLocal.edit(this.demande);
            }
            this.ut.commit();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal(List<Lignelivraisonclient> lignelivraisonclients) {
        Double resultat = 0.0;
        int i = 0;
        for (Lignelivraisonclient llc : lignelivraisonclients) {
            resultat += (llc.getMontant() * llc.getQuantite());
            (lignelivraisonclients.get(i)).setQuantitemultiple((llc.getQuantite() * llc.getIdlot().getIdarticle().getUnite()));
            i++;
        }
        return resultat;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal(this.lignelivraisonclients);
            this.livraisonclient.setMontant(this.total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            this.cout_quantite = 0.0D;
            if ((this.lignedemande.getQuantite() != null) && (this.lignedemande.getMontant() != null)) {
                this.cout_quantite = (this.lignedemande.getMontant() * this.lignedemande.getQuantite());
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.cout_quantite = 0.0;
        }
    }

    public void updatedataLot() {
    }

    public void notifyError(String message) {
        /* 711 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 712 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 716 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 717 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 718 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 722 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 723 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 724 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
