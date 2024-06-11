package presentation.Controlleurs;
import presentation.*;

public class ControleurConnexion {
    private Authentification connexionPage;
    public void LanceConnexion(){
    this.connexionPage = new Authentification(this);
    this.connexionPage.setVisible(true);
    }
    public static void main(String[] args) {
        ControleurConnexion c = new ControleurConnexion();
        c.LanceConnexion();
    }   
}