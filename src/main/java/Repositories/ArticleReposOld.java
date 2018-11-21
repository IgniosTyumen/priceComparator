package Repositories;

import ObjectsProject.Article;

import java.util.HashMap;
import java.util.Map;

public class ArticleReposOld {
    static Map<Double, Article> reposSet;
    private static ArticleReposOld ourInstance = new ArticleReposOld();

    private ArticleReposOld() {
        reposSet = new HashMap<>();
    }

    public static ArticleReposOld getInstance() {
        return ourInstance;
    }

    public void putArticle(Article article) {
        reposSet.put(article.getEan(), article);
    }

    public Article getArticleByEan(Double ean) {
        return reposSet.get(ean);
    }

    public Map<Double, Article> getRepoAcess() {
        return reposSet;
    }
}
