package sessions;

import entities.Utilisateur;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UtilisateurFacadeLocal {

    public void create(Utilisateur utilisateur);

    public void edit(Utilisateur utilisateur);

    public void remove(Utilisateur utilisateur);

    public Utilisateur find(Object object);

    public List<Utilisateur> findAll();

    public List<Utilisateur> findRange(int[] arrayOfInt);

    public int count();

    public Integer nextVal();

    public List<Utilisateur> findByIdStructure(int idStructure);

    public Utilisateur login(String login) throws Exception;

    public Utilisateur login(String login, String password) throws Exception;

    public List<Utilisateur> findByIdStructureEtat(int idStructure, boolean actif);

}
