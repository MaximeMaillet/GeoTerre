package demax.geoterre.views;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.List;

import demax.geoterre.R;
import demax.geoterre.application.datas.Parcelle;

/**
 * Created by 2Max on 29/07/2015.
 */
public class GeoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private MapsMainActivity myView;
    private Parcelle myParcelle;

    public GeoWindowAdapter(MapsMainActivity _view)
    {
        this.myView = _view;
    }

    @Override
    public View getInfoWindow(Marker marker) {

        View v = this.myView.getLayoutInflater().inflate(R.layout.geo_infowindow, null);
        myParcelle = MapsMainActivity.dicoMarkerParcelle.get(marker);

        // Infobulle
        TextView title = (TextView)v.findViewById(R.id.geomarker_title);
        TextView subtitle = (TextView) v.findViewById(R.id.geomarker_surface);
        title.setText(myParcelle.getNom());
        subtitle.setText("Surface : " + String.valueOf(myParcelle.getSurface()));

        //Drawer
        TextView name = (TextView) this.myView.findViewById(R.id.dwr_name);
            name.setText(myParcelle.getNom()+"  "+myParcelle.getCepage().getLibelle());
        TextView surface = (TextView) this.myView.findViewById(R.id.dwr_surface);
        String txt_surface = this.myView.getString(R.string.dwr_surface);
            surface.setText(String.valueOf(myParcelle.getSurface())+" "+txt_surface);
        TextView nbroute = (TextView) this.myView.findViewById(R.id.dwr_nbroute);
        String txt_nbroute = this.myView.getString(R.string.dwr_route);
            nbroute.setText(String.valueOf(myParcelle.getNombreroute())+" "+txt_nbroute);
        TextView cepage = (TextView) this.myView.findViewById(R.id.dwr_cepage);
            cepage.setText("Attente");

        Button seetask = (Button)this.myView.findViewById(R.id.dwr_button_task_detail);
            seetask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(myView, TaskActivity.class);
                    i.putExtra("ID", myParcelle.getID());
                    myView.startActivity(i);
                }
            });
        Button addtask = (Button)this.myView.findViewById(R.id.dwr_button_task_add);

        this.myView.open();

        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
