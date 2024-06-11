package metier.Pojo;

import java.time.*;
import java.util.ArrayList;

import org.bson.types.ObjectId;

public class Seance {

    private ObjectId idSeance;
    private String titreSeance;
    private String descriptionSeance, noteSeance;
    private LocalDateTime DateDebutSeance, DateFinSeance;
    private ArrayList<Documents> Docs;

    public Seance() {
        super();
        this.idSeance = new ObjectId();
        Docs = new ArrayList<Documents>();
    }

    public Seance( String titreSeance,String descriptionSeance, String noteSeance,
            LocalDateTime DateDebutSeance, LocalDateTime DateFinSeance) {
        this();
        this.titreSeance = titreSeance;
        this.descriptionSeance = descriptionSeance;
        this.noteSeance = noteSeance;
        this.DateDebutSeance = DateDebutSeance;
        this.DateFinSeance = DateFinSeance;
    }

    public ObjectId getIdSeance() {
        return idSeance;
    }

    @Override
    public String toString() {
        return "Seance [idSeance=" + idSeance + ", titreSeance=" + titreSeance + ", descriptionSeance="
                + descriptionSeance + ", noteSeance=" + noteSeance + ", DateDebutSeance=" + DateDebutSeance
                + ", DateFinSeance=" + DateFinSeance + ", Docs=" + Docs + "]";
    }

    public void setIdSeance(ObjectId idSeance) {
        this.idSeance = idSeance;
    }    

    public LocalDateTime getDateFinSeance() {
        return DateFinSeance;
    }

    public void setDateFinSeance(LocalDateTime DateFinSeance) {
        this.DateFinSeance = DateFinSeance;
    }

    public LocalDateTime getDateDebutSeance() {
        return DateDebutSeance;
    }

    public void setDateDebutSeance(LocalDateTime DateDebutSeance) {
        this.DateDebutSeance = DateDebutSeance;
    }

    public String getDescriptionSeance() {
        return descriptionSeance;
    }

    public void setDescriptionSeance(String descriptionSeance) {
        this.descriptionSeance = descriptionSeance;
    }

    public String getNoteSeance() {
        return noteSeance;
    }

    public void setNoteSeance(String noteSeance) {
        this.noteSeance = noteSeance;
    }

    public ArrayList<Documents> getDocs() {
        return Docs;
    }

    public void setDocs(ArrayList<Documents> docs) {
        Docs = docs;
    }

    public String getTitreSeance() {
        return titreSeance;
    }

    public void setTitreSeance(String titreSeance) {
        this.titreSeance = titreSeance;
    }
}