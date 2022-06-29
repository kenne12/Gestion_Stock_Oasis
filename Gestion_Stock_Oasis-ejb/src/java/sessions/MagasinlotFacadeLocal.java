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

    Magasinlot find(Object id);

    List<Magasinlot> findAll();

    List<Magasinlot> findRange(int[] range);

    int count();

    Long nextVal();

    List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle, boolean order, Date date) throws Exception;

    Magasinlot findByIdmagasinIdlot(int idmagasin, long idlot);

    List<Magasinlot> findByIdMagasin(int idMagasin);

    List<Magasinlot> findByIdmagasinEtatIsTrue(int idMagasin);

    List<Magasinlot> findByIdlot(long idLot);

    List<Magasinlot> findByIdmagasinNotPerempt(int idmagasin, boolean perissable, Date date) throws Exception;

    List<Magasinlot> findAllPeremptedEtatIsTrue(int idMagasin, Date date) throws Exception;

    void removeAllByIdarticle(long idmagasin);

    List<Magasinlot> findAllEtatIsTrue(int idmagasin) throws Exception;

    List<Magasinlot> findByArticleIsavailable(int idmagasin, long idarticle);

    List<Magasinlot> findByIdmagasinQtyNotNull(int idmagasin);

    List<Magasinlot> findByIdMagasinIdArticle(long idMagasinArticle);

    List<Magasinlot> findByIdMagasinAndEtatGtZero(int idMagasin, boolean etat);

    List<Magasinlot> findByIdmagasinAndFamilleAndEtatGtzero(int idMagasin, long idFamille, boolean etat);

    List<Magasinlot> findByIdArticle(long idArticle);
}
