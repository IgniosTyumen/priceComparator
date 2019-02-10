package Repositories;

import ObjectsProject.Article;
import ObjectsProject.Location;

import java.util.HashMap;
import java.util.Map;

public class ArticleReposNew {
    static Map<Integer, Article> reposSet;
    private static ArticleReposNew ourInstance = new ArticleReposNew();

    private ArticleReposNew() {
        reposSet = new HashMap<>();
    }

    public static ArticleReposNew getInstance() {
        return ourInstance;
    }

    public void putArticle(Article article) {
        reposSet.put(article.getEan(), article);
    }

    public Article getArticleByEan(Integer ean) {
        return reposSet.get(ean);
    }

    public Map<Integer, Article> getRepoAcess() {
        return reposSet;
    }
    //segment family category
    public static Location recallSFC(Integer ean){
        Location result = new Location();
        if (reposSet.containsKey(ean)){
            result.setSegment(reposSet.get(ean).getSegment());
            result.setFamily(reposSet.get(ean).getFamily());
            result.setCategory(reposSet.get(ean).getCategory());
        }
        return result;
    }
}
