/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumeration.ModeComptage;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    @NamedQuery(name = "Ligneinventaire.findAll", query = "SELECT l FROM Ligneinventaire l")
    ,
    @NamedQuery(name = "Ligneinventaire.findByIdligneinventaire", query = "SELECT l FROM Ligneinventaire l WHERE l.idligneinventaire = :idligneinventaire")
    ,
    @NamedQuery(name = "Ligneinventaire.findByQtetheorique", query = "SELECT l FROM Ligneinventaire l WHERE l.qtetheorique = :qtetheorique")
    ,
    @NamedQuery(name = "Ligneinventaire.findByQtephysique", query = "SELECT l FROM Ligneinventaire l WHERE l.qtephysique = :qtephysique")
    ,
    @NamedQuery(name = "Ligneinventaire.findByEcart", query = "SELECT l FROM Ligneinventaire l WHERE l.ecart = :ecart")
    ,
    @NamedQuery(name = "Ligneinventaire.findByObservation", query = "SELECT l FROM Ligneinventaire l WHERE l.observation = :observation")})
public class Ligneinventaire implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idligneinventaire;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private double qtetheorique;

    @Column(name = "qtetheorique_multiple")
    private double qtetheoriqueMultiple;

    private double qtephysique;
    @Column(name = "qtephysique_multiple")
    private double qtephysiqueMultiple;
    
    private double quantite;

    private double ecart;
    @Column(name = "prix_unitaire")
    
    private double prixUnitaire;
    @Column(name = "montant_total")
    private double montantTotal;


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

    private double unite;

    @JoinColumn(name = "idunite", referencedColumnName = "idunite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Unite idunite;

    @Column(name = "mode_comptage", length = 20)
    @Enumerated(EnumType.STRING)
    private ModeComptage modeComptage;

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

    public double getQtetheorique() {
        return qtetheorique;
    }

    public void setQtetheorique(double qtetheorique) {
        this.qtetheorique = qtetheorique;
    }

    public double getQtephysique() {
        return qtephysique;
    }

    public void setQtephysique(Double qtephysique) {
        this.qtephysique = qtephysique;
    }

    public double getEcart() {
        return ecart;
    }

    public void setEcart(double ecart) {
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

    public ModeComptage getModeComptage() {
        return modeComptage;
    }

    public void setModeComptage(ModeComptage modeComptage) {
        this.modeComptage = modeComptage;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public double getQtetheoriqueMultiple() {
        return qtetheoriqueMultiple;
    }

    public void setQtetheoriqueMultiple(double qtetheoriqueMultiple) {
        this.qtetheoriqueMultiple = qtetheoriqueMultiple;
    }

    public double getQtephysiqueMultiple() {
        return qtephysiqueMultiple;
    }

    public void setQtephysiqueMultiple(double qtephysiqueMultiple) {
        this.qtephysiqueMultiple = qtephysiqueMultiple;
    }

    public Unite getIdunite() {
        return idunite;
    }

    public void setIdunite(Unite idunite) {
        this.idunite = idunite;
    }

    public double getUnite() {
        return unite;
    }

    public void setUnite(double unite) {
        this.unite = unite;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
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
