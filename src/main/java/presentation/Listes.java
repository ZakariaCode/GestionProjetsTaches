package presentation;
import java.awt.*;
import javax.swing.*;
import metier.Pojo.Liste;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;
import presentation.Models.ModelListes;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import presentation.Controlleurs.*;

public class Listes extends JFrame implements Tools {
    private JPanel Head, searchPanel, panelPrincipal, panelBoutons;
    private JLabel searchLabel, iconLabel;
    private JList<Liste> listeListes;
    private JButton boutonAjouter, searchButton,btnTacheExist, boutonSupprimer, ButtonAcceuil, ButtonPrecedent,bouttonTache;
    private Font boldFont;
    private JTextField searchField;
    private ImageIcon scaledIcon;
    private JScrollPane scrollPane;
    private ControleurListes controleur;
    private ControleurTaches cTaches;
    private ControleurAccueil cAccueil ;

    public Listes(ControleurListes c) {
        super("Listes");
        this.controleur = c;
        this.initialiser();
        this.dessiner();
        this.actions();

    }

    private void initialiser() {
        cAccueil = new ControleurAccueil();
        Head = new JPanel(new GridLayout(2, 1));
        panelPrincipal = new JPanel();
        searchLabel = new JLabel("Vos Listes");
        boldFont = new Font(searchLabel.getFont().getName(), Font.BOLD, searchLabel.getFont().getSize());
        searchField = new JTextField(20);
        searchPanel = new JPanel();
        searchButton = new JButton("Rechercher");
        scaledIcon = rescaledIcon("Presentation/images/filtre.jpg", 25, 25);
        listeListes = new JList<>();
        iconLabel = new JLabel();
        scrollPane = new JScrollPane(listeListes);
        panelBoutons = new JPanel();
        boutonAjouter = new JButton("Ajouter");
        boutonSupprimer = new JButton("Supprimer");
        bouttonTache = new JButton("Tâches");
        btnTacheExist = new JButton("Ajouter des Tâches existantes");

    }

    private void dessiner() {
        this.setLayout(new BorderLayout());
        searchButton.setEnabled(false);
        Head.add(headerPanel());
        Head.setBorder(new EmptyBorder(0, 0, 5, 0));
        Head.add(navigation("Listes"));
        this.add(Head, BorderLayout.NORTH);
        this.add(footerPanel(), BorderLayout.SOUTH);
        panelPrincipal.setLayout(new BorderLayout());
        searchLabel.setFont(boldFont);
        searchPanel.setLayout(new FlowLayout());
        searchPanel.add(searchLabel);
        iconLabel.setIcon(scaledIcon);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(iconLabel);
        listeListes.setCellRenderer(new ListeCellRenderer());
        panelPrincipal.add(searchPanel, BorderLayout.NORTH);
        controleur.setModel(new ModelListes(controleur.getAllListes()));
        listeListes.setModel(controleur.getListesModel());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        bouttonTache.setEnabled(false);
        btnTacheExist.setEnabled(false);
        panelBoutons.add(bouttonTache);
        panelBoutons.add(boutonAjouter);
        panelBoutons.add(boutonSupprimer);
        panelBoutons.add(btnTacheExist);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        getContentPane().add(panelPrincipal);
    }

    private JPanel navigation(String fenetre) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        this.ButtonAcceuil = new JButton("Accueil");
        ButtonAcceuil.setForeground(Color.BLACK);
        this.ButtonPrecedent = new JButton("<-");
        ButtonPrecedent.setForeground(Color.BLACK);
        panel.add(ButtonAcceuil, BorderLayout.EAST);
        panel.add(ButtonPrecedent, BorderLayout.WEST);
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

    private void actions() {
        listeListes.addListSelectionListener(e -> {
            if (listeListes.getSelectedValue() != null) {
                bouttonTache.setEnabled(true);
                btnTacheExist.setEnabled(true);
            }
        });
        bouttonTache.addActionListener(e -> {
            dispose();
            controleur.setListeSelectionne(listeListes.getSelectedValue());
            cTaches=new ControleurTaches(controleur.getListeSelectionne());
            cTaches.LancerViewTaches("Liste");
        });
        boutonAjouter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                controleur.setModel(new ModelListes());
                controleur.LancerFormulaireListe();
            }
        });
        boutonSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomListe = listeListes.getSelectedValue().getNomListe();
                boolean result = controleur.supprimerListe(nomListe);
                if (!result) {
                    JOptionPane.showMessageDialog(null, "Impossible de supprimer la liste !", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    dispose();
                    controleur.setModel(new ModelListes());
                    controleur.LancerListes();
                }
            }
        });
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                controleur.getListesModel().clear();
                controleur.getListesModel().addAll(controleur.rechercheListe(searchField.getText()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                controleur.getListesModel().clear();
                controleur.getListesModel().addAll(controleur.rechercheListe(searchField.getText()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                controleur.getListesModel().clear();
                controleur.getListesModel().addAll(controleur.rechercheListe(searchField.getText()));
            }

        });
        btnTacheExist.addActionListener(e -> {
            cTaches=new ControleurTaches(listeListes.getSelectedValue());
            cTaches.LanceViewChoisi();
        });

    }
}