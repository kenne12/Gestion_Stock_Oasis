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
    @NamedQuery(name = "Mouchard.findAll", query = "SELECT m FROM Mouchard m"),
    @NamedQuery(name = "Mouchard.findByIdmouchard", query = "SELECT m FROM Mouchard m WHERE m.idmouchard = :idmouchard"),
    @NamedQuery(name = "Mouchard.findByAction", query = "SELECT m FROM Mouchard m WHERE m.action = :action"),
    @NamedQuery(name = "Mouchard.findByDate", query = "SELECT m FROM Mouchard m WHERE m.date = :date"),
    @NamedQuery(name = "Mouchard.findByHeure", query = "SELECT m FROM Mouchard m WHERE m.heure = :heure")})
public class Mouchard implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idmouchard;
    @Size(max = 254)
    private String action;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Temporal(TemporalType.TIME)
    private Date heure;
    @JoinColumn(name = "idutilisateur", referencedColumnName = "idutilisateur")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Utilisateur idutilisateur;

    public Mouchard() {
    }

    public Mouchard(Long idmouchard) {
        this.idmouchard = idmouchard;
    }

    public Long getIdmouchard() {
        return idmouchard;
    }

    public void setIdmouchard(Long idmouchard) {
        this.idmouchard = idmouchard;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public Utilisateur getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Utilisateur idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmouchard != null ? idmouchard.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mouchard)) {
            return false;
        }
        Mouchard other = (Mouchard) object;
        if ((this.idmouchard == null && other.idmouchard != null) || (this.idmouchard != null && !this.idmouchard.equals(other.idmouchard))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Mouchard[ idmouchard=" + idmouchard + " ]";
    }
    
}
