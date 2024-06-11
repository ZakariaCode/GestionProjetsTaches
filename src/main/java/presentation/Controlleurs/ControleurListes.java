package presentation.Controlleurs;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import metier.Pojo.Liste;
import persistance.DAOListe;
import presentation.*;
import presentation.Models.*;

public class ControleurListes {
    private Listes listesPage;
    private FormulaireListe Form;
    private ModelListes model;
    private Liste listeSelectionne;
    private DAOListe dao;
    public Liste getListeSelectionne() {
        return listeSelectionne;
    }
    public void setListeSelectionne(Liste listeSelectionne) {
        this.listeSelectionne = listeSelectionne;
    }
    public ControleurListes(){
        model= new ModelListes();
        dao = new DAOListe();
    }
    public void LancerListes(){
        this.listesPage = new Listes(this);
        this.listesPage.setVisible(true);
        this.listesPage.setSize(new Dimension(950, 980));
        this.listesPage.setLocationRelativeTo(null);
        this.listesPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.listesPage.setVisible(true);
    }
    public void LancerFormulaireListe(){
        this.Form =new FormulaireListe(this);
        this.Form.setSize(new Dimension(700, 400));
        this.Form.setLocationRelativeTo(null);
        this.Form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.Form.setVisible(true);
    }

    public DefaultListModel<Liste> getListeDetailModel(){
        return model.getModelListe();
    }
   
    public DefaultListModel<Liste> getListesModel(){
        return model.getModelListe();
    }
    public void setModel(ModelListes model) {
        this.model = model;
    }
    public ArrayList<Liste> getAllListes(){
        return dao.getAllListes();
    }
    public void ajouterListe(Liste newListe){
            dao.CreateList(newListe);
    }
    public boolean supprimerListe(String NomListe){
        return dao.DeleteList(NomListe);
    }
    public ArrayList<Liste> rechercheListe(String nom){
        return dao.RechercheParNom(nom);
    } 
}