package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;

import javax.swing.AbstractCellEditor;
import javax.swing.ButtonGroup;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dao.AirLineDAO;
import dao.AirPortDAO;
import dao.BookingDAO;
import dao.UserDAO;

public class AllBookingsDlg extends JDialog {

	private JTable mtable;
	private JLabel addminfo;
	private JCheckBox[] boxs;
	private ArrayList<String[]> names;
	private JLabel deleinfo;
	private DefaultTableModel defaultTableModel;
	private static AllBookingsDlg inst;
	private final JLabel suminfo;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static AllBookingsDlg getInstance() {
		if (inst == null)
			inst = new AllBookingsDlg();
		return inst;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AllBookingsDlg dialog = new AllBookingsDlg();
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
	public AllBookingsDlg() {
		super();
		inst=this;
		getContentPane().setLayout(null);
		setTitle("Sum");
		setBounds(100, 100, 600, 600);

		final JLabel filterLabel = new JLabel();
		filterLabel.setText("¶©Æ±»ã×Ü");
		filterLabel.setBounds(10, 10, 108, 18);
		getContentPane().add(filterLabel);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 34, 564, 408);
		getContentPane().add(scrollPane);

		mtable = new ManagerTable();
		defaultTableModel = new DefaultTableModel();
		defaultTableModel.setColumnIdentifiers(new String[] { "User ID",
				"Flight ID","Address","Phone","EMAIL","PRICE","ORDER TIME" });
		mtable.setModel(defaultTableModel);

//		TableColumnModel columnModel = mtable.getColumnModel();
//		columnModel.getColumn(1).setCellEditor(new CheckBox(new JCheckBox()));
//		columnModel.getColumn(1).setCellRenderer(new CheckBoxRender());

		scrollPane.setViewportView(mtable);

		addminfo = new JLabel();
		addminfo.setForeground(Color.RED);
		addminfo.setBounds(405, 162, 161, 18);
		getContentPane().add(addminfo);
		

		deleinfo = new JLabel();
		deleinfo.setForeground(Color.GREEN);
		deleinfo.setBounds(144, 518, 124, 18);
		getContentPane().add(deleinfo);

		suminfo = new JLabel();
		suminfo.setBounds(10, 469, 170, 18);
		getContentPane().add(suminfo);
		updateManagers();
		//
	}
	private void updateManagers() {

		
		BookingDAO bookingDAO=new BookingDAO();
		names = bookingDAO.getBookings(null);
		//boxs = new JCheckBox[names.size()];
		defaultTableModel.setNumRows(0);
		for (int j = 0; j < names.size(); j++) {
			//boxs[j] = new JCheckBox();
			Object[] objects=new Object[7];
			for(int i=0;i<7;i++)
				objects[i]=((String[])names.get(j))[i];
			defaultTableModel.addRow(objects);
		}
		
		suminfo.setText("Total Items: "+names.size());
		//ManagerDlg.getInstance().updateCities();

	}
//	private int deleteSelected() {
//		ArrayList<String> deletes = new ArrayList<String>();
//		for (int i = 0; i < boxs.length; i++)
//			if (boxs[i].isSelected())
//				deletes.add(names.get(i));
//		AirPortDAO airLineDAO=new AirPortDAO();
//		return airLineDAO.deleteAirports(deletes);
//	}


	class ManagerTable extends JTable {

		public boolean isCellEditable(int row, int column) {
			if (column == 0)
				return false;

			return true;
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

	class CheckBox extends DefaultCellEditor implements ItemListener {

		public CheckBox(JCheckBox checkBox) {
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
}
