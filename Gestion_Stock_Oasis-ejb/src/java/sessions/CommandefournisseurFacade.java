package sessions;

import entities.AnneeMois;
import entities.Commandefournisseur;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CommandefournisseurFacade extends AbstractFacade<Commandefournisseur> implements CommandefournisseurFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public CommandefournisseurFacade() {
        super(Commandefournisseur.class);
    }

    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(c.idcommandefournisseur) FROM Commandefournisseur c");
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return 1L;
            } else {
                return ((long) list.get(0) + 1);
            }
        } catch (Exception e) {
            return 1L;
        }

    }

    @Override
    public Long nextVal(int idMagasin, AnneeMois anneeMois) {
        try {
            Query query = this.em.createQuery("SELECT COUNT(c.idcommandefournisseur) FROM Commandefournisseur c WHERE c.datecommande BETWEEN :dateDebut AND :dateFin AND c.magasin.idmagasin=:idMagasin");
            query.setParameter("dateDebut", anneeMois.getDateDebut()).setParameter("dateFin", anneeMois.getDateFin()).setParameter("idMagasin", idMagasin);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return ((Long) list.get(0)) + 1;
            }
            return 1L;
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public List<Commandefournisseur> findAllRange(int idMagasin) {
        return this.em.createQuery("SELECT c FROM Commandefournisseur c WHERE c.magasin.idmagasin=:idMagasin ORDER BY c.idcommandefournisseur DESC, c.datecommande")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Commandefournisseur> findAllRange(int idMagasin, Date dateDebut, Date dateFin) {
        return this.em.createQuery("SELECT c FROM Commandefournisseur c WHERE c.magasin.idmagasin=:idMagasin AND c.datecommande BETWEEN :dateDebut AND :dateFin ORDER BY c.idcommandefournisseur DESC, c.datecommande")
                .setParameter("idMagasin", idMagasin).setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin)
                .getResultList();
    }

    @Override
    public List<Commandefournisseur> findByLivre(int idMagasin, boolean livree) {
        Query query = this.em.createQuery("SELECT c FROM Commandefournisseur c WHERE c.magasin.idmagasin=:idMagasin AND c.livre=:livree ORDER BY c.idcommandefournisseur DESC");
        query.setParameter("livree", livree).setParameter("idMagasin", idMagasin);
        return query.getResultList();
    }
}
