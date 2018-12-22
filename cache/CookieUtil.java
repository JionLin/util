package cn.itcast.common.util.cache;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * <p>cookie工具类</p>
 * 须先创建本类对象才可以使用相应方法<br>
 * 
 * <p>
 * 使用注意事项:<br>
 *	cookie大小有限制<br>
 *	创建cookie时候至少两部分内容，一个name，一个value<br>
 * 	在默认的情况下，cookie是一个会话级别cookie<br>
 * </p>
 * 
 * @author lipp
 *
 */
public class CookieUtil {
	
	/** cookie过期时间。10分钟：单位为秒；默认值 */
	public final static int COOKIE_AGE_TEN_MINUTES = 60 * 10;
	/** cookie过期时间。一小时：单位为秒；默认值 */
	public final static int COOKIE_AGE_HOUR = 60 * 60;
	/** cookie过期时间。一天：单位为秒 */
	public final static int COOKIE_AGE_DAY = 60 * 60 * 24;
	/** cookie过期时间。一周：单位为秒 */
	public final static int COOKIE_AGE_WEEK = 60 * 60 * 24 * 7;
	/** cookie过期时间。一月：单位为秒 */
	public final static int COOKIE_AGE_MONTH = 60 * 60 * 24 * 7 * 30;
	/** cookie中存放用户ID的key */
	public static final String COOKIE_USER_ID = "COOKIE_USER_ID";
	/** cookie中存放用户名的key */
	public static final String COOKIE_USER_NAME = "COOKIE_USER_NAME";
	/** cookie中存放用户唯一性登录标识的key */
	public static final String COOKIE_LOGIN_UUID = "COOKIE_LOGIN_UUID";

	private HttpServletRequest request;
	private HttpServletResponse response;

	/** cookie的时限，表示该cookie经过多长秒后被删除 */
	private int age = -1;

	/**
	 * <p>
	 * 创建<b> CookieUtil </b>对象，通过此对象操作cookie
	 * </p>
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @param response
	 *            HttpServletResponse对象
	 * @param age
	 *            cookie的时限：<br>
	 *            1.设置为0表示立即删除；<br> 
	 *            2.-1表示该cookie存储在浏览器进程中（内存中保存），关闭浏览器即失效；<br>
	 */
	public CookieUtil(HttpServletRequest request, HttpServletResponse response,
			int age) {
		this.request = request;
		this.response = response;
		this.age = age;
	}
	
	/**
	 * <p>创建<b> CookieUtil </b>对象，通过此对象操作cookie</p>
	 * 默认cookie的时限为1小时
	 * 
	 * @param request
	 * @param response
	 */
	public CookieUtil(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.age = COOKIE_AGE_HOUR;
	}

	/**
	 * <p>添加cookie</p>
	 * 
	 * @param name cookie的名称，即KEY
	 * @param value cookie的值
	 */
	public void addCookie(String name, String value) {
		Cookie cookies = new Cookie(name, value);
		cookies.setPath("/");// Cookies对此域下所有页面有效，暂时不允许动态设置此项
		cookies.setMaxAge(age);
		addCookie(cookies);
	}
	
	/**
	 * <p>添加cookie</p>
	 * 
	 * @param cookie cookie对象
	 */
	public void addCookie(Cookie cookie) {
		response.addCookie(cookie);
	}

	/**
	 * <p>根据cookie名称获取cookie对象</p>
	 * 
	 * @param cookieName cookie的名称，即key
	 * @return 返回给定名称对应的cookie对象，若不存在则返回null
	 */
	public Cookie getCookie(String cookieName) {
		Cookie[] cookies = this.request.getCookies();
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					return cookie;
				}
			}
		}
		return null;
	}

	/**
	 * <p>根据cookie名称获取cookie对象的值</p>
	 * 
	 * @param cookieName
	 * @return 返回给定名称对应的值，若不存在则返回null
	 */
	public String getCookieValue(String cookieName) {
		Cookie cookie = this.getCookie(cookieName);
		if (cookie != null) {
			return cookie.getValue();
		}
		return null;
	}

	/**
	 * <p>删除指定名称的cookie对象</p>
	 * <p>
	 *	第一步：获取指定cookie项目名称的对象
	 *	第二步：设置cookie有效时长 setMaxAge(0)
	 *	第三步：设置有效路径 ，要和销毁的cookie的路径一致 setPath("/")
	 *	第四步：把cookie回写到浏览器中
	 * </p>
	 * @param cookieName cookie名
	 */
	public void deleteCookie(String cookieName) {
		Cookie cookie = this.getCookie(cookieName);
		if (cookie != null) {
			cookie.setMaxAge(0);// 如果0，就说明立即删除
			cookie.setPath("/");// 不要漏掉
			addCookie(cookie);
		}
	}

	/**
	 * <p>删除全部cookie对象</p>
	 */
	public void deleteCookies() {
		Cookie[] cookies = request.getCookies();
		if(cookies != null && cookies.length > 0){
			for (Cookie cookie : cookies) {
				deleteCookie(cookie.getName());
			}
		}
	}

}
