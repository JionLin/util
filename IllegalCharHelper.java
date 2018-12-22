package cn.itcast.common.util;

/**
 * 文件名：IllegalCharHelper.java 2012-4-10
 * 描述：&用于处理非法字符
 */

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

public class IllegalCharHelper {
	/**配置文件路径*/
	private static final String CONFIGPATH = "/configs/config/IllegalCharReplace.properties";
	private static Properties ps = new Properties();

	static {
		try {
			InputStream in = IllegalCharHelper.class.getResourceAsStream(CONFIGPATH);
			ps.load(in);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//非法字符缓存
	private static Map<String, String> charCacheMap = new HashMap<String, String>();
	/**
	 * 替换非法字符的方法。
	 * 参数param可能为null或n空格。方法中对参数param不能有任何去空格形式的改变。
	 * @param param
	 * @return
	 */
	public static String repAllIllChar(String param) {
		if (param == null ||"".equals(param.trim())) {
			return "";
		}
		// 替换非法字符的方法。
		try {
			if(charCacheMap.isEmpty()){
				Iterator<Entry<Object, Object>> it = ps.entrySet().iterator(); // 得到properties中的所有的键值对迭代器
				if (it != null) {
					while (it.hasNext()) {
						Entry<Object, Object> entry = it.next();
						int illegal = Integer.valueOf(entry.getKey().toString());
						int valid = Integer.valueOf(entry.getValue().toString());
						int[] replStrCodeArr = { illegal, valid };
						charCacheMap.put(new String(replStrCodeArr, 0,1),  new String(replStrCodeArr, 1, 1));
					}
				}
			}
			for(Map.Entry<String, String> entry:charCacheMap.entrySet()){
				param = param.replaceAll(entry.getKey(),entry.getValue());
			}
			
			//替换特殊字符
			//param = SystemUtil.replaceSpecialTohtml(param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return param;
	}
 
}
