package controllers.analyse.peremption;

import entities.Lot;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.Printer;
import utils.SessionMBean;
import utils.Utilitaires;

@ManagedBean
@ViewScoped
public class StockPController extends AbstratStockPController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public Boolean checkPeremption(Lot lot) {
        return Utilitaires.checkPeremption(lot);
    }

    public void search() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                this.magasinlots = this.magasinlotFacadeLocal.findAllPeremptedEtatIsTrue(SessionMBean.getMagasin().getIdmagasin(), new Date(System.currentTimeMillis()));
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
                map.put("date_jour", new Date());
                Printer.print("/reports/ireport/stock_par_magasin_peremption.jasper", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
