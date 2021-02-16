/*    */ package utils;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import javax.annotation.PostConstruct;
/*    */ import javax.faces.bean.ManagedBean;
/*    */ import javax.faces.bean.SessionScoped;
/*    */ 
/*    */ @ManagedBean(name="themes")
/*    */ @SessionScoped
/*    */ public class themes
/*    */   implements Serializable
/*    */ {
/*    */   List<String> themes;
/*    */ 
/*    */   @PostConstruct
/*    */   public void init()
/*    */   {
/* 27 */     this.themes = new ArrayList();
/* 28 */     this.themes.add("afterdark");
/* 29 */     this.themes.add("afternoon");
/* 30 */     this.themes.add("afterwork");
/* 31 */     this.themes.add("aristo");
/* 32 */     this.themes.add("black-tie");
/* 33 */     this.themes.add("blitzer");
/* 34 */     this.themes.add("bluesky");
/* 35 */     this.themes.add("bootstrap");
/* 36 */     this.themes.add("casablanca");
/* 37 */     this.themes.add("cupertino");
/* 38 */     this.themes.add("cruze");
/* 39 */     this.themes.add("dark-hive");
/* 40 */     this.themes.add("delta");
/* 41 */     this.themes.add("dot-luv");
/* 42 */     this.themes.add("eggplant");
/* 43 */     this.themes.add("excite-bike");
/* 44 */     this.themes.add("flick");
/* 45 */     this.themes.add("glass-x");
/* 46 */     this.themes.add("home");
/* 47 */     this.themes.add("hot-sneaks");
/* 48 */     this.themes.add("humanity");
/* 49 */     this.themes.add("le-frog");
/* 50 */     this.themes.add("midnight");
/* 51 */     this.themes.add("mint-choc");
/* 52 */     this.themes.add("overcast");
/* 53 */     this.themes.add("pepper-grinder");
/* 54 */     this.themes.add("rocket");
/* 55 */     this.themes.add("sam");
/* 56 */     this.themes.add("smoothness");
/* 57 */     this.themes.add("south-street");
/* 58 */     this.themes.add("start");
/* 59 */     this.themes.add("sunny");
/* 60 */     this.themes.add("swanky-purse");
/* 61 */     this.themes.add("trontastic");
/* 62 */     this.themes.add("ui-darkness");
/* 63 */     this.themes.add("ui-lightness");
/* 64 */     this.themes.add("vader");
/*    */   }
/*    */ 
/*    */   public List<String> getThemes() {
/* 68 */     return this.themes;
/*    */   }
/*    */ 
/*    */   public void setThemes(List<String> themes) {
/* 72 */     this.themes = themes;
/*    */   }
/*    */ }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.themes
 * JD-Core Version:    0.6.2
 */