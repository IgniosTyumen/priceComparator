package Repositories;

import ObjectsProject.Article;

import java.util.HashMap;
import java.util.Map;

public class ArticleReposOld {
    static Map<Integer, Article> reposSet;
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

    public Article getArticleByEan(Integer ean) {
        return reposSet.get(ean);
    }

    public Map<Integer, Article> getRepoAcess() {
        return reposSet;
    }
}
