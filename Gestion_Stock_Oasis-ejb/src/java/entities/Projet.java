/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Projet.findAll", query = "SELECT p FROM Projet p"),
    @NamedQuery(name = "Projet.findByIdprojet", query = "SELECT p FROM Projet p WHERE p.idprojet = :idprojet"),
    @NamedQuery(name = "Projet.findByNom", query = "SELECT p FROM Projet p WHERE p.nom = :nom"),
    @NamedQuery(name = "Projet.findByCode", query = "SELECT p FROM Projet p WHERE p.code = :code"),
    @NamedQuery(name = "Projet.findByDatecreation", query = "SELECT p FROM Projet p WHERE p.datecreation = :datecreation"),
    @NamedQuery(name = "Projet.findByCloturee", query = "SELECT p FROM Projet p WHERE p.cloturee = :cloturee")})
public class Projet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idprojet;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date datecreation;
    private Boolean cloturee;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;

    public Projet() {
    }

    public Projet(Integer idprojet) {
        this.idprojet = idprojet;
    }

    public Integer getIdprojet() {
        return idprojet;
    }

    public void setIdprojet(Integer idprojet) {
        this.idprojet = idprojet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public Boolean getCloturee() {
        return cloturee;
    }

    public void setCloturee(Boolean cloturee) {
        this.cloturee = cloturee;
    }

    public Magasin getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Magasin idmagasin) {
        this.idmagasin = idmagasin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprojet != null ? idprojet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Projet)) {
            return false;
        }
        Projet other = (Projet) object;
        if ((this.idprojet == null && other.idprojet != null) || (this.idprojet != null && !this.idprojet.equals(other.idprojet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Projet[ idprojet=" + idprojet + " ]";
    }

}
