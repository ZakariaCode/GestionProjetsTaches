package presentation;
import javax.swing.*;
import java.awt.*;
public class TacheCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof metier.Pojo.Tache) {
            metier.Pojo.Tache Tache = (metier.Pojo.Tache) value;
            boolean status=Tache.getStatus();
            String statusLabel;
            if (status) {
                 statusLabel = "Cloturée";
                }else{
                    statusLabel = ""; 
                }
            String texteAffichage = "<html><b>" + Tache.getTitreTache() + "</b><br/>" +
                    "Catégorie: " + Tache.getCategorieTache()  +"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<b>"+statusLabel+"</b><br/>" +
                    "Date de début: " + Tache.getDatedebutTache() + "</html>";
            label.setText(texteAffichage);
        }
        return label;
    }
}