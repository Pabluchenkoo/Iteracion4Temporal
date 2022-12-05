package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public class OrdenPedido implements VOOrdenPedido{

    private long iDSucursal;

    private long iDProveedor;
    
    private long iDOrdenPedido;
    
    private String nombre;
    
    private Timestamp fecha;
    
    private Timestamp fechaEntregaEsperada;
    
    private double precioTotal;

    public OrdenPedido(long iDSucursal, long iDProveedor, long iDOrdenPedido, String nombre, Timestamp fecha, Timestamp fechaEntregaEsperada, double precioTotal) {
        this.iDSucursal = iDSucursal;
        this.iDProveedor = iDProveedor;
        this.iDOrdenPedido = iDOrdenPedido;
        this.nombre = nombre;
        this.fecha = fecha;
        this.fechaEntregaEsperada = fechaEntregaEsperada;
        this.precioTotal = precioTotal;
    }

    @Override
    public long getIdSucursal() {
        // TODO Auto-generated method stub
        return iDSucursal;
    }

    @Override
    public long getIdOrdenPedido() {
        // TODO Auto-generated method stub
        return iDOrdenPedido;
    }

    @Override
    public long getIdProveedor() {
        return iDProveedor;
    }

    @Override
    public String getNombre() {
        // TODO Auto-generated method stub
        return nombre;
    }

    @Override
    public Timestamp getFecha() {
        // TODO Auto-generated method stub
        return fecha;
    }

    @Override
    public Timestamp getfechaEntregaEsperada() {
        // TODO Auto-generated method stub
        return fechaEntregaEsperada;
    }

    @Override
    public double getPrecioTotal() {
        // TODO Auto-generated method stub
        return precioTotal;
    }

    @Override
    public String toString() {
        return "OrdenPedido{" +
                "iDSucursal=" + iDSucursal +
                ", iDProveedor=" + iDProveedor +
                ", iDOrdenPedido=" + iDOrdenPedido +
                ", nombre='" + nombre + '\'' +
                ", fecha=" + fecha +
                ", fechaEntregaEsperada=" + fechaEntregaEsperada +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
