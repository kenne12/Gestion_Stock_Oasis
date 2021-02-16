 package controllers.laboratoires;
 
 import entities.Laboratoire;
 import java.io.Serializable;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import org.primefaces.context.RequestContext;
 import sessions.LaboratoireFacadeLocal;
 import utils.JsfUtil;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 @ManagedBean
 @ViewScoped
 public class LaboratoireController extends AbstractLaboratoireController
   implements Serializable
 {
   public void prepareCreate()
   {
     try
     {
/*  34 */       if (!Utilitaires.isAccess(Long.valueOf(10L))) {
/*  35 */         signalError("acces_refuse");
/*  36 */         return;
       }
 
/*  39 */       this.laboratoire = new Laboratoire();
/*  40 */       this.mode = "Create";
/*  41 */       RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').show()");
     } catch (Exception e) {
/*  43 */       signalException(e);
     }
   }
 
   public void prepareEdit() {
     try {
/*  49 */       if (!Utilitaires.isAccess(Long.valueOf(11L))) {
/*  50 */         signalError("acces_refuse");
/*  51 */         return;
       }
/*  53 */       this.mode = "Edit";
/*  54 */       RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').show()");
     } catch (Exception e) {
/*  56 */       signalException(e);
     }
   }
 
   public void create() {
     try {
/*  62 */       if (this.mode.equals("Create")) {
/*  63 */         this.laboratoire.setIdlaboratoire(this.laboratoireFacadeLocal.nextVal());
/*  64 */         this.laboratoireFacadeLocal.create(this.laboratoire);
/*  65 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du laboratoire : " + this.laboratoire.getNom(), SessionMBean.getUserAccount());
/*  66 */         this.laboratoire = null;
/*  67 */         RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').hide()");
/*  68 */         signalSuccess();
       }
/*  70 */       else if (this.laboratoire != null) {
/*  71 */         this.laboratoireFacadeLocal.edit(this.laboratoire);
/*  72 */         this.laboratoire = null;
/*  73 */         RequestContext.getCurrentInstance().execute("PF('LaboratoireCreerDialog').hide()");
/*  74 */         signalSuccess();
       } else {
/*  76 */         JsfUtil.addErrorMessage("Aucun laboratoire selectionné");
       }
     }
     catch (Exception e) {
/*  80 */       signalException(e);
     }
   }
 
   public void delete() {
     try {
/*  86 */       if (this.laboratoire != null)
       {
/*  88 */         if (!Utilitaires.isAccess(Long.valueOf(12L))) {
/*  89 */           signalError("acces_refuse");
/*  90 */           return;
         }
 
/*  93 */         this.laboratoireFacadeLocal.remove(this.laboratoire);
 
/*  95 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du laboratoire : " + this.laboratoire.getNom(), SessionMBean.getUserAccount());
/*  96 */         this.laboratoire = null;
/*  97 */         signalSuccess();
       } else {
/*  99 */         JsfUtil.addErrorMessage("Aucune Laboratoire selectionnée");
       }
     } catch (Exception e) {
/* 102 */       signalException(e);
     }
   }
 
   public void signalError(String chaine) {
/* 107 */     this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
/* 108 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalSuccess() {
/* 112 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 113 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalException(Exception e) {
/* 117 */     this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
/* 118 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.laboratoires.LaboratoireController
 * JD-Core Version:    0.6.2
 */