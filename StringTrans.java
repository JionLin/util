package cn.itcast.common.util;


public class StringTrans {

	public static String GBK2Unicode(String dataStr) {
		StringBuffer buffer = new StringBuffer();
		if(null == dataStr) return "";
		for (int i = 0; i < dataStr.length(); i++) {
			buffer.append("&#x");
			char c = dataStr.charAt(i);
			int t = c;

			String last = Integer.toHexString(t / (16 * 16)).toUpperCase();
			if (last.length() < 2) {
				buffer.append("0" + last);
			} else {
				buffer.append(last);
			}
			String frist = Integer.toHexString(t % (16 * 16)).toUpperCase();
			if (frist.length() < 2) {
				buffer.append("0" + frist);
			} else {
				buffer.append(frist);
			}
			buffer.append(";");
		}
		return buffer.toString();
	}
	
	public static void main(String [] a){
		System.out.println(GBK2Unicode(""));
	}

}
