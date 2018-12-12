package Readers;

import ObjectsProject.NewTarif;
import Repositories.NewTarifRepo;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.InputStream;

/**
 * XSSF and SAX (Event API)
 */
public class ReadTarifsFromXLSM {

    static Integer ean = 0;
    static Double price = 0.0;
    static Double remaings = 0.0;
    static String manager = "";
    static String chief = "";
    static Double segment = 0.0;
    static String articleName = "";
    static String status = "";
    static boolean ready = false;
    static String cellAdr = "";
    static String filename = "";
    static String rowAdr = "";


    public static String getRowAdr() {
        return rowAdr;
    }

    public static void setRowAdr(String rowAdr) {
        ReadOldArticles.rowAdr = rowAdr;
    }

    public static Integer getEan() {
        return ean;
    }

    public static void setEan(Integer ean) {
        ReadOldArticles.ean = ean;
    }

    public static Double getPrice() {
        return price;
    }

    public static void setPrice(Double price) {
        ReadOldArticles.price = price;
    }

    public static Double getRemaings() {
        return remaings;
    }

    public static void setRemaings(Double remaings) {
        ReadOldArticles.remaings = remaings;
    }

    public static String getManager() {
        return manager;
    }

    public static void setManager(String manager) {
        ReadOldArticles.manager = manager;
    }

    public static String getChief() {
        return chief;
    }

    public static void setChief(String chief) {
        ReadOldArticles.chief = chief;
    }

    public static Double getSegment() {
        return segment;
    }


    public static String getArticleName() {
        return articleName;
    }

    public static void setArticleName(String articleName) {
        ReadOldArticles.articleName = articleName;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        ReadOldArticles.status = status;
    }

    public static boolean isReady() {
        return ready;
    }

    public static void setReady(boolean ready) {
        ReadOldArticles.ready = ready;
    }

    public static String getCellAdr() {
        return cellAdr;
    }

    public static void setCellAdr(String cellAdr) {
        ReadOldArticles.cellAdr = cellAdr;
    }

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        ReadOldArticles.filename = filename;
    }

    public void processOneSheet(String filename) throws Exception {
        setFilename(filename);
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // rId2 found by processing the Workbook
        // Seems to either be rId# or rSheet#
        InputStream sheet2 = r.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet2);
        parser.parse(sheetSource);
        sheet2.close();
    }


    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser =
                XMLReaderFactory.createXMLReader(
                        "org.apache.xerces.parsers.SAXParser"
                );
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private static class SheetHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;
        private String adress;

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        private static void uploadTarifParts(String adressColumn, String meaning) {
            switch (adressColumn) {
                case ("B"): {
                    ean = Integer.parseInt(meaning);
                    break;
                }
                case ("G"): {
                    price = Double.parseDouble(meaning);
                    NewTarif tarif = new NewTarif(ean, price);
                    NewTarifRepo.getInstance().putTarif(tarif);
                    break;
                }
            }
        }

        public void startElement(String uri, String localName, String name,
                                 Attributes attributes) throws SAXException {
            // c => cell
            if (name.equals("c")) {
                // Print the cell reference
                adress = attributes.getValue("r");

                String cellAdrs[] = CellSeparator.translateAdressToColAndRows(adress);
                cellAdr = cellAdrs[0];
                rowAdr = cellAdrs[1];
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if (cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }
            // Clear contents cache
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name)
                throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if (nextIsString && lastContents != null && !lastContents.equals("")) {

                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
            }

            // v => contents of a cell
            // Output after we've seen the string contents
            if (name.equals("v") && !rowAdr.equals("1")) {
                uploadTarifParts(ReadTarifsFromXLSM.getCellAdr(), lastContents);
            }
            lastContents = "";

        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }


}