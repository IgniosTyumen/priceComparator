package Repositories;

import ObjectsProject.PromoTarif;

import java.util.HashMap;
import java.util.Map;

public class PromoRepo {
    static HashMap<Integer, PromoTarif> reposSet;
    private static PromoRepo ourInstance = new PromoRepo();

    private PromoRepo() {
        reposSet = new HashMap<>();
    }

    public static PromoRepo getInstance() {
        return ourInstance;
    }

    public void putPromo(PromoTarif promo) {
        reposSet.put(promo.getEan(), promo);
    }

    public PromoTarif getPromoByEan(Integer ean) {
        return reposSet.get(ean);
    }


    public Map<Integer, PromoTarif> getAcessToRepository() {
        return reposSet;
    }
}
