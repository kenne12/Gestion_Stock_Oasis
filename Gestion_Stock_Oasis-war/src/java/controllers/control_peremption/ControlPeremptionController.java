 package controllers.control_peremption;
 
 import entities.Inventaire;
 import entities.Ligneinventaire;
 import entities.Lignemvtstock;
 import entities.Magasin;
 import entities.Magasinarticle;
 import entities.Magasinlot;
 import entities.Mvtstock;
 import java.io.Serializable;
 import java.util.ArrayList;
 import java.util.Date;
 import java.util.HashMap;
 import java.util.List;
 import java.util.Map;
 import javax.annotation.PostConstruct;
 import javax.faces.bean.ManagedBean;
 import javax.faces.bean.SessionScoped;
 import org.primefaces.context.RequestContext;
 import utils.Printer;
 import utils.Utilitaires;
 
 @ManagedBean
 @SessionScoped
 public class ControlPeremptionController extends AbstractControlPeremptionController
   implements Serializable
 {
   @PostConstruct
   private void init()
   {
     try
     {
/*  46 */       this.magasinlots = this.magasinlotFacadeLocal.findAllPeremptedEtatIsTrue(new Date());
/*  47 */       this.magasins.clear();
/*  48 */       this.showSessionPanel = false;
/*  49 */       if (!this.magasinlots.isEmpty()) {
/*  50 */         for (Magasinlot ml : this.magasinlots) {
/*  51 */           if (!this.magasins.contains(ml.getIdmagasinarticle().getIdmagasin())) {
/*  52 */             this.magasins.add(ml.getIdmagasinarticle().getIdmagasin());
           }
         }
/*  55 */         return;
       }
     }
     catch (Exception e) {
/*  59 */       e.printStackTrace();
     }
   }
 
   public List<Magasinlot> filterLotByMagasin(Magasin m) {
/*  64 */     List list = new ArrayList();
/*  65 */     for (Magasinlot ml : this.magasinlots) {
/*  66 */       if (ml.getIdmagasinarticle().getIdmagasin().equals(m)) {
/*  67 */         list.add(ml);
       }
     }
/*  70 */     return list;
   }
 
   public void openDialog() {
/*  74 */     this.showSessionPanel = true;
/*  75 */     RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').show()");
   }
 
   public void close() {
/*  79 */     this.showSessionPanel = false;
/*  80 */     RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').hide()");
   }
 
   public void closePeremptionDialog()
   {
     try {
/*  86 */       if (!Utilitaires.isAccess(Long.valueOf(1L))) {
/*  87 */         notifyError("acces_refuse");
/*  88 */         return;
       }
 
/*  91 */       for (Magasin m : this.magasins)
       {
/*  93 */         this.ut.begin();
 
/*  95 */         Mvtstock mvtstock = new Mvtstock();
 
/*  97 */         mvtstock.setIdmvtstock(this.mvtstockFacadeLocal.nextVal());
/*  98 */         mvtstock.setClient(" ");
/*  99 */         mvtstock.setFournisseur(" ");
/* 100 */         mvtstock.setMagasin(" ");
/* 101 */         mvtstock.setType(" ");
/* 102 */         mvtstock.setCode(Utilitaires.genererCodeStock("MVT", mvtstock.getIdmvtstock()));
/* 103 */         mvtstock.setDatemvt(new Date());
/* 104 */         this.mvtstockFacadeLocal.create(mvtstock);
 
/* 106 */         Inventaire inventaire = new Inventaire();
/* 107 */         inventaire.setIdinventaire(this.inventaireFacadeLocal.nextVal());
/* 108 */         inventaire.setEtat(Boolean.valueOf(true));
/* 109 */         inventaire.setDateinventaire(new Date());
/* 110 */         inventaire.setAllarticle(Boolean.valueOf(false));
/* 111 */         inventaire.setCode(Utilitaires.genererCodeInventaire("INV-", inventaire.getIdinventaire()));
/* 112 */         inventaire.setLibelle("Inventaire de retrait des peremptions / " + m.getNom());
/* 113 */         inventaire.setIdmagasin(m);
/* 114 */         inventaire.setIdmvtstock(mvtstock);
/* 115 */         this.inventaireFacadeLocal.create(inventaire);
 
/* 117 */         List<Magasinlot> mlList = filterLotByMagasin(m);
 
/* 119 */         for (Magasinlot ml : mlList)
         {
/* 121 */           Ligneinventaire li = new Ligneinventaire();
/* 122 */           Lignemvtstock lms = new Lignemvtstock();
/* 123 */           lms.setIdlignemvtstock(this.lignemvtstockFacadeLocal.nextVal());
/* 124 */           lms.setIdmvtstock(inventaire.getIdmvtstock());
/* 125 */           lms.setIdlot(ml.getIdlot());
/* 126 */           lms.setIdmagasinlot(ml);
/* 127 */           lms.setClient("RETRAIT PEREMPTION");
/* 128 */           lms.setFournisseur(" ");
/* 129 */           lms.setQteentree(Double.valueOf(0.0D));
/* 130 */           lms.setQtesortie(ml.getQuantitemultiple());
/* 131 */           lms.setReste(Double.valueOf(0.0D));
/* 132 */           lms.setType("SORTIE");
/* 133 */           this.lignemvtstockFacadeLocal.create(lms);
 
/* 135 */           li.setIdligneinventaire(this.ligneinventaireFacadeLocal.nextVal());
/* 136 */           li.setIdinventaire(inventaire);
/* 137 */           li.setQtetheorique(ml.getQuantitemultiple());
/* 138 */           li.setQtephysique(Double.valueOf(0.0D));
/* 139 */           li.setEcart(ml.getQuantitemultiple());
/* 140 */           li.setIdlot(ml.getIdlot());
/* 141 */           li.setIdmagasinlot(ml);
/* 142 */           li.setObservation("Retrait Des Péremptions");
/* 143 */           this.ligneinventaireFacadeLocal.create(li);
 
/* 145 */           updateMagasinArticle(li.getIdmagasinlot().getIdmagasinarticle().getIdmagasinarticle().longValue(), ml.getQuantitemultiple().doubleValue(), "-");
/* 146 */           updateMagasinLot(li.getIdmagasinlot().getIdmagasinlot().longValue(), ml.getQuantitemultiple().doubleValue(), "-");
         }
 
/* 149 */         this.ut.commit();
       }
 
/* 152 */       this.showSessionPanel = false;
/* 153 */       notifySuccess();
/* 154 */       RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').hide()");
     } catch (Exception e) {
/* 156 */       notifyFail(e);
     }
   }
 
   private void updateMagasinArticle(long idmagasinArticle, double ecart, String signe) {
/* 161 */     Magasinarticle magasinarticle = this.magasinarticleFacadeLocal.find(Long.valueOf(idmagasinArticle));
/* 162 */     if (signe.equals("-")) {
/* 163 */       double vabs = Math.abs(ecart);
/* 164 */       magasinarticle.setQuantitemultiple(Double.valueOf(magasinarticle.getQuantitemultiple().doubleValue() - vabs));
/* 165 */       magasinarticle.setQuantitereduite(Double.valueOf(magasinarticle.getQuantitereduite().doubleValue() - vabs));
/* 166 */       magasinarticle.setQuantite(Double.valueOf(magasinarticle.getQuantite().doubleValue() - vabs));
     } else {
/* 168 */       magasinarticle.setQuantitemultiple(Double.valueOf(magasinarticle.getQuantitemultiple().doubleValue() + ecart));
/* 169 */       magasinarticle.setQuantitereduite(Double.valueOf(magasinarticle.getQuantitereduite().doubleValue() + ecart));
/* 170 */       magasinarticle.setQuantite(Double.valueOf(magasinarticle.getQuantite().doubleValue() + ecart));
     }
/* 172 */     this.magasinarticleFacadeLocal.edit(magasinarticle);
   }
 
   public void updateMagasinLot(long idmagasinLot, double ecart, String signe) {
/* 176 */     Magasinlot magasinlot = this.magasinlotFacadeLocal.find(Long.valueOf(idmagasinLot));
/* 177 */     magasinlot.setEtat(Boolean.valueOf(false));
/* 178 */     if (signe.equals("+")) {
/* 179 */       magasinlot.setQuantitemultiple(Double.valueOf(magasinlot.getQuantitemultiple().doubleValue() + ecart));
/* 180 */       magasinlot.setQuantitereduite(Double.valueOf(magasinlot.getQuantitereduite().doubleValue() + ecart));
/* 181 */       magasinlot.setQuantite(Double.valueOf(magasinlot.getQuantite().doubleValue() + ecart));
     } else {
/* 183 */       double vabs = Math.abs(ecart);
/* 184 */       magasinlot.setQuantitemultiple(Double.valueOf(magasinlot.getQuantitemultiple().doubleValue() - vabs));
/* 185 */       magasinlot.setQuantitereduite(Double.valueOf(magasinlot.getQuantitereduite().doubleValue() - vabs));
/* 186 */       magasinlot.setQuantite(Double.valueOf(magasinlot.getQuantite().doubleValue() - vabs));
     }
/* 188 */     this.magasinlotFacadeLocal.edit(magasinlot);
   }
 
   public void print() {
     try {
/* 193 */       Map param = new HashMap();
/* 194 */       param.put("date_jour", new Date());
/* 195 */       Printer.print("/reports/ireport/produits_peremption_all.jasper", param);
/* 196 */       RequestContext.getCurrentInstance().execute("PF('PeremptionViewDialog_1').hide()");
     } catch (Exception e) {
/* 198 */       notifyFail(e);
     }
   }
 
   public void notifyError(String message) {
/* 203 */     RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
/* 204 */     this.routine.feedBack("avertissement", "/resources/tool_images/warning.jpeg", this.routine.localizeMessage(message));
/* 205 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void notifySuccess() {
/* 209 */     RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
/* 210 */     this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));
/* 211 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 
   public void notifyFail(Exception e) {
/* 215 */     RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').show()");
/* 216 */     this.routine.catchException(e, this.routine.localizeMessage("echec_operation"));
/* 217 */     RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     controllers.control_peremption.ControlPeremptionController
 * JD-Core Version:    0.6.2
 */