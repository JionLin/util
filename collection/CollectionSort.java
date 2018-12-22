package cn.itcast.common.util.collection;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * <p>
 * 集合排序工具类
 * </p>
 * 
 */
public class CollectionSort {

	/**
	 * <p>
	 * List的通用排序方法。
	 * </p>
	 * 按照list的每个vo中某一字段进行自然排序。若该字段为String，则按照字典顺序自然排序，详见String类。<br>
	 * 
	 * @param list
	 *            要排序的List
	 * @param method
	 *            用于排序字段的get方法(注意，名字不能写错)
	 * @param isAsc
	 *            是否升序，true为升序。
	 * @author leo_soul 2013-4-12
	 */
	public static <T> void SortList(List<T> list, final String method,
			final boolean isAsc) {
		if (list == null || list.isEmpty() || method == null
				|| "".equals(method.trim()))
			return;
		Collections.sort(list, new Comparator<T>() {
			@Override
			public int compare(T a, T b) {
				int ret = 0;
				try {
					Object o1 = a.getClass().getMethod(method).invoke(a);
					Object o2 = b.getClass().getMethod(method).invoke(b);
					if (o1 == null) {
						ret = -1;
					} else if (o2 == null) {
						ret = 1;
					} else if (o1 instanceof String) {
						ret = o1.toString().compareTo(o2.toString());
					} else {
						ret = ((Comparable) o1).compareTo(o2);
					}
					if (!isAsc)// 降序
						ret = 0 - ret;
				} catch (NoSuchMethodException e) {
					// System.out.println(e);
				} catch (IllegalAccessException e) {
					// System.out.println(e);
				} catch (IllegalArgumentException e) {
					// System.out.println(e);
				} catch (SecurityException e) {
					// System.out.println(e);
				} catch (InvocationTargetException e) {
					// System.out.println(e);
				}
				return ret;
			}
		});
	}

	/**
	 * 
	 * <P>
	 * set 元素排序
	 * </P>
	 * 
	 * @param set
	 *            元素是数值型的字符串，例："8"
	 * @return
	 */
	public static List<String> sortSet(Set<String> set) {
		List<String> strList = new LinkedList<String>();
		if (set != null) {
			strList.addAll(set);
			Collections.sort(strList, new Comparator() {
				@Override
				public int compare(Object a, Object b) {
					return Integer.valueOf((String) b)
							- Integer.valueOf((String) a);
				}
			});

		}
		return strList;
	}

	/**
	 * 
	 * <P>
	 * map key 降序排序
	 * </P>
	 * 
	 * @param map
	 * @return
	 */
	public static Map<Integer, List<String>> sortedKey(
			Map<Integer, List<String>> map) {
		List<Integer> list = new ArrayList(map.keySet());
		Collections.sort(list, new Comparator() {
			@Override
			public int compare(Object a, Object b) {
				return (Integer) b - (Integer) a;
			}
		});
		return map;
	}
}
