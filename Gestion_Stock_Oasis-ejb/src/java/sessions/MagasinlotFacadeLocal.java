package sessions;

import entities.Magasinlot;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MagasinlotFacadeLocal {

    public void create(Magasinlot paramMagasinlot);

    public void edit(Magasinlot paramMagasinlot);

    public void remove(Magasinlot paramMagasinlot);

    public Magasinlot find(Object paramObject);

    public List<Magasinlot> findAll();

    public List<Magasinlot> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Magasinlot> findByArticleIsavailable(int paramInt, long paramLong, boolean paramBoolean, Date paramDate) throws Exception;

    public Magasinlot findByIdmagasinIdlot(int paramInt, long paramLong);

    public List<Magasinlot> findByIdmagasinEtatIsTrue(int paramInt);

    public List<Magasinlot> findByIdlot(long paramLong);

    public List<Magasinlot> findByIdmagasinNotPerempt(int paramInt, boolean paramBoolean, Date paramDate) throws Exception;

    public List<Magasinlot> findAllPeremptedEtatIsTrue(Date paramDate) throws Exception;

    public void removeAllByIdarticle(long paramLong);

    public List<Magasinlot> findAllEtatIsTrue() throws Exception;

    public List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle);

    public List<Magasinlot> findByIdmagasinQtyNotNull(int idmagasin);
}
