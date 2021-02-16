package sessions;

import entities.Lignemvtstock;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignemvtstockFacade extends AbstractFacade<Lignemvtstock> implements LignemvtstockFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignemvtstockFacade() {
        super(Lignemvtstock.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignemvtstock) FROM Lignemvtstock l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Lignemvtstock> findByIdmvt(long idMvt) {
        Query query = this.em.createQuery("SELECT l FROM Lignemvtstock l WHERE l.idmvtstock.idmvtstock=:idMvt");
        query.setParameter("idMvt", idMvt);
        return query.getResultList();
    }

    @Override
    public void deleteByIdmvt(long idMvt) {
        Query query = this.em.createQuery("DELETE FROM Lignemvtstock l WHERE l.idmvtstock.idmvtstock=:idMvt");
        query.setParameter("idMvt", idMvt);
        query.executeUpdate();
    }

    @Override
    public List<Lignemvtstock> findByIdarticleIntevalDate(long idarticle, Date dateDebut, Date dateFin) {
        Query query = this.em.createQuery("SELECT l FROM Lignemvtstock l WHERE l.idlot.idarticle.idarticle=:idarticle AND l.idmvtstock.datemvt BETWEEN :date_debut AND :date_fin");
        query.setParameter("idarticle", idarticle).setParameter("date_debut", dateDebut).setParameter("date_fin", dateFin);
        return query.getResultList();
    }

    @Override
    public List<Lignemvtstock> findByIdmagasinIntevalDate(int idmagasin, Date dateDebut, Date dateFin) {
        Query query = this.em.createQuery("SELECT l FROM Lignemvtstock l WHERE l.idmagasinlot.idmagasinarticle.idmagasin.idmagasin=:idmagasin AND l.idmvtstock.datemvt BETWEEN :date_debut AND :date_fin ORDER BY l.idmvtstock.datemvt");
        query.setParameter("idmagasin", idmagasin).setParameter("date_debut", dateDebut).setParameter("date_fin", dateFin);
        return query.getResultList();
    }
}
