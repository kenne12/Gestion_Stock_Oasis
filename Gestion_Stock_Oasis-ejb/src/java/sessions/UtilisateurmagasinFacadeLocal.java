package sessions;

import entities.Utilisateurmagasin;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UtilisateurmagasinFacadeLocal {

    public void create(Utilisateurmagasin paramUtilisateurmagasin);

    public void edit(Utilisateurmagasin paramUtilisateurmagasin);

    public void remove(Utilisateurmagasin paramUtilisateurmagasin);

    public Utilisateurmagasin find(Object paramObject);

    public List<Utilisateurmagasin> findAll();

    public List<Utilisateurmagasin> findRange(int[] paramArrayOfInt);

    public int count();

    public Long nextVal();

    public List<Utilisateurmagasin> findByIdutilisateur(int paramInt);

    public Utilisateurmagasin findByIdutilisateurIdmagasin(int paramInt1, int paramInt2);

    public void deleteByIdutilisateur(int idutilisateur);
}
