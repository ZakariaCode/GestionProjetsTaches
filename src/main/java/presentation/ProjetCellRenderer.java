package presentation;

import javax.swing.*;
import java.awt.*;

public class ProjetCellRenderer extends DefaultListCellRenderer {

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof metier.Pojo.Projet) {
            metier.Pojo.Projet projet = (metier.Pojo.Projet) value;
            boolean status = projet.getCloture();
            String thisStatus="";
            if (status) {
                 thisStatus = "Cloturé";
            }
            String texteAffichage = "<html><b>" + projet.getTitreProjet() + "</b><br/>" +
                    "Catégorie: " + projet.getCategorieProjet() + "&emsp;&emsp;&emsp;Type: " + projet.getTypeProjet()+ "<br/>" +
                    "Date de fin: " + projet.getDelaiProjet() + "&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<b>"+thisStatus+"</b></html>";
            label.setText(texteAffichage);
        }
        return label;
    }
}