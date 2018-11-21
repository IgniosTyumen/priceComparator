package Repositories;

import ObjectsProject.PromoTarif;

import java.util.HashMap;
import java.util.Map;

public class PromoEndRepo {
    static HashMap<Double, PromoTarif> reposSet;
    private static PromoEndRepo ourInstance = new PromoEndRepo();

    private PromoEndRepo() {
        reposSet = new HashMap<>();
    }

    public static PromoEndRepo getInstance() {
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
