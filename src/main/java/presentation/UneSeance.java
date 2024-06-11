package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.Seance;
import presentation.Controlleurs.*;
import java.awt.*;
public class UneSeance extends JFrame implements Tools {
    private JPanel panelPrincipal,Head,HeadPanel,panelBoutons;
    private JList<Seance> listeUneSeance;
    private JButton Docs,ButtonModifier,ButtonAcceuil,ButtonPrecedent;
    private JLabel Label;
    private  Font boldFont;
    private JScrollPane scrollPane ;
    private ControleurSeances controleur;
    private ControleurAccueil cAccueil ;
    private ControleurDocuments cDocs;
    public UneSeance(ControleurSeances c) {
        super("UneSeance");
        this.controleur = c;
        this.initialiser();
        this.dessiner();
        this.actions();
    }
    private void initialiser() {
        cAccueil = new ControleurAccueil();
        Head= new JPanel(new GridLayout(2,1));
        panelPrincipal = new JPanel();
        Label = new JLabel("informations Séance");
        boldFont = new Font(Label.getFont().getName(), Font.BOLD, Label.getFont().getSize());
        HeadPanel = new JPanel();
        listeUneSeance = new JList<>();
        scrollPane = new JScrollPane(listeUneSeance);
        Docs = new JButton("Documents");
        ButtonModifier = new JButton("Modifier");
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
        listeUneSeance.setCellRenderer(new DetailSeanceCelleRendrer());
        listeUneSeance.setModel(controleur.getSeanceDetail());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        panelBoutons.add(Docs);
        panelBoutons.add(ButtonModifier);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        getContentPane().add(panelPrincipal);
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
            controleur.getseances().clear(); 
            controleur.LanceViewSeances();
		});
		return panel;
	}
    private void actions(){
        ButtonModifier.addActionListener(e -> {
                this.dispose();
                controleur.LanceViewFormulairModifierSeance();
        });
        Docs.addActionListener(e -> {
            this.dispose();
            cDocs=new ControleurDocuments(controleur.getSeanceSelectionne());
            cDocs.setProjetSelectionne(controleur.getProjetSelectionne());
            cDocs.LancerVueDocuments();
        });  
    }
}