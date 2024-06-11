package persistance;

import java.time.*;
import java.util.Date;
import java.util.regex.Pattern;
import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import metier.Pojo.Documents;

import java.util.ArrayList;


public class DAODocument {
    private connexion conn;
    public void CreateDocument(Documents MonDocument){
        try {
            conn = connexion.getInstance();
            String NomUnique = UniciteNomDocument(MonDocument.getLibelleDoc(),conn);
            Document document = new Document("LibelleDoc", NomUnique).append("DescriptionDoc",MonDocument.getDescriptionDoc()).append("Path",MonDocument.getPath()).append("DateAjout",MonDocument.getDateAjout());
            conn.GetCollection("Document").insertOne(document);
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public ArrayList<Documents> ReadDocumentProjet(String nomProjet) {
        ArrayList<Documents> listeDeDocuments = new ArrayList<>();
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet", nomProjet)).first();
            if (doc != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> listesDocuments = (ArrayList<String>) doc.get("Documents");
                for (String titreDocument : listesDocuments) {
                    Documents d = ReadDocument(titreDocument);
                    listeDeDocuments.add(d);
                }
            }
            return listeDeDocuments;
        } catch (Exception e) {
            return listeDeDocuments;
        }
    }    
    public Documents ReadDocument(String nomDocument){
        Documents monDocument = new Documents();
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Documents = conn.GetCollection("Document");
            Document doc = Documents.find(new Document("LibelleDoc",nomDocument)).first();
            if (doc!=null){
                monDocument.setLibelleDoc(doc.getString("LibelleDoc"));
                monDocument.setDescriptionDoc(doc.getString("DescriptionDoc"));
                monDocument.setPath(doc.getString("Path"));
                monDocument.setDateAjout(RetrieveDate(doc,"DateAjout"));
            }
            return monDocument;
        }catch(Exception e){
            return monDocument;
        }
    }
    public String UniciteNomDocument(String Nom,connexion conn){
        try{
            MongoCollection<Document> Documents = conn.GetCollection("Document");
            FindIterable<Document> iterDoc = Documents.find(Filters.regex("LibelleDoc", Pattern.compile("^"+Nom+"$")));
            if (iterDoc.first()==null){
                return Nom;
            }else{
                return Nom+"("+Documents.countDocuments()+")";
            }
        }catch(Exception e){
            return Nom;
        }
    }
    public LocalDateTime RetrieveDate(Document doc, String key){
        try{
            Date date = doc.getDate(key);
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }catch(Exception e){
            return LocalDateTime.now();
        }
    }
    public void DeleteDocument(String nomDocument){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Documents = conn.GetCollection("Document");
            Documents.deleteOne(Filters.eq("LibelleDoc", nomDocument));
        }catch(Exception e){
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public void UpdateDocument(String nomDocument, String cle, String newValeur){
        try{
            connexion conn=connexion.getInstance();
            Document doc = new Document("LibelleDoc", nomDocument);
            Document update = new Document("$set", new Document(cle, newValeur));
            conn.GetCollection("Document").updateOne(doc, update);
        }catch(Exception e){
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
    public ArrayList<Documents> ReadDocumentsTache(String nomTache){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> taches = conn.GetCollection("Tache");
            Document tache = taches.find(new Document("TitreTache",nomTache)).first();
            if(tache!=null){
                @SuppressWarnings("unchecked")
                ArrayList<String> documents = (ArrayList<String>)tache.get("Documents");
                if(documents!=null){
                    ArrayList<Documents> docs = new ArrayList<Documents>();
                    for(String doc : documents){
                        docs.add(ReadDocument(doc));
                    }
                    return docs;
                }
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
    public ArrayList<Documents> ReadDocumentsSeance(String nomSeance){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> seances = conn.GetCollection("Seance");
            Document seance = seances.find(new Document("TitreSeance",nomSeance)).first();
            if(seance!=null){
                @SuppressWarnings("unchecked")
                ArrayList<String> documents = (ArrayList<String>)seance.get("Documents");
                if(documents!=null){
                    ArrayList<Documents> docs = new ArrayList<Documents>();
                    for(String doc : documents){
                        docs.add(ReadDocument(doc));
                    }
                    return docs;
                }
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
    public void addDocumentSeance(String nomSeance,String nomDocument){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> doc = conn.GetCollection("Document");
            Document document = doc.find(new Document("LibelleDoc",nomDocument)).first();
            if(document!=null){
                MongoCollection<Document> seances = conn.GetCollection("Seance");
                Document seance = seances.find(new Document("TitreSeance",nomSeance)).first();
                if(seance!=null){
                    @SuppressWarnings("unchecked")
                    ArrayList<String> documents = (ArrayList<String>)seance.get("Documents");
                    if(documents!=null){
                        if(!documents.contains(document.getString("LibelleDoc"))){
                            documents.add(document.getString("LibelleDoc"));
                            seances.updateOne(Filters.eq("TitreSeance",nomSeance),new Document("$set",new Document("Documents",documents)));
                        }
                    }else{
                        ArrayList<String> newDocuments = new ArrayList<>();
                        newDocuments.add(document.getString("LibelleDoc"));
                        seances.updateOne(Filters.eq("TitreSeance",nomSeance),new Document("$set",new Document("Documents",newDocuments)));
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
   public void addDocumentToTache(String nomTache,String nomDocument){
    try{
        connexion conn=connexion.getInstance();
        MongoCollection<Document> doc = conn.GetCollection("Document");
        Document document = doc.find(new Document("LibelleDoc",nomDocument)).first();
        if(document!=null){
            MongoCollection<Document> taches = conn.GetCollection("Tache");
            Document tache = taches.find(new Document("TitreTache",nomTache)).first();
            if(tache!=null){
                @SuppressWarnings("unchecked")
                ArrayList<String> documents = (ArrayList<String>)tache.get("Documents");
                if(documents!=null){
                    if(!documents.contains(document.getString("LibelleDoc"))){
                        documents.add(document.getString("LibelleDoc"));
                        taches.updateOne(Filters.eq("TitreTache",nomTache),new Document("$set",new Document("Documents",documents)));
                    }
                }else{
                    ArrayList<String> newDocuments = new ArrayList<>();
                    newDocuments.add(document.getString("LibelleDoc"));
                    taches.updateOne(Filters.eq("TitreTache",nomTache),new Document("$set",new Document("Documents",newDocuments)));
                }
            }
        }
    }catch(Exception e){
        System.out.println(e.getMessage());
    }
    }
    public void AddDocumentToProjet(String nomProjet,String nomDocument){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> doc = conn.GetCollection("Document");
            Document document = doc.find(new Document("LibelleDoc",nomDocument)).first();
            if(document!=null){
                MongoCollection<Document> projets = conn.GetCollection("Projet");
                Document projet = projets.find(new Document("TitreProjet",nomProjet)).first();
                if(projet!=null){
                    @SuppressWarnings("unchecked")
                    ArrayList<String> documents = (ArrayList<String>)projet.get("Documents");
                    if(documents!=null){
                        if(!documents.contains(document.getString("LibelleDoc"))){
                            documents.add(document.getString("LibelleDoc"));
                            projets.updateOne(Filters.eq("TitreProjet",nomProjet),new Document("$set",new Document("Documents",documents)));
                        }
                    }else{
                        ArrayList<String> newDocuments = new ArrayList<>();
                        newDocuments.add(document.getString("LibelleDoc"));
                        projets.updateOne(Filters.eq("TitreProjet",nomProjet),new Document("$set",new Document("Documents",newDocuments)));
                    }
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
     public ArrayList<Documents> RechercheDocumentTache(String search,String tach){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> taches = conn.GetCollection("Tache");
            Document tache = taches.find(new Document("TitreTache",tach)).first();
            if(tache!=null){
                @SuppressWarnings("unchecked")
                ArrayList<String> documents = (ArrayList<String>)tache.get("Documents");
                if(documents!=null){
                    ArrayList<Documents> docs = new ArrayList<Documents>();
                    for(String doc : documents){
                        if(doc.startsWith(search)){
                            docs.add(ReadDocument(doc));
                        }
                    }
                    return docs;
                }
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
    public ArrayList<Documents> RechercheDocumentProjet(String search , String titreProjet){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> projets = conn.GetCollection("Projet");
            Document projet = projets.find(new Document("TitreProjet",titreProjet)).first();
            if(projet!=null){
                @SuppressWarnings("unchecked")
                ArrayList<String> documents = (ArrayList<String>)projet.get("Documents");
                if(documents!=null){
                    ArrayList<Documents> docs = new ArrayList<Documents>();
                    for(String doc : documents){
                        if(doc.startsWith(search)){
                            docs.add(ReadDocument(doc));
                        }
                    }
                    return docs;
                }
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
    public ArrayList<Documents> RechercheDocumentSeance(String search , String titreSeance){
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> seances = conn.GetCollection("Seance");
            Document seance = seances.find(new Document("TitreSeance",titreSeance)).first();
            if(seance!=null){
                @SuppressWarnings("unchecked")
                ArrayList<String> documents = (ArrayList<String>)seance.get("Documents");
                if(documents!=null){
                    ArrayList<Documents> docs = new ArrayList<Documents>();
                    for(String doc : documents){
                        if(doc.startsWith(search)){
                            docs.add(ReadDocument(doc));
                        }
                    }
                    return docs;
                }
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
}
