package sessions;

import entities.Mvtstock;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MvtstockFacade extends AbstractFacade<Mvtstock> implements MvtstockFacadeLocal {
    
    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;
    
    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }
    
    public MvtstockFacade() {
        super(Mvtstock.class);
    }
    
    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(m.idmvtstock) FROM Mvtstock m");
            return ((Long) query.getResultList().get(0) + 1);
        } catch (Exception e) {
            return 1L;
        }
    }
}
