package uniandes.isis2304.SuperAndes.negocio;

public interface VOProducto {

	public long getIdPresentacion();

	public long getIdcategoria();

	public long getIdproveedor();

	public long getIdordenpedido();

	public long getIdLote();

	public String getCodigoDeBarras();

	public String getNombre();
	public String getMarca();

	public String toString();
}
