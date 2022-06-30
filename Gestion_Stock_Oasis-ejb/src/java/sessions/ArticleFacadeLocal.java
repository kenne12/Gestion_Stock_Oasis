package sessions;

import entities.Article;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ArticleFacadeLocal {

    public void create(Article article);

    public void edit(Article article);

    public void remove(Article article);

    public Article find(Object object);

    public List<Article> findAll();

    public List<Article> findRange(int[] arrayOfInt);

    int count();

    Long nextVal();

    Long nextValByIdstructure(int idStructure);

    List<Article> findAllRange(int idStructure);

    Article findByCode(int idStructure, String code);

    List<Article> findByPerissable(int idStructure, boolean perrissable);

    List<Article> findByFamille(long idfamille) throws Exception;

    List<Article> findByFamille(long idfamille, boolean positif) throws Exception;

    List<Article> findAllRange(int idStructure, boolean etat);

    List<Article> findByEtatQuantityPositif(int idStructure, boolean etat) throws Exception;
}
