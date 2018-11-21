package Repositories;

import ObjectsProject.PromoTarif;

import java.util.HashMap;

public class PromoEndTodayRepo {
    static HashMap<Double, PromoTarif> reposSet;
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

    public PromoTarif getPromoByEan(Double ean) {
        return reposSet.get(ean);
    }


    public HashMap<Double, PromoTarif> getAcessToRepository() {
        return reposSet;
    }
}
