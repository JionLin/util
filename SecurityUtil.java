package cn.itcast.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecurityUtil {

	public static String md5(String src) {
		String ret = null;

		if (src == null) {
			return null;
		}

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			byte[] bytes = md.digest();

			StringBuilder sb = new StringBuilder("");

			int value;
			for (int i = 0; i < bytes.length; i++) {
				value = bytes[i];
				if (value < 0) {
					value += 256;
				}
				if (value < 16) {
					sb.append("0");
				}
				sb.append(Integer.toHexString(value));
			}

			ret = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return ret;
	}

}
