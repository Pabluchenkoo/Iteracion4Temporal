package uniandes.isis2304.SuperAndes.negocio;

public class Supermercado implements VOSupermercado{
	private long nit;
	private String nombre;
	
	public Supermercado(long nit, String nombre) {
		this.nit = nit;
		this.nombre = nombre;
	}
	
	public Supermercado() {
		this.nit = 0;
		this.nombre = "";
	}

	public long getNit() {
		return nit;
	}

	public void setNit(long nit) {
		this.nit = nit;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Supermercado [NIT=" + nit + ", nombre=" + nombre + "]";
	}
	
}
