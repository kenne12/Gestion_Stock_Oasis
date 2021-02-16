 package utils;
 
 import com.itextpdf.text.BaseColor;
 import com.itextpdf.text.Document;
 import com.itextpdf.text.DocumentException;
 import com.itextpdf.text.Font;
 import com.itextpdf.text.Font.FontFamily;
 import com.itextpdf.text.Paragraph;
 import com.itextpdf.text.pdf.PdfPCell;
 import com.itextpdf.text.pdf.PdfPTable;
 import com.itextpdf.text.pdf.PdfWriter;
 import entities.Article;
 import entities.Famille;
 import entities.Parametrage;
 import java.io.FileNotFoundException;
 import java.io.FileOutputStream;
 import java.text.SimpleDateFormat;
 import java.util.Date;
 import java.util.Iterator;
 import java.util.List;
 import java.util.logging.Level;
 import java.util.logging.Logger;
 
 public class PrintUtils
 {
/*  33 */   public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18.0F, 1);
/*  34 */   public static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.RED);
/*  35 */   public static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.BLACK);
/*  36 */   public static Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.BLUE);
/*  37 */   public static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1);
/*  38 */   public static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 1);
 
   public static Font setUpFont(float size, int style, BaseColor color) {
/*  41 */     Font font = new Font();
/*  42 */     font.setStyle(style);
/*  43 */     font.setSize(size);
/*  44 */     font.setColor(color);
/*  45 */     return font;
   }
 
   public static PdfPCell createPdfPCell(String sCell, int colspan, int position, Font font) {
/*  49 */     PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
/*  50 */     cell.setColspan(colspan);
/*  51 */     if (position == 1)
/*  52 */       cell.setHorizontalAlignment(0);
/*  53 */     else if (position == 2)
/*  54 */       cell.setHorizontalAlignment(1);
     else {
/*  56 */       cell.setHorizontalAlignment(2);
     }
/*  58 */     return cell;
   }
 
   public static PdfPCell createPdfPCell(String sCell, int colspan, int position) {
/*  62 */     PdfPCell cell = new PdfPCell(new Paragraph(sCell));
/*  63 */     cell.setColspan(colspan);
/*  64 */     if (position == 1)
/*  65 */       cell.setHorizontalAlignment(0);
/*  66 */     else if (position == 2)
/*  67 */       cell.setHorizontalAlignment(1);
     else {
/*  69 */       cell.setHorizontalAlignment(2);
     }
 
/*  72 */     return cell;
   }
 
   public static PdfPCell createPdfPCell(String sCell, int position, Font font) {
/*  76 */     PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
/*  77 */     if (position == 1)
/*  78 */       cell.setHorizontalAlignment(0);
/*  79 */     else if (position == 2)
/*  80 */       cell.setHorizontalAlignment(1);
     else {
/*  82 */       cell.setHorizontalAlignment(2);
     }
/*  84 */     return cell;
   }
 
   public static PdfPCell createPdfPCell(String sCell, int position, Font font, int borderLeft, int borderRight, int borderTop, int borderBottom) {
/*  88 */     PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
/*  89 */     if (position == 1)
/*  90 */       cell.setHorizontalAlignment(0);
/*  91 */     else if (position == 2)
/*  92 */       cell.setHorizontalAlignment(1);
     else {
/*  94 */       cell.setHorizontalAlignment(2);
     }
/*  96 */     cell.setBorderWidthLeft(borderLeft);
/*  97 */     cell.setBorderWidthRight(borderRight);
/*  98 */     cell.setBorderWidthTop(borderTop);
/*  99 */     cell.setBorderWidthBottom(borderBottom);
/* 100 */     return cell;
   }
 
   public static PdfPCell createPdfPCell(String sCell, int position, int colspan, Font font, int borderLeft, int borderRight, int borderTop, int borderBottom) {
/* 104 */     PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
/* 105 */     cell.setColspan(colspan);
/* 106 */     if (position == 1)
/* 107 */       cell.setHorizontalAlignment(0);
/* 108 */     else if (position == 2)
/* 109 */       cell.setHorizontalAlignment(1);
     else {
/* 111 */       cell.setHorizontalAlignment(2);
     }
/* 113 */     cell.setBorderWidthLeft(borderLeft);
/* 114 */     cell.setBorderWidthRight(borderRight);
/* 115 */     cell.setBorderWidthTop(borderTop);
/* 116 */     cell.setBorderWidthBottom(borderBottom);
/* 117 */     return cell;
   }
 
   public static PdfPCell createPdfPCell(String sCell, int position, int colspan, int rowspan, Font font, int borderLeft, int borderRight, int borderTop, int borderBottom) {
/* 121 */     PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
/* 122 */     cell.setColspan(colspan);
/* 123 */     cell.setRowspan(rowspan);
/* 124 */     if (position == 1)
/* 125 */       cell.setHorizontalAlignment(0);
/* 126 */     else if (position == 2)
/* 127 */       cell.setHorizontalAlignment(1);
     else {
/* 129 */       cell.setHorizontalAlignment(2);
     }
/* 131 */     cell.setBorderWidthLeft(borderLeft);
/* 132 */     cell.setBorderWidthRight(borderRight);
/* 133 */     cell.setBorderWidthTop(borderTop);
/* 134 */     cell.setBorderWidthBottom(borderBottom);
/* 135 */     return cell;
   }
 
   public static PdfPCell createPdfPCell(String sCell, int position) {
/* 139 */     PdfPCell cell = new PdfPCell(new Paragraph(sCell));
/* 140 */     if (position == 1)
/* 141 */       cell.setHorizontalAlignment(0);
/* 142 */     else if (position == 2)
/* 143 */       cell.setHorizontalAlignment(1);
     else {
/* 145 */       cell.setHorizontalAlignment(2);
     }
/* 147 */     return cell;
   }
 
   public static String printWeeklyReport(Date datedebut, Date datefin, List<Solde> soldes) {
/* 151 */     String fileName = "";
     try {
/* 153 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 154 */       fileName = "Rapport_periode_du_" + sdf.format(datedebut) + "_au_" + sdf.format(datefin) + ".pdf";
/* 155 */       Document rapport = new Document();
/* 156 */       PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/hebdomadaire/" + fileName));
/* 157 */       rapport.open();
/* 158 */       float[] widths = { 0.6F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F };
/* 159 */       PdfPTable table = new PdfPTable(widths);
/* 160 */       table.setWidthPercentage(100.0F);
 
/* 162 */       createEntete(rapport, SessionMBean.getParametrage());
 
/* 164 */       rapport.add(new Paragraph(" "));
 
/* 166 */       Paragraph titre = new Paragraph("RAPPORT PERIODIQUE D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
/* 167 */       titre.setAlignment(1);
/* 168 */       rapport.add(titre);
 
/* 170 */       Paragraph periode = new Paragraph("Période du  " + sdf.format(datedebut) + " au " + sdf.format(datefin), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
/* 171 */       periode.setAlignment(1);
/* 172 */       rapport.add(periode);
 
/* 174 */       rapport.add(new Paragraph(" "));
 
/* 176 */       table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 177 */       table.addCell(createPdfPCell("Nom(s) et prénom(s) du client", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 178 */       table.addCell(createPdfPCell("Montant versé", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 179 */       table.addCell(createPdfPCell("Montant retiré", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 180 */       table.addCell(createPdfPCell("Commissions", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 181 */       table.addCell(createPdfPCell("Frais carnet", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 182 */       table.addCell(createPdfPCell("Solde", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
 
/* 184 */       int sommeVerse = 0;
/* 185 */       int sommeRetire = 0;
/* 186 */       int sommeCarnet = 0;
/* 187 */       int sommeCommission = 0;
/* 188 */       int solde = 0;
 
/* 204 */       table.addCell(createPdfPCell("Totaux", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 205 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeVerse)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 206 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeRetire)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 207 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCommission)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 208 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCarnet)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 209 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
 
/* 211 */       rapport.add(table);
/* 212 */       rapport.close();
     }
     catch (DocumentException ex) {
/* 215 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     } catch (FileNotFoundException ex) {
/* 217 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     }
/* 219 */     return fileName;
   }
 
   public static String printDailylyReport(Date date, List<Solde> soldes) {
/* 223 */     String fileName = "";
     try {
/* 225 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 226 */       fileName = "Rapport_du_" + sdf.format(date) + ".pdf";
/* 227 */       Document rapport = new Document();
/* 228 */       PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/journalier/" + fileName));
/* 229 */       rapport.open();
/* 230 */       float[] widths = { 0.6F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F };
/* 231 */       PdfPTable table = new PdfPTable(widths);
/* 232 */       table.setWidthPercentage(100.0F);
 
/* 234 */       createEntete(rapport, SessionMBean.getParametrage());
 
/* 236 */       rapport.add(new Paragraph(" "));
 
/* 238 */       Paragraph titre = new Paragraph("RAPPORT JOURNALIER D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
/* 239 */       titre.setAlignment(1);
/* 240 */       rapport.add(titre);
 
/* 242 */       Paragraph periode = new Paragraph("Date : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
/* 243 */       periode.setAlignment(1);
/* 244 */       rapport.add(periode);
 
/* 246 */       rapport.add(new Paragraph(" "));
 
/* 248 */       table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 249 */       table.addCell(createPdfPCell("Nom(s) et prénom(s) du client", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 250 */       table.addCell(createPdfPCell("Montant versé", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 251 */       table.addCell(createPdfPCell("Montant retiré", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 252 */       table.addCell(createPdfPCell("Commissions", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 253 */       table.addCell(createPdfPCell("Frais carnet", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 254 */       table.addCell(createPdfPCell("Solde", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
 
/* 256 */       int sommeVerse = 0;
/* 257 */       int sommeRetire = 0;
/* 258 */       int sommeCarnet = 0;
/* 259 */       int sommeCommission = 0;
/* 260 */       int solde = 0;
 
/* 276 */       table.addCell(createPdfPCell("Totaux", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 277 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeVerse)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 278 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeRetire)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 279 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCommission)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 280 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCarnet)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 281 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
 
/* 283 */       rapport.add(table);
/* 284 */       rapport.close();
     }
     catch (DocumentException ex) {
/* 287 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     } catch (FileNotFoundException ex) {
/* 289 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     }
/* 291 */     return fileName;
   }
 
   public static String printProductReport(List<Article> articles)
   {
/* 356 */     String fileName = "";
     try {
/* 358 */       Date date = new Date();
/* 359 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 360 */       fileName = "Fiche_produits_" + sdf.format(date) + ".pdf";
/* 361 */       Document rapport = new Document();
/* 362 */       PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/produits/" + fileName));
/* 363 */       rapport.open();
/* 364 */       float[] widths = { 0.7F, 1.9F, 3.9F, 1.0F };
/* 365 */       PdfPTable table = new PdfPTable(widths);
/* 366 */       table.setWidthPercentage(100.0F);
 
/* 368 */       createEntete(rapport, SessionMBean.getParametrage());
 
/* 370 */       rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2)));
 
/* 372 */       Paragraph titre = new Paragraph("FICHE DESCRIPTIVE DES PRODUITS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
/* 373 */       titre.setAlignment(1);
/* 374 */       rapport.add(titre);
 
/* 376 */       Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 377 */       periode.setAlignment(1);
/* 378 */       rapport.add(periode);
 
/* 380 */       rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2)));
 
/* 382 */       table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 383 */       table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 384 */       table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 385 */       table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
 
/* 387 */       int compteur = 1;
 
/* 389 */       for (Article a : articles) {
/* 390 */         table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 391 */         table.addCell(createPdfPCell("" + a.getIdfamille().getNom(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 392 */         table.addCell(createPdfPCell("" + a.getLibelle(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
 
/* 394 */         compteur++;
       }
 
/* 397 */       rapport.add(table);
/* 398 */       rapport.close();
     }
     catch (DocumentException ex) {
/* 401 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     } catch (FileNotFoundException ex) {
/* 403 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     }
/* 405 */     return fileName;
   }
 
   public static String printGeneralStockReport(List<Article> articles) {
/* 409 */     String fileName = "";
     try {
/* 411 */       Date date = new Date();
/* 412 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 413 */       fileName = "Fiche_stock_general" + sdf.format(date) + ".pdf";
/* 414 */       Document rapport = new Document();
/* 415 */       PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
/* 416 */       rapport.open();
/* 417 */       float[] widths = { 0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.2F };
/* 418 */       PdfPTable table = new PdfPTable(widths);
/* 419 */       table.setWidthPercentage(100.0F);
 
/* 421 */       Paragraph entreprise = new Paragraph("FREDY BUSINESS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
/* 422 */       entreprise.setAlignment(1);
/* 423 */       rapport.add(entreprise);
 
/* 425 */       Paragraph entreprise1 = new Paragraph("Vente Téléphones & Accessoires , Cartes Mémoires -MP3-MP4 - Clé USB", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
/* 426 */       entreprise1.setAlignment(1);
/* 427 */       rapport.add(entreprise1);
 
/* 429 */       Paragraph entreprise2 = new Paragraph("Sis à MOKOLO Immeuble Cameroun Bébé (Yaoundé)", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 1));
/* 430 */       entreprise2.setAlignment(1);
/* 431 */       rapport.add(entreprise2);
 
/* 433 */       Paragraph adresse2 = new Paragraph(" Tél.: 675 82 35 78 / 694 43 08 81", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
/* 434 */       adresse2.setAlignment(1);
/* 435 */       rapport.add(adresse2);
 
/* 437 */       rapport.add(new Paragraph(" "));
 
/* 439 */       Paragraph titre = new Paragraph("FICHE DU STOCK EN MARCHANDISE", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
/* 440 */       titre.setAlignment(1);
/* 441 */       rapport.add(titre);
 
/* 443 */       Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 444 */       periode.setAlignment(1);
/* 445 */       rapport.add(periode);
 
/* 447 */       rapport.add(new Paragraph(" "));
 
/* 449 */       table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 450 */       table.addCell(createPdfPCell("Famille", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 451 */       table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 452 */       table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 453 */       table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 454 */       table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
 
/* 456 */       Double total = Double.valueOf(0.0D);
/* 457 */       int quantite = 0;
/* 458 */       int compteur = 1;
       Article localArticle;
/* 460 */       for (Iterator localIterator = articles.iterator(); localIterator.hasNext(); localArticle = (Article)localIterator.next());
/* 472 */       table.addCell(createPdfPCell("Totaux", 4, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 473 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(quantite)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 474 */       table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Utilitaires.arrondiNDecimales(total.doubleValue(), 1)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 475 */       rapport.add(table);
/* 476 */       rapport.close();
     }
     catch (DocumentException ex) {
/* 479 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     } catch (FileNotFoundException ex) {
/* 481 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     }
/* 483 */     return fileName;
   }
 
   public static String printCritickStockReport(List<Article> articles) {
/* 487 */     String fileName = "";
     try {
/* 489 */       Date date = new Date();
/* 490 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 491 */       fileName = "Fiche_stock_critic" + sdf.format(date) + ".pdf";
/* 492 */       Document rapport = new Document();
/* 493 */       PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
/* 494 */       rapport.open();
/* 495 */       float[] widths = { 0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.2F };
/* 496 */       PdfPTable table = new PdfPTable(widths);
/* 497 */       table.setWidthPercentage(100.0F);
 
/* 499 */       createEntete(rapport, SessionMBean.getParametrage());
 
/* 501 */       rapport.add(new Paragraph(" "));
 
/* 503 */       Paragraph titre = new Paragraph("FICHE DES PRODUITS EN SOUS-STOCKAGE", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
/* 504 */       titre.setAlignment(1);
/* 505 */       rapport.add(titre);
 
/* 507 */       Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 508 */       periode.setAlignment(1);
/* 509 */       rapport.add(periode);
 
/* 511 */       rapport.add(new Paragraph(" "));
 
/* 513 */       table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 514 */       table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 515 */       table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 516 */       table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 517 */       table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 518 */       table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
 
/* 520 */       Double total = Double.valueOf(0.0D);
/* 521 */       int quantite = 0;
/* 522 */       int compteur = 1;
 
/* 543 */       rapport.add(table);
/* 544 */       rapport.close();
     }
     catch (DocumentException ex) {
/* 547 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     } catch (FileNotFoundException ex) {
/* 549 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     }
/* 551 */     return fileName;
   }
 
   public static void createEntete(Document document, Parametrage parametrage)
     throws DocumentException
   {
     try
     {
/* 741 */       Paragraph entreprise = new Paragraph(parametrage.getNomStructure(), new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1));
/* 742 */       entreprise.setAlignment(1);
/* 743 */       document.add(entreprise);
 
/* 745 */       Paragraph entreprise1 = new Paragraph("" + parametrage.getDescriptif(), new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 1));
/* 746 */       entreprise1.setAlignment(1);
/* 747 */       document.add(entreprise1);
 
/* 749 */       Paragraph entreprise2 = new Paragraph("" + parametrage.getLocalisation(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 1));
/* 750 */       entreprise2.setAlignment(0);
 
/* 753 */       Paragraph adresse2 = new Paragraph("B.P.: " + parametrage.getBoitePostale() + "   Tél.: " + parametrage.getContact1() + " / " + parametrage.getContact2() + "   Fax : " + parametrage.getFax(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 2));
/* 754 */       adresse2.setAlignment(1);
/* 755 */       document.add(adresse2);
 
/* 757 */       Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 758 */       document.add(ligne);
     }
     catch (Exception localException)
     {
     }
   }
 
   public static void createLine(Document document)
     throws DocumentException
   {
/* 781 */     Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 782 */     document.add(ligne);
   }
 
   public static void createSignatureZone(Document document) throws DocumentException {
/* 786 */     Paragraph signature1 = new Paragraph("                                                                                        --------------Comptable------------------------Directeur-------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 787 */     document.add(signature1);
 
/* 789 */     Paragraph signature2 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 790 */     document.add(signature2);
 
/* 792 */     Paragraph signature4 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 793 */     document.add(signature4);
 
/* 795 */     Paragraph signature5 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 796 */     document.add(signature5);
 
/* 798 */     Paragraph signature6 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 799 */     document.add(signature6);
 
/* 801 */     Paragraph signature7 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 802 */     document.add(signature7);
 
/* 804 */     Paragraph signature3 = new Paragraph("                                                                                        ----------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 805 */     document.add(signature3);
   }
 
   public static String printInventoryReport(List<Article> produits) {
/* 809 */     String fileName = "";
     try {
/* 811 */       Date date = new Date();
/* 812 */       SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
/* 813 */       fileName = "Fiche_inventaire_stock" + sdf.format(date) + ".pdf";
/* 814 */       Document rapport = new Document();
/* 815 */       PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
/* 816 */       rapport.open();
/* 817 */       float[] widths = { 0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.0F, 1.0F };
/* 818 */       PdfPTable table = new PdfPTable(widths);
/* 819 */       table.setWidthPercentage(100.0F);
 
/* 821 */       createEntete(rapport, SessionMBean.getParametrage());
 
/* 823 */       rapport.add(new Paragraph(" "));
 
/* 825 */       Paragraph titre = new Paragraph("FICHE D'INVENTAIRE DU STOCK", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
/* 826 */       titre.setAlignment(1);
/* 827 */       rapport.add(titre);
 
/* 829 */       Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
/* 830 */       periode.setAlignment(1);
/* 831 */       rapport.add(periode);
 
/* 833 */       rapport.add(new Paragraph(" "));
 
/* 835 */       table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 836 */       table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 837 */       table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 838 */       table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 839 */       table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 840 */       table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
/* 841 */       table.addCell(createPdfPCell("Quantité\n physique", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
 
/* 843 */       Double total = Double.valueOf(0.0D);
/* 844 */       int quantite = 0;
/* 845 */       int compteur = 1;
 
/* 860 */       table.addCell(createPdfPCell("Totaux", 4, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
/* 861 */       table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(Integer.valueOf(quantite)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 862 */       table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(Utilitaires.arrondiNDecimales(total.doubleValue(), 1)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 863 */       table.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
/* 864 */       rapport.add(table);
/* 865 */       rapport.close();
     }
     catch (DocumentException ex) {
/* 868 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     } catch (FileNotFoundException ex) {
/* 870 */       Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
     }
/* 872 */     return fileName;
   }
 }

/* Location:           I:\GESTION_STOCK\GESTION_STOCK-war_war\WEB-INF\classes\
 * Qualified Name:     utils.PrintUtils
 * JD-Core Version:    0.6.2
 */