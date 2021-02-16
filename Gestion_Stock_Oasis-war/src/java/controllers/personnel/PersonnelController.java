 package controllers.personnel;
 
 import entities.Magasin;
 import entities.Personnel;
 import entities.Qualite;
 import java.io.Serializable;
 import java.util.HashMap;
 import java.util.Map;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import org.primefaces.context.RequestContext;
 import sessions.MagasinFacadeLocal;
 import sessions.PersonnelFacadeLocal;
 import sessions.QualiteFacadeLocal;
 import utils.Printer;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 @ManagedBean
 @ViewScoped
 public class PersonnelController extends AbstractPersonnelController
   implements Serializable
 {
   @PostConstruct
   private void init()
   {
/*  38 */     this.personnel = new Personnel();
   }
 
   public void prepareCreate() {
     try {
/*  43 */       if (!Utilitaires.isAccess(Long.valueOf(27L))) {
/*  44 */         notifyError("acces_refuse");
/*  45 */         return;
       }
 
/*  48 */       this.mode = "Create";
/*  49 */       this.personnel = new Personnel();
/*  50 */       this.magasin = new Magasin();
/*  51 */       this.qualite = new Qualite();
 
/*  53 */       this.personnel.setPrenom("-");
/*  54 */       this.personnel.setAddresse("");
/*  55 */       this.personnel.setContact("-");
/*  56 */       this.personnel.setMatricule("-");
/*  57 */       this.personnel.setAddresse("-");
 
/*  59 */       RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').show()");
     } catch (Exception e) {
/*  61 */       notifyFail(e);
     }
   }
 
   public void prepareEdit() {
     try {
/*  67 */       if (!Utilitaires.isAccess(Long.valueOf(27L))) {
/*  68 */         notifyError("acces_refuse");
/*  69 */         return;
       }
 
/*  72 */       if (this.personnel == null) {
/*  73 */         notifyError("not_row_selected");
/*  74 */         return;
       }
 
/*  77 */       this.mode = "Edit";
/*  78 */       this.qualite = this.personnel.getIdqualite();
/*  79 */       this.magasin = this.personnel.getIdmagasin();
 
/*  81 */       RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').show()");
     } catch (Exception e) {
/*  83 */       notifyFail(e);
     }
   }
 
   public void create() {
     try {
/*  89 */       if (this.mode.equals("Create"))
       {
/*  91 */         this.personnel.setIdpersonnel(this.personnelFacadeLocal.nextVal());
/*  92 */         this.personnel.setIdqualite(this.qualite);
/*  93 */         this.personnel.setIdmagasin(this.magasin);
/*  94 */         this.personnelFacadeLocal.create(this.personnel);
 
/*  96 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du personnel : " + this.personnel.getNom() + " " + this.personnel.getPrenom(), SessionMBean.getUserAccount());
/*  97 */         this.personnel = null;
/*  98 */         this.modifier = (this.supprimer = this.detail = Boolean.valueOf(true));
/*  99 */         RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').hide()");
/* 100 */         notifySuccess();
       }
/* 102 */       else if (this.personnel != null) {
/* 103 */         this.personnel.setIdqualite(this.qualiteFacadeLocal.find(this.qualite.getIdqualite()));
/* 104 */         this.personnel.setIdmagasin(this.magasinFacadeLocal.find(this.magasin.getIdmagasin()));
/* 105 */         this.personnelFacadeLocal.edit(this.personnel);
/* 106 */         RequestContext.getCurrentInstance().execute("PF('PersonnelCreerDialog').hide()");
/* 107 */         this.modifier = (this.supprimer = this.detail = Boolean.valueOf(true));
/* 108 */         notifySuccess();
       } else {
/* 110 */         notifyError("not_row_selected");
       }
     }
     catch (Exception e) {
/* 114 */       notifyFail(e);
     }
   }
 
   public void delete() {
     try {
/* 120 */       if (this.personnel != null)
       {
/* 122 */         if (!Utilitaires.isAccess(Long.valueOf(27L))) {
/* 123 */           notifyError("acces_refuse");
/* 124 */           return;
         }
 
/* 127 */         this.personnelFacadeLocal.remove(this.personnel);
/* 128 */         this.personnel = null;
/* 129 */         this.modifier = (this.supprimer = this.detail = Boolean.valueOf(true));
/* 130 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppression du personnel : " + this.personnel.getNom() + " " + this.personnel.getPrenom(), SessionMBean.getUserAccount());
/* 131 */         notifySuccess();
       } else {
/* 133 */         notifyError("not_row_selected");
       }
     } catch (Exception e) {
/* 136 */       notifyFail(e);
     }
   }
 
   public void print() {
     try {
/* 142 */       if (!Utilitaires.isAccess(Long.valueOf(27L))) {
/* 143 */         notifyError("acces_refuse");
/* 144 */         return;
       }
 
/* 147 */       Map map = new HashMap();
/* 148 */       Printer.print("/reports/ireport/liste_produit.jasper", map);
     } catch (Exception e) {
/* 150 */       notifyFail(e);
     }
   }
 
   public void notifyError(String message) {
/* 155 */     this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
/* 156 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void notifySuccess() {
/* 160 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 161 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void notifyFail(Exception e) {
/* 165 */     this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
/* 166 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.personnel.PersonnelController
 * JD-Core Version:    0.6.2
 */