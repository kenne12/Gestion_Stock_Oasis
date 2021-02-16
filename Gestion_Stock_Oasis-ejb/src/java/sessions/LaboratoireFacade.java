package sessions;

import entities.Laboratoire;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LaboratoireFacade extends AbstractFacade<Laboratoire> implements LaboratoireFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LaboratoireFacade() {
        super(Laboratoire.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlaboratoire) FROM Laboratoire l");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Laboratoire> findAllRange() {
        return this.em.createQuery("SELECT l FROM Laboratoire l ORDER BY l.nom").getResultList();
    }
}
