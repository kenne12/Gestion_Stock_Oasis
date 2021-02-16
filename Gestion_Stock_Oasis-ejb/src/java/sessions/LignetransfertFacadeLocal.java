package sessions;

import entities.Lignetransfert;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignetransfertFacadeLocal {

    public void create(Lignetransfert paramLignetransfert);

    public void edit(Lignetransfert paramLignetransfert);

    public void remove(Lignetransfert paramLignetransfert);

    public Lignetransfert find(Object paramObject);

    public List<Lignetransfert> findAll();

    public List<Lignetransfert> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignetransfert> findByIdTransfert(long paramLong);

    List<Lignetransfert> findByIdarticle(long idarticle);

}
