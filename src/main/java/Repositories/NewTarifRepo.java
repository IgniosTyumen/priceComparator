package Repositories;

import ObjectsProject.NewTarif;

import java.util.HashMap;
import java.util.Map;

public class NewTarifRepo {
    static Map<Integer, NewTarif> reposSet;
    private static NewTarifRepo ourInstance = new NewTarifRepo();

    private NewTarifRepo() {
        reposSet = new HashMap<>();
    }

    public static NewTarifRepo getInstance() {
        return ourInstance;
    }

    public void putTarif(NewTarif tarif) {
        reposSet.put(tarif.getEan(), tarif);
    }

    public NewTarif getArticleByEan(Integer ean) {
        return reposSet.get(ean);
    }

    public Map<Integer, NewTarif> getAcessToRepository() {
        return reposSet;
    }
}
