import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * 
 * @author Tolga ILDIZ, Volkan ALCIN
 * 
 *         LoginScreen class is for authenticate the user. It displays a screen where the user can enter
 *         username, password, port and host address.
 * 
 */
public class LoginScreen extends JPanel {

	public LoginScreen() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		setLayout(gridBagLayout);
		JLabel userNameLabel = new JLabel("Username: ");
		GridBagConstraints gbc_userNameLabel = new GridBagConstraints();
		gbc_userNameLabel.anchor = 13;
		gbc_userNameLabel.insets = new Insets(0, 0, 5, 5);
		gbc_userNameLabel.gridx = 1;
		gbc_userNameLabel.gridy = 1;
		add(userNameLabel, gbc_userNameLabel);
		userName = new JTextField();
		GridBagConstraints gbc_userName = new GridBagConstraints();
		gbc_userName.anchor = 13;
		gbc_userName.insets = new Insets(0, 0, 5, 5);
		gbc_userName.gridx = 2;
		gbc_userName.gridy = 1;
		add(userName, gbc_userName);
		userName.setColumns(10);
		JLabel passWordLabel = new JLabel("Password: ");
		GridBagConstraints gbc_passWordLabel = new GridBagConstraints();
		gbc_passWordLabel.anchor = 13;
		gbc_passWordLabel.insets = new Insets(0, 0, 5, 5);
		gbc_passWordLabel.gridx = 1;
		gbc_passWordLabel.gridy = 2;
		add(passWordLabel, gbc_passWordLabel);
		passWord = new JPasswordField();
		GridBagConstraints gbc_passWord = new GridBagConstraints();
		gbc_passWord.fill = 2;
		gbc_passWord.insets = new Insets(0, 0, 5, 5);
		gbc_passWord.gridx = 2;
		gbc_passWord.gridy = 2;
		add(passWord, gbc_passWord);
		loginButon = new JButton("Login");
		loginButon.setFont(new Font("Tahoma", 1, 16));
		GridBagConstraints gbc_loginButon = new GridBagConstraints();
		gbc_loginButon.anchor = 10;
		gbc_loginButon.fill = 3;
		gbc_loginButon.gridheight = 4;
		gbc_loginButon.gridx = 3;
		gbc_loginButon.gridy = 1;
		add(loginButon, gbc_loginButon);
		hostLabel = new JLabel("Host: ");
		GridBagConstraints gbc_hostLabel = new GridBagConstraints();
		gbc_hostLabel.anchor = 13;
		gbc_hostLabel.insets = new Insets(0, 0, 5, 5);
		gbc_hostLabel.gridx = 1;
		gbc_hostLabel.gridy = 3;
		add(hostLabel, gbc_hostLabel);
		host = new JTextField();
		host.setText("localhost");
		GridBagConstraints gbc_host = new GridBagConstraints();
		gbc_host.insets = new Insets(0, 0, 5, 5);
		gbc_host.fill = 2;
		gbc_host.gridx = 2;
		gbc_host.gridy = 3;
		add(host, gbc_host);
		host.setColumns(10);
		portLabel = new JLabel("Port:");
		GridBagConstraints gbc_portLabel = new GridBagConstraints();
		gbc_portLabel.anchor = 13;
		gbc_portLabel.insets = new Insets(0, 0, 0, 5);
		gbc_portLabel.gridx = 1;
		gbc_portLabel.gridy = 4;
		add(portLabel, gbc_portLabel);
		port = new JTextField();
		port.setText("3306");
		GridBagConstraints gbc_port = new GridBagConstraints();
		gbc_port.insets = new Insets(0, 0, 0, 5);
		gbc_port.fill = 2;
		gbc_port.gridx = 2;
		gbc_port.gridy = 4;
		add(port, gbc_port);
		port.setColumns(10);
	}

	/**
	 * Returns the username of the user.
	 * @return A string that contains the username.
	 */
	public String getUserName() {
		return userName.getText();
	}

	/**
	 * Returns the password of the user.
	 * @return A Char array that contains the password of the user.
	 */
	public char[] getPassWord() {
		return passWord.getPassword();
	}

	/**
	 * Returns the port number.
	 * @return A string that contains the port number.
	 */
	public String getPort() {
		return port.getText();
	}

	/**
	 * Returns the host address.
	 * @return A string that contains the Host IP or the Address.
	 */
	public String getHost() {
		return host.getText();
	}

	/**
	 * Link for the JButton "Login" for adding actionListener.
	 * 
	 * @param listenForLogin
	 */
	public void loginListener(ActionListener listenForLogin) {
		loginButon.addActionListener(listenForLogin);
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

	/**
	 * Resets the textfields.
	 */
	public void reset() {
		userName.setText("");
		passWord.setText("");
		port.setText("3306");
		host.setText("localhost");
	}

	private static final long serialVersionUID = 1L;
	private JTextField userName;
	private JPasswordField passWord;
	private JTextField port;
	private JTextField host;
	private JLabel hostLabel;
	private JLabel portLabel;
	private JButton loginButon;
}