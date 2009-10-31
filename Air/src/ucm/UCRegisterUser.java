package ucm;

import view.RegisterDlg;



public class UCRegisterUser extends UseCaseController{

	

	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		displayForm();
	}
	public static void main(String[] args) {
		UCRegisterUser registerUser=new UCRegisterUser();
		registerUser.run();
	}
	public void displayForm()
	{
		RegisterDlg dlg=new RegisterDlg();
		dlg.setVisible(true);
		
		
	}
	public void setRegisterInfo(String usename,String password,String email)
	{
		
	}
	public void submit()
	{
		
	}
}
