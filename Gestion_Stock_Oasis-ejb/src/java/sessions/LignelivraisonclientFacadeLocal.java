package sessions;

import entities.Lignelivraisonclient;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignelivraisonclientFacadeLocal {

    public void create(Lignelivraisonclient paramLignelivraisonclient);

    public void edit(Lignelivraisonclient paramLignelivraisonclient);

    public void remove(Lignelivraisonclient paramLignelivraisonclient);

    public Lignelivraisonclient find(Object paramObject);

    public List<Lignelivraisonclient> findAll();

    public List<Lignelivraisonclient> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignelivraisonclient> findByIdlivraisonclient(long paramLong);

    public List<Lignelivraisonclient> findAllRange();

    public List<Lignelivraisonclient> findByIdprojet(int idprojet, Date dateDebut, Date dateFin);

    public List<Lignelivraisonclient> findByIdArticle(long idarticle);
}
