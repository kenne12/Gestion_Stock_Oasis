/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author USER
 */
@Entity
public class Versement implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    private Long idversement;

    private double montant;
    @Temporal(TemporalType.TIMESTAMP)
    private Date heure;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idlivraisonclient", referencedColumnName = "idlivraisonclient")
    private Livraisonclient livraisonclient;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_operation")
    private Date dateOperation;
    private double reste;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idmois", referencedColumnName = "idmois")
    private AnneeMois mois;
    private String code;

    public Versement() {
    }

    public Versement(Long idversement) {
        this.idversement = idversement;
    }

    public Long getIdversement() {
        return idversement;
    }

    public void setIdversement(Long idversement) {
        this.idversement = idversement;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public Livraisonclient getLivraisonclient() {
        return livraisonclient;
    }

    public void setLivraisonclient(Livraisonclient livraisonclient) {
        this.livraisonclient = livraisonclient;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public AnneeMois getMois() {
        return mois;
    }

    public void setMois(AnneeMois mois) {
        this.mois = mois;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.idversement);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Versement other = (Versement) obj;
        if (!Objects.equals(this.idversement, other.idversement)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Versement{" + "idversement=" + idversement + ", montant=" + montant + ", heure=" + heure + ", livraisonclient=" + livraisonclient + ", date=" + dateOperation + ", reste=" + reste + ", mois=" + mois + ", code=" + code + '}';
    }

}
