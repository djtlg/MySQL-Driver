import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class QueryDisplay extends JPanel {

	private JTable queryContent;
	private JButton backButton;
	
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
		backButton.setPreferredSize(new Dimension(100,30));
		topPanel.add(backButton,BorderLayout.WEST);
		add(topPanel,BorderLayout.NORTH);
	}
	
	public void setContent(Vector<String> columnNames, Vector<Vector<String>> columnData){
		queryContent = new JTable(columnData, columnNames) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean getScrollableTracksViewportWidth() {
				return getPreferredSize().width < getParent().getWidth();
			}
		};
		queryContent.setAutoResizeMode(0);
		JScrollPane scrollPane = new JScrollPane(queryContent);
		add(scrollPane,BorderLayout.CENTER);
	}
	public void backButtonListener(ActionListener ListenForBackButton) {
		backButton.addActionListener(ListenForBackButton);
	}
}
