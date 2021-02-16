package sessions;

import entities.Privilege;
import java.util.List;
import javax.ejb.Local;

@Local
public interface PrivilegeFacadeLocal {

    public void create(Privilege paramPrivilege);

    public void edit(Privilege paramPrivilege);

    public void remove(Privilege paramPrivilege);

    public Privilege find(Object paramObject);

    public List<Privilege> findAll();

    public List<Privilege> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Privilege> findAll1();

    public List<Privilege> findByUser(int paramInt);

    public Privilege findByUser(int paramInt1, int paramInt2);

    public void delete(int paramInt1, int paramInt2);
    
    public void deleteByIdUtilisateur(int idutilisateur);
}
