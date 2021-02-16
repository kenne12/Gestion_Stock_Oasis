package controllers.analyse.mvt_par_projet;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.Printer;

@ManagedBean
@ViewScoped
public class MvtPReportController extends AbstratMvtPReportController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void search() {
        try {
            if (this.projet.getIdprojet() != null) {
                this.lignelivraisonclients = this.lignelivraisonclientFacadeLocal.findByIdprojet(this.projet.getIdprojet(), this.dateDebut, this.dateFin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void printStock() {
        try {
            Map map = new HashMap();
            map.put("idprojet", this.projet.getIdprojet());
            map.put("date_debut", this.dateDebut);
            map.put("date_fin", this.dateFin);
            Printer.print("/reports/ireport/sortie_materiel_par_projet_interval.jasper", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
