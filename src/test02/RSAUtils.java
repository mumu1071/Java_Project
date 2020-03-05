package test02;

import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.HashMap;


/**
 * RSA算法 公钥加密 私钥解密
 */ 
public class RSAUtils { 
	/** 指定加密算法为RSA */
	private static final String ALGORITHM = "RSA";
	/** 密钥长度，用来初始化 */
	private static final int KEYSIZE = 1024;
	/** 指定公钥存放文件 */
	private static String PUBLIC_KEY_FILE = "rsa_public_key.pem";
	/** 指定私钥存放文件 */
	private static String PRIVATE_KEY_FILE = "pkcs8_rsa_private_key.pem";
//	//默认RSA算法公钥
	public static final String DEFAULT_PUBLIC_KEY= 
	"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyMNBGGg1c7ZuV2Np5BGm/zGeq" + "\r" +
	"o/JGSBYgZE9Nr5pN6NOcQQMP2JkiC01bKhTtlTwZCwIdwuiGL03oZ6rChWHYEzvL" + "\r" +
	"LFPIUPNe+fszL6J1qBeNTmUm8Zc3S7D/ac3huTXw5s6QI6w2tz5y0ikHSa3+nBbr" + "\r" +
	"yX+NQ8/KgLKuKB+IRQIDAQAB" + "\r" ;
	
	
//	"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyMNBGGg1c7ZuV2Np5BGm/zGeq" + "\r" +
//	"o/JGSBYgZE9Nr5pN6NOcQQMP2JkiC01bKhTtlTwZCwIdwuiGL03oZ6rChWHYEzvL" + "\r" +
//	"LFPIUPNe+fszL6J1qBeNTmUm8Zc3S7D/ac3huTXw5s6QI6w2tz5y0ikHSa3+nBbr" + "\r" +
//	"yX+NQ8/KgLKuKB+IRQIDAQAB" + "\r" ;
	//默认RSA算法私钥
	public static final String DEFAULT_PRIVATE_KEY=
//		"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALIw0EYaDVztm5XY" + "\r" +
//		"2nkEab/MZ6qj8kZIFiBkT02vmk3o05xBAw/YmSILTVsqFO2VPBkLAh3C6IYvTehn" + "\r" +
//		"qsKFYdgTO8ssU8hQ8175+zMvonWoF41OZSbxlzdLsP9pzeG5NfDmzpAjrDa3PnLS" + "\r" +
//		"KQdJrf6cFuvJf41Dz8qAsq4oH4hFAgMBAAECgYBPQydiB3SqQYdTwIJuEmh3yJTU" + "\r" +
//		"ctjyKl6tSfF8vQAGZ+q5NrIF+nMjkaN3SeDFTfzNpvfqBSkphyFAn9x1ap96n7Vz" + "\r" +
//		"r9viHD4iEvYlA/73A1TPld1csY7EDhuhD84fKNREyHuo7Srj76SS16TgsX/XBn5N" + "\r" +
//		"uN4AxvwRl+i2kJVCgQJBAOMOzVgD8AREuceIINmuLyT3zvCdQ5K+p4ZtjS8PZn0+" + "\r" +
//		"EE3bO+HkXJOtHZzeJxLjnLQ+D2Jb+Zde5ZupPnj8rDcCQQDI52lo1FLjlRaTL2sv" + "\r" +
//		"s6/e6Q/D9GfgSnY54qVF2eORgXPgCsf0nCU3cdaBhNTG8aYirAC7DtaCmSTKHCMG" + "\r" +
//		"/QljAkA19tanDIiG+y6MVbY5BkT+LEdquHXO8rEuKetcO/TH33BQkMJytaurDYH0" + "\r" +
//		"Pnha7O3ZiMZvQhzX9R3tGRRX/PWRAkBQRfaeRkggWw+8G7ROSRO6k5ETEPL4cYgv" + "\r" +
//		"tNmowVjmaL0uBeDDYiiUsM2uzq5eF2v0apmYJGOdpydGAlwUjMJVAkBwCURD0wvT" + "\r" +
//		"wG5kPTBehWvNaRVK9kZFplEZNflOiZ2LOx18zY22SquqAhWhw2rNjP97zJMa3kjs" + "\r" +
//		"lYDg+c1FSNTn" + "\r" ;
		"MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBALIw0EYaDVztm5XY" + "\r" +
		"2nkEab/MZ6qj8kZIFiBkT02vmk3o05xBAw/YmSILTVsqFO2VPBkLAh3C6IYvTehn" + "\r" +
		"qsKFYdgTO8ssU8hQ8175+zMvonWoF41OZSbxlzdLsP9pzeG5NfDmzpAjrDa3PnLS" + "\r" +
		"KQdJrf6cFuvJf41Dz8qAsq4oH4hFAgMBAAECgYBPQydiB3SqQYdTwIJuEmh3yJTU" + "\r" +
		"ctjyKl6tSfF8vQAGZ+q5NrIF+nMjkaN3SeDFTfzNpvfqBSkphyFAn9x1ap96n7Vz" + "\r" +
		"r9viHD4iEvYlA/73A1TPld1csY7EDhuhD84fKNREyHuo7Srj76SS16TgsX/XBn5N" + "\r" +
		"uN4AxvwRl+i2kJVCgQJBAOMOzVgD8AREuceIINmuLyT3zvCdQ5K+p4ZtjS8PZn0+" + "\r" +
		"EE3bO+HkXJOtHZzeJxLjnLQ+D2Jb+Zde5ZupPnj8rDcCQQDI52lo1FLjlRaTL2sv" + "\r" +
		"s6/e6Q/D9GfgSnY54qVF2eORgXPgCsf0nCU3cdaBhNTG8aYirAC7DtaCmSTKHCMG" + "\r" +
		"/QljAkA19tanDIiG+y6MVbY5BkT+LEdquHXO8rEuKetcO/TH33BQkMJytaurDYH0" + "\r" +
		"Pnha7O3ZiMZvQhzX9R3tGRRX/PWRAkBQRfaeRkggWw+8G7ROSRO6k5ETEPL4cYgv" + "\r" +
		"tNmowVjmaL0uBeDDYiiUsM2uzq5eF2v0apmYJGOdpydGAlwUjMJVAkBwCURD0wvT" + "\r" +
		"wG5kPTBehWvNaRVK9kZFplEZNflOiZ2LOx18zY22SquqAhWhw2rNjP97zJMa3kjs" + "\r" +
		"lYDg+c1FSNTn" + "\r" ;
    /** 
     * RSA最大加密明文大小 
     */  
    private static final int MAX_ENCRYPT_BLOCK = 117;  
      
    /** 
     * RSA最大解密密文大小 
     */  
    private static final int MAX_DECRYPT_BLOCK = 128; 
	/**
	 * 生成密钥对放入文件中
	 * @throws Exception
	 */
	public static void generateKeyPair() throws Exception {
		// /** RSA算法要求有一个可信任的随机数源 */
		// SecureRandom secureRandom = new SecureRandom();

		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");

		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		// keyPairGenerator.initialize(KEYSIZE, secureRandom);
		keyPairGenerator.initialize(KEYSIZE);

		/** 生成密匙对 */
		KeyPair keyPair = keyPairGenerator.generateKeyPair();

		/** 得到公钥 */
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

		/** 得到私钥 */
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

		ObjectOutputStream out1 = null;
		ObjectOutputStream out2 = null;
		try {
			/** 用对象流将生成的密钥写入文件 */
			out1 = new ObjectOutputStream(new FileOutputStream(PUBLIC_KEY_FILE));
			out2 = new ObjectOutputStream(new FileOutputStream(PRIVATE_KEY_FILE));
			out1.writeObject(publicKey);
			out2.writeObject(privateKey);
			System.out.println(publicKey.getModulus());
			System.out.println(publicKey.getPublicExponent());
			System.out.println(privateKey.getModulus());
			System.out.println(privateKey.getPrivateExponent());
		} catch (Exception e) {
			throw e;
		} finally {
			/** 清空缓存，关闭文件输出流 */
			if (out1 != null) {
				out1.close();
			}
			if (out2 != null) {
				out2.close();
			}
		}
	}

	/**
	 * 生成公钥和私钥
	 * @throws NoSuchAlgorithmException
	 */
	public static HashMap<String, Object> getKeys() throws NoSuchAlgorithmException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM);
		keyPairGen.initialize(KEYSIZE);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();// 公钥

		publicKey.getPublicExponent();
		publicKey.getModulus();

		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		map.put("public", publicKey);
		map.put("private", privateKey);
		return map;
	}

	/**
	 * 使用模和指数生成RSA公钥
	 * @return
	 */
	public static RSAPublicKey getPublicKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			RSAPublicKeySpec keySpec = new RSAPublicKeySpec(b1, b2);
			return (RSAPublicKey) keyFactory.generatePublic(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 使用模和指数生成RSA私钥
	 * @return
	 */
	public static RSAPrivateKey getPrivateKey(String modulus, String exponent) {
		try {
			BigInteger b1 = new BigInteger(modulus);
			BigInteger b2 = new BigInteger(exponent);
			KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
			RSAPrivateKeySpec keySpec = new RSAPrivateKeySpec(b1, b2);
			return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 通过文件获取私钥
	 * @return
	 * @throws IOException
	 */
	public static RSAPrivateKey getPrivateKeyByFile(String uri) throws IOException {
		ObjectInputStream ois = null;
		try {
			String url = uri + PRIVATE_KEY_FILE;
			ois = new ObjectInputStream(new FileInputStream(url));
			RSAPrivateKey privateKey = (RSAPrivateKey) ois.readObject();
			return privateKey;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	/**
	 * 通过文件获取私钥
	 * @return
	 * @throws IOException
	 */
	public static RSAPublicKey getPublicKeyByFile(String uri) throws IOException {
		ObjectInputStream ois = null;
		try {
			String url = uri + "/" + PUBLIC_KEY_FILE;
			ois = new ObjectInputStream(new FileInputStream(url));
			RSAPublicKey RSAPublicKey = (RSAPublicKey) ois.readObject();
			return RSAPublicKey;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (ois != null) {
				ois.close();
			}
		}
	}

	/**
	 * 公钥加密
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPublicKey(String data, RSAPublicKey publicKey) throws Exception {
		// 对数据加密  
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		
        int inputLen = data.getBytes().length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data.getBytes(), offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data.getBytes(), offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();
		BASE64Encoder base64Encoder = new BASE64Encoder();
        String mi = base64Encoder.encode(encryptedData);
		return mi;
	}

	/**
	 * 把字节数组转换成16进制字符串
	 * @param bArray
	 * @return
	 */
	private static final String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	/**
	 * 私钥解密
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPrivateKey(byte[] data,RSAPrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		int inputLen = data.length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段解密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {  
                cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data, offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_DECRYPT_BLOCK;  
        }  
        byte[] decryptedData = out.toByteArray();  
        out.close();
		String mi = new String(decryptedData);
		return mi;
	}

	/**
	 * 把16进制字符串转换成byte数组
	 * @param bArray
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase().replace(" ", "");
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}




	/**
	 * 私钥加密
	 * 
	 * @param data
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String encryptByPrivateKey(String data, RSAPrivateKey privateKey)
			throws Exception {
		// 对数据加密  
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, privateKey);
		
        int inputLen = data.getBytes().length;  
        ByteArrayOutputStream out = new ByteArrayOutputStream();  
        int offSet = 0;  
        byte[] cache;  
        int i = 0;  
        // 对数据分段加密  
        while (inputLen - offSet > 0) {  
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {  
                cache = cipher.doFinal(data.getBytes(), offSet, MAX_ENCRYPT_BLOCK);  
            } else {  
                cache = cipher.doFinal(data.getBytes(), offSet, inputLen - offSet);  
            }  
            out.write(cache, 0, cache.length);  
            i++;  
            offSet = i * MAX_ENCRYPT_BLOCK;  
        }  
        byte[] encryptedData = out.toByteArray();  
        out.close();
		String mi = bytesToHexString(encryptedData);
//		BASE64Encoder base64Encoder = new BASE64Encoder();
//        String mi = base64Encoder.encode(encryptedData);

		return mi;
	}
}