package sessions;

import entities.Entredirect;
import java.util.List;
import javax.ejb.Local;

@Local
public interface EntredirectFacadeLocal {

    public void create(Entredirect paramEntredirect);

    public void edit(Entredirect paramEntredirect);

    public void remove(Entredirect paramEntredirect);

    public Entredirect find(Object paramObject);

    public List<Entredirect> findAll();

    public List<Entredirect> findRange(int[] paramArrayOfInt);

    public int count();
}
