package demax.geoterre.application.network;

import demax.geoterre.application.network.Response;

/**
 * Created by 2Max on 29/07/2015.
 */
public interface RequestCallback {
    void call(Response myResponse);
    void onFinish();
}
