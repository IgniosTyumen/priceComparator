package ObjectsProject;

public class NewTarif {
    private Double ean;
    private Double price;
    private String name;
    private Double Segment;

    public NewTarif(Double ean, Double price) {
        this.ean = ean;
        this.price = price;
    }

    @Override
    public String toString() {
        return "ObjectsProject.NewTarif{" +
                "ean=" + ean +
                ", price=" + price +
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

}
