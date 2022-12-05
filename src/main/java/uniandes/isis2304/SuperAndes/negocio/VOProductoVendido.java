package uniandes.isis2304.SuperAndes.negocio;

public interface VOProductoVendido {
	public long getIdFactura();

	public long getIdsucursal();
	public String getCodigoDeBarras();
	public long getIdProductoVendido();
	public int getCantidad();
	public String toString();
}
