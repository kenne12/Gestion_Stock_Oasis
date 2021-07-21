/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Versement;
import java.time.LocalDate;
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
public class VersementFacade extends AbstractFacade<Versement> implements VersementFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VersementFacade() {
        super(Versement.class);
    }

    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(v.idversement) FROM Versement v");
            Long result = (Long) query.getSingleResult();
            if (result == null) {
                result = 1L;
            } else {
                result = result + 1L;
            }
            return result;
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public List<Versement> findByIdmagasin(int idMagasin) {
        return em.createQuery("SELECT v FROM Versement v WHERE v.livraisonclient.idmagasin.idmagasin=:idMagasin ORDER BY v.dateOperation DESC")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Versement> findByIdmagasinTwodates(int idMagasin, LocalDate dateDebut, LocalDate dateFin) {
        return em.createQuery("SELECT v FROM Versement v WHERE v.livraisonclient.idmagasin.idmagasin=:idMagasin AND v.dateOperation BETWEEN :dateDebut AND :dateFin ORDER BY v.dateOperation DESC")
                .setParameter("idMagasin", idMagasin)
                .setParameter("dateDebut", dateDebut)
                .setParameter("dateFin", dateFin)
                .getResultList();
    }

    @Override
    public Long nextVal(int idMagasin, Date dateDebut, Date dateFin) {
        Query query = this.em.createQuery("SELECT COUNT(v.idversement) FROM Versement v WHERE v.livraisonclient.idmagasin.idmagasin=:idMagasin AND v.dateOperation BETWEEN :dateDebut AND :dateFin")
                .setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin).setParameter("idMagasin", idMagasin);

        List list = query.getResultList();
        if (list.isEmpty()) {
            return 1L;
        } else {
            return ((long) list.get(0)) + 1L;
        }
    }

    @Override
    public Long nextVal(int idMagasin) {
        Query query = this.em.createQuery("SELECT MAX(v.idversement) FROM Versement v WHERE v.livraisonclient.idmagasin.idmagasin=:idMagasin")
                .setParameter("idMagasin", idMagasin);
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result += 1L;
        }
        return result;
    }

}
