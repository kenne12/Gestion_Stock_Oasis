package sessions;

import entities.Lignerepartitionarticle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignerepartitionarticleFacade extends AbstractFacade<Lignerepartitionarticle> implements LignerepartitionarticleFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignerepartitionarticleFacade() {
        super(Lignerepartitionarticle.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignerepartitionarticle) FROM Lignerepartitionarticle l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Lignerepartitionarticle> findByIdRepartition(int idRepartition) {
        Query query = this.em.createQuery("SELECT l FROM Lignerepartitionarticle l WHERE l.idrepartitionarticle.idrepartitionarticle=:idrepartition");
        query.setParameter("idrepartition", idRepartition);
        return query.getResultList();
    }
}
