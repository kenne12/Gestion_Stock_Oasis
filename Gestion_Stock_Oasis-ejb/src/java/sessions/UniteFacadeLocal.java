package sessions;

import entities.Unite;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface UniteFacadeLocal {

    public abstract void create(Unite unite);

    public abstract void edit(Unite unite);

    public abstract void remove(Unite unite);

    public abstract Unite find(Object object);

    public abstract List<Unite> findAll();

    public abstract List<Unite> findRange(int[] paramArrayOfInt);

    public abstract int count();

    public abstract Long nextVal();

    public List<Unite> findAllRange(int idMagasin);

    public List<Unite> findByStructure(int idStructure);

    public Long nextValByIdstructure(int idStructure);
}
