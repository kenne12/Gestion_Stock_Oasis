/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findByIdarticle", query = "SELECT a FROM Article a WHERE a.idarticle = :idarticle"),
    @NamedQuery(name = "Article.findByCode", query = "SELECT a FROM Article a WHERE a.code = :code"),
    @NamedQuery(name = "Article.findByLibelle", query = "SELECT a FROM Article a WHERE a.libelle = :libelle"),
    @NamedQuery(name = "Article.findByDescription", query = "SELECT a FROM Article a WHERE a.description = :description"),
    @NamedQuery(name = "Article.findByCoutachat", query = "SELECT a FROM Article a WHERE a.coutachat = :coutachat"),
    @NamedQuery(name = "Article.findByPoids", query = "SELECT a FROM Article a WHERE a.poids = :poids"),
    @NamedQuery(name = "Article.findByPrixunit", query = "SELECT a FROM Article a WHERE a.prixunit = :prixunit"),
    @NamedQuery(name = "Article.findByTva", query = "SELECT a FROM Article a WHERE a.tva = :tva"),
    @NamedQuery(name = "Article.findByQuantitestock", query = "SELECT a FROM Article a WHERE a.quantitestock = :quantitestock"),
    @NamedQuery(name = "Article.findByQuantitemin", query = "SELECT a FROM Article a WHERE a.quantitemin = :quantitemin"),
    @NamedQuery(name = "Article.findByQuantitealerte", query = "SELECT a FROM Article a WHERE a.quantitealerte = :quantitealerte"),
    @NamedQuery(name = "Article.findByQuantiteavarie", query = "SELECT a FROM Article a WHERE a.quantiteavarie = :quantiteavarie"),
    @NamedQuery(name = "Article.findByQuantitepv", query = "SELECT a FROM Article a WHERE a.quantitepv = :quantitepv"),
    @NamedQuery(name = "Article.findByQuantiteminpv", query = "SELECT a FROM Article a WHERE a.quantiteminpv = :quantiteminpv"),
    @NamedQuery(name = "Article.findByQuantitealertepv", query = "SELECT a FROM Article a WHERE a.quantitealertepv = :quantitealertepv"),
    @NamedQuery(name = "Article.findByPhoto", query = "SELECT a FROM Article a WHERE a.photo = :photo"),
    @NamedQuery(name = "Article.findByPhotoRelatif", query = "SELECT a FROM Article a WHERE a.photoRelatif = :photoRelatif"),
    @NamedQuery(name = "Article.findByDateEnregistre", query = "SELECT a FROM Article a WHERE a.dateEnregistre = :dateEnregistre"),
    @NamedQuery(name = "Article.findByDerniereModif", query = "SELECT a FROM Article a WHERE a.derniereModif = :derniereModif"),
    @NamedQuery(name = "Article.findByPerissable", query = "SELECT a FROM Article a WHERE a.perissable = :perissable"),
    @NamedQuery(name = "Article.findByUnite", query = "SELECT a FROM Article a WHERE a.unite = :unite"),
    @NamedQuery(name = "Article.findByEtat", query = "SELECT a FROM Article a WHERE a.etat = :etat"),
    @NamedQuery(name = "Article.findByFabricant", query = "SELECT a FROM Article a WHERE a.fabricant = :fabricant"),
    @NamedQuery(name = "Article.findByNbjralerte", query = "SELECT a FROM Article a WHERE a.nbjralerte = :nbjralerte"),
    @NamedQuery(name = "Article.findByQuantitemultiple", query = "SELECT a FROM Article a WHERE a.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Article.findByUnitesortie", query = "SELECT a FROM Article a WHERE a.unitesortie = :unitesortie"),
    @NamedQuery(name = "Article.findByQuantitereduite", query = "SELECT a FROM Article a WHERE a.quantitereduite = :quantitereduite"),
    @NamedQuery(name = "Article.findByUniteentree", query = "SELECT a FROM Article a WHERE a.uniteentree = :uniteentree"),
    @NamedQuery(name = "Article.findByQuantitevirtuelle", query = "SELECT a FROM Article a WHERE a.quantitevirtuelle = :quantitevirtuelle"),
    @NamedQuery(name = "Article.findByQuantitesecurite", query = "SELECT a FROM Article a WHERE a.quantitesecurite = :quantitesecurite")})
public class Article implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idarticle;
    @Size(max = 50)
    private String code;
    @Size(max = 254)
    private String libelle;
    @Size(max = 2147483647)
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double coutachat;
    private Double poids;
    private Double prixunit;
    private Boolean tva;
    private Double quantitestock;
    private Integer quantitemin;
    private Double quantitealerte;
    private Integer quantiteavarie;
    private Integer quantitepv;
    private Integer quantiteminpv;
    private Integer quantitealertepv;
    @Size(max = 254)
    private String photo;
    @Size(max = 254)
    @Column(name = "photo_relatif")
    private String photoRelatif;
    @Column(name = "date_enregistre")
    @Temporal(TemporalType.DATE)
    private Date dateEnregistre;
    @Column(name = "derniere_modif")
    @Temporal(TemporalType.DATE)
    private Date derniereModif;
    private Boolean perissable;
    private Double unite;
    private Boolean etat;
    @Size(max = 2147483647)
    private String fabricant;
    private Integer nbjralerte;
    private Double quantitemultiple;
    private Double unitesortie;
    private Double quantitereduite;
    private Double uniteentree;
    private Double quantitevirtuelle;
    private Double quantitesecurite;
    @OneToMany(mappedBy = "idarticle", fetch = FetchType.LAZY)
    private List<Lot> lotList;
    @OneToMany(mappedBy = "idarticle", fetch = FetchType.LAZY)
    private List<Magasinarticle> magasinarticleList;
    @OneToMany(mappedBy = "idarticle", fetch = FetchType.LAZY)
    private List<Lignecommandefournisseur> lignecommandefournisseurList;
    @JoinColumn(name = "idfamille", referencedColumnName = "idfamille")
    @ManyToOne(fetch = FetchType.LAZY)
    private Famille idfamille;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;

    public Article() {
    }

    public Article(Long idarticle) {
        this.idarticle = idarticle;
    }

    public Long getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(Long idarticle) {
        this.idarticle = idarticle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getCoutachat() {
        return coutachat;
    }

    public void setCoutachat(Double coutachat) {
        this.coutachat = coutachat;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Double getPrixunit() {
        return prixunit;
    }

    public void setPrixunit(Double prixunit) {
        this.prixunit = prixunit;
    }

    public Boolean getTva() {
        return tva;
    }

    public void setTva(Boolean tva) {
        this.tva = tva;
    }

    public Double getQuantitestock() {
        return quantitestock;
    }

    public void setQuantitestock(Double quantitestock) {
        this.quantitestock = quantitestock;
    }

    public Integer getQuantitemin() {
        return quantitemin;
    }

    public void setQuantitemin(Integer quantitemin) {
        this.quantitemin = quantitemin;
    }

    public Double getQuantitealerte() {
        return quantitealerte;
    }

    public void setQuantitealerte(Double quantitealerte) {
        this.quantitealerte = quantitealerte;
    }

    public Integer getQuantiteavarie() {
        return quantiteavarie;
    }

    public void setQuantiteavarie(Integer quantiteavarie) {
        this.quantiteavarie = quantiteavarie;
    }

    public Integer getQuantitepv() {
        return quantitepv;
    }

    public void setQuantitepv(Integer quantitepv) {
        this.quantitepv = quantitepv;
    }

    public Integer getQuantiteminpv() {
        return quantiteminpv;
    }

    public void setQuantiteminpv(Integer quantiteminpv) {
        this.quantiteminpv = quantiteminpv;
    }

    public Integer getQuantitealertepv() {
        return quantitealertepv;
    }

    public void setQuantitealertepv(Integer quantitealertepv) {
        this.quantitealertepv = quantitealertepv;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPhotoRelatif() {
        return photoRelatif;
    }

    public void setPhotoRelatif(String photoRelatif) {
        this.photoRelatif = photoRelatif;
    }

    public Date getDateEnregistre() {
        return dateEnregistre;
    }

    public void setDateEnregistre(Date dateEnregistre) {
        this.dateEnregistre = dateEnregistre;
    }

    public Date getDerniereModif() {
        return derniereModif;
    }

    public void setDerniereModif(Date derniereModif) {
        this.derniereModif = derniereModif;
    }

    public Boolean getPerissable() {
        return perissable;
    }

    public void setPerissable(Boolean perissable) {
        this.perissable = perissable;
    }

    public Double getUnite() {
        return unite;
    }

    public void setUnite(Double unite) {
        this.unite = unite;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public String getFabricant() {
        return fabricant;
    }

    public void setFabricant(String fabricant) {
        this.fabricant = fabricant;
    }

    public Integer getNbjralerte() {
        return nbjralerte;
    }

    public void setNbjralerte(Integer nbjralerte) {
        this.nbjralerte = nbjralerte;
    }

    public Double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(Double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
    }

    public Double getUnitesortie() {
        return unitesortie;
    }

    public void setUnitesortie(Double unitesortie) {
        this.unitesortie = unitesortie;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public Double getUniteentree() {
        return uniteentree;
    }

    public void setUniteentree(Double uniteentree) {
        this.uniteentree = uniteentree;
    }

    public Double getQuantitevirtuelle() {
        return quantitevirtuelle;
    }

    public void setQuantitevirtuelle(Double quantitevirtuelle) {
        this.quantitevirtuelle = quantitevirtuelle;
    }

    public Double getQuantitesecurite() {
        return quantitesecurite;
    }

    public void setQuantitesecurite(Double quantitesecurite) {
        this.quantitesecurite = quantitesecurite;
    }

    @XmlTransient
    public List<Lot> getLotList() {
        return lotList;
    }

    public void setLotList(List<Lot> lotList) {
        this.lotList = lotList;
    }

    @XmlTransient
    public List<Magasinarticle> getMagasinarticleList() {
        return magasinarticleList;
    }

    public void setMagasinarticleList(List<Magasinarticle> magasinarticleList) {
        this.magasinarticleList = magasinarticleList;
    }

    @XmlTransient
    public List<Lignecommandefournisseur> getLignecommandefournisseurList() {
        return lignecommandefournisseurList;
    }

    public void setLignecommandefournisseurList(List<Lignecommandefournisseur> lignecommandefournisseurList) {
        this.lignecommandefournisseurList = lignecommandefournisseurList;
    }

    public Famille getIdfamille() {
        return idfamille;
    }

    public void setIdfamille(Famille idfamille) {
        this.idfamille = idfamille;
    }

    public Unite getIdunite() {
        return idunite;
    }

    public void setIdunite(Unite idunite) {
        this.idunite = idunite;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idarticle != null ? idarticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.idarticle == null && other.idarticle != null) || (this.idarticle != null && !this.idarticle.equals(other.idarticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Article[ idarticle=" + idarticle + " ]";
    }
    
}
