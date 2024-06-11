package presentation;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.Liste;
import presentation.Controlleurs.*;
import presentation.Models.ModelListes;
public class FormulaireListe extends JFrame implements Tools {

    private JPanel headerPanel, panel, panelBoutton, footerPanel;
    private JLabel titre, labelDescription;
    private JTextArea areaDescription;
    private JTextField nomListe;
    private JButton Ajouter, Annuler,ButtonPrecedent,ButtonAcceuil;
    private ControleurListes controleur;
    private ControleurAccueil cAccueil ;

    public FormulaireListe(ControleurListes c) {
        super("Ajouter une liste");
        this.controleur = c;
        this.initialiser();
        this.dessiner();
        this.actions();
    }

    private void initialiser() {
        cAccueil = new ControleurAccueil();
        headerPanel = new JPanel(new GridLayout(2, 1));
        nomListe = new JTextField(20);
        titre = new JLabel("Nom de la liste :");
        labelDescription = new JLabel("Description : ");
        areaDescription = new JTextArea(10, 20);
        panel = new JPanel(new GridLayout(6, 2));
        panelBoutton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Ajouter = new JButton("Valider");
        footerPanel = new JPanel(new BorderLayout());
    }

    private void dessiner() {
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        headerPanel.add(headerPanel());
        headerPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        headerPanel.add(navigation());
        this.add(headerPanel, BorderLayout.NORTH);
        panel.add(titre);
        panel.add(nomListe);
        panel.add(labelDescription);
        panel.add(new JScrollPane(areaDescription));
        panel.setBorder(new EmptyBorder(15, 70, 0, 70));
        getContentPane().add(panel, BorderLayout.CENTER);
        Ajouter.setBackground(Color.GREEN);
        panelBoutton.add(Ajouter);
        Annuler = new JButton("Annuler");
        Annuler.setBackground(Color.RED);
        panelBoutton.add(Annuler);
        panelBoutton.setBorder(new EmptyBorder(0, 0, 35, 50));
        footerPanel.add(panelBoutton, BorderLayout.NORTH);
        footerPanel.add(footerPanel(), BorderLayout.SOUTH);
        this.add(footerPanel, BorderLayout.SOUTH);
    }
    private JPanel navigation(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        this.ButtonAcceuil = new JButton("Accueil");
		ButtonAcceuil.setForeground(Color.BLACK);;
        this.ButtonPrecedent = new JButton("<-");
		ButtonPrecedent.setForeground(Color.BLACK);
		panel.add(ButtonAcceuil,BorderLayout.EAST);
		panel.add(ButtonPrecedent,BorderLayout.WEST);
		ButtonAcceuil.addActionListener(e -> {
			SwingUtilities.getWindowAncestor(ButtonAcceuil).dispose();
			cAccueil.LanceAccueil();
		});
        ButtonPrecedent.addActionListener(e -> {
			SwingUtilities.getWindowAncestor(ButtonAcceuil).dispose();
            controleur.LancerListes();
		});
		return panel;
	}

    public void actions() {
        Annuler.addActionListener(e -> {
            this.dispose();
            controleur.LancerListes();
        });
        Ajouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Liste newListe = new Liste (nomListe.getText(),areaDescription.getText());
                controleur.ajouterListe(newListe);
                controleur.setModel(new ModelListes());
                dispose();
                controleur.LancerListes();
            }
        });

    }

}