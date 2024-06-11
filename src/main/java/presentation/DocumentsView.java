package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import metier.Pojo.Documents;
import metier.Pojo.Projet;
import presentation.Controlleurs.*;
import java.time.LocalDateTime;
import java.awt.*;
import java.io.File;
import java.io.IOException;;


public class DocumentsView extends JFrame implements Tools {
    private JPanel Head,searchPanel,panelBoutons;
    private JLabel searchLabel,iconLabel;
    private JPanel panelPrincipal;
    private JList<Documents> listeDocuments;
    private JTextField searchField;
    private JButton boutonAjouter,ButtonAcceuil,ButtonPrecedent;
    private  ImageIcon scaledIcon;
    private JScrollPane scrollPane;
    private Font boldFont;
    private ControleurDocuments controleur;
    private ControleurAccueil cAccueil ;
    private ControleurTaches cTaches;
    private ControleurProjets cProjets;
    private ControleurSeances cSeances;
    private String fenetre;

    public DocumentsView(ControleurDocuments c,String fenetre) {
        super("Documents");
        this.controleur = c;
        this.fenetre = fenetre;
        this.initialiser();
        this.dessiner();
        this.actions();
    }
    private void initialiser(){
        cAccueil = new ControleurAccueil();
        cProjets = new ControleurProjets();
        Head= new JPanel(new GridLayout(2,1));
        panelPrincipal = new JPanel();
        searchLabel = new JLabel("Vos Documents");
        boldFont = new Font(searchLabel.getFont().getName(), Font.BOLD, searchLabel.getFont().getSize());
        searchField = new JTextField(20);
        scaledIcon = rescaledIcon("Presentation/images/filtre.jpg", 25, 25);
        iconLabel = new JLabel();
        listeDocuments = new JList<>();
        scrollPane = new JScrollPane(listeDocuments);
        panelBoutons = new JPanel(new BorderLayout());
        searchPanel = new JPanel();
        boutonAjouter = new JButton("Ajouter Document");
    }
    private void dessiner(){
        this.setLayout(new BorderLayout()); 
        Head.add(headerPanel());
        Head.setBorder(new EmptyBorder(0, 0, 5, 0));
        Head.add(navigation());
        this.add(Head,BorderLayout.NORTH);
        this.add(footerPanel(),BorderLayout.SOUTH);
        panelPrincipal.setLayout(new BorderLayout());
        searchLabel.setFont(boldFont);
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(searchLabel);
        iconLabel.setIcon(scaledIcon);
        searchPanel.add(searchField);
        searchPanel.add(iconLabel);
        panelPrincipal.add(searchPanel, BorderLayout.NORTH);
        listeDocuments.setCellRenderer(new DocumentsCellRenderer());
        if(fenetre.equals("Unprojet")){
            if(!controleur.estVideDocumentProjet()){
                listeDocuments.setModel(controleur.getDocumentProjet());
            }
        }
        else if(fenetre.equals("UneSeance")){
            if(!controleur.estVideDocumentSeance()){
                listeDocuments.setModel(controleur.getDocumentSeance());
            }

        }
        else {
            if(!controleur.estVideDocumentTache()){
                listeDocuments.setModel(controleur.getDocumentTache());
            }

        }
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        panelBoutons.add(boutonAjouter, BorderLayout.EAST);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        getContentPane().add(panelPrincipal);
        setSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            Projet p=controleur.getProjetSelectionne();
			SwingUtilities.getWindowAncestor(ButtonAcceuil).dispose();
            if(fenetre.equals("Unprojet")){
                  cProjets.setProjetSelectionne(p);
                  cProjets.LancerUnProjet();
            }
            else if(fenetre.equals("UneTache")){
                cTaches = new ControleurTaches();
                cTaches.setTacheSelectionne(controleur.getTacheSelectionne());
                cTaches.setProjetSelectionne(controleur.getProjetSelectionne());
                cTaches.LancerViewTaches("projet");
            }
            else if(fenetre.equals("UneSeance")){
                cSeances = new ControleurSeances();
                cSeances.setSeanceSelectionne(controleur.getSeanceSelectionne());
                cSeances.setProjetSelectionne(controleur.getProjetSelectionne());
                cSeances.LanceViewSeance();
            }
            else if(fenetre.equals("Liste")){
                cTaches = new ControleurTaches();
                cTaches.setListeSelectionne(controleur.getListeSelectionne());
                cTaches.LancerViewTaches("Liste");
            }
		});
		return panel;
	}
    private void actions(){
        
        if(fenetre.equals("Unprojet")){
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheProjet(search); 
                }
                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheProjet(search); 
                }
    
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheProjet(search); 
                }
                });
        }
        else if(fenetre.equals("UneSeance")){
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                   controleur.SetDocumentsRechercheSeance(search); 
                }
                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheSeance(search); 
                }
    
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheSeance(search); 
                }
                });
        }
        else{
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheTaches(search);
                }
                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheTaches(search); 
                }
    
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    controleur.SetDocumentsRechercheTaches(search); 
                }
                });

        }
       
       
        boutonAjouter.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                Documents d = new Documents();
                d.setLibelleDoc(selectedFile.getName());
                d.setPath(selectedFile.getAbsolutePath());
                d.setDateAjout(LocalDateTime.now());
                if(this.fenetre.equals("Unprojet")){
                    if(controleur.estVideDocumentProjet()){
                        controleur.AjouterDocumentProjet(d);
                        listeDocuments.setModel(controleur.getDocumentProjet());
                    }
                    else{
                        controleur.AjouterDocumentProjet(d);
                    }
                }
                else if(this.fenetre.equals("UneSeance")){
                    if(controleur.estVideDocumentSeance()){
                        controleur.AjouterDocumentSeance(d);
                        listeDocuments.setModel(controleur.getDocumentSeance());
                    }
                    else{
                        controleur.AjouterDocumentSeance(d);
                    }
                }
                else{
                    if(controleur.estVideDocumentTache()){
                        controleur.AjouterDocumentTache(d);
                        listeDocuments.setModel(controleur.getDocumentTache());
                    }
                    else{
                        controleur.AjouterDocumentTache(d);
                    }
                }
            }
        });
        listeDocuments.addListSelectionListener(e -> {
            if (listeDocuments.getSelectedValue() != null) {
                Documents d = listeDocuments.getSelectedValue();
                File file = new File(d.getPath());
                if(file.exists()){
                    try {
                        Desktop.getDesktop().open(file);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Desktop n'est pas supporté !", "Fichier non supporté", JOptionPane.ERROR_MESSAGE);
                    }
                }
                else{
                    JOptionPane.showMessageDialog(this, "Ce Document n'existe pas sur votre pc !", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}
