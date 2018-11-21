package Repositories;

import ObjectsProject.NewTarif;

import java.util.HashMap;
import java.util.Map;

public class NewTarifRepo {
    static Map<Double, NewTarif> reposSet;
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

    public NewTarif getArticleByEan(Double ean) {
        return reposSet.get(ean);
    }

    public Map<Double, NewTarif> getAcessToRepository() {
        return reposSet;
    }
}
