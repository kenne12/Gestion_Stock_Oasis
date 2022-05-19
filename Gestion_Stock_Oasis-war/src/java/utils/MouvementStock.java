package utils;

import entities.Article;
import entities.Famille;
import entities.Lot;

public class MouvementStock {

    private Famille famille;
    private Article article;
    private Lot lot;
    private int quantiteIn;
    private int quantiteOut;

    public Famille getFamille() {
        return this.famille;
    }

    public void setFamille(Famille famille) {
        this.famille = famille;
    }

    public Article getArticle() {
        return this.article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Lot getLot() {
        return this.lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public int getQuantiteIn() {
        return this.quantiteIn;
    }

    public void setQuantiteIn(int quantiteIn) {
        this.quantiteIn = quantiteIn;
    }

    public int getQuantiteOut() {
        return this.quantiteOut;
    }

    public void setQuantiteOut(int quantiteOut) {
        this.quantiteOut = quantiteOut;
    }
}
