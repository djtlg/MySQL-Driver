import java.awt.Font;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.w3c.dom.views.AbstractView;

import java.awt.GridBagConstraints;
import java.util.ArrayList;

public class Diolog extends JPanel {


	private final Font labelFont = new Font("Serif", Font.BOLD, 15);
	/**
	 * Create the panel.
	 */
	public Diolog() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		setLabels();
		JTable table = new JTable(new MyTableModel());
		add(table);
		
	}
	
	private void setLabels(){
		JLabel columnNamesLabel = new JLabel("Column Names");
		columnNamesLabel.setFont(labelFont);
		GridBagConstraints gbc_columnNamesLabel = new GridBagConstraints();
		gbc_columnNamesLabel.fill = GridBagConstraints.HORIZONTAL;
		add(columnNamesLabel, gbc_columnNamesLabel);
		JLabel columnTypeLabel = new JLabel("Column Types");
		columnTypeLabel.setFont(labelFont);
		GridBagConstraints gbc_columnTypeLabel = new GridBagConstraints();
		gbc_columnTypeLabel.fill = GridBagConstraints.HORIZONTAL;
		add(columnTypeLabel, gbc_columnTypeLabel);
	}
	
	private class MyTableModel extends AbstractTableModel{
		
		private final String[] columnNames = {"Column Name","Column Type"};
		private ArrayList<ArrayList<String>> data;;

		@Override
		public int getColumnCount() {
			return 2;
		}

		@Override
		public int getRowCount() {
			return data.size();
		}

		@Override
		public Object getValueAt(int arg0, int arg1) {
			return data.get(arg0).get(arg1);
		}
		
	}
}
