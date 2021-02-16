package controllers.livraison_fournisseur;

import entities.Article;
import entities.Commandefournisseur;
import entities.Fournisseur;
import entities.Lignecommandefournisseur;
import entities.Lignelivraisonfournisseur;
import entities.Lignemvtstock;
import entities.Livraisonfournisseur;
import entities.Lot;
import entities.Mvtstock;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class LivraisonFournisseurController extends AbstractLivraisonFournisseurController
        implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        /*  49 */ updateTotal();
    }

    public List<Lot> findByProduit(Article a) {
        try {
            /*  54 */ return this.lotFacadeLocal.findByArticle(a.getIdarticle(), a.getPerissable().booleanValue());
        } catch (Exception e) {
        }
        /*  56 */ return new ArrayList();
    }

    public void prepareCreate() {
        try {
            /*  63 */ if (!Utilitaires.isAccess(Long.valueOf(52L))) {
                /*  64 */ notifyError("acces_refuse");
                /*  65 */ return;
            }
            /*  67 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
            /*  68 */ this.mode = "Create";

            /*  70 */ this.livraisonfournisseur = new Livraisonfournisseur();
            /*  71 */ this.livraisonfournisseur.setMontant(Double.valueOf(0.0D));

            /*  73 */ this.lignelivraisonfournisseurs.clear();
            /*  74 */ this.lignecommandefournisseurs.clear();

            /*  76 */ this.commandefournisseur = new Commandefournisseur();
            /*  77 */ this.showSelectorCommand = Boolean.valueOf(false);

            /*  79 */ this.total = Double.valueOf(0.0D);
        } catch (Exception e) {
            /*  81 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            /*  82 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        try {
            /*  88 */ this.commandefournisseur = new Commandefournisseur();
            /*  89 */ this.fournisseur = new Fournisseur();
            /*  90 */ this.commandefournisseurs = this.commandefournisseurFacadeLocal.findByLivre(false);
            /*  91 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            /*  93 */ notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            /* 100 */ if (this.livraisonfournisseur == null) {
                /* 101 */ notifyError("not_row_selected");
                /* 102 */ return;
            }

            /* 105 */ if (this.livraisonfournisseur.getLivraisondirecte().booleanValue()) {
                /* 106 */ notifyError("livraison_effectuee_par_commande");
                /* 107 */ return;
            }
            /* 109 */ if (Utilitaires.isDayClosed()) {
                /* 110 */ notifyError("journee_cloturee");
                /* 111 */ return;
            }

            /* 114 */ this.showSelectorCommand = Boolean.valueOf(true);
            /* 115 */ this.commandefournisseur = this.commandefournisseurFacadeLocal.find(this.livraisonfournisseur.getIdcommandefournisseur().getIdcommandefournisseur());
            /* 116 */ if (this.commandefournisseur.getLivre().booleanValue()) {
                /* 117 */ notifyError("commande_deja_livree");
                /* 118 */ return;
            }

            /* 121 */ if (!Utilitaires.isAccess(Long.valueOf(49L))) {
                /* 122 */ notifyError("acces_refuse");
                /* 123 */ this.commandefournisseur = null;
                /* 124 */ return;
            }

            /* 127 */ this.mode = "Edit";

            /* 129 */ this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur().longValue());
            /* 130 */ this.lignelivraisonfournisseurs = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur().longValue());
            /* 131 */ this.fournisseur = this.commandefournisseur.getIdfournisseur();
            /* 132 */ this.total = this.livraisonfournisseur.getMontant();
            /* 133 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
        } catch (Exception e) {
            /* 136 */ notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            /* 142 */ if (this.livraisonfournisseur != null) {
                /* 143 */ this.lignelivraisonfournisseurs = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur().longValue());
                /* 144 */ if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    /* 145 */ RequestContext.getCurrentInstance().execute("PF('FactureDetailDialog').show()");
                    /* 146 */ return;
                }
                /* 148 */ notifyError("liste_article_vide");
            } else {
                /* 150 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 153 */ notifyFail(e);
        }
    }

    public void selectCommande() {
        try {
            /* 159 */ if (this.commandefournisseur != null) {
                /* 161 */ this.lignelivraisonfournisseurs.clear();

                /* 163 */ this.fournisseur = this.commandefournisseur.getIdfournisseur();
                /* 164 */ this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur().longValue());
                /* 165 */ this.commandefournisseurs.clear();

                /* 167 */ this.commandefournisseur.setDateeffectlivraison(new Date());
                /* 168 */ int conteur = 0;
                /* 169 */ for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                    /* 170 */ List lotTemps = this.lotFacadeLocal.findByArticleRangeDesc(lcf.getIdarticle().getIdarticle(), lcf.getIdarticle().getPerissable().booleanValue());

                    /* 172 */ Lignelivraisonfournisseur object = new Lignelivraisonfournisseur();
                    /* 173 */ object.setIdlignelivraisonfournisseur(Long.valueOf(0L));

                    /* 175 */ object.setIdlot((Lot) lotTemps.get(0));
                    /* 176 */ object.setQuantite(lcf.getQuantite());
                    /* 177 */ object.setUnite(lcf.getUnite());
                    /* 178 */ object.setPrixachat(object.getIdlot().getPrixachat());
                    /* 179 */ object.setQuantitemultiple(lcf.getQuantitemultiple());
                    /* 180 */ object.setQuantitereduite(lcf.getQuantitereduite());
                    /* 181 */ object.setIdunite(lcf.getIdunite());

                    /* 183 */ this.lignelivraisonfournisseurs.add(object);
                    /* 184 */ ((Lignelivraisonfournisseur) this.lignelivraisonfournisseurs.get(conteur)).setTauxsatisfaction(Double.valueOf(0.0D));
                    /* 185 */ conteur++;
                }
                /* 187 */ this.commandefournisseur.setTauxsatisfaction(Double.valueOf(0.0D));
                /* 188 */ updateTotal();
                /* 189 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            /* 192 */ e.printStackTrace();
        }
    }

    public void create() {
        try {
            String message;
            /* 201 */ if ("Create".equals(this.mode)) {
                /* 203 */ if (!this.lignecommandefournisseurs.isEmpty()) {
                    /* 205 */ message = "";
                    /* 206 */ updateTotal();

                    /* 208 */ this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    /* 209 */ String codeMvt = "MVT";
                    /* 210 */ Long nextMvt = this.mvtstock.getIdmvtstock();
                    /* 211 */ codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);
                    /* 212 */ this.mvtstock.setCode(codeMvt);
                    /* 213 */ this.mvtstock.setDatemvt(this.commandefournisseur.getDateeffectlivraison());
                    /* 214 */ this.mvtstock.setType(" ");
                    /* 215 */ this.mvtstock.setClient(" ");
                    /* 216 */ this.mvtstock.setMagasin(" ");
                    /* 217 */ this.mvtstock.setFournisseur(this.commandefournisseur.getIdfournisseur().getNom());

                    /* 219 */ this.ut.begin();

                    /* 221 */ this.mvtstockFacadeLocal.create(this.mvtstock);

                    /* 223 */ this.livraisonfournisseur.setIdlivraisonfournisseur(this.livraisonfournisseurFacadeLocal.nextVal());
                    /* 224 */ Long nextLiv = this.livraisonfournisseur.getIdlivraisonfournisseur();
                    /* 225 */ String codeLivraison = "LIV";
                    /* 226 */ codeLivraison = Utilitaires.genererCodeStock(codeLivraison, nextLiv);
                    /* 227 */ this.livraisonfournisseur.setCode(codeLivraison);

                    /* 229 */ this.livraisonfournisseur.setIdlivraisonfournisseur(this.livraisonfournisseurFacadeLocal.nextVal());
                    /* 230 */ this.livraisonfournisseur.setMontant(this.total);
                    /* 231 */ this.livraisonfournisseur.setIdfournisseur(this.fournisseur);
                    /* 232 */ this.livraisonfournisseur.setLivraisondirecte(Boolean.valueOf(false));
                    /* 233 */ this.livraisonfournisseur.setDatelivraison(this.commandefournisseur.getDateeffectlivraison());
                    /* 234 */ this.livraisonfournisseur.setIdcommandefournisseur(this.commandefournisseur);
                    /* 235 */ this.livraisonfournisseur.setIdmvtstock(this.mvtstock);

                    /* 237 */ this.livraisonfournisseurFacadeLocal.create(this.livraisonfournisseur);

                    /* 239 */ for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                        /* 240 */ llf.setIdlignelivraisonfournisseur(this.lignelivraisonfournisseurFacadeLocal.nextVal());
                        /* 241 */ llf.setIdlivraisonfournisseur(this.livraisonfournisseur);
                        /* 242 */ llf.setQuantitemultiple(Double.valueOf(llf.getQuantite().doubleValue() * llf.getUnite().doubleValue()));
                        /* 243 */ this.lignelivraisonfournisseurFacadeLocal.create(llf);

                        /* 245 */ llf.setIdlot(this.lotFacadeLocal.find(llf.getIdlot().getIdlot()));

                        /* 247 */ llf.getIdlot().getIdarticle().setQuantitereduite(Double.valueOf(llf.getIdlot().getIdarticle().getQuantitereduite().doubleValue() + llf.getQuantitereduite().doubleValue()));
                        /* 248 */ llf.getIdlot().getIdarticle().setQuantitestock(Double.valueOf(llf.getIdlot().getIdarticle().getQuantitestock().doubleValue() + llf.getQuantite().doubleValue()));
                        /* 249 */ llf.getIdlot().getIdarticle().setQuantitemultiple(Double.valueOf(llf.getIdlot().getIdarticle().getQuantitemultiple().doubleValue() + llf.getQuantitemultiple().doubleValue()));
                        /* 250 */ this.articleFacadeLocal.edit(llf.getIdlot().getIdarticle());

                        /* 252 */ llf.getIdlot().setQuantitereduite(Double.valueOf(llf.getIdlot().getQuantitereduite().doubleValue() + llf.getQuantitereduite().doubleValue()));
                        /* 253 */ llf.getIdlot().setQuantite(Double.valueOf(llf.getIdlot().getQuantite().doubleValue() + llf.getQuantite().doubleValue()));
                        /* 254 */ llf.getIdlot().setQuantitemultiple(Double.valueOf(llf.getIdlot().getQuantitemultiple().doubleValue() + llf.getQuantitemultiple().doubleValue()));
                        /* 255 */ this.lotFacadeLocal.edit(llf.getIdlot());

                        /* 257 */ Lignemvtstock lmvts = new Lignemvtstock();
                        /* 258 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        /* 259 */ lmvts.setIdmvtstock(this.mvtstock);
                        /* 260 */ lmvts.setIdlot(llf.getIdlot());
                        /* 261 */ lmvts.setQteentree(llf.getQuantitemultiple());
                        /* 262 */ lmvts.setQtesortie(Double.valueOf(0.0D));
                        /* 263 */ lmvts.setReste(llf.getIdlot().getQuantitemultiple());
                        /* 264 */ lmvts.setType("ENTREE");
                        /* 265 */ lmvts.setClient(" ");
                        /* 266 */ lmvts.setFournisseur(this.commandefournisseur.getIdfournisseur().getNom());
                        /* 267 */ lmvts.setMagasin("MAGASIN CENTRAL");
                        /* 268 */ this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    /* 271 */ this.commandefournisseur.setLivre(Boolean.valueOf(true));
                    /* 272 */ this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);

                    /* 274 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du bon de livraison fournisseur ; N° : " + this.livraisonfournisseur.getCode() + "; Montant : " + this.livraisonfournisseur.getMontant(), SessionMBean.getUserAccount());

                    /* 276 */ this.ut.commit();
                    /* 277 */ this.commandefournisseur = new Commandefournisseur();
                    /* 278 */ this.livraisonfournisseur = null;
                    /* 279 */ this.lignecommandefournisseurs.clear();
                    /* 280 */ this.lignelivraisonfournisseurs.clear();
                    /* 281 */ this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
                    /* 282 */ JsfUtil.addSuccessMessage(message);

                    /* 284 */ notifySuccess();
                    /* 285 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
                } else {
                    /* 287 */ notifyError("liste_article_vide");
                }
            } /* 290 */ else if (this.livraisonfournisseur != null) {
                /* 292 */ this.ut.begin();

                /* 294 */ updateTotal();
                /* 295 */ this.livraisonfournisseurFacadeLocal.edit(this.livraisonfournisseur);

                /* 297 */ this.mvtstock = this.livraisonfournisseur.getIdmvtstock();

                /* 299 */ if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    /* 300 */ for (Lignelivraisonfournisseur s : this.lignelivraisonfournisseurs) {
                        /* 302 */ Lignelivraisonfournisseur sp = this.lignelivraisonfournisseurFacadeLocal.find(s.getIdlignecommandefournisseur());
                        /* 303 */ if (sp.getQuantite() != s.getQuantite()) {
                            /* 304 */ Article art = sp.getIdlot().getIdarticle();
                            /* 305 */ art.setQuantitestock(Double.valueOf(art.getQuantitestock().doubleValue() + sp.getQuantite().doubleValue() - sp.getQuantite().doubleValue()));
                            /* 306 */ this.articleFacadeLocal.edit(art);

                            /* 308 */ Lot l = sp.getIdlot();
                            /* 309 */ l.setQuantite(Double.valueOf(l.getQuantite().doubleValue() + sp.getQuantite().doubleValue() - sp.getQuantite().doubleValue()));
                            /* 310 */ this.lotFacadeLocal.edit(l);

                            /* 312 */ Lignemvtstock lmvts = new Lignemvtstock();
                            /* 313 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            /* 314 */ lmvts.setIdmvtstock(this.mvtstock);
                            /* 315 */ lmvts.setIdlot(s.getIdlot());

                            /* 317 */ if (s.getQuantite().doubleValue() > sp.getQuantite().doubleValue()) {
                                /* 318 */ lmvts.setQteentree(Double.valueOf(s.getQuantite().doubleValue() - sp.getQuantite().doubleValue()));
                                /* 319 */ lmvts.setQtesortie(Double.valueOf(0.0D));
                                /* 320 */ lmvts.setReste(s.getIdlot().getQuantite());
                            } else {
                                /* 322 */ lmvts.setQteentree(Double.valueOf(0.0D));
                                /* 323 */ lmvts.setQtesortie(Double.valueOf(sp.getQuantite().doubleValue() - s.getQuantite().doubleValue()));
                                /* 324 */ lmvts.setReste(s.getIdlot().getQuantite());
                            }
                            /* 326 */ this.lignemvtstockFacadeLocal.create(lmvts);
                        }
                        /* 328 */ this.lignelivraisonfournisseurFacadeLocal.edit(s);
                    }
                }

                /* 332 */ this.ut.commit();
                /* 333 */ this.lignecommandefournisseurs.clear();
                /* 334 */ this.commandefournisseurs.clear();
                /* 335 */ this.lignemvtstocks.clear();
                /* 336 */ this.commandefournisseur = new Commandefournisseur();
                /* 337 */ this.livraisonfournisseur = null;
                /* 338 */ this.supprimer = (this.modifier = this.imprimer = this.detail = Boolean.valueOf(true));

                /* 340 */ notifySuccess();
                /* 341 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
            } else {
                /* 343 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 347 */ notifyFail(e);
        }
    }

    public void delete() {
        try {
            /* 353 */ if (this.livraisonfournisseur != null) {
                /* 355 */ if (!this.livraisonfournisseur.getLivraisondirecte().booleanValue()) {
                    /* 357 */ if (!Utilitaires.isAccess(Long.valueOf(53L))) {
                        /* 358 */ notifyError("acces_refuse");
                        /* 359 */ this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
                        /* 360 */ this.livraisonfournisseur = null;
                        /* 361 */ return;
                    }

                    /* 364 */ this.ut.begin();

                    /* 366 */ List<Lignelivraisonfournisseur> temp = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur().longValue());
                    /* 367 */ if (!temp.isEmpty()) {
                        /* 368 */ for (Lignelivraisonfournisseur sp : temp) {
                            /* 369 */ sp.getIdlot().setIdarticle(this.articleFacadeLocal.find(sp.getIdlot().getIdarticle().getIdarticle()));
                            /* 370 */ sp.getIdlot().getIdarticle().setQuantitestock(Double.valueOf(sp.getIdlot().getIdarticle().getQuantitestock().doubleValue() + sp.getQuantite().doubleValue()));
                            /* 371 */ this.articleFacadeLocal.edit(sp.getIdlot().getIdarticle());

                            /* 373 */ if (sp.getIdlot() != null) {
                                /* 374 */ sp.setIdlot(this.lotFacadeLocal.find(sp.getIdlot().getIdlot()));
                                /* 375 */ sp.getIdlot().setQuantite(Double.valueOf(sp.getIdlot().getQuantite().doubleValue() + sp.getQuantite().doubleValue()));
                                /* 376 */ this.lotFacadeLocal.edit(sp.getIdlot());
                            }
                            /* 378 */ this.lignelivraisonfournisseurFacadeLocal.remove(sp);
                        }
                    }
                    /* 381 */ this.livraisonfournisseurFacadeLocal.remove(this.livraisonfournisseur);

                    /* 383 */ this.commandefournisseur = this.commandefournisseurFacadeLocal.find(this.livraisonfournisseur.getIdcommandefournisseur().getIdcommandefournisseur());

                    /* 385 */ this.commandefournisseur.setLivre(Boolean.valueOf(false));
                    /* 386 */ this.commandefournisseur.setCode("-");
                    /* 387 */ this.commandefournisseur.setTauxsatisfaction(Double.valueOf(0.0D));
                    /* 388 */ this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);

                    /* 390 */ Mvtstock mTemp = this.livraisonfournisseur.getIdmvtstock();

                    /* 392 */ List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(mTemp.getIdmvtstock().longValue());
                    /* 393 */ for (Lignemvtstock l : lmvt) {
                        /* 394 */ this.lignemvtstockFacadeLocal.remove(l);
                    }
                    /* 396 */ this.mvtstockFacadeLocal.remove(mTemp);

                    /* 398 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de livraison fournisseur : " + this.livraisonfournisseur.getCode() + " ; Montant : " + this.livraisonfournisseur.getMontant() + " Fournisseur : " + this.livraisonfournisseur.getIdcommandefournisseur().getIdfournisseur().getNom(), SessionMBean.getUserAccount());
                    /* 399 */ this.ut.commit();

                    /* 401 */ this.livraisonfournisseur = null;
                    /* 402 */ this.commandefournisseur = new Commandefournisseur();
                    /* 403 */ this.supprimer = (this.modifier = this.imprimer = this.detail = Boolean.valueOf(true));
                    /* 404 */ notifySuccess();
                } else {
                    /* 407 */ notifyError(this.mode);
                }
            } /* 410 */ else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 413 */ notifyFail(e);
        }
    }

    public void initPrinter(Livraisonfournisseur l) {
        /* 418 */ this.livraisonfournisseur = l;
        /* 419 */ print();
    }

    public void initEdit(Livraisonfournisseur l) {
        /* 423 */ this.livraisonfournisseur = l;
        /* 424 */ prepareEdit();
    }

    public void initView(Livraisonfournisseur l) {
        /* 428 */ this.livraisonfournisseur = l;
        /* 429 */ prepareview();
    }

    public void initDelete(Livraisonfournisseur l) {
        /* 433 */ this.livraisonfournisseur = l;
        /* 434 */ delete();
    }

    public void print() {
        try {
            /* 439 */ if (!Utilitaires.isAccess(Long.valueOf(54L))) {
                /* 440 */ notifyError("acces_refuse");
                /* 441 */ this.commandefournisseur = null;
                /* 442 */ return;
            }

            /* 445 */ if (this.commandefournisseur != null) {
                /* 449 */ RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } /* 451 */ else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 454 */ notifyFail(e);
        }
    }

    public void addProduit() {
        try {
            /* 460 */ Lignecommandefournisseur l = this.lignecommandefournisseur;
            /* 461 */ l.setIdlignecommandefournisseur(Long.valueOf(0L));

            /* 464 */ boolean drapeau = false;
            /* 465 */ int i = 0;
            /* 466 */ for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                /* 471 */ i++;
            }

            /* 474 */ if (!drapeau) {
                /* 475 */ this.lignecommandefournisseurs.add(l);
                /* 476 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                /* 477 */ JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
                /* 478 */ this.lignecommandefournisseur = new Lignecommandefournisseur();
                /* 479 */ this.article = new Article();
                /* 480 */ return;
            }
            /* 482 */ JsfUtil.addErrorMessage("Article existant dans la commande");
            /* 483 */ updateTotal();
        } catch (Exception e) {
            /* 485 */ e.printStackTrace();
            /* 486 */ this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("formulaire_incomplet"));
            /* 487 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void removeProduit(int index) {
        try {
            /* 493 */ boolean trouve = false;
            /* 494 */ this.ut.begin();

            /* 496 */ Lignecommandefournisseur lcf = (Lignecommandefournisseur) this.lignecommandefournisseurs.get(index);
            /* 497 */ if (lcf.getIdlignecommandefournisseur().longValue() != 0L) {
                /* 498 */ trouve = true;
                /* 499 */ this.lignecommandefournisseurFacadeLocal.remove(lcf);
                /* 500 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'article : " + lcf.getIdarticle().getLibelle() + " quantité : " + lcf.getQuantite() + " dans la facture : " + this.livraisonfournisseur.getCode(), SessionMBean.getUserAccount());
            }
            /* 502 */ this.lignecommandefournisseurs.remove(index);

            /* 504 */ updateTotal();
            /* 505 */ if (trouve) {
                /* 506 */ this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);
            }
            /* 508 */ this.ut.commit();
            /* 509 */ JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            /* 511 */ e.printStackTrace();
            /* 512 */ JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal() {
        /* 517 */ Double resultat = Double.valueOf(0.0D);
        /* 518 */ int i = 0;
        /* 519 */ for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
            /* 520 */ resultat = Double.valueOf(resultat.doubleValue() + llf.getIdlot().getIdarticle().getCoutachat().doubleValue() * llf.getQuantite().doubleValue());
            /* 521 */ ((Lignelivraisonfournisseur) this.lignelivraisonfournisseurs.get(i)).setQuantitemultiple(Double.valueOf(llf.getQuantite().doubleValue() * llf.getUnite().doubleValue()));
            /* 522 */ ((Lignelivraisonfournisseur) this.lignelivraisonfournisseurs.get(i)).setQuantitereduite(Double.valueOf(((Lignelivraisonfournisseur) this.lignelivraisonfournisseurs.get(i)).getQuantitemultiple().doubleValue() / llf.getIdlot().getIdarticle().getUnite().doubleValue()));
            /* 523 */ i++;
        }
        /* 525 */ return resultat;
    }

    public void updateTotal() {
        try {
            /* 530 */ this.total = calculTotal();
            /* 531 */ this.livraisonfournisseur.setMontant(this.total);
        } catch (Exception e) {
            /* 533 */ e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            /* 539 */ this.cout_quantite = Double.valueOf(0.0D);
            /* 540 */ if ((this.lignecommandefournisseur.getQuantite() != null)
                    && /* 541 */ (this.lignecommandefournisseur.getMontant() != null)) /* 542 */ {
                this.cout_quantite = Double.valueOf(this.lignecommandefournisseur.getMontant().doubleValue() * this.lignecommandefournisseur.getQuantite().doubleValue());
            }
        } catch (Exception e) {
            /* 546 */ e.printStackTrace();
            /* 547 */ this.cout_quantite = Double.valueOf(0.0D);
        }
    }

    public void updatedataLot() {
    }

    public void notifyError(String message) {
        /* 556 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 557 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 561 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 562 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 563 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 567 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 568 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 569 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.livraison_fournisseur.LivraisonFournisseurController
 * JD-Core Version:    0.6.2
 */
