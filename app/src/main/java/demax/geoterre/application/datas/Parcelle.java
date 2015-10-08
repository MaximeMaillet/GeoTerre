package demax.geoterre.application.datas;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by 2Max on 28/07/2015.
 */
public class Parcelle {

    private int ID;
    private String nom;
    private double surface;
    private int nombreroute;
    private ArrayList<LatLng> coordonnes;

    public Parcelle()
    {
        this.ID = 0;
        this.nom = "Default";
        this.surface = 0.;
        this.nombreroute = 0;
        this.coordonnes = new ArrayList<LatLng>();
    }

    public Parcelle(int ID, String _nom, double surface, int nombreroute, ArrayList<LatLng> coordonnes) {
        this.ID = ID;
        this.nom = _nom;
        this.surface = surface;
        this.nombreroute = nombreroute;
        this.coordonnes = coordonnes;
    }

    public int getID() {
        return ID;
    }

    public double getSurface() {
        return surface;
    }

    public int getNombreroute() {
        return nombreroute;
    }

    public ArrayList<LatLng> getCoordonnes() {
        return coordonnes;
    }

    public String getNom() { return this.nom; }
}
