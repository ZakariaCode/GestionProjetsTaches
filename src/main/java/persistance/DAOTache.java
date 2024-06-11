package persistance;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import metier.Pojo.Tache;

public class DAOTache {
    private connexion conn;

    public String CreateTache(Tache MaTache) {
        try {
            conn = connexion.getInstance();
            String NomUnique = UniciteNomTache(MaTache.getTitreTache(), conn);
            Document document = new Document("TitreTache", NomUnique)
                    .append("DescriptionTache", MaTache.getDescriptionTache())
                    .append("CategorieTache", MaTache.getCategorieTache().toString());
            document.append("DatedebutTache", MaTache.getDatedebutTache()).append("DelaiTache", MaTache.getDelaiTache())
                    .append("StatusTache", MaTache.getStatus());
            document.append("Docs", new ArrayList<String>());
            conn.GetCollection("Tache").insertOne(document);
            return NomUnique;
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return "";
        }
    }

    public ArrayList<Tache> ReadTachesProjet(String nomProjet) {
        ArrayList<Tache> ListesDeTaches = new ArrayList<Tache>();
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> Projets = conn.GetCollection("Projet");
            Document doc = Projets.find(new Document("TitreProjet", nomProjet)).first();
            if (doc != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> listesTaches = (ArrayList<String>) doc.get("Taches");
                for (String titreTache : listesTaches) {
                    Tache t = ReadTache(titreTache);
                    ListesDeTaches.add(t);
                }
            }
            return ListesDeTaches;
        } catch (Exception e) {
            return ListesDeTaches;
        }
    }

    public ArrayList<Tache> RechercheParCategorieTacheProjet(Tache.categorie Categorie, String nomProjet) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> projets = conn.GetCollection("Projet");
            Document projet = projets.find(new Document("TitreProjet", nomProjet)).first();
            if (projet != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) projet.get("Taches");
                if (taches != null) {
                    ArrayList<Tache> tches = new ArrayList<Tache>();
                    for (String t : taches) {
                        Tache tacheCurrent = ReadTache(t);
                        if (tacheCurrent.getCategorieTache().equals(Categorie)) {
                            tches.add(tacheCurrent);
                        }
                    }
                    return tches;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public ArrayList<Tache> RechercheParCategorieTacheListe(Tache.categorie Categorie, String nomListe) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> listes = conn.GetCollection("Liste");
            Document liste = listes.find(new Document("nomListe", nomListe)).first();
            if (liste != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) liste.get("Taches");
                if (taches != null) {
                    ArrayList<Tache> tches = new ArrayList<Tache>();
                    for (String t : taches) {
                        Tache tacheCurrent = ReadTache(t);
                        if (tacheCurrent.getCategorieTache().equals(Categorie)) {
                            tches.add(tacheCurrent);
                        }
                    }
                    return tches;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public void updateTacheProjet(String filterField, Object filterValue, String updateField, Object updateValue,
            String nomprojet) {
        connexion conn = connexion.getInstance();
        Document filter = new Document(filterField, filterValue);
        Document update = new Document("$set", new Document(updateField, updateValue));
        conn.GetCollection("Tache").updateOne(filter, update);
        if (updateField.equals("TitreTache")) {
            MongoCollection<Document> projets = conn.GetCollection("Projet");
            Document projet = projets.find(new Document("TitreProjet", nomprojet)).first();
            if (projet != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) projet.get("Taches");
                if (taches != null) {
                    if (taches.contains(filterValue)) {
                        taches.remove(filterValue);
                        taches.add(updateValue.toString());
                        projets.updateOne(Filters.eq("TitreProjet", nomprojet),
                                new Document("$set", new Document("Taches", taches)));
                    }
                }
            }
        }

    }

    public void updateTacheListe(String filterField, Object filterValue, String updateField, Object updateValue,
            String nomListe) {
        connexion conn = connexion.getInstance();
        Document filter = new Document(filterField, filterValue);
        Document update = new Document("$set", new Document(updateField, updateValue));
        conn.GetCollection("Tache").updateOne(filter, update);
        if (updateField.equals("TitreTache")) {
            MongoCollection<Document> listes = conn.GetCollection("Liste");
            Document liste = listes.find(new Document("nomListe", nomListe)).first();
            if (liste != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) liste.get("Taches");
                if (taches != null) {
                    if (taches.contains(filterValue)) {
                        taches.remove(filterValue);
                        taches.add(updateValue.toString());
                        listes.updateOne(Filters.eq("nomListe", nomListe),
                                new Document("$set", new Document("Taches", taches)));
                    }
                }
            }
        }

    }

    public ArrayList<Tache> TachesListe(String nomListe) {
        conn = connexion.getInstance();
        ArrayList<Tache> Taches = new ArrayList<Tache>();
        Document doc = conn.GetCollection("Liste").find(new Document("nomListe", nomListe)).first();
        if (doc != null) {
            @SuppressWarnings("unchecked")
            ArrayList<String> taches = (ArrayList<String>) doc.get("Taches");
            for (String titreTache : taches) {
                Tache t = new DAOTache().ReadTache(titreTache);
                Taches.add(t);
            }
        }
        return Taches;
    }
    public ArrayList<Tache> rechercherTachesProjet(String valeur, String nomProjet, boolean rechercheParDescription) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> projets = conn.GetCollection("Projet");
            Document projet = projets.find(new Document("TitreProjet", nomProjet)).first();
            if (projet != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) projet.get("Taches");
                if (taches != null) {
                    ArrayList<Tache> tches = new ArrayList<Tache>();
                    for (String t : taches) {
                        Tache tacheCurrent = ReadTache(t);
                        if (rechercheParDescription) {
                            if (tacheCurrent.getDescriptionTache().startsWith(valeur)) {
                                tches.add(tacheCurrent);
                            }
                        } else {
                            if (tacheCurrent.getTitreTache().startsWith(valeur)) {
                                tches.add(tacheCurrent);
                            }
                        }
                    }
                    return tches;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
 public ArrayList<Tache> rechercherTachesListe(String valeur, String nomListe, boolean rechercheParDescription) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> listes = conn.GetCollection("Liste");
            Document liste = listes.find(new Document("nomListe", nomListe)).first();
            if (liste != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) liste.get("Taches");
                if (taches != null) {
                    ArrayList<Tache> tches = new ArrayList<Tache>();
                    for (String t : taches) {
                        Tache tacheCurrent = ReadTache(t);
                        if (rechercheParDescription) {
                            if (tacheCurrent.getDescriptionTache().startsWith(valeur)) {
                                tches.add(tacheCurrent);
                            }
                        } else {
                            if (tacheCurrent.getTitreTache().startsWith(valeur)) {
                                tches.add(tacheCurrent);
                            }
                        }
                    }
                    return tches;
                }
            }
            return null;
        } catch (Exception e) {
            return null;
        }
    }
    public Tache ReadTache(String nomTache) {
        Tache maTache = new Tache();
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> Taches = conn.GetCollection("Tache");
            Document doc = Taches.find(new Document("TitreTache", nomTache)).first();
            if (doc != null) {
                maTache.setTitreTache(doc.getString("TitreTache"));
                maTache.setCategorieTache(Tache.categorie.valueOf(doc.getString("CategorieTache")));
                maTache.setDescriptionTache(doc.getString("DescriptionTache"));
                maTache.setDatedebutTache(RetrieveDate(doc, "DatedebutTache"));
                maTache.setDelaiTache(RetrieveDate(doc, "DelaiTache"));
                maTache.setStatus(doc.getBoolean("StatusTache"));
            }
            return maTache;
        } catch (Exception e) {
            return maTache;
        }
    }

    public void DeleteTache(String nomTache) {
        try {
            connexion conn = connexion.getInstance();
            conn.GetCollection("Tache").deleteOne(Filters.eq("TitreTache", nomTache));
            System.out.println("Supression réussie");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private String UniciteNomTache(String nom, connexion conn) {
        String NomUnique = nom;
        if (conn.GetCollection("Tache").find(Filters.eq("TitreTache", NomUnique)).first() != null) {
            long nbr = 1;
            String patternString = "^" + Pattern.quote(NomUnique) + "\\s";
            Pattern reg = Pattern.compile(patternString);
            nbr += conn.GetCollection("Tache").countDocuments(Filters.eq("TitreTache", reg));
            NomUnique += " (" + nbr + ")";
        }
        return NomUnique;
    }

    public LocalDateTime RetrieveDate(Document doc, String field) {
        Date date = (Date) doc.getDate(field);
        Instant instant = date.toInstant();
        LocalDateTime localDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        return localDate;
    }

    public void AddTacheProjet(String nomProjet, String nomTache) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> doc = conn.GetCollection("Tache");
            Document tache = doc.find(new Document("TitreTache", nomTache)).first();
            if (tache != null) {
                MongoCollection<Document> projets = conn.GetCollection("Projet");
                Document projet = projets.find(new Document("TitreProjet", nomProjet)).first();
                if (projet != null) {
                    @SuppressWarnings("unchecked")
                    ArrayList<String> taches = (ArrayList<String>) projet.get("Taches");
                    if (taches != null) {
                        if (!taches.contains(tache.getString("TitreTache"))) {
                            taches.add(tache.getString("TitreTache"));
                            projets.updateOne(Filters.eq("TitreProjet", nomProjet),
                                    new Document("$set", new Document("Taches", taches)));
                        }
                    } else {
                        ArrayList<Document> newTaches = new ArrayList<Document>();
                        newTaches.add(tache);
                        projets.updateOne(Filters.eq("TitreProjet", nomProjet),
                                new Document("$set", new Document("Taches", newTaches)));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean CloturerTache(String nomTache) {
        connexion conn = connexion.getInstance();
        MongoCollection<Document> Taches = conn.GetCollection("Tache");
        Document doc = Taches.find(new Document("TitreTache", nomTache)).first();
        Document update = new Document("$set", new Document("StatusTache", true));
        UpdateResult result = Taches.updateOne(doc, update);
        return result.getModifiedCount() > 0;
    }

    public void AddTacheListe(String nomTache, String nomListe) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> doc = conn.GetCollection("Tache");
            Document tache = doc.find(new Document("TitreTache", nomTache)).first();
            if (tache != null) {
                MongoCollection<Document> listes = conn.GetCollection("Liste");
                Document liste = listes.find(new Document("nomListe", nomListe)).first();
                if (liste != null) {
                    @SuppressWarnings("unchecked")
                    ArrayList<String> taches = (ArrayList<String>) liste.get("Taches");
                    if (taches != null) {
                        if (!taches.contains(tache.getString("TitreTache"))) {
                            taches.add(tache.getString("TitreTache"));
                            listes.updateOne(Filters.eq("nomListe", nomListe),
                                    new Document("$set", new Document("Taches", taches)));
                        }
                    } else {
                        ArrayList<Document> newTaches = new ArrayList<Document>();
                        newTaches.add(tache);
                        listes.updateOne(Filters.eq("nomListe", nomListe),
                                new Document("$set", new Document("Taches", newTaches)));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeleteTacheProjet(String nomProjet, String nomTache) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> projets = conn.GetCollection("Projet");
            Document projet = projets.find(new Document("TitreProjet", nomProjet)).first();
            if (projet != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) projet.get("Taches");
                if (taches != null) {
                    if (taches.contains(nomTache)) {
                        taches.remove(nomTache);
                        projets.updateOne(Filters.eq("TitreProjet", nomProjet),
                                new Document("$set", new Document("Taches", taches)));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void DeleteTacheListe(String nomListe, String nomTache) {
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> listes = conn.GetCollection("Liste");
            Document liste = listes.find(new Document("nomListe", nomListe)).first();
            if (liste != null) {
                @SuppressWarnings("unchecked")
                ArrayList<String> taches = (ArrayList<String>) liste.get("Taches");
                if (taches != null) {
                    if (taches.contains(nomTache)) {
                        taches.remove(nomTache);
                        listes.updateOne(Filters.eq("nomListe", nomListe),
                                new Document("$set", new Document("Taches", taches)));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public ArrayList<Tache> ReadAllTaches() {
        ArrayList<Tache> ListesDeTaches = new ArrayList<Tache>();
        try {
            connexion conn = connexion.getInstance();
            MongoCollection<Document> Taches = conn.GetCollection("Tache");
            for (Document doc : Taches.find()) {
                Tache t = new Tache();
                t.setTitreTache(doc.getString("TitreTache"));
                t.setCategorieTache(Tache.categorie.valueOf(doc.getString("CategorieTache")));
                t.setDescriptionTache(doc.getString("DescriptionTache"));
                t.setDatedebutTache(RetrieveDate(doc, "DatedebutTache"));
                t.setDelaiTache(RetrieveDate(doc, "DelaiTache"));
                t.setStatus(doc.getBoolean("StatusTache"));
                ListesDeTaches.add(t);
            }
            return ListesDeTaches;
        } catch (Exception e) {
            return ListesDeTaches;
        }

    }
}