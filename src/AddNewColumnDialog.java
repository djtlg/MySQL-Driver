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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

public class AddNewColumnDialog extends JDialog {

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

	/**
	 * Create the panel.
	 */
	public AddNewColumnDialog() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(410, 330);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new FlowLayout());
		setUpTablePanel();
		setUpButtonPanel();
		setVisible(true);
	}

	public LinkedHashMap<String, String> getColumnNamesAndTypes() {
		return results;
	}

	private void setResults() {
		results = new LinkedHashMap<String, String>();
		for (int i = 0; i < dataTable.getModel().getRowCount(); i++) {
			results.put((String) dataTable.getModel().getValueAt(i, 0),
					(String) dataTable.getModel().getValueAt(i, 1));
		}
		results.remove(null);
	}

	private void setUpTablePanel() {
		tablePanel = new JPanel(new BorderLayout(5, 5));
		// Create the table to get input
		myTableModel = new MyTableModel();
		dataTable = new JTable(myTableModel);
		// dataTable.setAutoCreateRowSorter(true);
		dataTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		dataTable.setPreferredScrollableViewportSize(new Dimension(300, 200));
		dataTable.setFillsViewportHeight(true);
		dataTable.setRowHeight(20);
		scrollPane = new JScrollPane(dataTable);
		setUpDataTypeColumn(dataTable, dataTable.getColumnModel().getColumn(1));
		tablePanel.add(scrollPane, BorderLayout.CENTER);
		// Adds 5 more row to add data.
		moreRows = new JButton("+");
		moreRows.addActionListener(new ButtonListener());
		tablePanel.add(moreRows, BorderLayout.SOUTH);
		getContentPane().add(tablePanel);
	}

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

	private void setUpDataTypeColumn(JTable table, TableColumn sportColumn) {
		// Set up the editor for the sport cells.
		JComboBox<String> comboBox = new JComboBox<String>(DATA_TYPES);
		sportColumn.setCellEditor(new DefaultCellEditor(comboBox));
	}

	private class MyTableModel extends AbstractTableModel {

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

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == ok) {
				setResults();
				// User hit OK without Entering any Column name and type.
				if (results.isEmpty()) {
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
