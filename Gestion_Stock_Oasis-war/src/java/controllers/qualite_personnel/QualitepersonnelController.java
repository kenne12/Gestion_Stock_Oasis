 package controllers.qualite_personnel;
 
 import entities.Laboratoire;
 import entities.Qualite;
 import java.io.Serializable;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import org.primefaces.context.RequestContext;
 import sessions.QualiteFacadeLocal;
 import utils.JsfUtil;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 @ManagedBean
 @ViewScoped
 public class QualitepersonnelController extends AbstractQualitepersonnelController
   implements Serializable
 {
   public void prepareCreate()
   {
     try
     {
/*  35 */       if (!Utilitaires.isAccess(Long.valueOf(26L))) {
/*  36 */         signalError("acces_refuse");
/*  37 */         return;
       }
/*  39 */       this.mode = "Create";
/*  40 */       this.qualite = new Qualite();
/*  41 */       this.laboratoire = new Laboratoire();
/*  42 */       RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').show()");
     } catch (Exception e) {
/*  44 */       signalException(e);
     }
   }
 
   public void prepareEdit() {
     try {
/*  50 */       if (!Utilitaires.isAccess(Long.valueOf(26L))) {
/*  51 */         signalError("acces_refuse");
/*  52 */         return;
       }
/*  54 */       this.mode = "Edit";
/*  55 */       RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').show()");
     } catch (Exception e) {
/*  57 */       signalException(e);
     }
   }
 
   public void create() {
     try {
/*  63 */       if (this.mode.equals("Create"))
       {
/*  65 */         this.qualite.setIdqualite(this.qualiteFacadeLocal.nextVal());
/*  66 */         this.qualiteFacadeLocal.create(this.qualite);
/*  67 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement de la qualité du personnel : " + this.qualite.getNom(), SessionMBean.getUserAccount());
/*  68 */         this.qualite = null;
/*  69 */         RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').hide()");
/*  70 */         signalSuccess();
       }
/*  72 */       else if (this.qualite != null) {
/*  73 */         this.qualiteFacadeLocal.edit(this.qualite);
/*  74 */         this.qualite = null;
/*  75 */         this.modifier = (this.supprimer = Boolean.valueOf(true));
/*  76 */         RequestContext.getCurrentInstance().execute("PF('QualiteCreerDialog').hide()");
/*  77 */         signalSuccess();
       } else {
/*  79 */         JsfUtil.addErrorMessage("Aucun qualite selectionné");
       }
     }
     catch (Exception e) {
/*  83 */       signalException(e);
     }
   }
 
   public void delete() {
     try {
/*  89 */       if (this.qualite != null)
       {
/*  91 */         if (!Utilitaires.isAccess(Long.valueOf(26L))) {
/*  92 */           signalError("acces_refuse");
/*  93 */           return;
         }
 
/*  96 */         this.qualiteFacadeLocal.remove(this.qualite);
/*  97 */         this.modifier = (this.supprimer = Boolean.valueOf(true));
/*  98 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion de la qualité du personnel : " + this.qualite.getNom(), SessionMBean.getUserAccount());
/*  99 */         this.qualite = null;
/* 100 */         signalSuccess();
       } else {
/* 102 */         JsfUtil.addErrorMessage("Aucune Qualite selectionnée");
       }
     } catch (Exception e) {
/* 105 */       signalException(e);
     }
   }
 
   public void signalError(String chaine) {
/* 110 */     this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
/* 111 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalSuccess() {
/* 115 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 116 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalException(Exception e) {
/* 120 */     this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
/* 121 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.qualite_personnel.QualitepersonnelController
 * JD-Core Version:    0.6.2
 */