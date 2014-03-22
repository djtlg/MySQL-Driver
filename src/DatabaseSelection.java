import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

public class DatabaseSelection extends JPanel {

	public DatabaseSelection() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{20, 76, 71};
		setLayout(gridBagLayout);
		addListBox();
		addNextButton();
		addCancelButton();
	}

	private void addListBox() {
		databaseList = new DefaultListModel<String>();
		{
			lblNewLabel = new JLabel("Databases");
			GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
			gbc_lblNewLabel.gridwidth = 2;
			gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
			gbc_lblNewLabel.gridx = 1;
			gbc_lblNewLabel.gridy = 0;
			add(lblNewLabel, gbc_lblNewLabel);
		}
		list = new JList<String>();
		list.setSelectionMode(0);
		list.setVisibleRowCount(-1);
		GridBagConstraints listConstraints = new GridBagConstraints();
		listConstraints.insets = new Insets(0, 0, 5, 0);
		listConstraints.anchor = 10;
		listConstraints.fill = 1;
		listConstraints.gridx = 1;
		listConstraints.gridy = 1;
		listConstraints.gridwidth = 2;
		listConstraints.insets.set(0, 0, 5, 0);
//		databaseListContainer = new JScrollPane(list);
//		add(databaseListContainer, listConstraints);
		add(list, listConstraints);
	}

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

	public void setDatabaseList(ArrayList<String> databaseList) {
		this.databaseList.removeAllElements();
		databaseList.trimToSize();
		for (int i = 0; i < databaseList.size(); i++)
			this.databaseList.addElement((String) databaseList.get(i));
		list.setModel(this.databaseList);
	}

	public String getSelection() {
		return (String) list.getSelectedValue();
	}

	public void nextButtonListener(ActionListener listenForNextButton) {
		nextButton.addActionListener(listenForNextButton);
	}

	public void cancelButtonListener(ActionListener listenForCancelButton) {
		cancelButton.addActionListener(listenForCancelButton);
	}

	public void displayError(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	private static final long serialVersionUID = 1L;
	private JList<String> list;
	private DefaultListModel<String> databaseList;
	//private JScrollPane databaseListContainer;
	private JButton nextButton;
	private JButton cancelButton;
	private JLabel lblNewLabel;
}