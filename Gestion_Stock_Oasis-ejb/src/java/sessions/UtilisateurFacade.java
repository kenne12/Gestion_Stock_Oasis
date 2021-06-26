package sessions;

import entities.Utilisateur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UtilisateurFacade extends AbstractFacade<Utilisateur> implements UtilisateurFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public UtilisateurFacade() {
        super(Utilisateur.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(u.idutilisateur) FROM Utilisateur u");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = 1;
        } else {
            result += 1;
        }
        return result;
    }

    @Override
    public List<Utilisateur> findByIdStructure(int idStructure) {
        return em.createQuery("SELECT u FROM Utilisateur u WHERE u.idpersonnel.idmagasin.parametrage.id=:id ORDER BY u.idpersonnel.nom")
                .setParameter("id", idStructure)
                .getResultList();
    }

    @Override
    public Utilisateur login(String login) {
        List list = this.em.createQuery("SELECT u FROM Utilisateur U WHERE u.login=:login ")
                .setParameter("login", login)
                .getResultList();
        if (!list.isEmpty()) {
            return (Utilisateur) list.get(0);
        }
        return null;
    }

    @Override
    public Utilisateur login(String login, String password) throws Exception {
        List list = this.em.createQuery("SELECT u FROM Utilisateur U WHERE u.login=:login AND u.password=:password")
                .setParameter("login", login).setParameter("password", password)
                .getResultList();
        if (!list.isEmpty()) {
            return (Utilisateur) list.get(0);
        }
        return null;
    }

    @Override
    public List<Utilisateur> findByIdStructureEtat(int idStructure, boolean actif) {
        return this.em.createQuery("SELECT u FROM Utilisateur u WHERE u.idpersonnel.idmagasin.parametrage.id=:id AND u.actif=:actif ORDER BY u.idpersonnel.nom")
                .setParameter("actif", actif)
                .setParameter("id", idStructure)
                .getResultList();
    }

}
