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

import dao.UserDAO;

public class EditManagerDlg extends JDialog {

	private JPasswordField passwordField_1;
	private JPasswordField passwordField;
	private static EditManagerDlg inst;
	private String editid;
	/**
	 * Launch the application
	 * @param args
	 */
	public static EditManagerDlg getInstance()
	{
		if(inst==null)inst=new EditManagerDlg();
		return inst;
	}
	public void setEdit(String userid)
	{
		setTitle("Edit Manager: "+userid);
		editid=userid;
	}
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EditManagerDlg dialog = new EditManagerDlg();
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
	protected EditManagerDlg() {
		super();
		getContentPane().setLayout(null);
		setBounds(100, 100, 381, 254);

		final JLabel passwordLabel = new JLabel();
		passwordLabel.setText("Password");
		passwordLabel.setBounds(51, 62, 66, 18);
		getContentPane().add(passwordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(123, 60, 130, 22);
		getContentPane().add(passwordField);

		final JLabel confirmLabel = new JLabel();
		confirmLabel.setText("Confirm");
		confirmLabel.setBounds(51, 101, 66, 18);
		getContentPane().add(confirmLabel);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(123, 99, 130, 22);
		getContentPane().add(passwordField_1);
		final JLabel changeinfoLabel = new JLabel();
		changeinfoLabel.setForeground(Color.RED);
		changeinfoLabel.setText("changeinfo");
		changeinfoLabel.setBounds(124, 21, 232, 18);
		getContentPane().add(changeinfoLabel);
		final JButton changeButton = new JButton();
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String pwd = new String(passwordField.getPassword());
				String confir = new String(passwordField_1.getPassword());
				if (pwd.trim().equals("")) {
					changeinfoLabel.setText("Password Required");
					return;
				}
				if (!pwd.trim().equals(confir.trim())) {
					changeinfoLabel.setText("Confirm Wrong");
					return;
				}
				changeinfoLabel.setText("Adding Manager...");
				if (1 == UserDAO.getInstance().changeManagePassword(editid, pwd)) {
					changeinfoLabel.setText("Change password OK!");
					
					passwordField.setText("");
					passwordField_1.setText("");
					//updateManagers();
					return;
				}
				changeinfoLabel.setText("system error");
			}
		});
		changeButton.setText("Change");
		changeButton.setBounds(147, 156, 106, 28);
		getContentPane().add(changeButton);

		
		//
	}

}
