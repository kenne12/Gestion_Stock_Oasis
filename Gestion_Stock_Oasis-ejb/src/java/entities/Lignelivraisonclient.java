/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lignelivraisonclient.findAll", query = "SELECT l FROM Lignelivraisonclient l"),
    @NamedQuery(name = "Lignelivraisonclient.findByIdlignelivraisonclient", query = "SELECT l FROM Lignelivraisonclient l WHERE l.idlignelivraisonclient = :idlignelivraisonclient"),
    @NamedQuery(name = "Lignelivraisonclient.findByQuantite", query = "SELECT l FROM Lignelivraisonclient l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignelivraisonclient.findByTauxsatisfaction", query = "SELECT l FROM Lignelivraisonclient l WHERE l.tauxsatisfaction = :tauxsatisfaction"),
    @NamedQuery(name = "Lignelivraisonclient.findByQuantitemultiple", query = "SELECT l FROM Lignelivraisonclient l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Lignelivraisonclient.findByUnite", query = "SELECT l FROM Lignelivraisonclient l WHERE l.unite = :unite"),
    @NamedQuery(name = "Lignelivraisonclient.findByMontant", query = "SELECT l FROM Lignelivraisonclient l WHERE l.montant = :montant"),
    @NamedQuery(name = "Lignelivraisonclient.findByQuantitereduite", query = "SELECT l FROM Lignelivraisonclient l WHERE l.quantitereduite = :quantitereduite")})
public class Lignelivraisonclient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignelivraisonclient;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double tauxsatisfaction;
    private Double quantitemultiple;
    private Double unite;
    private Double montant;
    @Column(name = "prixunitaire")
    private Double prixUnitaire;
    private Double quantitereduite;
    private double marge;
    @Column(name = "prixachat")
    private double prixAchat;
    @Column(name = "prixvente")
    private double prixVente;
    @JoinColumn(name = "idlivraisonclient", referencedColumnName = "idlivraisonclient")
    @ManyToOne(fetch = FetchType.LAZY)
    private Livraisonclient idlivraisonclient;
    @JoinColumn(name = "idlot", referencedColumnName = "idlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot idlot;
    @JoinColumn(name = "idmagasinlot", referencedColumnName = "idmagasinlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinlot idmagasinlot;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;

    public Lignelivraisonclient() {
    }

    public Lignelivraisonclient(Long idlignelivraisonclient) {
        this.idlignelivraisonclient = idlignelivraisonclient;
    }

    public Long getIdlignelivraisonclient() {
        return idlignelivraisonclient;
    }

    public void setIdlignelivraisonclient(Long idlignelivraisonclient) {
        this.idlignelivraisonclient = idlignelivraisonclient;
    }

    public Double getQuantite() {
        return quantite;
    }

    public void setQuantite(Double quantite) {
        this.quantite = quantite;
    }

    public Double getTauxsatisfaction() {
        return tauxsatisfaction;
    }

    public void setTauxsatisfaction(Double tauxsatisfaction) {
        this.tauxsatisfaction = tauxsatisfaction;
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

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public Livraisonclient getIdlivraisonclient() {
        return idlivraisonclient;
    }

    public void setIdlivraisonclient(Livraisonclient idlivraisonclient) {
        this.idlivraisonclient = idlivraisonclient;
    }

    public Lot getIdlot() {
        return idlot;
    }

    public void setIdlot(Lot idlot) {
        this.idlot = idlot;
    }

    public Magasinlot getIdmagasinlot() {
        return idmagasinlot;
    }

    public void setIdmagasinlot(Magasinlot idmagasinlot) {
        this.idmagasinlot = idmagasinlot;
    }

    public Unite getIdunite() {
        return idunite;
    }

    public void setIdunite(Unite idunite) {
        this.idunite = idunite;
    }

    public Double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(Double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getMarge() {
        return marge;
    }

    public void setMarge(double marge) {
        this.marge = marge;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlignelivraisonclient != null ? idlignelivraisonclient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignelivraisonclient)) {
            return false;
        }
        Lignelivraisonclient other = (Lignelivraisonclient) object;
        if ((this.idlignelivraisonclient == null && other.idlignelivraisonclient != null) || (this.idlignelivraisonclient != null && !this.idlignelivraisonclient.equals(other.idlignelivraisonclient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignelivraisonclient[ idlignelivraisonclient=" + idlignelivraisonclient + " ]";
    }

}
