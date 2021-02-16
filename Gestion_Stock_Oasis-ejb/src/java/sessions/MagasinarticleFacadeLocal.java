package sessions;

import entities.Magasinarticle;
import java.util.List;
import javax.ejb.Local;

@Local
public interface MagasinarticleFacadeLocal {

    public void create(Magasinarticle paramMagasinarticle);

    public void edit(Magasinarticle paramMagasinarticle);

    public void remove(Magasinarticle paramMagasinarticle);

    public Magasinarticle find(Object paramObject);

    public List<Magasinarticle> findAll();

    public List<Magasinarticle> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Magasinarticle> findByIdmagasin(int paramInt, boolean paramBoolean);

    public List<Magasinarticle> findByIdmagasinProductIsActif(int paramInt, boolean paramBoolean);

    public Magasinarticle findByIdmagasinIdarticle(int paramInt, long paramLong);

    public List<Magasinarticle> findByIdmagasinIsavailable(int paramInt, boolean paramBoolean);

    public List<Magasinarticle> findByIdmagasinIdfamille(int paramInt, long paramLong, boolean paramBoolean);

    public List<Magasinarticle> findByIdmagasinIdfamilleIsavailable(int paramInt, long paramLong, boolean paramBoolean);

    public List<Magasinarticle> findByIdarticle(long paramLong);

    public void removeAllByIdarticle(long paramLong);

    public List<Magasinarticle> findAllEtatIsTrueAlert();

    public List<Magasinarticle> findAllEtatIsTrue();

    public List<Magasinarticle> findByIdmagasinQtyGreater(int idmagasin, boolean etat);

}
