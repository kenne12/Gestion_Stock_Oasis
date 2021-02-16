package sessions;

import entities.Menu;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MenuFacadeLocal {

    public void create(Menu paramMenu);

    public void edit(Menu paramMenu);

    public void remove(Menu paramMenu);

    public Menu find(Object paramObject);

    public List<Menu> findAll();

    public List<Menu> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Menu> findAllRangeEtatIsTrue();
}
