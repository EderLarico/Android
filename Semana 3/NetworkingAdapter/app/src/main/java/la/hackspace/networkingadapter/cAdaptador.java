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
    //Inicialización de adaptador
    private List<cMoneda> mDataset;

    //Inicialización de contexto
    private Context context;

    /**
     * Designación de ViewHolder para obtención de datos
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //region Inicialización de variables
        public TextView Nombre;
        public TextView Precio;
        public TextView PrecioDia;
        public TextView PrecioSemana;
        public ImageView Icono;
        //endregion Inicialización de variables

        public ViewHolder(View v) {
            super(v);
            //region Vinculación de variables
            Nombre= v.findViewById(R.id.tvNombre);
            Precio= v.findViewById(R.id.tvPrecio);
            PrecioDia= v.findViewById(R.id.tvPrecioDia);
            PrecioSemana= v.findViewById(R.id.tvPrecioSemana);
            Icono= v.findViewById(R.id.ivIcono);
            //endregion Vinculación de variables
        }
    }

    /**
     * Vinculación de adpatador y contexto
     * @param Info Lista de datos por cada moneda
     * @param context Contexto actual
     */
    cAdaptador(List<cMoneda> Info,Context context) {
        this.mDataset = Info;
        this.context = context;
    }

    /**
     * Rellenar datos de cada fila
     * @param holder ViewHolder de la fila en la posición actual
     * @param position Posición actual
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Obtención de datos de moneda en la posición actual
        cMoneda Moneda= this.mDataset.get(position);

        //region Escritura de datos en cada fila
        holder.Nombre.setText(Moneda.getNombre());
        holder.Precio.setText(Moneda.getPrecio());

        //Escritura de datos de acuerdo a la variación diaria
        if(Moneda.getInfoDia()){
            holder.PrecioDia.setText(context.getResources().getText(R.string.NULL));
        }
        else {
            if (Moneda.getRojoDia()) {holder.PrecioDia.setTextColor(context.getResources().getColor(R.color.Rojo));}
            else {holder.PrecioDia.setTextColor(context.getResources().getColor(R.color.VerdeOscuro));}
            holder.PrecioDia.setText(Moneda.getPrecioDia() + "%");
        }

        //Escritura de datos de acuerdo a la variación semanal
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
        //endregion Escritura de datos en cada fila
    }

    /**
     * Obtener el tamaño de la lista
     * @return Tamaño de lista
     */
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Inicialización, vinculación y creación de vista
     * @param parent Vista padre
     * @param ViewType Tipo de vista
     * @return Vista creada
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
        //Obtención e inicialización del contexto actual
        Context context = parent.getContext();
        //Inicialización y designación de inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        //Inicialización y designación de vista
        View view = inflater.inflate(R.layout.fila_crypto, parent, false);

        return new ViewHolder(view);
    }

    /**
     * Limpiar la lista
     */
    public void NuevaLista(){
        mDataset.clear();
    }

}
