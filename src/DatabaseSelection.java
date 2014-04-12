import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         DatabaseSelection class displays the databases that is on the host
 *         and the user has access to and lets the user to chose the one that
 *         the user wants to work on.
 * 
 */
public class DatabaseSelection extends JPanel {

	public DatabaseSelection() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 20, 76, 71 };
		setLayout(gridBagLayout);
		addListBox();
		addNextButton();
		addCancelButton();
	}

	/**
	 * Creates an empty JList encloses it in an JScrollPane and adds it to the
	 * Frame.
	 */
	private void addListBox() {
		databaseList = new DefaultListModel<String>();
		lblNewLabel = new JLabel("Databases");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 0;
		add(lblNewLabel, gbc_lblNewLabel);
		list = new JList<String>();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		GridBagConstraints listConstraints = new GridBagConstraints();
		listConstraints.insets = new Insets(0, 0, 5, 0);
		listConstraints.anchor = 10;
		listConstraints.fill = 1;
		listConstraints.gridx = 1;
		listConstraints.gridy = 1;
		listConstraints.gridwidth = 2;
		listConstraints.insets.set(0, 0, 5, 0);
		JScrollPane scrollPane = new JScrollPane(list);
		add(scrollPane, listConstraints);
	}

	/**
	 * Creates an JButton for the "Next" and adds it to the Frame.
	 */
	private void addNextButton() {
		nextButton = new JButton("Next");
		GridBagConstraints nextButtonConstraints = new GridBagConstraints();
		nextButtonConstraints.insets = new Insets(0, 0, 5, 0);
		nextButtonConstraints.anchor = 10;
		nextButtonConstraints.fill = 1;
		nextButtonConstraints.gridx = 2;
		nextButtonConstraints.gridy = 2;
		add(nextButton, nextButtonConstraints);
	}

	/**
	 * Creates an JButton for the "Cancel" and adds it to the Frame.
	 */
	private void addCancelButton() {
		cancelButton = new JButton("Cancel");
		GridBagConstraints cancelButtonConstraints = new GridBagConstraints();
		cancelButtonConstraints.insets = new Insets(0, 0, 5, 5);
		cancelButtonConstraints.anchor = 10;
		cancelButtonConstraints.fill = 1;
		cancelButtonConstraints.gridx = 1;
		cancelButtonConstraints.gridy = 2;
		add(cancelButton, cancelButtonConstraints);
	}

	/**
	 * Fills the empty Jlist with the contents of the databaseList.
	 * 
	 * @param databaseList
	 *            The names of the databases that is on the host.
	 */
	public void setDatabaseList(ArrayList<String> databaseList) {
		this.databaseList.removeAllElements();
		databaseList.trimToSize();
		for (int i = 0; i < databaseList.size(); i++)
			this.databaseList.addElement((String) databaseList.get(i));
		list.setModel(this.databaseList);
	}

	/**
	 * Returns the users selection.
	 * 
	 * @return the name of the database that user selected.
	 */
	public String getSelection() {
		return (String) list.getSelectedValue();
	}

	/**
	 * Link for the JButton "Next" for adding actionListener.
	 * 
	 * @param listenForNextButton
	 */
	public void nextButtonListener(ActionListener listenForNextButton) {
		nextButton.addActionListener(listenForNextButton);
	}

	/**
	 * Link for the JButton "Cancel" for adding actionListener.
	 * 
	 * @param listenForCancelButton
	 */
	public void cancelButtonListener(ActionListener listenForCancelButton) {
		cancelButton.addActionListener(listenForCancelButton);
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

	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String> databaseList;
	private JButton nextButton;
	private JButton cancelButton;
	private JLabel lblNewLabel;
}