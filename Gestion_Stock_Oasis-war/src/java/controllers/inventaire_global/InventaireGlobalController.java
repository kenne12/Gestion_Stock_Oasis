package controllers.inventaire_global;

import entities.Inventaire;
import entities.Ligneinventaire;
import entities.Lignemvtstock;
import entities.Lot;
import entities.Magasin;
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
public class InventaireGlobalController extends AbstractInventaireGlobalController
        implements Serializable {

    @PostConstruct
    private void init() {
    }

    public Boolean checkPeremption(Lot lot) {
        /*  49 */ return Boolean.valueOf(Utilitaires.checkPeremption(lot));
    }

    public void prepareCreate() {
        try {
            /*  55 */ if (!Utilitaires.isAccess(Long.valueOf(44L))) {
                /*  56 */ notifyError("acces_refuse");
                /*  57 */ return;
            }

            /*  60 */ this.mode = "Create";
            /*  61 */ this.valideBtn = this.routine.localizeMessage("valider");
            /*  62 */ this.editQuantite = false;

            /*  64 */ this.inventaire = new Inventaire();
            /*  65 */ this.inventaire.setDateinventaire(new Date());
            /*  66 */ this.inventaire.setCentral(Boolean.valueOf(false));
            /*  67 */ this.inventaire.setAllarticle(Boolean.valueOf(true));

            /*  69 */ this.ligneinventaires.clear();
            /*  70 */ this.ligneinventaires_1.clear();
            /*  71 */ this.articles.clear();
            /*  72 */ this.magasinarticles.clear();
            /*  73 */ this.magasinlots.clear();

            /*  75 */ this.magasin = new Magasin();

            /*  77 */ this.inventaire.setCode(Utilitaires.genererCodeInventaire("INV-", this.inventaireFacadeLocal.nextVal()));

            /*  81 */ RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            /*  83 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            /*  84 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreatePartial() {
        try {
            /*  91 */ if (!Utilitaires.isAccess(Long.valueOf(44L))) {
                /*  92 */ notifyError("acces_refuse");
                /*  93 */ return;
            }

            /*  96 */ this.mode = "Create";
            /*  97 */ this.valideBtn = ("" + this.routine.localizeMessage("valider"));
            /*  98 */ this.showSelectArticle = false;

            /* 100 */ this.inventaire = new Inventaire();
            /* 101 */ this.inventaire.setDateinventaire(new Date());

            /* 103 */ this.ligneinventaires.clear();
            /* 104 */ this.articles.clear();

            /* 106 */ String code = "INV-";
            /* 107 */ Long nextPayement = this.inventaireFacadeLocal.nextVal();
            /* 108 */ code = Utilitaires.genererCodeInventaire(code, nextPayement);
            /* 109 */ this.inventaire.setCode(code);

            /* 111 */ RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            /* 113 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            /* 114 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareAddArticle() {
        try {
            /* 120 */ if ("Create".equals(this.mode)) {
                /* 121 */ if (this.magasin.getIdmagasin().intValue() == 0) {
                    /* 122 */ this.lots = Utilitaires.filterNotPeremptLot(this.lotFacadeLocal.findAllRange());
                    /* 123 */ this.ligneinventaires_1.clear();
                    /* 124 */ this.selectedLots.clear();
                } else {
                    /* 126 */ this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinEtatIsTrue(this.magasin.getIdmagasin().intValue());
                    /* 127 */ this.ligneinventaires.clear();
                    /* 128 */ this.selectedMagasinlots.clear();
                }

                /* 131 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
                /* 132 */ return;
            }
            /* 134 */ this.selectedLots.clear();
            /* 135 */ this.articles.clear();
            /* 136 */ for (Ligneinventaire l : this.ligneinventaires) {
                /* 137 */ this.selectedLots.add(l.getIdlot());
            }
            /* 139 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            /* 141 */ notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            /* 148 */ if (this.inventaire == null) {
                /* 149 */ notifyError("not_row_selected");
                /* 150 */ return;
            }

            /* 153 */ if (!Utilitaires.isAccess(Long.valueOf(44L))) {
                /* 154 */ notifyError("acces_refuse");
                /* 155 */ this.inventaire = null;
                /* 156 */ return;
            }

            /* 159 */ if (this.inventaire.getEtat().booleanValue()) {
                /* 160 */ notifyError("inventaire_validee");
                /* 161 */ return;
            }

            /* 164 */ this.mode = "Edit";
            /* 165 */ this.editQuantite = false;
            /* 166 */ this.showSelectArticle = true;
            /* 167 */ this.magasin = this.inventaire.getIdmagasin();
            /* 168 */ this.valideBtn = ("" + this.routine.localizeMessage("valider"));
            /* 169 */ if (this.inventaire.getCentral().booleanValue()) /* 170 */ {
                this.ligneinventaires = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire().longValue());
            } else {
                /* 172 */ this.ligneinventaires_1 = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire().longValue());
            }

            /* 175 */ this.articles.clear();
            /* 176 */ if (!this.inventaire.getEtat().booleanValue()) {
                /* 177 */ this.showSelectArticle = false;
            }
            /* 179 */ RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            /* 181 */ notifyFail(e);
        }
    }

    public void prepareValidate() {
        try {
            /* 188 */ if (this.inventaire == null) {
                /* 189 */ notifyError("not_row_selected");
                /* 190 */ return;
            }

            /* 193 */ if (!Utilitaires.isAccess(Long.valueOf(45L))) {
                /* 194 */ notifyError("acces_refuse");
                /* 195 */ this.inventaire = null;
                /* 196 */ return;
            }

            /* 199 */ if (this.inventaire.getEtat().booleanValue()) {
                /* 200 */ notifyError("inventaire_validee");
                /* 201 */ return;
            }

            /* 204 */ this.mode = "Validate";
            /* 205 */ this.editQuantite = true;
            /* 206 */ this.showSelectArticle = true;
            /* 207 */ this.magasin = this.inventaire.getIdmagasin();
            /* 208 */ this.valideBtn = this.routine.localizeMessage("valider_inventaire");
            /* 209 */ if (this.inventaire.getCentral().booleanValue()) /* 210 */ {
                this.ligneinventaires = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire().longValue());
            } else {
                /* 212 */ this.ligneinventaires_1 = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire().longValue());
            }
            /* 214 */ this.articles.clear();
            /* 215 */ if (!this.inventaire.getEtat().booleanValue()) {
                /* 216 */ this.showSelectArticle = false;
            }
            /* 218 */ RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            /* 220 */ notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            /* 226 */ if (this.inventaire != null) {
                /* 227 */ this.ligneinventaires_1 = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire().longValue());
                /* 228 */ if (!this.ligneinventaires_1.isEmpty()) {
                    /* 229 */ RequestContext.getCurrentInstance().execute("PF('InventaireDetailDialog').show()");
                    /* 230 */ return;
                }
                /* 232 */ notifyError("liste_article_vide");
            } else {
                /* 235 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 238 */ notifyFail(e);
        }
    }

    public void updateData() {
        try {
            /* 244 */ if (this.magasin.getIdmagasin().intValue() == 0) {
                /* 245 */ this.inventaire.setCentral(Boolean.valueOf(true));
            } else {
                /* 247 */ this.inventaire.setCentral(Boolean.valueOf(false));
                /* 248 */ System.err.println("magasin non central");
            }
            /* 250 */ updateArticle();
        } catch (Exception e) {
            /* 252 */ e.printStackTrace();
        }
    }

    public void updateArticle() {
        try {
            /* 258 */ if (this.magasin.getIdmagasin().intValue() == 0) {
                /* 259 */ this.ligneinventaires_1.clear();
                /* 260 */ this.magasinlots.clear();
                /* 261 */ this.lots = Utilitaires.filterNotPeremptLot(this.lotFacadeLocal.findAllRangeEtatIsTrue());
                /* 262 */ insertLotToTable();
            } /* 264 */ else if (this.inventaire.getAllarticle().booleanValue()) {
                /* 265 */ this.lots.clear();
                /* 266 */ this.articles.clear();
                /* 267 */ this.ligneinventaires.clear();
                /* 268 */ insertMagasinLotToTable();
            } else {
                /* 270 */ this.ligneinventaires_1.clear();
            }
        } catch (Exception e) {
            /* 274 */ e.printStackTrace();
        }
    }

    private void insertLotToTable() {
        /* 279 */ for (Lot l : this.lots) /* 280 */ {
            if (!ifExistLot(this.ligneinventaires, l)) {
                /* 281 */ Ligneinventaire li = new Ligneinventaire();
                /* 282 */ li.setIdligneinventaire(Long.valueOf(0L));
                /* 283 */ li.setIdlot(l);
                /* 284 */ li.setQtetheorique(l.getQuantite());
                /* 285 */ li.setQtephysique(l.getQuantite());
                /* 286 */ li.setEcart(Double.valueOf(0.0D));
                /* 287 */ li.setObservation("-");
                /* 288 */ this.ligneinventaires.add(li);
            }
        }
    }

    private void insertMagasinLotToTable() {
        /* 294 */ this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinEtatIsTrue(this.magasin.getIdmagasin().intValue());
        /* 295 */ this.ligneinventaires_1.clear();
        /* 296 */ for (Magasinlot m : this.magasinlots) /* 297 */ {
            if (!ifExistLot(this.ligneinventaires_1, m)) {
                /* 298 */ Ligneinventaire li = new Ligneinventaire();
                /* 299 */ li.setIdligneinventaire(Long.valueOf(0L));
                /* 300 */ li.setIdlot(m.getIdlot());
                /* 301 */ li.setIdmagasinlot(m);
                /* 302 */ li.setQtephysique(m.getQuantitemultiple());
                /* 303 */ li.setQtetheorique(m.getQuantitemultiple());
                /* 304 */ li.setEcart(Double.valueOf(0.0D));
                /* 305 */ li.setObservation("-");
                /* 306 */ this.ligneinventaires_1.add(li);
            }
        }
    }

    public void create() {
        try {
            /* 313 */ if ("Create".equals(this.mode)) {
                /* 315 */ if (!this.ligneinventaires_1.isEmpty()) {
                    /* 317 */ this.ut.begin();

                    /* 319 */ this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    /* 320 */ this.mvtstock.setClient(" ");
                    /* 321 */ this.mvtstock.setFournisseur(" ");
                    /* 322 */ this.mvtstock.setMagasin(" ");
                    /* 323 */ this.mvtstock.setType(" ");
                    /* 324 */ this.mvtstock.setCode(Utilitaires.genererCodeStock("MVT", this.mvtstock.getIdmvtstock()));
                    /* 325 */ this.mvtstock.setDatemvt(this.inventaire.getDateinventaire());
                    /* 326 */ this.mvtstockFacadeLocal.create(this.mvtstock);

                    /* 328 */ this.inventaire.setIdinventaire(this.inventaireFacadeLocal.nextVal());
                    /* 329 */ this.inventaire.setEtat(Boolean.valueOf(false));
                    /* 330 */ this.inventaire.setIdmagasin(this.magasin);
                    /* 331 */ this.inventaire.setIdmvtstock(this.mvtstock);
                    /* 332 */ this.inventaireFacadeLocal.create(this.inventaire);

                    /* 334 */ if (this.magasin.getIdmagasin().intValue() != 0) /* 335 */ {
                        for (Ligneinventaire li : this.ligneinventaires_1) {
                            /* 336 */ li.setIdligneinventaire(this.ligneinventaireFacadeLocal.nextVal());
                            /* 337 */ li.setIdinventaire(this.inventaire);
                            /* 338 */ this.ligneinventaireFacadeLocal.create(li);
                        }
                    } else {
                        /* 341 */ for (Ligneinventaire li : this.ligneinventaires) {
                            /* 342 */ li.setIdligneinventaire(this.ligneinventaireFacadeLocal.nextVal());
                            /* 343 */ li.setIdinventaire(this.inventaire);
                            /* 344 */ this.ligneinventaireFacadeLocal.create(li);
                        }
                    }

                    /* 348 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());

                    /* 350 */ this.ut.commit();
                    /* 351 */ this.inventaire = null;
                    /* 352 */ this.ligneinventaires_1.clear();
                    /* 353 */ this.detail = (this.imprimer = Boolean.valueOf(true));

                    /* 355 */ notifySuccess();
                    /* 356 */ RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').hide()");
                } else {
                    /* 358 */ notifyError("liste_article_vide");
                }
                /* 360 */            } else if ("Edit".equals(this.mode)) {
                /* 361 */ if (this.inventaire != null) {
                    /* 363 */ this.ut.begin();
                    /* 364 */ this.inventaireFacadeLocal.edit(this.inventaire);

                    /* 366 */ if (!this.ligneinventaires.isEmpty()) {
                        /* 367 */ for (Ligneinventaire li : this.ligneinventaires_1) {
                            /* 368 */ this.ligneinventaireFacadeLocal.edit(li);
                        }
                    }

                    /* 372 */ this.ut.commit();
                    /* 373 */ this.inventaire = null;
                    /* 374 */ this.ligneinventaires_1.clear();
                    /* 375 */ this.detail = (this.imprimer = Boolean.valueOf(true));

                    /* 377 */ notifySuccess();
                    /* 378 */ RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').hide()");
                } else {
                    /* 381 */ notifyError("not_row_selected");
                }

            } /* 385 */ else if (this.inventaire != null) {
                /* 387 */ this.ut.begin();

                /* 389 */ this.inventaire.setEtat(Boolean.valueOf(true));
                /* 390 */ this.inventaireFacadeLocal.edit(this.inventaire);

                /* 392 */ if (this.inventaire.getCentral().equals(Boolean.valueOf(false))) {
                    /* 394 */ if (!this.ligneinventaires_1.isEmpty()) {
                        /* 395 */ for (Ligneinventaire li : this.ligneinventaires_1) {
                            /* 397 */ if (li.getEcart().doubleValue() == 0.0D) {
                                /* 398 */ li.setObservation("Normal");
                                /* 399 */                            } else if (li.getEcart().doubleValue() > 0.0D) {
                                /* 401 */ updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle().longValue(), li.getEcart().doubleValue(), "+");
                                /* 402 */ updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot().longValue(), li.getEcart().doubleValue(), "+");

                                /* 404 */ Lignemvtstock lmvts = new Lignemvtstock();
                                /* 405 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                                /* 406 */ lmvts.setIdmvtstock(this.inventaire.getIdmvtstock());
                                /* 407 */ lmvts.setIdlot(li.getIdlot());
                                /* 408 */ lmvts.setIdmagasinlot(li.getIdmagasinlot());
                                /* 409 */ lmvts.setClient(" ");
                                /* 410 */ lmvts.setFournisseur("ECART INVENTAIRE");
                                /* 411 */ lmvts.setQteentree(li.getEcart());
                                /* 412 */ lmvts.setQtesortie(Double.valueOf(0.0D));
                                /* 413 */ lmvts.setReste(li.getIdmagasinlot().getQuantitemultiple());
                                /* 414 */ lmvts.setType("ENTREE");
                                /* 415 */ this.lignemvtstockFacadeLocal.create(lmvts);
                            } else {
                                /* 418 */ updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle().longValue(), li.getEcart().doubleValue(), "-");
                                /* 419 */ updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot().longValue(), li.getEcart().doubleValue(), "-");

                                /* 421 */ Lignemvtstock lmvts = new Lignemvtstock();
                                /* 422 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                                /* 423 */ lmvts.setIdmvtstock(this.inventaire.getIdmvtstock());
                                /* 424 */ lmvts.setIdlot(li.getIdlot());
                                /* 425 */ lmvts.setClient(" ");
                                /* 426 */ lmvts.setFournisseur("ECART INVENTAIRE");
                                /* 427 */ lmvts.setQteentree(Double.valueOf(0.0D));
                                /* 428 */ lmvts.setQtesortie(Double.valueOf(Math.abs(li.getEcart().doubleValue())));
                                /* 429 */ lmvts.setReste(li.getIdlot().getQuantite());
                                /* 430 */ lmvts.setType("SORTIE");
                                /* 431 */ this.lignemvtstockFacadeLocal.create(lmvts);
                            }
                            /* 433 */ this.ligneinventaireFacadeLocal.edit(li);
                        }
                    }
                }

                /* 438 */ this.ut.commit();
                /* 439 */ this.inventaire = null;
                /* 440 */ this.ligneinventaires_1.clear();
                /* 441 */ this.detail = (this.imprimer = Boolean.valueOf(true));

                /* 443 */ notifySuccess();
                /* 444 */ RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').hide()");
            } else {
                /* 446 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 450 */ notifyFail(e);
        }
    }

    private void updateMagasinArticle(long idmagasinArticle, double ecart, String signe) {
        /* 455 */ Magasinarticle magasinarticle = this.magasinarticleFacadeLocal.find(Long.valueOf(idmagasinArticle));
        /* 456 */ if (signe.equals("-")) {
            /* 457 */ double vabs = Math.abs(ecart);
            /* 458 */ magasinarticle.setQuantitemultiple(Double.valueOf(magasinarticle.getQuantitemultiple().doubleValue() - vabs));
            /* 459 */ magasinarticle.setQuantitereduite(Double.valueOf(magasinarticle.getQuantitereduite().doubleValue() - vabs));
            /* 460 */ magasinarticle.setQuantite(Double.valueOf(magasinarticle.getQuantite().doubleValue() - vabs));
        } else {
            /* 462 */ magasinarticle.setQuantitemultiple(Double.valueOf(magasinarticle.getQuantitemultiple().doubleValue() + ecart));
            /* 463 */ magasinarticle.setQuantitereduite(Double.valueOf(magasinarticle.getQuantitereduite().doubleValue() + ecart));
            /* 464 */ magasinarticle.setQuantite(Double.valueOf(magasinarticle.getQuantite().doubleValue() + ecart));
        }
        /* 466 */ this.magasinarticleFacadeLocal.edit(magasinarticle);
    }

    public void updateMagasinLot(long idmagasinLot, double ecart, String signe) {
        /* 470 */ Magasinlot magasinlot = this.magasinlotFacadeLocal.find(Long.valueOf(idmagasinLot));
        /* 471 */ if (signe.equals("+")) {
            /* 472 */ magasinlot.setQuantitemultiple(Double.valueOf(magasinlot.getQuantitemultiple().doubleValue() + ecart));
            /* 473 */ magasinlot.setQuantitereduite(Double.valueOf(magasinlot.getQuantitereduite().doubleValue() + ecart));
            /* 474 */ magasinlot.setQuantite(Double.valueOf(magasinlot.getQuantite().doubleValue() + ecart));
        } else {
            /* 476 */ double vabs = Math.abs(ecart);
            /* 477 */ magasinlot.setQuantitemultiple(Double.valueOf(magasinlot.getQuantitemultiple().doubleValue() - vabs));
            /* 478 */ magasinlot.setQuantitereduite(Double.valueOf(magasinlot.getQuantitereduite().doubleValue() - vabs));
            /* 479 */ magasinlot.setQuantite(Double.valueOf(magasinlot.getQuantite().doubleValue() - vabs));
        }
        try {
            /* 483 */ if ((magasinlot.getIdlot().getDateperemption().after(new Date())) || (magasinlot.getIdlot().getDateperemption().equals(new Date()))) /* 484 */ {
                magasinlot.setEtat(Boolean.valueOf(false));
            }
        } catch (Exception localException) {
        }
        /* 488 */ this.magasinlotFacadeLocal.edit(magasinlot);
    }

    public void delete() {
        try {
            /* 494 */ if (this.inventaire == null) {
                /* 495 */ notifyError("not_row_selected");
                /* 496 */ return;
            }

            /* 499 */ if (!Utilitaires.isAccess(Long.valueOf(44L))) {
                /* 500 */ notifyError("acces_refuse");
                /* 501 */ this.detail = (this.imprimer = Boolean.valueOf(true));
                /* 502 */ this.inventaire = null;
                /* 503 */ return;
            }

            /* 506 */ if (this.inventaire.getEtat().booleanValue()) {
                /* 507 */ notifyError("inventaire_validee");
                /* 508 */ this.detail = (this.imprimer = Boolean.valueOf(true));
                /* 509 */ this.inventaire = null;
                /* 510 */ return;
            }

            /* 513 */ this.ut.begin();

            /* 515 */ this.lignemvtstockFacadeLocal.deleteByIdmvt(this.inventaire.getIdmvtstock().getIdmvtstock().longValue());
            /* 516 */ this.ligneinventaireFacadeLocal.removeByIdInventaire(this.inventaire.getIdinventaire().longValue());
            /* 517 */ this.inventaireFacadeLocal.remove(this.inventaire);
            /* 518 */ this.mvtstockFacadeLocal.remove(this.inventaire.getIdmvtstock());

            /* 520 */ this.ut.commit();
            /* 521 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());
            /* 522 */ this.inventaire = null;
            /* 523 */ this.detail = (this.imprimer = Boolean.valueOf(true));
            /* 524 */ notifySuccess();
        } catch (Exception e) {
            /* 526 */ notifyFail(e);
        }
    }

    public void annuler() {
        try {
            /* 533 */ if (this.inventaire == null) {
                /* 534 */ this.detail = (this.imprimer = Boolean.valueOf(true));
                /* 535 */ notifyError("not_row_selected");
                /* 536 */ return;
            }

            /* 539 */ if (!Utilitaires.isAccess(Long.valueOf(45L))) {
                /* 540 */ this.detail = (this.imprimer = Boolean.valueOf(true));
                /* 541 */ notifyError("acces_refuse");
                /* 542 */ this.inventaire = null;
                /* 543 */ return;
            }

            /* 546 */ if (!this.inventaire.getEtat().booleanValue()) {
                /* 547 */ notifyError("inventaire_non_validee");
                /* 548 */ return;
            }

            /* 551 */ this.ut.begin();

            /* 553 */ List<Ligneinventaire> temp = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire().longValue());
            /* 554 */ if (!temp.isEmpty()) {
                /* 555 */ for (Ligneinventaire li : temp) {
                    /* 556 */ if (li.getEcart().doubleValue() > 0.0D) {
                        /* 557 */ updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle().longValue(), li.getEcart().doubleValue(), "-");
                        /* 558 */ updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot().longValue(), li.getEcart().doubleValue(), "-");
                    } else {
                        /* 560 */ updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle().longValue(), li.getEcart().doubleValue(), "+");
                        /* 561 */ updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot().longValue(), li.getEcart().doubleValue(), "+");
                    }
                }
            }
            /* 565 */ this.inventaire.setEtat(Boolean.valueOf(false));
            /* 566 */ this.inventaireFacadeLocal.edit(this.inventaire);

            /* 568 */ this.ut.commit();
            /* 569 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de la validation l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());
            /* 570 */ this.inventaire = null;
            /* 571 */ this.detail = (this.supprimer = this.imprimer = Boolean.valueOf(true));
            /* 572 */ notifySuccess();
        } catch (Exception e) {
            /* 575 */ notifyFail(e);
        }
    }

    public void initPrinter(Inventaire i) {
        /* 580 */ this.inventaire = i;
        /* 581 */ print();
    }

    public void initEdit(Inventaire i) {
        /* 585 */ this.inventaire = i;
        /* 586 */ prepareEdit();
    }

    public void initView(Inventaire i) {
        /* 590 */ this.inventaire = i;
        /* 591 */ prepareview();
    }

    public void initDelete(Inventaire i) {
        /* 595 */ this.inventaire = i;
        /* 596 */ delete();
    }

    public void print() {
        try {
            /* 601 */ if (!Utilitaires.isAccess(Long.valueOf(44L))) {
                /* 602 */ notifyError("acces_refuse");
                /* 603 */ this.inventaire = null;
                /* 604 */ return;
            }

            /* 607 */ if (this.inventaire != null) {
                /* 612 */ Map paramp = new HashMap();
                /* 613 */ paramp.put("idinventaire", this.inventaire.getIdinventaire());
                /* 614 */ Printer.print("/reports/ireport/inventaire.jasper", paramp);
                /* 615 */ RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                /* 617 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 620 */ notifyFail(e);
        }
    }

    public void addProduit() {
        try {
            /* 626 */ if ((this.magasin.getIdmagasin() != null) && (this.magasin.getIdmagasin().intValue() != 0)) {
                /* 627 */ for (Magasinlot ml : this.selectedMagasinlots) {
                    /* 628 */ if (!ifExistLot(this.ligneinventaires_1, ml)) {
                        /* 629 */ Ligneinventaire li = new Ligneinventaire();
                        /* 630 */ li.setIdligneinventaire(Long.valueOf(0L));
                        /* 631 */ li.setIdlot(ml.getIdlot());
                        /* 632 */ li.setIdmagasinlot(ml);
                        /* 633 */ li.setQtetheorique(ml.getQuantitemultiple());
                        /* 634 */ li.setQtephysique(ml.getQuantitemultiple());
                        /* 635 */ li.setEcart(Double.valueOf(0.0D));
                        /* 636 */ li.setObservation("-");
                        /* 637 */ this.ligneinventaires_1.add(li);
                    }
                }
                /* 640 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                /* 641 */ return;
            }
            /* 643 */ for (Lot l : this.selectedLots) {
                /* 644 */ if (!ifExistLot(this.ligneinventaires, l)) {
                    /* 645 */ Ligneinventaire li = new Ligneinventaire();
                    /* 646 */ li.setIdligneinventaire(Long.valueOf(0L));
                    /* 647 */ li.setIdlot(l);
                    /* 648 */ li.setQtetheorique(l.getQuantite());
                    /* 649 */ li.setQtephysique(l.getQuantite());
                    /* 650 */ li.setEcart(Double.valueOf(0.0D));
                    /* 651 */ li.setObservation("-");
                    /* 652 */ this.ligneinventaires.add(li);
                }
            }
            /* 655 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
        } catch (Exception e) {
            /* 657 */ notifyFail(e);
        }
    }

    public boolean ifExistLot(List<Ligneinventaire> ligneinventaires, Lot lot) {
        /* 662 */ boolean result = false;
        /* 663 */ for (Ligneinventaire l : ligneinventaires) {
            /* 664 */ if (l.getIdlot().equals(lot)) {
                /* 665 */ result = true;
                /* 666 */ break;
            }
        }
        /* 669 */ return result;
    }

    public boolean ifExistLot(List<Ligneinventaire> ligneinventaires, Magasinlot magasinlot) {
        /* 673 */ boolean result = false;
        /* 674 */ for (Ligneinventaire l : ligneinventaires) {
            /* 675 */ if (l.getIdmagasinlot().equals(magasinlot)) {
                /* 676 */ result = true;
                /* 677 */ break;
            }
        }
        /* 680 */ return result;
    }

    public void removeProduit(int index) {
        try {
            /* 685 */ boolean trouve = false;
            /* 686 */ this.ut.begin();

            /* 688 */ Ligneinventaire li = (Ligneinventaire) this.ligneinventaires.get(index);
            /* 689 */ if (li.getIdligneinventaire().longValue() != 0L) {
                /* 690 */ trouve = true;
                /* 691 */ this.ligneinventaireFacadeLocal.remove(li);
                /* 692 */ if (li.getEcart().doubleValue() < 0.0D) {
                    /* 693 */ updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle().longValue(), li.getEcart().doubleValue(), "+");
                    /* 694 */ updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot().longValue(), li.getEcart().doubleValue(), "+");
                    /* 695 */                } else if (li.getEcart().doubleValue() > 0.0D) {
                    /* 696 */ updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle().longValue(), li.getEcart().doubleValue(), "-");
                    /* 697 */ updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot().longValue(), li.getEcart().doubleValue(), "-");
                }
                /* 699 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du lot : " + li.getIdlot().getNumero() + " dans l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());
            }
            /* 701 */ this.ligneinventaires.remove(index);
            /* 702 */ this.ut.commit();
            /* 703 */ JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            /* 705 */ e.printStackTrace();
            /* 706 */ JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public void updateEcart(int index) {
        /* 711 */ if (this.magasin.getIdmagasin().intValue() != 0) {
            /* 712 */ updateEcartLine(index);
            /* 713 */ return;
        }
        /* 715 */ updateEcartLine(index);
    }

    private void updateEcartLine(int index) {
        try {
            try {
                /* 721 */ ((Ligneinventaire) this.ligneinventaires_1.get(index)).setEcart(Double.valueOf(((Ligneinventaire) this.ligneinventaires_1.get(index)).getQtephysique().doubleValue() - ((Ligneinventaire) this.ligneinventaires_1.get(index)).getQtetheorique().doubleValue()));
            } catch (Exception e) {
                /* 723 */ ((Ligneinventaire) this.ligneinventaires_1.get(index)).setEcart(Double.valueOf(0.0D));
                /* 724 */ ((Ligneinventaire) this.ligneinventaires_1.get(index)).setQtephysique(((Ligneinventaire) this.ligneinventaires_1.get(index)).getIdmagasinlot().getQuantitemultiple());
            }
        } catch (Exception e) {
            /* 727 */ e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        /* 732 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 733 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 737 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 738 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 739 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 743 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 744 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 745 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.inventaire_global.InventaireGlobalController
 * JD-Core Version:    0.6.2
 */
