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
    @NamedQuery(name = "Livraisonfournisseur.findAll", query = "SELECT l FROM Livraisonfournisseur l")
    ,
    @NamedQuery(name = "Livraisonfournisseur.findByIdlivraisonfournisseur", query = "SELECT l FROM Livraisonfournisseur l WHERE l.idlivraisonfournisseur = :idlivraisonfournisseur")
    ,
    @NamedQuery(name = "Livraisonfournisseur.findByDatelivraison", query = "SELECT l FROM Livraisonfournisseur l WHERE l.datelivraison = :datelivraison")
    ,
    @NamedQuery(name = "Livraisonfournisseur.findByMontant", query = "SELECT l FROM Livraisonfournisseur l WHERE l.montant = :montant")
    ,
    @NamedQuery(name = "Livraisonfournisseur.findByRepartie", query = "SELECT l FROM Livraisonfournisseur l WHERE l.repartie = :repartie")
    ,
    @NamedQuery(name = "Livraisonfournisseur.findByCode", query = "SELECT l FROM Livraisonfournisseur l WHERE l.code = :code")
    ,
    @NamedQuery(name = "Livraisonfournisseur.findByLivraisondirecte", query = "SELECT l FROM Livraisonfournisseur l WHERE l.livraisondirecte = :livraisondirecte")})
public class Livraisonfournisseur implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlivraisonfournisseur;
    @Temporal(TemporalType.DATE)
    private Date datelivraison;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    private Boolean repartie;
    @Column(name = "modification_cout")
    private boolean modificationCout;
    @Size(max = 254)
    private String code;
    private Boolean livraisondirecte;
    @JoinColumn(name = "idcommandefournisseur", referencedColumnName = "idcommandefournisseur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Commandefournisseur idcommandefournisseur;
    @JoinColumn(name = "idfournisseur", referencedColumnName = "idfournisseur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Fournisseur idfournisseur;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @JoinColumn(name = "idmvtstock", referencedColumnName = "idmvtstock")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mvtstock idmvtstock;
    @OneToMany(mappedBy = "idlivraisonfournisseur", fetch = FetchType.LAZY)
    private List<Lignelivraisonfournisseur> lignelivraisonfournisseurList;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    private boolean comptabilise;

    public Livraisonfournisseur() {
        this.__constructor();
    }

    private void __constructor() {
        this.modificationCout = true;
        this.montant = 0.0;
    }

    public Livraisonfournisseur(Long idlivraisonfournisseur) {
        this.idlivraisonfournisseur = idlivraisonfournisseur;
    }

    public Long getIdlivraisonfournisseur() {
        return idlivraisonfournisseur;
    }

    public void setIdlivraisonfournisseur(Long idlivraisonfournisseur) {
        this.idlivraisonfournisseur = idlivraisonfournisseur;
    }

    public Date getDatelivraison() {
        return datelivraison;
    }

    public void setDatelivraison(Date datelivraison) {
        this.datelivraison = datelivraison;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Boolean getRepartie() {
        return repartie;
    }

    public void setRepartie(Boolean repartie) {
        this.repartie = repartie;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getLivraisondirecte() {
        return livraisondirecte;
    }

    public void setLivraisondirecte(Boolean livraisondirecte) {
        this.livraisondirecte = livraisondirecte;
    }

    public Commandefournisseur getIdcommandefournisseur() {
        return idcommandefournisseur;
    }

    public void setIdcommandefournisseur(Commandefournisseur idcommandefournisseur) {
        this.idcommandefournisseur = idcommandefournisseur;
    }

    public Fournisseur getIdfournisseur() {
        return idfournisseur;
    }

    public void setIdfournisseur(Fournisseur idfournisseur) {
        this.idfournisseur = idfournisseur;
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

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public boolean isComptabilise() {
        return comptabilise;
    }

    public void setComptabilise(boolean comptabilise) {
        this.comptabilise = comptabilise;
    }

    public boolean isModificationCout() {
        return modificationCout;
    }

    public void setModificationCout(boolean modificationCout) {
        this.modificationCout = modificationCout;
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
        hash += (idlivraisonfournisseur != null ? idlivraisonfournisseur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livraisonfournisseur)) {
            return false;
        }
        Livraisonfournisseur other = (Livraisonfournisseur) object;
        if ((this.idlivraisonfournisseur == null && other.idlivraisonfournisseur != null) || (this.idlivraisonfournisseur != null && !this.idlivraisonfournisseur.equals(other.idlivraisonfournisseur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Livraisonfournisseur[ idlivraisonfournisseur=" + idlivraisonfournisseur + " ]";
    }

}
