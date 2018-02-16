package la.hackspace.multiscreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class EnviarDatos extends AppCompatActivity {

    TextView Nombres,AP,AM,Email,DNI,Telefono;
    ImageView Imagen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviar_datos);

        Nombres = findViewById(R.id.tvNombre);
        AP = findViewById(R.id.tvAP);
        AM = findViewById(R.id.tvAM);
        Email = findViewById(R.id.tvEmail);
        DNI = findViewById(R.id.tvDNI);
        Telefono = findViewById(R.id.tvTelefono);
        Imagen = findViewById(R.id.ivImagen);

        Nombres.setText(getIntent().getExtras().getString("Nombres"));
        AP.setText(getIntent().getExtras().getString("AP"));
        AM.setText(getIntent().getExtras().getString("AM"));
        Email.setText(getIntent().getExtras().getString("Email"));
        DNI.setText(getIntent().getExtras().getString("DNI"));
        Telefono.setText(getIntent().getExtras().getString("Telefono"));


        if(getIntent().getExtras().getString("ImagenURI") != null){
            Log.i("IMAGEN:::",getIntent().getExtras().getString("ImagenURI"));
            Imagen.setImageURI(Uri.parse(getIntent().getExtras().getString("ImagenURI")));
        }
        else{
            Picasso.with(this).load(getIntent().getExtras().getString("ImagenURL")).into(Imagen);
        }

    }
    public void Enviar(View view){
        Drawable d =Imagen.getDrawable();
        BitmapDrawable bitDw = ((BitmapDrawable) d);
        Bitmap bitmap = bitDw.getBitmap();
        File mFile = CrearImagen(bitmap,"Img");



        String Texto= "            " + "Envio de datos" +
                "\n" + "Nombres: " + Nombres.getText().toString() +
                "\n" + "Apellido paterno: " + AP.getText().toString() +
                "\n" + "Apellido materno: " + AM.getText().toString() +
                "\n" + "Correo electrónico: " + Email.getText().toString() +
                "\n" + "DNI: " + DNI.getText().toString() +
                "\n" + "Teléfono: " + Telefono.getText().toString();
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("application/image");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{Email.getText().toString()});
        i.putExtra(Intent.EXTRA_SUBJECT, "Datos de usuario");
        i.putExtra(Intent.EXTRA_TEXT   , Texto);

        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFile));

        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "No hay e-mail del usuario por defecto", Toast.LENGTH_SHORT).show();
        }
    }

    private File CrearImagen(Bitmap bmp, String nombre) {
        String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        OutputStream outStream = null;
        File file = new File(extStorageDirectory, nombre + ".png");
        if (file.exists()) {
            file.delete();
            file = new File(extStorageDirectory, nombre + ".png");
        }

        try {
            outStream = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return file;
    }
}
