package ObjectsProject;

import java.util.Date;

public class PromoTarif {

    private Double ean;
    private Double price;
    private Date startTarif;
    private Date endTarif;
    private String name;
    private Double Segment;

    public PromoTarif(Double ean, Double price, Date startTarif, Date endTarif) {
        this.ean = ean;
        this.price = price;
        this.startTarif = startTarif;
        this.endTarif = endTarif;
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

    public Date getStartTarif() {
        return startTarif;
    }

    public Date getEndTarif() {
        return endTarif;
    }

}
