package sessions;

import entities.Lignedemande;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignedemandeFacade extends AbstractFacade<Lignedemande> implements LignedemandeFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        /* 27 */ return this.em;
    }

    public LignedemandeFacade() {
        /* 31 */ super(Lignedemande.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignedemande) FROM Lignedemande l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result += 1L;
        }
        return result;
    }

    @Override
    public List<Lignedemande> findByIddemande(long iddemande) {
        Query query = this.em.createQuery("SELECT l FROM Lignedemande l WHERE l.iddemande.iddemande=:iddemande ORDER BY l.idmagasinarticle.idarticle.libelle");
        query.setParameter("iddemande", iddemande);
        return query.getResultList();
    }

    @Override
    public List<Lignedemande> findByIdarticle(long idarticle) {
        Query query = this.em.createQuery("SELECT l FROM Lignedemande l WHERE l.idmagasinarticle.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        return query.getResultList();
    }
}
