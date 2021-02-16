 package controllers.unite;
 
 import entities.Unite;
 import java.io.Serializable;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import org.primefaces.context.RequestContext;
 import sessions.UniteFacadeLocal;
 import utils.JsfUtil;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 @ManagedBean
 @ViewScoped
 public class UniteController extends AbstractUniteController
   implements Serializable
 {
   public void prepareCreate()
   {
     try
     {
/*  34 */       if (!Utilitaires.isAccess(Long.valueOf(28L))) {
/*  35 */         signalError("acces_refuse");
/*  36 */         return;
       }
 
/*  39 */       this.unite = new Unite();
/*  40 */       this.mode = "Create";
/*  41 */       RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').show()");
     } catch (Exception e) {
/*  43 */       signalException(e);
     }
   }
 
   public void prepareEdit() {
     try {
/*  49 */       if (!Utilitaires.isAccess(Long.valueOf(28L))) {
/*  50 */         signalError("acces_refuse");
/*  51 */         return;
       }
/*  53 */       this.mode = "Edit";
/*  54 */       RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').show()");
     } catch (Exception e) {
/*  56 */       signalException(e);
     }
   }
 
   public void create() {
     try {
/*  62 */       if (this.mode.equals("Create")) {
/*  63 */         this.unite.setIdunite(this.uniteFacadeLocal.nextVal());
/*  64 */         this.uniteFacadeLocal.create(this.unite);
/*  65 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de l'unité : " + this.unite.getLibelle(), SessionMBean.getUserAccount());
/*  66 */         this.unite = null;
/*  67 */         this.modifier = (this.detail = this.supprimer = Boolean.valueOf(true));
/*  68 */         RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').hide()");
/*  69 */         signalSuccess();
       }
/*  71 */       else if (this.unite != null) {
/*  72 */         this.uniteFacadeLocal.edit(this.unite);
/*  73 */         this.unite = null;
/*  74 */         this.modifier = (this.detail = this.supprimer = Boolean.valueOf(true));
/*  75 */         RequestContext.getCurrentInstance().execute("PF('UniteCreerDialog').hide()");
/*  76 */         signalSuccess();
       } else {
/*  78 */         JsfUtil.addErrorMessage("Aucun unite selectionné");
       }
     }
     catch (Exception e) {
/*  82 */       signalException(e);
     }
   }
 
   public void delete() {
     try {
/*  88 */       if (this.unite != null)
       {
/*  90 */         if (!Utilitaires.isAccess(Long.valueOf(28L))) {
/*  91 */           signalError("acces_refuse");
/*  92 */           return;
         }
 
/*  95 */         this.uniteFacadeLocal.remove(this.unite);
/*  96 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de l'unité : " + this.unite.getLibelle(), SessionMBean.getUserAccount());
/*  97 */         this.unite = null;
/*  98 */         this.modifier = (this.detail = this.supprimer = Boolean.valueOf(true));
/*  99 */         signalSuccess();
       } else {
/* 101 */         JsfUtil.addErrorMessage("Aucune Unite selectionnée");
       }
     } catch (Exception e) {
/* 104 */       signalException(e);
     }
   }
 
   public void signalError(String chaine) {
/* 109 */     this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
/* 110 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalSuccess() {
/* 114 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 115 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalException(Exception e) {
/* 119 */     this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
/* 120 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.unite.UniteController
 * JD-Core Version:    0.6.2
 */