import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JMenu;

public class GUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private LoginScreen login;
	private DatabaseSelection dbSelector;
	private TablesAndRecords tablesAndRecords;
	private QueryDisplay queryDisplay;
	private Connection con;
	private ConnectionManager conMgr;
	private CardLayout layout;
	private String host;
	private String port;
	private String userName;
	private char passWord[];
	private Database database;
	private Table table;
	private Record record;
	private JMenuBar menuBar;
	private JMenu edit, advanced;
	private JMenuItem addDatabase, removeDatabase, addTable, removeTable,
			createQuery;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					// Set System L&F
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
					GUI frame = new GUI();
					frame.setVisible(true);
				} catch (UnsupportedLookAndFeelException e) {
					// handle exception
				} catch (ClassNotFoundException e) {
					// handle exception
				} catch (InstantiationException e) {
					// handle exception
				} catch (IllegalAccessException e) {
					// handle exception
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GUI() {
		setDefaultCloseOperation(3);
		setBounds(100, 100, 450, 300);
		// sets window to the center
		setLocationRelativeTo(null);
		createMenuBar();
		contentPane = new JPanel();
		layout = new CardLayout();
		contentPane.setLayout(layout);
		login = new LoginScreen();
		login.loginListener(new LoginListener());
		dbSelector = new DatabaseSelection();
		dbSelector.nextButtonListener(new NextButtonListener());
		dbSelector.cancelButtonListener(new CancelButtonListener());
		tablesAndRecords = new TablesAndRecords();
		tablesAndRecords.tableListListener(new TableListListener());
		tablesAndRecords.backButtonListener(new BackButtonListener());
		queryDisplay = new QueryDisplay();
		contentPane.add(login, "login");
		contentPane.add(dbSelector, "dbSelector");
		contentPane.add(tablesAndRecords, "tablesAndRecords");
		contentPane.add(queryDisplay, "queryDisplay");
		setContentPane(contentPane);
		layout.show(contentPane, "login");
	}

	private void createMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		edit = new JMenu("Edit");
		advanced = new JMenu("Advanced");
	}

	private void createDbSelectorEditMenu() {
		edit.removeAll();
		menuBar.add(edit);
		addDatabase = new JMenuItem("New Database");
		addDatabase.addActionListener(new MenuBarActionListener());
		edit.add(addDatabase);
		removeDatabase = new JMenuItem("Remove Database");
		removeDatabase.addActionListener(new MenuBarActionListener());
		edit.add(removeDatabase);
	}

	private void createEditMenuInTablesAndRecords() {
		edit.removeAll();
		menuBar.add(edit);
		addTable = new JMenuItem("New Table");
		addTable.addActionListener(new MenuBarActionListener());
		edit.add(addTable);
		removeTable = new JMenuItem("Remove Table");
		removeTable.addActionListener(new MenuBarActionListener());
		edit.add(removeTable);
	}

	private void createAdvancedMenu() {
		menuBar.add(advanced);
		createQuery = new JMenuItem("Create Query");
		createQuery.addActionListener(new MenuBarActionListener());
		advanced.add(createQuery);
	}

	private class MenuBarActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (arg0.getSource() == addDatabase) {
				try {
					String newDbName;
					do {
						newDbName = JOptionPane
								.showInputDialog("Please Enter Database Name: ");
						if (newDbName.isEmpty()) {
							dbSelector
									.displayError("Database name can not be empty.");
						}
					} while (newDbName.isEmpty());
					database.addDB(newDbName);
					dbSelector.setDatabaseList(database.getDBs());
					JOptionPane.showMessageDialog(null, "Database \""
							+ newDbName + "\" succesfully created.");
				} catch (SQLException e) {
					dbSelector.displayError(e.getMessage());
				}
			}
			if (arg0.getSource() == removeDatabase) {
				try {
					if (dbSelector.getSelection() == null)
						JOptionPane.showMessageDialog(null,
								"Please select a database", "Error ...",
								JOptionPane.ERROR_MESSAGE);
					else if (JOptionPane.showConfirmDialog(null,
							"YOU ARE ABOUT TO DELETE THE DATABASE \""
									+ dbSelector.getSelection()
									+ "\" WITH ALL OF IT'S DATA."
									+ "\nAre you sure?", "WARNING",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						database.removeDB(dbSelector.getSelection());
						dbSelector.setDatabaseList(database.getDBs());
					}
				} catch (SQLException e) {
					dbSelector.displayError(e.getMessage());
				}
			}
			if (arg0.getSource() == addTable) {
				try {
					AddTableDialog addTableDialog = new AddTableDialog();
					// Create new table in database.
					table.addTable(addTableDialog.getTableName(),
							addTableDialog.getColumnNamesAndTypes());
					// Refresh the table list in the view.
					tablesAndRecords.setTableList(table.getTables());
				} catch (SQLException e) {
					tablesAndRecords.displayError(e.getMessage());
				}
			}
			if (arg0.getSource() == removeTable) {
				try {
					if (tablesAndRecords.getTableChoice() == null)
						JOptionPane.showMessageDialog(null,
								"Please select a table", "Error ...",
								JOptionPane.ERROR_MESSAGE);
					else if (JOptionPane.showConfirmDialog(null,
							"YOU ARE ABOUT TO DELETE THE TABLE \""
									+ tablesAndRecords.getTableChoice()
									+ "\" WITH ALL OF IT'S DATA."
									+ "\nAre you sure?", "WARNING",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						table.removeTable(tablesAndRecords.getTableChoice());
						tablesAndRecords.setTableList(table.getTables());
					}
				} catch (SQLException e) {
					tablesAndRecords.displayError(e.getMessage());
				}
			}
			if (arg0.getSource() == createQuery) {
				try {
					QueryDialog queryDialog = new QueryDialog();
					if (!queryDialog.getQuery().isEmpty()) {
						Query query = new Query(con);
						query.createQuery(queryDialog.getQuery());
						queryDisplay.setContent(query.getQueryColumnNames(),
								query.getQueryResults());
						queryDisplay
								.backButtonListener(new BackButtonListener());
						layout.show(contentPane, "queryDisplay");
					}
				} catch (SQLException e) {
					tablesAndRecords.displayError(e.getMessage());
				}
			}
		}

	}

	private class LoginListener implements ActionListener {

		@Override
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
				createDbSelectorEditMenu();
			} catch (ClassNotFoundException | SQLException e) {
				login.displayError(e.getMessage());
			}
		}

	}

	private class NextButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				database.selectDB(dbSelector.getSelection());
				table = new Table(con);
				tablesAndRecords.setTableList(table.getTables());
				layout.show(contentPane, "tablesAndRecords");
				// maximize the window
				setExtendedState(getExtendedState() | Frame.MAXIMIZED_BOTH);
				createEditMenuInTablesAndRecords();
				createAdvancedMenu();
			} catch (SQLException e) {
				dbSelector.displayError(e.getMessage());
			}
		}
	}

	private class CancelButtonListener implements ActionListener {

		@Override
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
			menuBar.removeAll();
		}

	}

	private class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			layout.previous(contentPane);
		}
	}

	private class TableListListener implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (tablesAndRecords.getTableChoice() != null) {
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
			} else {
				tablesAndRecords.setTableContent(null, null);
			}
		}
	}
}