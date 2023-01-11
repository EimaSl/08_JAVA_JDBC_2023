//package lesson23.teamWork.service;
//
//
//
//import com.itextpdf.kernel.geom.PageSize;
//import com.itextpdf.kernel.pdf.PdfDocument;
//import com.itextpdf.kernel.pdf.PdfWriter;
//
//import java.io.FileNotFoundException;
//import java.net.MalformedURLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class GeneratePDF {
//
//    public static void main(String[] args) throws FileNotFoundException, MalformedURLException {
//
//        String path = "invoice.pdf";
//        PdfWriter pdfWriter = new PdfWriter(path);
//        PdfDocument pdfDocument = new PdfDocument(pdfWriter);
//        pdfDocument.setDefaultPageSize(PageSize.A4);
//
//        Document document = new Document(pdfDocument);
//        //watermark logo
//        String imagePath = "src/main/resources/test.png";
//        ImageData imageData = ImageDataFactory.create(imagePath);
//        Image image = new Image(imageData);
//
//        float x = pdfDocument.getDefaultPageSize().getWidth()/2;
//        float y = pdfDocument.getDefaultPageSize().getHeight()/2;
//        image.setFixedPosition(x-180, y-380);
//        image.setOpacity(0.4f);
//        document.add(image);
//
//        float threecol = 190f;
//        float twocol = 285f;
//        float twocol150 = twocol + 150f;
//        float twocolumnWidth[] = {twocol150, twocol};
//        float threeColumnWidth[] = {threecol, threecol, threecol};
//
//        float fullwidth[] = {threecol * 3};
//        Paragraph onespace = new Paragraph("\n");
//
//        Table table = new Table(twocolumnWidth);
//
//        table.addCell(new Cell().add("INVOICE").setFontSize(20f).setBorder(Border.NO_BORDER).setBold());
//
//        Table nestedTable = new Table(new float[]{twocol / 2, twocol / 2});
//        //nestedTable.addCell(new Cell().add("Invoice No. ").setBorder(Border.NO_BORDER));
//        nestedTable.addCell(getHeaderTextCell("Invoice No.: "));
//        //nestedTable.addCell(new Cell().add("LT546464565").setBorder(Border.NO_BORDER));
//        nestedTable.addCell(getHeaderTextCellValue("LT546464565"));
//        //nestedTable.addCell(new Cell().add("Invoice Date").setBorder(Border.NO_BORDER));
//        nestedTable.addCell(getHeaderTextCell("Invoice Date: "));
//        //nestedTable.addCell(new Cell().add("15/16/2022").setBorder(Border.NO_BORDER));
//        nestedTable.addCell(getHeaderTextCellValue("15/16/2022"));
//
//
//        table.addCell(new Cell().add(nestedTable).setBorder(Border.NO_BORDER));
//
//        //line
//        Border greyBorder = new SolidBorder(Color.GRAY, 2f);
//        Table divider = new Table(fullwidth);
//        divider.setBorder(greyBorder);
//
//        document.add(divider);
//
//        document.add(table);
//        document.add(onespace);
//        document.add(divider);
//        document.add(onespace);
//
//        //creating billing information
//        Table twoColTable = new Table(twocolumnWidth);
//        twoColTable.addCell(getBillingShippingCell("Billing Information"));
//        twoColTable.addCell(getBillingShippingCell("Shipping Information"));
//        document.add(twoColTable.setMargin(20f));
//
//        Table twoColTable2 = new Table(twocolumnWidth);
//        twoColTable2.addCell(getCell10fLeft("Company", true));
//        twoColTable2.addCell(getCell10fLeft("Name", true));
//        twoColTable2.addCell(getCell10fLeft("UAB VIT & K \n code: 3564564544", false));
//        twoColTable2.addCell(getCell10fLeft("UAB TRANS PANS HANS", false));
//        document.add(twoColTable2);
//
//        Table twoColTable3 = new Table(twocolumnWidth);
//        twoColTable3.addCell(getCell10fLeft("Name", true));
//        twoColTable3.addCell(getCell10fLeft("Address", true));
//        twoColTable3.addCell(getCell10fLeft("UAB VIT & K \n code: 3564564544", false));
//        twoColTable3.addCell(getCell10fLeft("Kaunas, Aplinkelio kelias 15, \n post code: LT21212 30,\n tel: 8686546465", false));
//        document.add(twoColTable3);
//
//        float oneColumnWidth[] = {twocol150};
//
//        Table oneColTable1 = new Table(oneColumnWidth);
//        oneColTable1.addCell(getCell10fLeft("Address", true));
//        oneColTable1.addCell(getCell10fLeft("MINDAUGO 15, LT-45662 ,\n Vilnius", false));
//        oneColTable1.addCell(getCell10fLeft("Email", true));
//        oneColTable1.addCell(getCell10fLeft("info@vilnius.lt", false));
//        document.add(oneColTable1.setMargin(5f));
//
//        //dotted line
//        Table tableDivider2 = new Table(fullwidth);
//        Border dgd = new DashedBorder(Color.GRAY, 0.5f);
//        document.add(tableDivider2.setBorder(dgd));
//
//        Paragraph producPara = new Paragraph("Products");
//        document.add(producPara.setBold());
//
//        Table threeColTable1 = new Table(threeColumnWidth);
//        threeColTable1.setBackgroundColor(Color.BLACK, 0.7f);
//
//        threeColTable1.addCell(new Cell().add("Description").setBold().setFontColor(Color.WHITE).setBorder(Border.NO_BORDER));
//        threeColTable1.addCell(new Cell().add("Quantity").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
//        threeColTable1.addCell(new Cell().add("Price").setBold().setFontColor(Color.WHITE).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
//        document.add(threeColTable1);
//
//        List<Product> productList = new ArrayList<>();
//        productList.add(new Product("Apple", 2, 2));
//        productList.add(new Product("Banana", 3, 3));
//        productList.add(new Product("Potatoes", 1, 2));
//
//        productList.add(new Product("Meat", 4, 4));
//        productList.add(new Product("Kiwi", 1, 4));
//        productList.add(new Product("Cherry", 2, 4));
//        productList.add(new Product("Coconut", 3, 4));
//
//        Table threeColTable2 = new Table(threeColumnWidth);
//
//        float totalSum = 0f;
//        for (Product product : productList) {
//            float total = product.getPricePerPiece() * product.getQuantity();
//            totalSum += total;
//            threeColTable2.addCell(new Cell().add(product.getName()).setFontColor(Color.BLACK).setBorder(Border.NO_BORDER));
//            threeColTable2.addCell(new Cell().add(String.valueOf(product.getQuantity())).setFontColor(Color.BLACK).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
//            threeColTable2.addCell(new Cell().add(String.valueOf(total)).setFontColor(Color.BLACK).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
//        }
//        document.add(threeColTable2.setMarginBottom(20f));
//
//        //dotted Line
//        float onetwo[] = {threecol + 125f, threecol * 2};
//        Table threeColTable4 = new Table(onetwo);
//        threeColTable4.addCell(new Cell().add("").setBorder(Border.NO_BORDER));
//        threeColTable4.addCell(new Cell().add(tableDivider2).setBorder(Border.NO_BORDER));
//        document.add(threeColTable4);
//
//        //Total
//        Table threeColTable3 = new Table(threeColumnWidth);
//        threeColTable3.addCell(new Cell().add("").setBorder(Border.NO_BORDER)).setMargin(10f);
//        threeColTable3.addCell(new Cell().add("Total").setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
//        threeColTable3.addCell(new Cell().add(String.valueOf(totalSum)).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
//        document.add(threeColTable3);
//        //dotted line
//        document.add(tableDivider2);
//        document.add(new Paragraph("\n"));
//        document.add(divider.setBorder(new SolidBorder(Color.GRAY, 1)).setMargin(15f));
//        //terms and conditions
//        Table tb = new Table(fullwidth);
//        tb.addCell(new Cell().add("TERMS AND CONDITIONS\n").setBold().setBorder(Border.NO_BORDER));
//        List<String> list = new ArrayList<>();
//        list.add("1. The seller shall not be liable ................");
//        list.add("2. The seller shall not be liable ................");
//        list.add("3. Warranty start from sell ing date :................");
//
//        for (String s : list) {
//            tb.addCell(new Cell().add(s).setBorder(Border.NO_BORDER));
//        }
//        document.add(tb);
//
//        document.close();
//
//    }
//
//    static Cell getHeaderTextCell(String textValue) {
//        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
//    }
//
//    static Cell getHeaderTextCellValue(String textValue) {
//        return new Cell().add(textValue).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.RIGHT);
//    }
//
//    static Cell getBillingShippingCell(String textValue) {
//        return new Cell().add(textValue).setFontSize(12f).setBold().setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
//    }
//
//    static Cell getCell10fLeft(String textValue, Boolean isBold) {
//        Cell myCell = new Cell().add(textValue).setFontSize(10f).setBorder(Border.NO_BORDER).setTextAlignment(TextAlignment.LEFT);
//        return isBold ? myCell.setBold() : myCell;
//    }
//}
