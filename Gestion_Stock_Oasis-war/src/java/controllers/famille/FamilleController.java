 package controllers.famille;
 
 import entities.Famille;
 import java.io.Serializable;
 import java.util.Date;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import org.primefaces.context.RequestContext;
 import sessions.FamilleFacadeLocal;
 import utils.JsfUtil;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 @ManagedBean
 @ViewScoped
 public class FamilleController extends AbstractFamilleController
   implements Serializable
 {
   public void prepareCreate()
   {
     try
     {
/*  35 */       if (!Utilitaires.isAccess(Long.valueOf(10L))) {
/*  36 */         signalError("acces_refuse");
/*  37 */         return;
       }
 
/*  40 */       this.famille = new Famille();
/*  41 */       this.mode = "Create";
/*  42 */       RequestContext.getCurrentInstance().execute("PF('FamilleCreerDialog').show()");
     } catch (Exception e) {
/*  44 */       signalException(e);
     }
   }
 
   public void prepareEdit() {
     try {
/*  50 */       if (!Utilitaires.isAccess(Long.valueOf(11L))) {
/*  51 */         signalError("acces_refuse");
/*  52 */         return;
       }
/*  54 */       this.mode = "Edit";
/*  55 */       RequestContext.getCurrentInstance().execute("PF('FamilleCreerDialog').show()");
     } catch (Exception e) {
/*  57 */       signalException(e);
     }
   }
 
   public void create() {
     try {
/*  63 */       if (this.mode.equals("Create")) {
/*  64 */         this.famille.setIdfamille(this.familleFacadeLocal.nextVal());
/*  65 */         this.famille.setEtat("True");
/*  66 */         this.famille.setDateEnregistre(new Date());
/*  67 */         this.famille.setDerniereModif(new Date());
/*  68 */         this.familleFacadeLocal.create(this.famille);
/*  69 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du la famille des produits : " + this.famille.getNom(), SessionMBean.getUserAccount());
/*  70 */         this.famille = null;
/*  71 */         signalSuccess();
       }
/*  73 */       else if (this.famille != null) {
/*  74 */         this.familleFacadeLocal.edit(this.famille);
/*  75 */         this.famille = null;
/*  76 */         signalSuccess();
       } else {
/*  78 */         JsfUtil.addErrorMessage("Aucun famille selectionné");
       }
     }
     catch (Exception e) {
/*  82 */       signalException(e);
     }
   }
 
   public void delete() {
     try {
/*  88 */       if (this.famille != null)
       {
/*  90 */         if (!Utilitaires.isAccess(Long.valueOf(12L))) {
/*  91 */           signalError("acces_refuse");
/*  92 */           return;
         }
 
/*  95 */         this.familleFacadeLocal.remove(this.famille);
 
/*  97 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de la famille de produit : " + this.famille.getNom(), SessionMBean.getUserAccount());
/*  98 */         this.famille = null;
/*  99 */         signalSuccess();
       } else {
/* 101 */         JsfUtil.addErrorMessage("Aucune Famille selectionnée");
       }
     } catch (Exception e) {
/* 104 */       signalException(e);
     }
   }
 
   public void signalError(String chaine) {
/* 109 */     RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
/* 110 */     this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
/* 111 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalSuccess() {
/* 115 */     RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
/* 116 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 117 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalException(Exception e) {
/* 121 */     RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
/* 122 */     this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
/* 123 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.famille.FamilleController
 * JD-Core Version:    0.6.2
 */