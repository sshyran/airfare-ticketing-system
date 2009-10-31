package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	
	private  Connection conn;
	private static Statement stmt;

	public DBManager()
	{
		
	    ResultSet resultSet;
	    try{
	      //加载Connector/J驱动
	      //这一句也可写为：Class.forName("com.mysql.jdbc.Driver");
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	      //建立到MySQL的连接
	    
	      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/air",
	                       "root", "root");
	      //执行SQL语句
	      stmt = conn.createStatement();
	      resultSet = stmt.executeQuery("select * from ENTITY_USER");
	      //处理结果集
	      while (resultSet.next())
	      {
	        String name = resultSet.getString("USER_ID");
	        System.out.println(name);
	      }
	      resultSet.close();
	    }catch (Exception e) {
			// TODO: handle exception
	    	e.printStackTrace();
		}

		
	}
	public  static void main(String[] args) throws SQLException{
	
		
	}
}
