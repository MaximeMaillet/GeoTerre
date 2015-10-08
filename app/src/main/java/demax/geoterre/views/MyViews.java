package demax.geoterre.views;

import android.app.Activity;

import demax.geoterre.application.network.Response;

public abstract class MyViews extends Activity {

	abstract public void exec(Response myObj);
}
