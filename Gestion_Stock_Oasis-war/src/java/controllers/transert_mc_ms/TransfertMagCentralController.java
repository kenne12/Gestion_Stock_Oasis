package controllers.transert_mc_ms;

import entities.Article;
import entities.Famille;
import entities.Lignemvtstock;
import entities.Lignerepartitionarticle;
import entities.Lignerepartitiontemp;
import entities.Lot;
import entities.Magasinlot;
import entities.Repartitionarticle;
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
import utils.GroupQuantite;
import utils.JsfUtil;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class TransfertMagCentralController extends AbstractTransertMagCentralController
        implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        /*  52 */ updateTotal();
    }

    public void prepareCreate() {
        try {
            /*  58 */ if (!Utilitaires.isAccess(Long.valueOf(52L))) {
                /*  59 */ notifyError("acces_refuse");
                /*  60 */ return;
            }
            /*  62 */ RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
            /*  63 */ this.mode = "Create";

            /*  65 */ this.magasinlots.clear();
            /*  66 */ this.selectedMagasinlots.clear();

            /*  68 */ this.repartitionarticle = new Repartitionarticle();
            /*  69 */ this.repartitionarticle.setDate(new Date());
            /*  70 */ this.payement_credit = false;

            /*  72 */ this.showSelectorCommand = Boolean.valueOf(false);
            /*  73 */ this.lignerepartitiontemps.clear();
            /*  74 */ this.lignerepartitionarticles.clear();
            /*  75 */ this.lignerepartitionarticles_1.clear();

            /*  77 */ this.total = Double.valueOf(0.0D);
            /*  78 */ this.state_zero = 0;
        } catch (Exception e) {
            /*  80 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            /*  81 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareAddArticle() {
        try {
            /*  87 */ this.lignerepartitionarticles_1.clear();
            /*  88 */ this.selectedMagasinlots.clear();
            /*  89 */ this.magasinlots.clear();

            /*  91 */ this.famille = new Famille();
            /*  92 */ this.lot = new Lot();
            /*  93 */ this.article = new Article();
            /*  94 */ this.lots.clear();
            /*  95 */ this.articles = this.articleFacadeLocal.findByEtatQuantityPositif(true);
            /*  96 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            /*  98 */ notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            /* 104 */ if (this.repartitionarticle == null) {
                /* 105 */ notifyError("not_row_selected");
                /* 106 */ return;
            }

            /* 109 */ this.showSelectorCommand = Boolean.valueOf(true);

            /* 111 */ if (!Utilitaires.isAccess(Long.valueOf(49L))) {
                /* 112 */ notifyError("acces_refuse");
                /* 113 */ this.repartitionarticle = null;
                /* 114 */ return;
            }

            /* 117 */ this.mode = "Edit";

            /* 119 */ this.lignerepartitionarticles = this.lignerepartitionarticleFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle().intValue());
            /* 120 */ this.lignerepartitiontemps = this.lignerepartitiontempFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle().intValue());

            /* 122 */ this.total = this.repartitionarticle.getMontanttotal();
            /* 123 */ RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').show()");
        } catch (Exception e) {
            /* 126 */ notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            /* 132 */ if (this.repartitionarticle != null) {
                /* 133 */ this.lignerepartitionarticles = this.lignerepartitionarticleFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle().intValue());
                /* 134 */ this.lignerepartitiontemps = this.lignerepartitiontempFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle().intValue());

                /* 136 */ if (!this.lignerepartitionarticles.isEmpty()) {
                    /* 137 */ RequestContext.getCurrentInstance().execute("PF('TransfertDetailDialog').show()");
                    /* 138 */ return;
                }
                /* 140 */ notifyError("liste_article_vide");
            } else {
                /* 142 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 145 */ notifyFail(e);
        }
    }

    public void filterArticle() {
        try {
            /* 151 */ this.articles.clear();
            /* 152 */ this.lots.clear();
            /* 153 */ this.article = new Article();
            /* 154 */ this.lot = new Lot();
            /* 155 */ if (this.famille.getIdfamille() != null) /* 156 */ {
                this.articles = this.articleFacadeLocal.findByFamille(this.famille.getIdfamille().longValue());
            }
        } catch (Exception e) {
            /* 159 */ e.printStackTrace();
        }
    }

    public void addProduit() {
        try {
            if (!this.selectedMagasinlots.isEmpty()) {
                if (findLotLignerepartititionTmp(this.lot)) {
                    Lignerepartitiontemp lrt = new Lignerepartitiontemp();
                    lrt.setIdlignerepartitiontemp(Long.valueOf(0L));
                    lrt.setIdlot(this.lot);
                    lrt.setQuantite(Double.valueOf(0.0D));
                    lrt.setUnite(this.lot.getIdarticle().getUnite());
                    lrt.setQuantitemultiple(Double.valueOf(0.0D));
                    this.lignerepartitiontemps.add(lrt);
                }

                for (Magasinlot m : this.selectedMagasinlots) {
                    this.state_zero += 1;
                    if (findMagasinLotInLigneRepartition(m, this.lignerepartitionarticles_1)) {
                        Lignerepartitionarticle lra = new Lignerepartitionarticle();
                        lra.setIdlignerepartitionarticle(Long.valueOf(0L));
                        lra.setIdmagasinlot(m);
                        lra.setUnite(m.getIdlot().getIdarticle().getUnite());
                        lra.setQuantite(Double.valueOf(1.0D));
                        lra.setQuantitemultiple(lra.getUnite());
                        lra.setIdunite(m.getIdlot().getIdarticle().getIdunite());
                        this.lignerepartitionarticles_1.add(lra);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean findLotLignerepartititionTmp(Lot lot) {
        /* 198 */ if (this.lignerepartitiontemps.isEmpty()) {
            /* 199 */ return true;
        }
        /* 201 */ boolean result = false;
        /* 202 */ for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
            /* 203 */ if (!lrt.getIdlot().equals(lot)) {
                /* 204 */ result = true;
                /* 205 */ break;
            }
        }
        /* 208 */ return result;
    }

    private boolean findMagasinLotInLigneRepartition(Magasinlot m, List<Lignerepartitionarticle> lignerepartitionarticles) {
        /* 212 */ if (lignerepartitionarticles.isEmpty()) {
            /* 213 */ return true;
        }
        /* 215 */ boolean result = false;

        /* 217 */ for (Lignerepartitionarticle lra : lignerepartitionarticles) {
            /* 218 */ if (!lra.getIdmagasinlot().equals(m)) {
                /* 219 */ result = true;
                /* 220 */ break;
            }
        }
        /* 223 */ return result;
    }

    public void updateListMagasin() {
        try {
            /* 228 */ this.magasinlots.clear();
            /* 229 */ if (this.lot != null) /* 230 */ {
                this.magasinlots = this.magasinlotFacadeLocal.findByIdlot(this.lot.getIdlot().longValue());
            }
        } catch (Exception e) {
            /* 233 */ e.printStackTrace();
        }
    }

    public void validateData() {
        try {
            if (!this.selectedMagasinlots.isEmpty()) {
                double qte_test = 0.0D;
                for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
                    qte_test += lrt.getIdlot().getQuantite().doubleValue();
                }

                for (Lignerepartitionarticle lra : this.lignerepartitionarticles_1) {
                    if (findMagasinLotInLigneRepartition(lra.getIdmagasinlot(), this.lignerepartitionarticles)) {
                        this.lignerepartitionarticles.add(lra);
                    }
                }

                double qte_all = 0.0D;
                for (Lignerepartitionarticle lra : lignerepartitionarticles) {
                    qte_all += lra.getQuantite().doubleValue();
                }

                if (qte_test < qte_all) {
                    notifyError("quantite_inexate");
                    return;
                }

                int i = 0;
                for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
                    Double qte_1 = Double.valueOf(0.0D);
                    for (Lignerepartitionarticle lra : this.lignerepartitionarticles) {
                        if (lrt.getIdlot().equals(lra.getIdmagasinlot().getIdlot())) {
                            qte_1 = Double.valueOf(qte_1.doubleValue() + lra.getQuantite().doubleValue());
                        }
                    }
                    ((Lignerepartitiontemp) this.lignerepartitiontemps.get(i)).setQuantite(qte_1);
                    ((Lignerepartitiontemp) this.lignerepartitiontemps.get(i)).setQuantitemultiple(Double.valueOf(qte_1.doubleValue() * ((Lignerepartitiontemp) this.lignerepartitiontemps.get(i)).getUnite().doubleValue()));
                    i++;
                }

                /* 275 */ updateTotal();
                /* 276 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            /* 279 */ notifyFail(e);
        }
    }

    public void updatedata() {
        try {
            /* 285 */ if (this.article != null) {
                /* 286 */ this.famille = this.article.getIdfamille();
                /* 287 */ this.lot = new Lot();
                /* 288 */ this.lots = this.lotFacadeLocal.findByArticle(this.article.getIdarticle(), this.article.getPerissable().booleanValue(), new Date(), true);
            }
        } catch (Exception e) {
            /* 291 */ e.printStackTrace();
        }
    }

    private Map groupQtyByLrt() {
        /* 296 */ Map map = new HashMap();
        /* 297 */ int i = 0;
        /* 298 */ for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
            /* 299 */ GroupQuantite gq = new GroupQuantite();
            /* 300 */ for (Lignerepartitionarticle lra : this.lignerepartitionarticles) {
                /* 301 */ if (lrt.getIdlot().equals(lra.getIdmagasinlot().getIdlot())) {
                    /* 302 */ gq.setQuantite(gq.getQuantite() + lra.getQuantite().doubleValue());
                    /* 303 */ gq.setQuantiteReduite(gq.getQuantiteReduite() + lra.getQuantitereduite().doubleValue());
                    /* 304 */ gq.setQuantiteMultiple(gq.getQuantiteMultiple() + lra.getQuantitemultiple().doubleValue());
                    /* 305 */ gq.setUnite(gq.getUnite() + lra.getUnite().doubleValue());
                }
            }
            /* 308 */ ((Lignerepartitiontemp) this.lignerepartitiontemps.get(i)).setQuantite(Double.valueOf(gq.getQuantite()));
            /* 309 */ ((Lignerepartitiontemp) this.lignerepartitiontemps.get(i)).setQuantitemultiple(Double.valueOf(gq.getQuantiteMultiple()));
            /* 310 */ ((Lignerepartitiontemp) this.lignerepartitiontemps.get(i)).setQuantitereduite(Double.valueOf(gq.getQuantiteReduite()));
            /* 311 */ ((Lignerepartitiontemp) this.lignerepartitiontemps.get(i)).setUnite(Double.valueOf(gq.getUnite()));
            /* 312 */ map.put("" + lrt.getIdlot().getIdlot(), gq);
        }
        /* 314 */ return map;
    }

    public void create() {
        try {
            /* 319 */ if ("Create".equals(this.mode)) {
                /* 321 */ if (!this.lignerepartitionarticles.isEmpty()) {
                    /* 323 */ updateTotal();
                    /* 324 */ Map mapGroupLrt = groupQtyByLrt();

                    /* 326 */ String message = "";

                    /* 328 */ String codeMvt = "MVT";
                    /* 329 */ this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    /* 330 */ Long nextMvt = this.mvtstock.getIdmvtstock();
                    /* 331 */ codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);

                    /* 333 */ this.ut.begin();

                    /* 335 */ this.mvtstock.setCode(codeMvt);
                    /* 336 */ this.mvtstock.setDatemvt(this.repartitionarticle.getDate());
                    /* 337 */ this.mvtstock.setClient("MAGA SEC");
                    /* 338 */ this.mvtstock.setFournisseur(" ");
                    /* 339 */ this.mvtstock.setMagasin("MAGASIN CENTRAL");
                    /* 340 */ this.mvtstock.setType("SORTIE");
                    /* 341 */ this.mvtstockFacadeLocal.create(this.mvtstock);

                    /* 343 */ String code = "MVT";
                    /* 344 */ Integer nextVal = this.repartitionarticleFacadeLocal.nextVal();
                    /* 345 */ code = Utilitaires.genererCodeDemande(code, Long.valueOf(nextVal.longValue()));
                    /* 346 */ this.repartitionarticle.setCode(code);
                    /* 347 */ this.repartitionarticle.setIdrepartitionarticle(nextVal);
                    /* 348 */ this.repartitionarticle.setIdmvtstock(this.mvtstock);
                    /* 349 */ this.repartitionarticleFacadeLocal.create(this.repartitionarticle);

                    /* 351 */ Map m = new HashMap();

                    /* 353 */ for (Lignerepartitiontemp lrt : this.lignerepartitiontemps) {
                        /* 354 */ lrt.setIdlignerepartitiontemp(this.lignerepartitiontempFacadeLocal.nextVal());
                        /* 355 */ lrt.setIdrepartitionarticle(this.repartitionarticle);

                        /* 357 */ GroupQuantite gq = (GroupQuantite) mapGroupLrt.get("" + lrt.getIdlot().getIdlot());
                        /* 358 */ lrt.setQuantitemultiple(Double.valueOf(gq.getQuantiteMultiple()));
                        /* 359 */ lrt.setUnite(Double.valueOf(gq.getUnite()));
                        /* 360 */ lrt.setQuantitereduite(Double.valueOf(gq.getQuantiteReduite()));
                        /* 361 */ this.lignerepartitiontempFacadeLocal.create(lrt);

                        /* 363 */ m.put("" + lrt.getIdlot().getIdlot(), lrt);

                        /* 365 */ lrt.setIdlot(this.lotFacadeLocal.find(lrt.getIdlot().getIdlot()));
                        /* 366 */ lrt.getIdlot().setQuantite(Double.valueOf(lrt.getIdlot().getQuantite().doubleValue() - lrt.getQuantite().doubleValue()));
                        /* 367 */ lrt.getIdlot().setQuantitemultiple(Double.valueOf(lrt.getIdlot().getQuantitemultiple().doubleValue() - lrt.getQuantitemultiple().doubleValue()));
                        /* 368 */ lrt.getIdlot().setQuantitereduite(Double.valueOf(lrt.getIdlot().getQuantitereduite().doubleValue() - lrt.getQuantitereduite().doubleValue()));
                        /* 369 */ this.lotFacadeLocal.edit(lrt.getIdlot());

                        /* 371 */ lrt.getIdlot().getIdarticle().setQuantitestock(Double.valueOf(lrt.getIdlot().getIdarticle().getQuantitestock().doubleValue() - lrt.getQuantite().doubleValue()));
                        /* 372 */ lrt.getIdlot().getIdarticle().setQuantitemultiple(Double.valueOf(lrt.getIdlot().getIdarticle().getQuantitemultiple().doubleValue() - lrt.getQuantitemultiple().doubleValue()));
                        /* 373 */ lrt.getIdlot().getIdarticle().setQuantitereduite(Double.valueOf(lrt.getIdlot().getIdarticle().getQuantitereduite().doubleValue() - lrt.getQuantitereduite().doubleValue()));
                        /* 374 */ this.articleFacadeLocal.edit(lrt.getIdlot().getIdarticle());

                        /* 376 */ Lignemvtstock lmvts = new Lignemvtstock();
                        /* 377 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        /* 378 */ lmvts.setIdmvtstock(this.mvtstock);

                        /* 380 */ lmvts.setIdlot(lrt.getIdlot());
                        /* 381 */ lmvts.setQtesortie(Double.valueOf(lrt.getQuantitemultiple().doubleValue() * lrt.getIdlot().getIdarticle().getUnite().doubleValue()));
                        /* 382 */ lmvts.setQteentree(Double.valueOf(0.0D));
                        /* 383 */ lmvts.setReste(lrt.getIdlot().getQuantitereduite());
                        /* 384 */ lmvts.setClient("MAGASIN SECONDAIRE");
                        /* 385 */ lmvts.setFournisseur("");
                        /* 386 */ lmvts.setType("SORTIE");
                        /* 387 */ this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    /* 390 */ for (Lignerepartitionarticle lra : this.lignerepartitionarticles) {
                        /* 391 */ lra.setIdlignerepartitionarticle(this.lignerepartitionarticleFacadeLocal.nextVal());
                        /* 392 */ lra.setIdrepartitionarticle(this.repartitionarticle);
                        /* 393 */ lra.setMontant(Double.valueOf(lra.getIdmagasinlot().getIdlot().getPrixunitaire().doubleValue() * lra.getQuantite().doubleValue()));
                        /* 394 */ lra.setIdlignerepartitiontemp((Lignerepartitiontemp) m.get("" + lra.getIdmagasinlot().getIdlot().getIdlot()));
                        /* 395 */ this.lignerepartitionarticleFacadeLocal.create(lra);

                        /* 397 */ lra.getIdmagasinlot().setIdmagasinarticle(this.magasinarticleFacadeLocal.find(lra.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle()));
                        /* 398 */ lra.getIdmagasinlot().getIdmagasinarticle().setQuantite(Double.valueOf(lra.getIdmagasinlot().getIdmagasinarticle().getQuantite().doubleValue() + lra.getQuantite().doubleValue()));
                        /* 399 */ lra.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple(Double.valueOf(lra.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple().doubleValue() + lra.getQuantitemultiple().doubleValue()));
                        /* 400 */ lra.getIdmagasinlot().getIdmagasinarticle().setQuantitereduite(Double.valueOf(lra.getIdmagasinlot().getIdmagasinarticle().getQuantitereduite().doubleValue() - lra.getQuantitereduite().doubleValue()));
                        /* 401 */ this.magasinarticleFacadeLocal.edit(lra.getIdmagasinlot().getIdmagasinarticle());

                        /* 403 */ lra.setIdmagasinlot(this.magasinlotFacadeLocal.find(lra.getIdmagasinlot().getIdmagasinlot()));
                        /* 404 */ lra.getIdmagasinlot().setQuantite(Double.valueOf(lra.getIdmagasinlot().getQuantite().doubleValue() + lra.getQuantite().doubleValue()));
                        /* 405 */ lra.getIdmagasinlot().setQuantitemultiple(Double.valueOf(lra.getIdmagasinlot().getQuantitemultiple().doubleValue() + lra.getQuantitemultiple().doubleValue()));
                        /* 406 */ lra.getIdmagasinlot().setQuantitereduite(Double.valueOf(lra.getIdmagasinlot().getQuantitereduite().doubleValue() - lra.getQuantitereduite().doubleValue()));
                        /* 407 */ this.magasinlotFacadeLocal.edit(lra.getIdmagasinlot());

                        /* 409 */ Lignemvtstock lmvts = new Lignemvtstock();
                        /* 410 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        /* 411 */ lmvts.setIdmvtstock(this.mvtstock);
                        /* 412 */ lmvts.setIdlot(lra.getIdmagasinlot().getIdlot());
                        /* 413 */ lmvts.setIdmagasinlot(lra.getIdmagasinlot());
                        /* 414 */ lmvts.setQtesortie(Double.valueOf(0.0D));
                        /* 415 */ lmvts.setQteentree(lra.getQuantitemultiple());
                        /* 416 */ lmvts.setClient(lra.getIdmagasinlot().getIdmagasinarticle().getIdmagasin().getNom());
                        /* 417 */ lmvts.setFournisseur("MAGASIN CENTRAL");
                        /* 418 */ lmvts.setType("ENTREE");
                        /* 419 */ lmvts.setReste(lra.getIdmagasinlot().getQuantite());
                        /* 420 */ this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    /* 423 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du transfert d'article ; N° : " + this.repartitionarticle.getCode() + "; Montant : " + this.repartitionarticle.getMontanttotal(), SessionMBean.getUserAccount());

                    /* 425 */ this.ut.commit();

                    /* 427 */ this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
                    /* 428 */ JsfUtil.addSuccessMessage(message);

                    /* 430 */ notifySuccess();
                    /* 431 */ RequestContext.getCurrentInstance().execute("PF('TransfertCreateDialog').hide()");
                } else {
                    /* 433 */ notifyError("liste_article_vide");
                }
            } /* 436 */ else if (this.repartitionarticle == null) {
                /* 439 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 443 */ notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.repartitionarticle != null) {
                if (!Utilitaires.isAccess(Long.valueOf(53L))) {
                    notifyError("acces_refuse");
                    this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
                    this.repartitionarticle = null;
                    return;
                }

                this.ut.begin();

                List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(this.repartitionarticle.getIdmvtstock().getIdmvtstock().longValue());
                for (Lignemvtstock l : lmvt) {
                    this.lignemvtstockFacadeLocal.remove(l);
                }

                List<Lignerepartitiontemp> listRepartitionTemp = this.lignerepartitiontempFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle().intValue());
                if (!listRepartitionTemp.isEmpty()) /* 467 */ {
                    for (Lignerepartitiontemp lrt : listRepartitionTemp) {

                        this.lignerepartitiontempFacadeLocal.remove(lrt);

                        lrt.setIdlot(this.lotFacadeLocal.find(lrt.getIdlot().getIdlot()));
                        lrt.getIdlot().setQuantite(Double.valueOf(lrt.getIdlot().getQuantite().doubleValue() + lrt.getQuantite().doubleValue()));
                        lrt.getIdlot().setQuantitemultiple(Double.valueOf(lrt.getIdlot().getQuantitemultiple().doubleValue() + lrt.getQuantitemultiple().doubleValue()));
                        lrt.getIdlot().setQuantitereduite(Double.valueOf(lrt.getIdlot().getQuantitereduite().doubleValue() + lrt.getQuantitereduite().doubleValue()));
                        this.lotFacadeLocal.edit(lrt.getIdlot());

                        lrt.getIdlot().getIdarticle().setQuantitestock(Double.valueOf(lrt.getIdlot().getIdarticle().getQuantitestock().doubleValue() + lrt.getQuantite().doubleValue()));
                        lrt.getIdlot().getIdarticle().setQuantitemultiple(Double.valueOf(lrt.getIdlot().getIdarticle().getQuantitemultiple().doubleValue() + lrt.getQuantitemultiple().doubleValue()));
                        lrt.getIdlot().getIdarticle().setQuantitereduite(Double.valueOf(lrt.getIdlot().getIdarticle().getQuantitereduite().doubleValue() + lrt.getQuantitereduite().doubleValue()));
                        this.articleFacadeLocal.edit(lrt.getIdlot().getIdarticle());
                    }
                }

                List<Lignerepartitionarticle> listLigneRepartArts = this.lignerepartitionarticleFacadeLocal.findByIdRepartition(this.repartitionarticle.getIdrepartitionarticle().intValue());
                for (Lignerepartitionarticle lra : listLigneRepartArts) {
                    this.lignerepartitionarticleFacadeLocal.remove(lra);

                    lra.setIdmagasinlot(this.magasinlotFacadeLocal.find(lra.getIdmagasinlot().getIdmagasinlot()));
                    lra.getIdmagasinlot().setQuantite(Double.valueOf(lra.getIdmagasinlot().getQuantite().doubleValue() - lra.getQuantite().doubleValue()));
                    lra.getIdmagasinlot().setQuantitemultiple(Double.valueOf(lra.getIdmagasinlot().getQuantitemultiple().doubleValue() - lra.getQuantitemultiple().doubleValue()));
                    lra.getIdmagasinlot().setQuantitereduite(Double.valueOf(lra.getIdmagasinlot().getQuantitereduite().doubleValue() - lra.getQuantitereduite().doubleValue()));

                    lra.getIdmagasinlot().getIdmagasinarticle().setQuantite(Double.valueOf(lra.getIdmagasinlot().getIdmagasinarticle().getQuantite().doubleValue() - lra.getQuantite().doubleValue()));
                    lra.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple(Double.valueOf(lra.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple().doubleValue() - lra.getQuantitemultiple().doubleValue()));
                    lra.getIdmagasinlot().getIdmagasinarticle().setQuantitemultiple(Double.valueOf(lra.getIdmagasinlot().getIdmagasinarticle().getQuantitemultiple().doubleValue() - lra.getQuantitemultiple().doubleValue()));

                    this.magasinlotFacadeLocal.edit(lra.getIdmagasinlot());
                    this.magasinarticleFacadeLocal.edit(lra.getIdmagasinlot().getIdmagasinarticle());
                }

                /* 500 */ this.repartitionarticleFacadeLocal.remove(this.repartitionarticle);
                /* 501 */ this.mvtstockFacadeLocal.remove(this.repartitionarticle.getIdmvtstock());

                /* 503 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation du transfert d'article : " + this.repartitionarticle.getCode() + " ; Montant : " + this.repartitionarticle.getMontanttotal(), SessionMBean.getUserAccount());
                /* 504 */ this.ut.commit();

                /* 506 */ this.repartitionarticle = null;
                /* 507 */ this.supprimer = (this.modifier = this.imprimer = this.detail = Boolean.valueOf(true));
                /* 508 */ notifySuccess();
            } else {
                /* 511 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 514 */ notifyFail(e);
        }
    }

    public void initPrinter(Repartitionarticle r) {
        /* 519 */ this.repartitionarticle = r;
        /* 520 */ print();
    }

    public void initPrinterBon(Repartitionarticle r) {
        /* 524 */ this.repartitionarticle = r;
        /* 525 */ printBonLivraison();
    }

    public void initEdit(Repartitionarticle r) {
        /* 529 */ this.repartitionarticle = r;
        /* 530 */ prepareEdit();
    }

    public void initView(Repartitionarticle r) {
        /* 534 */ this.repartitionarticle = r;
        /* 535 */ prepareview();
    }

    public void initDelete(Repartitionarticle r) {
        /* 539 */ this.repartitionarticle = r;
        /* 540 */ delete();
    }

    public void print() {
        try {
            /* 545 */ if (!Utilitaires.isAccess(Long.valueOf(54L))) {
                /* 546 */ notifyError("acces_refuse");
                /* 547 */ this.repartitionarticle = null;
                /* 548 */ return;
            }

            /* 551 */ if (this.repartitionarticle != null) {
                /* 552 */ this.printDialogTitle = this.routine.localizeMessage("livraisonclient");

                /* 556 */ RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                /* 558 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 561 */ notifyFail(e);
        }
    }

    public void printBonLivraison() {
        try {
            /* 567 */ if (!Utilitaires.isAccess(Long.valueOf(54L))) {
                /* 568 */ notifyError("acces_refuse");
                /* 569 */ this.repartitionarticle = null;
                /* 570 */ return;
            }

            /* 573 */ if (this.repartitionarticle != null) {
                /* 574 */ this.printDialogTitle = this.routine.localizeMessage("bon_de_livraison");

                /* 578 */ RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                /* 580 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 583 */ notifyFail(e);
        }
    }

    public void removeArticle(int index) {
        try {
            /* 589 */ boolean trouve = false;

            /* 591 */ JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            /* 593 */ e.printStackTrace();
            /* 594 */ JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal(List<Lignerepartitionarticle> lignerepartitionarticles) {
        /* 599 */ Double resultat = Double.valueOf(0.0D);
        /* 600 */ int i = 0;
        /* 601 */ for (Lignerepartitionarticle lra : lignerepartitionarticles) {
            /* 602 */ resultat = Double.valueOf(resultat.doubleValue() + lra.getIdmagasinlot().getIdlot().getPrixunitaire().doubleValue() * lra.getQuantite().doubleValue());
            /* 603 */ ((Lignerepartitionarticle) lignerepartitionarticles.get(i)).setQuantitemultiple(Double.valueOf(lra.getUnite().doubleValue() * lra.getQuantite().doubleValue()));
            /* 604 */ ((Lignerepartitionarticle) lignerepartitionarticles.get(i)).setQuantitereduite(Double.valueOf(((Lignerepartitionarticle) lignerepartitionarticles.get(i)).getQuantitemultiple().doubleValue() / lra.getIdmagasinlot().getIdlot().getIdarticle().getUnite().doubleValue()));
            /* 605 */ i++;
        }
        /* 607 */ return resultat;
    }

    public void updateTotal() {
        try {
            /* 612 */ this.total = calculTotal(this.lignerepartitionarticles);
            /* 613 */ this.repartitionarticle.setMontanttotal(this.total);
        } catch (Exception e) {
            /* 615 */ e.printStackTrace();
        }
    }

    public boolean findPositionZero(Lignerepartitionarticle l) {
        /* 620 */ int i = this.lignerepartitionarticles_1.indexOf(l);
        /* 621 */ System.err.println("position : " + i);

        /* 623 */ if (this.state_zero == 1) {
            /* 624 */ return true;
        }
        /* 626 */ return false;
    }

    public void updateTotaux() {
    }

    public void notifyError(String message) {
        /* 639 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 640 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 644 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 645 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 646 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 650 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 651 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 652 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
