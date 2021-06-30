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
    @NamedQuery(name = "Livraisonclient.findAll", query = "SELECT l FROM Livraisonclient l"),
    @NamedQuery(name = "Livraisonclient.findByIdlivraisonclient", query = "SELECT l FROM Livraisonclient l WHERE l.idlivraisonclient = :idlivraisonclient"),
    @NamedQuery(name = "Livraisonclient.findByLivraisondirecte", query = "SELECT l FROM Livraisonclient l WHERE l.livraisondirecte = :livraisondirecte"),
    @NamedQuery(name = "Livraisonclient.findByMontant", query = "SELECT l FROM Livraisonclient l WHERE l.montant = :montant"),
    @NamedQuery(name = "Livraisonclient.findByCode", query = "SELECT l FROM Livraisonclient l WHERE l.code = :code"),
    @NamedQuery(name = "Livraisonclient.findByDatelivraison", query = "SELECT l FROM Livraisonclient l WHERE l.datelivraison = :datelivraison")})
public class Livraisonclient implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlivraisonclient;
    private Boolean livraisondirecte;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private Double montant;
    @Size(max = 40)
    private String code;
    @Temporal(TemporalType.DATE)
    private Date datelivraison;
    @JoinColumn(name = "iddemande", referencedColumnName = "iddemande")
    @ManyToOne(fetch = FetchType.LAZY)
    private Demande iddemande;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @JoinColumn(name = "idmvtstock", referencedColumnName = "idmvtstock")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mvtstock idmvtstock;
    @OneToMany(mappedBy = "idlivraisonclient", fetch = FetchType.LAZY)
    private List<Lignelivraisonclient> lignelivraisonclientList;
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
    @Column(name = "avance_initiale")
    private double avanceInitiale;
    @Column(name = "mode_payement")
    private String modePayement;
    @Column(name = "montant_paye", length = 15)
    private double montantPaye;
    private boolean comptabilise;
    private double reste;
    private boolean paye;

    @Column(name = "idutilisateur")
    private int idUtilisateur;

    public Livraisonclient() {
        this.client = new Client();
    }

    public Livraisonclient(Long idlivraisonclient) {
        this.idlivraisonclient = idlivraisonclient;
    }

    public Long getIdlivraisonclient() {
        return idlivraisonclient;
    }

    public void setIdlivraisonclient(Long idlivraisonclient) {
        this.idlivraisonclient = idlivraisonclient;
    }

    public Boolean getLivraisondirecte() {
        return livraisondirecte;
    }

    public void setLivraisondirecte(Boolean livraisondirecte) {
        this.livraisondirecte = livraisondirecte;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDatelivraison() {
        return datelivraison;
    }

    public void setDatelivraison(Date datelivraison) {
        this.datelivraison = datelivraison;
    }

    public Demande getIddemande() {
        return iddemande;
    }

    public void setIddemande(Demande iddemande) {
        this.iddemande = iddemande;
    }

    public Magasin getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Magasin idmagasin) {
        this.idmagasin = idmagasin;
    }

    public Mvtstock getIdmvtstock() {
        return idmvtstock;
    }

    public void setIdmvtstock(Mvtstock idmvtstock) {
        this.idmvtstock = idmvtstock;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @XmlTransient
    public List<Lignelivraisonclient> getLignelivraisonclientList() {
        return lignelivraisonclientList;
    }

    public void setLignelivraisonclientList(List<Lignelivraisonclient> lignelivraisonclientList) {
        this.lignelivraisonclientList = lignelivraisonclientList;
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

    public double getAvanceInitiale() {
        return avanceInitiale;
    }

    public void setAvanceInitiale(double avanceInitiale) {
        this.avanceInitiale = avanceInitiale;
    }

    public String getModePayement() {
        return modePayement;
    }

    public void setModePayement(String modePayement) {
        this.modePayement = modePayement;
    }

    public double getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(double montantPaye) {
        this.montantPaye = montantPaye;
    }

    public boolean isComptabilise() {
        return comptabilise;
    }

    public void setComptabilise(boolean comptabilise) {
        this.comptabilise = comptabilise;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public boolean isPaye() {
        return paye;
    }

    public void setPaye(boolean paye) {
        this.paye = paye;
    }

    public int getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(int idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlivraisonclient != null ? idlivraisonclient.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Livraisonclient)) {
            return false;
        }
        Livraisonclient other = (Livraisonclient) object;
        if ((this.idlivraisonclient == null && other.idlivraisonclient != null) || (this.idlivraisonclient != null && !this.idlivraisonclient.equals(other.idlivraisonclient))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Livraisonclient[ idlivraisonclient=" + idlivraisonclient + " ]";
    }

}
