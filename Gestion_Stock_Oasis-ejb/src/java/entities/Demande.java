/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Demande.findAll", query = "SELECT d FROM Demande d"),
    @NamedQuery(name = "Demande.findByIddemande", query = "SELECT d FROM Demande d WHERE d.iddemande = :iddemande"),
    @NamedQuery(name = "Demande.findByCode", query = "SELECT d FROM Demande d WHERE d.code = :code"),
    @NamedQuery(name = "Demande.findByDatedemande", query = "SELECT d FROM Demande d WHERE d.datedemande = :datedemande"),
    @NamedQuery(name = "Demande.findByDateprevlivraison", query = "SELECT d FROM Demande d WHERE d.dateprevlivraison = :dateprevlivraison"),
    @NamedQuery(name = "Demande.findByDateeffectlivraison", query = "SELECT d FROM Demande d WHERE d.dateeffectlivraison = :dateeffectlivraison"),
    @NamedQuery(name = "Demande.findByMontant", query = "SELECT d FROM Demande d WHERE d.montant = :montant"),
    @NamedQuery(name = "Demande.findByTauxsatisfaction", query = "SELECT d FROM Demande d WHERE d.tauxsatisfaction = :tauxsatisfaction"),
    @NamedQuery(name = "Demande.findByValidee", query = "SELECT d FROM Demande d WHERE d.validee = :validee"),
    @NamedQuery(name = "Demande.findByMotif", query = "SELECT d FROM Demande d WHERE d.motif = :motif")})
public class Demande implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long iddemande;
    @Size(max = 35)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date datedemande;
    @Temporal(TemporalType.DATE)
    private Date dateprevlivraison;
    @Temporal(TemporalType.DATE)
    private Date dateeffectlivraison;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    private Double tauxsatisfaction;
    private Boolean validee;
    @Size(max = 2147483647)
    private String motif;
    @OneToMany(mappedBy = "iddemande", fetch = FetchType.LAZY)
    private List<Lignedemande> lignedemandeList;
    @OneToMany(mappedBy = "iddemande", fetch = FetchType.LAZY)
    private List<Livraisonclient> livraisonclientList;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idclient", referencedColumnName = "idclient")
    private Client client;
    @Column(name = "tauxtva")
    private double tauxTva;
    @Column(name = "montanttva")
    private double montantTva;
    @Column(name = "montantttc")
    private double montantTtc;
    @Column(name = "tauxremise")
    private double tauxRemise;
    @Column(name = "montantremise")
    private double montantRemise;
    @Column(name = "montantht")
    private double montantHt;
    private double marge;

    public Demande() {
    }

    public Demande(Long iddemande) {
        this.iddemande = iddemande;
    }

    public Long getIddemande() {
        return iddemande;
    }

    public void setIddemande(Long iddemande) {
        this.iddemande = iddemande;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDatedemande() {
        return datedemande;
    }

    public void setDatedemande(Date datedemande) {
        this.datedemande = datedemande;
    }

    public Date getDateprevlivraison() {
        return dateprevlivraison;
    }

    public void setDateprevlivraison(Date dateprevlivraison) {
        this.dateprevlivraison = dateprevlivraison;
    }

    public Date getDateeffectlivraison() {
        return dateeffectlivraison;
    }

    public void setDateeffectlivraison(Date dateeffectlivraison) {
        this.dateeffectlivraison = dateeffectlivraison;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Double getTauxsatisfaction() {
        return tauxsatisfaction;
    }

    public void setTauxsatisfaction(Double tauxsatisfaction) {
        this.tauxsatisfaction = tauxsatisfaction;
    }

    public Boolean getValidee() {
        return validee;
    }

    public void setValidee(Boolean validee) {
        this.validee = validee;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public double getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(double tauxTva) {
        this.tauxTva = tauxTva;
    }

    public double getMontantTva() {
        return montantTva;
    }

    public void setMontantTva(double montantTva) {
        this.montantTva = montantTva;
    }

    public double getMontantTtc() {
        return montantTtc;
    }

    public void setMontantTtc(double montantTtc) {
        this.montantTtc = montantTtc;
    }

    public double getTauxRemise() {
        return tauxRemise;
    }

    public void setTauxRemise(double tauxRemise) {
        this.tauxRemise = tauxRemise;
    }

    public double getMontantRemise() {
        return montantRemise;
    }

    public void setMontantRemise(double montantRemise) {
        this.montantRemise = montantRemise;
    }

    public double getMontantHt() {
        return montantHt;
    }

    public void setMontantHt(double montantHt) {
        this.montantHt = montantHt;
    }

    public double getMarge() {
        return marge;
    }

    public void setMarge(double marge) {
        this.marge = marge;
    }

    @XmlTransient
    public List<Lignedemande> getLignedemandeList() {
        return lignedemandeList;
    }

    public void setLignedemandeList(List<Lignedemande> lignedemandeList) {
        this.lignedemandeList = lignedemandeList;
    }

    @XmlTransient
    public List<Livraisonclient> getLivraisonclientList() {
        return livraisonclientList;
    }

    public void setLivraisonclientList(List<Livraisonclient> livraisonclientList) {
        this.livraisonclientList = livraisonclientList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddemande != null ? iddemande.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Demande)) {
            return false;
        }
        Demande other = (Demande) object;
        if ((this.iddemande == null && other.iddemande != null) || (this.iddemande != null && !this.iddemande.equals(other.iddemande))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Demande[ iddemande=" + iddemande + " ]";
    }

}
