package sessions;

import entities.Transfert;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface TransfertFacadeLocal {

    public void create(Transfert transfert);

    public void edit(Transfert transfert);

    public void remove(Transfert transfert);

    public Transfert find(Object object);

    public List<Transfert> findAll();

    public List<Transfert> findRange(int[] arrayOfInt);

    public int count();

    public Long nextVal();

    public List<Transfert> findByIdMagasinBidirection(int idMagasin);

    public List<Transfert> findByIdMagasinSource(int idMagasin);

    public List<Transfert> findByIdMagasinSource(int idMagasin, Date dateDebut, Date dateFin);

    public List<Transfert> findByIdMagasinSource(int idMagasin, Date date);

    public List<Transfert> findByIdMagasinDestination(int idMagasin);

    public List<Transfert> findByIdMagasinDestination(int idMagasin, Date dateDebut, Date dateFin);

    public List<Transfert> findByIdMagasinDestination(int idMagasin, Date date);
}
