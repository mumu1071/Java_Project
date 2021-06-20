package study;

import com.mongodb.BasicDBObject;
import com.mongodb.QueryBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import util.MongoDBUtil;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class MongoStudy {

    private static MongoCollection<Document> sheet;
    private static MongoDBUtil mongoDBUtil;


    public static void main(String[] args) {
        initDB();
        demo();
    }

    public static void demo() {
        List<Bson> list = new ArrayList<>();
        list.add(Filters.eq("name", "zhangsan"));
        list.add(Filters.lte("age", 18));
        Bson bson = Filters.and(list);
        //获取指定集合中所有文档(数据)
        FindIterable<Document> documents = sheet.find().projection(new BasicDBObject("_id", 0));
        //1.直接遍历
        for (Document document : documents) {
            System.out.println("document = " + document);
        }
        //关闭mongodb连接对象
        mongoDBUtil.closeDB();
    }

    public static void insert() {
        //一条文档中的字段信息和相对应的数据
        Map<String, Object> map = new HashMap<>();
        map.put("name", "王五");
        map.put("age", 18);
        map.put("sex", "男");

        //添加一条文档数据
        Document document = new Document(map);
        sheet.insertOne(document);

        //多条数据
        List<Document> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            //一条文档中的字段信息和相对应的数据
            Map<String, Object> mapTemp = new HashMap<>();
            mapTemp.put("name", "王五" + i);
            mapTemp.put("age", 18);
            mapTemp.put("sex", "男");
            //添加文档数据
            list.add(new Document(map));
        }
        //添加多条数据
        sheet.insertMany(list);

        //关闭mongodb连接对象
        mongoDBUtil.closeDB();
    }


    public static void queryData() {
        //筛选条件对象
        BasicDBObject basicDBObject = new BasicDBObject();
        //第一条数据 和 全部数据
        Document tempOne = sheet.find(basicDBObject).first();
        FindIterable<Document> allDocument = sheet.find();
        //指定字段的数据（除了 _id 你不能在一个对象中同时指定 0 和 1）
        sheet.find().projection(new BasicDBObject("_id", 0));
        //指定条件查询
        basicDBObject.append("name", "zhangsan").append("age", 18);
        sheet.find(basicDBObject);
        //大于 等于 小于 查询
        List<Bson> list = new ArrayList<>();
        list.add(Filters.eq("name", "zhangsan"));
        list.add(Filters.lte("age", 18));
        Bson bson = Filters.and(list);
        sheet.find(bson);
        //模糊查询
        Pattern pattern = Pattern.compile("^.*王.*$", Pattern.CASE_INSENSITIVE);
        basicDBObject.put("userName", pattern);
        //获取指定集合中所有文档(数据)
        FindIterable<Document> documents = sheet.find(basicDBObject);
        //1.直接遍历
        for (Document document : documents) {
            System.out.println("document = " + document);
        }
        //2.迭代器遍历
        MongoCursor<Document> mongoCursor = documents.iterator();
        while (mongoCursor.hasNext()) {
            System.out.println("iterator.document = " + mongoCursor.next());
        }
        //关闭mongodb连接对象
        mongoDBUtil.closeDB();
    }

    public static void update() {
        //筛选条件对象
        BasicDBObject basicDBObject = new BasicDBObject();
        //筛选条件
        basicDBObject.put("name", "zhangsan");
        basicDBObject.put("age", "20");

        //筛选条件对象
        List<Bson> list = new ArrayList<>();
        list.add(Filters.eq("name", "zhangsan"));
        list.add(Filters.lte("sex", "男"));
        Bson filter = Filters.and(list);
        //修改对象
        Document document = new Document();
        //添加修改内容
        document.append("$set", new Document("sex", "男"));
        //使用 basicDBObject
        sheet.updateOne(basicDBObject, document);
        // filter 大于等于
        sheet.updateOne(filter, document);
        //修改多个
        sheet.updateMany(filter, document);
        //关闭mongodb连接对象
        mongoDBUtil.closeDB();
    }

    public static void delete() {
        //筛选条件对象
        BasicDBObject bson = new BasicDBObject();
        bson.put("name", "wangwu");
        bson.put("age", 25);
        //删除单条数据
        sheet.deleteOne(bson);
        //删除多条
        sheet.deleteMany(bson);
        //关闭mongodb连接对象
        mongoDBUtil.closeDB();
    }

    public static void initDB() {
        //获取工具类对象
        mongoDBUtil = MongoDBUtil.getInstance();
        //获取指定数据库对象
        MongoDatabase database = mongoDBUtil.getDatabase("feifei");
        //获取指定集合对象
        sheet = database.getCollection("test04");
    }


}
