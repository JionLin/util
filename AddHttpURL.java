package cn.itcast.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 图片url处理
 *
 */
public class AddHttpURL {
    private static final String HTTPURL = PropertiesUtils.getPropValue("file.path");
    
    private static final String FONT_LIGHT_START = "/<font color=red>";
    private static final String FONT_LIGHT_END = "</font>/";
    private static final String FONT_LIGHT_START2 = "<font color=red>";
    private static final String FONT_LIGHT_END2 = "</font>";


    public static String getText(String path) {
        String returnStr = null;
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            StringBuffer buf = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                buf.append(line + "\n");
            }
            returnStr = buf.length() == 0 ? null : buf.toString();
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return returnStr;
    }

    /**
     * 替换系统路径分隔符
     * @param sour
     * @return
     */
    public static  String fileSeparator(String sour){
         return  sour.replaceAll("[\\/]","\\"+ File.separator);
    }

   
    public static String formatSrc(String source) {
        if (StringUtil.isEmpty(source)) return "";
        Pattern reg = Pattern.compile("(?i)(?:src=[\"\']?[/]?([./\\w]+)[\"\']?)");
        source = reg.matcher(source).replaceAll("src=\"$1\"");
        return source;
    }
    
    public static String addHTTPURL(String uri){
    	if(StringUtil.isEmpty(uri)) return "";
    		return HTTPURL + uri;
    }

    /**
     * 使用此方法
     * @param source
     * @return
     */
    public static String addHttpURLStrong(String source) {
        if (StringUtil.isEmpty(source)||"null".equals(source)) return "";
        StringBuilder sb = new StringBuilder(source);
        Pattern srcReg = Pattern.compile("(?i)(?:src=[\"\']?([./\\w]+)[\"\']?)");
        Matcher srcMat = srcReg.matcher(sb);
        Pattern imgReg = Pattern.compile("(?i)<img\\s*[^>]*?>");
        Matcher imgMat = imgReg.matcher(sb);

        int potinter = 0;
        while (imgMat.find(potinter)) {
            potinter = imgMat.end();
            srcMat.region(imgMat.start(), imgMat.end());
            while (srcMat.find()) {
                sb.replace(srcMat.start(), srcMat.end(), "src=\"" + HTTPURL + srcMat.group(1) + "\"");
                potinter = potinter + HTTPURL.length();
            }
        }
        return sb.toString();
    }
    
    /**
     * 图片加样式
     * @param source
     * @param style 
     * @return
     */
    public static String addHttpURLStyle(String source) {
        if (StringUtil.isEmpty(source) || "null".equals(source)) return "";
      //  if(source.indexOf(HTTPURL) != -1) return source;// add by lipp 2014-04-22
        
        String style = " style=vertical-align:middle;";
        StringBuilder sb = new StringBuilder(source);
        Pattern srcReg = Pattern.compile("(?i)(?:src=[\"\']?([./\\w]+)[\"\']?)");
        Matcher srcMat = srcReg.matcher(sb);
        Pattern imgReg = Pattern.compile("(?i)<img\\s*[^>]*?>");
        Matcher imgMat = imgReg.matcher(sb);

        int potinter = 0;
        while (imgMat.find(potinter)) {
            potinter = imgMat.end();
            srcMat.region(imgMat.start(), imgMat.end());
            while (srcMat.find()) {
                sb.replace(srcMat.start(), srcMat.end(), "src=\"" + HTTPURL + srcMat.group(1)+ "\""+ style );
                potinter = potinter + HTTPURL.length();
            }  
        }
        //log.debug(sb.toString());
        return sb.toString();
    }
    
    /**
     * 出掉<img 标签的高亮标签属性
     * @param source
     * @return
     */
    public static String removeImgLight(String source) {
        if (StringUtil.isEmpty(source) || "null".equals(source)) return "";
        if(source.indexOf(FONT_LIGHT_START) == -1) return source;// add by lipp 2014-04-22
        
        StringBuilder sb = new StringBuilder(source);
        Pattern imgReg = Pattern.compile("(?i)<img\\s*[^>]*?>");
        Matcher imgMat = imgReg.matcher(sb);
        String str = "";
        while (imgMat.find()) {
        	if(sb.toString().indexOf(FONT_LIGHT_START)>-1){
        		str = sb.toString().replaceAll(FONT_LIGHT_START, "/");
        		str = str.replaceAll(FONT_LIGHT_END, "/");
        	}
        }
        
        return StringUtil.isEmpty(str)?source:str;
    }
    
    public static String removeImgLight2(String source) {
        if (StringUtil.isEmpty(source) || "null".equals(source)) return "";
        if(source.indexOf(FONT_LIGHT_START) == -1) return source;// add by lipp 2014-04-22
//        try {
//			source = URLDecoder.decode(source.trim(), "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
        source = source.replaceAll(FONT_LIGHT_START2, "");
        source = source.replaceAll(FONT_LIGHT_END2, "");
        return source;
    }
    
//    public static void main(String[] args) {
//		String str = "<img src=/upload/<font color=red>2010</font>/0814/4581/image022.gif >";
//		System.out.println(removeImgLight(str));
//		
//		String str2 = "\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp;\u0026nbsp; \u003cfont color\u003dred\u003e一\u003c/font\u003e\u003cfont color\u003dred\u003e些\u003c/font\u003e国家\u003cfont color\u003dred\u003e曾\u003c/font\u003e\u003cfont color\u003dred\u003e秉\u003c/font\u003e\u003cfont color\u003dred\u003e承\u003c/font\u003e李嘉图\u003cfont color\u003dred\u003e的\u003c/font\u003e\u003cfont color\u003dred\u003e比\u003c/font\u003e\u003cfont color\u003dred\u003e较\u003c/font\u003e\u003cfont color\u003dred\u003e成\u003c/font\u003e\u003cfont color\u003dred\u003e本\u003c/font\u003e\u003cfont color\u003dred\u003e学\u003c/font\u003e\u003cfont color\u003dred\u003e说\u003c/font\u003e，\u003cfont color\u003dred\u003e集\u003c/font\u003e\u003cfont color\u003dred\u003e中\u003c/font\u003e\u003cfont color\u003dred\u003e发\u003c/font\u003e\u003cfont color\u003dred\u003e展\u003c/font\u003e\u003cfont color\u003dred\u003e具\u003c/font\u003e\u003cfont color\u003dred\u003e有\u003c/font\u003e\u003cfont color\u003dred\u003e比\u003c/font\u003e\u003cfont color\u003dred\u003e较\u003c/font\u003e\u003cfont color\u003dred\u003e优\u003c/font\u003e\u003cfont color\u003dred\u003e势\u003c/font\u003e\u003cfont color\u003dred\u003e的\u003c/font\u003e\u003cfont color\u003dred\u003e农\u003c/font\u003e\u003cfont color\u003dred\u003e业\u003c/font\u003e、矿\u003cfont color\u003dred\u003e业\u003c/font\u003e等以\u003cfont color\u003dred\u003e换\u003c/font\u003e\u003cfont color\u003dred\u003e取\u003c/font\u003e\u003cfont color\u003dred\u003e工\u003c/font\u003e\u003cfont color\u003dred\u003e业\u003c/font\u003e\u003cfont color\u003dred\u003e品\u003c/font\u003e；但\u003cfont color\u003dred\u003e发\u003c/font\u003e\u003cfont color\u003dred\u003e展\u003c/font\u003e\u003cfont color\u003dred\u003e的\u003c/font\u003e结果却是产\u003cfont color\u003dred\u003e业\u003c/font\u003e结构单\u003cfont color\u003dred\u003e一\u003c/font\u003e、对外依附加深、国际经济地位低下，并未享受到自\u003cfont color\u003dred\u003e由\u003c/font\u003e贸易带来\u003cfont color\u003dred\u003e的\u003c/font\u003e好处。二战后\u003cfont color\u003dred\u003e发\u003c/font\u003e达国家\u003cfont color\u003dred\u003e的\u003c/font\u003e\u003cfont color\u003dred\u003e农\u003c/font\u003e\u003cfont color\u003dred\u003e业\u003c/font\u003e虽没\u003cfont color\u003dred\u003e有\u003c/font\u003e\u003cfont color\u003dred\u003e比\u003c/font\u003e\u003cfont color\u003dred\u003e较\u003c/font\u003e\u003cfont color\u003dred\u003e优\u003c/font\u003e\u003cfont color\u003dred\u003e势\u003c/font\u003e，但政府仍大力支持其\u003cfont color\u003dred\u003e发\u003c/font\u003e\u003cfont color\u003dred\u003e展\u003c/font\u003e。新技术革命\u003cfont color\u003dred\u003e的\u003c/font\u003e\u003cfont color\u003dred\u003e成\u003c/font\u003e果被大量应用于\u003cfont color\u003dred\u003e农\u003c/font\u003e\u003cfont color\u003dred\u003e业\u003c/font\u003e领域，例如高科技对以色列\u003cfont color\u003dred\u003e农\u003c/font\u003e\u003cfont color\u003dred\u003e业\u003c/font\u003e增长\u003cfont color\u003dred\u003e的\u003c/font\u003e贡献率超过90%。\u003cfont color\u003dred\u003e发\u003c/font\u003e达国家通过\u003cfont color\u003dred\u003e发\u003c/font\u003e\u003cfont color\u003dred\u003e展\u003c/font\u003e\u003cfont color\u003dred\u003e农\u003c/font\u003e\u003cfont color\u003dred\u003e业\u003c/font\u003e不仅确保了自身粮食安全，而且在国际\u003cfont color\u003dred\u003e农\u003c/font\u003e产\u003cfont color\u003dred\u003e品\u003c/font\u003e市场占据重要地位。 \n\u003c\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e\u003c/\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e\n\u003c\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e阅读上述材料，回答下列问题：\u003c/\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e\n\u003c\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e（1）简述李嘉图\u003cfont color\u003dred\u003e的\u003c/font\u003e\u003cfont color\u003dred\u003e比\u003c/font\u003e\u003cfont color\u003dred\u003e较\u003c/font\u003e\u003cfont color\u003dred\u003e成\u003c/font\u003e\u003cfont color\u003dred\u003e本\u003c/font\u003e\u003cfont color\u003dred\u003e学\u003c/font\u003e\u003cfont color\u003dred\u003e说\u003c/font\u003e\u003cfont color\u003dred\u003e的\u003c/font\u003e局限性。\u003c/\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e\n\u003c\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e（2）上述材料\u003cfont color\u003dred\u003e中\u003c/font\u003e各国做法对我国\u003cfont color\u003dred\u003e发\u003c/font\u003e\u003cfont color\u003dred\u003e展\u003c/font\u003e\u003cfont color\u003dred\u003e农\u003c/font\u003e\u003cfont color\u003dred\u003e业\u003c/font\u003e\u003cfont color\u003dred\u003e有\u003c/font\u003e何借鉴？\u003c/\u003cfont color\u003dred\u003eP\u003c/font\u003e\u003e\",";
//		System.out.println("str2="+str2);
//		System.out.println("处理后="+removeImgLight2(str2));
//			
//	}

}
