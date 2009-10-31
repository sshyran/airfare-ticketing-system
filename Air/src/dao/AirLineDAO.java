package dao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import sun.misc.BASE64Encoder;


public class AirLineDAO {

	
	private static Connection conn;
	private AirLineDAO() {
		// TODO Auto-generated constructor stub
		
		ConnectionPool connectionPool=new ConnectionPool("com.mysql.jdbc.Driver","jdbc:mysql://localhost:3306/air1"
				,"root"
				,"root");
		try {
			connectionPool.createPool();
			conn = connectionPool.getConnection();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static Connection getConnection()
	{
		if(conn==null)new AirLineDAO();
		
		return conn;
	}
	public static String MD5(String src)
	{
		MessageDigest md;
		String newstr;
		try {
		
			md = MessageDigest.getInstance("MD5");
		
			BASE64Encoder encoder = new BASE64Encoder();
        
			newstr=encoder.encode(md.digest(src.getBytes("utf-8")));
			return newstr;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
        return null;

	}
	public static void main(String[] args) {
		System.out.println(AirLineDAO.MD5("17777234"));
	}
	
	
	
	
}
