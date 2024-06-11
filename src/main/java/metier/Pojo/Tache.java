package metier.Pojo;

import java.time.*;
import java.util.ArrayList;


public class Tache {
    public enum categorie {
        ENCADREMENT, ENSEIGNEMENT, AUTRE;
    }

    private String TitreTache, DescriptionTache;
    private LocalDateTime DatedebutTache, DelaiTache;
    private Boolean Status=false;
    private categorie CategorieTache;
    private ArrayList<Documents> Docs;

    public Tache() {
        super();
        Docs = new ArrayList<Documents>();
    }

    public Tache(String titreTache, String descriptionTache, LocalDateTime datedebutTache, LocalDateTime delaiTache,
            Tache.categorie categorieTache) {
        this();
        TitreTache = titreTache;
        DescriptionTache = descriptionTache;
        DatedebutTache = datedebutTache;
        DelaiTache = delaiTache;
        CategorieTache = categorieTache;
    }

    public String getTitreTache() {
        return TitreTache;
    }

    public void setTitreTache(String titreTache) {
        TitreTache = titreTache;
    }

    public String getDescriptionTache() {
        return DescriptionTache;
    }

    public void setDescriptionTache(String descriptionTache) {
        DescriptionTache = descriptionTache;
    }

    public LocalDateTime getDatedebutTache() {
        return DatedebutTache;
    }

    public void setDatedebutTache(LocalDateTime datedebutTache) {
        DatedebutTache = datedebutTache;
    }

    public LocalDateTime getDelaiTache() {
        return DelaiTache;
    }

    public void setDelaiTache(LocalDateTime delaiTache) {
        DelaiTache = delaiTache;
    }

    public categorie getCategorieTache() {
        return CategorieTache;
    }

    public void setCategorieTache(categorie categorieTache) {
        CategorieTache = categorieTache;
    }

    public ArrayList<Documents> getDocs() {
        return Docs;
    }

    public void setDocs(ArrayList<Documents> docs) {
        Docs = docs;
    }
    public Boolean getStatus() {
        return Status;
    }

    public void setStatus(Boolean status) {
        Status = status;
    }
    @Override
    public String toString() {
        return "Tache [TitreTache=" + TitreTache + ", DescriptionTache=" + DescriptionTache + ", DatedebutTache="
                + DatedebutTache + ", DelaiTache=" + DelaiTache + ", CategorieTache=" + CategorieTache + "]";
    }
}