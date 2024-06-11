package metier.Service;

import org.bson.Document;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.Events;


import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.DateTime;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.*;
import metier.Pojo.Projet;



public class GoogleCalendarAPI {

    private static final String APPLICATION_NAME = "TDL";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR_READONLY);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
    
    public static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)throws IOException {
    InputStream in = GoogleCalendarAPI.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets =
    GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
    Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    return credential;
  }
	
    public List<Event> ImportEvents() throws GeneralSecurityException, IOException
	   {
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    	Calendar service =
        new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, GoogleCalendarAPI.getCredentials(HTTP_TRANSPORT))
            .setApplicationName(APPLICATION_NAME)
            .build();
    	DateTime now = new DateTime(System.currentTimeMillis());
    	Events events = service.events().list("primary")
    	    .setTimeMin(now)
    	    .setOrderBy("startTime")
    	    .setSingleEvents(true)
    	    .execute();
    	List<Event> items = events.getItems();
		return items;
	}

    public ArrayList<Document> importer() throws GeneralSecurityException, IOException{
        List<Event> items = ImportEvents();
        ArrayList<Document> documents = new ArrayList<>();
        for (Event event : items)
        {
            Document document = new Document();
            if( event.getSummary()!=null && event.getDescription()!=null){
                
            }
            String titre=(event.getSummary()!=null)?event.getSummary():"Sans Titre";
            String description=(event.getDescription()!=null)?event.getDescription():"Sans Description";
            document.append("TitreProjet", titre);
            document.append("DescriptionProjet", description);
            document.append("DatedebutProjet", new Date(event.getStart().getDateTime().getValue()));
            document.append("DelaiProjet", new Date(event.getEnd().getDateTime().getValue()));
            document.append("TypeProjet", Projet.type.AUTRE.toString());
            document.append("CategorieProjet", Projet.categorie.AUTRE.toString());
            document.append("Taches", new ArrayList<String>());
            documents.add(document);
        }
        return documents;
    }
    public static void main(String[] args) {
        GoogleCalendarAPI obj = new GoogleCalendarAPI();
        try {
            ArrayList<Document> liste = obj.importer();
            for(Document d : liste){
                System.out.println(d);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
    }
    }
}