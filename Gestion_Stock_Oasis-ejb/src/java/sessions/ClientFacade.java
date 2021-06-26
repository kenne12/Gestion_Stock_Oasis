/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Client;
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
public class ClientFacade extends AbstractFacade<Client> implements ClientFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ClientFacade() {
        super(Client.class);
    }

    @Override
    public Integer nextVal() {
        Query query = this.em.createQuery("SELECT MAX(c.idclient) FROM Client c");
        Integer result = (Integer) query.getSingleResult();
        if (result == null) {
            result = (1);
        } else {
            result = (result + 1);
        }
        return result;
    }

    @Override
    public List<Client> findAllRange(int idMagasin) {
        return this.em.createQuery("SELECT c FROM Client c WHERE c.magasin.idmagasin=:idMagasin ORDER BY c.nom")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Client> findAllRange(int idMagasin, boolean etat) {
        return this.em.createQuery("SELECT c FROM Client c WHERE c.etat=:etat AND c.magasin.idmagasin=:idMagasin ORDER BY c.nom")
                .setParameter("etat", etat).setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Client> findByIdstructure(int idStructure, boolean etat) {
        return em.createQuery("SELECT c FROM Client c WHERE c.etat=:etat AND c.magasin.parametrage.id=:idStructure ORDER BY c.nom")
                .setParameter("etat", etat).setParameter("idStructure", idStructure)
                .getResultList();
    }
}
