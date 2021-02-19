package sessions;

import entities.Lignedemande;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignedemandeFacadeLocal {

    public void create(Lignedemande lignedemande);

    public void edit(Lignedemande lignedemande);

    public void remove(Lignedemande lignedemande);

    public Lignedemande find(Object id);

    public List<Lignedemande> findAll();

    public List<Lignedemande> findRange(int[] range);

    public int count();

    public Long nextVal();

    public List<Lignedemande> findByIddemande(long idDemande);

    public List<Lignedemande> findByIdarticle(long idArticle);
}
