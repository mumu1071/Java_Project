package net;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.security.interfaces.RSAPublicKey;
import java.util.Iterator;
import java.util.Random;


public class Demo {


    private static String url = "http://120.79.62.142:8081/json/TravelNNServer";

    private static String rsaPublickey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ/DOV4sn6I83QBjAUXmfhD02FtPEepFhWpS1nwzR6WVzRmeYxsX58oVINIVek8SptF3TpVfUq28DCDSGyAuOkCmmPIOP3zjy8q6Rht2KN9fZyCTKdwrHPZaw/h3HHXbUOSkywQE3Y372DdRxXoZN/I/QHDSKRRO8z9ESfxOBrDQIDAQAB";


    private static String[] securityElements = new String[]{
            "login_name",//登录名
            "c_no",
            "refund_amt"
    };


    public static void main(String[] args) {
        test_WC08();
    }

    /**
     *
     */
    public static void test_WC08() {
        JSONObject json = new JSONObject();
        //02安卓03苹果
        json.put("trcode", "WC08");//交易代码

        json.put("tr_code", "700001");
        json.put("reason", "8");
        json.put("order_type", "MANGGUO");
        json.put("channel", "04");//渠道来源
        json.put("key", "reason,channel,tr_code,trcode,order_type");//渠道来源
        json.put("sign", "379525FA55057E24E792AED61F08617720C799FC");//渠道来源

        json.put("refund_amt", "1");
        json.put("order_id", "191126221500000261380");
        json.put("mangoOrderNo", "1");

        json = encryptElements(json);//CityPaySecurity.encryptElements(obj);//

        json = addSign(json);


        System.out.println(JSON.toJSON(json));
        System.out.println(HttpRequest.postByJson(url, json));
//        System.out.println(JSON.toJSON(json));
//        System.out.println(getDecKey());

    }


    /**
     * 添加key,value验证信息
     *
     * @param obj
     * @return
     */
    public static JSONObject addSign(JSONObject obj) {
        String md5key = "123456";
        if (obj.containsKey("key")) {
            obj.remove("key");
        }
        if (obj.containsKey("sign")) {
            obj.remove("sign");
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject = obj;
        int count = 0;
        Iterator ite = jsonObject.keySet().iterator();
        StringBuffer old_key = new StringBuffer();
        StringBuffer old_content = new StringBuffer();

        while (ite.hasNext()) {
            count++;
            String e = (String) ite.next();
            old_key.append(e + ",");
            old_content.append(jsonObject.get(e));
        }

        if (old_key.length() > 0) {
            old_key = new StringBuffer(old_key.substring(0, old_key.length() - 1));
        }
        if (old_key.length() > 0) {
            jsonObject.put("key", old_key);
        }

        if (old_content.length() > 0) {
            if (jsonObject.keySet().size() - 1 == count) {//crypt
                jsonObject.put("sign", org.zt.tools.SHA1.getDigestOfString(org.zt.tools.MD5.crypt(old_content.append(md5key).toString()).toUpperCase().getBytes()).toUpperCase());
            }
        }
        return jsonObject;
    }


    /**
     * 按照规则,针对关键字段进行加密
     *
     * @param json
     * @return
     */
    public static JSONObject encryptElements(JSONObject json) {
        try {
            if (json == null) {
                return json;
            }
            //3des密钥
            String decKey = createNumber(24).toUpperCase();

//            String resulttest = DESEncrypt.encryptByDES3(json.get("refund_amt").toString().trim(), decKey);
//            json.put("test", resulttest);



            for (int i = 0; i < securityElements.length; i++) {
                String key = securityElements[i];
                Object object = json.get(key);
                if (object != null && !"".equals(object)) {
                    String result = DESEncrypt.encryptByDES3(object.toString().trim(), decKey);
                    json.put(key, result);
                }
            }
            RSAPublicKey rsaPublicKey = RSAEncrypt.loadPublicKey(rsaPublickey);
            decKey = RSAUtils.encryptByPublicKey(decKey, rsaPublicKey);
            System.out.println(decKey);
            json.put("dec_key", decKey.replaceAll("\r|\n", ""));
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    //根据指定长度生成纯数字的随机数
    public static String createNumber(int length) {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(10));
        }
//        return sb.toString();
        return "568516217148393718284726";
    }
}
