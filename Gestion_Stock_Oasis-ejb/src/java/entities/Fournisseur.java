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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
    @NamedQuery(name = "Fournisseur.findAll", query = "SELECT f FROM Fournisseur f"),
    @NamedQuery(name = "Fournisseur.findByIdfournisseur", query = "SELECT f FROM Fournisseur f WHERE f.idfournisseur = :idfournisseur"),
    @NamedQuery(name = "Fournisseur.findByNom", query = "SELECT f FROM Fournisseur f WHERE f.nom = :nom"),
    @NamedQuery(name = "Fournisseur.findByAdresse", query = "SELECT f FROM Fournisseur f WHERE f.adresse = :adresse"),
    @NamedQuery(name = "Fournisseur.findByContact", query = "SELECT f FROM Fournisseur f WHERE f.contact = :contact"),
    @NamedQuery(name = "Fournisseur.findByEmail", query = "SELECT f FROM Fournisseur f WHERE f.email = :email")})
public class Fournisseur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idfournisseur;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String adresse;
    @Size(max = 254)
    private String contact;
    // @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?", message="Invalid email")//if the field contains email address consider using this annotation to enforce field validation
    @Size(max = 254)
    private String email;
    @OneToMany(mappedBy = "idfournisseur", fetch = FetchType.LAZY)
    private List<Livraisonfournisseur> livraisonfournisseurList;
    @OneToMany(mappedBy = "idfournisseur", fetch = FetchType.LAZY)
    private List<Commandefournisseur> commandefournisseurList;

    public Fournisseur() {
    }

    public Fournisseur(Integer idfournisseur) {
        this.idfournisseur = idfournisseur;
    }

    public Integer getIdfournisseur() {
        return idfournisseur;
    }

    public void setIdfournisseur(Integer idfournisseur) {
        this.idfournisseur = idfournisseur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlTransient
    public List<Livraisonfournisseur> getLivraisonfournisseurList() {
        return livraisonfournisseurList;
    }

    public void setLivraisonfournisseurList(List<Livraisonfournisseur> livraisonfournisseurList) {
        this.livraisonfournisseurList = livraisonfournisseurList;
    }

    @XmlTransient
    public List<Commandefournisseur> getCommandefournisseurList() {
        return commandefournisseurList;
    }

    public void setCommandefournisseurList(List<Commandefournisseur> commandefournisseurList) {
        this.commandefournisseurList = commandefournisseurList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idfournisseur != null ? idfournisseur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Fournisseur)) {
            return false;
        }
        Fournisseur other = (Fournisseur) object;
        if ((this.idfournisseur == null && other.idfournisseur != null) || (this.idfournisseur != null && !this.idfournisseur.equals(other.idfournisseur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Fournisseur[ idfournisseur=" + idfournisseur + " ]";
    }
    
}
