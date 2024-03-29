package utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Utilities;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Annee;
import entities.AnneeMois;
import entities.Article;
import entities.Demande;
import entities.Journee;
import entities.Lignedemande;
import entities.Lignelivraisonclient;
import entities.Lignelivraisonfournisseur;
import entities.Lignemvtstock;
import entities.Lignetransfert;
import entities.Livraisonclient;
import entities.Livraisonfournisseur;
import entities.Magasin;
import entities.Magasinlot;
import entities.Parametrage;
import entities.Transfert;
import entities.Versement;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
        }
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int colspan, int position) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell));
        cell.setColspan(colspan);
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
        }
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position, Font font) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
        }
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position, Font font, int borderLeft, int borderRight, int borderTop, int borderBottom) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell, font));
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
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
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
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
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
        }
        cell.setBorderWidthLeft(borderLeft);
        cell.setBorderWidthRight(borderRight);
        cell.setBorderWidthTop(borderTop);
        cell.setBorderWidthBottom(borderBottom);
        return cell;
    }

    public static PdfPCell createPdfPCell(String sCell, int position) {
        PdfPCell cell = new PdfPCell(new Paragraph(sCell));
        switch (position) {
            case 1:
                cell.setHorizontalAlignment(0);
                break;
            case 2:
                cell.setHorizontalAlignment(1);
                break;
            default:
                cell.setHorizontalAlignment(2);
                break;
        }
        return cell;
    }

    public static String printWeeklyReport(Date datedebut, Date datefin, List<Solde> soldes) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Rapport_periode_du_" + sdf.format(datedebut) + "_au_" + sdf.format(datefin) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/hebdomadaire/" + fileName));
            rapport.open();
            float[] widths = {0.6F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT PERIODIQUE D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Période du  " + sdf.format(datedebut) + " au " + sdf.format(datefin), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Nom(s) et prénom(s) du client", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant versé", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant retiré", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Commissions", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Frais carnet", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Solde", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int sommeVerse = 0;
            int sommeRetire = 0;
            int sommeCarnet = 0;
            int sommeCommission = 0;
            int solde = 0;

            table.addCell(createPdfPCell("Totaux", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(sommeVerse), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeRetire)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(sommeCommission), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((sommeCarnet)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((solde)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public static String printDailylyReport(Date date, List<Solde> soldes) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Rapport_du_" + sdf.format(date) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/journalier/" + fileName));
            rapport.open();
            float[] widths = {0.6F, 3.0F, 1.0F, 1.0F, 1.0F, 1.0F, 1.0F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT JOURNALIER D'ACTIVITES [VERSEMENTS / RETRAITS]", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Date : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 6));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Nom(s) et prénom(s) du client", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant versé", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Montant retiré", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Commissions", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Frais carnet", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Solde", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int sommeVerse = 0;
            int sommeRetire = 0;
            int sommeCarnet = 0;
            int sommeCommission = 0;
            int solde = 0;

            table.addCell(createPdfPCell("Totaux", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(sommeVerse), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(sommeRetire), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(sommeCommission), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(sommeCarnet), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(solde), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public static String printProductReport(List<Article> articles) {
        String fileName = "";
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Fiche_produits_" + sdf.format(date) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/produits/" + fileName));
            rapport.open();
            float[] widths = {0.7F, 1.9F, 3.9F, 1.0F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2)));

            Paragraph titre = new Paragraph("FICHE DESCRIPTIVE DES PRODUITS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2)));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            int compteur = 1;

            for (Article a : articles) {
                table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + a.getIdfamille().getNom(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + a.getLibelle(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                compteur++;
            }

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public static String printGeneralStockReport(List<Article> articles) {
        String fileName = "";
        try {
            Date date = Date.from(Instant.now());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Fiche_stock_general" + sdf.format(date) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();

            float[] widths = {0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph entreprise = new Paragraph("FREDY BUSINESS", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            entreprise.setAlignment(1);
            rapport.add(entreprise);

            Paragraph entreprise1 = new Paragraph("Vente Téléphones & Accessoires , Cartes Mémoires -MP3-MP4 - Clé USB", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            entreprise1.setAlignment(1);
            rapport.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("Sis à MOKOLO Immeuble Cameroun Bébé (Yaoundé)", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 1));
            entreprise2.setAlignment(1);
            rapport.add(entreprise2);

            Paragraph adresse2 = new Paragraph(" Tél.: 675 82 35 78 / 694 43 08 81", new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 2));
            adresse2.setAlignment(1);
            rapport.add(adresse2);

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("FICHE DU STOCK EN MARCHANDISE", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

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
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((quantite)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Utilitaires.arrondiNDecimales(total, 1)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
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
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Fiche_stock_critic" + sdf.format(date) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();
            float[] widths = {0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("FICHE DES PRODUITS EN SOUS-STOCKAGE", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));

            Double total = 0.0;
            int quantite = 0;
            int compteur = 1;

            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PrintUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileName;
    }

    public static void createEntete(Document document, Parametrage parametrage) throws DocumentException {
        try {
            Paragraph entreprise = new Paragraph(parametrage.getNomStructure(), new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1));
            entreprise.setAlignment(1);
            document.add(entreprise);

            Paragraph entreprise1 = new Paragraph("" + parametrage.getDescriptif(), new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 1));
            entreprise1.setAlignment(1);
            document.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("" + parametrage.getLocalisation(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 1));
            entreprise2.setAlignment(0);

            Paragraph adresse2 = new Paragraph("B.P.: " + parametrage.getBoitePostale() + "   Tél.: " + parametrage.getContact1() + " / " + parametrage.getContact2() + "   Fax : " + parametrage.getFax(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 2));
            adresse2.setAlignment(1);
            document.add(adresse2);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            document.add(ligne);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void createEntete_80_297(Document document, Parametrage parametrage) throws DocumentException {
        try {
            Paragraph entreprise = new Paragraph(parametrage.getNomStructure(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 1));
            entreprise.setAlignment(1);
            document.add(entreprise);

            Paragraph entreprise1 = new Paragraph("" + parametrage.getDescriptif(), new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 1));
            entreprise1.setAlignment(1);
            document.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("" + parametrage.getLocalisation(), new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 1));
            entreprise2.setAlignment(0);

            /*Paragraph adresse2 = new Paragraph("B.P.: " + parametrage.getBoitePostale() + "   Tél.: " + parametrage.getContact1() + " / " + parametrage.getContact2() + "   Fax : " + parametrage.getFax(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 2));
            adresse2.setAlignment(1);
            document.add(adresse2);*/
            Paragraph ligne = new Paragraph("---------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 7.0F, 2));
            document.add(ligne);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public static void createEntetePaysage(Document document, Parametrage parametrage) throws DocumentException {
        try {
            Paragraph entreprise = new Paragraph(parametrage.getNomStructure(), new Font(Font.FontFamily.TIMES_ROMAN, 16.0F, 1));
            entreprise.setAlignment(1);
            document.add(entreprise);

            Paragraph entreprise1 = new Paragraph("" + parametrage.getDescriptif(), new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 1));
            entreprise1.setAlignment(1);
            document.add(entreprise1);

            Paragraph entreprise2 = new Paragraph("" + parametrage.getLocalisation(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 1));
            entreprise2.setAlignment(0);

            Paragraph adresse2 = new Paragraph("B.P.: " + parametrage.getBoitePostale() + "   Tél.: " + parametrage.getContact1() + " / " + parametrage.getContact2() + "   Fax : " + parametrage.getFax(), new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 2));
            adresse2.setAlignment(1);
            document.add(adresse2);

            Paragraph ligne = new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            document.add(ligne);
        } catch (Exception localException) {
        }
    }

    public static void createLine(Document document) throws DocumentException {
        Paragraph ligne = new Paragraph("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(ligne);
    }

    public static void createSignatureZone(Document document) throws DocumentException {
        Paragraph signature1 = new Paragraph("                                                                                        --------------Comptable------------------------Directeur-------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(signature1);

        Paragraph signature2 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(signature2);

        Paragraph signature4 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(signature4);

        Paragraph signature5 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(signature5);

        Paragraph signature6 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(signature6);

        Paragraph signature7 = new Paragraph("                                                                                        *                                                 *                                              *", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(signature7);

        Paragraph signature3 = new Paragraph("                                                                                        ----------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
        document.add(signature3);
    }

    public static String printInventoryReport(List<Article> produits) {
        String fileName = "";
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Fiche_inventaire_stock" + sdf.format(date) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();
            float[] widths = {0.5F, 1.5F, 2.9F, 1.0F, 0.8F, 1.0F, 1.0F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("FICHE D'INVENTAIRE DU STOCK", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("FAMILLE", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Quantité\n physique", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));

            Double total = 0.0;
            int quantite = 0;

            table.addCell(createPdfPCell("Totaux", 4, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(quantite), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(Utilitaires.arrondiNDecimales(total, 1)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
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
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(llc.getIdunite().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getPrixUnitaire()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getMontant()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
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

    //https://www.jmdoudoux.fr/java/dej/chap-generation-documents.htm
    public static String printFacture_format_80_217(Livraisonclient livraisonclient, Parametrage parametrage) {
        String fileName = "";
        Rectangle art = null;
        Rectangle format = null;
        PdfWriter writer = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            fileName = "Facture_" + livraisonclient.getClient().getNom() + "_" + livraisonclient.getCode() + "_" + sdf.format(livraisonclient.getDatelivraison()) + ".pdf";

            format = PageSize.A4;
            art = new Rectangle(format.getLeft() + 25, format.getBottom() + 25, format.getRight() - 25, format.getTop());

            int width = (int) ((8 / 2.54) * 72);
            int length = (int) ((29.7 / 2.54) * 72);

            //Document rapport = new Document(new Rectangle(width, length));
            Document rapport = new Document(new Rectangle(width, length), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f));
            //Document rapport = new Document(PageSize.A6);
            writer = PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/facture/" + fileName));
            //writer.setBoxSize("art", art);
            //writer.setPageEvent(new Custu);

            rapport.open();

            createEntete_80_297(rapport, parametrage);

            float[] width_entete = {3.0F, 2.5F, 3.0F, 3.0F, 0.4F, 0.4F};
            PdfPTable entete_1 = new PdfPTable(width_entete);
            entete_1.setWidthPercentage(100.0F);

            final Font fontBillDetail = new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 0);

            entete_1.addCell(createPdfPCell("Date", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf.format(new Date()), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Code du client", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + livraisonclient.getClient().getCode(), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));

            entete_1.addCell(createPdfPCell("Heure de tirage", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf1.format(new Date()), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Nom du client", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + livraisonclient.getClient().getNom(), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));

            rapport.add(entete_1);
            //rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            float[] widths = {0.5F, 3.2F, 1.3F, 0.8F, 1.5F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph titre = new Paragraph("FACTURE N° : " + livraisonclient.getCode() + " / " + sdf.format(livraisonclient.getDatelivraison()), new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            titre.setAlignment(1);
            rapport.add((Element) titre);

            //Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2));
            //rapport.add(ligne);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 5.0F, 0)));

            final Font dataFont = new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 0);

            table.addCell(createPdfPCell("N°", 2, dataFont));
            table.addCell(createPdfPCell("Dés", 2, dataFont));
            table.addCell(createPdfPCell("P.U", 2, dataFont));
            table.addCell(createPdfPCell("Qté", 2, dataFont));
            table.addCell(createPdfPCell("Total", 2, dataFont));

            Double quantite = 0.0;
            int counteur = 1;
            for (Lignelivraisonclient llc : livraisonclient.getLignelivraisonclientList()) {
                quantite += llc.getQuantite();
                table.addCell(createPdfPCell(" " + counteur, 1, dataFont));
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(llc.getIdlot().getIdarticle().getLibelle()), 1, dataFont));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getPrixUnitaire()), 3, dataFont));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getQuantite()), 3, dataFont));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getMontant()), 3, dataFont));
                counteur++;
            }

            table.addCell(createPdfPCell("Total ", 3, 3, dataFont));
            table.addCell(createPdfPCell(" " + quantite.intValue(), 3, dataFont));
            table.addCell(createPdfPCell(" " + (int) livraisonclient.getMontantHt(), 1, 3, dataFont));

            if (livraisonclient.getTauxTva() != 0 || livraisonclient.getMontantTva() != 0) {
                table.addCell(createPdfPCell("TVA " + livraisonclient.getTauxTva() + " %", 3, 3, dataFont));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantTva()), 3, dataFont));
                table.addCell(createPdfPCell(" ", 1, 2, dataFont));
            }

            if (livraisonclient.getTauxRemise() != 0 || livraisonclient.getMontantRemise() != 0) {
                table.addCell(createPdfPCell("Remise " + livraisonclient.getTauxRemise() + " %", 3, 3, dataFont));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantRemise()), 3, dataFont));
                table.addCell(createPdfPCell(" ", 1, 2, dataFont));
            }
            rapport.add(table);

            float[] bilanTableWidth = {1.0F, 0.8F, 0.5F, 1.8F, 2.7F, 2.7F};
            PdfPTable bilan = new PdfPTable(bilanTableWidth);
            bilan.setWidthPercentage(100.0F);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 4.0F, 0)));

            bilan.addCell(createPdfPCell(" ", 1, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("Caissier(e)", 2, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("Net à payer", 2, dataFont, 1, 1, 1, 1));

            bilan.addCell(createPdfPCell(" ", 1, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantTtc()), 2, dataFont, 1, 1, 0, 1));

            rapport.add(bilan);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printFacture_format_80_217_(Livraisonclient livraisonclient, Parametrage parametrage) {
        String fileName = "";
        //Rectangle art = null;
        //Rectangle format = null;
        PdfWriter writer = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            fileName = "Facture_" + livraisonclient.getClient().getNom() + "_" + livraisonclient.getCode() + "_" + sdf.format(livraisonclient.getDatelivraison()) + ".pdf";

            //format = PageSize.A4;
            //art = new Rectangle(format.getLeft() + 25, format.getBottom() + 25, format.getRight() - 25, format.getTop());
            int width = (int) ((8 / 2.54) * 72);
            int length = (int) ((29.7 / 2.54) * 72);

            //Document rapport = new Document(new Rectangle(width, length));
            Document rapport = new Document(new Rectangle(width, length), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f), Utilities.inchesToPoints(0.35f));
            //Document rapport = new Document(PageSize.A6);
            writer = PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/facture/" + fileName));
            //writer.setBoxSize("art", art);
            //writer.setPageEvent(new Custu);

            rapport.open();

            createEntete_80_297(rapport, parametrage);

            float[] width_entete = {3.0F, 2.5F, 3.0F, 3.0F, 0.4F, 0.4F};
            PdfPTable entete_1 = new PdfPTable(width_entete);
            entete_1.setWidthPercentage(100.0F);

            final Font fontBillDetail = new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 0);

            entete_1.addCell(createPdfPCell("Date", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf.format(new Date()), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Code du client", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + livraisonclient.getClient().getCode(), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));

            entete_1.addCell(createPdfPCell("Heure de tirage", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf1.format(new Date()), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Nom du client", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + livraisonclient.getClient().getNom(), 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, fontBillDetail, 0, 0, 0, 0));

            rapport.add(entete_1);
            //rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            float[] widths = {0.5F, 3.2F, 1.3F, 0.8F, 1.5F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph titre = new Paragraph("FACTURE N° : " + livraisonclient.getCode() + " / " + sdf.format(livraisonclient.getDatelivraison()), new Font(Font.FontFamily.TIMES_ROMAN, 8.0F, 1));
            titre.setAlignment(1);
            rapport.add((Element) titre);

            //Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 2));
            //rapport.add(ligne);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 5.0F, 0)));

            final Font dataFont = new Font(Font.FontFamily.TIMES_ROMAN, 6.0F, 0);

            table.addCell(createPdfPCell("N°", 2, dataFont, 0, 0, 0, 0));
            table.addCell(createPdfPCell("Dés", 2, dataFont, 0, 0, 0, 0));
            table.addCell(createPdfPCell("P.U", 2, dataFont, 0, 0, 0, 0));
            table.addCell(createPdfPCell("Qté", 2, dataFont, 0, 0, 0, 0));
            table.addCell(createPdfPCell("Total", 2, dataFont, 0, 0, 0, 0));

            Double quantite = 0.0;
            int counteur = 1;
            for (Lignelivraisonclient llc : livraisonclient.getLignelivraisonclientList()) {
                quantite += llc.getQuantite();
                table.addCell(createPdfPCell(" " + counteur, 1, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(llc.getIdlot().getIdarticle().getLibelle()), 1, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getPrixUnitaire()), 3, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getQuantite()), 3, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(llc.getMontant()), 3, dataFont, 0, 0, 0, 0));
                counteur++;
            }

            table.addCell(createPdfPCell("Total ", 3, 3, dataFont, 0, 0, 0, 0));
            table.addCell(createPdfPCell(" " + quantite.intValue(), 3, dataFont, 0, 0, 0, 0));
            table.addCell(createPdfPCell(" " + (int) livraisonclient.getMontantHt(), 3, 3, dataFont, 0, 0, 0, 0));

            if (livraisonclient.getTauxTva() != 0 || livraisonclient.getMontantTva() != 0) {
                table.addCell(createPdfPCell("TVA " + livraisonclient.getTauxTva() + " %", 3, 3, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantTva()), 3, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" ", 1, 2, dataFont, 0, 0, 0, 0));
            }

            if (livraisonclient.getTauxRemise() != 0 || livraisonclient.getMontantRemise() != 0) {
                table.addCell(createPdfPCell("Remise " + livraisonclient.getTauxRemise() + " %", 3, 3, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantRemise()), 3, dataFont, 0, 0, 0, 0));
                table.addCell(createPdfPCell(" ", 1, 2, dataFont, 0, 0, 0, 0));
            }

            rapport.add(table);

            float[] bilanTableWidth = {1.0F, 0.8F, 0.5F, 1.8F, 2.7F, 2.7F};
            PdfPTable bilan = new PdfPTable(bilanTableWidth);
            bilan.setWidthPercentage(100.0F);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 4.0F, 0)));

            bilan.addCell(createPdfPCell(" ", 1, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("Caissier(e)", 2, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("Net à payer", 2, dataFont, 1, 1, 1, 1));

            bilan.addCell(createPdfPCell(" ", 1, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, dataFont, 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(livraisonclient.getMontantTtc()), 2, dataFont, 1, 1, 0, 1));

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
            rapport.add(ligne);

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

            rapport.add(bilan);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printAnalyseRDAnnuel(String option, Annee annee, Magasin magasin, Map map, List<AnalyseRD> list, Parametrage parametrage) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            fileName = "Analyse_RD_" + magasin.getNom() + "_" + annee.getNom() + "_" + sdf.format(new Date()) + ".pdf";
            Document rapport = new Document(PageSize.A4.rotate());
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/analyse_rd/" + fileName));
            rapport.open();

            createEntetePaysage(rapport, parametrage);

            float[] width_entete = {2.0F, 2.3F, 1.8F, 3.8F, 1.0F, 1.0F};
            PdfPTable entete_1 = new PdfPTable(width_entete);
            entete_1.setWidthPercentage(100.0F);

            entete_1.addCell(createPdfPCell("Date de tirage   : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf.format(new Date()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Code Boutique : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + magasin.getCode(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            entete_1.addCell(createPdfPCell("Heure de tirage : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf1.format(new Date()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Nom de Boutique : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + magasin.getNom(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            rapport.add(entete_1);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            float[] widths = {3.0F, 2.5F, 1.5F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph titre = null;
            if (option.equals("R")) {
                titre = new Paragraph("ANALYSE DES RECETTES  : " + annee.getNom() + " / " + sdf.format(new Date()), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            } else {
                titre = new Paragraph("ANALYSE DES DEPENSES  : " + annee.getNom() + " / " + sdf.format(new Date()), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            }

            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph ligne = new Paragraph("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(ligne);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("Article", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("N° Lot", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Montant", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("%", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Jan", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Fev", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Mar", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Avr", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Mai", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Juin", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Juil", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Aao", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Sept", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Oct", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Nov", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Déc", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            for (AnalyseRD a : list) {
                table.addCell(createPdfPCell(" " + a.getMagasinlot().getIdmagasinarticle().getIdarticle().getLibelle(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + Utilitaires.formatPrenomMaj(a.getMagasinlot().getIdlot().getNumero()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getMontantTotal())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(Utilitaires.arrondiNDecimales(a.getPourcentage(), 2)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurJanv())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurFev())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurMars())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurAvr())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurMai())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurJuin())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurJuil())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurAout())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurSept())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurOct())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurNov())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(a.getValeurDec())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            }

            table.addCell(createPdfPCell("Totaux ", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("total"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(Math.ceil(((double) map.get("perc")))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt(((double) map.get("jan")))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("fev"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("mar"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("avr"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("mai"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("jui"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("juil"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("aou"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("sep"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("oct"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("nov"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(toInt((double) map.get("dec"))), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            rapport.add(table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    private static int toInt(Double value) {
        return value.intValue();
    }

    public static String printRecuVersement(Versement versement, Parametrage parametrage) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm:ss");
            fileName = "Recu_versement_" + versement.getLivraisonclient().getClient().getNom() + "_" + versement.getCode() + "_" + sdf.format(versement.getDateOperation()) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/facture/" + fileName));
            rapport.open();

            createEntete(rapport, parametrage);

            float[] width_entete = {2.0F, 2.0F, 1.8F, 3.8F, 1.0F, 1.0F};
            PdfPTable entete_1 = new PdfPTable(width_entete);
            entete_1.setWidthPercentage(100.0F);

            entete_1.addCell(createPdfPCell("Date de tirage   : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf.format(Date.from(Instant.now())), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Code du client : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + versement.getLivraisonclient().getClient().getCode(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("District : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            entete_1.addCell(createPdfPCell("Heure de tirage : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + sdf1.format(new Date()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell("Nom du client   : ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" " + versement.getLivraisonclient().getClient().getNom(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            entete_1.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            rapport.add(entete_1);
            rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            float[] widths = {3.2F, 1.5F, 1.2F, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            Paragraph titre = new Paragraph("Récu versement N° : " + versement.getCode() + " / " + sdf.format(versement.getDateOperation()), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(ligne);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("Motif", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Réf facture", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Montant", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell("Status\nReste", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            table.addCell(createPdfPCell(" Payement facture", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + versement.getLivraisonclient().getCode(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) versement.getMontant())), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

            if (versement.getReste() <= 0.0D) {
                table.addCell(createPdfPCell(" Soldé", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            } else {
                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney((int) versement.getReste()), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
            }

            rapport.add(table);

            float[] width = {2.0F, 1.0F, 0.5F, 2.0F, 2.0F, 1.5F};
            PdfPTable bilan = new PdfPTable(width);
            bilan.setWidthPercentage(100.0F);
            rapport.add(new Paragraph(" "));

            bilan.addCell(createPdfPCell(" ", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell(" ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("VISA DU CAISSIER(E)", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
            bilan.addCell(createPdfPCell("   ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

            rapport.add(bilan);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printStockByStore(List<Magasinlot> listLot) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Fiche_Stock_" + listLot.get(0).getIdmagasinarticle().getIdmagasin().getNom() + "_" + sdf.format(Date.from(Instant.now())) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();
            float[] widths = {0.5F, 2.7F, 1.2f, 0.8F, 0.6F, 0.7F, 0.9F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("FICHE DE STOCK", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(ligne);

            Paragraph nom = new Paragraph("Magasin : " + listLot.get(0).getIdmagasinarticle().getIdmagasin().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add(nom);

            Paragraph dateAchat = new Paragraph("Date : " + sdf.format(Date.from(Instant.now())), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add(dateAchat);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("N° Lot", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Qté (d)", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Qté (g)", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            double qteDetail = 0d;
            double qteGros = 0d;
            int compteur = 1;
            double montantTotal = 0d;
            for (Magasinlot ml : listLot) {
                qteDetail += ml.getQuantite();
                qteGros += (ml.getQuantite() * ml.getIdlot().getIdarticle().getUnite());
                table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + Utilitaires.formatPrenomMaj(ml.getIdlot().getIdarticle().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + ml.getIdlot().getNumero(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(ml.getIdlot().getIdarticle().getPrixunit()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

                Double qt = Utilitaires.arrondiNDecimales(ml.getQuantite(), 2);

                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(qt), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Math.ceil(ml.getQuantite() * ml.getIdlot().getIdarticle().getUnite())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                Double value = Math.ceil(ml.getIdlot().getIdarticle().getPrixunit() * ml.getQuantite());
                montantTotal += value;
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(value), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                compteur++;

                System.err.println("---------------------");
                System.err.println("Total  => Qte Gros => " + ml.getQuantite() + " Prix Gros => " + ml.getIdlot().getIdarticle().getPrixunit() + " Total => " + (ml.getQuantite() * ml.getIdlot().getIdarticle().getPrixunit()));
                System.err.println("Total  => Qte Detail => " + (ml.getQuantite() * ml.getIdlot().getIdarticle().getUnite()) + " Prix Detail => " + ml.getIdlot().getIdarticle().getPrixVenteDetail() + " Total => " + ((ml.getQuantite() * ml.getIdlot().getIdarticle().getUnite()) * ml.getIdlot().getIdarticle().getPrixVenteDetail()));
                System.err.println("---------------------");

            }

            table.addCell(createPdfPCell("Totaux", 4, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Math.ceil(qteDetail)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Math.ceil(qteGros)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            System.err.println("montant : " + ((int) montantTotal));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) montantTotal)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printFicheInventaire(List<Magasinlot> listLot) {
        String fileName = "";
        try {

            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Fiche_Inventaire_Stock_" + listLot.get(0).getIdmagasinarticle().getIdmagasin().getNom() + "_" + sdf.format(Date.from(Instant.now())) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();
            float[] widths = {0.5F, 2.7F, 1.2f, 0.8F, 0.8F, 0.5F, 0.8f, 0.8f, 1.2F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("FICHE D'INVENTAIRE DE STOCK", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(ligne);

            Paragraph nom = new Paragraph("Magasin : " + listLot.get(0).getIdmagasinarticle().getIdmagasin().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add(nom);

            Paragraph dateAchat = new Paragraph("Date : " + sdf.format(Date.from(Instant.now())), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add(dateAchat);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            //table.addCell(createPdfPCell("N° Lot", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Qté T (d)", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Qté T (g)", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            table.addCell(createPdfPCell("Unité", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            table.addCell(createPdfPCell("Qté P (g)", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Qté P (d)", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            double qteDetail = 0d;
            double qteGros = 0d;
            int compteur = 1;
            double montantTotal = 0d;
            for (Magasinlot ml : listLot) {
                qteDetail += ml.getQuantite();
                qteGros += (ml.getQuantite() * ml.getIdlot().getIdarticle().getUnite());
                table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + Utilitaires.formatPrenomMaj(ml.getIdlot().getIdarticle().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                //table.addCell(createPdfPCell("" + ml.getIdlot().getNumero(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(ml.getIdlot().getIdarticle().getPrixunit()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

                Double qt = Utilitaires.arrondiNDecimales(ml.getQuantite(), 2);

                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(qt), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Math.ceil(ml.getQuantite() * ml.getIdlot().getIdarticle().getUnite())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                Double value = Math.ceil(ml.getIdlot().getIdarticle().getPrixunit() * ml.getQuantite());
                montantTotal += value;

                table.addCell(createPdfPCell("" + ((int) ml.getIdlot().getIdarticle().getUnite()), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(value), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                compteur++;
            }

            table.addCell(createPdfPCell("Totaux", 3, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Math.ceil(qteDetail)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(Math.ceil(qteGros)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            table.addCell(createPdfPCell(" / ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("", 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) montantTotal)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add(table);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    private static List<Lignemvtstock> filterMvtStock(Article p, List<Lignemvtstock> ligneMvtStocks) {
        return ligneMvtStocks.stream()
                .filter((line) -> line.getIdlot().getIdarticle().getIdarticle().equals(p.getIdarticle()))
                .collect(Collectors.toList());
    }

    public static String printMouvementMensuel(List<Lignemvtstock> ligneMvtStocks, List<Article> produits, Parametrage parametrage, String title, String nom_fichier) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yy");
            fileName = nom_fichier;
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/facture/" + fileName));
            rapport.open();

            createEntete(rapport, parametrage);

            int conteur = 0;

            for (Article p : produits) {

                List<Lignemvtstock> ligne = filterMvtStock(p, ligneMvtStocks);
                if (!ligne.isEmpty()) {

                    ligneMvtStocks.removeAll(ligne);

                    Paragraph titre = new Paragraph("FICHE DE STOCK N° : " + (conteur + 1), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 1));
                    titre.setAlignment(1);
                    rapport.add(titre);

                    rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 0)));

                    float[] widthsEntete = {1.4F, 3.0F, 3.0F, 1.4F};
                    PdfPTable tableEntete = new PdfPTable(widthsEntete);
                    tableEntete.setWidthPercentage(100.0F);

                    tableEntete.addCell(createPdfPCell("Nom", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("" + p.getLibelle(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("CMM", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    tableEntete.addCell(createPdfPCell("Dosage", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("Niveau Maximum", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    tableEntete.addCell(createPdfPCell("Forme", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("Niveau Minimum", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    tableEntete.addCell(createPdfPCell("Conditionnement", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("Stock de sécurité", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    rapport.add(tableEntete);

                    rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 7.0F, 0)));

                    //float[] widths = {1.0F, 1.9F, 1.9F, 1.4f, 1.4F, 2.0F, 1.4F, 2.0F, 1.4F, 1.6F};
                    float[] widths = {1.0F, 1.9F, 1.4f, 1.4F, 2.0F, 1.4F, 2.0F, 1.4F, 1.6F};
                    PdfPTable table = new PdfPTable(widths);
                    table.setWidthPercentage(100.0F);

                    rapport.add(new Paragraph("MOUVEMENTS DE STOCK", new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 0)));
                    rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 7.0F, 0)));

                    table.addCell(createPdfPCell("Date", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    //table.addCell(createPdfPCell("Numéro de lot", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Date de péremption", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Qte A", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Qte E", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Provenance", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Qté S", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Destination", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Reste", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
                    table.addCell(createPdfPCell("Observation", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));

                    for (Lignemvtstock l : ligne) {

                        table.addCell(createPdfPCell("" + sdf.format(l.getIdmvtstock().getDatemvt()), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        //table.addCell(createPdfPCell("" + l.getIdlot().getNumero(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

                        try {
                            Date d = l.getIdlot().getDateperemption();
                            table.addCell(createPdfPCell(" " + sdf2.format(d), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        } catch (Exception e) {
                            table.addCell(createPdfPCell(" / ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        }

                        table.addCell(createPdfPCell("" + l.getQteAvant(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));

                        if (l.getQteentree() == 0d) {
                            table.addCell(createPdfPCell(" / ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                            table.addCell(createPdfPCell(" / ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        } else {
                            table.addCell(createPdfPCell("" + l.getQteentree(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                            table.addCell(createPdfPCell(" " + l.getFournisseur(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        }

                        if (l.getQtesortie() == 0d) {
                            table.addCell(createPdfPCell(" / ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                            table.addCell(createPdfPCell(" / ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        } else {
                            table.addCell(createPdfPCell(" " + l.getQtesortie(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                            table.addCell(createPdfPCell(" " + l.getClient(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        }

                        table.addCell(createPdfPCell(" " + l.getReste(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                        table.addCell(createPdfPCell(" ", 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                    }
                    rapport.add((Element) table);
                    rapport.newPage();
                }
                conteur++;
            }

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printMouvementMensuelWithBenefit(List<Lignemvtstock> ligneMvtStocks, List<Article> produits, Parametrage parametrage, String title, String nom_fichier) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            fileName = nom_fichier;
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/facture/" + fileName));
            rapport.open();

            createEntete(rapport, parametrage);

            int conteur = 0;

            double totalMarge = 0;
            for (Article p : produits) {

                List<Lignemvtstock> ligne = filterMvtStock(p, ligneMvtStocks);
                if (!ligne.isEmpty()) {

                    ligneMvtStocks.removeAll(ligne);

                    Paragraph titre = new Paragraph("FICHE DE STOCK N° : " + (conteur + 1), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 1));
                    titre.setAlignment(1);
                    rapport.add(titre);

                    rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 0)));

                    float[] widthsEntete = {1.4F, 3.0F, 3.0F, 1.4F};
                    PdfPTable tableEntete = new PdfPTable(widthsEntete);
                    tableEntete.setWidthPercentage(100.0F);

                    tableEntete.addCell(createPdfPCell("Nom", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("" + p.getLibelle(), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("CMM", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    tableEntete.addCell(createPdfPCell("Dosage", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("Niveau Maximum", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    tableEntete.addCell(createPdfPCell("Forme", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("Niveau Minimum", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    tableEntete.addCell(createPdfPCell("Conditionnement", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("Stock de sécurité", 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));
                    tableEntete.addCell(createPdfPCell("--", 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0), 0, 0, 0, 0));

                    rapport.add(tableEntete);

                    rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 7.0F, 0)));

                    //float[] widths = {1.5F, 2.1F, 1.2f, 1.2F, 2.0F, 1.2F, 2.0F, 1.4F, 1.3F};
                    float[] widths = {1.5F, 1.2f, 1.2F, 2.0F, 1.2F, 2.0F, 1.4F, 1.3F};
                    PdfPTable table = new PdfPTable(widths);
                    table.setWidthPercentage(100.0F);

                    rapport.add(new Paragraph("MOUVEMENTS DE STOCK", new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 0)));
                    rapport.add(new Paragraph(" ", new Font(Font.FontFamily.TIMES_ROMAN, 7.0F, 0)));

                    Font font_11_title = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0);

                    table.addCell(createPdfPCell("Date", 2, font_11_title));
                    //table.addCell(createPdfPCell("Numéro de lot", 2, font_11_title));

                    table.addCell(createPdfPCell("Qte A", 2, font_11_title));

                    table.addCell(createPdfPCell("Qte E", 2, font_11_title));
                    table.addCell(createPdfPCell("Provenance", 2, font_11_title));
                    table.addCell(createPdfPCell("Qté S", 2, font_11_title));
                    table.addCell(createPdfPCell("Destination", 2, font_11_title));
                    table.addCell(createPdfPCell("Reste", 2, font_11_title));
                    table.addCell(createPdfPCell("Bénef", 2, font_11_title));

                    Font font_10 = new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0);

                    double margeByProduct = 0;
                    for (Lignemvtstock item : ligne) {

                        table.addCell(createPdfPCell("" + sdf.format(item.getIdmvtstock().getDatemvt()), 1, 2, font_10));
                        //table.addCell(createPdfPCell("" + item.getIdlot().getNumero(), 1, 2, font_10));

                        table.addCell(createPdfPCell("" + item.getQteAvant(), 2, font_10));

                        if (item.getQteentree() == 0d) {
                            table.addCell(createPdfPCell(" ", 2, font_10));
                            table.addCell(createPdfPCell(" ", 2, font_10));
                        } else {
                            table.addCell(createPdfPCell("" + item.getLignelivraisonfournisseur().getQuantitemultiple(), 1, 2, font_10));
                            table.addCell(createPdfPCell(" " + item.getFournisseur().toLowerCase(), 1, 2, font_10));
                        }

                        if (item.getQtesortie() == 0d) {
                            table.addCell(createPdfPCell(" ", 2, font_10));
                            table.addCell(createPdfPCell(" ", 2, font_10));
                        } else {
                            table.addCell(createPdfPCell(" " + item.getLignelivraisonclient().getQuantitemultiple(), 1, 2, font_10));
                            String destination = item.getClient().contains("null") ? item.getClient().replace("null", "") : item.getClient();
                            table.addCell(createPdfPCell(" " + destination.toLowerCase(), 1, 2, font_10));
                        }

                        table.addCell(createPdfPCell(" " + item.getReste(), 2, font_10));

                        if (item.getQtesortie() == 0) {
                            table.addCell(createPdfPCell(" ", 2, font_10));
                        } else {
                            if (item.getLignelivraisonclient().getMarge() == 0) {
                                table.addCell(createPdfPCell(" ", 2, font_10));
                            } else {
                                double margeValue = item.getLignelivraisonclient().getMarge();
                                table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(margeValue), 1, 3, font_10));
                                margeByProduct += margeValue;
                            }
                        }
                    }
                    table.addCell(createPdfPCell(" Sous total ", 7, 3, font_10));
                    if (margeByProduct > 0) {
                        table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(margeByProduct), 1, 3, font_10));
                    } else {
                        table.addCell(createPdfPCell(" ", 1, 3, font_10));
                    }
                    rapport.add(table);
                    if ((conteur + 1) < produits.size()) {
                        rapport.newPage();
                    }
                    totalMarge += margeByProduct;
                }
                conteur++;
            }

            //float[] widths = {1.5F, 2.1F, 1.2f, 1.2F, 2.0F, 1.2F, 2.0F, 1.4F, 1.3F};
            float[] widths = {1.5F, 1.2f, 1.2F, 2.0F, 1.2F, 2.0F, 1.4F, 1.3F};
            PdfPTable tableEnd = new PdfPTable(widths);
            tableEnd.setWidthPercentage(100.0F);

            Font font_10 = new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0);
            tableEnd.addCell(createPdfPCell(" ", 8, 3, font_10));
            tableEnd.addCell(createPdfPCell(" Grand total ", 7, 3, font_10));
            tableEnd.addCell(createPdfPCell("  " + JsfUtil.formaterStringMoney(totalMarge), 1, 3, font_10));

            rapport.add(tableEnd);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printTransfert(Transfert transfert, String magasinCible) {
        String fileName = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Fiche_Transfert_Stock" + transfert.getIdmagasin().getNom() + "_" + magasinCible + "_" + sdf.format(Date.from(Instant.now())) + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();
            float[] widths = {0.5F, 3.5F, 1f, 0.8F, 0.6F, 1.0F};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100.0F);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("FICHE DE TRANSFERT DE MARCHANDISE", new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 1));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph ligne = new Paragraph("----------------------------------------------------------------------------------------------------------------------------------------------", new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(ligne);

            Paragraph magasinSource = new Paragraph("Magasin Source : " + transfert.getIdmagasin().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(magasinSource);

            Paragraph magasinDestination = new Paragraph("Magasin de Destination : " + magasinCible, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            rapport.add(magasinDestination);

            Paragraph dateAchat = new Paragraph("Date : " + sdf.format(Date.from(Instant.now())), new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 2));
            rapport.add(dateAchat);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Produit", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("N° Lot", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Prix", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Qté", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("Total", 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            Double quantite = 0d;
            int compteur = 1;
            Double montantTotal = 0d;
            for (Lignetransfert lt : transfert.getLignetransfertList()) {
                quantite += lt.getQuantite();
                table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
                table.addCell(createPdfPCell("" + Utilitaires.formatPrenomMaj(lt.getIdmagasinlot().getIdlot().getIdarticle().getLibelle()), 1, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + lt.getIdmagasinlot().getIdlot().getNumero(), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(lt.getMontant().intValue()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(lt.getQuantitemultiple().intValue()), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                Double value = (lt.getMontant() * lt.getQuantite());
                montantTotal += value;
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((value.intValue())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                compteur++;
            }

            table.addCell(createPdfPCell("Totaux", 4, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(quantite), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney((montantTotal.intValue())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));

            rapport.add((Element) table);

            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printRecette(AnneeMois anneeMois, List<Journee> journees) {
        String fileName = "";
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Recette_" + anneeMois.getIdmois().getNom() + "_" + anneeMois.getIdannee().getNom() + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/stock/" + fileName));
            rapport.open();
            float[] widths = {0.3F, 2.0F, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100f);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT MENSUEL " + anneeMois.getIdmois().getNom() + "_" + anneeMois.getIdannee().getNom(), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            periode.setAlignment(1);
            rapport.add(periode);

            rapport.add(new Paragraph(" "));

            table.addCell(createPdfPCell("N°", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Date", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Recette", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Transfert\nSortant", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Bord", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Appro", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));
            table.addCell(createPdfPCell("Transfert\nEntrant", 2, new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0)));

            int compteur = 1;

            double totalRecette = 0d;
            double totalBord = 0d;
            double totalAppro = 0d;
            double transfertEntrant = 0;
            double transfertSortant = 0;
            for (Journee j : journees) {
                totalRecette += j.getMontantVendu();
                totalBord += j.getBord();
                totalAppro += j.getMontantEntre();
                transfertEntrant += j.getTransfertEntrant();
                transfertSortant += j.getTransfertSortant();

                table.addCell(createPdfPCell("" + compteur, 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + sdf.format(j.getDateJour()), 2, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) j.getMontantVendu())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) j.getTransfertSortant())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) j.getBord())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) j.getMontantEntre())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) j.getTransfertEntrant())), 3, new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0)));
                compteur++;
            }

            table.addCell(createPdfPCell("Totaux", 2, 2, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalRecette)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) transfertSortant)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalBord)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalAppro)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) transfertEntrant)), 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE)));
            rapport.add((Element) table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }

    public static String printPeriodicOutput(List<Livraisonclient> livraisonclients, String periode) {
        String fileName = "";
        try {
            Date date = Date.from(Instant.now());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            fileName = "Rapport_Recette_" + periode + ".pdf";
            Document rapport = new Document();
            PdfWriter.getInstance(rapport, new FileOutputStream(Utilitaires.path + "/reports/vente/" + fileName));
            rapport.open();
            float[] widths = {0.6F, 2.0F, 1.5f, 1.0f, 1.5f, 1.0f, 1.0f};
            PdfPTable table = new PdfPTable(widths);
            table.setWidthPercentage(100f);

            createEntete(rapport, SessionMBean.getParametrage());

            rapport.add(new Paragraph(" "));

            Paragraph titre = new Paragraph("RAPPORT PERIODIQUE DES RECETTES " + periode.toUpperCase(), new Font(Font.FontFamily.TIMES_ROMAN, 14.0F, 5));
            titre.setAlignment(1);
            rapport.add(titre);

            Paragraph periode_ = new Paragraph("Imprimé le : " + sdf.format(date), new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 2));
            periode_.setAlignment(1);
            rapport.add(periode_);

            rapport.add(new Paragraph(" "));

            Font font_title_11 = new Font(Font.FontFamily.TIMES_ROMAN, 11.0F, 0);

            table.addCell(createPdfPCell("N°", 2, font_title_11));
            table.addCell(createPdfPCell("Date", 2, font_title_11));
            table.addCell(createPdfPCell("Montant ht", 2, font_title_11));
            table.addCell(createPdfPCell("Remise", 2, font_title_11));
            table.addCell(createPdfPCell("Montant ttc", 2, font_title_11));
            table.addCell(createPdfPCell("Marge", 2, font_title_11));
            table.addCell(createPdfPCell("Benéf", 2, font_title_11));

            int compteur = 1;

            double totalTtc = 0d;
            double totalMarge = 0d;
            double totalBenef = 0d;
            double totalRemise = 0d;
            double totalHt = 0d;

            Font font_line_10 = new Font(Font.FontFamily.TIMES_ROMAN, 10.0F, 0);
            for (Livraisonclient item : livraisonclients) {
                totalTtc += item.getMontantTtc();
                totalMarge += item.getMarge();
                totalBenef += item.getBenefice();
                totalRemise += item.getMontantRemise();
                totalHt += item.getMontantHt();

                table.addCell(createPdfPCell("" + compteur, 2, font_line_10));
                table.addCell(createPdfPCell("" + sdf.format(item.getDatelivraison()), 2, font_line_10));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) item.getMontantHt())), 3, font_line_10));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) item.getMontantRemise())), 3, font_line_10));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) item.getMontantTtc())), 3, font_line_10));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) item.getMarge())), 3, font_line_10));
                table.addCell(createPdfPCell("" + JsfUtil.formaterStringMoney(((int) item.getBenefice())), 3, font_line_10));

                compteur++;
            }

            Font font_title_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0, BaseColor.BLUE);

            table.addCell(createPdfPCell("Totaux", 2, 3, new Font(Font.FontFamily.TIMES_ROMAN, 12.0F, 0)));

            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalHt)), 3, font_title_12));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalRemise)), 3, font_title_12));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalTtc)), 3, font_title_12));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalMarge)), 3, font_title_12));
            table.addCell(createPdfPCell(" " + JsfUtil.formaterStringMoney(((int) totalBenef)), 3, font_title_12));
            rapport.add((Element) table);
            rapport.close();
        } catch (DocumentException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, (Throwable) ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(utils.PrintUtils.class.getName()).log(Level.SEVERE, (String) null, ex);
        }
        return fileName;
    }
}
