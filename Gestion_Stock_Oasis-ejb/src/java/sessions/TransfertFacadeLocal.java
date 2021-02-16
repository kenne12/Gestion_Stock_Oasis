package sessions;

import entities.Transfert;
import java.util.List;
import javax.ejb.Local;

@Local
public abstract interface TransfertFacadeLocal {

    public void create(Transfert paramTransfert);

    public void edit(Transfert paramTransfert);

    public void remove(Transfert paramTransfert);

    public Transfert find(Object paramObject);

    public List<Transfert> findAll();

    public List<Transfert> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Transfert> findAllRange();
}
