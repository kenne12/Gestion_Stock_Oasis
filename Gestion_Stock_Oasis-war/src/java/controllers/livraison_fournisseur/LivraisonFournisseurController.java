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
public class LivraisonFournisseurController extends AbstractLivraisonFournisseurController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public List<Lot> findByProduit(Article a) {
        try {
            return this.lotFacadeLocal.findByArticle(a.getIdarticle(), a.getPerissable());
        } catch (Exception e) {
        }
        return new ArrayList();
    }

    public void prepareCreate() {
        try {
            if (Utilitaires.isDayClosed()) {
                notifyError("journee_cloturee");
                return;
            }

            if (!Utilitaires.isAccess((52L))) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
            this.mode = "Create";

            this.livraisonfournisseur = new Livraisonfournisseur();
            this.livraisonfournisseur.setMontant((0.0D));

            this.lignelivraisonfournisseurs.clear();
            this.lignecommandefournisseurs.clear();

            this.commandefournisseur = new Commandefournisseur();
            this.showSelectorCommand = (false);

            this.total = (0.0D);
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        try {
            this.commandefournisseur = new Commandefournisseur();
            this.fournisseur = new Fournisseur();
            this.commandefournisseurs = this.commandefournisseurFacadeLocal.findByLivre(SessionMBean.getMagasin().getIdmagasin(), false);
            RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareEdit() {
        try {
            if (this.livraisonfournisseur == null) {
                notifyError("not_row_selected");
                return;
            }

            if (this.livraisonfournisseur.getLivraisondirecte()) {
                notifyError("livraison_effectuee_par_commande");
                return;
            }
            if (Utilitaires.isDayClosed()) {
                notifyError("journee_cloturee");
                return;
            }

            this.showSelectorCommand = (true);
            this.commandefournisseur = this.commandefournisseurFacadeLocal.find(this.livraisonfournisseur.getIdcommandefournisseur().getIdcommandefournisseur());
            if (this.commandefournisseur.getLivre()) {
                notifyError("commande_deja_livree");
                return;
            }

            if (!Utilitaires.isAccess((49L))) {
                notifyError("acces_refuse");
                this.commandefournisseur = null;
                return;
            }

            this.mode = "Edit";

            this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur());
            this.lignelivraisonfournisseurs = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
            this.fournisseur = this.commandefournisseur.getIdfournisseur();
            this.total = this.livraisonfournisseur.getMontant();
            RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.livraisonfournisseur != null) {
                this.lignelivraisonfournisseurs = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
                if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('FactureDetailDialog').show()");
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
            if (this.commandefournisseur != null) {
                this.lignelivraisonfournisseurs.clear();

                this.fournisseur = this.commandefournisseur.getIdfournisseur();
                this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur());
                this.commandefournisseurs.clear();

                this.commandefournisseur.setDateeffectlivraison(new Date());
                int conteur = 0;
                for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                    List lotTemps = this.lotFacadeLocal.findByArticleRangeDesc(lcf.getIdarticle().getIdarticle(), lcf.getIdarticle().getPerissable());

                    Lignelivraisonfournisseur object = new Lignelivraisonfournisseur();
                    object.setIdlignelivraisonfournisseur(0l);

                    object.setIdlot((Lot) lotTemps.get(0));
                    object.setQuantite(lcf.getQuantite());
                    object.setUnite(lcf.getUnite());
                    object.setPrixachat(object.getIdlot().getPrixachat());
                    object.setQuantitemultiple(lcf.getQuantitemultiple());
                    object.setQuantitereduite(lcf.getQuantitereduite());
                    object.setIdunite(lcf.getIdunite());

                    this.lignelivraisonfournisseurs.add(object);
                    this.lignelivraisonfournisseurs.get(conteur).setTauxsatisfaction(0d);
                    conteur++;
                }
                this.commandefournisseur.setTauxsatisfaction((0.0D));
                updateTotal();
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            String message;
            if ("Create".equals(this.mode)) {
                if (!this.lignecommandefournisseurs.isEmpty()) {
                    message = "";
                    updateTotal();

                    this.mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
                    String codeMvt = "MVT";
                    Long nextMvt = this.mvtstock.getIdmvtstock();
                    codeMvt = Utilitaires.genererCodeStock(codeMvt, nextMvt);
                    this.mvtstock.setCode(codeMvt);
                    this.mvtstock.setDatemvt(this.commandefournisseur.getDateeffectlivraison());
                    this.mvtstock.setType(" ");
                    this.mvtstock.setClient(" ");
                    this.mvtstock.setMagasin(" ");
                    this.mvtstock.setFournisseur(this.commandefournisseur.getIdfournisseur().getNom());

                    this.ut.begin();

                    this.mvtstockFacadeLocal.create(this.mvtstock);

                    this.livraisonfournisseur.setIdlivraisonfournisseur(this.livraisonfournisseurFacadeLocal.nextVal());
                    Long nextLiv = this.livraisonfournisseur.getIdlivraisonfournisseur();
                    String codeLivraison = "LIV";
                    codeLivraison = Utilitaires.genererCodeStock(codeLivraison, nextLiv);
                    this.livraisonfournisseur.setCode(codeLivraison);

                    this.livraisonfournisseur.setIdlivraisonfournisseur(this.livraisonfournisseurFacadeLocal.nextVal());
                    this.livraisonfournisseur.setMontant(this.total);
                    this.livraisonfournisseur.setIdfournisseur(this.fournisseur);
                    this.livraisonfournisseur.setLivraisondirecte((false));
                    this.livraisonfournisseur.setDatelivraison(this.commandefournisseur.getDateeffectlivraison());
                    this.livraisonfournisseur.setIdcommandefournisseur(this.commandefournisseur);
                    this.livraisonfournisseur.setIdUtilisateur(SessionMBean.getUserAccount().getIdutilisateur());
                    this.livraisonfournisseur.setIdmvtstock(this.mvtstock);

                    this.livraisonfournisseurFacadeLocal.create(this.livraisonfournisseur);

                    for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                        llf.setIdlignelivraisonfournisseur(this.lignelivraisonfournisseurFacadeLocal.nextVal());
                        llf.setIdlivraisonfournisseur(this.livraisonfournisseur);
                        llf.setQuantitemultiple((llf.getQuantite() * llf.getUnite()));
                        this.lignelivraisonfournisseurFacadeLocal.create(llf);

                        llf.setIdlot(this.lotFacadeLocal.find(llf.getIdlot().getIdlot()));

                        llf.getIdlot().getIdarticle().setQuantitereduite((llf.getIdlot().getIdarticle().getQuantitereduite() + llf.getQuantitereduite()));
                        llf.getIdlot().getIdarticle().setQuantitestock((llf.getIdlot().getIdarticle().getQuantitestock() + llf.getQuantite()));
                        llf.getIdlot().getIdarticle().setQuantitemultiple((llf.getIdlot().getIdarticle().getQuantitemultiple() + llf.getQuantitemultiple()));
                        this.articleFacadeLocal.edit(llf.getIdlot().getIdarticle());

                        llf.getIdlot().setQuantitereduite((llf.getIdlot().getQuantitereduite() + llf.getQuantitereduite()));
                        llf.getIdlot().setQuantite((llf.getIdlot().getQuantite() + llf.getQuantite()));
                        llf.getIdlot().setQuantitemultiple((llf.getIdlot().getQuantitemultiple() + llf.getQuantitemultiple()));
                        this.lotFacadeLocal.edit(llf.getIdlot());

                        Lignemvtstock lmvts = new Lignemvtstock();
                        lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                        lmvts.setIdmvtstock(this.mvtstock);
                        lmvts.setIdlot(llf.getIdlot());
                        lmvts.setQteentree(llf.getQuantitemultiple());
                        lmvts.setQtesortie(0d);
                        lmvts.setReste(llf.getIdlot().getQuantitemultiple());
                        lmvts.setType("ENTREE");
                        lmvts.setClient(" ");
                        lmvts.setFournisseur(this.commandefournisseur.getIdfournisseur().getNom());
                        lmvts.setMagasin("MAGASIN CENTRAL");
                        this.lignemvtstockFacadeLocal.create(lmvts);
                    }

                    this.commandefournisseur.setLivre(true);
                    this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du bon de livraison fournisseur ; N° : " + this.livraisonfournisseur.getCode() + "; Montant : " + this.livraisonfournisseur.getMontant(), SessionMBean.getUserAccount());

                    this.ut.commit();
                    this.commandefournisseur = new Commandefournisseur();
                    this.livraisonfournisseur = null;
                    this.lignecommandefournisseurs.clear();
                    this.lignelivraisonfournisseurs.clear();
                    this.detail = (this.supprimer = this.modifier = this.imprimer = true);
                    JsfUtil.addSuccessMessage(message);

                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
                } else {
                    notifyError("liste_article_vide");
                }
            } else if (this.livraisonfournisseur != null) {
                this.ut.begin();

                updateTotal();
                this.livraisonfournisseurFacadeLocal.edit(this.livraisonfournisseur);

                this.mvtstock = this.livraisonfournisseur.getIdmvtstock();

                if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    for (Lignelivraisonfournisseur s : this.lignelivraisonfournisseurs) {
                        Lignelivraisonfournisseur sp = this.lignelivraisonfournisseurFacadeLocal.find(s.getIdlignecommandefournisseur());
                        if (sp.getQuantite() != s.getQuantite()) {
                            Article art = sp.getIdlot().getIdarticle();
                            art.setQuantitestock((art.getQuantitestock() + sp.getQuantite() - sp.getQuantite()));
                            this.articleFacadeLocal.edit(art);

                            Lot l = sp.getIdlot();
                            l.setQuantite((l.getQuantite() + sp.getQuantite() - sp.getQuantite()));
                            this.lotFacadeLocal.edit(l);

                            Lignemvtstock lmvts = new Lignemvtstock();
                            lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            lmvts.setIdmvtstock(this.mvtstock);
                            lmvts.setIdlot(s.getIdlot());

                            if (s.getQuantite() > sp.getQuantite()) {
                                lmvts.setQteentree((s.getQuantite() - sp.getQuantite()));
                                lmvts.setQtesortie(0d);
                                lmvts.setReste(s.getIdlot().getQuantite());
                            } else {
                                lmvts.setQteentree(0d);
                                lmvts.setQtesortie((sp.getQuantite() - s.getQuantite()));
                                lmvts.setReste(s.getIdlot().getQuantite());
                            }
                            this.lignemvtstockFacadeLocal.create(lmvts);
                        }
                        this.lignelivraisonfournisseurFacadeLocal.edit(s);
                    }
                }

                this.ut.commit();
                this.lignecommandefournisseurs.clear();
                this.commandefournisseurs.clear();
                this.lignemvtstocks.clear();
                this.commandefournisseur = new Commandefournisseur();
                this.livraisonfournisseur = null;
                this.supprimer = (this.modifier = this.imprimer = this.detail = (true));

                notifySuccess();
                RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.livraisonfournisseur != null) {
                if (!this.livraisonfournisseur.getLivraisondirecte()) {

                    if (Utilitaires.isDayClosed()) {
                        notifyError("journee_cloturee");
                        return;
                    }

                    if (!Utilitaires.isAccess((53L))) {
                        notifyError("acces_refuse");
                        this.detail = (this.supprimer = this.modifier = this.imprimer = (true));
                        this.livraisonfournisseur = null;
                        return;
                    }

                    this.ut.begin();

                    List<Lignelivraisonfournisseur> temp = this.lignelivraisonfournisseurFacadeLocal.findByIdlivraison(this.livraisonfournisseur.getIdlivraisonfournisseur());
                    if (!temp.isEmpty()) {
                        for (Lignelivraisonfournisseur sp : temp) {
                            sp.getIdlot().setIdarticle(this.articleFacadeLocal.find(sp.getIdlot().getIdarticle().getIdarticle()));
                            sp.getIdlot().getIdarticle().setQuantitestock((sp.getIdlot().getIdarticle().getQuantitestock() + sp.getQuantite()));
                            this.articleFacadeLocal.edit(sp.getIdlot().getIdarticle());

                            if (sp.getIdlot() != null) {
                                sp.setIdlot(this.lotFacadeLocal.find(sp.getIdlot().getIdlot()));
                                sp.getIdlot().setQuantite((sp.getIdlot().getQuantite() + sp.getQuantite()));
                                this.lotFacadeLocal.edit(sp.getIdlot());
                            }
                            this.lignelivraisonfournisseurFacadeLocal.remove(sp);
                        }
                    }
                    this.livraisonfournisseurFacadeLocal.remove(this.livraisonfournisseur);

                    this.commandefournisseur = this.commandefournisseurFacadeLocal.find(this.livraisonfournisseur.getIdcommandefournisseur().getIdcommandefournisseur());

                    this.commandefournisseur.setLivre((false));
                    this.commandefournisseur.setCode("-");
                    this.commandefournisseur.setTauxsatisfaction((0.0D));
                    this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);

                    Mvtstock mTemp = this.livraisonfournisseur.getIdmvtstock();

                    List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(mTemp.getIdmvtstock());
                    for (Lignemvtstock l : lmvt) {
                        this.lignemvtstockFacadeLocal.remove(l);
                    }
                    this.mvtstockFacadeLocal.remove(mTemp);

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de livraison fournisseur : " + this.livraisonfournisseur.getCode() + " ; Montant : " + this.livraisonfournisseur.getMontant() + " Fournisseur : " + this.livraisonfournisseur.getIdcommandefournisseur().getIdfournisseur().getNom(), SessionMBean.getUserAccount());
                    this.ut.commit();

                    this.livraisonfournisseur = null;
                    this.commandefournisseur = new Commandefournisseur();
                    this.supprimer = (this.modifier = this.imprimer = this.detail = (true));
                    notifySuccess();
                } else {
                    notifyError(this.mode);
                }
            } else {
                notifyError("not_row_selected");
            }
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

    public void print() {
        try {
            if (!Utilitaires.isAccess(54L)) {
                notifyError("acces_refuse");
                this.commandefournisseur = null;
                return;
            }

            if (this.commandefournisseur != null) {
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
            Lignecommandefournisseur l = this.lignecommandefournisseur;
            l.setIdlignecommandefournisseur(Long.valueOf(0L));

            boolean drapeau = false;
            int i = 0;
            for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                i++;
            }

            if (!drapeau) {
                this.lignecommandefournisseurs.add(l);
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
                this.lignecommandefournisseur = new Lignecommandefournisseur();
                this.article = new Article();
                return;
            }
            JsfUtil.addErrorMessage("Article existant dans la commande");
            updateTotal();
        } catch (Exception e) {
            e.printStackTrace();
            this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("formulaire_incomplet"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void removeProduit(int index) {
        try {
            boolean trouve = false;
            this.ut.begin();

            Lignecommandefournisseur lcf = (Lignecommandefournisseur) this.lignecommandefournisseurs.get(index);
            if (lcf.getIdlignecommandefournisseur() != 0L) {
                trouve = true;
                this.lignecommandefournisseurFacadeLocal.remove(lcf);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'article : " + lcf.getIdarticle().getLibelle() + " quantité : " + lcf.getQuantite() + " dans la facture : " + this.livraisonfournisseur.getCode(), SessionMBean.getUserAccount());
            }
            this.lignecommandefournisseurs.remove(index);

            updateTotal();
            if (trouve) {
                this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);
            }
            this.ut.commit();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal() {
        Double resultat = (0.0D);
        int i = 0;
        for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
            resultat += (llf.getIdlot().getIdarticle().getCoutachat() * llf.getQuantite());
            this.lignelivraisonfournisseurs.get(i).setQuantitemultiple((llf.getQuantite() * llf.getUnite()));
            this.lignelivraisonfournisseurs.get(i).setQuantitereduite(((this.lignelivraisonfournisseurs.get(i)).getQuantitemultiple() / llf.getIdlot().getIdarticle().getUnite()));
            i++;
        }
        return resultat;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal();
            this.livraisonfournisseur.setMontant(this.total);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            this.cout_quantite = 0d;
            if ((this.lignecommandefournisseur.getQuantite() != null)
                    && (this.lignecommandefournisseur.getMontant() != null)) {
                this.cout_quantite = (this.lignecommandefournisseur.getMontant() * this.lignecommandefournisseur.getQuantite());
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.cout_quantite = 0d;
        }
    }

    public void updatedataLot() {
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
