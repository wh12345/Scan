package com.qiyan.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES {

	private static String sKey = "feab990bda3e40e762c6cc392059ea5f";// "antonline.cn/xzx";

	public static String Encrypt(String sSrc) throws Exception {

		if (sKey == null) {
			System.out.print("Key为空null");
			return null;
		}

		byte[] raw = convertHexString(sKey);// sKey.getBytes("utf-8");
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");

		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");// "算法/模式/补码方式"

		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		// sun.misc.BASE64Encoder en = new sun.misc.BASE64Encoder();
		return toHexString(encrypted);// en.encode(encrypted);//new
										// Base64()encodeToString(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
	}

	public static String Decrypt(String sSrc) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空null");
				return null;
			}
			// 判断Key是否为16位
			// if (sKey.length() != 16) {
			// System.out.print("Key长度不是16位");
			// return null;
			// }
			byte[] raw = convertHexString(sKey);// sKey.getBytes("utf-8");

			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NOPADDING");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);

			// sun.misc.BASE64Decoder de = new sun.misc.BASE64Decoder();
			byte[] encrypted1 = convertHexString(sSrc);// de.decodeBuffer(sSrc);//new
														// Base64().decode(sSrc);//先用base64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);

				String originalString = new String(original, "UTF-8");
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return e.getMessage();
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return ex.getMessage();
		}
	}

	private static byte[] convertHexString(String ss) throws Exception {

		byte digest[] = new byte[ss.length() / 2];

		for (int i = 0; i < digest.length; i++)

		{

			String byteString = ss.substring(2 * i, 2 * i + 2);

			int byteValue = Integer.parseInt(byteString, 16);

			digest[i] = (byte) byteValue;

		}

		return digest;

	}

	private static String toHexString(byte b[]) throws Exception {

		StringBuffer hexString = new StringBuffer();

		for (int i = 0; i < b.length; i++) {

			String plainText = Integer.toHexString(0xff & b[i]);

			if (plainText.length() < 2)

				plainText = "0" + plainText;

			hexString.append(plainText);
		}
		return hexString.toString();
	}

}
