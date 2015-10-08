package demax.geoterre.application.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;

/**
 * Created by 2Max on 30/07/2015.
 */
public abstract class DataLoader {
    public static final String SHAP_LOG = "demax.geoterre.views.SHAP_LOG";
    public static final String SHAP_KEY_IDACCOUNT = "SHAP_KEY_IDACCOUNT";
    public static final String SHAP_KEY_ACCESSTOKEN = "SHAP_KEY_ACCESSTOKEN";
    protected static final String filenamesavejson = "datasave.json";

    protected Context myContext;
    protected RequestCallback callback;
    protected NetworkConnection connection;
    protected NetworkInfo networkInfo;
    protected DataLoader loaderOnFinish;
    protected SimpleCallback callback_activity;

    public DataLoader(Context _context)
    {
        this(_context, null);
    }

    public DataLoader(Context _context, DataLoader _loader)
    {
        this.myContext = _context;
        this.loaderOnFinish = _loader;
    }

    public void load()
    {
        ConnectivityManager connMgr = (ConnectivityManager) this.myContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();
        connection = new NetworkConnection(this.callback);
        connection.setHostname("192.168.1.15");
    }

    public void setCallback(SimpleCallback _callback) {
        this.callback_activity = _callback;
    }
}
