package controllers.analyse.stock_par_magasin;

import entities.Magasin;
import entities.Magasinlot;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MagasinFacadeLocal;
import sessions.MagasinlotFacadeLocal;
import sessions.UtilisateurmagasinFacadeLocal;
import utils.SessionMBean;
import utils.Utilitaires;

public class AbstratStockReportController {

    @EJB
    protected MagasinFacadeLocal magasinFacadeLocal;
    protected Magasin magasin = new Magasin();
    protected List<Magasin> magasins = new ArrayList();

    @EJB
    protected MagasinlotFacadeLocal magasinlotFacadeLocal;
    protected List<Magasinlot> magasinlots = new ArrayList();

    @EJB
    protected UtilisateurmagasinFacadeLocal utilisateurmagasinFacadeLocal;

    public List<Magasin> getMagasins() {
        this.magasins = Utilitaires.returMagasinByUser(this.magasinFacadeLocal, this.utilisateurmagasinFacadeLocal, SessionMBean.getUserAccount().getIdpersonnel());
        return this.magasins;
    }

    public Magasin getMagasin() {
        return this.magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public List<Magasinlot> getMagasinlots() {
        return this.magasinlots;
    }

    public void setMagasinlots(List<Magasinlot> magasinlots) {
        this.magasinlots = magasinlots;
    }
}
