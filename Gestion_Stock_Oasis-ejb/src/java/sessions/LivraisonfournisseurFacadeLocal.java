package sessions;

import entities.Livraisonfournisseur;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LivraisonfournisseurFacadeLocal {

    public void create(Livraisonfournisseur livraisonfournisseur);

    public void edit(Livraisonfournisseur livraisonfournisseur);

    public void remove(Livraisonfournisseur livraisonfournisseur);

    public Livraisonfournisseur find(Object object);

    public List<Livraisonfournisseur> findAll();

    public List<Livraisonfournisseur> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Livraisonfournisseur> findAllRange();

    public List<Livraisonfournisseur> findAllRange(boolean livraisonDirecte);

    public List<Livraisonfournisseur> findAllRange(int idMagasin, Date dateDebut, Date dateFin, boolean livraisonDirecte);
}
