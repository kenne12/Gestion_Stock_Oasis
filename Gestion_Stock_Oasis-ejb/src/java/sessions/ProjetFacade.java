package sessions;

import entities.Projet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ProjetFacade extends AbstractFacade<Projet> implements ProjetFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ProjetFacade() {
        super(Projet.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(p.idprojet) FROM Projet p");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = (1);
        } else {
            result = (result + 1);
        }
        return result;
    }

    @Override
    public List<Projet> findAllRange() {
        /* 48 */ Query query = this.em.createQuery("SELECT p FROM Projet p ORDER BY p.nom");
        /* 49 */ return query.getResultList();
    }

    @Override
    public List<Projet> findByIdmagasin(int idmagasin) {
        /* 54 */ Query query = this.em.createQuery("SELECT p FROM Projet p WHERE p.idmagasin.idmagasin=:idmagasin");
        /* 55 */ query.setParameter("idmagasin", (idmagasin));
        /* 56 */ return query.getResultList();
    }
}
