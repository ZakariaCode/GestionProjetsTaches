package presentation;
import javax.swing.*;
import java.awt.*;
public class DetailTacheCelleRendrer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (value instanceof metier.Pojo.Tache) {
            metier.Pojo.Tache tache = (metier.Pojo.Tache) value;
            JLabel titreLabel = new JLabel("<html><b><i>Titre: " + tache.getTitreTache() + "</i></b></html>");
            JLabel categorieLabel = new JLabel("Categorie: " + tache.getCategorieTache().toString());
            JLabel dateDebutLabel = new JLabel("Date de début: " + tache.getDatedebutTache());
            JLabel delaiLabel = new JLabel("Delai: " + tache.getDelaiTache());
            JLabel descriptionLabel = new JLabel("Description:");
            JLabel statusLabel = new JLabel("Status: " + (tache.getStatus() ? "Cloturé" : "Non Cloturé"));

            JTextArea descriptionArea = new JTextArea(tache.getDescriptionTache());
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setBackground(new Color(237, 236, 236));
            descriptionArea.setEditable(false);
            descriptionArea.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
            JScrollPane scrollPane = new JScrollPane(descriptionArea);
            panel.add(titreLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(statusLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(categorieLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(dateDebutLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(delaiLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(descriptionLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(scrollPane);
        }

        return panel;
    }
}
