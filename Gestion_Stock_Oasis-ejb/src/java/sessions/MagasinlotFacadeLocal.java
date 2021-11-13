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

    public List<Magasinlot> findByIdMagasin(int idMagasin);

    public List<Magasinlot> findByIdmagasinEtatIsTrue(int idMagasin);

    public List<Magasinlot> findByIdlot(long idLot);

    public List<Magasinlot> findByIdmagasinNotPerempt(int idmagasin, boolean perissable, Date date) throws Exception;

    public List<Magasinlot> findAllPeremptedEtatIsTrue(int idMagasin, Date date) throws Exception;

    public void removeAllByIdarticle(long idmagasin);

    public List<Magasinlot> findAllEtatIsTrue(int idmagasin) throws Exception;

    public List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle);

    public List<Magasinlot> findByIdmagasinQtyNotNull(int idmagasin);

    public List<Magasinlot> findByIdMagasinIdArticle(long idMagasinArticle);

    public List<Magasinlot> findByIdMagasinAndEtatGtZero(int idMagasin, boolean etat);

    public List<Magasinlot> findByIdmagasinAndFamilleAndEtatGtzero(int idMagasin, long idFamille, boolean etat);
}
