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
    @NamedQuery(name = "Qualite.findAll", query = "SELECT q FROM Qualite q"),
    @NamedQuery(name = "Qualite.findByIdqualite", query = "SELECT q FROM Qualite q WHERE q.idqualite = :idqualite"),
    @NamedQuery(name = "Qualite.findByNom", query = "SELECT q FROM Qualite q WHERE q.nom = :nom"),
    @NamedQuery(name = "Qualite.findByCode", query = "SELECT q FROM Qualite q WHERE q.code = :code")})
public class Qualite implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idqualite;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String code;
    @OneToMany(mappedBy = "idqualite", fetch = FetchType.LAZY)
    private List<Personnel> personnelList;

    public Qualite() {
    }

    public Qualite(Integer idqualite) {
        this.idqualite = idqualite;
    }

    public Integer getIdqualite() {
        return idqualite;
    }

    public void setIdqualite(Integer idqualite) {
        this.idqualite = idqualite;
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

    @XmlTransient
    public List<Personnel> getPersonnelList() {
        return personnelList;
    }

    public void setPersonnelList(List<Personnel> personnelList) {
        this.personnelList = personnelList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idqualite != null ? idqualite.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Qualite)) {
            return false;
        }
        Qualite other = (Qualite) object;
        if ((this.idqualite == null && other.idqualite != null) || (this.idqualite != null && !this.idqualite.equals(other.idqualite))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Qualite[ idqualite=" + idqualite + " ]";
    }
    
}
