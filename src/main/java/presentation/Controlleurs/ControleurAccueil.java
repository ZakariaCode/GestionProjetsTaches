package presentation.Controlleurs;

import presentation.Accueil;

public class ControleurAccueil {
    private Accueil HomePage;
    public void LanceAccueil(){
        this.HomePage = new Accueil(this);
        this.HomePage.setVisible(true);
    }
}
