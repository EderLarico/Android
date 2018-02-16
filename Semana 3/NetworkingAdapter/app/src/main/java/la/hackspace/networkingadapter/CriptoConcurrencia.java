package la.hackspace.networkingadapter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CriptoConcurrencia extends AppCompatActivity {

    String Resultado;
    JSONArray Listado;
    String Imagen,Nombre,Precio,PrecioDia,PrecioSemana;
    cAdaptador Adaptador;
    ListView ListaMonedas;
    Button Actualizar;
    ProgressBar pbActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cripto_concurrencia);

        Actualizar = findViewById(R.id.buActualizar);
        pbActualizar = findViewById(R.id.pbActualizar);

        //Inicializar atributos de la lista
        Adaptador =new cAdaptador(this, R.layout.fila_crypto);
        ListaMonedas = findViewById(R.id.lvCriptoCoins);

        //Inicializar lista
        ListaMonedas.setAdapter(Adaptador);

        Resultado = getIntent().getExtras().getString("json_arreglo");

        try {
            Listado = new JSONArray(Resultado);
            int count=0;

            while(count<Listado.length()){
                JSONObject JO=Listado.getJSONObject(count);
                Imagen=JO.getString("symbol").toLowerCase();
                Nombre = JO.getString("symbol") + "|" + JO.getString("name");
                Precio= JO.getString("price_usd");
                PrecioDia= JO.getString("percent_change_24h");
                PrecioSemana= JO.getString("percent_change_7d");

                cMoneda CMoneda=new cMoneda(Imagen,Nombre,Precio,PrecioDia,PrecioSemana);
                Adaptador.add(CMoneda);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void Actualizar(View view){
        try {
            new BackgroundTask().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
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
            super.onPreExecute();
            pbActualizar.setVisibility(View.VISIBLE);
            Actualizar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String result) {
            Adaptador.NuevaLista();
            Resultado = result;
            try {
                Listado = new JSONArray(Resultado);
                int count=0;

                while(count<Listado.length()){
                    JSONObject JO=Listado.getJSONObject(count);
                    Imagen=JO.getString("symbol").toLowerCase();
                    Nombre = JO.getString("symbol") + "|" + JO.getString("name");
                    Precio= JO.getString("price_usd");
                    PrecioDia= JO.getString("percent_change_24h");
                    PrecioSemana= JO.getString("percent_change_7d");

                    cMoneda CMoneda=new cMoneda(Imagen,Nombre,Precio,PrecioDia,PrecioSemana);
                    Adaptador.add(CMoneda);
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pbActualizar.setVisibility(View.GONE);
            Actualizar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
    }
}
