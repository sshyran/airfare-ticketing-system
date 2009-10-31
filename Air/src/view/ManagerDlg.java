package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import com.sun.org.apache.bcel.internal.generic.NEW;

import dao.AirPortDAO;
import dao.FlightDAO;
import dao.PlaneDAO;

public class ManagerDlg extends JDialog {

	private JComboBox land_minites;
	private JComboBox land_hour;
	private JComboBox minites;
	private JComboBox hour;
	private JSpinner price;
	private JComboBox dests;
	private JComboBox srcs;
	private JComboBox planes;
	private JTable table;
	//private MyJtable stable;
	private JComboBox seats;
	JLabel addinfo ;
	/**
	 * Launch the application
	 * @param args
	 */
	private static ManagerDlg inst; 
	public static ManagerDlg getInstance()
	{
		if(inst==null)inst=new ManagerDlg();
		return inst;
	}
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ManagerDlg dialog = new ManagerDlg();
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
	public ManagerDlg() {
		super();
		inst=this;
		getContentPane().setLayout(null);
		setTitle("Manage Panel");
		setBounds(100, 100, 600, 600);

		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.setBounds(0, 0, 584, 564);
		getContentPane().add(tabbedPane);

		final JPanel panel = new JPanel();
		panel.setLayout(null);
		tabbedPane.addTab("Add Flights", null, panel, null);

		final JLabel fromLabel = new JLabel();
		fromLabel.setBounds(32, 49, 66, 18);
		fromLabel.setText("From");
		panel.add(fromLabel);

		final JLabel toLabel = new JLabel();
		toLabel.setText("To");
		toLabel.setBounds(32, 90, 66, 18);
		panel.add(toLabel);

		final JLabel timeLabel = new JLabel();
		timeLabel.setText("Take off");
		timeLabel.setBounds(32, 134, 66, 18);
		panel.add(timeLabel);

		final JLabel priceLabel = new JLabel();
		priceLabel.setText("Price($)");
		priceLabel.setBounds(32, 174, 66, 18);
		panel.add(priceLabel);

		final JLabel planeLabel = new JLabel();
		planeLabel.setText("Plane");
		planeLabel.setBounds(32, 215, 66, 18);
		panel.add(planeLabel);

		final JSeparator separator = new JSeparator();
		separator.setBounds(30, 288, 277, 20);
		panel.add(separator);

		final JButton addButton = new JButton();
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String fromcity=srcs.getSelectedItem().toString();
				String tocity=dests.getSelectedItem().toString();
				String takeoff=hour.getSelectedIndex()+":"+minites.getSelectedIndex()+":00";
				int tof=land_hour.getSelectedIndex()*60+land_minites.getSelectedIndex();
				float pri=Float.parseFloat(price.getValue().toString());
				String planeString=planes.getSelectedItem().toString();
				int seatsnum=Integer.parseInt(seats.getSelectedItem().toString());

				FlightDAO flightDAO=new FlightDAO();
				if(1==flightDAO.addFlight(fromcity, tocity, pri, seatsnum, planeString, takeoff, tof))
					{
					addinfo.setForeground(Color.GREEN);
					addinfo.setText(fromcity+"->"+tocity+":Take off at "+takeoff+",Last "+tof+"minutes,The price is "+pri+"$,"+seatsnum+"seats in "+planeString)
						;}
				else {addinfo.setForeground(Color.RED);
					addinfo.setText("system error");
				}
			}
		});
		addButton.setText("Add");
		addButton.setBounds(201, 318, 106, 28);
		panel.add(addButton);

		final JLabel seatLabel = new JLabel();
		seatLabel.setText("Seats");
		seatLabel.setBounds(32, 255, 66, 18);
		panel.add(seatLabel);

		seats = new JComboBox();
		seats.setBounds(104, 255, 87, 27);
		panel.add(seats);
		for (int i = 1; i < 150; i++) {
			seats.addItem(i);
		}
		

		final JLabel landingLabel = new JLabel();
		landingLabel.setText("TOF");
		landingLabel.setBounds(278, 134, 40, 18);
		panel.add(landingLabel);

		planes = new JComboBox();
		planes.setBounds(104, 211, 153, 27);
		panel.add(planes);

		final JButton addPlaneButton = new JButton();
		addPlaneButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				PlaneManageDlg.getInstance().setVisible(true);
			}
		});
		addPlaneButton.setText("Planes Manage");
		addPlaneButton.setBounds(317, 210, 149, 28);
		panel.add(addPlaneButton);

		final JButton addCityButton = new JButton();
		addCityButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AirportManageDlg.getInstance().setVisible(true);
			}
		});
		addCityButton.setText("Cities Manage");
		addCityButton.setBounds(317, 44, 149, 28);
		panel.add(addCityButton);

		srcs = new JComboBox();
		srcs.setToolTipText("Click to Choose");
		srcs.setBounds(104, 45, 153, 27);
		panel.add(srcs);

		dests = new JComboBox();
		dests.setToolTipText("Click to Choose");
		dests.setBounds(104, 86, 153, 27);
		panel.add(dests);

		price = new JSpinner();
		//DecimalFormat decimalFormat=((JSpinner.NumberEditor)price.getEditor()).getFormat();
//		 JSpinner.DateEditor   editor   =   new   JSpinner.DateEditor(spinner,   
//		  "HH:mm:ss");   
		SpinnerNumberModel spinnerNumberModel=new SpinnerNumberModel(1550.00,0.00,Float.MAX_VALUE,1.00);
		
		price.setModel(spinnerNumberModel);
		//decimalFormat.setMaximumFractionDigits(2);
		//decimalFormat.setMaximumFractionDigits(2);
		price.setBounds(104, 172, 75, 22);
		price.setValue(1550.0);
		panel.add(price);
		
		hour = new JComboBox();
		hour.setBounds(105, 129, 58, 27);
		panel.add(hour);

		minites = new JComboBox();
		minites.setBounds(169, 130, 88, 27);
		panel.add(minites);

		land_hour = new JComboBox();
		land_hour.setBounds(316, 130, 58, 27);
		panel.add(land_hour);

		land_minites = new JComboBox();
		land_minites.setBounds(378, 130, 88, 27);
		panel.add(land_minites);

		addinfo = new JLabel();
		addinfo.setForeground(Color.RED);
		addinfo.setBounds(10, 367, 569, 28);
		panel.add(addinfo);

		final JButton flightsManageButton = new JButton();
		flightsManageButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				FlightsManageFra.getInstance().setVisible(true);
			}
		});
		flightsManageButton.setText("Flights Manage");
		flightsManageButton.setBounds(460, 0, 119, 28);
		panel.add(flightsManageButton);

		final JButton showBookingsButton = new JButton();
		showBookingsButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				AllBookingsDlg.getInstance().setVisible(true);
			}
		});
		showBookingsButton.setText("Show orders");
		showBookingsButton.setBounds(460, 318, 109, 28);
		panel.add(showBookingsButton);
		for(int i=0;i<60;i++)
			{minites.addItem(i+"分");
			land_minites.addItem(i+"分");
			}
		for(int i=0;i<24;i++){
		hour.addItem(i+"小时");
		land_hour.addItem(i+"小时");
		}

		//stable = new MyJtable();

		final JScrollPane scrollPane_1 = new JScrollPane();

		table = new JTable();
		scrollPane_1.setViewportView(table);
		DefaultTableModel defaultTableModel=new DefaultTableModel();
		defaultTableModel.setColumnIdentifiers(new Object[]{"00","11","22"});
		for (int i = 0; i <100; i++) 
			
		
		{	
			defaultTableModel.addRow(new Object[]{"3","11","22"});
		
		}
		defaultTableModel.addColumn("ɾ��");
		defaultTableModel.addTableModelListener(new TableModelListener(){

			@Override
			public void tableChanged(TableModelEvent e) {
				// TODO Auto-generated method stub
				System.out.println("tablec");
				System.out.println(e.getColumn()+","+e.getFirstRow()+","+e.getLastRow());
			}
			
			
		});
		//stable.setModel(defaultTableModel);
		//TableColumnModel colModel = stable.getColumnModel();
	   
	        //colModel.getColumn(1).setCellRenderer(new ButtonRenderer());
	        //colModel.getColumn(1).setCellEditor(new AbstractButton(stable));
	        table.setCellSelectionEnabled(true);
		//stable.getColumn("00").setCellEditor(new AbstractButton(stable));
		//stable.getColumn("00").setCellRenderer(new MyTR());
		
		updateCities();
		updatePlanes();
		
		//stable.setEnabled(false);
		//
	}
	public void updateCities()
	{
	
		AirPortDAO airPortDAO=new AirPortDAO();
		ArrayList<String> ret=airPortDAO.getAirports();
		srcs.removeAllItems();
		dests.removeAllItems();
		System.out.println("update cities");
		for (String t : ret) {
			
			srcs.addItem(t);
			dests.addItem(t);
		}
		dests.setSelectedIndex(1);
		
	}
	public void updatePlanes()
	{
		PlaneDAO planeDAO=new PlaneDAO();
		ArrayList<String> ret=planeDAO.getPlanes();
		planes.removeAllItems();
		//System.out.println("update cities");
		for (String t : ret) {
			
			planes.addItem(t);
		}
		
	}

}
