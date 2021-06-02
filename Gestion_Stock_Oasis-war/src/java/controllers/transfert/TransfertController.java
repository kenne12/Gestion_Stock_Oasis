package controllers.transfert;

import entities.Famille;
import entities.Lignemvtstock;
import entities.Lignetransfert;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Transfert;
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
public class TransfertController extends AbstractTransertController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(43L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
            this.mode = "Create";

            this.magasinCible = new Magasin();
            this.magasin = new Magasin();

            this.lignetransferts.clear();
            this.lignetransferts_1.clear();
            this.transfert = new Transfert();
            this.transfert.setDatetransfert(new Date());
            this.magasinlots.clear();
            this.selectedMagasinlots.clear();

            this.showSelectorCommand = false;

            this.total = 0.0D;
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareAddArticle() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                if (this.magasinCible.getIdmagasin() != null) {
                    if (this.magasin.getIdmagasin() != this.magasinCible.getIdmagasin()) {
                        this.famille = new Famille();

                        this.lignetransfert = new Lignetransfert();
                        this.lignetransfert.setQuantite(1.0D);

                        this.magasinlots.clear();
                        this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinQtyNotNull(this.magasin.getIdmagasin());

                        this.magasinarticle = new Magasinarticle();
                        this.magasinlot = new Magasinlot();
                        this.lignetransferts_1.clear();
                        this.libelle_article = "-";
                        RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
                        return;
                    }
                    notifyError("veuillez_selectionner_les_magasin_differents");
                    return;
                }
                notifyError("veuillez_selectionner_le_magasin_cible");
                return;
            }
            notifyError("veuillez_selectionner_le_magasin_initial");
            return;
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.transfert == null) {
                notifyError("not_row_selected");
                return;
            }

            this.showSelectorCommand = true;

            if (!Utilitaires.isAccess(49L)) {
                notifyError("acces_refuse");
                this.transfert = null;
                return;
            }

            this.mode = "Edit";
            this.magasin = this.transfert.getIdmagasin();
            this.magasinCible = this.magasinFacadeLocal.find(this.transfert.getIdmagasincible());

            this.lignetransferts = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());
            this.lignetransferts_1 = this.lignetransferts;
            this.total = this.transfert.getMontanttotal();
            RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.transfert != null) {
                this.lignetransferts = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());

                if (!this.lignetransferts.isEmpty()) {
                    this.magasin = this.transfert.getIdmagasin();
                    this.magasinCible = this.magasinFacadeLocal.find(this.transfert.getIdmagasincible());
                    RequestContext.getCurrentInstance().execute("PF('TransfertDetailDialog').show()");
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

    public Magasin findMagasin(int idmagasin) {
        return this.magasinFacadeLocal.find(idmagasin);
    }

    public void filterArticle() {
        try {
            this.magasinarticles.clear();
            this.magasinlots.clear();
            this.selectedMagasinlots.clear();
            this.magasinarticle = new Magasinarticle();
            this.magasinlot = new Magasinlot();
            if (this.famille.getIdfamille() != null) {
                this.magasinarticleFacadeLocal.findByIdmagasinIdfamilleIsavailable(this.magasin.getIdmagasin(), this.famille.getIdfamille(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProduit() {
        try {
            if (this.magasinlot != null) {
                Magasinlot ml = this.magasinlot;

                if (this.magasinlot.getQuantitemultiple() < 1.0D) {
                    notifyError("defaut_de_quantite");
                    return;
                }

                if (this.lignetransfert.getQuantite() > this.magasinlot.getQuantitemultiple() - this.magasinlot.getQuantitevirtuelle()) {
                    notifyError("quantite_inexate");
                    return;
                }

                if (findMagasinLotInLigneRepartition(ml, this.lignetransferts)) {
                    /* 205 */ Lignetransfert lt = this.lignetransfert;
                    /* 206 */ lt.setIdlignetransfert((0L));
                    /* 207 */ lt.setIdmagasinlot(ml);
                    /* 208 */ lt.setUnite(ml.getIdlot().getIdarticle().getUnite());
                    /* 209 */ lt.setQuantitemultiple(ml.getIdlot().getIdarticle().getUnite());
                    /* 210 */ this.lignetransferts.add(lt);
                }
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean findMagasinLotInLigneRepartition(Magasinlot m, List<Lignetransfert> lignetransferts) {
        if (lignetransferts.isEmpty()) {
            return true;
        }
        boolean result = false;

        for (Lignetransfert lt : lignetransferts) {
            if (!lt.getIdmagasinlot().equals(m)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void selectProduct() {
        try {
            if (this.magasinlot != null) {
                this.lignetransfert.setUnite(this.magasinlot.getIdmagasinarticle().getIdarticle().getUnite());
                this.libelle_article = this.magasinlot.getIdmagasinarticle().getIdarticle().getLibelle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void validateData() {
        try {
            if (!this.lignetransferts_1.isEmpty()) {
                double qte_physique = 0.0D;
                double qte_solicitee = 0.0D;
                for (Lignetransfert lt : this.lignetransferts_1) {
                    qte_physique += lt.getIdmagasinlot().getQuantite();
                    qte_solicitee += lt.getQuantite();
                }

                if (qte_solicitee > qte_physique) {
                    notifyError("quantite_inexate");
                    return;
                }

                for (Lignetransfert lt : this.lignetransferts_1) {
                    if (findMagasinLotInLigneRepartition(lt.getIdmagasinlot(), this.lignetransferts)) {
                        this.lignetransferts.add(lt);
                    }
                }

                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void updatedata() {
        try {
            this.magasinlot = new Magasinlot();
            this.magasinlots.clear();
            this.selectedMagasinlots.clear();
            if (this.magasinarticle != null) {
                this.famille = this.magasinarticle.getIdarticle().getIdfamille();
                this.magasinlots = this.magasinlotFacadeLocal.findByArticleIsavailable(this.magasin.getIdmagasin(), this.magasinarticle.getIdarticle().getIdarticle(), this.magasinarticle.getIdarticle().getPerissable(), new Date());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            /* 290 */ if ("Create".equals(this.mode)) {
                /* 292 */ if (!this.lignetransferts.isEmpty()) {
                    /* 294 */ this.magasin = this.magasinFacadeLocal.find(this.magasin.getIdmagasin());
                    /* 295 */ this.magasinCible = this.magasinFacadeLocal.find(this.magasinCible.getIdmagasin());

                    /* 297 */ String message = "";

                    /* 299 */ String codeMvt = "MVT";
                    /* 300 */ this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    /* 301 */ Long nextMvt = this.mvtstock.getIdmvtstock();
                    /* 302 */ codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);

                    /* 304 */ this.ut.begin();

                    /* 306 */ this.mvtstock.setCode(codeMvt);
                    /* 307 */ this.mvtstock.setDatemvt(this.transfert.getDatetransfert());
                    /* 308 */ this.mvtstock.setClient(this.magasinCible.getNom());
                    /* 309 */ this.mvtstock.setFournisseur(this.magasin.getNom());
                    /* 310 */ this.mvtstock.setMagasin(" ");
                    /* 311 */ this.mvtstock.setType("ENTRE SORTIE");
                    /* 312 */ this.mvtstockFacadeLocal.create(this.mvtstock);

                    /* 314 */ String code = "MVT";
                    /* 315 */ Long nextVal = this.transfertFacadeLocal.nextVal();
                    /* 316 */ code = Utilitaires.genererCodeDemande(code, nextVal);
                    /* 317 */ this.transfert.setCode(code);
                    /* 318 */ this.transfert.setIdtransfert(nextVal);
                    /* 319 */ this.transfert.setIdmagasin(this.magasin);
                    /* 320 */ this.transfert.setIdmagasincible(this.magasinCible.getIdmagasin());
                    /* 321 */ this.transfert.setIdmvtstock(this.mvtstock);
                    /* 322 */ this.transfertFacadeLocal.create(this.transfert);

                    /* 324 */ updateTotal();

                    /* 326 */ for (Lignetransfert ltt : this.lignetransferts) {
                        /* 328 */ ltt.setIdlignetransfert(this.lignetransfertFacadeLocal.nextVal());
                        /* 329 */ ltt.setIdtransfert(this.transfert);
                        /* 330 */ ltt.setQuantitemultiple((ltt.getQuantite() * ltt.getUnite()));
                        /* 331 */ ltt.setMontant((ltt.getIdmagasinlot().getIdlot().getPrixunitaire() * ltt.getQuantite()));
                        /* 332 */ this.lignetransfertFacadeLocal.create(ltt);

                        /* 334 */ Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ltt.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        /* 335 */ maTemp.setQuantite((maTemp.getQuantite() - ltt.getQuantite()));
                        /* 336 */ maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - ltt.getQuantitemultiple()));
                        /* 337 */ maTemp.setQuantitereduite((maTemp.getQuantitereduite() - ltt.getQuantitereduite()));
                        /* 338 */ this.magasinarticleFacadeLocal.edit(maTemp);

                        /* 340 */ Magasinlot mlTemp = this.magasinlotFacadeLocal.find(ltt.getIdmagasinlot().getIdmagasinlot());
                        /* 341 */ mlTemp.setQuantite((mlTemp.getQuantite() - ltt.getQuantite()));
                        /* 342 */ mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - ltt.getQuantitemultiple()));
                        /* 343 */ mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - ltt.getQuantitereduite()));
                        /* 344 */ this.magasinlotFacadeLocal.edit(mlTemp);

                        /* 346 */ Lignemvtstock lmvts = new Lignemvtstock();
                        /* 347 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        /* 348 */ lmvts.setIdmvtstock(this.mvtstock);
                        /* 349 */ lmvts.setIdlot(ltt.getIdmagasinlot().getIdlot());
                        /* 350 */ lmvts.setIdmagasinlot(ltt.getIdmagasinlot());
                        /* 351 */ lmvts.setQtesortie(ltt.getQuantite());
                        /* 352 */ lmvts.setQteentree((0.0D));
                        /* 353 */ lmvts.setClient(this.magasinCible.getNom());
                        /* 354 */ lmvts.setFournisseur(" ");
                        /* 355 */ lmvts.setType("SORTIE");
                        /* 356 */ lmvts.setReste(mlTemp.getQuantitemultiple());
                        /* 357 */ this.lignemvtstockFacadeLocal.create(lmvts);

                        Magasinlot ml = this.magasinlotFacadeLocal.findByIdmagasinIdlot(this.magasinCible.getIdmagasin().intValue(), ltt.getIdmagasinlot().getIdlot().getIdlot().longValue());
                        if (ml != null) {
                            /* 361 */ ml.setIdmagasinarticle(this.magasinarticleFacadeLocal.find(ml.getIdmagasinarticle().getIdmagasinarticle()));
                            /* 362 */ ml.getIdmagasinarticle().setQuantite((ml.getIdmagasinarticle().getQuantite() + ltt.getQuantite()));
                            /* 363 */ ml.getIdmagasinarticle().setQuantitemultiple((ml.getIdmagasinarticle().getQuantitemultiple() + ltt.getQuantitemultiple()));
                            /* 364 */ ml.getIdmagasinarticle().setQuantitereduite((ml.getIdmagasinarticle().getQuantitereduite() + ltt.getQuantitereduite()));
                            /* 365 */ this.magasinarticleFacadeLocal.edit(ml.getIdmagasinarticle());

                            /* 367 */ ml.setQuantite((ml.getQuantite() + ltt.getQuantite()));
                            /* 368 */ ml.setQuantitemultiple((ml.getQuantitemultiple() + ltt.getQuantitemultiple()));
                            /* 369 */ ml.setQuantitereduite((ml.getQuantitereduite() + ltt.getQuantitereduite()));

                            /* 371 */ this.magasinlotFacadeLocal.edit(ml);

                            /* 374 */ Lignemvtstock lmvt = new Lignemvtstock();
                            /* 375 */ lmvt.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            /* 376 */ lmvt.setIdmvtstock(this.mvtstock);
                            /* 377 */ lmvt.setIdlot(ml.getIdlot());
                            /* 378 */ lmvt.setIdmagasinlot(ml);
                            /* 379 */ lmvt.setQtesortie((0.0D));
                            /* 380 */ lmvt.setQteentree(ltt.getQuantitemultiple());
                            /* 381 */ lmvt.setClient(" ");
                            /* 382 */ lmvt.setReste(ml.getQuantitemultiple());
                            /* 383 */ lmvt.setFournisseur(this.magasin.getNom());
                            /* 384 */ lmvt.setType("ENTREE");
                            /* 385 */ this.lignemvtstockFacadeLocal.create(lmvt);
                            /* 386 */ System.err.println("existe et le mouvement crée");
                        } else {
                            Magasinarticle ma = this.magasinarticleFacadeLocal.findByIdmagasinIdarticle(this.magasinCible.getIdmagasin().intValue(), ltt.getIdmagasinlot().getIdlot().getIdarticle().getIdarticle().longValue());
                            if (ma == null) {
                                /* 391 */ ma = new Magasinarticle();
                                /* 392 */ ma.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                                /* 393 */ ma.setIdarticle(ltt.getIdmagasinlot().getIdlot().getIdarticle());
                                /* 394 */ ma.setIdmagasin(this.magasinCible);
                                /* 395 */ ma.setQuantite(ltt.getQuantite());
                                /* 396 */ ma.setQuantitemultiple(ltt.getQuantitemultiple());
                                /* 397 */ ma.setUnite(ltt.getUnite());
                                /* 398 */ ma.setEtat((true));
                                /* 399 */ ma.setQuantitereduite(ltt.getQuantitereduite());
                                /* 400 */ ma.setQuantitevirtuelle((0.0D));
                                /* 401 */ ma.setQuantitesecurite(ltt.getIdmagasinlot().getIdlot().getIdarticle().getQuantitesecurite());
                                /* 402 */ this.magasinarticleFacadeLocal.create(ma);
                            } else {
                                /* 404 */ ma.setQuantite((ma.getQuantite() + ltt.getQuantite()));
                                /* 405 */ ma.setQuantitemultiple((ma.getQuantitemultiple() + ltt.getQuantitemultiple()));
                                /* 406 */ ma.setQuantitereduite((ma.getQuantitereduite() + ltt.getQuantitereduite()));
                                /* 407 */ this.magasinarticleFacadeLocal.edit(ma);
                            }

                            /* 410 */ Magasinlot mlTemp1 = new Magasinlot();
                            /* 411 */ mlTemp1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                            /* 412 */ mlTemp1.setIdmagasinarticle(ma);
                            /* 413 */ mlTemp1.setIdlot(ltt.getIdmagasinlot().getIdlot());
                            /* 414 */ mlTemp1.setEtat((true));
                            /* 415 */ mlTemp1.setQuantite(ltt.getQuantite());
                            /* 416 */ mlTemp1.setUnite(ltt.getUnite());
                            /* 417 */ mlTemp1.setQuantitemultiple(ltt.getQuantitemultiple());
                            /* 418 */ mlTemp1.setQuantitereduite(ltt.getQuantitemultiple());
                            /* 419 */ mlTemp1.setQuantitevirtuelle((0.0D));

                            /* 421 */ this.magasinlotFacadeLocal.create(mlTemp1);

                            /* 424 */ Lignemvtstock lmvt = new Lignemvtstock();
                            /* 425 */ lmvt.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            /* 426 */ lmvt.setIdmvtstock(this.mvtstock);
                            /* 427 */ lmvt.setIdlot(mlTemp1.getIdlot());
                            /* 428 */ lmvt.setIdmagasinlot(mlTemp1);
                            /* 429 */ lmvt.setQtesortie(Double.valueOf(0.0D));
                            /* 430 */ lmvt.setQteentree(ltt.getQuantitemultiple());
                            /* 431 */ lmvt.setClient(" ");
                            /* 432 */ lmvt.setReste(ltt.getQuantitemultiple());
                            /* 433 */ lmvt.setFournisseur(this.magasin.getNom());
                            /* 434 */ lmvt.setType("ENTREE");
                            /* 435 */ this.lignemvtstockFacadeLocal.create(lmvt);
                            /* 436 */ System.err.println("existe et le mouvement crée");

                            /* 438 */ System.err.println("n'existe pas et le mouvement crée");
                        }

                    }

                    /* 443 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du transfert d'article ; N° : " + this.transfert.getCode() + "; Montant : " + this.transfert.getMontanttotal(), SessionMBean.getUserAccount());

                    /* 445 */ this.ut.commit();
                    /* 446 */ this.detail = (this.supprimer = this.modifier = this.imprimer = (true));
                    /* 447 */ JsfUtil.addSuccessMessage(message);

                    /* 449 */ notifySuccess();
                    /* 450 */ RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').hide()");
                } else {
                    /* 452 */ notifyError("liste_article_vide");
                }
            } /* 455 */ else if (this.transfert == null) {
                /* 458 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 462 */ notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.transfert != null) {
                if (!Utilitaires.isAccess((43L))) {
                    notifyError("acces_refuse");
                    this.detail = (this.supprimer = this.modifier = this.imprimer = (true));
                    this.transfert = null;
                    return;
                }

                this.ut.begin();

                List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.transfert.getIdmvtstock().getIdmvtstock());
                for (Lignemvtstock l : lmvt) {
                    this.lignemvtstockFacadeLocal.remove(l);
                }
                Lignemvtstock l;
                List<Lignetransfert> listLigneTransfert = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());
                for (Lignetransfert lt : listLigneTransfert) {
                    this.lignetransfertFacadeLocal.remove(lt);

                    /* 488 */ Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(lt.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                    /* 489 */ maTemp.setQuantite((maTemp.getQuantite() + lt.getQuantite()));
                    /* 490 */ maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() + lt.getQuantitemultiple()));
                    /* 491 */ maTemp.setQuantitereduite((maTemp.getQuantitereduite() + lt.getQuantitereduite()));
                    /* 492 */ this.magasinarticleFacadeLocal.edit(maTemp);

                    /* 494 */ Magasinlot mlTemp = this.magasinlotFacadeLocal.find(lt.getIdmagasinlot().getIdmagasinlot());
                    /* 495 */ mlTemp.setQuantite((mlTemp.getQuantite() + lt.getQuantite()));
                    /* 496 */ mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() + lt.getQuantitemultiple()));
                    /* 497 */ mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() + lt.getQuantitereduite()));
                    /* 498 */ this.magasinlotFacadeLocal.edit(mlTemp);

                    Magasinlot ml = this.magasinlotFacadeLocal.findByIdmagasinIdlot(this.transfert.getIdmagasincible().intValue(), lt.getIdmagasinlot().getIdlot().getIdlot().longValue());
                    if (ml != null) {
                        /* 503 */ Magasinarticle ma1 = ml.getIdmagasinarticle();

                        /* 505 */ ma1.setQuantite((ma1.getQuantite() - lt.getQuantite()));
                        /* 506 */ ma1.setQuantitemultiple((ma1.getQuantitemultiple() - lt.getQuantitemultiple()));
                        /* 507 */ ma1.setQuantitereduite((ma1.getQuantitereduite() - lt.getQuantitereduite()));
                        /* 508 */ this.magasinarticleFacadeLocal.edit(ma1);

                        /* 510 */ ml.setQuantite((ml.getQuantite() - lt.getQuantite()));
                        /* 511 */ ml.setQuantitemultiple((ml.getQuantitemultiple() - lt.getQuantitemultiple()));
                        /* 512 */ ml.setQuantitereduite((ml.getQuantitereduite() - lt.getQuantitereduite()));
                        /* 513 */ this.magasinlotFacadeLocal.edit(ml);
                    }
                }

                /* 517 */ this.transfertFacadeLocal.remove(this.transfert);
                /* 518 */ this.mvtstockFacadeLocal.remove(this.transfert.getIdmvtstock());

                /* 520 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation du transfert d'article : " + this.transfert.getCode() + " ; Montant : " + this.transfert.getMontanttotal(), SessionMBean.getUserAccount());
                /* 521 */ this.ut.commit();

                /* 523 */ this.transfert = null;
                /* 524 */ this.supprimer = (this.modifier = this.imprimer = this.detail = Boolean.valueOf(true));
                /* 525 */ notifySuccess();
            } else {
                /* 528 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 531 */ notifyFail(e);
        }
    }

    public void initPrinter(Transfert t) {
        /* 536 */ this.transfert = t;
        /* 537 */ print();
    }

    public void initEdit(Transfert t) {
        /* 541 */ this.transfert = t;
        /* 542 */ prepareEdit();
    }

    public void initView(Transfert t) {
        /* 546 */ this.transfert = t;
        /* 547 */ prepareview();
    }

    public void initDelete(Transfert t) {
        /* 551 */ this.transfert = t;
        /* 552 */ delete();
    }

    public void print() {
        try {
            /* 557 */ if (!Utilitaires.isAccess((43L))) {
                /* 558 */ notifyError("acces_refuse");
                /* 559 */ this.transfert = null;
                /* 560 */ return;
            }

            /* 563 */ if (this.transfert != null) {
                /* 568 */ Map map = new HashMap();
                /* 569 */ map.put("idtransfert", this.transfert.getIdtransfert());
                /* 570 */ Printer.print("/reports/ireport/transfert.jasper", map);
                /* 571 */ RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                /* 573 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 576 */ notifyFail(e);
        }
    }

    public void removeArticle(int index) {
        try {
            boolean trouve = false;

            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal(List<Lignetransfert> lignetransferts) {
        Double resultat = 0.0D;
        int i = 0;
        for (Lignetransfert lt : lignetransferts) {
            resultat += (lt.getIdmagasinlot().getIdlot().getPrixunitaire() * lt.getQuantite() * lt.getUnite());
            lignetransferts.get(i).setQuantitemultiple((lignetransferts.get(i).getQuantite() * lt.getUnite()));
            lignetransferts.get(i).setQuantitereduite((lignetransferts.get(i).getQuantitemultiple() / lt.getIdmagasinlot().getIdlot().getIdarticle().getUnite()));
            i++;
        }
        return resultat;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal(this.lignetransferts);
            this.transfert.setMontanttotal(this.total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
