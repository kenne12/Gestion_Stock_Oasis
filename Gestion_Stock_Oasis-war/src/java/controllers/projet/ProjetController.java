 package controllers.projet;
 
 import entities.Magasin;
 import entities.Projet;
 import java.io.Serializable;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.Map;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.ViewScoped;
 import org.primefaces.context.RequestContext;
 import sessions.MagasinFacadeLocal;
 import sessions.ProjetFacadeLocal;
 import utils.JsfUtil;
 import utils.Printer;
 import utils.Routine;
 import utils.SessionMBean;
 import utils.Utilitaires;
 
 @ManagedBean
 @ViewScoped
 public class ProjetController extends AbstractProjetController
   implements Serializable
 {
   public void prepareCreate()
   {
     try
     {
/*  39 */       if (!Utilitaires.isAccess(Long.valueOf(29L))) {
/*  40 */         signalError("acces_refuse");
/*  41 */         return;
       }
/*  43 */       this.mode = "Create";
/*  44 */       this.projet = new Projet();
/*  45 */       this.magasin = new Magasin();
/*  46 */       RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').show()");
     } catch (Exception e) {
/*  48 */       signalException(e);
     }
   }
 
   public void prepareEdit() {
     try {
/*  54 */       if (!Utilitaires.isAccess(Long.valueOf(29L))) {
/*  55 */         signalError("acces_refuse");
/*  56 */         return;
       }
/*  58 */       if (this.projet != null) {
/*  59 */         this.magasin = this.projet.getIdmagasin();
       }
/*  61 */       this.mode = "Edit";
/*  62 */       RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').show()");
     } catch (Exception e) {
/*  64 */       signalException(e);
     }
   }
 
   public void create() {
     try {
/*  70 */       if (this.mode.equals("Create"))
       {
/*  72 */         this.projet.setIdprojet(this.projetFacadeLocal.nextVal());
/*  73 */         this.projet.setIdmagasin(this.magasin);
/*  74 */         this.projet.setDatecreation(new Date());
/*  75 */         this.projet.setCloturee(Boolean.valueOf(false));
/*  76 */         this.projetFacadeLocal.create(this.projet);
/*  77 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Enregistrement du projet : " + this.projet.getNom(), SessionMBean.getUserAccount());
/*  78 */         this.projet = null;
/*  79 */         this.modifier = (this.supprimer = Boolean.valueOf(true));
/*  80 */         RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').hide()");
/*  81 */         signalSuccess();
       }
/*  83 */       else if (this.projet != null) {
/*  84 */         this.projet.setIdmagasin(this.magasinFacadeLocal.find(this.magasin.getIdmagasin()));
/*  85 */         this.projetFacadeLocal.edit(this.projet);
/*  86 */         this.projet = null;
/*  87 */         this.modifier = (this.supprimer = Boolean.valueOf(true));
/*  88 */         RequestContext.getCurrentInstance().execute("PF('ProjetCreerDialog').hide()");
/*  89 */         signalSuccess();
       } else {
/*  91 */         JsfUtil.addErrorMessage("Aucun projet selectionné");
       }
     }
     catch (Exception e) {
/*  95 */       signalException(e);
     }
   }
 
   public void delete() {
     try {
/* 101 */       if (this.projet != null)
       {
/* 103 */         if (!Utilitaires.isAccess(Long.valueOf(29L))) {
/* 104 */           signalError("acces_refuse");
/* 105 */           return;
         }
 
/* 108 */         this.projetFacadeLocal.remove(this.projet);
/* 109 */         this.modifier = (this.supprimer = Boolean.valueOf(true));
/* 110 */         Utilitaires.saveOperation(this.mouchardFacadeLocal, "Suppresion du projet : " + this.projet.getNom(), SessionMBean.getUserAccount());
/* 111 */         this.projet = null;
/* 112 */         signalSuccess();
       } else {
/* 114 */         JsfUtil.addErrorMessage("Aucune Projet selectionnée");
       }
     } catch (Exception e) {
/* 117 */       signalException(e);
     }
   }
 
   public void print() {
     try {
/* 123 */       if (!Utilitaires.isAccess(Long.valueOf(27L))) {
/* 124 */         signalError("acces_refuse");
/* 125 */         return;
       }
 
/* 128 */       Map map = new HashMap();
/* 129 */       Printer.print("/reports/ireport/liste_produit.jasper", map);
     } catch (Exception e) {
/* 131 */       signalException(e);
     }
   }
 
   public void signalError(String chaine) {
/* 136 */     this.routine.feedBack("information", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(chaine));
/* 137 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalSuccess() {
/* 141 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 142 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void signalException(Exception e) {
/* 146 */     this.routine.catchException(e, this.routine.localizeMessage("erreur_execution"));
/* 147 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.projet.ProjetController
 * JD-Core Version:    0.6.2
 */