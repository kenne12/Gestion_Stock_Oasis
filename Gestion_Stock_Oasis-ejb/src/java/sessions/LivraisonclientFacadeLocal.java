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

    Long nextValue();

    Long nextVal(int idMagasin, AnneeMois anneeMois);

    List<Livraisonclient> findAllRange();

    Livraisonclient findByIddemande(long idDemande);

    List<Livraisonclient> findAllRange(int idMagasin, Date dateDebut, Date dateFin);

    List<Livraisonclient> findByIdmagasinNonRegle(int idMagasin);

    List<Livraisonclient> findByIdmagasin(int idMagasin);

    List<Livraisonclient> findByIdmagasinAndDate(int idMagasin, Date date);

    List<Livraisonclient> findByIdmagasinAndIdclientAndDate(int idMagasin, int idClient, Date date);

    List<Livraisonclient> findByIdmagasinAndIdclientTwoDates(int idMagasin, int idClient, Date dateDebut, Date dateFin);
    
    void deleteLivraison(long id);

}
