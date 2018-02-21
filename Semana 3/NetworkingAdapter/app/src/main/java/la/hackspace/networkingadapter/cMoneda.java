package la.hackspace.networkingadapter;

/**
 * Created by Eder on 14/02/2018.
 */

public class cMoneda {
    //region Inicialización de variables
    public String Imagen;
    public String Nombre;
    public String Precio;
    public String PrecioDia;
    public String PrecioSemana;
    public Boolean RojoDia;
    public Boolean RojoSemana;
    public Boolean InfoDia;
    public Boolean InfoSemana;
    //endregion Inicialización de variables

    public cMoneda(String Imagen, String Nombre, String Precio, String PrecioDia, String PrecioSemana){
        //region Designación de variables
        this.Imagen = Imagen;
        this.Nombre = Nombre;
        this.Precio = Precio;
        this.PrecioDia = PrecioDia;
        this.PrecioSemana = PrecioSemana;

        //Verificación de variación del día
        if(PrecioDia.equals("null")){
            this.InfoDia = true;
        }
        else {
            if (Float.valueOf(PrecioDia) < 0) {this.RojoDia = true;}
            else {this.RojoDia = false;}
            this.InfoDia = false;
        }

        //Verificación de variación de la semana
        if(PrecioSemana.equals("null")){
            this.InfoSemana = true;
        }
        else {
            if (Float.valueOf(PrecioSemana) < 0) {this.RojoSemana = true;}
            else {this.RojoSemana = false;}
            this.InfoSemana = false;
        }
        //endregion Designación de variables
    }

    public String getImagen() {
        return Imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public String getPrecioDia() {
        return PrecioDia;
    }

    public String getPrecioSemana() {
        return PrecioSemana;
    }

    public Boolean getRojoDia() {
        return RojoDia;
    }

    public Boolean getRojoSemana() {
        return RojoSemana;
    }

    public Boolean getInfoDia() {
        return InfoDia;
    }

    public Boolean getInfoSemana() {
        return InfoSemana;
    }
}
