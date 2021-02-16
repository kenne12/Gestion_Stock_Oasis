package sessions;

import entities.Utilisateur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UtilisateurFacadeLocal {

    public void create(Utilisateur paramUtilisateur);

    public void edit(Utilisateur paramUtilisateur);

    public void remove(Utilisateur paramUtilisateur);

    public Utilisateur find(Object paramObject);

    public List<Utilisateur> findAll();

    public List<Utilisateur> findRange(int[] paramArrayOfInt);

    public int count();

    public Integer nextVal();

    public Utilisateur login(String paramString1, String paramString2)
            throws Exception;

    public List<Utilisateur> findByActif(Boolean paramBoolean);
}
