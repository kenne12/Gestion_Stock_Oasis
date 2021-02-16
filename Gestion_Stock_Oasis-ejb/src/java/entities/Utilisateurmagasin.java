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
    @NamedQuery(name = "Utilisateurmagasin.findAll", query = "SELECT u FROM Utilisateurmagasin u"),
    @NamedQuery(name = "Utilisateurmagasin.findByIdutilisateurmagasin", query = "SELECT u FROM Utilisateurmagasin u WHERE u.idutilisateurmagasin = :idutilisateurmagasin")})
public class Utilisateurmagasin implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idutilisateurmagasin;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @JoinColumn(name = "idutilisateur", referencedColumnName = "idutilisateur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur idutilisateur;

    public Utilisateurmagasin() {
    }

    public Utilisateurmagasin(Long idutilisateurmagasin) {
        this.idutilisateurmagasin = idutilisateurmagasin;
    }

    public Long getIdutilisateurmagasin() {
        return idutilisateurmagasin;
    }

    public void setIdutilisateurmagasin(Long idutilisateurmagasin) {
        this.idutilisateurmagasin = idutilisateurmagasin;
    }

    public Magasin getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Magasin idmagasin) {
        this.idmagasin = idmagasin;
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
        hash += (idutilisateurmagasin != null ? idutilisateurmagasin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateurmagasin)) {
            return false;
        }
        Utilisateurmagasin other = (Utilisateurmagasin) object;
        if ((this.idutilisateurmagasin == null && other.idutilisateurmagasin != null) || (this.idutilisateurmagasin != null && !this.idutilisateurmagasin.equals(other.idutilisateurmagasin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Utilisateurmagasin[ idutilisateurmagasin=" + idutilisateurmagasin + " ]";
    }
    
}
