package sessions;

import entities.Utilisateurmagasin;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UtilisateurmagasinFacade extends AbstractFacade<Utilisateurmagasin> implements UtilisateurmagasinFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public UtilisateurmagasinFacade() {
        super(Utilisateurmagasin.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(u.idutilisateurmagasin) FROM Utilisateurmagasin u");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result += 1L;
        }
        return result;
    }

    @Override
    public List<Utilisateurmagasin> findByIdutilisateur(int idutilisateur) {
        Query query = this.em.createQuery("SELECT u FROM Utilisateurmagasin u WHERE u.idutilisateur.idutilisateur=:idutilisateur ORDER BY u.idmagasin.nom");
        query.setParameter("idutilisateur", idutilisateur);
        return query.getResultList();
    }

    @Override
    public Utilisateurmagasin findByIdutilisateurIdmagasin(int idutilisateur, int idmagasin) {
        Query query = this.em.createQuery("SELECT u FROM Utilisateurmagasin u WHERE u.idutilisateur.idutilisateur=:idutilisateur AND u.idmagasin.idmagasin=:idmagasin");
        query.setParameter("idutilisateur", idutilisateur).setParameter("idmagasin", idmagasin);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Utilisateurmagasin) list.get(0);
        }
        return null;
    }

    @Override
    public void deleteByIdutilisateur(int idutilisateur) {
        Query query = em.createQuery("DELETE FROM Utilisateurmagasin u WHERE u.idutilisateur.idutilisateur=:idutilisateur");
        query.setParameter("idutilisateur", idutilisateur);
        query.executeUpdate();
    }
}
