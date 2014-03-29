import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class RemoveColumnDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton ok;
	private JButton cancel;
	private JPanel listPanel;
	private JList<String> columnList;
	private JScrollPane scrollPane;
	private ArrayList<String> toBeRemoved;

	/**
	 * Create the panel.
	 */
	public RemoveColumnDialog(Vector<String> columnList) {
		this.columnList = new JList<String>(columnList);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setSize(410, 330);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new FlowLayout());
		setUpListPanel();
		setUpButtonPanel();
		setVisible(true);
	}

	public ArrayList<String> getToBeRemovedList() {
		return toBeRemoved;
	}

	private void setUpListPanel() {
		listPanel = new JPanel(new BorderLayout(5, 5));
		// Align text to center.
		columnList.setCellRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			public int getHorizontalAlignment() {
				return CENTER;
			}
		});
		scrollPane = new JScrollPane(columnList);
		scrollPane.setPreferredSize(new Dimension(300, 240));
		listPanel.add(scrollPane, BorderLayout.CENTER);
		JLabel warning = new JLabel(
				"<html>Please press and hold the CTRL button on the keyboard <br>to chose multiple items.</html>",
				SwingConstants.CENTER);
		listPanel.add(warning, BorderLayout.SOUTH);
		getContentPane().add(listPanel);
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
		getContentPane().add(buttonPanel);
	}

	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == ok) {
				// User hit OK without choosing any column.
				if (columnList.getSelectedValuesList().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please select columns.", "Error ...",
							JOptionPane.ERROR_MESSAGE);
				} else {
					toBeRemoved = new ArrayList<String>(
							columnList.getSelectedValuesList());
					setVisible(false);
					dispose();
				}
			}
			if (arg0.getSource() == cancel) {
				dispose();
			}
		}

	}
}
