/*    */ import java.io.PrintStream;
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.Locale;
/*    */ import javax.faces.application.Application;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.SessionScoped;
/*    */ import javax.faces.component.UIViewRoot;
/*    */ import javax.faces.context.FacesContext;
/*    */ import javax.faces.model.SelectItem;
/*    */ 
/*    */ @ManagedBean(name="localeManagedBean")
/*    */ @SessionScoped
/*    */ public class LocaleManagedBean
/*    */   implements Serializable
/*    */ {
/* 29 */   private Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
/*    */ 
/*    */   public String getLanguage()
/*    */   {
/* 37 */     return this.locale.getLanguage();
/*    */   }
/*    */ 
/*    */   public void setLanguage(String language)
/*    */   {
/* 46 */     this.locale = new Locale(language);
/* 47 */     FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
/*    */   }
/*    */ 
/*    */   public Locale getLocale()
/*    */   {
/* 56 */     return this.locale;
/*    */   }
/*    */ 
/*    */   public SelectItem[] getLocales()
/*    */   {
/* 65 */     ArrayList items = new ArrayList();
/*    */ 
/* 67 */     Application application = FacesContext.getCurrentInstance()
/* 67 */       .getApplication();
/* 68 */     Iterator supportedLocales = application.getSupportedLocales();
/*    */ 
/* 70 */     while (supportedLocales.hasNext()) {
/* 71 */       Locale loc = (Locale)supportedLocales.next();
/* 72 */       items.add(new SelectItem(loc.getLanguage(), loc
/* 73 */         .getDisplayName(this.locale)));
/*    */     }
/*    */ 
/* 75 */     SelectItem[] locales = new SelectItem[items.size()];
/* 76 */     items.toArray(locales);
/* 77 */     return locales;
/*    */   }
/*    */ 
/*    */   public void activerFR() {
/* 81 */     FacesContext context = FacesContext.getCurrentInstance();
/* 82 */     context.getViewRoot().setLocale(Locale.FRENCH);
/* 83 */     System.out.println("FRENCH");
/*    */   }
/*    */ 
/*    */   public void activerEN()
/*    */   {
/* 88 */     FacesContext context = FacesContext.getCurrentInstance();
/* 89 */     context.getViewRoot().setLocale(Locale.ENGLISH);
/* 90 */     System.out.println("ENGLISH");
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     LocaleManagedBean
 * JD-Core Version:    0.6.2
 */