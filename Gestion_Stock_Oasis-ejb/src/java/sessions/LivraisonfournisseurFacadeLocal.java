package sessions;

import entities.Livraisonfournisseur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LivraisonfournisseurFacadeLocal {

    public void create(Livraisonfournisseur paramLivraisonfournisseur);

    public void edit(Livraisonfournisseur paramLivraisonfournisseur);

    public void remove(Livraisonfournisseur paramLivraisonfournisseur);

    public Livraisonfournisseur find(Object paramObject);

    public List<Livraisonfournisseur> findAll();

    public List<Livraisonfournisseur> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Livraisonfournisseur> findAllRange();

    public List<Livraisonfournisseur> findAllRange(boolean paramBoolean);
}
