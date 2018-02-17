package la.hackspace.networkingadapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

/**
 * Created by Eder on 14/02/2018.
 */

public class cAdaptador extends RecyclerView.Adapter<cAdaptador.ViewHolder>{
    private List<cMoneda> mDataset;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView Nombre;
        public TextView Precio;
        public TextView PrecioDia;
        public TextView PrecioSemana;
        public ImageView Icono;
        public ViewHolder(View v) {
            super(v);
            Nombre= v.findViewById(R.id.tvNombre);
            Precio= v.findViewById(R.id.tvPrecio);
            PrecioDia= v.findViewById(R.id.tvPrecioDia);
            PrecioSemana= v.findViewById(R.id.tvPrecioSemana);
            Icono= v.findViewById(R.id.ivIcono);
        }
    }

    cAdaptador(List<cMoneda> Info,Context context) {
        this.mDataset = Info;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        cMoneda Moneda= this.mDataset.get(position);

        holder.Nombre.setText(Moneda.getNombre());
        holder.Precio.setText(Moneda.getPrecio());

        if(Moneda.getInfoDia()){
            holder.PrecioDia.setText(context.getResources().getText(R.string.NULL));
        }
        else {
            if (Moneda.getRojoDia()) {holder.PrecioDia.setTextColor(context.getResources().getColor(R.color.Rojo));}
            else {holder.PrecioDia.setTextColor(context.getResources().getColor(R.color.VerdeOscuro));}
            holder.PrecioDia.setText(Moneda.getPrecioDia() + "%");
        }
        if(Moneda.getInfoSemana()){
            holder.PrecioSemana.setText(context.getResources().getText(R.string.NULL));
        }
        else {
            if (Moneda.getRojoSemana()) { holder.PrecioSemana.setTextColor(context.getResources().getColor(R.color.Rojo));}
            else { holder.PrecioSemana.setTextColor(context.getResources().getColor(R.color.VerdeOscuro));}
            holder.PrecioSemana.setText(Moneda.getPrecioSemana() + "%");
        }
        int id = context.getResources().getIdentifier("drawable/"+Moneda.getImagen(), null, context.getPackageName());
        holder.Icono.setImageResource(id);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fila_crypto, parent, false);

        return new ViewHolder(view);
    }

    public void NuevaLista(){
        mDataset.clear();
    }

}
