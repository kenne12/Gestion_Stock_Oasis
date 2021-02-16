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
    @NamedQuery(name = "Ligneentreedirect.findAll", query = "SELECT l FROM Ligneentreedirect l"),
    @NamedQuery(name = "Ligneentreedirect.findByIdligneentreedirect", query = "SELECT l FROM Ligneentreedirect l WHERE l.idligneentreedirect = :idligneentreedirect"),
    @NamedQuery(name = "Ligneentreedirect.findByQuantite", query = "SELECT l FROM Ligneentreedirect l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Ligneentreedirect.findByCunitaire", query = "SELECT l FROM Ligneentreedirect l WHERE l.cunitaire = :cunitaire"),
    @NamedQuery(name = "Ligneentreedirect.findByQuantitemultiple", query = "SELECT l FROM Ligneentreedirect l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Ligneentreedirect.findByUnite", query = "SELECT l FROM Ligneentreedirect l WHERE l.unite = :unite")})
public class Ligneentreedirect implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idligneentreedirect;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double cunitaire;
    private Double quantitemultiple;
    private Integer unite;
    @JoinColumn(name = "identredirect", referencedColumnName = "identredirect")
    @ManyToOne(fetch = FetchType.LAZY)
    private Entredirect identredirect;
    @JoinColumn(name = "idmagasinlot", referencedColumnName = "idmagasinlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinlot idmagasinlot;

    public Ligneentreedirect() {
    }

    public Ligneentreedirect(Long idligneentreedirect) {
        this.idligneentreedirect = idligneentreedirect;
    }

    public Long getIdligneentreedirect() {
        return idligneentreedirect;
    }

    public void setIdligneentreedirect(Long idligneentreedirect) {
        this.idligneentreedirect = idligneentreedirect;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getCunitaire() {
        return cunitaire;
    }

    public void setCunitaire(Double cunitaire) {
        this.cunitaire = cunitaire;
    }

    public Double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(Double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
    }

    public Integer getUnite() {
        return unite;
    }

    public void setUnite(Integer unite) {
        this.unite = unite;
    }

    public Entredirect getIdentredirect() {
        return identredirect;
    }

    public void setIdentredirect(Entredirect identredirect) {
        this.identredirect = identredirect;
    }

    public Magasinlot getIdmagasinlot() {
        return idmagasinlot;
    }

    public void setIdmagasinlot(Magasinlot idmagasinlot) {
        this.idmagasinlot = idmagasinlot;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idligneentreedirect != null ? idligneentreedirect.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ligneentreedirect)) {
            return false;
        }
        Ligneentreedirect other = (Ligneentreedirect) object;
        if ((this.idligneentreedirect == null && other.idligneentreedirect != null) || (this.idligneentreedirect != null && !this.idligneentreedirect.equals(other.idligneentreedirect))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ligneentreedirect[ idligneentreedirect=" + idligneentreedirect + " ]";
    }
    
}
