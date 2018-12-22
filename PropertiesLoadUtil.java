package cn.itcast.common.util;

import java.io.IOException;
import java.util.Properties;

public class PropertiesLoadUtil {

	public static String sourceName = "system.properties";

	public static Properties configProperties;

	static {
		loadProperties();
	}

	public static void loadProperties() {
		configProperties = new Properties();
		try {
			configProperties.load(PropertiesLoadUtil.class.getClassLoader()
					.getResourceAsStream(sourceName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return configProperties.getProperty(key);
	}
}
