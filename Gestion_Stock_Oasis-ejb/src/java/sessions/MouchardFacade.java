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
        try {
            Query query = this.em.createQuery("SELECT MAX(m.idmouchard) FROM Mouchard m");
            return (Long) query.getResultList().get(0) + 1;
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public void deleteByIdutilisateur(int idutilisateur) {
        Query query = em.createQuery("DELETE  FROM Mouchard m WHERE m.idutilisateur.idutilisateur=:idutilisateur");
        query.setParameter("idutilisateur", idutilisateur);
        query.executeUpdate();
    }
}
