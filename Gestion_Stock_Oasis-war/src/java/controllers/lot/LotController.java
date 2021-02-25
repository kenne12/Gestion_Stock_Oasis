package controllers.lot;

import entities.Article;
import entities.Lot;
import entities.Magasinarticle;
import entities.Magasinlot;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class LotController extends AbstractLotController implements Serializable {

    @PostConstruct
    private void init() {
        this.article = new Article();
        try {
            this.articles = this.articleFacadeLocal.findByPerissable(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prepareCreate() {
        if (!Utilitaires.isAccess(38L)) {
            notifyError("acces_refuse");
            return;
        }

        this.mode = "Create";
        this.article = new Article();
        this.lot = new Lot();
        this.lot.setQuantite(0.0);
        this.lot.setEtat(true);
        this.disableProduct = false;
        this.dateRequired = true;
        RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').show()");
    }

    public void prepareEdit() {
        if (!Utilitaires.isAccess(39L)) {
            notifyError("acces_refuse");
            return;
        }

        if (Objects.equals(lot, null)) {
            notifyError("not_row_selected");
            return;
        }

        this.mode = "Edit";
        this.disableProduct = true;
        this.article = this.lot.getIdarticle();
        this.dateRequired = true;
        if (!this.lot.getIdarticle().getPerissable()) {
            this.dateRequired = false;
            if (!this.articles.contains(this.article)) {
                this.articles.add(this.article);
            }
        }

        if ((!this.lot.getIdarticle().getEtat()) && (!this.articles.contains(this.lot.getIdarticle()))) {
            this.articles.add(this.lot.getIdarticle());
        }

        RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').show()");
    }

    public void updateData() {
        try {
            if (this.article.getIdarticle() != null) {
                this.article = this.articleFacadeLocal.find(this.article.getIdarticle());
                this.lot.setPrixachat(this.article.getCoutachat());
                this.lot.setPrixunitaire(this.article.getPrixunit());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void create() {
        try {
            if (this.mode.equals("Create")) {
                if ((this.lot.getDateperemption().after(this.lot.getDatefabrication())) || (this.lot.getDateperemption().equals(this.lot.getDatefabrication()))) {
                    this.lot.setIdlot(this.lotFacadeLocal.nextVal());
                    this.lot.setIdarticle(this.article);
                    this.lot.setDateenregistrement(new Date());
                    this.lot.setEtat(true);
                    this.lot.setQuantitemultiple(0.0);

                    this.lotFacadeLocal.create(this.lot);

                    List<Magasinarticle> list = this.magasinarticleFacadeLocal.findByIdarticle(this.article.getIdarticle());
                    for (Magasinarticle m : list) {
                        Magasinlot obj1 = new Magasinlot();
                        obj1.setIdmagasinlot(this.magasinlotFacadeLocal.nextVal());
                        obj1.setIdmagasinarticle(m);
                        obj1.setIdlot(this.lot);
                        obj1.setQuantite(0.0);
                        obj1.setUnite(this.article.getUnite());
                        obj1.setQuantitemultiple(0.0);
                        obj1.setQuantitereduite(0.0);
                        obj1.setQuantitevirtuelle(0.0);
                        obj1.setQuantitesecurite(0.0);
                        obj1.setEtat(true);
                        this.magasinlotFacadeLocal.create(obj1);
                    }

                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du lot : " + this.lot.getNumero() + " de l'article -> " + this.article.getLibelle(), SessionMBean.getUserAccount());
                    this.lot = new Lot();
                    this.article = new Article();
                    RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').hide()");
                    notifySuccess();
                    return;
                }
                notifyError("erreur_ecart_date");
            } else if (this.lot != null) {
                this.lotFacadeLocal.edit(this.lot);
                RequestContext.getCurrentInstance().execute("PF('LotCreerDialog').hide()");
                notifySuccess();
                this.articles = this.articleFacadeLocal.findByPerissable(true);
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void delete() {
        try {
            if (this.lot != null) {
                if (!Utilitaires.isAccess(40L)) {
                    notifyError("acces_refuse");
                    return;
                }

                if (!this.lot.getIdarticle().getPerissable()) {
                    notifyError("impossible_supression_lot_initial");
                    return;
                }

                this.lotFacadeLocal.remove(this.lot);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du lot -> " + this.lot.getNumero() + " de l'article -> " + this.lot.getIdarticle().getLibelle(), SessionMBean.getUserAccount());
                this.lot = null;
                notifySuccess();
            } else {
                notifyError("not_row_selected");
            }
        } catch (Exception e) {
            notifyFail(e);
        }
    }

    public void print() {
        try {
            if (!Utilitaires.isAccess(9L)) {
                notifyError("acces_refuse");
                return;
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
            RequestContext.getCurrentInstance().execute("PF('LotImprimerDialog').show()");
        } catch (Exception e) {
            notifyFail(e);
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
        RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').show()");
        this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
        RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
    }
}
