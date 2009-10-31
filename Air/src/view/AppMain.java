package view;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import dao.UserDAO;

public class AppMain extends JDialog {

	private ButtonGroup buttonGroup = new ButtonGroup();
	private JPasswordField passwordField;
	private JTextField username;
	private JLabel logininfo;
	private static AppMain inst;
	private int selected=0;
	//private JFrame frame;

	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		AppMain window = new AppMain();
		window.setVisible(true);
	}

	/**
	 * Create the application
	 */
	public AppMain() {
		createContents();
		setTitle("Login");
		inst=this;
		logininfo = new JLabel();
		logininfo.setForeground(Color.RED);
		logininfo.setBounds(132, 58, 229, 18);
		getContentPane().add(logininfo);
	}
	public static AppMain getInstance()
	{
		if(inst==null)inst=new AppMain();
		return inst;
	}
	/**
	 * Initialize the contents of the frame
	 */
	private void createContents() {
		//frame = new JFrame();
		getContentPane().setLayout(null);
		setBounds(100, 100, 464, 352);
		//setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);

		final JButton loginButton = new JButton();
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				//if()
				
				String user=username.getText().trim();
				if(username.equals(""))
					logininfo.setText("Please Enter UserName");
				else{
				String pwd=new String(passwordField.getPassword());
				
				if(pwd.equals(""))
					logininfo.setText("Please Enter Password");
				else {
					
					UserDAO userDAO=UserDAO.getInstance();
					logininfo.setText("authenticating бн");
					switch (selected) {
					case 0:
						if(userDAO.isValidUser(user, pwd))
						{
							UserDAO.userid=user;
							UserManageDlg.getInstance().setVisible(true);
							
							
							System.out.println("set userid:"+user);
							setVisible(false);
							return;
						}
						break;
					case 1:
						if(userDAO.isValidManager(user, pwd))
						{
							ManagerDlg.getInstance().setVisible(true);
							UserDAO.userid=user;
							setVisible(false);
							return;
						}
						break;
					case 2:
						if(userDAO.isValidAdmin(user, pwd))
						{
							AdminManageDlg.getInstance().setVisible(true);
							UserDAO.userid=user;
							setVisible(false);
						}
						break;
					default:
						break;
					}
					
					
					
					logininfo.setText("Wrong Password!");
					
				}
				}
				//logininfo.	
			}
		});
		loginButton.setToolTipText("Log in");
		loginButton.setText("Login");
		loginButton.setBounds(132, 218, 106, 28);
		getContentPane().add(loginButton);

		final JLabel usernameLabel = new JLabel();
		usernameLabel.setText("UserName");
		usernameLabel.setBounds(60, 91, 66, 18);
		getContentPane().add(usernameLabel);

		username = new JTextField();
		username.setToolTipText("Please Enter UserName");
		username.setBounds(132, 89, 229, 22);
		getContentPane().add(username);

		final JLabel passwordLabel = new JLabel();
		passwordLabel.setText("Password");
		passwordLabel.setBounds(60, 128, 66, 18);
		getContentPane().add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setToolTipText("Please Input Password");
		passwordField.setBounds(132, 126, 229, 22);
		getContentPane().add(passwordField);

		final JRadioButton userRadioButton = new JRadioButton();
		userRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				selected=0;
			}
		});
		userRadioButton.setSelected(true);
		userRadioButton.setText("User");
		userRadioButton.setBounds(129, 168, 52, 26);
		getContentPane().add(userRadioButton);

		final JRadioButton managerRadioButton = new JRadioButton();
		managerRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				selected=1;
			}
		});
		managerRadioButton.setText("Manager");
		managerRadioButton.setBounds(189, 168, 75, 26);
		getContentPane().add(managerRadioButton);

		final JRadioButton administratorRadioButton = new JRadioButton();
		administratorRadioButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				selected=2;
			}
		});
		buttonGroup.add(administratorRadioButton);
		buttonGroup.add(userRadioButton);
		buttonGroup.add(managerRadioButton);
		administratorRadioButton.setText("Administrator");
		administratorRadioButton.setBounds(264, 168, 129, 26);
		getContentPane().add(administratorRadioButton);

		final JButton registerButton = new JButton();
		registerButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				setVisible(false);
				RegisterDlg.getInstance().setVisible(true);
			}
		});
		registerButton.setToolTipText("Register if you don't have a account");
		registerButton.setText("Register");
		registerButton.setBounds(255, 218, 106, 28);
		getContentPane().add(registerButton);

		final JLabel loginPanelLabel = new JLabel();
		loginPanelLabel.setFont(new Font("@Arial Unicode MS", Font.PLAIN, 15));
		loginPanelLabel.setText("Login ");
		loginPanelLabel.setBounds(219, 3, 106, 47);
		getContentPane().add(loginPanelLabel);

		final JButton visitorsButton = new JButton();
		visitorsButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AppMain.getInstance().setVisible(false);
				UserManageDlg.getInstance().setVistorModel();
				UserManageDlg.getInstance().setVisible(true);
				
			}
		});
		visitorsButton.setToolTipText("Enter without login");
		visitorsButton.setText("Visitors>>");
		visitorsButton.setBounds(132, 267, 106, 28);
		getContentPane().add(visitorsButton);
	}

}
