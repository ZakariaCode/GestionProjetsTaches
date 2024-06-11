package presentation.Models;
import metier.Pojo.Seance;
import java.util.ArrayList;
import javax.swing.DefaultListModel;

public class ModelSeances {
    private DefaultListModel<Seance> model;
    
    public ModelSeances(){
        model = new DefaultListModel<Seance>();
    }
    public ModelSeances(ArrayList<Seance> seances){
        for(Seance seance : seances){
            model.addElement(seance);
        }
    }
    public DefaultListModel<Seance> getModel() {
        return model;
    }
    public void setModel(ArrayList<Seance> newModel){
        for(Seance seance : newModel){
            model.addElement(seance);
        } 
    }
}