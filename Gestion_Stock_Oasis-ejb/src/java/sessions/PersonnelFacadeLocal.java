package sessions;

import entities.Personnel;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PersonnelFacadeLocal {

    public void create(Personnel personnel);

    public void edit(Personnel personnel);

    public void remove(Personnel personnel);

    public Personnel find(Object object);

    public List<Personnel> findAll();

    public List<Personnel> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Personnel> findAllRange(int idMagasin);

    public List<Personnel> findByIdStructure(int idStructure);
}
