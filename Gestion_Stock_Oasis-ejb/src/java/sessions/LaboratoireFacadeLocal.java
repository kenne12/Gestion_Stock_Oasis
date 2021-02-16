package sessions;

import entities.Laboratoire;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LaboratoireFacadeLocal {

    public void create(Laboratoire paramLaboratoire);

    public void edit(Laboratoire paramLaboratoire);

    public void remove(Laboratoire paramLaboratoire);

    public Laboratoire find(Object paramObject);

    public List<Laboratoire> findAll();

    public List<Laboratoire> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Laboratoire> findAllRange();
}
