package uniandes.isis2304.SuperAndes.negocio;

public class Sucursal implements VOSucursal{
	private long nitSupermercado;
	private long idSucursal;
	private String nombre;
	private int tamanio;
	private String ciudad;
	
	
	public Sucursal() {
		this.nitSupermercado = 0;
		this.idSucursal = 0;
		this.nombre = "";
		this.tamanio = 0;
		this.ciudad = "";
	}


	public Sucursal(long nitSupermercado, long idSucursal, String nombre, int tamanio, String ciudad) {
		this.nitSupermercado = nitSupermercado;
		this.idSucursal = idSucursal;
		this.nombre = nombre;
		this.tamanio = tamanio;
		this.ciudad = ciudad;
	}


	public long getNitSupermercado() {
		return nitSupermercado;
	}


	public void setNitSupermercado(long nitSupermercado) {
		this.nitSupermercado = nitSupermercado;
	}


	public long getIdSucursal() {
		return idSucursal;
	}


	public void setIdSucursal(long idSucursal) {
		this.idSucursal = idSucursal;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public int getTamanio() {
		return tamanio;
	}


	public void setTamanio(int tamanio) {
		this.tamanio = tamanio;
	}


	public String getCiudad() {
		return ciudad;
	}


	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	@Override
	public String toString() {
		return "Sucursal [nitSupermercado=" + nitSupermercado + ", idSucursal=" + idSucursal + ", nombre=" + nombre
				+ ", tamanio=" + tamanio + ", ciudad=" + ciudad + "]";
	}
	
	
}
