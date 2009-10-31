package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import com.sun.org.apache.bcel.internal.generic.NEW;


public class BookingDAO {

	public int bookingTicket(int flightid,String userid,int nums,String date)
	{
		String sql="insert meta_ticket(fight_id,user_id,seat,date_t,ORDER_TIME)" +
				"select "+flightid+",'"+userid+"',ifnull(max(seat)+1,1),'"+date+"','"+new Date().toLocaleString()+"' from meta_ticket";
		int sum=0;
		try {
			
			int t;
			for(int i=0;i<nums;i++)
				
				{
				t= AirLineDAO.getConnection().createStatement().executeUpdate(sql);
				if(t==1)sum++;
				}
			return sum;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sum;
		
		
	}
	public ArrayList<String[]> getBookings(String userid)
	{
		String sql="SELECT  EU.USER_ID,EF.FIGHT_ID,ADDRESS,PHONE,EMAIL,EF.PRICE,MT.ordeR_time FROM " +
				
"ENTITY_USER EU LEFT JOIN META_TICKET MT ON EU.USER_ID=MT.USER_ID LEFT JOIN ENTITY_FIGHT EF ON MT.FIGHT_ID=EF.FIGHT_ID" +
" WHERE MT.USER_ID IS NOT NULL";
					 
		ArrayList<String[]> ret=new ArrayList<String[]>();
		
		try {
			ResultSet rs=AirLineDAO.getConnection().createStatement().executeQuery(sql);
			while(rs.next())
			{
				String[] itemStrings=new String[7];
				for(int i=0;i<7;i++)
					itemStrings[i]=rs.getString(i+1);
				ret.add(itemStrings);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ret;
		
	}

//	public boolean hasBeingBookedOnce(String date,int flightid)
//	{
//		String sql="SELECT * FROM META_TICKET WHERE DATE_T='"+date+"' LIMIT 0,1";
//		try {
//			ResultSet resultSet=AirLineDAO.getConnection().createStatement().executeQuery(sql);
//			if(resultSet.next())
//				return true;
//			return false;
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
	public static void main(String[] args) {
		Date t=new Date();
		System.out.println(t.toLocaleString());
	}
}
