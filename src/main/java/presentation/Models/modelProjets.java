package presentation.Models;

import java.util.ArrayList;

import javax.swing.DefaultListModel;

import metier.Pojo.Projet;

public class modelProjets {

    private DefaultListModel<Projet> model;
    
    public modelProjets(){
        model = new DefaultListModel<Projet>();
    }
    public modelProjets(ArrayList<Projet> p){
        this();
        for(Projet pr : p){
            model.addElement(pr);
        }
    }
    public DefaultListModel<Projet> getModel() {
        return model;
    }
    public void setModel(DefaultListModel<Projet> newModel){
        model=newModel;
    } 
}