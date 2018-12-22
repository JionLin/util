/**
 * @fileName RandomUtil.java
 */

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomUtil {
	private static final SecureRandom sr = new SecureRandom();

	/**
	 * 
	 * <P> 得到随机字符</P>
	 * @param n
	 * @return
	 */
	public static String randomStr(int n) {
		String str1 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		String str2 = "";
		int len = str1.length() - 1;
		double r;
		for (int i = 0; i < n; i++) {
			r = (Math.random()) * len;
			str2 = str2 + str1.charAt((int) r);
		}
		return str2;
	}

	/**
	 * 
	 * <P>得到0～fan之间的随机n个数，用/隔开</P>
	 * @param fan
	 * @param n
	 * @return
	 */
	public static String randomStr1(int fan, int n) {
		String str1 = "/";
		String temp = "";
		int value_nul = 0;
		int no_value = 0;
		double r;
		while(value_nul < n) {
			r = (Math.random()) * fan;
			temp = String.valueOf((int) r) + "/";
			if (str1.indexOf("/" + temp) < 0) {
				str1 = str1 + temp;
				value_nul++;
			} else {
				no_value++;
			}
			if (no_value > 15 || value_nul > n) { // 15��ʾ��Ч����
				break;
			}
		}
		return str1;
	}
	
	/**
	 * 
	 * 产生任意给定长度的随纯机数字字符串。
	 * <p>
	 * </p>
	 * 
	 * @param iLength
	 *            产生随机数的位数
	 * @return 给定位数的随机数
	 */
	public static String getRandomIntNum(int iLength) {
		sr.setSeed(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < iLength; i++) {
			sb.append(Math.abs(sr.nextInt(10)));
		}
		return sb.toString();
	}

	public static int getRandomIntNumByMax(int max) {
		sr.setSeed(System.currentTimeMillis());
		return sr.nextInt(max);
	}
	
	/**
	 * 随机产生不同的随机数
	 * @param max(产生随机数的最大值>0)
	 * @param min(产生随机数的最小值>=0)
	 * @param counter(产生随机数的个数)
	 * @return
	 */
	public  int[] getRandom(int max,int min,int counter){
		int[]  result=new int[counter];
		ArrayList<Integer> al=new ArrayList<Integer>();
		for(int i=min;i<max;i++){
			al.add(i);
		}
		Random r=new Random();
		int size=al.size();
		for(int j=0;j<counter;j++){
			int index=r.nextInt(size-j);
			Object o=al.get(index);
			result[j]=((Integer)o).intValue();
			al.remove(o);
		}
		return result;
	}
	
	/**
	 * add by lipp
	 * <P>得到指定数目的不重复的随机数</P>
	 * @param seed 种子
	 * @param num 生成随机数目
	 * @return
	 */
	public static List<Integer> getNoRepeatRandom(int seed,int num){
		
		List<Integer> randomSet = new ArrayList<Integer>();
		Random random = new Random();
		boolean[] bool = new boolean[seed];  
		int randomNum = 0;
		for(int i=0;i<num;i++){
			do{
				randomNum = random.nextInt(seed);
			}while(bool[randomNum]);
			bool[randomNum] = true;
			randomSet.add(randomNum);
			if(randomSet.size()==num){
				break;
			}
		}
		return randomSet;
	}
	
	/**
	 * 随机生成指定长度指定范围的随机串
	 * @param length 表示生成字符串的长度
	 * @return
	 */
	public static String getRandomString(int length) { 
	    String base = "abcdefghijklmnopqrstuvwxyz0123456789";     
	    int seed = base.length();
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(seed);     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }   
	
	/**
	 * 将指定十进制转换成指定单位长度的36进制数；若转换后的数字长度小于指定长度，使用数字0进行前置填充；
	 * @param number 十进制数
	 * @param length 结果的字符串长度
	 * @return
	 */
	public static String get36DigitRandom(long number,int length) { 
		if(number > Math.pow(36,length)) return "";
		String target = cn.itcast.common.util.DigitConvertUtil.convertToSpecifyDigit(number, 36);
		int targetLen = target.length();
		StringBuffer sb = new StringBuffer();
		while(targetLen < length) {
			sb.append("0");
			targetLen++;
		}
		sb.append(target);
	    return sb.toString();     
	 }   
	
//	public static void main(String[] args) {
//		System.out.println(randomStr(4));
//		System.out.println(randomStr(4));
//		System.out.println(randomStr(4));
//		System.out.println(randomStr(4));
//		System.out.println(randomStr(4));
//		
//		System.out.println(get36DigitRandom(12000,4));
//	}

}
