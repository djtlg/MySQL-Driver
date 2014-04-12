import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         AddTableDialog class creates a dialog to interact with the user so
 *         the user can enter the custom Query to be run on the host.
 * 
 */
public class QueryDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private final Font font = new Font("Serif", Font.BOLD, 17);
	private JTextArea query;
	private JButton ok;
	private JButton cancel;

	public QueryDialog() {
		setSize(new Dimension(400, 400));
		setLocationRelativeTo(null);
		setModalityType(ModalityType.APPLICATION_MODAL);
		getContentPane().setLayout(new BorderLayout());
		JLabel label = new JLabel("Please Enter your query: ");
		label.setFont(font);
		getContentPane().add(label, BorderLayout.NORTH);
		query = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(query);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		JPanel buttonPanel = new JPanel();
		cancel = new JButton("Cancel");
		cancel.setPreferredSize(new Dimension(100, 30));
		cancel.addActionListener(new ButtonListener());
		buttonPanel.add(cancel);
		ok = new JButton("OK");
		ok.setPreferredSize(new Dimension(100, 30));
		ok.addActionListener(new ButtonListener());
		buttonPanel.add(ok);
		getContentPane().add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}

	/**
	 * Returns the custom Query that user entered.
	 * 
	 * @return A string that contains the custom Query.
	 */
	public String getQuery() {
		return query.getText();
	}

	/**
	 * Listens for the user clicks on ok and cancel reacts accordingly.
	 */
	private class ButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == ok) {
				// User hit Ok without Entering any data to the dialog.
				if (query.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null,
							"Please Enter a query.", "Error ...",
							JOptionPane.ERROR_MESSAGE);
				} else {
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
