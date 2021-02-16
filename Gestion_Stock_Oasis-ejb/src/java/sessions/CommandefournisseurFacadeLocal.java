package sessions;

import entities.Commandefournisseur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface CommandefournisseurFacadeLocal {

    public void create(Commandefournisseur paramCommandefournisseur);

    public void edit(Commandefournisseur paramCommandefournisseur);

    public void remove(Commandefournisseur paramCommandefournisseur);

    public Commandefournisseur find(Object paramObject);

    public List<Commandefournisseur> findAll();

    public List<Commandefournisseur> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Commandefournisseur> findAllRange();

    public List<Commandefournisseur> findByLivre(boolean paramBoolean);
}
