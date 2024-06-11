package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.Seance;
import presentation.Controlleurs.ControleurAccueil;
import presentation.Controlleurs.ControleurProjets;
import presentation.Controlleurs.ControleurSeances;
import java.awt.*;

public class Seances extends JFrame implements Tools {
    private JPanel HeadPanel,Head,panelPrincipal,panelBoutons;
    private JLabel Label;
    private JList<Seance> listeSeances;
    private JButton boutonAjouter,ButtonAcceuil,ButtonPrecedent, boutonSupprimer, boutonConsulter;
    private Font boldFont;
    private JScrollPane scrollPane;
    private ControleurSeances controleur;
    private ControleurAccueil cAccueil ;
    private ControleurProjets cProjets ;
    public Seances(ControleurSeances c) {
        super("Seances");
        this.controleur = c;
        this.initialiser();
        this.dessiner();
        this.actions();
    }
    private void initialiser(){
        cAccueil = new ControleurAccueil();
        cProjets = new ControleurProjets();
        Head= new JPanel(new GridLayout(2,1));
        panelPrincipal = new JPanel();
        Label = new JLabel("Séances Programmées");
        boldFont = new Font(Label.getFont().getName(), Font.BOLD, Label.getFont().getSize());
        listeSeances = new JList<>();
        HeadPanel = new JPanel();
        scrollPane = new JScrollPane(listeSeances);
        boutonAjouter = new JButton("Ajouter");
        boutonSupprimer = new JButton("Supprimer");
        boutonConsulter = new JButton("Consulter");
        panelBoutons = new JPanel();
    }
    private void dessiner(){
        this.setLayout(new BorderLayout());
        Head.add(headerPanel());
        Head.setBorder(new EmptyBorder(0, 0, 5, 0));
        Head.add(navigation());
        this.add(Head,BorderLayout.NORTH);
        this.add(footerPanel(),BorderLayout.SOUTH);
        panelPrincipal.setLayout(new BorderLayout());
        Label.setFont(boldFont);
        HeadPanel.setLayout(new FlowLayout());
        HeadPanel.add(Label);
        panelPrincipal.add(HeadPanel, BorderLayout.NORTH);
        listeSeances.setCellRenderer(new SeanceCelleRendrer());
        listeSeances.setModel(controleur.getseances());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonSupprimer);
        panelBoutons.add(boutonConsulter);
        boutonSupprimer.setEnabled(false);
        boutonConsulter.setEnabled(false);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        getContentPane().add(panelPrincipal);
    }
    private JPanel navigation(){
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
        this.ButtonAcceuil = new JButton("Accueil");
		ButtonAcceuil.setForeground(Color.BLACK);;
        this.ButtonPrecedent = new JButton("<-");
		ButtonPrecedent.setForeground(Color.BLACK);;
		panel.add(ButtonAcceuil,BorderLayout.EAST);
		panel.add(ButtonPrecedent,BorderLayout.WEST);
		ButtonAcceuil.addActionListener(e -> {
			SwingUtilities.getWindowAncestor(ButtonAcceuil).dispose();
			cAccueil.LanceAccueil();
		});
        ButtonPrecedent.addActionListener(e -> {
			SwingUtilities.getWindowAncestor(ButtonAcceuil).dispose();
            cProjets.setProjetSelectionne(controleur.getProjetSelectionne());
			cProjets.LancerUnProjet();
		});
		return panel;
	}
    private void actions(){
        listeSeances.addListSelectionListener(e -> {
            if (listeSeances.getSelectedValue() != null) {
                boutonSupprimer.setEnabled(true);
                boutonConsulter.setEnabled(true);
            }
        });
        boutonAjouter.addActionListener(e -> {
            this.dispose();
            controleur.LanceViewFormulairSeance();
        });
        boutonConsulter.addActionListener(e -> {
            if (listeSeances.getSelectedValue() != null) {
               controleur.setSeanceSelectionne(listeSeances.getSelectedValue());
               this.dispose();
                controleur.LanceViewSeance();
            }
        });
        boutonSupprimer.addActionListener(e -> {
            if (listeSeances.getSelectedValue() != null) {
                controleur.supprimerSeance(listeSeances.getSelectedValue());
            }
        });
    }
}