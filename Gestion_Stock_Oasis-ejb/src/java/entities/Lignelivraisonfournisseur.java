/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
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
    @NamedQuery(name = "Lignelivraisonfournisseur.findAll", query = "SELECT l FROM Lignelivraisonfournisseur l"),
    @NamedQuery(name = "Lignelivraisonfournisseur.findByIdlignelivraisonfournisseur", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.idlignelivraisonfournisseur = :idlignelivraisonfournisseur"),
    @NamedQuery(name = "Lignelivraisonfournisseur.findByQuantite", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignelivraisonfournisseur.findByTauxsatisfaction", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.tauxsatisfaction = :tauxsatisfaction"),
    @NamedQuery(name = "Lignelivraisonfournisseur.findByQuantitemultiple", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Lignelivraisonfournisseur.findByUnite", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.unite = :unite"),
    @NamedQuery(name = "Lignelivraisonfournisseur.findByPrixachat", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.prixachat = :prixachat"),
    @NamedQuery(name = "Lignelivraisonfournisseur.findByQuantitereduite", query = "SELECT l FROM Lignelivraisonfournisseur l WHERE l.quantitereduite = :quantitereduite")})
public class Lignelivraisonfournisseur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignelivraisonfournisseur;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double quantite;
    private Double tauxsatisfaction;
    private Double quantitemultiple;
    private Double unite;
    private Double prixachat;
    private Double quantitereduite;
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

    public Lignelivraisonfournisseur() {
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

    public Double getPrixachat() {
        return prixachat;
    }

    public void setPrixachat(Double prixachat) {
        this.prixachat = prixachat;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public Lignecommandefournisseur getIdlignecommandefournisseur() {
        return idlignecommandefournisseur;
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
