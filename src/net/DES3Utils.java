package net;


import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.Key;

/**
 * 3DES算法
 */
public class DES3Utils {
	private static final String Algorithm = "DESede";//定义 加密算法

	/**
	 * 3DES加密
	 * @param data
	 * @param deskey 密钥
	 * @return
	 */
	public static byte[] des3EncodeECB(byte[] data, String key) throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(Algorithm);
		deskey = keyfactory.generateSecret(spec);

		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, deskey);
		
		byte[] bOut = cipher.doFinal(data);
		return bOut;
	}

	/**
	 * 3DES解密
	 * @param src
	 * @param deskey
	 * @return
	 */
	public static byte[] ees3DecodeECB(byte[] data, String key)
			throws Exception {
		Key deskey = null;
		DESedeKeySpec spec = new DESedeKeySpec(key.getBytes());
		SecretKeyFactory keyfactory = SecretKeyFactory.getInstance(Algorithm);
		deskey = keyfactory.generateSecret(spec);
		
		Cipher cipher = Cipher.getInstance(Algorithm);
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		
		byte[] bOut = cipher.doFinal(data);
		return bOut;

	}

	public static byte[] build3DesKey(String keyStr) throws UnsupportedEncodingException {
		byte[] key = new byte[24];
		byte[] temp = keyStr.getBytes("UTF-8");
		if (key.length > temp.length) {
			System.arraycopy(temp, 0, key, 0, temp.length);
		} else {
			System.arraycopy(temp, 0, key, 0, key.length);
		}
		return key;
	}

	/**
	 * 数组转16进制
	 * @param bts
	 * @return
	 */
	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	private static int parse(char c) {
		if (c >= 'a') {
			return (c - 'a' + 10) & 0x0f;
		}
		if (c >= 'A') {
			return (c - 'A' + 10) & 0x0f;
		}
		return (c - '0') & 0x0f;
	}

}