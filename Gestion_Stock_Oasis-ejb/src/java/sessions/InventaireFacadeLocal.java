package sessions;

import entities.Inventaire;
import java.util.List;
import javax.ejb.Local;

@Local
public interface InventaireFacadeLocal {

    public void create(Inventaire inventaire);

    public void edit(Inventaire inventaire);

    public void remove(Inventaire inventaire);

    public Inventaire find(Object object);

    public List<Inventaire> findAll();

    public List<Inventaire> findRange(int[] arrayOfInt);

    public int count();

    public Long nextVal();

    public List<Inventaire> findAllRange(int idMagasin);
}
