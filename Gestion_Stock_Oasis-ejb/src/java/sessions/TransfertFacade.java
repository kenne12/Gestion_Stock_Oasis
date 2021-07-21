package sessions;

import entities.Transfert;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class TransfertFacade extends AbstractFacade<Transfert>
        implements TransfertFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public TransfertFacade() {
        super(Transfert.class);
    }

    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(t.idtransfert) FROM Transfert t");
            Long result = (Long) query.getSingleResult();
            if (result == null) {
                result = (1L);
            } else {
                result = (result + 1L);
            }
            return result;
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public List<Transfert> findByIdMagasinBidirection(int idMagasin) {
        return this.em.createQuery("SELECT t FROM Transfert t WHERE t.idmagasin.idmagasin=:idMagasin OR t.idmagasincible=:idMagasin ORDER BY t.datetransfert DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Transfert> findByIdMagasinSource(int idMagasin) {
        return this.em.createQuery("SELECT t FROM Transfert t WHERE t.idmagasin.idmagasin=:idMagasin ORDER BY t.datetransfert DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Transfert> findByIdMagasinSource(int idMagasin, Date dateDebut, Date dateFin) {
        return this.em.createQuery("SELECT t FROM Transfert t WHERE t.idmagasin.idmagasin=:idMagasin AND t.datetransfert BETWEEN :dateDebut AND :dateFin ORDER BY t.datetransfert DESC")
                .setParameter("idMagasin", idMagasin).setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin)
                .getResultList();
    }

    @Override
    public List<Transfert> findByIdMagasinSource(int idMagasin, Date date) {
        return this.em.createQuery("SELECT t FROM Transfert t WHERE t.idmagasin.idmagasin=:idMagasin AND t.datetransfert =:date ORDER BY t.datetransfert DESC")
                .setParameter("idMagasin", idMagasin).setParameter("date", date)
                .getResultList();
    }

    @Override
    public List<Transfert> findByIdMagasinDestination(int idMagasin) {
        return this.em.createQuery("SELECT t FROM Transfert t WHERE t.idmagasincible=:idMagasin ORDER BY t.datetransfert DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Transfert> findByIdMagasinDestination(int idMagasin, Date dateDebut, Date dateFin) {
        return this.em.createQuery("SELECT t FROM Transfert t WHERE t.idmagasincible=:idMagasin AND t.datetransfert BETWEEN :dateDebut AND :dateFin ORDER BY t.datetransfert DESC")
                .setParameter("idMagasin", idMagasin).setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin)
                .getResultList();
    }

    @Override
    public List<Transfert> findByIdMagasinDestination(int idMagasin, Date date) {
        return this.em.createQuery("SELECT t FROM Transfert t WHERE t.idmagasincible=:idMagasin AND t.datetransfert =:date ORDER BY t.datetransfert DESC")
                .setParameter("idMagasin", idMagasin).setParameter("date", date)
                .getResultList();
    }
}
