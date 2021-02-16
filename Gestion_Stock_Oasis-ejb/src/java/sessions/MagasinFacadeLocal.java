package sessions;

import entities.Magasin;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MagasinFacadeLocal {

    public void create(Magasin paramMagasin);

    public void edit(Magasin paramMagasin);

    public void remove(Magasin paramMagasin);

    public Magasin find(Object paramObject);

    public List<Magasin> findAll();

    public List<Magasin> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Magasin> findAllRange();

    public List<Magasin> findAllRangeMcIsFalse();
}
