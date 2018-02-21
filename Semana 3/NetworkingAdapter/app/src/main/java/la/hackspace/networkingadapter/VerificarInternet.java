package la.hackspace.networkingadapter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Eder on 15/02/2018.
 */

public class VerificarInternet {
    private Context context;

    /**
     * Inicialización de clase
     * @param context Contexto actual
     */
    public VerificarInternet(Context context) {
        this.context = context;
    }

    /**
     * Verifica si existe conexión a internet
     * @return Estado de la conexión a internet
     */
    public boolean ConexionDisponible() {
        //Inicialización de varibles de OkHttp
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        //Verificación de conexión
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }
}
