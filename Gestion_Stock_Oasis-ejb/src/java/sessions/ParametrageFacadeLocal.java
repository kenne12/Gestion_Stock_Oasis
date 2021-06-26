package sessions;

import entities.Parametrage;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ParametrageFacadeLocal {

    public void create(Parametrage parametrage);

    public void edit(Parametrage parametrage);

    public void remove(Parametrage parametrage);

    public Parametrage find(Object object);

    public List<Parametrage> findAll();

    public List<Parametrage> findRange(int[] arrayOfInt);

    public int count();

    Integer nextVal();

    List<Parametrage> findAllRange();
}
