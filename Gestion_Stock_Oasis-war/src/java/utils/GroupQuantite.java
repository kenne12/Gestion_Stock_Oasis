package utils;

public class GroupQuantite {

    double quantite;
    double quantiteMultiple;
    double quantiteReduite;
    double unite;

    public GroupQuantite() {
        /* 20 */ this.quantite = 0;
        /* 21 */ this.quantiteMultiple = 0;
        /* 22 */ this.quantiteReduite = 0;
        /* 23 */ this.unite = 0;
    }

    public GroupQuantite(double quantite, double quantiteMultiple, double quantiteReduite, double unite) {
        /* 27 */ this.quantite = quantite;
        /* 28 */ this.quantiteMultiple = quantiteMultiple;
        /* 29 */ this.quantiteReduite = quantiteReduite;
        /* 30 */ this.unite = unite;
    }

    public double getQuantite() {
        /* 34 */ return this.quantite;
    }

    public void setQuantite(double quantite) {
        /* 38 */ this.quantite = quantite;
    }

    public double getQuantiteMultiple() {
        /* 42 */ return this.quantiteMultiple;
    }

    public void setQuantiteMultiple(double quantiteMultiple) {
        /* 46 */ this.quantiteMultiple = quantiteMultiple;
    }

    public double getQuantiteReduite() {
        /* 50 */ return this.quantiteReduite;
    }

    public void setQuantiteReduite(double quantiteReduite) {
        /* 54 */ this.quantiteReduite = quantiteReduite;
    }

    public double getUnite() {
        /* 58 */ return this.unite;
    }

    public void setUnite(double unite) {
        /* 62 */ this.unite = unite;
    }
}
