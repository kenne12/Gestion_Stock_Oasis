package sessions;

import entities.Lignelivraisonclient;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignelivraisonclientFacadeLocal {

    public void create(Lignelivraisonclient lignelivraisonclient);

    public void edit(Lignelivraisonclient lignelivraisonclient);

    public void remove(Lignelivraisonclient lignelivraisonclient);

    public Lignelivraisonclient find(Object id);

    public List<Lignelivraisonclient> findAll();

    public List<Lignelivraisonclient> findRange(int[] range);

    public int count();

    public Long nextVal();

    public List<Lignelivraisonclient> findByIdlivraisonclient(long paramLong);

    public List<Lignelivraisonclient> findAllRange();

    List<Lignelivraisonclient> findByIdMagasin(int idMagasin, Date dateDebut, Date dateFin);

    public List<Lignelivraisonclient> findByIdArticle(long idarticle);

    List<Lignelivraisonclient> findByIdLot(long idMagasinLot, Date dateDebut, Date dateFin);
}
