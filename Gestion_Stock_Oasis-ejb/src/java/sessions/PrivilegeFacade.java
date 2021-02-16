package sessions;

import entities.Privilege;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PrivilegeFacade extends AbstractFacade<Privilege> implements PrivilegeFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public PrivilegeFacade() {
        super(Privilege.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(p.idprivilege) FROM Privilege p");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1l;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Privilege> findAll1() {
        List privileges = null;
        Query query = this.em.createQuery("SELECT p FROM Privilege p ORDER BY p.idutilisateur.nom, p.idutilisateur.prenom,p.idmenu.idmenu DESC");
        privileges = query.getResultList();
        return privileges;
    }

    @Override
    public List<Privilege> findByUser(int utilisateur) {
        List privileges = null;
        Query query = this.em.createQuery("SELECT p FROM Privilege p WHERE p.idutilisateur.idutilisateur=:utilisateur");
        query.setParameter("utilisateur", utilisateur);
        privileges = query.getResultList();
        return privileges;
    }

    @Override
    public Privilege findByUser(int utilisateur, int idmenu) {
        Query query = this.em.createQuery("SELECT p FROM Privilege p WHERE p.idutilisateur.idutilisateur=:utilisateur AND p.idmenu.idmenu=:menu");
        query.setParameter("utilisateur", utilisateur).setParameter("menu", (idmenu));
        if (!query.getResultList().isEmpty()) {
            return (Privilege) query.getResultList().get(0);
        }
        return null;
    }

    @Override
    public void delete(int idmenu, int utilisateur) {
        try {
            Query query = this.em.createQuery("DELETE FROM Privilege p WHERE p.idmenu.idmenu=:menu AND p.idutilisateur.idutilisateur=:utilisateur");
            query.setParameter("menu", idmenu).setParameter("utilisateur", utilisateur);
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteByIdUtilisateur(int idutilisateur) {
        Query query = this.em.createQuery("DELETE FROM Privilege p WHERE p.idutilisateur.idutilisateur=:idutilisateur");
        query.setParameter("idutilisateur", idutilisateur);
        query.executeUpdate();
    }
}
