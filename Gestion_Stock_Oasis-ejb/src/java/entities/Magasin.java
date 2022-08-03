/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import enumeration.ModeComptage;
import enumeration.ModeEntreSorti;
import enumeration.ModeleFacture;
import java.io.Serializable;
import java.util.List;
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
    @NamedQuery(name = "Magasin.findAll", query = "SELECT m FROM Magasin m")
    ,
    @NamedQuery(name = "Magasin.findByIdmagasin", query = "SELECT m FROM Magasin m WHERE m.idmagasin = :idmagasin")
    ,
    @NamedQuery(name = "Magasin.findByNom", query = "SELECT m FROM Magasin m WHERE m.nom = :nom")
    ,
    @NamedQuery(name = "Magasin.findByCode", query = "SELECT m FROM Magasin m WHERE m.code = :code")
    ,
    @NamedQuery(name = "Magasin.findByCentral", query = "SELECT m FROM Magasin m WHERE m.central = :central")})
public class Magasin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idmagasin;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String code;
    private Boolean central;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Livraisonfournisseur> livraisonfournisseurList;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Magasinarticle> magasinarticleList;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Projet> projetList;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Transfert> transfertList;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Inventaire> inventaireList;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Livraisonclient> livraisonclientList;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Personnel> personnelList;
    @OneToMany(mappedBy = "idmagasin", fetch = FetchType.LAZY)
    private List<Utilisateurmagasin> utilisateurmagasinList;
    @JoinColumn(name = "idstructure", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Parametrage parametrage;

    @Column(name = "mode_entre_default")
    @Enumerated(EnumType.STRING)
    private ModeEntreSorti modeEntreDefault;

    @Column(name = "mode_sorti_default")
    @Enumerated(EnumType.STRING)
    private ModeEntreSorti modeSortiDefault;

    @Column(name = "mode_comptage")
    @Enumerated(EnumType.STRING)
    private ModeComptage modeComptage;

    @Column(name = "modele_facture" , length = 30)
    @Enumerated(EnumType.STRING)
    private ModeleFacture modeleFacture;

    public Magasin() {
    }

    public Magasin(Integer idmagasin) {
        this.idmagasin = idmagasin;
    }

    public Integer getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Integer idmagasin) {
        this.idmagasin = idmagasin;
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

    public Boolean getCentral() {
        return central;
    }

    public void setCentral(Boolean central) {
        this.central = central;
    }

    public Parametrage getParametrage() {
        return parametrage;
    }

    public void setParametrage(Parametrage parametrage) {
        this.parametrage = parametrage;
    }

    public ModeComptage getModeComptage() {
        return modeComptage;
    }

    public void setModeComptage(ModeComptage modeComptage) {
        this.modeComptage = modeComptage;
    }

    public ModeleFacture getModeleFacture() {
        return modeleFacture;
    }

    public void setModeleFacture(ModeleFacture modeleFacture) {
        this.modeleFacture = modeleFacture;
    }

    @XmlTransient
    public List<Livraisonfournisseur> getLivraisonfournisseurList() {
        return livraisonfournisseurList;
    }

    public void setLivraisonfournisseurList(List<Livraisonfournisseur> livraisonfournisseurList) {
        this.livraisonfournisseurList = livraisonfournisseurList;
    }

    @XmlTransient
    public List<Magasinarticle> getMagasinarticleList() {
        return magasinarticleList;
    }

    public void setMagasinarticleList(List<Magasinarticle> magasinarticleList) {
        this.magasinarticleList = magasinarticleList;
    }

    @XmlTransient
    public List<Projet> getProjetList() {
        return projetList;
    }

    public void setProjetList(List<Projet> projetList) {
        this.projetList = projetList;
    }

    @XmlTransient
    public List<Transfert> getTransfertList() {
        return transfertList;
    }

    public void setTransfertList(List<Transfert> transfertList) {
        this.transfertList = transfertList;
    }

    @XmlTransient
    public List<Inventaire> getInventaireList() {
        return inventaireList;
    }

    public void setInventaireList(List<Inventaire> inventaireList) {
        this.inventaireList = inventaireList;
    }

    @XmlTransient
    public List<Livraisonclient> getLivraisonclientList() {
        return livraisonclientList;
    }

    public void setLivraisonclientList(List<Livraisonclient> livraisonclientList) {
        this.livraisonclientList = livraisonclientList;
    }

    @XmlTransient
    public List<Personnel> getPersonnelList() {
        return personnelList;
    }

    public void setPersonnelList(List<Personnel> personnelList) {
        this.personnelList = personnelList;
    }

    @XmlTransient
    public List<Utilisateurmagasin> getUtilisateurmagasinList() {
        return utilisateurmagasinList;
    }

    public void setUtilisateurmagasinList(List<Utilisateurmagasin> utilisateurmagasinList) {
        this.utilisateurmagasinList = utilisateurmagasinList;
    }

    public ModeEntreSorti getModeEntreDefault() {
        return modeEntreDefault;
    }

    public void setModeEntreDefault(ModeEntreSorti modeEntreDefault) {
        this.modeEntreDefault = modeEntreDefault;
    }

    public ModeEntreSorti getModeSortiDefault() {
        return modeSortiDefault;
    }

    public void setModeSortiDefault(ModeEntreSorti modeSortiDefault) {
        this.modeSortiDefault = modeSortiDefault;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmagasin != null ? idmagasin.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magasin)) {
            return false;
        }
        Magasin other = (Magasin) object;
        if ((this.idmagasin == null && other.idmagasin != null) || (this.idmagasin != null && !this.idmagasin.equals(other.idmagasin))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Magasin[ idmagasin=" + idmagasin + " ]";
    }

}
