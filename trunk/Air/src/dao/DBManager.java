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
	      //����Connector/J����
	      //��һ��Ҳ��дΪ��Class.forName("com.mysql.jdbc.Driver");
	    	Class.forName("com.mysql.jdbc.Driver").newInstance();
	      //������MySQL������
	    
	      conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/air",
	                       "root", "root");
	      //ִ��SQL���
	      stmt = conn.createStatement();
	      resultSet = stmt.executeQuery("select * from ENTITY_USER");
	      //��������
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
