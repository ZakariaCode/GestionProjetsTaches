package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import metier.Pojo.Tache;
import presentation.Controlleurs.*;
import java.awt.*;
public class Taches extends JFrame implements Tools {

    private JPanel Head, panelPrincipal, panelBoutons;
    private JList<Tache> listeTaches;
    private JButton boutonSupprimer;
    private JLabel searchLabel;
    private JButton boutonDoc, ButtonAcceuil, ButtonPrecedent, buttonFiltrer, bouttonConsulter, buttonRefrecher,boutonAdd;
    private JTextField searchField;
    private JPanel searchPanel;
    private JScrollPane scrollPane;
    private Font boldFont;
    private JComboBox<String> searchOption;
    private ControleurTaches controleur;
    private ControleurDocuments controleurDoc;
    private ImageIcon scaledIconFiltrer, scaledIconRefrecher;
    private ControleurAccueil cAccueil;
    private ControleurProjets cProjets;
    private ControleurListes cListes;
    private String page;

    public Taches(ControleurTaches c, String page) {
        super("Tâches");
        this.controleur = c;
        this.page = page;
        this.initialiser();
        this.dessiner();
        this.actions();
    }

    private void initialiser() {
        cAccueil = new ControleurAccueil();
        cProjets = new ControleurProjets();
        Head = new JPanel(new GridLayout(2, 1));
        panelPrincipal = new JPanel();
        searchLabel = new JLabel("Vos Tâches");
        boldFont = new Font(searchLabel.getFont().getName(), Font.BOLD, searchLabel.getFont().getSize());
        searchField = new JTextField(20);
        scaledIconFiltrer = rescaledIcon("src/main/java/presentation/images/Filtre.png", 20, 20);
        scaledIconRefrecher = rescaledIcon("src/main/java/presentation/images/refresh.png", 20, 20);
        listeTaches = new JList<>();
        scrollPane = new JScrollPane(listeTaches);
        searchPanel = new JPanel();
        boutonSupprimer = new JButton("Supprimer");
        boutonDoc = new JButton("Documents");
        boutonAdd = new JButton("Ajouter Tache");
        bouttonConsulter = new JButton("Consulter");
        buttonFiltrer = new JButton();
        buttonRefrecher = new JButton();
        panelBoutons = new JPanel();
        searchOption = new JComboBox<>(new String[] { "Titre", "Description" });

    }

    private void dessiner() {
        this.setLayout(new BorderLayout());
        Head.add(headerPanel());
        Head.setBorder(new EmptyBorder(0, 0, 5, 0));
        Head.add(navigation("Taches"));
        this.add(Head, BorderLayout.NORTH);
        this.add(footerPanel(), BorderLayout.SOUTH);
        panelPrincipal.setLayout(new BorderLayout());
        searchLabel.setFont(boldFont);
        buttonFiltrer.setIcon(scaledIconFiltrer);
        buttonRefrecher.setIcon(scaledIconRefrecher);
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(searchLabel);
        searchPanel.add(searchField);
        searchPanel.add(searchOption);
        searchPanel.add(buttonFiltrer);
        searchPanel.add(buttonRefrecher);
        panelPrincipal.add(searchPanel, BorderLayout.NORTH);
        listeTaches.setCellRenderer(new TacheCellRenderer());
        if (page.equals("Liste")) {
            listeTaches.setModel(controleur.getTachesListe());
        } else {
            listeTaches.setModel(controleur.getTachesProjet());
        }
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        boutonSupprimer.setEnabled(false);
        boutonDoc.setEnabled(false);
        bouttonConsulter.setEnabled(false);
        panelBoutons.add(bouttonConsulter);
        panelBoutons.add(boutonSupprimer);
        panelBoutons.add(boutonDoc);
        panelBoutons.add(boutonAdd);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        getContentPane().add(panelPrincipal);
        setSize(new Dimension(600, 400));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private JPanel navigation(String fenetre) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.ButtonAcceuil = new JButton("Accueil");
        ButtonAcceuil.setForeground(Color.BLACK);
        ;
        this.ButtonPrecedent = new JButton("<-");
        ButtonPrecedent.setForeground(Color.BLACK);
        ;
        panel.add(ButtonAcceuil, BorderLayout.EAST);
        panel.add(ButtonPrecedent, BorderLayout.WEST);
        ButtonAcceuil.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(ButtonAcceuil).dispose();
            cAccueil.LanceAccueil();
        });
        ButtonPrecedent.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(ButtonPrecedent).dispose();
            if (page.equals("Liste")) {
                controleur.getTachesListe().clear();
                cListes = new ControleurListes();
                cListes.setListeSelectionne(controleur.getListeSelectionne());
                cListes.LancerListes();
            } else {
                controleur.getTachesProjet().clear();
                cProjets.setProjetSelectionne(controleur.getProjetSelectionne());
                cProjets.LancerUnProjet();
            }
        });
        return panel;
    }

    private void actions(){
        listeTaches.addListSelectionListener(e -> {
            if (listeTaches.getSelectedValue() != null) {
                boutonDoc.setEnabled(true);
                boutonSupprimer.setEnabled(true);
                bouttonConsulter.setEnabled(true);
            }
        });
        if(page.equals("Liste")){
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                        controleur.rechercherTachesListe(search,false);     
                     }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                        controleur.rechercherTachesListe(search,true);   
                     }
                }
                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                        controleur.rechercherTachesListe(search,false); 
                    }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                        controleur.rechercherTachesListe(search,true);
                    }
                }
    
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                   if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                    controleur.rechercherTachesListe(search,false);        
                 }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                    controleur.rechercherTachesListe(search,true);  
                 }
                }
               
                });

        }
        else{
            searchField.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                        controleur.rechercherTachesProjet(search,false);     
                     }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                        controleur.rechercherTachesProjet(search,true);   
                     }
                }
                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                    if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                        controleur.rechercherTachesProjet(search,false); 
                    }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                        controleur.rechercherTachesProjet(search,true);
                    }
                }
    
                @Override
                public void changedUpdate(DocumentEvent arg0) {
                    String search = searchField.getText();
                   if(((String)searchOption.getSelectedItem()).equals("Titre")){  
                    controleur.rechercherTachesProjet(search,false);        
                 }else if(((String)searchOption.getSelectedItem()).equals("Description")){
                    controleur.rechercherTachesProjet(search,true);  
                 }
                }
               
                });
        }

    boutonSupprimer.addActionListener(e->{

    Tache tache = listeTaches
            .getSelectedValue();controleur.SupprimerTache(tache,controleur.getProjetSelectionne(),page);

    });
    boutonDoc.addActionListener(e->{
        Tache tache = listeTaches
                .getSelectedValue();if(page.equals("Liste")){controleurDoc=new ControleurDocuments(controleur.getListeSelectionne(),tache);}else{controleurDoc=new ControleurDocuments(tache);controleurDoc.setProjetSelectionne(controleur.getProjetSelectionne());}this.dispose();controleurDoc.LancerVueDocuments();});
    buttonFiltrer.addActionListener(e->{
        JComboBox<String> TypeFiltre = new JComboBox<>();TypeFiltre.addItem("categorie: ");TypeFiltre.addItem("    ENCADREMENT");TypeFiltre.addItem("    ENSEIGNEMENT");TypeFiltre.addItem("    AUTRE");

        JOptionPane optionPane = new JOptionPane(TypeFiltre, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane
                .createDialog("Filtrer");dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);dialog.setVisible(true);

        if(optionPane.getValue()!=null){
        String selectedValue = TypeFiltre.getSelectedItem().toString();
        if(selectedValue.equals("    ENCADREMENT")||selectedValue.equals("    ENSEIGNEMENT")||selectedValue.equals("    AUTRE"))
        {
            if (page.equals("Liste")) {
                controleur.RechercheParCategorieTacheListe(Tache.categorie.valueOf(selectedValue.trim()));
            } else {
                controleur.RechercheParCategorieTacheProjet(Tache.categorie.valueOf(selectedValue.trim()));
            }
        }

    }

});
buttonRefrecher.addActionListener(e->{
    if(page.equals("Liste")){
        controleur.refrecherTachesListe();
    }else{
       controleur.refrecherTachesProjet();
    }
});
boutonAdd.addActionListener(e->{
    this.dispose();
    if(page=="Liste"){
        controleur.LancerViewAjoutTache("Liste");
    }else{
        controleur.LancerViewAjoutTache("Projet");
    }
});
}
}