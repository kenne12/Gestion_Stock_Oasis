package sessions;

import entities.Lignerepartitiontemp;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignerepartitiontempFacadeLocal {

    public void create(Lignerepartitiontemp paramLignerepartitiontemp);

    public void edit(Lignerepartitiontemp paramLignerepartitiontemp);

    public void remove(Lignerepartitiontemp paramLignerepartitiontemp);

    public Lignerepartitiontemp find(Object paramObject);

    public List<Lignerepartitiontemp> findAll();

    public List<Lignerepartitiontemp> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignerepartitiontemp> findByIdRepartition(int paramInt);
}
