package Repositories;

import ObjectsProject.PromoTarif;

import java.util.HashMap;

public class PromoEndTodayRepo {
    static HashMap<Integer, PromoTarif> reposSet;
    private static PromoEndTodayRepo ourInstance = new PromoEndTodayRepo();

    private PromoEndTodayRepo() {
        reposSet = new HashMap<>();
    }

    public static PromoEndTodayRepo getInstance() {
        return ourInstance;
    }

    public void putPromo(PromoTarif promo) {
        reposSet.put(promo.getEan(), promo);
    }

    public PromoTarif getPromoByEan(Integer ean) {
        return reposSet.get(ean);
    }


    public HashMap<Integer, PromoTarif> getAcessToRepository() {
        return reposSet;
    }
}
