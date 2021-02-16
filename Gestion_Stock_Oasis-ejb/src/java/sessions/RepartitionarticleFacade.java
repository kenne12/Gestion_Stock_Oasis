package sessions;

import entities.Repartitionarticle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class RepartitionarticleFacade extends AbstractFacade<Repartitionarticle> implements RepartitionarticleFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public RepartitionarticleFacade() {
        super(Repartitionarticle.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(r.idrepartitionarticle) FROM Repartitionarticle r");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Repartitionarticle> findAllRange() {
        return this.em.createQuery("SELECT r FROM Repartitionarticle r ORDER BY r.date DESC").getResultList();
    }
}
