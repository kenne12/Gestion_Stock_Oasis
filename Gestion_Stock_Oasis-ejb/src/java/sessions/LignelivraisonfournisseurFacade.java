package sessions;

import entities.Lignelivraisonfournisseur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignelivraisonfournisseurFacade extends AbstractFacade<Lignelivraisonfournisseur>
        implements LignelivraisonfournisseurFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignelivraisonfournisseurFacade() {
        super(Lignelivraisonfournisseur.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignelivraisonfournisseur) FROM Lignelivraisonfournisseur l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<Lignelivraisonfournisseur> findByIdlivraison(long idlivraison) {
        Query query = this.em.createQuery("SELECT l FROM Lignelivraisonfournisseur l WHERE l.idlivraisonfournisseur.idlivraisonfournisseur=:idlivraison");
        query.setParameter("idlivraison", idlivraison);
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonfournisseur> findByIdarticle(long idarticle) {
        Query query = this.em.createQuery("SELECT l FROM Lignelivraisonfournisseur l WHERE l.idlot.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        return query.getResultList();
    }
}
