package controllers.analyse.stock_par_magasin;

import entities.Lot;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.context.RequestContext;
import utils.PrintUtils;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class StockReportController extends AbstratStockReportController implements Serializable {

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void search() {
        try {
            this.setPrintBtnState(true);
            if (this.magasin.getIdmagasin() != null) {
                this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinEtatIsTrue(this.magasin.getIdmagasin());
                if(!magasinlots.isEmpty()){
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
