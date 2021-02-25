package controllers.mois;

import entities.Annee;
import entities.AnneeMois;
import entities.Mois;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean(name = "moisController")
@ViewScoped
public class MoisController extends AbstractMoisCtrl implements MoisInterfaceCtrl, Serializable {
    
    @PostConstruct
    private void initAcces() {
        anneeMois = new AnneeMois();
        anneeMois.setIdannee(new Annee());
    }
    
    public void prepareCreate() {
        try {
            if (!Utilitaires.isAccess(36L)) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {
                this.annee = new Annee();
                this.moises.clear();
                this.selectedMois.clear();
                RequestContext.getCurrentInstance().execute("PF('MoisCreerDialog').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }
    
    public void prepareEdit() {
        try {
            if (!Utilitaires.isAccess(36L)) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {
                RequestContext.getCurrentInstance().execute("PF('MoisModifierDialog').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }
    
    public void updateMois() {
        this.moises.clear();
        this.selectedMois.clear();
        try {
            List<AnneeMois> temp = this.anneeMoisFacadeLocal.findByAnnee(this.annee.getIdannee());
            
            if (temp.isEmpty()) {
                this.moises = this.moisFacadeLocal.findAllRange();
            } else {
                List<Mois> temp1 = this.moisFacadeLocal.findAllRange();
                List<Mois> temp2 = new ArrayList<>();
                for (AnneeMois a : temp) {
                    temp2.add(a.getIdmois());
                }
                
                for (Mois m : temp1) {
                    if (!temp2.contains(m)) {
                        this.moises.add(m);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void enregistrerMois() {
        try {
            if (!this.selectedMois.isEmpty()) {
                for (Mois m : this.selectedMois) {
                    this.anneeMois = new AnneeMois();
                    this.anneeMois.setIdAnneeMois(this.anneeMoisFacadeLocal.nextVal());
                    this.anneeMois.setIdannee(this.annee);
                    this.anneeMois.setIdmois(m);
                    this.anneeMois.setEtat(true);
                    this.anneeMoisFacadeLocal.create(this.anneeMois);
                    
                    Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du mois  -> " + m.getNom() + " Année -> " + this.annee.getNom(), SessionMBean.getUserAccount());
                }
                RequestContext.getCurrentInstance().execute("PF('MoisCreerDialog').hide()");
                this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                this.anneeMois = null;
                this.moises.clear();
                this.selectedMois.clear();
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("suppression_impossible"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }
    
    @Override
    public void modifier() {
        try {
            if (this.anneeMois != null) {
                if (this.anneeMois.getDateFin().after(this.anneeMois.getDateDebut())) {
                    this.anneeMoisFacadeLocal.edit(this.anneeMois);
                } else {
                    this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("date_non_conforme"));
                    RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                }
                RequestContext.getCurrentInstance().execute("PF('MoisModifierDialog').hide()");
                this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("not_row_selected"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("suppression_impossible"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }
    
    @Override
    public void supprimer() {
        try {
            if (!Utilitaires.isAccess(37L)) {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }
            if (this.anneeMois != null) {
                this.anneeMoisFacadeLocal.remove(this.anneeMois);
                Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du mois -> " + this.anneeMois.getIdmois().getNom() + " de l'exercice -> " + this.anneeMois.getIdannee().getNom(), SessionMBean.getUserAccount());
                this.anneeMois = null;
                this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            } else {
                this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("not_row_selectd"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
            }
        } catch (Exception e) {
            this.routine.catchException(e, this.routine.localizeMessage("suppression_impossible"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }
    
}
