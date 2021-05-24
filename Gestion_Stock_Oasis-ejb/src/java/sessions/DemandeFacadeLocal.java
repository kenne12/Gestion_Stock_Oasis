package sessions;

import entities.Demande;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface DemandeFacadeLocal {

    public void create(Demande paramDemande);

    public void edit(Demande paramDemande);

    public void remove(Demande paramDemande);

    public Demande find(Object paramObject);

    public List<Demande> findAll();

    public List<Demande> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Demande> findAllRange();

    public List<Demande> findByValidee(boolean validee);

    public List<Demande> findByValidee(int idMagasin, boolean validee);

    public List<Demande> findByIdpersonnelIntervalDate(int idclient, Date dateDebut, Date dateFin);

    public List<Demande> findAllRange(int idClient);

    public List<Demande> findByIdMagasin(int idMagasin);
}
