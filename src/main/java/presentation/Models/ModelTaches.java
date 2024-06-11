package presentation.Models;
import metier.Pojo.Tache;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
public class ModelTaches {
    private DefaultListModel<Tache> TachesProjet;
    private DefaultListModel<Tache> TachesListe;
    public ModelTaches(){
        TachesProjet= new DefaultListModel<Tache>();
        TachesListe= new DefaultListModel<Tache>();
    }
    public DefaultListModel<Tache> getTachesProjet() {
        return TachesProjet;
    }
    public void setTachesProjet(ArrayList<Tache> tachesProjet) {
        for(Tache t : tachesProjet){
            TachesProjet.addElement(t);
        }
        
    }
    public DefaultListModel<Tache> getTachesListe() {
        return TachesListe;
    }
    public void setTachesListe(ArrayList<Tache> tachesListe) {
        for(Tache t : tachesListe){
            TachesListe.addElement(t);
        }
    }
}
