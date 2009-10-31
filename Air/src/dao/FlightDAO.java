package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;



public class FlightDAO {

	public int addFlight(String fromcity,String tocity,double price,int totalseats,String planetype,String takeofftime,int tof) {
		
		String sql="INSERT INTO ENTITY_FIGHT(TOF,TAKE_OFF_TIME,TOTAL_SEATS,PRICE,PLANE,FROM_CITY,TO_CITY)" +
			
		"VALUES("+tof+",'"+takeofftime+"',"+totalseats+","+price+",(SELECT PLANE_ID FROM ENTITY_PLANES WHERE PLANE_NAME='"+planetype+"' LIMIT 0,1)," +
				"(SELECT AIRPORT_ID FROM ENTITY_AIRPORTS WHERE AIRPORT_NAME='"+fromcity+"' LIMIT 0,1 ),"+
				"(SELECT AIRPORT_ID FROM ENTITY_AIRPORTS WHERE AIRPORT_NAME='"+tocity+"' LIMIT 0,1 ))";
	
		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public int updateFlight(int flightid,String fromcity,String tocity,double price,int totalseats,String planetype,String takeofftime,int tof) {
		
		String sql="UPDATE  ENTITY_FIGHT SET TOF="+tof+",TAKE_OFF_TIME='"+takeofftime+"',TOTAL_SEATS="+totalseats+",price="+price+","+
				"PLANE=(SELECT PLANE_ID FROM ENTITY_PLANES WHERE PLANE_NAME='"+planetype+"' LIMIT 0,1),"+
				"FROM_CITY=(SELECT AIRPORT_ID FROM ENTITY_AIRPORTS WHERE AIRPORT_NAME='"+fromcity+"' LIMIT 0,1 ),"+
				"TO_CITY=(SELECT AIRPORT_ID FROM ENTITY_AIRPORTS WHERE AIRPORT_NAME='"+tocity+"' LIMIT 0,1 )"+
				"WHERE FIGHT_ID="+flightid;
	
		try {
			return AirLineDAO.getConnection().createStatement().executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public ArrayList<String[]> getFlightsBetween(String date,String fromcity,String tocity)
	{
		String sql="SELECT DISTINCT EF.FIGHT_ID, EF.TOF,EF.TAKE_OFF_TIME,EF.TOTAL_SEATS,EF.PRICE,"+
		" EA1.AIRPORT_NAME FROM_CITY,EA.AIRPORT_NAME TO_CITY,EP.PLANE_NAME," +
		" if(EF.TOTAL_SEATS-COUNT(MT.FIGHT_ID)>0,EF.TOTAL_SEATS-COUNT(MT.FIGHT_ID),0) REMAIN"+
		" FROM entity_fight EF LEFT Join entity_airports EA ON EF.TO_CITY = EA.AIRPORT_ID" +
		" LEFT JOIN ENTITY_PLANES EP ON EF.PLANE=EP.PLANE_ID" +
		" LEFT JOIN ENTITY_AIRPORTS EA1 ON EA1.AIRPORT_ID=EF.FROM_CITY" +
		" LEFT JOIN META_TICKET MT ON MT.FIGHT_ID=EF.FIGHT_ID "+
		" WHERE MT.DATE_T='"+date+"' AND" +
		" EA1.AIRPORT_NAME='"+fromcity+"' AND EA.AIRPORT_NAME='"+tocity+"'"+
				" GROUP BY MT.FIGHT_ID";
					 
		ArrayList<String[]> ret=new ArrayList<String[]>();
		
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(rs.next())
			{
				String[] itemStrings=new String[9];
				for(int i=0;i<9;i++)
					itemStrings[i]=rs.getString(i+1);
				ret.add(itemStrings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
	public ArrayList<String[]> getFlightsBetweenWithoutDate(String fromcity,String tocity)
	{
		String sql="SELECT DISTINCT EF.FIGHT_ID, EF.TOF,EF.TAKE_OFF_TIME,EF.TOTAL_SEATS,EF.PRICE,"+
		" EA1.AIRPORT_NAME FROM_CITY,EA.AIRPORT_NAME TO_CITY,EP.PLANE_NAME," +
		" EF.TOTAL_SEATS REMAIN"+
		" FROM entity_fight EF LEFT Join entity_airports EA ON EF.TO_CITY = EA.AIRPORT_ID" +
		" LEFT JOIN ENTITY_PLANES EP ON EF.PLANE=EP.PLANE_ID" +
		" LEFT JOIN ENTITY_AIRPORTS EA1 ON EA1.AIRPORT_ID=EF.FROM_CITY" +
		" LEFT JOIN META_TICKET MT ON MT.FIGHT_ID=EF.FIGHT_ID WHERE"+
		
		" EA1.AIRPORT_NAME='"+fromcity+"' AND EA.AIRPORT_NAME='"+tocity+"'"+
				" GROUP BY MT.FIGHT_ID";
					 
		ArrayList<String[]> ret=new ArrayList<String[]>();
		
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(rs.next())
			{
				String[] itemStrings=new String[9];
				for(int i=0;i<9;i++)
					itemStrings[i]=rs.getString(i+1);
				ret.add(itemStrings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}

	public ArrayList<String[]> getFlights()
	{
		String sql="SELECT DISTINCT EF.FIGHT_ID, EF.TOF,EF.TAKE_OFF_TIME,EF.TOTAL_SEATS,EF.PRICE,"+
					" EA1.AIRPORT_NAME FROM_CITY,EA.AIRPORT_NAME TO_CITY,EP.PLANE_NAME" +
					" FROM entity_fight EF LEFT Join entity_airports EA ON EF.TO_CITY = EA.AIRPORT_ID" +
					" LEFT JOIN ENTITY_PLANES EP ON EF.PLANE=EP.PLANE_ID" +
					" LEFT JOIN ENTITY_AIRPORTS EA1 ON EA1.AIRPORT_ID=EF.FROM_CITY";
		ArrayList<String[]> ret=new ArrayList<String[]>();
		
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(rs.next())
			{
				String[] itemStrings=new String[8];
				for(int i=0;i<8;i++)
					itemStrings[i]=rs.getString(i+1);
				ret.add(itemStrings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
	public int getRemainSeats(String date,int flightid)
	{
		String sql="SELECT EF.FIGHT_ID,(EF.TOTAL_SEATS-(SELECT COUNT(*) FROM META_TICKET MT" +
				" WHERE MT.FIGHT_ID="+flightid+" AND MT.DATE_T='"+date+"')) REMAIN FROM ENTITY_FIGHT EF" +
				"	WHERE EF.FIGHT_ID  ="+flightid;
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			if(rs.next())
				return Integer.parseInt(rs.getString(2).toString());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	public ArrayList<String[]> getRemainFlights()
	{
		String sql="SELECT DISTINCT EF.FIGHT_ID, EF.TOF,EF.TAKE_OFF_TIME,EF.TOTAL_SEATS,EF.PRICE,"+
					" EA1.AIRPORT_NAME FROM_CITY,EA.AIRPORT_NAME TO_CITY,EP.PLANE_NAME," +
					" EF.TOTAL_SEATS REMAIN"+
					" FROM entity_fight EF LEFT Join entity_airports EA ON EF.TO_CITY = EA.AIRPORT_ID" +
					" LEFT JOIN ENTITY_PLANES EP ON EF.PLANE=EP.PLANE_ID" +
					" LEFT JOIN ENTITY_AIRPORTS EA1 ON EA1.AIRPORT_ID=EF.FROM_CITY" ;
					//" LEFT JOIN META_TICKET MT ON MT.FIGHT_ID=EF.FIGHT_ID ";
		ArrayList<String[]> ret=new ArrayList<String[]>();
		
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(rs.next())
			{
				String[] itemStrings=new String[9];
				for(int i=0;i<9;i++)
					itemStrings[i]=rs.getString(i+1);
				ret.add(itemStrings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
	public ArrayList<String> getFlightByID(int flightid)
	{
		String sql="SELECT DISTINCT EF.TOF,EF.TAKE_OFF_TIME,EF.TOTAL_SEATS,EF.PRICE,EA.AIRPORT_NAME TO_CITY,"+
					" EA1.AIRPORT_NAME FROM_CITY,EP.PLANE_NAME" +
					" FROM entity_fight EF LEFT Join entity_airports EA ON EF.TO_CITY = EA.AIRPORT_ID" +
					" LEFT JOIN ENTITY_PLANES EP ON EF.PLANE=EP.PLANE_ID" +
					" LEFT JOIN ENTITY_AIRPORTS EA1 ON EA1.AIRPORT_ID=EF.FROM_CITY" +
					" WHERE EF.FIGHT_ID="+flightid;
		ArrayList<String> ret=new ArrayList<String>();
		
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(rs.next())
			{
				
				for(int i=0;i<7;i++)
					ret.add(rs.getString(i+1));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
	public ArrayList<String[]> getFlightsOf(String userid)
	{
		String sql="SELECT DISTINCT mt.date_t, EF.FIGHT_ID,EF.TAKE_OFF_TIME,EF.TOF,mt.seat, " +
				
					 " EA1.AIRPORT_NAME FROM_CITY,EA.AIRPORT_NAME TO_CITY,EF.PRICE,EP.PLANE_NAME" +
					 
					 " FROM meta_ticket mt left join entity_fight EF on mt.fight_id=ef.fight_id " +
					 " LEFT Join entity_airports EA ON EF.TO_CITY = EA.AIRPORT_ID"+
					 
					 " LEFT JOIN ENTITY_PLANES EP ON EF.PLANE=EP.PLANE_ID"+
					 " LEFT JOIN ENTITY_AIRPORTS EA1 ON EA1.AIRPORT_ID=EF.FROM_CITY"+
					 " where mt.user_id='"+userid+"'";

		ArrayList<String[]> ret=new ArrayList<String[]>();
		
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(rs.next())
			{
				String[] itemStrings=new String[9];
				for(int i=0;i<9;i++)
					itemStrings[i]=rs.getString(i+1);
				ret.add(itemStrings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}
	public int deleteFlights(ArrayList<String> names)
	{
		StringBuffer sb=new StringBuffer();
		sb.append("DELETE FROM ENTITY_FIGHT WHERE FIGHT_ID IN (");
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
	
	public static void main(String[] args) {
		int a=new FlightDAO().getRemainSeats("2009-1-12",15);
		System.out.println(a);
	}
}
