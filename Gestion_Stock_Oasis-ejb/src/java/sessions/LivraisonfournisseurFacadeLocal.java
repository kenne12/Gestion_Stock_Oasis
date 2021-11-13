package sessions;

import entities.AnneeMois;
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

    Long nextVal();

    Long nextVal(int idMagasin, AnneeMois anneeMois);

    List<Livraisonfournisseur> findAllRange(int idMagasin);

    List<Livraisonfournisseur> findAllRange(int idMagasin, boolean livraisonDirecte);

    List<Livraisonfournisseur> findAllRange(int idMagasin, Date dateDebut, Date dateFin, boolean livraisonDirecte);

    List<Livraisonfournisseur> findAllRange(int idMagasin, Date dateDebut, Date dateFin);

    List<Livraisonfournisseur> findAllRange(int idMagasin, Date date);
}
