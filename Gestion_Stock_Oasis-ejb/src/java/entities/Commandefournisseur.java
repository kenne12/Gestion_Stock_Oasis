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
import javax.persistence.Column;
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
    @NamedQuery(name = "Commandefournisseur.findAll", query = "SELECT c FROM Commandefournisseur c"),
    @NamedQuery(name = "Commandefournisseur.findByIdcommandefournisseur", query = "SELECT c FROM Commandefournisseur c WHERE c.idcommandefournisseur = :idcommandefournisseur"),
    @NamedQuery(name = "Commandefournisseur.findByMontant", query = "SELECT c FROM Commandefournisseur c WHERE c.montant = :montant"),
    @NamedQuery(name = "Commandefournisseur.findByDateprevlivrasion", query = "SELECT c FROM Commandefournisseur c WHERE c.dateprevlivrasion = :dateprevlivrasion"),
    @NamedQuery(name = "Commandefournisseur.findByDateeffectlivraison", query = "SELECT c FROM Commandefournisseur c WHERE c.dateeffectlivraison = :dateeffectlivraison"),
    @NamedQuery(name = "Commandefournisseur.findByLivre", query = "SELECT c FROM Commandefournisseur c WHERE c.livre = :livre"),
    @NamedQuery(name = "Commandefournisseur.findByCode", query = "SELECT c FROM Commandefournisseur c WHERE c.code = :code"),
    @NamedQuery(name = "Commandefournisseur.findByDatecommande", query = "SELECT c FROM Commandefournisseur c WHERE c.datecommande = :datecommande"),
    @NamedQuery(name = "Commandefournisseur.findByPaye", query = "SELECT c FROM Commandefournisseur c WHERE c.paye = :paye"),
    @NamedQuery(name = "Commandefournisseur.findByTauxsatisfaction", query = "SELECT c FROM Commandefournisseur c WHERE c.tauxsatisfaction = :tauxsatisfaction")})
public class Commandefournisseur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idcommandefournisseur;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    @Temporal(TemporalType.DATE)
    private Date dateprevlivrasion;
    @Temporal(TemporalType.DATE)
    private Date dateeffectlivraison;
    private Boolean livre;
    @Size(max = 254)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date datecommande;
    private Boolean paye;
    private Double tauxsatisfaction;
    @OneToMany(mappedBy = "idcommandefournisseur", fetch = FetchType.LAZY)
    private List<Livraisonfournisseur> livraisonfournisseurList;
    @OneToMany(mappedBy = "idcommandefournisseur", fetch = FetchType.LAZY)
    private List<Lignecommandefournisseur> lignecommandefournisseurList;
    @JoinColumn(name = "idfournisseur", referencedColumnName = "idfournisseur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fournisseur idfournisseur;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    public Commandefournisseur() {
    }

    public Commandefournisseur(Long idcommandefournisseur) {
        this.idcommandefournisseur = idcommandefournisseur;
    }

    public Long getIdcommandefournisseur() {
        return idcommandefournisseur;
    }

    public void setIdcommandefournisseur(Long idcommandefournisseur) {
        this.idcommandefournisseur = idcommandefournisseur;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Date getDateprevlivrasion() {
        return dateprevlivrasion;
    }

    public void setDateprevlivrasion(Date dateprevlivrasion) {
        this.dateprevlivrasion = dateprevlivrasion;
    }

    public Date getDateeffectlivraison() {
        return dateeffectlivraison;
    }

    public void setDateeffectlivraison(Date dateeffectlivraison) {
        this.dateeffectlivraison = dateeffectlivraison;
    }

    public Boolean getLivre() {
        return livre;
    }

    public void setLivre(Boolean livre) {
        this.livre = livre;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDatecommande() {
        return datecommande;
    }

    public void setDatecommande(Date datecommande) {
        this.datecommande = datecommande;
    }

    public Boolean getPaye() {
        return paye;
    }

    public void setPaye(Boolean paye) {
        this.paye = paye;
    }

    public Double getTauxsatisfaction() {
        return tauxsatisfaction;
    }

    public void setTauxsatisfaction(Double tauxsatisfaction) {
        this.tauxsatisfaction = tauxsatisfaction;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @XmlTransient
    public List<Livraisonfournisseur> getLivraisonfournisseurList() {
        return livraisonfournisseurList;
    }

    public void setLivraisonfournisseurList(List<Livraisonfournisseur> livraisonfournisseurList) {
        this.livraisonfournisseurList = livraisonfournisseurList;
    }

    @XmlTransient
    public List<Lignecommandefournisseur> getLignecommandefournisseurList() {
        return lignecommandefournisseurList;
    }

    public void setLignecommandefournisseurList(List<Lignecommandefournisseur> lignecommandefournisseurList) {
        this.lignecommandefournisseurList = lignecommandefournisseurList;
    }

    public Fournisseur getIdfournisseur() {
        return idfournisseur;
    }

    public void setIdfournisseur(Fournisseur idfournisseur) {
        this.idfournisseur = idfournisseur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idcommandefournisseur != null ? idcommandefournisseur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Commandefournisseur)) {
            return false;
        }
        Commandefournisseur other = (Commandefournisseur) object;
        if ((this.idcommandefournisseur == null && other.idcommandefournisseur != null) || (this.idcommandefournisseur != null && !this.idcommandefournisseur.equals(other.idcommandefournisseur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Commandefournisseur[ idcommandefournisseur=" + idcommandefournisseur + " ]";
    }

}
