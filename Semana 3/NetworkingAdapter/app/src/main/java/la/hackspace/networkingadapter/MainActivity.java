package la.hackspace.networkingadapter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    //region Inicialización de variables
    String Resultado;
    Button Mostrar;
    ProgressBar Cargar;
    TextView Descargando;
    //endregion Inicialización de variables

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Vinculación de variables
        Mostrar = findViewById(R.id.buMostrar);
        Cargar = findViewById(R.id.pbCargar);
        Descargando = findViewById(R.id.tvDescargando);
        //endregion Vinculación de variables

        //Obtención de datos
        new BackgroundTask(this.getApplicationContext()).execute();

    }

    /**
     * Ingreso de datos y creación de nueva actividad
     * @param view Vista(padre)
     */
    public void MostrarDatos(View view){
        //Verificar conexión a internet
        if (new VerificarInternet(getApplicationContext()).ConexionDisponible()){

            if (Resultado!=null) {
                //Inicialización e ingreso de datos sobre nueva actividad
                Intent intent = new Intent(getApplicationContext(), CriptoConcurrencia.class);
                intent.putExtra("json_arreglo", Resultado);
                startActivity(intent);
            }
            else{
                //Inicialización e ingreso de datos sobre nueva actividad
                new BackgroundTask(this.getApplicationContext()).execute();
                Intent intent = new Intent(getApplicationContext(), CriptoConcurrencia.class);
                intent.putExtra("json_arreglo", Resultado);
                startActivity(intent);

            }
        }
        else{
            Toast.makeText(this, "Revise su conexión a internet",Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Método para obtener datos de criptomonedas
     */
    class BackgroundTask extends AsyncTask<Void,Void,String> {
        Context context;
        public BackgroundTask(Context ctx) {
            this.context = ctx;
        }

        @Override
        protected String doInBackground(Void... params) {

            //Declaración de variables OkHttp
            OkHttpClient httpClient = new OkHttpClient();
            String url = " https://api.coinmarketcap.com/v1/ticker/";

            //Creación de pedido
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            //Ejecución de pedido
            Response response = null;
            try {
                response = httpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                Toast.makeText(context,"No se pudo obtener datos del servidor, inténtalo más tarder",Toast.LENGTH_SHORT).show();
            }
            return null;

        }

        @Override
        protected void onPreExecute() {
            //Actualización de botones
            Mostrar.setVisibility(View.GONE);
            Cargar.setVisibility(View.VISIBLE);
            Descargando.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            //Obtención de resultado y actualización de botones
            Resultado=result;
            Mostrar.setVisibility(View.VISIBLE);
            Cargar.setVisibility(View.GONE);
            Descargando.setVisibility(View.GONE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
