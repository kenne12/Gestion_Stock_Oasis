package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Article;
import entities.Demande;
import entities.Lignedemande;
import entities.Lignelivraisonclient;
import entities.Lignelivraisonfournisseur;
import entities.Livraisonclient;
import entities.Livraisonfournisseur;
import entities.Parametrage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrintUtils {

    public static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18.0F, 1);
    public static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.RED);
    public static Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.BLACK);
    public static Font blueFont = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0, BaseColor.BLUE);
    public static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1);
    public static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 1);

    public static Font setUpFont(float size, int style, BaseColor color) {
        Font font = new Font();
        font.setStyle(style);
        font.setSize(size);
        font.setColor(color);
        return font;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, int position, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        cell.setColspan(colspan);
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, int position) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell));
        cell.setColspan(colspan);
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position, Font font, int borderLeft, int borderRight, int borderTop, int borderBottom) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        cell.setBorderWidthLeft(borderLeft);
        cell.setBorderWidthRight(borderRight);
        cell.setBorderWidthTop(borderTop);
        cell.setBorderWidthBottom(borderBottom);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position, int colspan, Font font, int borderLeft, int borderRight, int borderTop, int borderBottom) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        cell.setColspan(colspan);
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        cell.setBorderWidthLeft(borderLeft);
        cell.setBorderWidthRight(borderRight);
        cell.setBorderWidthTop(borderTop);
        cell.setBorderWidthBottom(borderBottom);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position, int colspan, int rowspan, Font font, int borderLeft, int borderRight, int borderTop, int borderBottom) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        cell.setColspan(colspan);
        cell.setRowspan(rowspan);
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        cell.setBorderWidthLeft(borderLeft);
        cell.setBorderWidthRight(borderRight);
        cell.setBorderWidthTop(borderTop);
        cell.setBorderWidthBottom(borderBottom);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell));
        if (position == 1) {
            cell.setHorizontalAlignment(0);
        } else if (position == 2) {
            cell.setHorizontalAlignment(1);
        } else {
            cell.setHorizontalAlignment(2);
        }
        return cell;
    }

    public static String printWeeklyReport(Date datedebut, Date datefin, List<Solde> soldes) {
        /* 151 */ String fileName = "";
        try {
            /* 153 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 154 */ fileName = "Rapport_periode_du_" + sdf.format(datedebut) + "_au_" + sdf.format(datefin) + ".pdf";
            /* 155 */ Document rapport = new Document();
            /* 156 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/hebdomadaire/" + fileName));
            /* 157 */ rapport.open();
            /* 158 */ float[] widths = {0.6F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
            /* 159 */ PdfPTable table = new PdfPTable(widths);
            /* 160 */ table.setWidthPercentage(100.0F);

            /* 162 */ createEntete(rapport, SessionMBean.getParametrage());

            /* 164 */ rapport.add(new Paragraph(" "));

            /* 166 */ Paragraph titre = new Paragraph("RAPPORT PERIODIQUE D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 167 */ titre.setAlignment(1);
            /* 168 */ rapport.add(titre);

            /* 170 */ Paragraph periode = new Paragraph("Période du  " + sdf.format(datedebut) + " au " + sdf.format(datefin), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            /* 171 */ periode.setAlignment(1);
            /* 172 */ rapport.add(periode);

            /* 174 */ rapport.add(new Paragraph(" "));

            /* 176 */ table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 177 */ table.addCell(createPdfPCell("Nom(s) et prénom(s) du client", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 178 */ table.addCell(createPdfPCell("Montant versé", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 179 */ table.addCell(createPdfPCell("Montant retiré", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 180 */ table.addCell(createPdfPCell("Commissions", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 181 */ table.addCell(createPdfPCell("Frais carnet", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 182 */ table.addCell(createPdfPCell("Solde", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            /* 184 */ int sommeVerse = 0;
            /* 185 */ int sommeRetire = 0;
            /* 186 */ int sommeCarnet = 0;
            /* 187 */ int sommeCommission = 0;
            /* 188 */ int solde = 0;

            /* 204 */ table.addCell(createPdfPCell("Totaux", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 205 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeVerse)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 206 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeRetire)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 207 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCommission)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 208 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCarnet)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 209 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            /* 211 */ rapport.add(table);
            /* 212 */ rapport.close();
        } catch (DocumentException ex) {
            /* 215 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            /* 217 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* 219 */ return fileName;
    }

    public static String printDailylyReport(Date date, List<Solde> soldes) {
        /* 223 */ String fileName = "";
        try {
            /* 225 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 226 */ fileName = "Rapport_du_" + sdf.format(date) + ".pdf";
            /* 227 */ Document rapport = new Document();
            /* 228 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/journalier/" + fileName));
            /* 229 */ rapport.open();
            /* 230 */ float[] widths = {0.6F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
            /* 231 */ PdfPTable table = new PdfPTable(widths);
            /* 232 */ table.setWidthPercentage(100.0F);

            /* 234 */ createEntete(rapport, SessionMBean.getParametrage());

            /* 236 */ rapport.add(new Paragraph(" "));

            /* 238 */ Paragraph titre = new Paragraph("RAPPORT JOURNALIER D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 239 */ titre.setAlignment(1);
            /* 240 */ rapport.add(titre);

            /* 242 */ Paragraph periode = new Paragraph("Date : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            /* 243 */ periode.setAlignment(1);
            /* 244 */ rapport.add(periode);

            /* 246 */ rapport.add(new Paragraph(" "));

            /* 248 */ table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 249 */ table.addCell(createPdfPCell("Nom(s) et prénom(s) du client", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 250 */ table.addCell(createPdfPCell("Montant versé", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 251 */ table.addCell(createPdfPCell("Montant retiré", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 252 */ table.addCell(createPdfPCell("Commissions", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 253 */ table.addCell(createPdfPCell("Frais carnet", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 254 */ table.addCell(createPdfPCell("Solde", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            /* 256 */ int sommeVerse = 0;
            /* 257 */ int sommeRetire = 0;
            /* 258 */ int sommeCarnet = 0;
            /* 259 */ int sommeCommission = 0;
            /* 260 */ int solde = 0;

            /* 276 */ table.addCell(createPdfPCell("Totaux", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 277 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeVerse)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 278 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeRetire)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 279 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCommission)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 280 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(sommeCarnet)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            /* 281 */ table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(solde)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            /* 283 */ rapport.add(table);
            /* 284 */ rapport.close();
        } catch (DocumentException ex) {
            /* 287 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            /* 289 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* 291 */ return fileName;
    }

    public static String printProductReport(List<Article> articles) {
        /* 356 */ String fileName = "";
        try {
            /* 358 */ Date date = new Date();
            /* 359 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 360 */ fileName = "Fiche_produits_" + sdf.format(date) + ".pdf";
            /* 361 */ Document rapport = new Document();
            /* 362 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/produits/" + fileName));
            /* 363 */ rapport.open();
            /* 364 */ float[] widths = {0.7F, 1.9F, 3.9F, 1.0F};
            /* 365 */ PdfPTable table = new PdfPTable(widths);
            /* 366 */ table.setWidthPercentage(100.0F);

            /* 368 */ createEntete(rapport, SessionMBean.getParametrage());

            /* 370 */ rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2)));

            /* 372 */ Paragraph titre = new Paragraph("FICHE DESCRIPTIVE DES PRODUITS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 373 */ titre.setAlignment(1);
            /* 374 */ rapport.add(titre);

            /* 376 */ Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            /* 377 */ periode.setAlignment(1);
            /* 378 */ rapport.add(periode);

            /* 380 */ rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2)));

            /* 382 */ table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 383 */ table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 384 */ table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            /* 385 */ table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int compteur = 1;

            for (Article a : articles) {
                table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + a.getIdfamille().getNom(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + a.getLibelle(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                compteur++;
            }

            /* 397 */ rapport.add(table);
            /* 398 */ rapport.close();
        } catch (DocumentException ex) {
            /* 401 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            /* 403 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* 405 */ return fileName;
    }

    public static String printGeneralStockReport(List<Article> articles) {
        /* 409 */ String fileName = "";
        try {
            /* 411 */ Date date = new Date();
            /* 412 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 413 */ fileName = "Fiche_stock_general" + sdf.format(date) + ".pdf";
            /* 414 */ Document rapport = new Document();
            /* 415 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            /* 416 */ rapport.open();
            /* 417 */ float[] widths = {0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.2F};
            /* 418 */ PdfPTable table = new PdfPTable(widths);
            /* 419 */ table.setWidthPercentage(100.0F);

            /* 421 */ Paragraph entreprise = new Paragraph("FREDY BUSINESS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            /* 422 */ entreprise.setAlignment(1);
            /* 423 */ rapport.add(entreprise);

            /* 425 */ Paragraph entreprise1 = new Paragraph("Vente Téléphones & Accessoires , Cartes Mémoires -MP3-MP4 - Clé USB", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            /* 426 */ entreprise1.setAlignment(1);
            /* 427 */ rapport.add(entreprise1);

            /* 429 */ Paragraph entreprise2 = new Paragraph("Sis à MOKOLO Immeuble Cameroun Bébé (Yaoundé)", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 1));
            /* 430 */ entreprise2.setAlignment(1);
            /* 431 */ rapport.add(entreprise2);

            /* 433 */ Paragraph adresse2 = new Paragraph(" Tél.: 675 82 35 78 / 694 43 08 81", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            /* 434 */ adresse2.setAlignment(1);
            /* 435 */ rapport.add(adresse2);

            /* 437 */ rapport.add(new Paragraph(" "));

            /* 439 */ Paragraph titre = new Paragraph("FICHE DU STOCK EN MARCHANDISE", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 440 */ titre.setAlignment(1);
            /* 441 */ rapport.add(titre);

            /* 443 */ Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            /* 444 */ periode.setAlignment(1);
            /* 445 */ rapport.add(periode);

            /* 447 */ rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Famille", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));

            Double total = 0.0D;
            int quantite = 0;
            int compteur = 1;
            Article localArticle;
            for (Iterator localIterator = articles.iterator(); localIterator.hasNext(); localArticle = (Article) localIterator.next());
            table.addCell(createPdfPCell("Totaux", 4, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Integer.valueOf(quantite)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Utilitaires.arrondiNDecimales(total.doubleValue(), 1)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public static String printCritickStockReport(List<Article> articles) {
        String fileName = "";
        try {
            /* 489 */ Date date = new Date();
            /* 490 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 491 */ fileName = "Fiche_stock_critic" + sdf.format(date) + ".pdf";
            /* 492 */ Document rapport = new Document();
            /* 493 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            /* 494 */ rapport.open();
            /* 495 */ float[] widths = {0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.2F};
            /* 496 */ PdfPTable table = new PdfPTable(widths);
            /* 497 */ table.setWidthPercentage(100.0F);

            /* 499 */ createEntete(rapport, SessionMBean.getParametrage());

            /* 501 */ rapport.add(new Paragraph(" "));

            /* 503 */ Paragraph titre = new Paragraph("FICHE DES PRODUITS EN SOUS-STOCKAGE", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 504 */ titre.setAlignment(1);
            /* 505 */ rapport.add(titre);

            /* 507 */ Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            /* 508 */ periode.setAlignment(1);
            /* 509 */ rapport.add(periode);

            /* 511 */ rapport.add(new Paragraph(" "));

            /* 513 */ table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 514 */ table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 515 */ table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 516 */ table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 517 */ table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 518 */ table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));

            /* 520 */ Double total = Double.valueOf(0.0D);
            /* 521 */ int quantite = 0;
            /* 522 */ int compteur = 1;

            /* 543 */ rapport.add(table);
            /* 544 */ rapport.close();
        } catch (DocumentException ex) {
            /* 547 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            /* 549 */ Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        /* 551 */ return fileName;
    }

    public static void createEntete(Document document, Parametrage parametrage) throws DocumentException {
        try {
            /* 741 */ Paragraph entreprise = new Paragraph(parametrage.getNomStructure(), new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1));
            /* 742 */ entreprise.setAlignment(1);
            /* 743 */ document.add(entreprise);

            /* 745 */ Paragraph entreprise1 = new Paragraph("" + parametrage.getDescriptif(), new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 1));
            /* 746 */ entreprise1.setAlignment(1);
            /* 747 */ document.add(entreprise1);

            /* 749 */ Paragraph entreprise2 = new Paragraph("" + parametrage.getLocalisation(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 1));
            /* 750 */ entreprise2.setAlignment(0);

            /* 753 */ Paragraph adresse2 = new Paragraph("B.P.: " + parametrage.getBoitePostale() + "   Tél.: " + parametrage.getContact1() + " / " + parametrage.getContact2() + "   Fax : " + parametrage.getFax(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 2));
            /* 754 */ adresse2.setAlignment(1);
            /* 755 */ document.add(adresse2);

            /* 757 */ Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            /* 758 */ document.add(ligne);
        } catch (Exception localException) {
        }
    }

    public static void createLine(Document document)
            throws DocumentException {
        /* 781 */ Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 782 */ document.add(ligne);
    }

    public static void createSignatureZone(Document document) throws DocumentException {
        /* 786 */ Paragraph signature1 = new Paragraph("                                                                                        --------------Comptable------------------------Directeur-------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 787 */ document.add(signature1);

        /* 789 */ Paragraph signature2 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 790 */ document.add(signature2);

        /* 792 */ Paragraph signature4 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 793 */ document.add(signature4);

        /* 795 */ Paragraph signature5 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 796 */ document.add(signature5);

        /* 798 */ Paragraph signature6 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 799 */ document.add(signature6);

        /* 801 */ Paragraph signature7 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 802 */ document.add(signature7);

        /* 804 */ Paragraph signature3 = new Paragraph("                                                                                        ----------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        /* 805 */ document.add(signature3);
    }

    public static String printInventoryReport(List<Article> produits) {
        /* 809 */ String fileName = "";
        try {
            /* 811 */ Date date = new Date();
            /* 812 */ SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            /* 813 */ fileName = "Fiche_inventaire_stock" + sdf.format(date) + ".pdf";
            /* 814 */ Document rapport = new Document();
            /* 815 */ PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            /* 816 */ rapport.open();
            /* 817 */ float[] widths = {0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.0F, 1.0F};
            /* 818 */ PdfPTable table = new PdfPTable(widths);
            /* 819 */ table.setWidthPercentage(100.0F);

            /* 821 */ createEntete(rapport, SessionMBean.getParametrage());

            /* 823 */ rapport.add(new Paragraph(" "));

            /* 825 */ Paragraph titre = new Paragraph("FICHE D'INVENTAIRE DU STOCK", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            /* 826 */ titre.setAlignment(1);
            /* 827 */ rapport.add(titre);

            /* 829 */ Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            /* 830 */ periode.setAlignment(1);
            /* 831 */ rapport.add(periode);

            /* 833 */ rapport.add(new Paragraph(" "));

            /* 835 */ table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 836 */ table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 837 */ table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 838 */ table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 839 */ table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 840 */ table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            /* 841 */ table.addCell(createPdfPCell("Quantité\n physique", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));

            Double total = 0.0;
            int quantite = 0;
            int compteur = 1;

            table.addCell(createPdfPCell("Totaux", 4, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(Integer.valueOf(quantite)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(Utilitaires.arrondiNDecimales(total.doubleValue(), 1)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public static String printFacture(Livraisonclient livraisonclient, Parametrage parametrage) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            fileName = "Facture_" + livraisonclient.getClient().getNom() + "_" + livraisonclient.getCode() + "_" + sdf.format(livraisonclient.getDatelivraison()) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/facture/" + fileName));
            rapport.open();

            createEntete(rapport, parametrage);

            float[] width_entete = {2.0F, 2.2F, 1.8F, 3.5F, 1.0F, 1.7F};
            PdfPTable entete_1 = new PdfPTable(width_entete);
            entete_1.setWidthPercentage(100.0F);

            entete_1.addCell(createPdfPCell("Date de tirage   : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf.format(new Date()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Code du client : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + livraisonclient.getClient().getCode(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            entete_1.addCell(createPdfPCell("Heure de tirage : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf1.format(new Date()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Nom du client   : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + livraisonclient.getClient().getNom(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            rapport.add(entete_1);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            float[] widths = {1.4F, 3.2F, 0.8F, 1.3F, 0.8F, 1.1F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph titre = new Paragraph("FACTURE N° : " + livraisonclient.getCode() + " / " + sdf.format(livraisonclient.getDatelivraison()), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            titre.setAlignment(1);
            rapport.add((Element) titre);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(ligne);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("Ref", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Désignation", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Quantité", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Présentation", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("P.U", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Prix total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            Double quantite = 0.0;
            for (Lignelivraisonclient llc : livraisonclient.getLignelivraisonclientList()) {
                quantite += llc.getQuantite();
                table.addCell(createPdfPCell(" " + llc.getIdlot().getIdarticle().getCode(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(llc.getIdlot().getIdarticle().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getQuantite()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(llc.getIdlot().getIdarticle().getIdunite().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getPrixUnitaire().intValue()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getMontant().intValue()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            }

            table.addCell(createPdfPCell("Total ", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + quantite.intValue(), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" ", 3, 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            table.addCell(createPdfPCell("TVA " + livraisonclient.getTauxTva() + " %", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantTva()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" ", 3, 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            table.addCell(createPdfPCell("Remise " + livraisonclient.getTauxRemise() + " %", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantRemise()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" ", 3, 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            rapport.add(table);

            float[] width = {2.0F, 1.0F, 0.5F, 2.0F, 2.0F, 1.5F};
            PdfPTable bilan = new PdfPTable(width);
            bilan.setWidthPercentage(100.0F);
            rapport.add(new Paragraph(" "));

            bilan.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("Caissier(e)", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("NET A PAYER", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 1, 1, 1, 1));

            bilan.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantTtc()), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 1, 1, 0, 1));

            rapport.add(bilan);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printStock(Livraisonfournisseur livraisonfournisseur) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Stock_" + livraisonfournisseur.getCode() + "_" + sdf.format(livraisonfournisseur.getDatelivraison()) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();
            float[] widths = {0.6F, 3.0F, 1.0F, 0.8F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("APPROVISIONNEMENT EN STOCK : " + livraisonfournisseur.getCode(), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            titre.setAlignment(1);
            rapport.add((Element) titre);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add((Element) ligne);

            Paragraph nom = new Paragraph("Fournisseur : " + livraisonfournisseur.getIdfournisseur().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add((Element) nom);

            Paragraph telephoneClient = new Paragraph("Contact : " + livraisonfournisseur.getIdfournisseur().getContact(), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add((Element) telephoneClient);

            Paragraph dateAchat = new Paragraph("Date : " + sdf.format(livraisonfournisseur.getDatelivraison()), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add((Element) dateAchat);

            rapport.add((Element) new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Prix d'achat", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Quantité", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            Double quantite = 0.0;
            int compteur = 1;
            for (Lignelivraisonfournisseur llf : livraisonfournisseur.getLignelivraisonfournisseurList()) {
                quantite += llf.getQuantite();
                table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + llf.getIdmagasinlot().getIdlot().getIdarticle().getLibelle(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(llf.getPrixachat()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(llf.getQuantite()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                Double value = (llf.getPrixachat() * llf.getQuantite());
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((value.intValue())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                compteur++;
            }

            table.addCell(createPdfPCell("Totaux", 3, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(quantite), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((livraisonfournisseur.getMontant().intValue())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add((Element) table);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printBonCommandeClient(Demande demande, Parametrage parametrage) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            fileName = "Boncommande_" + demande.getClient().getNom() + "_" + sdf.format(demande.getDatedemande()) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/facture/" + fileName));
            rapport.open();

            createEntete(rapport, parametrage);

            float[] width_entete = {2.0F, 2.3F, 1.8F, 3.8F, 1.0F, 1.0F};
            PdfPTable entete_1 = new PdfPTable(width_entete);
            entete_1.setWidthPercentage(100.0F);

            entete_1.addCell(createPdfPCell("Date de tirage   : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf.format(new Date()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Code du client : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + demande.getClient().getCode(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            entete_1.addCell(createPdfPCell("Heure de tirage : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf1.format(new Date()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Nom du client   : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + demande.getClient().getNom(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            rapport.add(entete_1);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            float[] widths = {1.4F, 3.2F, 0.8F, 1.3F, 0.8F, 1.1F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph titre = new Paragraph("BON DE COMMANDE N° : " + demande.getCode() + " / " + sdf.format(demande.getDatedemande()), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(ligne);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("Ref", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Désignation", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Quantité", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Présentation", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("P.U", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Prix total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            Double quantite = 0.0;
            for (Lignedemande l : demande.getLignedemandeList()) {
                quantite += l.getQuantite();
                table.addCell(createPdfPCell(" " + l.getIdmagasinarticle().getIdarticle().getCode(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(l.getIdmagasinarticle().getIdarticle().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(l.getQuantite()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(l.getIdmagasinarticle().getIdarticle().getIdunite().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(l.getPrixUnitaire().intValue()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney((l.getPrixUnitaire().intValue() * l.getQuantite().intValue())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            }

            table.addCell(createPdfPCell("Total ", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + quantite.intValue(), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" ", 3, 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            table.addCell(createPdfPCell("TVA " + demande.getTauxTva() + " %", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(demande.getMontantTva()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" ", 3, 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            table.addCell(createPdfPCell("Remise " + demande.getTauxRemise() + " %", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(demande.getMontantRemise()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" ", 3, 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            rapport.add(table);

            float[] width = {2.0F, 1.0F, 0.5F, 2.0F, 2.0F, 1.5F};
            PdfPTable bilan = new PdfPTable(width);
            bilan.setWidthPercentage(100.0F);
            rapport.add(new Paragraph(" "));

            bilan.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" VALEUR TTC", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 1, 1, 1, 1));

            bilan.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney((int) demande.getMontantTtc()), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 1, 1, 0, 1));

            rapport.add((Element) bilan);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }
}
