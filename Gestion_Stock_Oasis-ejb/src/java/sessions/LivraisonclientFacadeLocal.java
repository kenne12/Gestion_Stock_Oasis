package sessions;

import entities.Livraisonclient;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LivraisonclientFacadeLocal {

    public void create(Livraisonclient paramLivraisonclient);

    public void edit(Livraisonclient paramLivraisonclient);

    public void remove(Livraisonclient paramLivraisonclient);

    public Livraisonclient find(Object paramObject);

    public List<Livraisonclient> findAll();

    public List<Livraisonclient> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Livraisonclient> findAllRange();

    public Livraisonclient findByIddemande(long iddemande);
}
