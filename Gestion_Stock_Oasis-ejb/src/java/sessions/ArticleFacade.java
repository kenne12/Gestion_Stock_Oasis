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
        Query query = this.em.createQuery("SELECT MAX(a.idarticle) FROM Article a");
        Long result = (Long) query.getSingleResult();
        if (result == null) {
            result = 1L;
        } else {
            result = (result + 1L);
        }
        return result;
    }

    @Override
    public List<Article> findAllRange() {
        return this.em.createQuery("SELECT a FROM Article a ORDER BY a.libelle").getResultList();
    }

    @Override
    public Article findByCode(String code) {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.code=:code");
        query.setParameter("code", code);
        List list = query.getResultList();
        if (!list.isEmpty()) {
            return (Article) list.get(0);
        }
        return null;
    }

    @Override
    public List<Article> findByPerissable(boolean perrissable) {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.perissable=:perrissable ORDER BY a.libelle");
        query.setParameter("perrissable", perrissable);
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
    public List<Article> findAllRange(boolean etat) {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.etat=:etat ORDER BY a.libelle");
        query.setParameter("etat", etat);
        return query.getResultList();
    }

    @Override
    public List<Article> findByEtatQuantityPositif(boolean etat) throws Exception {
        Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.etat=:etat AND a.quantitestock>0D ORDER BY a.libelle");
        query.setParameter("etat", etat);
        return query.getResultList();
    }
}
