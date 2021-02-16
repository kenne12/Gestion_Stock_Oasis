/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
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
    @NamedQuery(name = "Parametrage.findAll", query = "SELECT p FROM Parametrage p"),
    @NamedQuery(name = "Parametrage.findById", query = "SELECT p FROM Parametrage p WHERE p.id = :id"),
    @NamedQuery(name = "Parametrage.findByNomStructure", query = "SELECT p FROM Parametrage p WHERE p.nomStructure = :nomStructure"),
    @NamedQuery(name = "Parametrage.findByBoitePostale", query = "SELECT p FROM Parametrage p WHERE p.boitePostale = :boitePostale"),
    @NamedQuery(name = "Parametrage.findByContact1", query = "SELECT p FROM Parametrage p WHERE p.contact1 = :contact1"),
    @NamedQuery(name = "Parametrage.findByContact2", query = "SELECT p FROM Parametrage p WHERE p.contact2 = :contact2"),
    @NamedQuery(name = "Parametrage.findByContact3", query = "SELECT p FROM Parametrage p WHERE p.contact3 = :contact3"),
    @NamedQuery(name = "Parametrage.findByDescriptif", query = "SELECT p FROM Parametrage p WHERE p.descriptif = :descriptif"),
    @NamedQuery(name = "Parametrage.findByRcm", query = "SELECT p FROM Parametrage p WHERE p.rcm = :rcm"),
    @NamedQuery(name = "Parametrage.findByNoContrib", query = "SELECT p FROM Parametrage p WHERE p.noContrib = :noContrib"),
    @NamedQuery(name = "Parametrage.findByLocalisation", query = "SELECT p FROM Parametrage p WHERE p.localisation = :localisation"),
    @NamedQuery(name = "Parametrage.findByRepertoireLogo", query = "SELECT p FROM Parametrage p WHERE p.repertoireLogo = :repertoireLogo"),
    @NamedQuery(name = "Parametrage.findByLogo", query = "SELECT p FROM Parametrage p WHERE p.logo = :logo"),
    @NamedQuery(name = "Parametrage.findByRepertoireSauvegardre", query = "SELECT p FROM Parametrage p WHERE p.repertoireSauvegardre = :repertoireSauvegardre"),
    @NamedQuery(name = "Parametrage.findByTauxTva", query = "SELECT p FROM Parametrage p WHERE p.tauxTva = :tauxTva"),
    @NamedQuery(name = "Parametrage.findByCapital", query = "SELECT p FROM Parametrage p WHERE p.capital = :capital"),
    @NamedQuery(name = "Parametrage.findByTauxRemise", query = "SELECT p FROM Parametrage p WHERE p.tauxRemise = :tauxRemise"),
    @NamedQuery(name = "Parametrage.findByCheminTemplate", query = "SELECT p FROM Parametrage p WHERE p.cheminTemplate = :cheminTemplate"),
    @NamedQuery(name = "Parametrage.findByEtatQuantiteDosage", query = "SELECT p FROM Parametrage p WHERE p.etatQuantiteDosage = :etatQuantiteDosage"),
    @NamedQuery(name = "Parametrage.findByEtatFormeProduit", query = "SELECT p FROM Parametrage p WHERE p.etatFormeProduit = :etatFormeProduit"),
    @NamedQuery(name = "Parametrage.findByEtatFormeStockage", query = "SELECT p FROM Parametrage p WHERE p.etatFormeStockage = :etatFormeStockage"),
    @NamedQuery(name = "Parametrage.findByCalcultva", query = "SELECT p FROM Parametrage p WHERE p.calcultva = :calcultva"),
    @NamedQuery(name = "Parametrage.findByCalculRemise", query = "SELECT p FROM Parametrage p WHERE p.calculRemise = :calculRemise"),
    @NamedQuery(name = "Parametrage.findByFax", query = "SELECT p FROM Parametrage p WHERE p.fax = :fax"),
    @NamedQuery(name = "Parametrage.findByNbreJrAlertePeremption", query = "SELECT p FROM Parametrage p WHERE p.nbreJrAlertePeremption = :nbreJrAlertePeremption"),
    @NamedQuery(name = "Parametrage.findByPourcentageBailleur", query = "SELECT p FROM Parametrage p WHERE p.pourcentageBailleur = :pourcentageBailleur"),
    @NamedQuery(name = "Parametrage.findByBanque", query = "SELECT p FROM Parametrage p WHERE p.banque = :banque"),
    @NamedQuery(name = "Parametrage.findByNumeroCompte", query = "SELECT p FROM Parametrage p WHERE p.numeroCompte = :numeroCompte"),
    @NamedQuery(name = "Parametrage.findByEtatbailleur", query = "SELECT p FROM Parametrage p WHERE p.etatbailleur = :etatbailleur"),
    @NamedQuery(name = "Parametrage.findByEtatuser", query = "SELECT p FROM Parametrage p WHERE p.etatuser = :etatuser")})
public class Parametrage implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer id;
    @Size(max = 2147483647)
    @Column(name = "nom_structure")
    private String nomStructure;
    @Size(max = 2147483647)
    @Column(name = "boite_postale")
    private String boitePostale;
    @Size(max = 2147483647)
    @Column(name = "contact_1")
    private String contact1;
    @Size(max = 2147483647)
    @Column(name = "contact_2")
    private String contact2;
    @Size(max = 2147483647)
    @Column(name = "contact_3")
    private String contact3;
    @Size(max = 2147483647)
    private String descriptif;
    @Size(max = 2147483647)
    private String rcm;
    @Size(max = 2147483647)
    @Column(name = "no_contrib")
    private String noContrib;
    @Size(max = 2147483647)
    private String localisation;
    @Size(max = 2147483647)
    @Column(name = "repertoire_logo")
    private String repertoireLogo;
    @Size(max = 2147483647)
    private String logo;
    @Size(max = 2147483647)
    @Column(name = "repertoire_sauvegardre")
    private String repertoireSauvegardre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "taux_tva")
    private Double tauxTva;
    private Double capital;
    @Column(name = "taux_remise")
    private Double tauxRemise;
    @Size(max = 2147483647)
    @Column(name = "chemin_template")
    private String cheminTemplate;
    @Column(name = "etat_quantite_dosage")
    private Boolean etatQuantiteDosage;
    @Column(name = "etat_forme_produit")
    private Boolean etatFormeProduit;
    @Column(name = "etat_forme_stockage")
    private Boolean etatFormeStockage;
    private Boolean calcultva;
    @Column(name = "calcul_remise")
    private Boolean calculRemise;
    // @Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone/fax format, should be as xxx-xxx-xxxx")//if the field contains phone or fax number consider using this annotation to enforce field validation
    @Size(max = 2147483647)
    private String fax;
    @Column(name = "nbre_jr_alerte_peremption")
    private Integer nbreJrAlertePeremption;
    @Column(name = "pourcentage_bailleur")
    private Double pourcentageBailleur;
    @Size(max = 50)
    private String banque;
    @Size(max = 60)
    @Column(name = "numero_compte")
    private String numeroCompte;
    private Boolean etatbailleur;
    private Boolean etatuser;

    public Parametrage() {
    }

    public Parametrage(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomStructure() {
        return nomStructure;
    }

    public void setNomStructure(String nomStructure) {
        this.nomStructure = nomStructure;
    }

    public String getBoitePostale() {
        return boitePostale;
    }

    public void setBoitePostale(String boitePostale) {
        this.boitePostale = boitePostale;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact3() {
        return contact3;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getDescriptif() {
        return descriptif;
    }

    public void setDescriptif(String descriptif) {
        this.descriptif = descriptif;
    }

    public String getRcm() {
        return rcm;
    }

    public void setRcm(String rcm) {
        this.rcm = rcm;
    }

    public String getNoContrib() {
        return noContrib;
    }

    public void setNoContrib(String noContrib) {
        this.noContrib = noContrib;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public String getRepertoireLogo() {
        return repertoireLogo;
    }

    public void setRepertoireLogo(String repertoireLogo) {
        this.repertoireLogo = repertoireLogo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getRepertoireSauvegardre() {
        return repertoireSauvegardre;
    }

    public void setRepertoireSauvegardre(String repertoireSauvegardre) {
        this.repertoireSauvegardre = repertoireSauvegardre;
    }

    public Double getTauxTva() {
        return tauxTva;
    }

    public void setTauxTva(Double tauxTva) {
        this.tauxTva = tauxTva;
    }

    public Double getCapital() {
        return capital;
    }

    public void setCapital(Double capital) {
        this.capital = capital;
    }

    public Double getTauxRemise() {
        return tauxRemise;
    }

    public void setTauxRemise(Double tauxRemise) {
        this.tauxRemise = tauxRemise;
    }

    public String getCheminTemplate() {
        return cheminTemplate;
    }

    public void setCheminTemplate(String cheminTemplate) {
        this.cheminTemplate = cheminTemplate;
    }

    public Boolean getEtatQuantiteDosage() {
        return etatQuantiteDosage;
    }

    public void setEtatQuantiteDosage(Boolean etatQuantiteDosage) {
        this.etatQuantiteDosage = etatQuantiteDosage;
    }

    public Boolean getEtatFormeProduit() {
        return etatFormeProduit;
    }

    public void setEtatFormeProduit(Boolean etatFormeProduit) {
        this.etatFormeProduit = etatFormeProduit;
    }

    public Boolean getEtatFormeStockage() {
        return etatFormeStockage;
    }

    public void setEtatFormeStockage(Boolean etatFormeStockage) {
        this.etatFormeStockage = etatFormeStockage;
    }

    public Boolean getCalcultva() {
        return calcultva;
    }

    public void setCalcultva(Boolean calcultva) {
        this.calcultva = calcultva;
    }

    public Boolean getCalculRemise() {
        return calculRemise;
    }

    public void setCalculRemise(Boolean calculRemise) {
        this.calculRemise = calculRemise;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Integer getNbreJrAlertePeremption() {
        return nbreJrAlertePeremption;
    }

    public void setNbreJrAlertePeremption(Integer nbreJrAlertePeremption) {
        this.nbreJrAlertePeremption = nbreJrAlertePeremption;
    }

    public Double getPourcentageBailleur() {
        return pourcentageBailleur;
    }

    public void setPourcentageBailleur(Double pourcentageBailleur) {
        this.pourcentageBailleur = pourcentageBailleur;
    }

    public String getBanque() {
        return banque;
    }

    public void setBanque(String banque) {
        this.banque = banque;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Boolean getEtatbailleur() {
        return etatbailleur;
    }

    public void setEtatbailleur(Boolean etatbailleur) {
        this.etatbailleur = etatbailleur;
    }

    public Boolean getEtatuser() {
        return etatuser;
    }

    public void setEtatuser(Boolean etatuser) {
        this.etatuser = etatuser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Parametrage)) {
            return false;
        }
        Parametrage other = (Parametrage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Parametrage[ id=" + id + " ]";
    }
    
}
