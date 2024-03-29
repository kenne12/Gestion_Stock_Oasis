/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Journee;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface JourneeFacadeLocal {

    void create(Journee journee);

    void edit(Journee journee);

    void remove(Journee journee);

    Journee find(Object id);

    List<Journee> findAll();

    List<Journee> findRange(int[] range);

    int count();

    public Long nextVal();

    public List<Journee> findByInterval(Date dateDebut, Date dateFin);

    public List<Journee> findByIdMois(int idMois);

    public Journee findByIdMagasinDate(int idMagasin, Date date);

    public List<Journee> findByIdmagasinTwoDates(int idMagasin, Date dateDebut, Date dateFin);

    public List<Journee> findByTwoDates(Date dateDebut, Date dateFin);

}
