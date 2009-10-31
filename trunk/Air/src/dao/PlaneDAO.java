package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaneDAO {
	public ArrayList<String> getPlanes()
	{
		String sql="SELECT * FROM ENTITY_PLANES";
		ArrayList<String> ret=new ArrayList<String>();
		try {
			ResultSet resultSet=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(resultSet.next())
			{
				ret.add(resultSet.getString("PLANE_NAME"));
			}
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}
	public int addPlane(String name) {
		String sql = "insert into ENTITY_PLANES(PLANE_NAME) VALUES ('"
				+ name + "')";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int deletePlanes(ArrayList<String> names)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("DELETE FROM ENTITY_PLANES WHERE PLANE_NAME IN (");
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
	public boolean isExistPlane(String name)
	{
		String sql="SELECT * FROM ENTITY_PLANES WHERE PLANE_NAME='"+name+"' LIMIT 0,1";
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
}
