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
            /* 137 */ this.magasinCible = this.magasinFacadeLocal.find(this.transfert.getIdmagasincible());

            /* 139 */ this.lignetransferts = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());
            /* 140 */ this.lignetransferts_1 = this.lignetransferts;
            /* 141 */ this.total = this.transfert.getMontanttotal();
            /* 142 */ RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
        } catch (Exception e) {
            /* 145 */ notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            /* 151 */ if (this.transfert != null) {
                /* 153 */ this.lignetransferts = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert());

                /* 155 */ if (!this.lignetransferts.isEmpty()) {
                    /* 156 */ this.magasin = this.transfert.getIdmagasin();
                    /* 157 */ this.magasinCible = this.magasinFacadeLocal.find(this.transfert.getIdmagasincible());
                    /* 158 */ RequestContext.getCurrentInstance().execute("PF('TransfertDetailDialog').show()");
                    /* 159 */ return;
                }
                /* 161 */ notifyError("liste_article_vide");
            } else {
                /* 163 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 166 */ notifyFail(e);
        }
    }

    public Magasin findMagasin(int idmagasin) {
        /* 171 */ return this.magasinFacadeLocal.find(Integer.valueOf(idmagasin));
    }

    public void filterArticle() {
        try {
            /* 176 */ this.magasinarticles.clear();
            /* 177 */ this.magasinlots.clear();
            /* 178 */ this.selectedMagasinlots.clear();
            /* 179 */ this.magasinarticle = new Magasinarticle();
            /* 180 */ this.magasinlot = new Magasinlot();
            /* 181 */ if (this.famille.getIdfamille() != null) /* 182 */ {
                this.magasinarticleFacadeLocal.findByIdmagasinIdfamilleIsavailable(this.magasin.getIdmagasin().intValue(), this.famille.getIdfamille().longValue(), true);
            }
        } catch (Exception e) {
            /* 185 */ e.printStackTrace();
        }
    }

    public void addProduit() {
        try {
            /* 191 */ if (this.magasinlot != null) {
                /* 192 */ Magasinlot ml = this.magasinlot;

                /* 194 */ if (this.magasinlot.getQuantitemultiple().doubleValue() < 1.0D) {
                    /* 195 */ notifyError("defaut_de_quantite");
                    /* 196 */ return;
                }

                /* 199 */ if (this.lignetransfert.getQuantite().doubleValue() > this.magasinlot.getQuantitemultiple().doubleValue() - this.magasinlot.getQuantitevirtuelle().doubleValue()) {
                    /* 200 */ notifyError("quantite_inexate");
                    /* 201 */ return;
                }

                /* 204 */ if (findMagasinLotInLigneRepartition(ml, this.lignetransferts)) {
                    /* 205 */ Lignetransfert lt = this.lignetransfert;
                    /* 206 */ lt.setIdlignetransfert(Long.valueOf(0L));
                    /* 207 */ lt.setIdmagasinlot(ml);
                    /* 208 */ lt.setUnite(ml.getIdlot().getIdarticle().getUnite());
                    /* 209 */ lt.setQuantitemultiple(ml.getIdlot().getIdarticle().getUnite());
                    /* 210 */ this.lignetransferts.add(lt);
                }
                /* 212 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            /* 215 */ e.printStackTrace();
        }
    }

    private boolean findMagasinLotInLigneRepartition(Magasinlot m, List<Lignetransfert> lignetransferts) {
        /* 220 */ if (lignetransferts.isEmpty()) {
            /* 221 */ return true;
        }
        /* 223 */ boolean result = false;

        /* 225 */ for (Lignetransfert lt : lignetransferts) {
            /* 226 */ if (!lt.getIdmagasinlot().equals(m)) {
                /* 227 */ result = true;
                /* 228 */ break;
            }
        }
        /* 231 */ return result;
    }

    public void selectProduct() {
        try {
            /* 236 */ if (this.magasinlot != null) {
                /* 237 */ this.lignetransfert.setUnite(this.magasinlot.getIdmagasinarticle().getIdarticle().getUnite());
                /* 238 */ this.libelle_article = this.magasinlot.getIdmagasinarticle().getIdarticle().getLibelle();
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
                        /* 330 */ ltt.setQuantitemultiple(Double.valueOf(ltt.getQuantite().doubleValue() * ltt.getUnite().doubleValue()));
                        /* 331 */ ltt.setMontant(Double.valueOf(ltt.getIdmagasinlot().getIdlot().getPrixunitaire().doubleValue() * ltt.getQuantite().doubleValue()));
                        /* 332 */ this.lignetransfertFacadeLocal.create(ltt);

                        /* 334 */ Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ltt.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        /* 335 */ maTemp.setQuantite(Double.valueOf(maTemp.getQuantite().doubleValue() - ltt.getQuantite().doubleValue()));
                        /* 336 */ maTemp.setQuantitemultiple(Double.valueOf(maTemp.getQuantitemultiple().doubleValue() - ltt.getQuantitemultiple().doubleValue()));
                        /* 337 */ maTemp.setQuantitereduite(Double.valueOf(maTemp.getQuantitereduite().doubleValue() - ltt.getQuantitereduite().doubleValue()));
                        /* 338 */ this.magasinarticleFacadeLocal.edit(maTemp);

                        /* 340 */ Magasinlot mlTemp = this.magasinlotFacadeLocal.find(ltt.getIdmagasinlot().getIdmagasinlot());
                        /* 341 */ mlTemp.setQuantite(Double.valueOf(mlTemp.getQuantite().doubleValue() - ltt.getQuantite().doubleValue()));
                        /* 342 */ mlTemp.setQuantitemultiple(Double.valueOf(mlTemp.getQuantitemultiple().doubleValue() - ltt.getQuantitemultiple().doubleValue()));
                        /* 343 */ mlTemp.setQuantitereduite(Double.valueOf(mlTemp.getQuantitereduite().doubleValue() - ltt.getQuantitereduite().doubleValue()));
                        /* 344 */ this.magasinlotFacadeLocal.edit(mlTemp);

                        /* 346 */ Lignemvtstock lmvts = new Lignemvtstock();
                        /* 347 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        /* 348 */ lmvts.setIdmvtstock(this.mvtstock);
                        /* 349 */ lmvts.setIdlot(ltt.getIdmagasinlot().getIdlot());
                        /* 350 */ lmvts.setIdmagasinlot(ltt.getIdmagasinlot());
                        /* 351 */ lmvts.setQtesortie(ltt.getQuantite());
                        /* 352 */ lmvts.setQteentree(Double.valueOf(0.0D));
                        /* 353 */ lmvts.setClient(this.magasinCible.getNom());
                        /* 354 */ lmvts.setFournisseur(" ");
                        /* 355 */ lmvts.setType("SORTIE");
                        /* 356 */ lmvts.setReste(mlTemp.getQuantitemultiple());
                        /* 357 */ this.lignemvtstockFacadeLocal.create(lmvts);

                        /* 359 */ Magasinlot ml = this.magasinlotFacadeLocal.findByIdmagasinIdlot(this.magasinCible.getIdmagasin().intValue(), ltt.getIdmagasinlot().getIdlot().getIdlot().longValue());
                        /* 360 */ if (ml != null) {
                            /* 361 */ ml.setIdmagasinarticle(this.magasinarticleFacadeLocal.find(ml.getIdmagasinarticle().getIdmagasinarticle()));
                            /* 362 */ ml.getIdmagasinarticle().setQuantite(Double.valueOf(ml.getIdmagasinarticle().getQuantite().doubleValue() + ltt.getQuantite().doubleValue()));
                            /* 363 */ ml.getIdmagasinarticle().setQuantitemultiple(Double.valueOf(ml.getIdmagasinarticle().getQuantitemultiple().doubleValue() + ltt.getQuantitemultiple().doubleValue()));
                            /* 364 */ ml.getIdmagasinarticle().setQuantitereduite(Double.valueOf(ml.getIdmagasinarticle().getQuantitereduite().doubleValue() + ltt.getQuantitereduite().doubleValue()));
                            /* 365 */ this.magasinarticleFacadeLocal.edit(ml.getIdmagasinarticle());

                            /* 367 */ ml.setQuantite(Double.valueOf(ml.getQuantite().doubleValue() + ltt.getQuantite().doubleValue()));
                            /* 368 */ ml.setQuantitemultiple(Double.valueOf(ml.getQuantitemultiple().doubleValue() + ltt.getQuantitemultiple().doubleValue()));
                            /* 369 */ ml.setQuantitereduite(Double.valueOf(ml.getQuantitereduite().doubleValue() + ltt.getQuantitereduite().doubleValue()));

                            /* 371 */ this.magasinlotFacadeLocal.edit(ml);

                            /* 374 */ Lignemvtstock lmvt = new Lignemvtstock();
                            /* 375 */ lmvt.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            /* 376 */ lmvt.setIdmvtstock(this.mvtstock);
                            /* 377 */ lmvt.setIdlot(ml.getIdlot());
                            /* 378 */ lmvt.setIdmagasinlot(ml);
                            /* 379 */ lmvt.setQtesortie(Double.valueOf(0.0D));
                            /* 380 */ lmvt.setQteentree(ltt.getQuantitemultiple());
                            /* 381 */ lmvt.setClient(" ");
                            /* 382 */ lmvt.setReste(ml.getQuantitemultiple());
                            /* 383 */ lmvt.setFournisseur(this.magasin.getNom());
                            /* 384 */ lmvt.setType("ENTREE");
                            /* 385 */ this.lignemvtstockFacadeLocal.create(lmvt);
                            /* 386 */ System.err.println("existe et le mouvement crée");
                        } else {
                            /* 389 */ Magasinarticle ma = this.magasinarticleFacadeLocal.findByIdmagasinIdarticle(this.magasinCible.getIdmagasin().intValue(), ltt.getIdmagasinlot().getIdlot().getIdarticle().getIdarticle().longValue());
                            /* 390 */ if (ma == null) {
                                /* 391 */ ma = new Magasinarticle();
                                /* 392 */ ma.setIdmagasinarticle(this.magasinarticleFacadeLocal.nextVal());
                                /* 393 */ ma.setIdarticle(ltt.getIdmagasinlot().getIdlot().getIdarticle());
                                /* 394 */ ma.setIdmagasin(this.magasinCible);
                                /* 395 */ ma.setQuantite(ltt.getQuantite());
                                /* 396 */ ma.setQuantitemultiple(ltt.getQuantitemultiple());
                                /* 397 */ ma.setUnite(ltt.getUnite());
                                /* 398 */ ma.setEtat(Boolean.valueOf(true));
                                /* 399 */ ma.setQuantitereduite(ltt.getQuantitereduite());
                                /* 400 */ ma.setQuantitevirtuelle(Double.valueOf(0.0D));
                                /* 401 */ ma.setQuantitesecurite(ltt.getIdmagasinlot().getIdlot().getIdarticle().getQuantitesecurite());
                                /* 402 */ this.magasinarticleFacadeLocal.create(ma);
                            } else {
                                /* 404 */ ma.setQuantite(Double.valueOf(ma.getQuantite().doubleValue() + ltt.getQuantite().doubleValue()));
                                /* 405 */ ma.setQuantitemultiple(Double.valueOf(ma.getQuantitemultiple().doubleValue() + ltt.getQuantitemultiple().doubleValue()));
                                /* 406 */ ma.setQuantitereduite(Double.valueOf(ma.getQuantitereduite().doubleValue() + ltt.getQuantitereduite().doubleValue()));
                                /* 407 */ this.magasinarticleFacadeLocal.edit(ma);
                            }

                            /* 410 */ Magasinlot mlTemp1 = new Magasinlot();
                            /* 411 */ mlTemp1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                            /* 412 */ mlTemp1.setIdmagasinarticle(ma);
                            /* 413 */ mlTemp1.setIdlot(ltt.getIdmagasinlot().getIdlot());
                            /* 414 */ mlTemp1.setEtat(Boolean.valueOf(true));
                            /* 415 */ mlTemp1.setQuantite(ltt.getQuantite());
                            /* 416 */ mlTemp1.setUnite(ltt.getUnite());
                            /* 417 */ mlTemp1.setQuantitemultiple(ltt.getQuantitemultiple());
                            /* 418 */ mlTemp1.setQuantitereduite(ltt.getQuantitemultiple());
                            /* 419 */ mlTemp1.setQuantitevirtuelle(Double.valueOf(0.0D));

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
                    /* 446 */ this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
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
            /* 468 */ if (this.transfert != null) {
                /* 470 */ if (!Utilitaires.isAccess(Long.valueOf(43L))) {
                    /* 471 */ notifyError("acces_refuse");
                    /* 472 */ this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
                    /* 473 */ this.transfert = null;
                    /* 474 */ return;
                }

                /* 477 */ this.ut.begin();

                List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.transfert.getIdmvtstock().getIdmvtstock().longValue());
                for (Lignemvtstock l : lmvt) {
                    this.lignemvtstockFacadeLocal.remove(l);
                }
                Lignemvtstock l;
                /* 484 */ List<Lignetransfert> listLigneTransfert = this.lignetransfertFacadeLocal.findByIdTransfert(this.transfert.getIdtransfert().longValue());
                /* 485 */ for (Lignetransfert lt : listLigneTransfert) {
                    /* 486 */ this.lignetransfertFacadeLocal.remove(lt);

                    /* 488 */ Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(lt.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                    /* 489 */ maTemp.setQuantite(Double.valueOf(maTemp.getQuantite().doubleValue() + lt.getQuantite().doubleValue()));
                    /* 490 */ maTemp.setQuantitemultiple(Double.valueOf(maTemp.getQuantitemultiple().doubleValue() + lt.getQuantitemultiple().doubleValue()));
                    /* 491 */ maTemp.setQuantitereduite(Double.valueOf(maTemp.getQuantitereduite().doubleValue() + lt.getQuantitereduite().doubleValue()));
                    /* 492 */ this.magasinarticleFacadeLocal.edit(maTemp);

                    /* 494 */ Magasinlot mlTemp = this.magasinlotFacadeLocal.find(lt.getIdmagasinlot().getIdmagasinlot());
                    /* 495 */ mlTemp.setQuantite(Double.valueOf(mlTemp.getQuantite().doubleValue() + lt.getQuantite().doubleValue()));
                    /* 496 */ mlTemp.setQuantitemultiple(Double.valueOf(mlTemp.getQuantitemultiple().doubleValue() + lt.getQuantitemultiple().doubleValue()));
                    /* 497 */ mlTemp.setQuantitereduite(Double.valueOf(mlTemp.getQuantitereduite().doubleValue() + lt.getQuantitereduite().doubleValue()));
                    /* 498 */ this.magasinlotFacadeLocal.edit(mlTemp);

                    /* 500 */ Magasinlot ml = this.magasinlotFacadeLocal.findByIdmagasinIdlot(this.transfert.getIdmagasincible().intValue(), lt.getIdmagasinlot().getIdlot().getIdlot().longValue());
                    /* 501 */ if (ml != null) {
                        /* 503 */ Magasinarticle ma1 = ml.getIdmagasinarticle();

                        /* 505 */ ma1.setQuantite(Double.valueOf(ma1.getQuantite().doubleValue() - lt.getQuantite().doubleValue()));
                        /* 506 */ ma1.setQuantitemultiple(Double.valueOf(ma1.getQuantitemultiple().doubleValue() - lt.getQuantitemultiple().doubleValue()));
                        /* 507 */ ma1.setQuantitereduite(Double.valueOf(ma1.getQuantitereduite().doubleValue() - lt.getQuantitereduite().doubleValue()));
                        /* 508 */ this.magasinarticleFacadeLocal.edit(ma1);

                        /* 510 */ ml.setQuantite(Double.valueOf(ml.getQuantite().doubleValue() - lt.getQuantite().doubleValue()));
                        /* 511 */ ml.setQuantitemultiple(Double.valueOf(ml.getQuantitemultiple().doubleValue() - lt.getQuantitemultiple().doubleValue()));
                        /* 512 */ ml.setQuantitereduite(Double.valueOf(ml.getQuantitereduite().doubleValue() - lt.getQuantitereduite().doubleValue()));
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
            /* 557 */ if (!Utilitaires.isAccess(Long.valueOf(43L))) {
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
            resultat = Double.valueOf(resultat.doubleValue() + lt.getIdmagasinlot().getIdlot().getPrixunitaire().doubleValue() * lt.getQuantite().doubleValue() * lt.getUnite().doubleValue());
            ((Lignetransfert) lignetransferts.get(i)).setQuantitemultiple(Double.valueOf(((Lignetransfert) lignetransferts.get(i)).getQuantite().doubleValue() * lt.getUnite().doubleValue()));
            ((Lignetransfert) lignetransferts.get(i)).setQuantitereduite(Double.valueOf(((Lignetransfert) lignetransferts.get(i)).getQuantitemultiple().doubleValue() / lt.getIdmagasinlot().getIdlot().getIdarticle().getUnite().doubleValue()));
            i++;
        }
        return resultat;
    }

    public void updateTotal() {
        try {
            /* 605 */ this.total = calculTotal(this.lignetransferts);
            /* 606 */ this.transfert.setMontanttotal(this.total);
        } catch (Exception e) {
            /* 608 */ e.printStackTrace();
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
