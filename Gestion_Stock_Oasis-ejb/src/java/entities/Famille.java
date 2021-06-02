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
    @NamedQuery(name = "Famille.findAll", query = "SELECT f FROM Famille f"),
    @NamedQuery(name = "Famille.findByIdfamille", query = "SELECT f FROM Famille f WHERE f.idfamille = :idfamille"),
    @NamedQuery(name = "Famille.findByNom", query = "SELECT f FROM Famille f WHERE f.nom = :nom"),
    @NamedQuery(name = "Famille.findByCode", query = "SELECT f FROM Famille f WHERE f.code = :code"),
    @NamedQuery(name = "Famille.findByEtat", query = "SELECT f FROM Famille f WHERE f.etat = :etat"),
    @NamedQuery(name = "Famille.findByDateEnregistre", query = "SELECT f FROM Famille f WHERE f.dateEnregistre = :dateEnregistre"),
    @NamedQuery(name = "Famille.findByDerniereModif", query = "SELECT f FROM Famille f WHERE f.derniereModif = :derniereModif")})
public class Famille implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idfamille;
    @Size(max = 254)
    private String nom;
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
    @OneToMany(mappedBy = "idfamille", fetch = FetchType.LAZY)
    private List<Article> articleList;
    @JoinColumn(name = "idstructure", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Parametrage parametrage;

    public Famille() {
    }

    public Famille(Long idfamille) {
        this.idfamille = idfamille;
    }

    public Long getIdfamille() {
        return idfamille;
    }

    public void setIdfamille(Long idfamille) {
        this.idfamille = idfamille;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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

    public Parametrage getParametrage() {
        return parametrage;
    }

    public void setParametrage(Parametrage parametrage) {
        this.parametrage = parametrage;
    }

    @XmlTransient
    public List<Article> getArticleList() {
        return articleList;
    }

    public void setArticleList(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfamille != null ? idfamille.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Famille)) {
            return false;
        }
        Famille other = (Famille) object;
        if ((this.idfamille == null && other.idfamille != null) || (this.idfamille != null && !this.idfamille.equals(other.idfamille))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Famille[ idfamille=" + idfamille + " ]";
    }

}
