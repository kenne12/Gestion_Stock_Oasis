package controllers.commande_fournisseur;

import entities.Article;
import entities.Commandefournisseur;
import entities.Famille;
import entities.Fournisseur;
import entities.Lignecommandefournisseur;
import entities.Lot;
import entities.Unite;
import java.io.Serializable;
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
public class CommandefournisseurController extends AbstractCommandefournisseurController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess((34L))) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
            this.mode = "Create";
            this.article = new Article();
            this.fournisseur = new Fournisseur();

            this.commandefournisseur = new Commandefournisseur();
            this.lignecommandefournisseurs.clear();

            this.commandefournisseur.setCode("-");
            this.commandefournisseur.setDatecommande(new Date());
            this.commandefournisseur.setDateprevlivrasion(new Date());
            this.commandefournisseur.setLivre(false);
            this.commandefournisseur.setTauxsatisfaction(0d);
            this.articles = this.articleFacadeLocal.findAllRange(true);
            this.total = 0d;
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        this.famille = new Famille();
        this.article = new Article();
        this.lignecommandefournisseur = new Lignecommandefournisseur();
        this.lignecommandefournisseur.setUnite(1d);

        this.cout_quantite = 0d;
        RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
    }

    public void prepareEdit() {
        try {
            if (this.commandefournisseur == null) {
                notifyError("not_row_selected");
                return;
            }

            if (this.commandefournisseur.getLivre()) {
                notifyError("commande_deja_livree");
                return;
            }

            if (!Utilitaires.isAccess(35L)) {
                notifyError("acces_refuse");
                this.commandefournisseur = null;
                return;
            }

            this.mode = "Edit";
            this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur());
            this.total = this.commandefournisseur.getMontant();

            RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.commandefournisseur != null) {
                this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur());
                if (!this.lignecommandefournisseurs.isEmpty()) {
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

    public void filterArticle() {
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
            String message;
            if ("Create".equals(this.mode)) {
                if (!this.lignecommandefournisseurs.isEmpty()) {
                    message = "";

                    updateTotal();

                    this.commandefournisseur.setIdcommandefournisseur(this.commandefournisseurFacadeLocal.nextVal());
                    this.commandefournisseur.setIdfournisseur(this.fournisseur);
                    this.commandefournisseur.setPaye(false);
                    this.commandefournisseur.setLivre(false);
                    this.commandefournisseur.setMontant(this.total);
                    this.commandefournisseur.setCode("CF" + Utilitaires.genererCodeDemande("", this.commandefournisseur.getIdcommandefournisseur()));

                    this.ut.begin();

                    this.commandefournisseurFacadeLocal.create(this.commandefournisseur);

                    for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                        lcf.setIdlignecommandefournisseur(this.lignecommandefournisseurFacadeLocal.nextVal());
                        lcf.setIdcommandefournisseur(this.commandefournisseur);
                        this.lignecommandefournisseurFacadeLocal.create(lcf);
                    }

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la commande fournisseur : " + this.commandefournisseur.getCode(), SessionMBean.getUserAccount());

                    this.ut.commit();
                    this.commandefournisseur = null;
                    this.lignecommandefournisseurs.clear();
                    this.detail = (this.supprimer = this.modifier = this.imprimer = true);
                    JsfUtil.addSuccessMessage(message);

                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
                } else {
                    notifyError("liste_article_vide");
                }
            } else if (this.commandefournisseur != null) {
                updateTotal();
                this.ut.begin();

                this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);

                if (!this.lignecommandefournisseurs.isEmpty()) {
                    for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                        if (lcf.getIdlignecommandefournisseur() != 0L) {
                            this.lignecommandefournisseurFacadeLocal.edit(lcf);
                        } else {
                            lcf.setIdlignecommandefournisseur(this.lignecommandefournisseurFacadeLocal.nextVal());
                            lcf.setIdcommandefournisseur(this.commandefournisseur);
                            this.lignecommandefournisseurFacadeLocal.create(lcf);
                        }
                    }
                }

                this.ut.commit();
                this.commandefournisseur = null;
                this.lignecommandefournisseurs.clear();
                this.detail = (this.supprimer = this.modifier = this.imprimer = true);

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
            if (this.commandefournisseur != null) {
                if (this.commandefournisseur.getLivre()) {
                    notifyError("commande_deja_livree");
                    return;
                }

                if (!Utilitaires.isAccess(36L)) {
                    notifyError("acces_refuse");
                    this.detail = this.supprimer = this.modifier = this.imprimer = true;
                    this.commandefournisseur = null;
                    return;
                }

                this.ut.begin();

                List<Lignecommandefournisseur> temp = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur());
                if (!temp.isEmpty()) {
                    for (Lignecommandefournisseur lcf : temp) {
                        this.lignecommandefournisseurFacadeLocal.remove(lcf);
                    }
                }
                this.commandefournisseurFacadeLocal.remove(this.commandefournisseur);
                this.ut.commit();
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de la commande fournisseur : " + this.commandefournisseur.getCode() + " Montant : " + this.commandefournisseur.getMontant() + " Fournisseur : " + this.commandefournisseur.getIdfournisseur().getNom(), SessionMBean.getUserAccount());
                this.commandefournisseur = null;
                this.supprimer = this.modifier = this.imprimer = true;
                notifySuccess();
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Commandefournisseur c) {
        this.commandefournisseur = c;
        print();
    }

    public void initEdit(Commandefournisseur c) {
        this.commandefournisseur = c;
        prepareEdit();
    }

    public void initView(Commandefournisseur c) {
        this.commandefournisseur = c;
        prepareview();
    }

    public void initDelete(Commandefournisseur c) {
        this.commandefournisseur = c;
        delete();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess((37L))) {
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

    public void addArticle() {
        try {
            Lignecommandefournisseur l = this.lignecommandefournisseur;
            l.setIdlignecommandefournisseur((0L));

            l.setIdarticle(this.article);

            boolean drapeau = false;
            int i = 0;
            for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                if (lcf.getIdarticle().getIdarticle().equals(l.getIdarticle().getIdarticle())) {
                    drapeau = true;
                    break;
                }
                i++;
            }

            if (!drapeau) {
                Unite u = this.unite;
                l.setIdunite(u);
                this.lignecommandefournisseurs.add(l);
                updateTotal();
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
                this.lignecommandefournisseur = new Lignecommandefournisseur();
                this.article = new Article();
                return;
            }
            JsfUtil.addErrorMessage("Article existant dans la commande");
        } catch (Exception e) {
            e.printStackTrace();
            this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("formulaire_incomplet"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void removeArticle(int index) {
        try {
            boolean trouve = false;
            this.ut.begin();

            Lignecommandefournisseur lcf = (Lignecommandefournisseur) this.lignecommandefournisseurs.get(index);
            if (lcf.getIdlignecommandefournisseur() != 0L) {
                trouve = true;
                this.lignecommandefournisseurFacadeLocal.remove(lcf);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'article : " + lcf.getIdarticle().getLibelle() + " quantité : " + lcf.getQuantite() + " dans la commande : " + this.commandefournisseur.getCode(), SessionMBean.getUserAccount());
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
        Double resultat = 0d;
        int i = 0;
        for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
            (this.lignecommandefournisseurs.get(i)).setQuantitemultiple((lcf.getQuantite() * lcf.getUnite()));
            (this.lignecommandefournisseurs.get(i)).setQuantitereduite(((this.lignecommandefournisseurs.get(i)).getQuantitemultiple() / lcf.getIdarticle().getUnite()));
            resultat += (lcf.getMontant() * lcf.getQuantite());
            i++;
        }
        this.commandefournisseur.setMontant(resultat);
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
            this.cout_quantite = 0d;
            if (this.lignecommandefournisseur.getQuantite() != null) {
                if (this.lignecommandefournisseur.getUnite() != null) {
                    this.lignecommandefournisseur.setQuantitemultiple((this.lignecommandefournisseur.getQuantite() * this.lignecommandefournisseur.getUnite()));
                }
                if (this.lignecommandefournisseur.getMontant() != null) {
                    this.cout_quantite = (this.lignecommandefournisseur.getMontant() * this.lignecommandefournisseur.getQuantite());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.cout_quantite = 0d;
        }
    }

    public void updatedata() {
        try {
            if (this.article != null) {
                this.famille = this.article.getIdfamille();
                this.lignecommandefournisseur.setMontant(this.article.getCoutachat());
                this.lignecommandefournisseur.setUnite(this.article.getUnite());
                this.unite = this.article.getIdunite();
                this.lignecommandefournisseur.setUnite(this.article.getUnite());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatedataLot() {
        try {
            if (this.lot != null) {
                this.lignecommandefournisseur.setMontant(this.lot.getPrixachat());
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
