package sessions;

import entities.Lignetransfert;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignetransfertFacade extends AbstractFacade<Lignetransfert> implements LignetransfertFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignetransfertFacade() {
        super(Lignetransfert.class);
    }

    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(l.idlignetransfert) FROM Lignetransfert l");
            Long result = (Long) query.getSingleResult();
            if (result == null) {
                result = 1L;
            } else {
                result += 1L;
            }
            return result;

        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public List<Lignetransfert> findByIdTransfert(long idtransfert) {
        Query query = this.em.createQuery("SELECT l FROM Lignetransfert l WHERE l.idtransfert.idtransfert=:idtransfert");
        query.setParameter("idtransfert", idtransfert);
        return query.getResultList();
    }

    @Override
    public List<Lignetransfert> findByIdarticle(long idarticle) {
        Query query = this.em.createQuery("SELECT l FROM Lignetransfert l WHERE l.idmagasinlot.idmagasinarticle.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        return query.getResultList();
    }
}
