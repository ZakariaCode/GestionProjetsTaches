package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import metier.Service.CalendarQuickstart;
import presentation.Controlleurs.*;
import java.awt.*;


public class Authentification extends JFrame implements Tools {
	private JLabel usernameLabel;
	private JTextField usernameField;
	private JButton loginButton;
	private JPanel panel,panel2,panel3;
	@SuppressWarnings("unused")
	private ControleurConnexion controleur;
	public static final String APPLICATION_NAME = "TDL";
	public static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();

	public Authentification(ControleurConnexion c) {
		
		this.setSize(new Dimension(600, 400));
		this.initialise();
		this.dessiner();
		this.focus();
		this.LoginAction();
		this.controleur = c;

	}

	public void initialise() {
		
		usernameLabel = new JLabel("Email");
		usernameField = new JTextField("Accede a votre compte Google");
		loginButton = new JButton();
		panel = new JPanel(new GridBagLayout());
		panel2 = new JPanel(new GridLayout(3, 1));
		panel3 = new JPanel(new BorderLayout());
	}

	public void dessiner() {

		this.add(headerPanel(), BorderLayout.NORTH);
		this.add(footerPanel(), BorderLayout.SOUTH);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginButton.setOpaque(true);
		loginButton.setFocusPainted(false);
		loginButton.setBorderPainted(false);
		loginButton.setText("Connecter");
		loginButton.setForeground(Color.BLACK);
		usernameLabel.setForeground(Color.BLACK);
		usernameLabel.setFont(usernameLabel.getFont().deriveFont(24f));
		usernameField.setFont(new Font("Arial", Font.ITALIC, 12));
		usernameField.setFocusable(false);
		usernameField.setPreferredSize(new Dimension(300, 15));
		usernameField.setEditable(false);
		usernameField.setEnabled(false);
		loginButton.setBackground(Color.GREEN);
		panel2.add(usernameLabel);
		panel2.add(usernameField);
		panel3.setBorder(new EmptyBorder(5, 0, 0, 0));
		panel3.add(loginButton, BorderLayout.EAST);
		panel2.add(panel3);
		panel.add(panel2);
		this.add(panel);
	}

	public void focus(){
		loginButton.addFocusListener(new java.awt.event.FocusAdapter() {
			public void focusGained(java.awt.event.FocusEvent evt) {
				loginButton.setBackground(new Color(0, 85, 0));
				loginButton.setForeground(Color.WHITE);
			}
			public void focusLost(java.awt.event.FocusEvent evt) {
				loginButton.setBackground(Color.GREEN);
				loginButton.setForeground(Color.BLACK);
			}
		});
	}

	public void LoginAction() {
		loginButton.addActionListener(e -> {
			try {
				final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
				Calendar service = new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY,
						CalendarQuickstart.getCredentials(HTTP_TRANSPORT))
						.setApplicationName(APPLICATION_NAME)
						.build();
				if (service != null) {
			
					JOptionPane.showMessageDialog(null, "Connection à Google Calendar réussie","Information",JOptionPane.INFORMATION_MESSAGE);
					ControleurAccueil controlleur = new ControleurAccueil();
					controlleur.LanceAccueil();
				}else{
					JOptionPane.showMessageDialog(null, "Connection à Google Calendar échouée","Erreur",JOptionPane.ERROR_MESSAGE);
				}
				SwingUtilities.getWindowAncestor(usernameField).dispose();
			} catch (Exception el) {
				el.printStackTrace();
			}

		});
	}
}