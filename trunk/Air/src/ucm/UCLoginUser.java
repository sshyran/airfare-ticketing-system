package ucm;

import java.awt.EventQueue;

import view.AppMain;

public class UCLoginUser extends UseCaseController {

	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		AppMain.getInstance().setVisible(true);
	}
	public static void main(String[] args) {
		UCLoginUser loginUser=new UCLoginUser();
		loginUser.run();
	}
	private void displayForm()
	{
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					AppMain window = new AppMain();
//					window.frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
	}

}
