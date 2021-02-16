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
    @NamedQuery(name = "Repartitionarticle.findAll", query = "SELECT r FROM Repartitionarticle r"),
    @NamedQuery(name = "Repartitionarticle.findByIdrepartitionarticle", query = "SELECT r FROM Repartitionarticle r WHERE r.idrepartitionarticle = :idrepartitionarticle"),
    @NamedQuery(name = "Repartitionarticle.findByDate", query = "SELECT r FROM Repartitionarticle r WHERE r.date = :date"),
    @NamedQuery(name = "Repartitionarticle.findByCode", query = "SELECT r FROM Repartitionarticle r WHERE r.code = :code"),
    @NamedQuery(name = "Repartitionarticle.findByMontanttotal", query = "SELECT r FROM Repartitionarticle r WHERE r.montanttotal = :montanttotal")})
public class Repartitionarticle implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idrepartitionarticle;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Size(max = 40)
    private String code;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montanttotal;
    @OneToMany(mappedBy = "idrepartitionarticle", fetch = FetchType.LAZY)
    private List<Lignerepartitionarticle> lignerepartitionarticleList;
    @JoinColumn(name = "idmvtstock", referencedColumnName = "idmvtstock")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mvtstock idmvtstock;
    @OneToMany(mappedBy = "idrepartitionarticle", fetch = FetchType.LAZY)
    private List<Lignerepartitiontemp> lignerepartitiontempList;

    public Repartitionarticle() {
    }

    public Repartitionarticle(Integer idrepartitionarticle) {
        this.idrepartitionarticle = idrepartitionarticle;
    }

    public Integer getIdrepartitionarticle() {
        return idrepartitionarticle;
    }

    public void setIdrepartitionarticle(Integer idrepartitionarticle) {
        this.idrepartitionarticle = idrepartitionarticle;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(Double montanttotal) {
        this.montanttotal = montanttotal;
    }

    @XmlTransient
    public List<Lignerepartitionarticle> getLignerepartitionarticleList() {
        return lignerepartitionarticleList;
    }

    public void setLignerepartitionarticleList(List<Lignerepartitionarticle> lignerepartitionarticleList) {
        this.lignerepartitionarticleList = lignerepartitionarticleList;
    }

    public Mvtstock getIdmvtstock() {
        return idmvtstock;
    }

    public void setIdmvtstock(Mvtstock idmvtstock) {
        this.idmvtstock = idmvtstock;
    }

    @XmlTransient
    public List<Lignerepartitiontemp> getLignerepartitiontempList() {
        return lignerepartitiontempList;
    }

    public void setLignerepartitiontempList(List<Lignerepartitiontemp> lignerepartitiontempList) {
        this.lignerepartitiontempList = lignerepartitiontempList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idrepartitionarticle != null ? idrepartitionarticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Repartitionarticle)) {
            return false;
        }
        Repartitionarticle other = (Repartitionarticle) object;
        if ((this.idrepartitionarticle == null && other.idrepartitionarticle != null) || (this.idrepartitionarticle != null && !this.idrepartitionarticle.equals(other.idrepartitionarticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Repartitionarticle[ idrepartitionarticle=" + idrepartitionarticle + " ]";
    }
    
}
