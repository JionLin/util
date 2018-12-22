package cn.itcast.common.util;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.json.JSONObject;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * XML解析的工具类
 */

public class XmlUtils {
	
	public static String getValue(String xmlPath,String key)throws Exception{
		File file = new File(xmlPath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		return doc.getElementsByTagName(key).item(0).getFirstChild().getNodeValue();
	}

	/**
	 * <P>
	 * 将xml转换成json
	 * </P>
	 * 
	 * @param xmlPath
	 *            xml路径
	 * @return
	 * @throws Exception
	 */
	public static String Xml2Json(String xmlPath) throws Exception {
		File file = new File(xmlPath);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(file);
		NodeList employees = doc.getChildNodes();
		// 拼写json串
		String name = "",value = "";
		
		// 获取根元素
		Node node = employees.item(1);
		name = node.getNodeName();
		// 获取子元素list
		NodeList employeeInfo = node.getChildNodes();
		JSONObject object = new JSONObject();
		for (int j = 0; j < employeeInfo.getLength(); j++) {
			// 获取第一个元素
			node = employeeInfo.item(j);
			name = node.getNodeName();
			if(!name.equals("#text")){
				value=node.getNodeValue();
				if(null==value){
					value=node.getChildNodes().item(0).getNodeValue();	
				}
				object.put(name, value);
			}
		}
		return object.toString();
	}

//	public static void main(String[] args) {
//		try {
//			Xml2Json("D:\\temp\\version.xml");
//		System.out.println(getValue("D:\\temp\\version.xml", "version_code"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
