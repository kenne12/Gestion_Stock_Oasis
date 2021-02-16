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
    @NamedQuery(name = "Lignerepartitiontemp.findAll", query = "SELECT l FROM Lignerepartitiontemp l"),
    @NamedQuery(name = "Lignerepartitiontemp.findByIdlignerepartitiontemp", query = "SELECT l FROM Lignerepartitiontemp l WHERE l.idlignerepartitiontemp = :idlignerepartitiontemp"),
    @NamedQuery(name = "Lignerepartitiontemp.findByQuantite", query = "SELECT l FROM Lignerepartitiontemp l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignerepartitiontemp.findByQuantitemultiple", query = "SELECT l FROM Lignerepartitiontemp l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Lignerepartitiontemp.findByUnite", query = "SELECT l FROM Lignerepartitiontemp l WHERE l.unite = :unite"),
    @NamedQuery(name = "Lignerepartitiontemp.findByMontanttotal", query = "SELECT l FROM Lignerepartitiontemp l WHERE l.montanttotal = :montanttotal"),
    @NamedQuery(name = "Lignerepartitiontemp.findByQuantitereduite", query = "SELECT l FROM Lignerepartitiontemp l WHERE l.quantitereduite = :quantitereduite")})
public class Lignerepartitiontemp implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignerepartitiontemp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double quantitemultiple;
    private Double unite;
    private Double montanttotal;
    private Double quantitereduite;
    @OneToMany(mappedBy = "idlignerepartitiontemp", fetch = FetchType.LAZY)
    private List<Lignerepartitionarticle> lignerepartitionarticleList;
    @JoinColumn(name = "idlot", referencedColumnName = "idlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot idlot;
    @JoinColumn(name = "idrepartitionarticle", referencedColumnName = "idrepartitionarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Repartitionarticle idrepartitionarticle;

    public Lignerepartitiontemp() {
    }

    public Lignerepartitiontemp(Long idlignerepartitiontemp) {
        this.idlignerepartitiontemp = idlignerepartitiontemp;
    }

    public Long getIdlignerepartitiontemp() {
        return idlignerepartitiontemp;
    }

    public void setIdlignerepartitiontemp(Long idlignerepartitiontemp) {
        this.idlignerepartitiontemp = idlignerepartitiontemp;
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

    public Double getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(Double montanttotal) {
        this.montanttotal = montanttotal;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    @XmlTransient
    public List<Lignerepartitionarticle> getLignerepartitionarticleList() {
        return lignerepartitionarticleList;
    }

    public void setLignerepartitionarticleList(List<Lignerepartitionarticle> lignerepartitionarticleList) {
        this.lignerepartitionarticleList = lignerepartitionarticleList;
    }

    public Lot getIdlot() {
        return idlot;
    }

    public void setIdlot(Lot idlot) {
        this.idlot = idlot;
    }

    public Repartitionarticle getIdrepartitionarticle() {
        return idrepartitionarticle;
    }

    public void setIdrepartitionarticle(Repartitionarticle idrepartitionarticle) {
        this.idrepartitionarticle = idrepartitionarticle;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlignerepartitiontemp != null ? idlignerepartitiontemp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignerepartitiontemp)) {
            return false;
        }
        Lignerepartitiontemp other = (Lignerepartitiontemp) object;
        if ((this.idlignerepartitiontemp == null && other.idlignerepartitiontemp != null) || (this.idlignerepartitiontemp != null && !this.idlignerepartitiontemp.equals(other.idlignerepartitiontemp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignerepartitiontemp[ idlignerepartitiontemp=" + idlignerepartitiontemp + " ]";
    }
    
}
