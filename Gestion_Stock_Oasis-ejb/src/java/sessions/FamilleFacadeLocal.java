package sessions;

import entities.Famille;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FamilleFacadeLocal {

    public void create(Famille famille);

    public void edit(Famille famille);

    public void remove(Famille famille);

    public Famille find(Object object);

    public List<Famille> findAll();

    public List<Famille> findRange(int[] arrayOfInt);

    public int count();

    public Long nextVal();

    public List<Famille> findAllRange(int idStructure);

    public Long nextValByIdstructure(int idStructure);
}
