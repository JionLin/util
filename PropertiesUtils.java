package cn.itcast.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/**
 * Created by IntelliJ IDEA.
 * Date: 2010-9-2
 * Time: 12:08:55
 * To change this template use File | Settings | File Templates.
 */
public class PropertiesUtils {
	
    protected static Properties config = new Properties();
    /**配置文件路径*/
    private static final String CONFIGPATH="/configs/config/project.properties";
    private static final String CONFIGPATH_EDUYUN="/configs/config/eduyun.properties";
    
    static {
        try {
        	InputStream configpath = PropertiesUtils.class.getResourceAsStream(CONFIGPATH);
        	if(configpath!=null)
        		config.load(configpath);
        	InputStream configedu = PropertiesUtils.class.getResourceAsStream(CONFIGPATH_EDUYUN);
        	if(configedu!=null)
        		config.load(configedu);
//        	InputStream configpathCon = PropertiesUtils.class.getResourceAsStream(CONFIGPATH_CURRENCY);
//        	if(configpath!=null)
//        		config.load(configpathCon);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
   
	/**
	 * add by lipp
	 * <P>根据属性的key得到属性value</P>
	 * @param propertyName 属性键
	 * @return
	 */
	public static String getPropValue(String propertyName) {
           return  config.getProperty(propertyName, "");
	}
	
	/**
	 * add by lipp
	 * <P>设置配置文件的属性值</P>
	 * @param propertyKey
	 * @param value
	 */
	public static void setPropValue(String propertyKey,String value){
		config.setProperty(propertyKey, value);
	}

}
