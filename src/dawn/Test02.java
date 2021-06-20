package dawn;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.bson.Document;
import util.ExcelUtils;
import util.MongoDBUtil;
import util.PdfUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yangjie-mac
 */
public class Test02 {
    private final static String file_lm_words = "/Users/yangjie/数据分析/语义区分/文本语调分析/LM词库.xlsx";
    private final static String file_base_path = "/Users/yangjie/数据分析/飞飞/万德科创版上市/";
    private static final MongoDBUtil util = MongoDBUtil.getInstance();

    private final static int startIndex = 250;
    private final static int endIndex = 253;


    public static void main(String[] args) {
        //获取指定数据库对象
        MongoDatabase database = util.getDatabase("feifei");
        //获取指定集合对象
        MongoCollection<Document> dbSheet = database.getCollection("pdf");
        List<String> positiveList = getWordList(0);
        List<String> negativeList = getWordList(1);
        List<List<String>> dataList = ExcelUtils.readFromXLSX(file_base_path + "文本结果.xlsx");
        for (int i = 0; i < dataList.size(); i++) {
            List<String> item = dataList.get(i);
            if (item == null || item.size() < 4 || "".equals(item.get(0))) {
                System.out.println("错误数据 ");
                continue;
            }
            if (i >= startIndex && i <= endIndex) {
                boolean isExit = queryOne(item.get(0), dbSheet);
                if (isExit) {
                    System.out.println("第 "+ i +"个 已存在 " + item.get(0));
                    continue;
                }
                System.out.println("正解析第 " + i + " 个");
                dealData(item, dbSheet, positiveList, negativeList);
                insertOne(item, dbSheet);
            }
        }
        //关闭mongodb连接对象
        util.closeDB();
    }

    public static void dealData(List<String> item, MongoCollection<Document> dbSheet, List<String> positiveList, List<String> negativeList) {
        String countShenBao = PdfUtils.readPdf(file_base_path + "申报稿/" + item.get(0) + "+申报稿.pdf");
        item.add(String.valueOf(countShenBao == null ? 0 : countShenBao.length()));
        System.out.println("申报稿字数 " + item.get(4) + " 个");

        String countShangHui = PdfUtils.readPdf(file_base_path + "上会稿/" + item.get(0) + "+上会稿.pdf");
        item.add(String.valueOf(countShangHui == null ? 0 : countShangHui.length()));
        System.out.println("上会稿字数 " + item.get(5) + " 个");

        String countWenXun = PdfUtils.readPdf(file_base_path + "问询函/" + item.get(0) + "+问询函.pdf");
        item.add(String.valueOf(countWenXun == null ? 0 : countWenXun.length()));
        System.out.println("问询函字数 " + item.get(6) + " 个");

        int positiveCount = getWordCount(countWenXun, positiveList);
        int negativeCount = getWordCount(countWenXun, negativeList);
        System.out.println("积极个数 " + positiveCount + " 消极个数 " + negativeCount);

        item.add(String.valueOf(positiveCount));
        item.add(String.valueOf(negativeCount));
    }

    public static void insertOne(List<String> strList, MongoCollection<Document> dbSheet) {
        //一条文档中的字段信息和相对应的数据
        Map<String, Object> map = new HashMap<>();
        map.put("code", strList.get(0));
        map.put("daiMa", strList.get(1));
        map.put("jianCheng", strList.get(2));
        map.put("name", strList.get(3));

        map.put("countShenBao", strList.get(4));
        map.put("countShangHui", strList.get(5));
        map.put("countWenXun", strList.get(6));
        map.put("positiveCount", strList.get(7));
        map.put("negativeCount", strList.get(7));

        //添加一条文档数据
        Document document = new Document(map);
        dbSheet.insertOne(document);
        System.out.println("成功 " + strList.get(0));
    }

    /**
     * 查询集合中所有数据
     */
    public static boolean queryOne(String code, MongoCollection<Document> dbSheet) {
        //筛选条件对象
        BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("code", code);
        //获取符合筛选条件的所有数据
        Document document = dbSheet.find(basicDBObject).first();
        return document != null;
    }


    public static int getWordCount(String content, List<String> wordList) {
        if (content == null || content.length() == 0) {
            return 0;
        }
        Result result = ToAnalysis.parse(content);
        if (result == null || result.getTerms() == null || result.getTerms().size() == 0) {
            return 0;
        }
        int count = 0;
        for (Term term : result.getTerms()) {
            if (wordList.contains(term.getName())) {
                count++;
            }
        }
        return count;
    }

    public static List<String> getWordList(int sheetIndex) {
        List<List<String>> dataList = ExcelUtils.readFromXLSX(file_lm_words, sheetIndex);
        List<String> wordList = new ArrayList<>();
        for (List<String> strList : dataList) {
            if (strList != null && strList.size() > 0) {
                wordList.add(strList.get(0));
            }
        }
        return wordList;
    }

    public static void dealByExcel() {
        List<String> positiveList = getWordList(0);
        List<String> negativeList = getWordList(1);

        List<List<String>> dataList = ExcelUtils.readFromXLSX(file_base_path + "文本结果.xlsx");
        for (int i = 0; i < dataList.size(); i++) {
            List<String> item = dataList.get(i);
            if (item == null || item.size() < 4 || "".equals(item.get(0))) {
                System.out.println("错误数据 ");
                continue;
            }
            if (i < 50) {
                System.out.println("正解析 " + i + " 个");

                String countShenBao = PdfUtils.readPdf(file_base_path + "申报稿/" + item.get(0) + "+申报稿.pdf");
                item.add(String.valueOf(countShenBao == null ? 0 : countShenBao.length()));
                System.out.println("申报稿字数 " + item.get(4) + " 个");

                String countShangHui = PdfUtils.readPdf(file_base_path + "上会稿/" + item.get(0) + "+上会稿.pdf");
                item.add(String.valueOf(countShangHui == null ? 0 : countShangHui.length()));
                System.out.println("上会稿字数 " + item.get(5) + " 个");

                String countWenXun = PdfUtils.readPdf(file_base_path + "问询函/" + item.get(0) + "+问询函.pdf");
                item.add(String.valueOf(countWenXun == null ? 0 : countWenXun.length()));
                System.out.println("问询函字数 " + item.get(6) + " 个");

                int positiveCount = getWordCount(countWenXun, positiveList);
                int negativeCount = getWordCount(countWenXun, negativeList);
                System.out.println("积极个数 " + positiveCount + " 消极个数 " + negativeCount);

                item.add(String.valueOf(positiveCount));
                item.add(String.valueOf(negativeCount));
            }

        }
        ExcelUtils.writeExcel(file_base_path + "结果0-50.xlsx", dataList);
    }


}
