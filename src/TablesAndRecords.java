import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         TablesAndRecord class displays the list of tables in a database and
 *         the data of the table that is chosen by the user.
 * 
 */
public class TablesAndRecords extends JPanel {

	public TablesAndRecords() {
		setLayout(new BorderLayout());
		setVerticalSplitPane();
		setTopNavigationPanel();
		setHorizontalSplitPane();
	}

	/**
	 * Loads the JList with the names of the tables in the database.
	 * 
	 * @param tableList
	 *            An ArrayList that contains the names of the tables.
	 */
	public void setTableList(ArrayList<String> tableList) {
		this.tableList.removeAllElements();
		tableList.trimToSize();
		for (int i = 0; i < tableList.size(); i++)
			this.tableList.addElement(tableList.get(i));
		list.setModel(this.tableList);
		list.setSelectedIndex(0);
	}

	/**
	 * Returns the user table choice from the table list.
	 * 
	 * @return A string that contains the name of the table that user chose.
	 */
	public String getTableChoice() {
		return (String) list.getSelectedValue();
	}

	/**
	 * Loads the table that displays the column names and the column data.
	 * 
	 * @param columnHeaders
	 *            A Vector that contains the names of the columns in the table.
	 * @param tableContent
	 *            A Vector that contains the data of the table.
	 */
	public void setTableContent(Vector<String> columnHeaders,
			Vector<Vector<String>> tableContent) {
		contentTable = new JTable(tableContent, columnHeaders) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}
		};
		contentTable.setAutoResizeMode(0);
		contentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		contentTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						for (int i = 0; i < contentTable.getColumnCount(); i++) {
							EDIT_BOXES.get(contentTable.getColumnName(i))
									.setText(
											(String) contentTable.getValueAt(
													contentTable
															.getSelectedRow(),
													i));
						}
					}
				});
		tableConctentScrollPane = new JScrollPane(contentTable);
		horizontalSplitPane.setRightComponent(tableConctentScrollPane);
		setEditBoxes();
	}

	/**
	 * Provides a link to the table list to add an ActionListener
	 * 
	 * @param listenForTableChoice
	 */
	public void tableListListener(ListSelectionListener listenForTableChoice) {
		list.addListSelectionListener(listenForTableChoice);
	}

	/**
	 * Provides a link to the back button to add an ActionListener
	 * 
	 * @param ListenForBackButton
	 */
	public void backButtonListener(ActionListener ListenForBackButton) {
		backButton.addActionListener(ListenForBackButton);
	}

	/**
	 * Creates and displays a dialog in case of errors.
	 * 
	 * @param message
	 *            The message the user will see.
	 */
	public void displayError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	/**
	 * Sets the selection of the table list.
	 * 
	 * @param index
	 */
	public void setSelectedIndex(int index) {
		list.setSelectedIndex(index);
	}

	/**
	 * Creates a JPanel that contains the back button and adds it to the frame.
	 */
	private void setTopNavigationPanel() {
		JPanel topNavigationPanel = new JPanel(new BorderLayout());
		backButton = new JButton("<--");
		backButton.setPreferredSize(new Dimension(100, 30));
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				EDIT_BOXES.clear();
				CHANGES.clear();
				bottomPanel.revalidate();
			}
		});
		topNavigationPanel.add(backButton, BorderLayout.WEST);
		add(topNavigationPanel, BorderLayout.NORTH);
	}

	/**
	 * Creates a JPanel that contains a number of JTextFields and adds it to the
	 * frame. The JTextBoxes is filled with data when user chose a row on the
	 * table.
	 */
	private void setEditBoxes() {
		// fill column names for textboxes at the bottom panel for insert,
		// update,remove operations.
		if (editBoxPanel != null)
			editBoxPanel.removeAll();
		EDIT_BOXES.clear();
		CHANGES.clear();
		for (int i = 0; i < contentTable.getColumnCount(); i++) {
			EDIT_BOXES.put(contentTable.getColumnName(i), new JTextField());
		}
		Iterator<String> it = EDIT_BOXES.keySet().iterator();
		editBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		while (it.hasNext()) {
			String columnName = it.next();
			JPanel tmp = new JPanel(new GridLayout(2, 1));
			JLabel lbl = new JLabel(columnName + ":");
			lbl.setPreferredSize(new Dimension(250, 25));
			tmp.add(lbl);
			tmp.add(EDIT_BOXES.get(columnName));
			editBoxPanel.add(tmp);
		}
		bottomPanel.add(editBoxPanel, BorderLayout.CENTER);
		bottomPanel.revalidate();
	}

	/**
	 * Returns the data that is entered to the TextBoxes at the bottom of the
	 * frame.
	 * 
	 * @return A LinkedHashMat that contains the column names and column data.
	 */
	public LinkedHashMap<String, String> getRecordDetail() {
		LinkedHashMap<String, String> tmp = new LinkedHashMap<String, String>();
		Iterator<String> it = EDIT_BOXES.keySet().iterator();
		while (it.hasNext()) {
			String columnName = it.next();
			tmp.put(columnName, EDIT_BOXES.get(columnName).getText());
		}
		return tmp;
	}

	/**
	 * Sets up the horizontal split pane.
	 */
	private void setHorizontalSplitPane() {
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplitPane.setResizeWeight(0.8);
		verticalSplitPane.setTopComponent(horizontalSplitPane);
		// bottom of the screen.
		bottomPanel = new JPanel(new BorderLayout());
		verticalSplitPane.setBottomComponent(bottomPanel);
		// Panel where insert, update and remove buttons reside.
		JPanel bottomButtonPanel = new JPanel(new GridLayout(4, 1));
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ButtonListener());
		bottomButtonPanel.add(updateButton);
		insertButton = new JButton("Insert");
		insertButton.addActionListener(new ButtonListener());
		bottomButtonPanel.add(insertButton);
		removeButton = new JButton("Remove");
		removeButton.addActionListener(new ButtonListener());
		bottomButtonPanel.add(removeButton);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ButtonListener());
		bottomButtonPanel.add(resetButton);
		bottomPanel.add(bottomButtonPanel, BorderLayout.EAST);
		add(verticalSplitPane, "Center");
	}

	/**
	 * Sets up the vertical split pane.
	 */
	private void setVerticalSplitPane() {
		tableList = new DefaultListModel<String>();
		list = new JList<String>(tableList);
		list.setSelectionMode(0);
		list.setVisibleRowCount(-1);
		tableListScrollPane = new JScrollPane(list);
		horizontalSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		horizontalSplitPane.setResizeWeight(0.2);
		horizontalSplitPane.setLeftComponent(tableListScrollPane);
	}

	/**
	 * Link for the JButton "insert" for adding actionListener.
	 * 
	 * @param listenForInsertButton
	 */
	public void insertButtontListener(ActionListener listenForInsertButton) {
		insertButton.addActionListener(listenForInsertButton);
	}

	/**
	 * Link for the JButton "remove" for adding actionListener.
	 * 
	 * @param listenForRemoveButton
	 */
	public void removeButtontListener(ActionListener listenForRemoveButton) {
		removeButton.addActionListener(listenForRemoveButton);
	}

	/**
	 * Listens for the user clicks on "update" and "reset" buttons and reacts
	 * accordingly.
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == updateButton) {
				for (int i = 0; i < contentTable.getColumnCount(); i++) {
					System.out.println(EDIT_BOXES.get(
							contentTable.getColumnName(i)).getText());
				}
				System.out.println("-------------------");
			}
			if (e.getSource() == resetButton) {
				for (int i = 0; i < contentTable.getColumnCount(); i++) {
					EDIT_BOXES.get(contentTable.getColumnName(i)).setText(null);
				}
			}
		}

	}

	private static final long serialVersionUID = 1L;
	private JTable contentTable;
	private JScrollPane tableListScrollPane;
	private JScrollPane tableConctentScrollPane;
	private JSplitPane horizontalSplitPane;
	private JSplitPane verticalSplitPane;
	private JList<String> list;
	private DefaultListModel<String> tableList;
	private JButton backButton;
	private JButton updateButton;
	private JButton removeButton;
	private JButton insertButton;
	private JButton resetButton;
	private LinkedHashMap<String, JTextField> EDIT_BOXES = new LinkedHashMap<String, JTextField>();
	private LinkedHashMap<String, JTextField> CHANGES = new LinkedHashMap<String, JTextField>();
	private JPanel bottomPanel;
	private JPanel editBoxPanel;
}