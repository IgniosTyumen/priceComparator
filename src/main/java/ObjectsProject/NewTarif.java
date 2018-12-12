package ObjectsProject;

import lombok.Data;

@Data
public class NewTarif {
    private Integer ean;
    private Double price;
    private String name;
    private Integer segment;
    private Integer family;
    private Integer category;

    public NewTarif(Integer ean, Double price) {
        this.ean = ean;
        this.price = price;
    }

    public NewTarif(Integer ean, Double price, Integer segment, Integer family, Integer category) {
        this.ean = ean;
        this.price = price;
        this.segment = segment;
        this.family = family;
        this.category = category;
    }

    @Override
    public String toString() {
        return "ObjectsProject.NewTarif{" +
                "ean=" + ean +
                ", price=" + price +
                '}';
    }

}
