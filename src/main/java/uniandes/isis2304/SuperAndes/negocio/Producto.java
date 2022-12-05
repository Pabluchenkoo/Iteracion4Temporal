package uniandes.isis2304.SuperAndes.negocio;

public class Producto implements VOProducto{
	
	private long idPresentacion;

	private long idcategoria;

	private long idproveedor;

	private long idordenpedido;

	private long idLote;
	private String codigoDeBarras;
	private String nombre;
	private String marca;


	public Producto(long idPresentacion, long idcategoria, long idproveedor, long idordenpedido,long idLote, String codigoDeBarras, String nombre, String marca) {
		this.idPresentacion = idPresentacion;
		this.idcategoria = idcategoria;
		this.idproveedor = idproveedor;
		this.idordenpedido = idordenpedido;
		this.idLote = idLote;
		this.codigoDeBarras = codigoDeBarras;
		this.nombre = nombre;
		this.marca = marca;
	}

	public long getIdPresentacion() {
		return idPresentacion;
	}

	@Override
	public long getIdcategoria() {
		return idcategoria;
	}

	@Override
	public long getIdproveedor() {
		return idproveedor;
	}

	@Override
	public long getIdordenpedido() {
		return idordenpedido;
	}

	@Override
	public long getIdLote() {
		return idLote;
	}

	public void setIdcategoria(long idcategoria) {
		this.idcategoria = idcategoria;
	}

	public void setIdproveedor(long idproveedor) {
		this.idproveedor = idproveedor;
	}

	public void setIdordenpedido(long idordenpedido) {
		this.idordenpedido = idordenpedido;
	}

	public void setIdPresentacion(long idPresentacion) {
		this.idPresentacion = idPresentacion;
	}

	public String getCodigoDeBarras() {
		return codigoDeBarras;
	}

	public void setCodigoDeBarras(String codigoDeBarras) {
		this.codigoDeBarras = codigoDeBarras;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}


	@Override
	public String toString() {
		return "Producto{" +
				"idPresentacion=" + idPresentacion +
				", idcategoria=" + idcategoria +
				", idproveedor=" + idproveedor +
				", idordenpedido=" + idordenpedido +
				", idLote=" + idLote +
				", codigoDeBarras='" + codigoDeBarras + '\'' +
				", nombre='" + nombre + '\'' +
				", marca='" + marca + '\'' +
				'}';
	}
}
