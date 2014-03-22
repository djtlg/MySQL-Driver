import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;

public class TablesAndRecords extends JPanel {

	public TablesAndRecords() {
		setLayout(new BorderLayout());
		tableList = new DefaultListModel<String>();
		list = new JList<String>(tableList);
		list.setSelectionMode(0);
		list.setVisibleRowCount(-1);
		tableListScrollPane = new JScrollPane(list);
		splitPane = new JSplitPane(1);
		splitPane.setResizeWeight(0.2);
		splitPane.setLeftComponent(tableListScrollPane);
		add(splitPane, "Center");
	}

	public void setTableList(ArrayList<String> tableList) {
		tableList.trimToSize();
		for (int i = 0; i < tableList.size(); i++)
			this.tableList.addElement((String) tableList.get(i));
		list.setSelectedIndex(0);
	}

	public String getTableChoice() {
		return (String) list.getSelectedValue();
	}

	@SuppressWarnings("serial")
	public void setTableContent(Vector<String> columnHeaders,
			Vector<Vector<String>> tableContent) {
		contentTable = new JTable(tableContent, columnHeaders) {
			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}
		};
		contentTable.setAutoResizeMode(0);
		tableConctentScrollPane = new JScrollPane(contentTable);
		splitPane.setRightComponent(tableConctentScrollPane);
	}

	public void tableListListener(ListSelectionListener listenForTableChoice) {
		list.addListSelectionListener(listenForTableChoice);
	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	public void setSelectedIndex(int a) {
		list.setSelectedIndex(a);
	}

	private static final long serialVersionUID = 1L;
	private JTable contentTable;
	private JScrollPane tableListScrollPane;
	private JScrollPane tableConctentScrollPane;
	private JSplitPane splitPane;
	private JList<String> list;
	private DefaultListModel<String> tableList;
}