package Repositories;

import ObjectsProject.PromoTarif;

import java.util.HashMap;
import java.util.Map;

public class PromoRepo {
    static HashMap<Double, PromoTarif> reposSet;
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

    public PromoTarif getPromoByEan(Double ean) {
        return reposSet.get(ean);
    }


    public Map<Double, PromoTarif> getAcessToRepository() {
        return reposSet;
    }
}
