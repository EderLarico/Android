package la.hackspace.networkingadapter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CriptoConcurrencia extends AppCompatActivity {

    String Resultado;
    JSONArray Listado;
    String Imagen,Nombre,Precio,PrecioDia,PrecioSemana;
    cAdaptador Adaptador;
    RecyclerView ListaMonedas;
    Button Actualizar;
    ProgressBar pbActualizar;
    RecyclerView.LayoutManager mLayoutManager;
    List<cMoneda> Texto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cripto_concurrencia);

        Actualizar = findViewById(R.id.buActualizar);
        pbActualizar = findViewById(R.id.pbActualizar);
        Texto = new ArrayList<>();
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

                Texto.add(CMoneda);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Adaptador = new cAdaptador(Texto,this);
        mLayoutManager = new LinearLayoutManager(this);

        //Inicializar atributos de la lista
        ListaMonedas = findViewById(R.id.rvCriptoCoins);
        ListaMonedas.setLayoutManager(mLayoutManager);

        //Inicializar lista
        ListaMonedas.setAdapter(Adaptador);

    }

    public void Actualizar(View view){
        new BackgroundTask().execute();
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
            Texto.clear();
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
                    Texto.add(CMoneda);
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
