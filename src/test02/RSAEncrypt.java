package test02;


import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by Administrator on 2016/12/17.
 */
public class RSAEncrypt {
    private static final String ALGORITHM = "RSA";
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    private static final String SIGNATURE_ALGORITHM = "SHA1WithRSA";
    private static BouncyCastleProvider bouncyCastleProvider = new BouncyCastleProvider();
    public static final String KEY_ALGORITHM = "RSA";
    private static final int MAX_ENCRYPT_BLOCK = 117;
    private static final int MAX_DECRYPT_BLOCK = 128;

    /**
     * 从字符串中加载公钥
     * @param publicKeyStr 公钥数据字符串
     * @throws Exception 加载公钥时产生的异常
     */
    public static RSAPublicKey loadPublicKey(String publicKeyStr) throws Exception{
        try {

            byte[] buffer= Base64.decode(publicKeyStr);
            KeyFactory keyFactory= KeyFactory.getInstance(ALGORITHM);
            X509EncodedKeySpec keySpec= new X509EncodedKeySpec(buffer);
            return (RSAPublicKey) keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        }  catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 根据公钥文件保存的16进制的 modulus和publicExponent反向生成公钥
     * rsaPublicKey.getModulus().toString(16) 获取16进制modulus串
     * rsaPublicKey.getPublicExponent().toString(16) 获取16进制publicExponent串
     * @param modulus  获取16进制modulus串
     * @param publicExponent 获取16进制publicExponent串
     * @return RSA公钥
     * @throws Exception
     */
    public static RSAPublicKey loadPublicKey(String modulus,String publicExponent) throws Exception{
        try {
            KeyFactory keyFactory= KeyFactory.getInstance(ALGORITHM);
            BigInteger n = new BigInteger(modulus,16);
            BigInteger e = new BigInteger(publicExponent,16);
            RSAPublicKeySpec spec = new RSAPublicKeySpec(n, e);
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        }  catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }


    /**
     * 从字符串中加载公钥
     * @param privateKeyStr 私钥数据字符串
     * @throws Exception 加载私钥时产生的异常
     */
    public static RSAPrivateKey loadPrivateKey(String privateKeyStr) throws Exception{
        try {
            KeyFactory keyFactory= KeyFactory.getInstance(ALGORITHM);
            byte[] buffer= Base64.decode(privateKeyStr);
            PKCS8EncodedKeySpec keySpec= new PKCS8EncodedKeySpec(buffer);
            return  (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        }  catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }


    /**
     * 公钥加密过程
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
        if(publicKey== null){
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance(CIPHER_ALGORITHM,bouncyCastleProvider);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] output= cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }


    /**
     * 私钥解密过程
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{
        if (privateKey== null){
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance(CIPHER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output= cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     * 公钥解密过程
     * @param publicKey 公钥
     * @param plainTextData 明文数据
     * @return
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] decrypt(RSAPublicKey publicKey, byte[] plainTextData) throws Exception{
        if(publicKey== null){
            throw new Exception("加密公钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance(CIPHER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] output= cipher.doFinal(plainTextData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此加密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("加密公钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("明文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("明文数据已损坏");
        }
    }

    /**
     * 私钥解密过程
     * @param privateKey 私钥
     * @param cipherData 密文数据
     * @return 明文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] encrypt(RSAPrivateKey privateKey, byte[] cipherData) throws Exception{
        if (privateKey== null){
            throw new Exception("解密私钥为空, 请设置");
        }
        Cipher cipher= null;
        try {
            cipher= Cipher.getInstance(CIPHER_ALGORITHM, bouncyCastleProvider);
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] output= cipher.doFinal(cipherData);
            return output;
        } catch (NoSuchAlgorithmException e) {
            throw new Exception("无此解密算法");
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            return null;
        }catch (InvalidKeyException e) {
            throw new Exception("解密私钥非法,请检查");
        } catch (IllegalBlockSizeException e) {
            throw new Exception("密文长度非法");
        } catch (BadPaddingException e) {
            throw new Exception("密文数据已损坏");
        }
    }


    /**
     *用私钥对信息生成数字签名
     * @param privateKey	//私钥
     * @param content  签名内容
     * @param charset 字符编码 默认为系统默认编码，建议约定为UTF-8
     * @return
     * @throws Exception
     */
    public static String sign(RSAPrivateKey privateKey,String content,String charset) throws SignatureException{
        try {
            if(privateKey==null){
               new  SignatureException("私钥不能为空");
            }
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            signature.initSign(privateKey);
            if (charset ==null || "".equals(charset.trim())) {
                signature.update(content.getBytes());
            } else {
                signature.update(content.getBytes(charset.trim()));
            }
            return new String(Base64.encode(signature.sign()));
        } catch (Exception e) {
            throw new SignatureException("RSAcontent = " + content + "; charset = " + charset, e);
        }
    }


    /**
     * 校验数字签名 签名信息为BASE串
     * @param content 加密数据
     * @param publicKey	公钥
     * @param sign	数字签名,签名为Base64处理后的签名
     * @param charset 字符编码 默认为系统默认编码，建议约定为UTF-8
     * @return
     * @throws Exception
     */
    public static boolean verify(PublicKey publicKey,String content,String sign,String charset)throws Exception{
        return verify(publicKey,content,Base64.decode(sign.getBytes()),charset);
    }

    /**
     * 校验数字签名
     * @param content 加密数据
     * @param publicKey	公钥
     * @param signBytes	数字签名的Byte数组
     * @param charset 字符编码 默认为系统默认编码，建议约定为UTF-8
     * @return
     * @throws Exception
     */
    public static boolean verify(PublicKey publicKey,String content,byte [] signBytes,String charset)throws Exception{
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        if (charset ==null || "".equals(charset.trim())) {
            signature.update(content.getBytes());
        } else {
            signature.update(content.getBytes(charset.trim()));
        }
        //验证签名是否正常
        return signature.verify(signBytes);
    }

    public static byte[] encryptByPublicKey(byte[] data, String publicKey)
            throws Exception {
        byte[] keyBytes = org.apache.commons.codec.binary.Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }
}
