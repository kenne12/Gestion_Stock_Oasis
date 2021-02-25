package sessions;

import entities.Mois;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MoisFacadeLocal {

    void create(Mois mois);

    void edit(Mois mois);

    void remove(Mois mois);

    Mois find(Object id);

    List<Mois> findAll();

    List<Mois> findRange(int[] range);

    int count();

    List<Mois> findAllRange();
}
