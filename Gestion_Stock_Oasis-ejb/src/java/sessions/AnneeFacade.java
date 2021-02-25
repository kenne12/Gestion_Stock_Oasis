package sessions;

import entities.Annee;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AnneeFacade extends AbstractFacade<Annee> implements AnneeFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public AnneeFacade() {
        super(Annee.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(a.idannee) FROM Annee a");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = (1);
        } else {
            result = result + 1;
        }
        return result;
    }

    @Override
    public List<Annee> findByEtat(boolean etat) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM Annee a WHERE a.etat=:etat ORDER BY a.nom");
        query.setParameter("etat", etat);
        return query.getResultList();
    }

    @Override
    public List<Annee> findByRange() {
        Query query = this.em.createQuery("SELECT a FROM Annee a  ORDER BY a.nom");
        return query.getResultList();
    }

    @Override
    public Annee findByCode(String nom) {
        Query query = this.em.createQuery("SELECT a FROM Annee a WHERE a.nom=:nom");
        query.setParameter("nom", nom);
        List<Annee> annees = query.getResultList();
        if (!annees.isEmpty()) {
            return annees.get(0);
        }
        return null;
    }
    
    @Override
    public Annee findOneActive() {
        Query query = this.em.createQuery("SELECT a FROM Annee a WHERE a.etat=true ORDER BY a.nom");
        List<Annee> annees = query.getResultList();
        if (!annees.isEmpty()) {
            return annees.get(0);
        }
        return null;
    }
}
