package controllers.entree_directe;

import entities.Article;
import entities.Fournisseur;
import entities.Lignelivraisonfournisseur;
import entities.Lignemvtstock;
import entities.Livraisonfournisseur;
import entities.Lot;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Mvtstock;
import enumeration.ModeEntreSorti;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class EntreedirecteController extends AbstractEntreedirecteController implements Serializable {

    @PostConstruct
    private void init() {
        this.magasins = SessionMBean.getMagasins();
        this.livraisonfournisseurs = this.livraisonfournisseurFacadeLocal
                .findAllRange(SessionMBean.getMagasin().getIdmagasin(),
                        SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin(), true);
    }

    public void prepareCreate() {
        try {

            if (Utilitaires.isDayClosed()) {
                notifyError("journee_cloturee");
                return;
            }

            if (!Utilitaires.isAccess(33L)) {
                notifyError("acces_refuse");
                return;
            }

            this.mode = "Create";

            this.article = new Article();
            this.fournisseur = new Fournisseur();
            this.fournisseurToSave = new Fournisseur();
            this.livraisonfournisseur = new Livraisonfournisseur();
            this.livraisonfournisseur.setDatelivraison(SessionMBean.getJournee().getDateJour());
            this.lignelivraisonfournisseurs.clear();
            this.magasinarticles = this.magasinarticleFacadeLocal.findByIdmagasinProductIsActif(this.magasin.getIdmagasin(), true);
            RequestContext.getCurrentInstance().execute("PF('StockCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareCreateCommande() {
        this.lignelivraisonfournisseur = new Lignelivraisonfournisseur();
        this.lot = new Lot(0l);
        magasinlot = new Magasinlot(0l);
        this.magasinarticle = new Magasinarticle();
        this.lignelivraisonfournisseur.setPrixachat(0.0);
        this.lignelivraisonfournisseur.setMontantTotal(0);
        this.libelle_article = "-";
        perempted = false;
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

            if (Utilitaires.isDayClosed()) {
                notifyError("journee_cloturee");
                return;
            }

            if (!this.livraisonfournisseur.getLivraisondirecte()) {
                notifyError("livraison_directe");
                return;
            }

            if (!Utilitaires.isAccess(33L)) {
                notifyError("acces_refuse");
                return;
            }

            this.mode = "Edit";
            if (this.livraisonfournisseur != null) {
                this.lignelivraisonfournisseurs = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
                this.fournisseur = this.livraisonfournisseur.getIdfournisseur();
                this.total = this.livraisonfournisseur.getMontant();
                this.mvtstock = this.livraisonfournisseur.getIdmvtstock();
                RequestContext.getCurrentInstance().execute("PF('StockCreateDialog').show()");
                return;
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
        perempted = false;
        if (this.magasinarticle != null) {
            this.famille = this.magasinarticle.getIdarticle().getIdfamille();
            this.article = this.magasinarticle.getIdarticle();
            if (article.getPerissable()) {
                perempted = true;
            }

            lignelivraisonfournisseur.setUnite(magasinarticle.getUnite());
            lignelivraisonfournisseur.setIdunite(magasinarticle.getIdarticle().getIdunite());
            lignelivraisonfournisseur.setQuantite(1d);
            lignelivraisonfournisseur.setQuantitemultiple(magasinarticle.getUnite());
            lignelivraisonfournisseur.setPrixachat(magasinarticle.getIdarticle().getCoutachat());
            this.lignelivraisonfournisseur.setModeVente(ModeEntreSorti.VENTE_EN_GROS);

            magasinlots = magasinlotFacadeLocal.findByIdMagasinIdArticle(this.magasinarticle.getIdmagasinarticle());
            if (!magasinlots.isEmpty()) {
                magasinlot = magasinlots.get(magasinlots.size() - 1);
                lot = magasinlot.getIdlot();
                lignelivraisonfournisseur.setMontantTotal(this.lignelivraisonfournisseur.getPrixachat());
            } else {
                lot.setIdlot(0l);
                lot.setNumero("-");
                lot.setDateperemption(Date.from(Instant.now()));
                lot.setDatefabrication(Date.from(Instant.now()));
                lot.setDateenregistrement(Date.from(Instant.now()));
            }
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
                lot.setIdlot(0l);
                lot.setNumero(numero);
                lot.setDateperemption(Date.from(Instant.now()));
                lot.setDatefabrication(Date.from(Instant.now()));
                lot.setDateenregistrement(Date.from(Instant.now()));
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
                this.articles = this.articleFacadeLocal.findByFamille(famille.getIdfamille());
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

                    String code = "S-" + SessionMBean.getAnnee().getNom() + "-" + SessionMBean.getMois().getIdmois().getNom().toUpperCase().substring(0, 3);
                    Long nextStock = livraisonfournisseurFacadeLocal.nextVal(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois());
                    code = Utilitaires.genererCodeStock(code, nextStock);

                    this.livraisonfournisseur.setCode(code);
                    this.livraisonfournisseur.setIdfournisseur(this.fournisseur);
                    this.livraisonfournisseur.setMontant(this.total);
                    this.livraisonfournisseur.setLivraisondirecte(true);
                    this.livraisonfournisseur.setIdmvtstock(this.mvtstock);
                    this.livraisonfournisseur.setIdmagasin(SessionMBean.getMagasin());
                    this.livraisonfournisseur.setIdUtilisateur(SessionMBean.getUserAccount().getIdutilisateur());
                    this.livraisonfournisseur.setIdlivraisonfournisseur(livraisonfournisseurFacadeLocal.nextVal());
                    this.livraisonfournisseurFacadeLocal.create(this.livraisonfournisseur);
                    double qteAvant = 0;
                    for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                        Lot lotTemp = this.lotFacadeLocal.findByCode(llf.getIdmagasinlot().getIdmagasinarticle().getIdarticle().getIdarticle(), llf.getIdlot().getNumero());
                        Double resteMvt = 0d;
                        boolean createLot = false;
                        if (lotTemp == null) {
                            lotTemp = llf.getIdlot();
                            lotTemp.setIdlot(this.lotFacadeLocal.nextVal());
                            lotTemp.setQuantite(0d);
                            lotTemp.setQuantitemultiple(0d);
                            lotTemp.setQuantitereduite(0d);
                            lotTemp.setEtat(true);
                            lotTemp.setPrixachat(llf.getPrixachat());
                            lotTemp.setPrixunitaire(llf.getPrixachat());
                            lotTemp.setUniteentree(1.0);
                            lotTemp.setUnitesortie(1.0);
                            lotTemp.setQuantitevirtuelle(0.0);
                            this.lotFacadeLocal.create(lotTemp);

                            Magasinlot ml = llf.getIdmagasinlot();
                            ml.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                            ml.setIdlot(lotTemp);
                            ml.setQuantite(llf.getQuantitereduite());
                            ml.setQuantitemultiple(llf.getQuantitemultiple());
                            ml.setQuantitereduite(llf.getQuantitereduite());
                            ml.setUnite(lotTemp.getIdarticle().getUnite());
                            ml.setQuantitevirtuelle(lotTemp.getIdarticle().getQuantitevirtuelle());
                            ml.setEtat(true);
                            this.magasinlotFacadeLocal.create(ml);

                            llf.setIdlot(lotTemp);
                            llf.setIdmagasinlot(ml);
                            resteMvt = llf.getQuantitereduite();
                            qteAvant = 0;
                        } else {
                            createLot = true;
                            Magasinlot mlTemp1 = this.magasinlotFacadeLocal.findByIdmagasinIdlot(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasin().getIdmagasin().intValue(), lotTemp.getIdlot().longValue());
                            if (mlTemp1 == null) {
                                createLot = false;
                                Magasinlot ml = llf.getIdmagasinlot();
                                qteAvant = 0;
                                ml.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                                ml.setIdlot(lotTemp);
                                ml.setQuantite(llf.getQuantitereduite());
                                ml.setQuantitemultiple(llf.getQuantitemultiple());
                                ml.setQuantitereduite(llf.getQuantitereduite());
                                ml.setUnite(lotTemp.getIdarticle().getUnite());
                                ml.setQuantitevirtuelle(0.0);
                                ml.setEtat(true);
                                this.magasinlotFacadeLocal.create(ml);
                                llf.setIdmagasinlot(ml);
                            } else {
                                qteAvant = mlTemp1.getQuantitereduite();
                            }
                        }

                        llf.setIdlignelivraisonfournisseur(this.lignelivraisonfournisseurFacadeLocal.nextVal());
                        llf.setIdlivraisonfournisseur(this.livraisonfournisseur);
                        this.lignelivraisonfournisseurFacadeLocal.create(llf);

                        Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        maTemp.setQuantite((maTemp.getQuantite() + llf.getQuantitereduite()));
                        maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() + llf.getQuantitemultiple()));
                        maTemp.setQuantitereduite((maTemp.getQuantitereduite() + llf.getQuantitereduite()));
                        this.magasinarticleFacadeLocal.edit(maTemp);

                        if (createLot) {
                            Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinlot());
                            mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() + llf.getQuantitemultiple()));
                            mlTemp.setQuantite((mlTemp.getQuantite() + llf.getQuantitereduite()));
                            mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() + llf.getQuantitereduite()));
                            this.magasinlotFacadeLocal.edit(mlTemp);
                            resteMvt = mlTemp.getQuantitereduite();
                        }

                        Lignemvtstock lmvts = new Lignemvtstock();
                        lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        lmvts.setIdmvtstock(this.mvtstock);
                        lmvts.setIdlot(llf.getIdlot());
                        lmvts.setIdmagasinlot(llf.getIdmagasinlot());
                        lmvts.setQteentree(llf.getQuantite());

                        lmvts.setQtesortie(0.0);
                        lmvts.setQteAvant(qteAvant);
                        lmvts.setReste(resteMvt);
                        lmvts.setFournisseur(maTemp.getIdmagasin().getNom());
                        lmvts.setClient(" ");
                        lmvts.setType("ENTREE");
                        lmvts.setMagasin(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasin().getNom());
                        lmvts.setLignelivraisonfournisseur(llf);
                        this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'entrée du stock N° : ", SessionMBean.getUserAccount());
                    this.ut.commit();

                    
                    this.livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getMois().getDateDebut(), SessionMBean.getMois().getDateFin(), true);
                    this.livraisonfournisseur = new Livraisonfournisseur();
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
                    for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                        if (llf.getIdlignelivraisonfournisseur() != 0L) {
                            Lignelivraisonfournisseur llfOld = this.lignelivraisonfournisseurFacadeLocal.find(llf.getIdlignelivraisonfournisseur());
                            if (!Objects.equals(llf.getQuantite(), llfOld.getQuantite())) {

                                Magasinarticle ma = this.magasinarticleFacadeLocal.find(llfOld.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                                ma.setQuantite((ma.getQuantite() - llfOld.getQuantitereduite()) + llf.getQuantitereduite());
                                ma.setQuantitemultiple((ma.getQuantitemultiple() - llfOld.getQuantitemultiple()) + llf.getQuantitemultiple());
                                ma.setQuantitereduite((ma.getQuantitereduite() - llfOld.getQuantitereduite()) + llf.getQuantitereduite());
                                this.magasinarticleFacadeLocal.edit(ma);

                                Magasinlot ml = this.magasinlotFacadeLocal.find(llfOld.getIdmagasinlot().getIdmagasinlot());
                                ml.setQuantite((ml.getQuantite() - llfOld.getQuantitereduite()) + llf.getQuantitereduite());
                                ml.setQuantitemultiple((ml.getQuantitemultiple() - llfOld.getQuantitemultiple()) + llf.getQuantitemultiple());
                                ml.setQuantitereduite((ml.getQuantitereduite() - llfOld.getQuantitereduite()) + llf.getQuantitereduite());
                                this.magasinlotFacadeLocal.edit(ml);

                                Lignemvtstock lmvts = lignemvtstockFacadeLocal.findByIdmvtIdLot(livraisonfournisseur.getIdmvtstock().getIdmvtstock(), llf.getIdlot().getIdlot());
                                lmvts.setQtesortie(llf.getQuantitereduite());
                                if (llf.getQuantitemultiple() > llfOld.getQuantitemultiple()) {
                                    lmvts.setQteentree(llf.getQuantitereduite() - llfOld.getQuantitereduite());
                                    lmvts.setQtesortie(0d);
                                    lmvts.setReste(lmvts.getReste() + (llf.getQuantitereduite() - llfOld.getQuantitereduite()));
                                } else {
                                    lmvts.setReste(lmvts.getReste() - (llfOld.getQuantitereduite() - llf.getQuantitereduite()));
                                }
                                this.lignemvtstockFacadeLocal.edit(lmvts);
                            }
                            this.lignelivraisonfournisseurFacadeLocal.edit(llf);
                        } else {
                            llf.setIdlignelivraisonfournisseur(this.lignelivraisonfournisseurFacadeLocal.nextVal());
                            llf.setIdlivraisonfournisseur(this.livraisonfournisseur);
                            this.lignelivraisonfournisseurFacadeLocal.create(llf);

                            Magasinarticle ma = this.magasinarticleFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                            ma.setQuantite((ma.getQuantite() - llf.getQuantitereduite()) + llf.getQuantitereduite());
                            ma.setQuantitemultiple((ma.getQuantitemultiple() - llf.getQuantitemultiple()) + llf.getQuantitemultiple());
                            ma.setQuantitereduite((ma.getQuantitereduite() - llf.getQuantitereduite()) + llf.getQuantitereduite());
                            this.magasinarticleFacadeLocal.edit(ma);

                            Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinlot());
                            mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() + llf.getQuantitemultiple()));
                            mlTemp.setQuantite((mlTemp.getQuantite() + llf.getQuantitereduite()));
                            mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() + llf.getQuantitereduite()));
                            this.magasinlotFacadeLocal.edit(mlTemp);

                            Lignemvtstock lmvts = new Lignemvtstock();
                            lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            lmvts.setIdmvtstock(this.mvtstock);
                            lmvts.setIdlot(llf.getIdlot());
                            lmvts.setQteentree(llf.getQuantitereduite());
                            lmvts.setQteAvant(mlTemp.getQuantitereduite() - llf.getQuantitereduite());
                            lmvts.setQtesortie(0d);
                            lmvts.setType("ENTREE");
                            lmvts.setFournisseur(this.fournisseur.getNom());
                            lmvts.setMagasin("MAGASIN CENTRAL");
                            lmvts.setClient(" ");
                            lmvts.setReste(mlTemp.getQuantitereduite());
                            lmvts.setLignelivraisonfournisseur(llf);
                            this.lignemvtstockFacadeLocal.create(lmvts);
                        }
                    }
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Modification de l'entrée en stock N° : " + this.livraisonfournisseur.getCode() + " ; Ancien Montant : " + s1.getMontant() + " Nouveau montant : " + this.livraisonfournisseur.getMontant(), SessionMBean.getUserAccount());
                    this.livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getAnnee().getDateDebut(), SessionMBean.getAnnee().getDateFin(), true);

                    this.ut.commit();
                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('StockCreateDialog').hide()");
                    return;
                }
                notifyError("liste_article_vide");
                return;
            }
            notifyError("not_row_selected");
        } catch (Exception ex) {
            notifyFail(ex);
        }
    }

    public void createFournisseur() {
        fournisseurToSave.setIdfournisseur(fournisseurFacadeLocal.nextVal());
        fournisseurToSave.setMagasin(SessionMBean.getMagasin());
        fournisseurFacadeLocal.create(fournisseurToSave);
        Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du fournisseur : " + this.fournisseurToSave.getNom(), SessionMBean.getUserAccount());
        this.fournisseur = fournisseurToSave;
        RequestContext.getCurrentInstance().execute("PF('FournisseurCreerDialog').hide()");
    }

    public void delete() {
        try {
            if (this.livraisonfournisseur != null) {

                if (Utilitaires.isDayClosed()) {
                    notifyError("journee_cloturee");
                    return;
                }

                if (!Utilitaires.isAccess(33L)) {
                    notifyError("acces_refuse");
                    return;
                }

                this.ut.begin();
                List<Lignelivraisonfournisseur> temp = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
                for (Lignelivraisonfournisseur llf : temp) {
                    Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                    maTemp.setQuantite((maTemp.getQuantite() - llf.getQuantitereduite()));
                    maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - llf.getQuantitemultiple()));
                    maTemp.setQuantitereduite((maTemp.getQuantitereduite() - llf.getQuantitereduite()));
                    this.magasinarticleFacadeLocal.edit(maTemp);

                    Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinlot());
                    mlTemp.setQuantite((mlTemp.getQuantite() - llf.getQuantitereduite()));
                    mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - llf.getQuantitemultiple()));
                    mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - llf.getQuantitereduite()));
                    this.magasinlotFacadeLocal.edit(mlTemp);

                    this.lignelivraisonfournisseurFacadeLocal.remove(llf);
                }

                livraisonfournisseurFacadeLocal.remove(this.livraisonfournisseur);
                lignemvtstockFacadeLocal.deleteByIdmvt(this.livraisonfournisseur.getIdmvtstock().getIdmvtstock());
                this.mvtstockFacadeLocal.remove(this.livraisonfournisseur.getIdmvtstock());

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de l'entrée directe en stock : " + this.livraisonfournisseur.getCode() + " Montant : " + this.livraisonfournisseur.getMontant(), SessionMBean.getUserAccount());
                this.ut.commit();
                livraisonfournisseurs.remove(livraisonfournisseur);

                this.livraisonfournisseur = new Livraisonfournisseur();
                //this.livraisonfournisseurs = this.livraisonfournisseurFacadeLocal.findAllRange(SessionMBean.getMagasin().getIdmagasin(), SessionMBean.getAnnee().getDateDebut(), SessionMBean.getAnnee().getDateFin(), true);
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
                livraisonfournisseur.setLignelivraisonfournisseurList(lignelivraisonfournisseurFacadeLocal.findByIdlivraison(livraisonfournisseur.getIdlivraisonfournisseur()));
                fileName = PrintUtils.printStock(livraisonfournisseur);
                RequestContext.getCurrentInstance().execute("PF('StockImprimerDialog').show()");
                return;
            }
            notifyError("not_row_selected");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Livraisonfournisseur l) {
        this.livraisonfournisseur = l;
        print();
    }

    public void initEdit(Livraisonfournisseur l) {
        this.livraisonfournisseur = l;
        prepareEdit();
    }

    public void initView(Livraisonfournisseur l) {
        this.livraisonfournisseur = l;
        prepareview();
    }

    public void initDelete(Livraisonfournisseur l) {
        this.livraisonfournisseur = l;
        delete();
    }

    public void addProduit() {
        try {
            Lignelivraisonfournisseur l = this.lignelivraisonfournisseur;
            l.setIdlignelivraisonfournisseur(0L);

            if (!magasinlot.getIdmagasinlot().equals(0l)) {
                magasinlot = magasinlotFacadeLocal.find(magasinlot.getIdmagasinlot());
                this.lot = magasinlot.getIdlot();
            }

            Lot lotTemp = this.lot;
            this.lot.setPrixunitaire(lotTemp.getPrixachat());
            lotTemp.setIdarticle(this.magasinarticle.getIdarticle());
            l.setIdlot(lotTemp);

            Magasinlot ml = new Magasinlot();
            Magasinarticle ma = this.magasinarticle;
            if (!magasinlot.getIdmagasinlot().equals(0l)) {
                ml = magasinlot;
            } else {
                ml.setIdmagasinarticle(ma);
            }

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
                        this.lignelivraisonfournisseurFacadeLocal.remove(llf);
                        this.livraisonfournisseur.setMontant((this.livraisonfournisseur.getMontant() - this.livraisonfournisseur.getMontant() * llf.getQuantite()));
                        this.livraisonfournisseurFacadeLocal.edit(this.livraisonfournisseur);

                        Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());
                        maTemp.setQuantite((maTemp.getQuantite() - llf.getQuantitereduite()));
                        maTemp.setQuantitemultiple((maTemp.getQuantitemultiple() - llf.getQuantitemultiple()));
                        maTemp.setQuantitereduite((maTemp.getQuantitereduite() - llf.getQuantitereduite()));
                        this.magasinarticleFacadeLocal.edit(maTemp);

                        Magasinlot mlTemp = this.magasinlotFacadeLocal.find(llf.getIdmagasinlot().getIdmagasinlot());
                        mlTemp.setQuantite((mlTemp.getQuantite() - llf.getQuantitereduite()));
                        mlTemp.setQuantitemultiple((mlTemp.getQuantitemultiple() - llf.getQuantitemultiple()));
                        mlTemp.setQuantitereduite((mlTemp.getQuantitereduite() - llf.getQuantitereduite()));
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
        Double resultat = 0d;
        if (!this.lignelivraisonfournisseurs.isEmpty()) {
            int i = 0;
            for (Lignelivraisonfournisseur llf : lignelivraisonfournisseurs) {
                if (llf.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                    lignelivraisonfournisseurs.get(i).setQuantitemultiple(llf.getQuantite() * llf.getUnite());
                    lignelivraisonfournisseurs.get(i).setQuantitereduite((this.lignelivraisonfournisseurs.get(i).getQuantitemultiple() / llf.getIdlot().getIdarticle().getUnite()));
                    lignelivraisonfournisseur.setMontantTotal(llf.getPrixachat() * llf.getQuantite());
                    resultat += (llf.getPrixachat() * llf.getQuantite());
                } else {
                    lignelivraisonfournisseurs.get(i).setQuantitemultiple(llf.getQuantite());
                    lignelivraisonfournisseurs.get(i).setQuantitereduite(((this.lignelivraisonfournisseurs.get(i)).getQuantitemultiple() / llf.getIdlot().getIdarticle().getUnite()));
                    lignelivraisonfournisseur.setMontantTotal(llf.getPrixachat() * llf.getQuantitemultiple());
                    resultat += (llf.getPrixachat() * llf.getQuantitemultiple());
                }
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
            double montantTotal = 0d;
            if (this.lignelivraisonfournisseur.getQuantite() != 0) {
                if (this.lignelivraisonfournisseur.getUnite() != 0) {
                    this.lignelivraisonfournisseur.setQuantitemultiple(this.lignelivraisonfournisseur.getQuantite() * this.lignelivraisonfournisseur.getUnite());
                }
                if (this.lignelivraisonfournisseur.getPrixachat() != 0) {
                    if (lignelivraisonfournisseur.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                        montantTotal = this.lignelivraisonfournisseur.getPrixachat() * this.lignelivraisonfournisseur.getQuantite();
                    } else {
                        montantTotal = this.lignelivraisonfournisseur.getPrixAchatDetail() * this.lignelivraisonfournisseur.getQuantitemultiple();
                    }
                }
                lignelivraisonfournisseur.setMontantTotal(montantTotal);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePrixGrosAndDetail() {
        if (magasinarticle != null) {
            if (lignelivraisonfournisseur.getModeVente().equals(ModeEntreSorti.VENTE_EN_GROS)) {
                lignelivraisonfournisseur.setPrixachat(magasinarticle.getIdarticle().getCoutachat());
                lignelivraisonfournisseur.setUnite(magasinarticle.getIdarticle().getUnite());
                lignelivraisonfournisseur.setIdunite(magasinarticle.getIdarticle().getIdunite());
                lignelivraisonfournisseur.setPrixAchatDetail(magasinarticle.getIdarticle().getPrixAchatDetail());
                lignelivraisonfournisseur.setQuantitemultiple(magasinarticle.getIdarticle().getUnite());
                lignelivraisonfournisseur.setQuantitereduite(lignelivraisonfournisseur.getQuantitemultiple() / magasinarticle.getIdarticle().getUnite());
                lignelivraisonfournisseur.setMontantTotal(lignelivraisonfournisseur.getPrixachat() * lignelivraisonfournisseur.getQuantitereduite());
            } else {
                lignelivraisonfournisseur.setPrixachat(magasinarticle.getIdarticle().getPrixVenteDetail());
                lignelivraisonfournisseur.setUnite(1d);
                lignelivraisonfournisseur.setPrixAchatDetail(magasinarticle.getIdarticle().getPrixAchatDetail());
                lignelivraisonfournisseur.setQuantitemultiple(lignelivraisonfournisseur.getQuantite());
                lignelivraisonfournisseur.setQuantitereduite(lignelivraisonfournisseur.getQuantitemultiple() / magasinarticle.getIdarticle().getUnite());
                lignelivraisonfournisseur.setIdunite(uniteFacadeLocal.find(magasinarticle.getIdarticle().getIdUniteDetail()));
                lignelivraisonfournisseur.setMontantTotal(lignelivraisonfournisseur.getPrixachat() * lignelivraisonfournisseur.getQuantitemultiple());
            }
        }
    }

    public void updatedata() {
        try {
            if (this.article.getIdarticle() != null) {
                this.famille = this.article.getIdfamille();
                this.lignelivraisonfournisseur.setUnite(this.article.getUnite());
                this.lot = new Lot();
                this.lot.setQuantite(0.0);
                this.lignelivraisonfournisseur.setPrixachat(0.0);

                this.lots = this.lotFacadeLocal.findByArticle(this.article.getIdarticle(), this.article.getPerissable());
                if (this.lots.size() == 1) {
                    this.lot = (this.lots.get(0));
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
            e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
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
