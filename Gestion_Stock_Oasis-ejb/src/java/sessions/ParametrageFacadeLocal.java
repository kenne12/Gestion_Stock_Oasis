package sessions;

import entities.Parametrage;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ParametrageFacadeLocal {

    public void create(Parametrage paramParametrage);

    public void edit(Parametrage paramParametrage);

    public void remove(Parametrage paramParametrage);

    public Parametrage find(Object paramObject);

    public List<Parametrage> findAll();

    public List<Parametrage> findRange(int[] paramArrayOfInt);

    public int count();
}
