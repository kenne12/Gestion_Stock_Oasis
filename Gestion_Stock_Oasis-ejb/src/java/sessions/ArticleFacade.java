package sessions;

import entities.Article;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ArticleFacade extends AbstractFacade<Article> implements ArticleFacadeLocal {

    @PersistenceContext(unitName = "Gestion_Stock_Oasis-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return this.em;
    }

    public ArticleFacade() {
        super(Article.class);
    }

    @Override
    public Long nextVal() {
        try {
            Query query = this.em.createQuery("SELECT MAX(a.idarticle) FROM Article a");
            Long result = (Long) query.getSingleResult();
            if (result == null) {
                return 1L;
            }
            return (result + 1L);
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public Long nextValByIdstructure(int idStructure) {
        try {
            Query query = this.em.createQuery("SELECT MAX(a.idarticle) FROM Article a WHERE a.parametrage.id=:idParametre")
                    .setParameter("idParametre", idStructure);
            Long result = (Long) query.getSingleResult();
            if (result == null) {
                return 1L;
            }
            return (result + 1L);
        } catch (Exception e) {
            return 1L;
        }
    }

    @Override
    public List<Article> findAllRange(int idStructure) {
        return this.em.createQuery("SELECT a FROM Article a WHERE a.parametrage.id=:id ORDER BY a.code")
                .setParameter("id", idStructure)
                .getResultList();
    }

    @Override
    public Article findByCode(int idStructure, String code) {
        try {
            Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.code=:code ANd a.parametrage.id=:id");
            query.setParameter("code", code).setParameter("id", idStructure);
            List list = query.getResultList();
            if (!list.isEmpty()) {
                return (Article) list.get(0);
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Article> findByPerissable(int idStructure, boolean perrissable) {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.perissable=:perrissable AND a.parametrage.id=:id ORDER BY a.code");
        query.setParameter("perrissable", perrissable).setParameter("id", idStructure);
        return query.getResultList();
    }

    @Override
    public List<Article> findByFamille(long idfamille) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.idfamille.idfamille=:idfamille ORDER BY a.libelle");
        query.setParameter("idfamille", idfamille);
        return query.getResultList();
    }

    @Override
    public List<Article> findByFamille(long idfamille, boolean positif) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.idfamille.idfamille=:idfamille AND a.quantitestock>0D ORDER BY a.libelle");
        query.setParameter("idfamille", idfamille);
        return query.getResultList();
    }

    @Override
    public List<Article> findAllRange(int idStructure, boolean etat) {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.etat=:etat AND a.parametrage.id=:id ORDER BY a.libelle");
        query.setParameter("etat", etat).setParameter("id", idStructure);
        return query.getResultList();
    }

    @Override
    public List<Article> findByEtatQuantityPositif(int idStructure, boolean etat) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.etat=:etat AND a.quantitestock>0D AND a.parametrage.id=:id ORDER BY a.libelle");
        query.setParameter("etat", etat).setParameter("id", idStructure);
        return query.getResultList();
    }
}
