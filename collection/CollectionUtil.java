package cn.itcast.common.util.collection;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CollectionUtil {

	/**
	 * <p>
	 * List去掉重复对象。
	 * </p>
	 * 
	 * @return
	 * @author leo_soul 2013-4-2
	 */
	public static <T> List<T> removeDuplicate(List<T> list) {
		if (list == null || list.isEmpty())
			return null;
		// 原list去重。为了保证顺序，此处要用LinkedHashSet。
		Set<T> set = new LinkedHashSet<T>(list);
		if (set.size() == 0)
			return null;
		List<T> rtn = null;
		try {
			// 根据传入的参数实现类，做返回结果的多态。
			rtn = list.getClass().newInstance();
		} catch (InstantiationException e) {
			// do nothing
		} catch (IllegalAccessException e) {
			// do nothing
		}
		rtn.addAll(set);
		return rtn;
	}

	/**
	 * map value值相加结果
	 * 
	 * @param totalMap
	 * @return
	 */
	public static int getSumMapValue(Map<Long, Integer> totalMap) {

		int value = 0;
		if (totalMap == null || totalMap.size() == 0) {
			return value;
		}
		for (long key : totalMap.keySet()) {
			value += totalMap.get(key);
		}
		return value;
	}

}
