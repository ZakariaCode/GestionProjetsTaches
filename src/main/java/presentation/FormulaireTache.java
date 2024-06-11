package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.Tache;
import presentation.Controlleurs.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.Arrays;

public class FormulaireTache extends JFrame implements Tools {
    private JPanel headerPanel, panel, dateDepart, dateFin, panelBoutton, footerPanel;
    private JLabel labeltitre, labelCategorie, labelDescription, labelDateDepart, labelDateFin;
    private JTextArea areaDescription;
    private JTextField titreTache;
    private JComboBox<Tache.categorie> choixCategorie;
    private JComboBox<String> choixJoursdepart, choixMoisDepart, choixAnneeDepart, choixHeureDepart, choixMinuteDepart;
    private JComboBox<String> choixJoursfin, choixMoisFin, choixAnneeFin, choixHeureFin, choixMinuteFin;
    private String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
            "Octobre", "Novembre", "Décembre" };
    String[] annees = { "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" };
    private String[] heures = new String[24];
    private String[] minutes = new String[60];
    private String[] jours = new String[31];
    private JButton Ajouter, ButtonAcceuil, ButtonPrecedent, Annuler;
    private ControleurTaches controleur;
    private ControleurAccueil cAccueil;
    private boolean modification;
    private String page;

    public FormulaireTache(ControleurTaches c, boolean modifier,String page) {
        super("Ajouter Tâche");
        this.controleur = c;
        this.modification = modifier;
        this.page=page;
        this.initialiser();
        this.dessiner();
        this.actions();
    }

    private void initialiser() {
        cAccueil = new ControleurAccueil();
        headerPanel = new JPanel(new GridLayout(2, 1));
        labelCategorie = new JLabel("Catégorie de la tâche : ");
        labeltitre = new JLabel("titre de la tâche : ");
        choixCategorie = new JComboBox<>(Tache.categorie.values());
        labelDescription = new JLabel("Description du tâche : ");
        areaDescription = new JTextArea(5, 20);
        titreTache = new JTextField(20);
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
        panel = new JPanel();
        dateDepart = createDateTimePanel(choixJoursdepart, choixMoisDepart, choixAnneeDepart, choixHeureDepart,
                choixMinuteDepart);
        dateFin = createDateTimePanel(choixJoursfin, choixMoisFin, choixAnneeFin, choixHeureFin, choixMinuteFin);
        panelBoutton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        Ajouter = new JButton("Valider");
        Annuler = new JButton("Annuler");
        footerPanel = new JPanel(new BorderLayout());
    }

    private void dessiner() {
        headerPanel.add(headerPanel());
        headerPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        headerPanel.add(navigation("FormulaireTache"));
        if (modification==true) {
            titreTache.setText(controleur.getTacheSelectionne().getTitreTache());
            choixCategorie.setSelectedItem(controleur.getTacheSelectionne().getCategorieTache());
            areaDescription.setText(controleur.getTacheSelectionne().getDescriptionTache());
        }
        this.add(headerPanel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(5, 2));
        panel.add(labeltitre);
        panel.add(titreTache);
        panel.add(labelCategorie);
        panel.add(choixCategorie);
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
            SwingUtilities.getWindowAncestor(ButtonPrecedent).dispose();
            if(modification){
                controleur.LancerViewTache(page);
            }else{
                if(page.equals("Liste")){
                    controleur.getTachesListe().clear(); 
                }else{
                    controleur.getTachesProjet().clear();
                }
                controleur.LancerViewTaches(page);
            }
        });
        return panel;
    }

    private void actions() {
        Ajouter.addActionListener(e -> {
            if (areaDescription.getText().isEmpty() || titreTache.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir toutes les informations du tâche", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (!modification) {
                    Tache tache = new Tache();
                    tache.setTitreTache(titreTache.getText());
                    tache.setCategorieTache((Tache.categorie) choixCategorie.getSelectedItem());
                    tache.setDescriptionTache(areaDescription.getText());
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
                    tache.setDatedebutTache(dateDebut);
                    tache.setDelaiTache(dateFin);
                    if(page.equals("Liste")){
                        controleur.AjoutTacheliste(tache);
                    }else{
                        controleur.ajouterTache(tache);
                    }
                    this.dispose();
                    controleur.LancerViewTaches(page);
                } else {
                    String titre = controleur.getTacheSelectionne().getTitreTache();
                    controleur.updateTache("TitreTache", titre, "TitreTache", titreTache.getText(),page);
                    controleur.updateTache("TitreTache", titre, "DescriptionTache", areaDescription.getText(),page);
                    controleur.getTacheDetailModel().clear();
                   String newTitre= titreTache.getText();
                    Tache tache = controleur.getTacheSelectionne();
                    tache.setTitreTache(newTitre);
                    tache.setDescriptionTache(areaDescription.getText());
                    controleur.setTacheSelectionne(tache);
                    this.dispose();
                    controleur.LancerViewTache(page);
                }
            }

        });
        Annuler.addActionListener(e -> {
            if (modification) {
                dispose();
                controleur.LancerViewTache(page);
            } else {
                dispose();
                controleur.getTachesProjet().clear();
                controleur.LancerViewTaches(page);
            }
        });

    }
}