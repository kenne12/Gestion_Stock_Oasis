package sessions;

import entities.Lignelivraisonfournisseur;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class LignelivraisonfournisseurFacade extends AbstractFacade<Lignelivraisonfournisseur> implements LignelivraisonfournisseurFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public LignelivraisonfournisseurFacade() {
        super(Lignelivraisonfournisseur.class);
    }

    @Override
    public Long nextVal() {
        Query query = this.em.createQuery("SELECT MAX(l.idlignelivraisonfournisseur) FROM Lignelivraisonfournisseur l");
        List list = query.getResultList();
        if (list != null) {
            return ((Long) list.get(0)) + 1;
        } else {
            return 1L;
        }
    }

    @Override
    public List<Lignelivraisonfournisseur> findByIdlivraison(long idLivraison) {
        Query query = this.em.createQuery("SELECT l FROM Lignelivraisonfournisseur l WHERE l.idlivraisonfournisseur.idlivraisonfournisseur=:idlivraison");
        query.setParameter("idlivraison", idLivraison);
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonfournisseur> findByIdarticle(long idArticle) {
        Query query = this.em.createQuery("SELECT l FROM Lignelivraisonfournisseur l WHERE l.idlot.idarticle.idarticle=:idarticle");
        query.setParameter("idarticle", idArticle);
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonfournisseur> findByIdLot(long idMagasinLot, Date dateDebut, Date dateFin) {
        Query query = em.createQuery("SELECT l FROM Lignelivraisonfournisseur l WHERE l.idmagasinlot.idmagasinlot=:idLot AND l.idlivraisonfournisseur.datelivraison BETWEEN :dateDebut AND :dateFin");
        query.setParameter("idLot", idMagasinLot).setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin);
        return query.getResultList();
    }

    @Override
    public List<Lignelivraisonfournisseur> findByIdMagasin(int idMagasin, Date dateDebut, Date dateFin) {
        Query query = em.createQuery("SELECT l FROM Lignelivraisonfournisseur l WHERE l.idmagasinlot.idmagasinarticle.idmagasin.idmagasin=:idMagasin AND l.idlivraisonfournisseur.datelivraison BETWEEN :dateDebut AND :dateFin");
        query.setParameter("idMagasin", idMagasin).setParameter("dateDebut", dateDebut).setParameter("dateFin", dateFin);
        return query.getResultList();
    }
}
