package com.qiyan.util;

/**
 * @author xiaozhongxi
 * 
 *         更改所生成类型注释的模板为 窗口 > 首选项 > Java > 代码生成 > 代码和注释
 * 
 *         MD5的算法在RFC1321 中定义 在RFC 1321中，给出了Test suite用来检验你的实现是否正确： MD5 ("") =
 *         d41d8cd98f00b204e9800998ecf8427e MD5 ("a") =
 *         0cc175b9c0f1b6a831c399e269772661 MD5 ("abc") =
 *         900150983cd24fb0d6963f7d28e17f72 MD5 ("message digest") =
 *         f96b697d7cb7938d525a2f31aaf161d0 MD5 ("abcdefghijklmnopqrstuvwxyz") =
 *         c3fcd3d76192e4007dfb496cca67e13b
 * 
 * @author haogj
 * 
 *         传入参数：一个字节数组 传出参数：字节数组的 MD5 结果字符串
 */
public class MD5 {
	public static String getMD5(byte[] source) {
		String s = null;
		char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
				'E', 'F' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest
					.getInstance("MD5");
			md.update(source);
			byte tmp[] = md.digest(); // MD5 的计算结果是一个 128 位的长整数，
			// 用字节表示就是 16 个字节
			char str[] = new char[16 * 2]; // 每个字节用 16 进制表示的话，使用两个字符，
			// 所以表示成 16 进制需要 32 个字符
			int k = 0; // 表示转换结果中对应的字符位置
			for (int i = 0; i < 16; i++) { // 从第一个字节开始，对 MD5 的每一个字节
				// 转换成 16 进制字符的转换
				byte byte0 = tmp[i]; // 取第 i 个字节
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; // 取字节中高 4 位的数字转换,
				// >>> 为逻辑右移，将符号位一起右移
				str[k++] = hexDigits[byte0 & 0xf]; // 取字节中低 4 位的数字转换
			}
			s = new String(str); // 换后的结果转换为字符串

		} catch (Exception e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void main(String[] args) {
		System.out.println(MD5.getMD5(("0302144020000000165423144020010031440291188001986201810161022079642581542DEBC43B6CF1AAA5F446919"
             +"C144285408F5B3C207B184715E5ACA8CA309224AF834B502F875CC1A90C7B1796B4ADD7AA13A3B20803A5809008A30B983996929D925C3959674540B2E73D7"
               +"EEBB951A8873DDCB86D2C77C16C76A69B07597D0010F2AD4D4F5D5465FF134AEEF7861FCE3AABDBDD6DE48798D4A597231200韶关交警测试1cs123456").getBytes()));
	}

}
