package sessions;

import entities.AnneeMois;
import entities.Commandefournisseur;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CommandefournisseurFacadeLocal {

    public void create(Commandefournisseur commandefournisseur);

    public void edit(Commandefournisseur commandefournisseur);

    public void remove(Commandefournisseur commandefournisseur);

    public Commandefournisseur find(Object object);

    public List<Commandefournisseur> findAll();

    public List<Commandefournisseur> findRange(int[] arrayOfInt);

    public int count();

    public Long nextVal();

    Long nextVal(int idMagasin, AnneeMois anneeMois);

    public List<Commandefournisseur> findAllRange(int idMagasin);

    List<Commandefournisseur> findAllRange(int idMagasin, Date dateDebut, Date dateFin);

    public List<Commandefournisseur> findByLivre(int idMagasin, boolean livre);
}
