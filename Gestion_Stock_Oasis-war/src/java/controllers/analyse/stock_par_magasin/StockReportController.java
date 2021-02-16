package controllers.analyse.stock_par_magasin;

import entities.Lot;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.Printer;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class StockReportController extends AbstratStockReportController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void search() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                this.magasinlots = this.magasinlotFacadeLocal.findByIdmagasinEtatIsTrue(this.magasin.getIdmagasin().intValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printStock() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                Map map = new HashMap();
                map.put("idmagasin", this.magasin.getIdmagasin());
                Printer.print("/reports/ireport/stock_par_magasin.jasper", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
