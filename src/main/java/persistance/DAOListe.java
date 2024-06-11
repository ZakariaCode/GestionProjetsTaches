package persistance;
import org.bson.Document;
import metier.Pojo.Liste;
import java.util.ArrayList;
import java.util.regex.Pattern;
import com.mongodb.client.model.Filters;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

public class DAOListe {
    private connexion conn ;
    public void CreateList(Liste newListe) {
        try {
            conn = connexion.getInstance();
            String NomUnique = UniciteNomList(newListe.getNomListe(),conn);
            Document document = new Document("nomListe", NomUnique).
            append("Description",newListe.getDescriptionListe());
            document.append("Taches",new ArrayList<String>());
            conn.GetCollection("Liste").insertOne(document);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public Liste ReadList(String nomListe){
        Liste liste = new Liste();
        try{
            conn=connexion.getInstance();
            MongoCollection<Document> Listes = conn.GetCollection("Liste");
            Document doc= Listes.find(new Document("nomListe",nomListe)).first();
            if(doc!=null){
                liste.setNomListe(doc.getString("nomListe"));
                liste.setDescriptionListe(doc.getString("Description"));
            }  
        }catch(Exception e){
            System.out.print(e.getMessage());
        }
        return liste;
    }

    public void UpdateList(String nomListe, String cle, String newValeur) {
        try {
            conn = connexion.getInstance();
            String ValeurEdit = newValeur;
            if (cle.equals("nomListe")) {
                ValeurEdit = UniciteNomList(newValeur,conn);
            }
            Document doc = new Document("nomListe", nomListe);
            Document update = new Document("$set", new Document(cle, ValeurEdit));
            conn.GetCollection("Liste").updateOne(doc, update);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public boolean DeleteList(String nomListe) {
        try {
            conn = connexion.getInstance();
            MongoCollection<Document> Listes = conn.GetCollection("Liste");
            Document doc= Listes.find(new Document("nomListe",nomListe)).first();
            @SuppressWarnings("unchecked")
            ArrayList<Object> arrayList = (ArrayList<Object>) doc.get("Taches");
            if (arrayList == null || arrayList.isEmpty()) {
                Listes.deleteOne(doc);
                return true;
            }
            return false;
        } catch (Exception e) {
           return false;
        }
    }
    public ArrayList<Liste> RechercheParNom(String nomListe){
        conn = connexion.getInstance();
        ArrayList<Liste> ListesDeProjets = new ArrayList<Liste>();
        FindIterable<Document> documents = conn.GetCollection("Liste").find(Filters.regex("nomListe", "^" + Pattern.quote(nomListe)));
        for(Document doc : documents){
            ListesDeProjets.add(this.ReadList(doc.getString("nomListe")));
        }
        return ListesDeProjets;
    }
    
    public ArrayList<Liste> getAllListes(){
        conn = connexion.getInstance();
        ArrayList<Liste> Listes = new ArrayList<Liste>();
        for(Document doc : conn.GetCollection("Liste").find()){
            Liste projet = ReadList(doc.getString("nomListe"));
            Listes.add(projet);
        }
        return Listes;
    }

    private String UniciteNomList(String nom,connexion conn) {
        String NomUnique = nom;
        if (conn.GetCollection("Liste").find(Filters.eq("nomListe", NomUnique)).first() != null) {
            long nbr = 1;
            String patternString = "^" + Pattern.quote(NomUnique) + "\\s";
            Pattern reg = Pattern.compile(patternString);
            nbr += conn.GetCollection("Liste").countDocuments(Filters.eq("nomListe", reg));
            NomUnique += " (" + nbr + ")";
        }
        return NomUnique;
    }
    
    
}