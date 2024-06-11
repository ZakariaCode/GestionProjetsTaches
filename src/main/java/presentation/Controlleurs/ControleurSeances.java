package presentation.Controlleurs;
import javax.swing.*;
import metier.Pojo.*;
import persistance.DAOSeance;
import presentation.*;
import presentation.Models.ModelSeances;
public class ControleurSeances {
    private DAOSeance dao;
    private ModelSeances modelseances;
    private Seance seanceSelectionne;
    private AjoutSeance viewFormulaire;
    private UneSeance detailViewSeance;
    private Seances ListeSeances;
    private Projet projetselectionne; 

    public ControleurSeances() {
        dao=new DAOSeance();
        modelseances= new ModelSeances();
        seanceSelectionne = new Seance();
    }
   
    public void LanceViewSeances(){
        ListeSeances = new Seances(this);
        ListeSeances.setSize(920, 510);
        ListeSeances.setLocationRelativeTo(null);
        ListeSeances.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ListeSeances.setVisible(true);
    }
    public void LanceViewSeance(){
        detailViewSeance = new UneSeance(this);
        detailViewSeance.setSize(800, 650);
        detailViewSeance.setLocationRelativeTo(null);
        detailViewSeance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        detailViewSeance.setVisible(true);
    }
    public void LanceViewFormulairSeance(){
        viewFormulaire = new AjoutSeance(this,false);
        viewFormulaire.setSize(1100, 510);
        viewFormulaire.setLocationRelativeTo(null);
        viewFormulaire.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFormulaire.setVisible(true);
    }
    public void LanceViewFormulairModifierSeance(){
        viewFormulaire = new AjoutSeance(this,true);
        viewFormulaire.setSize(1100, 510);
        viewFormulaire.setLocationRelativeTo(null);
        viewFormulaire.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        viewFormulaire.setVisible(true);
    }
    public void setSeances(){
        modelseances.getModel().clear();
        modelseances.setModel(dao.SeancesProjet(projetselectionne.getTitreProjet()));
     }
      public void setSeanceSelectionne(Seance seanceSelectionne) {
          this.seanceSelectionne = seanceSelectionne;
      }
     public Seance getSeanceSelectionne(){
         return seanceSelectionne;
     }
    public Projet getProjetSelectionne(){
        return projetselectionne;
    }
    public void setProjetSelectionne(Projet p){
        projetselectionne = p;
    }
    
    public DefaultListModel<Seance> getseances() {
        this.setSeances();
        return modelseances.getModel();
    }

    public void setModelseances(ModelSeances modelseances) {
        this.modelseances = modelseances;
    }
    public boolean AjouterSeance(Seance s){
        dao.CreateSeance(s);
        if(dao.AddSeanceToProjet(projetselectionne.getTitreProjet(), s.getTitreSeance())){
            modelseances.getModel().clear();
            this.getseances();
            return true;
        }
        return false;
    }
    public DefaultListModel<Seance> getSeanceDetail(){
        DefaultListModel<Seance> viewSeance = new DefaultListModel<>();
        viewSeance.addElement(this.seanceSelectionne);
        return viewSeance;
    }
    public void modifierSeance(String field,Object value){
        dao.updateSeanceProjet(projetselectionne.getTitreProjet(),seanceSelectionne.getTitreSeance(),field,value);
        modelseances.getModel().clear();
        this.getseances();
    }
    public void supprimerSeance(Seance s){
        dao.SupprimerSeanceProjet(projetselectionne.getTitreProjet(),s.getTitreSeance());
        modelseances.getModel().clear();
        this.getseances();
    }
    
}