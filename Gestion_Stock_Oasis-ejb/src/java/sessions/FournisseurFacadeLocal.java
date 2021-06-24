package sessions;

import entities.Fournisseur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface FournisseurFacadeLocal {

    public void create(Fournisseur paramFournisseur);

    public void edit(Fournisseur paramFournisseur);

    public void remove(Fournisseur paramFournisseur);

    public Fournisseur find(Object paramObject);

    public List<Fournisseur> findAll();

    public List<Fournisseur> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Fournisseur> findAllRange(int idMagasin);

    public List<Fournisseur> findByIdstructure(int idStructure);
}
