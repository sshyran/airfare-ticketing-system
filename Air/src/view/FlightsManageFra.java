package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
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
import dao.FlightDAO;
import dao.UserDAO;

public class FlightsManageFra extends JDialog {

	private JTable mtable;
	private JTextField filtertext;
	private JLabel addminfo;
	private JCheckBox[] boxs;
	private ArrayList<String> names;
	private JLabel deleinfo;
	private DefaultTableModel defaultTableModel;
	private static FlightsManageFra inst;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static FlightsManageFra getInstance() {
		if (inst == null)
			inst = new FlightsManageFra();
		return inst;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FlightsManageFra dialog = new FlightsManageFra();
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
	public FlightsManageFra() {
		super();
		inst = this;
		getContentPane().setLayout(null);
		setTitle("Airports Manage");
		setBounds(100, 100, 600, 700);

		final JLabel filterLabel = new JLabel();
		filterLabel.setText("Filter of Cities");
		filterLabel.setBounds(10, 10, 108, 18);
		getContentPane().add(filterLabel);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("Airport Name");
		nameLabel.setBounds(10, 45, 82, 18);
		getContentPane().add(nameLabel);

		filtertext = new JTextField();
		filtertext.setBounds(98, 43, 73, 22);
		getContentPane().add(filtertext);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 564, 485);
		getContentPane().add(scrollPane);

		mtable = new ManagerTable();
		mtable.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if (2 == e.getClickCount()) {
					System.out.println("double clicks");
					int row = mtable.rowAtPoint(e.getPoint());
					int column = mtable.columnAtPoint(e.getPoint());
					int flightid = Integer.parseInt(mtable.getValueAt(row, 0).toString());
					System.out.println(row + "," + column + "," + flightid);
					EditFlightDlg.getInstance().setVisible(true);
					EditFlightDlg.getInstance().setEditing(flightid);
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

		});
		defaultTableModel = new DefaultTableModel();
		defaultTableModel.setColumnIdentifiers(new String[] { "FIGHT_ID",
				"TOF(Minuites)", "TAKE OFF", "SEATS", "Price($)", "FROM", "TO",
				"PLANE", "Selected" });
		mtable.setModel(defaultTableModel);

		TableColumnModel columnModel = mtable.getColumnModel();
		columnModel.getColumn(8).setCellEditor(new CheckBox(new JCheckBox()));
		columnModel.getColumn(8).setCellRenderer(new CheckBoxRender());

		scrollPane.setViewportView(mtable);

		final JButton goButton = new JButton();
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.out.println(filtertext.getText());
				TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(
						defaultTableModel);

				mtable.setRowSorter(sorter);

				sorter.setRowFilter(RowFilter.regexFilter(filtertext.getText()
						.trim()));

			}
		});
		goButton.setText("Go");
		goButton.setBounds(177, 43, 58, 22);
		getContentPane().add(goButton);

		addminfo = new JLabel();
		addminfo.setForeground(Color.RED);
		addminfo.setBounds(405, 162, 161, 18);
		getContentPane().add(addminfo);
		updateManagers();

		final JButton deleteSelectedButton = new JButton();
		deleteSelectedButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				deleinfo.setText(deleteSelected() + " items Deleted!");
				updateManagers();
			}
		});
		deleteSelectedButton.setText("Delete Selected");
		deleteSelectedButton.setBounds(138, 596, 123, 28);
		getContentPane().add(deleteSelectedButton);

		final JCheckBox allCheckBox = new JCheckBox();
		final JCheckBox exAllCheckBox = new JCheckBox();
		allCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					for (JCheckBox jcb : boxs) {
						jcb.setSelected(true);

					}

					mtable.repaint();
					exAllCheckBox.setSelected(false);
				}
			}
		});

		allCheckBox.setText("All");
		allCheckBox.setBounds(10, 597, 39, 26);
		getContentPane().add(allCheckBox);

		exAllCheckBox.addItemListener(new ItemListener() {
			public void itemStateChanged(final ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					for (JCheckBox jcb : boxs) {
						jcb.setSelected(false);

					}

					mtable.repaint();
					allCheckBox.setSelected(false);
				}

			}
		});

		exAllCheckBox.setText("Ex All");
		exAllCheckBox.setBounds(55, 597, 58, 26);
		getContentPane().add(exAllCheckBox);

		final JButton flushButton = new JButton();
		flushButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				updateManagers();
			}
		});
		flushButton.setMargin(new Insets(2, 0, 2, 0));
		flushButton.setText("Flush");
		flushButton.setBounds(241, 43, 54, 22);
		getContentPane().add(flushButton);

		deleinfo = new JLabel();
		deleinfo.setForeground(Color.GREEN);
		deleinfo.setBounds(118, 636, 124, 18);
		getContentPane().add(deleinfo);

		final JLabel doubleClickToLabel = new JLabel();
		doubleClickToLabel.setForeground(Color.RED);
		doubleClickToLabel.setText("double clicks to Edit!");
		doubleClickToLabel.setBounds(312, 45, 262, 18);
		getContentPane().add(doubleClickToLabel);
		//
	}

	private int deleteSelected() {
		ArrayList<String> deletes = new ArrayList<String>();
		for (int i = 0; i < boxs.length; i++)
			if (boxs[i].isSelected())
				deletes.add(names.get(i));
		FlightDAO flightDAO = new FlightDAO();
		return flightDAO.deleteFlights(deletes);
	}

	private void updateManagers() {

		filtertext.setText("");
		mtable.setRowSorter(null);
		FlightDAO flightDAO = new FlightDAO();

		ArrayList<String[]> resultSet = flightDAO.getFlights();

		boxs = new JCheckBox[resultSet.size()];
		names = new ArrayList<String>(resultSet.size());
		defaultTableModel.setNumRows(0);
		for (int j = 0; j < resultSet.size(); j++) {
			boxs[j] = new JCheckBox();
			Object[] object = new Object[9];
			for (int i = 0; i < 8; i++)
				object[i] = ((String[]) resultSet.get(j))[i];
			names.add(((String[]) resultSet.get(j))[0]);
			object[8] = boxs[j];
			defaultTableModel.addRow(object);
			System.out.println(j);
		}

	}

	class ManagerTable extends JTable {

		public boolean isCellEditable(int row, int column) {
			if (column == 8)
				return true;

			return false;
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
