package cn.itcast.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
* <p>生成静态页面</p>
* @ClassName: MakeHtml
*
 */
public class MakeHtml {
	private static long star = 0;
	private static long end = 0;
	private static long ttime = 0;
	
	
	//	测试
	public static void main(String[] args) {
		// savahtml("english","D:/ken_english.html");
		String html = "<div id=\"cont\">\r\n<div id=\"page\">\r\n<div style=\"TEXT-ALIGN: left; PADDING-BOTTOM: 0px; PADDING-LEFT: 20px; PADDING-RIGHT: 20px; BACKGROUND: #fff; OVERFLOW: hidden; PADDING-TOP: 0px\" class=\"mycenter\">\r\n<fieldset id=\"508cae2c-3d0b-45a2-8452-36f87cd633ba\" class=\"quesborder\" s=\"chemistry\"><!--StartFragment-->\r\n<div class=\"pt1\">X、Y两元素的原子量之比为2：1，由两元素形成的化合物中X、Y元素的质量比为2：3，其中X元素的化合物价 为+a，则化合物中Y元素的化合价为（　　）<!--E1--></div>\r\n<div class=\"pt2\"><!--B2-->\r\n<table style=\"WIDTH: 100%\" class=\"ques quesborder\">\r\n  <tbody>\r\n    <tr>\r\n      <td style=\"WIDTH: 23%\" class=\"selectoption\"><label>A．-<span style=\"whiteSpace: nowrap; wordSpacing: normal; wordWrap: normal\" class=\"MathJye\" mathtag=\"math\">\r\n      <table style=\"MARGIN-RIGHT: 1px\" cellspacing=\"-1\" cellpadding=\"-1\">\r\n        <tbody>\r\n          <tr>\r\n            <td colspan=\"2\" style=\"BORDER-BOTTOM: black 1px solid\"><font class=\"MathJye_mi\">a</font></td>\r\n          </tr>\r\n          <tr>\r\n            <td>2</td>\r\n          </tr>\r\n        </tbody>\r\n      </table>\r\n      </span></label></td>\r\n      <td style=\"WIDTH: 23%\" class=\"selectoption s\"><label class=\"s\">B．-<span style=\"whiteSpace: nowrap; wordSpacing: normal; wordWrap: normal\" class=\"MathJye\" mathtag=\"math\">\r\n      <table style=\"MARGIN-RIGHT: 1px\" cellspacing=\"-1\" cellpadding=\"-1\">\r\n        <tbody>\r\n          <tr>\r\n            <td style=\"BORDER-BOTTOM: black 1px solid\"><font class=\"MathJye_mi\">a</font></td>\r\n          </tr>\r\n          <tr>\r\n            <td colspan=\"10\" rowspan=\"5\">3</td>\r\n          </tr><tr><td>asd</td></tr>\r\n        </tbody>\r\n     ";
		System.out.println("啊啊1：\n" + checkTable(html));
	}
	

	//	返回html代码
	public static String getHtmlCode(String httpUrl){
		Date before = new Date();
		star = before.getTime();
		String htmlCode = "";
		try {
			InputStream  in;
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent","Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			 
			java.io.BufferedReader breader = new BufferedReader(new InputStreamReader(in , "utf8"));
			String currentLine;
			while((currentLine=breader.readLine())!=null){
				htmlCode+=currentLine;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			Date after = new Date();
			end = after.getTime();
			ttime = end-star ;
		}
		return htmlCode;
	}
	
	//	存储文件
	public static synchronized void writeHtml(String filePath,String info,String flag) {

		PrintWriter pw = null;
		try {
			File writeFile = new File(filePath);
			boolean isExit = writeFile.exists();
			if (isExit != true) {
				writeFile.createNewFile();
			} else {
				if (!flag.equals("NO")) {
					writeFile.delete();
					writeFile.createNewFile();
				} 
			}
			pw = new PrintWriter(new FileOutputStream(filePath, true));
			pw.println(info);
			pw.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}finally{
			pw.close();
		}
	}
//	如果文件存在,删除,如果不存在,新建
	public static String savahtml(String weburl,String fulepath){
		String url = "http://img.winclass.net/KenAction.do?method=kdlist&subject="+weburl;

		File  f = new  File(fulepath);  
		try{ 
			if(f.exists()){
				f.delete();  
			}
		}  
		catch(Exception  e){} 
		writeHtml(fulepath,getHtmlCode(url),"NO");
		return fulepath;
	}

	
	/**
	 * 将给定html字符串转换成<b>org.jsoup.nodes.Document</b>对象，
	 * 检测所有table标签里tr，td个数是否合法，然后将其中不合法的部分设置合适的rowspan和colspan值
	 * @param html
	 */
	public static String checkTable(String html){
		if(StringUtil.isEmpty(html)) return "";
		Document doc = Jsoup.parseBodyFragment(html);
		Element body = doc.body();
		Elements tables = body.getElementsByTag("table");// 获取所有的table标签对象，包含多个
		Iterator<Element> tableIterator = tables.iterator();
		while(tableIterator.hasNext()){// 遍历每一个table
			Element table = tableIterator.next();// 获取table对象
			Elements trNodes = null;// 行对象列表
			Elements tbodys = table.getElementsByTag("tbody");
			if(tbodys != null && tbodys.size() > 0){
				// 当包含多个tbody时，说明该table下仍然包含table，当前只遍历第一个tbody下的tr标签，因为第一个才是属于当前table的tbody
				trNodes = tbodys.get(0).children();
			} else {
				trNodes = table.children();// 获取table下级节点列表
			}
			if(trNodes == null || trNodes.size() < 1)
				continue;
			int maxTdNum = 0;// 最大列数
			int trNodesSize = trNodes.size();
			// 存放每一行的最大跨行数，key 为行索引，value 为行对象
			Map<Integer, Object> wrongRowsMap = new HashMap<Integer, Object>();
			// 存放每一行的总列数，key为行索引，value为该行的总列数
			Map<Integer, Integer> colsByTrIndex = new HashMap<Integer, Integer>();
			// 存放每一行的总列数
			int[] colCountArr = new int[trNodesSize]; 
			for (int trNodeIndex = 0; trNodeIndex < trNodesSize; trNodeIndex++) {// 遍历每一个trNode
				Element tr = trNodes.get(trNodeIndex);// 获取当前行节点对象
				//System.out.println("tr.nodeName():" + tr.tagName());
				Elements tdNodes = tr.children();// tr下级节点集合,即td列表
				int colNum = 0;// 当前tr中的colomn总数
				// 循环每一个td，计算跨列总数量，跨行总数量
				for (int tdIndex = 0; tdIndex < tdNodes.size(); tdIndex++) {// 遍历每一个td
					Element tdNode = tdNodes.get(tdIndex);
					//System.out.println("td.nodeName():" + tdNode.tagName());
					// 检查跨列
					int colspan = 1;// 当前td所占列数
					if (tdNode.hasAttr("colspan") && !tdNode.attr("colspan").isEmpty()) {// 是否跨列,本行累加col总数，且跨列的累加增量
						int currTdCospan = Integer.valueOf(tdNode.attr("colspan"));
						colspan = currTdCospan > 0 ? currTdCospan : 1;
					}
					colNum += colspan;
					
					// 检查跨行
					int rowspan = 1;// 当前td所占行数
					if (tdNode.hasAttr("rowspan") && !tdNode.attr("rowspan").isEmpty()) {// 是否跨行,本行累加row总数，且跨行的累加增量
						int currTdRowpan = Integer.valueOf(tdNode.attr("rowspan"));
						rowspan = currTdRowpan > 0 ? currTdRowpan : 1;
					}
					if((rowspan + trNodeIndex) > trNodesSize) {// 跨行数 + 该行当前索引 必须 <= 行总数，否则将跨行失败，重设rowspan属性
						tdNode.attr("rowspan", "" + (trNodesSize - trNodeIndex));
					}
				}
				if(colNum > maxTdNum){
					maxTdNum = colNum;
				}
				// 将该行的总列数赋值给对应行索引的数组对象
				colCountArr[trNodeIndex] = colNum;
				// 将当前行的列数放到map里
				colsByTrIndex.put(trNodeIndex, colNum);
			}

			// double avg = avg(colCountArr);
			// 根据每一行的总列数的map对象得到对应的table里非法的行
			wrongRowsMap = putWrongRowsMap(table, colsByTrIndex, maxTdNum);
			// 修改和map中相同的table中的行的第一列的colspan属性
			modifyColspan(wrongRowsMap, maxTdNum);
			//System.out.println("table:" + table.toString());
			
		}// tableIterator while end
		
		return body.html();
		
	}
	
	/**
	 * 获取整形数组的平均值
	 * @param colCountArr
	 * @return
	 */
	public static double avg(int[] colCountArr){
		if(colCountArr == null || colCountArr.length == 0) return 0;
		double avg = 0;
		int total = 0;
		for(int i = 0; i < colCountArr.length; i++){
			total += colCountArr[i];
		}
		avg = total / colCountArr.length;
		return avg;
	}
	
	/**
	 * 分析map中的数据，将其中不合法的行索引及其对应的DOM对象分别以key-value形式存放到新的map中，大多数原则
	 * @param table
	 * @param colsByTrIndex 存放每一行的总列数，key为行索引，value为该行的总列数
	 * @param wrongRowsMap 返回一个key为行索引，value为行DOM对象的map
	 */
	public static Map<Integer, Object> putWrongRowsMap(Element table, Map<Integer, Integer> colsByTrIndex, int maxTdNum) {
		Map<Integer, Object> wrongRowsMap = new HashMap<Integer, Object>();
		Elements trNodes = null;// 行对象列表
		Elements tbodys = table.getElementsByTag("tbody");
		if(tbodys != null && tbodys.size() > 0){
			// 当包含多个tbody时，说明该table下仍然包含table，当前只遍历第一个tbody下的tr标签，因为第一个才是属于当前table的tbody
			trNodes = tbodys.get(0).children();
		} else {
			trNodes = table.children();// 获取table下级节点列表
		}
		if(trNodes == null)
			return null;
		// 倒序排列，最大的在最上面
		List<Map.Entry<Integer, Integer>> mappingList = sortByValueDesc(colsByTrIndex);
		// 获取最大列的值
		Integer colsMax = maxTdNum;// NullPointer...?
		for(int i = 0; i < mappingList.size(); i++){// 这里可以重写一个算法，得到数组中与众不同的对象，可能有多个
			Map.Entry<Integer, Integer> mapping = mappingList.get(i);
			if(mapping.getValue() < colsMax){
				wrongRowsMap.put(mapping.getKey(), trNodes.get(mapping.getKey()));
			}
		}
		
		/*
		int gtAvg = 0;// 大于平均数的行的个数
		int ltAvg = 0;// 小于平均数的行的个数
		Set<Integer> keySet = colsByTrIndex.keySet();
		Iterator<Integer> keyIt = keySet.iterator();
		List<Integer>  gtAvgKeys = new ArrayList<Integer>();// 大于平均数的行索引数组
		List<Integer>  ltAvgKeys = new ArrayList<Integer>();// 小于平均数的行索引数组
		while(keyIt.hasNext()){
			int key = keyIt.next();
			Integer value = colsByTrIndex.get(key);
			if(value > avg){
				gtAvg ++;
				gtAvgKeys.add(key);
			} else {
				ltAvg ++;
				ltAvgKeys.add(key);
			}
		}
		if(ltAvg >= gtAvg){// 一般小于平均数的行的个数较多,此时认为大于平均数的行是非法行
			int size = gtAvgKeys.size();
			if(size > 0){
				for(int i = 0; i < size; i++){
					wrongRowsMap.put(gtAvgKeys.get(i), trNodes.get(gtAvgKeys.get(i)));
				}
				wrongRowsMap.put(-10, colsByTrIndex.get(ltAvgKeys.get(0)));
			}
		} else {
			int size = ltAvgKeys.size();
			if(size > 0){
				for(int i = 0; i < size; i++){
					wrongRowsMap.put(ltAvgKeys.get(i), trNodes.get(ltAvgKeys.get(i)));
				}
				wrongRowsMap.put(-100, colsByTrIndex.get(gtAvgKeys.get(0)));
			}
		}*/
	
		return wrongRowsMap;
	}
	
	public static List<Map.Entry<Integer, Integer>> sortByValueDesc(Map<Integer, Integer> colsByTrIndex){
		// 通过ArrayList构造函数把map.entrySet()转换成list
		List<Map.Entry<Integer, Integer>> mappingList = 
				new ArrayList<Map.Entry<Integer, Integer>>(colsByTrIndex.entrySet());
		// 通过比较器实现比较排序,desc
		Collections.sort(mappingList, new Comparator<Map.Entry<Integer, Integer>>() {
			@Override
			public int compare(Map.Entry<Integer, Integer> mapping1,
					Map.Entry<Integer, Integer> mapping2) {
				return mapping1.getKey().compareTo(mapping2.getKey());
			}
		});
		
		return mappingList;
	}
	
	public static void modifyColspan(Map<Integer, Object> wrongRowsMap, int maxTdNum){
		Set<Integer> keySet = wrongRowsMap.keySet();
		Iterator<Integer> it = keySet.iterator();
		while(it.hasNext()){
			Integer key = it.next();
			if(key < 0) continue;
			Element nodeTr = (Element)wrongRowsMap.get(key);// 行对象
			Elements tdNodes = nodeTr.children();// td节点集合
			Element nodeTd = tdNodes.get(0);// 第一个td对象
//			int colNum = 0;// 当前tr中的colomn总数
			// 循环每一个td，计算跨列总数量，跨行总数量
			for (int tdIndex = 0; tdIndex < tdNodes.size(); tdIndex++) {// 遍历每一个td
				Element tdNode = tdNodes.get(tdIndex);
//				int colspan = 1;// 当前td所占列数
				if (tdNode.hasAttr("colspan") && !tdNode.attr("colspan").isEmpty()) {// 是否跨列,本行累加col总数，且跨列的累加增量
//					int currTdCospan = Integer.valueOf(tdNode.attr("colspan"));// exception...
//					colspan = currTdCospan > 0 ? currTdCospan : 1;
					tdNode.attr("colspan", "1");// 不论多少都置为1,即不跨列
				}
//				colNum += colspan;
			}
			/*if(wrongRowsMap.containsKey(-10)){
				nodeTd.attr("colspan", "" + wrongRowsMap.get(-10));
			} else if (wrongRowsMap.containsKey(-100)){
				nodeTd.attr("colspan", "" + wrongRowsMap.get(-100));
			} else {
				nodeTd.attr("colspan", "1");
			}*/
			
			nodeTd.attr("colspan", "" + (maxTdNum - tdNodes.size() + 1));
			//table.replaceWith(nodeTr);
		}
	}
	
}


