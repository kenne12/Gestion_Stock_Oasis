/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import entities.Magasinlot;

/**
 *
 * @author USER
 */
public class AnalyseRD {

    public AnalyseRD() {
        this.init();
    }

    public AnalyseRD(Magasinlot magasinlot) {
        this.magasinlot = magasinlot;
        this.init();
    }

    private void init() {
        this.valeurJanv = 0;
        this.valeurFev = 0;
        this.valeurMars = 0;
        this.valeurAvr = 0;
        this.valeurMai = 0;
        this.valeurJuin = 0;
        this.valeurJuil = 0;
        this.valeurAout = 0;
        this.valeurSept = 0;
        this.valeurOct = 0;
        this.valeurNov = 0;
        this.valeurDec = 0;
        this.montantTotal = 0;
        this.pourcentage = 0;
    }

    private Magasinlot magasinlot;

    private double valeurJanv;
    private double valeurFev;
    private double valeurMars;
    private double valeurAvr;
    private double valeurMai;
    private double valeurJuin;
    private double valeurJuil;
    private double valeurAout;
    private double valeurSept;
    private double valeurOct;
    private double valeurNov;
    private double valeurDec;
    private double montantTotal;
    private double pourcentage;

    public Magasinlot getMagasinlot() {
        return magasinlot;
    }

    public void setMagasinlot(Magasinlot magasinlot) {
        this.magasinlot = magasinlot;
    }

    public double getValeurJanv() {
        return valeurJanv;
    }

    public void setValeurJanv(double valeurJanv) {
        this.valeurJanv = valeurJanv;
    }

    public double getValeurFev() {
        return valeurFev;
    }

    public void setValeurFev(double valeurFev) {
        this.valeurFev = valeurFev;
    }

    public double getValeurMars() {
        return valeurMars;
    }

    public void setValeurMars(double valeurMars) {
        this.valeurMars = valeurMars;
    }

    public double getValeurAvr() {
        return valeurAvr;
    }

    public void setValeurAvr(double valeurAvr) {
        this.valeurAvr = valeurAvr;
    }

    public double getValeurMai() {
        return valeurMai;
    }

    public void setValeurMai(double valeurMai) {
        this.valeurMai = valeurMai;
    }

    public double getValeurJuin() {
        return valeurJuin;
    }

    public void setValeurJuin(double valeurJuin) {
        this.valeurJuin = valeurJuin;
    }

    public double getValeurJuil() {
        return valeurJuil;
    }

    public void setValeurJuil(double valeurJuil) {
        this.valeurJuil = valeurJuil;
    }

    public double getValeurAout() {
        return valeurAout;
    }

    public void setValeurAout(double valeurAout) {
        this.valeurAout = valeurAout;
    }

    public double getValeurSept() {
        return valeurSept;
    }

    public void setValeurSept(double valeurSept) {
        this.valeurSept = valeurSept;
    }

    public double getValeurOct() {
        return valeurOct;
    }

    public void setValeurOct(double valeurOct) {
        this.valeurOct = valeurOct;
    }

    public double getValeurNov() {
        return valeurNov;
    }

    public void setValeurNov(double valeurNov) {
        this.valeurNov = valeurNov;
    }

    public double getValeurDec() {
        return valeurDec;
    }

    public void setValeurDec(double valeurDec) {
        this.valeurDec = valeurDec;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

}
