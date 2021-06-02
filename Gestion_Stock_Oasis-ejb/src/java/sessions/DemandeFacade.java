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
        return this.em.createQuery("SELECT d FROM Demande d ORDER BY d.datedemande DESC")
                .getResultList();
    }

    @Override
    public List<Demande> findByValidee(boolean validee) {
        return this.em.createQuery("SELECT d FROM Demande d WHERE d.validee=:validee")
                .setParameter("validee", validee)
                .getResultList();
    }

    @Override
    public List<Demande> findByValidee(int idMagasin, boolean validee) {
        return this.em.createQuery("SELECT d FROM Demande d WHERE d.magasin.idmagasin=:idMagasin AND d.validee=:validee")
                .setParameter("validee", validee).setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Demande> findByIdpersonnelIntervalDate(int idclient, Date dateDebut, Date dateFin) {
        return this.em.createQuery("SELECT d FROM Demande d WHERE d.client.idclient=:idClient AND d.datedemande BETWEEN :date_debut AND :date_fin ORDER BY d.datedemande")
                .setParameter("idClient", idclient).setParameter("date_debut", dateDebut).setParameter("date_fin", dateFin)
                .getResultList();
    }

    @Override
    public List<Demande> findAllRange(int idClient) {
        return em.createQuery("SELECT d FROM Demande d WHERE d.client.idclient=:idClient ORDER BY d.datedemande DESC")
                .setParameter("idClient", idClient)
                .getResultList();
    }

    @Override
    public List<Demande> findByIdMagasin(int idMagasin) {
        return this.em.createQuery("SELECT d FROM Demande d WHERE d.magasin.idmagasin=:idMagasin ORDER BY d.datedemande DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }
}
