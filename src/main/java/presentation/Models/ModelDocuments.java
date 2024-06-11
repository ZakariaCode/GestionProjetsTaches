package presentation.Models;
import javax.swing.DefaultListModel;
import metier.Pojo.Documents;
import java.util.ArrayList;


public class ModelDocuments {
    private DefaultListModel<Documents> DocumentsTache;
    private DefaultListModel<Documents> DocumentsProjet;
    private DefaultListModel<Documents> DocumentsSeance;
    public ModelDocuments(){
        DocumentsTache= new DefaultListModel<Documents>();
        DocumentsProjet= new DefaultListModel<Documents>();
        DocumentsSeance= new DefaultListModel<Documents>();

    }
    public DefaultListModel<Documents> getDocumentsTache() {
        return DocumentsTache;
    }
    public void setDocumentsTache(ArrayList<Documents> documentsTache) {
        for(Documents d : documentsTache){
            DocumentsTache.addElement(d);
        }
    }
    public DefaultListModel<Documents> getDocumentsProjet() {
        return DocumentsProjet;
    }
    public void setDocumentsProjet(ArrayList<Documents> documentsProjet) {
        for(Documents d : documentsProjet){
            DocumentsProjet.addElement(d);
        }
    }

    public DefaultListModel<Documents> getDocumentsSeance() {
        return DocumentsSeance;
    }
    public void setDocumentsSeance(ArrayList<Documents> documentSeance) {
        for(Documents d : documentSeance){
            DocumentsSeance.addElement(d);
        }
    }
}
