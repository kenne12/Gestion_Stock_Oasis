package sessions;

import entities.Mouchard;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MouchardFacadeLocal {

    public void create(Mouchard mouchard);

    public void edit(Mouchard mouchard);

    public void remove(Mouchard mouchard);

    public Mouchard find(Object object);

    public List<Mouchard> findAll();

    public List<Mouchard> findRange(int[] arrayOfInt);

    int count();

    Long nextVal();

    void deleteByIdutilisateur(int idutilisateur);
}
