package sessions;

import entities.Famille;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FamilleFacadeLocal {

    public void create(Famille paramFamille);

    public void edit(Famille paramFamille);

    public void remove(Famille paramFamille);

    public Famille find(Object paramObject);

    public List<Famille> findAll();

    public List<Famille> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Famille> findAllRange();
}
