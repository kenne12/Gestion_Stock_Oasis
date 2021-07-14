package controllers.mouchard;

import entities.Mouchard;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import sessions.MouchardFacadeLocal;

public class AbstractMouchardController {

    @EJB
    protected MouchardFacadeLocal mouchardFacadeLocal;
    protected Mouchard mouchard;
    protected List<Mouchard> mouchards = new ArrayList();

    public List<Mouchard> getMouchards() {
        this.mouchards = this.mouchardFacadeLocal.findAll();
        return this.mouchards;
    }

    public void setMouchards(List<Mouchard> mouchards) {
        this.mouchards = mouchards;
    }
}
