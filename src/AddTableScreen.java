import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class AddTableScreen extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String[] DATA_TYPES = { "INT", "TINYINT", "SMALLINT",
			"MEDIUMINT", "BIGINT", "FLOAT", "DOUBLE", "DECIMAL", "DATE",
			"DATETIME", "TIMESTAMP", "TIME", "YEAR", "TEXT", "TINYTEXT",
			"MEDIUMTEXT", "LONGTEXT" };
	private JButton ok;
	private JButton cancel;
	private JButton reset;
	private JTable dataTable;
	private JPanel tablePanel;
	private MyTableModel myTableModel;
	private JScrollPane scrollPane;

	/**
	 * Create the panel.
	 */
	public AddTableScreen() {
		setUpTablePanel();
		setUpButtonPanel();
	}

	private void setUpTablePanel() {
		tablePanel = new JPanel(new BorderLayout());
		myTableModel = new MyTableModel();
		dataTable = new JTable(myTableModel);
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
		dataTable.setFillsViewportHeight(true);
		scrollPane = new JScrollPane(dataTable);
		setUpDataTypeColumn(dataTable, dataTable.getColumnModel().getColumn(1));
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		JButton moreRows = new JButton("+");
		// Adds 5 more row to add data.
		moreRows.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				myTableModel.addNewRows();
				scrollPane.getViewport().repaint();
				scrollPane.getViewport().revalidate();
			}
		});
		tablePanel.add(moreRows, BorderLayout.SOUTH);
		add(tablePanel);
	}

	private void setUpButtonPanel() {
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(3, 1));
		ok = new JButton("OK");
		ok.setPreferredSize(new Dimension(10, 20));
		buttonPanel.add(ok);
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		});
		buttonPanel.add(cancel);
		// Clears the table when clicked.
		reset = new JButton("Reset");
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				dataTable.getCellEditor().cancelCellEditing();
				myTableModel.data.clear();
				myTableModel.addNewRows();
				tablePanel.repaint();
			}
		});
		buttonPanel.add(reset);
		add(buttonPanel);
	}

	public void setUpDataTypeColumn(JTable table, TableColumn sportColumn) {
		// Set up the editor for the sport cells.
		JComboBox<String> comboBox = new JComboBox<String>(DATA_TYPES);
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}

	class MyTableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final String[] COLUMN_NAMES = { "Column Name", "Column Type" };
		private ArrayList<Object[]> data;

		MyTableModel() {
			super();
			data = new ArrayList<Object[]>();
			addNewRows();
		}

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public String getColumnName(int column) {
			return COLUMN_NAMES[column];
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			return data.get(arg0)[arg1];
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return true;
		}

		@Override
		public void setValueAt(Object value, int row, int col) {
			data.get(row)[col] = value;
			fireTableCellUpdated(row, col);
		}

		private void addNewRows() {
			data.add(new Object[2]);
			data.add(new Object[2]);
			data.add(new Object[2]);
			data.add(new Object[2]);
			data.add(new Object[2]);
		}
	}
}
