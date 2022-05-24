/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
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
    @NamedQuery(name = "Magasinlot.findAll", query = "SELECT m FROM Magasinlot m")
    ,
    @NamedQuery(name = "Magasinlot.findByIdmagasinlot", query = "SELECT m FROM Magasinlot m WHERE m.idmagasinlot = :idmagasinlot")
    ,
    @NamedQuery(name = "Magasinlot.findByQuantite", query = "SELECT m FROM Magasinlot m WHERE m.quantite = :quantite")
    ,
    @NamedQuery(name = "Magasinlot.findByQuantitemultiple", query = "SELECT m FROM Magasinlot m WHERE m.quantitemultiple = :quantitemultiple")
    ,
    @NamedQuery(name = "Magasinlot.findByUnite", query = "SELECT m FROM Magasinlot m WHERE m.unite = :unite")
    ,
    @NamedQuery(name = "Magasinlot.findByEtat", query = "SELECT m FROM Magasinlot m WHERE m.etat = :etat")
    ,
    @NamedQuery(name = "Magasinlot.findByQuantitereduite", query = "SELECT m FROM Magasinlot m WHERE m.quantitereduite = :quantitereduite")
    ,
    @NamedQuery(name = "Magasinlot.findByQuantitevirtuelle", query = "SELECT m FROM Magasinlot m WHERE m.quantitevirtuelle = :quantitevirtuelle")
    ,
    @NamedQuery(name = "Magasinlot.findByQuantitesecurite", query = "SELECT m FROM Magasinlot m WHERE m.quantitesecurite = :quantitesecurite")})
public class Magasinlot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idmagasinlot;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private double quantite;
    private double quantitemultiple;
    private double unite;
    private Boolean etat;
    private double quantitereduite;
    private double quantitevirtuelle;
    private double quantitesecurite;
    @Column(name = "prix_vente_detail")
    private double prixVenteDetail;
    @Column(name = "prix_vente_gros")
    private double prixVenteGros;
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

    private void initConstructor() {
        this.quantite = 0;
        this.quantitemultiple = 0;
        this.quantitereduite = 0;
        this.quantitevirtuelle = 0;
        this.quantitesecurite = 0;
        this.unite = 0;
        this.prixVenteDetail = 0;
        this.prixVenteGros = 0;
        this.etat = true;
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

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
    }

    public double getUnite() {
        return unite;
    }

    public void setUnite(double unite) {
        this.unite = unite;
    }

    public Boolean getEtat() {
        return etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public double getQuantitevirtuelle() {
        return quantitevirtuelle;
    }

    public void setQuantitevirtuelle(double quantitevirtuelle) {
        this.quantitevirtuelle = quantitevirtuelle;
    }

    public double getQuantitesecurite() {
        return quantitesecurite;
    }

    public void setQuantitesecurite(double quantitesecurite) {
        this.quantitesecurite = quantitesecurite;
    }

    public double getPrixVenteDetail() {
        return prixVenteDetail;
    }

    public void setPrixVenteDetail(double prixVenteDetail) {
        this.prixVenteDetail = prixVenteDetail;
    }

    public double getPrixVenteGros() {
        return prixVenteGros;
    }

    public void setPrixVenteGros(double prixVenteGros) {
        this.prixVenteGros = prixVenteGros;
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
        return !((this.idmagasinlot == null && other.idmagasinlot != null) || (this.idmagasinlot != null && !this.idmagasinlot.equals(other.idmagasinlot)));
    }

    @Override
    public String toString() {
        return "entities.Magasinlot[ idmagasinlot=" + idmagasinlot + " ]";
    }

}
