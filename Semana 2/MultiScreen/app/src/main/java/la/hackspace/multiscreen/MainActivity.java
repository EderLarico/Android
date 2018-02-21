package la.hackspace.multiscreen;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //region Inicialización de variables
    EditText Nombres,AP,AM,Email,DNI,Telefono,URL;
    Boolean BImagen;
    ImageView Imagen;
    Uri Direccion;
    //endregion Inicialización de variables

    //Código de busqueda de imagen
    final int SELECT_PICTURE = 1;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //region Vinculación de datos
        Nombres = findViewById(R.id.etNombre);
        AP = findViewById(R.id.etAP);
        AM = findViewById(R.id.etAM);
        Email = findViewById(R.id.etEmail);
        DNI = findViewById(R.id.etDNI);
        Telefono = findViewById(R.id.etTelefono);
        Imagen = findViewById(R.id.ivImagen);
        URL = findViewById(R.id.etURL);
        BImagen = false;
        //endregion Vinculación de datos

        //Obtención de imagen online
        URL.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Direccion = null;
                Imagen.setImageResource(R.drawable.iconosimple);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                BImagen=true;
            }
        });
    }

    /**
     * Búsqueda de imagen local
     * @param view Vista(padre)
     */
    public  void IngresarImagen(View view){
        URL.setText("");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PICTURE);
    }

    /**
     * Obtención de imagen local
     * @param requestCode Código de búsqueda
     * @param resultCode Código de resultado de búsqueda
     * @param data Dato resultante
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECT_PICTURE) {
            if (resultCode == RESULT_OK) {
                Direccion = null;
                Direccion = data.getData();
                Imagen.setImageURI(Direccion);
                BImagen = true;
            }
        }
    }

    /**
     * Envio de datos a otra actividad
     * @param view Vista(padre)
     */
    public void SiguienteActividad(View view ){
        //Validación de internet
        if(ConexionDisponible(view.getContext())) {
            //region Validación de datos
            if (Nombres.getText().toString().equals("")) {
                Toast.makeText(this, "Necesita ingresar su nombre", Toast.LENGTH_SHORT).show();
            } else if (AP.getText().toString().equals("")) {
                Toast.makeText(this, "Necesita ingresar su apellido paterno", Toast.LENGTH_SHORT).show();
            } else if (AM.getText().toString().equals("")) {
                Toast.makeText(this, "Necesita ingresar su apellido materno", Toast.LENGTH_SHORT).show();
            } else if (Email.getText().toString().equals("")) {
                Toast.makeText(this, "Necesita ingresar su Email", Toast.LENGTH_SHORT).show();
            } else if (DNI.getText().toString().equals("")) {
                Toast.makeText(this, "Necesita ingresar su DNI", Toast.LENGTH_SHORT).show();
            } else if (Telefono.getText().toString().equals("")) {
                Toast.makeText(this, "Necesita ingresar su teléfono", Toast.LENGTH_SHORT).show();
            } else if (!BImagen) {
                Toast.makeText(this, "Necesita ingresar su imagen", Toast.LENGTH_SHORT).show();
            }
            //endregion Validación de datos
            else {
                //region Creación de nueva actividad e incorporación de datos
                Intent Enviar = new Intent(getApplicationContext(), EnviarDatos.class);
                Enviar.putExtra("Nombres", Nombres.getText().toString());
                Enviar.putExtra("AP", AP.getText().toString());
                Enviar.putExtra("AM", AM.getText().toString());
                Enviar.putExtra("Email", Email.getText().toString());
                Enviar.putExtra("DNI", DNI.getText().toString());
                Enviar.putExtra("Telefono", Telefono.getText().toString());
                if (Direccion != null) {
                    Enviar.putExtra("ImagenURI", Direccion.toString());
                } else {
                    Enviar.putExtra("ImagenURL", URL.getText().toString().toString());
                }
                startActivity(Enviar);
                //endregion Creación de nueva actividad e incorporación de datos
            }
        }
        else {
            Toast.makeText(view.getContext(),"Verifica tu conexión a internet", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean ConexionDisponible(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}
