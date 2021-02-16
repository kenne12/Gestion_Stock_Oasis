package sessions;

import entities.Personnel;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PersonnelFacadeLocal {

    public void create(Personnel paramPersonnel);

    public void edit(Personnel paramPersonnel);

    public void remove(Personnel paramPersonnel);

    public Personnel find(Object paramObject);

    public List<Personnel> findAll();

    public List<Personnel> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Personnel> findAllRange();
}
