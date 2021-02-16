package sessions;

import entities.Ligneinventaire;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LigneinventaireFacade extends AbstractFacade<Ligneinventaire> implements LigneinventaireFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LigneinventaireFacade() {
        super(Ligneinventaire.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idligneinventaire) FROM Ligneinventaire l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<Ligneinventaire> findByIdInventaire(long idinventaire) {
        Query query = this.em.createQuery("SELECT l FROM Ligneinventaire l WHERE l.idinventaire.idinventaire=:idinventaire");
        query.setParameter("idinventaire", (idinventaire));
        return query.getResultList();
    }

    @Override
    public void removeByIdInventaire(long idinventaire) {
        Query query = this.em.createQuery("DELETE FROM Ligneinventaire l WHERE l.idinventaire.idinventaire=:idinventaire");
        query.setParameter("idinventaire", (idinventaire));
        query.executeUpdate();
    }
}
