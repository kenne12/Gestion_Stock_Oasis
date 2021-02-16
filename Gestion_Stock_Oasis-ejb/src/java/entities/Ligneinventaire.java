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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ligneinventaire.findAll", query = "SELECT l FROM Ligneinventaire l"),
    @NamedQuery(name = "Ligneinventaire.findByIdligneinventaire", query = "SELECT l FROM Ligneinventaire l WHERE l.idligneinventaire = :idligneinventaire"),
    @NamedQuery(name = "Ligneinventaire.findByQtetheorique", query = "SELECT l FROM Ligneinventaire l WHERE l.qtetheorique = :qtetheorique"),
    @NamedQuery(name = "Ligneinventaire.findByQtephysique", query = "SELECT l FROM Ligneinventaire l WHERE l.qtephysique = :qtephysique"),
    @NamedQuery(name = "Ligneinventaire.findByEcart", query = "SELECT l FROM Ligneinventaire l WHERE l.ecart = :ecart"),
    @NamedQuery(name = "Ligneinventaire.findByObservation", query = "SELECT l FROM Ligneinventaire l WHERE l.observation = :observation")})
public class Ligneinventaire implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idligneinventaire;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double qtetheorique;
    private Double qtephysique;
    private Double ecart;
    @Size(max = 100)
    private String observation;
    @JoinColumn(name = "idinventaire", referencedColumnName = "idinventaire")
    @ManyToOne(fetch = FetchType.LAZY)
    private Inventaire idinventaire;
    @JoinColumn(name = "idlot", referencedColumnName = "idlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot idlot;
    @JoinColumn(name = "idmagasinlot", referencedColumnName = "idmagasinlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinlot idmagasinlot;

    public Ligneinventaire() {
    }

    public Ligneinventaire(Long idligneinventaire) {
        this.idligneinventaire = idligneinventaire;
    }

    public Long getIdligneinventaire() {
        return idligneinventaire;
    }

    public void setIdligneinventaire(Long idligneinventaire) {
        this.idligneinventaire = idligneinventaire;
    }

    public Double getQtetheorique() {
        return qtetheorique;
    }

    public void setQtetheorique(Double qtetheorique) {
        this.qtetheorique = qtetheorique;
    }

    public Double getQtephysique() {
        return qtephysique;
    }

    public void setQtephysique(Double qtephysique) {
        this.qtephysique = qtephysique;
    }

    public Double getEcart() {
        return ecart;
    }

    public void setEcart(Double ecart) {
        this.ecart = ecart;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Inventaire getIdinventaire() {
        return idinventaire;
    }

    public void setIdinventaire(Inventaire idinventaire) {
        this.idinventaire = idinventaire;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idligneinventaire != null ? idligneinventaire.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ligneinventaire)) {
            return false;
        }
        Ligneinventaire other = (Ligneinventaire) object;
        if ((this.idligneinventaire == null && other.idligneinventaire != null) || (this.idligneinventaire != null && !this.idligneinventaire.equals(other.idligneinventaire))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ligneinventaire[ idligneinventaire=" + idligneinventaire + " ]";
    }
    
}
