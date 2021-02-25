package sessions;

import entities.Magasinlot;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MagasinlotFacadeLocal {

    public void create(Magasinlot magasinlot);

    public void edit(Magasinlot magasinlot);

    public void remove(Magasinlot magasinlot);

    public Magasinlot find(Object id);

    public List<Magasinlot> findAll();

    public List<Magasinlot> findRange(int[] range);

    public int count();

    public Long nextVal();

    public List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle, boolean order, Date date) throws Exception;

    public Magasinlot findByIdmagasinIdlot(int idmagasin, long idlot);

    List<Magasinlot> findByIdMagasin(int idMagasin);

    public List<Magasinlot> findByIdmagasinEtatIsTrue(int paramInt);

    public List<Magasinlot> findByIdlot(long paramLong);

    public List<Magasinlot> findByIdmagasinNotPerempt(int paramInt, boolean paramBoolean, Date paramDate) throws Exception;

    public List<Magasinlot> findAllPeremptedEtatIsTrue(Date paramDate) throws Exception;

    public void removeAllByIdarticle(long paramLong);

    public List<Magasinlot> findAllEtatIsTrue() throws Exception;

    public List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle);

    public List<Magasinlot> findByIdmagasinQtyNotNull(int idmagasin);

    List<Magasinlot> findByIdMagasinIdArticle(long idMagasinArticle);
}
