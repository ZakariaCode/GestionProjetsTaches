package persistance;
import org.bson.Document;
import metier.Pojo.Seance;
import java.util.Date;
import org.bson.types.ObjectId;
import com.mongodb.client.MongoCollection;
import java.util.ArrayList;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
public class DAOSeance { 
    public ObjectId CreateSeance(Seance MaSeance){
        try {
            connexion conn = connexion.getInstance();
            Document document = new Document("TitreSeance",MaSeance.getTitreSeance()).append("DescriptionSeance",MaSeance.getDescriptionSeance()).append("Note",MaSeance.getNoteSeance()).append("DateDebutSeance",MaSeance.getDateDebutSeance()).append("Documents",new ArrayList<String>());
            document.append("DateFinSeance",MaSeance.getDateFinSeance());
            conn.GetCollection("Seance").insertOne(document);
            MaSeance.setIdSeance(document.getObjectId("_id"));
            return document.getObjectId("_id");
        } catch (Exception e) {
            return null;
        }
    }
    public Seance ReadSeance(String idSeance){
        Seance maSeance = new Seance();
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Seances = conn.GetCollection("Seance");
            Document doc = Seances.find(new Document("_id",new ObjectId(idSeance))).first();
            if (doc!=null){
                maSeance.setTitreSeance(doc.getString("TitreSeance"));
                maSeance.setDescriptionSeance(doc.getString("DescriptionSeance"));
                maSeance.setNoteSeance(doc.getString("Note"));
                maSeance.setDateDebutSeance(RetrieveDate(doc,"DateDebutSeance"));
                maSeance.setDateFinSeance(RetrieveDate(doc,"DateFinSeance"));
            }
            return maSeance;
        }catch(Exception e){
            return maSeance;
        }
    }
    public ObjectId ReadSeanceBytitre(String titreSeance){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Seances = conn.GetCollection("Seance");
            Document doc = Seances.find(new Document("TitreSeance",titreSeance)).first();
            if (doc!=null){
                return doc.getObjectId("_id");
            }
            return null;
        }catch(Exception e){
            return null;
        }
    }
    public ArrayList<Seance> SeancesProjet(String nomProjet){
       ArrayList<Seance> ListesSeance = new ArrayList<Seance>();
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet",nomProjet)).first();
            if (doc!=null){
                @SuppressWarnings("unchecked")
                ArrayList<ObjectId> listesSeance = (ArrayList<ObjectId>)doc.get("Seances");
                for(ObjectId idSeance : listesSeance){
                    Seance s = ReadSeance(idSeance.toString());
                    ListesSeance.add(s);
                }
            }
            return ListesSeance;
        }
        catch(Exception e){
            return ListesSeance;
        }
    }
    public boolean AddSeanceToProjet(String nomProjet,String nomSeance){
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet",nomProjet)).first();
            if (doc!=null){
                @SuppressWarnings("unchecked")
                ArrayList<ObjectId> listesSeance = (ArrayList<ObjectId>)doc.get("Seances");
                ObjectId idSeance = ReadSeanceBytitre(nomSeance);
                if(!listesSeance.contains(idSeance)){
                    listesSeance.add(idSeance);
                    Document update = new Document("Seances",listesSeance);
                    conn.GetCollection("Projet").updateOne(new Document("TitreProjet",nomProjet),new Document("$set",update));
                    return true;
                }
                else{
                    return false;
                }
            }
            return false;
    }
    public LocalDateTime RetrieveDate(Document doc,String field){
        Date date = (Date)doc.getDate(field);
        Instant instant = date.toInstant();
        LocalDateTime localDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDate;  
    } 
    public void SupprimerSeanceProjet(String nomProjet,String nomSeance){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet",nomProjet)).first();
            if (doc!=null){
                @SuppressWarnings("unchecked")
                ArrayList<ObjectId> listesSeance = (ArrayList<ObjectId>)doc.get("Seances");
                ObjectId idSeance = ReadSeanceBytitre(nomSeance);
                listesSeance.remove(idSeance);
                Document update = new Document("Seances",listesSeance);
                conn.GetCollection("Projet").updateOne(new Document("TitreProjet",nomProjet),new Document("$set",update));
            }
        }catch(Exception e){
        }
    }
    public Seance RechercheParDateDebutDateFin(LocalDateTime dateDebut,LocalDateTime dateFin){
        Seance maSeance = new Seance();
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Seances = conn.GetCollection("Seance");
            Document doc = Seances.find(new Document("DateDebutSeance",dateDebut).append("DateFinSeance",dateFin)).first();
            if (doc!=null){
                maSeance.setTitreSeance(doc.getString("TitreSeance"));
                maSeance.setDescriptionSeance(doc.getString("DescriptionSeance"));
                maSeance.setNoteSeance(doc.getString("Note"));
                maSeance.setDateDebutSeance(RetrieveDate(doc,"DateDebutSeance"));
                maSeance.setDateFinSeance(RetrieveDate(doc,"DateFinSeance"));
            }
            return maSeance;
        }catch(Exception e){
            return maSeance;
        }
    }
    public void updateSeanceProjet(String nomProjet,String nomSeance,String field,Object value){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet",nomProjet)).first();
            if (doc!=null){
                @SuppressWarnings("unchecked")
                ArrayList<ObjectId> listesSeance = (ArrayList<ObjectId>)doc.get("Seances");
                ObjectId idSeance = ReadSeanceBytitre(nomSeance);
                if(listesSeance.contains(idSeance)){
                    Document update = new Document(field,value);
                    conn.GetCollection("Seance").updateOne(new Document("_id",idSeance),new Document("$set",update));
                }
            }
        }catch(Exception e){
        }
    }
}