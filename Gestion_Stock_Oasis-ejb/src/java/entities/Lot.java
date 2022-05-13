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
    @NamedQuery(name = "Lot.findAll", query = "SELECT l FROM Lot l")
    ,
    @NamedQuery(name = "Lot.findByIdlot", query = "SELECT l FROM Lot l WHERE l.idlot = :idlot")
    ,
    @NamedQuery(name = "Lot.findByNumero", query = "SELECT l FROM Lot l WHERE l.numero = :numero")
    ,
    @NamedQuery(name = "Lot.findByDatefabrication", query = "SELECT l FROM Lot l WHERE l.datefabrication = :datefabrication")
    ,
    @NamedQuery(name = "Lot.findByDateperemption", query = "SELECT l FROM Lot l WHERE l.dateperemption = :dateperemption")
    ,
    @NamedQuery(name = "Lot.findByQuantite", query = "SELECT l FROM Lot l WHERE l.quantite = :quantite")
    ,
    @NamedQuery(name = "Lot.findByPrixachat", query = "SELECT l FROM Lot l WHERE l.prixachat = :prixachat")
    ,
    @NamedQuery(name = "Lot.findByPrixunitaire", query = "SELECT l FROM Lot l WHERE l.prixunitaire = :prixunitaire")
    ,
    @NamedQuery(name = "Lot.findByDateenregistrement", query = "SELECT l FROM Lot l WHERE l.dateenregistrement = :dateenregistrement")
    ,
    @NamedQuery(name = "Lot.findByEtat", query = "SELECT l FROM Lot l WHERE l.etat = :etat")
    ,
    @NamedQuery(name = "Lot.findByQuantitemultiple", query = "SELECT l FROM Lot l WHERE l.quantitemultiple = :quantitemultiple")
    ,
    @NamedQuery(name = "Lot.findByUnitesortie", query = "SELECT l FROM Lot l WHERE l.unitesortie = :unitesortie")
    ,
    @NamedQuery(name = "Lot.findByQuantitereduite", query = "SELECT l FROM Lot l WHERE l.quantitereduite = :quantitereduite")
    ,
    @NamedQuery(name = "Lot.findByUniteentree", query = "SELECT l FROM Lot l WHERE l.uniteentree = :uniteentree")
    ,
    @NamedQuery(name = "Lot.findByQuantitevirtuelle", query = "SELECT l FROM Lot l WHERE l.quantitevirtuelle = :quantitevirtuelle")
    ,
    @NamedQuery(name = "Lot.findByQuantitesecurite", query = "SELECT l FROM Lot l WHERE l.quantitesecurite = :quantitesecurite")})
public class Lot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlot;
    @Size(max = 254)
    private String numero;
    @Temporal(TemporalType.DATE)
    private Date datefabrication;
    @Temporal(TemporalType.DATE)
    private Date dateperemption;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private double quantite;
    private double prixachat;
    private double prixunitaire;
    @Temporal(TemporalType.DATE)
    private Date dateenregistrement;
    private boolean etat;
    private double quantitemultiple;
    private double unitesortie;
    private double quantitereduite;
    private double uniteentree;
    private double quantitevirtuelle;
    private double quantitesecurite;
    @OneToMany(mappedBy = "idlot", fetch = FetchType.LAZY)
    private List<Ligneinventaire> ligneinventaireList;
    @JoinColumn(name = "idarticle", referencedColumnName = "idarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article idarticle;
    @OneToMany(mappedBy = "idlot", fetch = FetchType.LAZY)
    private List<Lignerepartitiontemp> lignerepartitiontempList;
    @OneToMany(mappedBy = "idlot", fetch = FetchType.LAZY)
    private List<Magasinlot> magasinlotList;
    @OneToMany(mappedBy = "idlot", fetch = FetchType.LAZY)
    private List<Lignelivraisonclient> lignelivraisonclientList;
    @OneToMany(mappedBy = "idlot", fetch = FetchType.LAZY)
    private List<Lignelivraisonfournisseur> lignelivraisonfournisseurList;
    @OneToMany(mappedBy = "idlot", fetch = FetchType.LAZY)
    private List<Lignemvtstock> lignemvtstockList;

    public Lot() {
        idarticle = new Article();
        this.init();
    }

    private void init() {
        quantitemultiple = 0;
        unitesortie = 0;
        quantitereduite = 0;
        uniteentree = 0;
        quantitevirtuelle = 0;
        quantitesecurite = 0;
        prixachat = 0;
        prixunitaire = 0;
        quantite = 0;
        
    }

    public Lot(Long idlot) {
        this.idlot = idlot;
    }

    public Long getIdlot() {
        return idlot;
    }

    public void setIdlot(Long idlot) {
        this.idlot = idlot;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDatefabrication() {
        return datefabrication;
    }

    public void setDatefabrication(Date datefabrication) {
        this.datefabrication = datefabrication;
    }

    public Date getDateperemption() {
        return dateperemption;
    }

    public void setDateperemption(Date dateperemption) {
        this.dateperemption = dateperemption;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getPrixachat() {
        return prixachat;
    }

    public void setPrixachat(Double prixachat) {
        this.prixachat = prixachat;
    }

    public Double getPrixunitaire() {
        return prixunitaire;
    }

    public void setPrixunitaire(Double prixunitaire) {
        this.prixunitaire = prixunitaire;
    }

    public Date getDateenregistrement() {
        return dateenregistrement;
    }

    public void setDateenregistrement(Date dateenregistrement) {
        this.dateenregistrement = dateenregistrement;
    }

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
    }

    public double getUnitesortie() {
        return unitesortie;
    }

    public void setUnitesortie(double unitesortie) {
        this.unitesortie = unitesortie;
    }

    public double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public double getUniteentree() {
        return uniteentree;
    }

    public void setUniteentree(double uniteentree) {
        this.uniteentree = uniteentree;
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

    @XmlTransient
    public List<Ligneinventaire> getLigneinventaireList() {
        return ligneinventaireList;
    }

    public void setLigneinventaireList(List<Ligneinventaire> ligneinventaireList) {
        this.ligneinventaireList = ligneinventaireList;
    }

    public Article getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(Article idarticle) {
        this.idarticle = idarticle;
    }

    @XmlTransient
    public List<Lignerepartitiontemp> getLignerepartitiontempList() {
        return lignerepartitiontempList;
    }

    public void setLignerepartitiontempList(List<Lignerepartitiontemp> lignerepartitiontempList) {
        this.lignerepartitiontempList = lignerepartitiontempList;
    }

    @XmlTransient
    public List<Magasinlot> getMagasinlotList() {
        return magasinlotList;
    }

    public void setMagasinlotList(List<Magasinlot> magasinlotList) {
        this.magasinlotList = magasinlotList;
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
        hash += (idlot != null ? idlot.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lot)) {
            return false;
        }
        Lot other = (Lot) object;
        if ((this.idlot == null && other.idlot != null) || (this.idlot != null && !this.idlot.equals(other.idlot))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lot[ idlot=" + idlot + " ]";
    }

}
