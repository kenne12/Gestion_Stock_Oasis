package controllers.lot;

import entities.Article;
import entities.Lot;
import entities.Magasinarticle;
import entities.Magasinlot;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class LotController extends AbstractLotController
        implements Serializable {

    @PostConstruct
    private void init() {
        /*  38 */ this.article = new Article();
        try {
            /*  40 */ this.articles = this.articleFacadeLocal.findByPerissable(true);
        } catch (Exception e) {
            /*  42 */ e.printStackTrace();
        }
    }

    public void prepareCreate() {
        /*  47 */ if (!Utilitaires.isAccess(Long.valueOf(38L))) {
            /*  48 */ notifyError("acces_refuse");
            /*  49 */ return;
        }

        /*  52 */ this.mode = "Create";
        /*  53 */ this.article = new Article();
        /*  54 */ this.lot = new Lot();
        /*  55 */ this.lot.setQuantite(Double.valueOf(0.0D));
        /*  56 */ this.lot.setEtat(Boolean.valueOf(true));
        /*  57 */ this.disableProduct = false;
        /*  58 */ this.dateRequired = true;
        /*  59 */ RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').show()");
    }

    public void prepareEdit() {
        /*  63 */ if (!Utilitaires.isAccess(Long.valueOf(39L))) {
            /*  64 */ notifyError("acces_refuse");
            /*  65 */ return;
        }

        /*  68 */ if (this.lot.equals(null)) {
            /*  69 */ notifyError("not_row_selected");
            /*  70 */ return;
        }

        /*  73 */ this.mode = "Edit";
        /*  74 */ this.disableProduct = true;
        /*  75 */ this.article = this.lot.getIdarticle();
        /*  76 */ this.dateRequired = true;
        /*  77 */ if (!this.lot.getIdarticle().getPerissable().booleanValue()) {
            /*  78 */ this.dateRequired = false;
            /*  79 */ if (!this.articles.contains(this.article)) {
                /*  80 */ this.articles.add(this.article);
            }
        }

        /*  84 */ if ((!this.lot.getIdarticle().getEtat().booleanValue())
                && /*  85 */ (!this.articles.contains(this.lot.getIdarticle()))) {
            /*  86 */ this.articles.add(this.lot.getIdarticle());
        }

        /*  90 */ RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').show()");
    }

    public void updateData() {
        try {
            /*  95 */ if (this.article.getIdarticle() != null) {
                /*  96 */ this.article = this.articleFacadeLocal.find(this.article.getIdarticle());
                /*  97 */ this.lot.setPrixachat(this.article.getCoutachat());
                /*  98 */ this.lot.setPrixunitaire(this.article.getPrixunit());
            }
        } catch (Exception e) {
            /* 101 */ e.printStackTrace();
        }
    }

    public void create() {
        try {
            /* 107 */ if (this.mode.equals("Create")) {
                /* 109 */ if ((this.lot.getDateperemption().after(this.lot.getDatefabrication())) || (this.lot.getDateperemption().equals(this.lot.getDatefabrication()))) {
                    /* 110 */ this.lot.setIdlot(this.lotFacadeLocal.nextVal());
                    /* 111 */ this.lot.setIdarticle(this.article);
                    /* 112 */ this.lot.setDateenregistrement(new Date());
                    /* 113 */ this.lot.setEtat(Boolean.valueOf(true));
                    /* 114 */ this.lot.setQuantitemultiple(Double.valueOf(0.0D));

                    /* 116 */ this.lotFacadeLocal.create(this.lot);

                    /* 118 */ List<Magasinarticle> list = this.magasinarticleFacadeLocal.findByIdarticle(this.article.getIdarticle().longValue());
                    /* 119 */ for (Magasinarticle m : list) {
                        /* 120 */ Magasinlot obj1 = new Magasinlot();
                        /* 121 */ obj1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                        /* 122 */ obj1.setIdmagasinarticle(m);
                        /* 123 */ obj1.setIdlot(this.lot);
                        /* 124 */ obj1.setQuantite(Double.valueOf(0.0D));
                        /* 125 */ obj1.setUnite(this.article.getUnite());
                        /* 126 */ obj1.setQuantitemultiple(Double.valueOf(0.0D));
                        /* 127 */ obj1.setEtat(Boolean.valueOf(true));
                        /* 128 */ this.magasinlotFacadeLocal.create(obj1);
                    }

                    /* 131 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du lot : " + this.lot.getNumero() + " de l'article -> " + this.article.getLibelle(), SessionMBean.getUserAccount());
                    /* 132 */ this.lot = null;
                    /* 133 */ this.article = new Article();
                    /* 134 */ RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').hide()");
                    /* 135 */ notifySuccess();
                    /* 136 */ return;
                }
                /* 138 */ notifyError("erreur_ecart_date");
            } /* 140 */ else if (this.lot != null) {
                /* 141 */ this.lotFacadeLocal.edit(this.lot);
                /* 142 */ RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').hide()");
                /* 143 */ notifySuccess();
                /* 144 */ this.articles = this.articleFacadeLocal.findByPerissable(true);
            } else {
                /* 146 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 150 */ notifyFail(e);
        }
    }

    public void delete() {
        try {
            /* 156 */ if (this.lot != null) {
                /* 157 */ if (!Utilitaires.isAccess(Long.valueOf(40L))) {
                    /* 158 */ notifyError("acces_refuse");
                    /* 159 */ return;
                }

                /* 162 */ if (!this.lot.getIdarticle().getPerissable().booleanValue()) {
                    /* 163 */ notifyError("impossible_supression_lot_initial");
                    /* 164 */ return;
                }

                /* 178 */ this.lotFacadeLocal.remove(this.lot);
                /* 179 */ Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du lot -> " + this.lot.getNumero() + " de l'article -> " + this.lot.getIdarticle().getLibelle(), SessionMBean.getUserAccount());
                /* 180 */ this.lot = null;
                /* 181 */ notifySuccess();
            } else {
                /* 183 */ notifyError("not_row_selected");
            }
        } catch (Exception e) {
            /* 186 */ notifyFail(e);
        }
    }

    public void print() {
        try {
            /* 192 */ if (!Utilitaires.isAccess(Long.valueOf(9L))) {
                /* 193 */ notifyError("acces_refuse");
                /* 194 */ return;
            }
            /* 196 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            /* 197 */ RequestContext.getCurrentInstance().execute("PF('LotImprimerDialog').show()");
        } catch (Exception e) {
            /* 199 */ notifyFail(e);
        }
    }

    public void notifyError(String message) {
        /* 204 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 205 */ this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
        /* 206 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifySuccess() {
        /* 210 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        /* 211 */ this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
        /* 212 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }

    public void notifyFail(Exception e) {
        /* 216 */ RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').show()");
        /* 217 */ this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        /* 218 */ RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
