package demax.geoterre.application.network;

import java.util.Map;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataJSON implements DataType {

	@Override
	public Map<String, String> convertizeToType(String chaine) {
		JSONObject parser = null;
		Map<String, String> list = new TreeMap<String, String>();
		try {
			parser = new JSONObject(chaine);
			JSONObject obj = parser.getJSONArray("root").getJSONObject(0);

			list.put("response", String.valueOf(obj.getBoolean("response")));

			if(obj.length() > 1)
			{
				JSONArray arr = obj.getJSONArray("0");
				for(int i = 0; i<arr.length(); i++) {
					JSONObject item = arr.getJSONObject(i);
					this.fillList(list, item);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	private void fillList(Map<String, String> list, JSONObject item)
	{
		try {
			if(!item.isNull("IDTache"))
				list.put("IDTache", item.getString("IDTache"));

			if(!item.isNull("libelle"))
				list.put("libelle", item.getString("libelle"));

			if(!item.isNull("datedebut"))
				list.put("datedebut", item.getString("datedebut"));

			if(!item.isNull("datefin"))
				list.put("datefin", item.getString("datefin"));

			if(!item.isNull("commentaire"))
				list.put("commentaire", item.getString("commentaire"));

			if(!item.isNull("IDParcelle"))
				list.put("IDParcelle", item.getString("IDParcelle"));

			if(!item.isNull("nom"))
				list.put("nom", item.getString("nom"));

			if(!item.isNull("IDAccount"))
				list.put("IDAccount", item.getString("IDAccount"));

			if(!item.isNull("accesstoken"))
				list.put("accesstoken", item.getString("accesstoken"));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String convertizeToString(String chaine) {
		// TODO Auto-generated method stub
		return null;
	}

}
