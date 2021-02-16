package sessions;

import entities.Livraisonclient;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LivraisonclientFacade extends AbstractFacade<Livraisonclient> implements LivraisonclientFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LivraisonclientFacade() {
        super(Livraisonclient.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlivraisonclient) FROM Livraisonclient l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Livraisonclient> findAllRange() {
        return this.em.createQuery("SELECT l FROM Livraisonclient l ORDER BY l.datelivraison DESC").getResultList();
    }

    @Override
    public Livraisonclient findByIddemande(long iddemande) {
        Query query = em.createQuery("SELECT l FROM Livraisonclient l WHERE l.iddemande.iddemande=:iddemande");
        query.setParameter("iddemande", iddemande);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Livraisonclient) list.get(0);
        }
        return null;
    }
}
