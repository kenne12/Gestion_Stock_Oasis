package sessions;

import entities.Lignelivraisonfournisseur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignelivraisonfournisseurFacadeLocal {

    public void create(Lignelivraisonfournisseur paramLignelivraisonfournisseur);

    public void edit(Lignelivraisonfournisseur paramLignelivraisonfournisseur);

    public void remove(Lignelivraisonfournisseur paramLignelivraisonfournisseur);

    public Lignelivraisonfournisseur find(Object paramObject);

    public List<Lignelivraisonfournisseur> findAll();

    public List<Lignelivraisonfournisseur> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignelivraisonfournisseur> findByIdlivraison(long paramLong);

    public List<Lignelivraisonfournisseur> findByIdarticle(long paramLong);
}
