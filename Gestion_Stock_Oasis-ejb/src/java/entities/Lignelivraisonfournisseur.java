/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumeration.ModeEntreSorti;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Lignelivraisonfournisseur.findAll", query = "SELECT l FROM Lignelivraisonfournisseur l")
    ,
    @NamedQuery(name = "Lignelivraisonfournisseur.findByIdlignelivraisonfournisseur", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.idlignelivraisonfournisseur = :idlignelivraisonfournisseur")
    ,
    @NamedQuery(name = "Lignelivraisonfournisseur.findByQuantite", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.quantite = :quantite")
    ,
    @NamedQuery(name = "Lignelivraisonfournisseur.findByTauxsatisfaction", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.tauxsatisfaction = :tauxsatisfaction")
    ,
    @NamedQuery(name = "Lignelivraisonfournisseur.findByQuantitemultiple", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.quantitemultiple = :quantitemultiple")
    ,
    @NamedQuery(name = "Lignelivraisonfournisseur.findByUnite", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.unite = :unite")
    ,
    @NamedQuery(name = "Lignelivraisonfournisseur.findByPrixachat", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.prixachat = :prixachat")
    ,
    @NamedQuery(name = "Lignelivraisonfournisseur.findByQuantitereduite", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.quantitereduite = :quantitereduite")})
public class Lignelivraisonfournisseur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignelivraisonfournisseur;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private double quantite;
    private double tauxsatisfaction;
    private double quantitemultiple;
    private double unite;
    private double prixachat;
    private double quantitereduite;
    @Column(name = "montant_total")
    private double montantTotal;
    @Column(name = "prix_achat_detail")
    private double prixAchatDetail;

    @Column(name = "prix_vente")
    private double prixVente;

    @Column(name = "prix_vente_detail")
    private double prixVenteDetail;

    @JoinColumn(name = "idlignecommandefournisseur", referencedColumnName = "idlignecommandefournisseur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lignecommandefournisseur idlignecommandefournisseur;
    @JoinColumn(name = "idlivraisonfournisseur", referencedColumnName = "idlivraisonfournisseur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Livraisonfournisseur idlivraisonfournisseur;
    @JoinColumn(name = "idlot", referencedColumnName = "idlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot idlot;
    @JoinColumn(name = "idmagasinlot", referencedColumnName = "idmagasinlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinlot idmagasinlot;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;

    private double prix;

    @Transient
    private double qtyNotConverted;

    @Column(name = "mode_vente", length = 15)
    @Enumerated(EnumType.STRING)
    private ModeEntreSorti modeVente;

    public Lignelivraisonfournisseur() {
        this.initConstructor();
    }

    private void initConstructor() {
        this.idunite = new Unite();
        this.prixVente = 0;
        this.prixVenteDetail = 0;
        this.prixAchatDetail = 0;
        this.prixachat = 0;
        this.montantTotal = 0;
    }

    public Lignelivraisonfournisseur(Long idlignelivraisonfournisseur) {
        this.idlignelivraisonfournisseur = idlignelivraisonfournisseur;
    }

    public Long getIdlignelivraisonfournisseur() {
        return idlignelivraisonfournisseur;
    }

    public void setIdlignelivraisonfournisseur(Long idlignelivraisonfournisseur) {
        this.idlignelivraisonfournisseur = idlignelivraisonfournisseur;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getTauxsatisfaction() {
        return tauxsatisfaction;
    }

    public void setTauxsatisfaction(double tauxsatisfaction) {
        this.tauxsatisfaction = tauxsatisfaction;
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

    public double getPrixachat() {
        return prixachat;
    }

    public void setPrixachat(double prixachat) {
        this.prixachat = prixachat;
    }

    public double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public double getPrixAchatDetail() {
        return prixAchatDetail;
    }

    public void setPrixAchatDetail(double prixAchatDetail) {
        this.prixAchatDetail = prixAchatDetail;
    }

    public Lignecommandefournisseur getIdlignecommandefournisseur() {
        return idlignecommandefournisseur;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) {
        this.prixVente = prixVente;
    }

    public double getPrixVenteDetail() {
        return prixVenteDetail;
    }

    public void setPrixVenteDetail(double prixVenteDetail) {
        this.prixVenteDetail = prixVenteDetail;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public double getQtyNotConverted() {
        return qtyNotConverted;
    }

    public void setQtyNotConverted(double qtyNotConverted) {
        this.qtyNotConverted = qtyNotConverted;
    }

    public void setIdlignecommandefournisseur(Lignecommandefournisseur idlignecommandefournisseur) {
        this.idlignecommandefournisseur = idlignecommandefournisseur;
    }

    public Livraisonfournisseur getIdlivraisonfournisseur() {
        return idlivraisonfournisseur;
    }

    public void setIdlivraisonfournisseur(Livraisonfournisseur idlivraisonfournisseur) {
        this.idlivraisonfournisseur = idlivraisonfournisseur;
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

    public ModeEntreSorti getModeVente() {
        return modeVente;
    }

    public void setModeVente(ModeEntreSorti modeVente) {
        this.modeVente = modeVente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlignelivraisonfournisseur != null ? idlignelivraisonfournisseur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignelivraisonfournisseur)) {
            return false;
        }
        Lignelivraisonfournisseur other = (Lignelivraisonfournisseur) object;
        if ((this.idlignelivraisonfournisseur == null && other.idlignelivraisonfournisseur != null) || (this.idlignelivraisonfournisseur != null && !this.idlignelivraisonfournisseur.equals(other.idlignelivraisonfournisseur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignelivraisonfournisseur[ idlignelivraisonfournisseur=" + idlignelivraisonfournisseur + " ]";
    }

}
