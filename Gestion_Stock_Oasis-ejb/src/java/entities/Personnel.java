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
    @NamedQuery(name = "Personnel.findAll", query = "SELECT p FROM Personnel p"),
    @NamedQuery(name = "Personnel.findByIdpersonnel", query = "SELECT p FROM Personnel p WHERE p.idpersonnel = :idpersonnel"),
    @NamedQuery(name = "Personnel.findByIdservice", query = "SELECT p FROM Personnel p WHERE p.idservice = :idservice"),
    @NamedQuery(name = "Personnel.findByMatricule", query = "SELECT p FROM Personnel p WHERE p.matricule = :matricule"),
    @NamedQuery(name = "Personnel.findByNiveauscolaire", query = "SELECT p FROM Personnel p WHERE p.niveauscolaire = :niveauscolaire"),
    @NamedQuery(name = "Personnel.findByDateembauche", query = "SELECT p FROM Personnel p WHERE p.dateembauche = :dateembauche"),
    @NamedQuery(name = "Personnel.findByNom", query = "SELECT p FROM Personnel p WHERE p.nom = :nom"),
    @NamedQuery(name = "Personnel.findByContact", query = "SELECT p FROM Personnel p WHERE p.contact = :contact"),
    @NamedQuery(name = "Personnel.findByPrenom", query = "SELECT p FROM Personnel p WHERE p.prenom = :prenom"),
    @NamedQuery(name = "Personnel.findByAddresse", query = "SELECT p FROM Personnel p WHERE p.addresse = :addresse")})
public class Personnel implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idpersonnel;
    private Integer idservice;
    @Size(max = 1024)
    private String matricule;
    @Size(max = 1024)
    private String niveauscolaire;
    @Temporal(TemporalType.DATE)
    private Date dateembauche;
    @Size(max = 1024)
    private String nom;
    @Size(max = 100)
    private String contact;
    @Size(max = 100)
    private String prenom;
    @Size(max = 100)
    private String addresse;
    @OneToMany(mappedBy = "idpersonnel", fetch = FetchType.LAZY)
    private List<Utilisateur> utilisateurList;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @JoinColumn(name = "idqualite", referencedColumnName = "idqualite")
    @ManyToOne(fetch = FetchType.LAZY)
    private Qualite idqualite;

    public Personnel() {
    }

    public Personnel(Long idpersonnel) {
        this.idpersonnel = idpersonnel;
    }

    public Long getIdpersonnel() {
        return idpersonnel;
    }

    public void setIdpersonnel(Long idpersonnel) {
        this.idpersonnel = idpersonnel;
    }

    public Integer getIdservice() {
        return idservice;
    }

    public void setIdservice(Integer idservice) {
        this.idservice = idservice;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public String getNiveauscolaire() {
        return niveauscolaire;
    }

    public void setNiveauscolaire(String niveauscolaire) {
        this.niveauscolaire = niveauscolaire;
    }

    public Date getDateembauche() {
        return dateembauche;
    }

    public void setDateembauche(Date dateembauche) {
        this.dateembauche = dateembauche;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    @XmlTransient
    public List<Utilisateur> getUtilisateurList() {
        return utilisateurList;
    }

    public void setUtilisateurList(List<Utilisateur> utilisateurList) {
        this.utilisateurList = utilisateurList;
    }

    public Magasin getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Magasin idmagasin) {
        this.idmagasin = idmagasin;
    }

    public Qualite getIdqualite() {
        return idqualite;
    }

    public void setIdqualite(Qualite idqualite) {
        this.idqualite = idqualite;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idpersonnel != null ? idpersonnel.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Personnel)) {
            return false;
        }
        Personnel other = (Personnel) object;
        if ((this.idpersonnel == null && other.idpersonnel != null) || (this.idpersonnel != null && !this.idpersonnel.equals(other.idpersonnel))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Personnel[ idpersonnel=" + idpersonnel + " ]";
    }

}
