package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AirPortDAO {

	public ArrayList<String> getAirports()
	{
		String sql="SELECT * FROM ENTITY_AIRPORTS";
		ArrayList<String> ret=new ArrayList<String>();
		try {
			ResultSet resultSet=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(resultSet.next())
			{
				ret.add(resultSet.getString("AIRPORT_NAME"));
			}
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public int addAirport(String airport) {
		String sql = "insert into ENTITY_AIRPORTS(AIRPORT_NAME) VALUES ('"
				+ airport + "')";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int deleteAirports(ArrayList<String> names)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("DELETE FROM ENTITY_AIRPORTS WHERE AIRPORT_NAME IN (");
		for (String name : names) {
			sb.append("'");
			sb.append(name);
			sb.append("',");
			
		}
		sb.setLength(sb.toString().length()-1);
		sb.append(")");
		System.out.println(sb.toString());
		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sb.toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public boolean isExistAirport(String name)
	{
		String sql="SELECT * FROM ENTITY_AIRPORTS WHERE AIRPORT_NAME='"+name+"' LIMIT 0,1";
		try {
			ResultSet rSet = (AirLineDAO.getConnection().createStatement()
					.executeQuery(sql));

			if (rSet.next())
				return true;
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	public static void main(String[] args) {
		
		
	}
}
