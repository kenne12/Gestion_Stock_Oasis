package controllers.traitement;

import entities.Demande;
import entities.Lignedemande;
import entities.Lignelivraisonclient;
import entities.Lignemvtstock;
import entities.Livraisonclient;
import entities.Lot;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Personnel;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
public class TraitementController extends AbstractTraitementController
        implements Serializable {

    @PostConstruct
    private void init() {
        /*  48 */ this.unites = this.uniteFacadeLocal.findAllRange();
    }

    public Boolean checkPeremption(Lot lot) {
        /*  52 */ return Boolean.valueOf(Utilitaires.checkPeremption(lot));
    }

    public void updateCalculTva() {
        /*  56 */ updateTotal();
    }

    public void prepareCreate() {
        try {
            /*  62 */ if (!Utilitaires.isAccess(Long.valueOf(41L))) {
                /*  63 */ notifyError("acces_refuse");
                /*  64 */ return;
            }

            /*  67 */ this.mode = "Create";

            /*  69 */ this.livraisonclient = new Livraisonclient();
            /*  70 */ this.personnel = new Personnel();

            /*  72 */ this.livraisonclient.setMontant(Double.valueOf(0.0D));

            /*  74 */ this.lignelivraisonclients.clear();
            /*  75 */ this.payement_credit = false;

            /*  78 */ this.lignedemandes.clear();
            /*  79 */ this.showSelectorCommand = Boolean.valueOf(false);

            /*  81 */ this.total = Double.valueOf(0.0D);
        } catch (Exception e) {
            /*  83 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            /*  84 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        try {
            /*  90 */ this.demande = new Demande();
            /*  91 */ this.demandes = this.demandeFacadeLocal.findByValidee(false);
            /*  92 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            /*  94 */ notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            /* 101 */ if (this.livraisonclient == null) {
                /* 102 */ notifyError("not_row_selected");
                /* 103 */ return;
            }

            /* 106 */ if (!this.livraisonclient.getLivraisondirecte().booleanValue()) {
                /* 108 */ this.showSelectorCommand = Boolean.valueOf(true);
                /* 109 */ this.demande = this.livraisonclient.getIddemande();

                /* 111 */ if (this.demande.getValidee().booleanValue()) {
                    /* 112 */ notifyError("demande_deja_livree");
                    /* 113 */ return;
                }

                /* 116 */ if (!Utilitaires.isAccess(Long.valueOf(41L))) {
                    /* 117 */ notifyError("acces_refuse");
                    /* 118 */ this.demande = null;
                    /* 119 */ return;
                }

                /* 122 */ this.mode = "Edit";

                /* 124 */ this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande().longValue());
                /* 125 */ this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient().longValue());
                /* 126 */ this.personnel = this.livraisonclient.getIddemande().getIdpersonnel();
                /* 127 */ this.total = this.livraisonclient.getMontant();
                /* 128 */ RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').show()");
            } else {
                /* 130 */ notifyError("vente_directe");
            }
        } catch (Exception e) {
            /* 133 */ notifyFail(e);
        }
    }

    public void afficherDemande() {
        /* 138 */ this.demandes_1 = this.demandeFacadeLocal.findAllRange();
    }

    public void prepareview() {
        try {
            /* 143 */ if (this.livraisonclient != null) {
                /* 144 */ this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient().longValue());
                /* 145 */ if (!this.lignelivraisonclients.isEmpty()) {
                    /* 146 */ RequestContext.getCurrentInstance().execute("PF('LivraisonClientDetailDialog').show()");
                    /* 147 */ return;
                }
                /* 149 */ notifyError("liste_article_vide");
            } else {
                /* 151 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 154 */ notifyFail(e);
        }
    }

    public void prepareviewD() {
        try {
            /* 160 */ if (this.demande != null) {
                /* 161 */ this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande().longValue());
                /* 162 */ if (!this.lignedemandes.isEmpty()) {
                    /* 163 */ RequestContext.getCurrentInstance().execute("PF('DemandeDetailDialog').show()");
                    /* 164 */ return;
                }
                /* 166 */ notifyError("liste_article_vide");
            } else {
                /* 169 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 172 */ notifyFail(e);
        }
    }

    public void selectCommande() {
        try {
            /* 178 */ if (this.demande != null) {
                /* 180 */ this.lignelivraisonclients.clear();

                /* 182 */ this.personnel = this.demande.getIdpersonnel();
                /* 183 */ this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande().longValue());

                /* 185 */ this.demande.setDateeffectlivraison(new Date());
                /* 186 */ int conteur = 0;
                /* 187 */ Double pourcentage = Double.valueOf(0.0D);
                /* 188 */ for (Lignedemande lcc : this.lignedemandes) {
                    /* 190 */ boolean suffisant = false;
                    /* 191 */ Double qteDemandee = lcc.getQuantitemultiple();
                    /* 192 */ Double qteReste = lcc.getQuantitemultiple();

                    /* 194 */ List<Magasinlot> lotTemp = this.magasinlotFacadeLocal.findByArticleIsavailable(lcc.getIdmagasinarticle().getIdmagasin().getIdmagasin(), lcc.getIdmagasinarticle().getIdarticle().getIdarticle());

                    /* 196 */ Double sommeQte = Double.valueOf(0.0D);

                    //Lignelivraisonclient c;
/* 198 */ if (!lotTemp.isEmpty()) {
                        /* 200 */ Magasinlot lSearch = (Magasinlot) lotTemp.get(0);
                        /* 201 */ if (lSearch != null) {
                            /* 203 */ if (lSearch.getQuantitemultiple().doubleValue() > 0.0D) {
                                /* 205 */ Lignelivraisonclient c = new Lignelivraisonclient();
                                /* 206 */ c.setIdlignelivraisonclient(Long.valueOf(0L));
                                /* 207 */ c.setIdmagasinlot(lSearch);
                                /* 208 */ c.setIdlot(lSearch.getIdlot());
                                /* 209 */ c.setMontant(lcc.getMontant());
                                /* 210 */ c.setIdunite(lcc.getIdunite());

                                /* 212 */ if (lSearch.getQuantitemultiple().doubleValue() >= lcc.getQuantitemultiple().doubleValue()) {
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
                                /* 230 */ suffisant = false;
                            }
                        }

                        /* 234 */ if (!suffisant) {
                            /* 235 */ for (Magasinlot l : lotTemp) {
                                /* 237 */ if (!l.getIdlot().equals(lotTemp)) {
                                    /* 239 */ if (l.getQuantitemultiple().doubleValue() > 0.0D) {
                                        /* 241 */ Lignelivraisonclient c = new Lignelivraisonclient();
                                        /* 242 */ c.setIdlignelivraisonclient(Long.valueOf(0L));
                                        /* 243 */ c.setIdmagasinlot(l);
                                        /* 244 */ c.setIdlot(l.getIdlot());
                                        /* 245 */ c.setMontant(lcc.getMontant());
                                        /* 246 */ c.setIdunite(lcc.getIdunite());

                                        /* 248 */ if (l.getQuantitemultiple().doubleValue() >= qteReste.doubleValue()) {
                                            /* 249 */ c.setQuantitemultiple(qteReste);
                                            /* 250 */ c.setQuantite(Double.valueOf(qteReste.doubleValue() / l.getUnite().doubleValue()));
                                            /* 251 */ c.setQuantitereduite(Double.valueOf(l.getQuantitemultiple().doubleValue() / l.getIdlot().getIdarticle().getUnite().doubleValue()));
                                            /* 252 */ this.lignelivraisonclients.add(c);
                                            /* 253 */ sommeQte = Double.valueOf(sommeQte.doubleValue() + qteReste.doubleValue());
                                            /* 254 */ suffisant = true;
                                            /* 255 */ break;
                                        }
                                        /* 257 */ qteReste = Double.valueOf(qteReste.doubleValue() - l.getQuantitemultiple().doubleValue());
                                        /* 258 */ c.setQuantitemultiple(l.getQuantitemultiple());
                                        /* 259 */ c.setQuantite(Double.valueOf(l.getQuantitemultiple().doubleValue() / l.getUnite().doubleValue()));
                                        /* 260 */ c.setQuantitereduite(Double.valueOf(l.getQuantitemultiple().doubleValue() / l.getIdlot().getIdarticle().getUnite().doubleValue()));
                                        /* 261 */ this.lignelivraisonclients.add(c);
                                        /* 262 */ sommeQte = Double.valueOf(sommeQte.doubleValue() + l.getQuantitemultiple().doubleValue());
                                        /* 263 */ suffisant = false;
                                    } else {
                                        /* 266 */ suffisant = false;
                                    }
                                }
                            }
                        }
                    }
                    /* 272 */ Utilitaires.arrondiNDecimales(sommeQte.doubleValue() / qteDemandee.doubleValue() * 100.0D, 2);
                    /* 273 */ ((Lignedemande) this.lignedemandes.get(conteur)).setTauxsatisfaction(Utilitaires.arrondiNDecimales(sommeQte.doubleValue() / qteDemandee.doubleValue() * 100.0D, 2));
                    /* 274 */ pourcentage = Double.valueOf(pourcentage.doubleValue() + Utilitaires.arrondiNDecimales(sommeQte.doubleValue() / qteDemandee.doubleValue() * 100.0D, 2).doubleValue());
                    /* 275 */ conteur++;
                }
                /* 277 */ this.demande.setTauxsatisfaction(pourcentage);
                /* 278 */ updateTotal();
                /* 279 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            /* 282 */ e.printStackTrace();
        }
    }

    private Lignedemande searchByMagasinLotInLigneDemande(Magasinlot ml) {
        /* 287 */ for (Lignedemande ld : this.lignedemandes) {
            /* 288 */ if (ld.getIdmagasinarticle().equals(ml.getIdmagasinarticle())) {
                /* 289 */ return ld;
            }
        }
        /* 292 */ return null;
    }

    public void create() {
        try {
            /* 297 */ if ("Create".equals(this.mode)) {
                /* 299 */ if (!this.lignelivraisonclients.isEmpty()) {
                    /* 301 */ updateTotal();

                    /* 303 */ for (Lignedemande ld : this.lignedemandes) {
                        /* 305 */ Double somme = Double.valueOf(0.0D);
                        /* 306 */ for (Lignelivraisonclient llc : this.lignelivraisonclients) {
                            /* 307 */ if ((ld.getIdmagasinarticle().getIdarticle().equals(llc.getIdlot().getIdarticle()))
                                    && /* 308 */ (ld.getIdmagasinarticle().getIdmagasin().equals(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasin()))) {
                                /* 309 */ somme = Double.valueOf(somme.doubleValue() + llc.getQuantitemultiple().doubleValue());
                            }

                        }

                        /* 314 */ if (somme.doubleValue() > ld.getQuantitemultiple().doubleValue()) {
                            /* 315 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                            /* 316 */ JsfUtil.addErrorMessage("Les quantités accordées sont superieurs à celles demandées");
                            /* 317 */ return;
                        }

                        /* 320 */ if (somme.doubleValue() > ld.getIdmagasinarticle().getQuantitemultiple().doubleValue()) {
                            /* 321 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                            /* 322 */ JsfUtil.addErrorMessage("Les quantités accordées sont superieurs à celles disponible en stock");
                            /* 323 */ return;
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
                    /* 337 */ this.mvtstock.setClient(this.demande.getIdpersonnel().getNom() + " " + this.demande.getIdpersonnel().getPrenom());
                    /* 338 */ this.mvtstock.setFournisseur(" ");
                    /* 339 */ this.mvtstock.setMagasin(" ");
                    /* 340 */ this.mvtstock.setType(" ");
                    /* 341 */ this.mvtstockFacadeLocal.create(this.mvtstock);

                    /* 343 */ String code = "LIV";
                    /* 344 */ this.livraisonclient.setIdlivraisonclient(this.livraisonclientFacadeLocal.nextVal());
                    /* 345 */ code = Utilitaires.genererCodeDemande(code, this.livraisonclient.getIdlivraisonclient());
                    /* 346 */ this.livraisonclient.setCode(code);
                    /* 347 */ this.livraisonclient.setMontant(this.total);
                    /* 348 */ this.livraisonclient.setLivraisondirecte(Boolean.valueOf(false));
                    /* 349 */ this.livraisonclient.setDatelivraison(this.demande.getDateeffectlivraison());
                    /* 350 */ this.livraisonclient.setIddemande(this.demande);
                    /* 351 */ this.livraisonclient.setIdpersonnel(this.demande.getIdpersonnel());
                    /* 352 */ this.livraisonclient.setIdmvtstock(this.mvtstock);
                    /* 353 */ this.livraisonclientFacadeLocal.create(this.livraisonclient);

                    /* 355 */ for (Lignelivraisonclient llc : this.lignelivraisonclients) {
                        /* 356 */ llc.setIdlignelivraisonclient(this.lignelivraisonclientFacadeLocal.nextVal());
                        /* 357 */ llc.setIdlivraisonclient(this.livraisonclient);
                        /* 358 */ this.lignelivraisonclientFacadeLocal.create(llc);

                        /* 360 */ Lignedemande ldTemp = searchByMagasinLotInLigneDemande(llc.getIdmagasinlot());

                        /* 362 */ Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        /* 363 */ maTemp.setQuantite(Double.valueOf(maTemp.getQuantite().doubleValue() - llc.getQuantite().doubleValue()));
                        /* 364 */ maTemp.setQuantitemultiple(Double.valueOf(maTemp.getQuantitemultiple().doubleValue() - llc.getQuantitemultiple().doubleValue()));
                        /* 365 */ maTemp.setQuantitereduite(Double.valueOf(maTemp.getQuantitereduite().doubleValue() - llc.getQuantitereduite().doubleValue()));
                        /* 366 */ maTemp.setQuantitevirtuelle(Double.valueOf(maTemp.getQuantitevirtuelle().doubleValue() - ldTemp.getQuantitemultiple().doubleValue()));
                        /* 367 */ this.magasinarticleFacadeLocal.edit(maTemp);

                        /* 369 */ Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot());
                        /* 370 */ mlTemp.setQuantite(Double.valueOf(mlTemp.getQuantite().doubleValue() - llc.getQuantite().doubleValue()));
                        /* 371 */ mlTemp.setQuantitemultiple(Double.valueOf(mlTemp.getQuantitemultiple().doubleValue() - llc.getQuantitemultiple().doubleValue()));
                        /* 372 */ mlTemp.setQuantitereduite(Double.valueOf(mlTemp.getQuantitereduite().doubleValue() - llc.getQuantitereduite().doubleValue()));
                        /* 373 */ this.magasinlotFacadeLocal.edit(mlTemp);

                        /* 375 */ Lignemvtstock lmvts = new Lignemvtstock();
                        /* 376 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        /* 377 */ lmvts.setIdmvtstock(this.mvtstock);
                        /* 378 */ lmvts.setIdlot(llc.getIdmagasinlot().getIdlot());
                        /* 379 */ lmvts.setQtesortie(llc.getQuantitemultiple());
                        /* 380 */ lmvts.setQteentree(Double.valueOf(0.0D));
                        /* 381 */ lmvts.setReste(mlTemp.getQuantitemultiple());
                        /* 382 */ lmvts.setIdmagasinlot(llc.getIdmagasinlot());
                        /* 383 */ lmvts.setUnite(llc.getUnite());
                        /* 384 */ lmvts.setMagasin(" ");
                        /* 385 */ lmvts.setClient(this.demande.getIdpersonnel().getNom() + " " + this.demande.getIdpersonnel().getPrenom());
                        /* 386 */ lmvts.setFournisseur(" ");
                        /* 387 */ lmvts.setType("SORTIE");
                        /* 388 */ this.lignemvtstockFacadeLocal.create(lmvts);
                    }
                    /* 390 */ this.demande.setValidee(Boolean.valueOf(true));
                    /* 391 */ this.demandeFacadeLocal.edit(this.demande);
                    /* 392 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Validation de la demande  N° : " + this.livraisonclient.getIddemande().getCode(), SessionMBean.getUserAccount());

                    /* 394 */ this.ut.commit();
                    /* 395 */ this.demande = new Demande();
                    /* 396 */ this.lignedemandes.clear();
                    /* 397 */ this.lignelivraisonclients.clear();
                    /* 398 */ this.detail = (this.supprimer = this.modifier = this.imprimer = this.imprimer2 = Boolean.valueOf(true));
                    /* 399 */ JsfUtil.addSuccessMessage(message);

                    /* 401 */ notifySuccess();
                    /* 402 */ RequestContext.getCurrentInstance().execute("PF('LivraisonClientCreateDialog').hide()");
                    /* 403 */ this.demandes_1 = this.demandeFacadeLocal.findAllRange();
                } else {
                    /* 405 */ notifyError("liste_article_vide");
                }
            } /* 408 */ else if (this.livraisonclient != null) {
                /* 410 */ this.ut.commit();
                /* 411 */ this.lignedemandes.clear();
                /* 412 */ this.demandes.clear();
                /* 413 */ this.demande = new Demande();
                /* 414 */ this.livraisonclient = null;
                /* 415 */ this.supprimer = (this.modifier = this.imprimer = Boolean.valueOf(true));

                /* 417 */ notifySuccess();
                /* 418 */ RequestContext.getCurrentInstance().execute("PF('LignelivraisonclientCreateDialog').hide()");
            } else {
                /* 420 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 424 */ e.printStackTrace();
            /* 425 */ notifyFail(e);
        }
    }

    public void delete() {
        try {
            /* 432 */ this.livraisonclient = this.livraisonclientFacadeLocal.findByIddemande(this.demande.getIddemande());

            /* 434 */ if (this.livraisonclient != null) {
                /* 436 */ if (!this.livraisonclient.getLivraisondirecte().booleanValue()) {
                    /* 438 */ if (!Utilitaires.isAccess(41L)) {
                        /* 439 */ notifyError("acces_refuse");
                        /* 440 */ this.detail = this.supprimer = this.modifier = this.imprimer = true;
                        /* 441 */ this.livraisonclient = null;
                        /* 442 */ return;
                    }

                    /* 445 */ this.ut.begin();

                    /* 447 */ List<Lignelivraisonclient> temp = this.lignelivraisonclientFacadeLocal.findByIdlivraisonclient(this.livraisonclient.getIdlivraisonclient().longValue());

                    /* 448 */ if (!temp.isEmpty()) /* 449 */ {
                        for (Lignelivraisonclient llc : temp) {

                            /* 451 */ llc.setIdmagasinlot(this.magasinlotFacadeLocal.find(llc.getIdmagasinlot().getIdmagasinlot()));

                            /* 453 */ llc.getIdmagasinlot().setQuantite(Double.valueOf(llc.getIdmagasinlot().getQuantite().doubleValue() + llc.getQuantite().doubleValue()));
                            /* 454 */ llc.getIdmagasinlot().setQuantitemultiple(Double.valueOf(llc.getIdmagasinlot().getQuantitemultiple().doubleValue() + llc.getQuantitemultiple().doubleValue()));
                            /* 455 */ llc.getIdmagasinlot().setQuantitereduite(Double.valueOf(llc.getIdmagasinlot().getQuantitereduite().doubleValue() + llc.getQuantitereduite().doubleValue()));
                            /* 456 */ llc.getIdmagasinlot().setQuantitevirtuelle(Double.valueOf(llc.getIdmagasinlot().getQuantitevirtuelle().doubleValue() + llc.getQuantitemultiple().doubleValue()));

                            /* 458 */ llc.getIdmagasinlot().getIdmagasinarticle().setQuantite(Double.valueOf(llc.getIdmagasinlot().getIdmagasinarticle().getQuantite().doubleValue() + llc.getQuantite().doubleValue()));
                            /* 459 */ llc.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple(Double.valueOf(llc.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple().doubleValue() + llc.getQuantitemultiple().doubleValue()));
                            /* 460 */ llc.getIdmagasinlot().getIdmagasinarticle().setQuantitereduite(Double.valueOf(llc.getIdmagasinlot().getIdmagasinarticle().getQuantitereduite().doubleValue() + llc.getQuantitereduite().doubleValue()));

                            /* 463 */ this.magasinlotFacadeLocal.edit(llc.getIdmagasinlot());
                            /* 464 */ this.magasinarticleFacadeLocal.edit(llc.getIdmagasinlot().getIdmagasinarticle());

                            /* 466 */ this.lignelivraisonclientFacadeLocal.remove(llc);
                        }
                    }
                    Lignelivraisonclient llc;
                    /* 469 */ this.livraisonclientFacadeLocal.remove(this.livraisonclient);

                    /* 471 */ this.demande = this.livraisonclient.getIddemande();

                    /* 473 */ this.demande.setValidee(Boolean.valueOf(false));
                    /* 474 */ this.demande.setTauxsatisfaction(Double.valueOf(0.0D));
                    /* 475 */ this.demandeFacadeLocal.edit(this.demande);

                    /* 477 */ List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.livraisonclient.getIdmvtstock().getIdmvtstock().longValue());
                    /* 478 */ for (Lignemvtstock l : lmvt) {
                        /* 479 */ this.lignemvtstockFacadeLocal.remove(l);
                    }

                    /* 482 */ this.mvtstockFacadeLocal.remove(this.livraisonclient.getIdmvtstock());

                    /* 484 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de validation de la demande : " + this.livraisonclient.getCode(), SessionMBean.getUserAccount());
                    /* 485 */ this.ut.commit();

                    /* 487 */ this.livraisonclient = null;
                    /* 488 */ this.demande = null;
                    /* 489 */ this.supprimer = (this.modifier = this.imprimer = this.detail = this.imprimer2 = Boolean.valueOf(true));
                    /* 490 */ notifySuccess();
                    /* 491 */ this.demandes_1 = this.demandeFacadeLocal.findAllRange();
                } else {
                    /* 493 */ notifyError("vente_directe");
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
            /* 549 */ this.livraisonclient = this.livraisonclientFacadeLocal.findByIddemande(this.demande.getIddemande().longValue());

            /* 551 */ if (!Utilitaires.isAccess(Long.valueOf(42L))) {
                /* 552 */ notifyError("acces_refuse");
                /* 553 */ this.livraisonclient = null;
                /* 554 */ return;
            }

            /* 557 */ if (this.livraisonclient != null) {
                /* 558 */ this.printDialogTitle = this.routine.localizeMessage("livraisonclient");
                /* 559 */ Map map = new HashMap();
                /* 560 */ map.put("idlivraisonclient", this.livraisonclient.getIdlivraisonclient());
                /* 561 */ Printer.print("/reports/ireport/fiche_sortie_materiel.jasper", map);
            } else {
                /* 564 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 567 */ notifyFail(e);
        }
    }

    public void printBonLivraison() {
        try {
            /* 573 */ if (!Utilitaires.isAccess(Long.valueOf(54L))) {
                /* 574 */ notifyError("acces_refuse");
                /* 575 */ this.livraisonclient = null;
                /* 576 */ return;
            }

            /* 579 */ if (this.livraisonclient != null) {
                /* 580 */ this.printDialogTitle = this.routine.localizeMessage("bon_de_livraison");

                /* 584 */ RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                /* 586 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 589 */ notifyFail(e);
        }
    }

    public void printDemande() {
        try {
            /* 595 */ if (!Utilitaires.isAccess(Long.valueOf(33L))) {
                /* 596 */ notifyError("acces_refuse");
                /* 597 */ this.demande = null;
                /* 598 */ return;
            }

            /* 601 */ if (this.demande != null) {
                /* 602 */ Map param = new HashMap();
                /* 603 */ param.put("iddemande", this.demande.getIddemande());
                /* 604 */ Printer.print("/reports/ireport/demande_report.jasper", param);
            } else {
                /* 606 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 609 */ notifyFail(e);
        }
    }

    public void addArticle() {
        try {
            /* 615 */ Lignedemande l = this.lignedemande;

            /* 620 */ boolean drapeau = false;
            /* 621 */ int i = 0;
            /* 622 */ for (Lignedemande lcc : this.lignedemandes) {
                /* 627 */ i++;
            }

            /* 630 */ if (!drapeau) {
                /* 631 */ this.lignedemandes.add(l);
                /* 632 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                /* 633 */ JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
                /* 634 */ this.lignedemande = new Lignedemande();

                /* 636 */ return;
            }
            /* 638 */ JsfUtil.addErrorMessage("Article existant dans la lignelivraisonclient");
            /* 639 */ updateTotal();
        } catch (Exception e) {
            /* 641 */ e.printStackTrace();
            /* 642 */ this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("formulaire_incomplet"));
            /* 643 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void removeArticle(int index) {
        try {
            /* 649 */ boolean trouve = false;
            /* 650 */ this.ut.begin();

            /* 652 */ Lignedemande lcc = (Lignedemande) this.lignedemandes.get(index);

            /* 658 */ this.lignedemandes.remove(index);

            /* 660 */ updateTotal();
            /* 661 */ if (trouve) {
                /* 662 */ this.demandeFacadeLocal.edit(this.demande);
            }
            /* 664 */ this.ut.commit();
            /* 665 */ JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            /* 667 */ e.printStackTrace();
            /* 668 */ JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal(List<Lignelivraisonclient> lignelivraisonclients) {
        /* 673 */ Double resultat = Double.valueOf(0.0D);
        /* 674 */ int i = 0;
        /* 675 */ for (Lignelivraisonclient llc : lignelivraisonclients) {
            /* 676 */ resultat = Double.valueOf(resultat.doubleValue() + llc.getMontant().doubleValue() * llc.getQuantite().doubleValue());
            /* 677 */ ((Lignelivraisonclient) lignelivraisonclients.get(i)).setQuantitemultiple(Double.valueOf(llc.getQuantite().doubleValue() * llc.getIdlot().getIdarticle().getUnite().doubleValue()));
            /* 678 */ i++;
        }
        /* 680 */ return resultat;
    }

    public void updateTotal() {
        try {
            /* 685 */ this.total = calculTotal(this.lignelivraisonclients);
            /* 686 */ this.livraisonclient.setMontant(this.total);
        } catch (Exception e) {
            /* 688 */ e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            /* 694 */ this.cout_quantite = Double.valueOf(0.0D);
            /* 695 */ if ((this.lignedemande.getQuantite() != null)
                    && /* 696 */ (this.lignedemande.getMontant() != null)) /* 697 */ {
                this.cout_quantite = Double.valueOf(this.lignedemande.getMontant().doubleValue() * this.lignedemande.getQuantite().doubleValue());
            }
        } catch (Exception e) {
            /* 701 */ e.printStackTrace();
            /* 702 */ this.cout_quantite = Double.valueOf(0.0D);
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

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.traitement.TraitementController
 * JD-Core Version:    0.6.2
 */
