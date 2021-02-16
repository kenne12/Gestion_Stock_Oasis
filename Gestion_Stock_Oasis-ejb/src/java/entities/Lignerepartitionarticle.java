/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lignerepartitionarticle.findAll", query = "SELECT l FROM Lignerepartitionarticle l"),
    @NamedQuery(name = "Lignerepartitionarticle.findByIdlignerepartitionarticle", query = "SELECT l FROM Lignerepartitionarticle l WHERE l.idlignerepartitionarticle = :idlignerepartitionarticle"),
    @NamedQuery(name = "Lignerepartitionarticle.findByQuantite", query = "SELECT l FROM Lignerepartitionarticle l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignerepartitionarticle.findByQuantitemultiple", query = "SELECT l FROM Lignerepartitionarticle l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Lignerepartitionarticle.findByUnite", query = "SELECT l FROM Lignerepartitionarticle l WHERE l.unite = :unite"),
    @NamedQuery(name = "Lignerepartitionarticle.findByMontant", query = "SELECT l FROM Lignerepartitionarticle l WHERE l.montant = :montant"),
    @NamedQuery(name = "Lignerepartitionarticle.findByQuantitereduite", query = "SELECT l FROM Lignerepartitionarticle l WHERE l.quantitereduite = :quantitereduite")})
public class Lignerepartitionarticle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignerepartitionarticle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double quantitemultiple;
    private Double unite;
    private Double montant;
    private Double quantitereduite;
    @JoinColumn(name = "idlignerepartitiontemp", referencedColumnName = "idlignerepartitiontemp")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lignerepartitiontemp idlignerepartitiontemp;
    @JoinColumn(name = "idmagasinlot", referencedColumnName = "idmagasinlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinlot idmagasinlot;
    @JoinColumn(name = "idrepartitionarticle", referencedColumnName = "idrepartitionarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Repartitionarticle idrepartitionarticle;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;

    public Lignerepartitionarticle() {
    }

    public Lignerepartitionarticle(Long idlignerepartitionarticle) {
        this.idlignerepartitionarticle = idlignerepartitionarticle;
    }

    public Long getIdlignerepartitionarticle() {
        return idlignerepartitionarticle;
    }

    public void setIdlignerepartitionarticle(Long idlignerepartitionarticle) {
        this.idlignerepartitionarticle = idlignerepartitionarticle;
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

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public Lignerepartitiontemp getIdlignerepartitiontemp() {
        return idlignerepartitiontemp;
    }

    public void setIdlignerepartitiontemp(Lignerepartitiontemp idlignerepartitiontemp) {
        this.idlignerepartitiontemp = idlignerepartitiontemp;
    }

    public Magasinlot getIdmagasinlot() {
        return idmagasinlot;
    }

    public void setIdmagasinlot(Magasinlot idmagasinlot) {
        this.idmagasinlot = idmagasinlot;
    }

    public Repartitionarticle getIdrepartitionarticle() {
        return idrepartitionarticle;
    }

    public void setIdrepartitionarticle(Repartitionarticle idrepartitionarticle) {
        this.idrepartitionarticle = idrepartitionarticle;
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
        hash += (idlignerepartitionarticle != null ? idlignerepartitionarticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignerepartitionarticle)) {
            return false;
        }
        Lignerepartitionarticle other = (Lignerepartitionarticle) object;
        if ((this.idlignerepartitionarticle == null && other.idlignerepartitionarticle != null) || (this.idlignerepartitionarticle != null && !this.idlignerepartitionarticle.equals(other.idlignerepartitionarticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignerepartitionarticle[ idlignerepartitionarticle=" + idlignerepartitionarticle + " ]";
    }
    
}
