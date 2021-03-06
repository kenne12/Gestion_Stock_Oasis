package sessions;

import entities.Magasin;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MagasinFacade extends AbstractFacade<Magasin> implements MagasinFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public MagasinFacade() {
        super(Magasin.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(m.idmagasin) FROM Magasin m");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Magasin> findAllRange(int idStructure) {
        return this.em.createQuery("SELECT m FROM Magasin m WHERE m.parametrage.id=:id ORDER BY m.nom")
                .setParameter("id", idStructure).getResultList();
    }

    @Override
    public List<Magasin> findAllRangeMcIsFalse(int idStructure) {
        return this.em.createQuery("SELECT m FROM Magasin m WHERE m.central=false AND m.parametrage.id=:id ORDER BY m.nom")
                .setParameter("id", idStructure).getResultList();
    }
}
