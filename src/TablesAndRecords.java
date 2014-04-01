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

public class TablesAndRecords extends JPanel {

	public TablesAndRecords() {
		setLayout(new BorderLayout());
		setVerticalSplitPane();
		setTopNavigationPanel();
		setHorizontalSplitPane();
	}

	public void setTableList(ArrayList<String> tableList) {
		this.tableList.removeAllElements();
		tableList.trimToSize();
		for (int i = 0; i < tableList.size(); i++)
			this.tableList.addElement(tableList.get(i));
		list.setModel(this.tableList);
		list.setSelectedIndex(0);
	}

	public String getTableChoice() {
		return (String) list.getSelectedValue();
	}

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

	public void tableListListener(ListSelectionListener listenForTableChoice) {
		list.addListSelectionListener(listenForTableChoice);
	}

	public void backButtonListener(ActionListener ListenForBackButton) {
		backButton.addActionListener(ListenForBackButton);
	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setSelectedIndex(int a) {
		list.setSelectedIndex(a);
	}

	private void setTopNavigationPanel() {
		JPanel topNavigationPanel = new JPanel(new BorderLayout());
		backButton = new JButton("<--");
		backButton.setPreferredSize(new Dimension(100, 30));
		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				EDIT_BOXES.clear();
				bottomPanel.revalidate();
			}
		});
		topNavigationPanel.add(backButton, BorderLayout.WEST);
		add(topNavigationPanel, BorderLayout.NORTH);
	}

	private void setEditBoxes() {
		// fill column names for textboxes at the bottom panel for insert,
		// update,remove operations.
		if(editBoxPanel != null) editBoxPanel.removeAll();
		EDIT_BOXES.clear();
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

	private void setHorizontalSplitPane() {
		verticalSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		verticalSplitPane.setResizeWeight(0.8);
		verticalSplitPane.setTopComponent(horizontalSplitPane);
		// bottom of the screen.
		bottomPanel = new JPanel(new BorderLayout());
		verticalSplitPane.setBottomComponent(bottomPanel);
		// Panel where insert, update and remove buttons reside.
		JPanel bottomButtonPanel = new JPanel(new GridLayout(3, 1));
		updateButton = new JButton("Update");
		updateButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < contentTable.getColumnCount(); i++) {
					System.out.println(EDIT_BOXES.get(contentTable.getColumnName(i)).getText());
				}
				System.out.println("-------------------");
			}
		});
		insertButton = new JButton("Insert");
		removeButton = new JButton("Remove");
		bottomButtonPanel.add(updateButton);
		bottomButtonPanel.add(insertButton);
		bottomButtonPanel.add(removeButton);
		bottomPanel.add(bottomButtonPanel, BorderLayout.EAST);
		add(verticalSplitPane, "Center");
	}

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
	private LinkedHashMap<String, JTextField> EDIT_BOXES = new LinkedHashMap<String, JTextField>();
	private JPanel bottomPanel;
	private JPanel editBoxPanel;
}