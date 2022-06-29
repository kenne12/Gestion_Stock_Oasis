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
        return this.em;
    }

    public MagasinlotFacade() {
        super(Magasinlot.class);
    }

    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(m.idmagasinlot) FROM Magasinlot m");
            return ((Long) query.getResultList().get(0) + 1);
        } catch (Exception e) {
            return 1L;
        }
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
    public List<Magasinlot> findByIdmagasinEtatIsTrue(int idMagasin) {
        Query query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.etat=true ORDER BY m.idlot.idarticle.libelle , m.idlot.dateenregistrement DESC");
        query.setParameter("idmagasin", idMagasin);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByIdlot(long idLot) {
        Query query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idlot.idlot=:idlot ORDER BY m.idmagasinarticle.idmagasin.nom");
        query.setParameter("idlot", idLot);
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
    public List<Magasinlot> findAllPeremptedEtatIsTrue(int idMagasin, Date date) throws Exception {
        Query query = this.em.createQuery("SELECT ml FROM Magasinlot ml WHERE ml.idlot.idarticle.perissable = true AND ml.etat=true AND ml.idlot.etat=true AND :date>=ml.idlot.dateperemption AND ml.idmagasinarticle.idmagasin.idmagasin=:idMagasin ORDER BY ml.idmagasinarticle.idmagasin.nom,ml.idmagasinarticle.idarticle.libelle,ml.idlot.numero");
        query.setParameter("date", date).setParameter("idMagasin", idMagasin);
        return query.getResultList();
    }

    @Override
    public void removeAllByIdarticle(long idarticle) {
        Query query = this.em.createQuery("DELETE FROM Magasinlot ml WHERE ml.idlot.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        query.executeUpdate();
    }

    @Override
    public List<Magasinlot> findAllEtatIsTrue(int idmagasin) throws Exception {
        Query query = this.em.createQuery("SELECT ml FROM Magasinlot ml WHERE ml.etat=true AND ml.idlot.etat=true AND ml.idmagasinarticle.idmagasin.idmagasin=:idMagasin ORDER BY ml.idmagasinarticle.idmagasin.nom,ml.idmagasinarticle.idarticle.libelle,ml.idlot.numero");
        return query.setParameter("idMagasin", idmagasin)
                .getResultList();
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

    @Override
    public List<Magasinlot> findByIdMagasinAndEtatGtZero(int idMagasin, boolean etat) {
        Query query = this.em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND m.etat=:etat AND m.quantitereduite > :value");
        query.setParameter("idmagasin", idMagasin).setParameter("etat", etat).setParameter("value", 0d);
        return query.getResultList();
    }

    @Override
    public List<Magasinlot> findByIdmagasinAndFamilleAndEtatGtzero(int idMagasin, long idFamille, boolean etat) {
        return em.createQuery("SELECT m FROM Magasinlot m WHERE m.idmagasinarticle.idmagasin.idmagasin=:idMagasin AND m.idlot.idarticle.idfamille.idfamille=:idFamille AND m.etat=:etat AND m.quantitereduite > 0D ORDER BY m.idlot.idarticle.libelle")
                .setParameter("idMagasin", idMagasin).setParameter("idFamille", idFamille).setParameter("etat", etat)
                .getResultList();
    }

    @Override
    public List<Magasinlot> findByIdArticle(long idArticle) {
        return em.createQuery("SELECT m FROM Magasinlot m WHERE m.idlot.idarticle.idarticle=:idArticle")
                .setParameter("idArticle", idArticle).getResultList();
    }
}
