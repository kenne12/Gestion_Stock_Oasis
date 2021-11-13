package sessions;

import entities.Lignelivraisonclient;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignelivraisonclientFacadeLocal {

    public void create(Lignelivraisonclient lignelivraisonclient);

    public void edit(Lignelivraisonclient lignelivraisonclient);

    public void remove(Lignelivraisonclient lignelivraisonclient);

    public Lignelivraisonclient find(Object id);

    public List<Lignelivraisonclient> findAll();

    public List<Lignelivraisonclient> findRange(int[] range);

    int count();

    Long nextVal();

    List<Lignelivraisonclient> findByIdlivraisonclient(long idLivraisonClient);

    List<Lignelivraisonclient> findAllRange();

    List<Lignelivraisonclient> findByIdMagasin(int idMagasin, Date dateDebut, Date dateFin);

    List<Lignelivraisonclient> findByIdArticle(long idarticle);

    List<Lignelivraisonclient> findByIdLot(long idMagasinLot, Date dateDebut, Date dateFin);

    void deleteByIdarticle(long idarticle);
}
