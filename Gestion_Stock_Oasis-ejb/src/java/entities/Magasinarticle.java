/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Magasinarticle.findAll", query = "SELECT m FROM Magasinarticle m"),
    @NamedQuery(name = "Magasinarticle.findByIdmagasinarticle", query = "SELECT m FROM Magasinarticle m WHERE m.idmagasinarticle = :idmagasinarticle"),
    @NamedQuery(name = "Magasinarticle.findByQuantite", query = "SELECT m FROM Magasinarticle m WHERE m.quantite = :quantite"),
    @NamedQuery(name = "Magasinarticle.findByQuantitemultiple", query = "SELECT m FROM Magasinarticle m WHERE m.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Magasinarticle.findByUnite", query = "SELECT m FROM Magasinarticle m WHERE m.unite = :unite"),
    @NamedQuery(name = "Magasinarticle.findByEtat", query = "SELECT m FROM Magasinarticle m WHERE m.etat = :etat"),
    @NamedQuery(name = "Magasinarticle.findByQuantitereduite", query = "SELECT m FROM Magasinarticle m WHERE m.quantitereduite = :quantitereduite"),
    @NamedQuery(name = "Magasinarticle.findByQuantitevirtuelle", query = "SELECT m FROM Magasinarticle m WHERE m.quantitevirtuelle = :quantitevirtuelle"),
    @NamedQuery(name = "Magasinarticle.findByQuantitesecurite", query = "SELECT m FROM Magasinarticle m WHERE m.quantitesecurite = :quantitesecurite")})
public class Magasinarticle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idmagasinarticle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double quantitemultiple;
    private Double unite;
    private Boolean etat;
    private Double quantitereduite;
    private Double quantitevirtuelle;
    private Double quantitesecurite;
    @JoinColumn(name = "idarticle", referencedColumnName = "idarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article idarticle;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @OneToMany(mappedBy = "idmagasinarticle", fetch = FetchType.LAZY)
    private List<Lignedemande> lignedemandeList;
    @OneToMany(mappedBy = "idmagasinarticle", fetch = FetchType.LAZY)
    private List<Magasinlot> magasinlotList;

    public Magasinarticle() {
        idarticle = new Article();
        idmagasin = new Magasin();
    }

    public Magasinarticle(Long idmagasinarticle) {
        this.idmagasinarticle = idmagasinarticle;
    }

    public Long getIdmagasinarticle() {
        return idmagasinarticle;
    }

    public void setIdmagasinarticle(Long idmagasinarticle) {
        this.idmagasinarticle = idmagasinarticle;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(Double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
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

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
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

    public Article getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(Article idarticle) {
        this.idarticle = idarticle;
    }

    public Magasin getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Magasin idmagasin) {
        this.idmagasin = idmagasin;
    }

    @XmlTransient
    public List<Lignedemande> getLignedemandeList() {
        return lignedemandeList;
    }

    public void setLignedemandeList(List<Lignedemande> lignedemandeList) {
        this.lignedemandeList = lignedemandeList;
    }

    @XmlTransient
    public List<Magasinlot> getMagasinlotList() {
        return magasinlotList;
    }

    public void setMagasinlotList(List<Magasinlot> magasinlotList) {
        this.magasinlotList = magasinlotList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmagasinarticle != null ? idmagasinarticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magasinarticle)) {
            return false;
        }
        Magasinarticle other = (Magasinarticle) object;
        if ((this.idmagasinarticle == null && other.idmagasinarticle != null) || (this.idmagasinarticle != null && !this.idmagasinarticle.equals(other.idmagasinarticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Magasinarticle[ idmagasinarticle=" + idmagasinarticle + " ]";
    }

}
