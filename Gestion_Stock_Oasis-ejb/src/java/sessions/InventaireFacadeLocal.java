package sessions;

import entities.Inventaire;
import java.util.List;
import javax.ejb.Local;

@Local
public interface InventaireFacadeLocal {

    public void create(Inventaire paramInventaire);

    public void edit(Inventaire paramInventaire);

    public void remove(Inventaire paramInventaire);

    public Inventaire find(Object paramObject);

    public List<Inventaire> findAll();

    public List<Inventaire> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Inventaire> findAllRange();
}
