package sessions;

import entities.AnneeMois;
import entities.Livraisonfournisseur;
import java.util.Date;
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
        try {
            Query query = this.em.createQuery("SELECT MAX(l.idlivraisonfournisseur) FROM Livraisonfournisseur l");
            return ((Long) query.getResultList().get(0)) + 1;
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public Long nextVal(int idMagasin, AnneeMois anneeMois) {
        try {
            Query query = this.em.createQuery("SELECT COUNT(l.idlivraisonfournisseur) FROM Livraisonfournisseur l WHERE l.datelivraison BETWEEN :dateDebut AND :dateFin AND l.idmagasin.idmagasin=:idMagasin");
            query.setParameter("dateDebut", anneeMois.getDateDebut()).setParameter("dateFin", anneeMois.getDateFin()).setParameter("idMagasin", idMagasin);
            List list = query.getResultList();
            if (list != null) {
                return ((Long) list.get(0)) + 1;
            } else {
                return 1L;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 1L;
        }
    }

    @Override
    public List<Livraisonfournisseur> findAllRange(int idMagasin) {
        return this.em.createQuery("SELECT l FROM Livraisonfournisseur l ORDER BY l.idlivraisonfournisseur DESC, l.datelivraison DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Livraisonfournisseur> findAllRange(int idMagasin, boolean livraisonDirecte) {
        return this.em.createQuery("SELECT l FROM Livraisonfournisseur l WHERE l.livraisondirecte=:livraisonDirecte AND l.idmagasin.idmagasin=:idMagasin ORDER BY l.datelivraison DESC")
                .setParameter("livraisonDirecte", livraisonDirecte).setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Livraisonfournisseur> findAllRange(int idMagasin, Date dateDebut, Date dateFin, boolean livraisonDirecte) {
        return this.em.createQuery("SELECT l FROM Livraisonfournisseur l WHERE l.idmagasin.idmagasin=:idMagasin AND l.datelivraison BETWEEN :dateDebut AND :dateFin AND l.livraisondirecte=:livraisonDirecte ORDER BY l.idlivraisonfournisseur DESC , l.datelivraison DESC")
                .setParameter("idMagasin", idMagasin)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .setParameter("livraisonDirecte", livraisonDirecte)
                .getResultList();
    }

    @Override
    public List<Livraisonfournisseur> findAllRange(int idMagasin, Date dateDebut, Date dateFin) {
        return this.em.createQuery("SELECT l FROM Livraisonfournisseur l WHERE l.idmagasin.idmagasin=:idMagasin AND l.datelivraison BETWEEN :dateDebut AND :dateFin ORDER BY l.datelivraison DESC")
                .setParameter("idMagasin", idMagasin)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

    @Override
    public List<Livraisonfournisseur> findAllRange(int idMagasin, Date date) {
        return this.em.createQuery("SELECT l FROM Livraisonfournisseur l WHERE l.idmagasin.idmagasin=:idMagasin AND l.datelivraison=:date ORDER BY l.datelivraison DESC , l.idcommandefournisseur DESC")
                .setParameter("idMagasin", idMagasin)
                .setParameter("date", date)
                .getResultList();
    }
}
