package sessions;

import entities.Inventaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class InventaireFacade extends AbstractFacade<Inventaire> implements InventaireFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public InventaireFacade() {
        super(Inventaire.class);
    }

    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(i.idinventaire) FROM Inventaire i");
            Long result = (Long) query.getSingleResult();
            if (result == null) {
                return 1L;
            }
            return (result + 1L);
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public List<Inventaire> findAllRange(int idMagasin) {
        return this.em.createQuery("SELECT i FROM Inventaire i WHERE i.idmagasin.idmagasin=:idMagasin ORDER BY i.dateinventaire DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }
}
