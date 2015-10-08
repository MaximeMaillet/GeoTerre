package demax.geoterre.views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.List;
import java.util.Map;

import demax.geoterre.R;
import demax.geoterre.application.datas.Parcelle;
import demax.geoterre.application.models.ListParcelle;

public class MapFragment extends Fragment {

    private MapView mMapView;
    private GoogleMap myMap;
    private MapsMainActivity map;

    public void setMap(MapsMainActivity map) {
        this.map = map;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) v.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();// needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        myMap = mMapView.getMap();
        myMap.getUiSettings().setZoomGesturesEnabled(true);
        myMap.setMyLocationEnabled(true);
        myMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        drawParcelle();

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(50.060121,3.768325)).zoom(16).build();
        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        // Perform any camera updates here
        return v;
    }

    public LatLng getLocation()
    {
        LocationManager locationManager = (LocationManager) map.getSystemService(Context.LOCATION_SERVICE);
        MyLocationListener mLL = new MyLocationListener();
        LocationListener locationListener = mLL;
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

        if (locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER )) {
            return mLL.getLatLng();
        }
        else
            return new LatLng(0,0);

    }

    /**
     * Permet de dessiner les parcelles
     */
    public void drawParcelle() {
        for (Parcelle p : ListParcelle.getInstance()) {
            Marker m = myMap.addMarker(new MarkerOptions()
                    .position(p.getCoordonnes().get(0))
                    .title(p.getNom())
                    .snippet("Surface: " + p.getSurface() + "m")
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            myMap.addPolygon(new PolygonOptions()
                    .add(p.getCoordonnes().get(0), p.getCoordonnes().get(1), p.getCoordonnes().get(2), p.getCoordonnes().get(3))
                    .fillColor(Color.argb(5, 0, 0, 255))
                    .strokeWidth(2));

            myMap.setInfoWindowAdapter(new GeoWindowAdapter(this.map));
            MapsMainActivity.dicoMarkerParcelle.put(m, p);
        }
    }

    public void activeScrolling(boolean active)
    {
        myMap.getUiSettings().setTiltGesturesEnabled(active);
    }

    private class MyLocationListener implements LocationListener {

        private LatLng location;

        public LatLng getLatLng() {
            return this.location;
        }

        @Override
        public void onLocationChanged(Location loc) {
            this.location = new LatLng(loc.getLatitude(),loc.getLongitude());
        }

        @Override
        public void onProviderDisabled(String provider) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    }
}
