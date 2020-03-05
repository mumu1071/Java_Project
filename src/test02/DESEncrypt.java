package test02;


import org.bouncycastle.util.encoders.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
import java.util.Arrays;

public class DESEncrypt {

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";


    /**
     * 加解密算法/工作模式/填充方式
     */
    private static final String ALGORITHM_STR = "DES/ECB/NoPadding";

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * 填充内容
     */
    private static final byte PADDING_V = 0x08;

    private static final int MODLENGTH = 8;

    /**
     * 加密模式
     *
     * @param date 数据
     * @param key  密钥16位 HEX字符串
     * @return
     */
    public static byte[] encrypt(String date, String key) throws Exception {
        try {
            return encrypt(convertDate(date), ByteProtocolUtils.hexStrToBytes(key));
        } catch (Exception e) {
            throw e;
        }
    }


    /**
     * 解密数据
     *
     * @param data 数据
     * @param key  密钥16位 HEX字符串
     * @return
     */
    public static byte[] decrypt(byte[] data, String key) throws Exception {
        try {
            byte[] bytes = decrypt(data, ByteProtocolUtils.hexStrToBytes(key));
            return restoreDate(bytes);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 处理明文数据补位为8的倍数,补位规则为
     * int m = mod(密文长度)
     * 如果m==0则补8位0x08
     * 如果m!=0则补足8-m位的0-m
     * 如 01 01 01 01  补位结果为 01 01 01 01  04  04  04  04
     *
     * @param date
     * @return
     */
    private static byte[] convertDate(String date) throws Exception {
        try {
            if (date == null)
                throw new Exception("明文数据不能为NULL");

            byte[] bytes = date.getBytes(CHARSET_NAME);
            int bytesLength = bytes.length;
            int mod = bytesLength % MODLENGTH;
            int fillLength = MODLENGTH - mod;
            bytes = Arrays.copyOf(bytes, bytes.length + fillLength);
            for (int i = bytesLength; i < bytes.length; i++) {
                bytes[i] = (byte) fillLength;
            }
            return bytes;
        } catch (Exception e) {
            throw new Exception("转换数据失败" + e);
        }
    }


    /**
     * 处理密文数据移除补位补位规则为
     * int m = mod(密文长度)
     * 如果m==0则补8位0x08
     * 如果m!=0则补足8-m位的0-m
     * 如 01 01 01 01  补位结果为 01 01 01 01  04  04  04  04
     *
     * @param date
     * @return
     */
    private static byte[] restoreDate(byte[] date) throws Exception {
        try {
            if (date == null) {
                throw new Exception("密文数据不能为NULL");
            }
            if (date.length % MODLENGTH != 0) {
                throw new Exception("密文数据长度补位8的倍数");
            }
            int fillLength = date[date.length - 1];
            return Arrays.copyOf(date, date.length - fillLength);
        } catch (Exception e) {
            throw new Exception("转换数据失败" + e);
        }
    }

    /**
     * 加密函数 ECB模式 不需要VI
     *
     * @param data 加密数据
     * @param key  密钥
     * @return 返回加密后的数据
     */
    private static byte[] encrypt(byte[] data, byte[] key) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // 从原始密钥数据创建DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);
            // 执行加密操作
            byte encryptedData[] = cipher.doFinal(data);
            return encryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，加密数据出错!");
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 解密函数
     *
     * @param data 解密数据
     * @param key  密钥
     * @return 返回解密后的数据
     */
    private static byte[] decrypt(byte[] data, byte[] key) {
        try {
            // DES算法要求有一个可信任的随机数源
            SecureRandom sr = new SecureRandom();
            // byte rawKeyData[] = /* 用某种方法获取原始密匙数据 */;
            // 从原始密匙数据创建一个DESKeySpec对象
            DESKeySpec dks = new DESKeySpec(key);
            // 创建一个密匙工厂，然后用它把DESKeySpec对象转换成
            // 一个SecretKey对象
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
            SecretKey secretKey = keyFactory.generateSecret(dks);
            // using DES in ECB mode
            Cipher cipher = Cipher.getInstance(ALGORITHM_STR);
            // 用密匙初始化Cipher对象
            cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);
            // 正式执行解密操作
            byte decryptedData[] = cipher.doFinal(data);
            return decryptedData;
        } catch (Exception e) {
            System.err.println("DES算法，解密出错。");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 与前端交互关键数据3DES加密
     *
     * @param data 明文字符串
     * @param key  3DES密钥
     * @return
     */
    public static String encryptByDES3(String data, String key) {
        try {
            byte[] returnJsonByte = DES3Utils.des3EncodeECB(data.getBytes("UTF-8"), key);
            return Base64.toBase64String(returnJsonByte);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * 与前端交互关键数据3DES解密
     *
     * @param data 密文字符串
     * @param key  3DES密钥
     * @return
     */
    public static String decryptByDES3(String data, String key) throws Exception {
        try {
            //16进制的加密
            byte[] returnByte = DES3Utils.ees3DecodeECB(Base64.decode(data), key);
            return new String(returnByte, "utf-8");
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static void main(String[] args) throws Exception {
        String a = DESEncrypt.decryptByDES3("4Y3q5PXQpFc=", "CF78PS6F7PS2TCOMBKMAE9ZZ");
        System.out.println(a);
//        String str = "123456781231111111111111";
//        String key = "636172646E616E6E";
//        byte [] strs = convertDate(str);
//        System.out.println("str = " + ByteProtocolUtils.bytesToHexStr(strs));
//        String en = ByteProtocolUtils.bytesToHexStr(encrypt(strs,ByteProtocolUtils.hexStrToBytes(key))).toUpperCase();
//        System.out.println("en = " + en);
//        byte [] ymw = decrypt(ByteProtocolUtils.hexStrToBytes(en),ByteProtocolUtils.hexStrToBytes(key));
//        System.out.println(ByteProtocolUtils.bytesToHexStr(ymw));
//        System.out.println(ByteProtocolUtils.bytesToHexStr(restoreDate(ymw)));
//        byte b = 8;
//        System.out.println("ByteProtocolUtils.byteToHexStr(b) = " + ByteProtocolUtils.byteToHexStr(b));
//        int c = b;
//        System.out.println("c = " + c);
//        byte [] dzwjmw = ByteProtocolUtils.convertFileToBytes("F://IN_45010000400020170731000001.txt");
//        System.out.println("ByteProtocolUtils.bytesToHexStr(dzwjmw) = " + ByteProtocolUtils.bytesToHexStr(dzwjmw));
//        byte [] dzwjjm = decrypt(dzwjmw,key);
//        System.out.println("ByteProtocolUtils.bytesToHexStr(dzwjjm) = " + ByteProtocolUtils.bytesToHexStr(dzwjjm));
//        System.out.println(new String(dzwjjm));
    }
}
