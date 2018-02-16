package la.hackspace.networkingadapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eder on 14/02/2018.
 */

public class cAdaptador extends ArrayAdapter{
    List lista = new ArrayList();
    Context context;

    public cAdaptador(@NonNull Context context, int resource) {
        super(context, resource);
        this.context = context;
    }

    public void NuevaLista(){
        lista.clear();
    }

    public void add(cMoneda object) {
        super.add(object);
        lista.add(object);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row ;
        row=convertView;
        FilaTemporal CoinHolder;

        if(row ==null){
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.fila_crypto,parent,false);
            CoinHolder=new FilaTemporal();

            CoinHolder.Nombre= row.findViewById(R.id.tvNombre);
            CoinHolder.Precio= row.findViewById(R.id.tvPrecio);
            CoinHolder.PrecioDia= row.findViewById(R.id.tvPrecioDia);
            CoinHolder.PrecioSemana= row.findViewById(R.id.tvPrecioSemana);
            CoinHolder.Icono= row.findViewById(R.id.ivIcono);

            row.setTag(CoinHolder);
        }else{
            CoinHolder=(FilaTemporal)row.getTag();
        }

        cMoneda Moneda=(cMoneda) this.getItem(position);

        CoinHolder.Nombre.setText(Moneda.getNombre());
        CoinHolder.Precio.setText(Moneda.getPrecio());

        if(Moneda.getInfoDia()){
            CoinHolder.PrecioDia.setText(parent.getResources().getText(R.string.NULL));
        }
        else {
            if (Moneda.getRojoDia()) {CoinHolder.PrecioDia.setTextColor(parent.getResources().getColor(R.color.Rojo));}
            else {CoinHolder.PrecioDia.setTextColor(parent.getResources().getColor(R.color.VerdeOscuro));}
            CoinHolder.PrecioDia.setText(Moneda.getPrecioDia() + "%");
        }
        if(Moneda.getInfoSemana()){
            CoinHolder.PrecioSemana.setText(parent.getResources().getText(R.string.NULL));
        }
        else {
            if (Moneda.getRojoSemana()) {CoinHolder.PrecioSemana.setTextColor(parent.getResources().getColor(R.color.Rojo));}
            else {CoinHolder.PrecioSemana.setTextColor(parent.getResources().getColor(R.color.VerdeOscuro));}
            CoinHolder.PrecioSemana.setText(Moneda.getPrecioSemana() + "%");
        }

        int id = context.getResources().getIdentifier("drawable/"+Moneda.getImagen(), null, context.getPackageName());
        CoinHolder.Icono.setImageResource(id);

        return row;
    }

    static class FilaTemporal{
        ImageView Icono;
        TextView Nombre,Precio,PrecioDia,PrecioSemana;
    }
}
