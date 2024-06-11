package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.*;
import java.awt.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import metier.Gestion.GestionnaireImport;
import metier.Pojo.Projet;
import persistance.DAOProjet;
import presentation.Controlleurs.*;

public class Projets extends JFrame implements Tools {

    private JPanel panelPrincipal, Head, panelBoutons;
    private JLabel searchLabel;
    private JList<Projet> listeProjets;
    private JButton boutonAjouter,ButtonAcceuil,ButtonPrecedent,buttonRefrecher,bouttonConsulter,buttonFiltrer, boutonCloner, boutonImporter, searchButton;
    private JPanel searchPanel;
    private JTextField searchField;
    private Font boldFont;
    private JComboBox<String> searchOption;
    private JScrollPane scrollPane;
    private ImageIcon scaledIconFiltrer, scaledIconRefrecher,scaledRechercher;
    private DAOProjet d = new DAOProjet();
   
    private ControleurProjets c;
    private ControleurAccueil cAccueil;
    
    public Projets(ControleurProjets c) {
        super("Projets");
        this.c=c;
        this.initialiser();
        this.dessiner();
        this.action();
    }

    private void initialiser() {
        cAccueil = new ControleurAccueil();
        Head = new JPanel(new GridLayout(2, 1));
        panelPrincipal = new JPanel();
        searchLabel = new JLabel("Vos Projets");
        boldFont = new Font(searchLabel.getFont().getName(), Font.BOLD, searchLabel.getFont().getSize());
        searchField = new JTextField(20);
        scrollPane = new JScrollPane();
        listeProjets = new JList<>();
        scrollPane.setViewportView(listeProjets);
        searchButton = new JButton();
        scaledIconFiltrer = rescaledIcon("src/main/java/presentation/images/Filtre.png", 20, 20);
        scaledIconRefrecher = rescaledIcon("src/main/java/presentation/images/refresh.png", 20, 20);
        scaledRechercher = rescaledIcon("src/main/java/presentation/images/searche.png", 24, 20);
        panelBoutons = new JPanel();
        boutonAjouter = new JButton("Ajouter");
        boutonCloner = new JButton("Clôner");
        boutonImporter = new JButton("Importer");
        bouttonConsulter = new JButton("Consulter");
        buttonFiltrer = new JButton();
        buttonRefrecher = new JButton();
        searchPanel = new JPanel();
        searchOption = new JComboBox<>(new String[]{"Titre","Description"});

    }

    private void dessiner() {
        this.setLayout(new BorderLayout());
        Head.add(headerPanel());
        Head.add(navigation("Projets"));
        this.add(Head, BorderLayout.NORTH);
        this.add(footerPanel(), BorderLayout.SOUTH);
        listeProjets.setCellRenderer(new ProjetCellRenderer());
        panelPrincipal.setLayout(new BorderLayout());
        searchLabel.setFont(boldFont);
        buttonFiltrer.setIcon(scaledIconFiltrer);
        buttonRefrecher.setIcon(scaledIconRefrecher);
        searchButton.setIcon(scaledRechercher);
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchButton);
        searchPanel.add(searchField);
        searchPanel.add(searchOption);
        searchPanel.add(buttonFiltrer);
        searchPanel.add(buttonRefrecher);
        panelPrincipal.add(searchPanel, BorderLayout.NORTH);
        listeProjets.setModel(c.getProjetModel());
        scrollPane.setViewportView(listeProjets);
        scrollPane.revalidate();
        scrollPane.repaint();
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        boutonCloner.setEnabled(false);
        bouttonConsulter.setEnabled(false);
        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonCloner);
        panelBoutons.add(bouttonConsulter);
        panelBoutons.add(boutonImporter);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        getContentPane().add(panelPrincipal);
        c.getProjetModel().addAll(d.getAllProjets());   
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
			cAccueil.LanceAccueil();
		});
		return panel;
	}
    public void action(){
        listeProjets.addListSelectionListener(e -> {
            if (listeProjets.getSelectedValue() != null) {
                boutonCloner.setEnabled(true);
                bouttonConsulter.setEnabled(true);
            }
        });
        bouttonConsulter.addActionListener(e -> {
            if (listeProjets.getSelectedValue() != null) {
                String titreProjetSelectionne = listeProjets.getSelectedValue().getTitreProjet();
                Projet projetSelectionne = d.ReadProjet(titreProjetSelectionne);
                c.setProjetSelectionne(projetSelectionne);
                c.LancerUnProjet();
                this.dispose();
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String search = searchField.getText();
               c.getProjetModel().clear();
                if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                   c.getProjetModel().addAll(d.RechercheParTitre(search));      
                }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                   c.getProjetModel().addAll(d.RechercheParDescription(search));  
                }
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                String search = searchField.getText();
                c.getProjetModel().clear();
                if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                   c.getProjetModel().addAll(d.RechercheParTitre(search));      
                }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                   c.getProjetModel().addAll(d.RechercheParDescription(search));  
                }
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                String search = searchField.getText();
                c.getProjetModel().clear();
                if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                   c.getProjetModel().addAll(d.RechercheParTitre(search));      
                }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                   c.getProjetModel().addAll(d.RechercheParDescription(search));  
                }
            }
        });
        boutonCloner.addActionListener(e -> {
            d.ClonerProjet(listeProjets.getSelectedValue());
           c.getProjetModel().clear();
           c.getProjetModel().addAll(d.getAllProjets());
        });
        boutonAjouter.addActionListener(e -> {
            this.dispose();
            c.setProjetSelectionne(new Projet());
            c.LanceFormulaireProjet();
        });
        boutonImporter.addActionListener(e -> {
                GestionnaireImport g = new GestionnaireImport();
                try {
                    g.enregistreImport();
                    JOptionPane.showMessageDialog(null, "Importation terminée ✔", "Importation", JOptionPane.INFORMATION_MESSAGE);
                   c.getProjetModel().clear();
                   c.getProjetModel().addAll(d.getAllProjets());

                } catch (GeneralSecurityException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        });
        buttonFiltrer.addActionListener(e -> {
            JComboBox<String> TypeFiltre = new JComboBox<>();     
            TypeFiltre.addItem("type: ");
            TypeFiltre.addItem("    THESE");
            TypeFiltre.addItem("    PFA");
            TypeFiltre.addItem("    PFE");
            TypeFiltre.addItem("    AUTRE");
            TypeFiltre.addItem("categorie: ");
            TypeFiltre.addItem("    ENCADREMENT");
            TypeFiltre.addItem("    ENSEIGNEMENT");
            TypeFiltre.addItem("    AUTRE");

            JOptionPane optionPane = new JOptionPane(TypeFiltre, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
            JDialog dialog = optionPane.createDialog("Filtrer");
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
            
            if (optionPane.getValue() != null) {
                String selectedValue = TypeFiltre.getSelectedItem().toString();
                
                if (selectedValue.equals("    THESE") || selectedValue.equals("    PFA") || selectedValue.equals("    PFE") || selectedValue.equals("    AUTRE")) {
                   c.getProjetModel().clear();
                   c.getProjetModel().addAll(d.RechercheParType( Projet.type.valueOf(selectedValue.trim())));
                } else if (selectedValue.equals("    ENCADREMENT") || selectedValue.equals("    ENSEIGNEMENT") || selectedValue.equals("    AUTRE")) {
                   c.getProjetModel().clear();
                   c.getProjetModel().addAll(d.RechercheParCategorie(Projet.categorie.valueOf(selectedValue.trim())));
                }
            }
            buttonRefrecher.addActionListener(e1 -> {
               c.getProjetModel().clear();
               c.getProjetModel().addAll(d.getAllProjets());
            });
    
        });
    }
}