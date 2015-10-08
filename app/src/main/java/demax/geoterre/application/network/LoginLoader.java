package demax.geoterre.application.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import org.json.JSONObject;

/**
 * Created by 2Max on 30/07/2015.
 */
public class LoginLoader extends DataLoader {

    private JSONObject datas;
    private SharedPreferences shrpref;

    public LoginLoader(Context _context, DataLoader _loader)
    {
        super(_context, _loader);
        this.shrpref = myContext.getSharedPreferences(SHAP_LOG, Context.MODE_PRIVATE);
        this.callback = new MyCallback();
    }

    @Override
    public void load() {

        if(shrpref.getInt(SHAP_KEY_IDACCOUNT, -1) == -1) {
            super.load();

            if (networkInfo != null && networkInfo.isConnected()) {
                connection.getMyRequest().setFile("/projets/ServeurVignici/camille/member/");
                connection.getMyRequest().setMethod(Request.RequestMethod.POST);
                connection.getMyRequest().addHeaders("Content-type", "application/json");
                connection.getMyRequest().addHeaders("Content-Length", String.valueOf(datas.toString().length()));
                connection.getMyRequest().addHeaders("Authorization", "Connect");
                connection.getMyRequest().setDatas(datas.toString());
                connection.execute();
            }
        }
        else {
            this.callback.onFinish();
        }
    }

    public void setDatas(JSONObject _datas) {
        this.datas = _datas;
    }

    public class MyCallback implements RequestCallback {
        @Override
        public void call(Response myResponse) {
            SharedPreferences.Editor editor_log = shrpref.edit();
            editor_log.putInt(SHAP_KEY_IDACCOUNT, Integer.parseInt(myResponse.getParams("IDAccount")));
            editor_log.putString(SHAP_KEY_ACCESSTOKEN, myResponse.getParams("accesstoken"));
            editor_log.commit();
            this.onFinish();
        }

        @Override
        public void onFinish() {
            loaderOnFinish.load();
        }
    };
}
