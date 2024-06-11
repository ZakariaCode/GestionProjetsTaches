package presentation;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import presentation.Controlleurs.ControleurAccueil;
import presentation.Controlleurs.ControleurProjets;
import presentation.Controlleurs.ControleurStatistique;

public class PourcentagesStatistiques extends JFrame implements Tools{
    private JPanel head;
    private JPanel footer;
    private JPanel contenaire;
    private JPanel CategoriePanel;
    private JPanel TypePanel;
    private JButton ButtonAcceuil,ButtonPrecedent;
    private ChartPanel camembertCategorieParSemaine,camembertCategorieParMois,camembertCategorieParAnnee,camembertTypeParSemaine,camembertTypeParMois,camembertTypeParAnnee;
    private ControleurProjets c;
    private ControleurAccueil cAccueil;
    private ControleurStatistique cs;
    public PourcentagesStatistiques() {
        super("Pourcentages");
        this.c=new ControleurProjets();
        this.initialiser();
        this.dessiner();
    }
    public void initialiser() {
        cAccueil = new ControleurAccueil();
        cs = new ControleurStatistique();
        this.head = new JPanel(new GridLayout(2,1));
        this.footer = footerPanel();
        this.CategoriePanel = new JPanel(new GridLayout(1,3));
        this.TypePanel = new JPanel(new GridLayout(1,3));
        this.contenaire = new JPanel(new GridLayout(2, 1));
        this.camembertCategorieParSemaine = new ChartPanel(PourcentageCategorieParSemaine());
        this.camembertCategorieParMois = new ChartPanel(PourcentageCategorieParMois());
        this.camembertCategorieParAnnee = new ChartPanel(PourcentageCategorieParAnnee());
        this.camembertTypeParSemaine = new ChartPanel(PourcentageTypeParSemaine());
        this.camembertTypeParMois = new ChartPanel(PourcentageTypeParMois());
        this.camembertTypeParAnnee = new ChartPanel(PourcentageTypeParAnnee());
    }
    public void dessiner() {
        head.add(headerPanel());
        head.setBorder(new EmptyBorder(0, 0, 5, 0));
        head.add(navigation());
        this.add(head,BorderLayout.NORTH);
        this.add(footer,BorderLayout.SOUTH);
        this.CategoriePanel.add(camembertCategorieParSemaine);
        this.CategoriePanel.add(camembertCategorieParMois);
        this.CategoriePanel.add(camembertCategorieParAnnee);
        this.TypePanel.add(camembertTypeParSemaine);
        this.TypePanel.add(camembertTypeParMois);
        this.TypePanel.add(camembertTypeParAnnee);
        this.contenaire.add(CategoriePanel);
        this.contenaire.add(TypePanel);
        this.add(contenaire);
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
              SwingUtilities.getWindowAncestor(ButtonPrecedent).dispose();
              cs.LancerStatistiquesView();
      });
      return panel;
    }
    @SuppressWarnings("unchecked")
    private JFreeChart PourcentageTypeParSemaine() {
        @SuppressWarnings("rawtypes")
        DefaultPieDataset donnees = new DefaultPieDataset();
         for (Map.Entry<String, Double> entry : c.getDonnesStatistiquesSemaineParType().entrySet()) {
            donnees.setValue(entry.getKey(), entry.getValue());
        }
      return ChartFactory.createPieChart("Répartition des projets par type - dernière semaine",donnees,true,true,false);
    }

    @SuppressWarnings("unchecked")
    private JFreeChart PourcentageTypeParMois() {
        @SuppressWarnings("rawtypes")
        DefaultPieDataset donnees = new DefaultPieDataset();
         for (Map.Entry<String, Double> entry : c.getDonnesStatistiquesMoisParType().entrySet()) {
            donnees.setValue(entry.getKey(), entry.getValue());
        }
      return ChartFactory.createPieChart("Répartition des projets par type - dernier mois",donnees,true,true,false);
    }
    @SuppressWarnings("unchecked")
    private JFreeChart PourcentageTypeParAnnee() {
        @SuppressWarnings("rawtypes")
        DefaultPieDataset donnees = new DefaultPieDataset();
         for (Map.Entry<String, Double> entry : c.getDonnesStatistiquesAnneeParType().entrySet()) {
            donnees.setValue(entry.getKey(), entry.getValue());
        }
      return ChartFactory.createPieChart("Répartition des projets par type - dernière année",donnees,true,true,false);
    }

    @SuppressWarnings("unchecked")
    private JFreeChart PourcentageCategorieParSemaine() {
        @SuppressWarnings("rawtypes")
        DefaultPieDataset donnees = new DefaultPieDataset();
         for (Map.Entry<String, Double> entry : c.getDonnesStatistiquesSemaineParCategorie().entrySet()) {
            donnees.setValue(entry.getKey(), entry.getValue());
        }
      return ChartFactory.createPieChart("Répartition des projets par categorie - dernière semaine",donnees,true,true,false);
    }
    @SuppressWarnings("unchecked")
    private JFreeChart PourcentageCategorieParMois() {
        @SuppressWarnings("rawtypes")
        DefaultPieDataset donnees = new DefaultPieDataset();
         for (Map.Entry<String, Double> entry : c.getDonnesStatistiquesMoisParCategorie().entrySet()) {
            donnees.setValue(entry.getKey(), entry.getValue());
        }
      return ChartFactory.createPieChart("Répartition des projets par categorie - dernier mois",donnees,true,true,false);
    }
    @SuppressWarnings("unchecked")
    private JFreeChart PourcentageCategorieParAnnee() {
        @SuppressWarnings("rawtypes")
        DefaultPieDataset donnees = new DefaultPieDataset();
         for (Map.Entry<String, Double> entry : c.getDonnesStatistiquesAnneeParCategorie().entrySet()) {
            donnees.setValue(entry.getKey(), entry.getValue());
        }
      return ChartFactory.createPieChart("Répartition des projets par categorie - dernière année",donnees,true,true,false);
    }
}