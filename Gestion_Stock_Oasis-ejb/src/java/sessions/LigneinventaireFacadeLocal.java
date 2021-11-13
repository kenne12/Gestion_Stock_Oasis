package sessions;

import entities.Ligneinventaire;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LigneinventaireFacadeLocal {

    public void create(Ligneinventaire ligneinventaire);

    public void edit(Ligneinventaire ligneinventaire);

    public void remove(Ligneinventaire ligneinventaire);

    public Ligneinventaire find(Object object);

    public List<Ligneinventaire> findAll();

    public List<Ligneinventaire> findRange(int[] arrayOfInt);

    public int count();

    public Long nextVal();

    public List<Ligneinventaire> findByIdInventaire(long idinventaire);

    public void removeByIdInventaire(long idinventaire);

    public void deleteByIdarticle(long idarticle);
}
