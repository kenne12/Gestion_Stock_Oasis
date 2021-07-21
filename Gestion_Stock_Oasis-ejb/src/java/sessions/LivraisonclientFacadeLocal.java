package sessions;

import entities.AnneeMois;
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

    public Long nextValue();

    Long nextVal(int idMagasin, AnneeMois anneeMois);

    public List<Livraisonclient> findAllRange();

    public Livraisonclient findByIddemande(long idDemande);

    public List<Livraisonclient> findAllRange(int idMagasin, Date dateDebut, Date dateFin);

    public List<Livraisonclient> findByIdmagasinNonRegle(int idMagasin);

    public List<Livraisonclient> findByIdmagasin(int idMagasin);

    public List<Livraisonclient> findByIdmagasinAndDate(int idMagasin, Date date);

    public List<Livraisonclient> findByIdmagasinAndIdclientAndDate(int idMagasin, int idClient, Date date);

    public List<Livraisonclient> findByIdmagasinAndIdclientTwoDates(int idMagasin, int idClient, Date dateDebut, Date dateFin);

}
