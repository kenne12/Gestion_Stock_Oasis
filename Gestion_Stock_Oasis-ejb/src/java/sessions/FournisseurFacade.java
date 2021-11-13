package sessions;

import entities.Fournisseur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class FournisseurFacade extends AbstractFacade<Fournisseur> implements FournisseurFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public FournisseurFacade() {
        super(Fournisseur.class);
    }

    @Override
    public Integer nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(f.idfournisseur) FROM Fournisseur f");
            return ((Integer) query.getResultList().get(0) + 1);
        } catch (Exception e) {
            return 1;
        }
    }

    @Override
    public List<Fournisseur> findAllRange(int idMagasin) {
        return this.em.createQuery("SELECT f FROM Fournisseur f WHERE f.magasin.idmagasin=:idMagasin ORDER BY f.nom")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }

    @Override
    public List<Fournisseur> findByIdstructure(int idStructure) {
        return this.em.createQuery("SELECT f FROM Fournisseur f WHERE f.magasin.parametrage.id=:idStructure ORDER BY f.nom")
                .setParameter("idStructure", idStructure)
                .getResultList();
    }
}
