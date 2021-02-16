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
        /* 27 */ return this.em;
    }

    public ArticleFacade() {
        /* 31 */ super(Article.class);
    }

    @Override
    public Long nextVal() {
        /* 36 */ Query query = this.em.createQuery("SELECT MAX(a.idarticle) FROM Article a");
        /* 37 */ Long result = (Long) query.getSingleResult();
        /* 38 */ if (result == null) /* 39 */ {
            result = Long.valueOf(1L);
        } else {
            /* 41 */ result = Long.valueOf(result.longValue() + 1L);
        }
        /* 43 */ return result;
    }

    @Override
    public List<Article> findAllRange() {
        /* 48 */ return this.em.createQuery("SELECT a FROM Article a ORDER BY a.libelle").getResultList();
    }

    @Override
    public Article findByCode(String code) {
        /* 53 */ Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.code=:code");
        /* 54 */ query.setParameter("code", code);
        /* 55 */ List list = query.getResultList();
        /* 56 */ if (!list.isEmpty()) {
            /* 57 */ return (Article) list.get(0);
        }
        /* 59 */ return null;
    }

    @Override
    public List<Article> findByPerissable(boolean perrissable) {
        /* 64 */ Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.perissable=:perrissable ORDER BY a.libelle");
        /* 65 */ query.setParameter("perrissable", Boolean.valueOf(perrissable));
        /* 66 */ return query.getResultList();
    }

    @Override
    public List<Article> findByFamille(long idfamille) throws Exception {
        /* 71 */ Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.idfamille.idfamille=:idfamille ORDER BY a.libelle");
        /* 72 */ query.setParameter("idfamille", Long.valueOf(idfamille));
        /* 73 */ return query.getResultList();
    }

    @Override
    public List<Article> findByFamille(long idfamille, boolean positif) throws Exception {
        /* 78 */ Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.idfamille.idfamille=:idfamille AND a.quantitestock>0D ORDER BY a.libelle");
        /* 79 */ query.setParameter("idfamille", Long.valueOf(idfamille));
        /* 80 */ return query.getResultList();
    }

    @Override
    public List<Article> findAllRange(boolean etat) {
        /* 85 */ Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.etat=:etat ORDER BY a.libelle");
        /* 86 */ query.setParameter("etat", Boolean.valueOf(etat));
        /* 87 */ return query.getResultList();
    }

    @Override
    public List<Article> findByEtatQuantityPositif(boolean etat) throws Exception {
        /* 92 */ Query query = this.em.createQuery("SELECT a FROM Article a WHERE a.etat=:etat AND a.quantitestock>0D ORDER BY a.libelle");
        /* 93 */ query.setParameter("etat", Boolean.valueOf(etat));
        /* 94 */ return query.getResultList();
    }
}
