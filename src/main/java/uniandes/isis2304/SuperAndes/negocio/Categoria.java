package uniandes.isis2304.SuperAndes.negocio;

public class Categoria implements VOCategoria{

	private long idCategoria;
	private String nombre;
	
	public Categoria(long idCategoria, String nombre) {
		this.idCategoria = idCategoria;
		this.nombre = nombre;
	}
	
	public Categoria() {
		this.idCategoria = 0;
		this.nombre = "";
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Categoria [idCategoria=" + idCategoria + ", nombre=" + nombre + "]";
	}
	
}
