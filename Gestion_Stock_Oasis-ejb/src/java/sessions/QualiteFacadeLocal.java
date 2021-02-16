package sessions;

import entities.Qualite;
import java.util.List;
import javax.ejb.Local;

@Local
public interface QualiteFacadeLocal {

    public void create(Qualite paramQualite);

    public void edit(Qualite paramQualite);

    public void remove(Qualite paramQualite);

    public Qualite find(Object paramObject);

    public List<Qualite> findAll();

    public List<Qualite> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Qualite> findAllRange();
}
