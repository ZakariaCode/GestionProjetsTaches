package presentation;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import metier.Pojo.Tache;
import presentation.Controlleurs.*;
import java.awt.*;
public class UneTache extends JFrame implements Tools {

    private JPanel panelPrincipal,Head,panelBoutons;
    private JList<Tache> infoTache;
    private JButton boutonCloturer;
    private  JScrollPane scrollPane;
    private JButton boutonCloner,ButtonModifier,ButtonAcceuil,ButtonPrecedent;
    public ControleurTaches controleur;
    private ControleurAccueil cAccueil;
    private String page;
    public UneTache(ControleurTaches c, String page) {
        super("Une tâche");
        this.controleur=c;
        this.page=page;
        this.initialiser();
        this.dessiner();
        this.actions();
    }
    public void initialiser() {
        cAccueil = new ControleurAccueil();
        Head= new JPanel(new GridLayout(2,1));
        panelPrincipal = new JPanel();
        infoTache = new JList<>();
        scrollPane = new JScrollPane(infoTache);
        boutonCloturer = new JButton("Clôturer");
        boutonCloner = new JButton("Clôner");
        ButtonModifier=new JButton("modifier");
        panelBoutons = new JPanel();

    }
    private void dessiner() {
        this.setLayout(new BorderLayout());
        Head.add(headerPanel());
        Head.setBorder(new EmptyBorder(0, 0, 5, 0));
        Head.add(navigation("UnProjet"));
        this.add(Head, BorderLayout.NORTH);
        this.add(footerPanel(), BorderLayout.SOUTH);
        panelPrincipal.setLayout(new BorderLayout());
        infoTache.setCellRenderer(new DetailTacheCelleRendrer());
        infoTache.setModel(controleur.getTacheDetailModel());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.setBorder(new EmptyBorder(0, 0, 5, 0));
        panelBoutons.add(boutonCloturer);
        panelBoutons.add(boutonCloner);
        panelBoutons.add(ButtonModifier);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        getContentPane().add(panelPrincipal);
        setSize(new Dimension(800, 650));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
            if(page.equals("Liste")){
                controleur.getTachesListe().clear();
                controleur.LancerViewTaches(page);
            }
            else{
                controleur.getTachesProjet().clear();
                controleur.LancerViewTaches(page);
            }
           
		});
		return panel;
	}
    public void actions() {
        boutonCloturer.addActionListener(e -> {
            controleur.CloturerTache();
            this.dispose();
            controleur.LancerViewTache(page);
        });
        boutonCloner.addActionListener(e -> {
            this.dispose();
            controleur.ClonerTache(controleur.getTacheSelectionne(),page);
        });
        ButtonModifier.addActionListener(e -> {
            this.dispose();
            controleur.LanceFormulaireModificationTache(page);
        }); 
    }
}