package sessions;

import entities.Livraisonclient;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LivraisonclientFacadeLocal {

    public void create(Livraisonclient livraisonclient);

    public void edit(Livraisonclient livraisonclient);

    public void remove(Livraisonclient livraisonclient);

    public Livraisonclient find(Object object);

    public List<Livraisonclient> findAll();

    public List<Livraisonclient> findRange(int[] arrayOfInt);

    public int count();

    public Long nextVal();

    public List<Livraisonclient> findAllRange();

    public Livraisonclient findByIddemande(long idDemande);

    public List<Livraisonclient> findAllRange(int idMagasin, Date dateDebut, Date dateFin);

    public List<Livraisonclient> findByIdmagasinNonRegle(int idMagasin);

}
