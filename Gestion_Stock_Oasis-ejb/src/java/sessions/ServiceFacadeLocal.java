package sessions;

import entities.Service;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ServiceFacadeLocal {

    public void create(Service paramService);

    public void edit(Service paramService);

    public void remove(Service paramService);

    public Service find(Object paramObject);

    public List<Service> findAll();

    public List<Service> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Service> findAllRange();
}
