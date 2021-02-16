package sessions;

import entities.Ligneentreedirect;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class LigneentreedirectFacade extends AbstractFacade<Ligneentreedirect> implements LigneentreedirectFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LigneentreedirectFacade() {
        super(Ligneentreedirect.class);
    }
}
