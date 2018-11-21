package Readers;

import ObjectsProject.Article;
import Repositories.ArticleReposNew;
import Repositories.ArticleReposOld;
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
public class ReadOldArticles {

    static Double ean = 0.0;
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

    public static Double getEan() {
        return ean;
    }

    public static void setEan(Double ean) {
        ReadOldArticles.ean = ean;
    }


    public static String getCellAdr() {
        return cellAdr;
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
        InputStream sheet2 = r.getSheet("rId4");
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


        private static void uploadArticleParts(String adressColumn, String meaning) {
            switch (adressColumn) {
                case ("A"): {
                    segment = Double.parseDouble(meaning);
                    break;
                }
                case ("D"): {
                    ean = Double.parseDouble(meaning);
                    break;
                }
                case ("E"): {
                    articleName = meaning;
                    break;
                }
                case ("F"): {
                    status = meaning;
                    break;
                }
                case ("I"): {
                    price = Double.parseDouble(meaning);
                    break;
                }
                case ("M"): {
                    remaings = Double.parseDouble(meaning);
                    break;
                }
                case ("AH"): {
                    chief = (meaning);
                    break;
                }
                case ("AI"): {
                    manager = (meaning);
                    Article article = new Article(ean, price, remaings, meaning, chief, segment, articleName, status);
                    if (filename.equals("LPDPold")) {
                        ArticleReposOld.getInstance().putArticle(article);
                    } else {
                        ArticleReposNew.getInstance().putArticle(article);
                    }
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
                uploadArticleParts(ReadOldArticles.getCellAdr(), lastContents);
            }
            lastContents = "";

        }

        public void characters(char[] ch, int start, int length)
                throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }


}