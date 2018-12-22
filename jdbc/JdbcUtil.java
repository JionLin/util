package cn.itcast.common.util.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 万能JDBC封装类<br>
 * 
 * 封装JDBC的增删改查操作
 * 
 * @author lipp
 *
 */
public class JdbcUtil {

	/* 数据库配置文件名：db.properties */
	private static final String DATABASE_NAME = "/com/itheima/test/action/db1.properties";
	/* 配置文件KEY：数据库驱动类 */
	public static final String JDBC_DRIVER = "jdbc.driver";
	/* 配置文件KEY：数据库连接url */
	public static final String JDBC_URL = "jdbc.url";
	/* 配置文件KEY：数据库连接用户名 */
	public static final String JDBC_USERNAME = "jdbc.username";
	/* 配置文件KEY：数据库连接密码 */
	public static final String JDBC_PASSWORD = "jdbc.password";
	/* 属性文件配置映射表 */
	private static Properties prop;

	/**
	 * 在类加载时候，执行读取文件的操作
	 */
	static {
		try {
			prop = new Properties();
			InputStream in = JdbcUtil.class.getClassLoader()
					.getResourceAsStream(DATABASE_NAME);
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 得到数据库连接
	 * @return
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 * @throws Exception
	 */
	private static Connection getConnection() throws 
			ClassNotFoundException, SQLException {
		
		Class.forName(prop.getProperty(JDBC_DRIVER));// 加载驱动
		return DriverManager.getConnection(prop.getProperty(JDBC_URL),
				prop.getProperty(JDBC_USERNAME),
				prop.getProperty(JDBC_PASSWORD));
	}
	
	/**
	 * 执行查询操作，返回结果字符串，JSON格式<br>
	 * 查询结果只有一条数据时返回JSON对象，有多条数据时返回JSON数组
	 * <p>
	 * <b>该方法只能用于查询操作</b>
	 * </p>
	 * 
	 * @param sql 要执行的sql
	 * @return 返回结果字符串，JSON格式
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String executeQuery(String sql)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection(); // 获取数据库连接
		PreparedStatement pstmt = conn.prepareStatement(sql); // 预编译sql
		ResultSet rs = pstmt.executeQuery(); // 执行sql，获取结果集
		ResultSetMetaData metaData = rs.getMetaData(); // 获取元数据
		int columnCount = metaData.getColumnCount(); // 获取总列数
		
		/* 遍历数据，拼接成json字符串 */
		StringBuffer sb = new StringBuffer(); // 返回结果
		while(rs.next()) { // 当next方法返回true表示有数据
			StringBuffer sbObj = new StringBuffer();
			sbObj.append("{"); // 每条数据开始
			for (int i = 1; i <= columnCount; i++) {
				String colName = metaData.getColumnName(i); // 获取列名
				String str = rs.getString(i); // 获取第 i 列数据
				sbObj.append("\""+ colName +"\":" + "\""+ str +"\"");
				if(i < columnCount) {
					sbObj.append(",");
				}
			}
			sbObj.append("}"); // 每条数据结束
			sb.append(sbObj.toString());
			if(!rs.isLast()) { // 该数据如果不是最后一条，则拼接逗号
				sb.append(",");
			}
		}
		clearConn(conn, pstmt, rs); // 关闭数据库连接
		return sb.toString();
	}
	
	/**
	 * 执行查询操作，返回结果字符串，JSON格式<br>
	 * 查询结果只有一条数据时返回JSON对象，有多条数据时返回JSON数组
	 * <p>
	 * <b>该方法只能用于查询操作</b>
	 * </p>
	 * 
	 * @param sql 要执行的sql
	 * @return 返回结果字符串，JSON格式
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static String executeQueryList(String sql)
			throws ClassNotFoundException, SQLException {
		String result = executeQuery(sql);
		return "[" + result + "]";
	}
	
	/**
	 * 执行修改、删除、插入语句，返回操作记录数<br>
	 * <p>
	 * <b>该方法只能用于修改、删除、插入操作</b>
	 * </p>
	 * 
	 * @param sql 要执行的sql
	 * @return 返回操作记录数
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static int executeUpdate(String sql)
			throws ClassNotFoundException, SQLException {
		Connection conn = getConnection(); // 获取数据库连接
		PreparedStatement pstmt = conn.prepareStatement(sql); // 创建PreparedStatement对象
		int resultCount = pstmt.executeUpdate(); // 执行sql
		clearConn(conn, pstmt, null); // 关闭数据库连接
		return resultCount;
	}
	
	/**
	 * 释放资源
	 * @param conn 数据库连接对象
	 * @param stmt 处理sql的Statement对象
	 * @param rs 结果集对象
	 */
	private static void clearConn(Connection conn, Statement stmt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}

		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
}
