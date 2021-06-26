package sessions;

import entities.Personnel;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersonnelFacade extends AbstractFacade<Personnel> implements PersonnelFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public PersonnelFacade() {
        super(Personnel.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(p.idpersonnel) FROM Personnel p");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result += 1L;
        }
        return result;
    }

    @Override
    public List<Personnel> findAllRange(int idMagasin) {
        return this.em.createQuery("SELECT p FROM Personnel p WHERE p.idmagasin.idmagasin=:idMagasin ORDER BY p.nom , p.prenom")
                .setParameter("idMagasin", idMagasin)
                .getResultList();
    }
    
    
    @Override
    public List<Personnel> findByIdStructure(int idStructure) {
        return this.em.createQuery("SELECT p FROM Personnel p WHERE p.idmagasin.parametrage.id=:idStructure ORDER BY p.nom , p.prenom")
                .setParameter("idStructure", idStructure)
                .getResultList();
    }
}
