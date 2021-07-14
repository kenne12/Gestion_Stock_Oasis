package sessions;

import entities.Unite;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class UniteFacade extends AbstractFacade<Unite> implements UniteFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public UniteFacade() {
        super(Unite.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(u.idunite) FROM Unite u");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result += 1L;
        }
        return result;
    }

    @Override
    public List<Unite> findAllRange(int idMagasin) {
        return this.em.createQuery("SELECT u FROM Unite u WHERE u.magasin.idmagasin=:idMagasin ORDER BY u.libelle")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }
    
    @Override
    public List<Unite> findByStructure(int idStructure) {
        return this.em.createQuery("SELECT u FROM Unite u WHERE u.magasin.parametrage.id=:idStructure ORDER BY u.libelle")
                .setParameter("idStructure", idStructure)
                .getResultList();
    }
    
    @Override
    public Long nextValByIdstructure(int idStructure) {
        Query query = this.em.createQuery("SELECT MAX(u.idunite) FROM Unite u WHERE u.magasin.parametrage.id=:idParametre")
                .setParameter("idParametre", idStructure);
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }
}
