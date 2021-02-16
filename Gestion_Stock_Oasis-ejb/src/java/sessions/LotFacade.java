package sessions;

import entities.Lot;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LotFacade extends AbstractFacade<Lot> implements LotFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LotFacade() {
        super(Lot.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlot) FROM Lot l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Lot> findAllRange() {
        return this.em.createQuery("SELECT l FROM Lot l ORDER BY l.idarticle.libelle, l.numero").getResultList();
    }

    @Override
    public List<Lot> findAllRangeEtatIsTrue() {
        return this.em.createQuery("SELECT l FROM Lot l WHERE l.etat=true ORDER BY l.idarticle.libelle, l.numero").getResultList();
    }

    @Override
    public List<Lot> findAllRange(boolean perissable) {
        Query query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.perissable=:perissable ORDER BY l.idarticle.libelle, l.numero");
        query.setParameter("perissable", perissable);
        return query.getResultList();
    }

    @Override
    public Lot findByCode(long idarticle, String numero) {
        Query query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle AND l.numero=:numero");
        query.setParameter("idarticle", idarticle).setParameter("numero", numero);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Lot) list.get(0);
        }
        return null;
    }

    @Override
    public Lot findByCode(String numero) {
        Query query = this.em.createQuery("SELECT l FROM Lot l WHERE l.numero=:numero");
        query.setParameter("numero", numero);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Lot) list.get(0);
        }
        return null;
    }

    @Override
    public List<Lot> findByArticle1(Long idacrticle, boolean order, Date date) throws Exception {
        Query query = null;
        if (order) {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle AND l.dateperemption>:date ORDER BY l.dateperemption");
            query.setParameter("date", date);
        } else {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle");
        }
        query.setParameter("idarticle", idacrticle);
        return query.getResultList();
    }

    @Override
    public List<Lot> findByArticle(Long idacrticle) {
        Query query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle ORDER BY l.numero");
        query.setParameter("idarticle", idacrticle);
        return query.getResultList();
    }

    @Override
    public List<Lot> findByArticle(Long idacrticle, boolean order) throws Exception {
        Query query = null;
        if (order) {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle ORDER BY l.dateperemption");
        } else {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle");
        }
        query.setParameter("idarticle", idacrticle);
        return query.getResultList();
    }

    @Override
    public List<Lot> findByArticleRangeDesc(Long idacrticle, boolean order) throws Exception {
        Query query = null;
        if (order) {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle ORDER BY l.dateperemption DESC");
        } else {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle ORDER BY l.dateenregistrement DESC");
        }
        query.setParameter("idarticle", idacrticle);
        return query.getResultList();
    }

    @Override
    public List<Lot> findByArticle(Long idacrticle, boolean order, Date date) throws Exception {
        Query query = null;
        if (order) {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle AND l.quantite>0D AND l.dateperemption>:date ORDER BY l.dateperemption");
            query.setParameter("date", date).setParameter("idarticle", idacrticle);
        } else {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle AND l.quantite>0D");
        }
        query.setParameter("idarticle", idacrticle);
        return query.getResultList();
    }

    @Override
    public List<Lot> findByArticle(Long idacrticle, boolean order, Date date, boolean desc) throws Exception {
        Query query = null;
        if (order) {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle AND l.quantite>0D AND l.dateperemption>:date ORDER BY l.dateperemption DESC");
            query.setParameter("idarticle", idacrticle).setParameter("date", date);
        } else {
            query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.idarticle=:idarticle AND l.quantite>0D");
        }
        query.setParameter("idarticle", idacrticle);
        return query.getResultList();
    }

    @Override
    public List<Lot> findAllPeremptionIsActif(Date date) {
        Query query = this.em.createQuery("SELECT l FROM Lot l WHERE l.idarticle.perissable=true AND l.etat=true AND :date>=l.dateperemption ORDER BY l.idarticle.libelle, l.numero");
        query.setParameter("date", date);
        return query.getResultList();
    }
}
