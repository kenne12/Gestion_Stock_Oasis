/*    */ package utils;
/*    */ 
/*    */ import entities.Parametrage;
/*    */ import entities.Utilisateur;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ import javax.faces.context.ExternalContext;
/*    */ import javax.faces.context.FacesContext;
/*    */ import javax.servlet.http.HttpServletRequest;
/*    */ import javax.servlet.http.HttpSession;
/*    */ 
/*    */ public class SessionMBean
/*    */ {
/*    */   public static HttpSession getSession()
/*    */   {
/* 20 */     return (HttpSession)FacesContext.getCurrentInstance()
/* 20 */       .getExternalContext().getSession(false);
/*    */   }
/*    */ 
/*    */   public static HttpServletRequest getRequest()
/*    */   {
/* 25 */     return (HttpServletRequest)FacesContext.getCurrentInstance()
/* 25 */       .getExternalContext().getRequest();
/*    */   }
/*    */ 
/*    */   public static String getUserName()
/*    */   {
/* 30 */     HttpSession session = (HttpSession)FacesContext.getCurrentInstance()
/* 30 */       .getExternalContext().getSession(false);
/* 31 */     return session.getAttribute("login").toString();
/*    */   }
/*    */ 
/*    */   public static String getUserId() {
/* 35 */     HttpSession session = getSession();
/* 36 */     if (session != null) {
/* 37 */       return (String)session.getAttribute("utilisateurid");
/*    */     }
/* 39 */     return null;
/*    */   }
/*    */ 
/*    */   public static Utilisateur getUserAccount()
/*    */   {
/* 44 */     HttpSession session = getSession();
/* 45 */     if (session != null) {
/* 46 */       return (Utilisateur)session.getAttribute("compte");
/*    */     }
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   public static Boolean getSession1()
/*    */   {
/* 53 */     HttpSession session = getSession();
/* 54 */     if (session != null) {
/* 55 */       return (Boolean)session.getAttribute("session");
/*    */     }
/* 57 */     return Boolean.valueOf(false);
/*    */   }
/*    */ 
/*    */   public static Parametrage getParametrage()
/*    */   {
/* 62 */     HttpSession session = getSession();
/* 63 */     if (session != null) {
/* 64 */       return (Parametrage)session.getAttribute("parametre");
/*    */     }
/* 66 */     return null;
/*    */   }
/*    */ 
/*    */   public static List<Long> getAccess()
/*    */   {
/* 71 */     HttpSession session = getSession();
/* 72 */     if (session != null) {
/* 73 */       return (List)session.getAttribute("accesses");
/*    */     }
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */   public static Date getDateOuverture()
/*    */   {
/* 80 */     HttpSession session = getSession();
/* 81 */     if (session != null) {
/* 82 */       return (Date)session.getAttribute("date");
/*    */     }
/* 84 */     return null;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.SessionMBean
 * JD-Core Version:    0.6.2
 */