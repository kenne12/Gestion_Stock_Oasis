/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
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
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a")
    ,
    @NamedQuery(name = "Article.findByIdarticle", query = "SELECT a FROM Article a WHERE a.idarticle = :idarticle")
    ,
    @NamedQuery(name = "Article.findByCode", query = "SELECT a FROM Article a WHERE a.code = :code")
    ,
    @NamedQuery(name = "Article.findByLibelle", query = "SELECT a FROM Article a WHERE a.libelle = :libelle")
    ,
    @NamedQuery(name = "Article.findByDescription", query = "SELECT a FROM Article a WHERE a.description = :description")
    ,
    @NamedQuery(name = "Article.findByCoutachat", query = "SELECT a FROM Article a WHERE a.coutachat = :coutachat")
    ,
    @NamedQuery(name = "Article.findByPoids", query = "SELECT a FROM Article a WHERE a.poids = :poids")
    ,
    @NamedQuery(name = "Article.findByPrixunit", query = "SELECT a FROM Article a WHERE a.prixunit = :prixunit")
    ,
    @NamedQuery(name = "Article.findByTva", query = "SELECT a FROM Article a WHERE a.tva = :tva")
    ,
    @NamedQuery(name = "Article.findByQuantitestock", query = "SELECT a FROM Article a WHERE a.quantitestock = :quantitestock")
    ,
    @NamedQuery(name = "Article.findByQuantitemin", query = "SELECT a FROM Article a WHERE a.quantitemin = :quantitemin")
    ,
    @NamedQuery(name = "Article.findByQuantitealerte", query = "SELECT a FROM Article a WHERE a.quantitealerte = :quantitealerte")
    ,
    @NamedQuery(name = "Article.findByQuantiteavarie", query = "SELECT a FROM Article a WHERE a.quantiteavarie = :quantiteavarie")
    ,
    @NamedQuery(name = "Article.findByQuantitepv", query = "SELECT a FROM Article a WHERE a.quantitepv = :quantitepv")
    ,
    @NamedQuery(name = "Article.findByQuantiteminpv", query = "SELECT a FROM Article a WHERE a.quantiteminpv = :quantiteminpv")
    ,
    @NamedQuery(name = "Article.findByQuantitealertepv", query = "SELECT a FROM Article a WHERE a.quantitealertepv = :quantitealertepv")
    ,
    @NamedQuery(name = "Article.findByPhoto", query = "SELECT a FROM Article a WHERE a.photo = :photo")
    ,
    @NamedQuery(name = "Article.findByPhotoRelatif", query = "SELECT a FROM Article a WHERE a.photoRelatif = :photoRelatif")
    ,
    @NamedQuery(name = "Article.findByDateEnregistre", query = "SELECT a FROM Article a WHERE a.dateEnregistre = :dateEnregistre")
    ,
    @NamedQuery(name = "Article.findByDerniereModif", query = "SELECT a FROM Article a WHERE a.derniereModif = :derniereModif")
    ,
    @NamedQuery(name = "Article.findByPerissable", query = "SELECT a FROM Article a WHERE a.perissable = :perissable")
    ,
    @NamedQuery(name = "Article.findByUnite", query = "SELECT a FROM Article a WHERE a.unite = :unite")
    ,
    @NamedQuery(name = "Article.findByEtat", query = "SELECT a FROM Article a WHERE a.etat = :etat")
    ,
    @NamedQuery(name = "Article.findByFabricant", query = "SELECT a FROM Article a WHERE a.fabricant = :fabricant")
    ,
    @NamedQuery(name = "Article.findByNbjralerte", query = "SELECT a FROM Article a WHERE a.nbjralerte = :nbjralerte")
    ,
    @NamedQuery(name = "Article.findByQuantitemultiple", query = "SELECT a FROM Article a WHERE a.quantitemultiple = :quantitemultiple")
    ,
    @NamedQuery(name = "Article.findByUnitesortie", query = "SELECT a FROM Article a WHERE a.unitesortie = :unitesortie")
    ,
    @NamedQuery(name = "Article.findByQuantitereduite", query = "SELECT a FROM Article a WHERE a.quantitereduite = :quantitereduite")
    ,
    @NamedQuery(name = "Article.findByUniteentree", query = "SELECT a FROM Article a WHERE a.uniteentree = :uniteentree")
    ,
    @NamedQuery(name = "Article.findByQuantitevirtuelle", query = "SELECT a FROM Article a WHERE a.quantitevirtuelle = :quantitevirtuelle")
    ,
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
    @Size(max = 254)
    private String description;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private double coutachat;
    private double poids;
    private double prixunit;
    private boolean tva;
    private double quantitestock;
    private int quantitemin;
    private double quantitealerte;
    private int quantiteavarie;
    private int quantitepv;
    private int quantiteminpv;
    private int quantitealertepv;
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
    private double unite;
    private Boolean etat;
    @Size(max = 100)
    private String fabricant;
    private int nbjralerte;
    private double quantitemultiple;
    private double unitesortie;
    private double quantitereduite;
    private double uniteentree;
    private double quantitevirtuelle;
    private double quantitesecurite;
    @OneToMany(mappedBy = "idarticle", fetch = FetchType.LAZY)
    private List<Lot> lotList = new ArrayList<>();
    @OneToMany(mappedBy = "idarticle", fetch = FetchType.LAZY)
    private List<Magasinarticle> magasinarticleList = new ArrayList<>();
    @OneToMany(mappedBy = "idarticle", fetch = FetchType.LAZY)
    private List<Lignecommandefournisseur> lignecommandefournisseurList = new ArrayList<>();
    @JoinColumn(name = "idfamille", referencedColumnName = "idfamille")
    @ManyToOne(fetch = FetchType.LAZY)
    private Famille idfamille;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;
    @JoinColumn(name = "idstructure", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Parametrage parametrage;

    @Column(name = "idunite_detail")
    private long idUniteDetail;

    @Column(name = "prix_achat_detail")
    private double prixAchatDetail;

    @Column(name = "prix_vente_detail")
    private double prixVenteDetail;

    private void initConstructor() {
        idunite = new Unite();
        idfamille = new Famille();
        parametrage = new Parametrage();
        idUniteDetail = 0l;
        quantitemultiple = 0;
        unitesortie = 1;
        quantitereduite = 0;
        uniteentree = 0;
        quantitevirtuelle = 0;
        quantitesecurite = 0;

        quantitestock = 0;
        quantitemin = 0;
        quantitealerte = 0;
        quantiteavarie = 0;
        quantitepv = 0;
        quantiteminpv = 0;
        quantitealertepv = 0;

        coutachat = 0;
        poids = 0;
        unite = 1;
    }

    public Article() {
        super();
        this.initConstructor();
    }

    public Article(Long idarticle) {
        this.idarticle = idarticle;
        this.initConstructor();
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

    public double getPoids() {
        return poids;
    }

    public void setPoids(double poids) {
        this.poids = poids;
    }

    public double getPrixunit() {
        return prixunit;
    }

    public void setPrixunit(double prixunit) {
        this.prixunit = prixunit;
    }

    public double getQuantitestock() {
        return quantitestock;
    }

    public void setQuantitestock(double quantitestock) {
        this.quantitestock = quantitestock;
    }

    public int getQuantitemin() {
        return quantitemin;
    }

    public void setQuantitemin(int quantitemin) {
        this.quantitemin = quantitemin;
    }

    public double getQuantitealerte() {
        return quantitealerte;
    }

    public void setQuantitealerte(double quantitealerte) {
        this.quantitealerte = quantitealerte;
    }

    public int getQuantiteavarie() {
        return quantiteavarie;
    }

    public void setQuantiteavarie(int quantiteavarie) {
        this.quantiteavarie = quantiteavarie;
    }

    public int getQuantitepv() {
        return quantitepv;
    }

    public void setQuantitepv(int quantitepv) {
        this.quantitepv = quantitepv;
    }

    public int getQuantiteminpv() {
        return quantiteminpv;
    }

    public void setQuantiteminpv(int quantiteminpv) {
        this.quantiteminpv = quantiteminpv;
    }

    public int getQuantitealertepv() {
        return quantitealertepv;
    }

    public void setQuantitealertepv(int quantitealertepv) {
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

    public double getUnite() {
        return unite;
    }

    public void setUnite(double unite) {
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

    public double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
    }

    public double getUnitesortie() {
        return unitesortie;
    }

    public void setUnitesortie(double unitesortie) {
        this.unitesortie = unitesortie;
    }

    public double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public double getUniteentree() {
        return uniteentree;
    }

    public void setUniteentree(double uniteentree) {
        this.uniteentree = uniteentree;
    }

    public double getQuantitevirtuelle() {
        return quantitevirtuelle;
    }

    public void setQuantitevirtuelle(double quantitevirtuelle) {
        this.quantitevirtuelle = quantitevirtuelle;
    }

    public double getQuantitesecurite() {
        return quantitesecurite;
    }

    public void setQuantitesecurite(double quantitesecurite) {
        this.quantitesecurite = quantitesecurite;
    }

    public long getIdUniteDetail() {
        return idUniteDetail;
    }

    public void setIdUniteDetail(long idUniteDetail) {
        this.idUniteDetail = idUniteDetail;
    }

    public boolean isTva() {
        return tva;
    }

    public void setTva(boolean tva) {
        this.tva = tva;
    }

    public double getPrixAchatDetail() {
        return prixAchatDetail;
    }

    public void setPrixAchatDetail(double prixAchatDetail) {
        this.prixAchatDetail = prixAchatDetail;
    }

    public double getPrixVenteDetail() {
        return prixVenteDetail;
    }

    public void setPrixVenteDetail(double prixVenteDetail) {
        this.prixVenteDetail = prixVenteDetail;
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

    public Parametrage getParametrage() {
        return parametrage;
    }

    public void setParametrage(Parametrage parametrage) {
        this.parametrage = parametrage;
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
