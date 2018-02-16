package la.hackspace.networkingadapter;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    String Resultado;
    Button Mostrar;
    ProgressBar Cargar;
    TextView Descargando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Mostrar = findViewById(R.id.buMostrar);
        Cargar = findViewById(R.id.pbCargar);
        Descargando = findViewById(R.id.tvDescargando);
        new BackgroundTask().execute();

    }

    public void MostrarDatos(View view){
        if (new VerificarInternet(getApplicationContext()).ConexionDisponible()){

            if (Resultado!=null) {
                Intent intent = new Intent(getApplicationContext(), CriptoConcurrencia.class);
                intent.putExtra("json_arreglo", Resultado);
                startActivity(intent);
            }
            else{
                try {
                    Resultado = new BackgroundTask().execute().get();
                    Intent intent = new Intent(getApplicationContext(), CriptoConcurrencia.class);
                    intent.putExtra("json_arreglo", Resultado);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            Toast.makeText(this, "Revise su conexión a internet",Toast.LENGTH_SHORT).show();
        }

    }

    class BackgroundTask extends AsyncTask<Void,Void,String> {

        @Override
        protected String doInBackground(Void... params) {
            OkHttpClient httpClient = new OkHttpClient();
            String url = " https://api.coinmarketcap.com/v1/ticker/";

            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = null;
            try {
                response = httpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                Log.e("Error:", "error in getting response get request okhttp");
            }
            return null;

        }

        @Override
        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(), "Espera un poco",Toast.LENGTH_SHORT).show();
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
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
