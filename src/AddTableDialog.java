import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         AddTableDialog class creates a dialog to interact with the user so
 *         the user can add a new table to an existing SQL database.
 * 
 */
public class AddTableDialog extends JDialog {

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
	private JButton moreRows;
	private JTable dataTable;
	private JPanel tablePanel;
	private MyTableModel myTableModel;
	private JScrollPane scrollPane;
	private LinkedHashMap<String, String> results;
	private JTextField tableName;

	/**
	 * Create the panel.
	 */
	public AddTableDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(410, 330);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new FlowLayout());
		setUpTablePanel();
		setUpButtonPanel();
		setVisible(true);
	}

	/**
	 * Returns the name for the new table that is going to be added to the
	 * database.
	 * 
	 * @return the name for the new table.
	 */
	public String getTableName() {
		return tableName.getText();
	}

	/**
	 * This method returns LinkedHashMap loaded with the column names and their
	 * types that the user entered.
	 * 
	 * @return LinkedHashMap <K,V> K - The name of the columns the user entered.
	 *         V - The type of the column the user entered.
	 */
	public LinkedHashMap<String, String> getColumnNamesAndTypes() {
		return results;
	}

	/**
	 * Loads the user inputs in to the LinkedHashMap.
	 */
	private void setResults() {
		results = new LinkedHashMap<String, String>();
		for (int i = 0; i < dataTable.getModel().getRowCount(); i++) {
			results.put((String) dataTable.getModel().getValueAt(i, 0),
					(String) dataTable.getModel().getValueAt(i, 1));
		}
		results.remove(null);
	}

	/**
	 * Creates a JPanel that is consist of the table name, input table and the
	 * "+" button that is beneath it and adds it to the content pane.
	 */
	private void setUpTablePanel() {
		tablePanel = new JPanel(new BorderLayout(5, 5));
		// Create the top panel for table name entry
		JPanel tableNamePanel = new JPanel(new BorderLayout());
		JLabel tableNameLabel = new JLabel("Table Name: ");
		tableNamePanel.add(tableNameLabel, BorderLayout.WEST);
		tableName = new JTextField();
		tableNamePanel.add(tableName, BorderLayout.CENTER);
		tablePanel.add(tableNamePanel, BorderLayout.NORTH);
		// Create the table to get input
		myTableModel = new MyTableModel();
		dataTable = new JTable(myTableModel);
		// dataTable.setAutoCreateRowSorter(true);
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
		dataTable.setFillsViewportHeight(true);
		dataTable.setRowHeight(20);
		scrollPane = new JScrollPane(dataTable);
		setUpDataTypeColumn(dataTable.getColumnModel().getColumn(1));
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		// Adds 5 more row to add data.
		moreRows = new JButton("+");
		moreRows.addActionListener(new ButtonListener());
		tablePanel.add(moreRows, BorderLayout.SOUTH);
		getContentPane().add(tablePanel);
	}

	/**
	 * Creates a JPanel that is consist of the OK, Cancel and Reset buttons and
	 * adds it to the content pane.
	 */
	private void setUpButtonPanel() {
		JPanel buttonPanel = new JPanel(new GridLayout(3, 1));
		ok = new JButton("OK");
		ok.setPreferredSize(new Dimension(10, 20));
		ok.addActionListener(new ButtonListener());
		buttonPanel.add(ok);
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ButtonListener());
		buttonPanel.add(cancel);
		// Clears the table when clicked.
		reset = new JButton("Reset");
		reset.addActionListener(new ButtonListener());
		buttonPanel.add(reset);
		getContentPane().add(buttonPanel);
	}

	/**
	 * Sets up the column on the table that displays the data types as dropbox.
	 * 
	 * @param typeColumn
	 */
	private void setUpDataTypeColumn(TableColumn typeColumn) {
		// Set up the editor for the sport cells.
		JComboBox<String> comboBox = new JComboBox<String>(DATA_TYPES);
		typeColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}

	/**
	 * The custom table model that the table is based on.
	 */
	private class MyTableModel extends AbstractTableModel {

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
		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int row, int col) {
			return data.get(row)[col];
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

	/**
	 * Listens for the user clicks on ok, cancel, reset and "+" buttons and
	 * reacts accordingly.
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == ok) {
				setResults();
				// User hit Ok without Entering any data to the dialog.
				if (results.isEmpty() && tableName.getText().isEmpty()) {
					JOptionPane
							.showMessageDialog(
									null,
									"Please Enter Table Name, Colum Names and Colum Types.",
									"Error ...", JOptionPane.ERROR_MESSAGE);
				}
				// User hit OK without Entering a table name.
				else if (tableName.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please Enter Table Name.", "Error ...",
							JOptionPane.ERROR_MESSAGE);
				}
				// User hit OK without Entering any Column name and type.
				else if (results.isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please Enter Colum Names and Colum Types.",
							"Error ...", JOptionPane.ERROR_MESSAGE);
				} else {
					setVisible(false);
					dispose();
				}
			}
			if (arg0.getSource() == cancel) {
				dispose();
			}
			if (arg0.getSource() == reset) {
				if (dataTable.getCellEditor() != null)
					dataTable.getCellEditor().cancelCellEditing();
				myTableModel.data.clear();
				myTableModel.addNewRows();
				tablePanel.repaint();
			}
			if (arg0.getSource() == moreRows) {
				myTableModel.addNewRows();
				scrollPane.getViewport().repaint();
				scrollPane.getViewport().revalidate();
			}
		}

	}
}
