package test02;


import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by 17232 on 2017/10/18.
 */
public class HttpRequest {

    private static final String DEFAULT_CHARSET = "UTF-8";

    // GET
    private static final String _GET = "GET";
    // POST
    private static final String _POST = "POST";
    public static final int DEF_CONN_TIMEOUT = 10000;
    public static final int DEF_READ_TIMEOUT = 10000;

    

    /**
     * 通用POST请求获取数据
     */
    public static String postByJson(String path, JSONObject para) {
        String object = "";
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept_Charset","UTF-8");
            connection.setRequestProperty("contentType","UTF-8");
            connection.connect();
            //发送POST请求参数
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            out.write(para.toJSONString());
            out.flush();
            out.close();

            //读取响应
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //读取响应
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                String lines;
                StringBuffer str = new StringBuffer();
                while ((lines = reader.readLine()) != null) {
                    lines = new String(lines.getBytes());
                    str.append(lines);
                }
                object = str.toString();
                reader.close();

            }else {
                return null;
            }
            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }

    private static HttpURLConnection initHttp(String url, String method, Map<String, String> headers) throws Exception {
        URL _url = new URL(url);
        HttpURLConnection http = (HttpURLConnection) _url.openConnection();
        // 连接超时
        http.setConnectTimeout(DEF_CONN_TIMEOUT);
        // 读取超时 --服务器响应比较慢，增大时间
        http.setReadTimeout(DEF_READ_TIMEOUT);
        http.setUseCaches(false);
        http.setRequestMethod(method);
        http.setRequestProperty("X-Requested-With", "XMLHttpRequest");
        http.setRequestProperty("accept", "application/json");
        http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        http.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.146 Safari/537.36");
        if (null != headers && !headers.isEmpty()) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                http.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        http.setDoOutput(true);
        http.setDoInput(true);
        http.connect();
        return http;
    }

    /**
     * post请求
     *
     * @param url
     * @param params
     * @return
     * @throws Exception
     */
    public static String post(String url, String params) throws Exception {
        HttpURLConnection http = initHttp(url, _POST, null);
        OutputStream out = http.getOutputStream();
        out.write(params.getBytes(DEFAULT_CHARSET));
        out.flush();
        out.close();

        InputStream in = http.getInputStream();
        BufferedReader read = new BufferedReader(new InputStreamReader(in, DEFAULT_CHARSET));
        String valueString = null;
        StringBuffer bufferRes = new StringBuffer();
        while ((valueString = read.readLine()) != null) {
            bufferRes.append(valueString);
        }
        in.close();
        if (http != null) {
            http.disconnect();// 关闭连接
        }
        return bufferRes.toString();
    }

}
