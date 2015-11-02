package demax.geoterre.application.datas;

/**
 * Created by 2Max on 02/11/2015.
 */
public class Cepage {

    private String ID;
    private String libelle;

    public Cepage(String ID, String libelle) {
        this.ID = ID;
        this.libelle = libelle;
    }

    public String getID() {
        return ID;
    }

    public String getLibelle() {
        return libelle;
    }
}
