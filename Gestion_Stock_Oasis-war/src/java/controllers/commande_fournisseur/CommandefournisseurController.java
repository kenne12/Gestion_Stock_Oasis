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
public class CommandefournisseurController extends AbstractCommandefournisseurController
        implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void updateCalculTva() {
        /*  46 */ updateTotal();
    }

    public void prepareCreate() {
        try {
            /*  52 */ if (!Utilitaires.isAccess(Long.valueOf(34L))) {
                /*  53 */ notifyError("acces_refuse");
                /*  54 */ return;
            }
            /*  56 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
            /*  57 */ this.mode = "Create";
            /*  58 */ this.article = new Article();
            /*  59 */ this.fournisseur = new Fournisseur();

            /*  61 */ this.commandefournisseur = new Commandefournisseur();
            /*  62 */ this.lignecommandefournisseurs.clear();

            /*  64 */ this.commandefournisseur.setCode("-");
            /*  65 */ this.commandefournisseur.setDatecommande(new Date());
            /*  66 */ this.commandefournisseur.setDateprevlivrasion(new Date());
            /*  67 */ this.commandefournisseur.setLivre(Boolean.valueOf(false));
            /*  68 */ this.commandefournisseur.setTauxsatisfaction(Double.valueOf(0.0D));
            /*  69 */ this.articles = this.articleFacadeLocal.findAllRange(true);
            /*  70 */ this.total = Double.valueOf(0.0D);
        } catch (Exception e) {
            /*  72 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            /*  73 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void prepareCreateCommande() {
        /*  78 */ this.famille = new Famille();
        /*  79 */ this.article = new Article();
        /*  80 */ this.lignecommandefournisseur = new Lignecommandefournisseur();
        /*  81 */ this.lignecommandefournisseur.setUnite(Double.valueOf(1.0D));

        /*  83 */ this.cout_quantite = Double.valueOf(0.0D);
        /*  84 */ RequestContext.getCurrentInstance().execute("PF('ArticleCreateDialog').show()");
    }

    public void prepareEdit() {
        try {
            /*  91 */ if (this.commandefournisseur == null) {
                /*  92 */ notifyError("not_row_selected");
                /*  93 */ return;
            }

            /*  96 */ if (this.commandefournisseur.getLivre().booleanValue()) {
                /*  97 */ notifyError("commande_deja_livree");
                /*  98 */ return;
            }

            /* 101 */ if (!Utilitaires.isAccess(Long.valueOf(35L))) {
                /* 102 */ notifyError("acces_refuse");
                /* 103 */ this.commandefournisseur = null;
                /* 104 */ return;
            }

            /* 107 */ this.mode = "Edit";
            /* 108 */ this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur().longValue());
            /* 109 */ this.total = this.commandefournisseur.getMontant();

            /* 111 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').show()");
        } catch (Exception e) {
            /* 113 */ notifyFail(e);
        }
    }

    public void prepareview() {
        try {
            /* 119 */ if (this.commandefournisseur != null) {
                /* 120 */ this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur().longValue());
                /* 121 */ if (!this.lignecommandefournisseurs.isEmpty()) {
                    /* 122 */ RequestContext.getCurrentInstance().execute("PF('FactureDetailDialog').show()");
                    /* 123 */ return;
                }
                /* 125 */ notifyError("liste_article_vide");
            } else {
                /* 128 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 131 */ notifyFail(e);
        }
    }

    public void filterArticle() {
        try {
            /* 137 */ this.articles.clear();
            /* 138 */ this.article = new Article();
            /* 139 */ this.lot = new Lot();
            /* 140 */ if (this.famille.getIdfamille() != null) /* 141 */ {
                this.articles = this.articleFacadeLocal.findByFamille(this.famille.getIdfamille().longValue());
            }
        } catch (Exception e) {
            /* 144 */ e.printStackTrace();
        }
    }

    public void create() {
        try {
            String message;
            /* 150 */ if ("Create".equals(this.mode)) {
                /* 152 */ if (!this.lignecommandefournisseurs.isEmpty()) {
                    /* 154 */ message = "";

                    /* 156 */ updateTotal();

                    /* 158 */ this.commandefournisseur.setIdcommandefournisseur(this.commandefournisseurFacadeLocal.nextVal());
                    /* 159 */ this.commandefournisseur.setIdfournisseur(this.fournisseur);
                    /* 160 */ this.commandefournisseur.setPaye(Boolean.valueOf(false));
                    /* 161 */ this.commandefournisseur.setLivre(Boolean.valueOf(false));
                    /* 162 */ this.commandefournisseur.setMontant(this.total);
                    /* 163 */ this.commandefournisseur.setCode("CF" + Utilitaires.genererCodeDemande("", this.commandefournisseur.getIdcommandefournisseur()));

                    /* 165 */ this.ut.begin();

                    /* 167 */ this.commandefournisseurFacadeLocal.create(this.commandefournisseur);

                    /* 169 */ for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                        /* 170 */ lcf.setIdlignecommandefournisseur(this.lignecommandefournisseurFacadeLocal.nextVal());
                        /* 171 */ lcf.setIdcommandefournisseur(this.commandefournisseur);
                        /* 172 */ this.lignecommandefournisseurFacadeLocal.create(lcf);
                    }

                    /* 175 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la commande fournisseur : " + this.commandefournisseur.getCode(), SessionMBean.getUserAccount());

                    /* 177 */ this.ut.commit();
                    /* 178 */ this.commandefournisseur = null;
                    /* 179 */ this.lignecommandefournisseurs.clear();
                    /* 180 */ this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));
                    /* 181 */ JsfUtil.addSuccessMessage(message);

                    /* 183 */ notifySuccess();
                    /* 184 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
                } else {
                    /* 186 */ notifyError("liste_article_vide");
                }
            } /* 189 */ else if (this.commandefournisseur != null) {
                /* 191 */ updateTotal();
                /* 192 */ this.ut.begin();

                /* 194 */ this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);

                /* 196 */ if (!this.lignecommandefournisseurs.isEmpty()) {
                    /* 197 */ for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                        /* 198 */ if (lcf.getIdlignecommandefournisseur().longValue() != 0L) {
                            /* 199 */ this.lignecommandefournisseurFacadeLocal.edit(lcf);
                        } else {
                            /* 201 */ lcf.setIdlignecommandefournisseur(this.lignecommandefournisseurFacadeLocal.nextVal());
                            /* 202 */ lcf.setIdcommandefournisseur(this.commandefournisseur);
                            /* 203 */ this.lignecommandefournisseurFacadeLocal.create(lcf);
                        }
                    }
                }

                /* 208 */ this.ut.commit();
                /* 209 */ this.commandefournisseur = null;
                /* 210 */ this.lignecommandefournisseurs.clear();
                /* 211 */ this.detail = (this.supprimer = this.modifier = this.imprimer = Boolean.valueOf(true));

                /* 213 */ notifySuccess();
                /* 214 */ RequestContext.getCurrentInstance().execute("PF('CommandeCreateDialog').hide()");
            } else {
                /* 217 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 221 */ notifyFail(e);
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
        /* 269 */ this.commandefournisseur = c;
        /* 270 */ prepareEdit();
    }

    public void initView(Commandefournisseur c) {
        /* 274 */ this.commandefournisseur = c;
        /* 275 */ prepareview();
    }

    public void initDelete(Commandefournisseur c) {
        /* 279 */ this.commandefournisseur = c;
        /* 280 */ delete();
    }

    public void print() {
        try {
            /* 285 */ if (!Utilitaires.isAccess(Long.valueOf(37L))) {
                /* 286 */ notifyError("acces_refuse");
                /* 287 */ this.commandefournisseur = null;
                /* 288 */ return;
            }

            /* 291 */ if (this.commandefournisseur != null) {
                /* 295 */ RequestContext.getCurrentInstance().execute("PF('FactureImprimerDialog').show()");
            } /* 297 */ else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 300 */ notifyFail(e);
        }
    }

    public void addArticle() {
        try {
            Lignecommandefournisseur l = this.lignecommandefournisseur;
            l.setIdlignecommandefournisseur(Long.valueOf(0L));

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
            /* 343 */ boolean trouve = false;
            /* 344 */ this.ut.begin();

            /* 346 */ Lignecommandefournisseur lcf = (Lignecommandefournisseur) this.lignecommandefournisseurs.get(index);
            /* 347 */ if (lcf.getIdlignecommandefournisseur() != 0L) {
                /* 348 */ trouve = true;
                /* 349 */ this.lignecommandefournisseurFacadeLocal.remove(lcf);
                /* 350 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression de l'article : " + lcf.getIdarticle().getLibelle() + " quantité : " + lcf.getQuantite() + " dans la commande : " + this.commandefournisseur.getCode(), SessionMBean.getUserAccount());
            }
            /* 352 */ this.lignecommandefournisseurs.remove(index);

            /* 354 */ updateTotal();
            /* 355 */ if (trouve) {
                /* 356 */ this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);
            }
            /* 358 */ this.ut.commit();
            /* 359 */ JsfUtil.addSuccessMessage("Supprimé");
        } catch (Exception e) {
            /* 361 */ e.printStackTrace();
            /* 362 */ JsfUtil.addErrorMessage(this.routine.localizeMessage("echec_operation"));
        }
    }

    public Double calculTotal() {
        /* 367 */ Double resultat = Double.valueOf(0.0D);
        /* 368 */ int i = 0;
        /* 369 */ for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
            /* 370 */ ((Lignecommandefournisseur) this.lignecommandefournisseurs.get(i)).setQuantitemultiple(Double.valueOf(lcf.getQuantite().doubleValue() * lcf.getUnite().doubleValue()));
            /* 371 */ ((Lignecommandefournisseur) this.lignecommandefournisseurs.get(i)).setQuantitereduite(Double.valueOf(((Lignecommandefournisseur) this.lignecommandefournisseurs.get(i)).getQuantitemultiple().doubleValue() / lcf.getIdarticle().getUnite().doubleValue()));
            /* 372 */ resultat = Double.valueOf(resultat.doubleValue() + lcf.getMontant().doubleValue() * lcf.getQuantite().doubleValue());
            /* 373 */ i++;
        }
        /* 375 */ this.commandefournisseur.setMontant(resultat);
        /* 376 */ return resultat;
    }

    public void updateTotal() {
        try {
            /* 381 */ this.total = calculTotal();
        } catch (Exception e) {
            /* 383 */ e.printStackTrace();
        }
    }

    public void updateTotaux() {
        try {
            /* 389 */ this.cout_quantite = Double.valueOf(0.0D);
            /* 390 */ if (this.lignecommandefournisseur.getQuantite() != null) {
                /* 391 */ if (this.lignecommandefournisseur.getUnite() != null) {
                    /* 392 */ this.lignecommandefournisseur.setQuantitemultiple(Double.valueOf(this.lignecommandefournisseur.getQuantite().doubleValue() * this.lignecommandefournisseur.getUnite().doubleValue()));
                }
                /* 394 */ if (this.lignecommandefournisseur.getMontant() != null) /* 395 */ {
                    this.cout_quantite = Double.valueOf(this.lignecommandefournisseur.getMontant().doubleValue() * this.lignecommandefournisseur.getQuantite().doubleValue());
                }
            }
        } catch (Exception e) {
            /* 399 */ e.printStackTrace();
            /* 400 */ this.cout_quantite = Double.valueOf(0.0D);
        }
    }

    public void updatedata() {
        try {
            /* 406 */ if (this.article != null) {
                /* 407 */ this.famille = this.article.getIdfamille();
                /* 408 */ this.lignecommandefournisseur.setMontant(this.article.getCoutachat());
                /* 409 */ this.lignecommandefournisseur.setUnite(this.article.getUnite());
                /* 410 */ this.unite = this.article.getIdunite();
                /* 411 */ this.lignecommandefournisseur.setUnite(this.article.getUnite());
            }
        } catch (Exception e) {
            /* 414 */ e.printStackTrace();
        }
    }

    public void updatedataLot() {
        try {
            /* 420 */ if (this.lot != null) /* 421 */ {
                this.lignecommandefournisseur.setMontant(this.lot.getPrixachat());
            }
        } catch (Exception e) {
            /* 424 */ e.printStackTrace();
        }
    }

    public void notifyError(String message) {
        /* 429 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 430 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 434 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 435 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 436 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 440 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 441 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 442 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
