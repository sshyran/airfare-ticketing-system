package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.AirLineDAO;
import dao.UserDAO;

import ucm.UCRegisterUser;

public class RegisterDlg extends JDialog {

	private JTextField email_tf;
	private JTextArea address_ta;
	private JTextField phone_tf;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField;
	private JTextField username_TF;
	private UCRegisterUser usercase;
	private JLabel reginfo;
	/**
	 * Launch the application
	 * @param args
	 */
	private static RegisterDlg inst;
	public static RegisterDlg getInstance()
	{
		if(inst==null)inst=new RegisterDlg();
		return inst;
	}
	public static void main(String args[]) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					RegisterDlg dialog = new RegisterDlg();
//					dialog.addWindowListener(new WindowAdapter() {
//						public void windowClosing(WindowEvent e) {
//							System.exit(0);
//						}
//					});
//					dialog.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
		//RegisterDlg dialog = new RegisterDlg();
//		dialog.addWindowListener(new WindowAdapter() {
//			public void windowClosing(WindowEvent e) {
//				System.exit(0);
//			}
//		});
		//dialog.setVisible(true);
	}

	/**
	 * Create the dialog
	 */
	public RegisterDlg() {
		
		super();
		inst=this;
		getContentPane().setLayout(null);
		setTitle("Register");
		setBounds(100, 100, 500, 389);

		final JLabel usernameLabel = new JLabel();
		usernameLabel.setText("UserName*");
		usernameLabel.setBounds(88, 54, 66, 18);
		getContentPane().add(usernameLabel);

		username_TF = new JTextField();
		username_TF.setBounds(160, 52, 168, 22);
		getContentPane().add(username_TF);

		final JSeparator separator = new JSeparator();
		separator.setBounds(88, 155, 333, 20);
		getContentPane().add(separator);

		final JLabel passwordLabel = new JLabel();
		passwordLabel.setText("Password*");
		passwordLabel.setBounds(88, 92, 66, 18);
		getContentPane().add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(160, 90, 168, 22);
		getContentPane().add(passwordField);

		final JLabel conLabel = new JLabel();
		conLabel.setText("Confirm*");
		conLabel.setBounds(88, 129, 66, 18);
		getContentPane().add(conLabel);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(160, 127, 168, 22);
		getContentPane().add(passwordField_1);

		final JLabel ddressLabel = new JLabel();
		ddressLabel.setText("Phone");
		ddressLabel.setBounds(88, 198, 66, 18);
		getContentPane().add(ddressLabel);

		phone_tf = new JTextField();
		phone_tf.setBounds(160, 194, 168, 22);
		getContentPane().add(phone_tf);

		final JLabel addressLabel = new JLabel();
		addressLabel.setText("Address");
		addressLabel.setBounds(88, 222, 66, 18);
		getContentPane().add(addressLabel);

		address_ta = new JTextArea();
		address_ta.setBounds(160, 222, 261, 71);
		getContentPane().add(address_ta);

		final JButton registerNowButton = new JButton();
		registerNowButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				//usercase.submit();
				String user=username_TF.getText().trim();
				if(user.equals("")){reginfo.setText("Username Required");
					return;
				}
				String pwd=new String(passwordField.getPassword());
				String confirm=new String(passwordField_1.getPassword());
				if(pwd.equals("")){
					reginfo.setText("Password Required");
					return;
				}
				if(!pwd.equals(confirm)){
					reginfo.setText("Password Confirm Wrong");
					return;
				}
				UserDAO userDAO=UserDAO.getInstance();
				int result=userDAO.registerUser(user,pwd,email_tf.getText().trim(), address_ta.getText().trim(),phone_tf.getText());
				if(1==result){
					reginfo.setText("Register Ok!");
					clearAll();
				}else{
					reginfo.setText("Username already Exist,Please Change one!");
				}
			}
		});
		registerNowButton.setText("Register Now");
		registerNowButton.setBounds(304, 315, 117, 28);
		getContentPane().add(registerNowButton);

		final JLabel emailLabel = new JLabel();
		emailLabel.setBounds(88, 164, 66, 18);
		getContentPane().add(emailLabel);
		emailLabel.setText("Email");

		email_tf = new JTextField();
		email_tf.setBounds(160, 162, 168, 22);
		getContentPane().add(email_tf);

		reginfo = new JLabel();
		reginfo.setForeground(Color.RED);
		reginfo.setBounds(160, 28, 273, 18);
		getContentPane().add(reginfo);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
				AppMain.getInstance().setVisible(true);
			}
		});
		button.setText("<<Login");
		button.setBounds(24, 315, 85, 28);
		getContentPane().add(button);
		//
	}
	private void clearAll()
	{
		username_TF.setText("");
		passwordField.setText("");
		passwordField_1.setText("");
		email_tf.setText("");
		address_ta.setText("");
		phone_tf.setText("");
	}


}
