package sessions;

import entities.Magasinarticle;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class MagasinarticleFacade extends AbstractFacade<Magasinarticle> implements MagasinarticleFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public MagasinarticleFacade() {
        super(Magasinarticle.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(m.idmagasinarticle) FROM Magasinarticle m");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = result + 1L;
        }
        return result;
    }

    @Override
    public List<Magasinarticle> findByIdmagasin(int idmagasin, boolean etat) {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idmagasin.idmagasin=:idmagasin AND m.etat=:etat ORDER BY m.idarticle.code");
        query.setParameter("idmagasin", idmagasin).setParameter("etat", etat);
        return query.getResultList();
    }

    @Override
    public List<Magasinarticle> findByIdmagasinProductIsActif(int idmagasin, boolean etat) {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idmagasin.idmagasin=:idmagasin AND m.etat=:etat AND m.idarticle.etat=true ORDER BY m.idarticle.code");
        query.setParameter("idmagasin", idmagasin).setParameter("etat", (etat));
        return query.getResultList();
    }

    @Override
    public Magasinarticle findByIdmagasinIdarticle(int idmagasin, long idarticle) {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idmagasin.idmagasin=:idmagasin AND m.idarticle.idarticle=:idarticle ORDER BY m.idarticle.libelle");
        query.setParameter("idmagasin", idmagasin).setParameter("idarticle", (idarticle));
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Magasinarticle) list.get(0);
        }
        return null;
    }

    @Override
    public List<Magasinarticle> findByIdmagasinIsavailable(int idmagasin, boolean etat) {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idmagasin.idmagasin=:idmagasin AND m.etat=:etat AND m.quantite>0D ORDER BY m.idarticle.libelle");
        query.setParameter("idmagasin", (idmagasin)).setParameter("etat", (etat));
        return query.getResultList();
    }

    @Override
    public List<Magasinarticle> findByIdmagasinIdfamille(int idmagasin, long idfamille, boolean etat) {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idmagasin.idmagasin=:idmagasin AND m.idarticle.idfamille.idfamille=:idfamille AND m.etat=:etat ORDER BY m.idarticle.libelle");
        query.setParameter("idmagasin", idmagasin).setParameter("etat", etat).setParameter("idfamille", (idfamille));
        return query.getResultList();
    }

    @Override
    public List<Magasinarticle> findByIdmagasinIdfamilleIsavailable(int idmagasin, long idfamille, boolean etat) {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idmagasin.idmagasin=:idmagasin AND m.idarticle.idfamille.idfamille=:idfamille AND m.etat=:etat AND m.quantite>0d ORDER BY m.idarticle.libelle");
        query.setParameter("idmagasin", idmagasin).setParameter("etat", etat).setParameter("idfamille", idfamille);
        return query.getResultList();
    }

    @Override
    public List<Magasinarticle> findByIdarticle(long idarticle) {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        return query.getResultList();
    }

    @Override
    public void removeAllByIdarticle(long idarticle) {
        Query query = this.em.createQuery("DELETE FROM Magasinarticle ml WHERE ml.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idarticle);
        query.executeUpdate();
    }

    @Override
    public List<Magasinarticle> findAllEtatIsTrueAlert() {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.etat=true AND m.quantitemultiple<=m.idarticle.quantitesecurite ORDER BY m.idmagasin.nom , m.idarticle.code");
        return query.getResultList();
    }

    @Override
    public List<Magasinarticle> findAllEtatIsTrue() {
        Query query = this.em.createQuery("SELECT m FROM Magasinarticle m WHERE m.etat=true  ORDER BY m.idmagasin.nom , m.idarticle.code");
        return query.getResultList();
    }

    @Override
    public List<Magasinarticle> findByIdmagasinQtyGreater(int idmagasin, boolean etat) {
        Query query = em.createQuery("SELECT m FROM Magasinarticle m WHERE m.idmagasin.idmagasin=:idmagasin AND m.etat=:etat AND m.quantite>0d");
        query.setParameter("idmagasin", idmagasin).setParameter("etat", etat);
        return query.getResultList();
    }
}
