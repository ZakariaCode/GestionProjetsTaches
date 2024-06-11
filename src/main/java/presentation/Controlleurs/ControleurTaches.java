package presentation.Controlleurs;
import java.util.ArrayList;
import javax.swing.*;
import metier.Pojo.*;
import persistance.DAOTache;
import presentation.*;
import presentation.Models.ModelTaches;
public class ControleurTaches {
    private ModelTaches model;
    private DAOTache dao;
    private Taches viewTaches;
    private UneTache viewTache;
    private FormulaireTache viewAjoutTache,viewModifierTache;
    private  Projet projetSelectionne;
    private Tache tacheSelectionne;
    private Liste listeSelectionne;
    private AllTaches allTaches;
    public ControleurTaches(){
        dao=new DAOTache();
        model = new ModelTaches();
    }
    public Liste getListeSelectionne() {
        return listeSelectionne;
    }
    public void setListeSelectionne(Liste listeSelectionne) {
        this.listeSelectionne = listeSelectionne;
    }
    public ControleurTaches(Projet p) {
        this();
        this.projetSelectionne = p;
    }
    public ControleurTaches(Tache t){
        this();
        this.tacheSelectionne = t;
    }
    public ControleurTaches(Liste l){
        this();
        this.listeSelectionne = l;
    }
    public void LancerViewTaches(String page){
        this.viewTaches =new Taches(this,page);
        this.viewTaches.setVisible(true);
        this.viewTaches.setSize(700, 400);
        viewTaches.setLocationRelativeTo(null);
    }
    public void LancerViewTache(String page) {
        this.viewTache = new UneTache(this,page);
        this.viewTache.setVisible(true);
        this.viewTache.setSize(800, 650);
        viewTache.setLocationRelativeTo(null);   
    }
     public void LanceFormulaireModificationTache(String page){
        viewModifierTache = new FormulaireTache(this,true,page);
        viewModifierTache.setSize(1100, 510);
        viewModifierTache.setLocationRelativeTo(null);
        viewModifierTache.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewModifierTache.setVisible(true);
    }
    public void LancerViewAjoutTache(String page){
        viewAjoutTache = new FormulaireTache(this,false,page);
        viewAjoutTache.setVisible(true); 
        viewAjoutTache.setSize(1100, 510);
        viewAjoutTache.setLocationRelativeTo(null);
        viewAjoutTache.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
    }
    public void LanceViewChoisi(){
        allTaches = new AllTaches(this);
        allTaches.setVisible(true); 
        allTaches.setSize(500, 470);
        allTaches.setLocationRelativeTo(null);
        allTaches.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void updateTache(String filterField, Object filterValue,String updateField, Object updateValue,String page){ 
        if(page.equals("Liste")){
            dao.updateTacheListe(filterField, filterValue, updateField, updateValue,listeSelectionne.getNomListe());
            this.getTachesListe().clear();
        }
        else{
            dao.updateTacheProjet(filterField, filterValue, updateField, updateValue,projetSelectionne.getTitreProjet());
            this.getTachesProjet().clear();
        }
       
    }
    public void SetTacheListe(){
        model.getTachesListe().clear();
        model.setTachesListe(dao.TachesListe(listeSelectionne.getNomListe()));
    }
    public DefaultListModel<Tache> getTachesListe(){
        SetTacheListe();
        return model.getTachesListe();
    }
    public Projet getProjetSelectionne(){
        return projetSelectionne;
    }
    public void setProjetSelectionne(Projet p){
        projetSelectionne = p;
    }
    public void setTacheSelectionne(Tache t){
        this.tacheSelectionne = t;
    }
    public Tache getTacheSelectionne(){
        return this.tacheSelectionne;
        
    }
    private void SetTachesProjet(){
        model.setTachesProjet(dao.ReadTachesProjet(projetSelectionne.getTitreProjet()));
    }
    public void setTacheRecherche(ArrayList<Tache> taches){
        model.getTachesProjet().clear();
        model.setTachesProjet(taches);
    }
    public DefaultListModel<Tache> getTachesProjet(){
        this.model.getTachesProjet().clear();  
        this.SetTachesProjet();
        return model.getTachesProjet();
    }
    public void ClonerTache(Tache t,String page){
        String titreTache=dao.CreateTache(t);
        if(page.equals("Liste")){
            dao.AddTacheListe(titreTache,listeSelectionne.getNomListe());
            model.getTachesListe().clear();
            this.SetTacheListe();
            LancerViewTaches(page);
        }
        else{
            dao.AddTacheProjet(projetSelectionne.getTitreProjet(),titreTache);
            model.getTachesProjet().clear();
            this.SetTachesProjet();
            LancerViewTaches(page);
        }
    }
    public DefaultListModel<Tache> getTacheDetailModel(){
        DefaultListModel<Tache> viewTache = new DefaultListModel<>();
        viewTache.addElement(this.tacheSelectionne);
        return viewTache;
    }
    public void CloturerTache(){
        dao.CloturerTache(tacheSelectionne.getTitreTache());
        this.tacheSelectionne=dao.ReadTache(tacheSelectionne.getTitreTache());
        System.out.println(tacheSelectionne.getStatus());
        this.getTacheDetailModel().clear();
    }
    public void SupprimerTache(Tache t,Projet p,String page){
        if(page.equals("Liste")){
            dao.DeleteTacheListe(listeSelectionne.getNomListe(), t.getTitreTache());
            this.getTachesListe().clear();
            this.SetTacheListe();
        }
        else{
            dao.DeleteTacheProjet(p.getTitreProjet(), t.getTitreTache());
            this.getTachesProjet().clear();
            this.SetTachesProjet();
        }
    }
    
    public void ajouterTache(Tache t){
        dao.CreateTache(t);
        dao.AddTacheProjet(projetSelectionne.getTitreProjet(), t.getTitreTache());
    }
    public void AjoutTacheliste(Tache t){
        dao.CreateTache(t);
        System.out.println(listeSelectionne.getNomListe());
        System.out.println(t.getTitreTache());
        dao.AddTacheListe( t.getTitreTache(),listeSelectionne.getNomListe());
    }
    public DefaultListModel<Tache> getAllTaches(){
        DefaultListModel<Tache> taches = new DefaultListModel<>();
        ArrayList<Tache> tachesList = dao.ReadAllTaches();
        for(Tache t : tachesList){
            taches.addElement(t);
        }
        return taches;
    }
    public void ajouterTacheToListe(Tache t){
        dao.AddTacheListe(t.getTitreTache(),listeSelectionne.getNomListe());

    }
    public void RechercheParCategorieTacheProjet(Tache.categorie categorie){
        model.getTachesProjet().clear();
        model.setTachesProjet(dao.RechercheParCategorieTacheProjet(categorie,projetSelectionne.getTitreProjet()));
    }
    public void rechercherTachesProjet(String search,boolean isDescription){
        model.getTachesProjet().clear();
        model.setTachesProjet(dao.rechercherTachesProjet(search,projetSelectionne.getTitreProjet(),isDescription));
    }
    public void rechercherTachesListe(String search,boolean isDescription){
        model.getTachesListe().clear();
        model.setTachesListe(dao.rechercherTachesListe(search,listeSelectionne.getNomListe(),isDescription));
    }
    public void RechercheParCategorieTacheListe(Tache.categorie categorie){
        model.getTachesListe().clear();
        model.setTachesListe(dao.RechercheParCategorieTacheListe(categorie,listeSelectionne.getNomListe()));
    }
    public void refrecherTachesListe(){
        model.getTachesListe().clear();
        model.setTachesListe(dao.TachesListe(listeSelectionne.getNomListe()));
    }
    public void refrecherTachesProjet(){
        model.getTachesProjet().clear();
        model.setTachesProjet(dao.ReadTachesProjet(projetSelectionne.getTitreProjet()));
    }
}
