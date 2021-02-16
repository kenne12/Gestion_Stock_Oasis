package sessions;

import entities.Mouchard;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MouchardFacadeLocal {

    public void create(Mouchard paramMouchard);

    public void edit(Mouchard paramMouchard);

    public void remove(Mouchard paramMouchard);

    public Mouchard find(Object paramObject);

    public List<Mouchard> findAll();

    public List<Mouchard> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public void deleteByIdutilisateur(int idutilisateur);
}
