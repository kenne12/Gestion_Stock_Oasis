package sessions;

import entities.Lignecommandefournisseur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignecommandefournisseurFacadeLocal {

    public void create(Lignecommandefournisseur paramLignecommandefournisseur);

    public void edit(Lignecommandefournisseur paramLignecommandefournisseur);

    public void remove(Lignecommandefournisseur paramLignecommandefournisseur);

    public Lignecommandefournisseur find(Object paramObject);

    public List<Lignecommandefournisseur> findAll();

    public List<Lignecommandefournisseur> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignecommandefournisseur> findAllRange();

    public List<Lignecommandefournisseur> findByCommande(long paramLong);
}
