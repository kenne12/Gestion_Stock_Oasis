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
    @NamedQuery(name = "Livraisonclient.findAll", query = "SELECT l FROM Livraisonclient l"),
    @NamedQuery(name = "Livraisonclient.findByIdlivraisonclient", query = "SELECT l FROM Livraisonclient l WHERE l.idlivraisonclient = :idlivraisonclient"),
    @NamedQuery(name = "Livraisonclient.findByLivraisondirecte", query = "SELECT l FROM Livraisonclient l WHERE l.livraisondirecte = :livraisondirecte"),
    @NamedQuery(name = "Livraisonclient.findByMontant", query = "SELECT l FROM Livraisonclient l WHERE l.montant = :montant"),
    @NamedQuery(name = "Livraisonclient.findByCode", query = "SELECT l FROM Livraisonclient l WHERE l.code = :code"),
    @NamedQuery(name = "Livraisonclient.findByDatelivraison", query = "SELECT l FROM Livraisonclient l WHERE l.datelivraison = :datelivraison")})
public class Livraisonclient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlivraisonclient;
    private Boolean livraisondirecte;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    @Size(max = 40)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date datelivraison;
    @JoinColumn(name = "iddemande", referencedColumnName = "iddemande")
    @ManyToOne(fetch = FetchType.LAZY)
    private Demande iddemande;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @JoinColumn(name = "idmvtstock", referencedColumnName = "idmvtstock")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mvtstock idmvtstock;
    @JoinColumn(name = "idpersonnel", referencedColumnName = "idpersonnel")
    @ManyToOne(fetch = FetchType.LAZY)
    private Personnel idpersonnel;
    @OneToMany(mappedBy = "idlivraisonclient", fetch = FetchType.LAZY)
    private List<Lignelivraisonclient> lignelivraisonclientList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient", referencedColumnName = "idclient")
    private Client client;

    public Livraisonclient() {
    }

    public Livraisonclient(Long idlivraisonclient) {
        this.idlivraisonclient = idlivraisonclient;
    }

    public Long getIdlivraisonclient() {
        return idlivraisonclient;
    }

    public void setIdlivraisonclient(Long idlivraisonclient) {
        this.idlivraisonclient = idlivraisonclient;
    }

    public Boolean getLivraisondirecte() {
        return livraisondirecte;
    }

    public void setLivraisondirecte(Boolean livraisondirecte) {
        this.livraisondirecte = livraisondirecte;
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

    public Date getDatelivraison() {
        return datelivraison;
    }

    public void setDatelivraison(Date datelivraison) {
        this.datelivraison = datelivraison;
    }

    public Demande getIddemande() {
        return iddemande;
    }

    public void setIddemande(Demande iddemande) {
        this.iddemande = iddemande;
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

    public Personnel getIdpersonnel() {
        return idpersonnel;
    }

    public void setIdpersonnel(Personnel idpersonnel) {
        this.idpersonnel = idpersonnel;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @XmlTransient
    public List<Lignelivraisonclient> getLignelivraisonclientList() {
        return lignelivraisonclientList;
    }

    public void setLignelivraisonclientList(List<Lignelivraisonclient> lignelivraisonclientList) {
        this.lignelivraisonclientList = lignelivraisonclientList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlivraisonclient != null ? idlivraisonclient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livraisonclient)) {
            return false;
        }
        Livraisonclient other = (Livraisonclient) object;
        if ((this.idlivraisonclient == null && other.idlivraisonclient != null) || (this.idlivraisonclient != null && !this.idlivraisonclient.equals(other.idlivraisonclient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Livraisonclient[ idlivraisonclient=" + idlivraisonclient + " ]";
    }

}
