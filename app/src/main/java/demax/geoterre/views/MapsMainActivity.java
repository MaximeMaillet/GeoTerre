package demax.geoterre.views;

        import android.app.Activity;
        import android.app.FragmentManager;
        import android.app.FragmentTransaction;
        import android.content.Context;
        import android.location.LocationManager;
        import android.os.Bundle;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v4.app.Fragment;
        import android.view.Gravity;
        import android.view.Menu;
        import android.view.View;
        import android.widget.ArrayAdapter;
        import android.widget.LinearLayout;
        import android.widget.ListView;
        import android.widget.TextView;
        import com.google.android.gms.maps.model.Marker;
        import java.util.HashMap;
        import java.util.Map;

        import demax.geoterre.R;
        import demax.geoterre.application.datas.Parcelle;
        import demax.geoterre.application.models.ListParcelle;
        import demax.geoterre.application.network.Response;

public class MapsMainActivity extends Activity {

    private TextView textTest;
    //
    //
    private MapFragment map;
    public static Map<Marker, Parcelle> dicoMarkerParcelle = new HashMap<Marker, Parcelle>();
    private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerLinearLayoutRight;
    private LinearLayout mDrawerLinearLayoutLeft;
    private ListView mDrawerList;
    private ListView mDrawerList2;
    private String[] mPlanetTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_main);

        // ------------
        textTest = (TextView) findViewById(R.id.textView);
        Parcelle p = ListParcelle.getInstance().getParcelleByName("vigne");
        textTest.setText(ListParcelle.getInstance().size() + " --" + p.getCoordonnes().get(0).toString());
        // -------------

        // Create map fragment
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        this.map = new MapFragment();
        this.map.setMap(this);
        fragmentTransaction.add(R.id.myMapFragment, this.map);
        fragmentTransaction.commit();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLinearLayoutLeft = (LinearLayout) findViewById(R.id.drawer_left);
        mDrawerLinearLayoutRight = (LinearLayout) findViewById(R.id.drawer_right);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mDrawerLinearLayoutRight);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        mDrawerLayout.setDrawerListener(new myDrawerListener());

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps_main, menu);
        return true;
    }

    /**
     * Create map fragment
     * @return void
     */
    public void createMap()
    {
    }

    /**
     * Draw DrawerLayout (Left and Right)
     * @return
     */
    public void drawDrawers()
    {

    }

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }
    public void open() { this.getmDrawerLayout().openDrawer(Gravity.RIGHT); }

    /**
     * My Drawer Listener
     * For close right drawer and lock map
     */
    private class myDrawerListener implements DrawerLayout.DrawerListener
    {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            map.activeScrolling(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, mDrawerLinearLayoutRight);
        }

        @Override
        public void onDrawerClosed(View drawerView) {
            map.activeScrolling(true);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, mDrawerLinearLayoutRight);
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    }
}
