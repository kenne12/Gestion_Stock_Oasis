 package controllers.service;
 
 import entities.Service;
 import java.io.Serializable;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import org.primefaces.context.RequestContext;
 import sessions.ServiceFacadeLocal;
 import utils.JsfUtil;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 @ManagedBean
 @ViewScoped
 public class ServiceController extends AbstractServiceController
   implements Serializable
 {
   public void prepareCreate()
   {
     try
     {
/*  34 */       if (!Utilitaires.isAccess(Long.valueOf(17L))) {
/*  35 */         signalError("acces_refuse");
/*  36 */         return;
       }
 
/*  39 */       this.service = new Service();
/*  40 */       this.mode = "Create";
/*  41 */       RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').show()");
     } catch (Exception e) {
/*  43 */       signalException(e);
     }
   }
 
   public void prepareEdit() {
     try {
/*  49 */       if (!Utilitaires.isAccess(Long.valueOf(18L))) {
/*  50 */         signalError("acces_refuse");
/*  51 */         return;
       }
/*  53 */       this.mode = "Edit";
/*  54 */       RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').show()");
     } catch (Exception e) {
/*  56 */       signalException(e);
     }
   }
 
   public void create() {
     try {
/*  62 */       if (this.mode.equals("Create")) {
/*  63 */         this.service.setIdservice(this.serviceFacadeLocal.nextVal());
/*  64 */         this.serviceFacadeLocal.create(this.service);
/*  65 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du service : " + this.service.getNom(), SessionMBean.getUserAccount());
/*  66 */         this.service = null;
/*  67 */         this.modifier = (this.detail = this.supprimer = Boolean.valueOf(true));
/*  68 */         RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').hide()");
/*  69 */         signalSuccess();
       }
/*  71 */       else if (this.service != null) {
/*  72 */         this.serviceFacadeLocal.edit(this.service);
/*  73 */         this.service = null;
/*  74 */         this.modifier = (this.detail = this.supprimer = Boolean.valueOf(true));
/*  75 */         RequestContext.getCurrentInstance().execute("PF('ServiceCreerDialog').hide()");
/*  76 */         signalSuccess();
       } else {
/*  78 */         JsfUtil.addErrorMessage("Aucun service selectionné");
       }
     }
     catch (Exception e) {
/*  82 */       signalException(e);
     }
   }
 
   public void delete() {
     try {
/*  88 */       if (this.service != null) {
/*  89 */         if (!Utilitaires.isAccess(Long.valueOf(19L))) {
/*  90 */           signalError("acces_refuse");
/*  91 */           return;
         }
/*  93 */         this.serviceFacadeLocal.remove(this.service);
 
/*  95 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du service : " + this.service.getNom(), SessionMBean.getUserAccount());
/*  96 */         this.service = null;
/*  97 */         this.modifier = (this.detail = this.supprimer = Boolean.valueOf(true));
/*  98 */         signalSuccess();
       } else {
/* 100 */         JsfUtil.addErrorMessage("Aucune Service selectionnée");
       }
     } catch (Exception e) {
/* 103 */       signalException(e);
     }
   }
 
   public void signalError(String chaine) {
/* 108 */     this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
/* 109 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalSuccess() {
/* 113 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 114 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalException(Exception e) {
/* 118 */     this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
/* 119 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.service.ServiceController
 * JD-Core Version:    0.6.2
 */