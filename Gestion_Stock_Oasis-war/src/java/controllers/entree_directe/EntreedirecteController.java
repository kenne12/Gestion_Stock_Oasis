package controllers.entree_directe;

import entities.Article;
import entities.Fournisseur;
import entities.Lignelivraisonfournisseur;
import entities.Lignemvtstock;
import entities.Livraisonfournisseur;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import entities.Unite;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.Printer;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class EntreedirecteController extends AbstractEntreedirecteController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(46L)) {
                notifyError("acces_refuse");
                return;
            }

            this.mode = "Create";

            this.article = null;
            this.fournisseur = new Fournisseur();
            this.fournisseurToSave = new Fournisseur();
            this.livraisonfournisseur = new Livraisonfournisseur();
            this.livraisonfournisseur.setDatelivraison(new Date());
            this.lignelivraisonfournisseurs.clear();

            RequestContext.getCurrentInstance().execute("PF('StockCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareCreateCommande() {
        this.magasin = new Magasin();
        this.unite = new Unite();
        this.lignelivraisonfournisseur = new Lignelivraisonfournisseur();
        this.lot = new Lot();
        this.magasinarticle = new Magasinarticle();
        this.cout_quantite = 0.0D;
        this.lignelivraisonfournisseur.setPrixachat(0.0D);
        this.lignelivraisonfournisseur.setPrixachat(0.0D);
        this.libelle_article = "-";
    }

    public void filterProductByMagasin() {
        try {
            this.magasins.clear();
            if (this.magasin.getIdmagasin() != null) {
                this.magasinarticles = this.magasinarticleFacadeLocal.findByIdmagasinProductIsActif(this.magasin.getIdmagasin(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareEdit() {
        try {
            if (!this.livraisonfournisseur.getLivraisondirecte()) {
                notifyError("livraison_directe");
                return;
            }

            if (!Utilitaires.isAccess(46L)) {
                notifyError("acces_refuse");
                return;
            }

            this.mode = "Edit";
            if (this.livraisonfournisseur != null) {
                this.lignelivraisonfournisseurs = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
                this.fournisseur = this.livraisonfournisseur.getIdfournisseur();
                this.total = this.livraisonfournisseur.getMontant();
                this.mvtstock = this.livraisonfournisseur.getIdmvtstock();
                /* 115 */ RequestContext.getCurrentInstance().execute("PF('StockCreateDialog').show()");
                /* 116 */ return;
            }
            notifyError("not_row_selected");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.livraisonfournisseur != null) {
                this.lignelivraisonfournisseurs = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
                if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    notifyError("liste_article_vide");
                    return;
                }
                RequestContext.getCurrentInstance().execute("PF('StockDetailDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectProduct() {
        if (this.magasinarticle != null) {
            this.famille = this.magasinarticle.getIdarticle().getIdfamille();
            this.article = this.magasinarticle.getIdarticle();
            this.libelle_article = this.article.getLibelle();
            this.lot.setNumero("-");
            this.lot.setDateperemption(new Date());
            this.lot.setDatefabrication(new Date());
            this.lot.setDateenregistrement(new Date());
            this.unite = this.magasinarticle.getIdarticle().getIdunite();
            this.lignelivraisonfournisseur.setUnite(this.magasinarticle.getUnite());
            this.lignelivraisonfournisseur.setQuantite(1d);
            this.lignelivraisonfournisseur.setQuantitemultiple(1d);
            this.lignelivraisonfournisseur.setPrixachat(this.magasinarticle.getIdarticle().getCoutachat());
            this.magasin = this.magasinarticle.getIdmagasin();
        }
    }

    public void generateLotNumber() {
        try {
            if (this.magasinarticle != null) {
                List<Lot> listLot = lotFacadeLocal.findByArticle(magasinarticle.getIdarticle().getIdarticle());

                String numero = "Lot_" + magasinarticle.getIdarticle().getCode() + "_";
                if (!listLot.isEmpty()) {
                    numero += "" + (listLot.size() + 1);
                } else {
                    numero += "1";
                }

                lot.setNumero(numero);
                lot.setDateperemption(new Date());
                lot.setDatefabrication(new Date());
                lot.setDateenregistrement(new Date());
            } else {
                JsfUtil.addErrorMessage("Veuillez selectionner un article");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterProduit() {
        try {
            this.articles.clear();
            this.article = new Article();
            this.lot = new Lot();
            if (this.famille.getIdfamille() != null) {
                this.articles = this.articleFacadeLocal.findByFamille(this.famille.getIdfamille());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {

            if (this.mode.equals("Create")) {
                if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    calculTotal();
                    this.fournisseur = this.fournisseurFacadeLocal.find(this.fournisseur.getIdfournisseur());

                    this.ut.begin();

                    this.mvtstock = new Mvtstock();
                    this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    String codeMvt = "MVT";
                    Long nextMvt = this.mvtstock.getIdmvtstock();
                    codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);

                    this.mvtstock.setCode(codeMvt);
                    this.mvtstock.setClient(" ");
                    this.mvtstock.setFournisseur(this.fournisseur.getNom());
                    this.mvtstock.setType(" ");
                    this.mvtstock.setMagasin(" ");
                    this.mvtstock.setDatemvt(this.livraisonfournisseur.getDatelivraison());
                    this.mvtstockFacadeLocal.create(this.mvtstock);

                    this.livraisonfournisseur.setIdlivraisonfournisseur(this.livraisonfournisseurFacadeLocal.nextVal());
                    String code = "LIV";
                    Long nextPayement = this.livraisonfournisseur.getIdlivraisonfournisseur();
                    code = Utilitaires.genererCodeStock(code, nextPayement);

                    this.livraisonfournisseur.setCode(code);
                    this.livraisonfournisseur.setIdfournisseur(this.fournisseur);
                    this.livraisonfournisseur.setMontant(this.total);
                    this.livraisonfournisseur.setLivraisondirecte(true);
                    this.livraisonfournisseur.setIdmvtstock(this.mvtstock);
                    this.livraisonfournisseurFacadeLocal.create(this.livraisonfournisseur);

                    for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                        Lot lotTemp = this.lotFacadeLocal.findByCode(llf.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getIdarticle(), llf.getIdlot().getNumero());
                        Double resteMvt = 0.0D;
                        boolean createLot = false;
                        if (lotTemp == null) {
                            lotTemp = llf.getIdlot();
                            lotTemp.setIdlot(this.lotFacadeLocal.nextVal());
                            lotTemp.setQuantite(0.0D);
                            lotTemp.setQuantitemultiple(0.0D);
                            lotTemp.setQuantitereduite(0.0D);
                            lotTemp.setEtat(true);
                            lotTemp.setPrixachat(llf.getPrixachat());
                            lotTemp.setPrixunitaire(llf.getPrixachat());
                            lotTemp.setUniteentree(1.0D);
                            lotTemp.setUnitesortie(1.0D);
                            lotTemp.setQuantitevirtuelle(0.0D);
                            this.lotFacadeLocal.create(lotTemp);

                            Magasinlot ml = llf.getIdmagasinlot();
                            ml.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                            ml.setIdlot(lotTemp);
                            ml.setQuantite(llf.getQuantite());
                            ml.setQuantitemultiple(llf.getQuantitemultiple());
                            ml.setQuantitereduite(llf.getQuantitereduite());
                            ml.setUnite(lotTemp.getIdarticle().getUnite());
                            ml.setQuantitevirtuelle(lotTemp.getIdarticle().getQuantitevirtuelle());
                            ml.setEtat(true);
                            this.magasinlotFacadeLocal.create(ml);

                            llf.setIdlot(lotTemp);
                            llf.setIdmagasinlot(ml);
                            resteMvt = llf.getQuantitemultiple();
                        } else {
                            createLot = true;
                            Magasinlot mlTemp1 = this.magasinlotFacadeLocal.findByIdmagasinIdlot(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasin().getIdmagasin().intValue(), lotTemp.getIdlot().longValue());
                            if (mlTemp1 == null) {
                                createLot = false;
                                Magasinlot ml = llf.getIdmagasinlot();
                                ml.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                                ml.setIdlot(lotTemp);
                                ml.setQuantite(llf.getQuantite());
                                ml.setQuantitemultiple(llf.getQuantitemultiple());
                                ml.setQuantitereduite(llf.getQuantitereduite());
                                ml.setUnite(lotTemp.getIdarticle().getUnite());
                                ml.setQuantitevirtuelle(0.0D);
                                ml.setEtat(true);
                                this.magasinlotFacadeLocal.create(ml);
                                llf.setIdmagasinlot(ml);
                            }
                        }

                        llf.setIdlignelivraisonfournisseur(this.lignelivraisonfournisseurFacadeLocal.nextVal());
                        llf.setIdlivraisonfournisseur(this.livraisonfournisseur);
                        this.lignelivraisonfournisseurFacadeLocal.create(llf);

                        Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        maTemp.setQuantite((maTemp.getQuantite() + llf.getQuantite()));
                        maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() + llf.getQuantitemultiple()));
                        maTemp.setQuantitereduite((maTemp.getQuantitereduite() + llf.getQuantitereduite()));
                        this.magasinarticleFacadeLocal.edit(maTemp);

                        if (createLot) {
                            Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinlot());
                            mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() + llf.getQuantitemultiple()));
                            mlTemp.setQuantite((mlTemp.getQuantite() + llf.getQuantite()));
                            mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() + llf.getQuantitereduite()));
                            this.magasinlotFacadeLocal.edit(mlTemp);
                            resteMvt = mlTemp.getQuantitemultiple();
                        }

                        Lignemvtstock lmvts = new Lignemvtstock();
                        lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        lmvts.setIdmvtstock(this.mvtstock);
                        lmvts.setIdlot(llf.getIdlot());
                        lmvts.setIdmagasinlot(llf.getIdmagasinlot());
                        lmvts.setQteentree(llf.getQuantitemultiple());

                        lmvts.setQtesortie(0.0D);
                        lmvts.setReste(resteMvt);
                        lmvts.setFournisseur(maTemp.getIdmagasin().getNom());
                        lmvts.setClient(" ");
                        lmvts.setType("ENTREE");
                        lmvts.setMagasin(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasin().getNom());
                        this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'entrée du stock N° : ", SessionMBean.getUserAccount());
                    this.ut.commit();

                    this.livraisonfournisseur = null;

                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('StockCreateDialog').hide()");
                    return;
                }
                notifyError("liste_article_vide");
                return;
            }
            if (this.livraisonfournisseur != null) {
                Livraisonfournisseur s1 = this.livraisonfournisseurFacadeLocal.find(this.livraisonfournisseur.getIdlivraisonfournisseur());

                this.fournisseur = this.fournisseurFacadeLocal.find(this.fournisseur.getIdfournisseur());
                this.livraisonfournisseur.setIdfournisseur(this.fournisseur);
                this.livraisonfournisseur.setMontant(this.total);
                calculTotal();
                this.ut.begin();

                this.livraisonfournisseurFacadeLocal.edit(this.livraisonfournisseur);
                if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    for (Lignelivraisonfournisseur s : this.lignelivraisonfournisseurs) {
                        if (s.getIdlignelivraisonfournisseur() != 0L) {
                            Lignelivraisonfournisseur sp = this.lignelivraisonfournisseurFacadeLocal.find(s.getIdlignelivraisonfournisseur());
                            if (s.getQuantite() != sp.getQuantite()) {
                                Article pro = sp.getIdlot().getIdarticle();
                                pro.setQuantitestock((pro.getQuantitestock() - sp.getQuantite() + s.getQuantite()));
                                pro.setQuantitereduite((pro.getQuantitemultiple() - sp.getQuantitereduite() + s.getQuantite() * s.getUnite() / pro.getUnite()));
                                pro.setQuantitemultiple((pro.getQuantitemultiple() - sp.getQuantitemultiple() + s.getQuantite() * s.getUnite()));
                                this.articleFacadeLocal.edit(pro);

                                Lot l = sp.getIdlot();
                                l.setQuantite(l.getQuantite() - sp.getQuantite() + s.getQuantite());
                                this.lotFacadeLocal.edit(l);

                                Lignemvtstock lmvts = new Lignemvtstock();
                                lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                                lmvts.setIdmvtstock(this.mvtstock);
                                lmvts.setIdlot(s.getIdlot());
                                if (s.getQuantitemultiple() > sp.getQuantitemultiple()) {
                                    lmvts.setQteentree((s.getQuantitemultiple() - sp.getQuantitemultiple()));
                                    lmvts.setQtesortie(0.0D);
                                    lmvts.setReste(s.getIdlot().getQuantitemultiple());
                                } else {
                                    lmvts.setQteentree(0.0D);
                                    lmvts.setQtesortie(sp.getQuantitemultiple() - s.getQuantitemultiple());
                                    lmvts.setReste(s.getIdlot().getQuantite());
                                }
                                this.lignemvtstockFacadeLocal.create(lmvts);
                            }
                            this.lignelivraisonfournisseurFacadeLocal.edit(s);
                        } else {
                            s.setIdlignelivraisonfournisseur(this.lignelivraisonfournisseurFacadeLocal.nextVal());
                            s.setIdlivraisonfournisseur(this.livraisonfournisseur);
                            this.lignelivraisonfournisseurFacadeLocal.create(s);

                            s.getIdlot().setIdarticle(this.articleFacadeLocal.find(s.getIdlot().getIdarticle().getIdarticle()));
                            s.getIdlot().getIdarticle().setQuantitestock((s.getIdlot().getIdarticle().getQuantitestock() + s.getQuantite()));
                            this.articleFacadeLocal.edit(s.getIdlot().getIdarticle());

                            s.setIdlot(this.lotFacadeLocal.find(s.getIdlot().getIdlot()));
                            s.getIdlot().setQuantite((s.getIdlot().getQuantite() + s.getQuantite()));
                            this.lotFacadeLocal.edit(s.getIdlot());

                            Lignemvtstock lmvts = new Lignemvtstock();
                            lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            lmvts.setIdmvtstock(this.mvtstock);
                            lmvts.setIdlot(s.getIdlot());
                            lmvts.setQteentree(s.getQuantitemultiple());
                            lmvts.setQtesortie(0.0D);
                            lmvts.setType("ENTREE");
                            lmvts.setFournisseur(this.fournisseur.getNom());
                            lmvts.setMagasin("MAGASIN CENTRAL");
                            lmvts.setClient(" ");
                            lmvts.setReste(s.getIdlot().getQuantitemultiple());
                            this.lignemvtstockFacadeLocal.create(lmvts);
                        }
                    }
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification de l'entrée en stock N° : " + this.livraisonfournisseur.getCode() + " ; Ancien Montant : " + s1.getMontant() + " Nouveau montant : " + this.livraisonfournisseur.getMontant(), SessionMBean.getUserAccount());

                    this.ut.commit();
                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('StockCreateDialog').hide()");
                    return;
                }
                notifyError("liste_article_vide");
                return;
            }
            notifyError("not_row_selected");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void createFournisseur() {
        this.fournisseurToSave.setIdfournisseur(this.fournisseurFacadeLocal.nextVal());
        this.fournisseurFacadeLocal.create(this.fournisseurToSave);
        Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du fournisseur : " + this.fournisseurToSave.getNom(), SessionMBean.getUserAccount());
        this.fournisseur = fournisseurToSave;
        RequestContext.getCurrentInstance().execute("PF('FournisseurCreerDialog').hide()");
    }

    public void delete() {
        try {
            if (this.livraisonfournisseur != null) {
                if (!Utilitaires.isAccess(47L)) {
                    notifyError("acces_refuse");
                    return;
                }

                this.ut.begin();
                List<Lignelivraisonfournisseur> temp = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
                for (Lignelivraisonfournisseur llf : temp) {
                    Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                    maTemp.setQuantite((maTemp.getQuantite() - llf.getQuantite()));
                    maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - llf.getQuantite() * llf.getUnite()));
                    maTemp.setQuantitereduite((maTemp.getQuantitereduite() - llf.getQuantitereduite()));
                    this.magasinarticleFacadeLocal.edit(maTemp);

                    Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinlot());
                    mlTemp.setQuantite((mlTemp.getQuantite() - llf.getQuantite()));
                    mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - llf.getQuantitemultiple()));
                    mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - llf.getQuantitereduite()));

                    this.magasinlotFacadeLocal.edit(mlTemp);
                    this.lignelivraisonfournisseurFacadeLocal.remove(llf);
                }

                this.livraisonfournisseurFacadeLocal.remove(this.livraisonfournisseur);

                Mvtstock mTemp = this.livraisonfournisseur.getIdmvtstock();
                List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(mTemp.getIdmvtstock());
                for (Lignemvtstock l : lmvt) {
                    this.lignemvtstockFacadeLocal.remove(l);
                }
                this.mvtstockFacadeLocal.remove(mTemp);

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de l'entrée directe en stock : " + this.livraisonfournisseur.getCode() + " Montant : " + this.livraisonfournisseur.getMontant(), SessionMBean.getUserAccount());
                this.ut.commit();

                this.livraisonfournisseur = null;
                notifySuccess();
                return;
            }
            notifyError("not_row_selected");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(46L)) {
                notifyError("acces_refuse");
                return;
            }

            if (this.livraisonfournisseur != null) {
                Map map = new HashMap();
                map.put("idlivraisonfournisseur", this.livraisonfournisseur.getIdlivraisonfournisseur());
                Printer.print("/reports/ireport/entree_directe.jasper", map);

                RequestContext.getCurrentInstance().execute("PF('StockImprimerDialog').show()");
                return;
            }
            notifyError("not_row_selected");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Livraisonfournisseur l) {
        /* 465 */ this.livraisonfournisseur = l;
        /* 466 */ print();
    }

    public void initEdit(Livraisonfournisseur l) {
        /* 470 */ this.livraisonfournisseur = l;
        /* 471 */ prepareEdit();
    }

    public void initView(Livraisonfournisseur l) {
        /* 475 */ this.livraisonfournisseur = l;
        /* 476 */ prepareview();
    }

    public void initDelete(Livraisonfournisseur l) {
        /* 480 */ this.livraisonfournisseur = l;
        /* 481 */ delete();
    }

    public void addProduit() {
        try {
            Lignelivraisonfournisseur l = this.lignelivraisonfournisseur;
            l.setIdlignelivraisonfournisseur(0L);

            Lot lotTemp = this.lot;
            this.lot.setPrixunitaire(lotTemp.getPrixachat());
            lotTemp.setIdarticle(this.magasinarticle.getIdarticle());
            l.setIdlot(lotTemp);

            Magasinlot ml = new Magasinlot();
            Magasinarticle ma = this.magasinarticle;
            ml.setIdmagasinarticle(ma);
            l.setIdmagasinlot(ml);

            boolean drapeau = false;
            for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                if ((llf.getIdmagasinlot().getIdmagasinarticle().equals(ma)) && (llf.getIdlot().getNumero().equals(l.getIdlot().getNumero()))) {
                    drapeau = true;
                    break;
                }
            }

            if (drapeau) {
                notifyError("article_existant_dans_le_tableau");
                return;
            }
            Unite u = this.unite;
            l.setIdunite(u);

            this.lignelivraisonfournisseurs.add(l);
            this.total = calculTotal();

            this.lignelivraisonfournisseur = new Lignelivraisonfournisseur();
            this.article = new Article();
            RequestContext.getCurrentInstance().execute("PF('AddarticleCreateDialog').hide()");
            JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void removeProduit(Lignelivraisonfournisseur lignelivraisonfournisseur) {
        try {
            int i = 0;
            for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                if (llf.getIdlot().getIdlot().equals(lignelivraisonfournisseur.getIdlot().getIdlot())) {
                    if (llf.getIdlignelivraisonfournisseur() != 0L) {
                        /* 537 */ this.lignelivraisonfournisseurFacadeLocal.remove(llf);
                        /* 538 */ this.livraisonfournisseur.setMontant((this.livraisonfournisseur.getMontant() - this.livraisonfournisseur.getMontant() * llf.getQuantite()));
                        /* 539 */ this.livraisonfournisseurFacadeLocal.edit(this.livraisonfournisseur);

                        /* 541 */ Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        /* 542 */ maTemp.setQuantite((maTemp.getQuantite() - llf.getQuantite()));
                        /* 543 */ maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - llf.getQuantite() * llf.getUnite()));
                        /* 544 */ maTemp.setQuantitereduite((maTemp.getQuantitereduite() - llf.getQuantitereduite()));
                        /* 545 */ this.magasinarticleFacadeLocal.edit(maTemp);

                        /* 547 */ Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinlot());
                        /* 548 */ mlTemp.setQuantite((mlTemp.getQuantite() - llf.getQuantite()));
                        /* 549 */ mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - llf.getQuantitemultiple()));
                        /* 550 */ mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - llf.getQuantitereduite()));
                    }

                    this.lignelivraisonfournisseurs.remove(i);
                    break;
                }
                i++;
            }
            this.total = calculTotal();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    private Double calculTotal() {
        Double resultat = 0.0D;

        if (!this.lignelivraisonfournisseurs.isEmpty()) {
            int i = 0;
            for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                resultat += (llf.getPrixachat() * llf.getQuantite());
                this.lignelivraisonfournisseurs.get(i).setQuantitemultiple(llf.getQuantite() * llf.getUnite());
                this.lignelivraisonfournisseurs.get(i).setQuantitereduite(((this.lignelivraisonfournisseurs.get(i)).getQuantitemultiple() / llf.getIdlot().getIdarticle().getUnite()));
                i++;
            }
        }
        this.livraisonfournisseur.setMontant(resultat);
        return resultat;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            this.cout_quantite = 0.0D;
            if (this.lignelivraisonfournisseur.getQuantite() != null) {
                if (this.lignelivraisonfournisseur.getUnite() != null) {
                    this.lignelivraisonfournisseur.setQuantitemultiple(this.lignelivraisonfournisseur.getQuantite() * this.lignelivraisonfournisseur.getUnite());
                }
                if (this.lignelivraisonfournisseur.getPrixachat() != null) {
                    this.cout_quantite = (this.lignelivraisonfournisseur.getPrixachat() * this.lignelivraisonfournisseur.getQuantite());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatedata() {
        try {
            if (this.article.getIdarticle() != null) {
                this.famille = this.article.getIdfamille();
                /* 608 */ this.unite = this.article.getIdunite();
                /* 609 */ this.lignelivraisonfournisseur.setUnite(this.article.getUnite());
                /* 610 */ this.lot = new Lot();
                /* 611 */ this.lot.setQuantite(0.0D);
                /* 612 */ this.lignelivraisonfournisseur.setPrixachat(0.0D);

                /* 614 */ this.lots = this.lotFacadeLocal.findByArticle(this.article.getIdarticle(), this.article.getPerissable());
                if (this.lots.size() == 1) {
                    this.lot = ((Lot) this.lots.get(0));
                    this.lignelivraisonfournisseur.setPrixachat(this.lot.getPrixachat());
                    this.lignelivraisonfournisseur.setUnite(this.lot.getIdarticle().getUnite());
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatedataLot() {
        try {
            if (this.lot != null) {
                this.lignelivraisonfournisseur.setPrixachat(this.lot.getPrixachat());
                this.lignelivraisonfournisseur.setUnite(this.lot.getIdarticle().getUnite());
            }
        } catch (Exception e) {
            /* 634 */ e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        /* 639 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 640 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 641 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 645 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 646 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 647 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 651 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 652 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 653 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
