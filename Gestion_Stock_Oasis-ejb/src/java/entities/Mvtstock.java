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
    @NamedQuery(name = "Mvtstock.findAll", query = "SELECT m FROM Mvtstock m"),
    @NamedQuery(name = "Mvtstock.findByIdmvtstock", query = "SELECT m FROM Mvtstock m WHERE m.idmvtstock = :idmvtstock"),
    @NamedQuery(name = "Mvtstock.findByDatemvt", query = "SELECT m FROM Mvtstock m WHERE m.datemvt = :datemvt"),
    @NamedQuery(name = "Mvtstock.findByOperation", query = "SELECT m FROM Mvtstock m WHERE m.operation = :operation"),
    @NamedQuery(name = "Mvtstock.findByCode", query = "SELECT m FROM Mvtstock m WHERE m.code = :code"),
    @NamedQuery(name = "Mvtstock.findByClient", query = "SELECT m FROM Mvtstock m WHERE m.client = :client"),
    @NamedQuery(name = "Mvtstock.findByFournisseur", query = "SELECT m FROM Mvtstock m WHERE m.fournisseur = :fournisseur"),
    @NamedQuery(name = "Mvtstock.findByType", query = "SELECT m FROM Mvtstock m WHERE m.type = :type"),
    @NamedQuery(name = "Mvtstock.findByMagasin", query = "SELECT m FROM Mvtstock m WHERE m.magasin = :magasin")})
public class Mvtstock implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idmvtstock;
    @Temporal(TemporalType.DATE)
    private Date datemvt;
    @Size(max = 254)
    private String operation;
    @Size(max = 35)
    private String code;
    @Size(max = 70)
    private String client;
    @Size(max = 50)
    private String fournisseur;
    @Size(max = 20)
    private String type;
    @Size(max = 50)
    private String magasin;
    @OneToMany(mappedBy = "idmvtstock", fetch = FetchType.LAZY)
    private List<Repartitionarticle> repartitionarticleList;
    @OneToMany(mappedBy = "idmvtstock", fetch = FetchType.LAZY)
    private List<Livraisonfournisseur> livraisonfournisseurList;
    @OneToMany(mappedBy = "idmvtstock", fetch = FetchType.LAZY)
    private List<Transfert> transfertList;
    @OneToMany(mappedBy = "idmvtstock", fetch = FetchType.LAZY)
    private List<Inventaire> inventaireList;
    @OneToMany(mappedBy = "idmvtstock", fetch = FetchType.LAZY)
    private List<Livraisonclient> livraisonclientList;
    @OneToMany(mappedBy = "idmvtstock", fetch = FetchType.LAZY)
    private List<Lignemvtstock> lignemvtstockList;

    public Mvtstock() {
    }

    public Mvtstock(Long idmvtstock) {
        this.idmvtstock = idmvtstock;
    }

    public Long getIdmvtstock() {
        return idmvtstock;
    }

    public void setIdmvtstock(Long idmvtstock) {
        this.idmvtstock = idmvtstock;
    }

    public Date getDatemvt() {
        return datemvt;
    }

    public void setDatemvt(Date datemvt) {
        this.datemvt = datemvt;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }

    @XmlTransient
    public List<Repartitionarticle> getRepartitionarticleList() {
        return repartitionarticleList;
    }

    public void setRepartitionarticleList(List<Repartitionarticle> repartitionarticleList) {
        this.repartitionarticleList = repartitionarticleList;
    }

    @XmlTransient
    public List<Livraisonfournisseur> getLivraisonfournisseurList() {
        return livraisonfournisseurList;
    }

    public void setLivraisonfournisseurList(List<Livraisonfournisseur> livraisonfournisseurList) {
        this.livraisonfournisseurList = livraisonfournisseurList;
    }

    @XmlTransient
    public List<Transfert> getTransfertList() {
        return transfertList;
    }

    public void setTransfertList(List<Transfert> transfertList) {
        this.transfertList = transfertList;
    }

    @XmlTransient
    public List<Inventaire> getInventaireList() {
        return inventaireList;
    }

    public void setInventaireList(List<Inventaire> inventaireList) {
        this.inventaireList = inventaireList;
    }

    @XmlTransient
    public List<Livraisonclient> getLivraisonclientList() {
        return livraisonclientList;
    }

    public void setLivraisonclientList(List<Livraisonclient> livraisonclientList) {
        this.livraisonclientList = livraisonclientList;
    }

    @XmlTransient
    public List<Lignemvtstock> getLignemvtstockList() {
        return lignemvtstockList;
    }

    public void setLignemvtstockList(List<Lignemvtstock> lignemvtstockList) {
        this.lignemvtstockList = lignemvtstockList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmvtstock != null ? idmvtstock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mvtstock)) {
            return false;
        }
        Mvtstock other = (Mvtstock) object;
        if ((this.idmvtstock == null && other.idmvtstock != null) || (this.idmvtstock != null && !this.idmvtstock.equals(other.idmvtstock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mvtstock[ idmvtstock=" + idmvtstock + " ]";
    }
    
}
