package controllers.inventaire_global;

import entities.Inventaire;
import entities.Ligneinventaire;
import entities.Lignemvtstock;
import entities.Lot;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Magasinlot;
import entities.Unite;
import enumeration.ModeComptage;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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
public class InventaireGlobalController extends AbstractInventaireGlobalController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(44L)) {
                notifyError("acces_refuse");
                return;
            }

            this.mode = "Create";
            this.valideBtn = this.routine.localizeMessage("valider");
            this.editQuantite = false;

            this.inventaire = new Inventaire();
            this.inventaire.setDateinventaire(new Date());
            this.inventaire.setCentral(false);
            this.inventaire.setAllarticle(true);

            this.ligneinventaires.clear();
            this.ligneinventaires_1.clear();
            this.articles.clear();
            this.magasinarticles.clear();
            this.magasinlots.clear();

            this.magasin = new Magasin();

            this.inventaire.setCode(Utilitaires.genererCodeInventaire("INV-", inventaireFacadeLocal.nextVal()));

            RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreatePartial() {
        try {
            if (!Utilitaires.isAccess(44L)) {
                notifyError("acces_refuse");
                return;
            }

            this.mode = "Create";
            this.valideBtn = (this.routine.localizeMessage("valider"));
            this.showSelectArticle = false;

            this.inventaire = new Inventaire();
            this.inventaire.setDateinventaire(new Date());

            this.ligneinventaires.clear();
            this.articles.clear();

            String code = "INV-";
            Long nextPayement = this.inventaireFacadeLocal.nextVal();
            code = Utilitaires.genererCodeInventaire(code, nextPayement);
            this.inventaire.setCode(code);

            RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareAddArticle() {
        try {
            if ("Create".equals(this.mode)) {
                if (this.magasin.getIdmagasin() == 0) {
                    this.lots = Utilitaires.filterNotPeremptLot(this.lotFacadeLocal.findAllRange());
                    this.ligneinventaires_1.clear();
                    this.selectedLots.clear();
                } else {
                    this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinEtatIsTrue(this.magasin.getIdmagasin());
                    this.ligneinventaires.clear();
                    this.selectedMagasinlots.clear();
                }

                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
                return;
            }
            this.selectedLots.clear();
            this.articles.clear();
            this.selectedLots.addAll(this.ligneinventaires.stream()
                    .map(Ligneinventaire::getIdlot)
                    .collect(Collectors.toList()));

            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.inventaire == null) {
                notifyError("not_row_selected");
                return;
            }

            if (!Utilitaires.isAccess(44L)) {
                notifyError("acces_refuse");
                this.inventaire = null;
                return;
            }

            if (this.inventaire.getEtat()) {
                notifyError("inventaire_validee");
                return;
            }

            this.mode = "Edit";
            this.editQuantite = false;
            this.showSelectArticle = true;
            this.magasin = this.inventaire.getIdmagasin();
            this.valideBtn = ("" + this.routine.localizeMessage("valider"));
            if (this.inventaire.getCentral()) {
                this.ligneinventaires = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire());
            } else {
                this.ligneinventaires_1 = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire());
            }

            this.articles.clear();
            if (!this.inventaire.getEtat()) {
                this.showSelectArticle = false;
            }
            RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareValidate() {
        try {
            if (this.inventaire == null) {
                notifyError("not_row_selected");
                return;
            }

            if (!Utilitaires.isAccess(45L)) {
                notifyError("acces_refuse");
                this.inventaire = null;
                return;
            }

            if (this.inventaire.getEtat()) {
                notifyError("inventaire_validee");
                return;
            }

            this.mode = "Validate";
            this.editQuantite = true;
            this.showSelectArticle = true;
            this.magasin = this.inventaire.getIdmagasin();
            this.valideBtn = this.routine.localizeMessage("valider_inventaire");
            if (this.inventaire.getCentral()) {
                this.ligneinventaires = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire());
            } else {
                this.ligneinventaires_1 = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire());
            }
            this.articles.clear();
            if (!this.inventaire.getEtat()) {
                this.showSelectArticle = false;
            }
            RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.inventaire != null) {
                this.ligneinventaires_1 = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire());
                if (!this.ligneinventaires_1.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('InventaireDetailDialog').show()");
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

    public void updateData() {
        try {
            if (this.magasin.getIdmagasin() == 0) {
                this.inventaire.setCentral(true);
            } else {
                this.inventaire.setCentral(false);
            }
            updateArticle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateArticle() {
        try {
            if (this.magasin.getIdmagasin() == 0) {
                this.ligneinventaires_1.clear();
                this.magasinlots.clear();
                this.lots = Utilitaires.filterNotPeremptLot(this.lotFacadeLocal.findAllRangeEtatIsTrue());
                insertLotToTable();
            } else if (this.inventaire.getAllarticle()) {
                magasin = magasinFacadeLocal.find(magasin.getIdmagasin());
                this.lots.clear();
                this.articles.clear();
                this.ligneinventaires.clear();
                insertMagasinLotToTable();
            } else {
                this.ligneinventaires_1.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void insertLotToTable() {
        for (Lot l : this.lots) {
            if (!ifExistLot(this.ligneinventaires, l)) {
                Ligneinventaire li = new Ligneinventaire();
                li.setIdligneinventaire(0L);
                li.setIdlot(l);
                li.setQtetheorique(l.getQuantite());
                li.setQtephysique(l.getQuantite());
                li.setEcart(0.0);
                li.setObservation("-");
                this.ligneinventaires.add(li);
            }
        }
    }

    private void initModeDecompte(Magasin magasin, Ligneinventaire ligneinventaire) {
        ligneinventaire.setModeComptage(magasin.getModeComptage());
    }

    private void initQuantiteDefault(ModeComptage modeComptage, Magasinlot magasinlot, Ligneinventaire ligneinventaire) {
        if (modeComptage.equals(ModeComptage.DECOMPTE_EN_DETAIL)) {
            ligneinventaire.setQuantite(magasinlot.getQuantitemultiple());
        } else {
            ligneinventaire.setQuantite(magasinlot.getQuantitereduite());
        }
    }

    private void initUniteDecompte(ModeComptage modeComptage, Magasinlot magasinlot, Ligneinventaire ligneinventaire) {
        if (modeComptage.equals(ModeComptage.DECOMPTE_EN_DETAIL)) {
            ligneinventaire.setIdunite(new Unite(magasinlot.getIdmagasinarticle().getIdarticle().getIdUniteDetail()));
        } else {
            ligneinventaire.setIdunite(magasinlot.getIdmagasinarticle().getIdarticle().getIdunite());
        }
    }

    private void initUnitPrice(ModeComptage modeComptage, Magasinlot magasinlot, Ligneinventaire ligneinventaire) {
        if (modeComptage.equals(ModeComptage.DECOMPTE_EN_DETAIL)) {
            if (magasinlot.getPrixVenteDetail() == 0) {
                ligneinventaire.setPrixUnitaire(magasinlot.getIdmagasinarticle().getIdarticle().getPrixVenteDetail());
                return;
            }
            ligneinventaire.setPrixUnitaire(magasinlot.getPrixVenteDetail());
            return;
        }
        if (magasinlot.getPrixVenteGros() == 0) {
            ligneinventaire.setPrixUnitaire(magasinlot.getIdmagasinarticle().getIdarticle().getPrixunit());
            return;
        }
        ligneinventaire.setPrixUnitaire(magasinlot.getPrixVenteGros());
    }

    private void initDetailUnitQty(ModeComptage modeComptage, Magasinlot magasinlot, Ligneinventaire ligneinventaire) {
        if (modeComptage.equals(ModeComptage.DECOMPTE_EN_DETAIL)) {
            ligneinventaire.setUnite(1);
        } else {
            ligneinventaire.setUnite(magasinlot.getIdlot().getIdarticle().getUnite());
        }
    }

    private void insertMagasinLotToTable() {
        this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinEtatIsTrue(this.magasin.getIdmagasin());
        this.ligneinventaires_1.clear();
        for (Magasinlot item : this.magasinlots) {
            if (!ifExistLot(this.ligneinventaires_1, item)) {
                Ligneinventaire li = new Ligneinventaire();
                li.setIdligneinventaire(0L);
                li.setIdlot(item.getIdlot());
                li.setIdmagasinlot(item);

                // set mode comptage default
                // initModeDecompte(item.getIdmagasinarticle().getIdmagasin(), li);
                li.setModeComptage(magasin.getModeComptage());

                li.setQtetheorique(item.getQuantitereduite());
                li.setQtetheoriqueMultiple(item.getQuantitemultiple());

                // set quantite default by mode comptage
                initQuantiteDefault(li.getModeComptage(), item, li);

                li.setQtephysique(item.getQuantitereduite());
                li.setQtephysiqueMultiple(item.getQuantitemultiple());

                li.setEcart(0d);

                // set unit price
                this.initUnitPrice(li.getModeComptage(), item, li);

                // init unite comptage
                this.initUniteDecompte(li.getModeComptage(), item, li);

                // set unite for gros and details
                this.initDetailUnitQty(li.getModeComptage(), li.getIdmagasinlot(), li);

                li.setObservation("-");
                this.ligneinventaires_1.add(li);
            }
        }
    }

    public void create() {
        try {
            if ("Create".equals(this.mode)) {
                if (this.ligneinventaires == null || this.ligneinventaires_1.isEmpty()) {
                    notifyError("liste_article_vide");
                    return;
                }

                this.ut.begin();

                this.mvtstock.setIdmvtstock(mvtstockFacadeLocal.nextVal());
                this.mvtstock.setClient(" ");
                this.mvtstock.setFournisseur(" ");
                this.mvtstock.setMagasin(" ");
                this.mvtstock.setType(" ");
                this.mvtstock.setCode(Utilitaires.genererCodeStock("MVT", mvtstock.getIdmvtstock()));
                this.mvtstock.setDatemvt(this.inventaire.getDateinventaire());
                this.mvtstockFacadeLocal.create(this.mvtstock);

                this.inventaire.setIdinventaire(inventaireFacadeLocal.nextVal());
                this.inventaire.setEtat(false);
                this.inventaire.setIdmagasin(this.magasin);
                this.inventaire.setIdmvtstock(this.mvtstock);

                this.inventaire.setLigneinventaireList(ligneinventaires);
                this.inventaire.computeTotals();
                this.inventaireFacadeLocal.create(this.inventaire);

                if (this.magasin.getIdmagasin() != 0) {
                    for (Ligneinventaire li : this.ligneinventaires_1) {
                        li.setIdligneinventaire(ligneinventaireFacadeLocal.nextVal());
                        li.setIdinventaire(this.inventaire);
                        ligneinventaireFacadeLocal.create(li);
                    }
                } else {
                    ligneinventaires.stream().map((li) -> {
                        li.setIdinventaire(this.inventaire);
                        return li;
                    }).forEachOrdered((li) -> {
                        li.setIdligneinventaire(ligneinventaireFacadeLocal.nextVal());
                        ligneinventaireFacadeLocal.create(li);
                    });
                }

                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());

                this.ut.commit();
                this.inventaire = new Inventaire();
                this.ligneinventaires_1.clear();
                this.detail = this.imprimer = true;

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').hide()");

            } else if ("Edit".equals(this.mode)) {
                if (this.inventaire == null) {
                    notifyError("not_row_selected");
                    return;
                }

                this.ut.begin();

                this.inventaire.setLigneinventaireList(ligneinventaires);
                this.inventaire.computeTotals();
                this.inventaireFacadeLocal.edit(this.inventaire);

                if (!this.ligneinventaires.isEmpty()) {
                    this.ligneinventaires_1.forEach((li) -> {
                        this.ligneinventaireFacadeLocal.edit(li);
                    });
                }

                this.ut.commit();
                this.inventaire = new Inventaire();
                this.ligneinventaires_1.clear();
                this.detail = this.imprimer = true;

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').hide()");

            } else if (this.inventaire != null) {
                this.ut.begin();

                this.inventaire.setEtat(true);
                this.inventaireFacadeLocal.edit(this.inventaire);

                if (this.inventaire.getCentral().equals(false)) {
                    if (!this.ligneinventaires_1.isEmpty()) {
                        for (Ligneinventaire li : this.ligneinventaires_1) {

                            if (li.getEcart() == 0.0) {
                                li.setObservation("Normal");
                            } else if (li.getEcart() > 0.0) {

                                Magasinlot ml = magasinlotFacadeLocal.find(li.getIdmagasinlot().getIdmagasinlot());
                                double qteAvant = this.getQtyBefore(ml, li.getModeComptage());

                                final double ecartMulti = li.getQtephysiqueMultiple() - li.getQtetheoriqueMultiple();
                                final double ecartReduit = li.getQtephysique() - li.getQtetheorique();

                                updateMagasinArticle(li, ecartReduit, ecartMulti, "+");
                                Magasinlot mla = updateMagasinLot(li, ecartReduit, ecartMulti, "+");

                                Lignemvtstock lmvts = new Lignemvtstock();
                                lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                                lmvts.setIdmvtstock(this.inventaire.getIdmvtstock());
                                lmvts.setIdlot(li.getIdlot());
                                lmvts.setIdmagasinlot(li.getIdmagasinlot());
                                lmvts.setClient(" ");
                                lmvts.setFournisseur("ECART INVENTAIRE");
                                lmvts.setQteentree(li.getEcart());
                                lmvts.setQtesortie(0d);
                                lmvts.setQteAvant(qteAvant);
                                lmvts.setUnite(li.getUnite());

                                lmvts.setReste(this.getQtyBefore(mla, li.getModeComptage()));

                                lmvts.setType("ENTREE");
                                lmvts.setMagasin(mla.getIdmagasinarticle().getIdmagasin().getNom());
                                lmvts.setLigneinventaire(li);
                                this.lignemvtstockFacadeLocal.create(lmvts);
                            } else {

                                Magasinlot ml = magasinlotFacadeLocal.find(li.getIdmagasinlot().getIdmagasinlot());
                                double qteAvant = this.getQtyBefore(ml, li.getModeComptage());

                                final double ecartMulti = li.getQtephysiqueMultiple() - li.getQtetheoriqueMultiple();
                                final double ecartReduit = li.getQtephysique() - li.getQtetheorique();

                                updateMagasinArticle(li, ecartReduit, ecartMulti, "-");
                                Magasinlot mla = updateMagasinLot(li, ecartReduit, ecartMulti, "-");

                                Lignemvtstock lmvts = new Lignemvtstock();
                                lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                                lmvts.setIdmvtstock(this.inventaire.getIdmvtstock());
                                lmvts.setIdlot(li.getIdlot());
                                lmvts.setIdmagasinlot(li.getIdmagasinlot());
                                lmvts.setClient(" ");
                                lmvts.setFournisseur("ECART INVENTAIRE");
                                lmvts.setQteentree(0d);
                                lmvts.setQteAvant(qteAvant);
                                lmvts.setQtesortie(Math.abs(li.getEcart()));
                                lmvts.setUnite(li.getUnite());

                                lmvts.setReste(this.getQtyBefore(mla, li.getModeComptage()));

                                lmvts.setType("SORTIE");
                                lmvts.setMagasin(mla.getIdmagasinarticle().getIdmagasin().getNom());
                                lmvts.setLigneinventaire(li);
                                this.lignemvtstockFacadeLocal.create(lmvts);
                            }
                            this.ligneinventaireFacadeLocal.edit(li);
                        }
                    }
                }

                this.ut.commit();
                this.inventaire = new Inventaire();
                this.ligneinventaires_1.clear();
                this.detail = (this.imprimer = true);

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('InventaireCreateDialog').hide()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    private double getQtyBefore(Magasinlot magasinlot, ModeComptage modeComptage) {
        if (modeComptage.equals(ModeComptage.DECOMPTE_EN_DETAIL)) {
            return magasinlot.getQuantitemultiple();
        }
        return magasinlot.getQuantitereduite();
    }

    private void updateMagasinArticle(Ligneinventaire ligneinventaire, double ecartReduit, double ecartMulti, String signe) {
        Magasinarticle magasinarticle = this.magasinarticleFacadeLocal.find(ligneinventaire.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle());

        if (signe.equals("-")) {
            magasinarticle.setQuantitemultiple(magasinarticle.getQuantitemultiple() - Math.abs(ecartMulti));
            magasinarticle.setQuantitereduite((magasinarticle.getQuantitereduite() - Math.abs(ecartReduit)));
            magasinarticle.setQuantite((magasinarticle.getQuantite() - Math.abs(ecartReduit)));
        } else {
            magasinarticle.setQuantitemultiple(magasinarticle.getQuantitemultiple() + ecartMulti);
            magasinarticle.setQuantitereduite(magasinarticle.getQuantitereduite() + ecartReduit);
            magasinarticle.setQuantite(magasinarticle.getQuantite() + ecartReduit);
        }
        magasinarticleFacadeLocal.edit(magasinarticle);
    }

    /*private void updateMagasinArticle(long idmagasinArticle, double ecart, String signe) {
        Magasinarticle magasinarticle = this.magasinarticleFacadeLocal.find(idmagasinArticle);
        if (signe.equals("-")) {
            double vabs = Math.abs(ecart);
            magasinarticle.setQuantitemultiple(magasinarticle.getQuantitemultiple() - (vabs * magasinarticle.getIdarticle().getUnite()));
            magasinarticle.setQuantitereduite((magasinarticle.getQuantitereduite() - vabs));
            magasinarticle.setQuantite((magasinarticle.getQuantite() - vabs));
        } else {
            magasinarticle.setQuantitemultiple(magasinarticle.getQuantitemultiple() + (ecart * magasinarticle.getIdarticle().getUnite()));
            magasinarticle.setQuantitereduite((magasinarticle.getQuantitereduite() + ecart));
            magasinarticle.setQuantite((magasinarticle.getQuantite() + ecart));
        }
        this.magasinarticleFacadeLocal.edit(magasinarticle);
    }*/
    public Magasinlot updateMagasinLot(Ligneinventaire ligneinventaire, double ecartReduit, double ecartMulti, String signe) {
        Magasinlot magasinlot = this.magasinlotFacadeLocal.find(ligneinventaire.getIdmagasinlot().getIdmagasinlot());
        if (signe.equals("+")) {
            magasinlot.setQuantitemultiple(magasinlot.getQuantitemultiple() + ecartMulti);
            magasinlot.setQuantitereduite(magasinlot.getQuantitereduite() + ecartReduit);
            magasinlot.setQuantite(magasinlot.getQuantite() + ecartReduit);
        } else {
            magasinlot.setQuantitemultiple(magasinlot.getQuantitemultiple() - Math.abs(ecartMulti));
            magasinlot.setQuantitereduite(magasinlot.getQuantitereduite() - Math.abs(ecartReduit));
            magasinlot.setQuantite(magasinlot.getQuantite() - Math.abs(ecartReduit));
        }
        try {
            if ((magasinlot.getIdlot().getDateperemption().after(Date.from(Instant.now()))) || (magasinlot.getIdlot().getDateperemption().equals(Date.from(Instant.now())))) {
                magasinlot.setEtat(false);
            }
        } catch (Exception e) {
        }
        this.magasinlotFacadeLocal.edit(magasinlot);
        return magasinlot;
    }

    public void delete() {
        try {
            if (this.inventaire == null) {
                notifyError("not_row_selected");
                return;
            }

            if (!Utilitaires.isAccess(44L)) {
                notifyError("acces_refuse");
                this.detail = (this.imprimer = true);
                this.inventaire = new Inventaire();
                return;
            }

            if (this.inventaire.getEtat()) {
                notifyError("inventaire_validee");
                this.detail = (this.imprimer = true);
                this.inventaire = new Inventaire();
                return;
            }

            this.ut.begin();

            this.lignemvtstockFacadeLocal.deleteByIdmvt(this.inventaire.getIdmvtstock().getIdmvtstock());
            this.ligneinventaireFacadeLocal.removeByIdInventaire(this.inventaire.getIdinventaire());
            this.inventaireFacadeLocal.remove(this.inventaire);
            this.mvtstockFacadeLocal.remove(this.inventaire.getIdmvtstock());

            this.ut.commit();
            Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());
            this.inventaire = null;
            this.detail = (this.imprimer = true);
            notifySuccess();
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void annuler() {
        try {
            if (this.inventaire == null) {
                this.detail = (this.imprimer = true);
                notifyError("not_row_selected");
                return;
            }

            if (!Utilitaires.isAccess(45L)) {
                this.detail = (this.imprimer = true);
                notifyError("acces_refuse");
                this.inventaire = null;
                return;
            }

            if (!this.inventaire.getEtat()) {
                notifyError("inventaire_non_validee");
                return;
            }

            this.ut.begin();

            List<Ligneinventaire> inventItems = this.ligneinventaireFacadeLocal.findByIdInventaire(this.inventaire.getIdinventaire());
            if (!inventItems.isEmpty()) {
                inventItems.forEach((li) -> {
                    final double ecartMulti = li.getQtephysiqueMultiple() - li.getQtetheoriqueMultiple();
                    final double ecartReduit = li.getQtephysique() - li.getQtetheorique();

                    if (li.getEcart() > 0) {
                        updateMagasinArticle(li, Math.abs(ecartReduit), Math.abs(ecartMulti), "-");
                        updateMagasinLot(li, Math.abs(ecartReduit), Math.abs(ecartMulti), "-");
                    } else if (li.getEcart() < 0) {
                        updateMagasinArticle(li, ecartReduit, ecartMulti, "+");
                        updateMagasinLot(li, ecartReduit, ecartMulti, "+");
                    }
                });
            }
            this.inventaire.setEtat(false);
            this.inventaireFacadeLocal.edit(this.inventaire);
            this.lignemvtstockFacadeLocal.deleteByIdmvt(this.inventaire.getIdmvtstock().getIdmvtstock());
            this.ut.commit();
            Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de la validation l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());
            this.inventaire = new Inventaire();
            this.detail = (this.supprimer = this.imprimer = true);
            notifySuccess();
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Inventaire i) {
        this.inventaire = i;
        print();
    }

    public void initEdit(Inventaire i) {
        this.inventaire = i;
        prepareEdit();
    }

    public void initView(Inventaire i) {
        this.inventaire = i;
        prepareview();
    }

    public void initDelete(Inventaire i) {
        this.inventaire = i;
        delete();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(44L)) {
                notifyError("acces_refuse");
                this.inventaire = null;
                return;
            }

            if (this.inventaire != null) {
                Map paramp = new HashMap();
                paramp.put("idinventaire", this.inventaire.getIdinventaire());
                Printer.print("/reports/ireport/inventaire.jasper", paramp);
                RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void addProduit() {
        try {
            if ((this.magasin.getIdmagasin() != null) && (this.magasin.getIdmagasin() != 0)) {
                for (Magasinlot item : this.selectedMagasinlots) {
                    if (!ifExistLot(this.ligneinventaires_1, item)) {
                        Ligneinventaire li = new Ligneinventaire();
                        li.setIdligneinventaire(0L);
                        li.setIdlot(item.getIdlot());
                        li.setIdmagasinlot(item);

                        li.setQtetheorique(item.getQuantitemultiple());
                        li.setQtephysique(item.getQuantitemultiple());

                        // set mode comptage default
                        // initModeDecompte(item.getIdmagasinarticle().getIdmagasin(), li);
                        li.setModeComptage(item.getIdmagasinarticle().getIdmagasin().getModeComptage());

                        li.setQtetheorique(item.getQuantitereduite());
                        li.setQtetheoriqueMultiple(item.getQuantitemultiple());

                        // set quantite default by mode comptage
                        initQuantiteDefault(li.getModeComptage(), item, li);

                        li.setQtephysique(item.getQuantitereduite());
                        li.setQtephysiqueMultiple(item.getQuantitemultiple());

                        li.setEcart(0d);

                        // set unit price
                        this.initUnitPrice(li.getModeComptage(), item, li);

                        // init unite comptage
                        this.initUniteDecompte(li.getModeComptage(), item, li);

                        // set unite for gros and details
                        this.initDetailUnitQty(li.getModeComptage(), li.getIdmagasinlot(), li);

                        li.setEcart(0.0);
                        li.setObservation("-");
                        this.ligneinventaires_1.add(li);
                    }
                }
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                return;
            }
            for (Lot l : this.selectedLots) {
                if (!ifExistLot(this.ligneinventaires, l)) {
                    Ligneinventaire li = new Ligneinventaire();
                    li.setIdligneinventaire(0L);
                    li.setIdlot(l);
                    li.setQtetheorique(l.getQuantite());
                    li.setQtephysique(l.getQuantite());
                    li.setEcart(0.0D);
                    li.setObservation("-");
                    this.ligneinventaires.add(li);
                }
            }
            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public boolean ifExistLot(List<Ligneinventaire> ligneinventaires, Lot lot) {
        boolean result = ligneinventaires.stream().anyMatch(item -> item.getIdlot().equals(lot));
        return result;
    }

    public boolean ifExistLot(List<Ligneinventaire> ligneinventaires, Magasinlot magasinlot) {
        boolean result = ligneinventaires.stream().anyMatch(item -> item.getIdmagasinlot().equals(magasinlot));
        return result;
    }

    public void removeProduit(int index) {
        try {
            boolean trouve = false;
            this.ut.begin();

            Ligneinventaire li = (Ligneinventaire) this.ligneinventaires.get(index);
            if (li.getIdligneinventaire() != 0L) {
                trouve = true;
                this.ligneinventaireFacadeLocal.remove(li);

                final double ecartMulti = li.getQtephysiqueMultiple() - li.getQtetheoriqueMultiple();
                final double ecartReduit = li.getQtephysique() - li.getQtetheorique();

                if (li.getEcart() < 0.0) {
                    updateMagasinArticle(li, Math.abs(ecartReduit), Math.abs(ecartMulti), "+");
                    updateMagasinLot(li, Math.abs(ecartReduit), Math.abs(ecartMulti), "+");
                } else if (li.getEcart() > 0.0) {
                    updateMagasinArticle(li, ecartReduit, ecartMulti, "-");
                    updateMagasinLot(li, ecartReduit, ecartMulti, "-");
                }
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du lot : " + li.getIdlot().getNumero() + " dans l'inventaire : " + this.inventaire.getCode(), SessionMBean.getUserAccount());
            }
            this.ligneinventaires.remove(index);
            this.ut.commit();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public void updateEcart(int index) {
        if (this.magasin.getIdmagasin() != 0) {
            updateEcartLine(index);
            return;
        }
        updateEcartLine(index);
    }

    public void updateModeleDecompte(int index) {
        if (magasin.getIdmagasin() != 0) {
            updateModeleLine(index);
        }
    }

    private void updateEcartLine(int index) {
        final Ligneinventaire ligneinventaire = ligneinventaires_1.get(index);
        try {
            if (ligneinventaire.getModeComptage().equals(ModeComptage.DECOMPTE_EN_GROS)) {

                ligneinventaires_1.get(index).setQtephysiqueMultiple(ligneinventaire.getQuantite() * ligneinventaire.getIdlot().getIdarticle().getUnite());
                ligneinventaires_1.get(index).setQtephysique(ligneinventaire.getQuantite());

                ligneinventaires_1.get(index).setEcart((ligneinventaires_1.get(index).getQtephysique() - ligneinventaire.getQtetheorique()));
            } else {
                ligneinventaires_1.get(index).setQtephysiqueMultiple(ligneinventaire.getQuantite());
                ligneinventaires_1.get(index).setQtephysique((ligneinventaire.getQuantite() / ligneinventaire.getIdlot().getIdarticle().getUnite()));
                ligneinventaires_1.get(index).setEcart((ligneinventaires_1.get(index).getQtephysiqueMultiple() - ligneinventaire.getQtetheoriqueMultiple()));
            }
        } catch (Exception e) {
            ligneinventaires_1.get(index).setEcart(0d);
            ligneinventaires_1.get(index).setQtephysique(ligneinventaire.getIdmagasinlot().getQuantitereduite());
            ligneinventaires_1.get(index).setQtephysiqueMultiple(ligneinventaire.getIdmagasinlot().getQuantitemultiple());
            e.printStackTrace();
        }
    }

    private void updateModeleLine(int index) {
        try {
            try {

                ModeComptage modeComptage = ligneinventaires_1.get(index).getModeComptage();
                final Ligneinventaire ligneinventaire = ligneinventaires_1.get(index);

                ligneinventaires_1.get(index).setEcart(0d);
                ligneinventaires_1.get(index).setQtephysique(ligneinventaire.getIdmagasinlot().getQuantitereduite());
                ligneinventaires_1.get(index).setQtephysiqueMultiple(ligneinventaire.getIdmagasinlot().getQuantitemultiple());

                this.initQuantiteDefault(modeComptage, ligneinventaires_1.get(index).getIdmagasinlot(), ligneinventaires_1.get(index));

                this.initUniteDecompte(modeComptage, ligneinventaires_1.get(index).getIdmagasinlot(), ligneinventaires_1.get(index));
                this.initUnitPrice(modeComptage, ligneinventaires_1.get(index).getIdmagasinlot(), ligneinventaires_1.get(index));

                this.initDetailUnitQty(modeComptage, ligneinventaires_1.get(index).getIdmagasinlot(), ligneinventaires_1.get(index));
            } catch (Exception e) {
                ligneinventaires_1.get(index).setEcart(0d);
                ligneinventaires_1.get(index).setQtephysique((ligneinventaires_1.get(index)).getIdmagasinlot().getQuantitereduite());
                ligneinventaires_1.get(index).setQtephysiqueMultiple((ligneinventaires_1.get(index)).getIdmagasinlot().getQuantitemultiple());
            }
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
