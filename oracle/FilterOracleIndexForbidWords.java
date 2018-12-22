package cn.itcast.common.util.oracle;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * 过滤全文索引停用词
 * </p>
 * description:遇到停用词时请添加，应用的解决方案：当搜索关键字出现这些停用词时，请用like 搜索（性能很差）
 * 
 * @ClassName: FilterOracleIndexForbidWords
 * 
 */
public class FilterOracleIndexForbidWords {

	/**
	 * 
	 * <P>
	 * 得到禁用的关键字
	 * </P>
	 * 
	 * @return
	 */
	public static List<String> getForbidWords() {

		List<String> words = new ArrayList<String>();
		words.add("还是");
		words.add("就是");
		words.add("也是");
		words.add("如果");
		words.add("and");
		words.add("or");
		words.add("about");
		words.add("not");
		words.add("这个");
		return words;
	}

	/**
	 * 
	 * <P>
	 * 搜索时不能匹配的关键字
	 * </P>
	 * 
	 * @return
	 */
	public static List<String> getKeyWords() {
		List<String> words = new ArrayList<String>();
		words.add("%");
		words.add("--");
		words.add("-");
		words.add("/*");
		words.add("*/");
		words.add("&");
		words.add("$");
		words.add("*");
		words.add("(");
		words.add(")");
		words.add("[");
		words.add("]");
		words.add(",");
		words.add("?");
		words.add("~");
		words.add("!");
		words.add("<");
		words.add(">");
		words.add("|");
		words.add("{");
		words.add("}");
		return words;
	}
}
