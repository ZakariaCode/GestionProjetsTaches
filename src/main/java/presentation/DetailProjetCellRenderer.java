package presentation;
import javax.swing.*;
import java.awt.*;
public class DetailProjetCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (value instanceof metier.Pojo.Projet) {
            metier.Pojo.Projet projet = (metier.Pojo.Projet) value;
            JLabel typeLabel = new JLabel("Type: " + projet.getTypeProjet().toString());
            JLabel categorieLabel = new JLabel("Categorie: " + projet.getCategorieProjet().toString());
            JLabel dateDebutLabel = new JLabel("Date de début: " + projet.getDatedebutProjet());
            JLabel delaiLabel = new JLabel("Delai: " + projet.getDelaiProjet());
            JLabel descriptionLabel = new JLabel("Description: ");
            boolean status = projet.getCloture();
            JLabel statusLabel=new JLabel();
            if (status) {
                 statusLabel = new JLabel("Status: " + "Cloturé");
            }
            statusLabel.setText("<html><b><u>Status: Cloturé</u></b> </html>");
            JTextArea descriptionArea = new JTextArea(projet.getDescriptionProjet());
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setEditable(false);
            descriptionArea.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
            panel.add(statusLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(typeLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(categorieLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(dateDebutLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(delaiLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(descriptionLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(new JScrollPane(descriptionArea));
        }

        return panel;
    }
}
