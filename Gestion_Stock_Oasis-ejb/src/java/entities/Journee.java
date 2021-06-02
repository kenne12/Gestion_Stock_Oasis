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
public class Journee implements Serializable {

    @Id
    @Basic(optional = false)
    @NotNull
    private Long idjournee;

    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin magasin;

    @JoinColumn(name = "idmois", referencedColumnName = "idmois")
    @ManyToOne(fetch = FetchType.LAZY)
    private AnneeMois anneeMois;

    @JoinColumn(name = "idutilisateur_ouverture", referencedColumnName = "idutilisateur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur utilisateurOuverture;
    
    @JoinColumn(name = "idutilisateur_fermetture", referencedColumnName = "idutilisateur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Utilisateur utilisateurFermetture;

    @Column(name = "heure_ouverture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureOuverture;
    @Column(name = "heure_fermetture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date heureFermetture;
    @Column(name = "date_jour")
    @Temporal(TemporalType.DATE)
    private Date dateJour;
    private boolean ouverte;
    private boolean fermee;
    @Column(name = "montant_vendu")
    private double montantVendu;
    private double bord;
    @Column(name = "montant_entre")
    private double montantEntre;
    @Column(name = "transfert_entrant")
    private double transfertEntrant;
    @Column(name = "transfert_sortant")
    private double transfertSortant;
    @Column(name = "qte_sortie")
    private double qteSortie;
    @Column(name = "qte_entrant")
    private double qteEntrant;
    @Column(name = "qte_transfert_entrant")
    private double qteTransfertEntrant;
    @Column(name = "qte_transfert_sortant")
    private double qteTransfertSortant;

    public Journee() {
    }

    public Journee(Long idjournee) {
        this.idjournee = idjournee;
    }

    public Long getIdjournee() {
        return idjournee;
    }

    public void setIdjournee(Long idjournee) {
        this.idjournee = idjournee;
    }

    public Magasin getMagasin() {
        return magasin;
    }

    public void setMagasin(Magasin magasin) {
        this.magasin = magasin;
    }

    public AnneeMois getAnneeMois() {
        return anneeMois;
    }

    public void setAnneeMois(AnneeMois anneeMois) {
        this.anneeMois = anneeMois;
    }

    public Utilisateur getUtilisateurOuverture() {
        return utilisateurOuverture;
    }

    public void setUtilisateurOuverture(Utilisateur utilisateurOuverture) {
        this.utilisateurOuverture = utilisateurOuverture;
    }

    public Utilisateur getUtilisateurFermetture() {
        return utilisateurFermetture;
    }

    public void setUtilisateurFermetture(Utilisateur utilisateurFermetture) {
        this.utilisateurFermetture = utilisateurFermetture;
    }

    public Date getHeureOuverture() {
        return heureOuverture;
    }

    public void setHeureOuverture(Date heureOuverture) {
        this.heureOuverture = heureOuverture;
    }

    public Date getHeureFermetture() {
        return heureFermetture;
    }

    public void setHeureFermetture(Date heureFermetture) {
        this.heureFermetture = heureFermetture;
    }

    public boolean isOuverte() {
        return ouverte;
    }

    public void setOuverte(boolean ouverte) {
        this.ouverte = ouverte;
    }

    public boolean isFermee() {
        return fermee;
    }

    public void setFermee(boolean fermee) {
        this.fermee = fermee;
    }

    public double getMontantVendu() {
        return montantVendu;
    }

    public void setMontantVendu(double montantVendu) {
        this.montantVendu = montantVendu;
    }

    public double getBord() {
        return bord;
    }

    public void setBord(double bord) {
        this.bord = bord;
    }

    public double getMontantEntre() {
        return montantEntre;
    }

    public void setMontantEntre(double montantEntre) {
        this.montantEntre = montantEntre;
    }

    public double getTransfertEntrant() {
        return transfertEntrant;
    }

    public void setTransfertEntrant(double transfertEntrant) {
        this.transfertEntrant = transfertEntrant;
    }

    public double getTransfertSortant() {
        return transfertSortant;
    }

    public void setTransfertSortant(double transfertSortant) {
        this.transfertSortant = transfertSortant;
    }

    public double getQteSortie() {
        return qteSortie;
    }

    public void setQteSortie(double qteSortie) {
        this.qteSortie = qteSortie;
    }

    public double getQteEntrant() {
        return qteEntrant;
    }

    public void setQteEntrant(double qteEntrant) {
        this.qteEntrant = qteEntrant;
    }

    public double getQteTransfertEntrant() {
        return qteTransfertEntrant;
    }

    public void setQteTransfertEntrant(double qteTransfertEntrant) {
        this.qteTransfertEntrant = qteTransfertEntrant;
    }

    public double getQteTransfertSortant() {
        return qteTransfertSortant;
    }

    public void setQteTransfertSortant(double qteTransfertSortant) {
        this.qteTransfertSortant = qteTransfertSortant;
    }

    public Date getDateJour() {
        return dateJour;
    }

    public void setDateJour(Date dateJour) {
        this.dateJour = dateJour;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.idjournee);
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
        final Journee other = (Journee) obj;
        if (!Objects.equals(this.idjournee, other.idjournee)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Journee{" + "idjournee=" + idjournee + ", magasin=" + magasin + ", anneeMois=" + anneeMois + ", utilisateurOuverture=" + utilisateurOuverture + ", utilisateurFermetture=" + utilisateurFermetture + ", heureOuverture=" + heureOuverture + ", heureFermetture=" + heureFermetture + ", ouverte=" + ouverte + ", fermee=" + fermee + ", montantVendu=" + montantVendu + ", bord=" + bord + ", montantEntre=" + montantEntre + ", transfertEntrant=" + transfertEntrant + ", transfertSortant=" + transfertSortant + ", qteSortie=" + qteSortie + ", qteEntrant=" + qteEntrant + ", qteTransfertEntrant=" + qteTransfertEntrant + ", qteTransfertSortant=" + qteTransfertSortant + '}';
    }

}
