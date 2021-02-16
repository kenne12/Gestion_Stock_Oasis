/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
    @NamedQuery(name = "Laboratoire.findAll", query = "SELECT l FROM Laboratoire l"),
    @NamedQuery(name = "Laboratoire.findByIdlaboratoire", query = "SELECT l FROM Laboratoire l WHERE l.idlaboratoire = :idlaboratoire"),
    @NamedQuery(name = "Laboratoire.findByNom", query = "SELECT l FROM Laboratoire l WHERE l.nom = :nom"),
    @NamedQuery(name = "Laboratoire.findByCode", query = "SELECT l FROM Laboratoire l WHERE l.code = :code")})
public class Laboratoire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idlaboratoire;
    @Size(max = 60)
    private String nom;
    @Size(max = 20)
    private String code;

    public Laboratoire() {
    }

    public Laboratoire(Integer idlaboratoire) {
        this.idlaboratoire = idlaboratoire;
    }

    public Integer getIdlaboratoire() {
        return idlaboratoire;
    }

    public void setIdlaboratoire(Integer idlaboratoire) {
        this.idlaboratoire = idlaboratoire;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlaboratoire != null ? idlaboratoire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Laboratoire)) {
            return false;
        }
        Laboratoire other = (Laboratoire) object;
        if ((this.idlaboratoire == null && other.idlaboratoire != null) || (this.idlaboratoire != null && !this.idlaboratoire.equals(other.idlaboratoire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Laboratoire[ idlaboratoire=" + idlaboratoire + " ]";
    }
    
}
