package sessions;

import entities.Article;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ArticleFacadeLocal {

    public void create(Article paramArticle);

    public void edit(Article paramArticle);

    public void remove(Article paramArticle);

    public Article find(Object object);

    public List<Article> findAll();

    public List<Article> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Article> findAllRange(int idStructure);

    public Article findByCode(int idStructure, String code);

    public List<Article> findByPerissable(int idStructure, boolean paramBoolean);

    public List<Article> findByFamille(long paramLong)
            throws Exception;

    public List<Article> findByFamille(long paramLong, boolean paramBoolean)
            throws Exception;

    public List<Article> findAllRange(int idStructure, boolean paramBoolean);

    public List<Article> findByEtatQuantityPositif(int idStructure, boolean paramBoolean)
            throws Exception;
}
