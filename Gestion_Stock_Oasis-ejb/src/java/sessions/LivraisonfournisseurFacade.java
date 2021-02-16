package sessions;

import entities.Livraisonfournisseur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LivraisonfournisseurFacade extends AbstractFacade<Livraisonfournisseur> implements LivraisonfournisseurFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LivraisonfournisseurFacade() {
        super(Livraisonfournisseur.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlivraisonfournisseur) FROM Livraisonfournisseur l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<Livraisonfournisseur> findAllRange() {
        return this.em.createQuery("SELECT l FROM Livraisonfournisseur l ORDER BY l.datelivraison DESC").getResultList();
    }

    @Override
    public List<Livraisonfournisseur> findAllRange(boolean livraisonDirecte) {
        return this.em.createQuery("SELECT l FROM Livraisonfournisseur l WHERE l.livraisondirecte=:livraisonDirecte ORDER BY l.datelivraison DESC").setParameter("livraisonDirecte", Boolean.valueOf(livraisonDirecte)).getResultList();
    }
}
