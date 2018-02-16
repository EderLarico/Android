package la.hackspace.multiscreen;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Nombres,AP,AM,Email,DNI,Telefono,URL;
    Boolean BImagen;
    ImageView Imagen;
    Uri Direccion;
    final int SELECT_PICTURE = 1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Nombres = findViewById(R.id.etNombre);
        AP = findViewById(R.id.etAP);
        AM = findViewById(R.id.etAM);
        Email = findViewById(R.id.etEmail);
        DNI = findViewById(R.id.etDNI);
        Telefono = findViewById(R.id.etTelefono);
        Imagen = findViewById(R.id.ivImagen);
        URL = findViewById(R.id.etURL);
        BImagen = false;

        URL.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Direccion = null;
                Imagen.setImageResource(R.drawable.iconosimple);
                return false;
            }
        });
    }

    public  void IngresarImagen(View view){
        URL.setText("");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PICTURE);
    }

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

    public void SiguienteActividad(View view ){
        if(URL.getText().equals("")){BImagen=false;}
        else {BImagen=true;}
        if(Nombres.getText().toString().equals("")){Toast.makeText(this,"Necesita ingresar su nombre",Toast.LENGTH_SHORT).show();}
        else if(AP.getText().toString().equals("")){Toast.makeText(this,"Necesita ingresar su apellido paterno",Toast.LENGTH_SHORT).show();}
        else if(AM.getText().toString().equals("")){Toast.makeText(this,"Necesita ingresar su apellido materno",Toast.LENGTH_SHORT).show();}
        else if(Email.getText().toString().equals("")){Toast.makeText(this,"Necesita ingresar su Email",Toast.LENGTH_SHORT).show();}
        else if(DNI.getText().toString().equals("")){Toast.makeText(this,"Necesita ingresar su DNI",Toast.LENGTH_SHORT).show();}
        else if(Telefono.getText().toString().equals("")){Toast.makeText(this,"Necesita ingresar su teléfono",Toast.LENGTH_SHORT).show();}
        else if(!BImagen ){Toast.makeText(this,"Necesita ingresar su imagen",Toast.LENGTH_SHORT).show();}
        else {
            Intent Enviar = new Intent(getApplicationContext(), EnviarDatos.class);
            Enviar.putExtra("Nombres", Nombres.getText().toString());
            Enviar.putExtra("AP", AP.getText().toString());
            Enviar.putExtra("AM", AM.getText().toString());
            Enviar.putExtra("Email", Email.getText().toString());
            Enviar.putExtra("DNI", DNI.getText().toString());
            Enviar.putExtra("Telefono", Telefono.getText().toString());
            if(Direccion!=null){Enviar.putExtra("ImagenURI",Direccion.toString());}
            else{Enviar.putExtra("ImagenURL",URL.getText().toString().toString());}
            startActivity(Enviar);
        }
    }



}
