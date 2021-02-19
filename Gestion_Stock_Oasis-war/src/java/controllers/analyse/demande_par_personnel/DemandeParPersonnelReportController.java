package controllers.analyse.demande_par_personnel;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import utils.Printer;

@ManagedBean
@ViewScoped
public class DemandeParPersonnelReportController extends AbstratDemandeParPersonnelReportController implements Serializable {

    @PostConstruct
    private void init() {
    }

    public void search() {
        try {
            if (this.personnel.getIdpersonnel() != null) {
                //this.demandes = this.demandeFacadeLocal.findByIdpersonnelIntervalDate(this.personnel.getIdpersonnel().longValue(), this.dateDebut, this.dateFin);
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
            map.put("idpersonnel", this.personnel.getIdpersonnel());
            Printer.print("/reports/ireport/demande_par_personnel.jasper", map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
