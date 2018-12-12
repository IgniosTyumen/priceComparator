package Repositories;

import ObjectsProject.Article;

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
}
