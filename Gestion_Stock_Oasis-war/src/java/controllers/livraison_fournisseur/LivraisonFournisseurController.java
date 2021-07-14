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
            this.commandefournisseurs = this.commandefournisseurFacadeLocal.findByLivre(false);
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

                /* 163 */ this.fournisseur = this.commandefournisseur.getIdfournisseur();
                /* 164 */ this.lignecommandefournisseurs = this.lignecommandefournisseurFacadeLocal.findByCommande(this.commandefournisseur.getIdcommandefournisseur());
                /* 165 */ this.commandefournisseurs.clear();

                /* 167 */ this.commandefournisseur.setDateeffectlivraison(new Date());
                /* 168 */ int conteur = 0;
                /* 169 */ for (Lignecommandefournisseur lcf : this.lignecommandefournisseurs) {
                    /* 170 */ List lotTemps = this.lotFacadeLocal.findByArticleRangeDesc(lcf.getIdarticle().getIdarticle(), lcf.getIdarticle().getPerissable().booleanValue());

                    /* 172 */ Lignelivraisonfournisseur object = new Lignelivraisonfournisseur();
                    /* 173 */ object.setIdlignelivraisonfournisseur((0L));

                    /* 175 */ object.setIdlot((Lot) lotTemps.get(0));
                    /* 176 */ object.setQuantite(lcf.getQuantite());
                    /* 177 */ object.setUnite(lcf.getUnite());
                    /* 178 */ object.setPrixachat(object.getIdlot().getPrixachat());
                    /* 179 */ object.setQuantitemultiple(lcf.getQuantitemultiple());
                    /* 180 */ object.setQuantitereduite(lcf.getQuantitereduite());
                    /* 181 */ object.setIdunite(lcf.getIdunite());

                    /* 183 */ this.lignelivraisonfournisseurs.add(object);
                    /* 184 */ this.lignelivraisonfournisseurs.get(conteur).setTauxsatisfaction(0d);
                    /* 185 */ conteur++;
                }
                /* 187 */ this.commandefournisseur.setTauxsatisfaction((0.0D));
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
                    this.livraisonfournisseur.setIdUtilisateur(SessionMBean.getUserAccount().getIdutilisateur());
                    /* 235 */ this.livraisonfournisseur.setIdmvtstock(this.mvtstock);

                    /* 237 */ this.livraisonfournisseurFacadeLocal.create(this.livraisonfournisseur);

                    /* 239 */ for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
                        /* 240 */ llf.setIdlignelivraisonfournisseur(this.lignelivraisonfournisseurFacadeLocal.nextVal());
                        /* 241 */ llf.setIdlivraisonfournisseur(this.livraisonfournisseur);
                        /* 242 */ llf.setQuantitemultiple(Double.valueOf(llf.getQuantite() * llf.getUnite()));
                        /* 243 */ this.lignelivraisonfournisseurFacadeLocal.create(llf);

                        /* 245 */ llf.setIdlot(this.lotFacadeLocal.find(llf.getIdlot().getIdlot()));

                        /* 247 */ llf.getIdlot().getIdarticle().setQuantitereduite(Double.valueOf(llf.getIdlot().getIdarticle().getQuantitereduite() + llf.getQuantitereduite()));
                        /* 248 */ llf.getIdlot().getIdarticle().setQuantitestock(Double.valueOf(llf.getIdlot().getIdarticle().getQuantitestock() + llf.getQuantite()));
                        /* 249 */ llf.getIdlot().getIdarticle().setQuantitemultiple(Double.valueOf(llf.getIdlot().getIdarticle().getQuantitemultiple() + llf.getQuantitemultiple()));
                        /* 250 */ this.articleFacadeLocal.edit(llf.getIdlot().getIdarticle());

                        /* 252 */ llf.getIdlot().setQuantitereduite(Double.valueOf(llf.getIdlot().getQuantitereduite() + llf.getQuantitereduite()));
                        /* 253 */ llf.getIdlot().setQuantite(Double.valueOf(llf.getIdlot().getQuantite() + llf.getQuantite()));
                        /* 254 */ llf.getIdlot().setQuantitemultiple(Double.valueOf(llf.getIdlot().getQuantitemultiple() + llf.getQuantitemultiple()));
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
            } else if (this.livraisonfournisseur != null) {
                this.ut.begin();
                
                updateTotal();
                this.livraisonfournisseurFacadeLocal.edit(this.livraisonfournisseur);
                
                this.mvtstock = this.livraisonfournisseur.getIdmvtstock();
                
                if (!this.lignelivraisonfournisseurs.isEmpty()) {
                    for (Lignelivraisonfournisseur s : this.lignelivraisonfournisseurs) {
                        Lignelivraisonfournisseur sp = this.lignelivraisonfournisseurFacadeLocal.find(s.getIdlignecommandefournisseur());
                        if (sp.getQuantite() != s.getQuantite()) {
                            /* 304 */ Article art = sp.getIdlot().getIdarticle();
                            /* 305 */ art.setQuantitestock((art.getQuantitestock() + sp.getQuantite() - sp.getQuantite()));
                            /* 306 */ this.articleFacadeLocal.edit(art);

                            /* 308 */ Lot l = sp.getIdlot();
                            /* 309 */ l.setQuantite((l.getQuantite() + sp.getQuantite() - sp.getQuantite()));
                            /* 310 */ this.lotFacadeLocal.edit(l);

                            /* 312 */ Lignemvtstock lmvts = new Lignemvtstock();
                            /* 313 */ lmvts.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
                            /* 314 */ lmvts.setIdmvtstock(this.mvtstock);
                            /* 315 */ lmvts.setIdlot(s.getIdlot());
                            
                            if (s.getQuantite() > sp.getQuantite()) {
                                lmvts.setQteentree((s.getQuantite() - sp.getQuantite()));
                                lmvts.setQtesortie((0.0D));
                                lmvts.setReste(s.getIdlot().getQuantite());
                            } else {
                                lmvts.setQteentree((0.0D));
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
                /* 334 */ this.commandefournisseurs.clear();
                /* 335 */ this.lignemvtstocks.clear();
                /* 336 */ this.commandefournisseur = new Commandefournisseur();
                /* 337 */ this.livraisonfournisseur = null;
                /* 338 */ this.supprimer = (this.modifier = this.imprimer = this.detail = (true));
                
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
                    /* 381 */ this.livraisonfournisseurFacadeLocal.remove(this.livraisonfournisseur);

                    /* 383 */ this.commandefournisseur = this.commandefournisseurFacadeLocal.find(this.livraisonfournisseur.getIdcommandefournisseur().getIdcommandefournisseur());

                    /* 385 */ this.commandefournisseur.setLivre((false));
                    /* 386 */ this.commandefournisseur.setCode("-");
                    /* 387 */ this.commandefournisseur.setTauxsatisfaction((0.0D));
                    /* 388 */ this.commandefournisseurFacadeLocal.edit(this.commandefournisseur);

                    /* 390 */ Mvtstock mTemp = this.livraisonfournisseur.getIdmvtstock();
                    
                    List<Lignemvtstock> lmvt = this.lignemvtstockFacadeLocal.findByIdmvt(mTemp.getIdmvtstock());
                    for (Lignemvtstock l : lmvt) {
                        this.lignemvtstockFacadeLocal.remove(l);
                    }
                    /* 396 */ this.mvtstockFacadeLocal.remove(mTemp);

                    /* 398 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Annulation de livraison fournisseur : " + this.livraisonfournisseur.getCode() + " ; Montant : " + this.livraisonfournisseur.getMontant() + " Fournisseur : " + this.livraisonfournisseur.getIdcommandefournisseur().getIdfournisseur().getNom(), SessionMBean.getUserAccount());
                    /* 399 */ this.ut.commit();

                    /* 401 */ this.livraisonfournisseur = null;
                    /* 402 */ this.commandefournisseur = new Commandefournisseur();
                    /* 403 */ this.supprimer = (this.modifier = this.imprimer = this.detail = (true));
                    /* 404 */ notifySuccess();
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
        Double resultat = (0.0D);
        int i = 0;
        for (Lignelivraisonfournisseur llf : this.lignelivraisonfournisseurs) {
            /* 520 */ resultat += (llf.getIdlot().getIdarticle().getCoutachat() * llf.getQuantite());
            /* 521 */ this.lignelivraisonfournisseurs.get(i).setQuantitemultiple((llf.getQuantite() * llf.getUnite()));
            /* 522 */ this.lignelivraisonfournisseurs.get(i).setQuantitereduite(((this.lignelivraisonfournisseurs.get(i)).getQuantitemultiple() / llf.getIdlot().getIdarticle().getUnite().doubleValue()));
            /* 523 */ i++;
        }
        return resultat;
    }
    
    public void updateTotal() {
        try {
            this.total = calculTotal();
            this.livraisonfournisseur.setMontant(this.total);
        } catch (Exception e) {
            /* 533 */ e.printStackTrace();
        }
    }
    
    public void updateTotaux() {
        try {
            this.cout_quantite = (0.0D);
            if ((this.lignecommandefournisseur.getQuantite() != null)
                    && (this.lignecommandefournisseur.getMontant() != null)) {
                this.cout_quantite = (this.lignecommandefournisseur.getMontant() * this.lignecommandefournisseur.getQuantite());
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
