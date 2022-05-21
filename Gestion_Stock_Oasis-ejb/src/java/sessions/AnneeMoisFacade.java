package sessions;

import entities.AnneeMois;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AnneeMoisFacade extends AbstractFacade<AnneeMois> implements AnneeMoisFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public AnneeMoisFacade() {
        super(AnneeMois.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(a.idAnneeMois) FROM AnneeMois a");
        try {
            return (Integer) query.getSingleResult() + 1;
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<AnneeMois> findByAnneeEtat(int idAnnee, boolean etat) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM AnneeMois a WHERE a.idannee.idannee=:annee AND a.etat=:etat ORDER BY a.idmois.idmois");
        query.setParameter("annee", idAnnee).setParameter("etat", etat);
        return query.getResultList();
    }

    @Override
    public List<AnneeMois> findByAnnee(int idAnnee) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM AnneeMois a WHERE a.idannee.idannee=:annee ORDER BY a.idmois.idmois");
        query.setParameter("annee", idAnnee);
        return query.getResultList();
    }

    @Override
    public List<AnneeMois> findByEtat(Boolean etat) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM AnneeMois a WHERE a.etat=:etat ORDER BY a.idmois.idmois");
        query.setParameter("etat", etat);
        return query.getResultList();
    }

    @Override
    public List<AnneeMois> findByRange() throws Exception {
        Query query = this.em.createQuery("SELECT a FROM AnneeMois a ORDER BY a.idannee.nom DESC , a.idmois.numero ASC");
        return query.getResultList();
    }

    @Override
    public AnneeMois findByDate(Date date) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM AnneeMois a WHERE a.dateDebut>=:date AND a.dateFin<=:date");
        query.setParameter("date", date);
        List<AnneeMois> list = query.getResultList();
        if (!list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public AnneeMois findDefaultMonthByIdannee(int idAnnee) {
        List list = this.em.createQuery("SELECT a FROM AnneeMois a WHERE a.idannee.idannee=:idAnnee ORDER BY a.idmois.numero")
                .setParameter("idAnnee", idAnnee)
                .getResultList();

        if (!list.isEmpty()) {
            return (AnneeMois) list.get(0);
        }
        return null;
    }
}
