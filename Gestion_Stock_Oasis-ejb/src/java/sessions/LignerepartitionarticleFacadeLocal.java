package sessions;

import entities.Lignerepartitionarticle;
import java.util.List;
import javax.ejb.Local;

@Local
public interface LignerepartitionarticleFacadeLocal {

    public void create(Lignerepartitionarticle paramLignerepartitionarticle);

    public void edit(Lignerepartitionarticle paramLignerepartitionarticle);

    public void remove(Lignerepartitionarticle paramLignerepartitionarticle);

    public Lignerepartitionarticle find(Object paramObject);

    public List<Lignerepartitionarticle> findAll();

    public List<Lignerepartitionarticle> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Lignerepartitionarticle> findByIdRepartition(int paramInt);
}
