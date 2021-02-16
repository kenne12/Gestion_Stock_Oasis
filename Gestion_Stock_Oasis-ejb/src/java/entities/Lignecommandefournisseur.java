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
    @NamedQuery(name = "Lignecommandefournisseur.findAll", query = "SELECT l FROM Lignecommandefournisseur l"),
    @NamedQuery(name = "Lignecommandefournisseur.findByIdlignecommandefournisseur", query = "SELECT l FROM Lignecommandefournisseur l WHERE l.idlignecommandefournisseur = :idlignecommandefournisseur"),
    @NamedQuery(name = "Lignecommandefournisseur.findByMontant", query = "SELECT l FROM Lignecommandefournisseur l WHERE l.montant = :montant"),
    @NamedQuery(name = "Lignecommandefournisseur.findByQuantite", query = "SELECT l FROM Lignecommandefournisseur l WHERE l.quantite = :quantite"),
    @NamedQuery(name = "Lignecommandefournisseur.findByQuantitemultiple", query = "SELECT l FROM Lignecommandefournisseur l WHERE l.quantitemultiple = :quantitemultiple"),
    @NamedQuery(name = "Lignecommandefournisseur.findByUnite", query = "SELECT l FROM Lignecommandefournisseur l WHERE l.unite = :unite"),
    @NamedQuery(name = "Lignecommandefournisseur.findByQuantitereduite", query = "SELECT l FROM Lignecommandefournisseur l WHERE l.quantitereduite = :quantitereduite")})
public class Lignecommandefournisseur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignecommandefournisseur;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    private Double quantite;
    private Double quantitemultiple;
    private Double unite;
    private Double quantitereduite;
    @JoinColumn(name = "idarticle", referencedColumnName = "idarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article idarticle;
    @JoinColumn(name = "idcommandefournisseur", referencedColumnName = "idcommandefournisseur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Commandefournisseur idcommandefournisseur;
    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;
    @OneToMany(mappedBy = "idlignecommandefournisseur", fetch = FetchType.LAZY)
    private List<Lignelivraisonfournisseur> lignelivraisonfournisseurList;

    public Lignecommandefournisseur() {
    }

    public Lignecommandefournisseur(Long idlignecommandefournisseur) {
        this.idlignecommandefournisseur = idlignecommandefournisseur;
    }

    public Long getIdlignecommandefournisseur() {
        return idlignecommandefournisseur;
    }

    public void setIdlignecommandefournisseur(Long idlignecommandefournisseur) {
        this.idlignecommandefournisseur = idlignecommandefournisseur;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
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

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(Double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public Article getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(Article idarticle) {
        this.idarticle = idarticle;
    }

    public Commandefournisseur getIdcommandefournisseur() {
        return idcommandefournisseur;
    }

    public void setIdcommandefournisseur(Commandefournisseur idcommandefournisseur) {
        this.idcommandefournisseur = idcommandefournisseur;
    }

    public Unite getIdunite() {
        return idunite;
    }

    public void setIdunite(Unite idunite) {
        this.idunite = idunite;
    }

    @XmlTransient
    public List<Lignelivraisonfournisseur> getLignelivraisonfournisseurList() {
        return lignelivraisonfournisseurList;
    }

    public void setLignelivraisonfournisseurList(List<Lignelivraisonfournisseur> lignelivraisonfournisseurList) {
        this.lignelivraisonfournisseurList = lignelivraisonfournisseurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlignecommandefournisseur != null ? idlignecommandefournisseur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignecommandefournisseur)) {
            return false;
        }
        Lignecommandefournisseur other = (Lignecommandefournisseur) object;
        if ((this.idlignecommandefournisseur == null && other.idlignecommandefournisseur != null) || (this.idlignecommandefournisseur != null && !this.idlignecommandefournisseur.equals(other.idlignecommandefournisseur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignecommandefournisseur[ idlignecommandefournisseur=" + idlignecommandefournisseur + " ]";
    }
    
}
