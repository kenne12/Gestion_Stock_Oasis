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
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
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
    @NamedQuery(name = "Entredirect.findAll", query = "SELECT e FROM Entredirect e"),
    @NamedQuery(name = "Entredirect.findByIdentredirect", query = "SELECT e FROM Entredirect e WHERE e.identredirect = :identredirect"),
    @NamedQuery(name = "Entredirect.findByDateentree", query = "SELECT e FROM Entredirect e WHERE e.dateentree = :dateentree"),
    @NamedQuery(name = "Entredirect.findByMontant", query = "SELECT e FROM Entredirect e WHERE e.montant = :montant"),
    @NamedQuery(name = "Entredirect.findByCode", query = "SELECT e FROM Entredirect e WHERE e.code = :code")})
public class Entredirect implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer identredirect;
    @Temporal(TemporalType.DATE)
    private Date dateentree;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    @Size(max = 254)
    private String code;
    @OneToMany(mappedBy = "identredirect", fetch = FetchType.LAZY)
    private List<Ligneentreedirect> ligneentreedirectList;

    public Entredirect() {
    }

    public Entredirect(Integer identredirect) {
        this.identredirect = identredirect;
    }

    public Integer getIdentredirect() {
        return identredirect;
    }

    public void setIdentredirect(Integer identredirect) {
        this.identredirect = identredirect;
    }

    public Date getDateentree() {
        return dateentree;
    }

    public void setDateentree(Date dateentree) {
        this.dateentree = dateentree;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @XmlTransient
    public List<Ligneentreedirect> getLigneentreedirectList() {
        return ligneentreedirectList;
    }

    public void setLigneentreedirectList(List<Ligneentreedirect> ligneentreedirectList) {
        this.ligneentreedirectList = ligneentreedirectList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (identredirect != null ? identredirect.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entredirect)) {
            return false;
        }
        Entredirect other = (Entredirect) object;
        if ((this.identredirect == null && other.identredirect != null) || (this.identredirect != null && !this.identredirect.equals(other.identredirect))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Entredirect[ identredirect=" + identredirect + " ]";
    }
    
}
