package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annee.findAll", query = "SELECT a FROM Annee a"),
    @NamedQuery(name = "Annee.findByIdannee", query = "SELECT a FROM Annee a WHERE a.idannee = :idannee"),
    @NamedQuery(name = "Annee.findByNom", query = "SELECT a FROM Annee a WHERE a.nom = :nom"),
    @NamedQuery(name = "Annee.findByEtat", query = "SELECT a FROM Annee a WHERE a.etat = :etat"),
    @NamedQuery(name = "Annee.findByDateDebut", query = "SELECT a FROM Annee a WHERE a.dateDebut = :dateDebut"),
    @NamedQuery(name = "Annee.findByDateFin", query = "SELECT a FROM Annee a WHERE a.dateFin = :dateFin")})
public class Annee
        implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private Integer idannee;
    @Size(max = 2147483647)
    private String nom;
    private Boolean etat;
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    @OneToMany(mappedBy = "idannee", fetch = FetchType.LAZY)
    private List<AnneeMois> anneeMoisList;

    public Annee() {
    }

    public Annee(Integer idannee) {
        this.idannee = idannee;
    }

    public Integer getIdannee() {
        return this.idannee;
    }

    public void setIdannee(Integer idannee) {
        this.idannee = idannee;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean getEtat() {
        return this.etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Date getDateDebut() {
        return this.dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return this.dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @XmlTransient
    public List<AnneeMois> getAnneeMoisList() {
        return this.anneeMoisList;
    }

    public void setAnneeMoisList(List<AnneeMois> anneeMoisList) {
        this.anneeMoisList = anneeMoisList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idannee != null) ? this.idannee.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Annee)) {
            return false;
        }
        Annee other = (Annee) object;
        if ((this.idannee == null && other.idannee != null) || (this.idannee != null && !this.idannee.equals(other.idannee))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Annee[ idannee=" + this.idannee + " ]";
    }
}
