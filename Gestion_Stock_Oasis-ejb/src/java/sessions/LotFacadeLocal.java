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

    List<Lot> findAllRange();

    List<Lot> findAllRangeEtatIsTrue();

    List<Lot> findAllRange(int idStructure, boolean perissable);

    List<Lot> findAllRange(int idStructure);

    Lot findByCode(long idarticle, String numero);

    Lot findByCode(int idStructure, String numero);

    List<Lot> findByArticle1(Long idarticle, boolean order, Date paramDate)
            throws Exception;

    List<Lot> findByArticle(Long idarticle, boolean order)
            throws Exception;

    List<Lot> findByArticle(Long idacrticle);

    List<Lot> findByArticleRangeDesc(Long idarticle, boolean paramBoolean)
            throws Exception;

    List<Lot> findByArticle(Long idarticle, boolean paramBoolean, Date date)
            throws Exception;

    List<Lot> findByArticle(Long idarticle, boolean paramBoolean1, Date date, boolean paramBoolean2)
            throws Exception;

    List<Lot> findAllPeremptionIsActif(int idStructure, Date date);

    void deleteByIdarticle(long idarticle);
}
