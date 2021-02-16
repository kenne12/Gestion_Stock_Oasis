package sessions;

import entities.Lignecommandefournisseur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignecommandefournisseurFacade extends AbstractFacade<Lignecommandefournisseur> implements LignecommandefournisseurFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignecommandefournisseurFacade() {
        super(Lignecommandefournisseur.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignecommandefournisseur) FROM Lignecommandefournisseur l");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1l;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Lignecommandefournisseur> findAllRange() {
        return this.em.createQuery("SELECT l FROM Lignecommandefournisseur l").getResultList();
    }

    @Override
    public List<Lignecommandefournisseur> findByCommande(long idcommande) {
        Query query = this.em.createQuery("SELECT l FROM Lignecommandefournisseur l WHERE l.idcommandefournisseur.idcommandefournisseur=:idcommande");
        query.setParameter("idcommande", idcommande);
        return query.getResultList();
    }
}
