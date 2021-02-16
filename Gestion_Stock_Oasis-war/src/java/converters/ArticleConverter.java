/*    */ package converters;
/*    */ 
/*    */ import entities.Article;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.ejb.EJB;
/*    */ import javax.faces.component.UIComponent;
/*    */ import javax.faces.context.FacesContext;
/*    */ import javax.faces.convert.Converter;
/*    */ import javax.faces.convert.FacesConverter;
/*    */ import sessions.ArticleFacadeLocal;
/*    */ import utils.JsfUtil;
/*    */ 
/*    */ @FacesConverter("articleConverter")
/*    */ public class ArticleConverter
/*    */   implements Converter
/*    */ {
/*    */ 
/*    */   @EJB
/*    */   private ArticleFacadeLocal ejbFacade;
/*    */ 
/*    */   public Object getAsObject(FacesContext facesContext, UIComponent component, String value)
/*    */   {
/* 23 */     if ((value == null) || (value.length() == 0) || (JsfUtil.isDummySelectItem(component, value))) {
/* 24 */       return null;
/*    */     }
/* 26 */     return this.ejbFacade.find(getKey(value));
/*    */   }
/*    */ 
/*    */   Long getKey(String value)
/*    */   {
/* 31 */     Long key = Long.valueOf(value);
/* 32 */     return key;
/*    */   }
/*    */ 
/*    */   String getStringKey(Long value) {
/* 36 */     return "" + value;
/*    */   }
/*    */ 
/*    */   public String getAsString(FacesContext facesContext, UIComponent component, Object object)
/*    */   {
/* 41 */     if ((object == null) || (((object instanceof String)) && 
/* 42 */       (((String)object)
/* 42 */       .length() == 0))) {
/* 43 */       return null;
/*    */     }
/* 45 */     if ((object instanceof Article)) {
/* 46 */       Article a = (Article)object;
/* 47 */       return getStringKey(a.getIdarticle());
/*    */     }
/* 49 */     Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[] { object, object.getClass().getName(), Article.class.getName() });
/* 50 */     return null;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     converters.ArticleConverter
 * JD-Core Version:    0.6.2
 */