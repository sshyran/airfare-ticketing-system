package dao;

public class AirDAOException extends RuntimeException {

	public AirDAOException(String str)
	{
		super(str);
	}
	public AirDAOException(){
		super();
	}
}
