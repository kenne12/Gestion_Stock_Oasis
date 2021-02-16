package sessions;

import entities.Article;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ArticleFacadeLocal {

    public void create(Article paramArticle);

    public void edit(Article paramArticle);

    public void remove(Article paramArticle);

    public Article find(Object paramObject);

    public List<Article> findAll();

    public List<Article> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Article> findAllRange();

    public Article findByCode(String paramString);

    public List<Article> findByPerissable(boolean paramBoolean);

    public List<Article> findByFamille(long paramLong)
            throws Exception;

    public List<Article> findByFamille(long paramLong, boolean paramBoolean)
            throws Exception;

    public List<Article> findAllRange(boolean paramBoolean);

    public List<Article> findByEtatQuantityPositif(boolean paramBoolean)
            throws Exception;
}
