package net;

import java.math.BigInteger;

/**
 * 字节控制协议处理工具类
 * @version 1.0
 */
public class ByteProtocolUtils {
	/**
     * int转为一位的byte类型
     * int为4个字节,byte为一个字节,转换的时候前置转换取低位8位字节,
     * 8位字节的最大数字为255,即该函数只支持0-255
     * @param iSource
     * @return
     */
    public static byte intToByte(int iSource) throws Exception{
        if (iSource > 255) {
            throw new Exception("大于255的数组转为一个字节会使得数字不正确");
        }
        return (byte) iSource;
    }

    /**
     * byte转为int类型
     * @param b
     * @return
     */
    public static int byteToInt(byte b) throws Exception{
        return b&0xff;
    }

    /**
     * int类型转为byte数组
     * @param iSource
     * @param iArrayLen
     * @return
     */
    public static byte[] intToByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        for (int i = iArrayLen-1; (i < 4) && (i >-1 ); i--) {
            bLocalArr[i] = (byte) (iSource >> 8 * (iArrayLen-i-1) & 0xFF);
        }
        return bLocalArr;
    }

    /**
     * Long类型转为byte数组
     * @param iSource
     * @param iArrayLen
     * @return
     */
    public static byte[] longToByteArray(Long iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        for (int i = iArrayLen-1; (i < 8) && (i >-1 ); i--) {
            bLocalArr[i] = (byte) (iSource >> 8 * (iArrayLen-i-1) & 0xFF);
        }
        return bLocalArr;
    }
    
    /**
     * byte数组转换为int
     * @param bRefArr
     * @return
     */
    public static int byteArrayToInt(byte[] bRefArr) {
        int iOutcome = 0;
        byte bLoop;
        for (int i = bRefArr.length-1; i >-1 ; i--) {
            bLoop = bRefArr[i];
            iOutcome += (bLoop & 0xFF) << (8 * (bRefArr.length-i-1));
        }
        return iOutcome;
    }
    
    /**
     * byte数组转换为Long
     * @param bRefArr
     * @return
     */
    public static Long byteArrayToLong(byte[] bRefArr) {
        Long iOutcome = 0L;
        byte bLoop;
        for (int i = bRefArr.length-1; i >-1 ; i--) {
            bLoop = bRefArr[i];
            iOutcome += ((long)bLoop & 0xFF) << (8 * (bRefArr.length-i-1));
        }
        return iOutcome;
    }
    
    /**
     * 所有接受字节数组转换为String
     * @param tBytes
     * @param charset 字符编码
     * @return
     * @throws Exception
     */
    public static String byteArrayToStr(byte [] tBytes,String charset) throws Exception{
        if (tBytes == null || tBytes.length < 1) {
            throw new Exception("字节数组为空");
        }
        return new String(tBytes,charset);
    }

    /**
     * 字符串转换为指定字符编码的字节数组
     * 所以该方法支持包含中文的字符串转换成字节数组
     * @param str
     * @param charset 字符编码
     * @return
     * @throws Exception
     */
    public static byte [] strToByteArray(String str,String charset) throws Exception{
        if (str == null || str.length() < 0) {
            throw new Exception("字符串不能为空");
        }
        return str.getBytes(charset);
    }

    /**
     * 对数组进行前补0，即对前面添加0x30
     * @param original 原来数组,数组可为空
     * @param length 目标数组的长度,该值的长度不能大于元数组
     * @return
     */
    public static byte[] padLeftZero(byte[] original,int length)throws Exception{
        if (length < 1) {
            throw new Exception("目标字节数组长度不能为空");
        }
        byte [] target = new byte[length];
        int originalLength = (original == null ? 0 : original.length);
        if (length-originalLength < 0) {
            throw new Exception("原数组长度过长");
        }
        for (int i = 0; i < length; i++) {
            if (i < originalLength) {
                target[i] = 0x30;
            } else {
                target[i] = original[i-originalLength];
            }
        }
        return target;
    }

    /**
     * 对数组进行右补空格，即对前面添加0X00
     * @param original 原来数组,数组可为空
     * @param length 目标数组的长度,该值的长度不能大于元数组
     * @return
     */
    public static byte[] padRightBlank(byte[] original,int length)throws Exception{
        if (length < 1) {
            throw new Exception("目标字节数组长度不能为空");
        }
        byte [] target = new byte[length];
        int originalLength = (original == null ? 0 : original.length);
        if (length-originalLength < 0) {
            throw new Exception("原数组长度过长");
        }
        for (int i = 0; i < length; i++) {
            if (i < originalLength) {
                target[i] = original[i];
            } else {
                target[i] = 0x20;
            }
        }
        return target;
    }
    
    /**
     * Convert byte[] to hex string.这里我们可以将byte转换成int，
     * 然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte[] data
     * @return hex string
     */   
    public static String bytesToHexStr(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }
    /**
     * Convert byte to hex string.这里我们可以将byte转换成int，
     * 然后利用Integer.toHexString(int)来转换成16进制字符串。
     * @param src byte data
     * @return hex string
     */ 
    public static String byteToHexStr(byte src){
        int v = src & 0xFF;
        String hv = Integer.toHexString(v);
        if (hv.length() < 2) {
           return "0"+hv;
        }
        return hv;
    }
    
    /**
     * Convert hex string to byte[]
     * @param hexStr the hex string
     * @return byte[]
     */
    public static byte[] hexStrToBytes(String hexStr) {
        if (hexStr == null || "".equals(hexStr)) {
            return null;
        }
        hexStr = hexStr.toUpperCase();
        int length = hexStr.length() / 2;
        char[] hexChars = hexStr.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     * @param c char
     * @return byte
     */
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
     
    public static String binary(byte[] bytes, int radix){  
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
    } 
     
    public static String byteToBinaryStr(byte b){  
         String result ="";  
         byte a = b; ;  
         for (int i = 0; i < 8; i++){  
             result = (a % 2) + result;  
             a = (byte) (a/2);  
         }  
         return result;  
    }
}