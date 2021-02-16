package controllers.analyse.mvt_par_magasin;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.Printer;

@ManagedBean
@ViewScoped
public class MvtMReportController extends AbstratMvtMReportController implements Serializable {

    public void search() {
        try {
            if (this.magasin.getIdmagasin() != null) {
                this.lignemvtstocks = this.lignemvtstockFacadeLocal.findByIdmagasinIntevalDate(this.magasin.getIdmagasin(), this.dateDebut, this.dateFin);
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
                map.put("date_debut", this.dateDebut);
                map.put("date_fin", this.dateFin);
                Printer.print("/reports/ireport/mouvement_par_magasin.jasper", map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
