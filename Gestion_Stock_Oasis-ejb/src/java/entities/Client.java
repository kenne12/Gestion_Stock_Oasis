/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author USER
 */
@Entity
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idclient;
    @Size(max = 254)
    private String nom;
    @Size(max = 254)
    private String prenom;
    @Size(max = 254)
    private String contact;
    private Integer solde;
    @Size(max = 50)
    @Column(name = "numero_compte")
    private String numeroCompte;
    @Size(max = 50)
    @Column(name = "contact_2")
    private String contact2;
    @Size(max = 50)
    private String adresse;
    @Size(max = 50)
    private String email;
    @Size(max = 50)
    private String code;
    @Size(max = 50)
    private String fax;
    private boolean etat;
    @OneToMany(mappedBy = "client")
    private List<Livraisonclient> livraisonclients;
    @OneToMany(mappedBy = "client")
    private List<Demande> listDemandes;

    public Client() {
    }

    public Client(Integer idclient) {
        this.idclient = idclient;
    }

    public Integer getIdclient() {
        return idclient;
    }

    public void setIdclient(Integer idclient) {
        this.idclient = idclient;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSolde() {
        return solde;
    }

    public void setSolde(Integer solde) {
        this.solde = solde;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public List<Livraisonclient> getLivraisonclients() {
        return livraisonclients;
    }

    public void setLivraisonclients(List<Livraisonclient> livraisonclients) {
        this.livraisonclients = livraisonclients;
    }

    public boolean isEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public List<Demande> getListDemandes() {
        return listDemandes;
    }

    public void setListDemandes(List<Demande> listDemandes) {
        this.listDemandes = listDemandes;
    }

    @Override
    public int hashCode() {
        int hash = 7;
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
        final Client other = (Client) obj;
        if (!Objects.equals(this.idclient, other.idclient)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Client{" + "idclient=" + idclient + ", nom=" + nom + ", prenom=" + prenom + ", contact=" + contact + ", solde=" + solde + ", numeroCompte=" + numeroCompte + ", contact2=" + contact2 + ", adresse=" + adresse + ", email=" + email + ", code=" + code + ", fax=" + fax + '}';
    }

}
