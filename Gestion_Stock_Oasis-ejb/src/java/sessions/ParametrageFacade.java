package sessions;

import entities.Parametrage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ParametrageFacade extends AbstractFacade<Parametrage> implements ParametrageFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ParametrageFacade() {
        super(Parametrage.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(p.id) FROM Parametrage p");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result += 1;
        }
        return result;
    }

    @Override
    public List<Parametrage> findAllRange() {
        return em.createQuery("SELECT p FROM Parametrage p ORDER BY p.id")
                .getResultList();
    }

}
