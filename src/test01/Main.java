package test01;

import java.util.List;

/**
 * @author yangjie-mac
 */
public class Main {

    private final static String xlsx2007 = "/Users/yangjie-mac/Desktop/数据分析/抓数据/表格数据";
    private final static String pdfPath = "/Users/yangjie-mac/Desktop/数据分析/抓数据/表格数据";
    private final static String xlsx2007_copy = "/Users/yangjie-mac/Desktop/数据分析/抓数据/表格数据";

    public static void main(String[] args) {
//        dealShanghaiEnquiry();
//        dealShanghaiManage();

//        dealShenJiaoManage();
        dealShenZhen();
    }

    /**
     * 上交所-问询函
     */
    public static void dealShanghaiEnquiry() {
        List<List<String>> dataList = ExcelUtils.readFromXLSX(xlsx2007 + "/上交所/上交所-监管问询.xlsx");
        for (List<String> item : dataList) {
            if (item.size() < 4) {
                break;
            }
            String pdfTemp = pdfPath + "/上交所/上交所-监管问询/" + item.get(0) + item.get(4) + ".pdf";
            if (!pdfTemp.isEmpty()) {
                item.add(PdfUtils.readPdf(pdfTemp));
            }
        }
        ExcelUtils.writeExcel(xlsx2007_copy + "/上交所/上交所-监管问询_copy.xlsx", dataList);
    }

    /**
     * 上交所-监管措施
     */
    public static void dealShanghaiManage() {
        List<List<String>> dataList = ExcelUtils.readFromXLSX(xlsx2007 + "/上交所/上交所-监管措施.xlsx");
        for (List<String> item : dataList) {
            if (item.size() < 4) {
                break;
            }
            String pdfTemp = pdfPath + "/上交所/上交所-监管措施/" + item.get(0) + item.get(3) + ".pdf";
            if (!pdfTemp.isEmpty()) {
                item.add(PdfUtils.readPdf(pdfTemp));
            }
        }
        ExcelUtils.writeExcel(xlsx2007_copy + "/上交所/上交所-监管措施_copy.xlsx", dataList);
    }


    /**
     * 深交所-监管措施
     */
    public static void dealShenJiaoManage() {
        List<List<String>> dataList = ExcelUtils.readFromXLSX(xlsx2007 + "/深交所/监管措施.xlsx");
        for (List<String> item : dataList) {
            if (item.size() < 4) {
                break;
            }
            String pdfTemp = pdfPath + "/深交所/监管措施/" + item.get(4);
            if (!pdfTemp.isEmpty()) {
                item.add(PdfUtils.readPdf(pdfTemp));
            }
        }
        ExcelUtils.writeExcel(xlsx2007_copy + "/深交所/监管措施_copy.xlsx", dataList);
    }

    /**
     * 深交所-问询函
     */
    public static void dealShenZhen() {
        List<List<String>> dataList1 = ExcelUtils.readFromXLSX(xlsx2007 + "/深交所/问询函/创业板.xlsx");
        for (List<String> item : dataList1) {
            while (item.size() < 8) {
                item.add(" ");
            }
            if (!"".equals(item.get(4))) {
                String pdfTemp = pdfPath + "/深交所/问询函/创业板/" + item.get(4);
                if (!pdfTemp.isEmpty()) {
                    item.add(PdfUtils.readPdf(pdfTemp));
                }
            }
            if (!"".equals(item.get(5))) {
                String pdfTemp = pdfPath + "/深交所/问询函/创业板-回复/" + item.get(5);
                if (!pdfTemp.isEmpty()) {
                    item.add(PdfUtils.readPdf(pdfTemp));
                }
            }
        }
        ExcelUtils.writeExcel(xlsx2007_copy + "/深交所/问询函/创业板_copy.xlsx", dataList1);


        List<List<String>> dataList2 = ExcelUtils.readFromXLSX(xlsx2007 + "/深交所/问询函/中小企业板.xlsx");
        for (List<String> item : dataList2) {
            while (item.size() < 8) {
                item.add(" ");
            }
            if (!"".equals(item.get(4))) {
                String pdfTemp = pdfPath + "/深交所/问询函/中小企业板/" + item.get(4);
                if (!pdfTemp.isEmpty()) {
                    item.add(PdfUtils.readPdf(pdfTemp));
                }
            }
            if (!"".equals(item.get(5))) {
                String pdfTemp = pdfPath + "/深交所/问询函/中小企业板-回复/" + item.get(5);
                if (!pdfTemp.isEmpty()) {
                    item.add(PdfUtils.readPdf(pdfTemp));
                }
            }
        }
        ExcelUtils.writeExcel(xlsx2007_copy + "/深交所/问询函/中小企业板_copy.xlsx", dataList2);


        List<List<String>> dataList3 = ExcelUtils.readFromXLSX(xlsx2007 + "/深交所/问询函/主板.xlsx");
        for (List<String> item : dataList3) {
            while (item.size() < 8) {
                item.add(" ");
            }
            if (!"".equals(item.get(4))) {
                String pdfTemp = pdfPath + "/深交所/问询函/主板/" + item.get(4);
                if (!pdfTemp.isEmpty()) {
                    item.add(PdfUtils.readPdf(pdfTemp));
                }
            }
            if (!"".equals(item.get(5))) {
                String pdfTemp = pdfPath + "/深交所/问询函/主板-回复/" + item.get(5);
                if (!pdfTemp.isEmpty()) {
                    item.add(PdfUtils.readPdf(pdfTemp));
                }
            }
        }
        ExcelUtils.writeExcel(xlsx2007_copy + "/深交所/问询函/主板_copy.xlsx", dataList3);

    }


}
