package cn.itcast.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class ReflectUtil {
	
	public static final String KEY_TYPE = "type";
	public static final String KEY_NAME = "name";
	public static final String KEY_VALUE = "value";

	/**
	 * 获取对象中某个属性名对应的属性值
	 * 
	 * @param fieldName 属性名
	 * @param o 对象
	 * @return
	 * @throws Exception
	 */
	public static Object getFieldValueByName(Object o, String fieldName) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		String getter = "get" + firstLetter + fieldName.substring(1);
		try {
			Method method = o.getClass().getMethod(getter, new Class[] {});
			Object value = method.invoke(o, new Object[] {});
			return value;
		} catch (Exception e) {
			try {
				Field field = o.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				return getFieldValue(o, field);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
	}
	
	/**
	 * 为指定对象的属性赋值
	 * @param o 指定对象
	 * @param fieldName 指定属性名
	 * @param fieldValue 属性值
	 */
	public static void setValue(Object o, String fieldName, Object fieldValue) {
		Field field;
		try {
			field = o.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(o, fieldValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取对象中某个属性对应的属性值
	 * 
	 * @param o 对象
	 * @param field 属性
	 * @return 
	 */
	public static Object getFieldValue(Object o, Field field) {
		if(o == null || field == null) {
			return null;
		}
		try {
			field.setAccessible(true);
			return field.get(o);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	

	/**
	 * 获取给定对象中属性名数组
	 * 
	 * @param o 对象
	 * @return 属性名称字符串数组
	 */
	public static String[] getFieldNames(Object o) {
		Field[] fields;
		if(o instanceof Class){
			Class<?> c = (Class<?>)o;
			fields = c.getDeclaredFields();
		} else {
			fields = o.getClass().getDeclaredFields();
		}
		String[] fieldNames = new String[fields.length];
		for (int i = 0; i < fields.length; i++) {
			fieldNames[i] = fields[i].getName();
		}
		return fieldNames;
	}
	
	/**
	 * 获取给定对象中属性类型(type)，属性名(name)，属性值(value)的map组成的list
	 * 
	 * @param o 给定对象
	 * @return
	 * @throws Exception
	 */
	public static List<Map<String, Object>> getFiledValueMapList(Object o) throws Exception {
		Field[] fields = o.getClass().getDeclaredFields();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> infoMap = null;
		for (int i = 0; i < fields.length; i++) {
			infoMap = new HashMap<String, Object>();
			infoMap.put(KEY_TYPE, fields[i].getType().toString());
			infoMap.put(KEY_NAME, fields[i].getName());
			infoMap.put(KEY_VALUE, getFieldValueByName(o, fields[i].getName()));
			list.add(infoMap);
		}
		return list;
	}

	/**
	 * 获取给定对象的所有属性值
	 * 
	 * @param o 给定对象
	 * @return 返回一个属性值构成的对象数组
	 * @throws Exception
	 */
	public static Object[] getFiledValues(Object o) throws Exception {
		String[] fieldNames = getFieldNames(o);
		Object[] value = new Object[fieldNames.length];
		for (int i = 0; i < fieldNames.length; i++) {
			value[i] = getFieldValueByName(o, fieldNames[i]);
		}
		return value;
	}
	
	/**
	 * 获取给定对象的所有属性名-值构成的Map集合对象
	 * 
	 * @param o 给定对象
	 * @return 返回一个"属性名-属性值"构成的Map集合
	 * @throws Exception
	 */
	public static Map<String, Object> getFiledValueMap(Object o) throws Exception {
		String[] fieldNames = getFieldNames(o);
		Map<String, Object> map = new HashMap<String, Object>();
		for (int i = 0; i < fieldNames.length; i++) {
			map.put(fieldNames[i], getFieldValueByName(o, fieldNames[i]));
		}
		return map;
	}

	/**
	 * 
	 * <P>得到某个对象的公共属性</P>
	 * 
	 * @param owner
	 * @param fieldName
	 * @return
	 * @throws Exception
	 */
	public static Object getProperty(Object owner, String fieldName)
			throws Exception {
		return BeanUtils.getProperty(owner, fieldName);
	}

	/**
	 * 得到某类的静态公共属性
	 * 
	 * @param className
	 *            类名
	 * @param fieldName
	 *            属性名
	 * @return 该属性对象
	 * @throws Exception
	 */
	public static Object getStaticProperty(String className, String fieldName)
			throws Exception {
		Class<?> ownerClass = Class.forName(className);

		Field field = ownerClass.getField(fieldName);

		Object property = field.get(ownerClass);

		return property;
	}

	/**
	 * 执行某对象方法
	 * 
	 * @param owner
	 *            对象
	 * @param methodName
	 *            方法名
	 * @param args
	 *            参数
	 * @return 方法返回值
	 * @throws Exception
	 */
	public static Object invokeMethod(Object owner, String methodName,
			Object[] args) throws Exception {
		Class<? extends Object> ownerClass = owner.getClass();
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		Method method = ownerClass.getMethod(methodName, argsClass);
		return method.invoke(owner, args);
	}

	/**
	 * 执行某类的静态方法
	 * 
	 * @param className
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param args
	 *            参数数组
	 * @return 执行方法返回的结果
	 * @throws Exception
	 */
	public static Object invokeStaticMethod(String className,
			String methodName, Object[] args) throws Exception {
		Class<?> ownerClass = Class.forName(className);

		Class<?>[] argsClass = new Class[args.length];

		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}

		Method method = ownerClass.getMethod(methodName, argsClass);

		return method.invoke(null, args);
	}

	/**
	 * 新建实例
	 * 
	 * @param className
	 *            类名
	 * @param args
	 *            构造函数的参数
	 * @return 新建的实例
	 * @throws Exception
	 */
	public static Object newInstance(String className, Object[] args)
			throws Exception {
		Class<?> newoneClass = Class.forName(className);
		Class<?>[] argsClass = new Class[args.length];
		for (int i = 0, j = args.length; i < j; i++) {
			argsClass[i] = args[i].getClass();
		}
		
		Constructor<?> cons = newoneClass.getConstructor(argsClass);
		return cons.newInstance(args);

	}

	/**
	 * 是不是某个类的实例
	 * 
	 * @param obj
	 *            实例
	 * @param cls
	 *            类
	 * @return 如果 obj 是此类的实例，则返回 true
	 */
	public static boolean isInstance(Object obj, Class<?> cls) {
		return cls.isInstance(obj);
	}

	/**
	 * 得到数组中的某个元素
	 * 
	 * @param array
	 *            数组
	 * @param index
	 *            索引
	 * @return 返回指定数组对象中索引组件的值
	 */
	public static Object getByArray(Object array, int index) {
		return Array.get(array, index);
	}

	/**
	 * <p>对象拷贝</p>
	 * 
	 * @param src 源对象
	 * @param dest 目标对象
	 * @throws Exception
	 */
	public static void copyProperties(Object src, Object dest)
			throws Exception {
		BeanUtils.copyProperties(dest, src);
	}

}
