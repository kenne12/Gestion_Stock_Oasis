package sessions;

import entities.Lignerepartitiontemp;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignerepartitiontempFacade extends AbstractFacade<Lignerepartitiontemp> implements LignerepartitiontempFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignerepartitiontempFacade() {
        super(Lignerepartitiontemp.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignerepartitiontemp) FROM Lignerepartitiontemp l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = (1L);
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<Lignerepartitiontemp> findByIdRepartition(int idRepartition) {
        Query query = this.em.createQuery("SELECT l FROM Lignerepartitiontemp l WHERE l.idrepartitionarticle.idrepartitionarticle=:idrepartition");
        query.setParameter("idrepartition", idRepartition);
        return query.getResultList();
    }
}
