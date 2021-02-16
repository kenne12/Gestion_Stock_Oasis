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
import javax.persistence.CascadeType;
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
    @NamedQuery(name = "Utilisateur.findAll", query = "SELECT u FROM Utilisateur u"),
    @NamedQuery(name = "Utilisateur.findByIdutilisateur", query = "SELECT u FROM Utilisateur u WHERE u.idutilisateur = :idutilisateur"),
    @NamedQuery(name = "Utilisateur.findByNom", query = "SELECT u FROM Utilisateur u WHERE u.nom = :nom"),
    @NamedQuery(name = "Utilisateur.findByPrenom", query = "SELECT u FROM Utilisateur u WHERE u.prenom = :prenom"),
    @NamedQuery(name = "Utilisateur.findByLogin", query = "SELECT u FROM Utilisateur u WHERE u.login = :login"),
    @NamedQuery(name = "Utilisateur.findByPassword", query = "SELECT u FROM Utilisateur u WHERE u.password = :password"),
    @NamedQuery(name = "Utilisateur.findByPhoto", query = "SELECT u FROM Utilisateur u WHERE u.photo = :photo"),
    @NamedQuery(name = "Utilisateur.findByTemplate", query = "SELECT u FROM Utilisateur u WHERE u.template = :template"),
    @NamedQuery(name = "Utilisateur.findByTheme", query = "SELECT u FROM Utilisateur u WHERE u.theme = :theme"),
    @NamedQuery(name = "Utilisateur.findByDatecreation", query = "SELECT u FROM Utilisateur u WHERE u.datecreation = :datecreation"),
    @NamedQuery(name = "Utilisateur.findByDatederniereconnexion", query = "SELECT u FROM Utilisateur u WHERE u.datederniereconnexion = :datederniereconnexion"),
    @NamedQuery(name = "Utilisateur.findByHeurederniereconnexion", query = "SELECT u FROM Utilisateur u WHERE u.heurederniereconnexion = :heurederniereconnexion"),
    @NamedQuery(name = "Utilisateur.findByActif", query = "SELECT u FROM Utilisateur u WHERE u.actif = :actif")})
public class Utilisateur implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idutilisateur;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String prenom;
    @Size(max = 254)
    private String login;
    @Size(max = 254)
    private String password;
    @Size(max = 254)
    private String photo;
    @Size(max = 254)
    private String template;
    @Size(max = 254)
    private String theme;
    @Temporal(TemporalType.DATE)
    private Date datecreation;
    @Temporal(TemporalType.DATE)
    private Date datederniereconnexion;
    @Temporal(TemporalType.DATE)
    private Date heurederniereconnexion;
    private Boolean actif;
    @OneToMany(mappedBy = "idutilisateur", fetch = FetchType.LAZY)
    private List<Privilege> privilegeList;
    @JoinColumn(name = "idpersonnel", referencedColumnName = "idpersonnel")
    @ManyToOne(fetch = FetchType.LAZY)
    private Personnel idpersonnel;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idutilisateur", fetch = FetchType.LAZY)
    private List<Mouchard> mouchardList;
    @OneToMany(mappedBy = "idutilisateur", fetch = FetchType.LAZY)
    private List<Utilisateurmagasin> utilisateurmagasinList;

    public Utilisateur() {
    }

    public Utilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public Integer getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Integer idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Date getDatecreation() {
        return datecreation;
    }

    public void setDatecreation(Date datecreation) {
        this.datecreation = datecreation;
    }

    public Date getDatederniereconnexion() {
        return datederniereconnexion;
    }

    public void setDatederniereconnexion(Date datederniereconnexion) {
        this.datederniereconnexion = datederniereconnexion;
    }

    public Date getHeurederniereconnexion() {
        return heurederniereconnexion;
    }

    public void setHeurederniereconnexion(Date heurederniereconnexion) {
        this.heurederniereconnexion = heurederniereconnexion;
    }

    public Boolean getActif() {
        return actif;
    }

    public void setActif(Boolean actif) {
        this.actif = actif;
    }

    @XmlTransient
    public List<Privilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public Personnel getIdpersonnel() {
        return idpersonnel;
    }

    public void setIdpersonnel(Personnel idpersonnel) {
        this.idpersonnel = idpersonnel;
    }

    @XmlTransient
    public List<Mouchard> getMouchardList() {
        return mouchardList;
    }

    public void setMouchardList(List<Mouchard> mouchardList) {
        this.mouchardList = mouchardList;
    }

    @XmlTransient
    public List<Utilisateurmagasin> getUtilisateurmagasinList() {
        return utilisateurmagasinList;
    }

    public void setUtilisateurmagasinList(List<Utilisateurmagasin> utilisateurmagasinList) {
        this.utilisateurmagasinList = utilisateurmagasinList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idutilisateur != null ? idutilisateur.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Utilisateur)) {
            return false;
        }
        Utilisateur other = (Utilisateur) object;
        if ((this.idutilisateur == null && other.idutilisateur != null) || (this.idutilisateur != null && !this.idutilisateur.equals(other.idutilisateur))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Utilisateur[ idutilisateur=" + idutilisateur + " ]";
    }
    
}
