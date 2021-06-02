/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Journee;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
@Stateless
public class JourneeFacade extends AbstractFacade<Journee> implements JourneeFacadeLocal {
    
    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
    
    public JourneeFacade() {
        super(Journee.class);
    }
    
    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(j.idjournee) FROM Journee j");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result += 1L;
        }
        return result;
    }
    
    @Override
    public List<Journee> findByInterval(Date dateDebut, Date dateFin) {
        return em.createQuery("SELECT j FROM Journee j WHERE j.dateJour BETWEEN :dateDebut AND :dateFin ORDER BY j.dateJour")
                .setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin)
                .getResultList();
    }
    
    @Override
    public List<Journee> findByIdMois(int idMois) {
        return em.createQuery("SELECT j FROM Journee j WHERE j.anneeMois.idAnneeMois=:idMois ORDER BY j.dateJour")
                .setParameter("idMois", idMois)
                .getResultList();
    }
    
    @Override
    public Journee findByIdMagasinDate(int idMagasin, Date date) {
        List<Journee> list = em.createQuery("SELECT j FROM Journee j WHERE j.magasin.idmagasin=:idMagasin AND j.dateJour=:dateJour")
                .setParameter("idMagasin", idMagasin).setParameter("dateJour", date)
                .getResultList();
        return list.isEmpty() == false ? list.get(0) : null;
    }
    
}
