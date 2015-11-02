package demax.geoterre.application.network;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import demax.geoterre.application.datas.Parcelle;
import demax.geoterre.application.models.ListParcelle;
import demax.geoterre.views.MapsMainActivity;

/**
 * Created by 2Max on 30/07/2015.
 */
public class ParcelleLoader extends DataLoader {

    private SharedPreferences shrpref;
    private boolean dataIsSaving = false;

    public ParcelleLoader(Context _context) {
        super(_context);
        this.callback = new MyCallback();
        this.shrpref = myContext.getSharedPreferences(SHAP_LOG, Context.MODE_PRIVATE);
    }

    @Override
    public void load()
    {
        if(!read()) {
            super.load();
            if (networkInfo != null && networkInfo.isConnected()) {
                connection.getMyRequest().setFile("/projets/ServeurVignici/camille/parcelle/" + String.valueOf(shrpref.getInt(SHAP_KEY_IDACCOUNT, 0)));
                connection.getMyRequest().setMethod(Request.RequestMethod.GET);
                connection.getMyRequest().addHeaders("Content-type", "application/json");
                connection.getMyRequest().addHeaders("Authorization", shrpref.getString(SHAP_KEY_ACCESSTOKEN, ""));
                connection.execute();
            }
        }
    }

    private void save(String jsonbrut)
    {
        File cacheFile = new File(myContext.getFilesDir(), this.filenamesavejson);
        try {
            FileWriter fw = new FileWriter(cacheFile);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(jsonbrut);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean read()
    {
        // Test pour éviter persistence
        return false;
/*
        BufferedReader bufferedReader = null;
        StringBuilder sb = null;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(myContext.openFileInput(this.filenamesavejson)));
            sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            dataIsSaving = true;
            this.callback.call(new Response(sb.toString(), Response.TYPE.ONLY_DATA));
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
        */
    }

    /**
     * Parse JSON and add Parcelle to ListParcelle
     * @param datas
     */
    public void decompoze(String datas) throws JSONException {
        ListParcelle list = ListParcelle.getInstance();
        JSONObject jso = new JSONObject(datas);
        JSONArray array = jso.getJSONArray("root");
        JSONArray values = array.getJSONObject(0).getJSONArray("0");

        for (int i = 0; i < values.length(); i++) {
            ArrayList<LatLng> myArrayCoord = new ArrayList<LatLng>();
            String[] tabCoord = values.getJSONObject(i).getString("coordonnees").split(";;");
            for (int j = 0; j < tabCoord.length; j++) {
                String[] tabCoordPoint = tabCoord[j].split(";");
                myArrayCoord.add(new LatLng(Double.valueOf(tabCoordPoint[0]), Double.valueOf(tabCoordPoint[1])));
            }

            Parcelle p = new Parcelle(values.getJSONObject(i).getInt("IDParcelle"),
                    values.getJSONObject(i).getString("nom"),
                    values.getJSONObject(i).getDouble("surface"),
                    values.getJSONObject(i).getInt("nombreroute"),
                    myArrayCoord);
            list.add(p);
        }
    }

    public class MyCallback implements RequestCallback
    {
        @Override
        public void call(Response myResponse) {
            try {
                decompoze(myResponse.getDataBrut());
                if(!dataIsSaving)
                    save(myResponse.getDataBrut());
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(myContext, "Error load Parcelle"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
            this.onFinish();
        }

        @Override
        public void onFinish() {
            callback_activity.call();
        }
    }
}
