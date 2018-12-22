package cn.itcast.common.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class SystemUtil {
		
	/**
	 * 将地区封装成Map，前端直接遍历Map即可得到地区(适用web端)
	 * @return
	 */
	public static Map<Integer,String> getAreaMap() {
		Map<Integer,String> map = new LinkedHashMap<Integer, String>();
		map.put(0, "全国");
		map.put(110000, "北京市");
		map.put(120000, "天津市");
		map.put(130000, "河北省");
		map.put(140000, "山西省");
		map.put(150000, "内蒙古");
		map.put(210000, "辽宁省");
		map.put(220000, "吉林省");
		map.put(230000, "黑龙江省");
		map.put(310000, "上海市");
		map.put(320000, "江苏省");
		map.put(330000, "浙江省");
		map.put(340000, "安徽省");
		map.put(350000, "福建省");
		map.put(360000, "江西省");
		map.put(370000, "山东省");
		map.put(410000, "河南省");
		map.put(420000, "湖北省");
		map.put(430000, "湖南省");
		map.put(440000, "广东省");
		map.put(450000, "广西");
		map.put(460000, "海南省");
		map.put(500000, "重庆市");
		map.put(510000, "四川省");
		map.put(520000, "贵州省");
		map.put(530000, "云南省");
		map.put(540000, "西藏");
		map.put(610000, "陕西省");
		map.put(620000, "甘肃省");
		map.put(630000, "青海省");
		map.put(640000, "宁夏");
		map.put(650000, "新疆");
		return map;
	}

	/**
	 * 
	 * <P>
	 * 返回省区
	 * </P>
	 * 
	 * @param areaid
	 * @return
	 */
	public static String getAreacode(int areaid) {
		switch (areaid) {
			case 0:
				return "全国";
			case 110000:
				return "北京市";
			case 120000:
				return "天津市";
			case 130000:
				return "河北省";
			case 140000:
				return "山西省";
			case 150000:
				return "内蒙古";
			case 210000:
				return "辽宁省";
			case 220000:
				return "吉林省";
			case 230000:
				return "黑龙江省";
			case 310000:
				return "上海市";
			case 320000:
				return "江苏省";
			case 330000:
				return "浙江省";
			case 340000:
				return "安徽省";
			case 350000:
				return "福建省";
			case 360000:
				return "江西省";
			case 370000:
				return "山东省";
			case 410000:
				return "河南省";
			case 420000:
				return "湖北省";
			case 430000:
				return "湖南省";
			case 440000:
				return "广东省";
			case 450000:
				return "广西";
			case 460000:
				return "海南省";
			case 500000:
				return "重庆市";
			case 510000:
				return "四川省";
			case 520000:
				return "贵州省";
			case 530000:
				return "云南省";
			case 540000:
				return "西藏";
			case 610000:
				return "陕西省";
			case 620000:
				return "甘肃省";
			case 630000:
				return "青海省";
			case 640000:
				return "宁夏";
			case 650000:
				return "新疆";
			case 710000:
				return "台湾";
			case 810000:
				return "香港";
			case 820000:
				return "澳门";
			default:
				return "";
		}
	}

	
	/**
     * 
     * <P>把html编码转为音标</P>
     * 暂时未用
     * @param s
     * @return
     */
    public static String transHTMLToYinbiao(String s) {
		if (s == null || s.equals("")) {
			return "";
		} else {
			s = s.replaceAll("&#952;","θ");
			s = s.replaceAll("&#240;","ð");
			s = s.replaceAll("&#643;","ʃ");
			s = s.replaceAll("&#658;","ʒ");
			s = s.replaceAll("&#331;","ŋ");
			s = s.replaceAll("&#618;","ɪ");
			s = s.replaceAll("&aelig;","æ");
			s = s.replaceAll("&#596;","ɔ");
			s = s.replaceAll("&#652;","ʌ");
			s = s.replaceAll("&#650;","ʊ");
			s = s.replaceAll("&#601;","ə");
			s = s.replaceAll("&#603;","ɛ");
			s = s.replaceAll("&#593;:","ɑ:");
			s = s.replaceAll("&#650;:","ʊ:");
			s = s.replaceAll("&#596;:","ɔ:");
			s = s.replaceAll("&#603;","ɛ");
			return s;
		}
	}
    
    
	/**
     * 
     * <P>把音标转为html编码</P>
     * 用于单词导入功能
     * @param s
     * @return
     */
    public static String transYinbiaoToHTML(String s) {
		if (s == null || s.equals("")) {
			return "";
		} else {
			s = s.replaceAll("θ","&#952;");
			s = s.replaceAll("ð","&#240;");
			s = s.replaceAll("ʃ","&#643;");
			s = s.replaceAll("ʒ","&#658;");
			s = s.replaceAll("ŋ","&#331;");
			s = s.replaceAll("ɪ","&#618;");
			s = s.replaceAll("æ","&aelig;");
			s = s.replaceAll("ɔ","&#596;");
			s = s.replaceAll("ʌ","&#652;");
			s = s.replaceAll("ʊ","&#650;");
			s = s.replaceAll("ə","&#601;");
			s = s.replaceAll("ɛ","&#603;");
			s = s.replaceAll("ɑ:","&#593;:");
			s = s.replaceAll("ʊ:","&#650;:");
			s = s.replaceAll("ɔ:","&#596;:");
			s = s.replaceAll("ɛ","&#603;");
			return s;
		}
	}
    
    
}
