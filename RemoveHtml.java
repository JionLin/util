package cn.itcast.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class RemoveHtml {
    //高亮的背景
    private static final String BACKGROUND = "yellow";
    //高亮的颜色
    private static final String COLOR = "red";

    /**
     * 去除所有html标签
     *
     * @param source
     * @return
     */
    public static String removeAllTag(String source) {
        Pattern reg = Pattern.compile("(?i)<(\"[^\"]*\"|\'[^\']*\'|[^\'\">])*>");
        source = reg.matcher(source).replaceAll("");
        System.out.println(reg.pattern());
        return source;
    }

    /**
     * 去除所有标记  包括 &nbsp;
     * @param source
     * @return
     */
    public static String removeAllTagNew(String source){
        Pattern reg=Pattern.compile("(?i)(<[^>]*>|&nbsp;)");
        source=reg.matcher(source).replaceAll("");
        return source;
    }

    /**
     * 给需要高亮显示的文本加入样式
     *
     * @param source  源字符串
     * @param keyWord 关键字
     * @return
     */
    public static String highLightWord(String source, String keyWord) {
        if("".equals(keyWord)||"null".equals(keyWord))return  source;
        keyWord=keyWord.trim();
        String replaceStr = "&lt;span style=\"background-color:" + BACKGROUND + ";color:" + COLOR + "\"&gt;" + keyWord + "&lt;/span&gt;";
        Pattern p = Pattern.compile("(?i)" + "\\b"+keyWord+"\\b");
        Matcher m = p.matcher(source);
        while (m.find()) {
            String str=m.group();
            replaceStr=getReplaceStr(str);
            source = m.replaceAll(replaceStr);
        }
        StringBuilder sb = new StringBuilder(source);
        Pattern ptag = Pattern.compile("(?i)<img([^>]+)>");
        Matcher mtag = ptag.matcher(sb);
        Pattern pspan = Pattern.compile("(?i)&lt;span.*?&gt;(.*?)&lt;/span&gt;");
        Matcher mspan = pspan.matcher(sb);
        int pointer = 0;
        while (mtag.find(pointer)) {
            pointer = mtag.start();
            mspan.region(mtag.start(), mtag.end());
            int end = mtag.end();
            while (mspan.find()) {
                sb.replace(mspan.start(), mspan.end(), keyWord);
                pointer = mspan.end() - (replaceStr.length() - keyWord.length());
                if (end > pointer) {
                    end = end - (replaceStr.length() - keyWord.length());
                    mspan.region(pointer, end);
                }
            }
        }
        source = sb.toString();
        source = Pattern.compile("&lt;").matcher(source).replaceAll("<");
        source = Pattern.compile("&gt;").matcher(source).replaceAll(">");
        return source;
    }

    public static String getReplaceStr(String key){
      String str= "&lt;span style=\"background-color:" + BACKGROUND + ";color:" + COLOR + "\"&gt;" + key + "&lt;/span&gt;";
        return str;
    }


    public static String highLightWord1(String source, String keyWord) {
        int n = source.toLowerCase().indexOf(keyWord.toLowerCase());
        String s = "";
        if (n > -1) {
            s = source.substring(n, n + keyWord.length());
        }
        String replaceStr = "&lt;span style=\"background-color:" + BACKGROUND + ";color:" + COLOR + "\"&gt;" + keyWord + "&lt;/span&gt;";
        Pattern p = Pattern.compile("(?i)" + "\\b"+keyWord+"\\b");
        Matcher m = p.matcher(source);
        while (m.find()) {
            source = m.replaceAll(replaceStr);
        }
        StringBuilder sb = new StringBuilder(source);
        Pattern ptag = Pattern.compile("(?i)<([^>]+)>");
        Matcher mtag = ptag.matcher(sb);
        Pattern pspan = Pattern.compile("(?i)&lt;span.*?&gt;(.*)&lt;/span&gt;");
        Matcher mspan = pspan.matcher(sb);
        int pointer = 0;
        while (mtag.find(pointer)) {
            pointer = mtag.end();
            mspan.region(mtag.start(), mtag.end());
            while (mspan.find()) {
                //sb.replace(mspan.start(), mspan.end(), keyWord);
                pointer = mspan.end() + (replaceStr.length() - keyWord.length());
            }
        }
        source = sb.toString();
        source = Pattern.compile("&lt;").matcher(source).replaceAll("<");
        source = Pattern.compile("&gt;").matcher(source).replaceAll(">");
        if (!s.equals(""))
            source = source.replaceAll(keyWord, s);
        return source;
    }

    public static String highLightWord(String source, String keyWord, int sourcetype, String a, String result) {
        String temp = source;
        a = a == null || "".equals(a) ? "A" : a;
        result = result == null || "".equals(result) ? "A" : result;
        //题干变色
        if (sourcetype == 3) {
            temp = highLightWord(source, keyWord);
        }
        //正确答案变色
        if (sourcetype == 1) {
            if (a.equalsIgnoreCase((result.trim()))) {
                temp = highLightWord(source, keyWord);
            }

        }
        if (sourcetype == 2) {
            if (!a.equalsIgnoreCase((result.trim()))) {
                temp = highLightWord(source, keyWord);
            }

        }

        return temp;
    }
    
    /**
     * 
     * <P>过滤掉标点符号</P>
     * @param sourceStr 需要替换字符串源
     * @param replacementStr 替换目的串
     * @return
     */
    public static String filterPunctuation(String sourceStr,String replacementStr){
    	if(sourceStr==null || "".equals(sourceStr)){
    		return null;
    	}
    	Pattern p = Pattern.compile("[.,\"\\?!:']");// 增加对应的标点   
        Matcher m = p.matcher(sourceStr);   
           
    	return m.replaceAll(replacementStr);
    }
}
