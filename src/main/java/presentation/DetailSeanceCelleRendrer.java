package presentation;
import javax.swing.*;
import java.awt.*;
public class DetailSeanceCelleRendrer extends DefaultListCellRenderer{
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (value instanceof metier.Pojo.Seance) {
            metier.Pojo.Seance Seance = (metier.Pojo.Seance) value;
            JLabel TitreLabel = new JLabel();
            TitreLabel.setText("<html><b><i>Titre: "+Seance.getTitreSeance()+"</i></b> </html>");
            JLabel dateDebutLabel = new JLabel("Date de début: " + Seance.getDateDebutSeance());
            JLabel delaiLabel = new JLabel("Delai: " + Seance.getDateFinSeance());
            JTextArea descriptionArea = new JTextArea("Description: "+Seance.getDescriptionSeance());
            descriptionArea.setLineWrap(true);
            descriptionArea.setWrapStyleWord(true);
            descriptionArea.setEditable(false);
            descriptionArea.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
            descriptionArea.setBackground(new Color(237,236,236));
            JTextArea noteArea=new JTextArea("Note: "+Seance.getNoteSeance());
            noteArea.setLineWrap(true);
            noteArea.setWrapStyleWord(true);
            noteArea.setEditable(false);
            noteArea.setMaximumSize(new Dimension(400, Integer.MAX_VALUE));
            noteArea.setBackground(new Color(237,236,236));
            panel.add(TitreLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10)));
            panel.add(dateDebutLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(delaiLabel);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(noteArea);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(descriptionArea);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); 
            panel.add(new JScrollPane(descriptionArea));
        }
        return panel;
    }
}
