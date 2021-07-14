package sessions;

import entities.Famille;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class FamilleFacade extends AbstractFacade<Famille> implements FamilleFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public FamilleFacade() {
        super(Famille.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(f.idfamille) FROM Famille f");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Famille> findAllRange(int idStructure) {
        return this.em.createQuery("SELECT f FROM Famille f WHERE f.parametrage.id=:id ORDER BY f.code")
                .setParameter("id", idStructure)
                .getResultList();
    }
    
    @Override
    public Long nextValByIdstructure(int idStructure) {
        Query query = this.em.createQuery("SELECT MAX(f.idfamille) FROM Famille f WHERE f.parametrage.id=:idParametre")
                .setParameter("idParametre", idStructure);
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }
}
