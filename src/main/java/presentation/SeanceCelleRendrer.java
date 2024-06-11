package presentation;
import javax.swing.*;
import java.awt.*;
public class SeanceCelleRendrer  extends DefaultListCellRenderer {
    
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (value instanceof metier.Pojo.Seance) {
            metier.Pojo.Seance seance = (metier.Pojo.Seance) value;
            String texteAffichage = "<html><b>" + seance.getTitreSeance() + "</b><br/>" +
                    "Date debut: " + seance.getDateDebutSeance()  +"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+ "Date fin: " +seance.getDateFinSeance()+"</html>";
            label.setText(texteAffichage);
        }
        return label;
    }
}
