package dao;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryDAO {

	public ResultSet getManagers() {
		String sql = "SELECT * FROM ENTITY_MANAGER";

		try {
			return (AirLineDAO.getConnection().createStatement()
					.executeQuery(sql));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
