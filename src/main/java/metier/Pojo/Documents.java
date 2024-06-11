package metier.Pojo;
import java.time.*;
public class Documents {
    private String LibelleDoc,DescriptionDoc,Path;
    private LocalDateTime DateAjout;
    
    public Documents() {
        super();
    }

    public Documents(String LibelleDoc, String DescriptionDoc,String Path, LocalDateTime DateAjout) { 
        this.LibelleDoc = LibelleDoc;
        this.DescriptionDoc = DescriptionDoc;
        this.DateAjout = DateAjout;
        this.Path=Path;
    }

    public String getLibelleDoc() {
        return LibelleDoc;
    }

    public void setLibelleDoc(String LibelleDoc) {
        this.LibelleDoc = LibelleDoc;
    }

    public String getDescriptionDoc() {
        return DescriptionDoc;
    }

    public void setDescriptionDoc(String DescriptionDoc) {
       this. DescriptionDoc = DescriptionDoc;
    }

    public LocalDateTime getDateAjout() {
        return DateAjout;
    }

    public void setDateAjout(LocalDateTime DateAjout) {
        this.DateAjout = DateAjout;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String Path) {
        this.Path = Path;
    }

    @Override
    public String toString() {
        return "Documents [LibelleDoc=" + LibelleDoc + ", DescriptionDoc=" + DescriptionDoc + ", Path=" + Path
                + ", DateAjout=" + DateAjout + "]";
    }
    
  
}