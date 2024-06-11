package presentation.Controlleurs;
import presentation.PourcentagesStatistiques;
import presentation.Statistiques;
public class ControleurStatistique {
    private Statistiques DiagrammeView;
    private PourcentagesStatistiques CammembertView;

    public void LancerStatistiquesView(){
        DiagrammeView = new Statistiques();
        DiagrammeView.setSize(680, 550);
        DiagrammeView.setLocationRelativeTo(null);
        DiagrammeView.setVisible(true);
    }
    public void LancerpourcentageView(){
        CammembertView = new PourcentagesStatistiques();
        CammembertView.setSize(900, 600);
        CammembertView.setLocationRelativeTo(null);
        CammembertView.setVisible(true);   
    }

}