package sessions;

import entities.Demande;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class DemandeFacade extends AbstractFacade<Demande> implements DemandeFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public DemandeFacade() {
        super(Demande.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(d.iddemande) FROM Demande d");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Demande> findAllRange() {
        Query query = this.em.createQuery("SELECT d FROM Demande d ORDER BY d.datedemande DESC");
        return query.getResultList();
    }

    @Override
    public List<Demande> findByValidee(boolean validee) {
        Query query = this.em.createQuery("SELECT d FROM Demande d WHERE d.validee=:validee");
        query.setParameter("validee", validee);
        return query.getResultList();
    }

    @Override
    public List<Demande> findByIdpersonnelIntervalDate(long idpersonnel, Date dateDebut, Date dateFin) {
        Query query = this.em.createQuery("SELECT d FROM Demande d WHERE d.idpersonnel.idpersonnel=:idpersonnel AND d.datedemande BETWEEN :date_debut AND :date_fin ORDER BY d.datedemande");
        query.setParameter("idpersonnel", idpersonnel).setParameter("date_debut", dateDebut).setParameter("date_fin", dateFin);
        return query.getResultList();
    }

    @Override
    public List<Demande> findAllRange(long idpersonnel) {
        Query query = em.createQuery("SELECT d FROM Demande d WHERE d.idpersonnel.idpersonnel=:idpersonnel ORDER BY d.datedemande DESC");
        query.setParameter("idpersonnel", idpersonnel);
        return query.getResultList();
    }
}
