package metier.Gestion;
import metier.Pojo.Projet;
import metier.Service.*;
import persistance.DAOProjet;

import java.util.ArrayList;
import org.bson.Document;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class GestionnaireImport {
    private GoogleCalendarAPI obj = new GoogleCalendarAPI();
    public void enregistreImport() throws GeneralSecurityException, IOException{
        DAOProjet dao = new DAOProjet();
        ArrayList<Document> liste = obj.importer();
        for(Document d : liste){
            if(dao.ReadProjet(d.getString("TitreProjet")).getTitreProjet() == null){
                Projet p = dao.DocumentToProjet(d);
                dao.CreateProjet(p);
            }
        }
    }
}