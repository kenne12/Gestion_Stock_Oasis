package sessions;

import entities.Mouchard;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MouchardFacade extends AbstractFacade<Mouchard> implements MouchardFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public MouchardFacade() {
        super(Mouchard.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(m.idmouchard) FROM Mouchard m");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result += 1L;
        }
        return result;
    }

    @Override
    public void deleteByIdutilisateur(int idutilisateur) {
        Query query = em.createQuery("DELETE  FROM Mouchard m WHERE m.idutilisateur.idutilisateur=:idutilisateur");
        query.setParameter("idutilisateur", idutilisateur);
        query.executeUpdate();
    }
}
