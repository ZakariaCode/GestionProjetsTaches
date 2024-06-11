package presentation;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import metier.Pojo.Projet;
import java.util.ArrayList;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.plot.PlotOrientation;
import presentation.Controlleurs.*;

import org.jfree.data.category.DefaultCategoryDataset;

public class Statistiques extends JFrame implements Tools {
    private JPanel Head,panelPrincipal,panelBoutons;
    private JButton voirPlusButton,ButtonAcceuil,ButtonPrecedent;
    private ChartPanel DiagrammeBattons;
    private ControleurProjets c;
    private ControleurStatistique cs;
    private ControleurAccueil cAccueil;

    public Statistiques() {
        super("Statistiques");
        this.c=new ControleurProjets();
        this.setLocationRelativeTo(null);
        this.initialiser();
        this.dessiner();
        this.actions();
    }

    public void initialiser() {
        cs = new ControleurStatistique();
        cAccueil = new ControleurAccueil();
        Head= new JPanel(new GridLayout(2,1));
        panelBoutons = new JPanel();
        panelPrincipal = new JPanel();
        this.DiagrammeBattons = new ChartPanel(DiagrammeHeures_NombreDocuments());
        this.voirPlusButton = new JButton("Autre Statistiques");
        
    }

    public void dessiner() {
        this.setLayout(new BorderLayout()); 
        Head.add(headerPanel());
        Head.setBorder(new EmptyBorder(0, 0, 5, 0));
        Head.add(navigation());
        this.add(Head,BorderLayout.NORTH);
        this.add(footerPanel(),BorderLayout.SOUTH);
        panelPrincipal.setLayout(new BorderLayout());
        panelPrincipal.add(DiagrammeBattons, BorderLayout.CENTER);
        panelBoutons.add(voirPlusButton);
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
            SwingUtilities.getWindowAncestor(ButtonPrecedent).dispose();
            cAccueil.LanceAccueil();
		});
		return panel;
	}
    private JFreeChart DiagrammeHeures_NombreDocuments() {
        DefaultCategoryDataset donnees = new DefaultCategoryDataset();
        ArrayList<Projet> liste = new ArrayList<Projet>(c.getAllProjets());
        for(Projet p : liste){
            donnees.addValue(c.getDureeprojet(p.getTitreProjet()), "Heures", p.getTitreProjet());
            donnees.addValue(c.NombreDocument(p.getTitreProjet()), "Documents", p.getTitreProjet());}
        JFreeChart chart = ChartFactory.createBarChart("Nombre heures de travail et documents sur un projet ","","",donnees,
        PlotOrientation.VERTICAL,true,false,false);
      return chart;
    }
    private void actions(){
        voirPlusButton.addActionListener(e -> {
            SwingUtilities.getWindowAncestor(voirPlusButton).dispose();
            cs.LancerpourcentageView();
        });
    }

}