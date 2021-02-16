/*    */ package converters;
/*    */ 
/*    */ import entities.Personnel;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.ejb.EJB;
/*    */ import javax.faces.component.UIComponent;
/*    */ import javax.faces.context.FacesContext;
/*    */ import javax.faces.convert.Converter;
/*    */ import javax.faces.convert.FacesConverter;
/*    */ import sessions.PersonnelFacadeLocal;
/*    */ import utils.JsfUtil;
/*    */ 
/*    */ @FacesConverter("personnelConverter")
/*    */ public class PersonnelConverter
/*    */   implements Converter
/*    */ {
/*    */ 
/*    */   @EJB
/*    */   private PersonnelFacadeLocal ejbFacade;
/*    */ 
/*    */   public Object getAsObject(FacesContext facesContext, UIComponent component, String value)
/*    */   {
/* 22 */     if ((value == null) || (value.length() == 0) || (JsfUtil.isDummySelectItem(component, value))) {
/* 23 */       return null;
/*    */     }
/* 25 */     return this.ejbFacade.find(getKey(value));
/*    */   }
/*    */ 
/*    */   Long getKey(String value)
/*    */   {
/* 30 */     Long key = Long.valueOf(value);
/* 31 */     return key;
/*    */   }
/*    */ 
/*    */   String getStringKey(Long value) {
/* 35 */     return "" + value;
/*    */   }
/*    */ 
/*    */   public String getAsString(FacesContext facesContext, UIComponent component, Object object)
/*    */   {
/* 40 */     if ((object == null) || (((object instanceof String)) && 
/* 41 */       (((String)object)
/* 41 */       .length() == 0))) {
/* 42 */       return null;
/*    */     }
/* 44 */     if ((object instanceof Personnel)) {
/* 45 */       Personnel p = (Personnel)object;
/* 46 */       return getStringKey(p.getIdpersonnel());
/*    */     }
/* 48 */     Logger.getLogger(getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[] { object, object.getClass().getName(), Personnel.class.getName() });
/* 49 */     return null;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     converters.PersonnelConverter
 * JD-Core Version:    0.6.2
 */