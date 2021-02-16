package sessions;

import entities.Transfert;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TransfertFacade extends AbstractFacade<Transfert>
        implements TransfertFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public TransfertFacade() {
        super(Transfert.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(t.idtransfert) FROM Transfert t");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = (1L);
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<Transfert> findAllRange() {
        return this.em.createQuery("SELECT t FROM Transfert t ORDER BY t.datetransfert DESC").getResultList();
    }
}
