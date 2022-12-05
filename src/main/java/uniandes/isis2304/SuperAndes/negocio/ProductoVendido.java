package uniandes.isis2304.SuperAndes.negocio;

public class ProductoVendido implements VOProductoVendido{
	private long idFactura;

	private long idsucursal;
	private String codigoDeBarras;
	private long idProductoVendido;
	private int cantidad;


	public ProductoVendido(long idFactura, long idsucursal, String codigoDeBarras, long idProductoVendido, int cantidad) {
		this.idFactura = idFactura;
		this.idsucursal = idsucursal;
		this.codigoDeBarras = codigoDeBarras;
		this.idProductoVendido = idProductoVendido;
		this.cantidad = cantidad;
	}

	@Override
	public long getIdFactura() {
		return idFactura;
	}

	public void setIdFactura(long idFactura) {
		this.idFactura = idFactura;
	}

	public long getIdsucursal() {
		return idsucursal;
	}

	public void setIdsucursal(long idsucursal) {
		this.idsucursal = idsucursal;
	}

	@Override
	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	@Override
	public long getIdProductoVendido() {
		return idProductoVendido;
	}

	public void setIdProductoVendido(long idProductoVendido) {
		this.idProductoVendido = idProductoVendido;
	}

	@Override
	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public String toString() {
		return "ProductoVendido{" +
				"idFactura=" + idFactura +
				", idsucursal=" + idsucursal +
				", codigoDeBarras='" + codigoDeBarras + '\'' +
				", idProductoVendido=" + idProductoVendido +
				", cantidad=" + cantidad +
				'}';
	}
}
