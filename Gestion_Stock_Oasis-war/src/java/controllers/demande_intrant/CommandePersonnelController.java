package controllers.demande_intrant;

import entities.Client;
import entities.Demande;
import entities.Famille;
import entities.Lignedemande;
import entities.Magasin;
import entities.Magasinarticle;
import entities.Projet;
import entities.Unite;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import org.primefaces.context.RequestContext;
import utils.JsfUtil;
import utils.PrintUtils;
import utils.Printer;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class CommandePersonnelController extends AbstractCommandePersonnelController implements Serializable {

    @PostConstruct
    private void init() {
        this.conteur = 0;
        demande = new Demande();
        demande.setClient(new Client());
    }

    public void updateCalculTva() {
        updateTotal();
    }

    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(30L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
            this.mode = "Create";
            this.magasin = new Magasin();
            this.magasinarticle = new Magasinarticle();

            this.client = new Client(0);
            this.lignedemandes.clear();

            this.demande = new Demande();
            this.demande.setTauxRemise(SessionMBean.getParametrage().getTauxRemise());
            this.demande.setTauxTva(SessionMBean.getParametrage().getTauxTva());

            this.demande.setValidee(false);
            this.demande.setMontant(0.0D);
            this.demande.setMotif("-");
            this.demande.setDatedemande(new Date());
            this.demande.setDateprevlivraison(new Date());
            this.magasinarticles.clear();

            this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, this.personnel);
            this.total = 0.0D;
            this.conteur = 0;
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        this.famille = new Famille();
        this.magasin = new Magasin();
        this.unite = new Unite();
        this.magasinarticle = new Magasinarticle();
        this.lignedemande = new Lignedemande();
        this.lignedemande.setUnite(1.0D);
        this.cout_quantite = 0.0D;
        this.libelle_article = "-";
        RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
    }

    public void prepareEdit() {
        try {
            if (this.demande == null) {
                notifyError("not_row_selected");
                return;
            }

            if (this.demande.getValidee()) {
                notifyError("commande_deja_livree");
                return;
            }

            if (!Utilitaires.isAccess(31L)) {
                notifyError("acces_refuse");
                this.demande = null;
                return;
            }

            this.mode = "Edit";
            this.showSelector = false;

            this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());
            this.client = this.demande.getClient();
            this.total = this.demande.getMontant();
            RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            if (this.demande != null) {
                this.lignedemandes = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());
                if (!this.lignedemandes.isEmpty()) {
                    RequestContext.getCurrentInstance().execute("PF('DemandeDetailDialog').show()");
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
            this.magasinarticles.clear();
            this.magasinarticle = new Magasinarticle();
            if (this.famille.getIdfamille() != null) {
                this.magasinarticles = this.magasinarticleFacadeLocal.findByIdmagasinIdfamille(this.magasin.getIdmagasin(), this.famille.getIdfamille(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void filterProductByMagasin() {
        try {
            this.magasinarticles.clear();
            if (this.magasin.getIdmagasin() != null) {
                this.magasinarticles = this.magasinarticleFacadeLocal.findByIdmagasinQtyGreater(this.magasin.getIdmagasin(), true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void selectProduct() {
        if (this.magasinarticle != null) {
            this.unite = this.magasinarticle.getIdarticle().getIdunite();
            this.lignedemande.setIdunite(this.magasinarticle.getIdarticle().getIdunite());
            this.lignedemande.setPrixUnitaire(this.magasinarticle.getIdarticle().getCoutachat());
            this.lignedemande.setMontant(this.magasinarticle.getIdarticle().getCoutachat());
            this.lignedemande.setQuantite(1.0);
            this.lignedemande.setQuantitemultiple(1.0);
            this.magasin = this.magasinarticle.getIdmagasin();
            this.libelle_article = this.magasinarticle.getIdarticle().getLibelle();
        }
    }

    public void create() {
        try {
            String message;
            if ("Create".equals(this.mode)) {
                if (!this.lignedemandes.isEmpty()) {
                    message = "";

                    updateTotal();

                    this.demande.setIddemande(this.demandeFacadeLocal.nextVal());
                    this.demande.setClient(this.client);
                    this.demande.setTauxsatisfaction(0.0D);
                    this.demande.setCode("D" + Utilitaires.genererCodeDemande("", this.demande.getIddemande()));
                    this.demandeFacadeLocal.create(this.demande);
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la commande : " + this.client.getNom() + " ; Code : " + this.demande.getCode(), SessionMBean.getUserAccount());

                    this.ut.begin();

                    for (Lignedemande ld : this.lignedemandes) {
                        ld.setIdlignedemande(this.lignedemandeFacadeLocal.nextVal());
                        ld.setIddemande(this.demande);
                        ld.setQtestock((ld.getIdmagasinarticle().getQuantitemultiple() - ld.getIdmagasinarticle().getQuantitevirtuelle()));
                        this.lignedemandeFacadeLocal.create(ld);

                        Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ld.getIdmagasinarticle().getIdmagasinarticle());
                        maTemp.setQuantitevirtuelle((maTemp.getQuantitevirtuelle() + ld.getQuantitemultiple()));
                        this.magasinarticleFacadeLocal.edit(maTemp);
                    }

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la commande : " + this.demande.getCode(), SessionMBean.getUserAccount());

                    this.ut.commit();
                    this.demande = null;
                    this.lignedemandes.clear();
                    this.detail = (this.supprimer = this.modifier = this.imprimer = true);
                    JsfUtil.addSuccessMessage(message);

                    notifySuccess();
                    RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
                } else {
                    notifyError("liste_magasinarticle_vide");
                }
            } else if (this.client != null) {
                this.ut.begin();

                this.client = clientFacadeLocal.find(client.getIdclient());
                this.demande.setClient(this.client);

                updateTotal();
                this.demandeFacadeLocal.edit(this.demande);

                if (!this.lignedemandes.isEmpty()) {
                    for (Lignedemande ld : this.lignedemandes) {
                        if (ld.getIdlignedemande() != 0L) {
                            Lignedemande ldOld = lignedemandeFacadeLocal.find(ld.getIdlignedemande());
                            if (!Objects.equals(ld.getQuantitemultiple(), ldOld.getQuantitemultiple())) {
                                if (ld.getQuantitemultiple() > ldOld.getQuantitemultiple()) {
                                    double reste = ld.getQuantitemultiple() - ldOld.getQuantitemultiple();

                                    Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ld.getIdmagasinarticle().getIdmagasinarticle());
                                    maTemp.setQuantitevirtuelle((maTemp.getQuantitevirtuelle() + reste));
                                    this.magasinarticleFacadeLocal.edit(maTemp);
                                    ld.setQtestock(ld.getQtestock() - reste);
                                } else {
                                    double reste = ldOld.getQuantitemultiple() - ld.getQuantitemultiple();

                                    Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ld.getIdmagasinarticle().getIdmagasinarticle());
                                    maTemp.setQuantitevirtuelle((maTemp.getQuantitevirtuelle() - reste));
                                    this.magasinarticleFacadeLocal.edit(maTemp);
                                    ld.setQtestock(ld.getQtestock() + reste);
                                }
                            }
                            this.lignedemandeFacadeLocal.edit(ld);
                        } else {
                            ld.setIdlignedemande(this.lignedemandeFacadeLocal.nextVal());
                            ld.setIddemande(this.demande);
                            ld.setQtestock((ld.getIdmagasinarticle().getQuantitemultiple() - ld.getIdmagasinarticle().getQuantitevirtuelle()));
                            this.lignedemandeFacadeLocal.create(ld);

                            Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ld.getIdmagasinarticle().getIdmagasinarticle());
                            maTemp.setQuantitevirtuelle((maTemp.getQuantitevirtuelle() + ld.getQuantitemultiple()));
                            this.magasinarticleFacadeLocal.edit(maTemp);
                        }
                    }
                }

                this.ut.commit();
                this.demande = new Demande();
                this.lignedemandes.clear();
                this.detail = this.supprimer = this.modifier = this.imprimer = true;

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
            if (this.demande != null) {
                if (this.demande.getValidee()) {
                    notifyError("commande_deja_livree");
                    return;
                }

                if (!Utilitaires.isAccess(32L)) {
                    notifyError("acces_refuse");
                    this.detail = (this.supprimer = this.modifier = this.imprimer = true);
                    this.client = new Client();
                    return;
                }

                this.ut.begin();

                List<Lignedemande> temp = this.lignedemandeFacadeLocal.findByIddemande(this.demande.getIddemande());
                if (!temp.isEmpty()) {
                    for (Lignedemande ld : temp) {
                        this.lignedemandeFacadeLocal.remove(ld);

                        Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(ld.getIdmagasinarticle().getIdmagasinarticle());
                        maTemp.setQuantitevirtuelle(maTemp.getQuantitevirtuelle() - ld.getQuantitemultiple());
                        this.magasinarticleFacadeLocal.edit(maTemp);
                    }
                }
                this.demandeFacadeLocal.remove(this.demande);
                this.ut.commit();
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de la commande : " + this.demande.getCode() + " ; Personnel : " + this.demande.getClient().getNom(), SessionMBean.getUserAccount());
                this.client = new Client();
                this.supprimer = (this.modifier = this.imprimer = true);
                notifySuccess();
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void initPrinter(Demande d) {
        this.demande = d;
        print();
    }

    public void initEdit(Demande d) {
        this.demande = d;
        prepareEdit();
    }

    public void initView(Demande d) {
        this.demande = d;
        prepareview();
    }

    public void initDelete(Demande d) {
        this.demande = d;
        delete();
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(33L)) {
                notifyError("acces_refuse");
                this.demande = null;
                return;
            }

            if (this.demande != null) {
                demande.setLignedemandeList(lignedemandeFacadeLocal.findByIddemande(demande.getIddemande()));
                fileName = PrintUtils.printBonCommandeClient(demande, SessionMBean.getParametrage());
                RequestContext.getCurrentInstance().execute("PF('DemandeImprimerDialog').show()");
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void addArticle() {
        try {
            Lignedemande l = this.lignedemande;
            l.setIdlignedemande(0L);

            if (this.magasinarticle.getQuantitemultiple() < 1.0D) {
                JsfUtil.addWarningMessage(this.routine.localizeMessage("defaut_de_quantite"));
                return;
            }

            if (l.getQuantitemultiple() > this.magasinarticle.getQuantitemultiple() - this.magasinarticle.getQuantitevirtuelle()) {
                JsfUtil.addWarningMessage(this.routine.localizeMessage("quantite_debordee"));
                return;
            }

            Unite u = this.unite;
            l.setIdmagasinarticle(this.magasinarticle);
            l.setIdunite(u);
            l.setMontant(l.getPrixUnitaire() * l.getQuantitemultiple());

            boolean drapeau = false;
            int i = 0;
            for (Lignedemande lcc : this.lignedemandes) {
                if (lcc.getIdmagasinarticle().getIdmagasinarticle().equals(l.getIdmagasinarticle().getIdmagasinarticle())) {
                    drapeau = true;
                    break;
                }
                i++;
            }

            if (!drapeau) {
                this.lignedemandes.add(l);
                RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').hide()");
                JsfUtil.addSuccessMessage(this.routine.localizeMessage("operation_reussie"));
                this.lignedemande = new Lignedemande();
                this.magasinarticle = new Magasinarticle();
                updateTotal();
                return;
            }
            JsfUtil.addErrorMessage("Article existant dans la commande");
            this.conteur += 1;
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

            Lignedemande lcc = (Lignedemande) this.lignedemandes.get(index);
            if (lcc.getIdlignedemande() != 0L) {
                trouve = true;
                this.lignedemandeFacadeLocal.remove(lcc);
                Magasinarticle maTemp = this.magasinarticleFacadeLocal.find(lcc.getIdmagasinarticle().getIdmagasinarticle());
                maTemp.setQuantitevirtuelle((maTemp.getQuantitevirtuelle() - lcc.getQuantitemultiple()));
                this.magasinarticleFacadeLocal.edit(maTemp);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'article : " + lcc.getIdmagasinarticle().getIdarticle().getLibelle() + " quantité : " + lcc.getQuantite() + " dans la facture : " + this.demande.getCode(), SessionMBean.getUserAccount());
            }
            this.lignedemandes.remove(index);

            updateTotal();
            if (trouve) {
                this.clientFacadeLocal.edit(this.client);
            }
            this.ut.commit();
            JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            e.printStackTrace();
            JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal() {
        Double resultat = 0.0;
        int i = 0;
        for (Lignedemande lcc : this.lignedemandes) {
            resultat += (lcc.getPrixUnitaire() * lcc.getQuantite());
            this.lignedemandes.get(i).setQuantitemultiple((lcc.getQuantite() * lcc.getUnite()));
            this.lignedemandes.get(i).setMontant((this.lignedemandes.get(i)).getQuantitemultiple() * lcc.getPrixUnitaire());
            this.lignedemandes.get(i).setQuantitereduite((this.lignedemandes.get(i).getQuantitemultiple() / lcc.getIdmagasinarticle().getIdarticle().getUnite()));
            i++;
        }
        return resultat;
    }

    public void updateTotal() {
        try {
            this.total = calculTotal();
            this.demande.setMontant(this.total);
            this.demande.setMontantRemise((total * demande.getTauxRemise()) / 100);
            this.demande.setMontantHt((total - demande.getMontantRemise()));
            this.demande.setMontantTva((demande.getMontantHt() * demande.getTauxTva()) / 100);
            this.demande.setMontantTtc(this.demande.getMontantTva() + this.demande.getMontantHt());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            this.cout_quantite = 0.0D;
            if ((this.lignedemande.getQuantite() != null) && (this.lignedemande.getMontant() != null)) {
                this.cout_quantite = (this.lignedemande.getMontant() * this.lignedemande.getQuantite());
            }
        } catch (Exception e) {
            e.printStackTrace();
            this.cout_quantite = 0.0D;
        }
    }

    public void updatedata() {
        try {
            if (this.magasinarticle != null) {
                this.famille = this.magasinarticle.getIdarticle().getIdfamille();
                this.lignedemande.setMontant(this.magasinarticle.getIdarticle().getPrixunit());
                this.lignedemande.setUnite(this.magasinarticle.getUnite());
                this.unite = this.magasinarticle.getIdarticle().getIdunite();
                this.lignedemande.setUnite(this.magasinarticle.getIdarticle().getUnite());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateQuantity() {
        try {
            if (this.unite != null) {
                this.lignedemande.setQuantitemultiple((this.lignedemande.getQuantite() * this.lignedemande.getUnite()));
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
