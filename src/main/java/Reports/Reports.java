package Reports;

import DB.JDBCInitializer;
import DB.JDBCLocation;
import ObjectsProject.Article;
import ObjectsProject.Location;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Reports {

    public static void saveNormals(File file, TreeMap<Integer, Article> print) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Updated tarifs");
       List<Map.Entry<Integer,Article>> comp = print.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Iterator iterator = comp.iterator();

        int dash = 0;
        int rownum = 0;
        Cell cell;
        Row row;



        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderTop(BorderStyle.DOUBLE);
        style1.setBorderBottom(BorderStyle.DOUBLE);
        style1.setBorderLeft(BorderStyle.DOUBLE);
        style1.setBorderRight(BorderStyle.DOUBLE);
        style1.setAlignment(HorizontalAlignment.CENTER);

        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.THIN);
        style2.setBorderBottom(BorderStyle.THIN);
        style2.setBorderLeft(BorderStyle.THIN);
        style2.setBorderRight(BorderStyle.THIN);
        style2.setAlignment(HorizontalAlignment.LEFT);
        style2.setWrapText(true);

        CellStyle style3 = workbook.createCellStyle();
        style3.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont font = workbook.createFont();
        font.setFontName("C39HrP24DhTt");
        font.setFontHeight((short)560);
        style3.setFont(font);
        style3.setWrapText(true);




        sheet.setColumnWidth(3, 10000);
        sheet.setColumnWidth(7, 7000);
        sheet.setColumnWidth(8, 10000);

        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Отчет о действии стандартных тарифов :"+ LocalDate.now());
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Проверил_______________________");
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(0 + dash);
        cell.setCellValue("Отметка");
        cell.setCellStyle(style1);
        cell = row.createCell(1 + dash);
        cell.setCellValue("Сегмент");
        cell.setCellStyle(style2);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Артикул");
        cell.setCellStyle(style2);
        cell = row.createCell(3 + dash);
        cell.setCellValue("Описание");
        cell.setCellStyle(style2);
        cell = row.createCell(4 + dash);
        cell.setCellValue("Цена вчера");
        cell.setCellStyle(style2);
        cell = row.createCell(5 + dash);
        cell.setCellValue("Цена сегодня");
        cell.setCellStyle(style2);
        cell = row.createCell(6 + dash);
        cell.setCellValue("Остатки");
        cell.setCellStyle(style2);
        cell = row.createCell(7 + dash);
        cell.setCellValue("Менеджер");
        cell.setCellStyle(style2);
        cell = row.createCell(8 + dash);
        cell.setCellValue("Штрихкод");
        cell.setCellStyle(style2);
        cell = row.createCell(9 + dash);
        cell.setCellValue("АЛ");
        cell.setCellStyle(style2);
        cell = row.createCell(10 + dash);
        cell.setCellValue("ГОН");
        cell.setCellStyle(style2);
        cell = row.createCell(11 + dash);
        cell.setCellValue("ЭЛ");
        cell.setCellStyle(style2);
        while (iterator.hasNext()) {
            rownum++;
            Map.Entry<Double,Article> entry = (Map.Entry<Double, Article>) iterator.next();
            Article article = entry.getValue();
            row = sheet.createRow(rownum);
            cell = row.createCell(0 + dash);
            cell.setCellStyle(style1);
            cell.setCellValue("O");
            cell = row.createCell(1 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getSegment());
            cell = row.createCell(2 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getEan());
            cell = row.createCell(3 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getArticleName());
            cell = row.createCell(4 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getPrice());
            cell = row.createCell(5 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getNewPrice());
            cell = row.createCell(6 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getRemaings());
            cell = row.createCell(7 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getManager());
            cell = row.createCell(8 + dash);
            cell.setCellStyle(style3);
            String search = JDBCInitializer.checkifexists(article.getEan().intValue());
            if (search!=null){
                cell.setCellValue("*"+search+"*");
            } else
            cell.setCellValue("unknown");

            Location location1 = JDBCLocation.checkifexists(article.getEan());
            if (location1 == null || location1.getAlley().equals(0)){
                location1 = JDBCLocation.checkForUnknown(article.getSegment(), article.getFamily(), article.getCategory());
            }
        cell = row.createCell(9 + dash);
        cell.setCellStyle(style2);
        cell.setCellValue(location1.getAlley());
            cell = row.createCell(10 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getGondol());
            cell = row.createCell(11 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getElement());
    }
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }

    public static void savePromo(File file, TreeMap<Integer, Article> print) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Updated promos");
        List<Map.Entry<Integer,Article>> comp = print.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Iterator iterator = comp.iterator();


        int dash = 0;
        int rownum = 0;
        Cell cell;
        Row row;
        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderTop(BorderStyle.DOUBLE);
        style1.setBorderBottom(BorderStyle.DOUBLE);
        style1.setBorderLeft(BorderStyle.DOUBLE);
        style1.setBorderRight(BorderStyle.DOUBLE);
        style1.setAlignment(HorizontalAlignment.CENTER);

        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.DASH_DOT);
        style2.setBorderBottom(BorderStyle.DASH_DOT);
        style2.setBorderLeft(BorderStyle.DASH_DOT);
        style2.setBorderRight(BorderStyle.DASH_DOT);
        style2.setAlignment(HorizontalAlignment.LEFT);
        style2.setWrapText(true);

        CellStyle style3 = workbook.createCellStyle();
        style3.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont font = workbook.createFont();
        font.setFontHeight((short)560);
        font.setFontName("C39HrP24DhTt");
        style3.setFont(font);
        style3.setWrapText(true);

        sheet.setColumnWidth(3, 11000);
        sheet.setColumnWidth(7, 7000);
        sheet.setColumnWidth(8, 10000);
        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Отчет о начале промотарифов =" + LocalDate.now());

        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Проверил_______________________");
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(0 + dash);
        cell.setCellValue("Отметка");
        cell.setCellStyle(style1);
        cell = row.createCell(1 + dash);
        cell.setCellValue("Сегмент");
        cell.setCellStyle(style2);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Артикул");
        cell.setCellStyle(style2);
        cell = row.createCell(3 + dash);
        cell.setCellValue("Описание");
        cell.setCellStyle(style2);
        cell = row.createCell(4 + dash);
        cell.setCellValue("Цена вчера");
        cell.setCellStyle(style2);
        cell = row.createCell(5 + dash);
        cell.setCellValue("Цена сегодня");
        cell.setCellStyle(style2);
        cell = row.createCell(6 + dash);
        cell.setCellValue("Остатки");
        cell.setCellStyle(style2);
        cell = row.createCell(7 + dash);
        cell.setCellValue("Менеджер");
        cell.setCellStyle(style2);
        cell = row.createCell(8 + dash);
        cell.setCellValue("Штрихкод");
        cell.setCellStyle(style2);
        cell = row.createCell(9 + dash);
        cell.setCellValue("АЛ");
        cell.setCellStyle(style2);
        cell = row.createCell(10 + dash);
        cell.setCellValue("ГОН");
        cell.setCellStyle(style2);
        cell = row.createCell(11 + dash);
        cell.setCellValue("ЭЛ");
        cell.setCellStyle(style2);

        while (iterator.hasNext()) {
            rownum++;
            Map.Entry<Double,Article> entry = (Map.Entry<Double, Article>) iterator.next();
            Article article = entry.getValue();


            row = sheet.createRow(rownum);
            cell = row.createCell(0 + dash);
            cell.setCellStyle(style1);
            cell.setCellValue("O");
            cell = row.createCell(1 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getSegment());
            cell = row.createCell(2 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getEan());
            cell = row.createCell(3 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getArticleName());
            cell = row.createCell(4 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getPrice());
            cell = row.createCell(5 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getNewPrice());
            cell = row.createCell(6 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getRemaings());
            cell = row.createCell(7 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getManager());
            cell = row.createCell(8 + dash);
            cell.setCellStyle(style3);
            String search = JDBCInitializer.checkifexists(article.getEan().intValue());
            if (search!=null){
                cell.setCellValue("*"+search+"*");
            } else
                cell.setCellValue("unknown");
            Location location1 = null;
            if (article!=null) {
                location1 = JDBCLocation.checkifexists(article.getEan());
            }
            if (location1 == null || location1.getAlley().equals(0)){
                location1 = JDBCLocation.checkForUnknown(article.getSegment(), article.getFamily(), article.getCategory());
            }
            cell = row.createCell(9 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getAlley());
            cell = row.createCell(10 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getGondol());
            cell = row.createCell(11 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getElement());

        }
        rownum++;
        sheet.autoSizeColumn(100000);
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }


    public static void savePromoYersterday(File file, TreeMap<Integer, Article> print) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Вчерашнее промо");
        List<Map.Entry<Integer,Article>> comp = print.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Iterator iterator = comp.iterator();


        int dash = 0;
        int rownum = 0;
        Cell cell;
        Row row;
        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderTop(BorderStyle.DOUBLE);
        style1.setBorderBottom(BorderStyle.DOUBLE);
        style1.setBorderLeft(BorderStyle.DOUBLE);
        style1.setBorderRight(BorderStyle.DOUBLE);
        style1.setAlignment(HorizontalAlignment.CENTER);

        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.DASH_DOT);
        style2.setBorderBottom(BorderStyle.DASH_DOT);
        style2.setBorderLeft(BorderStyle.DASH_DOT);
        style2.setBorderRight(BorderStyle.DASH_DOT);
        style2.setAlignment(HorizontalAlignment.LEFT);
        style2.setWrapText(true);

        CellStyle style3 = workbook.createCellStyle();
        style3.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont font = workbook.createFont();
        font.setFontName("C39HrP24DhTt");
        font.setFontHeight((short)560);
        style3.setFont(font);
        style3.setWrapText(true);

        sheet.setColumnWidth(3, 11000);
        sheet.setColumnWidth(6, 7000);
        sheet.setColumnWidth(7, 10000);
        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Отчет о завершении промотарифов ="+ LocalDate.now().minusDays(1));
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Проверил_______________________");
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(0 + dash);
        cell.setCellValue("Отметка");
        cell.setCellStyle(style1);
        cell = row.createCell(1 + dash);
        cell.setCellValue("Сегмент");
        cell.setCellStyle(style2);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Артикул");
        cell.setCellStyle(style2);
        cell = row.createCell(3 + dash);
        cell.setCellValue("Описание");
        cell.setCellStyle(style2);
        cell = row.createCell(4 + dash);
        cell.setCellValue("Цена промо вчера");
        cell.setCellStyle(style2);
        cell = row.createCell(5 + dash);
        cell.setCellValue("Остатки");
        cell.setCellStyle(style2);
        cell = row.createCell(6 + dash);
        cell.setCellValue("Менеджер");
        cell.setCellStyle(style2);
        cell = row.createCell(7 + dash);
        cell.setCellValue("Штрихкод");
        cell.setCellStyle(style2);
        cell = row.createCell(8 + dash);
        cell.setCellValue("АЛ");
        cell.setCellStyle(style2);
        cell = row.createCell(9 + dash);
        cell.setCellValue("ГОН");
        cell.setCellStyle(style2);
        cell = row.createCell(10 + dash);
        cell.setCellValue("ЭЛ");
        cell.setCellStyle(style2);

        while (iterator.hasNext()) {
            rownum++;
            Map.Entry<Double,Article> entry = (Map.Entry<Double, Article>) iterator.next();
            Article article = entry.getValue();


            row = sheet.createRow(rownum);
            cell = row.createCell(0 + dash);
            cell.setCellStyle(style1);
            cell.setCellValue("O");
            cell = row.createCell(1 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getSegment());
            cell = row.createCell(2 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getEan());
            cell = row.createCell(3 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getArticleName());
            cell = row.createCell(4 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getNewPrice());
            cell = row.createCell(5 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getRemaings());
            cell = row.createCell(6 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getManager());
            cell = row.createCell(7 + dash);
            cell.setCellStyle(style3);
            String search = JDBCInitializer.checkifexists(article.getEan().intValue());
            if (search!=null){
                cell.setCellValue("*"+search+"*");
            } else
                cell.setCellValue("unknown");
            Location location1 = null;
            if (article!=null) {
                location1 = JDBCLocation.checkifexists(article.getEan());
            }
            if (location1 == null || location1.getAlley().equals(0)){
                location1 = JDBCLocation.checkForUnknown(article.getSegment(), article.getFamily(), article.getCategory());
            }
            cell = row.createCell(8 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getAlley());
            cell = row.createCell(9 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getGondol());
            cell = row.createCell(10 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getElement());
        }
        rownum++;
        sheet.autoSizeColumn(100000);
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }


    public static void savePromoEndsToday(File file, TreeMap<Integer, Article> print) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("Закончится сегодня");
        List<Map.Entry<Integer,Article>> comp = print.entrySet().stream().sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Iterator iterator = comp.iterator();

        int dash = 0;
        int rownum = 0;
        Cell cell;
        Row row;
        CellStyle style1 = workbook.createCellStyle();
        style1.setBorderTop(BorderStyle.DOUBLE);
        style1.setBorderBottom(BorderStyle.DOUBLE);
        style1.setBorderLeft(BorderStyle.DOUBLE);
        style1.setBorderRight(BorderStyle.DOUBLE);
        style1.setAlignment(HorizontalAlignment.CENTER);

        CellStyle style2 = workbook.createCellStyle();
        style2.setBorderTop(BorderStyle.DASH_DOT);
        style2.setBorderBottom(BorderStyle.DASH_DOT);
        style2.setBorderLeft(BorderStyle.DASH_DOT);
        style2.setBorderRight(BorderStyle.DASH_DOT);
        style2.setAlignment(HorizontalAlignment.LEFT);
        style2.setWrapText(true);

        CellStyle style3 = workbook.createCellStyle();
        style3.setAlignment(HorizontalAlignment.LEFT);
        HSSFFont font = workbook.createFont();
        font.setFontName("C39HrP24DhTt");
        font.setFontHeight((short)560);
        style3.setFont(font);
        style3.setWrapText(true);

        sheet.setColumnWidth(3, 11000);
        sheet.setColumnWidth(6, 7000);
        sheet.setColumnWidth(7, 10000);
        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Отчет о завершении промотарифов ="+ LocalDate.now());
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Проверил_______________________");
        rownum++;
        row = sheet.createRow(rownum);
        cell = row.createCell(0 + dash);
        cell.setCellValue("Отметка");
        cell.setCellStyle(style1);
        cell = row.createCell(1 + dash);
        cell.setCellValue("Сегмент");
        cell.setCellStyle(style2);
        cell = row.createCell(2 + dash);
        cell.setCellValue("Артикул");
        cell.setCellStyle(style2);
        cell = row.createCell(3 + dash);
        cell.setCellValue("Описание");
        cell.setCellStyle(style2);
        cell = row.createCell(4 + dash);
        cell.setCellValue("Цена по промо");
        cell.setCellStyle(style2);
        cell = row.createCell(5 + dash);
        cell.setCellValue("Остатки");
        cell.setCellStyle(style2);
        cell = row.createCell(6 + dash);
        cell.setCellValue("Менеджер");
        cell.setCellStyle(style2);
        cell = row.createCell(7 + dash);
        cell.setCellValue("Штрихкод");
        cell.setCellStyle(style2);
        cell = row.createCell(8 + dash);
        cell.setCellValue("АЛ");
        cell.setCellStyle(style2);
        cell = row.createCell(9 + dash);
        cell.setCellValue("ГОН");
        cell.setCellStyle(style2);
        cell = row.createCell(10 + dash);
        cell.setCellValue("ЭЛ");
        cell.setCellStyle(style2);

        while (iterator.hasNext()) {
            rownum++;
            Map.Entry<Double,Article> entry = (Map.Entry<Double, Article>) iterator.next();
            Article article = entry.getValue();


            row = sheet.createRow(rownum);
            cell = row.createCell(0 + dash);
            cell.setCellStyle(style1);
            cell.setCellValue("O");
            cell = row.createCell(1 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getSegment());
            cell = row.createCell(2 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getEan());
            cell = row.createCell(3 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getArticleName());
            cell = row.createCell(4 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getNewPrice());
            cell = row.createCell(5 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getRemaings());
            cell = row.createCell(6 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(article.getManager());
            cell = row.createCell(7 + dash);
            cell.setCellStyle(style3);
            String search = JDBCInitializer.checkifexists(article.getEan().intValue());
            if (search!=null){
                cell.setCellValue("*"+search+"*");
            } else
                cell.setCellValue("unknown");
            Location location1 = null;
            if (article!=null) {
                location1 = JDBCLocation.checkifexists(article.getEan());
            }
            if (location1 == null || location1.getAlley().equals(0)){
                location1 = JDBCLocation.checkForUnknown(article.getSegment(), article.getFamily(), article.getCategory());
            }
            cell = row.createCell(8 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getAlley());
            cell = row.createCell(9 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getGondol());
            cell = row.createCell(10 + dash);
            cell.setCellStyle(style2);
            cell.setCellValue(location1.getElement());
        }
        rownum++;
        sheet.autoSizeColumn(100000);
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
    }

}