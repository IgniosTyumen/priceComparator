package Logic;

import ObjectsProject.Article;
import ObjectsProject.NewTarif;
import ObjectsProject.PromoTarif;
import Repositories.PromoRepo;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

public class TarifAnolizer {


    public static TreeMap<Integer, Article> execute(Map<Integer, Article> old, Map<Integer, NewTarif> tarifs) {
        TreeMap<Integer, Article> result = new TreeMap<>();

        for (Integer ean : tarifs.keySet()) {
            if ((old.get(ean) != null) &&
                    (!old.get(ean).getPrice().equals(tarifs.get(ean).getPrice())) &&
                    (old.get(ean).getRemaings() != 0)
                    ) {
                old.get(ean).setNewPrice(tarifs.get(ean).getPrice());
                result.put(ean, old.get(ean));
            } else {

            }
        }
        for (Integer f: PromoRepo.getInstance().getAcessToRepository().keySet())
              {
                  result.remove(f);

        }
        return result;
    }

    public static TreeMap<Integer, Article> executePromo(Map<Integer, Article> old, Map<Integer, PromoTarif> tarifs) {
        TreeMap<Integer, Article> result = new TreeMap<>();
        Date now = new Date();
        int nDay = now.getDay();
        int nMonth = now.getMonth();
        int nYear = now.getYear();
        Date compare = new Date(now.getYear(), now.getMonth(), now.getDay());
        for (Integer ean : tarifs.keySet()) {
            if (old.get(ean) != null) {

                int tDay = tarifs.get(ean).getStartTarif().getDay();
                int tMonth = tarifs.get(ean).getStartTarif().getMonth();
                int tYear = tarifs.get(ean).getStartTarif().getYear();
                boolean sameDate = (nDay == tDay && nMonth == tMonth && nYear == tYear);
                if ((sameDate) &&
                        (!old.get(ean).getPrice().equals(tarifs.get(ean).getPrice())) &&
                        (old.get(ean).getRemaings() != 0)) {
                    old.get(ean).setNewPrice(tarifs.get(ean).getPrice());
                    result.put(ean, old.get(ean));
                }
            }
        }
        return result;
    }

    public static TreeMap<Integer, Article> executePromoGettingOld(Map<Integer, Article> old, Map<Integer, PromoTarif> tarifs) {
        TreeMap<Integer, Article> result = new TreeMap<>();
        Date now = new Date();
        int nDay = now.getDay();
        int nMonth = now.getMonth();
        int nYear = now.getYear();
        Date compare = new Date(now.getYear(), now.getMonth(), now.getDay());

        for (Integer ean : tarifs.keySet()) {
            if (old.get(ean) != null) {

                int tDay = tarifs.get(ean).getEndTarif().getDay();
                int tMonth = tarifs.get(ean).getEndTarif().getMonth();
                int tYear = tarifs.get(ean).getEndTarif().getYear();
                boolean sameDate = (nDay == tDay && nMonth == tMonth && nYear == tYear);
                if ((sameDate) &&

                        (old.get(ean).getRemaings() != 0)) {
                    old.get(ean).setNewPrice(tarifs.get(ean).getPrice());
                    result.put(ean, old.get(ean));
                }
            }
        }
        return result;
    }

    public static TreeMap<Integer, Article> executePromoEndedYesterday(Map<Integer, Article> old, Map<Integer, PromoTarif> tarifs) {
        TreeMap<Integer, Article> result = new TreeMap<>();
        for (Integer ean : tarifs.keySet()) {
            if (old.get(ean) != null) {
                if  (old.get(ean).getRemaings() != 0) {
                    old.get(ean).setNewPrice(tarifs.get(ean).getPrice());
                    result.put(ean, old.get(ean));
                }
            }
        }

        for (Integer f: PromoRepo.getInstance().getAcessToRepository().keySet())
        {if ((tarifs.get(f)!=null) && (PromoRepo.getInstance().getAcessToRepository().get(f)!=null)) {

            if (tarifs.get(f).getPrice().equals(PromoRepo.getInstance().getAcessToRepository().get(f).getPrice()))

                result.remove(f);
        }
        }
        return result;
    }

//    public static Map<Double,Article> compareMap(HashMap<Double, Article> input){
//        Map<Double,Article> result = new TreeMap<>();
//
//    }
}
