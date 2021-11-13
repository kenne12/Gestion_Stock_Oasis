package sessions;

import entities.Lignemvtstock;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignemvtstockFacadeLocal {

    public void create(Lignemvtstock lignemvtstock);

    public void edit(Lignemvtstock lignemvtstock);

    public void remove(Lignemvtstock lignemvtstock);

    public Lignemvtstock find(Object id);

    public List<Lignemvtstock> findAll();

    public List<Lignemvtstock> findRange(int[] range);

    public int count();

    public Long nextVal();

    public List<Lignemvtstock> findByIdmvt(long idMvt);

    public void deleteByIdmvt(long idMvt);

    public List<Lignemvtstock> findByIdarticleIntevalDate(long idarticle, Date dateDebut, Date dateFin);

    public List<Lignemvtstock> findByIdmagasinIntevalDate(int idmagasin, Date dateDebut, Date dateFin);

    public Lignemvtstock findByIdmvtIdLot(long idMvt, long idLot);

    public Lignemvtstock findByIdmvtIdLot(long idMvt, long idLot, long idLlc);
    
    void deleteByIdarticle(long idarticle);
    
    

}
