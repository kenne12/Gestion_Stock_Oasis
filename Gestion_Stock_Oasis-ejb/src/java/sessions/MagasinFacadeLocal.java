package sessions;

import entities.Magasin;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MagasinFacadeLocal {

    public void create(Magasin magasin);

    public void edit(Magasin magasin);

    public void remove(Magasin magasin);

    public Magasin find(Object id);

    public List<Magasin> findAll();

    public List<Magasin> findRange(int[] range);

    public int count();

    public Integer nextVal();

    public List<Magasin> findAllRange();

    public List<Magasin> findAllRangeMcIsFalse();
}
