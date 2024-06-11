package presentation.Models;
import metier.Pojo.Liste;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
public class ModelListes {
    
    private DefaultListModel<Liste> modelListe;
    
    public ModelListes(){
        modelListe = new DefaultListModel<Liste>();
    }
    public ModelListes(ArrayList<Liste> p){
        this();
        for(Liste pr : p){
            modelListe.addElement(pr);
        }
    }
    public DefaultListModel<Liste> getModelListe() {
        return modelListe;
    }
    public void setModelListe(DefaultListModel<Liste> newModel){
        modelListe=newModel;
    }
}