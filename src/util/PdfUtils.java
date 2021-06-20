package util;


import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;

import java.io.File;

public final class PdfUtils {

    public static String readPdf(String pdfPath) {
        File file = new File(pdfPath);
        if (!file.exists()) {
            return "";
        }
        //创建StringBuilder实例
        StringBuilder sb = new StringBuilder();
        //创建PdfDocument实例
        PdfDocument doc = new PdfDocument();
        try {
            //加载PDF文件
            doc.loadFromFile(pdfPath);
            PdfPageBase page;
            //遍历PDF页面，获取每个页面的文本并添加到StringBuilder对象
            int allPageCount = doc.getPages().getCount();
            for (int i = 0; i < allPageCount; i++) {
                page = doc.getPages().get(i);
                if (page == null) {
                    return sb.toString().replace(" ", "");
                }
//                System.out.println(pdfPath + " 总页码 " + allPageCount + " " + i);
                String tempStr = page.extractText(false);
                if (tempStr != null && tempStr.length() > 0) {
                    sb.append(tempStr.replaceAll("\r|\n", ""));
                }
//                System.out.println(pdfPath + " 总页码 " + allPageCount + " " + i + " " + tempStr.length());
            }
        } catch (Exception e) {
            return sb.toString();
        } finally {
            try {
                doc.close();
            } catch (Exception e) {
                System.out.println("异常 " + e.getMessage());
            }
        }
        return sb.toString().replace(" ", "");
    }

}
