package demax.geoterre.application.models;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import demax.geoterre.application.datas.Parcelle;

/**
 * Created by 2Max on 28/07/2015.
 */
public class ListParcelle extends ArrayList<Parcelle> {

    public static ListParcelle instance;
    private ListParcelle()
    {
        super();
    }

    public static ListParcelle getInstance() {
        if (instance == null)
            instance = new ListParcelle();
        return instance;
    }

    public Parcelle getParcelleByName(String name)
    {
        for(Parcelle p : this)
        {
            if(p.getNom().equals(name))
                return p;
        }
        return null;
    }

    public Parcelle getParcelleByID(int ID)
    {
        for(Parcelle p : this)
        {
            if(p.getID() == ID)
                return p;
        }
        return null;
    }
}
