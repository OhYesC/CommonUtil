package com.ljzforum.platform.util;



import java.security.MessageDigest;


/**
 * @author jinhua.li
 * @2014年9月28日  上午11:56:36
 */
public class MD5Util {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**  
	 * encode  
	 *   
	 * @param originString  
	 * @return  
	 */
	public static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				MessageDigest md = MessageDigest.getInstance("MD5");
		
				byte[] results = md.digest(originString.getBytes());
				String resultString = byteArrayToHexString(results);
				return resultString;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}

	/**  
	 * change the Byte[] to hex string  
	 *   
	 * @param b  
	 * @return  
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**  
	 * change a byte to hex string  
	 *   
	 * @param b  
	 * @return  
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static void main(String[] args) {
//		MD5Util md5Util = new MD5Util();
//		String test = "123456";
//		System.out.println(md5Util.encodeByMD5(test));

	}

}
