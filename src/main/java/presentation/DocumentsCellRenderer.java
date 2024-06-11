package presentation;
import javax.swing.*;
import java.awt.*;
public class DocumentsCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof metier.Pojo.Documents) {
            metier.Pojo.Documents document = (metier.Pojo.Documents) value;
            String texteAffichage = "<html><b>" + document.getLibelleDoc() + "</b><br/>" +
                    "Date d'ajout: " + document.getDateAjout() + "</html>";
            label.setText(texteAffichage);
        }
        return label;
    }
}
