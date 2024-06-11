package presentation;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import metier.Pojo.Tache;
import presentation.Controlleurs.ControleurTaches;

public class AllTaches extends JFrame{
    private ControleurTaches controleurTaches;
    private JList<Tache> listeTaches;
    private JButton boutonAjouterTache,btnAnnuler;
    private JPanel panelPrincipal,panelBoutons;
    private JScrollPane scrollPane;
    public AllTaches(ControleurTaches controleurTaches){
        this.controleurTaches = controleurTaches;
        this.initialiser();
        this.dessiner();
        this.actions();
    }
    public void initialiser(){
        panelPrincipal = new JPanel(new BorderLayout());
        panelBoutons = new JPanel();
        boutonAjouterTache = new JButton("Ajouter");
        btnAnnuler = new JButton("Annuler");
        listeTaches = new JList<>();
        scrollPane = new JScrollPane(listeTaches);

    }
    public void dessiner(){
        listeTaches.setCellRenderer(new TacheCellRenderer());
        listeTaches.setModel(controleurTaches.getAllTaches());
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelBoutons.add(boutonAjouterTache);
        panelBoutons.add(btnAnnuler);
        panelPrincipal.add(panelBoutons, BorderLayout.SOUTH);
        this.add(panelPrincipal);
    }
    public void actions(){
        boutonAjouterTache.addActionListener(e -> {
            controleurTaches.ajouterTacheToListe(listeTaches.getSelectedValue());
            this.dispose();
        });
        btnAnnuler.addActionListener(e -> {
            this.dispose();
        });
    }   
}
