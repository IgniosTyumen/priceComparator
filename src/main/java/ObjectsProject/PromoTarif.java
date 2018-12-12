package ObjectsProject;

import lombok.Data;

import java.util.Date;

@Data
public class PromoTarif {

    private Integer ean;
    private Double price;
    private Date startTarif;
    private Date endTarif;
    private String name;

    private Integer segment;
    private Integer family;
    private Integer category;

    public PromoTarif(Integer ean, Double price, Date startTarif, Date endTarif) {
        this.ean = ean;
        this.price = price;
        this.startTarif = startTarif;
        this.endTarif = endTarif;
    }

    public PromoTarif(Integer ean, Double price, Date startTarif, Date endTarif, Integer segment, Integer family, Integer category) {
        this.ean = ean;
        this.price = price;
        this.startTarif = startTarif;
        this.endTarif = endTarif;
        this.segment = segment;
        this.family = family;
        this.category = category;
    }
}
