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

    public int count();

    public Long nextVal();

    Long nextValByIdstructure(int idStructure);

    public List<Article> findAllRange(int idStructure);

    public Article findByCode(int idStructure, String code);

    public List<Article> findByPerissable(int idStructure, boolean paramBoolean);

    public List<Article> findByFamille(long idfamille) throws Exception;

    public List<Article> findByFamille(long idfamille, boolean paramBoolean) throws Exception;

    public List<Article> findAllRange(int idStructure, boolean paramBoolean);

    public List<Article> findByEtatQuantityPositif(int idStructure, boolean paramBoolean) throws Exception;
}
