package sessions;

import entities.AnneeMois;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface AnneeMoisFacadeLocal {

    void create(AnneeMois anneeMois);

    void edit(AnneeMois anneeMois);

    void remove(AnneeMois anneeMois);

    AnneeMois find(Object id);

    List<AnneeMois> findAll();

    List<AnneeMois> findRange(int[] range);

    int count();

    Integer nextVal();

    List<AnneeMois> findByAnneeEtat(int idAnnee, boolean etat) throws Exception;

    List<AnneeMois> findByAnnee(int idAnnee) throws Exception;

    List<AnneeMois> findByEtat(Boolean etat) throws Exception;

    List<AnneeMois> findByRange() throws Exception;

    AnneeMois findByDate(Date date) throws Exception;
    
    AnneeMois findDefaultMonthByIdannee(int idAnnee);
}
