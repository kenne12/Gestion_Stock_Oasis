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
        try {
            Query query = this.em.createQuery("SELECT MAX(a.idannee) FROM Annee a");
            Integer result = (Integer) query.getSingleResult();
            return result == null ? 1 : (result+1);
        } catch (Exception e) {
            return 1;
        }
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
        List annees = this.em.createQuery("SELECT a FROM Annee a WHERE a.nom=:nom")
                .setParameter("nom", nom)
                .getResultList();

        if (!annees.isEmpty()) {
            return (Annee) annees.get(0);
        }
        return null;
    }

    @Override
    public Annee findOneActive() {
        List annees = this.em.createQuery("SELECT a FROM Annee a WHERE a.etat=true ORDER BY a.nom")
                .getResultList();
        if (!annees.isEmpty()) {
            return (Annee) annees.get(0);
        }
        return null;
    }

    @Override
    public Annee findOneDefault() {
        List annees = this.em.createQuery("SELECT a FROM Annee a WHERE a.defaultYear=true ORDER BY a.nom DESC")
                .getResultList();
        if (!annees.isEmpty()) {
            return (Annee) annees.get(0);
        }
        return null;
    }

    @Override
    public void unsetDefaultForOtherYear(Integer idannee) {
        this.em.createQuery("UPDATE Annee a set a.defaultYear = false WHERE a.idannee!= :idannee")
                .setParameter("idannee", idannee)
                .executeUpdate();
    }
}
