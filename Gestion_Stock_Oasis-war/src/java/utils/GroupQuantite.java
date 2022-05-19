package utils;

public class GroupQuantite {

    double quantite;
    double quantiteMultiple;
    double quantiteReduite;
    double unite;

    public GroupQuantite() {
        this.quantite = 0;
        this.quantiteMultiple = 0;
        this.quantiteReduite = 0;
        this.unite = 0;
    }

    public GroupQuantite(double quantite, double quantiteMultiple, double quantiteReduite, double unite) {
        this.quantite = quantite;
        this.quantiteMultiple = quantiteMultiple;
        this.quantiteReduite = quantiteReduite;
        this.unite = unite;
    }

    public double getQuantite() {
        return this.quantite;
    }

    public void setQuantite(double quantite) {
        this.quantite = quantite;
    }

    public double getQuantiteMultiple() {
        return this.quantiteMultiple;
    }

    public void setQuantiteMultiple(double quantiteMultiple) {
        this.quantiteMultiple = quantiteMultiple;
    }

    public double getQuantiteReduite() {
        return this.quantiteReduite;
    }

    public void setQuantiteReduite(double quantiteReduite) {
        this.quantiteReduite = quantiteReduite;
    }

    public double getUnite() {
        return this.unite;
    }

    public void setUnite(double unite) {
        this.unite = unite;
    }
}
