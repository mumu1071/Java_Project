package test01;


import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;

public final class PdfUtils {

    public static String readPdf(String pdfPath) {
        //创建StringBuilder实例
        StringBuilder sb = new StringBuilder();
        //创建PdfDocument实例
        PdfDocument doc = new PdfDocument();
        try {
            //加载PDF文件
            doc.loadFromFile(pdfPath);
            PdfPageBase page;
            //遍历PDF页面，获取每个页面的文本并添加到StringBuilder对象
            for (int i = 0; i < doc.getPages().getCount(); i++) {
                page = doc.getPages().get(i);
                String tempStr = page.extractText(false);
                sb.append(tempStr.replaceAll("\r|\n", ""));
            }
        } catch (Exception e) {
            return sb.toString();
        } finally {
            try {
                doc.close();
            } catch (Exception e) {

            }
        }
        return sb.toString().replace(" ", "");
    }

}
