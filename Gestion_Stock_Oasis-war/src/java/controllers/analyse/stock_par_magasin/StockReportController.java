package controllers.analyse.stock_par_magasin;

import com.google.common.io.Files;
import entities.Article;
import entities.Lot;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import utils.PrintUtils;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@SessionScoped
public class StockReportController extends AbstratStockReportController implements Serializable {

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void search() {
        try {
            this.setPrintBtnState(true);
            if (this.magasin.getIdmagasin() != null) {
                this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinEtatIsTrue(this.magasin.getIdmagasin());
                if (!magasinlots.isEmpty()) {
                    this.setPrintBtnState(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printStock() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                // Map map = new HashMap();
                // map.put("idmagasin", this.magasin.getIdmagasin());
                fileName = PrintUtils.printStockByStore(magasinlots);
                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('StockImprimerDialog').show()");
                //Printer.print("/reports/ireport/stock_par_magasin.jasper", map);
            }
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        } catch (Exception e) {
            e.printStackTrace();
            RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
        }
    }

    public void prepareUploadPhoto(Article item) {
        this.article = item;
        this.filename = item.getPhoto();
        File f1 = new File(Utilitaires.path + "" + this.imageDir + "/" + item.getPhoto());
        if (!f1.isFile()) {
            File f2 = new File(SessionMBean.getParametrage().getRepertoireImageProduct() + File.separatorChar + "" + item.getPhoto());
            if (f2.exists()) {
                try {
                    Files.copy(f2, f1);
                    filename = item.getPhoto();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        HttpSession session = SessionMBean.getSession();
        session.setAttribute("product_to_upload_photo", item);
        RequestContext.getCurrentInstance().execute("PF('PhotoProduitDialog').show()");
    }
    
    public void handleFileUpload(FileUploadEvent event) {
        try {
            if ((event.getFile() == null) || (event.getFile().getFileName() == null) || (event.getFile().getFileName().equals(""))) {
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("nom_image_incorrect"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            if (!Utilitaires.estExtensionImage(Utilitaires.getExtension(event.getFile().getFileName()))) {
                this.routine.feedBack("avertissement", "/resources/tool_images/error.png", this.routine.localizeMessage("fichier_non_pris_en_charge"));
                RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
                return;
            }

            article = SessionMBean.getProductToUpLoadPhoto();
            String nom = "image_product_" + article.getCode().toLowerCase() + "_" + article.getIdarticle() + "." + Utilitaires.getExtension(event.getFile().getFileName());

            FileOutputStream out = new FileOutputStream(Utilitaires.path + "" + this.imageDir + "/" + nom, true);

            byte[] bytes = event.getFile().getContents();
            out.write(bytes);

            this.filename = nom;
            this.article.setPhoto(nom);
            this.articleFacadeLocal.edit(this.article);

            this.routine.feedBack("information", "/resources/tool_images/success.png", this.routine.localizeMessage("operation_reussie"));

            File f1 = new File(Utilitaires.path + "" + this.imageDir + File.separatorChar + nom);
            File f2 = new File(SessionMBean.getParametrage().getRepertoireImageProduct() + File.separatorChar + "" + nom);
            //File f3 = new File(System.getProperty("user.dir") + File.separatorChar + "gescom" + File.separatorChar + "photo_products" + File.separatorChar + nom);
            Files.copy(f1, f2);
            //Files.copy(f1, f3);
            RequestContext.getCurrentInstance().execute("PF('PhotoProduitDialog').hide()");
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("product_to_upload_photo");

            article = new Article();
            article.setIdUniteDetail(0l);
        } catch (IOException ex) {
            this.routine.catchException(ex, this.routine.localizeMessage("echec_operation"));
            RequestContext.getCurrentInstance().execute("PF('NotifyDialog1').show()");
        }
    }

    public void closeUploadDialog() {

        article = new Article();
        article.setIdUniteDetail(0l);

        RequestContext.getCurrentInstance().execute("PF('PhotoProduitDialog').hide()");
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("product_to_upload_photo");
    }
}
