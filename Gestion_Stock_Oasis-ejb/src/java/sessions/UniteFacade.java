package sessions;

import entities.Unite;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UniteFacade extends AbstractFacade<Unite> implements UniteFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        /* 27 */ return this.em;
    }

    public UniteFacade() {
        /* 31 */ super(Unite.class);
    }

    @Override
    public Long nextVal() {
        /* 36 */ Query query = this.em.createQuery("SELECT MAX(u.idunite) FROM Unite u");
        /* 37 */ Long result = (Long) query.getSingleResult();
        /* 38 */ if (result == null) /* 39 */ {
            result = Long.valueOf(1L);
        } else {
            /* 41 */ result = Long.valueOf(result.longValue() + 1L);
        }
        /* 43 */ return result;
    }

    @Override
    public List<Unite> findAllRange() {
        /* 48 */ return this.em.createQuery("SELECT u FROM Unite u ORDER BY u.libelle").getResultList();
    }
}
