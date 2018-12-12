package Repositories;

import ObjectsProject.PromoTarif;

import java.util.HashMap;
import java.util.Map;

public class PromoEndRepo {
    static HashMap<Integer, PromoTarif> reposSet;
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

    public PromoTarif getPromoByEan(Integer ean) {
        return reposSet.get(ean);
    }


    public Map<Integer, PromoTarif> getAcessToRepository() {
        return reposSet;
    }
}
