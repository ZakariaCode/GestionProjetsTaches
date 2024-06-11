package metier.Pojo;

import java.util.ArrayList;

public class Liste {
    private String nomListe,DescriptionListe;
    private ArrayList<String> Taches;
    
    public Liste(){
        super();
        Taches = new ArrayList<String>();
    }
    @Override
    public String toString() {
        return "Liste [nomListe=" + nomListe + ", DescriptionListe=" + DescriptionListe + ", Taches=" + Taches + "]";
    }
    public Liste(String nomListe, String DescriptionListe) {
        this();
        this.nomListe = nomListe;
        this.DescriptionListe = DescriptionListe;
    }

    public String getNomListe() {
        return nomListe;
    }

    public void setNomListe(String nomListe) {
        this.nomListe = nomListe;
    }

    public String getDescriptionListe() {
        return DescriptionListe;
    }

    public void setDescriptionListe(String descriptionListe) {
        DescriptionListe = descriptionListe;
    }
    public ArrayList<String> getTaches() {
        return Taches;
    }
    public void setTaches(ArrayList<String> taches) {
        Taches = taches;
    }
    

}