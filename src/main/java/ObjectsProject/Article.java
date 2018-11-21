package ObjectsProject;

import java.io.Serializable;

public class Article implements Comparable<Article>,Serializable {
    private Double ean;
    private Double price;
    private Double remaings;
    private String manager;
    private String chief;
    private Double segment;
    private String articleName;
    private String status;
private Double newPrice;


    public Double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(Double newPrice) {
        this.newPrice = newPrice;
    }

    public Article(Double ean, Double price, Double remaings, String manager, String chief, Double segment, String articleName, String status) {
        this.ean = ean;
        this.price = price;
        this.remaings = remaings;
        this.manager = manager;
        this.chief = chief;
        this.segment = segment;
        this.articleName = articleName;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ObjectsProject.Article{" +
                "ean=" + ean +
                ", price=" + price +
                ", remaings=" + remaings +
                ", manager='" + manager + '\'' +
                ", chief='" + chief + '\'' +
                ", segment=" + segment +
                ", articleName='" + articleName + '\'' +
                '}';
    }

    public Double getEan() {
        return ean;
    }

    public void setEan(Double ean) {
        this.ean = ean;
    }

    public Double getPrice() {
        return price;
    }

    public String getArticleName() {
        return articleName;
    }

    public Double getRemaings() {
        return remaings;
    }

    public String getManager() {
        return manager;
    }

    public Double getSegment() {
        return segment;
    }

    @Override
    public int compareTo(Article o) {
        return Double.compare(this.getSegment(), o.getSegment());
    }
}
