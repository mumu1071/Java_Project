package dawn.test03;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.spire.pdf.PdfDocument;
import com.spire.pdf.PdfPageBase;
import org.bson.Document;
import util.ExcelUtils;
import util.MongoDBUtil;
import util.bson.BsonUtil;
import util.bson.StudentModel;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author yangjie-mac
 */
public class Test04 {

    private final static String file_base_path = "/Users/yangjie/数据分析/飞飞/重组报告书/";
    private final static int startIndex = 0;
    private final static int endIndex = 2100;

    public static void main(String[] args) {
        Test04 test04 = new Test04();
        test04.dealDataFromDb();
    }

    public void dealDataFromDb() {
        WordParseHelper wordParseHelper = new WordParseHelper();
        MongoDBUtil util = MongoDBUtil.getInstance();
        MongoDatabase database = util.getDatabase("feifei");
        MongoCollection<Document> dbSheet = database.getCollection("test05");
        FindIterable<Document> documents = dbSheet.find();
        for (Document document : documents) {
            Model model = BsonUtil.toBean(document, Model.class);
            if (model == null) {
                System.out.println("转换失败model ");
                continue;
            }
            model.testStr = "测试测试";
            wordParseHelper.getWordCount(model);
            if (model.errorMsg != null) {
                System.out.println("错误 " + model.errorMsg);
            }
            Document newRow = BsonUtil.toBson(model);
            if (newRow == null) {
                System.out.println("转换失败 row ");
                continue;
            }
            //更新操作
            UpdateResult updateResult = dbSheet.updateOne(
                    Filters.eq("_id", document.get("_id")),
                    new Document("$set", newRow));
            long updateCount = updateResult.getMatchedCount();
            if (updateCount > 0) {
                System.out.println("更新成功 " + updateCount);
            } else {
                System.out.println("更新失败 " + updateCount);
            }
        }
    }


    public void dealData() {
        WordParseHelper wordParseHelper = new WordParseHelper();
        MongoDBUtil util = MongoDBUtil.getInstance();
        //获取指定数据库对象
        MongoDatabase database = util.getDatabase("feifei");
        //获取指定集合对象
        MongoCollection<Document> dbSheet = database.getCollection("test05");
        List<List<String>> dataList = ExcelUtils.readFromXLSX(file_base_path + "重大资产重组文本分析.xlsx");

        for (int i = 0; i < dataList.size(); i++) {
            List<String> item = dataList.get(i);
            if (item == null || item.size() < 3 || "".equals(item.get(0))) {
                System.out.println("错误数据 ");
                continue;
            }
            Model model = getModel(item);
            if (i >= startIndex && i <= endIndex) {
                boolean isExit = queryOne(model.TCode, dbSheet);
                if (isExit) {
                    System.out.println("第 " + i + "个 已存在 " + model.TCode);
                    continue;
                }
                System.out.println("正解析第 " + i + " 个");
                readPdf(model);
                wordParseHelper.getWordCount(model);
                if (model.errorMsg != null) {
                    System.out.println("错误 " + model.errorMsg);
                }
                insertOne(model, dbSheet);
            }
        }
        //关闭mongodb连接对象
        util.closeDB();
    }

    public Model getModel(List<String> item) {
        Model model = new Model();
        model.TCode = item.get(0);
        model.股票代码 = item.get(1);
        model.首次披露日期 = item.get(2);
        return model;
    }

    public void insertOne(Model model, MongoCollection<Document> dbSheet) {
        //一条文档中的字段信息和相对应的数据
        Map<String, Object> map = model.getMap();
        //添加一条文档数据
        Document document = new Document(map);
        dbSheet.insertOne(document);
        System.out.println("成功 " + model.TCode);
    }

    /**
     * 查询集合中所有数据
     */
    public boolean queryOne(String TCode, MongoCollection<Document> dbSheet) {
        //筛选条件对象
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("TCode", TCode);
        //获取符合筛选条件的所有数据
        Document document = dbSheet.find(basicDBObject).first();
        return document != null;
    }

    public Model readPdf(Model model) {
        String pdfPath = file_base_path + "重组报告书/" + model.TCode + ".pdf";
        File file = new File(pdfPath);
        if (!file.exists()) {
            model.errorMsg = "pdf 文件不存在";
            return model;
        }
        model.文件大小 = file.length();
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
            model.总页数 = allPageCount;
            for (int i = 0; i < allPageCount; i++) {
                page = doc.getPages().get(i);
                if (page == null) {
                    model.errorMsg = "PDF 有空页";
                    continue;
                }
                String tempStr = page.extractText(false);
                sb.append(tempStr);
            }
        } catch (Exception e) {
            model.errorMsg = e.getMessage();
        } finally {
            try {
                doc.close();
            } catch (Exception e) {
                model.errorMsg = "PDF 关闭异常";
            }
        }
        String tempStr = sb.toString().replace(" ", "");
        if (tempStr.length() > 0) {
            model.总段落数 += countStr(tempStr, "\n");
            model.总段落数 += countStr(tempStr, "\r");
            model.pdfContent = tempStr.replaceAll("\r|\n", "");
            model.总句数 = countStr(model.pdfContent, "。");
            model.总字数 = model.pdfContent.length();
        } else {
            model.errorMsg = "PDF 空内容";
        }
        return model;
    }

    /**
     * @param str     原字符串
     * @param sToFind 需要查找的字符串
     * @return 返回在原字符串中sToFind出现的次数
     */
    private int countStr(String str, String sToFind) {
        int num = 0;
        while (str.contains(sToFind)) {
            str = str.substring(str.indexOf(sToFind) + sToFind.length());
            num++;
        }
        return num;
    }
}
