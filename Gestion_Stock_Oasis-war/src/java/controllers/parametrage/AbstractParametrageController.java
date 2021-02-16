 package controllers.parametrage;
 
 import entities.Parametrage;
 import java.util.ArrayList;
 import java.util.List;
 import javax.annotation.Resource;
 import javax.ejb.EJB;
 import javax.transaction.UserTransaction;
 import sessions.MouchardFacadeLocal;
 import sessions.ParametrageFacadeLocal;
 import sessions.PrivilegeFacadeLocal;
 import utils.Routine;
 import utils.SessionMBean;
 
 public class AbstractParametrageController
 {
 
   @Resource
   protected UserTransaction ut;
 
   @EJB
   protected ParametrageFacadeLocal parametrageFacadeLocal;
/*  31 */   protected Parametrage parametrage = new Parametrage();
 
/*  33 */   protected String imageDir = "/photos/";
/*  34 */   protected String chemin_replicated_images = SessionMBean.getParametrage().getRepertoireLogo();
 
/*  36 */   protected Routine routine = new Routine();
 
   @EJB
   protected MouchardFacadeLocal mouchardFacadeLocal;
 
   @EJB
   protected PrivilegeFacadeLocal privilegeFacadeLocal;
/*  44 */   protected List<String> password = new ArrayList();
 
/*  46 */   protected String sessionPassword = "";
 
/*  48 */   protected Boolean session = Boolean.valueOf(true);
 
/*  50 */   protected String filename = "";
 
   public String getFilename()
   {
/*  57 */     return this.filename;
   }
 
   public void setFilename(String fileName) {
/*  61 */     this.filename = fileName;
   }
 
   public Boolean getSession() {
     try {
/*  66 */       if (SessionMBean.getSession1().booleanValue())
/*  67 */         this.session = Boolean.valueOf(false);
       else
/*  69 */         this.session = Boolean.valueOf(true);
     }
     catch (Exception e) {
/*  72 */       e.printStackTrace();
     }
/*  74 */     return this.session;
   }
 
   public void setSession(Boolean session) {
/*  78 */     this.session = session;
   }
 
   public String getSessionPassword() {
/*  82 */     return this.sessionPassword;
   }
 
   public void setSessionPassword(String sessionPassword) {
/*  86 */     this.sessionPassword = sessionPassword;
   }
 
   public Parametrage getParametrage() {
/*  90 */     return this.parametrage;
   }
 
   public void setParametrage(Parametrage parametrage) {
/*  94 */     this.parametrage = parametrage;
   }
 
   public Routine getRoutine() {
/*  98 */     return this.routine;
   }
 
   public String getChemin_replicated_images() {
/* 102 */     return this.chemin_replicated_images;
   }
 
   public void setChemin_replicated_images(String chemin_replicated_images) {
/* 106 */     this.chemin_replicated_images = chemin_replicated_images;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.parametrage.AbstractParametrageController
 * JD-Core Version:    0.6.2
 */