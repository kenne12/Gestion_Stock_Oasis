package sessions;

import entities.Mois;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MoisFacade extends AbstractFacade<Mois> implements MoisFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public MoisFacade() {
        super(Mois.class);
    }

    @Override
    public List<Mois> findAllRange() {
        Query query = this.em.createQuery("SELECT m FROM Mois m ORDER BY m.numero");
        return query.getResultList();
    }
}
