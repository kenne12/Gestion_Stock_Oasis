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
    @NamedQuery(name = "Lignedemande.findAll", query = "SELECT l FROM Lignedemande l"),
    @NamedQuery(name = "Lignedemande.findByIdlignedemande", query = "SELECT l FROM Lignedemande l WHERE l.idlignedemande = :idlignedemande"),
    @NamedQuery(name = "Lignedemande.findByQuantite", query = "SELECT l FROM Lignedemande l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignedemande.findByQuantitemultiple", query = "SELECT l FROM Lignedemande l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Lignedemande.findByUnite", query = "SELECT l FROM Lignedemande l WHERE l.unite = :unite"),
    @NamedQuery(name = "Lignedemande.findByMontant", query = "SELECT l FROM Lignedemande l WHERE l.montant = :montant"),
    @NamedQuery(name = "Lignedemande.findByTauxsatisfaction", query = "SELECT l FROM Lignedemande l WHERE l.tauxsatisfaction = :tauxsatisfaction"),
    @NamedQuery(name = "Lignedemande.findByQuantitereduite", query = "SELECT l FROM Lignedemande l WHERE l.quantitereduite = :quantitereduite"),
    @NamedQuery(name = "Lignedemande.findByQtestock", query = "SELECT l FROM Lignedemande l WHERE l.qtestock = :qtestock")})
public class Lignedemande implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignedemande;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double quantitemultiple;
    private Double unite;
    private Double montant;
    private Double tauxsatisfaction;
    private Double quantitereduite;
    private Double qtestock;
    @JoinColumn(name = "iddemande", referencedColumnName = "iddemande")
    @ManyToOne(fetch = FetchType.LAZY)
    private Demande iddemande;
    @JoinColumn(name = "idmagasinarticle", referencedColumnName = "idmagasinarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinarticle idmagasinarticle;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;

    public Lignedemande() {
    }

    public Lignedemande(Long idlignedemande) {
        this.idlignedemande = idlignedemande;
    }

    public Long getIdlignedemande() {
        return idlignedemande;
    }

    public void setIdlignedemande(Long idlignedemande) {
        this.idlignedemande = idlignedemande;
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

    public Double getTauxsatisfaction() {
        return tauxsatisfaction;
    }

    public void setTauxsatisfaction(Double tauxsatisfaction) {
        this.tauxsatisfaction = tauxsatisfaction;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public Double getQtestock() {
        return qtestock;
    }

    public void setQtestock(Double qtestock) {
        this.qtestock = qtestock;
    }

    public Demande getIddemande() {
        return iddemande;
    }

    public void setIddemande(Demande iddemande) {
        this.iddemande = iddemande;
    }

    public Magasinarticle getIdmagasinarticle() {
        return idmagasinarticle;
    }

    public void setIdmagasinarticle(Magasinarticle idmagasinarticle) {
        this.idmagasinarticle = idmagasinarticle;
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
        hash += (idlignedemande != null ? idlignedemande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignedemande)) {
            return false;
        }
        Lignedemande other = (Lignedemande) object;
        if ((this.idlignedemande == null && other.idlignedemande != null) || (this.idlignedemande != null && !this.idlignedemande.equals(other.idlignedemande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignedemande[ idlignedemande=" + idlignedemande + " ]";
    }
    
}
