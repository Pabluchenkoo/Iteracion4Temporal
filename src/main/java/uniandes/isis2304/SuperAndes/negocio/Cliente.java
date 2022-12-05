package uniandes.isis2304.SuperAndes.negocio;

public class Cliente implements VOCliente{
    
     private long numDoc;
     
     private String tipoDoc;
     
     private String nombre;
     
     private String correo;
     
     private String medioPago;
     
     private int puntos;
     
     

    public Cliente(long numDoc, String tipoDoc, String nombre, String correo, String medioPago, int puntos) {
        super();
        this.numDoc = numDoc;
        this.tipoDoc = tipoDoc;
        this.nombre = nombre;
        this.correo = correo;
        this.medioPago = medioPago;
        this.puntos = puntos;
    }
    public Cliente() {
        this.numDoc = 0;
        this.tipoDoc = "";
        this.nombre = "";
        this.correo = "";
        this.medioPago = "";
        this.puntos = 0;
    }

    @Override
    public long getNumDoc() {
        // TODO Auto-generated method stub
        return numDoc;
    }

    @Override
    public String getTipoDoc() {
        // TODO Auto-generated method stub
        return tipoDoc;
    }

    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        return nombre;
    }

    @Override
    public String getCorreo() {
        // TODO Auto-generated method stub
        return correo;
    }

    @Override
    public String getMedioPago() {
        // TODO Auto-generated method stub
        return medioPago;
    }

    @Override
    public long getPuntos() {
        // TODO Auto-generated method stub
        return puntos;
    }

    @Override
    public String toString() {
        return "Cliente [numDoc=" + numDoc + ", tipoDoc=" + tipoDoc + ", nombre=" + nombre + ", correo=" + correo
                + ", medioPago=" + medioPago + ", puntos=" + puntos + "]";
    }
     
     

}
