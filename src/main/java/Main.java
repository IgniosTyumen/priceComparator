import DB.JDBCInitializer;
import DB.JDBCLocation;
import DB.JDBCUpdateLocation;
import Logic.TarifAnolizer;
import ObjectsProject.Location;
import Readers.*;
import Reports.Reports;
import Repositories.*;
import Spreadsheets.SpreadsheetLocationUpdater;

import java.io.File;
import java.io.IOException;

public class Main {


    public static void main(String[] args) {
        try {

            JDBCUpdateLocation.connect();
            JDBCInitializer.connect();


            ReadOldArticles howto = new ReadOldArticles();
            System.out.println("Начало чтение файла LPDP");
            howto.processOneSheet("XLSX/LPDP.xlsm");
            System.out.println("Начало чтение файла тарифы");
            ReadTarifsFromXLSM tarifs = new ReadTarifsFromXLSM();
            tarifs.processOneSheet("XLSX/тарифы.xlsm");
            ReadPromoFromXLMS promoTarifs = new ReadPromoFromXLMS();
            promoTarifs.processOneSheet("XLSX/тарифы.xlsm");
            ReadPromoEndYersterdayFromXLMS promoTarifsGotOld = new ReadPromoEndYersterdayFromXLMS();
            promoTarifsGotOld.processOneSheet("XLSX/тарифы.xlsm");
            ReadPromoEndTodayFromXLMS promoTarifsGettingOld = new ReadPromoEndTodayFromXLMS();
            promoTarifsGettingOld.processOneSheet("XLSX/тарифы.xlsm");
//            SpreadsheetLocationUpdater.uploadChangesFromSpreadsheet();
//            int length = LocationsOnUpdateRepo.getInstance().getRepoAcess().values().size();
//            System.out.println("Переимплантировано "+length+" артикулов");
//            for (Location location : LocationsOnUpdateRepo.getInstance().getRepoAcess().values()) {
//
//                JDBCUpdateLocation.checkifexists(location, length);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            JDBCUpdateLocation.close();
            JDBCLocation.connect();
        }
        try {

            Reports.savePromo(new File("новые промо тарифы (печатать).xls"), TarifAnolizer.executePromo(ArticleReposNew.getInstance().getRepoAcess(), PromoRepo.getInstance().getAcessToRepository()));
            System.out.println("новые промо выполнено");
            Reports.saveNormals(new File("новые тарифы (печатать).xls"), TarifAnolizer.execute(ArticleReposNew.getInstance().getRepoAcess(), NewTarifRepo.getInstance().getAcessToRepository()));
            System.out.println("новые тарифы выполнено");
            Reports.savePromoEndsToday(new File("тарифы закончатся сегодня (менеджерам).xls"), TarifAnolizer.executePromoGettingOld(ArticleReposNew.getInstance().getRepoAcess(), PromoEndTodayRepo.getInstance().getAcessToRepository()));
            System.out.println("окончание промо сегодня выполнено");
            Reports.savePromoYersterday(new File("тарифы закончились вчера (печатать).xls"), TarifAnolizer.executePromoEndedYesterday(ArticleReposNew.getInstance().getRepoAcess(), PromoEndRepo.getInstance().getAcessToRepository()));
            System.out.println("окончание промо вчера выполнено");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        JDBCInitializer.close();
        JDBCLocation.close();
    }
}
