package metier.Pojo;
import java.time.*;
import java.util.ArrayList;

import org.bson.types.ObjectId;

public class Projet {
    public enum type {
        THESE,PFA,PFE,AUTRE;
    }
    public enum categorie {
        ENCADREMENT,ENSEIGNEMENT,AUTRE;
    }
    private String TitreProjet,DescriptionProjet;
    private LocalDateTime DatedebutProjet,DelaiProjet;
    private type TypeProjet;
    private categorie CategorieProjet;
    private ArrayList<ObjectId> SeanceAssociees;
    private ArrayList<String> lesTaches;
    private ArrayList<String> documents;
    public ArrayList<ObjectId> getSeanceAssociees() {
        return SeanceAssociees;
    }


    public void setSeanceAssociees(ArrayList<ObjectId> seanceAssociees) {
        SeanceAssociees = seanceAssociees;
    }

    private Boolean status;

    
    public Projet() {
        super();
        SeanceAssociees = new ArrayList<ObjectId>();
        lesTaches = new ArrayList<String>();
        documents = new ArrayList<String>();
        this.status=false;
    }


    public Projet(String titreProjet, String descriptionProjet, LocalDateTime datedebutProjet, LocalDateTime delaiProjet,
        Projet.type typeProjet, Projet.categorie categorieProjet) {
        this();
        this.TitreProjet = titreProjet;
        this.DescriptionProjet = descriptionProjet;
        this.DatedebutProjet = datedebutProjet;
        this.DelaiProjet = delaiProjet;
        this.TypeProjet = typeProjet;
        this.CategorieProjet = categorieProjet;
    }
    
    public String getTitreProjet() {
        return TitreProjet;
    }

    public void setTitreProjet(String titreProjet) {
        TitreProjet = titreProjet;
    }

    public String getDescriptionProjet() {
        return DescriptionProjet;
    }
    
    public void setDescriptionProjet(String descriptionProjet) {
        DescriptionProjet = descriptionProjet;
    }
    
    public LocalDateTime getDatedebutProjet() {
        return DatedebutProjet;
    }

    public void setDatedebutProjet(LocalDateTime datedebutProjet) {
        DatedebutProjet = datedebutProjet;
    }

    public LocalDateTime getDelaiProjet() {
        return DelaiProjet;
    }

    public void setDelaiProjet(LocalDateTime delaiProjet) {
        DelaiProjet = delaiProjet;
    }

    public type getTypeProjet() {
        return TypeProjet;
    }

    public void setTypeProjet(type typeProjet) {
        TypeProjet = typeProjet;
    }

    public categorie getCategorieProjet() {
        return CategorieProjet;
    }

    public void setCategorieProjet(categorie categorieProjet) {
        CategorieProjet = categorieProjet;
    }

    public Boolean getCloture() {
        return status;
    }

    public ArrayList<String> getLesTaches() {
        return lesTaches;
    }


    public void setLesTaches(ArrayList<String> lesTaches) {
        this.lesTaches = lesTaches;
    }


    public ArrayList<String> getDocuments() {
        return documents;
    }


    public void setDocuments(ArrayList<String> documents) {
        this.documents = documents;
    }


    public void setCloture(Boolean cloture) {
        status = cloture;
    }

    @Override
    public String toString() {
        return "Projet [TitreProjet=" + TitreProjet + ", DescriptionProjet=" + DescriptionProjet + ", DatedebutProjet="
                + DatedebutProjet + ", DelaiProjet=" + DelaiProjet + ", TypeProjet=" + TypeProjet + ", CategorieProjet="
                + CategorieProjet + "]";
    }

    
    
    
}