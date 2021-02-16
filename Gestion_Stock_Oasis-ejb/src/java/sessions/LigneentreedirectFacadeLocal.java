package sessions;

import entities.Ligneentreedirect;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LigneentreedirectFacadeLocal {

    public void create(Ligneentreedirect paramLigneentreedirect);

    public void edit(Ligneentreedirect paramLigneentreedirect);

    public void remove(Ligneentreedirect paramLigneentreedirect);

    public Ligneentreedirect find(Object paramObject);

    public List<Ligneentreedirect> findAll();

    public List<Ligneentreedirect> findRange(int[] paramArrayOfInt);

    public int count();
}
