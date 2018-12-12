import DB.JDBCInitializer;
import DB.JDBCLocation;
import Logic.TarifAnolizer;
import Readers.*;
import Reports.Reports;
import Repositories.*;

import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        try {

            ReadOldArticles howto = new ReadOldArticles();
            System.out.println("Начало чтение файла");
            howto.processOneSheet("XLSX/LPDP.xlsm");
            ReadTarifsFromXLSM tarifs = new ReadTarifsFromXLSM();
            tarifs.processOneSheet("XLSX/тарифы.xlsm");
            ReadPromoFromXLMS promoTarifs = new ReadPromoFromXLMS();
            promoTarifs.processOneSheet("XLSX/тарифы.xlsm");
            ReadPromoEndYersterdayFromXLMS promoTarifsGotOld = new ReadPromoEndYersterdayFromXLMS();
            promoTarifsGotOld.processOneSheet("XLSX/тарифы.xlsm");
            ReadPromoEndTodayFromXLMS promoTarifsGettingOld = new ReadPromoEndTodayFromXLMS();
            promoTarifsGettingOld.processOneSheet("XLSX/тарифы.xlsm");
            JDBCInitializer.connect();
            JDBCLocation.connect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Reports.savePromo(new File("новые промо тарифы (печатать).xls"), TarifAnolizer.executePromo(ArticleReposNew.getInstance().getRepoAcess(), PromoRepo.getInstance().getAcessToRepository()));
            Reports.saveNormals(new File("новые тарифы (печатать).xls"), TarifAnolizer.execute(ArticleReposNew.getInstance().getRepoAcess(), NewTarifRepo.getInstance().getAcessToRepository()));
            Reports.savePromoEndsToday(new File("тарифы закончатся сегодня (менеджерам).xls"), TarifAnolizer.executePromoGettingOld(ArticleReposNew.getInstance().getRepoAcess(), PromoEndTodayRepo.getInstance().getAcessToRepository()));
            Reports.savePromoYersterday(new File("тарифы закончились вчера (печатать).xls"), TarifAnolizer.executePromoEndedYesterday(ArticleReposNew.getInstance().getRepoAcess(), PromoEndRepo.getInstance().getAcessToRepository()));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    JDBCInitializer.close();
    }
}
