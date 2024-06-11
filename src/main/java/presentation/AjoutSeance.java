package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.Seance;
import presentation.Controlleurs.*;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.awt.*;
public class AjoutSeance extends JFrame implements Tools{
    private JPanel headerPanel,panel,panelBoutton,footerPanel,dateDepart,dateFin;
    private JLabel labeltitre,labelDescription,labelNote,labelDateDepart, labelDateFin;
    private JTextField titreSeance;
    private JTextArea areaDescription,areaNote;
    private JComboBox<String> choixJoursdepart, choixMoisDepart, choixAnneeDepart, choixHeureDepart, choixMinuteDepart;
    private JComboBox<String> choixJoursfin, choixMoisFin, choixAnneeFin, choixHeureFin, choixMinuteFin;
    private String[] mois = { "Janvier", "Février", "Mars", "Avril", "Mai", "Juin", "Juillet", "Août", "Septembre",
            "Octobre", "Novembre", "Décembre" };
    String[] annees = { "2022", "2023", "2024", "2025", "2026", "2027", "2028", "2029", "2030" };
    private String[] heures = new String[24];
    private String[] minutes = new String[60];
    private String[] jours = new String[31];
    private JButton Ajouter, Annuler,ButtonAcceuil, ButtonPrecedent;
    private ControleurSeances controleur;
    private ControleurAccueil cAccueil;
    private boolean modification;

    public AjoutSeance(ControleurSeances c,boolean modifier) {
        super("Ajouter Séance");
        this.controleur = c;
        this.modification=modifier;
        this.initialiser();
        this.dessiner();
        this.actions();
    }
    private void initialiser(){
        cAccueil = new ControleurAccueil();
        headerPanel = new JPanel(new GridLayout(2, 1));
        labeltitre = new JLabel("Titre du Séance : ");
        titreSeance = new JTextField(20);
        labelDescription = new JLabel("Description du Séance : ");
        areaDescription = new JTextArea(5, 20);
        labelNote = new JLabel("Note : ");
        areaNote = new JTextArea(5, 20);
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
        panel = new JPanel(new GridLayout(6,2));
        dateDepart = createDateTimePanel(choixJoursdepart, choixMoisDepart, choixAnneeDepart, choixHeureDepart,
                choixMinuteDepart);
        dateFin = createDateTimePanel(choixJoursfin, choixMoisFin, choixAnneeFin, choixHeureFin, choixMinuteFin);
        panel = new JPanel();
        panelBoutton = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
        footerPanel = new JPanel(new BorderLayout());
    }
    private void dessiner(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        headerPanel.add(headerPanel());
        headerPanel.setBorder(new EmptyBorder(0, 0, 5, 0));
        headerPanel.add(navigation());
        if (modification) {
            titreSeance.setText(controleur.getSeanceSelectionne().getTitreSeance());
            areaDescription.setText(controleur.getSeanceSelectionne().getDescriptionSeance());
            areaNote.setText(controleur.getSeanceSelectionne().getNoteSeance());
        }
        this.add(headerPanel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(6, 2));
        panel.add(labeltitre);
        panel.add(titreSeance);
        panel.add(labelDescription);
        panel.add(new JScrollPane(areaDescription));
        panel.add(labelNote);
        panel.add(new JScrollPane(areaNote)); 
        panel.add(labelDateDepart); 
        panel.add(dateDepart);
        panel.add(labelDateFin);
        panel.add(dateFin);
        panel.setBorder(new EmptyBorder(25, 70, 0, 70));
        getContentPane().add(panel, BorderLayout.CENTER);
        Ajouter = new JButton("Ajouter");
        panelBoutton.add(Ajouter);
        Annuler = new JButton("Annuler");
        panelBoutton.add(Annuler); 
        panelBoutton.setBorder(new EmptyBorder(0, 0, 35, 50));  
        footerPanel.add(panelBoutton, BorderLayout.NORTH);
        footerPanel.add(footerPanel(), BorderLayout.SOUTH);
        this.add(footerPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
   
    private JPanel navigation() {
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
            controleur.getseances().clear();
            controleur.LanceViewSeances();
        });
        return panel;
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
    private void actions(){
        Ajouter.addActionListener(e -> {
            if (areaDescription.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir la description du seance", "Erreur",
                        JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (!modification) {
                    Seance seance = new Seance();
                    seance.setTitreSeance(titreSeance.getText());
                    seance.setDescriptionSeance(areaDescription.getText());
                    seance.setNoteSeance(areaNote.getText());
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
                            JOptionPane.showMessageDialog(this,
                                    "Heure invalide, veuillez sélectionner une heure entre 0 et 23", "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        LocalDateTime dateDebut = LocalDateTime.of(anneeDepart, moisDepart, jourDepart, heureDepart,
                                minuteDepart);
                        LocalDateTime dateFin = LocalDateTime.of(anneeFin, moisFin, jourFin, heureFin, minuteFin);
                        seance.setDateDebutSeance(dateDebut);
                        seance.setDateFinSeance(dateFin);
                    } catch (NumberFormatException | IndexOutOfBoundsException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(this,
                                "Une erreur s'est produite lors de la conversion des valeurs", "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    if(controleur.AjouterSeance(seance)){
                        JOptionPane.showMessageDialog(this, "Séance ajouté avec succès", "Succès",
                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(this, "Séance déjà exist", "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    }
                    this.dispose();
                    controleur.LanceViewSeances();
                } else {
                    String titre = titreSeance.getText();
                    controleur.modifierSeance("TitreSeance",titre);
                    controleur.getSeanceSelectionne().setTitreSeance(titre);
                    controleur.modifierSeance("DescriptionSeance",areaDescription.getText());
                    controleur.getSeanceSelectionne().setDescriptionSeance(areaDescription.getText());
                    controleur.modifierSeance("Note",areaNote.getText());
                    controleur.getSeanceSelectionne().setNoteSeance(areaNote.getText());
                    this.dispose();
                    controleur.LanceViewSeance();
                }
                

            }

        });

    }
}