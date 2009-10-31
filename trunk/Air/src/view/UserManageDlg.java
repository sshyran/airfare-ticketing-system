package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.ComboBoxEditor;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import dao.AirPortDAO;
import dao.BookingDAO;
import dao.FlightDAO;
import dao.UserDAO;

public class UserManageDlg extends JDialog {

	private MyTable historytable;
	private JComboBox days;
	private JComboBox month;
	private JComboBox year;
	private JComboBox dests;
	private JComboBox srcs;
	private JTable btable;
	// private JTable table;
	private JTextArea address;
	private JTextField phone;
	private JTextField email;
	private JPasswordField passwordField_1;
	private JPasswordField passwordField;
	private DefaultTableModel defaultTableModel;
	private DefaultTableModel historyModel;
	final JLabel changeinfo;
	private JCheckBox[] boxs;
	//private JTextField[] textFields;
	private JLabel  addinfo;
	private ArrayList<String> selectedflights;
	private JTabbedPane tabbedPane; 
	private JLabel priceinfo ;
	private boolean isvisitorModel;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	private static UserManageDlg inst;

	public static UserManageDlg getInstance() {
		if (inst == null)
			inst = new UserManageDlg();
		return inst;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserManageDlg dialog = new UserManageDlg();
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
	public void setVistorModel()
	{
		tabbedPane.setEnabledAt(0, false);
		
		tabbedPane.setSelectedIndex(2);
		isvisitorModel=true;
	}
	/**
	 * Create the dialog
	 */
	public UserManageDlg() {
		super();
		inst = this;
		addWindowListener(new WindowAdapter() {
			public void windowClosed(final WindowEvent e) {
				System.out.println("Window Closed");
			}

			public void windowClosing(final WindowEvent e) {
				System.out.println("Window Closing");
			}
		});
		setTitle("User Account Manage");
		getContentPane().setLayout(null);
		setBounds(100, 100, 700, 600);

		tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 10, 684, 554);
		getContentPane().add(tabbedPane);
		
		final JPanel panel = new JPanel();
		panel.setLayout(null);
		tabbedPane.addTab("Profile", null, panel, null);

		final JLabel username = new JLabel();
		username.setBounds(10, 24, 66, 18);
		panel.add(username);

		final JLabel changePasswordLabel = new JLabel();
		changePasswordLabel.setText("Change Password");
		changePasswordLabel.setBounds(10, 61, 115, 18);
		panel.add(changePasswordLabel);

		passwordField = new JPasswordField();
		passwordField.setBounds(138, 59, 150, 22);
		panel.add(passwordField);

		final JLabel confirmLabel = new JLabel();
		confirmLabel.setText("Confirm");
		confirmLabel.setBounds(10, 96, 66, 18);
		panel.add(confirmLabel);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(138, 94, 150, 22);
		panel.add(passwordField_1);

		final JButton changeButton = new JButton();
		changeButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String pwd = new String(passwordField.getPassword());
				String confir = new String(passwordField_1.getPassword());
				if (pwd.trim().equals("")) {
					changeinfo.setText("Password Required");
					return;
				}
				if (!pwd.trim().equals(confir.trim())) {
					changeinfo.setText("Confirm Wrong");
					return;
				}
				addinfo.setText("Adding Manager...");
				if (1 == UserDAO.getInstance().changeUserPassword(UserDAO.userid, pwd)) {
					changeinfo.setText("Change password OK!");
					
					passwordField.setText("");
					passwordField_1.setText("");
					//updateManagers();
					return;
				}
				changeinfo.setText("system error");
				
			}
		});
		changeButton.setText("Change");
		changeButton.setBounds(339, 129, 88, 28);
		panel.add(changeButton);

		final JSeparator separator = new JSeparator();
		separator.setBounds(10, 163, 469, 20);
		panel.add(separator);

		final JLabel informationLabel = new JLabel();
		informationLabel.setText("Information");
		informationLabel.setBounds(20, 189, 81, 18);
		panel.add(informationLabel);

		final JLabel emailLabel = new JLabel();
		emailLabel.setText("Email");
		emailLabel.setBounds(20, 227, 66, 18);
		panel.add(emailLabel);

		email = new JTextField();
		email.setBounds(138, 225, 199, 22);
		panel.add(email);

		final JLabel addressLabel = new JLabel();
		addressLabel.setText("Phone");
		addressLabel.setBounds(20, 262, 66, 18);
		panel.add(addressLabel);

		phone = new JTextField();
		phone.setBounds(138, 260, 199, 22);
		panel.add(phone);

		final JLabel addessLabel = new JLabel();
		addessLabel.setText("Addess");
		addessLabel.setBounds(20, 299, 66, 18);
		panel.add(addessLabel);

		address = new JTextArea();
		address.setBounds(138, 306, 199, 59);
		panel.add(address);
		final JLabel updateinfo = new JLabel();
		updateinfo.setForeground(Color.RED);
		updateinfo.setBounds(138, 189, 199, 18);
		panel.add(updateinfo);
		final JButton updateButton = new JButton();
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if(1==UserDAO.getInstance().updateUserInfo(UserDAO.userid, email.getText().trim(), 
						phone.getText().trim(), address.getText().trim()))
					{
					updateinfo.setText("Update OK");
					}
				else updateinfo.setText("system error");
			}
		});
		updateButton.setText("Update");
		updateButton.setBounds(339, 371, 88, 28);
		panel.add(updateButton);

		 changeinfo = new JLabel();
		changeinfo.setForeground(Color.RED);
		changeinfo.setBounds(138, 24, 150, 18);
		panel.add(changeinfo);

		final JButton deleteMyAccountButton = new JButton();
		deleteMyAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				int ret=JOptionPane.showConfirmDialog(UserManageDlg.getInstance(), "Confirm to delete account?");
				if(ret==JOptionPane.OK_OPTION)
				{
					System.out.println("dd");
					UserDAO userDAO=UserDAO.getInstance();
					if(1==userDAO.deleteUser(UserDAO.userid))
					{
						System.exit(0);
					}else{
						JOptionPane.showMessageDialog(UserManageDlg.getInstance(),"User Not Exist");
						
					}
				}
			}
		});
		deleteMyAccountButton.setText("Delete My Account");
		deleteMyAccountButton.setBounds(19, 486, 139, 28);
		panel.add(deleteMyAccountButton);

		final JLabel userinfo = new JLabel();
		userinfo.setForeground(Color.RED);
		userinfo.setBounds(10, 24, 139, 18);
		panel.add(userinfo);
		
		userinfo.setText("User:  "+UserDAO.userid);

		
		final JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		tabbedPane.addTab("History", null, panel_1, null);
		
		final JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 679, 483);
		panel_1.add(scrollPane_1);

		historytable = new MyTable();
		historytable.setRowHeight(30);
		scrollPane_1.setViewportView(historytable);
		historyModel=new DefaultTableModel();
		historyModel.setColumnIdentifiers(new String[]{"Date","Flight_ID","Take off time"
				,"TOF","Seat Num","From","To","Price","Plane Type"});
		historytable.setModel(historyModel);

		final JButton flushButton = new JButton();
		flushButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				updateHistory();
			}
		});
		flushButton.setText("Flush");
		flushButton.setBounds(563, 490, 106, 28);
		panel_1.add(flushButton);

		priceinfo = new JLabel();
		priceinfo.setBounds(17, 495, 215, 18);
		panel_1.add(priceinfo);
		// table = new JTable();
		// table.setBounds(0, 10, 479, 414);

		final JPanel panel_2 = new JPanel();
		panel_2.setPreferredSize(new Dimension(600, 700));
		panel_2.setLayout(null);
		tabbedPane.addTab("Booking", null, panel_2, null);
		
		final JLabel fromLabel = new JLabel();
		fromLabel.setText("From");
		fromLabel.setBounds(10, 34, 66, 18);
		panel_2.add(fromLabel);

		final JLabel toLabel = new JLabel();
		toLabel.setText("To");
		toLabel.setBounds(153, 34, 66, 18);
		panel_2.add(toLabel);

		final JLabel dateLabel = new JLabel();
		dateLabel.setText("Date");
		dateLabel.setBounds(10, 74, 66, 18);
		panel_2.add(dateLabel);

		final JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 113, 669, 20);
		panel_2.add(separator_1);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 139, 659, 341);
		panel_2.add(scrollPane);

		btable = new ManagerTable();
		scrollPane.setViewportView(btable);

		defaultTableModel = new DefaultTableModel();
		defaultTableModel.setColumnIdentifiers(new String[] { "FIGHT_ID",
				"TOF(Minuites)", "TAKE OFF", "Total_SEATS", "Price($)", "FROM", "TO",
				"PLANE", "Remain", "Selected","Num" });
		btable.setModel(defaultTableModel);
		btable.setRowHeight(30);
		TableColumnModel columnModel = btable.getColumnModel();
		columnModel.getColumn(9).setCellEditor(new MyCheckBox(new JCheckBox()));
		columnModel.getColumn(9).setCellRenderer(new CheckBoxRender());
		// columnModel.getColumn(9).setCellRenderer(new TextFieldRender());
		// columnModel.getColumn(9).setCellEditor(new MyComboEditor(new
		// JComboBox()));

		TableColumn column = btable.getColumnModel().getColumn(10);

		JComboBox comboBox = new JComboBox();
		for (int i = 0; i < 30; i++)
			comboBox.addItem(i);
		comboBox.setToolTipText("Click to Change");
		column.setCellEditor(new DefaultCellEditor(comboBox));
		// column.setCellRenderer(new )
		// If the cell should appear like a combobox in its
		// non-editing state, also set the combobox renderer

		srcs = new JComboBox();
		srcs.setBounds(53, 30, 79, 27);
		panel_2.add(srcs);

		dests = new JComboBox();
		dests.setBounds(201, 30, 79, 27);
		panel_2.add(dests);

		year = new JComboBox();
		year.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				setDays();
			}
		});
		year.setBounds(53, 70, 66, 27);
		panel_2.add(year);

		month = new JComboBox();
		month.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				setDays();
			}
		});
		month.setBounds(129, 70, 79, 27);
		panel_2.add(month);

		days = new JComboBox();
		days.setBounds(214, 70, 66, 27);
		panel_2.add(days);
		for (int i = 2009; i < 2020; i++)
			year.addItem(i + "年");
		for (int i = 1; i <= 12; i++)
			month.addItem(i + "月");
		for (int i = 1; i <= 31; i++)
			days.addItem(i + "日");
		days.setSelectedIndex(0);
		final JButton submitButton = new JButton();
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				bookingTickets();
			}
		});
		submitButton.setText("Submit");
		submitButton.setBounds(563, 486, 106, 28);
		panel_2.add(submitButton);

		final JButton showButton = new JButton();
		showButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				updateData(false);
			}
		});
		showButton.setText("Show");
		showButton.setBounds(314, 69, 106, 28);
		panel_2.add(showButton);

		final JButton showAllButton = new JButton();
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				updateData(true);
			}
		});
		showAllButton.setText("Show All Flights");
		showAllButton.setBounds(487, 69, 123, 28);
		panel_2.add(showAllButton);

		addinfo = new JLabel();
		addinfo.setForeground(Color.RED);
		addinfo.setBounds(352, 491, 194, 18);
		panel_2.add(addinfo);

		updateCities();
		//
	}

	private void setDays() {
		int selected = days.getSelectedIndex();
		days.removeAllItems();

		Calendar calendar = Calendar.getInstance();
		calendar.set(year.getSelectedIndex() + 2009, month.getSelectedIndex(),
				1);

		int max = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		for (int i = 1; i <= max; i++)
			days.addItem(i + "��");

		try {
			days.setSelectedIndex(selected);
		} catch (IndexOutOfBoundsException e) {

			// TODO: handle exception
		} catch (IllegalArgumentException e) {
			days.setSelectedIndex(0);
			// TODO: handle exception
		}
		// System.out.println((year.getSelectedIndex()+2009)+","+month.getSelectedIndex());
	}
	private String getDate()
	{
		StringBuffer buffer = new StringBuffer();
		buffer.append((year.getSelectedIndex() + 2009) + "-");
		buffer.append((month.getSelectedIndex() + 1) + "-");
		buffer.append(days.getSelectedIndex() + 1);
		return buffer.toString();
	}
	private void bookingTickets() {
		if (defaultTableModel.getRowCount() == 0)
			return;
		if(isvisitorModel)
		{
			OrderDlg.getInstance().setVisible(true);
		}
		int sure=JOptionPane.showConfirmDialog(this, "Sure to booking selected flight(s)?");
		if(sure!=JOptionPane.OK_OPTION)return;
		ArrayList<String> deletes = new ArrayList<String>();
		BookingDAO bookingDAO = new BookingDAO();
		int sum=0;
		for (int i = 0; i < boxs.length; i++)
			if (boxs[i].isSelected()) {
				deletes.add(selectedflights.get(i));
				String flightid = btable.getValueAt(i, 0).toString();
				
				sum+=bookingDAO.bookingTicket(Integer.parseInt(flightid),
						UserDAO.userid, Integer.parseInt(btable
								.getValueAt(i, 10).toString()),getDate());
				System.out.println(btable.getValueAt(i, 10) + "," + flightid
						+ "," + getDate());
				boxs[i].setSelected(false);

			}
		btable.repaint();
		addinfo.setText("Booking "+sum+" tickets OK!");
		
	}
	private void updateHistory()
	{
		if(UserDAO.userid==null)return;
		FlightDAO flightDAO = new FlightDAO();

		ArrayList<String[]> resultSet=flightDAO.getFlightsOf(UserDAO.userid);
		historyModel.setNumRows(0);
		int colums=9;
		float sum=0;
		for (int j = 0; j < resultSet.size(); j++) {
			
			Object[] object = new Object[colums];
			for (int i = 0; i < colums; i++)
				object[i] = ((String[]) resultSet.get(j))[i];
			try {
				sum+=Float.parseFloat(object[7].toString());
			} catch (Exception e) {
				// TODO: handle exception
			}
			
			historyModel.addRow(object);
			
		}
		priceinfo.setText("Total:  "+sum+"($)");

	}
	private void updateCities() {

		AirPortDAO airPortDAO = new AirPortDAO();
		ArrayList<String> ret = airPortDAO.getAirports();
		srcs.removeAllItems();
		dests.removeAllItems();
		System.out.println("update cities");
		for (String t : ret) {

			srcs.addItem(t);
			dests.addItem(t);
		}

	}

	private void updateData(boolean isall) {
		FlightDAO flightDAO = new FlightDAO();

		ArrayList<String[]> resultSet=null;
		
		if (isall)
			{resultSet = flightDAO.getRemainFlights();
			
			}
		else
			{
			BookingDAO bookingDAO=new BookingDAO();

			resultSet=flightDAO.getFlightsBetweenWithoutDate(srcs.getSelectedItem()
					.toString(), dests.getSelectedItem().toString());
			}

		boxs = new JCheckBox[resultSet.size()];
		//textFields = new JTextField[resultSet.size()];
		selectedflights = new ArrayList<String>(resultSet.size());
		defaultTableModel.setNumRows(0);
		for (int j = 0; j < resultSet.size(); j++) {
			boxs[j] = new JCheckBox();
			//textFields[j] = new JTextField();
			Object[] object = new Object[11];
			for (int i = 0; i < 9; i++)
				object[i] = ((String[]) resultSet.get(j))[i];
			selectedflights.add(((String[]) resultSet.get(j))[0]);
			System.out.println("reget:"+object[0]);
			object[8]=flightDAO.getRemainSeats(getDate(),Integer.parseInt(object[0].toString()));
			object[9] = boxs[j];
			object[10] = 0;
			defaultTableModel.addRow(object);
			System.out.println(j);
		}

	}

	class ManagerTable extends JTable {

		public boolean isCellEditable(int row, int column) {
			if (column <= 8)
				return false;

			return true;
		}
	}

	class MyComboEditor extends DefaultCellEditor implements ItemListener {

		public MyComboEditor(JComboBox comboBox) {
			super(comboBox);
			// TODO Auto-generated constructor stub
		}

		private JComboBox comboBox;

		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			this.fireEditingStopped();
			System.out.println("edit.......");

		}

		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			// TODO Auto-generated method stub
			// checkBox.setSelected(isSelected);
			// System.out.println("editor");
			comboBox = (JComboBox) value;
			comboBox.addItemListener(this);
			return comboBox;
		}

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			System.out.println("value");
			// return null;
			return comboBox;
		}

		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			this.fireEditingStopped();
			System.out.println(e.getStateChange());
		}

	}

	class MyCheckBox extends DefaultCellEditor implements ItemListener {

		public MyCheckBox(JCheckBox checkBox) {
			super(checkBox);
			// TODO Auto-generated constructor stub
		}

		private JCheckBox checkBox;

		@Override
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			// TODO Auto-generated method stub
			// checkBox.setSelected(isSelected);
			System.out.println("editor");
			checkBox = (JCheckBox) value;
			checkBox.addItemListener(this);
			return checkBox;
		}

		@Override
		public Object getCellEditorValue() {
			// TODO Auto-generated method stub
			System.out.println("value");
			// return null;
			return checkBox;
		}

		@Override
		public void itemStateChanged(ItemEvent e) {
			// TODO Auto-generated method stub
			super.fireEditingStopped();
			System.out.println("check box:" + checkBox.isSelected());
		}

	}

	class CheckBoxRender implements TableCellRenderer {
		JCheckBox checkBox = new JCheckBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			// checkBox.setSelected(isSelected);
			// System.out.println("render");
			return (Component) value;
		}

	}

	class TextFieldRender implements TableCellRenderer {
		private JComboBox field = new JComboBox();

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			// TODO Auto-generated method stub
			return field;
		}

	}
	

}
