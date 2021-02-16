package sessions;

import entities.Repartitionarticle;
import java.util.List;
import javax.ejb.Local;

@Local
public interface RepartitionarticleFacadeLocal {

    public void create(Repartitionarticle paramRepartitionarticle);

    public void edit(Repartitionarticle paramRepartitionarticle);

    public void remove(Repartitionarticle paramRepartitionarticle);

    public Repartitionarticle find(Object paramObject);

    public List<Repartitionarticle> findAll();

    public List<Repartitionarticle> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Repartitionarticle> findAllRange();
}
