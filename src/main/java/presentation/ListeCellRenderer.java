package presentation;
import javax.swing.*;
import java.awt.*;
public class ListeCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof metier.Pojo.Liste) {
            metier.Pojo.Liste Liste = (metier.Pojo.Liste) value;
            String texteAffichage = "<html><b>"+Liste.getNomListe()+"</b><br/>"+"<p> Description:"+Liste.getDescriptionListe()+"</p></html>";
            label.setText(texteAffichage);
        }
        return label;
    }
}