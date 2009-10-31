package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAO {

	public static String userid;
	public static UserDAO inst;
	public static UserDAO getInstance()
	{
		if(inst==null)inst=new UserDAO();
		return inst;
	}
	protected UserDAO()
	{
		
	}
	public int registerUser(String username, String password, String email,
			String address, String phone) {
		String sql = "insert into ENTITY_USER(USER_ID,PASSWORD,PHONE,ADDRESS,EMAIL) VALUES ('"
				+ username
				+ "','"
				+ AirLineDAO.MD5(password)
				+ "',"
				+ "'"
				+ phone + "','" + address + "','" + email + "')";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int addManager(String username, String password) {
		String sql = "insert into ENTITY_MANAGER(MANAGER_ID,PASSWORD) VALUES ('"
				+ username + "','" + AirLineDAO.MD5(password) + "')";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public boolean isValidUser(String username, String pwd) {
		String sql = "SELECT * FROM ENTITY_USER WHERE USER_ID='" + username
				+ "' AND PASSWORD='" + AirLineDAO.MD5(pwd) + "' LIMIT 0,1";
		System.out.println(sql);
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

	public boolean isValidManager(String username, String pwd) {
		String sql = "SELECT * FROM ENTITY_MANAGER WHERE MANAGER_ID='"
				+ username + "' AND PASSWORD='" + AirLineDAO.MD5(pwd)
				+ "' LIMIT 0,1";
		System.out.println(sql);
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
	
	public int changeUserPassword(String userid, String newpwd) {
		String sql = "UPDATE ENTITY_USER SET PASSWORD='"
				+ AirLineDAO.MD5(newpwd) + "' WHERE USER_ID='" + userid + "'";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int changeManagePassword(String userid, String newpwd) {
		String sql = "UPDATE ENTITY_MANAGER SET PASSWORD='"
				+ AirLineDAO.MD5(newpwd) + "' WHERE MANAGER_ID='" + userid + "'";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int updateUserInfo(String userid, String email,String phone,String address) {
		String sql = "UPDATE ENTITY_USER SET EMAIL='"
				+ email + "',PHONE='"+phone+"',ADDRESS='"+address+"' WHERE USER_ID='" + userid + "'";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public boolean isValidAdmin(String username, String pwd) {
		String sql = "SELECT * FROM ENTITY_ADMIN WHERE ADMIN_ID='" + username
				+ "' AND PASSWORD='" + AirLineDAO.MD5(pwd) + "' LIMIT 0,1";
		System.out.println(sql);
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

	public int deleteUser(String userid) {
		String sql = "delete  FROM entity_user where user_id='" + userid + "'";

		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(
					sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public int deleteManagers(ArrayList<String> names) {
		StringBuffer sb = new StringBuffer();
		sb.append("DELETE FROM ENTITY_MANAGER WHERE MANAGER_ID IN (");
		for (String name : names) {
			sb.append("'");
			sb.append(name);
			sb.append("',");

		}
		sb.setLength(sb.toString().length() - 1);
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

	public ArrayList<String> getManagers() {
		String sql = "SELECT * FROM ENTITY_MANAGER";
		ArrayList<String> ret = new ArrayList<String>();
		try {
			ResultSet resultSet = AirLineDAO.getConnection().createStatement()
					.executeQuery(sql);
			while (resultSet.next()) {
				ret.add(resultSet.getString("MANAGER_ID"));
			}
			return ret;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
	}

	public static void main(String[] args) {
		UserDAO userDAO = new UserDAO();
		//System.out.println(userDAO.changePassword("1", "2"));
	}
}
