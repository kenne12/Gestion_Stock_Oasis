package sessions;

import entities.Annee;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AnneeFacadeLocal {

    void create(Annee annee);

    void edit(Annee annee);

    void remove(Annee annee);

    Annee find(Object object);

    List<Annee> findAll();

    List<Annee> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Annee> findByEtat(boolean etat) throws Exception;

    List<Annee> findByRange();

    Annee findByCode(String nom);

    Annee findOneActive();

    Annee findOneDefault();

    void unsetDefaultForOtherYear(Integer idannee);
  
}
