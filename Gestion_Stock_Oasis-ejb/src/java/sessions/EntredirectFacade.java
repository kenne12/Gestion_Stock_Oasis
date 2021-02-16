package sessions;

import entities.Entredirect;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class EntredirectFacade extends AbstractFacade<Entredirect> implements EntredirectFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public EntredirectFacade() {
        super(Entredirect.class);
    }
}
