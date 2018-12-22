/**
 * @fileName RandomUtil.java
 */
package cn.itcast.common.util;

import java.math.BigInteger;

public class DigitConvertUtil {
	
	final static char[] digits = {
    	'0' , '1' , '2' , '3' , '4' , '5' ,
    	'6' , '7' , '8' , '9' , 'a' , 'b' ,
    	'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
    	'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
    	'o' , 'p' , 'q' , 'r' , 's' , 't' ,
    	'u' , 'v' , 'w' , 'x' , 'y' , 'z'
        };

	/**
	 * 将十进制数字转换为对应进制的数字
	 * @param number  原始数字
	 * @param digit   位数，2进制、8进制、16进制........
	 * @return
	 */
	public static String convertToSpecifyDigit(long number, int digit) {
		BigInteger num = BigInteger.valueOf(number);
		StringBuffer sb = new StringBuffer();
		if (num.shortValue() == 0) {
			return "";
		} 
		BigInteger valueOf = BigInteger.valueOf(digit);
		BigInteger a = num.divide(valueOf);
		String str = convertToSpecifyDigit(a.longValue(), digit);
		sb.append(str);
		short index = num.mod(valueOf).shortValue();
		return sb.append(digits[index]).toString();
	}
	
//	public static void main(String[] args) {
//		System.out.println(convertToSpecifyDigit(1234567l,36));
//	}

}
