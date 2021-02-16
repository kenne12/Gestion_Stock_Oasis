package sessions;

import entities.Mvtstock;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MvtstockFacadeLocal {

    public void create(Mvtstock paramMvtstock);

    public void edit(Mvtstock paramMvtstock);

    public void remove(Mvtstock paramMvtstock);

    public Mvtstock find(Object paramObject);

    public List<Mvtstock> findAll();

    public List<Mvtstock> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();
}
