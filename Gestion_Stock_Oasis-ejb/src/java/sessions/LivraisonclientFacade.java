package sessions;

import entities.AnneeMois;
import entities.Livraisonclient;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LivraisonclientFacade extends AbstractFacade<Livraisonclient> implements LivraisonclientFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LivraisonclientFacade() {
        super(Livraisonclient.class);
    }

    /*@Override
     public Long nextVal2(){
     Query query =  em.createQuery("SELECT l.idlivraisonclient, MAX(l.idlivraisonclient) FROM Livraisonclient l GROUP BY l.idlivraisonclient", Object[].class);
      
     if(query.getResultList().isEmpty()){
     return 1L;
     }
     Long result = 0l;
     query.getResultList().stream()
     .forEach(e -> {
              
     result = (long) e[1];
            
     });
     }*/
    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlivraisonclient) FROM Livraisonclient l");
        List list = query.getResultList();
        if (list.isEmpty()) {
            return 1L;
        } else {
            return (Long) list.get(0) + 1;
        }
    }

    @Override
    public Long nextVal(int idMagasin, AnneeMois anneeMois) {
        try {
            Query query = this.em.createQuery("SELECT COUNT(l.idlivraisonclient) FROM Livraisonclient l WHERE l.datelivraison BETWEEN :dateDebut AND :dateFin AND l.idmagasin.idmagasin=:idMagasin");
            query.setParameter("dateDebut", anneeMois.getDateDebut()).setParameter("dateFin", anneeMois.getDateFin()).setParameter("idMagasin", idMagasin);
            List list = query.getResultList();
            if (list.isEmpty()) {
                return 1L;
            } else {
                return (Long) list.get(0) + 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1L;
        }
    }

    @Override
    public List<Livraisonclient> findAllRange() {
        return this.em.createQuery("SELECT l FROM Livraisonclient l ORDER BY l.idlivraisonclient DESC").getResultList();
    }

    @Override
    public Livraisonclient findByIddemande(long iddemande) {
        List list = em.createQuery("SELECT l FROM Livraisonclient l WHERE l.iddemande.iddemande=:iddemande ORDER BY l.idlivraisonclient DESC")
                .setParameter("iddemande", iddemande)
                .getResultList();
        if (!list.isEmpty()) {
            return (Livraisonclient) list.get(0);
        }
        return null;
    }

    @Override
    public List<Livraisonclient> findAllRange(int idMagasin, Date dateDebut, Date dateFin) {
        return this.em.createQuery("SELECT l FROM Livraisonclient l WHERE l.datelivraison BETWEEN :dateDebut AND :dateFin AND l.idmagasin.idmagasin=:idMagasin ORDER BY l.idlivraisonclient DESC , l.datelivraison DESC")
                .setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin).setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Livraisonclient> findByIdmagasinNonRegle(int idMagasin) {
        return this.em.createQuery("SELECT l FROM Livraisonclient l WHERE l.idmagasin.idmagasin=:idMagasin  AND l.modePayement=:modePayement AND l.montantPaye < l.montantTtc  ORDER BY l.idlivraisonclient DESC")
                .setParameter("idMagasin", idMagasin)
                .setParameter("modePayement", "PAYE_A_CREDIT")
                .getResultList();
    }

    @Override
    public List<Livraisonclient> findByIdmagasin(int idMagasin) {
        return this.em.createQuery("SELECT l FROM Livraisonclient l WHERE l.idmagasin.idmagasin=:idMagasin ORDER BY l.idlivraisonclient DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Livraisonclient> findByIdmagasinAndDate(int idMagasin, Date date) {
        return this.em.createQuery("SELECT l FROM Livraisonclient l WHERE l.datelivraison  =:date AND l.idmagasin.idmagasin=:idMagasin ORDER BY l.idlivraisonclient DESC")
                .setParameter("date", date)
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Livraisonclient> findByIdmagasinAndIdclientAndDate(int idMagasin, int idClient, Date date) {
        return this.em.createQuery("SELECT l FROM Livraisonclient l WHERE l.datelivraison  =:date AND l.idmagasin.idmagasin=:idMagasin AND l.client.idclient=:idClient ORDER BY l.idlivraisonclient DESC")
                .setParameter("date", date)
                .setParameter("idMagasin", idMagasin)
                .setParameter("idClient", idClient)
                .getResultList();
    }

    @Override
    public List<Livraisonclient> findByIdmagasinAndIdclientTwoDates(int idMagasin, int idClient, Date dateDebut, Date dateFin) {
        return this.em.createQuery("SELECT l FROM Livraisonclient l WHERE l.datelivraison BETWEEN  :dateDebut AND :dateFin AND l.idmagasin.idmagasin=:idMagasin AND l.client.idclient=:idClient ORDER BY l.idlivraisonclient DESC")
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .setParameter("idMagasin", idMagasin)
                .setParameter("idClient", idClient)
                .getResultList();
    }

}
