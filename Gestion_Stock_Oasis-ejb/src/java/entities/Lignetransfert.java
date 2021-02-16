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
    @NamedQuery(name = "Lignetransfert.findAll", query = "SELECT l FROM Lignetransfert l"),
    @NamedQuery(name = "Lignetransfert.findByIdlignetransfert", query = "SELECT l FROM Lignetransfert l WHERE l.idlignetransfert = :idlignetransfert"),
    @NamedQuery(name = "Lignetransfert.findByQuantite", query = "SELECT l FROM Lignetransfert l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignetransfert.findByUnite", query = "SELECT l FROM Lignetransfert l WHERE l.unite = :unite"),
    @NamedQuery(name = "Lignetransfert.findByQuantitemultiple", query = "SELECT l FROM Lignetransfert l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Lignetransfert.findByMontant", query = "SELECT l FROM Lignetransfert l WHERE l.montant = :montant"),
    @NamedQuery(name = "Lignetransfert.findByQuantitereduite", query = "SELECT l FROM Lignetransfert l WHERE l.quantitereduite = :quantitereduite")})
public class Lignetransfert implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignetransfert;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double unite;
    private Double quantitemultiple;
    private Double montant;
    private Double quantitereduite;
    @JoinColumn(name = "idmagasinlot", referencedColumnName = "idmagasinlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinlot idmagasinlot;
    @JoinColumn(name = "idtransfert", referencedColumnName = "idtransfert")
    @ManyToOne(fetch = FetchType.LAZY)
    private Transfert idtransfert;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;

    public Lignetransfert() {
    }

    public Lignetransfert(Long idlignetransfert) {
        this.idlignetransfert = idlignetransfert;
    }

    public Long getIdlignetransfert() {
        return idlignetransfert;
    }

    public void setIdlignetransfert(Long idlignetransfert) {
        this.idlignetransfert = idlignetransfert;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getUnite() {
        return unite;
    }

    public void setUnite(Double unite) {
        this.unite = unite;
    }

    public Double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(Double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
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

    public Magasinlot getIdmagasinlot() {
        return idmagasinlot;
    }

    public void setIdmagasinlot(Magasinlot idmagasinlot) {
        this.idmagasinlot = idmagasinlot;
    }

    public Transfert getIdtransfert() {
        return idtransfert;
    }

    public void setIdtransfert(Transfert idtransfert) {
        this.idtransfert = idtransfert;
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
        hash += (idlignetransfert != null ? idlignetransfert.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignetransfert)) {
            return false;
        }
        Lignetransfert other = (Lignetransfert) object;
        if ((this.idlignetransfert == null && other.idlignetransfert != null) || (this.idlignetransfert != null && !this.idlignetransfert.equals(other.idlignetransfert))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignetransfert[ idlignetransfert=" + idlignetransfert + " ]";
    }
    
}
