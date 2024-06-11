package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.*;
import persistance.DAOProjet;
import presentation.Controlleurs.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;
public class FormulaireProjet extends JFrame implements Tools {
    private JPanel headerPanel, panel, dateDepart, dateFin, panelBoutton, footerPanel;
    private JLabel labelCategorie, titre, labeltype, labelDescription, labelDateDepart, labelDateFin;
    private JTextArea areaDescription;
    private JTextField titreProjet;
    private JComboBox<Projet.categorie> choixCategorie;
    private JComboBox<Projet.type> choixtype;
    private JComboBox<String> choixJoursdepart, choixMoisDepart, choixAnneeDepart, choixHeureDepart, choixMinuteDepart;
    private JComboBox<String> choixJoursfin, choixMoisFin, choixAnneeFin, choixHeureFin, choixMinuteFin;
    private JButton Ajouter, Annuler, ButtonAcceuil, ButtonPrecedent;
    private boolean modification;
    private DAOProjet dao = new DAOProjet();
    private String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
            "Octobre", "Novembre", "Décembre" };
    String[] annees = { "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" };
    private String[] heures = new String[24];
    private String[] minutes = new String[60];
    private String[] jours = new String[31];
    private ControleurProjets c;
    private ControleurAccueil cAccueil;

    public FormulaireProjet(ControleurProjets c, boolean modifier) {
        super("Modifier Projet");
        this.c = c;
        this.modification = modifier;
        this.initialiser();
        this.dessiner();
        this.actions();
    }

    private void initialiser() {
        cAccueil = new ControleurAccueil();
        headerPanel = new JPanel(new GridLayout(2, 1));
        titre = new JLabel("titre du projet :");
        labelCategorie = new JLabel("Catégorie du projet : ");
        labeltype = new JLabel("Type de projet : ");
        choixCategorie = new JComboBox<>(Projet.categorie.values());
        choixtype = new JComboBox<>(Projet.type.values());
        labelDescription = new JLabel("Description du document : ");
        areaDescription = new JTextArea(5, 20);
        labelDateDepart = new JLabel("Date de création : ");
        labelDateFin = new JLabel("Date de fin : ");
        for (int i = 0; i < 24; i++) {
            heures[i] = String.format("%02d", i);
        }
        for (int i = 0; i < 31; i++) {
            jours[i] = String.format("%02d", i + 1);
        }
        for (int i = 0; i < 60; i++) {
            minutes[i] = String.format("%02d", i);
        }
        choixJoursdepart = new JComboBox<>(jours);
        choixMoisDepart = new JComboBox<>(mois);
        choixAnneeDepart = new JComboBox<>(annees);
        choixHeureDepart = new JComboBox<>(heures);
        choixMinuteDepart = new JComboBox<>(minutes);
        choixJoursfin = new JComboBox<>(jours);
        choixMoisFin = new JComboBox<>(mois);
        choixAnneeFin = new JComboBox<>(annees);
        choixHeureFin = new JComboBox<>(heures);
        choixMinuteFin = new JComboBox<>(minutes);
        panel = new JPanel(new GridLayout(6, 2));
        dateDepart = createDateTimePanel(choixJoursdepart, choixMoisDepart, choixAnneeDepart, choixHeureDepart,
                choixMinuteDepart);
        dateFin = createDateTimePanel(choixJoursfin, choixMoisFin, choixAnneeFin, choixHeureFin, choixMinuteFin);
        panelBoutton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Ajouter = new JButton("Valider");
        Annuler = new JButton("Annuler");
        titreProjet = new JTextField(20);
        footerPanel = new JPanel(new BorderLayout());
    }

    private void dessiner() {
        setSize(700, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        headerPanel.add(headerPanel());
        headerPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        headerPanel.add(navigation("FormulaireProjet"));
        this.add(headerPanel, BorderLayout.NORTH);
        titreProjet.setText(c.getProjetSelectionne().getTitreProjet());
        choixCategorie.setSelectedItem(c.getProjetSelectionne().getCategorieProjet());
        choixtype.setSelectedItem(c.getProjetSelectionne().getTypeProjet());
        areaDescription.setText(c.getProjetSelectionne().getDescriptionProjet());
        panel.add(titre);
        panel.add(titreProjet);
        panel.add(labelCategorie);
        panel.add(choixCategorie);
        panel.add(labeltype);
        panel.add(choixtype);
        panel.add(labelDescription);
        panel.add(new JScrollPane(areaDescription));
        panel.add(labelDateDepart);
        panel.add(dateDepart);
        panel.add(labelDateFin);
        panel.add(dateFin);
        panel.setBorder(new EmptyBorder(25, 70, 0, 70));
        getContentPane().add(panel, BorderLayout.CENTER);
        panelBoutton.add(Ajouter);
        panelBoutton.add(Annuler);
        panelBoutton.setBorder(new EmptyBorder(0, 0, 35, 50));
        footerPanel.add(panelBoutton, BorderLayout.NORTH);
        footerPanel.add(footerPanel(), BorderLayout.SOUTH);
        this.add(footerPanel, BorderLayout.SOUTH);
    }

    private JPanel createDateTimePanel(JComboBox<String> jours, JComboBox<String> mois, JComboBox<String> annees,
            JComboBox<String> heures, JComboBox<String> minutes) {
        JPanel datePanel = new JPanel(new FlowLayout());
        datePanel.add(jours);
        datePanel.add(mois);
        datePanel.add(annees);
        datePanel.add(heures);
        datePanel.add(minutes);
        return datePanel;
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
            SwingUtilities.getWindowAncestor(ButtonAcceuil).dispose();
            c.LanceViewProjets();
        });
        return panel;
    }

    private void actions() {
        Annuler.addActionListener(e -> {
            if (modification) {
                this.dispose();
                c.LancerUnProjet();
            } else {
                this.dispose();
                c.getProjetModel().clear();
                c.LanceViewProjets();
            }

        });
        Ajouter.addActionListener(e -> {
            if (areaDescription.getText().isEmpty() || titreProjet.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir toutes les informations du projet", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (!modification) {
                    Projet projet = new Projet();
                    String titre = titreProjet.getText();
                    projet.setTitreProjet(titre);
                    projet.setCategorieProjet((Projet.categorie) choixCategorie.getSelectedItem());
                    projet.setTypeProjet((Projet.type) choixtype.getSelectedItem());
                    projet.setDescriptionProjet(areaDescription.getText());
                    String jourDepartStr = (String) choixJoursdepart.getSelectedItem();
                    String moisDepartStr = (String) choixMoisDepart.getSelectedItem();
                    String anneeDepartStr = (String) choixAnneeDepart.getSelectedItem();
                    String heureDepartStr = (String) choixHeureDepart.getSelectedItem();
                    String minuteDepartStr = (String) choixMinuteDepart.getSelectedItem();
                    String jourFinStr = (String) choixJoursfin.getSelectedItem();
                    String moisFinStr = (String) choixMoisFin.getSelectedItem();
                    String anneeFinStr = (String) choixAnneeFin.getSelectedItem();
                    String heureFinStr = (String) choixHeureFin.getSelectedItem();
                    String minuteFinStr = (String) choixMinuteFin.getSelectedItem();
                
                    try {
                        int jourDepart = Integer.parseInt(jourDepartStr);
                        int moisDepart = Arrays.asList(mois).indexOf(moisDepartStr) + 1;
                        int anneeDepart = Integer.parseInt(anneeDepartStr);
                        int heureDepart = Integer.parseInt(heureDepartStr);
                        int minuteDepart = Integer.parseInt(minuteDepartStr);
                        int jourFin = Integer.parseInt(jourFinStr);
                        int moisFin = Arrays.asList(mois).indexOf(moisFinStr) + 1;
                        int anneeFin = Integer.parseInt(anneeFinStr);
                        int heureFin = Integer.parseInt(heureFinStr);
                        int minuteFin = Integer.parseInt(minuteFinStr);
                
                        if (heureDepart < 0 || heureDepart > 23 || heureFin < 0 || heureFin > 23) {
                            JOptionPane.showMessageDialog(this, "Heure invalide, veuillez sélectionner une heure entre 0 et 23", "Erreur", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                
                        LocalDateTime dateDebut = LocalDateTime.of(anneeDepart, moisDepart, jourDepart, heureDepart, minuteDepart);
                        LocalDateTime dateFin = LocalDateTime.of(anneeFin, moisFin, jourFin, heureFin, minuteFin);
                        projet.setDatedebutProjet(dateDebut);
                        projet.setDelaiProjet(dateFin);
                
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de la conversion des valeurs", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                    JOptionPane.showMessageDialog(this, "Projet ajouté avec succès", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println(projet);
                    dao.CreateProjet(projet);
                    c.getProjetModel().clear();
                } else {
                    String TitreProjet = c.getProjetSelectionne().getTitreProjet();
                    dao.updateProjet("TitreProjet", TitreProjet, "TitreProjet", titreProjet.getText());
                    dao.updateProjet("TitreProjet", TitreProjet, "DescriptionProjet", areaDescription.getText());
                    c.getProjetModel().clear();
                }
                this.dispose();
                c.LanceViewProjets();
            }

        });

    }
}