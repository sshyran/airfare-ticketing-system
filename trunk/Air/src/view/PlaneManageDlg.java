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
import dao.PlaneDAO;
import dao.UserDAO;

public class PlaneManageDlg extends JDialog {

	private JTextField username_TF;
	private JTable mtable;
	private JTextField filtertext;
	private JLabel addminfo;
	private JCheckBox[] boxs;
	private ArrayList<String> names;
	private JLabel deleinfo;
	private DefaultTableModel defaultTableModel;
	private static PlaneManageDlg inst;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static PlaneManageDlg getInstance() {
		if (inst == null)
			inst = new PlaneManageDlg();
		return inst;
	}

	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PlaneManageDlg dialog = new PlaneManageDlg();
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
	public PlaneManageDlg() {
		super();
		inst=this;
		getContentPane().setLayout(null);
		setTitle("Planes Manage(\u673A\u578B\u7BA1\u7406)");
		setBounds(100, 100, 600, 600);

		final JLabel filterLabel = new JLabel();
		filterLabel.setText("Filter of Planes");
		filterLabel.setBounds(10, 10, 108, 18);
		getContentPane().add(filterLabel);

		final JLabel nameLabel = new JLabel();
		nameLabel.setText("Planes Type");
		nameLabel.setBounds(10, 45, 82, 18);
		getContentPane().add(nameLabel);

		filtertext = new JTextField();
		filtertext.setBounds(98, 43, 73, 22);
		getContentPane().add(filtertext);

		final JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 285, 358);
		getContentPane().add(scrollPane);

		mtable = new ManagerTable();
		defaultTableModel = new DefaultTableModel();
		defaultTableModel.setColumnIdentifiers(new String[] { "Name",
				"Selected" });
		mtable.setModel(defaultTableModel);

		TableColumnModel columnModel = mtable.getColumnModel();
		columnModel.getColumn(1).setCellEditor(new CheckBox(new JCheckBox()));
		columnModel.getColumn(1).setCellRenderer(new CheckBoxRender());

		scrollPane.setViewportView(mtable);

		final JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(301, 45, 15, 509);
		getContentPane().add(separator);

		final JLabel adminstratorLabel = new JLabel();
		adminstratorLabel.setText("Add new Type");
		adminstratorLabel.setBounds(305, 10, 89, 18);
		getContentPane().add(adminstratorLabel);

		final JLabel nameLabel_1 = new JLabel();
		nameLabel_1.setText("Plane type");
		nameLabel_1.setBounds(333, 45, 66, 18);
		getContentPane().add(nameLabel_1);

		username_TF = new JTextField();
		username_TF.setBounds(405, 43, 161, 22);
		getContentPane().add(username_TF);

		final JButton addAManageButton = new JButton();
		addAManageButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				String user = username_TF.getText().trim();
				if (user.equals("")) {
					addminfo.setText("Name Required");
					return;
				}
				PlaneDAO planeDAO=new PlaneDAO();
				if(planeDAO.isExistPlane(user)){
					addminfo.setText("Plane already exists!");
					return;
				}
				if(1==planeDAO.addPlane(user))
					{addminfo.setText("Add "+user+" Ok!");
						username_TF.setText("");
						updateManagers();
					}
				else addminfo.setText("SQL WRONG!");
			}
		});
		addAManageButton.setText("Add a Plane");
		addAManageButton.setBounds(431, 301, 114, 28);
		getContentPane().add(addAManageButton);

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
		deleteSelectedButton.setBounds(145, 473, 123, 28);
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
		allCheckBox.setBounds(10, 473, 39, 26);
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
		exAllCheckBox.setBounds(53, 473, 73, 26);
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
		deleinfo.setBounds(144, 518, 124, 18);
		getContentPane().add(deleinfo);
		//
	}

	private int deleteSelected() {
		ArrayList<String> deletes = new ArrayList<String>();
		for (int i = 0; i < boxs.length; i++)
			if (boxs[i].isSelected())
				deletes.add(names.get(i));
		PlaneDAO planeDAO=new PlaneDAO();
		return planeDAO.deletePlanes(deletes);
	}

	private void updateManagers() {

		filtertext.setText("");
		mtable.setRowSorter(null);
		PlaneDAO planeDAO=new PlaneDAO();
		names = planeDAO.getPlanes();
		boxs = new JCheckBox[names.size()];
		defaultTableModel.setNumRows(0);
		for (int j = 0; j < names.size(); j++) {
			boxs[j] = new JCheckBox();

			defaultTableModel.addRow(new Object[] { names.get(j), boxs[j] });
		}
		ManagerDlg.getInstance().updatePlanes();
	}

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
