package utils;

import entities.Personnel;

public class Solde {

    private Personnel personnel;
    private Integer montantVerse;
    private Integer montantRetire;
    private Integer carnet;
    private Integer commission;

    public Solde() {
    }

    public Solde(Personnel personnel, Integer montantVerse, Integer montantRetire) {
        this.personnel = personnel;
        this.montantVerse = montantVerse;
        this.montantRetire = montantRetire;
    }

    public Personnel getPersonnel() {
        return this.personnel;
    }

    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
    }

    public Integer getMontantVerse() {
        return this.montantVerse;
    }

    public void setMontantVerse(Integer montantVerse) {
        this.montantVerse = montantVerse;
    }

    public Integer getMontantRetire() {
        return this.montantRetire;
    }

    public void setMontantRetire(Integer montantRetire) {
        this.montantRetire = montantRetire;
    }

    public Integer getCarnet() {
        return this.carnet;
    }

    public void setCarnet(Integer carnet) {
        this.carnet = carnet;
    }

    public Integer getCommission() {
        return this.commission;
    }

    public void setCommission(Integer commission) {
        this.commission = commission;
    }
}
