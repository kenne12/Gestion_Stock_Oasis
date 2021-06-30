package controllers.analyse.mvt_par_magasin;

import entities.Article;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.PrintUtils;
import utils.SessionMBean;

@ManagedBean
@ViewScoped
public class MvtMReportController extends AbstratMvtMReportController implements Serializable {

    public void search() {
        try {
            printStateBtn = true;
            if (this.magasin.getIdmagasin() != null) {
                this.lignemvtstocks = this.lignemvtstockFacadeLocal.findByIdmagasinIntevalDate(this.magasin.getIdmagasin(), this.dateDebut, this.dateFin);
                if (!lignemvtstocks.isEmpty()) {
                    printStateBtn = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printStock() {
        try {
            if (magasin.getIdmagasin() != null) {

                magasin = magasinFacadeLocal.find(magasin.getIdmagasin());
                List<Article> articles = articleFacadeLocal.findAllRange(SessionMBean.getParametrage().getId());

                String titre = "FICHE DES MOUVEMENTS DU STOCK";

                this.fileName = "fiche_mvt_stock_" + magasin.getNom() + ".pdf";

                fileName = PrintUtils.printMouvementMensuel(lignemvtstocks, articles, SessionMBean.getParametrage(), titre, fileName);

                RequestContext.getCurrentInstance().execute("PF('AjaxNotifyDialog').hide()");
                RequestContext.getCurrentInstance().execute("PF('StockImprimerDialog').show()");

                //Map map = new HashMap();
                //map.put("idmagasin", this.magasin.getIdmagasin());
                //map.put("date_debut", this.dateDebut);
                //map.put("date_fin", this.dateFin);
                //Printer.print("/reports/ireport/mouvement_par_magasin.jasper", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
