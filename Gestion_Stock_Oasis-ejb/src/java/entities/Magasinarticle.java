/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author USER
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Magasinarticle.findAll", query = "SELECT m FROM Magasinarticle m")
    ,
    @NamedQuery(name = "Magasinarticle.findByIdmagasinarticle", query = "SELECT m FROM Magasinarticle m WHERE m.idmagasinarticle = :idmagasinarticle")
    ,
    @NamedQuery(name = "Magasinarticle.findByQuantite", query = "SELECT m FROM Magasinarticle m WHERE m.quantite = :quantite")
    ,
    @NamedQuery(name = "Magasinarticle.findByQuantitemultiple", query = "SELECT m FROM Magasinarticle m WHERE m.quantitemultiple = :quantitemultiple")
    ,
    @NamedQuery(name = "Magasinarticle.findByUnite", query = "SELECT m FROM Magasinarticle m WHERE m.unite = :unite")
    ,
    @NamedQuery(name = "Magasinarticle.findByEtat", query = "SELECT m FROM Magasinarticle m WHERE m.etat = :etat")
    ,
    @NamedQuery(name = "Magasinarticle.findByQuantitereduite", query = "SELECT m FROM Magasinarticle m WHERE m.quantitereduite = :quantitereduite")
    ,
    @NamedQuery(name = "Magasinarticle.findByQuantitevirtuelle", query = "SELECT m FROM Magasinarticle m WHERE m.quantitevirtuelle = :quantitevirtuelle")
    ,
    @NamedQuery(name = "Magasinarticle.findByQuantitesecurite", query = "SELECT m FROM Magasinarticle m WHERE m.quantitesecurite = :quantitesecurite")})
public class Magasinarticle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Long idmagasinarticle;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    private double quantite;
    private double quantitemultiple;
    private double unite;
    private boolean etat;
    private double quantitereduite;
    private double quantitevirtuelle;
    private double quantitesecurite;
    @Column(name = "prix_vente_detail")
    private double prixVenteDetail;
    @Column(name = "prix_vente_gros")
    private double prixVenteGros;
    @JoinColumn(name = "idarticle", referencedColumnName = "idarticle")
    @ManyToOne(fetch = FetchType.LAZY)
    private Article idarticle;
    @JoinColumn(name = "idmagasin", referencedColumnName = "idmagasin")
    @ManyToOne(fetch = FetchType.LAZY)
    private Magasin idmagasin;
    @OneToMany(mappedBy = "idmagasinarticle", fetch = FetchType.LAZY)
    private List<Lignedemande> lignedemandeList;
    @OneToMany(mappedBy = "idmagasinarticle", fetch = FetchType.LAZY)
    private List<Magasinlot> magasinlotList;

    private void initConstructor() {
        this.quantitemultiple = 0;
        this.quantitereduite = 0;
        this.quantitesecurite = 0;
        this.quantite = 0;
        this.unite = 0;
        this.magasinlotList = new ArrayList<>();
        this.lignedemandeList = new ArrayList<>();
        this.etat = true;
    }

    public Magasinarticle() {
        super();
        idarticle = new Article();
        idmagasin = new Magasin();
        this.initConstructor();
    }

    public Magasinarticle(Long idmagasinarticle) {
        this.idmagasinarticle = idmagasinarticle;
        this.initConstructor();
    }

    public Long getIdmagasinarticle() {
        return idmagasinarticle;
    }

    public void setIdmagasinarticle(Long idmagasinarticle) {
        this.idmagasinarticle = idmagasinarticle;
    }

    public double getQuantite() {
        return quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getQuantitemultiple() {
        return quantitemultiple;
    }

    public void setQuantitemultiple(double quantitemultiple) {
        this.quantitemultiple = quantitemultiple;
    }

    public double getUnite() {
        return unite;
    }

    public void setUnite(double unite) {
        this.unite = unite;
    }

    public boolean getEtat() {
        return etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }

    public Double getQuantitereduite() {
        return quantitereduite;
    }

    public void setQuantitereduite(double quantitereduite) {
        this.quantitereduite = quantitereduite;
    }

    public double getQuantitevirtuelle() {
        return quantitevirtuelle;
    }

    public void setQuantitevirtuelle(double quantitevirtuelle) {
        this.quantitevirtuelle = quantitevirtuelle;
    }

    public double getQuantitesecurite() {
        return quantitesecurite;
    }

    public void setQuantitesecurite(double quantitesecurite) {
        this.quantitesecurite = quantitesecurite;
    }

    public Article getIdarticle() {
        return idarticle;
    }

    public void setIdarticle(Article idarticle) {
        this.idarticle = idarticle;
    }

    public Magasin getIdmagasin() {
        return idmagasin;
    }

    public void setIdmagasin(Magasin idmagasin) {
        this.idmagasin = idmagasin;
    }

    public double getPrixVenteDetail() {
        return prixVenteDetail;
    }

    public void setPrixVenteDetail(double prixVenteDetail) {
        this.prixVenteDetail = prixVenteDetail;
    }

    public double getPrixVenteGros() {
        return prixVenteGros;
    }

    public void setPrixVenteGros(double prixVenteGros) {
        this.prixVenteGros = prixVenteGros;
    }

    @XmlTransient
    public List<Lignedemande> getLignedemandeList() {
        return lignedemandeList;
    }

    public void setLignedemandeList(List<Lignedemande> lignedemandeList) {
        this.lignedemandeList = lignedemandeList;
    }

    @XmlTransient
    public List<Magasinlot> getMagasinlotList() {
        return magasinlotList;
    }

    public void setMagasinlotList(List<Magasinlot> magasinlotList) {
        this.magasinlotList = magasinlotList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmagasinarticle != null ? idmagasinarticle.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Magasinarticle)) {
            return false;
        }
        Magasinarticle other = (Magasinarticle) object;
        if ((this.idmagasinarticle == null && other.idmagasinarticle != null) || (this.idmagasinarticle != null && !this.idmagasinarticle.equals(other.idmagasinarticle))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Magasinarticle[ idmagasinarticle=" + idmagasinarticle + " ]";
    }

}
