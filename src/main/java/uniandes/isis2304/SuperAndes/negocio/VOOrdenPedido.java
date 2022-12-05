package uniandes.isis2304.SuperAndes.negocio;

import java.sql.Timestamp;

public interface VOOrdenPedido {
    
    public long getIdSucursal();
    public long getIdOrdenPedido();

    public long getIdProveedor();
    public String getNombre();
    public Timestamp getFecha();
    public Timestamp getfechaEntregaEsperada();
    public double getPrecioTotal();
    

}
