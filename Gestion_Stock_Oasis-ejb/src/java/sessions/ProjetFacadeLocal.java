package sessions;

import entities.Projet;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProjetFacadeLocal {

    public void create(Projet paramProjet);

    public void edit(Projet paramProjet);

    public void remove(Projet paramProjet);

    public Projet find(Object paramObject);

    public List<Projet> findAll();

    public List<Projet> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Projet> findAllRange();

    public List<Projet> findByIdmagasin(int paramInt);
}
