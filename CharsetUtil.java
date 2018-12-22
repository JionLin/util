package cn.itcast.common.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;

/**
 * 转换字符串的编码
 */
public class CharsetUtil {
	    /**  7位ASCII字符，也叫作ISO646-US、Unicode字符集的基本拉丁块      */
	    public static final String US_ASCII = "US-ASCII";
	    
	    /**  ISO拉丁字母表 No.1，也叫做ISO-LATIN-1     */
	    public static final String ISO_8859_1 = "ISO-8859-1";
	    
	    /**  8 位 UCS 转换格式     */
	    public static final String UTF_8 = "UTF-8";
	    
	    /**  16 位 UCS 转换格式，Big Endian(最低地址存放高位字节）字节顺序     */
	    public static final String UTF_16BE = "UTF-16BE";
	    
	    /**  16 位 UCS 转换格式，Litter Endian（最高地址存放地位字节）字节顺序     */
	    public static final String UTF_16LE = "UTF-16LE";
	    
	    /**  16 位 UCS 转换格式，字节顺序由可选的字节顺序标记来标识     */
	    public static final String UTF_16 = "UTF-16";
	    
	    /**  中文超大字符集     **/
	    public static final String GBK = "GBK";
	    
	    
	    public static final String GB2312 = "GB2312";
	    
	    /**  将字符编码转换成US-ASCII码     */
	    public String toASCII(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str, US_ASCII);
	    }
	    
	    /**  将字符编码转换成ISO-8859-1     */
	    public String toISO_8859_1(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str, ISO_8859_1);
	    }
	    
	    /**  将字符编码转换成UTF-8     */
	    public String toUTF_8(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str, UTF_8);
	    }
	    
	    /**  将字符编码转换成UTF-16BE     */
	    public String toUTF_16BE(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str, UTF_16BE);
	    }
	    
	    /**  将字符编码转换成UTF-16LE     */
	    public String toUTF_16LE(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str, UTF_16LE);
	    }
	    
	    /**  将字符编码转换成UTF-16     */
	    public String toUTF_16(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str, UTF_16);
	    }
	    
	    /**  将字符编码转换成GBK     */
	    public String toGBK(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str, GBK);
	    }
	    
	    /**  将字符编码转换成GB2312     */
	    public String toGB2312(String str) throws UnsupportedEncodingException{
	        return this.changeCharset(str,GB2312);
	    }
	    
	    /** 
	     * 字符串编码转换的实现方法
	     * @param str    待转换的字符串
	     * @param newCharset    目标编码
	     */
	    public String changeCharset(String str, String newCharset) throws UnsupportedEncodingException{
	        if(str != null){
	            //用默认字符编码解码字符串。与系统相关，中文windows默认为GB2312
	            byte[] bs = str.getBytes();
	            return new String(bs, newCharset);    //用新的字符编码生成字符串
	        }
	        return null;
	    }
	    
	    /**
	     * 字符串编码转换的实现方法
	     * @param str    待转换的字符串
	     * @param oldCharset    源字符集
	     * @param newCharset    目标字符集
	     */
	    public String changeCharset(String str, String oldCharset, String newCharset) throws UnsupportedEncodingException{
	        if(str != null){
	            //用源字符编码解码字符串
	            byte[] bs = str.getBytes(oldCharset);
	            return new String(bs, newCharset);
	        }
	        return null;
	    }
	    
	    /**
		 * 
		 * <p>%获得字符集的编码格式%。</p> 
		 * 0xefbb:  "UTF-8";     前两字节为EFBB
		 * 0xfffe:  "Unicode";   前两个字节为FFFE
		 * 0xfeff:  "Unicode   big   endian";  前两字节为FEFF
		 * ANSI：　　　　　　　　        无格式定义
		 * @param file
		 * @return
		 */
		public static String get_charset(File file) {
			
			// 默认字符集编码格式
			String charset = "GBK";
			
			byte[] first3Bytes = new byte[3];
			
			try {
				boolean checked = false;
				BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
				
				bis.mark(0);
				// 获取前3个字符  BOM
				int read = bis.read(first3Bytes, 0, 3);
				if (read == -1)
					return charset;
				if (first3Bytes[0] == (byte) 0xFF && first3Bytes[1] == (byte) 0xFE) {
					charset = "UTF-16LE";
					checked = true;
				} else if (first3Bytes[0] == (byte) 0xFE && first3Bytes[1] == (byte) 0xFF) {
					charset = "UTF-16BE";
					checked = true;
				} else if (first3Bytes[0] == (byte) 0xEF && first3Bytes[1] == (byte) 0xBB && first3Bytes[2] == (byte) 0xBF) {
					charset = "UTF-8";
					checked = true;
				}
				bis.reset();
				if (!checked) {
					int loc = 0;
					while ((read = bis.read()) != -1) {
						loc++;
						if (read >= 0xF0)
							break;
						if (0x80 <= read && read <= 0xBF) // 单独出现BF以下的，也算是GBK
							break;
						if (0xC0 <= read && read <= 0xDF) {
							read = bis.read();
							if (0x80 <= read && read <= 0xBF)// 双字节 (0xC0 - 0xDF)
								// (0x80 - 0xBF),也可能在GB编码内
								continue;
							else
								break;
						} else if (0xE0 <= read && read <= 0xEF) {// 也有可能出错，但是几率较小
							read = bis.read();
							if (0x80 <= read && read <= 0xBF) {
								read = bis.read();
								if (0x80 <= read && read <= 0xBF) {
									charset = "UTF-8";
									break;
								} else
									break;
							} else
								break;
						}
					}
					//BOM信息
//					System.out.println(loc + " " + Integer.toHexString(read));
				}
				bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return charset;
		}
		
	    public static void main(String[] args) throws UnsupportedEncodingException{
	        CharsetUtil test = new CharsetUtil();
	        String str = "This is a 中文的 String!";
	        System.out.println("str：" + str);
	        
	        String gbk = test.toGBK(str);
	        System.out.println("转换成GBK码：" + gbk);
	        System.out.println();
	        
	        String ascii = test.toASCII(str);
	        System.out.println("转换成US-ASCII：" + ascii);
	        System.out.println();
	        
	        String iso88591 = test.toISO_8859_1(str);
	        System.out.println("转换成ISO-8859-1码：" + iso88591);
	        System.out.println();
	        
	        gbk = test.changeCharset(iso88591, ISO_8859_1, GBK);
	        System.out.println("再把ISO-8859-1码的字符串转换成GBK码：" + gbk);
	        System.out.println();
	        
	        String utf8 = test.toUTF_8(str);
	        System.out.println();
	        System.out.println("转换成UTF-8码：" + utf8);
	        
	        String utf16be = test.toUTF_16BE(str);
	        System.out.println("转换成UTF-16BE码：" + utf16be);
	        
	        gbk = test.changeCharset(utf16be, UTF_16BE, GBK);
	        System.out.println("再把UTF-16BE编码的字符转换成GBK码：" + gbk);
	        System.out.println();
	        
	        String utf16le = test.toUTF_16LE(str);
	        System.out.println("转换成UTF-16LE码：" + utf16le);
	        
	        gbk = test.changeCharset(utf16le, UTF_16LE, GBK);
	        System.out.println("再把UTF-16LE编码的字符串转换成GBK码：" + gbk);
	        System.out.println();
	        
	        String utf16 = test.toUTF_16(str);
	        System.out.println("转换成UTF-16码：" + utf16);
	        
	        String gb2312 = test.changeCharset(utf16, UTF_16, GB2312);
	        System.out.println("再把UTF-16编码的字符串转换成GB2312码：" + gb2312);
	    }

	}