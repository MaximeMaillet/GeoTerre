package demax.geoterre.application.network;

import java.util.Map;

public interface DataType {

	Map<String, String> convertizeToType(String chaine);
	String convertizeToString(String chaine);
}
