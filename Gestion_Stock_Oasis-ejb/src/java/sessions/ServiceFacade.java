package sessions;

import entities.Service;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ServiceFacade extends AbstractFacade<Service> implements ServiceFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ServiceFacade() {
        super(Service.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(s.idservice) FROM Service s");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = (1);
        } else {
            result = (result + 1);
        }
        return result;
    }

    @Override
    public List<Service> findAllRange() {
        return this.em.createQuery("SELECT s FROM Service s ORDER BY s.nom").getResultList();
    }
}
