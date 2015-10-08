package demax.geoterre.application.network;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Request {

	public enum RequestMethod {
		GET, POST, PUT, DELETE
	}
	
	private Map<String, String> headers;
	private String datas; // Data in JSON format
	private RequestMethod method;
	private String file;
	private String host;
	
	public Request(String _file, RequestMethod _method, String _datas, String _host)
	{
		headers = new HashMap<String, String>();
		file = _file;
		method = _method;
		datas = _datas;
		host = _host;
	}
	
	public String getRequest()
	{
		StringBuilder request = new StringBuilder();
		// First line
		request.append(this.method+" "+this.file+" HTTP/1.1\n");
		request.append("Host: "+this.host+"\n");
		
		// Add headers
		for(Entry<String, String> entry : this.headers.entrySet())
			request.append(entry.getKey()+": "+entry.getValue()+"\n");

		request.append("\n");

		request.append(this.datas);

		return request.toString();
	}
	
	public void setDatas(String datas) {
		this.datas = datas;
	}

	public void setMethod(RequestMethod method) {
		this.method = method;
	}
	
	public void setHost(String _host) {
		this.host = _host;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public void addHeaders(String libelle, String value) {
		this.headers.put(libelle, value);
	}
}
