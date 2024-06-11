package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.Projet;
import persistance.DAOProjet;
import presentation.Controlleurs.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
public class UnProjet extends JFrame implements Tools {

    private JPanel panelPrincipal,StatPanel,Head,stat;
    private JList<Projet> infoProjet;
    private JButton boutonCloturer;
    private JLabel label;
    private JButton boutonTache,ButtonModifier, ButtonAcceuil,ButtonPrecedent,boutonSeance,boutonDocuments,btnStatistique;
    private JScrollPane scrollPane;
    private  JPanel panelBoutons;
    private ControleurProjets controleur;
    private ControleurTaches cTaches;
    private ControleurAccueil cAccueil;
    private ControleurDocuments cDoc;
    private ControleurSeances cSeances;
   
    

    public UnProjet(ControleurProjets c) {
        super("Un projet");
        this.controleur=c;
        this.initialiser();
        this.dessiner();
        this.actions();
    }
    private void initialiser() {
        cAccueil = new ControleurAccueil();
        cSeances = new ControleurSeances();
        Head= new JPanel(new GridLayout(2,1));
        panelPrincipal = new JPanel();
        btnStatistique = new JButton("Statistique");
        StatPanel = new JPanel(new BorderLayout());
        stat = new JPanel(new BorderLayout());
        label = new JLabel();
        infoProjet = new JList<>();
        scrollPane = new JScrollPane(infoProjet);
        boutonCloturer = new JButton("Cloturer");
        boutonTache = new JButton("Tâches");
        boutonSeance = new JButton("Seances");
        boutonDocuments = new JButton("Documents");
        ButtonModifier=new JButton("modifier");
        panelBoutons = new JPanel();
    }
    private void dessiner() {
        this.setLayout(new BorderLayout());
        Head.add(headerPanel());
        Head.setBorder(new EmptyBorder(0, 0, 5, 0));
        Head.add(navigation("UnProjet"));
        this.add(Head,BorderLayout.NORTH);
        this.add(footerPanel(),BorderLayout.SOUTH);
        panelPrincipal.setLayout(new BorderLayout());
        btnStatistique.setBackground(Color.GREEN);
        StatPanel.setBorder(new EmptyBorder(0, 0, 0, 5));
        StatPanel.add(btnStatistique, BorderLayout.EAST);
        stat.add(StatPanel, BorderLayout.EAST);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, label.getFont().getSize()));
        label.setHorizontalAlignment(JLabel.CENTER);
        stat.add(label, BorderLayout.CENTER);
        panelPrincipal.add(stat, BorderLayout.NORTH);
        infoProjet.setCellRenderer(new DetailProjetCellRenderer());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        panelBoutons.add(boutonCloturer);
        panelBoutons.add(ButtonModifier);
        panelBoutons.add(boutonTache);
        panelBoutons.add(boutonSeance);
        panelBoutons.add(boutonDocuments);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        infoProjet.setModel(controleur.getProjetDetailModel());
        panelPrincipal.add(infoProjet);
        getContentPane().add(panelPrincipal);
        setSize(new Dimension(800, 650));
        String titre = (controleur.getProjetDetailModel().get(0)).getTitreProjet();
        label.setText(titre);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
    private JPanel navigation(String fenetre){
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
            controleur.getProjetModel().clear(); 
            controleur.LanceViewProjets();
		});
		return panel;
	}
    private void actions() {
        boutonTache.addActionListener(e -> {
            this.dispose();
            cTaches=new ControleurTaches(controleur.getProjetSelectionne());
            cTaches.LancerViewTaches("projet");
        });
        boutonCloturer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    boutonCloturer.setEnabled(false);
                    DAOProjet d =new DAOProjet();
                    d.CloturerProjet(controleur.getProjetDetailModel().get(0).getTitreProjet());
                    controleur.LanceViewProjets();
                }
        });
        ButtonModifier.addActionListener(e->{
            this.dispose();
            controleur.LanceFormulaireModificationProjet();
        });
        boutonDocuments.addActionListener(e -> {
            cDoc = new ControleurDocuments(controleur.getProjetSelectionne());
            this.dispose();
            cDoc.LancerVueDocuments();
        });
        boutonSeance.addActionListener(e -> {
            this.dispose();
            cSeances.setProjetSelectionne(controleur.getProjetSelectionne());
            cSeances.LanceViewSeances();
        });
    }
}