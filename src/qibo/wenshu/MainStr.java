package qibo.wenshu;

import com.google.gson.Gson;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import util.MongoDBUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author yangjie-mac
 */

public class MainStr {

    private static final MongoDBUtil util = MongoDBUtil.getInstance();
    public static String fileName = "/Users/yangjie/数据分析/祁博/案件/json/著作权合同纠纷/test.json";

    public static void main(String[] args) {
        //获取指定数据库对象
        MongoDatabase database = util.getDatabase("dhgate");
        //获取指定集合对象
        MongoCollection<Document> dbSheet = database.getCollection("wen_shu");
        Gson gson = new Gson();

        String strData = readFileContent(fileName);
        Model model = gson.fromJson(strData, Model.class);
        String des3DecodeCBC = Des3Utils.decrypt(model.secretKey, "20210515", model.result);
        System.out.println(des3DecodeCBC);
    }

    public static void insertOne(Map<String, Object> map, MongoCollection<Document> dbSheet) {
        //添加一条文档数据
        Document document = new Document(map);
        dbSheet.insertOne(document);
        System.out.println("成功 ");
    }

    public static String readFileContent(String fileName) {
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sbf = new StringBuffer();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            return sbf.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sbf.toString();
    }


}
