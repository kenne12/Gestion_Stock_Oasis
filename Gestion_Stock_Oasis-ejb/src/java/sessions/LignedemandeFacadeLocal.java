package sessions;

import entities.Lignedemande;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignedemandeFacadeLocal {

    public void create(Lignedemande paramLignedemande);

    public void edit(Lignedemande paramLignedemande);

    public void remove(Lignedemande paramLignedemande);

    public Lignedemande find(Object paramObject);

    public List<Lignedemande> findAll();

    public List<Lignedemande> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignedemande> findByIddemande(long paramLong);

    public List<Lignedemande> findByIdarticle(long paramLong);
}
