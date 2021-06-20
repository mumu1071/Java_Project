package util;


import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取数据库对象工具类
 *
 * @author hellfs
 * 2020-08-06
 */
public class MongoDBUtil {
    /**
     * MongoDB连接对象
     */
    private MongoClient client;

    /**
     * MongoDBUtil对象
     */
    private static MongoDBUtil instance;

    /**
     * 获取MongoDBUtil对象
     *
     * @return MongoDBUtil
     */
    public static MongoDBUtil getInstance() {
        if (instance == null) {
            instance = new MongoDBUtil();
        }
        return instance;
    }

    /**
     * 关闭连接对象
     */
    public void closeDB() {
        if (client != null) {
            client.close();
        }
        client = null;
    }

    /**
     * 不需要认证获取连接对象
     */
    public void mongoClient() {
        try {
            //获取mongodb连接对象
//            client = new MongoClient("47.99.187.0", 27017);
            client = new MongoClient("127.0.0.1", 27017);
        } catch (Exception e) {
            System.out.println("不需要认证获取连接对象失败" + e.getMessage());
        }
    }

    /**
     * 需要认证获取连接对象
     */
    public void certifyMongoClient() {
        try {
            //连接到MongoDB服务 如果是远程连接可以替换“localhost”为服务器所在IP地址
            //ServerAddress()两个参数分别为 服务器地址 和 端口
            ServerAddress serverAddress = new ServerAddress("localhost", 27017);
            List<ServerAddress> addrs = new ArrayList<ServerAddress>();
            addrs.add(serverAddress);

            //MongoCredential.createScramSha1Credential()三个参数分别为 用户名 数据库名称 密码
            MongoCredential credential = MongoCredential.createScramSha1Credential("username", "databaseName", "password".toCharArray());
            List<MongoCredential> credentials = new ArrayList<MongoCredential>();
            credentials.add(credential);

            //通过连接认证获取MongoDB连接
            client = new MongoClient(addrs, credentials);
        } catch (Exception e) {
            System.out.println("不需要认证获取连接对象失败" + e.getMessage());
        }
    }

    /**
     * 获取数据库对象
     *
     * @param databaseName 数据库名
     * @return MongoDatabase
     */
    public MongoDatabase getDatabase(String databaseName) {
        if (client == null) {
            mongoClient();
        }
        MongoDatabase database = client.getDatabase(databaseName);
        return database;
    }
}
