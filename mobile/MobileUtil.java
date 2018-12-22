package cn.itcast.common.util.mobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号码处理工具类
 */
public class MobileUtil {
	
	/**  
     * 手机号验证  
     * @param  str  
     * @return 验证通过返回true  
     */  
    public static boolean isMobile(String str) {
    	boolean b = false;
    	if(str==null || "".equals(str)){
    		return b;
    	}
        Pattern p = null;   
        Matcher m = null;   
        p = Pattern.compile("^[1][3,4,5,8,7][0-9]{9}$"); // 验证手机号   
        m = p.matcher(str);   
        b = m.matches();    
        return b;   
    } 
    
//    public static void main(String[] args) {
//		String str = "";
//		System.out.println(isMobile(str));
//		
//		str = null;
//		System.out.println(isMobile(str));
//		
//		str = "13718730090";
//		System.out.println(isMobile(str));
//		
//		str = "1371873009";
//		System.out.println(isMobile(str));
//		
//		str = "17601559692";
//		System.out.println(isMobile(str));
//	}
    

}
