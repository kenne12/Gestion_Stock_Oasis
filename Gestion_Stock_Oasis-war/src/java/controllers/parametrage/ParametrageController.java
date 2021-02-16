/*    */ package controllers.parametrage;
/*    */ 
/*    */ import com.google.common.io.Files;
/*    */ import entities.Parametrage;
/*    */ import java.io.File;
/*    */ import java.io.FileOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.Serializable;
/*    */ import java.util.List;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.ViewScoped;
/*    */ import org.primefaces.context.RequestContext;
/*    */ import org.primefaces.event.FileUploadEvent;
/*    */ import org.primefaces.model.UploadedFile;
/*    */ import sessions.ParametrageFacadeLocal;
/*    */ import utils.Routine;
/*    */ import utils.SessionMBean;
/*    */ import utils.Utilitaires;
/*    */ 
/*    */ @ManagedBean
/*    */ @ViewScoped
/*    */ public class ParametrageController extends AbstractParametrageController
/*    */   implements Serializable
/*    */ {
/*    */   @PostConstruct
/*    */   private void init()
/*    */   {
/* 37 */     this.parametrage = ((Parametrage)this.parametrageFacadeLocal.findAll().get(0));
/* 38 */     this.filename = this.parametrage.getLogo();
/* 39 */     this.password.add("momo1234");
/* 40 */     this.password.add("kenne1234");
/*    */   }
/*    */ 
/*    */   public void save() {
/*    */     try {
/* 45 */       if (!Utilitaires.isAccess(Long.valueOf(41L))) {
/* 46 */         this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
/* 47 */         RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/* 48 */         return;
/*    */       }
/*    */ 
/* 51 */       this.parametrageFacadeLocal.edit(this.parametrage);
/* 52 */       this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 53 */       RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/*    */     } catch (Exception e) {
/* 55 */       this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
/* 56 */       RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void prepareUpload() {
/*    */     try {
/* 62 */       if (!Utilitaires.isAccess(Long.valueOf(41L))) {
/* 63 */         this.routine.feedBack("information", "/resources/tool_images/error.png", this.routine.localizeMessage("acces_refuse"));
/* 64 */         RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/* 65 */         return;
/*    */       }
/* 67 */       RequestContext.getCurrentInstance().execute("PF('LogoDialog').show()");
/*    */     } catch (Exception e) {
/* 69 */       this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
/* 70 */       RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/*    */     }
/*    */   }
/*    */ 
/*    */   public void handleFileUpload(FileUploadEvent event)
/*    */   {
/*    */     try {
/* 77 */       if ((event.getFile() == null) || (event.getFile().getFileName() == null) || (event.getFile().getFileName().equals(""))) {
/* 78 */         this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("nom_image_incorrect"));
/* 79 */         RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/* 80 */         return;
/*    */       }
/*    */ 
/* 83 */       if (!Utilitaires.estExtensionImage(Utilitaires.getExtension(event.getFile().getFileName()))) {
/* 84 */         this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("fichier_non_pris_en_charge"));
/* 85 */         RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/* 86 */         return;
/*    */       }
/*    */ 
/* 89 */       String nom = "logo_atlantis." + Utilitaires.getExtension(event.getFile().getFileName());
/*    */ 
/* 92 */       FileOutputStream out = new FileOutputStream(Utilitaires.path + "" + this.imageDir + "/" + nom, true);
/*    */ 
/* 94 */       byte[] bytes = event.getFile().getContents();
/* 95 */       out.write(bytes);
/*    */ 
/* 99 */       this.filename = nom;
/* 100 */       this.parametrage.setLogo(nom);
/* 101 */       this.parametrageFacadeLocal.edit(this.parametrage);
/*    */ 
/* 103 */       this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 104 */       RequestContext.getCurrentInstance().execute("PF('LogoDialog').hide()");
/*    */ 
/* 106 */       File f1 = new File(Utilitaires.path + "" + this.imageDir + "/" + nom);
/* 107 */       File f2 = new File(SessionMBean.getParametrage().getRepertoireLogo() + "" + nom);
/* 108 */       Files.copy(f1, f2);
/*    */     }
/*    */     catch (IOException ex) {
/* 111 */       this.routine.catchException(ex, this.routine.localizeMessage("echec_operation"));
/* 112 */       RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
/*    */     }
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.parametrage.ParametrageController
 * JD-Core Version:    0.6.2
 */