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
    @NamedQuery(name = "Magasinlot.findAll", query = "SELECT m FROM Magasinlot m"),
    @NamedQuery(name = "Magasinlot.findByIdmagasinlot", query = "SELECT m FROM Magasinlot m WHERE m.idmagasinlot = :idmagasinlot"),
    @NamedQuery(name = "Magasinlot.findByQuantite", query = "SELECT m FROM Magasinlot m WHERE m.quantite = :quantite"),
    @NamedQuery(name = "Magasinlot.findByQuantitemultiple", query = "SELECT m FROM Magasinlot m WHERE m.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Magasinlot.findByUnite", query = "SELECT m FROM Magasinlot m WHERE m.unite = :unite"),
    @NamedQuery(name = "Magasinlot.findByEtat", query = "SELECT m FROM Magasinlot m WHERE m.etat = :etat"),
    @NamedQuery(name = "Magasinlot.findByQuantitereduite", query = "SELECT m FROM Magasinlot m WHERE m.quantitereduite = :quantitereduite"),
    @NamedQuery(name = "Magasinlot.findByQuantitevirtuelle", query = "SELECT m FROM Magasinlot m WHERE m.quantitevirtuelle = :quantitevirtuelle"),
    @NamedQuery(name = "Magasinlot.findByQuantitesecurite", query = "SELECT m FROM Magasinlot m WHERE m.quantitesecurite = :quantitesecurite")})
public class Magasinlot implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idmagasinlot;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double quantitemultiple;
    private Double unite;
    private Boolean etat;
    private Double quantitereduite;
    private Double quantitevirtuelle;
    private Double quantitesecurite;
    @OneToMany(mappedBy = "idmagasinlot", fetch = FetchType.LAZY)
    private List<Lignerepartitionarticle> lignerepartitionarticleList;
    @OneToMany(mappedBy = "idmagasinlot", fetch = FetchType.LAZY)
    private List<Ligneinventaire> ligneinventaireList;
    @OneToMany(mappedBy = "idmagasinlot", fetch = FetchType.LAZY)
    private List<Lignetransfert> lignetransfertList;
    @OneToMany(mappedBy = "idmagasinlot", fetch = FetchType.LAZY)
    private List<Ligneentreedirect> ligneentreedirectList;
    @JoinColumn(name = "idlot", referencedColumnName = "idlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot idlot;
    @JoinColumn(name = "idmagasinarticle", referencedColumnName = "idmagasinarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinarticle idmagasinarticle;
    @OneToMany(mappedBy = "idmagasinlot", fetch = FetchType.LAZY)
    private List<Lignelivraisonclient> lignelivraisonclientList;
    @OneToMany(mappedBy = "idmagasinlot", fetch = FetchType.LAZY)
    private List<Lignelivraisonfournisseur> lignelivraisonfournisseurList;
    @OneToMany(mappedBy = "idmagasinlot", fetch = FetchType.LAZY)
    private List<Lignemvtstock> lignemvtstockList;

    
    private void initConstructor(){
        this.quantite = 0d;
        this.quantitemultiple = 0d;
        this.quantitereduite = 0d;
        this.quantitevirtuelle = 0d;
        this.quantitesecurite = 0d;
        this.unite = 0d;    
    }
    
    public Magasinlot() {
        super();
        this.initConstructor();
    }

    public Magasinlot(Long idmagasinlot) {
        this.idmagasinlot = idmagasinlot;
        this.initConstructor();
    }

    public Long getIdmagasinlot() {
        return idmagasinlot;
    }

    public void setIdmagasinlot(Long idmagasinlot) {
        this.idmagasinlot = idmagasinlot;
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

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public Double getQuantitevirtuelle() {
        return quantitevirtuelle;
    }

    public void setQuantitevirtuelle(Double quantitevirtuelle) {
        this.quantitevirtuelle = quantitevirtuelle;
    }

    public Double getQuantitesecurite() {
        return quantitesecurite;
    }

    public void setQuantitesecurite(Double quantitesecurite) {
        this.quantitesecurite = quantitesecurite;
    }

    @XmlTransient
    public List<Lignerepartitionarticle> getLignerepartitionarticleList() {
        return lignerepartitionarticleList;
    }

    public void setLignerepartitionarticleList(List<Lignerepartitionarticle> lignerepartitionarticleList) {
        this.lignerepartitionarticleList = lignerepartitionarticleList;
    }

    @XmlTransient
    public List<Ligneinventaire> getLigneinventaireList() {
        return ligneinventaireList;
    }

    public void setLigneinventaireList(List<Ligneinventaire> ligneinventaireList) {
        this.ligneinventaireList = ligneinventaireList;
    }

    @XmlTransient
    public List<Lignetransfert> getLignetransfertList() {
        return lignetransfertList;
    }

    public void setLignetransfertList(List<Lignetransfert> lignetransfertList) {
        this.lignetransfertList = lignetransfertList;
    }

    @XmlTransient
    public List<Ligneentreedirect> getLigneentreedirectList() {
        return ligneentreedirectList;
    }

    public void setLigneentreedirectList(List<Ligneentreedirect> ligneentreedirectList) {
        this.ligneentreedirectList = ligneentreedirectList;
    }

    public Lot getIdlot() {
        return idlot;
    }

    public void setIdlot(Lot idlot) {
        this.idlot = idlot;
    }

    public Magasinarticle getIdmagasinarticle() {
        return idmagasinarticle;
    }

    public void setIdmagasinarticle(Magasinarticle idmagasinarticle) {
        this.idmagasinarticle = idmagasinarticle;
    }

    @XmlTransient
    public List<Lignelivraisonclient> getLignelivraisonclientList() {
        return lignelivraisonclientList;
    }

    public void setLignelivraisonclientList(List<Lignelivraisonclient> lignelivraisonclientList) {
        this.lignelivraisonclientList = lignelivraisonclientList;
    }

    @XmlTransient
    public List<Lignelivraisonfournisseur> getLignelivraisonfournisseurList() {
        return lignelivraisonfournisseurList;
    }

    public void setLignelivraisonfournisseurList(List<Lignelivraisonfournisseur> lignelivraisonfournisseurList) {
        this.lignelivraisonfournisseurList = lignelivraisonfournisseurList;
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
        hash += (idmagasinlot != null ? idmagasinlot.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magasinlot)) {
            return false;
        }
        Magasinlot other = (Magasinlot) object;
        if ((this.idmagasinlot == null && other.idmagasinlot != null) || (this.idmagasinlot != null && !this.idmagasinlot.equals(other.idmagasinlot))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Magasinlot[ idmagasinlot=" + idmagasinlot + " ]";
    }
    
}
