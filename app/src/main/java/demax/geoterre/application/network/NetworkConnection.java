package demax.geoterre.application.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import android.os.AsyncTask;

public class NetworkConnection extends AsyncTask<Void, Void, Response> {

	private String hostname = "192.168.1.15";
	private int port = 80;
	private Request myRequest;
	private Response myResponse;
	private RequestCallback function;

	public NetworkConnection(RequestCallback fct)
	{
		myRequest = new Request(null, null, null, null);
		this.function = fct;
	}

	@Override
	protected Response doInBackground(Void... params) {

		Socket mySocket = null;
		PrintWriter toServer= null;
		BufferedReader fromServer = null;
		StringBuilder fullResponse = new StringBuilder();
		
		try {
			mySocket = new Socket(this.hostname, this.port);
		
			fromServer =  new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
			toServer = new PrintWriter(new OutputStreamWriter(mySocket.getOutputStream()));

			toServer.print(myRequest.getRequest());
			toServer.flush();
			
			String line;
			while((line = fromServer.readLine()) != null)
				fullResponse.append(line+"\n");

			myResponse = new Response(fullResponse.toString());
			return myResponse;
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally {
			try {
				mySocket.close();
				toServer.close();
				fromServer.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return myResponse;
	}
	
	@Override
	protected void onPostExecute(Response result) {
		// TODO Auto-generated method stub
		this.function.call(result);
	}
	
	public void setHostname(String hostname) {
		this.hostname = hostname;
		this.myRequest.setHost(hostname);
	}

	public Response getMyResponse() {
		return myResponse;
	}
	
	public Request getMyRequest() {
		return myRequest;
	}
}
