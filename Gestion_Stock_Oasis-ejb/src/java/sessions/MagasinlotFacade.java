package sessions;

import entities.Magasinlot;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MagasinlotFacade extends AbstractFacade<Magasinlot> implements MagasinlotFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        /*  28 */ return this.em;
    }

    public MagasinlotFacade() {
        /*  32 */ super(Magasinlot.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(m.idmagasinlot) FROM Magasinlot m");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle, boolean order, Date date) throws Exception {
        Query query = null;
        if (order) {
            query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.quantitemultiple >0D  AND  m.idmagasinarticle.idarticle.idarticle=:idarticle AND  m.idlot.dateperemption>:date ORDER BY m.idlot.dateperemption DESC");
            query.setParameter("date", date);
        } else {
            query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.quantitemultiple >0D  AND  m.idmagasinarticle.idarticle.idarticle=:idarticle ORDER BY m.idlot.dateenregistrement DESC");
        }
        query.setParameter("idarticle", idarticle).setParameter("idmagasin", idmagasin);
        return query.getResultList();
    }

    @Override
    public Magasinlot findByIdmagasinIdlot(int idmagasin, long idlot) {
        Query query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.idlot.idlot=:idlot");
        query.setParameter("idmagasin", idmagasin).setParameter("idlot", idlot);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Magasinlot) list.get(0);
        }
        return null;
    }

    @Override
    public List<Magasinlot> findByIdMagasin(int idMagasin) {
        Query query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin");
        query.setParameter("idmagasin", idMagasin);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByIdmagasinEtatIsTrue(int idmagasin) {
        Query query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.etat=true ORDER BY m.idlot.idarticle.libelle , m.idlot.dateenregistrement DESC");
        query.setParameter("idmagasin", idmagasin);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByIdlot(long idlot) {
        Query query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idlot.idlot=:idlot ORDER BY m.idmagasinarticle.idmagasin.nom");
        query.setParameter("idlot", idlot);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByIdmagasinNotPerempt(int idmagasin, boolean perissable, Date date) throws Exception {
        Query query = null;
        if (perissable) {
            query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.idlot.dateperemption>:date ORDER BY m.idlot.dateperemption DESC");
            query.setParameter("date", date);
        } else {
            query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin ORDER BY m.idlot.dateenregistrement DESC");
        }
        query.setParameter("idmagasin", idmagasin);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findAllPeremptedEtatIsTrue(Date date) throws Exception {
        Query query = this.em.createQuery("SELECT ml FROM Magasinlot ml WHERE ml.idlot.idarticle.perissable = true AND ml.etat=true AND ml.idlot.etat=true AND :date>=ml.idlot.dateperemption ORDER BY ml.idmagasinarticle.idmagasin.nom,ml.idmagasinarticle.idarticle.libelle,ml.idlot.numero");
        query.setParameter("date", date);
        return query.getResultList();
    }

    @Override
    public void removeAllByIdarticle(long idarticle) {
        Query query = this.em.createQuery("DELETE FROM Magasinlot ml WHERE ml.idlot.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        query.executeUpdate();
    }

    @Override
    public List<Magasinlot> findAllEtatIsTrue() throws Exception {
        Query query = this.em.createQuery("SELECT ml FROM Magasinlot ml WHERE ml.etat=true AND ml.idlot.etat=true ORDER BY ml.idmagasinarticle.idmagasin.nom,ml.idmagasinarticle.idarticle.libelle,ml.idlot.numero");
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle) {
        Query query = em.createQuery("SELECT ml FROM Magasinlot ml WHERE ml.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND ml.idmagasinarticle.idarticle.idarticle=:idarticle AND ml.quantite>0d");
        query.setParameter("idmagasin", idmagasin).setParameter("idarticle", idarticle);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByIdmagasinQtyNotNull(int idmagasin) {
        Query query = em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.quantite>0d");
        query.setParameter("idmagasin", idmagasin);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByIdMagasinIdArticle(long idMagasinArticle) {
        Query query = em.createQuery("SELECT ml FROM Magasinlot ml WHERE ml.idmagasinarticle.idmagasinarticle=:idMagasinArticle ORDER BY ml.idmagasinlot");
        query.setParameter("idMagasinArticle", idMagasinArticle);
        return query.getResultList();
    }
}
