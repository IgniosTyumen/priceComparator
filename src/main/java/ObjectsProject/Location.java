package ObjectsProject;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class Location {


    private Long id;

    private Integer article;

    private Integer segment;
    private Integer category;
    private Integer family;

    private Integer alley;
    private Integer gondol;
    private Integer element;

    private Double remaings;

    public Location(Integer article, Integer segment, Integer category, Integer family, Integer alley, Integer gondol, Integer element, Double remaings) {
        this.article = article;
        this.segment = segment;
        this.category = category;
        this.family = family;
        this.alley = alley;
        this.gondol = gondol;
        this.element = element;
        this.remaings = remaings;
    }

    public Location(Integer article, Integer segment, Integer category, Integer family) {
        this.article = article;
        this.segment = segment;
        this.category = category;
        this.family = family;
    }

    public Location(Article article){
        this.article = article.getEan();
        this.segment = article.getSegment();
        this.category = article.getCategory();
        this.family = article.getFamily();
        this.alley = 0;
        this.gondol = 0;
        this.element = 0;
        this.remaings = article.getRemaings();
    }
}
