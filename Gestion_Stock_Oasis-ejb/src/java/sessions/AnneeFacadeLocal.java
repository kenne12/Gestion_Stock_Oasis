package sessions;

import entities.Annee;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AnneeFacadeLocal {

    void create(Annee paramAnnee);

    void edit(Annee paramAnnee);

    void remove(Annee paramAnnee);

    Annee find(Object paramObject);

    List<Annee> findAll();

    List<Annee> findRange(int[] paramArrayOfint);

    int count();

    Integer nextVal();

    List<Annee> findByEtat(boolean paramBoolean) throws Exception;

    List<Annee> findByRange();

    Annee findByCode(String paramString);
}
