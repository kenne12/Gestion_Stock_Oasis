package sessions;

import entities.Qualite;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class QualiteFacade extends AbstractFacade<Qualite>
        implements QualiteFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        /* 27 */ return this.em;
    }

    public QualiteFacade() {
        /* 31 */ super(Qualite.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(q.idqualite) FROM Qualite q");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Qualite> findAllRange() {
        return this.em.createQuery("SELECT q FROM Qualite q ORDER BY q.nom").getResultList();
    }
}
