package persistance;
import java.time.*;
import java.util.regex.Pattern;
import java.util.Date;
import java.util.HashMap;

import org.bson.Document;
import org.bson.types.ObjectId;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import metier.Pojo.Projet;
import java.util.ArrayList;
import java.util.Arrays;


public class DAOProjet {
    private connexion conn;
    public boolean CreateProjet(Projet MonProjet){
        try {
            conn = connexion.getInstance();
            String NomUnique = UniciteNomProjet(MonProjet.getTitreProjet(),conn);
            Document document = new Document("TitreProjet", NomUnique).append("DescriptionProjet",MonProjet.getDescriptionProjet()).append("CategorieProjet",MonProjet.getCategorieProjet().toString());
            document.append("TypeProjet",MonProjet.getTypeProjet().toString()).append("DatedebutProjet",MonProjet.getDatedebutProjet()).append("DelaiProjet",MonProjet.getDelaiProjet());
            document.append("Status",MonProjet.getCloture()).append("Taches",new ArrayList<String>());
            document.append("Documents",new ArrayList<String>());
            document.append("Seances",new ArrayList<ObjectId>());
            
            conn.GetCollection("Projet").insertOne(document);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    public Projet ReadProjet(String nomProjet){
        Projet monProjet = new Projet();
        try{
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet",nomProjet)).first();
            if (doc!=null){
                monProjet.setTitreProjet(doc.getString("TitreProjet"));
                monProjet.setCategorieProjet(Projet.categorie.valueOf(doc.getString("CategorieProjet")));
                monProjet.setTypeProjet(Projet.type.valueOf(doc.getString("TypeProjet")));
                monProjet.setDescriptionProjet(doc.getString("DescriptionProjet"));
                monProjet.setDatedebutProjet(RetrieveDate(doc,"DatedebutProjet"));
                monProjet.setDelaiProjet(RetrieveDate(doc,"DelaiProjet"));
                monProjet.setCloture(doc.getBoolean("Status"));
            }
            return monProjet;
        }catch(Exception e){
            return monProjet;
        }
    }

    public boolean DeleteProjet(String nomProjet) {
        try {
            connexion conn = connexion.getInstance();
            conn.GetCollection("Projet").deleteOne(Filters.eq("TitreProjet", nomProjet));
            return true;
        } catch (Exception e) {
            return false;
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
    public void updateProjet(String filterField, Object filterValue,String updateField, Object updateValue) {
        connexion conn = connexion.getInstance();
        Document filter = new Document(filterField, filterValue);
        Document update = new Document("$set", new Document(updateField, updateValue));
        conn.GetCollection("Projet").updateOne(filter, update);
    }
    public boolean ClonerProjet(Projet ProjetClone){
        return CreateProjet(ProjetClone);
    }

    public ArrayList<Projet> RechercheParTitre(String Titre){
        conn = connexion.getInstance();
        ArrayList<Projet> ListesDeProjets = new ArrayList<Projet>();
        FindIterable<Document> documents = conn.GetCollection("Projet").find(Filters.regex("TitreProjet", "^" + Pattern.quote(Titre)));
        for(Document doc : documents){
            ListesDeProjets.add(this.ReadProjet(doc.getString("TitreProjet")));
        }
        return ListesDeProjets;
    }
    public ArrayList<Projet> RechercheParDescription(String description){
        conn = connexion.getInstance();
        ArrayList<Projet> ListesDeProjets = new ArrayList<Projet>();
        FindIterable<Document> documents = conn.GetCollection("Projet").find(Filters.regex("DescriptionProjet",".*"+Pattern.quote(description)+".*"));
        for(Document doc : documents){
            ListesDeProjets.add(this.ReadProjet(doc.getString("TitreProjet")));
        }
        return ListesDeProjets;
    }

    public ArrayList<Projet> getAllProjets(){
        conn = connexion.getInstance();
        ArrayList<Projet> ListesDeProjets = new ArrayList<Projet>();
        for(Document doc : conn.GetCollection("Projet").find()){
            Projet projet = ReadProjet(doc.getString("TitreProjet"));
            ListesDeProjets.add(projet);
        }
        return ListesDeProjets;
    }
    public boolean CloturerProjet(String nomProjet){
            connexion conn=connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet",nomProjet)).first();
            Document update = new Document("$set", new Document("Status", true));
            UpdateResult result = Projets.updateOne(doc, update);
            return result.getModifiedCount()>0;
        }

    private String UniciteNomProjet(String nom,connexion conn) {
        String NomUnique = nom;
        if (conn.GetCollection("Projet").find(Filters.eq("TitreProjet", NomUnique)).first() != null) {
            long nbr = 1;
            String patternString = "^" + Pattern.quote(NomUnique) + "\\s";
            Pattern reg = Pattern.compile(patternString);
            nbr += conn.GetCollection("Projet").countDocuments(Filters.eq("TitreProjet", reg));
            NomUnique += " (" + nbr + ")";
        }
        return NomUnique;
    }
    public ArrayList<Projet> RechercheParCategorie(Projet.categorie Categorie){
        conn = connexion.getInstance();
        ArrayList<Projet> ListesDeProjets = new ArrayList<Projet>();
        FindIterable<Document> documents = conn.GetCollection("Projet").find(Filters.eq("CategorieProjet", Categorie.toString()));
        for(Document doc : documents){
            ListesDeProjets.add(this.ReadProjet(doc.getString("TitreProjet")));
        }
        return ListesDeProjets;
    }
    public Projet DocumentToProjet(Document doc){
        Projet monProjet = new Projet();
        monProjet.setTitreProjet(doc.getString("TitreProjet"));
        monProjet.setCategorieProjet(Projet.categorie.valueOf(doc.getString("CategorieProjet")));
        monProjet.setTypeProjet(Projet.type.valueOf(doc.getString("TypeProjet")));
        monProjet.setDescriptionProjet(doc.getString("DescriptionProjet"));
        monProjet.setDatedebutProjet(RetrieveDate(doc,"DatedebutProjet"));
        monProjet.setDelaiProjet(RetrieveDate(doc,"DelaiProjet"));
        return monProjet;
      
        }
    public ArrayList<Projet> RechercheParType(Projet.type Type){
        conn = connexion.getInstance();
        ArrayList<Projet> ListesDeProjets = new ArrayList<Projet>();
        FindIterable<Document> documents = conn.GetCollection("Projet").find(Filters.eq("TypeProjet", Type.toString()));
        for(Document doc : documents){
            ListesDeProjets.add(this.ReadProjet(doc.getString("TitreProjet")));
        }
        return ListesDeProjets;
    }
    private LocalDateTime RetrieveDate(Document doc,String field){
        Date date = (Date)doc.getDate(field);
        Instant instant = date.toInstant();
        LocalDateTime localDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDate;  
    }
    //last add
     public double DureeProjet(String TitreProjet){
        Double durationHours = 0.0;
        try {
            conn = connexion.getInstance();
            MongoCollection<Document> collection = conn.GetCollection("Projet");
            Document match = new Document("$match", new Document("TitreProjet", TitreProjet));
            Document project = new Document("$project", new Document("durationHours", new Document("$divide", Arrays.asList(
            new Document("$subtract", Arrays.asList("$DelaiProjet", "$DatedebutProjet")), 3600000))));
            MongoCursor<Document> cursor = collection.aggregate(Arrays.asList(match,project)).iterator();
            while (cursor.hasNext()) {
                Document document = cursor.next();
                durationHours = document.getDouble("durationHours");
            }
            return durationHours;          
        } catch (Exception e) {
            return durationHours;
        }
    }
    @SuppressWarnings("finally")
    public int NombreDocument(String TitreProjet){
        int nbrDoc = 0;
        try{
            conn = connexion.getInstance();
            MongoCollection<Document> collection = conn.GetCollection("Projet");
            Document projectDocument = collection.find(new Document("TitreProjet", TitreProjet)).first();
            @SuppressWarnings("unchecked")
            ArrayList<Object> elements = projectDocument.get("Documents", ArrayList.class);
            nbrDoc = elements.size();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            return nbrDoc;
        }
    }

    
    @SuppressWarnings("finally")
    public double HeuresTravailProjetDansSemaine(String champ,String valeur){
        double Total = 0.0;
        try{
        conn = connexion.getInstance();
        MongoCollection<Document> collection = conn.GetCollection("Projet");
        LocalDate oneWeekAgo = LocalDate.now().minusWeeks(1);
        Date oneWeekAgoDate = Date.from(oneWeekAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Document filtres = new Document();
        filtres.append("DatedebutProjet", new Document("$gt", oneWeekAgoDate));
        filtres.append(champ,valeur);
        for(Document doc : collection.find(filtres)){
            Total+= DureeProjet(ReadProjet(doc.getString("TitreProjet")).getTitreProjet());
        }
        }catch(Exception e){
            System.out.println();
        }finally{
            return Total;
        }
    }
    @SuppressWarnings("finally")
    public double HeuresTravailProjetDansMois(String champ,String valeur){
        double Total = 0.0;
        try{
        conn = connexion.getInstance();
        MongoCollection<Document> collection = conn.GetCollection("Projet");
        LocalDate oneWeekAgo = LocalDate.now().minusMonths(1);
        Date oneWeekAgoDate = Date.from(oneWeekAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Document filtres = new Document();
        filtres.append("DatedebutProjet", new Document("$gt", oneWeekAgoDate));
        filtres.append(champ,valeur);
        for(Document doc : collection.find(filtres)){
            Total+= DureeProjet(ReadProjet(doc.getString("TitreProjet")).getTitreProjet());
        }
        }catch(Exception e){
            System.out.println();
        }finally{
            return Total;
        }
    }
    @SuppressWarnings("finally")
    public double HeuresTravailProjetDansAnnee(String champ,String valeur){
        double Total = 0.0;
        try{
        conn = connexion.getInstance();
        MongoCollection<Document> collection = conn.GetCollection("Projet");
        LocalDate oneWeekAgo = LocalDate.now().minusYears(1);
        Date oneWeekAgoDate = Date.from(oneWeekAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Document filtres = new Document();
        filtres.append("DatedebutProjet", new Document("$gt", oneWeekAgoDate));
        filtres.append(champ,valeur);
        for(Document doc : collection.find(filtres)){
            Total+= DureeProjet(ReadProjet(doc.getString("TitreProjet")).getTitreProjet());
        }
        }catch(Exception e){
            System.out.println();
        }finally{
            return Total;
        }
    }
    public HashMap<String,Double> getStatistiquesHebdoParType(){
        HashMap<String,Double> stats  =new HashMap<String,Double>();
        stats.put("AUTRE", HeuresTravailProjetDansSemaine("TypeProjet","AUTRE"));
        stats.put("THESE", HeuresTravailProjetDansSemaine("TypeProjet","THESE"));
        stats.put("PFA", HeuresTravailProjetDansSemaine("TypeProjet","PFA"));
        stats.put("PFE", HeuresTravailProjetDansSemaine("TypeProjet","PFE"));
        return stats;
    }
    public HashMap<String,Double> getStatistiquesHebdoParCategorie(){
        HashMap<String,Double> stats  =new HashMap<String,Double>();
        stats.put("ENSEIGNEMENT", HeuresTravailProjetDansSemaine("CategorieProjet","ENSEIGNEMENT"));
        stats.put("ENCADREMENT", HeuresTravailProjetDansSemaine("CategorieProjet","ENCADREMENT"));
        stats.put("AUTRE", HeuresTravailProjetDansSemaine("CategorieProjet","AUTRE"));
        return stats;
    }
    public HashMap<String,Double> getStatistiquesMensuellesParType(){
        HashMap<String,Double> stats  =new HashMap<String,Double>();
        stats.put("AUTRE", HeuresTravailProjetDansMois("TypeProjet","AUTRE"));
        stats.put("THESE", HeuresTravailProjetDansMois("TypeProjet","THESE"));
        stats.put("PFA", HeuresTravailProjetDansMois("TypeProjet","PFA"));
        stats.put("PFE", HeuresTravailProjetDansMois("TypeProjet","PFE"));
        return stats;
    }
    public HashMap<String,Double> getStatistiquesMensuellesParCategorie(){
        HashMap<String,Double> stats  =new HashMap<String,Double>();
        stats.put("ENSEIGNEMENT", HeuresTravailProjetDansMois("CategorieProjet","ENSEIGNEMENT"));
        stats.put("ENCADREMENT", HeuresTravailProjetDansMois("CategorieProjet","ENCADREMENT"));
        stats.put("AUTRE", HeuresTravailProjetDansMois("CategorieProjet","AUTRE"));
        return stats;
    }
    public HashMap<String,Double> getStatistiquesAnuellesParType(){
        HashMap<String,Double> stats  =new HashMap<String,Double>();
        stats.put("AUTRE", HeuresTravailProjetDansAnnee("TypeProjet","AUTRE"));
        stats.put("THESE", HeuresTravailProjetDansAnnee("TypeProjet","THESE"));
        stats.put("PFA", HeuresTravailProjetDansAnnee("TypeProjet","PFA"));
        stats.put("PFE", HeuresTravailProjetDansAnnee("TypeProjet","PFE"));
        return stats;
    }
    public HashMap<String,Double> getStatistiquesAnnuellesParCategorie(){
        HashMap<String,Double> stats  =new HashMap<String,Double>();
        stats.put("ENSEIGNEMENT", HeuresTravailProjetDansAnnee("CategorieProjet","ENSEIGNEMENT"));
        stats.put("ENCADREMENT", HeuresTravailProjetDansAnnee("CategorieProjet","ENCADREMENT"));
        stats.put("AUTRE", HeuresTravailProjetDansAnnee("CategorieProjet","AUTRE"));
        return stats;
    }     
}