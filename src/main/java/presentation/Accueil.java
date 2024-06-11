package presentation;

import java.awt.*;
import javax.swing.*;

import presentation.Controlleurs.*;

public class Accueil extends JFrame implements Tools{

	private JPanel CentralContainer,MainBox,box1,box2,box3;
	private JLabel label1,label2,label3;
	private JButton buttonListe,buttonProjets,buttonStatistique;
	private ControleurListes cListes;
	private ControleurProjets cProjet;	
	private ControleurStatistique cStatistique;
		public Accueil(ControleurAccueil c){

			super("Accueil");
			this.initialiser();
			this.dessiner();
			this.actions();
			this.cListes = new ControleurListes();
			this.cProjet = new ControleurProjets();
		}
		private void initialiser(){
			cStatistique = new ControleurStatistique();
			CentralContainer = new JPanel();
			MainBox = new JPanel();
			box1 = new JPanel();
			label1 = new JLabel("  Listes",rescaledIcon("src/main/java/presentation/images/tache.png",35,35),SwingConstants.LEFT);
			label2 =new JLabel("  PROJETS",rescaledIcon("src/main/java/presentation/images/projet.png",35,35),SwingConstants.LEFT);
			label3 = new JLabel("  STATISTIQUES",rescaledIcon("src/main/java/presentation/images/stats.png",35,35),SwingConstants.LEFT);
			buttonListe = new JButton("Consulter");
			buttonProjets = new JButton("Consulter" );
			buttonStatistique = new JButton("Consulter");
			box2 = new JPanel();
			box3 = new JPanel();

		}
		private void dessiner() {

			this.setMinimumSize(new Dimension(720,720));
			this.add(headerPanel(),BorderLayout.NORTH);
			this.add(footerPanel(),BorderLayout.SOUTH);
			this.setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			MainBox.setLayout(new BoxLayout(MainBox, BoxLayout.Y_AXIS));
			CentralContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 25, 20));
			box1.setLayout(new BoxLayout(box1, BoxLayout.Y_AXIS));
			box1.setPreferredSize(new Dimension(300, 160));
			box1.setBackground(Color.DARK_GRAY);
			label1.setForeground(Color.WHITE);
			label1.setFont(label1.getFont().deriveFont(24f));
			buttonListe.setFont(new Font("Arial", Font.ITALIC, 12)); 
			buttonListe.setFocusPainted(false);
			box1.add(Box.createRigidArea(new Dimension(30,60)));
			box1.add(label1);
			box1.add(Box.createVerticalStrut(20));
			box1.add(buttonListe);
			label2.setForeground(Color.WHITE);
			label2.setFont(label2.getFont().deriveFont(24f));
			buttonProjets.setFont(new Font("Arial", Font.ITALIC, 12)); 
			buttonProjets.setFocusPainted(false);
			box2.setPreferredSize(new Dimension(300, 160));
			box2.setBackground(new Color(15,140,70));
			box2.setLayout(new BoxLayout(box2, BoxLayout.Y_AXIS));
			box3.setPreferredSize(new Dimension(300, 160));
			box3.setBackground(Color.ORANGE);
			box3.setLayout(new BoxLayout(box3, BoxLayout.Y_AXIS));
			label3.setForeground(Color.WHITE);
			label3.setFont(label3.getFont().deriveFont(24f));
			buttonStatistique.setFont(new Font("Arial", Font.ITALIC, 12)); 
			buttonStatistique.setFocusPainted(false);
			box2.add(Box.createRigidArea(new Dimension(30,60)));
			box2.add(label2);
			box2.add(Box.createVerticalStrut(20));
			box2.add(buttonProjets);
			box3.add(Box.createRigidArea(new Dimension(30,60)));
			box3.add(label3);
			box3.add(Box.createVerticalStrut(20));
			box3.add(buttonStatistique);
			CentralContainer.add(box1);
			CentralContainer.add(box2);
			CentralContainer.add(box3);
			MainBox.add(Box.createVerticalStrut(160));
			MainBox.add(CentralContainer);
        	this.add(MainBox, BorderLayout.CENTER);
			setVisible(true);
		}
		private void actions(){
			buttonListe.addActionListener(e -> {
				SwingUtilities.getWindowAncestor(buttonListe).dispose();
				cListes.LancerListes();
			});
			buttonProjets.addActionListener(e -> {
				SwingUtilities.getWindowAncestor(buttonProjets).dispose();
				cProjet.LanceViewProjets();
			});
			buttonStatistique.addActionListener(e -> {
				SwingUtilities.getWindowAncestor(buttonStatistique).dispose();
				cStatistique.LancerStatistiquesView();
			});

		}
}