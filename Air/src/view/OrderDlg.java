package view;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JLabel;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import dao.UserDAO;

public class OrderDlg extends JDialog {


	private JTextArea address;
	private JTextField phone;
	private JTextField username;
	private static OrderDlg inst;
	public static OrderDlg getInstance()
	{
		if(inst==null)inst=new OrderDlg();
		return inst;
	}
	/**
	 * Launch the application
	 * @param args
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OrderDlg dialog = new OrderDlg();
					dialog.addWindowListener(new WindowAdapter() {
						public void windowClosing(WindowEvent e) {
							System.exit(0);
						}
					});
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog
	 */
	public OrderDlg() {
		super();
		getContentPane().setLayout(null);
		setTitle("Enter Details");
		setBounds(100, 100, 500, 229);
		setModal(true);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("Name:");
		nameLabel.setBounds(41, 12, 66, 18);
		getContentPane().add(nameLabel);

		username = new JTextField();
		username.setBounds(133, 10, 135, 22);
		getContentPane().add(username);

		final JLabel phoneLabel = new JLabel();
		phoneLabel.setText("Phone");
		phoneLabel.setBounds(41, 51, 66, 18);
		getContentPane().add(phoneLabel);

		phone = new JTextField();
		phone.setBounds(133, 49, 135, 22);
		getContentPane().add(phone);

		final JLabel deliaddressLabel = new JLabel();
		deliaddressLabel.setText("Delivery Address");
		deliaddressLabel.setBounds(20, 94, 96, 18);
		getContentPane().add(deliaddressLabel);

		address = new JTextArea();
		address.setBounds(133, 94, 220, 91);
		getContentPane().add(address);

		final JButton submitButton = new JButton();
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String userString=username.getText().trim();
				String phoneString=phone.getText().trim();
				String addressString=address.getText().trim();
				if(userString.equals("")||phoneString.equals("")
						||addressString.equals("")){
					OrderDlg.getInstance().setTitle("Input the essential infomation  Please...............");
					return ;
				}
				Random random=new Random();
				String uid="vistor_"+userString+"_"+random.nextInt(9999);
				if(1==UserDAO.getInstance().registerUser(uid,phone.getText().trim(),"", address.getText(), phone.getText().trim()))
					
					{
						UserDAO.userid=uid;
						OrderDlg.getInstance().setVisible(false);
					}else OrderDlg.getInstance().setTitle("user name exist");
				;
			}
		});
		submitButton.setText("Submit");
		submitButton.setBounds(378, 156, 106, 28);
		getContentPane().add(submitButton);
		  
		
		//
	}

}
