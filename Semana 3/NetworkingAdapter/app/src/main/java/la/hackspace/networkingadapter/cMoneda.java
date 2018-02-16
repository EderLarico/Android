package la.hackspace.networkingadapter;

/**
 * Created by Eder on 14/02/2018.
 */

public class cMoneda {
    public String Imagen;
    public String Nombre;
    public String Precio;
    public String PrecioDia;
    public String PrecioSemana;
    public Boolean RojoDia;
    public Boolean RojoSemana;
    public Boolean InfoDia;
    public Boolean InfoSemana;

    public cMoneda(String Imagen, String Nombre, String Precio, String PrecioDia, String PrecioSemana){
        this.Imagen = Imagen;
        this.Nombre = Nombre;
        this.Precio = Precio;
        this.PrecioDia = PrecioDia;
        this.PrecioSemana = PrecioSemana;
        if(PrecioDia.equals("null")){
            this.InfoDia = true;
        }
        else {
            if (Float.valueOf(PrecioDia) < 0) {this.RojoDia = true;}
            else {this.RojoDia = false;}
            this.InfoDia = false;
        }
        if(PrecioSemana.equals("null")){
            this.InfoSemana = true;
        }
        else {
            if (Float.valueOf(PrecioSemana) < 0) {this.RojoSemana = true;}
            else {this.RojoSemana = false;}
            this.InfoSemana = false;
        }

    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getPrecio() {
        return Precio;
    }

    public void setPrecio(String precio) {
        Precio = precio;
    }

    public String getPrecioDia() {
        return PrecioDia;
    }

    public void setPrecioSemana(String precioSemana) {
        PrecioDia = precioSemana;
    }

    public String getPrecioSemana() {
        return PrecioSemana;
    }

    public void setPrecioMes(String precioMes) {
        PrecioSemana = precioMes;
    }

    public Boolean getRojoDia() {
        return RojoDia;
    }

    public void setRojoDia(Boolean rojo) {
        RojoDia = rojo;
    }

    public Boolean getRojoSemana() {
        return RojoSemana;
    }

    public void setRojoSemana(Boolean rojo) {
        RojoSemana = rojo;
    }

    public Boolean getInfoDia() {
        return InfoDia;
    }

    public void setInfoDia(Boolean infoDia) {
        InfoDia = infoDia;
    }

    public Boolean getInfoSemana() {
        return InfoSemana;
    }

    public void setInfoSemana(Boolean infoSemana) {
        InfoSemana = infoSemana;
    }
}
