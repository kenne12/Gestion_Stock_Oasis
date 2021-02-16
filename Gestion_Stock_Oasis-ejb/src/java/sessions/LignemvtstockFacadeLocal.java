package sessions;

import entities.Lignemvtstock;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignemvtstockFacadeLocal {

    public void create(Lignemvtstock paramLignemvtstock);

    public void edit(Lignemvtstock paramLignemvtstock);

    public void remove(Lignemvtstock paramLignemvtstock);

    public Lignemvtstock find(Object paramObject);

    public List<Lignemvtstock> findAll();

    public List<Lignemvtstock> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignemvtstock> findByIdmvt(long idMvt);

    public void deleteByIdmvt(long idMvt);

    public List<Lignemvtstock> findByIdarticleIntevalDate(long paramLong, Date paramDate1, Date paramDate2);

    public List<Lignemvtstock> findByIdmagasinIntevalDate(int idmagasin, Date dateDebut, Date dateFin);

}
