/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.math.BigInteger;
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
    @NamedQuery(name = "Unite.findAll", query = "SELECT u FROM Unite u"),
    @NamedQuery(name = "Unite.findByIdunite", query = "SELECT u FROM Unite u WHERE u.idunite = :idunite"),
    @NamedQuery(name = "Unite.findByIdParent", query = "SELECT u FROM Unite u WHERE u.idParent = :idParent"),
    @NamedQuery(name = "Unite.findByLibelle", query = "SELECT u FROM Unite u WHERE u.libelle = :libelle"),
    @NamedQuery(name = "Unite.findByCode", query = "SELECT u FROM Unite u WHERE u.code = :code"),
    @NamedQuery(name = "Unite.findByEtat", query = "SELECT u FROM Unite u WHERE u.etat = :etat"),
    @NamedQuery(name = "Unite.findByDateEnregistre", query = "SELECT u FROM Unite u WHERE u.dateEnregistre = :dateEnregistre"),
    @NamedQuery(name = "Unite.findByDerniereModif", query = "SELECT u FROM Unite u WHERE u.derniereModif = :derniereModif")})
public class Unite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idunite;
    @Column(name = "id_parent")
    private BigInteger idParent;
    @Size(max = 1024)
    private String libelle;
    @Size(max = 1024)
    private String code;
    @Size(max = 50)
    private String etat;
    @Column(name = "date_enregistre")
    @Temporal(TemporalType.DATE)
    private Date dateEnregistre;
    @Column(name = "derniere_modif")
    @Temporal(TemporalType.DATE)
    private Date derniereModif;
    @OneToMany(mappedBy = "idunite", fetch = FetchType.LAZY)
    private List<Lignerepartitionarticle> lignerepartitionarticleList;
    @OneToMany(mappedBy = "idunite", fetch = FetchType.LAZY)
    private List<Lignetransfert> lignetransfertList;
    @OneToMany(mappedBy = "idunite", fetch = FetchType.LAZY)
    private List<Lignecommandefournisseur> lignecommandefournisseurList;
    @OneToMany(mappedBy = "idunite", fetch = FetchType.LAZY)
    private List<Lignedemande> lignedemandeList;
    @OneToMany(mappedBy = "idunite", fetch = FetchType.LAZY)
    private List<Article> articleList;
    @OneToMany(mappedBy = "idunite", fetch = FetchType.LAZY)
    private List<Lignelivraisonclient> lignelivraisonclientList;
    @OneToMany(mappedBy = "idunite", fetch = FetchType.LAZY)
    private List<Lignelivraisonfournisseur> lignelivraisonfournisseurList;

    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin magasin;

    public Unite() {
    }

    public Unite(Long idunite) {
        this.idunite = idunite;
    }

    public Long getIdunite() {
        return idunite;
    }

    public void setIdunite(Long idunite) {
        this.idunite = idunite;
    }

    public BigInteger getIdParent() {
        return idParent;
    }

    public void setIdParent(BigInteger idParent) {
        this.idParent = idParent;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
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

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    @XmlTransient
    public List<Lignerepartitionarticle> getLignerepartitionarticleList() {
        return lignerepartitionarticleList;
    }

    public void setLignerepartitionarticleList(List<Lignerepartitionarticle> lignerepartitionarticleList) {
        this.lignerepartitionarticleList = lignerepartitionarticleList;
    }

    @XmlTransient
    public List<Lignetransfert> getLignetransfertList() {
        return lignetransfertList;
    }

    public void setLignetransfertList(List<Lignetransfert> lignetransfertList) {
        this.lignetransfertList = lignetransfertList;
    }

    @XmlTransient
    public List<Lignecommandefournisseur> getLignecommandefournisseurList() {
        return lignecommandefournisseurList;
    }

    public void setLignecommandefournisseurList(List<Lignecommandefournisseur> lignecommandefournisseurList) {
        this.lignecommandefournisseurList = lignecommandefournisseurList;
    }

    @XmlTransient
    public List<Lignedemande> getLignedemandeList() {
        return lignedemandeList;
    }

    public void setLignedemandeList(List<Lignedemande> lignedemandeList) {
        this.lignedemandeList = lignedemandeList;
    }

    @XmlTransient
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @XmlTransient
    public List<Lignelivraisonclient> getLignelivraisonclientList() {
        return lignelivraisonclientList;
    }

    public void setLignelivraisonclientList(List<Lignelivraisonclient> lignelivraisonclientList) {
        this.lignelivraisonclientList = lignelivraisonclientList;
    }

    @XmlTransient
    public List<Lignelivraisonfournisseur> getLignelivraisonfournisseurList() {
        return lignelivraisonfournisseurList;
    }

    public void setLignelivraisonfournisseurList(List<Lignelivraisonfournisseur> lignelivraisonfournisseurList) {
        this.lignelivraisonfournisseurList = lignelivraisonfournisseurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idunite != null ? idunite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Unite)) {
            return false;
        }
        Unite other = (Unite) object;
        if ((this.idunite == null && other.idunite != null) || (this.idunite != null && !this.idunite.equals(other.idunite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Unite[ idunite=" + idunite + " ]";
    }

}
