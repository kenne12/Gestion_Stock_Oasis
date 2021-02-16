package sessions;

import entities.Ligneinventaire;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LigneinventaireFacadeLocal {

    public void create(Ligneinventaire paramLigneinventaire);

    public void edit(Ligneinventaire paramLigneinventaire);

    public void remove(Ligneinventaire paramLigneinventaire);

    public Ligneinventaire find(Object paramObject);

    public List<Ligneinventaire> findAll();

    public List<Ligneinventaire> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Ligneinventaire> findByIdInventaire(long paramLong);

    public void removeByIdInventaire(long paramLong);
}
