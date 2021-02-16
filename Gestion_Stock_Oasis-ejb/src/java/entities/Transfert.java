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
    @NamedQuery(name = "Transfert.findAll", query = "SELECT t FROM Transfert t"),
    @NamedQuery(name = "Transfert.findByIdtransfert", query = "SELECT t FROM Transfert t WHERE t.idtransfert = :idtransfert"),
    @NamedQuery(name = "Transfert.findByMontanttotal", query = "SELECT t FROM Transfert t WHERE t.montanttotal = :montanttotal"),
    @NamedQuery(name = "Transfert.findByDatetransfert", query = "SELECT t FROM Transfert t WHERE t.datetransfert = :datetransfert"),
    @NamedQuery(name = "Transfert.findByCode", query = "SELECT t FROM Transfert t WHERE t.code = :code"),
    @NamedQuery(name = "Transfert.findByIdmagasincible", query = "SELECT t FROM Transfert t WHERE t.idmagasincible = :idmagasincible")})
public class Transfert implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idtransfert;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montanttotal;
    @Temporal(TemporalType.DATE)
    private Date datetransfert;
    @Size(max = 40)
    private String code;
    private Integer idmagasincible;
    @OneToMany(mappedBy = "idtransfert", fetch = FetchType.LAZY)
    private List<Lignetransfert> lignetransfertList;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @JoinColumn(name = "idmvtstock", referencedColumnName = "idmvtstock")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mvtstock idmvtstock;

    public Transfert() {
    }

    public Transfert(Long idtransfert) {
        this.idtransfert = idtransfert;
    }

    public Long getIdtransfert() {
        return idtransfert;
    }

    public void setIdtransfert(Long idtransfert) {
        this.idtransfert = idtransfert;
    }

    public Double getMontanttotal() {
        return montanttotal;
    }

    public void setMontanttotal(Double montanttotal) {
        this.montanttotal = montanttotal;
    }

    public Date getDatetransfert() {
        return datetransfert;
    }

    public void setDatetransfert(Date datetransfert) {
        this.datetransfert = datetransfert;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getIdmagasincible() {
        return idmagasincible;
    }

    public void setIdmagasincible(Integer idmagasincible) {
        this.idmagasincible = idmagasincible;
    }

    @XmlTransient
    public List<Lignetransfert> getLignetransfertList() {
        return lignetransfertList;
    }

    public void setLignetransfertList(List<Lignetransfert> lignetransfertList) {
        this.lignetransfertList = lignetransfertList;
    }

    public Magasin getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Magasin idmagasin) {
        this.idmagasin = idmagasin;
    }

    public Mvtstock getIdmvtstock() {
        return idmvtstock;
    }

    public void setIdmvtstock(Mvtstock idmvtstock) {
        this.idmvtstock = idmvtstock;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtransfert != null ? idtransfert.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transfert)) {
            return false;
        }
        Transfert other = (Transfert) object;
        if ((this.idtransfert == null && other.idtransfert != null) || (this.idtransfert != null && !this.idtransfert.equals(other.idtransfert))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Transfert[ idtransfert=" + idtransfert + " ]";
    }
    
}
