import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         QueryDisplay class displays the results of a custom query.
 * 
 */
public class QueryDisplay extends JPanel {

	private JTable queryContent;
	private JButton backButton;
	private JScrollPane scrollPane;
	private DefaultTableModel tableModel;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public QueryDisplay() {
		setLayout(new BorderLayout());
		JPanel topPanel = new JPanel(new BorderLayout());
		backButton = new JButton("<--");
		backButton.setPreferredSize(new Dimension(100, 30));
		topPanel.add(backButton, BorderLayout.WEST);
		add(topPanel, BorderLayout.NORTH);
		queryContent = new JTable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}
		};
		queryContent.setAutoResizeMode(0);
		scrollPane = new JScrollPane(queryContent);
		add(scrollPane, BorderLayout.CENTER);
	}

	/**
	 * Load the display with the custom query results.
	 * 
	 * @param columnNames
	 *            A Vector that contains the names of the columns that is used
	 *            in the query.
	 * @param columnData
	 *            A vector that contains the data of the custom query.
	 */
	public void setContent(Vector<String> columnNames,
			Vector<Vector<String>> columnData) {
		tableModel = new DefaultTableModel(columnData, columnNames);
		queryContent.setModel(tableModel);
	}

	public void backButtonListener(ActionListener ListenForBackButton) {
		backButton.addActionListener(ListenForBackButton);
	}
}
