package presentation.Controlleurs;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import java.awt.*;
import presentation.*;
import presentation.Models.*;
import metier.Pojo.*;
import persistance.DAOProjet;

public class ControleurProjets {
    static  ArrayList<Projet> ListeProjets;
    private Projets viewProjets;
    private DAOProjet d;
    private FormulaireProjet formProjet;
    private UnProjet viewProjet;
    private modelProjets LesProjet;
    public Projet projetSelectionnee;
    private HashMap<String,Double> DonnesStatistiquesSemaineParType;
    private HashMap<String,Double> DonnesStatistiquesSemaineParCategorie;
    private HashMap<String,Double> DonnesStatistiquesMoisParType;
    private HashMap<String,Double> DonnesStatistiquesMoisParCategorie;
    private HashMap<String,Double> DonnesStatistiquesAnneeParType;
    private HashMap<String,Double> DonnesStatistiquesAnneeParCategorie;
    public ControleurProjets() {
        LesProjet= new modelProjets();
        d = new DAOProjet();
        DonnesStatistiquesSemaineParType = new HashMap<>(d.getStatistiquesHebdoParType());
        DonnesStatistiquesSemaineParCategorie = new HashMap<>(d.getStatistiquesHebdoParCategorie());
        DonnesStatistiquesMoisParType = new HashMap<>(d.getStatistiquesMensuellesParType());
        DonnesStatistiquesMoisParCategorie = new HashMap<>(d.getStatistiquesMensuellesParCategorie());
        DonnesStatistiquesAnneeParType = new HashMap<>(d.getStatistiquesAnuellesParType());
        DonnesStatistiquesAnneeParCategorie = new HashMap<>(d.getStatistiquesAnnuellesParCategorie());
    }
    
    public void LanceFormulaireProjet(){
        formProjet = new FormulaireProjet(this,false);
        formProjet.setSize(1100, 510);
        formProjet.setLocationRelativeTo(null);
        formProjet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formProjet.setVisible(true);
    }
    public void LanceFormulaireModificationProjet(){
        formProjet = new FormulaireProjet(this,true);
        formProjet.setSize(920, 510);
        formProjet.setLocationRelativeTo(null);
        formProjet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        formProjet.setVisible(true);
    }
    public void LanceViewProjets(){
        viewProjets = new Projets(this);
        viewProjets.setSize(new Dimension(700, 400));
        viewProjets.setLocationRelativeTo(null);
        viewProjets.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewProjets.setVisible(true);
    }   
    public DefaultListModel<Projet> getProjetModel(){
        return LesProjet.getModel();
    }
    public void LancerUnProjet(){
        viewProjet = new UnProjet(this);
        viewProjet.setSize(new Dimension(800, 650));
        viewProjet.setLocationRelativeTo(null);
        viewProjet.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewProjet.setVisible(true);
    }
    public void setListeProjets(ArrayList<Projet> listeProjets) {
        modelProjets newModel = new modelProjets(listeProjets);
        LesProjet.setModel(newModel.getModel());
    }
    public void setProjetSelectionne(Projet p){
        this.projetSelectionnee=p;
    }
    public DefaultListModel<Projet> getProjetDetailModel(){
        DefaultListModel<Projet> viewProjet = new DefaultListModel<>();
        
        viewProjet.addElement(projetSelectionnee);
        return viewProjet;
    }
    public Projet getProjetSelectionne(){
        return projetSelectionnee;
    }
    //added recently
    public ArrayList<Projet> getAllProjets(){
        return d.getAllProjets();
    }
    public Double getDureeprojet(String titreProjet){
        return d.DureeProjet(titreProjet);
    }
    public int NombreDocument(String titreProjet){
        return d.NombreDocument(titreProjet);
    }
    public HashMap<String, Double> getDonnesStatistiquesSemaineParCategorie() {
        return DonnesStatistiquesSemaineParCategorie;
    }
    public HashMap<String, Double> getDonnesStatistiquesSemaineParType() {
        return DonnesStatistiquesSemaineParType;
    }

    public HashMap<String, Double> getDonnesStatistiquesMoisParType() {
        return DonnesStatistiquesMoisParType;
    }

    public HashMap<String, Double> getDonnesStatistiquesMoisParCategorie() {
        return DonnesStatistiquesMoisParCategorie;
    }

    public HashMap<String, Double> getDonnesStatistiquesAnneeParType() {
        return DonnesStatistiquesAnneeParType;
    }

    public HashMap<String, Double> getDonnesStatistiquesAnneeParCategorie() {
        return DonnesStatistiquesAnneeParCategorie;
    }
}