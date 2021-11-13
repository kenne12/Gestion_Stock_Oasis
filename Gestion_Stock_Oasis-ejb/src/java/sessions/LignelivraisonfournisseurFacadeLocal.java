package sessions;

import entities.Lignelivraisonfournisseur;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignelivraisonfournisseurFacadeLocal {

    public void create(Lignelivraisonfournisseur lignelivraisonfournisseur);

    public void edit(Lignelivraisonfournisseur lignelivraisonfournisseur);

    public void remove(Lignelivraisonfournisseur lignelivraisonfournisseur);

    public Lignelivraisonfournisseur find(Object id);

    public List<Lignelivraisonfournisseur> findAll();

    public List<Lignelivraisonfournisseur> findRange(int[] range);

    public int count();

    public Long nextVal();

    public List<Lignelivraisonfournisseur> findByIdlivraison(long idLivraison);

    public List<Lignelivraisonfournisseur> findByIdarticle(long idArticle);

    public List<Lignelivraisonfournisseur> findByIdLot(long idMagasinLot, Date dateDebut, Date dateFin);

    public List<Lignelivraisonfournisseur> findByIdMagasin(int idMagasin, Date dateDebut, Date dateFin);

    public void deleteByIdarticle(long idarticle);
}
