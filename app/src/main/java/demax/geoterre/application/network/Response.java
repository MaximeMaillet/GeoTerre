package demax.geoterre.application.network;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.TreeMap;

import demax.geoterre.application.network.DataJSON;

/**
 * Created by 2Max on 27/07/2015.
 */
public class Response {
    private String state;
    private String responseBrut;
    private String databrut;
    private Map<String, String> params;
    private Map<String, String> headers;

    public Response(String _databrut)
    {
        this(_databrut, TYPE.ALL);
    }

    public Response(String _response, TYPE _type)
    {
        if(_type == TYPE.ALL) {
            this.responseBrut = _response;
            this.headers = new TreeMap<String, String>();
            this.params = new TreeMap<String, String>();
            this.decompoze();
        }
        else if(_type == TYPE.ONLY_DATA)
            this.convertData(_response);

    }

    public String getParams(String key) {
        return this.params.get(key);
    }

    public String getHeaders(String key) {
        return this.headers.get(key);
    }

    private void decompoze()
    {
        String[] reponseTab = this.responseBrut.split("\\n");
        this.state = reponseTab[0];
        int i = 0;
        while(!reponseTab[i].equals("")) {
            if(reponseTab[i].contains(":"))
            {
                String[] tabline = reponseTab[i].split(":");
                this.headers.put(tabline[0], tabline[1]);
            }
            i++;
        }

        this.convertData(reponseTab[i+1]);
    }

    private void convertData(String data)
    {
        this.databrut = data;
        DataType type = new DataJSON();
        this.params = type.convertizeToType(this.databrut);
    }

    public String getBrut()
    {
        return this.responseBrut;
    }
    public String getDataBrut() { return this.databrut; }

    public enum TYPE {
        ALL, ONLY_DATA
    }
}
