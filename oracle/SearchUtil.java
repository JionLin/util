package cn.itcast.common.util.oracle;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import cn.itcast.common.util.StringUtil;

/**
 * 
* <p>搜索工具类</p>
* @ClassName: SearchUtil
*
 */
public class SearchUtil {
	
	/**
	 * 
	 * <P>搜索关键字里是否含有数据库的禁用词或停用词</P>
	 * 含有禁用词时返回true，否则返回false。<br>
	 * 注意，keywordsList为空或null时，返回false。<br>
	 * @param keywordsList 搜索关键字列表
	 * @param isOracle 是否是oracle数据库
	 * @return
	 */
	public static boolean isContainsForbidWords(List<String> keywordsList,
			boolean isOracle){
		if(null==keywordsList || keywordsList.size()==0)
			return false;
		if(isOracle){
			List<String> borbidWords = new ArrayList<String>();
			borbidWords.addAll(FilterOracleIndexForbidWords.getForbidWords());
			borbidWords.addAll(FilterOracleIndexForbidWords.getKeyWords());
			Set<String> keywordSet = new HashSet<String>(keywordsList);
			Iterator<String> keywordItr = keywordSet.iterator();
			while(keywordItr.hasNext()){
				String keyword = keywordItr.next();
				for(String borbidWord : borbidWords){
					if(keyword.toUpperCase(new Locale("utf-8")).equals(borbidWord.toUpperCase(new Locale("utf-8"))))
						return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * <P>把搜索关键字里的标点符号替换</P>
	 * 此方法返回结果会去掉首尾空格。<br>
	 * 此方法不会返回纯空格字符串。<br>
	 * 此方法返回结果可能出现连续的replacementStr。<br>
	 * @param keywords 关键字
	 * @param replacementStr 用来替换的字符串
	 * @return
	 */
	public static String transKeywords(String keywords,String replacementStr){
		
		if(StringUtil.isEmpty(keywords)){
			return "";
		}
		/*
		 * targetArr用来判断是否替换的符号数组。
		 * targetRegexArr用来替换的符号数组，正则表达式保留的符号要将其转义!
		 * 需要注意，两个数组的符号要一一匹配。
		 */
		String[] targetArr = {",","，","、",";","；",".","。","'","‘","’",
				"\"","“","”","`","·","%","-","&","$","*","(",")","[","]","?","~","!","<",">","|","{","}",
				"．",": ","▲","："};
		String[] targetRegexArr = {",","，","、",";","；","\\.","。","'","‘","’",
				"\"","“","”","`","·","%","\\-","\\&","\\$","\\*","\\(","\\)","\\[","\\]","\\?","~","\\!","\\<","\\>","\\|","\\{","\\}",
				"．",": ","▲","："};
		for(int i=0;i<targetArr.length;i++) {
			if(keywords.indexOf(targetArr[i])>-1)
				keywords = keywords.replaceAll(targetRegexArr[i],replacementStr);
		}
		return keywords;
	}
}
