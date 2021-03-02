package controllers.analyse.mvt_par_produit;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.Printer;

@ManagedBean
@ViewScoped
public class MvtPrReportController extends AbstratMvtPrReportController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void search() {
        try {
            if (this.article.getIdarticle() != null) {
                this.lignemvtstocks = this.lignemvtstockFacadeLocal.findByIdarticleIntevalDate(this.article.getIdarticle(), this.dateDebut, this.dateFin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printStock() {
        try {
            Map map = new HashMap();
            map.put("date_debut", this.dateDebut);
            map.put("date_fin", this.dateFin);
            map.put("idarticle", this.article.getIdarticle());
            Printer.print("/reports/ireport/mouvement_par_produit.jasper", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
