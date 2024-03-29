/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sessions;

import entities.Client;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author USER
 */
@Local
public interface ClientFacadeLocal {

    void create(Client client);

    void edit(Client client);

    void remove(Client client);

    Client find(Object id);

    List<Client> findAll();

    List<Client> findRange(int[] range);

    int count();

    Integer nextVal();

    List<Client> findAllRange(int idMagasin);

    List<Client> findAllRange(int idMagasin, boolean etat);

    List<Client> findByIdstructure(int idStructure, boolean etat);

}
