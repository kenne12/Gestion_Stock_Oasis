package sessions;

import entities.Lot;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LotFacadeLocal {

    public void create(Lot paramLot);

    public void edit(Lot paramLot);

    public void remove(Lot paramLot);

    public Lot find(Object paramObject);

    public List<Lot> findAll();

    public List<Lot> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lot> findAllRange();

    public List<Lot> findAllRangeEtatIsTrue();

    public List<Lot> findAllRange(boolean paramBoolean);

    public Lot findByCode(long idarticle, String numero);

    public Lot findByCode(String numero);

    public List<Lot> findByArticle1(Long idarticle, boolean order, Date paramDate)
            throws Exception;

    public List<Lot> findByArticle(Long idarticle, boolean order)
            throws Exception;

    public List<Lot> findByArticle(Long idacrticle);

    public List<Lot> findByArticleRangeDesc(Long idarticle, boolean paramBoolean)
            throws Exception;

    public List<Lot> findByArticle(Long idarticle, boolean paramBoolean, Date date)
            throws Exception;

    public List<Lot> findByArticle(Long idarticle, boolean paramBoolean1, Date date, boolean paramBoolean2)
            throws Exception;

    public List<Lot> findAllPeremptionIsActif(Date date);
}
