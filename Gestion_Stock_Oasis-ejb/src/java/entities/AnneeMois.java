package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "annee_mois")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnneeMois.findAll", query = "SELECT a FROM AnneeMois a"),
    @NamedQuery(name = "AnneeMois.findByIdAnneeMois", query = "SELECT a FROM AnneeMois a WHERE a.idAnneeMois = :idAnneeMois"),
    @NamedQuery(name = "AnneeMois.findByDateDebut", query = "SELECT a FROM AnneeMois a WHERE a.dateDebut = :dateDebut"),
    @NamedQuery(name = "AnneeMois.findByDateFin", query = "SELECT a FROM AnneeMois a WHERE a.dateFin = :dateFin"),
    @NamedQuery(name = "AnneeMois.findByEtat", query = "SELECT a FROM AnneeMois a WHERE a.etat = :etat")})
public class AnneeMois implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id_annee_mois")
    private Integer idAnneeMois;
    @Column(name = "date_debut")
    @Temporal(TemporalType.DATE)
    private Date dateDebut;
    @Column(name = "date_fin")
    @Temporal(TemporalType.DATE)
    private Date dateFin;
    private Boolean etat;
    @JoinColumn(name = "idannee", referencedColumnName = "idannee")
    @ManyToOne(fetch = FetchType.LAZY)
    private Annee idannee;
    @JoinColumn(name = "idmois", referencedColumnName = "idmois")
    @ManyToOne(fetch = FetchType.LAZY)
    private Mois idmois;

    public AnneeMois() {
    }

    public AnneeMois(Integer idAnneeMois) {
        this.idAnneeMois = idAnneeMois;
    }

    public Integer getIdAnneeMois() {
        return this.idAnneeMois;
    }

    public void setIdAnneeMois(Integer idAnneeMois) {
        this.idAnneeMois = idAnneeMois;
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

    public Boolean getEtat() {
        return this.etat;
    }

    public void setEtat(Boolean etat) {
        this.etat = etat;
    }

    public Annee getIdannee() {
        return this.idannee;
    }

    public void setIdannee(Annee idannee) {
        this.idannee = idannee;
    }

    public Mois getIdmois() {
        return this.idmois;
    }

    public void setIdmois(Mois idmois) {
        this.idmois = idmois;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.idAnneeMois != null) ? this.idAnneeMois.hashCode() : 0;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof AnneeMois)) {
            return false;
        }
        AnneeMois other = (AnneeMois) object;
        if ((this.idAnneeMois == null && other.idAnneeMois != null) || (this.idAnneeMois != null && !this.idAnneeMois.equals(other.idAnneeMois))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.AnneeMois[ idAnneeMois=" + this.idAnneeMois + " ]";
    }
}
