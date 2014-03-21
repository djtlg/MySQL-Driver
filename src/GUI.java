import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static LoginScreen login;
	private static DatabaseSelection dbSelector;
	private static TablesAndRecords tablesAndRecords;
	private Connection con;
	private ConnectionManager conMgr;
	private static CardLayout layout;
	private static String host;
	private static String port;
	private static String userName;
	private static char passWord[];
	private static Database database;
	private static Table table;
	private static Record record;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		setDefaultCloseOperation(3);
		setBounds(100, 100, 450, 300);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		layout = new CardLayout();
		contentPane.setLayout(layout);
		login = new LoginScreen();
		login.loginListener(new LoginListener());
		dbSelector = new DatabaseSelection();
		dbSelector.nextButtonListener(new NextButtonListener());
		dbSelector.removeButtonListener(new RemoveButtonListener());
		dbSelector.newButtonListener(new NewButtonListener());
		dbSelector.cancelButtonListener(new CancelButtonListener());
		tablesAndRecords = new TablesAndRecords();
		tablesAndRecords.tableListListener(new TableListListener());
		contentPane.add(login, "login");
		contentPane.add(dbSelector, "dbSelector");
		contentPane.add(tablesAndRecords, "tablesAndRecords");
		setContentPane(contentPane);
		layout.show(contentPane, "login");
	}
	
	private class LoginListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			try {
				host = login.getHost();
				port = login.getPort();
				userName = login.getUserName();
				passWord = login.getPassWord();
				conMgr = new ConnectionManager(host, port);
				con = conMgr.getConnection(userName, new String(passWord));
				database = new Database(con);
				dbSelector.setDatabaseList(database.getDBs());
				layout.show(contentPane, "dbSelector");
			} catch (ClassNotFoundException | SQLException e) {
				login.displayError(e.getMessage());
			}
		}

	}

	private class NextButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			try {
				database.selectDB(dbSelector.getSelection());
				table = new Table(con);
				tablesAndRecords.setTableList(table.getTables());
				layout.show(contentPane, "tablesAndRecords");
			} catch (SQLException e) {
				dbSelector.displayError(e.getMessage());
			}
		}
	}
	
	private class RemoveButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			try { 
				if(JOptionPane.showConfirmDialog (null,"YOU ARE ABOUT TO DELETE THE DATABASE \""+ dbSelector.getSelection() +"\" WITH ALL OF IT'S DATA." +"\nAre you sure?","WARNING", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
					database.removeDB(dbSelector.getSelection());
					dbSelector.setDatabaseList(database.getDBs());
				}
			} catch (SQLException e) {
				dbSelector.displayError(e.getMessage());
			}
		}
	}
	
	private class NewButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			try {
				String newDbName;
				do{
					newDbName = JOptionPane
							.showInputDialog("Please Enter Database Name: ");
				if (newDbName.isEmpty()) {
					dbSelector.displayError("Database name can not be empty.");
				}
				}while(newDbName.isEmpty());
				database.addDB(newDbName);
				dbSelector.setDatabaseList(database.getDBs());
				JOptionPane.showMessageDialog(null, "Database \"" + newDbName + "\" succesfully created.");
				} catch (SQLException e) {
				dbSelector.displayError(e.getMessage());
			}
		}
	}
	
	private class CancelButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			host = null;
			port = null;
			userName = null;
			passWord = null;
			conMgr = null;
			con = null;
			database = null;
			login.reset();
			layout.show(contentPane, "login");
		}

	}

	private class TableListListener implements ListSelectionListener {

		public void valueChanged(ListSelectionEvent arg0) {
			record = new Record(con);
			try {
				tablesAndRecords
						.setTableContent(table.getColumns(tablesAndRecords
								.getTableChoice()), record
								.getAllRecords(tablesAndRecords
										.getTableChoice()));
			} catch (SQLException e) {
				tablesAndRecords.displayError(e.getMessage());
			}
		}
	}
}