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
    @NamedQuery(name = "Lignemvtstock.findAll", query = "SELECT l FROM Lignemvtstock l")
    ,
    @NamedQuery(name = "Lignemvtstock.findByIdlignemvtstock", query = "SELECT l FROM Lignemvtstock l WHERE l.idlignemvtstock = :idlignemvtstock")
    ,
    @NamedQuery(name = "Lignemvtstock.findByQteentree", query = "SELECT l FROM Lignemvtstock l WHERE l.qteentree = :qteentree")
    ,
    @NamedQuery(name = "Lignemvtstock.findByQtesortie", query = "SELECT l FROM Lignemvtstock l WHERE l.qtesortie = :qtesortie")
    ,
    @NamedQuery(name = "Lignemvtstock.findByUnite", query = "SELECT l FROM Lignemvtstock l WHERE l.unite = :unite")
    ,
    @NamedQuery(name = "Lignemvtstock.findByReste", query = "SELECT l FROM Lignemvtstock l WHERE l.reste = :reste")
    ,
    @NamedQuery(name = "Lignemvtstock.findByClient", query = "SELECT l FROM Lignemvtstock l WHERE l.client = :client")
    ,
    @NamedQuery(name = "Lignemvtstock.findByFournisseur", query = "SELECT l FROM Lignemvtstock l WHERE l.fournisseur = :fournisseur")
    ,
    @NamedQuery(name = "Lignemvtstock.findByType", query = "SELECT l FROM Lignemvtstock l WHERE l.type = :type")
    ,
    @NamedQuery(name = "Lignemvtstock.findByMagasin", query = "SELECT l FROM Lignemvtstock l WHERE l.magasin = :magasin")})
public class Lignemvtstock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idlignemvtstock;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private double qteentree;
    private double qtesortie;
    private double unite;
    private double reste;
    @Column(name = "qteavant")
    private double qteAvant;
    @Size(max = 70)
    private String client;
    @Size(max = 70)
    private String fournisseur;
    @Size(max = 20)
    private String type;
    @Size(max = 50)
    private String magasin;
    @JoinColumn(name = "idlot", referencedColumnName = "idlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lot idlot;
    @JoinColumn(name = "idmagasinlot", referencedColumnName = "idmagasinlot")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasinlot idmagasinlot;
    @JoinColumn(name = "idmvtstock", referencedColumnName = "idmvtstock")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mvtstock idmvtstock;

    @JoinColumn(name = "idlignelivraisonfournisseur", referencedColumnName = "idlignelivraisonfournisseur")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lignelivraisonfournisseur lignelivraisonfournisseur;

    @JoinColumn(name = "idlignelivraisonclient", referencedColumnName = "idlignelivraisonclient")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lignelivraisonclient lignelivraisonclient;

    @JoinColumn(name = "idlignetransfert", referencedColumnName = "idlignetransfert")
    @ManyToOne(fetch = FetchType.LAZY)
    private Lignetransfert lignetransfert;

    @JoinColumn(name = "idligneinventaire", referencedColumnName = "idligneinventaire")
    @ManyToOne(fetch = FetchType.LAZY)
    private Ligneinventaire ligneinventaire;

    public Lignemvtstock() {
    }

    public Lignemvtstock(Long idlignemvtstock) {
        this.idlignemvtstock = idlignemvtstock;
    }

    public Long getIdlignemvtstock() {
        return idlignemvtstock;
    }

    public void setIdlignemvtstock(Long idlignemvtstock) {
        this.idlignemvtstock = idlignemvtstock;
    }

    public double getQteentree() {
        return qteentree;
    }

    public void setQteentree(double qteentree) {
        this.qteentree = qteentree;
    }

    public double getQtesortie() {
        return qtesortie;
    }

    public void setQtesortie(double qtesortie) {
        this.qtesortie = qtesortie;
    }

    public double getUnite() {
        return unite;
    }

    public void setUnite(double unite) {
        this.unite = unite;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
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

    public Mvtstock getIdmvtstock() {
        return idmvtstock;
    }

    public void setIdmvtstock(Mvtstock idmvtstock) {
        this.idmvtstock = idmvtstock;
    }

    public double getQteAvant() {
        return qteAvant;
    }

    public void setQteAvant(double qteAvant) {
        this.qteAvant = qteAvant;
    }

    public Lignelivraisonfournisseur getLignelivraisonfournisseur() {
        return lignelivraisonfournisseur;
    }

    public void setLignelivraisonfournisseur(Lignelivraisonfournisseur lignelivraisonfournisseur) {
        this.lignelivraisonfournisseur = lignelivraisonfournisseur;
    }

    public Lignelivraisonclient getLignelivraisonclient() {
        return lignelivraisonclient;
    }

    public void setLignelivraisonclient(Lignelivraisonclient lignelivraisonclient) {
        this.lignelivraisonclient = lignelivraisonclient;
    }

    public Lignetransfert getLignetransfert() {
        return lignetransfert;
    }

    public void setLignetransfert(Lignetransfert lignetransfert) {
        this.lignetransfert = lignetransfert;
    }

    public Ligneinventaire getLigneinventaire() {
        return ligneinventaire;
    }

    public void setLigneinventaire(Ligneinventaire ligneinventaire) {
        this.ligneinventaire = ligneinventaire;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idlignemvtstock != null ? idlignemvtstock.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lignemvtstock)) {
            return false;
        }
        Lignemvtstock other = (Lignemvtstock) object;
        if ((this.idlignemvtstock == null && other.idlignemvtstock != null) || (this.idlignemvtstock != null && !this.idlignemvtstock.equals(other.idlignemvtstock))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Lignemvtstock[ idlignemvtstock=" + idlignemvtstock + " ]";
    }

}
