package la.hackspace.networkingadapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Eder on 15/02/2018.
 */

public class VerificarInternet {
    private Context context;

    public VerificarInternet(Context context) {
        this.context = context;
    }

    public boolean ConexionDisponible() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
