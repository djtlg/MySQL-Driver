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

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         GUI class is the controller of the whole program.
 * 
 */
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
			addColumn, removeColumn, changeColumnType, createQuery;

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
		tablesAndRecords.insertButtontListener(new InsertButtonListener());
		tablesAndRecords.removeButtontListener(new RemoveButtonListener());
		queryDisplay = new QueryDisplay();
		contentPane.add(login, "login");
		contentPane.add(dbSelector, "dbSelector");
		contentPane.add(tablesAndRecords, "tablesAndRecords");
		contentPane.add(queryDisplay, "queryDisplay");
		setContentPane(contentPane);
		layout.show(contentPane, "login");
	}

	/**
	 * Creates the menu at the top.
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		edit = new JMenu("Edit");
		advanced = new JMenu("Advanced");
	}

	/**
	 * Creates the edit menu that is displayed on the database selection screen.
	 */
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

	/**
	 * Creates the edit menu that is displayed on the tables and records screen.
	 */
	private void createEditMenuInTablesAndRecords() {
		edit.removeAll();
		menuBar.add(edit);
		addTable = new JMenuItem("New Table");
		addTable.addActionListener(new MenuBarActionListener());
		edit.add(addTable);
		removeTable = new JMenuItem("Remove Table");
		removeTable.addActionListener(new MenuBarActionListener());
		edit.add(removeTable);
		JMenu alterTable = new JMenu("Alter Table");
		edit.add(alterTable);
		addColumn = new JMenuItem("Add Column");
		addColumn.addActionListener(new MenuBarActionListener());
		alterTable.add(addColumn);
		removeColumn = new JMenuItem("Remove Column");
		removeColumn.addActionListener(new MenuBarActionListener());
		alterTable.add(removeColumn);
		changeColumnType = new JMenuItem("Change Column Type");
		changeColumnType.addActionListener(new MenuBarActionListener());
		alterTable.add(changeColumnType);
	}

	/**
	 * Creates the advanced menu.
	 */
	private void createAdvancedMenu() {
		menuBar.add(advanced);
		createQuery = new JMenuItem("Create Query");
		createQuery.addActionListener(new MenuBarActionListener());
		advanced.add(createQuery);
	}

	/**
	 * Resets the database list.
	 * 
	 * @throws SQLException
	 */
	private void resetDatabaseList() throws SQLException {
		dbSelector.setDatabaseList(database.getDBs());
	}

	/**
	 * Resets the table list.
	 * 
	 * @throws SQLException
	 */
	private void resetTableList() throws SQLException {
		tablesAndRecords.setTableList(table.getTables());
	}

	/**
	 * Resets the table that holds the records.
	 * 
	 * @throws SQLException
	 */
	private void resetRecordTable() throws SQLException {
		if (tablesAndRecords.getTableChoice() != null) {
			tablesAndRecords.setTableContent(
					table.getColumns(tablesAndRecords.getTableChoice()),
					record.getAllRecords(tablesAndRecords.getTableChoice()));
		} else
			tablesAndRecords.setTableContent(null, null);
	}

	/**
	 * Listens for the menu bar clicks and acts accordingly.
	 */
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
					resetDatabaseList();
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
						resetDatabaseList();
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
					resetTableList();
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
						resetTableList();
						resetRecordTable();
					}
				} catch (SQLException e) {
					tablesAndRecords.displayError(e.getMessage());
				}
			}
			if (arg0.getSource() == addColumn) {
				try {
					AddNewColumnDialog addNewColumn = new AddNewColumnDialog();
					if (tablesAndRecords.getTableChoice() == null)
						JOptionPane.showMessageDialog(null,
								"Please select a table", "Error ...",
								JOptionPane.ERROR_MESSAGE);
					else {
						table.addColumn(tablesAndRecords.getTableChoice(),
								addNewColumn.getColumnNamesAndTypes());
						resetRecordTable();
					}
				} catch (SQLException e) {
					tablesAndRecords.displayError(e.getMessage());
				}
			}
			if (arg0.getSource() == removeColumn) {
				try {
					RemoveColumnDialog removeColumn = new RemoveColumnDialog(
							table.getColumns(tablesAndRecords.getTableChoice()));
					if (tablesAndRecords.getTableChoice() == null)
						JOptionPane.showMessageDialog(null,
								"Please select a table", "Error ...",
								JOptionPane.ERROR_MESSAGE);
					else {
						table.removeColumn(tablesAndRecords.getTableChoice(),
								removeColumn.getToBeRemovedList());
						resetRecordTable();
					}
				} catch (SQLException e) {
					tablesAndRecords.displayError(e.getMessage());
				}
			}
			if (arg0.getSource() == changeColumnType) {
				// TODO
				System.out.println("Change Column Type");
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
								.backButtonListener(new QueryBackButtonListener());
						layout.show(contentPane, "queryDisplay");
					}
				} catch (SQLException e) {
					tablesAndRecords.displayError(e.getMessage());
				}
			}
		}

	}

	/**
	 * Listen for the login button to be clicked on login screen and acts
	 * accordingly.
	 */
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

	/**
	 * Listens for the next button on the database selection screen.
	 */
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

	/**
	 * Listens for the cancel button on the database selection screen.
	 */
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

	/**
	 * Listens for the back button on the tables and records screen.
	 */
	private class BackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			table = null;
			menuBar.removeAll();
			advanced.removeAll();
			menuBar.repaint();
			createDbSelectorEditMenu();
			layout.show(contentPane, "dbSelector");
		}
	}

	/**
	 * Listens for the back button on the query display screen.
	 */
	private class QueryBackButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			menuBar.removeAll();
			menuBar.repaint();
			layout.show(contentPane, "tablesAndRecords");
		}

	}

	/**
	 * Listens for the insert button on the tables and records screen.
	 */
	private class InsertButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				record.addRecord(tablesAndRecords.getTableChoice(),
						tablesAndRecords.getRecordDetail());
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

	/**
	 * Listens for the remove button on the tables and records screen.
	 */
	private class RemoveButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			try {
				record.removeRecord(tablesAndRecords.getTableChoice(),
						tablesAndRecords.getRecordDetail());
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

	/**
	 * Listens for the table list selection changes.
	 */
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
			}
		}
	}
}