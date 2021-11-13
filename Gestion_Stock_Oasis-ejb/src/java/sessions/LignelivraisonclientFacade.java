package sessions;

import entities.Lignelivraisonclient;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignelivraisonclientFacade extends AbstractFacade<Lignelivraisonclient> implements LignelivraisonclientFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignelivraisonclientFacade() {
        super(Lignelivraisonclient.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignelivraisonclient) FROM Lignelivraisonclient l");
        try {
            return ((Long) query.getResultList().get(0)) + 1l;
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public List<Lignelivraisonclient> findByIdlivraisonclient(long idLivraisonClient) {
        Query query = this.em.createQuery("SELECT l FROM Lignelivraisonclient l WHERE l.idlivraisonclient.idlivraisonclient=:idlivraisonclient");
        query.setParameter("idlivraisonclient", idLivraisonClient);
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonclient> findAllRange() {
        Query query = this.em.createQuery("SELECT l FROM Lignelivraisonclient l ORDER BY l.idlivraisonclient.datelivraison");
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonclient> findByIdMagasin(int idMagasin, Date dateDebut, Date dateFin) {
        Query query = em.createQuery("SELECT l FROM Lignelivraisonclient l WHERE l.idmagasinlot.idmagasinarticle.idmagasin.idmagasin=:idMagasin AND l.idlivraisonclient.datelivraison BETWEEN :dateDebut AND :dateFin");
        query.setParameter("idMagasin", idMagasin).setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin);
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonclient> findByIdArticle(long idarticle) {
        Query query = em.createQuery("SELECT l FROM Lignelivraisonclient l WHERE l.idmagasinlot.idmagasinarticle.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonclient> findByIdLot(long idMagasinLot, Date dateDebut, Date dateFin) {
        Query query = em.createQuery("SELECT l FROM Lignelivraisonclient l WHERE l.idmagasinlot.idmagasinlot=:idLot AND l.idlivraisonclient.datelivraison BETWEEN :dateDebut AND :dateFin");
        query.setParameter("idLot", idMagasinLot).setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin);
        return query.getResultList();
    }
    
    @Override
    public void deleteByIdarticle(long idarticle){
        em.createQuery("DELETE FROM Lignelivraisonclient l WHERE l.idmagasinlot.idmagasinarticle.idarticle.idarticle=:idArticle")
                .setParameter("idArticle", idarticle)
                .executeUpdate();
    }
}
