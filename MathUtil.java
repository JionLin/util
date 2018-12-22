/**
 * @fileName MathUtil.java
 * @description 
 * @author leo_soul 2013-4-15
 * @Copr. 
 */
package cn.itcast.common.util;

import java.text.DecimalFormat;

public class MathUtil {
	
	/**
	 * 
	 * <P> 将2进制数num2转换为10进制数</P>
	 * @param num2
	 * @return
	 */
	public static String to10b(String num2) {
		int temp = 0;
		for (int i = 0; i < num2.length(); i++) {
			temp = (temp * 2)
					+ Integer.parseInt(String.valueOf(num2.charAt(i)));
		}
		return String.valueOf(temp);
	}
	
	/**
	 * 
	 * <P>将10进制数num10转换为2进制数</P>
	 * @param num10
	 * @return
	 */
	public static String to2b(long num10) {
		long temp = num10;
		String result1 = "";
		while (temp > 0) {
			result1 = String.valueOf(temp % 2) + result1;
			temp = temp / 2;
		}
		return result1;
	}
	
	/**
	 * 
	 * <P>返回a/b的百分数</P>
	 * 舍弃小数位<br>
	 * @param a
	 * @param b
	 * @return
	 */
	public static String getDivide(int a, int b) {
		String res = "0";
		if (b != 0 && a != 0) {
			res = (a * 100 / b) + "%";
		}
		return res;
	}
	
	/**
	 * <P>得到两个整数除后保留两个小数位数</P>
	 * 使用默认的舍入模式：HALF_EVEN
	 * @param a 分子
	 * @param b 分母
	 * @return 如： 45%
	 */
	public static String getDivideFormat(int a, int b) {
		String res = "0";
		//格式化小数，不足的补0
		DecimalFormat df = new DecimalFormat("0");
		if (b != 0 && a != 0) {
			float result = (a*100/ (float)b);
			res = df.format(result) + "%";
		}
		return res;
	}
	
}
