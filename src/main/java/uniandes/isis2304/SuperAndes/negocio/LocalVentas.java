package uniandes.isis2304.SuperAndes.negocio;

public class LocalVentas implements VOLocalVentas{
	
	private long idSucursal;

	private long idProductoVendido;
	private long idLocalVentas;
	private double tamanio;
	private String direccion;
	
	public LocalVentas(long idSucursal,long idProductoVendido, long idLocalVentas, double tamanio, String direccion) {
		this.idSucursal = idSucursal;
		this.idProductoVendido = idProductoVendido;
		this.idLocalVentas = idLocalVentas;
		this.tamanio = tamanio;
		this.direccion = direccion;
	}
	
	public LocalVentas() {
		this.idSucursal = 0;
		this.idProductoVendido = 0;
		this.idLocalVentas = 0;
		this.tamanio = 0;
		this.direccion = "";
	}

	public long getIdSucursal() {
		return idSucursal;
	}

	@Override
	public long getIdProductoVendido() {
		return idProductoVendido;
	}

	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}

	public long getIdLocalVentas() {
		return idLocalVentas;
	}

	public void setIdLocalVentas(long idLocalVentas) {
		this.idLocalVentas = idLocalVentas;
	}

	public double getTamanio() {
		return tamanio;
	}

	public void setTamanio(double tamanio) {
		this.tamanio = tamanio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	@Override
	public String toString() {
		return "LocalVentas [idSucursal=" + idSucursal + ", idLocalVentas=" + idLocalVentas + ", tamanio=" + tamanio
				+ ", direccion=" + direccion + "]";
	}
	
}
