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
    @NamedQuery(name = "Inventaire.findAll", query = "SELECT i FROM Inventaire i"),
    @NamedQuery(name = "Inventaire.findByIdinventaire", query = "SELECT i FROM Inventaire i WHERE i.idinventaire = :idinventaire"),
    @NamedQuery(name = "Inventaire.findByCode", query = "SELECT i FROM Inventaire i WHERE i.code = :code"),
    @NamedQuery(name = "Inventaire.findByDateinventaire", query = "SELECT i FROM Inventaire i WHERE i.dateinventaire = :dateinventaire"),
    @NamedQuery(name = "Inventaire.findByEtat", query = "SELECT i FROM Inventaire i WHERE i.etat = :etat"),
    @NamedQuery(name = "Inventaire.findByCentral", query = "SELECT i FROM Inventaire i WHERE i.central = :central"),
    @NamedQuery(name = "Inventaire.findByLibelle", query = "SELECT i FROM Inventaire i WHERE i.libelle = :libelle"),
    @NamedQuery(name = "Inventaire.findByAllarticle", query = "SELECT i FROM Inventaire i WHERE i.allarticle = :allarticle")})
public class Inventaire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idinventaire;
    @Size(max = 254)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date dateinventaire;
    private Boolean etat;
    private Boolean central;
    @Size(max = 100)
    private String libelle;
    private Boolean allarticle;
    @OneToMany(mappedBy = "idinventaire", fetch = FetchType.LAZY)
    private List<Ligneinventaire> ligneinventaireList;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @JoinColumn(name = "idmvtstock", referencedColumnName = "idmvtstock")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mvtstock idmvtstock;

    public Inventaire() {
    }

    public Inventaire(Long idinventaire) {
        this.idinventaire = idinventaire;
    }

    public Long getIdinventaire() {
        return idinventaire;
    }

    public void setIdinventaire(Long idinventaire) {
        this.idinventaire = idinventaire;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateinventaire() {
        return dateinventaire;
    }

    public void setDateinventaire(Date dateinventaire) {
        this.dateinventaire = dateinventaire;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Boolean getCentral() {
        return central;
    }

    public void setCentral(Boolean central) {
        this.central = central;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Boolean getAllarticle() {
        return allarticle;
    }

    public void setAllarticle(Boolean allarticle) {
        this.allarticle = allarticle;
    }

    @XmlTransient
    public List<Ligneinventaire> getLigneinventaireList() {
        return ligneinventaireList;
    }

    public void setLigneinventaireList(List<Ligneinventaire> ligneinventaireList) {
        this.ligneinventaireList = ligneinventaireList;
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
        hash += (idinventaire != null ? idinventaire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Inventaire)) {
            return false;
        }
        Inventaire other = (Inventaire) object;
        if ((this.idinventaire == null && other.idinventaire != null) || (this.idinventaire != null && !this.idinventaire.equals(other.idinventaire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Inventaire[ idinventaire=" + idinventaire + " ]";
    }
    
}
