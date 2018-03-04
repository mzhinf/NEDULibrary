package nedu.edu.library.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import nedu.edu.library.util.ConfigManager;

//基类:数据库操作通用类
public class BaseDao {

	Connection connection = null;
	PreparedStatement pstmt = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	public BaseDao() {
	}
	
	//获得数据库连接
	public boolean getConnection(){
		//读取配置信息
		String driver = ConfigManager.getInstance().getString("jdbc.driver_class");
		String url = ConfigManager.getInstance().getString("jdbc.connection.url");
		String username = ConfigManager.getInstance().getString("jdbc.connection.username");
		String password = ConfigManager.getInstance().getString("jdbc.connection.password");
		//加载JDBC驱动
		try {
			//1.Class.forName() 加载驱动
			Class.forName(driver);
			//2.DriverManager.getConnection(URL,username,pasword) 获取数据库连接 Connection
			connection = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	//获得数据库连接
	public Connection getConnectionTwo(){
		//初始化上下文
		try {
			Context context = new InitialContext();
			DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/orcl");
			connection = ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	//关闭资源
	public boolean closeResoure(){
		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		if(pstmt != null){
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		if(stmt != null){
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		if(rs != null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		}
		
		return true;
	}

	//更新
	public int executeUpdate(String sql, Object[] params){
		int updateRows = 0;
		getConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			//填充占位符
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			updateRows=pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return updateRows;
	}
	
	//查询
	public ResultSet executeSQL(String sql,Object[] params) {
		getConnection();
		try {
			pstmt = connection.prepareStatement(sql);
			//填充占位符
			for(int i=0;i<params.length;i++){
				pstmt.setObject(i+1, params[i]);
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
}
