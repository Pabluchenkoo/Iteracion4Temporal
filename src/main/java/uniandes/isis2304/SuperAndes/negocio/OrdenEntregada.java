package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public class OrdenEntregada implements VOOrdenEntregada{
    
    private long iDOrdenPedido;

    private String nombre;

    private Timestamp fecha;


    public OrdenEntregada(long iDOrdenPedido, String nombre, Timestamp fecha) {
        super();
        this.iDOrdenPedido = iDOrdenPedido;
        this.nombre = nombre;
        this.fecha = fecha;

    }

    @Override
    public long getIdOrdenPedido() {
        // TODO Auto-generated method stub
        return iDOrdenPedido;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public Timestamp getFecha() {
        return fecha;
    }

    @Override
    public String toString() {
        return "OrdenEntregada{" +
                "iDOrdenPedido=" + iDOrdenPedido +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                '}';
    }
}
