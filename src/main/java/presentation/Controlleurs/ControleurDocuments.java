package presentation.Controlleurs;
import metier.Pojo.*;
import persistance.DAODocument;
import presentation.DocumentsView;
import presentation.Models.ModelDocuments;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
public class ControleurDocuments {
    private ModelDocuments model;
    private DAODocument daoDocuments;
    private DocumentsView vue;
    private  Tache tacheSelectionne;
    private  Projet projetSelectionne;
    private Seance SeanceSelectionne;
    private Liste listeSelectionne;
  
    public ControleurDocuments(Projet p){
        daoDocuments = new DAODocument();
        model = new ModelDocuments();
        this.projetSelectionne = p;
        this.vue = new DocumentsView(this,"Unprojet");
    }
    public ControleurDocuments(Seance s){
        daoDocuments = new DAODocument();
        model = new ModelDocuments();
        this.SeanceSelectionne = s;
        this.vue = new DocumentsView(this,"UneSeance");
    }
    public ControleurDocuments(Tache t){
        daoDocuments = new DAODocument();
        model = new ModelDocuments();
        this.tacheSelectionne = t;
        this.vue = new DocumentsView(this,"UneTache");
    }
    public ControleurDocuments(Liste l,Tache t){
        daoDocuments = new DAODocument();
        model = new ModelDocuments();
        this.listeSelectionne = l;
        this.tacheSelectionne = t;
        this.vue = new DocumentsView(this,"Liste");
    }
    public void LancerVueDocuments(){
        this.vue.setVisible(true);
        this.vue.setSize(800, 650);
        vue.setLocationRelativeTo(null);
    }
    public Liste getListeSelectionne() {
        return listeSelectionne;
    }
    public void setListeSelectionne(Liste listeSelectionne) {
        this.listeSelectionne = listeSelectionne;
    }
    public Seance getSeanceSelectionne() {
        return SeanceSelectionne;
    }
    public void setSeanceSelectionne(Seance seanceSelectionne) {
        SeanceSelectionne = seanceSelectionne;
    }
    public  Tache getTacheSelectionne() {
        return tacheSelectionne;
    }
    public void setTacheSelectionne(Tache tacheSelectionne) {
        this.tacheSelectionne = tacheSelectionne;
    }
    public  Projet getProjetSelectionne() {
        return projetSelectionne;
    }

    private void SetDocumentProjet(){
        model.setDocumentsProjet(daoDocuments.ReadDocumentProjet(projetSelectionne.getTitreProjet()));
    }
    private void SetDocumentSeance(){
        model.setDocumentsSeance(daoDocuments.ReadDocumentsSeance(SeanceSelectionne.getTitreSeance()));
    }
    private void SetDocumentTache(){
        model.setDocumentsTache(daoDocuments.ReadDocumentsTache(tacheSelectionne.getTitreTache()));
    }
    public void SetDocumentsRechercheTaches(String search){
        ArrayList<Documents> documents=daoDocuments.RechercheDocumentTache(search,tacheSelectionne.getTitreTache());
        model.getDocumentsTache().clear();
        model.setDocumentsTache(documents);
    }
    public void SetDocumentsRechercheProjet(String search){
        ArrayList<Documents> documents=daoDocuments.RechercheDocumentProjet(search,projetSelectionne.getTitreProjet());
        model.getDocumentsProjet().clear();
        model.setDocumentsProjet(documents);
    }
    public void SetDocumentsRechercheSeance(String search){
        ArrayList<Documents> documents=daoDocuments.RechercheDocumentSeance(search,SeanceSelectionne.getTitreSeance());
        model.getDocumentsSeance().clear();
        model.setDocumentsSeance(documents);
    }
    public DefaultListModel<Documents> getDocumentProjet(){
        if(!this.estVideDocumentProjet()){
            if(!model.getDocumentsProjet().isEmpty()){
                model.getDocumentsProjet().clear();
            }
            this.SetDocumentProjet();
        }
        return model.getDocumentsProjet();
    }
    public DefaultListModel<Documents> getDocumentTache(){
        if(!this.estVideDocumentTache()){
            if(!model.getDocumentsTache().isEmpty()){
                model.getDocumentsTache().clear();
            }
            this.SetDocumentTache();
        }
        return model.getDocumentsTache();
    }
    public DefaultListModel<Documents> getDocumentSeance(){
        if(!this.estVideDocumentSeance()){
            if(!model.getDocumentsSeance().isEmpty()){
                model.getDocumentsSeance().clear();
            }
            this.SetDocumentSeance();
        }
        return model.getDocumentsSeance();
    }
    public void AjouterDocumentProjet(Documents d){
        daoDocuments.CreateDocument(d);
        daoDocuments.AddDocumentToProjet(projetSelectionne.getTitreProjet(), d.getLibelleDoc());
        model.getDocumentsProjet().clear();
        this.SetDocumentProjet();
    }
    public void AjouterDocumentTache(Documents d){
        daoDocuments.CreateDocument(d);
        daoDocuments.addDocumentToTache(tacheSelectionne.getTitreTache(), d.getLibelleDoc());
        model.getDocumentsTache().clear();
        this.SetDocumentTache();
    }
    public void AjouterDocumentSeance(Documents d){
        daoDocuments.CreateDocument(d);
        daoDocuments.addDocumentSeance(SeanceSelectionne.getTitreSeance(), d.getLibelleDoc());
        model.getDocumentsSeance().clear();
        this.SetDocumentSeance();
    }

    public void setProjetSelectionne(Projet p){
        this.projetSelectionne = p;
    }
    public boolean estVideDocumentProjet(){
        return daoDocuments.ReadDocumentProjet(projetSelectionne.getTitreProjet()).isEmpty();
    }
    public boolean estVideDocumentTache(){
        return daoDocuments.ReadDocumentsTache(tacheSelectionne.getTitreTache())==null;
    }
    public boolean estVideDocumentSeance(){
        return daoDocuments.ReadDocumentsSeance(SeanceSelectionne.getTitreSeance())==null;
    }
}
