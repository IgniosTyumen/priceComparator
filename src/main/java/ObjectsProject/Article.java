package ObjectsProject;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@Builder(toBuilder = true)
public class Article implements Comparable<Article>,Serializable {
    private Integer ean;
    private Double price;
    private Double remaings;
    private String manager;
    private String chief;
    private Integer segment;
    private String articleName;
    private String status;
    private Double newPrice;
    private Integer category;
    private Integer family;
    private Integer alley;
    private Integer gondol;
    private Integer element;


    @Override
    public int compareTo(Article o) {
        return Double.compare(this.getSegment(), o.getSegment());
    }
}
